package com.steelpipe.file.controller;

import com.steelpipe.common.result.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${file.upload-url}")
    private String uploadUrl;

    @Value("${file.download-url}")
    private String downloadUrl;

    @Value("${file.storage-path:D:\\steelfactory\\files\\}")
    private String storagePath;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return R.error("上传文件不能为空");
        }
        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀
        String ext = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        // 使用 UUID 生成新文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + ext;
        // 构造存储目录
        File dir = new File(storagePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 保存文件
        try {
            file.transferTo(new File(storagePath + newFileName));
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
        // 返回文件下载路径
        String url = downloadUrl + "/" + newFileName;
        return R.success(url);
    }

    /**
     * 文件下载（富文本编辑器图片回显也使用这个接口）
     */
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        try {
            File file = new File(storagePath + fileName);
            if (!file.exists()) {
                response.setStatus(404);
                return;
            }
            // 设置响应头
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            response.setHeader("Content-Type", "application/octet-stream");
            // 读取文件
            FileInputStream fis = new FileInputStream(file);
            ServletOutputStream os = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
            os.close();
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }
    }
}
