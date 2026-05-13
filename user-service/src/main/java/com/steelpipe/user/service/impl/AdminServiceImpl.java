package com.steelpipe.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.steelpipe.common.exception.CustomerException;
import com.steelpipe.user.entity.Account;
import com.steelpipe.user.entity.Admin;
import com.steelpipe.user.mapper.AdminMapper;
import com.steelpipe.user.service.AdminService;
import com.steelpipe.user.utils.TokenUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 登录频率限制：2小时（单位：秒）
    private static final long LOGIN_RATE_LIMIT_SECONDS = 2 * 60 * 60;

    @Value("${login.rate-limit.enabled:true}")
    private boolean loginRateLimitEnabled;

    @Override
    public void add(Admin admin) {
        // 根据新的账号查询数据库 是否存在同样账号的数据
        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
        if (dbAdmin != null) {
            throw new CustomerException("账号重复");
        }
        // 默认密码
        if (StrUtil.isBlank(admin.getPassword())) {
            admin.setPassword("123456");
        }
        // 对密码进行 BCrypt 加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole("ADMIN");
        adminMapper.insert(admin);
    }

    @Override
    public void update(Admin admin) {
        adminMapper.updateById(admin);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Admin> list) {
        for (Admin admin : list) {
            adminMapper.deleteById(admin.getId());
        }
    }

    @Override
    public Admin selectById(String id) {
        return adminMapper.selectById(id);
    }

    @Override
    public List<Admin> selectAll() {
        return adminMapper.selectAll(null);
    }

    @Override
    public PageInfo<Admin> selectPage(Integer pageNum, Integer pageSize, Admin admin) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }

    /**
     * 管理员登录方法
     */
    @Override
    public Admin login(Account account) {
        // 验证账号是否存在
        Admin dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (dbAdmin == null) {
            throw new CustomerException("账号不存在");
        }

        // 检查登录频率限制（2小时内只能登录一次）
        if (loginRateLimitEnabled) {
            String loginRateKey = "login_rate:admin:" + account.getUsername();
            Object lastLoginTime = redisTemplate.opsForValue().get(loginRateKey);
            if (lastLoginTime != null) {
                long lastLoginTimestamp = Long.parseLong(lastLoginTime.toString());
                long currentTime = System.currentTimeMillis();
                long timeDiff = (currentTime - lastLoginTimestamp) / 1000; // 转换为秒

                if (timeDiff < LOGIN_RATE_LIMIT_SECONDS) {
                    long remainingSeconds = LOGIN_RATE_LIMIT_SECONDS - timeDiff;
                    long remainingHours = remainingSeconds / 3600;
                    long remainingMinutes = (remainingSeconds % 3600) / 60;
                    throw new CustomerException("登录过于频繁，请" + remainingHours + "小时" + remainingMinutes + "分钟后再试");
                }
            }
        }

        // 使用 BCrypt 验证密码
        String inputPassword = account.getPassword();
        String dbAdminPassword = dbAdmin.getPassword();
        boolean isValid = passwordEncoder.matches(inputPassword, dbAdminPassword);

        if (!isValid) {
            throw new CustomerException("账号或密码错误");
        }

        // 记录本次登录时间（用于频率限制，登录成功后才记录）
        if (loginRateLimitEnabled) {
            String loginRateKey = "login_rate:admin:" + account.getUsername();
            redisTemplate.opsForValue().set(loginRateKey, System.currentTimeMillis(), LOGIN_RATE_LIMIT_SECONDS, TimeUnit.SECONDS);
        }

        // 获取客户端 IP 地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String clientIp = getClientIp(request);

        // 创建token并返回给前端（包含 IP 绑定）
        String token = TokenUtils.createToken(dbAdmin.getId() + "-" + "ADMIN", dbAdmin.getPassword(), clientIp);
        dbAdmin.setToken(token);

        return dbAdmin;
    }

    @Override
    public void updatePassword(Account account) {
        // 判断新密码和旧密码是否相等
        if (!account.getNewpassword().equals(account.getNew2password())) {
            throw new CustomerException("500", "你两次输入的密码不一致");
        }
        // 判断原密码是否正确
        Account currentUser = TokenUtils.getCurrentUser();
        if (!passwordEncoder.matches(account.getPassword(), currentUser.getPassword())) {
            throw new CustomerException("500", "原密码输入错误");
        }
        // 开始更新密码 - BCrypt 加密
        account.setNewpassword(passwordEncoder.encode(account.getNewpassword()));
        Admin admin = adminMapper.selectById(currentUser.getId().toString());
        admin.setPassword(account.getNewpassword());
        adminMapper.updateById(admin);
    }

    /**
     * 获取客户端真实 IP 地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于通过多个代理的情况，第一个 IP 为客户端真实 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
