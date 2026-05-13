# 钢铁工厂微服务项目

基于 **Spring Boot 3.3.6** + **Spring Cloud 2023.0.4** + **Spring Cloud Alibaba 2023.0.3.3** 构建的钢铁工厂管理微服务系统。

## 📋 目录

- [项目概述](#项目概述)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [核心功能](#核心功能)
- [架构设计](#架构设计)
- [快速开始](#快速开始)
- [API 接口文档](#api-接口文档)
- [认证与授权](#认证与授权)
- [数据库设计](#数据库设计)
- [部署指南](#部署指南)
- [开发规范](#开发规范)

---

## 项目概述

钢铁工厂微服务项目是一个完整的工厂管理系统，包含管理后台（Vue3）和微信小程序（UniApp）两个前端应用，后端采用微服务架构，提供用户管理、产品价格展示、内容管理、文件上传下载等功能。

**核心特性：**
- 🔐 JWT + Redis 双重认证机制，支持 IP 绑定防多设备登录
- 🚪 API Gateway 统一网关鉴权，白名单灵活配置
- 💾 BCrypt 密码加密，保障数据安全
- ⚡ Redis 缓存优化，延迟双删策略保证数据一致性
- 🌐 Nacos 服务注册与发现
- 📦 MyBatis + PageHelper 分页查询
- 🔄 Flyway 数据库版本管理
- 🐳 Docker 容器化部署

---

## 技术栈

### 后端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| **JDK** | 17 | Java 运行环境 |
| **Spring Boot** | 3.3.6 | 核心框架 |
| **Spring Cloud** | 2023.0.4 | 微服务框架 |
| **Spring Cloud Alibaba** | 2023.0.3.3 | 阿里微服务组件 |
| **Nacos** | 2.4.3 | 服务注册与发现、配置中心 |
| **Spring Cloud Gateway** | - | API 网关 |
| **MyBatis** | 3.0.4 | ORM 框架 |
| **PageHelper** | 2.1.0 | 分页插件 |
| **MySQL** | 8.0 | 关系型数据库 |
| **Redis** | 7 | 缓存数据库 |
| **Flyway** | 10.10.0 | 数据库迁移工具 |
| **JWT (auth0)** | 4.4.0 | JSON Web Token 认证 |
| **BCrypt** | - | 密码加密 |
| **Hutool** | 5.8.25 | Java 工具类库 |
| **Lombok** | 1.18.34 | 简化代码 |

### 前端技术

#### 管理后台 (admin-frontend)
- Vue 3
- Element Plus
- Axios

#### 微信小程序 (migrate-miniprogram)
- UniApp
- Vue 3

---

## 项目结构

```
steel-pipe-factory-microservices/
├── common/                          # 公共模块（其他服务依赖）
│   ├── src/main/java/com/steelpipe/common/
│   │   ├── config/
│   │   │   ├── CorsConfig.java      # CORS 跨域配置
│   │   │   └── RedisConfig.java     # Redis 序列化配置
│   │   ├── exception/
│   │   │   ├── CustomerException.java    # 自定义业务异常
│   │   │   └── GlobalExceptionHandler.java # 全局异常处理器
│   │   └── result/
│   │       ├── R.java               # 统一返回结果封装
│   │       └── ResultCodeEnum.java  # 响应状态码枚举
│   └── pom.xml
│
├── api-gateway/                     # API 网关服务（端口 8080）
│   ├── src/main/java/com/steelpipe/gateway/
│   │   ├── GatewayApplication.java  # 启动类
│   │   ├── config/
│   │   │   └── RedisConfig.java     # Redis 配置
│   │   └── filter/
│   │       └── AuthGlobalFilter.java # JWT 全局认证过滤器
│   ├── src/main/resources/
│   │   └── application.yml          # 配置文件
│   ├── Dockerfile
│   └── pom.xml
│
├── user-service/                    # 用户服务（端口 8081）
│   ├── src/main/java/com/steelpipe/user/
│   │   ├── UserServiceApplication.java # 启动类
│   │   ├── common/
│   │   │   ├── JWTInterceptor.java  # JWT 拦截器（内部验证）
│   │   │   └── WebConfig.java       # Web 配置（白名单）
│   │   ├── controller/
│   │   │   ├── AdminController.java # 管理员 CRUD
│   │   │   ├── UserController.java  # 用户 CRUD
│   │   │   └── WebController.java   # 登录/修改密码
│   │   ├── entity/
│   │   │   ├── Account.java         # 账号实体（登录用）
│   │   │   ├── Admin.java           # 管理员实体
│   │   │   └── User.java            # 用户实体
│   │   ├── mapper/
│   │   │   ├── AdminMapper.java     # 管理员 Mapper
│   │   │   └── UserMapper.java      # 用户 Mapper
│   │   ├── service/
│   │   │   ├── AdminService.java    # 管理员服务接口
│   │   │   ├── UserService.java     # 用户服务接口
│   │   │   └── impl/
│   │   │       ├── AdminServiceImpl.java
│   │   │       └── UserServiceImpl.java
│   │   └── utils/
│   │       └── TokenUtils.java      # JWT Token 工具类
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── mapper/
│   │       ├── AdminMapper.xml
│   │       └── UserMapper.xml
│   ├── Dockerfile
│   └── pom.xml
│
├── product-service/                 # 产品服务（端口 8082）
│   ├── src/main/java/com/steelpipe/product/
│   │   ├── ProductServiceApplication.java # 启动类
│   │   ├── controller/
│   │   │   ├── SteelProductPriceController.java    # 钢材价格管理
│   │   │   └── SampleDisplayController.java        # 样品展示管理
│   │   ├── entity/
│   │   │   ├── SteelProductPrice.java # 钢材价格实体
│   │   │   └── SampleDisplay.java     # 样品展示实体
│   │   ├── mapper/
│   │   │   ├── SteelProductPriceMapper.java
│   │   │   └── SampleDisplayMapper.java
│   │   ├── service/
│   │   │   ├── SteelProductPriceService.java
│   │   │   ├── SampleDisplayService.java
│   │   │   └── impl/
│   │   │       ├── SteelProductPriceServiceImpl.java
│   │   │       └── SampleDisplayServiceImpl.java
│   │   └── (service impl 实现含 Redis 缓存逻辑)
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── mapper/
│   │       ├── SteelProductPriceMapper.xml
│   │       └── SampleDisplayMapper.xml
│   ├── Dockerfile
│   └── pom.xml
│
├── content-service/                 # 内容服务（端口 8083）
│   ├── src/main/java/com/steelpipe/content/
│   │   ├── ContentServiceApplication.java # 启动类
│   │   ├── config/
│   │   │   └── RestTemplateConfig.java # RestTemplate 负载均衡配置
│   │   ├── controller/
│   │   │   ├── AboutUsController.java          # 关于我们
│   │   │   ├── FeaturedCompanyController.java  # 精选企业
│   │   │   ├── ObtainInformationController.java # 资讯信息
│   │   │   └── UniappController.java           # 小程序数据接口
│   │   ├── entity/
│   │   │   ├── AboutUs.java
│   │   │   ├── FeaturedCompany.java
│   │   │   └── ObtainInformation.java
│   │   ├── mapper/
│   │   │   ├── AboutUsMapper.java
│   │   │   ├── FeaturedCompanyMapper.java
│   │   │   └── ObtainInformationMapper.java
│   │   └── service/
│   │       ├── AboutUsService.java
│   │       ├── FeaturedCompanyService.java
│   │       ├── ObtainInformationService.java
│   │       └── impl/
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── mapper/
│   │       ├── AboutUsMapper.xml
│   │       ├── FeaturedCompanyMapper.xml
│   │       └── ObtainInformationMapper.xml
│   ├── Dockerfile
│   └── pom.xml
│
├── file-service/                    # 文件服务（端口 8084）
│   ├── src/main/java/com/steelpipe/file/
│   │   ├── FileServiceApplication.java # 启动类
│   │   └── controller/
│   │       └── FileController.java  # 文件上传下载
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   └── pom.xml
│
├── admin-frontend/                  # Vue3 管理后台
├── migrate-miniprogram/             # UniApp 微信小程序
├── sql/                             # 数据库初始化脚本
│   └── init-databases.sql
├── docker-compose.yml               # Docker 编排文件
├── .gitignore                       # Git 忽略配置
├── .dockerignore                    # Docker 忽略配置
└── pom.xml                          # Maven 父 POM
```

---

## 核心功能

### 1. 用户认证模块 (user-service)
- **登录认证**：支持 ADMIN（管理员）和 USER（普通用户）两种角色登录
- **密码加密**：使用 BCrypt 进行密码加密存储
- **Token 管理**：JWT + Redis 双重验证，支持 IP 绑定防止多设备登录
- **登录频率限制**：同一账号 2 小时内只能登录一次（可配置）
- **密码修改**：支持用户修改密码，需验证原密码
- **用户管理**：管理员和用户信息的 CRUD 操作

### 2. 产品服务模块 (product-service)
- **钢材价格管理**：钢材产品价格的增删改查
  - 字段：名称、规格、价格、涨跌幅、日期
  - 支持分页查询和条件搜索
- **样品展示管理**：产品展示的增删改查
  - 字段：名称、规格、材质、特点、应用场景、图片、分类、是否新品
  - 特点字段采用 JSON 格式存储，前端解析为数组
  - 支持分页查询和按名称/分类搜索
- **Redis 缓存优化**：
  - `SteelProductPrice`：延迟双删策略（500ms），缓存 5 分钟
  - `SampleDisplay`：写操作清除缓存，读操作缓存 10 分钟

### 3. 内容服务模块 (content-service)
- **关于我们**：企业介绍信息管理
- **精选企业**：合作企业信息展示
- **资讯信息**：行业新闻和公告管理
- **小程序接口**：提供 UniApp 小程序专用数据接口
- **负载均衡**：集成 RestTemplate + LoadBalancer 支持服务间调用

### 4. 文件服务模块 (file-service)
- **文件上传**：支持单文件上传，自动生成 UUID 文件名
- **文件下载**：支持文件下载和图片回显
- **存储路径**：默认存储在 `D:\\steelfactory\\files\\`
- **跨域支持**：通过 common 模块统一配置 CORS

### 5. API 网关 (api-gateway)
- **统一入口**：所有请求通过网关路由到对应微服务
- **全局鉴权**：JWT Token 验证，支持白名单配置
- **IP 绑定验证**：防止 Token 被其他设备复用
- **用户信息透传**：将解析后的用户 ID 和角色传递给下游服务
- **Nacos 集成**：基于服务名的动态路由

---

## 架构设计

### 系统架构图

```
┌─────────────┐         ┌──────────────┐
│   Client    │────────▶│ API Gateway  │
│ (Web/App)   │         │  (Port 8080) │
└─────────────┘         └──────┬───────┘
                               │
              ┌────────────────┼────────────────┐
              ▼                ▼                ▼
      ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
      │ User Service │ │Product Service│ │Content Service│
      │  (Port 8081) │ │  (Port 8082)  │ │  (Port 8083)  │
      └──────────────┘ └──────────────┘ └──────────────┘
              │                │                │
              └────────────────┼────────────────┘
                               ▼
                      ┌──────────────┐
                      │ File Service │
                      │  (Port 8084) │
                      └──────────────┘
                               │
              ┌────────────────┼────────────────┐
              ▼                ▼                ▼
        ┌──────────┐    ┌──────────┐    ┌──────────┐
        │  MySQL   │    │  Redis   │    │  Nacos   │
        └──────────┘    └──────────┘    └──────────┘
```

### 认证流程

```
1. 用户登录
   ↓
2. user-service 验证账号密码
   ↓
3. 生成 JWT Token（包含 userId-role）
   ↓
4. 存储到 Redis：
   - token:{userId-role} → IP地址
   - token:password:{userId-role} → 密码（用于签名验证）
   ↓
5. 返回 Token 给客户端
   ↓
6. 后续请求携带 Token
   ↓
7. api-gateway 拦截验证：
   - 检查白名单
   - 解析 JWT
   - 验证 Redis 中的密码
   - 验证 IP 匹配
   ↓
8. 透传 X-User-Id 和 X-User-Role 到下游服务
```

### 关键设计模式

1. **统一响应封装**：`R` 类封装所有 API 返回结果
2. **全局异常处理**：`@ControllerAdvice` 统一捕获异常
3. **自定义异常**：`CustomerException` 区分业务异常
4. **延迟双删**：保证 Redis 缓存与数据库一致性
5. **静态初始化**：`TokenUtils` 使用 `@PostConstruct` 注入静态依赖

---

## 快速开始

### 环境准备

1. **JDK 17+**
2. **Maven 3.8+**
3. **MySQL 8.0**
4. **Redis 7**
5. **Nacos 2.4.3**

### 本地开发部署

1. **启动基础设施**
   ```bash
   # 启动 Nacos（作为服务注册中心和配置中心）
   docker run --name nacos -d -p 8848:8848 -e MODE=standalone nacos/nacos-server:v2.4.3
   
   # 启动 MySQL（确保创建所需数据库）
   docker run --name mysql -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0
   
   # 启动 Redis
   docker run --name redis -d -p 6379:6379 redis:7
   ```

2. **初始化数据库**
   ```bash
   # 执行数据库初始化脚本
   mysql -u root -p -h localhost < sql/init-databases.sql
   ```

3. **修改配置文件**
   - 更新各服务 `src/main/resources/application.yml` 中的数据库、Redis、Nacos 连接信息
   - 确保各服务能够连接到对应的外部服务

4. **启动微服务**
   ```bash
   # 1. 启动公共模块（先安装到本地仓库）
   cd common && mvn clean install
   
   # 2. 按顺序启动各服务
   cd ../api-gateway && mvn spring-boot:run  # 端口 8080
   cd ../user-service && mvn spring-boot:run   # 端口 8081
   cd ../product-service && mvn spring-boot:run # 端口 8082
   cd ../content-service && mvn spring-boot:run # 端口 8083
   cd ../file-service && mvn spring-boot:run    # 端口 8084
   ```

5. **启动前端应用**
   ```bash
   # 管理后台
   cd admin-frontend
   npm install
   npm run dev
   
   # 微信小程序
   cd migrate-miniprogram
   npm install
   npm run dev:mp-weixin
   ```

### Docker 部署

1. **构建镜像**
   ```bash
   # 构建所有服务镜像
   mvn clean package -DskipTests
   docker-compose build
   ```

2. **启动容器**
   ```bash
   docker-compose up -d
   ```

---

## API 接口文档

### 1. 用户认证接口 (user-service)

#### 登录接口
- **POST** `/login`
- **请求体**:
  ```json
  {
    "username": "admin",
    "password": "123456",
    "role": "ADMIN"
  }
  ```
- **响应**:
  ```json
  {
    "success": true,
    "code": 20000,
    "message": "请求成功",
    "data": {
      "id": 1,
      "username": "admin",
      "name": "管理员",
      "role": "ADMIN",
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    }
  }
  ```

#### 修改密码接口
- **POST** `/updatePassword`
- **请求体**:
  ```json
  {
    "password": "当前密码",
    "newpassword": "新密码",
    "new2password": "确认新密码"
  }
  ```

#### 管理员管理接口
- **GET** `/admin/selectAll` - 获取所有管理员
- **POST** `/admin/add` - 新增管理员
- **PUT** `/admin/update` - 更新管理员
- **DELETE** `/admin/delete/{id}` - 删除管理员

#### 用户管理接口
- **GET** `/user/selectAll` - 获取所有用户
- **POST** `/user/add` - 新增用户
- **PUT** `/user/update` - 更新用户
- **DELETE** `/user/delete/{id}` - 删除用户

### 2. 产品服务接口 (product-service)

#### 钢材价格管理
- **GET** `/steelProductPrice/selectAll` - 获取所有钢材价格
- **GET** `/steelProductPrice/selectPage` - 分页获取钢材价格
- **POST** `/steelProductPrice/add` - 新增钢材价格
- **PUT** `/steelProductPrice/update` - 更新钢材价格
- **DELETE** `/steelProductPrice/delete/{id}` - 删除钢材价格

#### 样品展示管理
- **GET** `/sampleDisplay/selectAll` - 获取所有样品
- **GET** `/sampleDisplay/selectPage` - 分页获取样品
- **POST** `/sampleDisplay/add` - 新增样品
- **PUT** `/sampleDisplay/update` - 更新样品
- **DELETE** `/sampleDisplay/delete/{id}` - 删除样品

### 3. 内容服务接口 (content-service)

#### 关于我们管理
- **GET** `/aboutUs/selectAll` - 获取关于我们信息
- **POST** `/aboutUs/add` - 新增关于我们信息
- **PUT** `/aboutUs/update` - 更新关于我们信息

#### 精选企业管理
- **GET** `/featuredCompany/selectAll` - 获取精选企业
- **POST** `/featuredCompany/add` - 新增精选企业
- **PUT** `/featuredCompany/update` - 更新精选企业

#### 资讯信息管理
- **GET** `/obtainInformation/selectAll` - 获取资讯信息
- **POST** `/obtainInformation/add` - 新增资讯信息
- **PUT** `/obtainInformation/update` - 更新资讯信息

#### 小程序接口
- **GET** `/uniapp/getAboutData` - 获取关于我们数据
- **GET** `/uniapp/getFeaturedCompaniesDataList` - 获取精选企业列表
- **GET** `/uniapp/getIndustryNewsDetailData` - 获取资讯详情
- **GET** `/uniapp/getSampleData` - 获取样品数据
- **GET** `/uniapp/getSteelPriceData` - 获取钢材价格数据

### 4. 文件服务接口 (file-service)

#### 文件上传
- **POST** `/files/upload`
- **参数**: `file` (multipart/form-data)
- **响应**:
  ```json
  {
    "success": true,
    "code": 20000,
    "message": "请求成功",
    "data": "http://localhost:8084/files/download/filename.jpg"
  }
  ```

#### 文件下载
- **GET** `/files/download/{fileName}`
- **说明**: 直接下载文件

---

## 认证与授权

### JWT Token 结构

Token Payload 包含 Audience 字段，格式为 `{userId}-{role}`，例如 `1-ADMIN` 表示 ID 为 1 的管理员用户。

### 网关认证流程

1. **白名单路径**：以下路径无需认证
   - `/login`, `/api/v1/login`
   - `/updatePassword`, `/api/v1/updatePassword`
   - `/files/download`, `/api/v1/files/download`
   - `/uniapp/**`, `/api/v1/uniapp/**`
   - `/hello`, `/api/v1/hello`
   - `/actuator`

2. **Token 验证**：
   - 从请求头或查询参数获取 `token`
   - 解析 Token 获取 `userId-role`
   - 验证 Redis 中存储的密码
   - 验证客户端 IP 与 Token 绑定的 IP 是否一致

3. **信息透传**：
   - 验证通过后，在请求头添加 `X-User-Id` 和 `X-User-Role`
   - 传递给下游微服务使用

### 角色权限

- **ADMIN**: 管理员角色，拥有完整权限
- **USER**: 普通用户角色，仅拥有基本访问权限

---

## 数据库设计

### 数据库结构

系统包含三个独立数据库：

1. **steel_user_db** - 用户相关数据
   - `admin` 表：管理员信息
   - `user` 表：普通用户信息

2. **steel_product_db** - 产品相关数据
   - `steel_product_price` 表：钢材价格信息
   - `sample_display` 表：样品展示信息

3. **steel_content_db** - 内容相关数据
   - `about_us` 表：关于我们信息
   - `featured_company` 表：精选企业信息
   - `obtain_information` 表：资讯信息

### 表结构详情

#### 用户相关表

**admin 表**:
- `id`: 主键
- `username`: 用户名
- `password`: 加密密码
- `role`: 角色（固定为 ADMIN）
- `name`: 姓名
- `avatar`: 头像

**user 表**:
- `id`: 主键
- `username`: 用户名
- `password`: 加密密码
- `role`: 角色（固定为 USER）
- `name`: 姓名
- `avatar`: 头像

#### 产品相关表

**steel_product_price 表**:
- `id`: 主键
- `name`: 产品名称
- `spec`: 规格
- `price`: 价格
- `change`: 涨跌幅
- `date`: 日期
- `create_time`: 创建时间
- `update_time`: 更新时间

**sample_display 表**:
- `id`: 主键
- `name`: 样品名称
- `spec`: 规格
- `material`: 材质
- `features`: 特点（JSON 格式）
- `application`: 应用场景
- `image`: 图片路径
- `category`: 分类
- `is_new`: 是否新品
- `create_time`: 创建时间
- `update_time`: 更新时间

#### 内容相关表

**about_us 表**:
- `id`: 主键
- `title`: 标题
- `content`: 内容
- `create_time`: 创建时间
- `update_time`: 更新时间

**featured_company 表**:
- `id`: 主键
- `name`: 企业名称
- `description`: 企业描述
- `image`: 企业图片
- `create_time`: 创建时间
- `update_time`: 更新时间

**obtain_information 表**:
- `id`: 主键
- `title`: 资讯标题
- `summary`: 摘要
- `content`: 内容
- `author`: 作者
- `publish_date`: 发布日期
- `create_time`: 创建时间
- `update_time`: 更新时间

---

## 部署指南

### 生产环境部署

1. **环境要求**:
   - JDK 17+
   - MySQL 8.0
   - Redis 7+
   - Nacos 集群模式

2. **配置调整**:
   - 修改各服务的 `application-prod.yml`
   - 配置生产环境数据库、Redis、Nacos 连接
   - 调整日志级别和性能参数

3. **容器化部署**:
   ```yaml
   # docker-compose.yml 示例
   version: '3.8'
   services:
     nacos:
       image: nacos/nacos-server:v2.4.3
       ports:
         - "8848:8848"
       environment:
         - MODE=standalone
     
     mysql:
       image: mysql:8.0
       ports:
         - "3306:3306"
       environment:
         - MYSQL_ROOT_PASSWORD=root
     
     redis:
       image: redis:7
       ports:
         - "6379:6379"
     
     gateway:
       build: ./api-gateway
       ports:
         - "8080:8080"
       depends_on:
         - nacos
     
     user-service:
       build: ./user-service
       ports:
         - "8081:8081"
       depends_on:
         - nacos
         - mysql
         - redis
   ```

4. **监控与运维**:
   - 集成 Spring Boot Actuator 监控
   - 配置健康检查端点
   - 日志收集与分析

---

## 开发规范

### 代码规范

1. **命名规范**:
   - 类名：大驼峰命名法（PascalCase）
   - 方法名/变量名：小驼峰命名法（camelCase）
   - 常量名：全大写下划线分隔（UPPER_SNAKE_CASE）

2. **注释规范**:
   - 类和公共方法必须有 Javadoc 注释
   - 复杂逻辑需添加行内注释
   - 接口定义应说明用途和参数含义

3. **异常处理**:
   - 使用自定义异常类（CustomerException）
   - 业务异常统一返回规范格式
   - 不要忽略异常，必须妥善处理

### Git 规范

1. **分支策略**:
   - `main`: 生产环境主分支
   - `develop`: 开发主分支
   - `feature/*`: 功能开发分支
   - `hotfix/*`: 紧急修复分支

2. **提交规范**:
   - feat: 新功能
   - fix: 修复缺陷
   - docs: 文档更新
   - style: 代码格式调整
   - refactor: 重构
   - test: 测试
   - chore: 构建过程或辅助工具的变动

### 安全规范

1. **敏感信息保护**:
   - 密码必须使用 BCrypt 加密
   - Token 需绑定 IP 防止盗用
   - 敏感配置信息使用环境变量

2. **输入验证**:
   - 所有用户输入必须进行验证
   - 使用 JSR-303 注解进行参数校验
   - 防止 SQL 注入和 XSS 攻击

3. **访问控制**:
   - 实施最小权限原则
   - 接口访问频率限制
   - 定期清理过期 Token

---

## 项目维护

### 性能优化

1. **数据库优化**:
   - 合理使用索引提升查询性能
   - 分页查询大数据集
   - 使用缓存减少数据库压力

2. **缓存策略**:
   - 读多写少的数据使用 Redis 缓存
   - 合理设置缓存过期时间
   - 实施缓存穿透、击穿防护

3. **并发处理**:
   - 合理使用线程池
   - 避免长事务
   - 使用异步处理提升响应速度

### 扩展性考虑

1. **服务拆分**:
   - 保持服务边界清晰
   - 减少服务间耦合
   - 使用消息队列处理异步任务

2. **配置外置**:
   - 使用 Nacos 统一管理配置
   - 环境差异化配置
   - 动态配置更新

---

## 致谢

感谢以下开源项目的支持：

- Spring Framework & Spring Boot
- Spring Cloud & Spring Cloud Alibaba
- MyBatis & PageHelper
- Redis & MySQL
- JWT & BCrypt
- Vue.js & Element Plus
- UniApp

---

**项目持续更新中，欢迎 Star ⭐ 和贡献代码！**
