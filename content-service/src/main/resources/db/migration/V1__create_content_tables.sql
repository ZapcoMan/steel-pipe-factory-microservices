-- 内容服务数据库迁移脚本 (steel_content_db)

CREATE TABLE IF NOT EXISTS `about_us` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `about_us` text COMMENT '关于我们描述内容',
  `features` text COMMENT '功能特性列表 (JSON 数组字符串)',
  `contact_phone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `contact_address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `about_us` VALUES (1, '这是一个专为钢铁工厂设计的小程序，提供生产管理、数据分析和计算工具等功能，帮助工厂提高生产效率和管理水平。', '["生产数据统计与分析","设备维护提醒","订单跟踪","库存管理","质量检测记录"]', '0635-110-110', 'support@steelfactory.com', '北京市朝阳区钢铁工业区 123 号', '2026-03-18 12:50:40', '2026-03-18 12:54:31');

CREATE TABLE IF NOT EXISTS `featured_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '企业 ID',
  `name` varchar(255) DEFAULT NULL COMMENT '企业名称',
  `type` varchar(255) DEFAULT NULL COMMENT '企业类型',
  `scope` varchar(255) DEFAULT NULL COMMENT '经营范围',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `featured_company` VALUES (1, '山东钢铁集团', '国企', '钢铁冶炼、钢材生产销售、技术研发', '0531-88888888', '2026-03-11 19:55:48', '2026-03-11 19:55:48');
INSERT INTO `featured_company` VALUES (2, '临沂华强钢材', '民企', '各类钢材贸易、仓储物流、加工配送', '0539-66666666', '2026-03-11 19:55:48', '2026-03-11 19:55:48');
INSERT INTO `featured_company` VALUES (3, '青岛海西重机', '制造业', '重型机械制造、钢结构加工、设备租赁', '0532-77777777', '2026-03-11 19:55:48', '2026-03-11 19:55:48');

CREATE TABLE IF NOT EXISTS `obtain_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资讯 ID',
  `time` varchar(255) DEFAULT NULL COMMENT '时间',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `obtain_information` VALUES (1, '2026-03-11 17:02:02', '【市场动态】螺纹钢价格今日上涨 20 元/吨，市场交易活跃', '<p><strong>市场分析：</strong></p><p>今日国内螺纹钢市场价格呈现上涨态势，主流钢厂出厂价格上调 20 元/吨。</p>', '2026-03-11 19:55:48', '2026-03-11 19:55:48');
INSERT INTO `obtain_information` VALUES (2, '09:15', '【技术分享】不锈钢焊接工艺要点及常见问题解析', '<p><strong>一、不锈钢焊接特点</strong></p><p>不锈钢具有良好的耐腐蚀性和耐热性。</p>', '2026-03-11 19:55:48', '2026-03-11 19:55:48');
INSERT INTO `obtain_information` VALUES (3, '2026-03-16 00:00:00', '【政策解读】钢铁行业节能减排新标准正式实施', '<p><strong>政策背景：</strong></p><p>为贯彻落实国家碳达峰、碳中和战略部署。</p>', '2026-03-11 19:55:48', '2026-03-11 19:55:48');

-- 全文索引（支持 MATCH AGAINST 全文搜索）
ALTER TABLE `obtain_information` ADD FULLTEXT INDEX `ft_title_content` (`title`, `content`) WITH PARSER ngram;
ALTER TABLE `featured_company` ADD FULLTEXT INDEX `ft_name_scope` (`name`, `scope`) WITH PARSER ngram;
