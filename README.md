# 钢铁工厂微服务项目

基于 Spring Boot 3.3.6 + Spring Cloud 2023.0.4 + Spring Cloud Alibaba 2023.0.3.3 的微服务架构。

## 项目结构

```
steel-pipe-factory-microservices/
├── api-gateway/        # API 网关 (端口 8080)
├── user-service/       # 用户服务 (端口 8081) — Admin/User 登录认证
├── product-service/    # 产品服务 (端口 8082) — 钢材价格/样品展示
├── content-service/    # 内容服务 (端口 8083) — 关于我们/精选企业/资讯
├── file-service/       # 文件服务 (端口 8084) — 文件上传下载
├── common/             # 公共模块 — R/ResultCodeEnum/Exception/Redis/CORS
├── admin-frontend/     # Vue 管理后台 (Element Plus)
├── uniapp/             # 微信小程序 (UniApp)
├── sql/                # 数据库初始化脚本
└── docker-compose.yml  # Docker 编排
```

## 技术栈

| 组件 | 版本 |
|---|---|
| Spring Boot | 3.3.6 |
| Spring Cloud | 2023.0.4 |
| Spring Cloud Alibaba | 2023.0.3.3 |
| Nacos | 2.4.3 (服务注册/发现) |
| MySQL | 8.0 |
| Redis | 7 |
| MyBatis + PageHelper | 3.0.4 |
| Flyway | 10.10.0 |
| JWT (auth0) | 4.4.0 |
| Hutool | 5.8.25 |

## 数据库

- `steel_user_db` — admin, user 表
- `steel_product_db` — steel_product_price, sample_display 表
- `steel_content_db` — about_us, featured_company, obtain_information 表

## 快速启动

### 前置条件

- JDK 17+
- Maven 3.8+
- MySQL 8.0
- Redis 7
- Nacos 2.4.3

### 本地开发

1. 启动 Nacos、MySQL、Redis
2. 执行 `sql/init-databases.sql` 创建数据库和表
3. 修改各服务的 `application.yml` 中的数据库/Redis/Nacos 连接信息
4. 依次启动各服务：gateway → user → product → content → file
5. 访问 `http://localhost:8080` (Gateway)

### Docker 部署

```bash
docker-compose up -d
```

### 前端

```bash
# 管理后台
cd admin-frontend
npm install
npm run dev

# 小程序
cd uniapp
npm install
npm run dev:mp-weixin
```

## 网关路由

| 前端路径 | 目标服务 | 说明 |
|---|---|---|
| `/api/v1/admin/**` | user-service | 管理后台 (StripPrefix=2) |
| `/api/v1/user/**` | user-service | 管理后台 (StripPrefix=2) |
| `/api/v1/steelProductPrice/**` | product-service | 管理后台 (StripPrefix=2) |
| `/api/v1/sampleDisplay/**` | product-service | 管理后台 (StripPrefix=2) |
| `/api/v1/aboutUs/**` | content-service | 管理后台 (StripPrefix=2) |
| `/api/v1/featuredCompany/**` | content-service | 管理后台 (StripPrefix=2) |
| `/api/v1/obtainInformation/**` | content-service | 管理后台 (StripPrefix=2) |
| `/api/v1/files/**` | file-service | 管理后台 (StripPrefix=2) |
| `/admin/**` | user-service | 小程序直连 |
| `/user/**` | user-service | 小程序直连 |
| `/steelProductPrice/**` | product-service | 小程序直连 |
| `/sampleDisplay/**` | product-service | 小程序直连 |
| `/aboutUs/**` | content-service | 小程序直连 |
| `/featuredCompany/**` | content-service | 小程序直连 |
| `/obtainInformation/**` | content-service | 小程序直连 |
| `/uniapp/**` | content-service | 小程序直连 |
| `/files/**` | file-service | 小程序直连 |

## 认证机制

- Gateway `AuthGlobalFilter` 统一认证
- JWT (HMAC256) + Redis 存储 + IP 绑定
- 白名单：`/login`, `/hello`, `/uniapp/**`, `/files/download/**` 等
