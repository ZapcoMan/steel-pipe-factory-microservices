-- 用户服务数据库迁移脚本 (steel_user_db)

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id(主键)',
  `username` varchar(50) NOT NULL COMMENT '账号(唯一)',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `role` varchar(20) NOT NULL DEFAULT 'ADMIN' COMMENT '身份',
  `name` varchar(20) NOT NULL COMMENT '真实姓名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_index` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `admin` VALUES (1, 'admin', '$2a$10$n7nxVZVJaD79vAXKJDr61OlxQne2c0e3QGBVAaMQ0sWwX2zEX7g4C', 'ADMIN', '管理员1', '');

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id(主键)',
  `username` varchar(50) NOT NULL COMMENT '账号(唯一)',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `role` varchar(20) NOT NULL COMMENT '身份',
  `name` varchar(20) NOT NULL COMMENT '真实姓名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_index` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
