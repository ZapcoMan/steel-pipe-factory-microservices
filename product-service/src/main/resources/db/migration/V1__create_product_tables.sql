-- 产品服务数据库迁移脚本 (steel_product_db)

CREATE TABLE IF NOT EXISTS `steel_product_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品 ID',
  `name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `spec` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `change` decimal(10,2) DEFAULT NULL COMMENT '涨跌幅',
  `date` varchar(50) DEFAULT NULL COMMENT '日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `steel_product_price` VALUES (1, '螺纹钢', 'HRB400E Φ12-14mm', 12200.00, 1.20, '2025-12-25', '2026-03-18 00:01:07', '2026-03-18 00:01:07');
INSERT INTO `steel_product_price` VALUES (2, '线材', 'HPB300 Φ6.5-12mm', 4250.00, 3.80, '2025-12-25', '2026-03-18 00:01:07', '2026-03-18 00:01:07');
INSERT INTO `steel_product_price` VALUES (3, '热轧板卷', 'Q235B 3.0mm', 4150.00, -0.50, '2025-12-25', '2026-03-18 00:01:07', '2026-03-18 00:01:07');
INSERT INTO `steel_product_price` VALUES (4, '冷轧板卷', 'DC01 1.0mm', 4500.00, 0.30, '2025-12-25', '2026-03-18 00:01:07', '2026-03-18 00:01:07');
INSERT INTO `steel_product_price` VALUES (5, '中厚板', 'Q235B 20mm', 0.00, 1.50, '2025-12-25', '2026-03-18 00:01:07', '2026-03-18 00:01:07');

CREATE TABLE IF NOT EXISTS `sample_display` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '样品 ID',
  `name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `spec` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `material` varchar(255) DEFAULT NULL COMMENT '材质',
  `features` text COMMENT '特性 (JSON 数组)',
  `application` varchar(500) DEFAULT NULL COMMENT '应用领域',
  `image` varchar(500) DEFAULT NULL COMMENT '图片 URL',
  `category` varchar(50) DEFAULT NULL COMMENT '分类',
  `is_new` tinyint(1) DEFAULT 0 COMMENT '是否新品',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `sample_display` VALUES (1, '无缝钢管', 'Φ108*4.0mm', '20#钢', '["高强度", "耐压", "耐腐蚀"]', '石油化工、流体输送', '/static/logo.png', 'steel-tube', 1, '2026-03-18 00:01:07', '2026-03-18 00:01:07');
INSERT INTO `sample_display` VALUES (2, '直缝焊管', 'Φ159*5.0mm', 'Q235B', '["成本低", "易加工", "规格全"]', '建筑结构、流体输送', '/static/logo.png', 'steel-tube', 0, '2026-03-18 00:01:07', '2026-03-18 00:01:07');
INSERT INTO `sample_display` VALUES (3, '方管', '100*100*4.0mm', 'Q345B', '["截面利用率高", "便于连接", "承载能力强"]', '建筑结构、机械制造', '/static/logo.png', 'rectangular', 1, '2026-03-18 00:01:07', '2026-03-18 00:01:07');
INSERT INTO `sample_display` VALUES (4, '矩形管', '150*100*5.0mm', 'Q235B', '["截面惯性矩大", "抗弯性能好", "美观实用"]', '建筑装饰、机械结构', '/static/logo.png', 'rectangular', 0, '2026-03-18 00:01:07', '2026-03-18 00:01:07');
INSERT INTO `sample_display` VALUES (5, '工字钢', '250*118*10mm', 'Q345B', '["耐腐蚀","规格全","截面利用率高"]', '工业建筑、大跨度结构', '/static/logo.png', 'i-beam', 0, '2026-03-18 00:01:07', '2026-03-18 01:44:09');
INSERT INTO `sample_display` VALUES (6, '角钢', '75*75*8mm', 'Q235B', NULL, '建筑结构、设备框架', '/static/logo.png', 'angle', 0, '2026-03-18 00:01:07', '2026-03-18 01:35:03');
INSERT INTO `sample_display` VALUES (7, '槽钢', '[20a 200*73*7mm]', 'Q235B', '["截面形状合理", "承载能力强", "便于固定"]', '建筑结构、车辆制造', '/static/logo.png', 'channel', 0, '2026-03-18 00:01:07', '2026-03-18 00:01:07');
INSERT INTO `sample_display` VALUES (8, 'H 型钢', 'HW300*300*10*15mm', 'Q345B', '["截面模数大", "抗弯能力强", "省材料"]', '高层建筑、工业厂房', '/static/logo.png', 'h-beam', 1, '2026-03-18 00:01:07', '2026-03-18 00:01:07');

-- 全文索引（支持 MATCH AGAINST 全文搜索）
ALTER TABLE `sample_display` ADD FULLTEXT INDEX `ft_name_features` (`name`, `features`) WITH PARSER ngram;
