

-- ----------------------------
-- Table structure for advert_config
-- ----------------------------
DROP TABLE IF EXISTS `advert_config`;
CREATE TABLE `advert_config`  (
  `configId` bigint(20) NOT NULL AUTO_INCREMENT,
  `configName` varchar(70)  NOT NULL,
  `configDesc` varchar(500)  NULL DEFAULT NULL,
  `advertCode` mediumtext  NULL,
  `posModelId` bigint(20) NULL DEFAULT NULL,
  `contentModelId` bigint(20) NULL DEFAULT NULL,
  `userState` int(11) NOT NULL,
  `creator` varchar(40)  NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`configId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for advert_config_param
-- ----------------------------
DROP TABLE IF EXISTS `advert_config_param`;
CREATE TABLE `advert_config_param`  (
  `paramId` bigint(20) NOT NULL AUTO_INCREMENT,
  `configId` bigint(20) NOT NULL DEFAULT 0,
  `paramFlag` varchar(50)  NOT NULL,
  `paramType` int(11) NOT NULL,
  `paramName` varchar(60)  NOT NULL,
  `htmlType` int(11) NOT NULL,
  `choiceValue` varchar(900)  NULL DEFAULT NULL,
  `defaultValue` varchar(200)  NULL DEFAULT NULL,
  `mustFill` int(11) NOT NULL,
  `allowFileType` varchar(300)  NULL DEFAULT NULL,
  `fileSize` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`paramId`) USING BTREE,
  INDEX `configId_paramId`(`configId`, `paramId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for advert_content
-- ----------------------------
DROP TABLE IF EXISTS `advert_content`;
CREATE TABLE `advert_content`  (
  `advertId` bigint(20) NOT NULL AUTO_INCREMENT,
  `adName` varchar(80)  NOT NULL,
  `adFlag` varchar(60)  NOT NULL,
  `posId` bigint(20) NOT NULL,
  `advertCode` mediumtext  NULL,
  `showStartDate` datetime NULL DEFAULT NULL,
  `showEndDate` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `percentVal` int(11) NOT NULL,
  `importance` int(11) NOT NULL,
  `target` int(11) NOT NULL,
  `keyword` varchar(300)  NULL DEFAULT NULL,
  `creator` varchar(120)  NULL DEFAULT 'CURRENT_TIMESTAMP',
  `useState` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`advertId`) USING BTREE,
  INDEX `flag`(`adFlag`) USING BTREE,
  INDEX `ad_con`(`posId`, `showStartDate`, `useState`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of advert_content
-- ----------------------------

-- ----------------------------
-- Table structure for advert_param_value
-- ----------------------------
DROP TABLE IF EXISTS `advert_param_value`;
CREATE TABLE `advert_param_value`  (
  `paramId` bigint(20) NOT NULL,
  `configType` int(11) NOT NULL,
  `configId` bigint(20) NOT NULL,
  `targetId` bigint(20) NOT NULL,
  `paramValue` varchar(800)  NULL DEFAULT NULL,
  INDEX `pctId`(`paramId`, `configType`, `targetId`, `configId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for advert_position
-- ----------------------------
DROP TABLE IF EXISTS `advert_position`;
CREATE TABLE `advert_position`  (
  `posId` bigint(20) NOT NULL AUTO_INCREMENT,
  `posName` varchar(80)  NOT NULL,
  `posFlag` varchar(80)  NOT NULL,
  `configName` varchar(80)  NULL DEFAULT NULL,
  `configId` bigint(20) NOT NULL,
  `width` int(11) NULL DEFAULT NULL,
  `height` int(11) NULL DEFAULT NULL,
  `posDesc` varchar(700)  NULL DEFAULT NULL,
  `useState` int(11) NOT NULL,
  `creator` varchar(70)  NULL DEFAULT NULL,
  `target` varchar(15)  NOT NULL,
  `showMode` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`posId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of advert_position
-- ----------------------------

-- ----------------------------
-- Table structure for block_content
-- ----------------------------
DROP TABLE IF EXISTS `block_content`;
CREATE TABLE `block_content`  (
  `blockId` bigint(20) NOT NULL,
  `row` int(11) NOT NULL,
  `rowOrder` int(11) NOT NULL,
  `title` varchar(400)  NOT NULL,
  `url` varchar(400)  NOT NULL,
  `imgUrl` varchar(400)  NULL DEFAULT NULL,
  `summary` varchar(600)  NULL DEFAULT NULL,
  `addTime` datetime NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for block_info
-- ----------------------------
DROP TABLE IF EXISTS `block_info`;
CREATE TABLE `block_info`  (
  `blockId` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) NULL DEFAULT NULL,
  `blockName` varchar(80)  NOT NULL,
  `flag` varchar(40)  NOT NULL,
  `type` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `templateUrl` varchar(200)  NULL DEFAULT NULL,
  `creator` varchar(255)  NULL DEFAULT NULL,
  `blockDesc` varchar(600)  NULL DEFAULT NULL,
  `staticUrl` varchar(700)  NULL DEFAULT NULL,
  `jobId` bigint(20) NULL DEFAULT NULL,
  `periodType` int(11) NULL DEFAULT NULL,
  `period` int(11) NULL DEFAULT NULL,
  `lastPubTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`blockId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of block_info
-- ----------------------------
INSERT INTO `block_info` VALUES (33, 4, '首页幻灯', 'mh_sy_hd', 1, 5, '/block/index_left_top.jsp', 'admin', '首页幻灯,来自推荐位的数据,定时更新', 'mh_sy_hd.html', NULL, 2, 0, NULL);

-- ----------------------------
-- Table structure for block_pub_dt_trace
-- ----------------------------
DROP TABLE IF EXISTS `block_pub_dt_trace`;
CREATE TABLE `block_pub_dt_trace`  (
  `selfBlockId` bigint(20) NOT NULL,
  `lastPubDT` datetime NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of block_pub_dt_trace
-- ----------------------------
INSERT INTO `block_pub_dt_trace` VALUES (33, '2017-01-26 10:52:49');

-- ----------------------------
-- Table structure for block_type
-- ----------------------------
DROP TABLE IF EXISTS `block_type`;
CREATE TABLE `block_type`  (
  `blockTypeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `blockTypeName` varchar(60)  NOT NULL,
  `siteFlag` varchar(40)  NOT NULL,
  `creator` varchar(60)  NOT NULL,
  `templateUrl` varchar(500)  NULL DEFAULT NULL,
  PRIMARY KEY (`blockTypeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of block_type
-- ----------------------------
INSERT INTO `block_type` VALUES (4, '首页', 'demo', 'a', '/index.jsp');

-- ----------------------------
-- Table structure for comment_info
-- ----------------------------
DROP TABLE IF EXISTS `comment_info`;
CREATE TABLE `comment_info`  (
  `commentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `contentId` bigint(20) NOT NULL,
  `classId` bigint(20) NOT NULL,
  `memberId` bigint(20) NOT NULL,
  `modelId` bigint(20) NOT NULL,
  `typeId` bigint(20) NOT NULL,
  `replyId` bigint(20) NOT NULL,
  `replayText` mediumtext  NULL,
  `replyTrace` varchar(4500)  NULL DEFAULT NULL,
  `userName` varchar(80)  NULL DEFAULT NULL,
  `commentText` varchar(3000)  NULL DEFAULT NULL,
  `score` int(11) NULL DEFAULT NULL,
  `supportCount` bigint(20) NULL DEFAULT NULL,
  `againstCount` bigint(20) NULL DEFAULT NULL,
  `moodFlag` int(11) NULL DEFAULT NULL,
  `ip` varchar(25)  NULL DEFAULT NULL,
  `censorState` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `commDT` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`commentId`) USING BTREE,
  INDEX `cid`(`contentId`, `censorState`) USING BTREE,
  INDEX `classId`(`classId`, `censorState`) USING BTREE,
  INDEX `memId`(`memberId`, `siteId`) USING BTREE,
  INDEX `ct`(`contentId`, `typeId`) USING BTREE,
  INDEX `cts`(`contentId`, `typeId`, `censorState`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for comment_replay_text
-- ----------------------------
DROP TABLE IF EXISTS `comment_replay_text`;
CREATE TABLE `comment_replay_text`  (
  `commentId` bigint(20) NOT NULL DEFAULT 0,
  `replayText` longtext  NULL,
  PRIMARY KEY (`commentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_aiticle_dl_img
-- ----------------------------
DROP TABLE IF EXISTS `content_aiticle_dl_img`;
CREATE TABLE `content_aiticle_dl_img`  (
  `contentId` bigint(20) NOT NULL DEFAULT 0,
  `resId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_art_source
-- ----------------------------
DROP TABLE IF EXISTS `content_art_source`;
CREATE TABLE `content_art_source`  (
  `sId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sourceName` varchar(160)  NOT NULL,
  `firstChar` varchar(5)  NOT NULL,
  PRIMARY KEY (`sId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of content_art_source
-- ----------------------------
INSERT INTO `content_art_source` VALUES (1, '环球网', 'h');
INSERT INTO `content_art_source` VALUES (2, '中国新闻网', 'z');
INSERT INTO `content_art_source` VALUES (3, '新华社', 'x');

-- ----------------------------
-- Table structure for content_assistant_copy_info
-- ----------------------------
DROP TABLE IF EXISTS `content_assistant_copy_info`;
CREATE TABLE `content_assistant_copy_info`  (
  `contentId` bigint(20) NOT NULL,
  `refClassId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_assistant_link_info
-- ----------------------------
DROP TABLE IF EXISTS `content_assistant_link_info`;
CREATE TABLE `content_assistant_link_info`  (
  `cId` bigint(20) NULL DEFAULT 0,
  `linkId` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of content_assistant_link_info
-- ----------------------------
INSERT INTO `content_assistant_link_info` VALUES (7, 8);
INSERT INTO `content_assistant_link_info` VALUES (7, 9);
INSERT INTO `content_assistant_link_info` VALUES (11, 12);

-- ----------------------------
-- Table structure for content_assistant_page_info
-- ----------------------------
DROP TABLE IF EXISTS `content_assistant_page_info`;
CREATE TABLE `content_assistant_page_info`  (
  `contentId` bigint(20) NOT NULL DEFAULT 0,
  `pos` int(11) NOT NULL,
  `pageTitle` varchar(70)  NULL DEFAULT NULL,
  `pageContent` mediumtext  NULL,
  `pageStaticUrl` varchar(300)  NULL DEFAULT NULL,
  `startPos` int(11) NOT NULL,
  `endPos` int(11) NOT NULL,
  INDEX `c-pos`(`contentId`, `pos`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_assistant_photo_group
-- ----------------------------
DROP TABLE IF EXISTS `content_assistant_photo_group`;
CREATE TABLE `content_assistant_photo_group`  (
  `contentId` bigint(20) NOT NULL,
  `groupSign` varchar(20)  NOT NULL,
  `modelType` bigint(20) NULL DEFAULT NULL,
  `photoName` varchar(300)  NULL DEFAULT NULL,
  `isCover` int(11) NULL DEFAULT NULL,
  `url` varchar(400)  NULL DEFAULT NULL,
  `photoDesc` varchar(300)  NULL DEFAULT NULL,
  `outLinkUrl` varchar(300)  NULL DEFAULT NULL,
  `photoAddTime` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `width` int(11) NULL DEFAULT NULL,
  `height` int(11) NULL DEFAULT NULL,
  `needMark` int(11) NULL DEFAULT NULL,
  `orderFlag` int(11) NULL DEFAULT NULL,
  INDEX `co`(`contentId`, `orderFlag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_assistant_publish_id
-- ----------------------------
DROP TABLE IF EXISTS `content_assistant_publish_id`;
CREATE TABLE `content_assistant_publish_id`  (
  `pubIdTrace` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of content_assistant_publish_id
-- ----------------------------
INSERT INTO `content_assistant_publish_id` VALUES (10);

-- ----------------------------
-- Table structure for content_commend_push_info
-- ----------------------------
DROP TABLE IF EXISTS `content_commend_push_info`;
CREATE TABLE `content_commend_push_info`  (
  `infoId` bigint(20) NOT NULL AUTO_INCREMENT,
  `rowFlag` bigint(20) NOT NULL,
  `rowOrder` int(11) NOT NULL,
  `contentId` bigint(20) NULL DEFAULT 0,
  `title` varchar(200)  NULL DEFAULT NULL,
  `url` varchar(300)  NULL DEFAULT NULL,
  `img` varchar(200)  NULL DEFAULT NULL,
  `summary` varchar(600)  NULL DEFAULT NULL,
  `addTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modelId` bigint(20) NULL DEFAULT NULL,
  `classId` bigint(20) NULL DEFAULT NULL,
  `commendTypeId` bigint(20) NULL DEFAULT NULL,
  `orderFlag` int(11) NULL DEFAULT NULL,
  `typeFlag` varchar(100)  NULL DEFAULT NULL,
  `commendFlag` varchar(100)  NOT NULL,
  `commendMan` varchar(30)  NOT NULL,
  `siteFlag` varchar(60)  NOT NULL,
  PRIMARY KEY (`infoId`) USING BTREE,
  INDEX `rfro`(`commendFlag`, `rowFlag`, `rowOrder`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 813 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of content_commend_push_info
-- ----------------------------
INSERT INTO `content_commend_push_info` VALUES (521, 3, 1, 301672, '澳总理邀G20领袖烧烤', 'JTOPCMS-CONTENT-URL:301672', 'id=7866;t=i;sid=5;reUrl=2014-11-16/1416148046515402881e449b8ef5d1370149b90052b3001f.jpg;iw=130;ih=85;', '澳总理邀G20领导人烧烤自助 习大大穿衬衫取餐', '2014-11-16 22:28:46', 82, 10658, 53, NULL, NULL, 'gn_left_small', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (522, 4, 1, 301656, '湖南开刘少奇主题党课', 'JTOPCMS-CONTENT-URL:301656', 'id=7868;t=i;sid=5;reUrl=2014-11-16/1416148100359402881e449b8ef5d990149b90125070023.jpg;iw=130;ih=85;', '湖南省图书馆开刘少奇主题党课 近百人听讲(图)', '2014-11-16 22:28:46', 82, 10658, 53, NULL, NULL, 'gn_left_small', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (525, 2, 1, 301652, '中国正在告别山寨之路', 'JTOPCMS-CONTENT-URL:301652', 'id=7864;t=i;sid=5;reUrl=2014-11-16/1416148000187402881e449b8ef5d5160149b8ff9dbb001b.jpg;iw=130;ih=85;', '彭波: 中国正走在告别山寨的快车道上', '2014-11-16 22:29:27', -1, 10658, 53, NULL, NULL, 'gn_left_small', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (527, 1, 1, 301683, '河北藏1.2亿官员已被免', 'JTOPCMS-CONTENT-URL:301683', 'id=7862;t=i;sid=5;reUrl=2014-11-16/1416147893078402881e449b8ef5d2190149b8fdfb560016.jpg;iw=130;ih=85;', '河北藏1.2亿现金官员已被免 案件仍在侦查', '2014-11-16 22:28:46', -1, 10658, 53, NULL, NULL, 'gn_left_small', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (537, 1, 1, 301664, '习近平就二十国集团提出三点建议', 'JTOPCMS-CONTENT-URL:301664', 'id=8683;t=i;sid=5;reUrl=2014-11-17/1416235601640402881e449be2a4b9550149be384ee8001b.jpg;iw=275;ih=200;', '习近平就二十国集团全面增长提出三点建议(图)', '2015-10-19 23:12:20', -1, 10658, 52, NULL, NULL, 'mh_gn_let', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (539, 6, 1, 301107, '总结王儒林山西思路:高调反腐直面问题', 'JTOPCMS-CONTENT-URL:301107', '', '媒体总结王儒林重塑山西思路:高调反腐直面问题', '2016-11-30 17:32:57', 82, 10658, 54, NULL, NULL, 'gn_mid_list', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (540, 7, 1, 301105, '南水北调一期工程今年汛后将全线通水', 'JTOPCMS-CONTENT-URL:301105', '', '南水北调中线一期工程今年汛后将全线通水', '2016-11-30 17:32:57', 82, 10658, 54, NULL, NULL, 'gn_mid_list', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (544, 5, 1, 301111, '广东4年追外逃贪官10人 挽回损失2亿', 'JTOPCMS-CONTENT-URL:301111', '', '广东4年追回外逃贪官10人 挽回损失2.3亿元', '2016-11-30 17:32:57', 82, 10658, 54, NULL, NULL, 'gn_mid_list', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (545, 4, 1, 301667, 'APEC领导人午宴瓷器:做210套仅用21套', 'JTOPCMS-CONTENT-URL:301667', '', 'APEC领导人午宴瓷器揭秘:做210套仅使用21套', '2016-11-30 17:32:57', 82, 10658, 54, NULL, NULL, 'gn_mid_list', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (546, 2, 1, 301678, '李克强:企业和个人明年起年减负400亿', 'JTOPCMS-CONTENT-URL:301678', '', '李克强:企业和个人明年起年减负400亿', '2016-11-30 17:32:57', 82, 10658, 54, NULL, NULL, 'gn_mid_list', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (548, 3, 1, 301663, '外交部:中美应妥善管控分歧和敏感问题', 'JTOPCMS-CONTENT-URL:301663', '', '外交部:中美应妥善管控分歧和敏感问题', '2016-11-30 17:32:57', 82, 10658, 54, NULL, NULL, 'gn_mid_list', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (549, 8, 1, 301123, '中国驻菲使馆再提醒在菲华人注意安全', 'JTOPCMS-CONTENT-URL:301123', '', '中国驻菲大使馆再次提醒在菲华人华侨注意安全', '2016-11-30 17:32:57', 82, 10658, 54, NULL, NULL, 'gn_mid_list', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (551, 1, 1, 302394, '盘点世界最穷国家排名 揭秘世界最穷总统穆希卡', 'JTOPCMS-CONTENT-URL:302394', 'id=8680;t=i;sid=5;reUrl=2014-11-17/1416234919468402881e449be2a4b9270149be2de62c000b.jpg;iw=400;ih=278;', '世界最穷总统穆希卡，穷住板房，保镖只有两个警察还有一条三条腿的瘸狗，上月阿拉伯一名酋长出价100万美元欲购买乌拉圭总统何塞?穆希卡的甲壳虫座驾，据估值，他这个座驾大概价值千余美元，那么千余美元夹克虫为啥不肯百万卖呢，有什么内幕呢？', '2014-11-17 22:41:12', 82, 10664, 58, NULL, NULL, 'mh_gj_zd', 'a', 'demo');
INSERT INTO `content_commend_push_info` VALUES (552, 2, 1, 300996, 'going home', 'JTOPCMS-CONTENT-URL:300996', 'id=970;t=i;sid=5;reUrl=2015-05-17/1431836391359402881e44d600bdc456014d601977bf001a.jpg;iw=140;ih=85;', '', '2015-05-17 12:19:53', -1, 10657, 60, NULL, NULL, 'mh_sp_top', 'mhadmin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (554, 1, 1, 300996, 'going home', 'JTOPCMS-CONTENT-URL:300996', 'id=8694;t=i;sid=5;reUrl=2014-11-18/1416289414987402881e449c0a74d6800149c16d6f4b00e1.jpg;iw=148;ih=85;', '', '2014-11-18 13:43:45', 62, 10665, 61, NULL, NULL, 'mh_sp_lt', 'mhadmin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (555, 1, 1, 300996, 'going home', 'JTOPCMS-CONTENT-URL:300996', '', '', '2014-11-18 13:33:46', 62, 10665, 62, NULL, NULL, 'mh_sp_mb', 'mhadmin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (729, 19, 1, 302762, '鲁炜：中美青年对两国网络关系应有基本认识', 'JTOPCMS-CONTENT-URL:302762', '', '视频加载中，请稍候... 自动播放 ? play 中美互联网论坛开幕 向前 向后 　　12月2日，中国国家互联网信息办公室主任鲁炜在美国乔治·华盛顿大学发表演讲时指出，青年一代是中美关系的希望所在。 　　12月2日，中国', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (730, 20, 1, 302800, '顾客入住超10分钟不得退房 汉庭称员工业务不熟', 'JTOPCMS-CONTENT-URL:302800', '', '　　11月21日，李先生入住汉庭连锁酒店金桥碧云店(以下简称汉庭碧云店)后，发现电视信号不好，而且连接不上WiFi，遂要求退房，却被前台服务员告知“因入住时间超过10分钟，不得退房”。因对这一规定不满，李先生选择了离店维', '2014-12-07 14:09:59', 82, 10652, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (731, 21, 1, 302834, '中原地产宣布“二次革命”:减少和电商合作规模', 'JTOPCMS-CONTENT-URL:302834', '', '　　中原地产宣布“二次革命”减少和电商合作规模 　　每经记者 杨羚强 发自上海 　　“市场不好的时候才要逼我们改革，逼我们创新。”在中原上海公司的创始员工，前董事总经理谭百强离职两天之后，昨日(12月2日)，中原地产集团', '2014-12-07 14:10:00', 82, 10652, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (732, 22, 1, 302835, '林肯汽车重返中国市场或重蹈覆辙:慢了不只一拍', 'JTOPCMS-CONTENT-URL:302835', '', '资料图 福特汽车最新实时行情 公司新闻 公司研究 机构持股 财务信息 汽车制造行业 客户端 　　林肯汽车或重蹈覆辙 　　法治周末特约撰稿 曾高飞 　　以美国第16任总统亚伯拉罕.林肯的名字命名的林肯汽车，正在让这个伟大的', '2014-12-07 14:10:00', 82, 10652, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (733, 23, 1, 302836, '世合投资高收益吸纳公众存款 多地负责人关门跑路', 'JTOPCMS-CONTENT-URL:302836', '', '世合投资公司北京分部人去楼空。 　　世合投资“高收益”下的陷阱 　　法治周末实习生 代秀辉 　 法治周末记者 戴蕾蕾 　　10月22日，对于甘肃世合投资有限公司(以下简称甘肃世合投资)的投资者而言或许是一个有点凄凉的日子', '2014-12-07 14:10:00', 82, 10652, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (734, 24, 1, 302837, '盐改直指专营垄断：中盐开个票盐价从500涨到3000', 'JTOPCMS-CONTENT-URL:302837', '', '? ? ? ?【推荐阅读】 ???????食盐专营延续2600年即将终结 或出现高价保健盐 ???????食盐专营退场中盐曾反对：2.5元的盐成本仅5毛? ? ? ? ?【图解】 ???????起底中盐：古老垄断者的终结', '2014-12-07 14:10:00', 82, 10652, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (735, 25, 1, 302870, '沃尔玛中国数十名高管被离职 人事制度被指强势', 'JTOPCMS-CONTENT-URL:302870', '', '沃尔玛最新实时行情 公司新闻 公司研究 机构持股 财务信息 零售行业 客户端 ? ?【推荐阅读】沃尔玛中国砍掉20余中层岗位 连续3年4季度裁员 　　沃尔玛中国上演大规模裁员 　　118位中高层管理人员受影响 　　全球最', '2014-12-07 14:10:00', 82, 10652, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (736, 1, 1, 302704, '世银估计埃博拉疫区国家经济损失将超20亿美元', 'JTOPCMS-CONTENT-URL:302704', '', '　　环球网综合报道据台湾“联合新闻网”12月3日报道，世界银行2日表示，埃博拉疫情对西非疫区国家利比里亚、塞拉利昂共和国及几内亚共和国造成的经济损失，预估今明两年将超20亿美元(约合人民币128.7亿元)。 　　世界银行', '2014-12-03 22:38:51', 82, 10664, 72, NULL, NULL, 'zt_zzjj_fx', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (737, 2, 1, 302730, '海外日本人为众院选举投票 经济和外交受关注', 'JTOPCMS-CONTENT-URL:302730', '', '????????????????????????????????? 海外日本人为众院选举投票（共同社）。 　　中新网12月3日电? 据日本共同社3日报道，现居海外的日本人当天也开始进行众院选举投票，他们对首相安倍晋三的经', '2014-12-03 22:38:51', 82, 10664, 72, NULL, NULL, 'zt_zzjj_fx', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (738, 3, 1, 302731, '美媒：大宗商品价格下跌提高澳明年降息几率', 'JTOPCMS-CONTENT-URL:302731', '', '　　中新网12月3日电? 据美国《华尔街日报》2日报道，大宗商品价格下跌提高了澳大利亚明年降息的几率。 　　就在两周前，金融市场走势所反映的预期还是澳大利亚至少明年不太可能加息或降息。为了刺激疲软的经济，澳大利亚目前将利', '2014-12-03 22:38:51', 82, 10664, 72, NULL, NULL, 'zt_zzjj_fx', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (739, 4, 1, 302741, '内蒙古包头原副市长张继平被双开(图/简历)', 'JTOPCMS-CONTENT-URL:302741', '', '资料图：张继平。中国经济网 　　新华网呼和浩特12月3日电 据内蒙古纪检监察网今日发布的消息，包头市政府原副市长张继平严重违纪违法，被开除党籍、开除公职，将其涉嫌犯罪问题移送司法机关依法处理。 　　张继平简历 　　张继平', '2014-12-03 22:38:51', 82, 10658, 72, NULL, NULL, 'zt_zzjj_fx', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (740, 5, 1, 302742, '迟耀云任山西纪委常务副书记 前任7月被查', 'JTOPCMS-CONTENT-URL:302742', '', '视频加载中，请稍候... 自动播放 ? play 杨森林接受组织调查 向前 向后 资料图：迟耀云。中国经济网 　　中国经济网太原12月3日综合报道 山西省纪委监察厅官方网站发布消息，日前，经中共山西省委研究：迟耀云同志任', '2014-12-03 22:38:51', 82, 10658, 72, NULL, NULL, 'zt_zzjj_fx', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (741, 6, 1, 302764, '武汉开建长江上最宽桥梁 2017年底将建成通车', 'JTOPCMS-CONTENT-URL:302764', '', '　　中新社武汉12月3日电(徐金波随业辉)中国长江上最宽的桥梁——武汉沌口长江公路大桥3日正式开建。 　　武汉沌口长江公路大桥位于白沙洲长江大桥和军山长江大桥之间，起点接武汉市四环线西段，在武汉市经济技术开发区汉洪高速设', '2014-12-03 22:38:51', 82, 10658, 72, NULL, NULL, 'zt_zzjj_fx', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (742, 7, 1, 302776, '中国内地公民年出境游人次首破1亿', 'JTOPCMS-CONTENT-URL:302776', '', '　　新华网北京12月3日电（记者钱春弦）中国国家旅游局3日宣布，截至2014年11月，中国内地公民当年出境游首破1亿人次，而亚洲特别是周边国家和地区占据了这一庞大出境旅游市场的近九成份额。 　　国家旅游局新闻发言人张吉林', '2014-12-03 22:38:51', 82, 10658, 72, NULL, NULL, 'zt_zzjj_fx', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (743, 8, 1, 302793, '广州一汽巴士违规支付民营股东7432万', 'JTOPCMS-CONTENT-URL:302793', '', '　　一汽巴士违规支付民营股东7432万 　　审计调查过程仍有782万元没有追回 　　南方日报讯 (记者/刘怀宇)本来是应付的1.14亿元工资及福利费，被广州市一汽公司违规转增为企业利润，并向民营股东支付不合理经济补偿款7', '2014-12-03 22:38:51', 82, 10652, 72, NULL, NULL, 'zt_zzjj_fx', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (745, 6, 1, 302883, '红杉资本亿元注资韩后', 'JTOPCMS-CONTENT-URL:302883', '', '　　红杉资本亿元注资韩后 风投捕捉传统行业互联网创新商机 　　本报记者 叶碧华 广州报道 　　11月28日，21世纪经济报道从知名风险投资机构红杉资本获悉，因“张太体”大胆广告迅速走红的本土化妆品品牌韩后获得了红杉资本首', '2014-12-03 22:51:12', 82, 10713, 74, NULL, NULL, 'zt_zgjj_tt', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (746, 7, 1, 302882, '融创欲找拍档全面收购绿城 九龙仓成最大可能', 'JTOPCMS-CONTENT-URL:302882', '', '　　昨日早间，融创发布了针对并购绿城事宜的第三份公告，表示将对并购做出调整，如果绿城中国主席宋卫平不能如期还钱，融创或将在并购中引入合伙人。对此，业内人士分析，融创最有可能联手的是绿城另一股东九龙仓，双方要么对半分摊宋卫', '2014-12-03 22:51:12', 82, 10652, 74, NULL, NULL, 'zt_zgjj_tt', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (748, 5, 1, 302787, '五粮液业绩连续两年滑坡', 'JTOPCMS-CONTENT-URL:302787', '', '热点栏目 资金流向 千股千评 个股诊断 最新评级 模拟交易 客户端 ? ?【推荐阅读】52度水晶瓶五粮液零售价逼近600元 经销商已无利可图 　　经销商接连倒戈 五粮液背水一战：业绩连续两年滑坡 或被洋河赶超痛失“老二”', '2014-12-03 22:51:34', 82, 10713, 74, NULL, NULL, 'zt_zgjj_tt', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (749, 4, 1, 302783, '反腐风暴影响美国赌城', 'JTOPCMS-CONTENT-URL:302783', '', '　　法制晚报讯（记者 黎史翔） 中国反腐行动在全球范围内起到的震慑作用远未结束。如今，这股“反腐之风”就刮到了美国赌城。 　　根据美国官方公布的数据，10月，美国拉斯维加斯大道上各家赌场的收入出现下滑，减少5.6％至5.', '2014-12-03 22:50:47', 82, 10713, 74, NULL, NULL, 'zt_zgjj_tt', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (750, 3, 1, 302772, '我国九成政府站有漏洞', 'JTOPCMS-CONTENT-URL:302772', '', '　　新华社北京12月3日电(记者张辛欣) 中国软件评测中心3日发布2014年中国政府网站绩效评估结果显示，在评估范围内的900余家政府网站中，超过93%存在各种危险等级安全漏洞，近50%的网站被监测到的安全漏洞超过30个', '2014-12-03 22:47:42', 82, 10713, 74, NULL, NULL, 'zt_zgjj_tt', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (752, 1, 1, 302826, '阿迪耐克争开女子专卖', 'JTOPCMS-CONTENT-URL:302826', '', '　　争开女子专卖店 阿迪、耐克葫芦里卖什么药 　　胡军华 　　[在一般的运动门店，大约有七八成的消费者是男性，女性产品的种类和款式往往只占总量的30%~35%] 　　最了解中国市场的，可能不是中国人，而是老外。 　　12', '2014-12-03 22:46:53', 82, 10713, 74, NULL, NULL, 'zt_zgjj_tt', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (754, 2, 1, 302867, '铁塔公司接管千亿资产', 'JTOPCMS-CONTENT-URL:302867', '', '　　据新华社电 记者1日从中国铁塔股份有限公司获悉，国资委[微博]、工业和信息化部联合组织中国移动[微博]、中国联通、中国电信[微博]和铁塔公司落实的“铁塔相关资产清查评估工作”1日启动，铁塔公司将用一年时间完成三家电信', '2014-12-03 22:47:17', 82, 10713, 74, NULL, NULL, 'zt_zgjj_tt', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (770, 10, 1, 302784, '广西柳州火车头冲入居民区 初步调查因机械故障', 'JTOPCMS-CONTENT-URL:302784', '', '事发现场 　　12月3日10时许，南宁铁路局一辆火车头在专用线调度过程中脱轨，冲倒挡土墙进入附近的小区，未造成人员伤亡。据介绍该火车头约以5公里每小时的速度行进，冲入铁轨末端土包后冲倒挡土墙，进入和平路小区约5米。初步调', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (771, 11, 1, 302783, '中国反腐风暴影响美国赌城 10月收入锐减5.6%', 'JTOPCMS-CONTENT-URL:302783', '', '　　法制晚报讯（记者 黎史翔） 中国反腐行动在全球范围内起到的震慑作用远未结束。如今，这股“反腐之风”就刮到了美国赌城。 　　根据美国官方公布的数据，10月，美国拉斯维加斯大道上各家赌场的收入出现下滑，减少5.6％至5.', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (772, 12, 1, 302782, '铁路部门：仅改签以后退票收20%退票费', 'JTOPCMS-CONTENT-URL:302782', '', '　　法制晚报讯(记者王硕) 针对有消息称春运期间的火车退票全都收费，铁路部门上午辟谣，明确表示只有改签以后发生退票才收20%的退票费，如果是开车前15天以上直接退票的，一律免费。 　　按照中国铁路总公司安排，为了方便旅客', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (773, 13, 1, 302781, '海南国土资源厅执法监察局局长吴坤汉被调查', 'JTOPCMS-CONTENT-URL:302781', '', '海南国土资源厅执法监察局局长吴坤汉被调查', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (774, 14, 1, 302780, '烟台规划局原局长杜伟平涉违纪违法被开除党籍', 'JTOPCMS-CONTENT-URL:302780', '', '　　中新网烟台12月3日电 (闫轩)中共烟台市纪委烟台市监察局官方网站3日发布消息称，烟台市规划局原局长杜伟平被开除党籍。 　　据悉，近期山东省纪委对烟台市规划局原局长杜伟平涉嫌违纪违法问题进行了查处。经查，杜伟平利用职', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (775, 15, 1, 302779, '台媒称《大公报》助理总编遭台间谍策反后赴台', 'JTOPCMS-CONTENT-URL:302779', '', '王善勇 　　台湾《旺报》2日转引《博讯》杂志的报道称，香港《大公报》助理总编辑王善勇对台湾收集情报，却被台湾女间谍策反。现年50出头的王善勇，1982年从位于福建泉州的华侨大学毕业后即到香港，曾先后在香港多家平面媒体工作', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (776, 16, 1, 302778, '湖南衡阳干部回应阻扰纪委办案:有些后悔', 'JTOPCMS-CONTENT-URL:302778', '', '　　新京报快讯 (记者 程媛媛)昨日(12月2日)，新京报新媒体报道湖南省衡阳市祁东县工商局干部龙向阳于11月23日，因阻扰威胁纪委办案被行政拘留5日。今日(12月3日)上午，龙向阳回应称已写检讨书向办案组道歉，目前停职', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (777, 17, 1, 302777, '黑龙江抚远县已连降暴雪50多小时', 'JTOPCMS-CONTENT-URL:302777', '', '黑龙江抚远县遭暴风雪袭击 　　从11月30日开始，黑龙江抚远县遭暴风雪袭击。平均积雪深度达90厘米，平均风力6级，气温为零下17度，目前已连续降雪50多个小时。暴雪导致全县中小学停课，飞机客车停运，相关部门正在组织人员2', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (778, 18, 1, 302776, '中国内地公民年出境游人次首破1亿', 'JTOPCMS-CONTENT-URL:302776', '', '　　新华网北京12月3日电（记者钱春弦）中国国家旅游局3日宣布，截至2014年11月，中国内地公民当年出境游首破1亿人次，而亚洲特别是周边国家和地区占据了这一庞大出境旅游市场的近九成份额。 　　国家旅游局新闻发言人张吉林', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (779, 1, 1, 302766, '新疆兵团原副秘书长焦明启被双开', 'JTOPCMS-CONTENT-URL:302766', '', '视频加载中，请稍候... 自动播放 ? play 资料-焦明启接受采访 向前 向后 资料图：焦明启。中国广播网 　　据新疆生产建设兵团纪委信息：日前，新疆生产建设兵团纪委对兵团原副秘书长、经济技术协作办公室主任焦明启涉嫌', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (780, 2, 1, 302765, '宁夏贺兰县委书记被控受贿4000余万元', 'JTOPCMS-CONTENT-URL:302765', '', '　　新华网银川12月3日电(记者张亮) 2日至3日，宁夏回族自治区贺兰县原县委书记方仁涉嫌受贿一案在中卫市中级人民法院开庭审理。公诉机关指控方仁受贿金额达4000多万元。 　　检察机关指控，方仁在担任银川市经贸委主任，贺', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (781, 3, 1, 302764, '武汉开建长江上最宽桥梁 2017年底将建成通车', 'JTOPCMS-CONTENT-URL:302764', '', '　　中新社武汉12月3日电(徐金波随业辉)中国长江上最宽的桥梁——武汉沌口长江公路大桥3日正式开建。 　　武汉沌口长江公路大桥位于白沙洲长江大桥和军山长江大桥之间，起点接武汉市四环线西段，在武汉市经济技术开发区汉洪高速设', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (782, 4, 1, 302763, '浙江3名医生涉嫌拿回扣 卫生局称此现象很普遍 ', 'JTOPCMS-CONTENT-URL:302763', '', '　　新京报快讯 (记者杨锋 实习生张恒)近日，一则名为《医院里的秘密，你能看得懂么》的帖子，在网上热传。帖子中附有四张照片，分别为浙江省金华市三家医院的医生开药统方。统方列出了医生姓名、科室、开药数量、给医生的钱等数据。', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (783, 5, 1, 302762, '鲁炜：中美青年对两国网络关系应有基本认识', 'JTOPCMS-CONTENT-URL:302762', '', '视频加载中，请稍候... 自动播放 ? play 中美互联网论坛开幕 向前 向后 　　12月2日，中国国家互联网信息办公室主任鲁炜在美国乔治·华盛顿大学发表演讲时指出，青年一代是中美关系的希望所在。 　　12月2日，中国', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (784, 6, 1, 302761, '习近平：依法治国首先要坚持依宪治国', 'JTOPCMS-CONTENT-URL:302761', '', '视频加载中，请稍候... 自动播放 ? play 全面推进依法治国 play 依法治国给人民红利 play 十八届四中全会现场 play 专题讨论依法治国 向前 向后 资料图：习近平 　　【习近平：坚决维护宪法法律权威】', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (785, 7, 1, 302760, '辽宁营口疑犯开枪打死银行领导致2死2伤', 'JTOPCMS-CONTENT-URL:302760', '', '视频加载中，请稍候... 自动播放 ? play 银行员工枪杀行长2人 向前 向后 　　【中国人民银行大石桥办事处发生伤人事件】今天下午3点半左右，中国人民银行大石桥办事处发生伤人事件，2死2伤。据了解，当事人因和单位有', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (786, 8, 1, 302759, '广东妇幼保健院院长被查 韶关两官员受贿被双开', 'JTOPCMS-CONTENT-URL:302759', '', '　　中新网广州12月3日电 (索有为 粤纪宣)广东省纪委官方网站“南粤清风网”3日通报称，广东省妇幼保健院院长张小庄，因涉嫌严重违纪，正在接受组织立案调查。韶关市乐昌市原市委书记李维员、韶关市浈江区原区委书记吴玉环因严重', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (787, 9, 1, 302758, '外交部回应中国全球清廉印象指数排名下降报道', 'JTOPCMS-CONTENT-URL:302758', '', '视频加载中，请稍候... 自动播放 ? play 外交部斥英干涉内政 play 反腐是输不起的战争 play 专项巡视成反腐新常态 play APEC反腐达共识 向前 向后 2014年12月3日外交部发言人华春莹主持例行', '2014-12-07 14:09:59', 82, 10658, 71, NULL, NULL, 'zt_zzjj_gd', 'adminX', 'demo');
INSERT INTO `content_commend_push_info` VALUES (791, 2, 2, -1, 'asdasdadasdsad', '', '', '', '2016-11-30 17:32:57', -1, 10644, 54, NULL, NULL, 'gn_mid_list', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (795, 1, 1, 802, 'bj水电费', 'JTOPCMS-CONTENT-URL:802', '', '阿萨德', '2016-11-30 17:32:57', 82, 10644, 54, NULL, NULL, 'gn_mid_list', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (801, 1, 1, 859, 'ddddd', 'JTOPCMS-CONTENT-URL:859', '', 'dddd', '2017-01-27 12:33:16', 82, 10644, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (802, 2, 1, 858, 'dsfsdfdsf', 'JTOPCMS-CONTENT-URL:858', '', 'sfffdfsdf', '2017-01-27 12:33:16', 82, 10644, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (803, 3, 1, 857, 'ccvcc c vsdsd', 'JTOPCMS-CONTENT-URL:857', '', 'c', '2017-01-27 12:33:16', 82, 10644, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (804, 4, 1, 856, 'xcvxcvcvv', 'JTOPCMS-CONTENT-URL:856', '', 'vcvvvxvcxvxvc255dsd的的9.jpgtimg.jpg', '2017-01-27 12:33:16', 82, 10644, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (805, 5, 1, 853, 'mb,bbmn', 'JTOPCMS-CONTENT-URL:853', '', 'jhkhkjhkkjkj255dsd的的9.jpgtimg.jpg', '2017-01-27 12:33:16', 82, 10644, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (806, 6, 1, 852, 'cvxcvcxvc', 'JTOPCMS-CONTENT-URL:852', '', 'xcvxcvcvcvvxvcv', '2017-01-27 12:33:16', 82, 10644, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (807, 7, 1, 851, 'dgdfgfg', 'JTOPCMS-CONTENT-URL:851', '', 'fgdgdggdgd', '2017-01-27 12:33:16', 82, 10644, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (808, 8, 1, 850, 'dfgdfgfgg', 'JTOPCMS-CONTENT-URL:850', '', 'gdgdgfgdfgdfg', '2017-01-27 12:33:16', 82, 10644, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (809, 9, 1, 843, 'asdasdasdas d', 'JTOPCMS-CONTENT-URL:843', '', 'asdassdadsadasd', '2017-01-27 12:33:16', 82, 10644, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (810, 1, 2, -1, 'asdasdsaddasd', 'asdasd', '', '', '2017-01-27 12:33:31', -1, -9999, 64, NULL, NULL, 'mh_syyw', 'admin', 'demo');
INSERT INTO `content_commend_push_info` VALUES (812, 1, 1, 10, '水电费是的', 'JTOPCMS-CONTENT-URL:10', '', '发多少发生的发多少的的', '2018-06-15 20:51:55', -1, 10658, 49, NULL, NULL, 'syzstj', 'admin', 'demo');

-- ----------------------------
-- Table structure for content_commend_push_temp
-- ----------------------------
DROP TABLE IF EXISTS `content_commend_push_temp`;
CREATE TABLE `content_commend_push_temp`  (
  `contentId` bigint(20) NOT NULL DEFAULT 0,
  `commTypeId` bigint(20) NOT NULL,
  `commFlag` varchar(80)  NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_commend_type
-- ----------------------------
DROP TABLE IF EXISTS `content_commend_type`;
CREATE TABLE `content_commend_type`  (
  `commendTypeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `siteFlag` varchar(60)  NOT NULL,
  `isSpec` int(11) NOT NULL,
  `classLinerFlag` varchar(400)  NULL DEFAULT NULL,
  `classId` bigint(20) NOT NULL,
  `commendName` varchar(60)  NOT NULL,
  `commFlag` varchar(30)  NOT NULL,
  `modelId` bigint(20) NULL DEFAULT NULL,
  `typeDesc` varchar(400)  NULL DEFAULT NULL,
  `listTemplateUrl` varchar(120)  NULL DEFAULT NULL,
  `mobListTemplateUrl` varchar(120)  NULL DEFAULT NULL,
  `padListTemplateUrl` varchar(120)  NULL DEFAULT NULL,
  `listProduceType` int(11) NOT NULL,
  `listPublishRuleId` bigint(20) NULL DEFAULT NULL,
  `listStaticUrl` varchar(150)  NULL DEFAULT NULL,
  `childClassMode` int(11) NOT NULL,
  `mustCensor` int(11) NOT NULL,
  `creator` varchar(80)  NULL DEFAULT NULL,
  `imageWidth` int(11) NULL DEFAULT NULL,
  `imageHeight` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`commendTypeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of content_commend_type
-- ----------------------------
INSERT INTO `content_commend_type` VALUES (49, 'demo', 0, '', -9999, '首页左上推荐', 'syzstj', NULL, '首页左上推荐,无需审核,将作为区块独立更新', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 310, 220);
INSERT INTO `content_commend_type` VALUES (52, 'demo', 0, '', 10644, '国内频道置顶大图', 'mh_gn_let', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'mhadmin', 275, 200);
INSERT INTO `content_commend_type` VALUES (53, 'demo', 0, '', 10644, '国内频道左上四图文', 'gn_left_small', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'mhadmin', 130, 85);
INSERT INTO `content_commend_type` VALUES (54, 'demo', 0, '', 10644, '国内频道置顶列表', 'gn_mid_list', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', NULL, NULL);
INSERT INTO `content_commend_type` VALUES (58, 'demo', 0, '', 10645, '国际频道置顶图', 'mh_gj_zd', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 1, 'a', 400, 278);
INSERT INTO `content_commend_type` VALUES (59, 'demo', 0, '', 10648, '图片频道顶部图', 'mh_tp_top', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 0, 0);
INSERT INTO `content_commend_type` VALUES (60, 'demo', 0, '', 10657, '视频频道置顶', 'mh_sp_top', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 370, 320);
INSERT INTO `content_commend_type` VALUES (61, 'demo', 0, '', 10657, '视频频道右中', 'mh_sp_lt', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 148, 85);
INSERT INTO `content_commend_type` VALUES (62, 'demo', 0, '', 10657, '视频频道中下', 'mh_sp_mb', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 148, 85);
INSERT INTO `content_commend_type` VALUES (63, 'demo', 0, '', 10682, '图片精选大图', 'mh_tp_big', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'mhadmin', 478, 299);
INSERT INTO `content_commend_type` VALUES (64, 'demo', 0, '', -9999, '首页要闻一', 'mh_syyw', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 0, 0);
INSERT INTO `content_commend_type` VALUES (66, 'demo', 0, '', -9999, '首页右推荐图集', 'mh_sy_right_tp', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 135, 77);
INSERT INTO `content_commend_type` VALUES (67, 'demo', 0, '', -9999, '门户左推荐视频', 'mh_left_sp', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 0, 0);
INSERT INTO `content_commend_type` VALUES (68, 'demo', 0, '', -9999, '首页要闻二', 'mh_syyw2', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 0, 0);
INSERT INTO `content_commend_type` VALUES (69, 'demo', 0, '', -9999, '首页要闻三', 'mh_syyw3', NULL, '', NULL, NULL, NULL, 0, NULL, NULL, 1, 0, 'a', 0, 0);
INSERT INTO `content_commend_type` VALUES (71, 'demo', 1, '', 10713, '滚动报道', 'zt_zzjj_gd', NULL, NULL, 'spec_list.jsp?channelId=10687&tid={type-id}', NULL, NULL, 1, 24, NULL, 1, 0, 'a', 0, 0);
INSERT INTO `content_commend_type` VALUES (72, 'demo', 1, '', 10713, '深度分析', 'zt_zzjj_fx', NULL, NULL, 'spec_list.jsp?channelId=10687&tid={type-id}', NULL, NULL, 1, 24, NULL, 1, 0, 'a', 0, 0);
INSERT INTO `content_commend_type` VALUES (74, 'demo', 1, '', 10713, '头条关注', 'zt_zgjj_tt', NULL, NULL, '', NULL, NULL, 1, 0, NULL, 1, 0, 'adminX', 0, 0);

-- ----------------------------
-- Table structure for content_main_info
-- ----------------------------
DROP TABLE IF EXISTS `content_main_info`;
CREATE TABLE `content_main_info`  (
  `contentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `classId` bigint(20) NOT NULL,
  `refCid` bigint(20) NULL DEFAULT NULL,
  `linkCid` bigint(20) NULL DEFAULT NULL,
  `title` varchar(400)  NOT NULL,
  `simpleTitle` varchar(300)  NULL DEFAULT NULL,
  `shortTitle` varchar(300)  NULL DEFAULT NULL,
  `author` varchar(60)  NULL DEFAULT NULL,
  `creator` varchar(50)  NULL DEFAULT NULL,
  `orgCode` varchar(200)  NULL DEFAULT NULL,
  `boost` float NULL DEFAULT NULL,
  `summary` varchar(500)  NULL DEFAULT NULL,
  `titleStyle` varchar(80)  NULL DEFAULT NULL,
  `simpleTitleStyle` varchar(80)  NULL DEFAULT NULL,
  `addTime` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `clickMonthCount` bigint(20) NULL DEFAULT NULL,
  `clickWeekCount` bigint(20) NULL DEFAULT NULL,
  `clickDayCount` bigint(20) NULL DEFAULT NULL,
  `clickCount` bigint(20) NULL DEFAULT NULL,
  `commMonthCount` bigint(20) NULL DEFAULT NULL,
  `commWeekCount` bigint(20) NULL DEFAULT NULL,
  `commDayCount` bigint(20) NULL DEFAULT NULL,
  `commCount` bigint(20) NULL DEFAULT NULL,
  `supportCount` bigint(20) NULL DEFAULT NULL,
  `againstCount` bigint(20) NULL DEFAULT NULL,
  `homeImage` varchar(200)  NULL DEFAULT NULL,
  `classImage` varchar(200)  NULL DEFAULT NULL,
  `channelImage` varchar(200)  NULL DEFAULT NULL,
  `contentImage` varchar(200)  NULL DEFAULT NULL,
  `systemHandleTime` varchar(25)  NOT NULL,
  `especialTemplateUrl` varchar(110)  NULL DEFAULT NULL,
  `staticPageUrl` varchar(400)  NULL DEFAULT NULL,
  `outLink` varchar(200)  NULL DEFAULT NULL,
  `produceType` int(11) NULL DEFAULT NULL,
  `censorState` int(11) NULL DEFAULT NULL,
  `isPageContent` int(11) NULL DEFAULT NULL,
  `isSystemOrder` int(11) NOT NULL,
  `orderIdFlag` double NOT NULL,
  `keywords` varchar(200)  NULL DEFAULT NULL,
  `tagKey` varchar(120)  NULL DEFAULT NULL,
  `pubDateSysDT` bigint(20) NULL DEFAULT NULL,
  `appearStartDateTime` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `appearEndDateTime` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `homeImgFlag` int(11) NULL DEFAULT NULL,
  `classImgFlag` int(11) NULL DEFAULT NULL,
  `commendFlag` int(11) NULL DEFAULT NULL,
  `channelImgFlag` int(11) NULL DEFAULT NULL,
  `contentImgFlag` int(11) NULL DEFAULT NULL,
  `relateIds` varchar(300)  NULL DEFAULT NULL,
  `relateSurvey` varchar(200)  NULL DEFAULT NULL,
  `typeFlag` varchar(40)  NULL DEFAULT NULL,
  `topFlag` int(11) NULL DEFAULT NULL,
  `otherFlag` int(11) NULL DEFAULT NULL,
  `modelId` bigint(20) NOT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  `allowCommend` int(11) NULL DEFAULT NULL,
  `moodT1Count` bigint(20) NULL DEFAULT NULL,
  `moodT2Count` bigint(20) NULL DEFAULT NULL,
  `moodT3Count` bigint(20) NULL DEFAULT NULL,
  `moodT4Count` bigint(20) NULL DEFAULT NULL,
  `moodT5Count` bigint(20) NULL DEFAULT NULL,
  `moodT6Count` bigint(20) NULL DEFAULT NULL,
  `moodT7Count` bigint(20) NULL DEFAULT NULL,
  `moodT8Count` bigint(20) NULL DEFAULT NULL,
  `moodT9Count` bigint(20) NULL DEFAULT NULL,
  `moodT10Count` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`contentId`) USING BTREE,
  INDEX `ois`(`orderIdFlag`, `isSystemOrder`) USING BTREE,
  INDEX `hcmo`(`classImgFlag`, `classId`, `modelId`, `censorState`, `orderIdFlag`) USING BTREE,
  INDEX `cmc-oid`(`classId`, `modelId`, `censorState`, `orderIdFlag`) USING BTREE,
  INDEX `cmto-limit`(`classId`, `modelId`, `topFlag`, `orderIdFlag`) USING BTREE,
  INDEX `title-cid`(`title`(383), `classId`) USING BTREE,
  INDEX `tcmcoid`(`topFlag`, `classId`, `modelId`, `censorState`, `orderIdFlag`) USING BTREE,
  INDEX `tcmccid`(`topFlag`, `classId`, `modelId`, `censorState`, `contentId`) USING BTREE,
  INDEX `tcm`(`topFlag`, `classId`, `modelId`, `contentId`) USING BTREE,
  INDEX `cmmonclk`(`classId`, `modelId`, `clickMonthCount`) USING BTREE,
  INDEX `cmwekclk`(`classId`, `modelId`, `clickWeekCount`) USING BTREE,
  INDEX `cmdayclk`(`classId`, `modelId`, `clickDayCount`) USING BTREE,
  INDEX `cmclk`(`classId`, `modelId`, `clickCount`) USING BTREE,
  INDEX `cmmoncom`(`classId`, `modelId`, `commMonthCount`) USING BTREE,
  INDEX `cmwekcom`(`classId`, `modelId`, `commWeekCount`) USING BTREE,
  INDEX `cmdaycom`(`classId`, `modelId`, `commDayCount`) USING BTREE,
  INDEX `cmcom`(`classId`, `modelId`, `commCount`) USING BTREE,
  INDEX `cmsu`(`classId`, `modelId`, `supportCount`) USING BTREE,
  INDEX `cmag`(`classId`, `modelId`, `againstCount`) USING BTREE,
  INDEX `tcmpt`(`topFlag`, `classId`, `modelId`, `appearStartDateTime`) USING BTREE,
  INDEX `tcmcpt`(`topFlag`, `classId`, `modelId`, `censorState`, `appearStartDateTime`) USING BTREE,
  INDEX `tcmo`(`topFlag`, `classId`, `modelId`, `orderIdFlag`) USING BTREE,
  INDEX `cmat`(`classId`, `modelId`, `addTime`) USING BTREE,
  INDEX `tcmcpd`(`topFlag`, `classId`, `modelId`, `censorState`, `pubDateSysDT`) USING BTREE,
  INDEX `tcmpd`(`topFlag`, `classId`, `modelId`, `pubDateSysDT`) USING BTREE,
  INDEX `cmlioid`(`classId`, `modelId`, `classImgFlag`, `orderIdFlag`) USING BTREE,
  INDEX `cmhioid`(`classId`, `modelId`, `homeImgFlag`, `orderIdFlag`) USING BTREE,
  INDEX `cmchioid`(`classId`, `modelId`, `channelImgFlag`, `orderIdFlag`) USING BTREE,
  INDEX `cmcoioid`(`classId`, `modelId`, `contentImgFlag`, `orderIdFlag`) USING BTREE,
  INDEX `sidcapt`(`siteId`, `censorState`, `appearStartDateTime`) USING BTREE,
  INDEX `sidcept`(`siteId`, `censorState`, `appearEndDateTime`) USING BTREE,
  INDEX `test2`(`classId`, `censorState`, `contentId`) USING BTREE,
  INDEX `test`(`classId`, `censorState`, `pubDateSysDT`) USING BTREE,
  INDEX `test3`(`pubDateSysDT`, `contentId`) USING BTREE,
  INDEX `memId`(`creator`, `otherFlag`, `siteId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_mood
-- ----------------------------
DROP TABLE IF EXISTS `content_mood`;
CREATE TABLE `content_mood`  (
  `contentId` bigint(20) NOT NULL,
  `supportCount` bigint(20) NULL DEFAULT NULL,
  `againstCount` bigint(20) NULL DEFAULT NULL,
  `moodT1Count` bigint(20) NULL DEFAULT NULL,
  `moodT2Count` bigint(20) NULL DEFAULT NULL,
  `moodT3Count` bigint(20) NULL DEFAULT NULL,
  `moodT4Count` bigint(20) NULL DEFAULT NULL,
  `moodT5Count` bigint(20) NULL DEFAULT NULL,
  `moodT6Count` bigint(20) NULL DEFAULT NULL,
  `moodT7Count` bigint(20) NULL DEFAULT NULL,
  `moodT8Count` bigint(20) NULL DEFAULT NULL,
  `moodT9Count` bigint(20) NULL DEFAULT NULL,
  `moodT10Count` bigint(20) NULL DEFAULT NULL,
  INDEX `cid`(`contentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_oper_info
-- ----------------------------
DROP TABLE IF EXISTS `content_oper_info`;
CREATE TABLE `content_oper_info`  (
  `contentId` bigint(20) NULL DEFAULT NULL,
  `opId` bigint(20) NULL DEFAULT NULL,
  `verContentId` bigint(20) NULL DEFAULT NULL,
  `puserName` varchar(300)  NULL DEFAULT NULL,
  `actionId` varchar(300)  NULL DEFAULT NULL,
  `currStepName` varchar(300)  NULL DEFAULT NULL,
  `msgContent` varchar(4000)  NULL DEFAULT NULL,
  `fromStepId` varchar(300)  NULL DEFAULT NULL,
  `toStepId` varchar(300)  NULL DEFAULT NULL,
  `eventDT` datetime NULL DEFAULT NULL,
  `cMode` varchar(20)  NULL DEFAULT NULL,
  `infoType` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of content_oper_info
-- ----------------------------
INSERT INTO `content_oper_info` VALUES (2, NULL, NULL, 'admin', '添加内容', NULL, NULL, NULL, NULL, '2017-10-26 17:02:45', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (3, NULL, NULL, 'admin', '发布内容', NULL, '', NULL, NULL, '2017-10-27 14:55:20', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (4, NULL, NULL, 'admin', '发布内容', NULL, '', NULL, NULL, '2017-10-29 21:33:58', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (6, NULL, NULL, 'admin', '添加内容', NULL, NULL, NULL, NULL, '2017-10-29 22:26:59', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (7, NULL, NULL, 'admin', '添加内容', NULL, NULL, NULL, NULL, '2017-10-29 22:27:06', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (6, NULL, NULL, 'admin', '添加内容', NULL, NULL, NULL, NULL, '2018-01-17 14:51:14', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (6, NULL, NULL, 'admin', '发布内容', NULL, '', NULL, NULL, '2018-01-17 14:53:34', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (6, NULL, NULL, 'admin', '发布内容', NULL, '', NULL, NULL, '2018-01-17 14:55:00', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (6, NULL, NULL, 'admin', '发布内容', NULL, '', NULL, NULL, '2018-01-17 14:55:36', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (7, NULL, NULL, 'admin', '添加内容', NULL, NULL, NULL, NULL, '2018-01-17 17:20:17', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '添加内容', NULL, NULL, NULL, NULL, '2018-06-15 19:50:34', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '进入工作流', NULL, '', NULL, NULL, '2018-06-15 19:50:49', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '提交编辑', NULL, '', NULL, NULL, '2018-06-15 19:50:51', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '编辑通过', NULL, '', NULL, NULL, '2018-06-15 19:50:53', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '进入工作流', NULL, '', NULL, NULL, '2018-06-15 19:51:00', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '提交编辑', NULL, '', NULL, NULL, '2018-06-15 20:23:36', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '编辑通过', NULL, '', NULL, NULL, '2018-06-15 20:26:53', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '进入工作流', NULL, '', NULL, NULL, '2018-06-15 20:27:16', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '提交编辑', NULL, '', NULL, NULL, '2018-06-15 20:27:30', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (10, NULL, NULL, 'admin', '编辑通过', NULL, '', NULL, NULL, '2018-06-15 20:27:33', NULL, NULL);
INSERT INTO `content_oper_info` VALUES (11, NULL, NULL, 'admin', '添加内容', NULL, NULL, NULL, NULL, '2018-06-15 20:51:14', NULL, NULL);

-- ----------------------------
-- Table structure for content_relate_info
-- ----------------------------
DROP TABLE IF EXISTS `content_relate_info`;
CREATE TABLE `content_relate_info`  (
  `contentId` bigint(20) NOT NULL,
  `rClassId` int(20) NULL DEFAULT NULL,
  `infoId` bigint(20) NULL DEFAULT NULL,
  `orderFlag` int(11) NULL DEFAULT NULL,
  INDEX `c-r`(`contentId`, `rClassId`, `orderFlag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_special_subject_info
-- ----------------------------
DROP TABLE IF EXISTS `content_special_subject_info`;
CREATE TABLE `content_special_subject_info`  (
  `specSubId` bigint(20) NOT NULL AUTO_INCREMENT,
  `specFlag` varchar(20)  NULL DEFAULT NULL,
  `title` varchar(120)  NOT NULL,
  `shortTitle` varchar(70)  NULL DEFAULT NULL,
  `summary` varchar(2500)  NULL DEFAULT NULL,
  `banner` varchar(100)  NULL DEFAULT NULL,
  `homeImage` varchar(100)  NULL DEFAULT NULL,
  `classImage` varchar(100)  NULL DEFAULT NULL,
  `channelImage` varchar(100)  NULL DEFAULT NULL,
  `specProduceType` int(11) NULL DEFAULT NULL,
  `listProduceType` int(11) NULL DEFAULT NULL,
  `specPublishRuleId` int(11) NULL DEFAULT NULL,
  `listPublishRuleId` int(11) NULL DEFAULT NULL,
  `specTemplateUrl` varchar(180)  NULL DEFAULT NULL,
  `listTemplateUrl` varchar(180)  NULL DEFAULT NULL,
  `syncPubSpec` int(11) NOT NULL,
  `listPageLimit` varchar(5)  NOT NULL,
  `staticPageUrl` varchar(300)  NULL DEFAULT NULL,
  `useStatus` int(11) NOT NULL,
  PRIMARY KEY (`specSubId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_status
-- ----------------------------
DROP TABLE IF EXISTS `content_status`;
CREATE TABLE `content_status`  (
  `selfContentId` bigint(20) NOT NULL DEFAULT 0,
  `clickMonthCount` bigint(20) NULL DEFAULT NULL,
  `clickWeekCount` bigint(20) NULL DEFAULT NULL,
  `clickDayCount` bigint(20) NULL DEFAULT NULL,
  `commMonthCount` bigint(20) NULL DEFAULT NULL,
  `commWeekCount` bigint(20) NULL DEFAULT NULL,
  `commDayCount` bigint(20) NULL DEFAULT NULL,
  `commCount` bigint(20) NULL DEFAULT NULL,
  `clickCount` bigint(20) NULL DEFAULT NULL,
  `supportCount` bigint(20) NULL DEFAULT NULL,
  `againstCount` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`selfContentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_trash_main_info
-- ----------------------------
DROP TABLE IF EXISTS `content_trash_main_info`;
CREATE TABLE `content_trash_main_info`  (
  `contentId` bigint(20) NOT NULL DEFAULT 0,
  `classId` bigint(20) NOT NULL,
  `refCid` bigint(20) NULL DEFAULT NULL,
  `linkCid` bigint(20) NULL DEFAULT NULL,
  `title` varchar(400)  NOT NULL,
  `simpleTitle` varchar(300)  NULL DEFAULT NULL,
  `shortTitle` varchar(300)  NULL DEFAULT NULL,
  `author` varchar(60)  NULL DEFAULT NULL,
  `creator` varchar(50)  NULL DEFAULT NULL,
  `orgCode` varchar(200)  NULL DEFAULT NULL,
  `summary` varchar(500)  NULL DEFAULT NULL,
  `boost` float NULL DEFAULT NULL,
  `titleStyle` varchar(80)  NULL DEFAULT NULL,
  `simpleTitleStyle` varchar(80)  NULL DEFAULT NULL,
  `addTime` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `clickMonthCount` bigint(20) NULL DEFAULT NULL,
  `clickWeekCount` bigint(20) NULL DEFAULT NULL,
  `clickDayCount` bigint(20) NULL DEFAULT NULL,
  `clickCount` bigint(20) NULL DEFAULT NULL,
  `commMonthCount` bigint(20) NULL DEFAULT NULL,
  `commWeekCount` bigint(20) NULL DEFAULT NULL,
  `commDayCount` bigint(20) NULL DEFAULT NULL,
  `commCount` bigint(20) NULL DEFAULT NULL,
  `supportCount` bigint(20) NULL DEFAULT NULL,
  `againstCount` bigint(20) NULL DEFAULT NULL,
  `homeImage` varchar(200)  NULL DEFAULT NULL,
  `classImage` varchar(200)  NULL DEFAULT NULL,
  `channelImage` varchar(200)  NULL DEFAULT NULL,
  `contentImage` varchar(200)  NULL DEFAULT NULL,
  `systemHandleTime` varchar(25)  NOT NULL,
  `especialTemplateUrl` varchar(110)  NULL DEFAULT NULL,
  `staticPageUrl` varchar(400)  NULL DEFAULT NULL,
  `produceType` int(11) NULL DEFAULT NULL,
  `censorState` int(11) NULL DEFAULT NULL,
  `isPageContent` int(11) NULL DEFAULT NULL,
  `isSystemOrder` int(11) NOT NULL,
  `orderIdFlag` double NOT NULL,
  `keywords` varchar(200)  NULL DEFAULT NULL,
  `tagKey` varchar(300)  NULL DEFAULT NULL,
  `pubDateSysDT` bigint(20) NULL DEFAULT NULL,
  `appearStartDateTime` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `appearEndDateTime` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `homeImgFlag` int(11) NULL DEFAULT NULL,
  `classImgFlag` int(11) NULL DEFAULT NULL,
  `commendFlag` int(11) NULL DEFAULT NULL,
  `channelImgFlag` int(11) NULL DEFAULT NULL,
  `contentImgFlag` int(11) NULL DEFAULT NULL,
  `typeFlag` varchar(40)  NULL DEFAULT NULL,
  `topFlag` int(11) NULL DEFAULT NULL,
  `modelId` bigint(20) NOT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  `allowCommend` int(11) NULL DEFAULT NULL,
  `deleteTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`contentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for content_type
-- ----------------------------
DROP TABLE IF EXISTS `content_type`;
CREATE TABLE `content_type`  (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(80)  NOT NULL,
  `typeFlag` varchar(60)  NOT NULL,
  `groupId` int(11) NULL DEFAULT NULL,
  `siteId` bigint(6) NOT NULL,
  PRIMARY KEY (`typeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of content_type
-- ----------------------------
INSERT INTO `content_type` VALUES (6, '图片', 'mh_tp', NULL, 5);
INSERT INTO `content_type` VALUES (7, '视频', 'mh_sp', NULL, 5);
INSERT INTO `content_type` VALUES (8, '精选', 'mh_jx', NULL, 5);
INSERT INTO `content_type` VALUES (9, '测试测试测试测试', 'mh_cscs', NULL, 5);

-- ----------------------------
-- Table structure for content_wait_pub_temp
-- ----------------------------
DROP TABLE IF EXISTS `content_wait_pub_temp`;
CREATE TABLE `content_wait_pub_temp`  (
  `contentId` bigint(20) NULL DEFAULT NULL,
  `classId` bigint(20) NULL DEFAULT NULL,
  `orderIdFlag` double(15, 6) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for contentclass
-- ----------------------------
DROP TABLE IF EXISTS `contentclass`;
CREATE TABLE `contentclass`  (
  `classId` bigint(20) NOT NULL AUTO_INCREMENT,
  `linkFromClass` bigint(20) NULL DEFAULT NULL,
  `linkToClass` bigint(20) NULL DEFAULT NULL,
  `siteFlag` varchar(40)  NOT NULL,
  `classFlag` varchar(70)  NOT NULL,
  `className` varchar(60)  NOT NULL,
  `classType` int(11) NOT NULL,
  `parent` bigint(20) NOT NULL,
  `layer` int(11) NOT NULL,
  `isLeaf` int(11) NOT NULL,
  `orgMode` int(11) NULL DEFAULT NULL,
  `classDesc` varchar(255)  NULL DEFAULT NULL,
  `isSpecial` int(11) NOT NULL,
  `isRecommend` int(11) NULL DEFAULT NULL,
  `linearOrderFlag` varchar(900)  NOT NULL,
  `isLastChild` int(11) NOT NULL,
  `contentType` bigint(20) NOT NULL,
  `singleContentId` bigint(20) NULL DEFAULT NULL,
  `classHomeTemplateUrl` varchar(180)  NULL DEFAULT NULL,
  `mobClassHomeTemplateUrl` varchar(200)  NULL DEFAULT NULL,
  `classTemplateUrl` varchar(180)  NULL DEFAULT NULL,
  `mobClassTemplateUrl` varchar(200)  NULL DEFAULT NULL,
  `contentTemplateUrl` varchar(180)  NULL DEFAULT NULL,
  `mobContentTemplateUrl` varchar(200)  NULL DEFAULT NULL,
  `padClassHomeTemplateUrl` varchar(200)  NULL DEFAULT NULL,
  `padClassTemplateUrl` varchar(200)  NULL DEFAULT NULL,
  `padContentTemplateUrl` varchar(200)  NULL DEFAULT NULL,
  `channelPath` varchar(600)  NULL DEFAULT NULL,
  `syncPubClass` int(11) NOT NULL,
  `listPageLimit` varchar(5)  NOT NULL,
  `endStaticPageUrl` varchar(300)  NULL DEFAULT NULL,
  `endPagePos` int(11) NULL DEFAULT NULL,
  `staticHomePageUrl` varchar(300)  NULL DEFAULT NULL,
  `staticPageUrl` varchar(300)  NULL DEFAULT NULL,
  `classHomeProduceType` int(11) NOT NULL,
  `classProduceType` int(11) NOT NULL,
  `contentProduceType` int(11) NOT NULL,
  `mobClassHomeProduceType` int(11) NULL DEFAULT NULL,
  `mobClassProduceType` int(11) NULL DEFAULT NULL,
  `mobContentProduceType` int(11) NULL DEFAULT NULL,
  `immediatelyStaticAction` int(11) NOT NULL,
  `needCensor` int(11) NULL DEFAULT NULL,
  `showStatus` int(11) NOT NULL,
  `workflowId` bigint(20) NULL DEFAULT NULL,
  `useStatus` int(11) NOT NULL,
  `contentPublishRuleId` bigint(20) NULL DEFAULT NULL,
  `classHomePublishRuleId` bigint(20) NULL DEFAULT NULL,
  `classPublishRuleId` bigint(20) NULL DEFAULT NULL,
  `outLink` varchar(300)  NULL DEFAULT NULL,
  `logoImage` varchar(200)  NULL DEFAULT NULL,
  `banner` varchar(200)  NULL DEFAULT NULL,
  `openComment` int(11) NOT NULL,
  `mustCommentCensor` int(11) NOT NULL,
  `notMemberComment` int(11) NOT NULL,
  `commentCaptcha` int(11) NULL DEFAULT NULL,
  `filterCommentSensitive` int(11) NULL DEFAULT NULL,
  `commentHtml` int(11) NULL DEFAULT NULL,
  `sensitiveMode` int(11) NOT NULL,
  `seoTitle` varchar(300)  NULL DEFAULT NULL,
  `seoKeyword` varchar(300)  NULL DEFAULT NULL,
  `seoDesc` varchar(500)  NULL DEFAULT NULL,
  `searchStatus` int(11) NULL DEFAULT NULL,
  `relateRangeType` int(11) NULL DEFAULT NULL,
  `memberAddContent` int(11) NULL DEFAULT NULL,
  `extDataModelId` bigint(20) NULL DEFAULT NULL,
  `editorImageMark` int(11) NULL DEFAULT NULL,
  `editorImageH` int(11) NULL DEFAULT NULL,
  `editorImageW` int(11) NULL DEFAULT NULL,
  `homeImageW` int(11) NULL DEFAULT NULL,
  `homeImageH` int(11) NULL DEFAULT NULL,
  `classImageW` int(11) NULL DEFAULT NULL,
  `classImageH` int(11) NULL DEFAULT NULL,
  `listImageW` int(11) NULL DEFAULT NULL,
  `listImageH` int(11) NULL DEFAULT NULL,
  `contentImageW` int(11) NULL DEFAULT NULL,
  `contentImageH` int(11) NULL DEFAULT NULL,
  `systemHandleTime` datetime NULL DEFAULT NULL,
  `addYear` int(11) NULL DEFAULT NULL,
  `addMonth` int(11) NULL DEFAULT NULL,
  `contentImageDM` int(11) NULL DEFAULT NULL,
  `listImageDM` int(11) NULL DEFAULT NULL,
  `classImageDM` int(11) NULL DEFAULT NULL,
  `homeImageDM` int(11) NULL DEFAULT NULL,
  `editorImageDM` int(11) NULL DEFAULT NULL,
  `whiteIp` varchar(800)  NULL DEFAULT NULL,
  `submitWhiteIp` varchar(800)  NULL DEFAULT NULL,
  `relateClassIds` varchar(200)  NULL DEFAULT NULL,
  `submitDCBehavior` varchar(200)  NULL DEFAULT NULL,
  PRIMARY KEY (`classId`) USING BTREE,
  INDEX `site`(`siteFlag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10786 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of contentclass
-- ----------------------------
INSERT INTO `contentclass` VALUES (10644, 10669, NULL, 'demo', 'mh_gn', '国内', 1, -9999, 1, 0, NULL, NULL, 0, 0, '081', 0, 82, NULL, 'channel.jsp?channelId={class-id}', 'mob/list.jsp?id={class-id}', '', 'mob/list.jsp?id={class-id}', 'news_content.jsp?id={content-id}', 'mob/article.jsp?id={content-id}', '', '', '', '10644', 1, '10', NULL, NULL, 'http://192.168.0.102/demo/', 'http://192.168.0.102/demo/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, 53, 1, 39, -1, -1, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10645, NULL, NULL, 'demo', 'mh_gj', '国际', 1, -9999, 1, 0, NULL, NULL, 0, 0, '082', 0, 82, NULL, 'news.jsp?channelId={class-id}', NULL, 'list.jsp?cid={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10645', 1, '10', NULL, NULL, 'channel/10645/index.html', 'channel/list/10645.html', 3, 1, 3, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10646, NULL, NULL, 'demo', 'mh_js', '军事', 1, -9999, 1, 1, NULL, NULL, 0, 0, '085', 0, 111, NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'list_channel.jsp?channelId={class-id}', NULL, '/wenku_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10646', 1, '10', NULL, NULL, 'channel/10646/index.html', 'channel/list/10646.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 1, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10648, NULL, NULL, 'demo', 'mh_tp', '图片', 1, -9999, 1, 0, NULL, NULL, 0, 0, '083', 0, 99, NULL, 'photo.jsp?channelId={class-id}', NULL, 'list.jsp?cid={class-id}', NULL, 'photo_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10648', 1, '10', NULL, NULL, 'channel/10648/index.html', 'channel/list/10648.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, 53, 1, 25, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 500, 500, 310, 220, 236, 147, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10650, NULL, NULL, 'demo', 'mh_ty', '体育', 1, -9999, 1, 1, NULL, NULL, 0, 0, '088', 0, 82, NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10650', 1, '10', NULL, NULL, 'channel/10650/index.html', 'channel/list/10650.html', 1, 3, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10651, NULL, NULL, 'demo', 'mh_kj', '科技', 1, -9999, 1, 1, NULL, NULL, 0, 0, '089', 0, 82, NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'list_channel.thtml?channelId={class-id}', NULL, 'news_content.thtml?id={content-id}', NULL, NULL, NULL, NULL, '10651', 1, '2', NULL, NULL, 'channel/10651/index.html', 'channel/list/10651.html', 1, 3, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 1, 0, 0, 1, 0, 1, '', '', '', 1, 1, 1, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, '192.168.1.*', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10652, NULL, NULL, 'demo', 'mh_cj', '财经', 1, -9999, 1, 1, NULL, NULL, 0, 0, '090', 0, 82, NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10652', 1, '10', NULL, NULL, 'channel/10652/index.html', 'channel/list/10652.html', 3, 3, 3, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10653, NULL, NULL, 'demo', 'mh_yl', '娱乐', 1, -9999, 1, 1, NULL, NULL, 0, 0, '091', 0, 82, NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10653', 1, '10', NULL, NULL, 'channel/10653/index.html', 'channel/list/10653.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10655, NULL, NULL, 'demo', 'mh_vote', '调查', 1, -9999, 1, 1, NULL, NULL, 0, 0, '115', 0, -1, NULL, 'vote.jsp?channelId={class-id}', NULL, '', NULL, '', NULL, NULL, NULL, NULL, '10655', 1, '10', NULL, NULL, 'channel/10655/index.html', 'channel/list/10655.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 25, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 310, 220, 0, 0, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10657, NULL, NULL, 'demo', 'mh_sp', '视频', 1, -9999, 1, 0, NULL, NULL, 0, 0, '084', 0, 98, NULL, 'channel_video.jsp?channelId={class-id}', NULL, 'list.jsp?cid={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657', 1, '10', NULL, NULL, 'channel/10657/index.html', 'channel/list/10657.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 25, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-17 23:57:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10658, 10644, NULL, 'demo', 'mh_gnxw', '国内新闻', 1, 10644, 2, 1, 0, NULL, 0, 0, '081003', 0, 82, NULL, 'channel.jsp?channelId={class-id}', '', 'list_news.jsp?cid={class-id}', '', 'news_content.jsp?id={content-id}', '', '', '', '', '10644,10658', 1, '10', NULL, NULL, 'channel/10658/index.html', 'channel/list/10658.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, 53, 1, 40, 36, 23, 'http://', 'id=31;t=i;sid=5;reUrl=2018-06-15/1529062876629402880e76403344e3050164033fe5d500b6.jpg;iw=480;ih=320;', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 600, 600, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-18 10:43:45', NULL, NULL, 0, 0, 0, 0, 0, '192.168.1.*,127.0.0.1', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10659, 10644, NULL, 'demo', 'mh_szjj', '时政聚焦', 1, 10644, 2, 1, NULL, NULL, 0, 0, '081004', 0, 82, NULL, 'channel.jsp?channelId={class-id}', '', 'list_news.jsp?cid={class-id}', '', 'news_content.jsp?id={content-id}', '', '', '', '', '10644,10659', 1, '10', NULL, NULL, 'channel/10659/index.html', 'channel/list/10659.html', 1, 3, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-18 10:43:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10660, NULL, NULL, 'demo', 'mh_gdbd', '港台新闻', 1, 10644, 2, 1, NULL, NULL, 0, 0, '081005', 0, 82, NULL, 'channel.jsp?channelId={class-id}', NULL, 'list_news.jsp?cid={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10644,10660', 1, '10', NULL, NULL, 'channel/10660/index.html', 'channel/list/10660.html', 1, 3, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-18 10:43:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10661, NULL, NULL, 'demo', 'mh_shyw', '世态万象', 1, 10644, 2, 1, NULL, NULL, 0, 0, '081006', 0, 82, NULL, 'channel.jsp?channelId={class-id}', NULL, 'list_news.jsp?cid={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10644,10661', 1, '10', NULL, NULL, 'channel/10661/index.html', 'channel/list/10661.html', 1, 3, 1, NULL, NULL, NULL, 1, NULL, 1, 53, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-18 10:43:45', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10662, 0, NULL, 'demo', 'mh_sdzz', '深度追踪', 1, 10644, 2, 1, NULL, NULL, 0, 0, '081007', 1, 82, NULL, 'channel.jsp?channelId={class-id}', '', 'list_news.jsp?cid={class-id}', '', 'article.jsp?id={content-id}', '', '', '', '', '10644,10662', 1, '10', NULL, NULL, 'channel/10662/index.html', 'channel/list/10662.html', 1, 3, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-18 10:43:46', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10664, NULL, NULL, 'demo', 'mh_gjyw', '国际要闻', 1, 10645, 2, 1, NULL, NULL, 0, 0, '082001', 0, 82, NULL, 'channel.jsp?channelId={class-id}', NULL, 'list_news.jsp?cid={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10645,10664', 1, '10', NULL, NULL, 'channel/10664/index.html', 'channel/list/10664.html', 1, 3, 3, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 500, 500, 291, 120, 0, 0, 124, 77, 0, 0, '2014-09-18 23:00:10', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10665, NULL, NULL, 'demo', 'sp_gnsp', '资讯频道', 1, 10657, 2, 0, NULL, NULL, 0, 0, '084001', 0, 98, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content_m.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10665', 1, '10', NULL, NULL, 'http://192.168.1.101/jtopcms/', 'http://192.168.1.101/jtopcms/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, -1, 0, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 17:28:34', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10668, NULL, NULL, 'demo', 'sp_gjsp', '娱乐圈', 1, 10657, 2, 0, NULL, NULL, 0, 0, '084002', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10668', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 21:54:41', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10669, NULL, NULL, 'demo', 'sp_jlsj', '记录世界', 1, 10657, 2, 0, NULL, NULL, 0, 0, '084003', 1, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10669', 1, '10', NULL, NULL, 'http://demo2.jtopcms.com/ButterflyCMS/', 'http://demo2.jtopcms.com/ButterflyCMS/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 21:54:41', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10670, NULL, NULL, 'demo', 'sp_xwzx', '新闻资讯', 1, 10665, 3, 1, NULL, NULL, 0, 0, '084001001', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10665,10670', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:01:46', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10671, NULL, NULL, 'demo', 'sp_xcbd', '现场报道', 1, 10665, 3, 1, NULL, NULL, 0, 0, '084001002', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10665,10671', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:01:46', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10672, NULL, NULL, 'demo', 'sp_shms', '社会民生', 1, 10665, 3, 1, NULL, NULL, 0, 0, '084001003', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10665,10672', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:01:46', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10673, NULL, NULL, 'demo', 'sp_qrqs', '奇人奇事', 1, 10665, 3, 1, NULL, NULL, 0, 0, '084001004', 1, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10665,10673', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:01:46', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10674, NULL, NULL, 'demo', 'yl_mxbg', '明星八卦', 1, 10668, 3, 1, NULL, NULL, 0, 0, '084002001', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10668,10674', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:22:10', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10675, NULL, NULL, 'demo', 'yl_djzz', '独家制造', 1, 10668, 3, 1, NULL, NULL, 0, 0, '084002002', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10668,10675', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:22:10', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10676, NULL, NULL, 'demo', 'yl_ysqy', '影视前沿', 1, 10668, 3, 1, NULL, NULL, 0, 0, '084002003', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10668,10676', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:22:10', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10677, NULL, NULL, 'demo', 'yl_yydd', '音乐地带', 1, 10668, 3, 1, NULL, NULL, 0, 0, '084002004', 1, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10668,10677', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:22:10', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10678, NULL, NULL, 'demo', 'jl_lsrw', '历史人物', 1, 10669, 3, 1, NULL, NULL, 0, 0, '084003001', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10669,10678', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:31:10', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10679, NULL, NULL, 'demo', 'jl_zrtx', '自然探险', 1, 10669, 3, 1, NULL, NULL, 0, 0, '084003002', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10669,10679', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:31:10', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10680, NULL, NULL, 'demo', 'jl_jskj', '军事科技', 1, 10669, 3, 1, NULL, NULL, 0, 0, '084003003', 0, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10669,10680', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:31:10', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10681, NULL, NULL, 'demo', 'jl_whsh', '文化生活', 1, 10669, 3, 1, NULL, NULL, 0, 0, '084003004', 1, 62, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'video_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10657,10669,10681', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 0, 0, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-25 22:31:10', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10682, NULL, NULL, 'demo', 'tj_yzjx', '一周精选', 1, 10648, 2, 1, NULL, NULL, 0, 0, '083001', 0, 106, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'photo_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10648,10682', 1, '10', NULL, NULL, 'http://demo.jtopcms.com/jtopcms/', 'channel/list/10682.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, 53, 1, 25, -1, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 310, 220, 236, 147, 124, 77, 0, 0, '2014-09-28 13:44:19', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10683, NULL, NULL, 'demo', 'tj_gnrm', '国内热门', 1, 10648, 2, 1, NULL, NULL, 0, 0, '083002', 0, 99, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'photo_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10648,10683', 1, '10', NULL, NULL, 'http://192.168.1.100/jtopcms/demo/', 'channel/list/10683.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 25, -1, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 310, 220, 236, 147, 124, 77, 0, 0, '2014-09-28 13:44:19', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10684, NULL, NULL, 'demo', 'tj_gwtp', '国外图片', 1, 10648, 2, 1, NULL, NULL, 0, 0, '083003', 0, 99, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'photo_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10648,10684', 1, '10', NULL, NULL, 'channel/10684/index.html', 'channel/list/10684.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, 53, 1, 25, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 310, 220, 236, 147, 124, 77, 0, 0, '2014-09-28 13:44:19', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10685, NULL, NULL, 'demo', 'tj_sjjd', '视觉焦点', 1, 10648, 2, 1, NULL, NULL, 0, 0, '083004', 1, 49, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'photo_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10648,10685', 1, '10', NULL, NULL, 'http://192.168.1.100/', 'channel/list/10685.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, 53, 1, 25, -1, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 310, 220, 236, 147, 124, 77, 0, 0, '2014-09-28 13:44:19', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10686, NULL, NULL, 'demo', 'mh_2014_zt', '门户2014', 4, -9999, 1, 0, NULL, NULL, 1, 0, '095', 0, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '10686', 1, '10', NULL, NULL, 'http://demo2.jtopcms.com/ButterflyCMS/', 'http://demo2.jtopcms.com/ButterflyCMS/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, NULL, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-09-30 09:45:40', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10687, NULL, NULL, 'demo', 'zt_gate', '专题', 1, -9999, 1, 1, NULL, NULL, 0, 0, '114', 0, -1, NULL, 'channel_spec.thtml?channelId={class-id}', NULL, '', NULL, '', NULL, NULL, NULL, NULL, '10687', 1, '10', NULL, NULL, 'channel/10687/index.html', 'http://192.168.0.100/demo/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, -1, 36, -1, 'http://www.sohu.com', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-10-02 23:50:08', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10688, NULL, NULL, 'demo', 'site_gb', '留言', 1, -9999, 1, 1, NULL, NULL, 0, 0, '116', 0, -1, NULL, 'guestbook.jsp?channelId={class-id}', NULL, '', NULL, '', NULL, NULL, NULL, NULL, '10688', 1, '10', NULL, NULL, 'channel/10688/index.html', 'http://demo2.jtopcms.com/ButterflyCMS/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 0, 36, 0, 'http://demo2.jtopcms.com/ButterflyCMS/comment_list.jsp', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-10-02 23:52:49', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10713, NULL, NULL, 'demo', 'zgjj', '中国经济专题', 5, 10686, 2, 1, NULL, NULL, 1, 0, '095001', 1, -1, NULL, 'channel_spec.jsp?channelId=10687', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '10686,10713', 1, '10', NULL, NULL, 'spec/10713/index.html', 'http://demo.jtopcms.com:8089/jtopcms/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, NULL, 34, NULL, NULL, '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, NULL, -1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2014-10-31 22:12:28', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10714, NULL, NULL, 'demo', 'mh_tag', 'Tag', 1, -9999, 1, 1, NULL, NULL, 0, 0, '097', 0, 57, NULL, '', NULL, '', NULL, '', NULL, NULL, NULL, NULL, '10714', 1, '10', NULL, NULL, 'http://192.168.1.100/', 'http://192.168.1.100/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 0, -1, 1, -1, -1, -1, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-11-01 00:40:34', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10715, NULL, NULL, 'demo', 'mh_hqsy', '环球视野', 1, 10645, 2, 1, NULL, NULL, 0, 0, '082002', 0, 82, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10645,10715', 1, '10', NULL, NULL, 'channel/10715/index.html', 'channel/list/10715.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-11-16 00:27:23', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10716, NULL, NULL, 'demo', 'mh_hqqw', '环球趣闻', 1, 10645, 2, 1, NULL, NULL, 0, 0, '082003', 0, 82, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10645,10716', 1, '10', NULL, NULL, 'http://demo.jtopcms.com:81/jtopcms/', 'channel/list/10716.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, -1, 23, 'http://', '', '', 1, 1, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-11-16 00:27:23', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10717, NULL, NULL, 'demo', 'mh_hqgc', '海外观察', 1, 10645, 2, 1, NULL, NULL, 0, 0, '082004', 0, 82, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10645,10717', 1, '10', NULL, NULL, 'channel/10717/index.html', 'channel/list/10717.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-11-16 00:27:23', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10718, NULL, NULL, 'demo', 'mh_gjzt', '国际杂谈', 1, 10645, 2, 1, NULL, NULL, 0, 0, '082005', 0, 82, NULL, '', NULL, 'list.jsp?cid={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10645,10718', 1, '10', NULL, NULL, 'channel/10718/index.html', 'channel/list/10718.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-11-16 00:30:32', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10719, 0, NULL, 'demo', 'mh_yzcz', '近邻动态', 2, 10645, 2, 1, NULL, NULL, 0, 0, '082006', 1, 82, NULL, '', '', 'list.jsp?cid={class-id}', '', 'news_content.jsp?id={content-id}', '', '', '', '', '10645,10719', 1, '10', NULL, NULL, 'http://127.0.0.1/', 'channel/list/10719.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, -1, 23, 'http://www.baidu.com', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-11-16 00:39:03', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10720, NULL, NULL, 'demo', 'mh_qc', '读书', 1, -9999, 1, 1, NULL, NULL, 0, 0, '093', 0, 82, NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'list_channel.jsp?channelId={class-id}', NULL, 'news_content.jsp?id={content-id}', NULL, NULL, NULL, NULL, '10720', 1, '10', NULL, NULL, 'channel/10720/index.html', 'channel/list/10720.html', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, 36, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-11-19 23:10:18', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10721, NULL, NULL, 'demo', 'mh_sm', '简历', 1, -9999, 1, 1, NULL, NULL, 0, 0, '096', 0, 82, NULL, 'add_def_form_data.jsp?channelId={class-id}', NULL, '', NULL, '', NULL, NULL, NULL, NULL, '10721', 1, '10', NULL, NULL, 'http://192.168.1.102/jtopcms/', 'http://192.168.1.102/jtopcms/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, 40, -1, -1, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 291, 120, 0, 0, 124, 77, 0, 0, '2014-11-19 23:10:18', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10724, 0, NULL, 'demo', 'dy1', '单页', 3, -9999, 1, 1, NULL, NULL, 0, 0, '117', 0, 82, 4, '', '', 'channel.jsp', '', 'news_content.jsp?id={content-id}', '', '', '', '', '10724', 1, '10', NULL, NULL, 'http://127.0.0.1/', 'channel/list/10724.html', 1, 3, 3, NULL, NULL, NULL, 1, NULL, 1, 53, 1, 40, -1, 23, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2014-11-25 21:26:27', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10784, 0, NULL, 'demo', 'sadavf', '撒旦按时', 3, -9999, 1, 1, NULL, NULL, 0, 0, '139', 0, 82, 5, '', '', '', '', '', '', '', '', '', '10784', 1, '10', NULL, NULL, 'http://127.0.0.1/', 'http://127.0.0.1/', 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, -1, -1, -1, 'http://', '', '', 1, 0, 1, 1, 1, 0, 1, '', '', '', 1, 1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2016-06-11 20:25:05', NULL, NULL, 0, 0, 0, 0, 0, '', NULL, NULL, NULL);
INSERT INTO `contentclass` VALUES (10785, NULL, NULL, 'demo', 'saf345', 'dfsdf', 1, -9999, 1, 1, NULL, NULL, 0, 0, '140', 1, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '10785', 1, '10', NULL, NULL, NULL, NULL, 1, 1, 1, NULL, NULL, NULL, 1, NULL, 1, -1, 1, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, 1, 1, 1, 0, 1, NULL, NULL, NULL, 1, 1, NULL, NULL, 1, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, '2017-11-09 15:10:33', NULL, NULL, 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for friend_site_link
-- ----------------------------
DROP TABLE IF EXISTS `friend_site_link`;
CREATE TABLE `friend_site_link`  (
  `flId` bigint(20) NOT NULL AUTO_INCREMENT,
  `siteName` varchar(120)  NULL DEFAULT NULL,
  `siteLink` varchar(300)  NULL DEFAULT NULL,
  `siteLogo` varchar(120)  NULL DEFAULT NULL,
  `orderFlag` int(11) NOT NULL,
  `typeId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`flId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of friend_site_link
-- ----------------------------
INSERT INTO `friend_site_link` VALUES (4, 'asdasdasdasdsdf', 'asdasdasd', '', 3, 4, 1);
INSERT INTO `friend_site_link` VALUES (5, 'Oschina主站', 'http://www.oschina.net/', '', 44, 4, 5);
INSERT INTO `friend_site_link` VALUES (7, 'CSDN主站', 'http://www.csdn.net/', '', 222, 4, 5);

-- ----------------------------
-- Table structure for friend_site_link_type
-- ----------------------------
DROP TABLE IF EXISTS `friend_site_link_type`;
CREATE TABLE `friend_site_link_type`  (
  `ltId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(60)  NOT NULL,
  `typeFlag` varchar(60)  NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`ltId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of friend_site_link_type
-- ----------------------------
INSERT INTO `friend_site_link_type` VALUES (4, '全站共用', 'hp', 5);

-- ----------------------------
-- Table structure for guestbook_config
-- ----------------------------
DROP TABLE IF EXISTS `guestbook_config`;
CREATE TABLE `guestbook_config`  (
  `configId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cfgName` varchar(50)  NOT NULL,
  `cfgDesc` varchar(300)  NULL DEFAULT NULL,
  `mustHaveTitle` int(11) NOT NULL,
  `mustLogin` int(11) NOT NULL,
  `mustCensor` int(11) NOT NULL,
  `needVerifyCode` int(11) NOT NULL,
  `cfgFlag` varchar(80)  NOT NULL,
  `useState` int(11) NOT NULL,
  `infoModelId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `workflowId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`configId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of guestbook_config
-- ----------------------------
INSERT INTO `guestbook_config` VALUES (9, '测试', 'sx', 1, 0, 1, 1, 'cs', 1, -1, 5, NULL);

-- ----------------------------
-- Table structure for guestbook_main_info
-- ----------------------------
DROP TABLE IF EXISTS `guestbook_main_info`;
CREATE TABLE `guestbook_main_info`  (
  `gbId` bigint(20) NOT NULL AUTO_INCREMENT,
  `configId` bigint(20) NOT NULL,
  `isReply` int(11) NOT NULL,
  `isCensor` int(11) NOT NULL,
  `isOpen` int(11) NOT NULL,
  `gbTitle` varchar(400)  NULL DEFAULT NULL,
  `gbMan` varchar(80)  NULL DEFAULT NULL,
  `gbText` varchar(3500)  NULL DEFAULT NULL,
  `gbEmail` varchar(70)  NOT NULL,
  `replyMan` varchar(200)  NULL DEFAULT NULL,
  `replyText` varchar(3000)  NULL DEFAULT NULL,
  `replyDate` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `ip` varchar(25)  NULL DEFAULT NULL,
  `memberId` bigint(20) NULL DEFAULT NULL,
  `addDate` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `siteId` bigint(20) NOT NULL,
  `gbUserId` bigint(20) NULL DEFAULT NULL,
  `isGbUserId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`gbId`) USING BTREE,
  INDEX `c-isOp`(`configId`, `isOpen`) USING BTREE,
  INDEX `c`(`configId`) USING BTREE,
  INDEX `c-isCe`(`configId`, `isCensor`) USING BTREE,
  INDEX `c-isRe`(`configId`, `isReply`) USING BTREE,
  INDEX `memId`(`memberId`, `siteId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of guestbook_main_info
-- ----------------------------
INSERT INTO `guestbook_main_info` VALUES (3, 9, 0, 0, 1, 'cms.ContentListcms.ContentListcms.ContentListcms.ContentListcms.ContentListcms.ContentList', 'cms.ContentList', 'cms.ContentListcms.ContentListcms.ContentListcms.ContentListcms.ContentListcms.ContentList', '', NULL, NULL, NULL, '127.0.0.1', NULL, '2018-06-15 20:54:15', 5, NULL, NULL);

-- ----------------------------
-- Table structure for icon_res_info
-- ----------------------------
DROP TABLE IF EXISTS `icon_res_info`;
CREATE TABLE `icon_res_info`  (
  `iconName` varchar(50)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of icon_res_info
-- ----------------------------
INSERT INTO `icon_res_info` VALUES ('barcode.png');
INSERT INTO `icon_res_info` VALUES ('edit-rotate.png');
INSERT INTO `icon_res_info` VALUES ('arrow-step.png');
INSERT INTO `icon_res_info` VALUES ('cutlery-spoon.png');
INSERT INTO `icon_res_info` VALUES ('speaker--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-page-last.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-excel-csv.png');
INSERT INTO `icon_res_info` VALUES ('paper-bag-label.png');
INSERT INTO `icon_res_info` VALUES ('address-book--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-k.png');
INSERT INTO `icon_res_info` VALUES ('application-tile-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('snowman.png');
INSERT INTO `icon_res_info` VALUES ('hand-point-180.png');
INSERT INTO `icon_res_info` VALUES ('calendar--minus.png');
INSERT INTO `icon_res_info` VALUES ('receipt-shred.png');
INSERT INTO `icon_res_info` VALUES ('shield--minus.png');
INSERT INTO `icon_res_info` VALUES ('key.png');
INSERT INTO `icon_res_info` VALUES ('arrow-135.png');
INSERT INTO `icon_res_info` VALUES ('edit-space.png');
INSERT INTO `icon_res_info` VALUES ('briefcase-small.png');
INSERT INTO `icon_res_info` VALUES ('telephone--pencil.png');
INSERT INTO `icon_res_info` VALUES ('plus-shield.png');
INSERT INTO `icon_res_info` VALUES ('folder.png');
INSERT INTO `icon_res_info` VALUES ('globe-place.png');
INSERT INTO `icon_res_info` VALUES ('alarm-clock-select.png');
INSERT INTO `icon_res_info` VALUES ('glass--arrow.png');
INSERT INTO `icon_res_info` VALUES ('eye-red.png');
INSERT INTO `icon_res_info` VALUES ('edit-image-center.png');
INSERT INTO `icon_res_info` VALUES ('question-octagon-frame.png');
INSERT INTO `icon_res_info` VALUES ('navigation-000-frame.png');
INSERT INTO `icon_res_info` VALUES ('camcorder.png');
INSERT INTO `icon_res_info` VALUES ('servers-network.png');
INSERT INTO `icon_res_info` VALUES ('gear--arrow.png');
INSERT INTO `icon_res_info` VALUES ('pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('media-player-medium-purple.png');
INSERT INTO `icon_res_info` VALUES ('document-page-next.png');
INSERT INTO `icon_res_info` VALUES ('block--arrow.png');
INSERT INTO `icon_res_info` VALUES ('folder--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('present--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('computer-off.png');
INSERT INTO `icon_res_info` VALUES ('equalizer-low.png');
INSERT INTO `icon_res_info` VALUES ('arrow-branch.png');
INSERT INTO `icon_res_info` VALUES ('shield--plus.png');
INSERT INTO `icon_res_info` VALUES ('broom-code.png');
INSERT INTO `icon_res_info` VALUES ('arrow-return-180.png');
INSERT INTO `icon_res_info` VALUES ('notebook-share.png');
INSERT INTO `icon_res_info` VALUES ('arrow-045-medium.png');
INSERT INTO `icon_res_info` VALUES ('lock--pencil.png');
INSERT INTO `icon_res_info` VALUES ('sort-price-descending.png');
INSERT INTO `icon_res_info` VALUES ('table-export.png');
INSERT INTO `icon_res_info` VALUES ('paint-tube--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('umbrella--plus.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('battery--arrow.png');
INSERT INTO `icon_res_info` VALUES ('chevron-small.png');
INSERT INTO `icon_res_info` VALUES ('server.png');
INSERT INTO `icon_res_info` VALUES ('marker.png');
INSERT INTO `icon_res_info` VALUES ('edit-image.png');
INSERT INTO `icon_res_info` VALUES ('arrow-transition-270.png');
INSERT INTO `icon_res_info` VALUES ('slash-small.png');
INSERT INTO `icon_res_info` VALUES ('edit-bold.png');
INSERT INTO `icon_res_info` VALUES ('edit-underline-double.png');
INSERT INTO `icon_res_info` VALUES ('map--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-page-previous.png');
INSERT INTO `icon_res_info` VALUES ('question-small-white.png');
INSERT INTO `icon_res_info` VALUES ('arrow-return-270-left.png');
INSERT INTO `icon_res_info` VALUES ('dashboard--plus.png');
INSERT INTO `icon_res_info` VALUES ('lifebuoy.png');
INSERT INTO `icon_res_info` VALUES ('user-white.png');
INSERT INTO `icon_res_info` VALUES ('newspaper.png');
INSERT INTO `icon_res_info` VALUES ('mobile-phone.png');
INSERT INTO `icon_res_info` VALUES ('t-shirt.png');
INSERT INTO `icon_res_info` VALUES ('wand--plus.png');
INSERT INTO `icon_res_info` VALUES ('traffic-cone.png');
INSERT INTO `icon_res_info` VALUES ('stamp--plus.png');
INSERT INTO `icon_res_info` VALUES ('spectacle-3d.png');
INSERT INTO `icon_res_info` VALUES ('disc-label.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-medium.png');
INSERT INTO `icon_res_info` VALUES ('pill--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-pdf-text.png');
INSERT INTO `icon_res_info` VALUES ('geotag-document.png');
INSERT INTO `icon_res_info` VALUES ('monitor-network.png');
INSERT INTO `icon_res_info` VALUES ('service-bell--minus.png');
INSERT INTO `icon_res_info` VALUES ('globe-network.png');
INSERT INTO `icon_res_info` VALUES ('ui-slider-050.png');
INSERT INTO `icon_res_info` VALUES ('inbox--plus.png');
INSERT INTO `icon_res_info` VALUES ('script-word.png');
INSERT INTO `icon_res_info` VALUES ('bean--arrow.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-photoshop.png');
INSERT INTO `icon_res_info` VALUES ('keyboard--arrow.png');
INSERT INTO `icon_res_info` VALUES ('music-small.png');
INSERT INTO `icon_res_info` VALUES ('ui-splitter.png');
INSERT INTO `icon_res_info` VALUES ('camcorder--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('home.png');
INSERT INTO `icon_res_info` VALUES ('drive--pencil.png');
INSERT INTO `icon_res_info` VALUES ('document-block.png');
INSERT INTO `icon_res_info` VALUES ('flag--plus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-skip-180.png');
INSERT INTO `icon_res_info` VALUES ('credit-card--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('flashlight--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('control-stop-square-small.png');
INSERT INTO `icon_res_info` VALUES ('edit-scale-vertical.png');
INSERT INTO `icon_res_info` VALUES ('mail-small.png');
INSERT INTO `icon_res_info` VALUES ('eraser--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('computer--pencil.png');
INSERT INTO `icon_res_info` VALUES ('magnifier--plus.png');
INSERT INTO `icon_res_info` VALUES ('highlighter--arrow.png');
INSERT INTO `icon_res_info` VALUES ('star-half.png');
INSERT INTO `icon_res_info` VALUES ('selection-input.png');
INSERT INTO `icon_res_info` VALUES ('folders-stack.png');
INSERT INTO `icon_res_info` VALUES ('layer-shape-line.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-i.png');
INSERT INTO `icon_res_info` VALUES ('calendar-select-days.png');
INSERT INTO `icon_res_info` VALUES ('color-adjustment-green.png');
INSERT INTO `icon_res_info` VALUES ('wall--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-hf-select-footer.png');
INSERT INTO `icon_res_info` VALUES ('navigation-090-button.png');
INSERT INTO `icon_res_info` VALUES ('slides-stack.png');
INSERT INTO `icon_res_info` VALUES ('zones-stack.png');
INSERT INTO `icon_res_info` VALUES ('calculator--arrow.png');
INSERT INTO `icon_res_info` VALUES ('monitor--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-zoom-actual-equal.png');
INSERT INTO `icon_res_info` VALUES ('document-music-playlist.png');
INSERT INTO `icon_res_info` VALUES ('task-select.png');
INSERT INTO `icon_res_info` VALUES ('point.png');
INSERT INTO `icon_res_info` VALUES ('opml-balloon.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-i.png');
INSERT INTO `icon_res_info` VALUES ('network-status-busy.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-break.png');
INSERT INTO `icon_res_info` VALUES ('application-text.png');
INSERT INTO `icon_res_info` VALUES ('chart--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('store--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('edit-alignment-right.png');
INSERT INTO `icon_res_info` VALUES ('safe.png');
INSERT INTO `icon_res_info` VALUES ('document-music.png');
INSERT INTO `icon_res_info` VALUES ('magnet--minus.png');
INSERT INTO `icon_res_info` VALUES ('screwdriver--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-folders-stack.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-sign-out.png');
INSERT INTO `icon_res_info` VALUES ('document-php.png');
INSERT INTO `icon_res_info` VALUES ('robot.png');
INSERT INTO `icon_res_info` VALUES ('point-small.png');
INSERT INTO `icon_res_info` VALUES ('mail-forward.png');
INSERT INTO `icon_res_info` VALUES ('bamboos.png');
INSERT INTO `icon_res_info` VALUES ('ui-slider-vertical-050.png');
INSERT INTO `icon_res_info` VALUES ('image-small-sunset.png');
INSERT INTO `icon_res_info` VALUES ('bug--arrow.png');
INSERT INTO `icon_res_info` VALUES ('ui-toolbar--plus.png');
INSERT INTO `icon_res_info` VALUES ('sql-join-right-exclude.png');
INSERT INTO `icon_res_info` VALUES ('puzzle--pencil.png');
INSERT INTO `icon_res_info` VALUES ('edit-shade.png');
INSERT INTO `icon_res_info` VALUES ('alarm-clock--plus.png');
INSERT INTO `icon_res_info` VALUES ('mail-open.png');
INSERT INTO `icon_res_info` VALUES ('socket--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-import.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-view-thumbnail.png');
INSERT INTO `icon_res_info` VALUES ('table-select-row.png');
INSERT INTO `icon_res_info` VALUES ('external-small.png');
INSERT INTO `icon_res_info` VALUES ('border-horizontal-all.png');
INSERT INTO `icon_res_info` VALUES ('alarm-clock-blue.png');
INSERT INTO `icon_res_info` VALUES ('direction--minus.png');
INSERT INTO `icon_res_info` VALUES ('target.png');
INSERT INTO `icon_res_info` VALUES ('drawer--plus.png');
INSERT INTO `icon_res_info` VALUES ('switch.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle-double-135.png');
INSERT INTO `icon_res_info` VALUES ('card--pencil.png');
INSERT INTO `icon_res_info` VALUES ('plus-small.png');
INSERT INTO `icon_res_info` VALUES ('wall-brick.png');
INSERT INTO `icon_res_info` VALUES ('point--minus.png');
INSERT INTO `icon_res_info` VALUES ('images-stack.png');
INSERT INTO `icon_res_info` VALUES ('media-player-small.png');
INSERT INTO `icon_res_info` VALUES ('edit-alignment-center.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-zoom.png');
INSERT INTO `icon_res_info` VALUES ('speaker.png');
INSERT INTO `icon_res_info` VALUES ('home-small.png');
INSERT INTO `icon_res_info` VALUES ('cassette.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-small-list.png');
INSERT INTO `icon_res_info` VALUES ('camera-black.png');
INSERT INTO `icon_res_info` VALUES ('clapperboard--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('application-table.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-pdf.png');
INSERT INTO `icon_res_info` VALUES ('edit-padding.png');
INSERT INTO `icon_res_info` VALUES ('box--minus.png');
INSERT INTO `icon_res_info` VALUES ('spectacle-small.png');
INSERT INTO `icon_res_info` VALUES ('game.png');
INSERT INTO `icon_res_info` VALUES ('hammer-left.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-medium-left.png');
INSERT INTO `icon_res_info` VALUES ('keyboard--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('newspaper--arrow.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-s.png');
INSERT INTO `icon_res_info` VALUES ('user-nude.png');
INSERT INTO `icon_res_info` VALUES ('leaf--plus.png');
INSERT INTO `icon_res_info` VALUES ('report-paper.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-php.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-separator-label.png');
INSERT INTO `icon_res_info` VALUES ('wallet--arrow.png');
INSERT INTO `icon_res_info` VALUES ('monitor--arrow.png');
INSERT INTO `icon_res_info` VALUES ('guitar--minus.png');
INSERT INTO `icon_res_info` VALUES ('bandaid.png');
INSERT INTO `icon_res_info` VALUES ('arrow-branch-000-left.png');
INSERT INTO `icon_res_info` VALUES ('fingerprint-recognition.png');
INSERT INTO `icon_res_info` VALUES ('microphone--arrow.png');
INSERT INTO `icon_res_info` VALUES ('document-resize-actual.png');
INSERT INTO `icon_res_info` VALUES ('pencil-ruler.png');
INSERT INTO `icon_res_info` VALUES ('ui-breadcrumb.png');
INSERT INTO `icon_res_info` VALUES ('report-excel.png');
INSERT INTO `icon_res_info` VALUES ('box-search-result.png');
INSERT INTO `icon_res_info` VALUES ('cookie-chocolate.png');
INSERT INTO `icon_res_info` VALUES ('quill.png');
INSERT INTO `icon_res_info` VALUES ('flag-small.png');
INSERT INTO `icon_res_info` VALUES ('question-octagon.png');
INSERT INTO `icon_res_info` VALUES ('disk--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('sort-rating-descending.png');
INSERT INTO `icon_res_info` VALUES ('paint-brush-small.png');
INSERT INTO `icon_res_info` VALUES ('desktop.png');
INSERT INTO `icon_res_info` VALUES ('cross-circle-frame.png');
INSERT INTO `icon_res_info` VALUES ('metronome--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('disc-blue.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-film.png');
INSERT INTO `icon_res_info` VALUES ('media-player--minus.png');
INSERT INTO `icon_res_info` VALUES ('media-player-xsmall.png');
INSERT INTO `icon_res_info` VALUES ('scissors--arrow.png');
INSERT INTO `icon_res_info` VALUES ('disk-return-black.png');
INSERT INTO `icon_res_info` VALUES ('document-hf-delete.png');
INSERT INTO `icon_res_info` VALUES ('information-balloon.png');
INSERT INTO `icon_res_info` VALUES ('table-split-row.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-tree.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-shield.png');
INSERT INTO `icon_res_info` VALUES ('dummy.png');
INSERT INTO `icon_res_info` VALUES ('balloon-small-left.png');
INSERT INTO `icon_res_info` VALUES ('sql-join-left-exclude.png');
INSERT INTO `icon_res_info` VALUES ('disk--pencil.png');
INSERT INTO `icon_res_info` VALUES ('pin-small.png');
INSERT INTO `icon_res_info` VALUES ('map-share.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-d.png');
INSERT INTO `icon_res_info` VALUES ('slide.png');
INSERT INTO `icon_res_info` VALUES ('user-medical.png');
INSERT INTO `icon_res_info` VALUES ('weather-lightning.png');
INSERT INTO `icon_res_info` VALUES ('layout-select-sidebar.png');
INSERT INTO `icon_res_info` VALUES ('mail-receive.png');
INSERT INTO `icon_res_info` VALUES ('folder-open-slide.png');
INSERT INTO `icon_res_info` VALUES ('smiley-wink.png');
INSERT INTO `icon_res_info` VALUES ('truck--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('edit-heading.png');
INSERT INTO `icon_res_info` VALUES ('usb-flash-drive--arrow.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open-table.png');
INSERT INTO `icon_res_info` VALUES ('fill-180.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-task.png');
INSERT INTO `icon_res_info` VALUES ('ui-address-bar-yellow.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-z.png');
INSERT INTO `icon_res_info` VALUES ('cushion-gray.png');
INSERT INTO `icon_res_info` VALUES ('auction-hammer--pencil.png');
INSERT INTO `icon_res_info` VALUES ('battery--minus.png');
INSERT INTO `icon_res_info` VALUES ('glass--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('television--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-snippet.png');
INSERT INTO `icon_res_info` VALUES ('counter-stop.png');
INSERT INTO `icon_res_info` VALUES ('border-bottom-thick.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-small-white.png');
INSERT INTO `icon_res_info` VALUES ('money-medium.png');
INSERT INTO `icon_res_info` VALUES ('projection-screen--plus.png');
INSERT INTO `icon_res_info` VALUES ('layout-header-2.png');
INSERT INTO `icon_res_info` VALUES ('safe--arrow.png');
INSERT INTO `icon_res_info` VALUES ('auction-hammer--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-list-box.png');
INSERT INTO `icon_res_info` VALUES ('film.png');
INSERT INTO `icon_res_info` VALUES ('printer-medium.png');
INSERT INTO `icon_res_info` VALUES ('globe-network-ethernet.png');
INSERT INTO `icon_res_info` VALUES ('palette--arrow.png');
INSERT INTO `icon_res_info` VALUES ('gradient-small.png');
INSERT INTO `icon_res_info` VALUES ('inbox-upload.png');
INSERT INTO `icon_res_info` VALUES ('control-eject-small.png');
INSERT INTO `icon_res_info` VALUES ('navigation-270-white.png');
INSERT INTO `icon_res_info` VALUES ('building--arrow.png');
INSERT INTO `icon_res_info` VALUES ('pda-off.png');
INSERT INTO `icon_res_info` VALUES ('water--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('shield--pencil.png');
INSERT INTO `icon_res_info` VALUES ('layer-rotate.png');
INSERT INTO `icon_res_info` VALUES ('camera-small-black.png');
INSERT INTO `icon_res_info` VALUES ('media-player-xsmall-green.png');
INSERT INTO `icon_res_info` VALUES ('speaker-volume.png');
INSERT INTO `icon_res_info` VALUES ('puzzle--arrow.png');
INSERT INTO `icon_res_info` VALUES ('computer.png');
INSERT INTO `icon_res_info` VALUES ('ghost.png');
INSERT INTO `icon_res_info` VALUES ('application-resize-full.png');
INSERT INTO `icon_res_info` VALUES ('ruler--minus.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder--arrow.png');
INSERT INTO `icon_res_info` VALUES ('sql.png');
INSERT INTO `icon_res_info` VALUES ('document-excel.png');
INSERT INTO `icon_res_info` VALUES ('speaker-volume-control-mute.png');
INSERT INTO `icon_res_info` VALUES ('weather-moon-fog.png');
INSERT INTO `icon_res_info` VALUES ('trophy--arrow.png');
INSERT INTO `icon_res_info` VALUES ('edit-uppercase.png');
INSERT INTO `icon_res_info` VALUES ('eye--arrow.png');
INSERT INTO `icon_res_info` VALUES ('picture-medium.png');
INSERT INTO `icon_res_info` VALUES ('shopping-basket.png');
INSERT INTO `icon_res_info` VALUES ('ui-toolbar-bookmark.png');
INSERT INTO `icon_res_info` VALUES ('user-white-female.png');
INSERT INTO `icon_res_info` VALUES ('ui-menu-blue.png');
INSERT INTO `icon_res_info` VALUES ('beans.png');
INSERT INTO `icon_res_info` VALUES ('blog-blue.png');
INSERT INTO `icon_res_info` VALUES ('eye--pencil.png');
INSERT INTO `icon_res_info` VALUES ('user-small.png');
INSERT INTO `icon_res_info` VALUES ('notebook--pencil.png');
INSERT INTO `icon_res_info` VALUES ('direction--plus.png');
INSERT INTO `icon_res_info` VALUES ('home-share.png');
INSERT INTO `icon_res_info` VALUES ('ui-tab-side.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle-225.png');
INSERT INTO `icon_res_info` VALUES ('bookmark--arrow.png');
INSERT INTO `icon_res_info` VALUES ('node-select-all.png');
INSERT INTO `icon_res_info` VALUES ('inbox--minus.png');
INSERT INTO `icon_res_info` VALUES ('music.png');
INSERT INTO `icon_res_info` VALUES ('heart.png');
INSERT INTO `icon_res_info` VALUES ('application-detail.png');
INSERT INTO `icon_res_info` VALUES ('paint-brush--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('inbox-document.png');
INSERT INTO `icon_res_info` VALUES ('star-small.png');
INSERT INTO `icon_res_info` VALUES ('bluetooth.png');
INSERT INTO `icon_res_info` VALUES ('quill--plus.png');
INSERT INTO `icon_res_info` VALUES ('ui-check-boxes-series.png');
INSERT INTO `icon_res_info` VALUES ('mahjong--minus.png');
INSERT INTO `icon_res_info` VALUES ('gradient.png');
INSERT INTO `icon_res_info` VALUES ('edit-italic.png');
INSERT INTO `icon_res_info` VALUES ('bean-small-green.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-music-playlist.png');
INSERT INTO `icon_res_info` VALUES ('balloon--minus.png');
INSERT INTO `icon_res_info` VALUES ('traffic-light--arrow.png');
INSERT INTO `icon_res_info` VALUES ('stamp-pattern.png');
INSERT INTO `icon_res_info` VALUES ('disk-black.png');
INSERT INTO `icon_res_info` VALUES ('flag--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('sitemap-application-blue.png');
INSERT INTO `icon_res_info` VALUES ('application-dialog.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-visual-studio.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-export.png');
INSERT INTO `icon_res_info` VALUES ('feed-document.png');
INSERT INTO `icon_res_info` VALUES ('balance.png');
INSERT INTO `icon_res_info` VALUES ('smiley-roll-blue.png');
INSERT INTO `icon_res_info` VALUES ('folder-import.png');
INSERT INTO `icon_res_info` VALUES ('inbox--arrow.png');
INSERT INTO `icon_res_info` VALUES ('layer-shape-polyline.png');
INSERT INTO `icon_res_info` VALUES ('cursor.png');
INSERT INTO `icon_res_info` VALUES ('cards-stack.png');
INSERT INTO `icon_res_info` VALUES ('status-busy.png');
INSERT INTO `icon_res_info` VALUES ('layout-join-vertical.png');
INSERT INTO `icon_res_info` VALUES ('document-pdf-text.png');
INSERT INTO `icon_res_info` VALUES ('flashlight--arrow.png');
INSERT INTO `icon_res_info` VALUES ('television--pencil.png');
INSERT INTO `icon_res_info` VALUES ('bookmark-small.png');
INSERT INTO `icon_res_info` VALUES ('report.png');
INSERT INTO `icon_res_info` VALUES ('desktop-network.png');
INSERT INTO `icon_res_info` VALUES ('tick-button.png');
INSERT INTO `icon_res_info` VALUES ('block--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('jar--plus.png');
INSERT INTO `icon_res_info` VALUES ('magnifier--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('cross-button.png');
INSERT INTO `icon_res_info` VALUES ('chair--arrow.png');
INSERT INTO `icon_res_info` VALUES ('chair--pencil.png');
INSERT INTO `icon_res_info` VALUES ('paint-tube--plus.png');
INSERT INTO `icon_res_info` VALUES ('edit-writing-mode.png');
INSERT INTO `icon_res_info` VALUES ('sofa--arrow.png');
INSERT INTO `icon_res_info` VALUES ('crown--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('media-player--arrow.png');
INSERT INTO `icon_res_info` VALUES ('films.png');
INSERT INTO `icon_res_info` VALUES ('application-resize-actual.png');
INSERT INTO `icon_res_info` VALUES ('control-stop-270-small.png');
INSERT INTO `icon_res_info` VALUES ('map-pin.png');
INSERT INTO `icon_res_info` VALUES ('megaphone--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('drive-network.png');
INSERT INTO `icon_res_info` VALUES ('box-label.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb--pencil.png');
INSERT INTO `icon_res_info` VALUES ('document-code.png');
INSERT INTO `icon_res_info` VALUES ('plug-connect.png');
INSERT INTO `icon_res_info` VALUES ('magnet--arrow.png');
INSERT INTO `icon_res_info` VALUES ('application-split-vertical.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve-090.png');
INSERT INTO `icon_res_info` VALUES ('film-small.png');
INSERT INTO `icon_res_info` VALUES ('table-select-column.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-shred.png');
INSERT INTO `icon_res_info` VALUES ('geotag.png');
INSERT INTO `icon_res_info` VALUES ('arrow-switch.png');
INSERT INTO `icon_res_info` VALUES ('ticket-small.png');
INSERT INTO `icon_res_info` VALUES ('media-player--plus.png');
INSERT INTO `icon_res_info` VALUES ('balloon-facebook-left.png');
INSERT INTO `icon_res_info` VALUES ('pill--minus.png');
INSERT INTO `icon_res_info` VALUES ('report--minus.png');
INSERT INTO `icon_res_info` VALUES ('inbox-download.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-hf-delete-footer.png');
INSERT INTO `icon_res_info` VALUES ('slide-powerpoint.png');
INSERT INTO `icon_res_info` VALUES ('mahjong.png');
INSERT INTO `icon_res_info` VALUES ('flag-yellow.png');
INSERT INTO `icon_res_info` VALUES ('image--arrow.png');
INSERT INTO `icon_res_info` VALUES ('headphone--arrow.png');
INSERT INTO `icon_res_info` VALUES ('pill--pencil.png');
INSERT INTO `icon_res_info` VALUES ('block.png');
INSERT INTO `icon_res_info` VALUES ('color--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('paper-bag--plus.png');
INSERT INTO `icon_res_info` VALUES ('notebook-sticky-note.png');
INSERT INTO `icon_res_info` VALUES ('speaker-volume-control-up.png');
INSERT INTO `icon_res_info` VALUES ('receipt-import.png');
INSERT INTO `icon_res_info` VALUES ('document-page-previous.png');
INSERT INTO `icon_res_info` VALUES ('shield--arrow.png');
INSERT INTO `icon_res_info` VALUES ('system-monitor--minus.png');
INSERT INTO `icon_res_info` VALUES ('system-monitor.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-c.png');
INSERT INTO `icon_res_info` VALUES ('edit-code.png');
INSERT INTO `icon_res_info` VALUES ('fire--plus.png');
INSERT INTO `icon_res_info` VALUES ('mobile-phone--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('mobile-phone--arrow.png');
INSERT INTO `icon_res_info` VALUES ('task--pencil.png');
INSERT INTO `icon_res_info` VALUES ('image-blur.png');
INSERT INTO `icon_res_info` VALUES ('scanner--pencil.png');
INSERT INTO `icon_res_info` VALUES ('balance--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('binocular-small.png');
INSERT INTO `icon_res_info` VALUES ('camcorder--plus.png');
INSERT INTO `icon_res_info` VALUES ('layers-small.png');
INSERT INTO `icon_res_info` VALUES ('receipt--plus.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-bar.png');
INSERT INTO `icon_res_info` VALUES ('document-horizontal-text.png');
INSERT INTO `icon_res_info` VALUES ('toolbox--pencil.png');
INSERT INTO `icon_res_info` VALUES ('language-small.png');
INSERT INTO `icon_res_info` VALUES ('user--arrow.png');
INSERT INTO `icon_res_info` VALUES ('monitor-wallpaper.png');
INSERT INTO `icon_res_info` VALUES ('application--minus.png');
INSERT INTO `icon_res_info` VALUES ('folder-network.png');
INSERT INTO `icon_res_info` VALUES ('speaker--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle-double.png');
INSERT INTO `icon_res_info` VALUES ('ticket--minus.png');
INSERT INTO `icon_res_info` VALUES ('auction-hammer--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('bean--plus.png');
INSERT INTO `icon_res_info` VALUES ('flag.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-m.png');
INSERT INTO `icon_res_info` VALUES ('arrow-turn-090-left.png');
INSERT INTO `icon_res_info` VALUES ('terminal-network.png');
INSERT INTO `icon_res_info` VALUES ('screwdriver.png');
INSERT INTO `icon_res_info` VALUES ('smiley-money.png');
INSERT INTO `icon_res_info` VALUES ('cigarette.png');
INSERT INTO `icon_res_info` VALUES ('marker--plus.png');
INSERT INTO `icon_res_info` VALUES ('category.png');
INSERT INTO `icon_res_info` VALUES ('receipt.png');
INSERT INTO `icon_res_info` VALUES ('image-cast.png');
INSERT INTO `icon_res_info` VALUES ('drawer.png');
INSERT INTO `icon_res_info` VALUES ('smiley-eek-blue.png');
INSERT INTO `icon_res_info` VALUES ('dummy-small.png');
INSERT INTO `icon_res_info` VALUES ('bank--arrow.png');
INSERT INTO `icon_res_info` VALUES ('application-wave.png');
INSERT INTO `icon_res_info` VALUES ('json.png');
INSERT INTO `icon_res_info` VALUES ('shortcut.png');
INSERT INTO `icon_res_info` VALUES ('books-stack.png');
INSERT INTO `icon_res_info` VALUES ('images.png');
INSERT INTO `icon_res_info` VALUES ('plug-disconnect-slash.png');
INSERT INTO `icon_res_info` VALUES ('tick-shield.png');
INSERT INTO `icon_res_info` VALUES ('mouse--plus.png');
INSERT INTO `icon_res_info` VALUES ('clapperboard.png');
INSERT INTO `icon_res_info` VALUES ('smiley-surprise.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve-270.png');
INSERT INTO `icon_res_info` VALUES ('blue-folders.png');
INSERT INTO `icon_res_info` VALUES ('network-hub.png');
INSERT INTO `icon_res_info` VALUES ('inbox-document-music-playlist.png');
INSERT INTO `icon_res_info` VALUES ('control-pause-record-small.png');
INSERT INTO `icon_res_info` VALUES ('navigation-180-white.png');
INSERT INTO `icon_res_info` VALUES ('edit-letter-spacing.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve-090-left.png');
INSERT INTO `icon_res_info` VALUES ('telephone.png');
INSERT INTO `icon_res_info` VALUES ('receipt-excel.png');
INSERT INTO `icon_res_info` VALUES ('document-film.png');
INSERT INTO `icon_res_info` VALUES ('smiley-roll-sweat.png');
INSERT INTO `icon_res_info` VALUES ('arrow-branch-090.png');
INSERT INTO `icon_res_info` VALUES ('notebook-medium.png');
INSERT INTO `icon_res_info` VALUES ('printer-empty.png');
INSERT INTO `icon_res_info` VALUES ('user-nude-female.png');
INSERT INTO `icon_res_info` VALUES ('card--arrow.png');
INSERT INTO `icon_res_info` VALUES ('toolbox--minus.png');
INSERT INTO `icon_res_info` VALUES ('point-bronze.png');
INSERT INTO `icon_res_info` VALUES ('document-resize.png');
INSERT INTO `icon_res_info` VALUES ('tags-label.png');
INSERT INTO `icon_res_info` VALUES ('key--pencil.png');
INSERT INTO `icon_res_info` VALUES ('arrow-180-medium.png');
INSERT INTO `icon_res_info` VALUES ('control-270.png');
INSERT INTO `icon_res_info` VALUES ('edit-alignment-justify-distribute.png');
INSERT INTO `icon_res_info` VALUES ('flask--plus.png');
INSERT INTO `icon_res_info` VALUES ('zone--pencil.png');
INSERT INTO `icon_res_info` VALUES ('tick-white.png');
INSERT INTO `icon_res_info` VALUES ('star--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-s.png');
INSERT INTO `icon_res_info` VALUES ('lock.png');
INSERT INTO `icon_res_info` VALUES ('control-skip-000-small.png');
INSERT INTO `icon_res_info` VALUES ('calendar-small.png');
INSERT INTO `icon_res_info` VALUES ('chart-pie-separate.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-m.png');
INSERT INTO `icon_res_info` VALUES ('balloon-quotation.png');
INSERT INTO `icon_res_info` VALUES ('tables-stacks.png');
INSERT INTO `icon_res_info` VALUES ('notebook--plus.png');
INSERT INTO `icon_res_info` VALUES ('wand--pencil.png');
INSERT INTO `icon_res_info` VALUES ('folder-open-film.png');
INSERT INTO `icon_res_info` VALUES ('ui-tooltip--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-tab--pencil.png');
INSERT INTO `icon_res_info` VALUES ('property-import.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-field-password.png');
INSERT INTO `icon_res_info` VALUES ('thumb-up.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-horizontal-text.png');
INSERT INTO `icon_res_info` VALUES ('document-pdf.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-f.png');
INSERT INTO `icon_res_info` VALUES ('arrow-180.png');
INSERT INTO `icon_res_info` VALUES ('bell.png');
INSERT INTO `icon_res_info` VALUES ('compass--plus.png');
INSERT INTO `icon_res_info` VALUES ('property-export.png');
INSERT INTO `icon_res_info` VALUES ('media-player-xsmall-black.png');
INSERT INTO `icon_res_info` VALUES ('layout-hf.png');
INSERT INTO `icon_res_info` VALUES ('cup--pencil.png');
INSERT INTO `icon_res_info` VALUES ('balloon--arrow.png');
INSERT INTO `icon_res_info` VALUES ('paper-plane-return.png');
INSERT INTO `icon_res_info` VALUES ('blue-document--arrow.png');
INSERT INTO `icon_res_info` VALUES ('paper-clip-small.png');
INSERT INTO `icon_res_info` VALUES ('geotag-small.png');
INSERT INTO `icon_res_info` VALUES ('function.png');
INSERT INTO `icon_res_info` VALUES ('sort-rating.png');
INSERT INTO `icon_res_info` VALUES ('wallet--plus.png');
INSERT INTO `icon_res_info` VALUES ('mouse-select.png');
INSERT INTO `icon_res_info` VALUES ('card-medium.png');
INSERT INTO `icon_res_info` VALUES ('picture--plus.png');
INSERT INTO `icon_res_info` VALUES ('bug--plus.png');
INSERT INTO `icon_res_info` VALUES ('fruit-grape.png');
INSERT INTO `icon_res_info` VALUES ('bell-small.png');
INSERT INTO `icon_res_info` VALUES ('trophy--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('border-vertical.png');
INSERT INTO `icon_res_info` VALUES ('flask--arrow.png');
INSERT INTO `icon_res_info` VALUES ('drive-share.png');
INSERT INTO `icon_res_info` VALUES ('scripts.png');
INSERT INTO `icon_res_info` VALUES ('smiley-zipper.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-export.png');
INSERT INTO `icon_res_info` VALUES ('layer--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('ui-button-navigation-back.png');
INSERT INTO `icon_res_info` VALUES ('fill.png');
INSERT INTO `icon_res_info` VALUES ('ui-labels.png');
INSERT INTO `icon_res_info` VALUES ('battery.png');
INSERT INTO `icon_res_info` VALUES ('monitor--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-radio-button.png');
INSERT INTO `icon_res_info` VALUES ('wand--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('traffic-light-off.png');
INSERT INTO `icon_res_info` VALUES ('control-power-small.png');
INSERT INTO `icon_res_info` VALUES ('tag-import.png');
INSERT INTO `icon_res_info` VALUES ('layout-split-vertical.png');
INSERT INTO `icon_res_info` VALUES ('magnet-blue.png');
INSERT INTO `icon_res_info` VALUES ('receipt--arrow.png');
INSERT INTO `icon_res_info` VALUES ('address-book--pencil.png');
INSERT INTO `icon_res_info` VALUES ('direction--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('spectacle-sunglass.png');
INSERT INTO `icon_res_info` VALUES ('projection-screen.png');
INSERT INTO `icon_res_info` VALUES ('car--arrow.png');
INSERT INTO `icon_res_info` VALUES ('edit-symbol.png');
INSERT INTO `icon_res_info` VALUES ('alarm-clock--arrow.png');
INSERT INTO `icon_res_info` VALUES ('picture.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-q.png');
INSERT INTO `icon_res_info` VALUES ('balloon-twitter.png');
INSERT INTO `icon_res_info` VALUES ('layer-shade.png');
INSERT INTO `icon_res_info` VALUES ('edit-percent.png');
INSERT INTO `icon_res_info` VALUES ('bank--minus.png');
INSERT INTO `icon_res_info` VALUES ('layers-stack-arrange-back.png');
INSERT INTO `icon_res_info` VALUES ('bin-metal-full.png');
INSERT INTO `icon_res_info` VALUES ('image.png');
INSERT INTO `icon_res_info` VALUES ('gender-female.png');
INSERT INTO `icon_res_info` VALUES ('application-task.png');
INSERT INTO `icon_res_info` VALUES ('tag--arrow.png');
INSERT INTO `icon_res_info` VALUES ('application-split-tile.png');
INSERT INTO `icon_res_info` VALUES ('building-network.png');
INSERT INTO `icon_res_info` VALUES ('disc--minus.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-small.png');
INSERT INTO `icon_res_info` VALUES ('universal.png');
INSERT INTO `icon_res_info` VALUES ('ruler-triangle.png');
INSERT INTO `icon_res_info` VALUES ('notebooks.png');
INSERT INTO `icon_res_info` VALUES ('present--plus.png');
INSERT INTO `icon_res_info` VALUES ('monitor-cast.png');
INSERT INTO `icon_res_info` VALUES ('box--arrow.png');
INSERT INTO `icon_res_info` VALUES ('picture-small.png');
INSERT INTO `icon_res_info` VALUES ('validation-valid.png');
INSERT INTO `icon_res_info` VALUES ('calendar--plus.png');
INSERT INTO `icon_res_info` VALUES ('store-medium.png');
INSERT INTO `icon_res_info` VALUES ('hammer--plus.png');
INSERT INTO `icon_res_info` VALUES ('smiley-grin.png');
INSERT INTO `icon_res_info` VALUES ('cup--plus.png');
INSERT INTO `icon_res_info` VALUES ('disc-share.png');
INSERT INTO `icon_res_info` VALUES ('luggage--arrow.png');
INSERT INTO `icon_res_info` VALUES ('heart--minus.png');
INSERT INTO `icon_res_info` VALUES ('report--arrow.png');
INSERT INTO `icon_res_info` VALUES ('edit-drop-cap.png');
INSERT INTO `icon_res_info` VALUES ('fruit.png');
INSERT INTO `icon_res_info` VALUES ('sticky-notes-text.png');
INSERT INTO `icon_res_info` VALUES ('clapperboard--plus.png');
INSERT INTO `icon_res_info` VALUES ('calendar-month.png');
INSERT INTO `icon_res_info` VALUES ('minus-octagon.png');
INSERT INTO `icon_res_info` VALUES ('cutter--pencil.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve-000-left.png');
INSERT INTO `icon_res_info` VALUES ('media-player-medium-orange.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-text-image.png');
INSERT INTO `icon_res_info` VALUES ('currency-ruble.png');
INSERT INTO `icon_res_info` VALUES ('service-bell--pencil.png');
INSERT INTO `icon_res_info` VALUES ('bin.png');
INSERT INTO `icon_res_info` VALUES ('table-split.png');
INSERT INTO `icon_res_info` VALUES ('system-monitor--pencil.png');
INSERT INTO `icon_res_info` VALUES ('document-share.png');
INSERT INTO `icon_res_info` VALUES ('user--pencil.png');
INSERT INTO `icon_res_info` VALUES ('plus-white.png');
INSERT INTO `icon_res_info` VALUES ('paint-tube--pencil.png');
INSERT INTO `icon_res_info` VALUES ('slide--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('brightness.png');
INSERT INTO `icon_res_info` VALUES ('chevron.png');
INSERT INTO `icon_res_info` VALUES ('system-monitor--plus.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-p.png');
INSERT INTO `icon_res_info` VALUES ('television-off.png');
INSERT INTO `icon_res_info` VALUES ('money-coin.png');
INSERT INTO `icon_res_info` VALUES ('document-hf-delete-footer.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-field-password-yellow.png');
INSERT INTO `icon_res_info` VALUES ('star--plus.png');
INSERT INTO `icon_res_info` VALUES ('image-medium.png');
INSERT INTO `icon_res_info` VALUES ('building-old.png');
INSERT INTO `icon_res_info` VALUES ('opml-document.png');
INSERT INTO `icon_res_info` VALUES ('calendar-list.png');
INSERT INTO `icon_res_info` VALUES ('key--plus.png');
INSERT INTO `icon_res_info` VALUES ('layout-3-mix.png');
INSERT INTO `icon_res_info` VALUES ('bookmark-import.png');
INSERT INTO `icon_res_info` VALUES ('navigation-180.png');
INSERT INTO `icon_res_info` VALUES ('report--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('application-rename.png');
INSERT INTO `icon_res_info` VALUES ('tag-label-gray.png');
INSERT INTO `icon_res_info` VALUES ('user-yellow-female.png');
INSERT INTO `icon_res_info` VALUES ('disks.png');
INSERT INTO `icon_res_info` VALUES ('mouse-select-wheel.png');
INSERT INTO `icon_res_info` VALUES ('validation-invalid.png');
INSERT INTO `icon_res_info` VALUES ('tags.png');
INSERT INTO `icon_res_info` VALUES ('hard-hat--arrow.png');
INSERT INTO `icon_res_info` VALUES ('store-network.png');
INSERT INTO `icon_res_info` VALUES ('table-sheet.png');
INSERT INTO `icon_res_info` VALUES ('wrench--minus.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-r.png');
INSERT INTO `icon_res_info` VALUES ('ui-address-bar-red.png');
INSERT INTO `icon_res_info` VALUES ('terminal--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-090-medium.png');
INSERT INTO `icon_res_info` VALUES ('tables-relation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-return-000-left.png');
INSERT INTO `icon_res_info` VALUES ('metronome--minus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-225-medium.png');
INSERT INTO `icon_res_info` VALUES ('cross-octagon.png');
INSERT INTO `icon_res_info` VALUES ('paint-can-color.png');
INSERT INTO `icon_res_info` VALUES ('mouse--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-flash.png');
INSERT INTO `icon_res_info` VALUES ('chain--plus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-split-180.png');
INSERT INTO `icon_res_info` VALUES ('pipette.png');
INSERT INTO `icon_res_info` VALUES ('clock-history.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-b.png');
INSERT INTO `icon_res_info` VALUES ('point-silver.png');
INSERT INTO `icon_res_info` VALUES ('sticky-notes-stack.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-block.png');
INSERT INTO `icon_res_info` VALUES ('eraser-small.png');
INSERT INTO `icon_res_info` VALUES ('arrow-join.png');
INSERT INTO `icon_res_info` VALUES ('piano--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('mouse.png');
INSERT INTO `icon_res_info` VALUES ('document-word-text.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-hf.png');
INSERT INTO `icon_res_info` VALUES ('document-word.png');
INSERT INTO `icon_res_info` VALUES ('heart-empty.png');
INSERT INTO `icon_res_info` VALUES ('information-button.png');
INSERT INTO `icon_res_info` VALUES ('home--plus.png');
INSERT INTO `icon_res_info` VALUES ('plus-octagon.png');
INSERT INTO `icon_res_info` VALUES ('arrow-turn-090.png');
INSERT INTO `icon_res_info` VALUES ('wrench-screwdriver.png');
INSERT INTO `icon_res_info` VALUES ('media-player-medium-black.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-sign.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-t.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-u.png');
INSERT INTO `icon_res_info` VALUES ('switch-medium.png');
INSERT INTO `icon_res_info` VALUES ('applications-blue.png');
INSERT INTO `icon_res_info` VALUES ('node-select-previous.png');
INSERT INTO `icon_res_info` VALUES ('inbox-film.png');
INSERT INTO `icon_res_info` VALUES ('ui-menu.png');
INSERT INTO `icon_res_info` VALUES ('table-insert.png');
INSERT INTO `icon_res_info` VALUES ('photo-album.png');
INSERT INTO `icon_res_info` VALUES ('media-player-xsmall-pink.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-network.png');
INSERT INTO `icon_res_info` VALUES ('hammer-screwdriver.png');
INSERT INTO `icon_res_info` VALUES ('envelope--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('ice--minus.png');
INSERT INTO `icon_res_info` VALUES ('edit-direction.png');
INSERT INTO `icon_res_info` VALUES ('binocular--plus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-continue-000-top.png');
INSERT INTO `icon_res_info` VALUES ('dashboard--pencil.png');
INSERT INTO `icon_res_info` VALUES ('image--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('wallet--minus.png');
INSERT INTO `icon_res_info` VALUES ('system-monitor--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('report-image.png');
INSERT INTO `icon_res_info` VALUES ('truck--arrow.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-b.png');
INSERT INTO `icon_res_info` VALUES ('control-eject.png');
INSERT INTO `icon_res_info` VALUES ('battery--plus.png');
INSERT INTO `icon_res_info` VALUES ('box-small.png');
INSERT INTO `icon_res_info` VALUES ('database--plus.png');
INSERT INTO `icon_res_info` VALUES ('question-frame.png');
INSERT INTO `icon_res_info` VALUES ('weather-rain.png');
INSERT INTO `icon_res_info` VALUES ('open-share-balloon.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-bookmark.png');
INSERT INTO `icon_res_info` VALUES ('calendar-import.png');
INSERT INTO `icon_res_info` VALUES ('image-vertical.png');
INSERT INTO `icon_res_info` VALUES ('wooden-box-label.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-paste-image.png');
INSERT INTO `icon_res_info` VALUES ('price-tag.png');
INSERT INTO `icon_res_info` VALUES ('arrow-switch-270.png');
INSERT INTO `icon_res_info` VALUES ('wooden-box--plus.png');
INSERT INTO `icon_res_info` VALUES ('hard-hat--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-hf-insert.png');
INSERT INTO `icon_res_info` VALUES ('cutlery-fork.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-zipper.png');
INSERT INTO `icon_res_info` VALUES ('media-player-medium-blue.png');
INSERT INTO `icon_res_info` VALUES ('toggle.png');
INSERT INTO `icon_res_info` VALUES ('ui-radio-button-uncheck.png');
INSERT INTO `icon_res_info` VALUES ('photo-album--plus.png');
INSERT INTO `icon_res_info` VALUES ('cutter--minus.png');
INSERT INTO `icon_res_info` VALUES ('balloon-ellipsis.png');
INSERT INTO `icon_res_info` VALUES ('heart--pencil.png');
INSERT INTO `icon_res_info` VALUES ('headphone--minus.png');
INSERT INTO `icon_res_info` VALUES ('balloon-buzz.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-tag.png');
INSERT INTO `icon_res_info` VALUES ('flask--minus.png');
INSERT INTO `icon_res_info` VALUES ('postage-stamp--minus.png');
INSERT INTO `icon_res_info` VALUES ('hammer--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('document-office-text.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-area.png');
INSERT INTO `icon_res_info` VALUES ('lightning--arrow.png');
INSERT INTO `icon_res_info` VALUES ('application-image.png');
INSERT INTO `icon_res_info` VALUES ('xfn-friend.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-red.png');
INSERT INTO `icon_res_info` VALUES ('chain--arrow.png');
INSERT INTO `icon_res_info` VALUES ('ui-group-box.png');
INSERT INTO `icon_res_info` VALUES ('puzzle--minus.png');
INSERT INTO `icon_res_info` VALUES ('mahjong--plus.png');
INSERT INTO `icon_res_info` VALUES ('price-tag--arrow.png');
INSERT INTO `icon_res_info` VALUES ('layers-arrange-back.png');
INSERT INTO `icon_res_info` VALUES ('brightness-small.png');
INSERT INTO `icon_res_info` VALUES ('servers.png');
INSERT INTO `icon_res_info` VALUES ('ui-flow.png');
INSERT INTO `icon_res_info` VALUES ('disk-small.png');
INSERT INTO `icon_res_info` VALUES ('bin-metal.png');
INSERT INTO `icon_res_info` VALUES ('sql-join-inner.png');
INSERT INTO `icon_res_info` VALUES ('building--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('computer--plus.png');
INSERT INTO `icon_res_info` VALUES ('cigarette-stop.png');
INSERT INTO `icon_res_info` VALUES ('balance--arrow.png');
INSERT INTO `icon_res_info` VALUES ('property.png');
INSERT INTO `icon_res_info` VALUES ('palette-color.png');
INSERT INTO `icon_res_info` VALUES ('application-small-list-blue.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-medium.png');
INSERT INTO `icon_res_info` VALUES ('metronome--arrow.png');
INSERT INTO `icon_res_info` VALUES ('printer-small.png');
INSERT INTO `icon_res_info` VALUES ('smiley-cool.png');
INSERT INTO `icon_res_info` VALUES ('arrow-045-small.png');
INSERT INTO `icon_res_info` VALUES ('truck--plus.png');
INSERT INTO `icon_res_info` VALUES ('question.png');
INSERT INTO `icon_res_info` VALUES ('edit-replace.png');
INSERT INTO `icon_res_info` VALUES ('weather-snow.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-paste-document-text.png');
INSERT INTO `icon_res_info` VALUES ('cards-bind-address.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-text.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute.png');
INSERT INTO `icon_res_info` VALUES ('task-select-first.png');
INSERT INTO `icon_res_info` VALUES ('information-octagon-frame.png');
INSERT INTO `icon_res_info` VALUES ('star-small-empty.png');
INSERT INTO `icon_res_info` VALUES ('briefcase--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('store-open.png');
INSERT INTO `icon_res_info` VALUES ('system-monitor-medium.png');
INSERT INTO `icon_res_info` VALUES ('traffic-light--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('validation-label.png');
INSERT INTO `icon_res_info` VALUES ('book--pencil.png');
INSERT INTO `icon_res_info` VALUES ('arrow-000-small.png');
INSERT INTO `icon_res_info` VALUES ('inbox--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('chart--plus.png');
INSERT INTO `icon_res_info` VALUES ('ice.png');
INSERT INTO `icon_res_info` VALUES ('ui-combo-box.png');
INSERT INTO `icon_res_info` VALUES ('edit-kerning.png');
INSERT INTO `icon_res_info` VALUES ('application-network.png');
INSERT INTO `icon_res_info` VALUES ('bandaid--plus.png');
INSERT INTO `icon_res_info` VALUES ('chart--pencil.png');
INSERT INTO `icon_res_info` VALUES ('arrow-135-small.png');
INSERT INTO `icon_res_info` VALUES ('music-beam-16.png');
INSERT INTO `icon_res_info` VALUES ('flag-pink.png');
INSERT INTO `icon_res_info` VALUES ('mobile-phone-off.png');
INSERT INTO `icon_res_info` VALUES ('color-adjustment.png');
INSERT INTO `icon_res_info` VALUES ('photo-album--arrow.png');
INSERT INTO `icon_res_info` VALUES ('database-insert.png');
INSERT INTO `icon_res_info` VALUES ('flashlight--pencil.png');
INSERT INTO `icon_res_info` VALUES ('balloons-facebook.png');
INSERT INTO `icon_res_info` VALUES ('smiley-draw.png');
INSERT INTO `icon_res_info` VALUES ('table-money.png');
INSERT INTO `icon_res_info` VALUES ('wrench.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-shield-frame.png');
INSERT INTO `icon_res_info` VALUES ('smiley-roll.png');
INSERT INTO `icon_res_info` VALUES ('switch--plus.png');
INSERT INTO `icon_res_info` VALUES ('stamp--arrow.png');
INSERT INTO `icon_res_info` VALUES ('trophy--plus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-return.png');
INSERT INTO `icon_res_info` VALUES ('lightning-small.png');
INSERT INTO `icon_res_info` VALUES ('table-join-row.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-outlook.png');
INSERT INTO `icon_res_info` VALUES ('lifebuoy--arrow.png');
INSERT INTO `icon_res_info` VALUES ('book-open-previous.png');
INSERT INTO `icon_res_info` VALUES ('question-white.png');
INSERT INTO `icon_res_info` VALUES ('chart.png');
INSERT INTO `icon_res_info` VALUES ('mail-open-document-music-playlist.png');
INSERT INTO `icon_res_info` VALUES ('application-documents.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-copy.png');
INSERT INTO `icon_res_info` VALUES ('layout-header-3.png');
INSERT INTO `icon_res_info` VALUES ('trophy-bronze.png');
INSERT INTO `icon_res_info` VALUES ('edit-superscript.png');
INSERT INTO `icon_res_info` VALUES ('xfn-sweetheart.png');
INSERT INTO `icon_res_info` VALUES ('ui-toolbar--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('database-network.png');
INSERT INTO `icon_res_info` VALUES ('camera--pencil.png');
INSERT INTO `icon_res_info` VALUES ('paper-plane.png');
INSERT INTO `icon_res_info` VALUES ('contrast-low.png');
INSERT INTO `icon_res_info` VALUES ('toilet-female.png');
INSERT INTO `icon_res_info` VALUES ('eye-half.png');
INSERT INTO `icon_res_info` VALUES ('smiley-sad-blue.png');
INSERT INTO `icon_res_info` VALUES ('edit-alignment.png');
INSERT INTO `icon_res_info` VALUES ('magnifier--arrow.png');
INSERT INTO `icon_res_info` VALUES ('table-delete.png');
INSERT INTO `icon_res_info` VALUES ('medal-bronze.png');
INSERT INTO `icon_res_info` VALUES ('drive-disc-blue.png');
INSERT INTO `icon_res_info` VALUES ('application-text-image.png');
INSERT INTO `icon_res_info` VALUES ('calendar-previous.png');
INSERT INTO `icon_res_info` VALUES ('smiley-evil.png');
INSERT INTO `icon_res_info` VALUES ('processor.png');
INSERT INTO `icon_res_info` VALUES ('umbrella--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('switch--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-tag.png');
INSERT INTO `icon_res_info` VALUES ('drive--minus.png');
INSERT INTO `icon_res_info` VALUES ('paint-can--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-copy.png');
INSERT INTO `icon_res_info` VALUES ('sort--pencil.png');
INSERT INTO `icon_res_info` VALUES ('usb-flash-drive--plus.png');
INSERT INTO `icon_res_info` VALUES ('book-open-bookmark.png');
INSERT INTO `icon_res_info` VALUES ('disc.png');
INSERT INTO `icon_res_info` VALUES ('service-bell--plus.png');
INSERT INTO `icon_res_info` VALUES ('reports-stack.png');
INSERT INTO `icon_res_info` VALUES ('smiley-sad.png');
INSERT INTO `icon_res_info` VALUES ('pin--minus.png');
INSERT INTO `icon_res_info` VALUES ('server-property.png');
INSERT INTO `icon_res_info` VALUES ('opml.png');
INSERT INTO `icon_res_info` VALUES ('fire--minus.png');
INSERT INTO `icon_res_info` VALUES ('receipt--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('pencil--plus.png');
INSERT INTO `icon_res_info` VALUES ('target--arrow.png');
INSERT INTO `icon_res_info` VALUES ('sort-date.png');
INSERT INTO `icon_res_info` VALUES ('spray--plus.png');
INSERT INTO `icon_res_info` VALUES ('chain--pencil.png');
INSERT INTO `icon_res_info` VALUES ('direction--arrow.png');
INSERT INTO `icon_res_info` VALUES ('shopping-basket--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('metronome.png');
INSERT INTO `icon_res_info` VALUES ('tick-octagon-frame.png');
INSERT INTO `icon_res_info` VALUES ('target--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('sort-number.png');
INSERT INTO `icon_res_info` VALUES ('toilet.png');
INSERT INTO `icon_res_info` VALUES ('heart-small.png');
INSERT INTO `icon_res_info` VALUES ('card--minus.png');
INSERT INTO `icon_res_info` VALUES ('media-player-medium-yellow.png');
INSERT INTO `icon_res_info` VALUES ('blogs.png');
INSERT INTO `icon_res_info` VALUES ('paint-can-paint-brush.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-small.png');
INSERT INTO `icon_res_info` VALUES ('color.png');
INSERT INTO `icon_res_info` VALUES ('arrow-continue-270.png');
INSERT INTO `icon_res_info` VALUES ('zone--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('cutter--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-o.png');
INSERT INTO `icon_res_info` VALUES ('scissors.png');
INSERT INTO `icon_res_info` VALUES ('card--plus.png');
INSERT INTO `icon_res_info` VALUES ('keyboard-space.png');
INSERT INTO `icon_res_info` VALUES ('edit-hyphenation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-stop-090.png');
INSERT INTO `icon_res_info` VALUES ('layer--arrow.png');
INSERT INTO `icon_res_info` VALUES ('eye-close.png');
INSERT INTO `icon_res_info` VALUES ('pin--pencil.png');
INSERT INTO `icon_res_info` VALUES ('weather-moon.png');
INSERT INTO `icon_res_info` VALUES ('applications.png');
INSERT INTO `icon_res_info` VALUES ('layer-flip-vertical.png');
INSERT INTO `icon_res_info` VALUES ('rocket--minus.png');
INSERT INTO `icon_res_info` VALUES ('feed-small.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-view.png');
INSERT INTO `icon_res_info` VALUES ('paint-brush--minus.png');
INSERT INTO `icon_res_info` VALUES ('luggage--pencil.png');
INSERT INTO `icon_res_info` VALUES ('sort-quantity.png');
INSERT INTO `icon_res_info` VALUES ('control-000-small.png');
INSERT INTO `icon_res_info` VALUES ('binocular.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb-off.png');
INSERT INTO `icon_res_info` VALUES ('balloons.png');
INSERT INTO `icon_res_info` VALUES ('traffic-cone--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('pda--pencil.png');
INSERT INTO `icon_res_info` VALUES ('gear.png');
INSERT INTO `icon_res_info` VALUES ('layers-alignment-right.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-flash.png');
INSERT INTO `icon_res_info` VALUES ('home--minus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-transition.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open-slide.png');
INSERT INTO `icon_res_info` VALUES ('alarm-clock.png');
INSERT INTO `icon_res_info` VALUES ('medal--pencil.png');
INSERT INTO `icon_res_info` VALUES ('ui-color-picker-default.png');
INSERT INTO `icon_res_info` VALUES ('glass-empty.png');
INSERT INTO `icon_res_info` VALUES ('edit-overline.png');
INSERT INTO `icon_res_info` VALUES ('table-excel.png');
INSERT INTO `icon_res_info` VALUES ('tag--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-globe.png');
INSERT INTO `icon_res_info` VALUES ('blue-documents-text.png');
INSERT INTO `icon_res_info` VALUES ('smiley-cry.png');
INSERT INTO `icon_res_info` VALUES ('clapperboard--minus.png');
INSERT INTO `icon_res_info` VALUES ('clapperboard--pencil.png');
INSERT INTO `icon_res_info` VALUES ('feed--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('barcode-2d.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-insert.png');
INSERT INTO `icon_res_info` VALUES ('paper-clip.png');
INSERT INTO `icon_res_info` VALUES ('document-powerpoint.png');
INSERT INTO `icon_res_info` VALUES ('box--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-music.png');
INSERT INTO `icon_res_info` VALUES ('flag-white.png');
INSERT INTO `icon_res_info` VALUES ('spray--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('present--arrow.png');
INSERT INTO `icon_res_info` VALUES ('credit-card-medium.png');
INSERT INTO `icon_res_info` VALUES ('hourglass--plus.png');
INSERT INTO `icon_res_info` VALUES ('selection-select-input.png');
INSERT INTO `icon_res_info` VALUES ('broom--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('database-import.png');
INSERT INTO `icon_res_info` VALUES ('jar--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('soap-header.png');
INSERT INTO `icon_res_info` VALUES ('pictures.png');
INSERT INTO `icon_res_info` VALUES ('traffic-cone--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-n.png');
INSERT INTO `icon_res_info` VALUES ('jar-label.png');
INSERT INTO `icon_res_info` VALUES ('weather-moon-clouds.png');
INSERT INTO `icon_res_info` VALUES ('blog--arrow.png');
INSERT INTO `icon_res_info` VALUES ('cards-address.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-excel-table.png');
INSERT INTO `icon_res_info` VALUES ('luggage--minus.png');
INSERT INTO `icon_res_info` VALUES ('usb-flash-drive--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('smiley-kiss.png');
INSERT INTO `icon_res_info` VALUES ('xfn-colleague.png');
INSERT INTO `icon_res_info` VALUES ('home-network.png');
INSERT INTO `icon_res_info` VALUES ('envelope.png');
INSERT INTO `icon_res_info` VALUES ('paper-plane--plus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-stop.png');
INSERT INTO `icon_res_info` VALUES ('magnet--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-field-password-red.png');
INSERT INTO `icon_res_info` VALUES ('layer.png');
INSERT INTO `icon_res_info` VALUES ('arrow-return-180-left.png');
INSERT INTO `icon_res_info` VALUES ('globe--arrow.png');
INSERT INTO `icon_res_info` VALUES ('heart-small-empty.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-w.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb-small.png');
INSERT INTO `icon_res_info` VALUES ('pencil-field.png');
INSERT INTO `icon_res_info` VALUES ('book-open.png');
INSERT INTO `icon_res_info` VALUES ('gear--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('slash-button.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-y.png');
INSERT INTO `icon_res_info` VALUES ('slide--minus.png');
INSERT INTO `icon_res_info` VALUES ('bin--plus.png');
INSERT INTO `icon_res_info` VALUES ('lightning--plus.png');
INSERT INTO `icon_res_info` VALUES ('question-small.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-zoom-out.png');
INSERT INTO `icon_res_info` VALUES ('wrench--arrow.png');
INSERT INTO `icon_res_info` VALUES ('newspaper--plus.png');
INSERT INTO `icon_res_info` VALUES ('block-small.png');
INSERT INTO `icon_res_info` VALUES ('car-red.png');
INSERT INTO `icon_res_info` VALUES ('application-monitor.png');
INSERT INTO `icon_res_info` VALUES ('heart-small-half.png');
INSERT INTO `icon_res_info` VALUES ('arrow-return-090-left.png');
INSERT INTO `icon_res_info` VALUES ('wooden-box--arrow.png');
INSERT INTO `icon_res_info` VALUES ('magnifier--minus.png');
INSERT INTO `icon_res_info` VALUES ('glass.png');
INSERT INTO `icon_res_info` VALUES ('switch--arrow.png');
INSERT INTO `icon_res_info` VALUES ('ui-combo-boxes.png');
INSERT INTO `icon_res_info` VALUES ('equalizer--minus.png');
INSERT INTO `icon_res_info` VALUES ('leaf.png');
INSERT INTO `icon_res_info` VALUES ('tag-label-pink.png');
INSERT INTO `icon_res_info` VALUES ('table-delete-column.png');
INSERT INTO `icon_res_info` VALUES ('screwdriver--arrow.png');
INSERT INTO `icon_res_info` VALUES ('keyboard--plus.png');
INSERT INTO `icon_res_info` VALUES ('credit-card.png');
INSERT INTO `icon_res_info` VALUES ('bug--minus.png');
INSERT INTO `icon_res_info` VALUES ('crown--plus.png');
INSERT INTO `icon_res_info` VALUES ('folder--pencil.png');
INSERT INTO `icon_res_info` VALUES ('smiley-sweat.png');
INSERT INTO `icon_res_info` VALUES ('cake--minus.png');
INSERT INTO `icon_res_info` VALUES ('traffic-cone--plus.png');
INSERT INTO `icon_res_info` VALUES ('calendar-select-month.png');
INSERT INTO `icon_res_info` VALUES ('playing-card.png');
INSERT INTO `icon_res_info` VALUES ('calendar-search-result.png');
INSERT INTO `icon_res_info` VALUES ('ui-tooltip.png');
INSERT INTO `icon_res_info` VALUES ('document-number.png');
INSERT INTO `icon_res_info` VALUES ('zone--plus.png');
INSERT INTO `icon_res_info` VALUES ('bandaid-small.png');
INSERT INTO `icon_res_info` VALUES ('layer-resize-replicate-vertical.png');
INSERT INTO `icon_res_info` VALUES ('glass--pencil.png');
INSERT INTO `icon_res_info` VALUES ('spray--pencil.png');
INSERT INTO `icon_res_info` VALUES ('balloon-white-left.png');
INSERT INTO `icon_res_info` VALUES ('zone--arrow.png');
INSERT INTO `icon_res_info` VALUES ('drawer--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('bank--plus.png');
INSERT INTO `icon_res_info` VALUES ('paint-can--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('server-network.png');
INSERT INTO `icon_res_info` VALUES ('building.png');
INSERT INTO `icon_res_info` VALUES ('flask--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('image-sharpen.png');
INSERT INTO `icon_res_info` VALUES ('navigation-000-button.png');
INSERT INTO `icon_res_info` VALUES ('maps-stack.png');
INSERT INTO `icon_res_info` VALUES ('gear-small.png');
INSERT INTO `icon_res_info` VALUES ('printer--minus.png');
INSERT INTO `icon_res_info` VALUES ('arrow.png');
INSERT INTO `icon_res_info` VALUES ('arrow-merge.png');
INSERT INTO `icon_res_info` VALUES ('monitor-window-3d.png');
INSERT INTO `icon_res_info` VALUES ('block-share.png');
INSERT INTO `icon_res_info` VALUES ('computer--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('plug.png');
INSERT INTO `icon_res_info` VALUES ('arrow-turn.png');
INSERT INTO `icon_res_info` VALUES ('media-players.png');
INSERT INTO `icon_res_info` VALUES ('balloons-facebook-box.png');
INSERT INTO `icon_res_info` VALUES ('cookie--minus.png');
INSERT INTO `icon_res_info` VALUES ('border-top-bottom.png');
INSERT INTO `icon_res_info` VALUES ('question-balloon.png');
INSERT INTO `icon_res_info` VALUES ('telephone-off.png');
INSERT INTO `icon_res_info` VALUES ('bomb.png');
INSERT INTO `icon_res_info` VALUES ('arrow-continue-090-left.png');
INSERT INTO `icon_res_info` VALUES ('table--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('spray--minus.png');
INSERT INTO `icon_res_info` VALUES ('zone--minus.png');
INSERT INTO `icon_res_info` VALUES ('bug.png');
INSERT INTO `icon_res_info` VALUES ('glass--minus.png');
INSERT INTO `icon_res_info` VALUES ('folder-zipper.png');
INSERT INTO `icon_res_info` VALUES ('table-small.png');
INSERT INTO `icon_res_info` VALUES ('ui-spin.png');
INSERT INTO `icon_res_info` VALUES ('ui-slider-100.png');
INSERT INTO `icon_res_info` VALUES ('document-shred.png');
INSERT INTO `icon_res_info` VALUES ('television--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('card-address.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-globe.png');
INSERT INTO `icon_res_info` VALUES ('piano.png');
INSERT INTO `icon_res_info` VALUES ('flask.png');
INSERT INTO `icon_res_info` VALUES ('edit-shadow.png');
INSERT INTO `icon_res_info` VALUES ('battery-charge.png');
INSERT INTO `icon_res_info` VALUES ('bean-green.png');
INSERT INTO `icon_res_info` VALUES ('monitor-window-flow.png');
INSERT INTO `icon_res_info` VALUES ('ui-combo-box-blue.png');
INSERT INTO `icon_res_info` VALUES ('application-small-blue.png');
INSERT INTO `icon_res_info` VALUES ('alarm-clock--minus.png');
INSERT INTO `icon_res_info` VALUES ('service-bell--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('ruler.png');
INSERT INTO `icon_res_info` VALUES ('tables.png');
INSERT INTO `icon_res_info` VALUES ('card.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-xaml.png');
INSERT INTO `icon_res_info` VALUES ('wand-hat.png');
INSERT INTO `icon_res_info` VALUES ('credit-card--minus.png');
INSERT INTO `icon_res_info` VALUES ('marker--minus.png');
INSERT INTO `icon_res_info` VALUES ('envelope--minus.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note-small-pin.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-diamond-frame.png');
INSERT INTO `icon_res_info` VALUES ('truck.png');
INSERT INTO `icon_res_info` VALUES ('ruby.png');
INSERT INTO `icon_res_info` VALUES ('inbox-document-text.png');
INSERT INTO `icon_res_info` VALUES ('mails-stack.png');
INSERT INTO `icon_res_info` VALUES ('server--plus.png');
INSERT INTO `icon_res_info` VALUES ('node-insert-previous.png');
INSERT INTO `icon_res_info` VALUES ('chart-up.png');
INSERT INTO `icon_res_info` VALUES ('calendar-blue.png');
INSERT INTO `icon_res_info` VALUES ('drill--minus.png');
INSERT INTO `icon_res_info` VALUES ('tag--pencil.png');
INSERT INTO `icon_res_info` VALUES ('wooden-box.png');
INSERT INTO `icon_res_info` VALUES ('navigation-270-frame.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-flash-movie.png');
INSERT INTO `icon_res_info` VALUES ('scanner--arrow.png');
INSERT INTO `icon_res_info` VALUES ('table-import.png');
INSERT INTO `icon_res_info` VALUES ('information-frame.png');
INSERT INTO `icon_res_info` VALUES ('balloon-small.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-view-book.png');
INSERT INTO `icon_res_info` VALUES ('control-stop-square.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note--arrow.png');
INSERT INTO `icon_res_info` VALUES ('clipboard--pencil.png');
INSERT INTO `icon_res_info` VALUES ('magnet--plus.png');
INSERT INTO `icon_res_info` VALUES ('bell--arrow.png');
INSERT INTO `icon_res_info` VALUES ('network-status-away.png');
INSERT INTO `icon_res_info` VALUES ('camcorder-image.png');
INSERT INTO `icon_res_info` VALUES ('direction--pencil.png');
INSERT INTO `icon_res_info` VALUES ('postage-stamp--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('minus-octagon-frame.png');
INSERT INTO `icon_res_info` VALUES ('user-small-female.png');
INSERT INTO `icon_res_info` VALUES ('question-button.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-form.png');
INSERT INTO `icon_res_info` VALUES ('control-skip-270-small.png');
INSERT INTO `icon_res_info` VALUES ('tick-circle.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-z.png');
INSERT INTO `icon_res_info` VALUES ('switch--pencil.png');
INSERT INTO `icon_res_info` VALUES ('script--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('control-090.png');
INSERT INTO `icon_res_info` VALUES ('cross.png');
INSERT INTO `icon_res_info` VALUES ('wooden-box--minus.png');
INSERT INTO `icon_res_info` VALUES ('monitor-window.png');
INSERT INTO `icon_res_info` VALUES ('tick-red.png');
INSERT INTO `icon_res_info` VALUES ('edit-rule.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note-text.png');
INSERT INTO `icon_res_info` VALUES ('ticket--arrow.png');
INSERT INTO `icon_res_info` VALUES ('medal--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('cross-white.png');
INSERT INTO `icon_res_info` VALUES ('drive--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open-document-text.png');
INSERT INTO `icon_res_info` VALUES ('medal-red.png');
INSERT INTO `icon_res_info` VALUES ('server--minus.png');
INSERT INTO `icon_res_info` VALUES ('blogs-stack.png');
INSERT INTO `icon_res_info` VALUES ('globe-green.png');
INSERT INTO `icon_res_info` VALUES ('border-right.png');
INSERT INTO `icon_res_info` VALUES ('burn--pencil.png');
INSERT INTO `icon_res_info` VALUES ('folder--minus.png');
INSERT INTO `icon_res_info` VALUES ('medal--plus.png');
INSERT INTO `icon_res_info` VALUES ('slide-medium.png');
INSERT INTO `icon_res_info` VALUES ('application-sidebar-expand.png');
INSERT INTO `icon_res_info` VALUES ('door--minus.png');
INSERT INTO `icon_res_info` VALUES ('piano--plus.png');
INSERT INTO `icon_res_info` VALUES ('binocular--arrow.png');
INSERT INTO `icon_res_info` VALUES ('flashlight--plus.png');
INSERT INTO `icon_res_info` VALUES ('crown.png');
INSERT INTO `icon_res_info` VALUES ('terminal--arrow.png');
INSERT INTO `icon_res_info` VALUES ('layers-stack.png');
INSERT INTO `icon_res_info` VALUES ('equalizer-high.png');
INSERT INTO `icon_res_info` VALUES ('bean.png');
INSERT INTO `icon_res_info` VALUES ('property-blue.png');
INSERT INTO `icon_res_info` VALUES ('jar-open.png');
INSERT INTO `icon_res_info` VALUES ('compass.png');
INSERT INTO `icon_res_info` VALUES ('water.png');
INSERT INTO `icon_res_info` VALUES ('arrow-315.png');
INSERT INTO `icon_res_info` VALUES ('node-delete-previous.png');
INSERT INTO `icon_res_info` VALUES ('layout-hf-3-mix.png');
INSERT INTO `icon_res_info` VALUES ('pipette--arrow.png');
INSERT INTO `icon_res_info` VALUES ('computer--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-node.png');
INSERT INTO `icon_res_info` VALUES ('tag-export.png');
INSERT INTO `icon_res_info` VALUES ('point--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-f.png');
INSERT INTO `icon_res_info` VALUES ('plus-small-white.png');
INSERT INTO `icon_res_info` VALUES ('block--plus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-skip-270.png');
INSERT INTO `icon_res_info` VALUES ('t-shirt-gray.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-table.png');
INSERT INTO `icon_res_info` VALUES ('luggage--plus.png');
INSERT INTO `icon_res_info` VALUES ('paper-bag--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('book-bookmark.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle-225-left.png');
INSERT INTO `icon_res_info` VALUES ('block--minus.png');
INSERT INTO `icon_res_info` VALUES ('palette-paint-brush.png');
INSERT INTO `icon_res_info` VALUES ('application-block.png');
INSERT INTO `icon_res_info` VALUES ('rocket--plus.png');
INSERT INTO `icon_res_info` VALUES ('edit-alignment-justify.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-b.png');
INSERT INTO `icon_res_info` VALUES ('wooden-box--pencil.png');
INSERT INTO `icon_res_info` VALUES ('smiley-lol.png');
INSERT INTO `icon_res_info` VALUES ('webcam--minus.png');
INSERT INTO `icon_res_info` VALUES ('user-green-female.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve.png');
INSERT INTO `icon_res_info` VALUES ('paint-can-medium.png');
INSERT INTO `icon_res_info` VALUES ('paint-tube-color.png');
INSERT INTO `icon_res_info` VALUES ('control.png');
INSERT INTO `icon_res_info` VALUES ('ruler--pencil.png');
INSERT INTO `icon_res_info` VALUES ('home--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-join-090.png');
INSERT INTO `icon_res_info` VALUES ('document--pencil.png');
INSERT INTO `icon_res_info` VALUES ('switch-small.png');
INSERT INTO `icon_res_info` VALUES ('projection-screen--pencil.png');
INSERT INTO `icon_res_info` VALUES ('battery-full.png');
INSERT INTO `icon_res_info` VALUES ('ui-split-panel.png');
INSERT INTO `icon_res_info` VALUES ('script-code.png');
INSERT INTO `icon_res_info` VALUES ('paint-brush--arrow.png');
INSERT INTO `icon_res_info` VALUES ('layer-shape-ellipse.png');
INSERT INTO `icon_res_info` VALUES ('zone-select.png');
INSERT INTO `icon_res_info` VALUES ('counter.png');
INSERT INTO `icon_res_info` VALUES ('pill--plus.png');
INSERT INTO `icon_res_info` VALUES ('compass--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('star--minus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle-315.png');
INSERT INTO `icon_res_info` VALUES ('color-swatches.png');
INSERT INTO `icon_res_info` VALUES ('arrow-branch-270.png');
INSERT INTO `icon_res_info` VALUES ('edit-strike.png');
INSERT INTO `icon_res_info` VALUES ('layout-select-footer.png');
INSERT INTO `icon_res_info` VALUES ('notebook--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('document.png');
INSERT INTO `icon_res_info` VALUES ('umbrella--pencil.png');
INSERT INTO `icon_res_info` VALUES ('odata-document.png');
INSERT INTO `icon_res_info` VALUES ('inbox-table.png');
INSERT INTO `icon_res_info` VALUES ('navigation-090-frame.png');
INSERT INTO `icon_res_info` VALUES ('ui-toolbar.png');
INSERT INTO `icon_res_info` VALUES ('application-run.png');
INSERT INTO `icon_res_info` VALUES ('mail-open-film.png');
INSERT INTO `icon_res_info` VALUES ('user-share.png');
INSERT INTO `icon_res_info` VALUES ('desktop-share.png');
INSERT INTO `icon_res_info` VALUES ('database-export.png');
INSERT INTO `icon_res_info` VALUES ('document-list.png');
INSERT INTO `icon_res_info` VALUES ('medal-bronze-red.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-g.png');
INSERT INTO `icon_res_info` VALUES ('toggle-expand.png');
INSERT INTO `icon_res_info` VALUES ('glass-wide.png');
INSERT INTO `icon_res_info` VALUES ('document-clock.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open.png');
INSERT INTO `icon_res_info` VALUES ('scissors--plus.png');
INSERT INTO `icon_res_info` VALUES ('microphone.png');
INSERT INTO `icon_res_info` VALUES ('guitar.png');
INSERT INTO `icon_res_info` VALUES ('inbox.png');
INSERT INTO `icon_res_info` VALUES ('shield.png');
INSERT INTO `icon_res_info` VALUES ('balloon-twitter-left.png');
INSERT INTO `icon_res_info` VALUES ('network-cloud.png');
INSERT INTO `icon_res_info` VALUES ('paper-bag--arrow.png');
INSERT INTO `icon_res_info` VALUES ('calculator--pencil.png');
INSERT INTO `icon_res_info` VALUES ('edit-language.png');
INSERT INTO `icon_res_info` VALUES ('contrast-small.png');
INSERT INTO `icon_res_info` VALUES ('truck-empty.png');
INSERT INTO `icon_res_info` VALUES ('heart--plus.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute.png');
INSERT INTO `icon_res_info` VALUES ('edit-list.png');
INSERT INTO `icon_res_info` VALUES ('cookies.png');
INSERT INTO `icon_res_info` VALUES ('photo-album--pencil.png');
INSERT INTO `icon_res_info` VALUES ('document-excel-table.png');
INSERT INTO `icon_res_info` VALUES ('lifebuoy--minus.png');
INSERT INTO `icon_res_info` VALUES ('clock-small.png');
INSERT INTO `icon_res_info` VALUES ('navigation-180-button.png');
INSERT INTO `icon_res_info` VALUES ('chain--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('calendar--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('cross-small-circle.png');
INSERT INTO `icon_res_info` VALUES ('sd-memory-card.png');
INSERT INTO `icon_res_info` VALUES ('building--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('gear--plus.png');
INSERT INTO `icon_res_info` VALUES ('task--minus.png');
INSERT INTO `icon_res_info` VALUES ('application-tile.png');
INSERT INTO `icon_res_info` VALUES ('bookmark.png');
INSERT INTO `icon_res_info` VALUES ('credit-cards.png');
INSERT INTO `icon_res_info` VALUES ('monitor-off.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-x.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-bookmark.png');
INSERT INTO `icon_res_info` VALUES ('hourglass-select.png');
INSERT INTO `icon_res_info` VALUES ('document-import.png');
INSERT INTO `icon_res_info` VALUES ('hand-point-090.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('notebook.png');
INSERT INTO `icon_res_info` VALUES ('bamboo.png');
INSERT INTO `icon_res_info` VALUES ('playing-card--arrow.png');
INSERT INTO `icon_res_info` VALUES ('balance--pencil.png');
INSERT INTO `icon_res_info` VALUES ('layer-shape-text.png');
INSERT INTO `icon_res_info` VALUES ('magnifier.png');
INSERT INTO `icon_res_info` VALUES ('edit-strike-double.png');
INSERT INTO `icon_res_info` VALUES ('layer--pencil.png');
INSERT INTO `icon_res_info` VALUES ('clock--pencil.png');
INSERT INTO `icon_res_info` VALUES ('user-silhouette-question.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-bar-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('arrow-resize-135.png');
INSERT INTO `icon_res_info` VALUES ('building-small.png');
INSERT INTO `icon_res_info` VALUES ('drive-download.png');
INSERT INTO `icon_res_info` VALUES ('border-top-bottom-thick.png');
INSERT INTO `icon_res_info` VALUES ('layout-4.png');
INSERT INTO `icon_res_info` VALUES ('folder-shred.png');
INSERT INTO `icon_res_info` VALUES ('ui-ruler.png');
INSERT INTO `icon_res_info` VALUES ('document-break.png');
INSERT INTO `icon_res_info` VALUES ('edit-lowercase.png');
INSERT INTO `icon_res_info` VALUES ('edit-vertical-alignment-top.png');
INSERT INTO `icon_res_info` VALUES ('car--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('broom--plus.png');
INSERT INTO `icon_res_info` VALUES ('blueprint-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('pictures-stack.png');
INSERT INTO `icon_res_info` VALUES ('key--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('color--pencil.png');
INSERT INTO `icon_res_info` VALUES ('keyboard--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-h.png');
INSERT INTO `icon_res_info` VALUES ('robot-off.png');
INSERT INTO `icon_res_info` VALUES ('webcam-network.png');
INSERT INTO `icon_res_info` VALUES ('fill-270.png');
INSERT INTO `icon_res_info` VALUES ('disk--arrow.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-e.png');
INSERT INTO `icon_res_info` VALUES ('card-small.png');
INSERT INTO `icon_res_info` VALUES ('address-book--plus.png');
INSERT INTO `icon_res_info` VALUES ('fruit-orange.png');
INSERT INTO `icon_res_info` VALUES ('document-excel-csv.png');
INSERT INTO `icon_res_info` VALUES ('odata-small.png');
INSERT INTO `icon_res_info` VALUES ('blueprint--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-315-small.png');
INSERT INTO `icon_res_info` VALUES ('scanner--minus.png');
INSERT INTO `icon_res_info` VALUES ('edit-scale.png');
INSERT INTO `icon_res_info` VALUES ('clock--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('script-globe.png');
INSERT INTO `icon_res_info` VALUES ('sort--minus.png');
INSERT INTO `icon_res_info` VALUES ('speaker-volume-none.png');
INSERT INTO `icon_res_info` VALUES ('application-list.png');
INSERT INTO `icon_res_info` VALUES ('disc--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-powerpoint.png');
INSERT INTO `icon_res_info` VALUES ('star-small-half.png');
INSERT INTO `icon_res_info` VALUES ('hand-property.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-field-hidden.png');
INSERT INTO `icon_res_info` VALUES ('ui-tooltip--arrow.png');
INSERT INTO `icon_res_info` VALUES ('databases-relation.png');
INSERT INTO `icon_res_info` VALUES ('server-cast.png');
INSERT INTO `icon_res_info` VALUES ('media-player--pencil.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-u.png');
INSERT INTO `icon_res_info` VALUES ('network-status.png');
INSERT INTO `icon_res_info` VALUES ('arrow-transition-180.png');
INSERT INTO `icon_res_info` VALUES ('image-share.png');
INSERT INTO `icon_res_info` VALUES ('disc-case.png');
INSERT INTO `icon_res_info` VALUES ('ring.png');
INSERT INTO `icon_res_info` VALUES ('picture-small-sunset.png');
INSERT INTO `icon_res_info` VALUES ('paint-can--arrow.png');
INSERT INTO `icon_res_info` VALUES ('picture--minus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-page.png');
INSERT INTO `icon_res_info` VALUES ('television-image.png');
INSERT INTO `icon_res_info` VALUES ('sql-join-outer.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-p.png');
INSERT INTO `icon_res_info` VALUES ('mail-forward-all.png');
INSERT INTO `icon_res_info` VALUES ('puzzle--plus.png');
INSERT INTO `icon_res_info` VALUES ('fire--arrow.png');
INSERT INTO `icon_res_info` VALUES ('mouse--pencil.png');
INSERT INTO `icon_res_info` VALUES ('contrast-control-up.png');
INSERT INTO `icon_res_info` VALUES ('document-page.png');
INSERT INTO `icon_res_info` VALUES ('arrow-225-small.png');
INSERT INTO `icon_res_info` VALUES ('spell-check-error.png');
INSERT INTO `icon_res_info` VALUES ('slash.png');
INSERT INTO `icon_res_info` VALUES ('application-terminal.png');
INSERT INTO `icon_res_info` VALUES ('border-top.png');
INSERT INTO `icon_res_info` VALUES ('layer-mask.png');
INSERT INTO `icon_res_info` VALUES ('plate-cutlery.png');
INSERT INTO `icon_res_info` VALUES ('creative-commons.png');
INSERT INTO `icon_res_info` VALUES ('disk--minus.png');
INSERT INTO `icon_res_info` VALUES ('script-flash.png');
INSERT INTO `icon_res_info` VALUES ('arrow-stop-180.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-word.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-horizontal-open.png');
INSERT INTO `icon_res_info` VALUES ('present.png');
INSERT INTO `icon_res_info` VALUES ('fire-small.png');
INSERT INTO `icon_res_info` VALUES ('webcam--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('hourglass--minus.png');
INSERT INTO `icon_res_info` VALUES ('script-office.png');
INSERT INTO `icon_res_info` VALUES ('media-player--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('application-sidebar.png');
INSERT INTO `icon_res_info` VALUES ('ui-address-bar-green.png');
INSERT INTO `icon_res_info` VALUES ('fire-big.png');
INSERT INTO `icon_res_info` VALUES ('webcam-medium.png');
INSERT INTO `icon_res_info` VALUES ('receipt-export.png');
INSERT INTO `icon_res_info` VALUES ('alarm-clock--pencil.png');
INSERT INTO `icon_res_info` VALUES ('ui-tooltip--plus.png');
INSERT INTO `icon_res_info` VALUES ('layout-3.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-share.png');
INSERT INTO `icon_res_info` VALUES ('minus-white.png');
INSERT INTO `icon_res_info` VALUES ('balloons-box.png');
INSERT INTO `icon_res_info` VALUES ('game-monitor.png');
INSERT INTO `icon_res_info` VALUES ('compile.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-zoom-in.png');
INSERT INTO `icon_res_info` VALUES ('layers-alignment-left.png');
INSERT INTO `icon_res_info` VALUES ('border-bottom-double.png');
INSERT INTO `icon_res_info` VALUES ('point--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('broom--minus.png');
INSERT INTO `icon_res_info` VALUES ('layers-alignment.png');
INSERT INTO `icon_res_info` VALUES ('tag-medium.png');
INSERT INTO `icon_res_info` VALUES ('sql-join.png');
INSERT INTO `icon_res_info` VALUES ('paper-bag--pencil.png');
INSERT INTO `icon_res_info` VALUES ('arrow-split-090.png');
INSERT INTO `icon_res_info` VALUES ('star.png');
INSERT INTO `icon_res_info` VALUES ('ticket-stub.png');
INSERT INTO `icon_res_info` VALUES ('script-excel.png');
INSERT INTO `icon_res_info` VALUES ('thumb.png');
INSERT INTO `icon_res_info` VALUES ('compass--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-g.png');
INSERT INTO `icon_res_info` VALUES ('usb-flash-drive.png');
INSERT INTO `icon_res_info` VALUES ('minus-small.png');
INSERT INTO `icon_res_info` VALUES ('truck--minus.png');
INSERT INTO `icon_res_info` VALUES ('box.png');
INSERT INTO `icon_res_info` VALUES ('node-select-next.png');
INSERT INTO `icon_res_info` VALUES ('credit-card--arrow.png');
INSERT INTO `icon_res_info` VALUES ('funnel--plus.png');
INSERT INTO `icon_res_info` VALUES ('guide-snap.png');
INSERT INTO `icon_res_info` VALUES ('weather.png');
INSERT INTO `icon_res_info` VALUES ('palette--minus.png');
INSERT INTO `icon_res_info` VALUES ('blueprint--plus.png');
INSERT INTO `icon_res_info` VALUES ('telephone--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('spell-check.png');
INSERT INTO `icon_res_info` VALUES ('arrow-merge-090-left.png');
INSERT INTO `icon_res_info` VALUES ('color-swatch-small.png');
INSERT INTO `icon_res_info` VALUES ('clipboard.png');
INSERT INTO `icon_res_info` VALUES ('photo-album-blue.png');
INSERT INTO `icon_res_info` VALUES ('box-medium.png');
INSERT INTO `icon_res_info` VALUES ('leaf--minus.png');
INSERT INTO `icon_res_info` VALUES ('book--minus.png');
INSERT INTO `icon_res_info` VALUES ('image--minus.png');
INSERT INTO `icon_res_info` VALUES ('plus.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-c.png');
INSERT INTO `icon_res_info` VALUES ('ui-label-link.png');
INSERT INTO `icon_res_info` VALUES ('microformats.png');
INSERT INTO `icon_res_info` VALUES ('application-medium.png');
INSERT INTO `icon_res_info` VALUES ('ui-combo-box-edit.png');
INSERT INTO `icon_res_info` VALUES ('paint-brush--pencil.png');
INSERT INTO `icon_res_info` VALUES ('smiley-yell.png');
INSERT INTO `icon_res_info` VALUES ('disc--arrow.png');
INSERT INTO `icon_res_info` VALUES ('balloons-white.png');
INSERT INTO `icon_res_info` VALUES ('magnet.png');
INSERT INTO `icon_res_info` VALUES ('arrow-continue-090.png');
INSERT INTO `icon_res_info` VALUES ('control-skip-180-small.png');
INSERT INTO `icon_res_info` VALUES ('tag-label-yellow.png');
INSERT INTO `icon_res_info` VALUES ('ui-slider-vertical-100.png');
INSERT INTO `icon_res_info` VALUES ('leaf--arrow.png');
INSERT INTO `icon_res_info` VALUES ('folder-share.png');
INSERT INTO `icon_res_info` VALUES ('chain.png');
INSERT INTO `icon_res_info` VALUES ('beaker--plus.png');
INSERT INTO `icon_res_info` VALUES ('projection-screen--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('equalizer--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('chart--minus.png');
INSERT INTO `icon_res_info` VALUES ('control-stop-270.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-image.png');
INSERT INTO `icon_res_info` VALUES ('bookmark--minus.png');
INSERT INTO `icon_res_info` VALUES ('home--arrow.png');
INSERT INTO `icon_res_info` VALUES ('bin--arrow.png');
INSERT INTO `icon_res_info` VALUES ('terminal--plus.png');
INSERT INTO `icon_res_info` VALUES ('application-form.png');
INSERT INTO `icon_res_info` VALUES ('tag-small.png');
INSERT INTO `icon_res_info` VALUES ('crown-silver.png');
INSERT INTO `icon_res_info` VALUES ('service-bell.png');
INSERT INTO `icon_res_info` VALUES ('document-view.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-office-text.png');
INSERT INTO `icon_res_info` VALUES ('edit-writing-mode-tbrl.png');
INSERT INTO `icon_res_info` VALUES ('clipboard--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('cookie--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('wand-small.png');
INSERT INTO `icon_res_info` VALUES ('price-tag--minus.png');
INSERT INTO `icon_res_info` VALUES ('envelope--arrow.png');
INSERT INTO `icon_res_info` VALUES ('jar--minus.png');
INSERT INTO `icon_res_info` VALUES ('flag-gray.png');
INSERT INTO `icon_res_info` VALUES ('globe.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note-pin.png');
INSERT INTO `icon_res_info` VALUES ('drawer--arrow.png');
INSERT INTO `icon_res_info` VALUES ('desktop-image.png');
INSERT INTO `icon_res_info` VALUES ('edit-indent.png');
INSERT INTO `icon_res_info` VALUES ('currency-pound.png');
INSERT INTO `icon_res_info` VALUES ('car--minus.png');
INSERT INTO `icon_res_info` VALUES ('mail-open-image.png');
INSERT INTO `icon_res_info` VALUES ('new-text.png');
INSERT INTO `icon_res_info` VALUES ('user-detective-gray.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-broken.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-e.png');
INSERT INTO `icon_res_info` VALUES ('star--pencil.png');
INSERT INTO `icon_res_info` VALUES ('application--plus.png');
INSERT INTO `icon_res_info` VALUES ('palette--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('bin--pencil.png');
INSERT INTO `icon_res_info` VALUES ('shopping-basket--minus.png');
INSERT INTO `icon_res_info` VALUES ('mail-open-document.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder--plus.png');
INSERT INTO `icon_res_info` VALUES ('webcam--plus.png');
INSERT INTO `icon_res_info` VALUES ('map--minus.png');
INSERT INTO `icon_res_info` VALUES ('odata-balloon.png');
INSERT INTO `icon_res_info` VALUES ('drive-disc.png');
INSERT INTO `icon_res_info` VALUES ('drive--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('toolbox--plus.png');
INSERT INTO `icon_res_info` VALUES ('bug--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('lifebuoy--plus.png');
INSERT INTO `icon_res_info` VALUES ('mail-reply.png');
INSERT INTO `icon_res_info` VALUES ('status.png');
INSERT INTO `icon_res_info` VALUES ('hourglass--pencil.png');
INSERT INTO `icon_res_info` VALUES ('tag-label-black.png');
INSERT INTO `icon_res_info` VALUES ('mahjong--arrow.png');
INSERT INTO `icon_res_info` VALUES ('water--minus.png');
INSERT INTO `icon_res_info` VALUES ('layer-select.png');
INSERT INTO `icon_res_info` VALUES ('car--plus.png');
INSERT INTO `icon_res_info` VALUES ('battery--pencil.png');
INSERT INTO `icon_res_info` VALUES ('flag-blue.png');
INSERT INTO `icon_res_info` VALUES ('key--arrow.png');
INSERT INTO `icon_res_info` VALUES ('image-resize-actual.png');
INSERT INTO `icon_res_info` VALUES ('battery--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('binocular--minus.png');
INSERT INTO `icon_res_info` VALUES ('pipette--minus.png');
INSERT INTO `icon_res_info` VALUES ('flashlight.png');
INSERT INTO `icon_res_info` VALUES ('layout-hf-2.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle-315-left.png');
INSERT INTO `icon_res_info` VALUES ('eraser--plus.png');
INSERT INTO `icon_res_info` VALUES ('task--plus.png');
INSERT INTO `icon_res_info` VALUES ('beaker--arrow.png');
INSERT INTO `icon_res_info` VALUES ('camcorder--pencil.png');
INSERT INTO `icon_res_info` VALUES ('clock-history-frame.png');
INSERT INTO `icon_res_info` VALUES ('terminal.png');
INSERT INTO `icon_res_info` VALUES ('calculator.png');
INSERT INTO `icon_res_info` VALUES ('document-view-thumbnail.png');
INSERT INTO `icon_res_info` VALUES ('anchor.png');
INSERT INTO `icon_res_info` VALUES ('hammer.png');
INSERT INTO `icon_res_info` VALUES ('edit-size-down.png');
INSERT INTO `icon_res_info` VALUES ('layers-stack-arrange.png');
INSERT INTO `icon_res_info` VALUES ('building--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-binary.png');
INSERT INTO `icon_res_info` VALUES ('application-blog.png');
INSERT INTO `icon_res_info` VALUES ('chevron-expand.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-red-frame.png');
INSERT INTO `icon_res_info` VALUES ('table--plus.png');
INSERT INTO `icon_res_info` VALUES ('folder-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('film-cast.png');
INSERT INTO `icon_res_info` VALUES ('zone.png');
INSERT INTO `icon_res_info` VALUES ('plug--pencil.png');
INSERT INTO `icon_res_info` VALUES ('mobile-phone-medium.png');
INSERT INTO `icon_res_info` VALUES ('ui-tooltip-balloon-bottom.png');
INSERT INTO `icon_res_info` VALUES ('script-text.png');
INSERT INTO `icon_res_info` VALUES ('folder-rename.png');
INSERT INTO `icon_res_info` VALUES ('control-double-180.png');
INSERT INTO `icon_res_info` VALUES ('eye--plus.png');
INSERT INTO `icon_res_info` VALUES ('bandaid--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('e-book-reader.png');
INSERT INTO `icon_res_info` VALUES ('document-broken.png');
INSERT INTO `icon_res_info` VALUES ('screwdriver--plus.png');
INSERT INTO `icon_res_info` VALUES ('microphone--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('drill--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('ui-tooltip--pencil.png');
INSERT INTO `icon_res_info` VALUES ('currency-yen.png');
INSERT INTO `icon_res_info` VALUES ('paint-brush--plus.png');
INSERT INTO `icon_res_info` VALUES ('playing-card--pencil.png');
INSERT INTO `icon_res_info` VALUES ('inbox-document-music.png');
INSERT INTO `icon_res_info` VALUES ('cards-bind.png');
INSERT INTO `icon_res_info` VALUES ('trophy.png');
INSERT INTO `icon_res_info` VALUES ('graphic-card.png');
INSERT INTO `icon_res_info` VALUES ('documents-stack.png');
INSERT INTO `icon_res_info` VALUES ('palette.png');
INSERT INTO `icon_res_info` VALUES ('lock-unlock.png');
INSERT INTO `icon_res_info` VALUES ('terminal-medium.png');
INSERT INTO `icon_res_info` VALUES ('calendar--arrow.png');
INSERT INTO `icon_res_info` VALUES ('weather-fog.png');
INSERT INTO `icon_res_info` VALUES ('brightness-control.png');
INSERT INTO `icon_res_info` VALUES ('share.png');
INSERT INTO `icon_res_info` VALUES ('film-medium.png');
INSERT INTO `icon_res_info` VALUES ('table.png');
INSERT INTO `icon_res_info` VALUES ('scanner-off.png');
INSERT INTO `icon_res_info` VALUES ('usb-flash-drive--pencil.png');
INSERT INTO `icon_res_info` VALUES ('traffic-light.png');
INSERT INTO `icon_res_info` VALUES ('heart--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('cassette-small.png');
INSERT INTO `icon_res_info` VALUES ('metronome--plus.png');
INSERT INTO `icon_res_info` VALUES ('highlighter--plus.png');
INSERT INTO `icon_res_info` VALUES ('control-double-090-small.png');
INSERT INTO `icon_res_info` VALUES ('spray--arrow.png');
INSERT INTO `icon_res_info` VALUES ('script--minus.png');
INSERT INTO `icon_res_info` VALUES ('border-outside.png');
INSERT INTO `icon_res_info` VALUES ('trophy--minus.png');
INSERT INTO `icon_res_info` VALUES ('luggage.png');
INSERT INTO `icon_res_info` VALUES ('guitar--arrow.png');
INSERT INTO `icon_res_info` VALUES ('fire--pencil.png');
INSERT INTO `icon_res_info` VALUES ('documents-text.png');
INSERT INTO `icon_res_info` VALUES ('smiley-slim.png');
INSERT INTO `icon_res_info` VALUES ('chair--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('sitemap-image.png');
INSERT INTO `icon_res_info` VALUES ('currency-euro.png');
INSERT INTO `icon_res_info` VALUES ('ui-check-box-mix.png');
INSERT INTO `icon_res_info` VALUES ('cursor-small.png');
INSERT INTO `icon_res_info` VALUES ('arrow-branch-180-left.png');
INSERT INTO `icon_res_info` VALUES ('calendar-select-days-span.png');
INSERT INTO `icon_res_info` VALUES ('puzzle--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('application-tile-vertical.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-k.png');
INSERT INTO `icon_res_info` VALUES ('eye.png');
INSERT INTO `icon_res_info` VALUES ('document-insert.png');
INSERT INTO `icon_res_info` VALUES ('marker--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('ui-slider-vertical.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note--minus.png');
INSERT INTO `icon_res_info` VALUES ('headphone.png');
INSERT INTO `icon_res_info` VALUES ('receipt-stamp.png');
INSERT INTO `icon_res_info` VALUES ('node-insert-next.png');
INSERT INTO `icon_res_info` VALUES ('drill.png');
INSERT INTO `icon_res_info` VALUES ('application-sidebar-list.png');
INSERT INTO `icon_res_info` VALUES ('film--plus.png');
INSERT INTO `icon_res_info` VALUES ('mobile-phone--pencil.png');
INSERT INTO `icon_res_info` VALUES ('ruler--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('scissors--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('ui-color-picker.png');
INSERT INTO `icon_res_info` VALUES ('shortcut-small.png');
INSERT INTO `icon_res_info` VALUES ('application-export.png');
INSERT INTO `icon_res_info` VALUES ('lightning--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('wallet--pencil.png');
INSERT INTO `icon_res_info` VALUES ('eraser--minus.png');
INSERT INTO `icon_res_info` VALUES ('desktop-empty.png');
INSERT INTO `icon_res_info` VALUES ('script-php.png');
INSERT INTO `icon_res_info` VALUES ('photo-album--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('monitor-screensaver.png');
INSERT INTO `icon_res_info` VALUES ('globe-model.png');
INSERT INTO `icon_res_info` VALUES ('printer--arrow.png');
INSERT INTO `icon_res_info` VALUES ('medal-silver-red.png');
INSERT INTO `icon_res_info` VALUES ('postage-stamp--plus.png');
INSERT INTO `icon_res_info` VALUES ('image--pencil.png');
INSERT INTO `icon_res_info` VALUES ('map--arrow.png');
INSERT INTO `icon_res_info` VALUES ('control-skip.png');
INSERT INTO `icon_res_info` VALUES ('key--minus.png');
INSERT INTO `icon_res_info` VALUES ('envelope--plus.png');
INSERT INTO `icon_res_info` VALUES ('user-green.png');
INSERT INTO `icon_res_info` VALUES ('mouse--arrow.png');
INSERT INTO `icon_res_info` VALUES ('cup--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-tab--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('bank--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('box--pencil.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-tree.png');
INSERT INTO `icon_res_info` VALUES ('slide--pencil.png');
INSERT INTO `icon_res_info` VALUES ('balloon-sound.png');
INSERT INTO `icon_res_info` VALUES ('ui-tab--arrow.png');
INSERT INTO `icon_res_info` VALUES ('book-small.png');
INSERT INTO `icon_res_info` VALUES ('color-small.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-o.png');
INSERT INTO `icon_res_info` VALUES ('layout-select-content.png');
INSERT INTO `icon_res_info` VALUES ('flag-green.png');
INSERT INTO `icon_res_info` VALUES ('books.png');
INSERT INTO `icon_res_info` VALUES ('application-dock.png');
INSERT INTO `icon_res_info` VALUES ('sort--arrow.png');
INSERT INTO `icon_res_info` VALUES ('application-share.png');
INSERT INTO `icon_res_info` VALUES ('application-sidebar-collapse.png');
INSERT INTO `icon_res_info` VALUES ('camera-lens.png');
INSERT INTO `icon_res_info` VALUES ('megaphone--minus.png');
INSERT INTO `icon_res_info` VALUES ('monitor-medium.png');
INSERT INTO `icon_res_info` VALUES ('cookie.png');
INSERT INTO `icon_res_info` VALUES ('balloon-white.png');
INSERT INTO `icon_res_info` VALUES ('newspaper--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-merge-270.png');
INSERT INTO `icon_res_info` VALUES ('chart-up-color.png');
INSERT INTO `icon_res_info` VALUES ('cards.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-i.png');
INSERT INTO `icon_res_info` VALUES ('task-select-last.png');
INSERT INTO `icon_res_info` VALUES ('gender.png');
INSERT INTO `icon_res_info` VALUES ('heart-half.png');
INSERT INTO `icon_res_info` VALUES ('megaphone.png');
INSERT INTO `icon_res_info` VALUES ('bandaid--arrow.png');
INSERT INTO `icon_res_info` VALUES ('beaker--minus.png');
INSERT INTO `icon_res_info` VALUES ('headphone--pencil.png');
INSERT INTO `icon_res_info` VALUES ('equalizer.png');
INSERT INTO `icon_res_info` VALUES ('target--pencil.png');
INSERT INTO `icon_res_info` VALUES ('keyboard--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-stand.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note-small.png');
INSERT INTO `icon_res_info` VALUES ('telephone--arrow.png');
INSERT INTO `icon_res_info` VALUES ('balloon--pencil.png');
INSERT INTO `icon_res_info` VALUES ('image-import.png');
INSERT INTO `icon_res_info` VALUES ('smiley-razz.png');
INSERT INTO `icon_res_info` VALUES ('drill--pencil.png');
INSERT INTO `icon_res_info` VALUES ('arrow-branch-090-left.png');
INSERT INTO `icon_res_info` VALUES ('door-open.png');
INSERT INTO `icon_res_info` VALUES ('layout-split.png');
INSERT INTO `icon_res_info` VALUES ('xfn.png');
INSERT INTO `icon_res_info` VALUES ('pipette--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('money--arrow.png');
INSERT INTO `icon_res_info` VALUES ('arrow-return-270.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-hf-select.png');
INSERT INTO `icon_res_info` VALUES ('blog--plus.png');
INSERT INTO `icon_res_info` VALUES ('inbox-image.png');
INSERT INTO `icon_res_info` VALUES ('smiley-mad.png');
INSERT INTO `icon_res_info` VALUES ('layout-header-2-equal.png');
INSERT INTO `icon_res_info` VALUES ('border-weight.png');
INSERT INTO `icon_res_info` VALUES ('blue-documents.png');
INSERT INTO `icon_res_info` VALUES ('bookmarks.png');
INSERT INTO `icon_res_info` VALUES ('traffic-light--plus.png');
INSERT INTO `icon_res_info` VALUES ('external.png');
INSERT INTO `icon_res_info` VALUES ('calendar-select.png');
INSERT INTO `icon_res_info` VALUES ('edit-mathematics.png');
INSERT INTO `icon_res_info` VALUES ('application-dock-090.png');
INSERT INTO `icon_res_info` VALUES ('control-skip-270.png');
INSERT INTO `icon_res_info` VALUES ('folder-open.png');
INSERT INTO `icon_res_info` VALUES ('sort-price.png');
INSERT INTO `icon_res_info` VALUES ('pill--arrow.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-octagon-frame.png');
INSERT INTO `icon_res_info` VALUES ('open-share.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-j.png');
INSERT INTO `icon_res_info` VALUES ('media-player-medium-red.png');
INSERT INTO `icon_res_info` VALUES ('mail--minus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('table--arrow.png');
INSERT INTO `icon_res_info` VALUES ('media-player-phone.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-field-password-green.png');
INSERT INTO `icon_res_info` VALUES ('funnel.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-f.png');
INSERT INTO `icon_res_info` VALUES ('computer-network.png');
INSERT INTO `icon_res_info` VALUES ('hammer--minus.png');
INSERT INTO `icon_res_info` VALUES ('hourglass--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('home-medium.png');
INSERT INTO `icon_res_info` VALUES ('receipt--pencil.png');
INSERT INTO `icon_res_info` VALUES ('edit-size.png');
INSERT INTO `icon_res_info` VALUES ('navigation.png');
INSERT INTO `icon_res_info` VALUES ('document-sub.png');
INSERT INTO `icon_res_info` VALUES ('report--pencil.png');
INSERT INTO `icon_res_info` VALUES ('layer-resize-replicate.png');
INSERT INTO `icon_res_info` VALUES ('document-sticky-note.png');
INSERT INTO `icon_res_info` VALUES ('film--arrow.png');
INSERT INTO `icon_res_info` VALUES ('leaf--pencil.png');
INSERT INTO `icon_res_info` VALUES ('auction-hammer-gavel.png');
INSERT INTO `icon_res_info` VALUES ('crown-bronze.png');
INSERT INTO `icon_res_info` VALUES ('arrow-skip-090.png');
INSERT INTO `icon_res_info` VALUES ('arrow-000-medium.png');
INSERT INTO `icon_res_info` VALUES ('ui-toolbar--minus.png');
INSERT INTO `icon_res_info` VALUES ('bell--minus.png');
INSERT INTO `icon_res_info` VALUES ('smiley-red.png');
INSERT INTO `icon_res_info` VALUES ('task--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('user-business.png');
INSERT INTO `icon_res_info` VALUES ('magnifier--pencil.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-diamond.png');
INSERT INTO `icon_res_info` VALUES ('ruler--plus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-in.png');
INSERT INTO `icon_res_info` VALUES ('border-bottom.png');
INSERT INTO `icon_res_info` VALUES ('ui-tab-content.png');
INSERT INTO `icon_res_info` VALUES ('arrow-merge-180-left.png');
INSERT INTO `icon_res_info` VALUES ('asterisk-small-yellow.png');
INSERT INTO `icon_res_info` VALUES ('service-bell--arrow.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve-180.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-page-next.png');
INSERT INTO `icon_res_info` VALUES ('database-delete.png');
INSERT INTO `icon_res_info` VALUES ('map--pencil.png');
INSERT INTO `icon_res_info` VALUES ('stamp--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('construction.png');
INSERT INTO `icon_res_info` VALUES ('arrow-270-medium.png');
INSERT INTO `icon_res_info` VALUES ('script--pencil.png');
INSERT INTO `icon_res_info` VALUES ('arrow-turn-180.png');
INSERT INTO `icon_res_info` VALUES ('toolbox.png');
INSERT INTO `icon_res_info` VALUES ('bean--minus.png');
INSERT INTO `icon_res_info` VALUES ('edit-image-right.png');
INSERT INTO `icon_res_info` VALUES ('wand--minus.png');
INSERT INTO `icon_res_info` VALUES ('calendar-select-week.png');
INSERT INTO `icon_res_info` VALUES ('alarm-clock--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('leaf-wormhole.png');
INSERT INTO `icon_res_info` VALUES ('ui-tab.png');
INSERT INTO `icon_res_info` VALUES ('table-medium.png');
INSERT INTO `icon_res_info` VALUES ('postage-stamp.png');
INSERT INTO `icon_res_info` VALUES ('address-book.png');
INSERT INTO `icon_res_info` VALUES ('drill--plus.png');
INSERT INTO `icon_res_info` VALUES ('sum.png');
INSERT INTO `icon_res_info` VALUES ('mahjong--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('jar--pencil.png');
INSERT INTO `icon_res_info` VALUES ('clock--arrow.png');
INSERT INTO `icon_res_info` VALUES ('broom.png');
INSERT INTO `icon_res_info` VALUES ('control-power.png');
INSERT INTO `icon_res_info` VALUES ('table-insert-column.png');
INSERT INTO `icon_res_info` VALUES ('flashlight-shine.png');
INSERT INTO `icon_res_info` VALUES ('marker--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-small-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('database-small.png');
INSERT INTO `icon_res_info` VALUES ('calendar-task.png');
INSERT INTO `icon_res_info` VALUES ('node-delete-child.png');
INSERT INTO `icon_res_info` VALUES ('pda--arrow.png');
INSERT INTO `icon_res_info` VALUES ('ui-paginator.png');
INSERT INTO `icon_res_info` VALUES ('node-delete.png');
INSERT INTO `icon_res_info` VALUES ('border-vertical-all.png');
INSERT INTO `icon_res_info` VALUES ('node-select-child.png');
INSERT INTO `icon_res_info` VALUES ('address-book-open.png');
INSERT INTO `icon_res_info` VALUES ('application-import.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-x.png');
INSERT INTO `icon_res_info` VALUES ('receipts-text.png');
INSERT INTO `icon_res_info` VALUES ('ui-button-default.png');
INSERT INTO `icon_res_info` VALUES ('balance-unbalance.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-field-suggestion.png');
INSERT INTO `icon_res_info` VALUES ('equalizer--plus.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-o.png');
INSERT INTO `icon_res_info` VALUES ('pda--minus.png');
INSERT INTO `icon_res_info` VALUES ('book-question.png');
INSERT INTO `icon_res_info` VALUES ('mail--pencil.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb--plus.png');
INSERT INTO `icon_res_info` VALUES ('user-red.png');
INSERT INTO `icon_res_info` VALUES ('counter-count.png');
INSERT INTO `icon_res_info` VALUES ('edit-size-up.png');
INSERT INTO `icon_res_info` VALUES ('share-balloon.png');
INSERT INTO `icon_res_info` VALUES ('tick-small-circle.png');
INSERT INTO `icon_res_info` VALUES ('ui-color-picker-switch.png');
INSERT INTO `icon_res_info` VALUES ('folder-open-table.png');
INSERT INTO `icon_res_info` VALUES ('database-medium.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-w.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-r.png');
INSERT INTO `icon_res_info` VALUES ('highlighter.png');
INSERT INTO `icon_res_info` VALUES ('cutleries.png');
INSERT INTO `icon_res_info` VALUES ('information-shield.png');
INSERT INTO `icon_res_info` VALUES ('folder-open-document-music.png');
INSERT INTO `icon_res_info` VALUES ('newspaper--minus.png');
INSERT INTO `icon_res_info` VALUES ('zone-money.png');
INSERT INTO `icon_res_info` VALUES ('postage-stamp--pencil.png');
INSERT INTO `icon_res_info` VALUES ('fire--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('user-thief-female.png');
INSERT INTO `icon_res_info` VALUES ('rocket--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('globe-medium.png');
INSERT INTO `icon_res_info` VALUES ('databases.png');
INSERT INTO `icon_res_info` VALUES ('plug--minus.png');
INSERT INTO `icon_res_info` VALUES ('na.png');
INSERT INTO `icon_res_info` VALUES ('information-small-white.png');
INSERT INTO `icon_res_info` VALUES ('arrow-return-090.png');
INSERT INTO `icon_res_info` VALUES ('border-all.png');
INSERT INTO `icon_res_info` VALUES ('media-player-phone-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('system-monitor--arrow.png');
INSERT INTO `icon_res_info` VALUES ('network-wireless.png');
INSERT INTO `icon_res_info` VALUES ('paint-can--plus.png');
INSERT INTO `icon_res_info` VALUES ('ice--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('document-hf-select.png');
INSERT INTO `icon_res_info` VALUES ('safe--minus.png');
INSERT INTO `icon_res_info` VALUES ('validation-invalid-document.png');
INSERT INTO `icon_res_info` VALUES ('cup-tea.png');
INSERT INTO `icon_res_info` VALUES ('wand--arrow.png');
INSERT INTO `icon_res_info` VALUES ('image-sunset.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-u.png');
INSERT INTO `icon_res_info` VALUES ('wooden-box--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('credit-card--pencil.png');
INSERT INTO `icon_res_info` VALUES ('arrow-180-small.png');
INSERT INTO `icon_res_info` VALUES ('envelope-label.png');
INSERT INTO `icon_res_info` VALUES ('disc--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-hf-delete.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb-code.png');
INSERT INTO `icon_res_info` VALUES ('cursor-question.png');
INSERT INTO `icon_res_info` VALUES ('glass--plus.png');
INSERT INTO `icon_res_info` VALUES ('safe--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('calculator--plus.png');
INSERT INTO `icon_res_info` VALUES ('pillow.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-field-select.png');
INSERT INTO `icon_res_info` VALUES ('clock-select.png');
INSERT INTO `icon_res_info` VALUES ('megaphone--arrow.png');
INSERT INTO `icon_res_info` VALUES ('user-medical-female.png');
INSERT INTO `icon_res_info` VALUES ('present--pencil.png');
INSERT INTO `icon_res_info` VALUES ('briefcase--plus.png');
INSERT INTO `icon_res_info` VALUES ('rocket-fly.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-s.png');
INSERT INTO `icon_res_info` VALUES ('application-search-result.png');
INSERT INTO `icon_res_info` VALUES ('gear--pencil.png');
INSERT INTO `icon_res_info` VALUES ('folder-stand.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-zipper.png');
INSERT INTO `icon_res_info` VALUES ('ui-panel.png');
INSERT INTO `icon_res_info` VALUES ('arrow-stop-270.png');
INSERT INTO `icon_res_info` VALUES ('monitor--pencil.png');
INSERT INTO `icon_res_info` VALUES ('application-tree.png');
INSERT INTO `icon_res_info` VALUES ('sitemap-application.png');
INSERT INTO `icon_res_info` VALUES ('bean--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blueprint.png');
INSERT INTO `icon_res_info` VALUES ('counter-reset.png');
INSERT INTO `icon_res_info` VALUES ('clapperboard--arrow.png');
INSERT INTO `icon_res_info` VALUES ('feed--pencil.png');
INSERT INTO `icon_res_info` VALUES ('border-dash.png');
INSERT INTO `icon_res_info` VALUES ('clock.png');
INSERT INTO `icon_res_info` VALUES ('scanner--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('discs.png');
INSERT INTO `icon_res_info` VALUES ('telephone--minus.png');
INSERT INTO `icon_res_info` VALUES ('odata.png');
INSERT INTO `icon_res_info` VALUES ('camera.png');
INSERT INTO `icon_res_info` VALUES ('truck--pencil.png');
INSERT INTO `icon_res_info` VALUES ('control-skip-090.png');
INSERT INTO `icon_res_info` VALUES ('wall-break.png');
INSERT INTO `icon_res_info` VALUES ('door--pencil.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-history-left.png');
INSERT INTO `icon_res_info` VALUES ('document-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('control-pause-small.png');
INSERT INTO `icon_res_info` VALUES ('speaker--minus.png');
INSERT INTO `icon_res_info` VALUES ('cake--arrow.png');
INSERT INTO `icon_res_info` VALUES ('image-crop.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve-270-left.png');
INSERT INTO `icon_res_info` VALUES ('layout-select.png');
INSERT INTO `icon_res_info` VALUES ('book-open-list.png');
INSERT INTO `icon_res_info` VALUES ('image-empty.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open-film.png');
INSERT INTO `icon_res_info` VALUES ('document-bookmark.png');
INSERT INTO `icon_res_info` VALUES ('layout.png');
INSERT INTO `icon_res_info` VALUES ('medal.png');
INSERT INTO `icon_res_info` VALUES ('book.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-resize-actual.png');
INSERT INTO `icon_res_info` VALUES ('arrow-repeat.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('bandaid--minus.png');
INSERT INTO `icon_res_info` VALUES ('pda.png');
INSERT INTO `icon_res_info` VALUES ('receipt-medium.png');
INSERT INTO `icon_res_info` VALUES ('chart-pie.png');
INSERT INTO `icon_res_info` VALUES ('image-reflection.png');
INSERT INTO `icon_res_info` VALUES ('bookmark-export.png');
INSERT INTO `icon_res_info` VALUES ('speaker--arrow.png');
INSERT INTO `icon_res_info` VALUES ('tick-small.png');
INSERT INTO `icon_res_info` VALUES ('mobile-phone-cast.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-history.png');
INSERT INTO `icon_res_info` VALUES ('auction-hammer--plus.png');
INSERT INTO `icon_res_info` VALUES ('clock--minus.png');
INSERT INTO `icon_res_info` VALUES ('smiley-neutral.png');
INSERT INTO `icon_res_info` VALUES ('balance--plus.png');
INSERT INTO `icon_res_info` VALUES ('points.png');
INSERT INTO `icon_res_info` VALUES ('wallet.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-left.png');
INSERT INTO `icon_res_info` VALUES ('calculator--minus.png');
INSERT INTO `icon_res_info` VALUES ('mail-medium.png');
INSERT INTO `icon_res_info` VALUES ('tick-small-red.png');
INSERT INTO `icon_res_info` VALUES ('traffic-light--pencil.png');
INSERT INTO `icon_res_info` VALUES ('database-share.png');
INSERT INTO `icon_res_info` VALUES ('ui-radio-buttons.png');
INSERT INTO `icon_res_info` VALUES ('arrow-step-over.png');
INSERT INTO `icon_res_info` VALUES ('arrow-move.png');
INSERT INTO `icon_res_info` VALUES ('wrench--plus.png');
INSERT INTO `icon_res_info` VALUES ('hourglass.png');
INSERT INTO `icon_res_info` VALUES ('edit-subscript.png');
INSERT INTO `icon_res_info` VALUES ('layers-alignment-bottom.png');
INSERT INTO `icon_res_info` VALUES ('t-shirt-print-gray.png');
INSERT INTO `icon_res_info` VALUES ('key-solid.png');
INSERT INTO `icon_res_info` VALUES ('book--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder--minus.png');
INSERT INTO `icon_res_info` VALUES ('application-dock-180.png');
INSERT INTO `icon_res_info` VALUES ('folder-tree.png');
INSERT INTO `icon_res_info` VALUES ('layer-shape.png');
INSERT INTO `icon_res_info` VALUES ('edit-small-caps.png');
INSERT INTO `icon_res_info` VALUES ('script--arrow.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle.png');
INSERT INTO `icon_res_info` VALUES ('price-tag--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('cutlery-knife.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open-feed.png');
INSERT INTO `icon_res_info` VALUES ('edit-column.png');
INSERT INTO `icon_res_info` VALUES ('burn--arrow.png');
INSERT INTO `icon_res_info` VALUES ('toolbox--arrow.png');
INSERT INTO `icon_res_info` VALUES ('wall-small.png');
INSERT INTO `icon_res_info` VALUES ('arrow-resize.png');
INSERT INTO `icon_res_info` VALUES ('toggle-small.png');
INSERT INTO `icon_res_info` VALUES ('hand-point.png');
INSERT INTO `icon_res_info` VALUES ('document--plus.png');
INSERT INTO `icon_res_info` VALUES ('target--plus.png');
INSERT INTO `icon_res_info` VALUES ('hard-hat--minus.png');
INSERT INTO `icon_res_info` VALUES ('balloons-twitter-box.png');
INSERT INTO `icon_res_info` VALUES ('share-small.png');
INSERT INTO `icon_res_info` VALUES ('computer--arrow.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-code.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open-document.png');
INSERT INTO `icon_res_info` VALUES ('funnel-small.png');
INSERT INTO `icon_res_info` VALUES ('hourglass--arrow.png');
INSERT INTO `icon_res_info` VALUES ('fire.png');
INSERT INTO `icon_res_info` VALUES ('printer--pencil.png');
INSERT INTO `icon_res_info` VALUES ('drawer-open.png');
INSERT INTO `icon_res_info` VALUES ('book-open-next.png');
INSERT INTO `icon_res_info` VALUES ('plug--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('layer--minus.png');
INSERT INTO `icon_res_info` VALUES ('rocket.png');
INSERT INTO `icon_res_info` VALUES ('card-import.png');
INSERT INTO `icon_res_info` VALUES ('chain-unchain.png');
INSERT INTO `icon_res_info` VALUES ('border-color.png');
INSERT INTO `icon_res_info` VALUES ('node-magnifier.png');
INSERT INTO `icon_res_info` VALUES ('rainbow.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-zoom-actual.png');
INSERT INTO `icon_res_info` VALUES ('contrast-small-low.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-import.png');
INSERT INTO `icon_res_info` VALUES ('telephone-network.png');
INSERT INTO `icon_res_info` VALUES ('disk-small-black.png');
INSERT INTO `icon_res_info` VALUES ('film--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('webcam-share.png');
INSERT INTO `icon_res_info` VALUES ('telephone-medium.png');
INSERT INTO `icon_res_info` VALUES ('smiley-kitty.png');
INSERT INTO `icon_res_info` VALUES ('layer-resize-actual.png');
INSERT INTO `icon_res_info` VALUES ('medal--arrow.png');
INSERT INTO `icon_res_info` VALUES ('bell--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('magnet--pencil.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb-small-off.png');
INSERT INTO `icon_res_info` VALUES ('application-home.png');
INSERT INTO `icon_res_info` VALUES ('cross-script.png');
INSERT INTO `icon_res_info` VALUES ('compile-warning.png');
INSERT INTO `icon_res_info` VALUES ('subversion-small.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note.png');
INSERT INTO `icon_res_info` VALUES ('document-hf-insert-footer.png');
INSERT INTO `icon_res_info` VALUES ('grid.png');
INSERT INTO `icon_res_info` VALUES ('navigation-090-white.png');
INSERT INTO `icon_res_info` VALUES ('ui-separator.png');
INSERT INTO `icon_res_info` VALUES ('receipt-share.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane.png');
INSERT INTO `icon_res_info` VALUES ('information.png');
INSERT INTO `icon_res_info` VALUES ('newspapers.png');
INSERT INTO `icon_res_info` VALUES ('globe-medium-green.png');
INSERT INTO `icon_res_info` VALUES ('chart--arrow.png');
INSERT INTO `icon_res_info` VALUES ('blue-document--minus.png');
INSERT INTO `icon_res_info` VALUES ('address-book--arrow.png');
INSERT INTO `icon_res_info` VALUES ('control-090-small.png');
INSERT INTO `icon_res_info` VALUES ('feed--minus.png');
INSERT INTO `icon_res_info` VALUES ('beaker-empty.png');
INSERT INTO `icon_res_info` VALUES ('fingerprint.png');
INSERT INTO `icon_res_info` VALUES ('paint-can--pencil.png');
INSERT INTO `icon_res_info` VALUES ('subversion.png');
INSERT INTO `icon_res_info` VALUES ('headphone--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('globe-small.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-p.png');
INSERT INTO `icon_res_info` VALUES ('minus-shield.png');
INSERT INTO `icon_res_info` VALUES ('building-medium.png');
INSERT INTO `icon_res_info` VALUES ('wall--pencil.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-empty.png');
INSERT INTO `icon_res_info` VALUES ('document-tree.png');
INSERT INTO `icon_res_info` VALUES ('stamp-medium.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-small.png');
INSERT INTO `icon_res_info` VALUES ('geotag-balloon.png');
INSERT INTO `icon_res_info` VALUES ('edit-pilcrow.png');
INSERT INTO `icon_res_info` VALUES ('layout-6.png');
INSERT INTO `icon_res_info` VALUES ('edit-padding-right.png');
INSERT INTO `icon_res_info` VALUES ('cross-small.png');
INSERT INTO `icon_res_info` VALUES ('receipt-invoice.png');
INSERT INTO `icon_res_info` VALUES ('document-search-result.png');
INSERT INTO `icon_res_info` VALUES ('book-open-text.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-search-result.png');
INSERT INTO `icon_res_info` VALUES ('document-flash-movie.png');
INSERT INTO `icon_res_info` VALUES ('layers-ungroup.png');
INSERT INTO `icon_res_info` VALUES ('document-hf.png');
INSERT INTO `icon_res_info` VALUES ('mahjong-white.png');
INSERT INTO `icon_res_info` VALUES ('envelope-share.png');
INSERT INTO `icon_res_info` VALUES ('bin--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-button.png');
INSERT INTO `icon_res_info` VALUES ('layout-2-equal.png');
INSERT INTO `icon_res_info` VALUES ('control-stop.png');
INSERT INTO `icon_res_info` VALUES ('store.png');
INSERT INTO `icon_res_info` VALUES ('palette--pencil.png');
INSERT INTO `icon_res_info` VALUES ('television--plus.png');
INSERT INTO `icon_res_info` VALUES ('notebook--minus.png');
INSERT INTO `icon_res_info` VALUES ('card--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('safe--plus.png');
INSERT INTO `icon_res_info` VALUES ('infocard-small.png');
INSERT INTO `icon_res_info` VALUES ('tag--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('paper-plane--minus.png');
INSERT INTO `icon_res_info` VALUES ('control-stop-180-small.png');
INSERT INTO `icon_res_info` VALUES ('table-join-column.png');
INSERT INTO `icon_res_info` VALUES ('control-double-090.png');
INSERT INTO `icon_res_info` VALUES ('layer-vector.png');
INSERT INTO `icon_res_info` VALUES ('funnel--minus.png');
INSERT INTO `icon_res_info` VALUES ('highlighter--minus.png');
INSERT INTO `icon_res_info` VALUES ('database--pencil.png');
INSERT INTO `icon_res_info` VALUES ('printer--plus.png');
INSERT INTO `icon_res_info` VALUES ('flag-purple.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve-180-double.png');
INSERT INTO `icon_res_info` VALUES ('cup--arrow.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-h.png');
INSERT INTO `icon_res_info` VALUES ('report--plus.png');
INSERT INTO `icon_res_info` VALUES ('wand.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-v.png');
INSERT INTO `icon_res_info` VALUES ('diamond.png');
INSERT INTO `icon_res_info` VALUES ('clock-select-remain.png');
INSERT INTO `icon_res_info` VALUES ('crown--pencil.png');
INSERT INTO `icon_res_info` VALUES ('picture-sunset.png');
INSERT INTO `icon_res_info` VALUES ('arrow-270-small.png');
INSERT INTO `icon_res_info` VALUES ('plug--arrow.png');
INSERT INTO `icon_res_info` VALUES ('user-medium-female.png');
INSERT INTO `icon_res_info` VALUES ('spray.png');
INSERT INTO `icon_res_info` VALUES ('wall--arrow.png');
INSERT INTO `icon_res_info` VALUES ('pipette--plus.png');
INSERT INTO `icon_res_info` VALUES ('balloon-facebook.png');
INSERT INTO `icon_res_info` VALUES ('node-insert.png');
INSERT INTO `icon_res_info` VALUES ('layers.png');
INSERT INTO `icon_res_info` VALUES ('shopping-basket--pencil.png');
INSERT INTO `icon_res_info` VALUES ('auction-hammer.png');
INSERT INTO `icon_res_info` VALUES ('arrow-switch-090.png');
INSERT INTO `icon_res_info` VALUES ('calendar--pencil.png');
INSERT INTO `icon_res_info` VALUES ('playing-card--plus.png');
INSERT INTO `icon_res_info` VALUES ('sockets.png');
INSERT INTO `icon_res_info` VALUES ('arrow-resize-045.png');
INSERT INTO `icon_res_info` VALUES ('edit-decimal-decrease.png');
INSERT INTO `icon_res_info` VALUES ('calendar-medium.png');
INSERT INTO `icon_res_info` VALUES ('border.png');
INSERT INTO `icon_res_info` VALUES ('mail-reply-all.png');
INSERT INTO `icon_res_info` VALUES ('layer-shape-polygon.png');
INSERT INTO `icon_res_info` VALUES ('navigation-180-frame.png');
INSERT INTO `icon_res_info` VALUES ('cup-empty.png');
INSERT INTO `icon_res_info` VALUES ('wheel.png');
INSERT INTO `icon_res_info` VALUES ('socket--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('calendar-export.png');
INSERT INTO `icon_res_info` VALUES ('crown--arrow.png');
INSERT INTO `icon_res_info` VALUES ('document-text-image.png');
INSERT INTO `icon_res_info` VALUES ('smiley-small.png');
INSERT INTO `icon_res_info` VALUES ('arrow-merge-180.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-rename.png');
INSERT INTO `icon_res_info` VALUES ('projection-screen--arrow.png');
INSERT INTO `icon_res_info` VALUES ('application-small-list.png');
INSERT INTO `icon_res_info` VALUES ('bandaid--pencil.png');
INSERT INTO `icon_res_info` VALUES ('spray-medium.png');
INSERT INTO `icon_res_info` VALUES ('layers-arrange.png');
INSERT INTO `icon_res_info` VALUES ('bookmark--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('paint-tube--arrow.png');
INSERT INTO `icon_res_info` VALUES ('scanner.png');
INSERT INTO `icon_res_info` VALUES ('node-delete-next.png');
INSERT INTO `icon_res_info` VALUES ('ui-status-bar-blue.png');
INSERT INTO `icon_res_info` VALUES ('quill--minus.png');
INSERT INTO `icon_res_info` VALUES ('book-small-brown.png');
INSERT INTO `icon_res_info` VALUES ('users.png');
INSERT INTO `icon_res_info` VALUES ('balloon.png');
INSERT INTO `icon_res_info` VALUES ('socket.png');
INSERT INTO `icon_res_info` VALUES ('ui-tooltip-balloon.png');
INSERT INTO `icon_res_info` VALUES ('microphone--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-task.png');
INSERT INTO `icon_res_info` VALUES ('arrow-continue-180-top.png');
INSERT INTO `icon_res_info` VALUES ('cake--plus.png');
INSERT INTO `icon_res_info` VALUES ('server-medium.png');
INSERT INTO `icon_res_info` VALUES ('eraser--pencil.png');
INSERT INTO `icon_res_info` VALUES ('tick-octagon.png');
INSERT INTO `icon_res_info` VALUES ('open-share-small.png');
INSERT INTO `icon_res_info` VALUES ('webcam.png');
INSERT INTO `icon_res_info` VALUES ('control-pause-record.png');
INSERT INTO `icon_res_info` VALUES ('door-open-in.png');
INSERT INTO `icon_res_info` VALUES ('alarm-clock-select-remain.png');
INSERT INTO `icon_res_info` VALUES ('application-document.png');
INSERT INTO `icon_res_info` VALUES ('receipt-excel-text.png');
INSERT INTO `icon_res_info` VALUES ('arrow-branch-180.png');
INSERT INTO `icon_res_info` VALUES ('weather-cloudy.png');
INSERT INTO `icon_res_info` VALUES ('soap-body.png');
INSERT INTO `icon_res_info` VALUES ('bell--plus.png');
INSERT INTO `icon_res_info` VALUES ('smiley-confuse.png');
INSERT INTO `icon_res_info` VALUES ('wrench--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('ui-tab--minus.png');
INSERT INTO `icon_res_info` VALUES ('cross-octagon-frame.png');
INSERT INTO `icon_res_info` VALUES ('snowman-hat.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-list.png');
INSERT INTO `icon_res_info` VALUES ('new.png');
INSERT INTO `icon_res_info` VALUES ('compass--minus.png');
INSERT INTO `icon_res_info` VALUES ('broom--arrow.png');
INSERT INTO `icon_res_info` VALUES ('ui-check-boxes.png');
INSERT INTO `icon_res_info` VALUES ('document-illustrator.png');
INSERT INTO `icon_res_info` VALUES ('moneys.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-rename.png');
INSERT INTO `icon_res_info` VALUES ('compile-error.png');
INSERT INTO `icon_res_info` VALUES ('home--pencil.png');
INSERT INTO `icon_res_info` VALUES ('hand-point-270.png');
INSERT INTO `icon_res_info` VALUES ('edit-outdent.png');
INSERT INTO `icon_res_info` VALUES ('grid-snap.png');
INSERT INTO `icon_res_info` VALUES ('ui-accordion.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-stamp.png');
INSERT INTO `icon_res_info` VALUES ('layer-shred.png');
INSERT INTO `icon_res_info` VALUES ('drive.png');
INSERT INTO `icon_res_info` VALUES ('arrow-branch-270-left.png');
INSERT INTO `icon_res_info` VALUES ('socket--plus.png');
INSERT INTO `icon_res_info` VALUES ('ice--pencil.png');
INSERT INTO `icon_res_info` VALUES ('asterisk-small.png');
INSERT INTO `icon_res_info` VALUES ('calendar-next.png');
INSERT INTO `icon_res_info` VALUES ('light-bulb--arrow.png');
INSERT INTO `icon_res_info` VALUES ('spray-color.png');
INSERT INTO `icon_res_info` VALUES ('minus.png');
INSERT INTO `icon_res_info` VALUES ('media-player-medium-pink.png');
INSERT INTO `icon_res_info` VALUES ('cookie--plus.png');
INSERT INTO `icon_res_info` VALUES ('exclamation.png');
INSERT INTO `icon_res_info` VALUES ('drawer--pencil.png');
INSERT INTO `icon_res_info` VALUES ('table-select.png');
INSERT INTO `icon_res_info` VALUES ('document-table.png');
INSERT INTO `icon_res_info` VALUES ('cassette--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-small.png');
INSERT INTO `icon_res_info` VALUES ('money.png');
INSERT INTO `icon_res_info` VALUES ('arrow-retweet.png');
INSERT INTO `icon_res_info` VALUES ('calendar-relation.png');
INSERT INTO `icon_res_info` VALUES ('chair--minus.png');
INSERT INTO `icon_res_info` VALUES ('clipboard--arrow.png');
INSERT INTO `icon_res_info` VALUES ('control-cursor.png');
INSERT INTO `icon_res_info` VALUES ('balloon-buzz-left.png');
INSERT INTO `icon_res_info` VALUES ('server--pencil.png');
INSERT INTO `icon_res_info` VALUES ('ruler-crop.png');
INSERT INTO `icon_res_info` VALUES ('border-outside-thick.png');
INSERT INTO `icon_res_info` VALUES ('arrow-merge-000-left.png');
INSERT INTO `icon_res_info` VALUES ('arrow-045.png');
INSERT INTO `icon_res_info` VALUES ('selection.png');
INSERT INTO `icon_res_info` VALUES ('mail-open-document-music.png');
INSERT INTO `icon_res_info` VALUES ('arrow-turn-180-left.png');
INSERT INTO `icon_res_info` VALUES ('store--arrow.png');
INSERT INTO `icon_res_info` VALUES ('arrow-turn-000-left.png');
INSERT INTO `icon_res_info` VALUES ('address-book--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('control-double.png');
INSERT INTO `icon_res_info` VALUES ('tag--plus.png');
INSERT INTO `icon_res_info` VALUES ('cake--pencil.png');
INSERT INTO `icon_res_info` VALUES ('lifebuoy--pencil.png');
INSERT INTO `icon_res_info` VALUES ('credit-card--plus.png');
INSERT INTO `icon_res_info` VALUES ('tag-label.png');
INSERT INTO `icon_res_info` VALUES ('pda--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('application-blue.png');
INSERT INTO `icon_res_info` VALUES ('mouse-select-right.png');
INSERT INTO `icon_res_info` VALUES ('microphone--plus.png');
INSERT INTO `icon_res_info` VALUES ('ice--plus.png');
INSERT INTO `icon_res_info` VALUES ('briefcase--arrow.png');
INSERT INTO `icon_res_info` VALUES ('clipboard--plus.png');
INSERT INTO `icon_res_info` VALUES ('cookie-bite.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-text.png');
INSERT INTO `icon_res_info` VALUES ('battery-low.png');
INSERT INTO `icon_res_info` VALUES ('navigation-000-white.png');
INSERT INTO `icon_res_info` VALUES ('calendar-small-month.png');
INSERT INTO `icon_res_info` VALUES ('ticket--plus.png');
INSERT INTO `icon_res_info` VALUES ('funnel--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('playing-card--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('tag-label-purple.png');
INSERT INTO `icon_res_info` VALUES ('document-office.png');
INSERT INTO `icon_res_info` VALUES ('edit-vertical-alignment.png');
INSERT INTO `icon_res_info` VALUES ('soap.png');
INSERT INTO `icon_res_info` VALUES ('battery-plug.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-list.png');
INSERT INTO `icon_res_info` VALUES ('user-black-female.png');
INSERT INTO `icon_res_info` VALUES ('disc-small.png');
INSERT INTO `icon_res_info` VALUES ('headphone-microphone.png');
INSERT INTO `icon_res_info` VALUES ('language-document.png');
INSERT INTO `icon_res_info` VALUES ('flag--arrow.png');
INSERT INTO `icon_res_info` VALUES ('network.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-c.png');
INSERT INTO `icon_res_info` VALUES ('umbrella--arrow.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-l.png');
INSERT INTO `icon_res_info` VALUES ('drive-upload.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-e.png');
INSERT INTO `icon_res_info` VALUES ('layer-select-point.png');
INSERT INTO `icon_res_info` VALUES ('umbrella--minus.png');
INSERT INTO `icon_res_info` VALUES ('globe-small-green.png');
INSERT INTO `icon_res_info` VALUES ('edit-all-caps.png');
INSERT INTO `icon_res_info` VALUES ('pill.png');
INSERT INTO `icon_res_info` VALUES ('edit-line-spacing.png');
INSERT INTO `icon_res_info` VALUES ('heart--arrow.png');
INSERT INTO `icon_res_info` VALUES ('pencil--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('sofa--minus.png');
INSERT INTO `icon_res_info` VALUES ('building-low.png');
INSERT INTO `icon_res_info` VALUES ('highlighter--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('equalizer--pencil.png');
INSERT INTO `icon_res_info` VALUES ('document-hf-insert.png');
INSERT INTO `icon_res_info` VALUES ('blog--minus.png');
INSERT INTO `icon_res_info` VALUES ('edit-padding-left.png');
INSERT INTO `icon_res_info` VALUES ('edit-quotation.png');
INSERT INTO `icon_res_info` VALUES ('umbrella.png');
INSERT INTO `icon_res_info` VALUES ('puzzle.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open-document-music-playlist.png');
INSERT INTO `icon_res_info` VALUES ('slide--plus.png');
INSERT INTO `icon_res_info` VALUES ('documents.png');
INSERT INTO `icon_res_info` VALUES ('picture--arrow.png');
INSERT INTO `icon_res_info` VALUES ('fill-090.png');
INSERT INTO `icon_res_info` VALUES ('heart-break.png');
INSERT INTO `icon_res_info` VALUES ('database--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('sql-join-outer-exclude.png');
INSERT INTO `icon_res_info` VALUES ('car.png');
INSERT INTO `icon_res_info` VALUES ('folder--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-n.png');
INSERT INTO `icon_res_info` VALUES ('folder-open-document-text.png');
INSERT INTO `icon_res_info` VALUES ('ruler--arrow.png');
INSERT INTO `icon_res_info` VALUES ('grid-dot.png');
INSERT INTO `icon_res_info` VALUES ('scanner--plus.png');
INSERT INTO `icon_res_info` VALUES ('script-stamp.png');
INSERT INTO `icon_res_info` VALUES ('plate.png');
INSERT INTO `icon_res_info` VALUES ('sort--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('globe--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-y.png');
INSERT INTO `icon_res_info` VALUES ('plug-disconnect.png');
INSERT INTO `icon_res_info` VALUES ('cassette--arrow.png');
INSERT INTO `icon_res_info` VALUES ('document-stand.png');
INSERT INTO `icon_res_info` VALUES ('ticket.png');
INSERT INTO `icon_res_info` VALUES ('cutlery.png');
INSERT INTO `icon_res_info` VALUES ('door-open-out.png');
INSERT INTO `icon_res_info` VALUES ('burn.png');
INSERT INTO `icon_res_info` VALUES ('hard-hat.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-j.png');
INSERT INTO `icon_res_info` VALUES ('blueprints.png');
INSERT INTO `icon_res_info` VALUES ('quill--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('disks-black.png');
INSERT INTO `icon_res_info` VALUES ('paper-plane--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('rocket--pencil.png');
INSERT INTO `icon_res_info` VALUES ('brightness-control-up.png');
INSERT INTO `icon_res_info` VALUES ('application--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder.png');
INSERT INTO `icon_res_info` VALUES ('palette-medium.png');
INSERT INTO `icon_res_info` VALUES ('arrow-continue-180.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-share.png');
INSERT INTO `icon_res_info` VALUES ('screwdriver--pencil.png');
INSERT INTO `icon_res_info` VALUES ('receipt--minus.png');
INSERT INTO `icon_res_info` VALUES ('layer-transparent.png');
INSERT INTO `icon_res_info` VALUES ('pin--arrow.png');
INSERT INTO `icon_res_info` VALUES ('user-red-female.png');
INSERT INTO `icon_res_info` VALUES ('weather-clouds.png');
INSERT INTO `icon_res_info` VALUES ('clock--plus.png');
INSERT INTO `icon_res_info` VALUES ('foaf.png');
INSERT INTO `icon_res_info` VALUES ('user-thief.png');
INSERT INTO `icon_res_info` VALUES ('cutter.png');
INSERT INTO `icon_res_info` VALUES ('folder-small.png');
INSERT INTO `icon_res_info` VALUES ('user-yellow.png');
INSERT INTO `icon_res_info` VALUES ('ui-color-picker-transparent.png');
INSERT INTO `icon_res_info` VALUES ('eye--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('color--minus.png');
INSERT INTO `icon_res_info` VALUES ('edit.png');
INSERT INTO `icon_res_info` VALUES ('control-stop-000-small.png');
INSERT INTO `icon_res_info` VALUES ('music--minus.png');
INSERT INTO `icon_res_info` VALUES ('user-gray-female.png');
INSERT INTO `icon_res_info` VALUES ('brightness-small-low.png');
INSERT INTO `icon_res_info` VALUES ('dashboard-network.png');
INSERT INTO `icon_res_info` VALUES ('balance--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-tab--plus.png');
INSERT INTO `icon_res_info` VALUES ('playing-card--minus.png');
INSERT INTO `icon_res_info` VALUES ('image-export.png');
INSERT INTO `icon_res_info` VALUES ('hard-hat-mine.png');
INSERT INTO `icon_res_info` VALUES ('safe--pencil.png');
INSERT INTO `icon_res_info` VALUES ('camera--minus.png');
INSERT INTO `icon_res_info` VALUES ('map-medium.png');
INSERT INTO `icon_res_info` VALUES ('door.png');
INSERT INTO `icon_res_info` VALUES ('camera--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('wall-small-brick.png');
INSERT INTO `icon_res_info` VALUES ('drive-globe.png');
INSERT INTO `icon_res_info` VALUES ('briefcase--pencil.png');
INSERT INTO `icon_res_info` VALUES ('card-export.png');
INSERT INTO `icon_res_info` VALUES ('information-octagon.png');
INSERT INTO `icon_res_info` VALUES ('pin--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('document-photoshop.png');
INSERT INTO `icon_res_info` VALUES ('infocard.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-number.png');
INSERT INTO `icon_res_info` VALUES ('navigation-270-button.png');
INSERT INTO `icon_res_info` VALUES ('control-stop-090-small.png');
INSERT INTO `icon_res_info` VALUES ('monitor-image.png');
INSERT INTO `icon_res_info` VALUES ('party-hat.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open-document-music.png');
INSERT INTO `icon_res_info` VALUES ('plus-octagon-frame.png');
INSERT INTO `icon_res_info` VALUES ('lock--minus.png');
INSERT INTO `icon_res_info` VALUES ('cassette--plus.png');
INSERT INTO `icon_res_info` VALUES ('layer-rotate-left.png');
INSERT INTO `icon_res_info` VALUES ('contrast-control.png');
INSERT INTO `icon_res_info` VALUES ('water--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-table.png');
INSERT INTO `icon_res_info` VALUES ('usb-flash-drive--minus.png');
INSERT INTO `icon_res_info` VALUES ('marker--arrow.png');
INSERT INTO `icon_res_info` VALUES ('user-worker-boss.png');
INSERT INTO `icon_res_info` VALUES ('disc-case-label.png');
INSERT INTO `icon_res_info` VALUES ('sort-alphabet-descending.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-stand.png');
INSERT INTO `icon_res_info` VALUES ('media-player-small-green.png');
INSERT INTO `icon_res_info` VALUES ('door--plus.png');
INSERT INTO `icon_res_info` VALUES ('price-tag-label.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-t.png');
INSERT INTO `icon_res_info` VALUES ('media-player-small-blue.png');
INSERT INTO `icon_res_info` VALUES ('locale.png');
INSERT INTO `icon_res_info` VALUES ('ui-split-panel-vertical.png');
INSERT INTO `icon_res_info` VALUES ('border-inside.png');
INSERT INTO `icon_res_info` VALUES ('pencil-color.png');
INSERT INTO `icon_res_info` VALUES ('chain--minus.png');
INSERT INTO `icon_res_info` VALUES ('monitor--plus.png');
INSERT INTO `icon_res_info` VALUES ('balloons-twitter.png');
INSERT INTO `icon_res_info` VALUES ('application-icon.png');
INSERT INTO `icon_res_info` VALUES ('network-firewall.png');
INSERT INTO `icon_res_info` VALUES ('report-medium.png');
INSERT INTO `icon_res_info` VALUES ('printer-network.png');
INSERT INTO `icon_res_info` VALUES ('flashlight--minus.png');
INSERT INTO `icon_res_info` VALUES ('funnel--arrow.png');
INSERT INTO `icon_res_info` VALUES ('disc--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-stamp.png');
INSERT INTO `icon_res_info` VALUES ('disk.png');
INSERT INTO `icon_res_info` VALUES ('user-black.png');
INSERT INTO `icon_res_info` VALUES ('smiley-sleep.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-broken.png');
INSERT INTO `icon_res_info` VALUES ('water--pencil.png');
INSERT INTO `icon_res_info` VALUES ('disk-share.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve-180-left.png');
INSERT INTO `icon_res_info` VALUES ('control-stop-090.png');
INSERT INTO `icon_res_info` VALUES ('script.png');
INSERT INTO `icon_res_info` VALUES ('globe-share.png');
INSERT INTO `icon_res_info` VALUES ('cutter--arrow.png');
INSERT INTO `icon_res_info` VALUES ('application--arrow.png');
INSERT INTO `icon_res_info` VALUES ('document-visual-studio.png');
INSERT INTO `icon_res_info` VALUES ('book--arrow.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note--plus.png');
INSERT INTO `icon_res_info` VALUES ('jar--arrow.png');
INSERT INTO `icon_res_info` VALUES ('yin-yang.png');
INSERT INTO `icon_res_info` VALUES ('blog-medium.png');
INSERT INTO `icon_res_info` VALUES ('telephone-fax.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-small-red.png');
INSERT INTO `icon_res_info` VALUES ('paper-plane--arrow.png');
INSERT INTO `icon_res_info` VALUES ('script-block.png');
INSERT INTO `icon_res_info` VALUES ('report-word.png');
INSERT INTO `icon_res_info` VALUES ('edit-vertical-alignment-middle.png');
INSERT INTO `icon_res_info` VALUES ('broom--pencil.png');
INSERT INTO `icon_res_info` VALUES ('hammer--arrow.png');
INSERT INTO `icon_res_info` VALUES ('blue-document--pencil.png');
INSERT INTO `icon_res_info` VALUES ('currency-dollar-aud.png');
INSERT INTO `icon_res_info` VALUES ('border-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('ice--arrow.png');
INSERT INTO `icon_res_info` VALUES ('layer-resize.png');
INSERT INTO `icon_res_info` VALUES ('battery-empty.png');
INSERT INTO `icon_res_info` VALUES ('scripts-text.png');
INSERT INTO `icon_res_info` VALUES ('highlighter-color.png');
INSERT INTO `icon_res_info` VALUES ('arrow-join-180.png');
INSERT INTO `icon_res_info` VALUES ('layout-hf-2-equal.png');
INSERT INTO `icon_res_info` VALUES ('server--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('flag--minus.png');
INSERT INTO `icon_res_info` VALUES ('traffic-cone--pencil.png');
INSERT INTO `icon_res_info` VALUES ('open-share-document.png');
INSERT INTO `icon_res_info` VALUES ('user-medium.png');
INSERT INTO `icon_res_info` VALUES ('shield--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('tag-label-green.png');
INSERT INTO `icon_res_info` VALUES ('arrow-turn-270.png');
INSERT INTO `icon_res_info` VALUES ('inbox--pencil.png');
INSERT INTO `icon_res_info` VALUES ('folder-open-feed.png');
INSERT INTO `icon_res_info` VALUES ('minus-small-white.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-office.png');
INSERT INTO `icon_res_info` VALUES ('network-clouds.png');
INSERT INTO `icon_res_info` VALUES ('border-down.png');
INSERT INTO `icon_res_info` VALUES ('layout-header.png');
INSERT INTO `icon_res_info` VALUES ('water--arrow.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-blog.png');
INSERT INTO `icon_res_info` VALUES ('paint-brush-color.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-search-result.png');
INSERT INTO `icon_res_info` VALUES ('user-detective.png');
INSERT INTO `icon_res_info` VALUES ('user-thief-baldie.png');
INSERT INTO `icon_res_info` VALUES ('telephone-share.png');
INSERT INTO `icon_res_info` VALUES ('user-female.png');
INSERT INTO `icon_res_info` VALUES ('feed--arrow.png');
INSERT INTO `icon_res_info` VALUES ('screwdriver--minus.png');
INSERT INTO `icon_res_info` VALUES ('layout-header-3-mix.png');
INSERT INTO `icon_res_info` VALUES ('border-up.png');
INSERT INTO `icon_res_info` VALUES ('grid-small.png');
INSERT INTO `icon_res_info` VALUES ('cup--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('present--minus.png');
INSERT INTO `icon_res_info` VALUES ('control-double-000-small.png');
INSERT INTO `icon_res_info` VALUES ('scissors-blue.png');
INSERT INTO `icon_res_info` VALUES ('mail--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('currency-dollar-nzd.png');
INSERT INTO `icon_res_info` VALUES ('sort-quantity-descending.png');
INSERT INTO `icon_res_info` VALUES ('rocket--arrow.png');
INSERT INTO `icon_res_info` VALUES ('building--pencil.png');
INSERT INTO `icon_res_info` VALUES ('gear--minus.png');
INSERT INTO `icon_res_info` VALUES ('cookie--arrow.png');
INSERT INTO `icon_res_info` VALUES ('validation-label-html.png');
INSERT INTO `icon_res_info` VALUES ('envelope--pencil.png');
INSERT INTO `icon_res_info` VALUES ('mail-open-table.png');
INSERT INTO `icon_res_info` VALUES ('receipts.png');
INSERT INTO `icon_res_info` VALUES ('beaker.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('speaker--plus.png');
INSERT INTO `icon_res_info` VALUES ('edit-direction-rtl.png');
INSERT INTO `icon_res_info` VALUES ('blog--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-illustrator.png');
INSERT INTO `icon_res_info` VALUES ('node-insert-child.png');
INSERT INTO `icon_res_info` VALUES ('webcam--arrow.png');
INSERT INTO `icon_res_info` VALUES ('control-skip-090-small.png');
INSERT INTO `icon_res_info` VALUES ('store-share.png');
INSERT INTO `icon_res_info` VALUES ('edit-number.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle-135.png');
INSERT INTO `icon_res_info` VALUES ('node-design.png');
INSERT INTO `icon_res_info` VALUES ('folder-bookmark.png');
INSERT INTO `icon_res_info` VALUES ('microphone--minus.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-y.png');
INSERT INTO `icon_res_info` VALUES ('table--minus.png');
INSERT INTO `icon_res_info` VALUES ('language.png');
INSERT INTO `icon_res_info` VALUES ('asterisk-yellow.png');
INSERT INTO `icon_res_info` VALUES ('paint-tube--minus.png');
INSERT INTO `icon_res_info` VALUES ('selection-select.png');
INSERT INTO `icon_res_info` VALUES ('ui-splitter-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('toggle-small-expand.png');
INSERT INTO `icon_res_info` VALUES ('document-small-list.png');
INSERT INTO `icon_res_info` VALUES ('ui-toolbar--arrow.png');
INSERT INTO `icon_res_info` VALUES ('camcorder--arrow.png');
INSERT INTO `icon_res_info` VALUES ('calculator-gray.png');
INSERT INTO `icon_res_info` VALUES ('ui-toolbar--pencil.png');
INSERT INTO `icon_res_info` VALUES ('flask--pencil.png');
INSERT INTO `icon_res_info` VALUES ('arrow-135-medium.png');
INSERT INTO `icon_res_info` VALUES ('user--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-button-navigation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-join-270.png');
INSERT INTO `icon_res_info` VALUES ('door--arrow.png');
INSERT INTO `icon_res_info` VALUES ('currency.png');
INSERT INTO `icon_res_info` VALUES ('price-tag--pencil.png');
INSERT INTO `icon_res_info` VALUES ('document-rename.png');
INSERT INTO `icon_res_info` VALUES ('beer.png');
INSERT INTO `icon_res_info` VALUES ('document-outlook.png');
INSERT INTO `icon_res_info` VALUES ('speaker-volume-low.png');
INSERT INTO `icon_res_info` VALUES ('burn-small.png');
INSERT INTO `icon_res_info` VALUES ('validation-valid-document.png');
INSERT INTO `icon_res_info` VALUES ('paint-tube.png');
INSERT INTO `icon_res_info` VALUES ('target--minus.png');
INSERT INTO `icon_res_info` VALUES ('media-player-cast.png');
INSERT INTO `icon_res_info` VALUES ('script--plus.png');
INSERT INTO `icon_res_info` VALUES ('plug--plus.png');
INSERT INTO `icon_res_info` VALUES ('mahjong--pencil.png');
INSERT INTO `icon_res_info` VALUES ('edit-signiture.png');
INSERT INTO `icon_res_info` VALUES ('slides.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-word-text.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-octagon.png');
INSERT INTO `icon_res_info` VALUES ('jar-empty.png');
INSERT INTO `icon_res_info` VALUES ('table--pencil.png');
INSERT INTO `icon_res_info` VALUES ('stamp.png');
INSERT INTO `icon_res_info` VALUES ('metronome--pencil.png');
INSERT INTO `icon_res_info` VALUES ('control-double-270-small.png');
INSERT INTO `icon_res_info` VALUES ('drawer--minus.png');
INSERT INTO `icon_res_info` VALUES ('layout-design.png');
INSERT INTO `icon_res_info` VALUES ('arrow-step-out.png');
INSERT INTO `icon_res_info` VALUES ('folders.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-icon.png');
INSERT INTO `icon_res_info` VALUES ('edit-decimal.png');
INSERT INTO `icon_res_info` VALUES ('license-key.png');
INSERT INTO `icon_res_info` VALUES ('applications-stack.png');
INSERT INTO `icon_res_info` VALUES ('cup.png');
INSERT INTO `icon_res_info` VALUES ('control-double-180-small.png');
INSERT INTO `icon_res_info` VALUES ('book-brown.png');
INSERT INTO `icon_res_info` VALUES ('smiley-mr-green.png');
INSERT INTO `icon_res_info` VALUES ('fruit-lime.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-sticky-note.png');
INSERT INTO `icon_res_info` VALUES ('drive-small.png');
INSERT INTO `icon_res_info` VALUES ('joystick.png');
INSERT INTO `icon_res_info` VALUES ('hard-hat--plus.png');
INSERT INTO `icon_res_info` VALUES ('postage-stamp--arrow.png');
INSERT INTO `icon_res_info` VALUES ('media-player-small-red.png');
INSERT INTO `icon_res_info` VALUES ('cross-circle.png');
INSERT INTO `icon_res_info` VALUES ('direction.png');
INSERT INTO `icon_res_info` VALUES ('sticky-notes.png');
INSERT INTO `icon_res_info` VALUES ('cookie-medium.png');
INSERT INTO `icon_res_info` VALUES ('hammer--pencil.png');
INSERT INTO `icon_res_info` VALUES ('paper-plane--pencil.png');
INSERT INTO `icon_res_info` VALUES ('music--arrow.png');
INSERT INTO `icon_res_info` VALUES ('border-top-bottom-double.png');
INSERT INTO `icon_res_info` VALUES ('wall--plus.png');
INSERT INTO `icon_res_info` VALUES ('validation-document.png');
INSERT INTO `icon_res_info` VALUES ('document-task.png');
INSERT INTO `icon_res_info` VALUES ('disk--plus.png');
INSERT INTO `icon_res_info` VALUES ('document--minus.png');
INSERT INTO `icon_res_info` VALUES ('control-180.png');
INSERT INTO `icon_res_info` VALUES ('crown--minus.png');
INSERT INTO `icon_res_info` VALUES ('sofa--plus.png');
INSERT INTO `icon_res_info` VALUES ('bookmark--pencil.png');
INSERT INTO `icon_res_info` VALUES ('briefcase.png');
INSERT INTO `icon_res_info` VALUES ('star-empty.png');
INSERT INTO `icon_res_info` VALUES ('point--arrow.png');
INSERT INTO `icon_res_info` VALUES ('monitor.png');
INSERT INTO `icon_res_info` VALUES ('document-small.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-w.png');
INSERT INTO `icon_res_info` VALUES ('webcam--pencil.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-x.png');
INSERT INTO `icon_res_info` VALUES ('keyboard.png');
INSERT INTO `icon_res_info` VALUES ('table-select-cells.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-image.png');
INSERT INTO `icon_res_info` VALUES ('monitor-sidebar.png');
INSERT INTO `icon_res_info` VALUES ('e-book-reader-white.png');
INSERT INTO `icon_res_info` VALUES ('blog.png');
INSERT INTO `icon_res_info` VALUES ('status-offline.png');
INSERT INTO `icon_res_info` VALUES ('balloon-twitter-retweet.png');
INSERT INTO `icon_res_info` VALUES ('camera--plus.png');
INSERT INTO `icon_res_info` VALUES ('application-split.png');
INSERT INTO `icon_res_info` VALUES ('flag--pencil.png');
INSERT INTO `icon_res_info` VALUES ('beaker--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('balloon-left.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-q.png');
INSERT INTO `icon_res_info` VALUES ('eraser--arrow.png');
INSERT INTO `icon_res_info` VALUES ('chain-small.png');
INSERT INTO `icon_res_info` VALUES ('palette--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-template.png');
INSERT INTO `icon_res_info` VALUES ('folder-open-document-music-playlist.png');
INSERT INTO `icon_res_info` VALUES ('ticket--pencil.png');
INSERT INTO `icon_res_info` VALUES ('layers-group.png');
INSERT INTO `icon_res_info` VALUES ('edit-padding-top.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-k.png');
INSERT INTO `icon_res_info` VALUES ('store-label.png');
INSERT INTO `icon_res_info` VALUES ('calendar-share.png');
INSERT INTO `icon_res_info` VALUES ('ui-check-box.png');
INSERT INTO `icon_res_info` VALUES ('application-dock-tab.png');
INSERT INTO `icon_res_info` VALUES ('flask-empty.png');
INSERT INTO `icon_res_info` VALUES ('sofa--pencil.png');
INSERT INTO `icon_res_info` VALUES ('ui-label.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-field.png');
INSERT INTO `icon_res_info` VALUES ('lightning--pencil.png');
INSERT INTO `icon_res_info` VALUES ('television.png');
INSERT INTO `icon_res_info` VALUES ('plus-button.png');
INSERT INTO `icon_res_info` VALUES ('money--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('vise-drawer.png');
INSERT INTO `icon_res_info` VALUES ('ui-tab-bottom.png');
INSERT INTO `icon_res_info` VALUES ('map.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-text.png');
INSERT INTO `icon_res_info` VALUES ('currency-dollar-usd.png');
INSERT INTO `icon_res_info` VALUES ('cushion.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-photoshop-image.png');
INSERT INTO `icon_res_info` VALUES ('bank.png');
INSERT INTO `icon_res_info` VALUES ('pin--plus.png');
INSERT INTO `icon_res_info` VALUES ('projection-screen-presentation.png');
INSERT INTO `icon_res_info` VALUES ('ui-tooltip--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('calculator-scientific.png');
INSERT INTO `icon_res_info` VALUES ('dashboard--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('printer.png');
INSERT INTO `icon_res_info` VALUES ('headphone--plus.png');
INSERT INTO `icon_res_info` VALUES ('box-share.png');
INSERT INTO `icon_res_info` VALUES ('arrow-curve-000-double.png');
INSERT INTO `icon_res_info` VALUES ('mail-send-receive.png');
INSERT INTO `icon_res_info` VALUES ('folder-open-image.png');
INSERT INTO `icon_res_info` VALUES ('glass-narrow.png');
INSERT INTO `icon_res_info` VALUES ('photo-album--minus.png');
INSERT INTO `icon_res_info` VALUES ('bean--pencil.png');
INSERT INTO `icon_res_info` VALUES ('image-resize.png');
INSERT INTO `icon_res_info` VALUES ('television--arrow.png');
INSERT INTO `icon_res_info` VALUES ('point--plus.png');
INSERT INTO `icon_res_info` VALUES ('table-paint-can.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-convert.png');
INSERT INTO `icon_res_info` VALUES ('door--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('pencil-small.png');
INSERT INTO `icon_res_info` VALUES ('layer--plus.png');
INSERT INTO `icon_res_info` VALUES ('cassette--pencil.png');
INSERT INTO `icon_res_info` VALUES ('lock--plus.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-d.png');
INSERT INTO `icon_res_info` VALUES ('book-open-text-image.png');
INSERT INTO `icon_res_info` VALUES ('asterisk.png');
INSERT INTO `icon_res_info` VALUES ('bin--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('balloon-smiley.png');
INSERT INTO `icon_res_info` VALUES ('bug--pencil.png');
INSERT INTO `icon_res_info` VALUES ('briefcase--minus.png');
INSERT INTO `icon_res_info` VALUES ('globe--plus.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note-medium.png');
INSERT INTO `icon_res_info` VALUES ('lock-small.png');
INSERT INTO `icon_res_info` VALUES ('bean-small.png');
INSERT INTO `icon_res_info` VALUES ('document-zipper.png');
INSERT INTO `icon_res_info` VALUES ('present-label.png');
INSERT INTO `icon_res_info` VALUES ('weather-cloud.png');
INSERT INTO `icon_res_info` VALUES ('table-share.png');
INSERT INTO `icon_res_info` VALUES ('mask.png');
INSERT INTO `icon_res_info` VALUES ('arrow-merge-270-left.png');
INSERT INTO `icon_res_info` VALUES ('wallet--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('cake.png');
INSERT INTO `icon_res_info` VALUES ('layout-hf-3.png');
INSERT INTO `icon_res_info` VALUES ('grid-small-dot.png');
INSERT INTO `icon_res_info` VALUES ('chart-down-color.png');
INSERT INTO `icon_res_info` VALUES ('mail-share.png');
INSERT INTO `icon_res_info` VALUES ('cassette-label.png');
INSERT INTO `icon_res_info` VALUES ('scissors--minus.png');
INSERT INTO `icon_res_info` VALUES ('megaphone--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-access.png');
INSERT INTO `icon_res_info` VALUES ('spectacle.png');
INSERT INTO `icon_res_info` VALUES ('blueprint--minus.png');
INSERT INTO `icon_res_info` VALUES ('folder-stamp.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-shred.png');
INSERT INTO `icon_res_info` VALUES ('mouse--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('language-balloon.png');
INSERT INTO `icon_res_info` VALUES ('layout-2.png');
INSERT INTO `icon_res_info` VALUES ('calendar-day.png');
INSERT INTO `icon_res_info` VALUES ('binocular--pencil.png');
INSERT INTO `icon_res_info` VALUES ('funnel--pencil.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-z.png');
INSERT INTO `icon_res_info` VALUES ('minus-circle.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-paste.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-invoice.png');
INSERT INTO `icon_res_info` VALUES ('sort-number-descending.png');
INSERT INTO `icon_res_info` VALUES ('blueprint-medium.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-j.png');
INSERT INTO `icon_res_info` VALUES ('image-balloon.png');
INSERT INTO `icon_res_info` VALUES ('database--arrow.png');
INSERT INTO `icon_res_info` VALUES ('drive-medium.png');
INSERT INTO `icon_res_info` VALUES ('magnifier-zoom-fit.png');
INSERT INTO `icon_res_info` VALUES ('server--arrow.png');
INSERT INTO `icon_res_info` VALUES ('mobile-phone--minus.png');
INSERT INTO `icon_res_info` VALUES ('sitemap.png');
INSERT INTO `icon_res_info` VALUES ('minus-small-circle.png');
INSERT INTO `icon_res_info` VALUES ('switch--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('store--minus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-split-270.png');
INSERT INTO `icon_res_info` VALUES ('pipette-color.png');
INSERT INTO `icon_res_info` VALUES ('color--arrow.png');
INSERT INTO `icon_res_info` VALUES ('control-stop-180.png');
INSERT INTO `icon_res_info` VALUES ('user-business-gray-boss.png');
INSERT INTO `icon_res_info` VALUES ('film--minus.png');
INSERT INTO `icon_res_info` VALUES ('color-swatch.png');
INSERT INTO `icon_res_info` VALUES ('money--pencil.png');
INSERT INTO `icon_res_info` VALUES ('media-player-xsmall-polish.png');
INSERT INTO `icon_res_info` VALUES ('database--minus.png');
INSERT INTO `icon_res_info` VALUES ('arrow-resize-090.png');
INSERT INTO `icon_res_info` VALUES ('lightning--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-g.png');
INSERT INTO `icon_res_info` VALUES ('hard-hat-military.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder--pencil.png');
INSERT INTO `icon_res_info` VALUES ('luggage--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-270.png');
INSERT INTO `icon_res_info` VALUES ('plus-circle-frame.png');
INSERT INTO `icon_res_info` VALUES ('paint-tube-medium.png');
INSERT INTO `icon_res_info` VALUES ('zones.png');
INSERT INTO `icon_res_info` VALUES ('layers-alignment-middle.png');
INSERT INTO `icon_res_info` VALUES ('folder-horizontal-open.png');
INSERT INTO `icon_res_info` VALUES ('traffic-light--minus.png');
INSERT INTO `icon_res_info` VALUES ('cross-shield.png');
INSERT INTO `icon_res_info` VALUES ('piano--pencil.png');
INSERT INTO `icon_res_info` VALUES ('eraser.png');
INSERT INTO `icon_res_info` VALUES ('feed.png');
INSERT INTO `icon_res_info` VALUES ('clock-frame.png');
INSERT INTO `icon_res_info` VALUES ('arrow-out.png');
INSERT INTO `icon_res_info` VALUES ('guide.png');
INSERT INTO `icon_res_info` VALUES ('status-away.png');
INSERT INTO `icon_res_info` VALUES ('user.png');
INSERT INTO `icon_res_info` VALUES ('cake-plain.png');
INSERT INTO `icon_res_info` VALUES ('highlighter-text.png');
INSERT INTO `icon_res_info` VALUES ('blueprint--pencil.png');
INSERT INTO `icon_res_info` VALUES ('navigation-270.png');
INSERT INTO `icon_res_info` VALUES ('calendar-delete.png');
INSERT INTO `icon_res_info` VALUES ('quill--arrow.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-d.png');
INSERT INTO `icon_res_info` VALUES ('money--minus.png');
INSERT INTO `icon_res_info` VALUES ('document-stamp.png');
INSERT INTO `icon_res_info` VALUES ('sofa.png');
INSERT INTO `icon_res_info` VALUES ('calendar-empty.png');
INSERT INTO `icon_res_info` VALUES ('socket--minus.png');
INSERT INTO `icon_res_info` VALUES ('script-visual-studio.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-paste-word.png');
INSERT INTO `icon_res_info` VALUES ('paper-bag.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-h.png');
INSERT INTO `icon_res_info` VALUES ('bell--pencil.png');
INSERT INTO `icon_res_info` VALUES ('user-silhouette.png');
INSERT INTO `icon_res_info` VALUES ('sort--plus.png');
INSERT INTO `icon_res_info` VALUES ('map--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('system-monitor-network.png');
INSERT INTO `icon_res_info` VALUES ('sort-date-descending.png');
INSERT INTO `icon_res_info` VALUES ('luggage-tag.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-search-result.png');
INSERT INTO `icon_res_info` VALUES ('node-select.png');
INSERT INTO `icon_res_info` VALUES ('traffic-cone--arrow.png');
INSERT INTO `icon_res_info` VALUES ('application-dock-270.png');
INSERT INTO `icon_res_info` VALUES ('hard-hat--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('arrow-225.png');
INSERT INTO `icon_res_info` VALUES ('medal-silver.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-open-image.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle-045-left.png');
INSERT INTO `icon_res_info` VALUES ('pin.png');
INSERT INTO `icon_res_info` VALUES ('mail.png');
INSERT INTO `icon_res_info` VALUES ('grid-snap-dot.png');
INSERT INTO `icon_res_info` VALUES ('arrow-turn-270-left.png');
INSERT INTO `icon_res_info` VALUES ('application.png');
INSERT INTO `icon_res_info` VALUES ('paper-bag--minus.png');
INSERT INTO `icon_res_info` VALUES ('drive--arrow.png');
INSERT INTO `icon_res_info` VALUES ('credit-card-green.png');
INSERT INTO `icon_res_info` VALUES ('xfn-friend-met.png');
INSERT INTO `icon_res_info` VALUES ('control-double-270.png');
INSERT INTO `icon_res_info` VALUES ('ui-search-field.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-detail.png');
INSERT INTO `icon_res_info` VALUES ('arrow-switch-180.png');
INSERT INTO `icon_res_info` VALUES ('document-image.png');
INSERT INTO `icon_res_info` VALUES ('color--plus.png');
INSERT INTO `icon_res_info` VALUES ('ui-buttons.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-button.png');
INSERT INTO `icon_res_info` VALUES ('disk-return.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-n.png');
INSERT INTO `icon_res_info` VALUES ('user-gray.png');
INSERT INTO `icon_res_info` VALUES ('folder--arrow.png');
INSERT INTO `icon_res_info` VALUES ('money--plus.png');
INSERT INTO `icon_res_info` VALUES ('drive-rename.png');
INSERT INTO `icon_res_info` VALUES ('inbox-slide.png');
INSERT INTO `icon_res_info` VALUES ('smiley.png');
INSERT INTO `icon_res_info` VALUES ('eye--minus.png');
INSERT INTO `icon_res_info` VALUES ('exclamation-white.png');
INSERT INTO `icon_res_info` VALUES ('auction-hammer--arrow.png');
INSERT INTO `icon_res_info` VALUES ('feed--plus.png');
INSERT INTO `icon_res_info` VALUES ('control-180-small.png');
INSERT INTO `icon_res_info` VALUES ('dashboard--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-check-box-uncheck.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-snippet.png');
INSERT INTO `icon_res_info` VALUES ('cake--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('clipboard--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-progress-bar.png');
INSERT INTO `icon_res_info` VALUES ('wrench--pencil.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-sub.png');
INSERT INTO `icon_res_info` VALUES ('cross-small-white.png');
INSERT INTO `icon_res_info` VALUES ('document-text.png');
INSERT INTO `icon_res_info` VALUES ('document-export.png');
INSERT INTO `icon_res_info` VALUES ('openid.png');
INSERT INTO `icon_res_info` VALUES ('arrow-090-small.png');
INSERT INTO `icon_res_info` VALUES ('music--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('bin-full.png');
INSERT INTO `icon_res_info` VALUES ('document-access.png');
INSERT INTO `icon_res_info` VALUES ('smiley-fat.png');
INSERT INTO `icon_res_info` VALUES ('edit-comma.png');
INSERT INTO `icon_res_info` VALUES ('script-binary.png');
INSERT INTO `icon_res_info` VALUES ('music--pencil.png');
INSERT INTO `icon_res_info` VALUES ('media-player-medium-green.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-l.png');
INSERT INTO `icon_res_info` VALUES ('open-source.png');
INSERT INTO `icon_res_info` VALUES ('plus-small-circle.png');
INSERT INTO `icon_res_info` VALUES ('application-small.png');
INSERT INTO `icon_res_info` VALUES ('ui-combo-box-calendar.png');
INSERT INTO `icon_res_info` VALUES ('image-select.png');
INSERT INTO `icon_res_info` VALUES ('trophy-silver.png');
INSERT INTO `icon_res_info` VALUES ('mail-open-document-text.png');
INSERT INTO `icon_res_info` VALUES ('equalizer--arrow.png');
INSERT INTO `icon_res_info` VALUES ('calendar.png');
INSERT INTO `icon_res_info` VALUES ('star--arrow.png');
INSERT INTO `icon_res_info` VALUES ('tick-circle-frame.png');
INSERT INTO `icon_res_info` VALUES ('pencil--arrow.png');
INSERT INTO `icon_res_info` VALUES ('switch-network.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-v.png');
INSERT INTO `icon_res_info` VALUES ('opml-small.png');
INSERT INTO `icon_res_info` VALUES ('guitar--pencil.png');
INSERT INTO `icon_res_info` VALUES ('information-small.png');
INSERT INTO `icon_res_info` VALUES ('sofa--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('document--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('database.png');
INSERT INTO `icon_res_info` VALUES ('edit-outline.png');
INSERT INTO `icon_res_info` VALUES ('picture--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('task.png');
INSERT INTO `icon_res_info` VALUES ('media-player-medium.png');
INSERT INTO `icon_res_info` VALUES ('blog--pencil.png');
INSERT INTO `icon_res_info` VALUES ('box--plus.png');
INSERT INTO `icon_res_info` VALUES ('guitar--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blueprint--arrow.png');
INSERT INTO `icon_res_info` VALUES ('zone-label.png');
INSERT INTO `icon_res_info` VALUES ('guitar--plus.png');
INSERT INTO `icon_res_info` VALUES ('script-export.png');
INSERT INTO `icon_res_info` VALUES ('question-shield.png');
INSERT INTO `icon_res_info` VALUES ('burn--plus.png');
INSERT INTO `icon_res_info` VALUES ('receipt-text.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-t.png');
INSERT INTO `icon_res_info` VALUES ('folder-open-document.png');
INSERT INTO `icon_res_info` VALUES ('edit-diff.png');
INSERT INTO `icon_res_info` VALUES ('ui-list-box-blue.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-list.png');
INSERT INTO `icon_res_info` VALUES ('store-small.png');
INSERT INTO `icon_res_info` VALUES ('ui-slider.png');
INSERT INTO `icon_res_info` VALUES ('medal--minus.png');
INSERT INTO `icon_res_info` VALUES ('table-join.png');
INSERT INTO `icon_res_info` VALUES ('tick.png');
INSERT INTO `icon_res_info` VALUES ('cassette--minus.png');
INSERT INTO `icon_res_info` VALUES ('validation.png');
INSERT INTO `icon_res_info` VALUES ('megaphone--pencil.png');
INSERT INTO `icon_res_info` VALUES ('ghost-small.png');
INSERT INTO `icon_res_info` VALUES ('sticky-notes-pin.png');
INSERT INTO `icon_res_info` VALUES ('do-not-disturb-sign-cross.png');
INSERT INTO `icon_res_info` VALUES ('burn--minus.png');
INSERT INTO `icon_res_info` VALUES ('wall.png');
INSERT INTO `icon_res_info` VALUES ('tick-small-white.png');
INSERT INTO `icon_res_info` VALUES ('shopping-basket--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-documents-stack.png');
INSERT INTO `icon_res_info` VALUES ('picture--pencil.png');
INSERT INTO `icon_res_info` VALUES ('table-sum.png');
INSERT INTO `icon_res_info` VALUES ('speaker-network.png');
INSERT INTO `icon_res_info` VALUES ('ticket--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-node.png');
INSERT INTO `icon_res_info` VALUES ('car--pencil.png');
INSERT INTO `icon_res_info` VALUES ('bookmark--plus.png');
INSERT INTO `icon_res_info` VALUES ('layer-flip.png');
INSERT INTO `icon_res_info` VALUES ('user-worker.png');
INSERT INTO `icon_res_info` VALUES ('balloon--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('calendar-insert.png');
INSERT INTO `icon_res_info` VALUES ('document-template.png');
INSERT INTO `icon_res_info` VALUES ('ui-address-bar.png');
INSERT INTO `icon_res_info` VALUES ('image--plus.png');
INSERT INTO `icon_res_info` VALUES ('telephone--plus.png');
INSERT INTO `icon_res_info` VALUES ('layer-shape-round.png');
INSERT INTO `icon_res_info` VALUES ('store--pencil.png');
INSERT INTO `icon_res_info` VALUES ('store--plus.png');
INSERT INTO `icon_res_info` VALUES ('toilet-male.png');
INSERT INTO `icon_res_info` VALUES ('reports.png');
INSERT INTO `icon_res_info` VALUES ('toolbox--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('terminal--pencil.png');
INSERT INTO `icon_res_info` VALUES ('shopping-basket--arrow.png');
INSERT INTO `icon_res_info` VALUES ('table-delete-row.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-excel.png');
INSERT INTO `icon_res_info` VALUES ('sql-join-right.png');
INSERT INTO `icon_res_info` VALUES ('do-not-disturb-sign.png');
INSERT INTO `icon_res_info` VALUES ('xfn-colleague-met.png');
INSERT INTO `icon_res_info` VALUES ('chair.png');
INSERT INTO `icon_res_info` VALUES ('camera-small.png');
INSERT INTO `icon_res_info` VALUES ('border-left.png');
INSERT INTO `icon_res_info` VALUES ('document--arrow.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-m.png');
INSERT INTO `icon_res_info` VALUES ('music--plus.png');
INSERT INTO `icon_res_info` VALUES ('mail--arrow.png');
INSERT INTO `icon_res_info` VALUES ('user--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('control-record-small.png');
INSERT INTO `icon_res_info` VALUES ('pda--plus.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-resize.png');
INSERT INTO `icon_res_info` VALUES ('currency-dollar-cad.png');
INSERT INTO `icon_res_info` VALUES ('compass--arrow.png');
INSERT INTO `icon_res_info` VALUES ('network-ethernet.png');
INSERT INTO `icon_res_info` VALUES ('beaker--pencil.png');
INSERT INTO `icon_res_info` VALUES ('film--pencil.png');
INSERT INTO `icon_res_info` VALUES ('piano--minus.png');
INSERT INTO `icon_res_info` VALUES ('t-shirt-print.png');
INSERT INTO `icon_res_info` VALUES ('hand.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-hf-insert-footer.png');
INSERT INTO `icon_res_info` VALUES ('paint-can.png');
INSERT INTO `icon_res_info` VALUES ('memory.png');
INSERT INTO `icon_res_info` VALUES ('layer-small.png');
INSERT INTO `icon_res_info` VALUES ('lifebuoy--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('books-brown.png');
INSERT INTO `icon_res_info` VALUES ('control-pause.png');
INSERT INTO `icon_res_info` VALUES ('counter-count-up.png');
INSERT INTO `icon_res_info` VALUES ('arrow-skip.png');
INSERT INTO `icon_res_info` VALUES ('block--pencil.png');
INSERT INTO `icon_res_info` VALUES ('printer-share.png');
INSERT INTO `icon_res_info` VALUES ('sql-join-left.png');
INSERT INTO `icon_res_info` VALUES ('document-medium.png');
INSERT INTO `icon_res_info` VALUES ('edit-underline.png');
INSERT INTO `icon_res_info` VALUES ('equalizer-flat.png');
INSERT INTO `icon_res_info` VALUES ('ui-status-bar.png');
INSERT INTO `icon_res_info` VALUES ('lightning.png');
INSERT INTO `icon_res_info` VALUES ('lock--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-document.png');
INSERT INTO `icon_res_info` VALUES ('book--plus.png');
INSERT INTO `icon_res_info` VALUES ('script-medium.png');
INSERT INTO `icon_res_info` VALUES ('edit-color.png');
INSERT INTO `icon_res_info` VALUES ('contrast.png');
INSERT INTO `icon_res_info` VALUES ('arrow-continue-270-left.png');
INSERT INTO `icon_res_info` VALUES ('price-tag--plus.png');
INSERT INTO `icon_res_info` VALUES ('highlighter-small.png');
INSERT INTO `icon_res_info` VALUES ('wall--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('binocular--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-sticky-note.png');
INSERT INTO `icon_res_info` VALUES ('ui-layered-pane.png');
INSERT INTO `icon_res_info` VALUES ('speaker-volume-control.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-hf-select-footer.png');
INSERT INTO `icon_res_info` VALUES ('dashboard.png');
INSERT INTO `icon_res_info` VALUES ('arrow-090.png');
INSERT INTO `icon_res_info` VALUES ('terminal--minus.png');
INSERT INTO `icon_res_info` VALUES ('cookie--pencil.png');
INSERT INTO `icon_res_info` VALUES ('cutter--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('minus-button.png');
INSERT INTO `icon_res_info` VALUES ('layers-alignment-center.png');
INSERT INTO `icon_res_info` VALUES ('sort.png');
INSERT INTO `icon_res_info` VALUES ('arrow-transition-090.png');
INSERT INTO `icon_res_info` VALUES ('slide--arrow.png');
INSERT INTO `icon_res_info` VALUES ('control-skip-180.png');
INSERT INTO `icon_res_info` VALUES ('ui-layout-panel.png');
INSERT INTO `icon_res_info` VALUES ('user-business-boss.png');
INSERT INTO `icon_res_info` VALUES ('sort-small.png');
INSERT INTO `icon_res_info` VALUES ('maps.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-r.png');
INSERT INTO `icon_res_info` VALUES ('drill--arrow.png');
INSERT INTO `icon_res_info` VALUES ('document-photoshop-image.png');
INSERT INTO `icon_res_info` VALUES ('smiley-eek.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-text-image.png');
INSERT INTO `icon_res_info` VALUES ('layer-shape-curve.png');
INSERT INTO `icon_res_info` VALUES ('burn--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('notebook--arrow.png');
INSERT INTO `icon_res_info` VALUES ('feed-balloon.png');
INSERT INTO `icon_res_info` VALUES ('globe--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('document-xaml.png');
INSERT INTO `icon_res_info` VALUES ('sticky-note-shred.png');
INSERT INTO `icon_res_info` VALUES ('piano--arrow.png');
INSERT INTO `icon_res_info` VALUES ('ui-button-toggle.png');
INSERT INTO `icon_res_info` VALUES ('media-player-xsmall-blue.png');
INSERT INTO `icon_res_info` VALUES ('document-page-last.png');
INSERT INTO `icon_res_info` VALUES ('pencil--minus.png');
INSERT INTO `icon_res_info` VALUES ('edit-list-order.png');
INSERT INTO `icon_res_info` VALUES ('mails.png');
INSERT INTO `icon_res_info` VALUES ('pillow-gray.png');
INSERT INTO `icon_res_info` VALUES ('share-document.png');
INSERT INTO `icon_res_info` VALUES ('tag-label-red.png');
INSERT INTO `icon_res_info` VALUES ('arrow-circle-135-left.png');
INSERT INTO `icon_res_info` VALUES ('chevron-small-expand.png');
INSERT INTO `icon_res_info` VALUES ('application-browser.png');
INSERT INTO `icon_res_info` VALUES ('document-view-book.png');
INSERT INTO `icon_res_info` VALUES ('paint-brush.png');
INSERT INTO `icon_res_info` VALUES ('music-beam.png');
INSERT INTO `icon_res_info` VALUES ('blue-folder-medium.png');
INSERT INTO `icon_res_info` VALUES ('stamp--pencil.png');
INSERT INTO `icon_res_info` VALUES ('user-business-gray.png');
INSERT INTO `icon_res_info` VALUES ('application--pencil.png');
INSERT INTO `icon_res_info` VALUES ('dashboard--arrow.png');
INSERT INTO `icon_res_info` VALUES ('flag-black.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute.png');
INSERT INTO `icon_res_info` VALUES ('ui-scroll-pane-both.png');
INSERT INTO `icon_res_info` VALUES ('control-270-small.png');
INSERT INTO `icon_res_info` VALUES ('information-white.png');
INSERT INTO `icon_res_info` VALUES ('layout-join.png');
INSERT INTO `icon_res_info` VALUES ('document-attribute-l.png');
INSERT INTO `icon_res_info` VALUES ('folder-search-result.png');
INSERT INTO `icon_res_info` VALUES ('color-adjustment-red.png');
INSERT INTO `icon_res_info` VALUES ('arrow-split.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-attribute-v.png');
INSERT INTO `icon_res_info` VALUES ('calculator--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('lock--arrow.png');
INSERT INTO `icon_res_info` VALUES ('globe--minus.png');
INSERT INTO `icon_res_info` VALUES ('newspaper--pencil.png');
INSERT INTO `icon_res_info` VALUES ('plus-circle.png');
INSERT INTO `icon_res_info` VALUES ('camera--arrow.png');
INSERT INTO `icon_res_info` VALUES ('script-import.png');
INSERT INTO `icon_res_info` VALUES ('leaf--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('document-invoice.png');
INSERT INTO `icon_res_info` VALUES ('camcorder--minus.png');
INSERT INTO `icon_res_info` VALUES ('exclamation--frame.png');
INSERT INTO `icon_res_info` VALUES ('socket--arrow.png');
INSERT INTO `icon_res_info` VALUES ('mail-send.png');
INSERT INTO `icon_res_info` VALUES ('mail--plus.png');
INSERT INTO `icon_res_info` VALUES ('script-attribute-q.png');
INSERT INTO `icon_res_info` VALUES ('brightness-low.png');
INSERT INTO `icon_res_info` VALUES ('image-vertical-sunset.png');
INSERT INTO `icon_res_info` VALUES ('image-small.png');
INSERT INTO `icon_res_info` VALUES ('media-player-black.png');
INSERT INTO `icon_res_info` VALUES ('controller.png');
INSERT INTO `icon_res_info` VALUES ('balloon--plus.png');
INSERT INTO `icon_res_info` VALUES ('magnet-small.png');
INSERT INTO `icon_res_info` VALUES ('hourglass-select-remain.png');
INSERT INTO `icon_res_info` VALUES ('smiley-twist.png');
INSERT INTO `icon_res_info` VALUES ('images-flickr.png');
INSERT INTO `icon_res_info` VALUES ('media-player-small-pink.png');
INSERT INTO `icon_res_info` VALUES ('scissors--pencil.png');
INSERT INTO `icon_res_info` VALUES ('network-status-offline.png');
INSERT INTO `icon_res_info` VALUES ('mobile-phone--plus.png');
INSERT INTO `icon_res_info` VALUES ('application-resize.png');
INSERT INTO `icon_res_info` VALUES ('receipt-sticky-note.png');
INSERT INTO `icon_res_info` VALUES ('folder-small-horizontal.png');
INSERT INTO `icon_res_info` VALUES ('sort-alphabet.png');
INSERT INTO `icon_res_info` VALUES ('task--arrow.png');
INSERT INTO `icon_res_info` VALUES ('document-convert.png');
INSERT INTO `icon_res_info` VALUES ('pipette--pencil.png');
INSERT INTO `icon_res_info` VALUES ('jar.png');
INSERT INTO `icon_res_info` VALUES ('chart-down.png');
INSERT INTO `icon_res_info` VALUES ('bank--pencil.png');
INSERT INTO `icon_res_info` VALUES ('ui-progress-bar-indeterminate.png');
INSERT INTO `icon_res_info` VALUES ('minus-circle-frame.png');
INSERT INTO `icon_res_info` VALUES ('arrow-315-medium.png');
INSERT INTO `icon_res_info` VALUES ('border-draw.png');
INSERT INTO `icon_res_info` VALUES ('trophy--pencil.png');
INSERT INTO `icon_res_info` VALUES ('pill-small.png');
INSERT INTO `icon_res_info` VALUES ('plates.png');
INSERT INTO `icon_res_info` VALUES ('hand-share.png');
INSERT INTO `icon_res_info` VALUES ('address-book-blue.png');
INSERT INTO `icon_res_info` VALUES ('chair--plus.png');
INSERT INTO `icon_res_info` VALUES ('folder-medium.png');
INSERT INTO `icon_res_info` VALUES ('control-record.png');
INSERT INTO `icon_res_info` VALUES ('arrow-merge-090.png');
INSERT INTO `icon_res_info` VALUES ('zone-medium.png');
INSERT INTO `icon_res_info` VALUES ('table-insert-row.png');
INSERT INTO `icon_res_info` VALUES ('table-select-all.png');
INSERT INTO `icon_res_info` VALUES ('printer--exclamation.png');
INSERT INTO `icon_res_info` VALUES ('document-binary.png');
INSERT INTO `icon_res_info` VALUES ('table-split-column.png');
INSERT INTO `icon_res_info` VALUES ('navigation-090.png');
INSERT INTO `icon_res_info` VALUES ('media-player.png');
INSERT INTO `icon_res_info` VALUES ('arrow-continue.png');
INSERT INTO `icon_res_info` VALUES ('folder-broken.png');
INSERT INTO `icon_res_info` VALUES ('arrow-repeat-once.png');
INSERT INTO `icon_res_info` VALUES ('folder-sticky-note.png');
INSERT INTO `icon_res_info` VALUES ('stamp--minus.png');
INSERT INTO `icon_res_info` VALUES ('ui-text-field-format.png');
INSERT INTO `icon_res_info` VALUES ('xfn-sweetheart-met.png');
INSERT INTO `icon_res_info` VALUES ('blue-document-clock.png');
INSERT INTO `icon_res_info` VALUES ('user--plus.png');
INSERT INTO `icon_res_info` VALUES ('vise.png');
INSERT INTO `icon_res_info` VALUES ('node.png');
INSERT INTO `icon_res_info` VALUES ('tag.png');
INSERT INTO `icon_res_info` VALUES ('projection-screen--minus.png');
INSERT INTO `icon_res_info` VALUES ('folder-export.png');
INSERT INTO `icon_res_info` VALUES ('clipboard-invoice.png');

-- ----------------------------
-- Table structure for jtopcms_model_mh_jl
-- ----------------------------
DROP TABLE IF EXISTS `jtopcms_model_mh_jl`;
CREATE TABLE `jtopcms_model_mh_jl`  (
  `contentId` bigint(20) NOT NULL,
  `jtopcms_def_jt_jl_xm` varchar(60)  NULL DEFAULT NULL,
  `jtopcms_def_jt_jl_jieshao` varchar(2000)  NULL DEFAULT NULL,
  `jtopcms_def_jt_mh_jl_gw` int(11) NULL DEFAULT NULL,
  `jtopcms_def_jt_jl_xl` int(11) NULL DEFAULT NULL,
  `jtopcms_def_jt_asdasdasd` varchar(200)  NULL DEFAULT NULL,
  PRIMARY KEY (`contentId`) USING BTREE,
  INDEX `mh_jl`(`jtopcms_def_jt_mh_jl_gw`, `contentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jtopcms_model_mh_jl
-- ----------------------------
INSERT INTO `jtopcms_model_mh_jl` VALUES (1, 'asd', 'asdassubmitGBInfo(submitGBInfo(submitGBInfo(submitGBInfo(submitGBInfo(submitGBInfo(', 1, 2, '');

-- ----------------------------
-- Table structure for jtopcms_model_mh_ly
-- ----------------------------
DROP TABLE IF EXISTS `jtopcms_model_mh_ly`;
CREATE TABLE `jtopcms_model_mh_ly`  (
  `contentId` bigint(20) NOT NULL,
  `jtopcms_def_mh_lylx` int(11) NULL DEFAULT NULL,
  `jtopcms_def_mh_ly_lxdh` varchar(60)  NULL DEFAULT NULL,
  `jtopcms_def_mh_ly_qqhm` varchar(60)  NULL DEFAULT NULL,
  `jtopcms_def_mh_ly_mail` varchar(60)  NULL DEFAULT NULL,
  `jtopcms_def_mh_ly_dwdz` varchar(300)  NULL DEFAULT NULL,
  `jtopcms_def_mh_ly_slr` varchar(200)  NULL DEFAULT NULL,
  `jtopcms_def_mh_ly_zltp` varchar(200)  NULL DEFAULT NULL,
  PRIMARY KEY (`contentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for jtopcms_model_mh_sp
-- ----------------------------
DROP TABLE IF EXISTS `jtopcms_model_mh_sp`;
CREATE TABLE `jtopcms_model_mh_sp`  (
  `contentId` bigint(20) NOT NULL,
  `jtopcms_def_mh_sp_file` varchar(200)  NULL DEFAULT NULL,
  `jtopcms_def_jt_spmc` varchar(60)  NULL DEFAULT NULL,
  PRIMARY KEY (`contentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for jtopcms_model_mh_tpj
-- ----------------------------
DROP TABLE IF EXISTS `jtopcms_model_mh_tpj`;
CREATE TABLE `jtopcms_model_mh_tpj`  (
  `contentId` bigint(20) NOT NULL,
  `jtopcms_def_mh_tpj1` varchar(200)  NULL DEFAULT NULL,
  `jtopcms_def_mh_tpj2` varchar(200)  NULL DEFAULT NULL,
  PRIMARY KEY (`contentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for jtopcms_model_mhxw
-- ----------------------------
DROP TABLE IF EXISTS `jtopcms_model_mhxw`;
CREATE TABLE `jtopcms_model_mhxw`  (
  `contentId` bigint(20) NOT NULL,
  `jtopcms_def_mh_wz` longtext  NULL,
  PRIMARY KEY (`contentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jtopcms_model_mhxw
-- ----------------------------
INSERT INTO `jtopcms_model_mhxw` VALUES (4, '<p>&nbsp;刚发的地方个地方发给对方个地方个搞定搞定覆盖 &nbsp;</p>');
INSERT INTO `jtopcms_model_mhxw` VALUES (5, '<p>&nbsp;发生的发多少发多少&nbsp;</p>');
INSERT INTO `jtopcms_model_mhxw` VALUES (6, '<p>&nbsp;</p><p style=\"TEXT-ALIGN: center\"><img id=\"jtopcms_content_image_7\" alt=\"djs.jpg\" src=\"/demo/upload/2018-01-17/1516172109405402880e76102e0e2366016102e6665d0061.jpg\" width=\"600\"/>&nbsp;</p><p>&nbsp;</p>');
INSERT INTO `jtopcms_model_mhxw` VALUES (7, '<p style=\"text-align: center\"><img id=\"jtopcms_content_image_12\" src=\"http://127.0.0.1:8080/demo/upload/2018-01-17/1516180781424402880e7610365ce7430161036ab970000f.jpg\" alt=\"djs.jpg\" width=\"120\"/>&nbsp;</p><p><br/></p>');
INSERT INTO `jtopcms_model_mhxw` VALUES (8, '<p style=\"text-align: center\"><img id=\"jtopcms_content_image_13\" src=\"http://127.0.0.1:8080/demo/upload/2018-01-17/1516180817301402880e7610365ce4770161036b45950010.jpg\" alt=\"djs.jpg\" width=\"120\"/>&nbsp;</p><p><br/></p>');
INSERT INTO `jtopcms_model_mhxw` VALUES (9, '<p style=\"text-align: center\"><img id=\"jtopcms_content_image_14\" src=\"http://127.0.0.1:8080/demo/upload/2018-01-17/1516180817331402880e7610365ce3170161036b45b30011.jpg\" alt=\"djs.jpg\" width=\"120\"/>&nbsp;</p><p><br/></p>');
INSERT INTO `jtopcms_model_mhxw` VALUES (10, '<p>发多少发生的发多少的 的</p>');
INSERT INTO `jtopcms_model_mhxw` VALUES (11, '<p>阿萨德&nbsp;</p>');
INSERT INTO `jtopcms_model_mhxw` VALUES (12, '<p>阿萨德&nbsp;</p>');

-- ----------------------------
-- Table structure for jtopcms_model_wenku
-- ----------------------------
DROP TABLE IF EXISTS `jtopcms_model_wenku`;
CREATE TABLE `jtopcms_model_wenku`  (
  `contentId` bigint(20) NOT NULL,
  `jtopcms_def_jt_wk_doc` varchar(200)  NULL DEFAULT NULL,
  PRIMARY KEY (`contentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for manager_message
-- ----------------------------
DROP TABLE IF EXISTS `manager_message`;
CREATE TABLE `manager_message`  (
  `msgId` bigint(20) NOT NULL AUTO_INCREMENT,
  `msgTypeName` varchar(300)  NOT NULL,
  `msgTitle` varchar(600)  NOT NULL,
  `msgContent` text  NOT NULL,
  `replyContent` text  NULL,
  `sender` bigint(20) NOT NULL,
  `sendDT` datetime NULL DEFAULT NULL,
  `replyDT` datetime NULL DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  `isRead` int(11) NOT NULL,
  `isReply` int(11) NOT NULL,
  `isReDelete` int(11) NOT NULL,
  `isSeDelete` int(11) NOT NULL,
  PRIMARY KEY (`msgId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `memberId` bigint(20) NOT NULL AUTO_INCREMENT,
  `memberName` varchar(200)  NOT NULL,
  `email` varchar(150)  NULL DEFAULT NULL,
  `headPhoto` varchar(200)  NULL DEFAULT NULL,
  `isTrueEmail` int(11) NOT NULL,
  `password` varchar(400)  NULL DEFAULT NULL,
  `isTruePass` int(11) NULL DEFAULT NULL,
  `phoneNumber` varchar(20)  NULL DEFAULT NULL,
  `isTruePhone` int(11) NOT NULL,
  `regDT` datetime NULL DEFAULT NULL,
  `trueName` varchar(50)  NULL DEFAULT NULL,
  `certType` int(11) NULL DEFAULT NULL,
  `certCode` varchar(200)  NULL DEFAULT NULL,
  `certPhotoP` varchar(200)  NULL DEFAULT NULL,
  `certPhotoR` varchar(200)  NULL DEFAULT NULL,
  `isTrueMan` int(11) NULL DEFAULT NULL,
  `prevLoginIp` varchar(60)  NULL DEFAULT NULL,
  `prevLoginArea` varchar(80)  NULL DEFAULT NULL,
  `prevLoginDT` datetime NULL DEFAULT NULL,
  `currLoginIp` varchar(60)  NULL DEFAULT NULL,
  `currLoginDT` datetime NULL DEFAULT NULL,
  `memLevel` int(11) NOT NULL,
  `score` bigint(20) NOT NULL,
  `useStatus` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `isFirst` int(6) NULL DEFAULT 0,
  `loginSuccessCount` bigint(20) NOT NULL,
  PRIMARY KEY (`memberId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_acc_class_relate_role
-- ----------------------------
DROP TABLE IF EXISTS `member_acc_class_relate_role`;
CREATE TABLE `member_acc_class_relate_role`  (
  `classId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_acc_rule
-- ----------------------------
DROP TABLE IF EXISTS `member_acc_rule`;
CREATE TABLE `member_acc_rule`  (
  `accRuleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `accName` varchar(120)  NOT NULL,
  `ruleDesc` varchar(500)  NULL DEFAULT NULL,
  `minScore` bigint(20) NOT NULL,
  `minLever` bigint(20) NOT NULL,
  `roleIds` varchar(4000)  NULL DEFAULT NULL,
  `eft` int(11) NOT NULL,
  `typeId` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`accRuleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member_acc_rule
-- ----------------------------
INSERT INTO `member_acc_rule` VALUES (4, '高级会员可访问', '', 50000, -1, '7', 1, 1, 5);
INSERT INTO `member_acc_rule` VALUES (5, '初级会员可访问', '', 1, 2, '5', 1, 1, 5);
INSERT INTO `member_acc_rule` VALUES (6, '高级会员投稿', '', 1, 2, '7', 1, 2, 5);
INSERT INTO `member_acc_rule` VALUES (7, '初级会员投稿', '', 1, 2, '5', 1, 2, 5);
INSERT INTO `member_acc_rule` VALUES (8, 'VIP', '按时的的,asd 撒旦', 0, 3, NULL, 1, 1, 5);

-- ----------------------------
-- Table structure for member_class_acc
-- ----------------------------
DROP TABLE IF EXISTS `member_class_acc`;
CREATE TABLE `member_class_acc`  (
  `classId` bigint(20) NOT NULL,
  `accRuleId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_class_submit_acc
-- ----------------------------
DROP TABLE IF EXISTS `member_class_submit_acc`;
CREATE TABLE `member_class_submit_acc`  (
  `classId` bigint(20) NOT NULL,
  `accRuleId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_coll_content
-- ----------------------------
DROP TABLE IF EXISTS `member_coll_content`;
CREATE TABLE `member_coll_content`  (
  `cId` bigint(20) NOT NULL,
  `mId` bigint(20) NULL DEFAULT NULL,
  `memId` bigint(20) NULL DEFAULT NULL,
  INDEX `cm`(`memId`, `mId`, `cId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_history_content
-- ----------------------------
DROP TABLE IF EXISTS `member_history_content`;
CREATE TABLE `member_history_content`  (
  `cId` bigint(20) NULL DEFAULT NULL,
  `memId` bigint(20) NULL DEFAULT NULL,
  `mId` bigint(20) NULL DEFAULT NULL,
  `addDate` datetime NULL DEFAULT NULL,
  INDEX `cm`(`memId`, `cId`, `mId`, `addDate`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_login_trace
-- ----------------------------
DROP TABLE IF EXISTS `member_login_trace`;
CREATE TABLE `member_login_trace`  (
  `ip` varchar(60)  NOT NULL,
  `eventDT` datetime NOT NULL,
  `loginSuccess` int(6) NOT NULL,
  `userName` varchar(100)  NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_lost_email_trace
-- ----------------------------
DROP TABLE IF EXISTS `member_lost_email_trace`;
CREATE TABLE `member_lost_email_trace`  (
  `keyStr` varchar(3000)  NOT NULL,
  `eventDt` datetime NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member_lost_email_trace
-- ----------------------------
INSERT INTO `member_lost_email_trace` VALUES ('27fbd4b10448242d45c061a6222e2b7218fd25c3d1334405cf9f9c35a3751ae9a719dbfe225e0cc2710e00df513899dd979413120f2fb5209960d7af9aaa7bf64b679a8dd06ce7e66295a513f00b67f9', '2015-10-27 11:04:29');
INSERT INTO `member_lost_email_trace` VALUES ('27fbd4b10448242d45c061a6222e2b7218fd25c3d1334405cf9f9c35a3751ae9a719dbfe225e0cc2710e00df513899dddbdc5ddbebcb64ee46cd436726a8cb8767ecefed0ffc75cd580a16a6b5f8abf7', '2015-10-27 11:48:15');

-- ----------------------------
-- Table structure for member_message
-- ----------------------------
DROP TABLE IF EXISTS `member_message`;
CREATE TABLE `member_message`  (
  `msgId` bigint(20) NOT NULL AUTO_INCREMENT,
  `msgTypeName` varchar(300)  NULL DEFAULT NULL,
  `msgTitle` varchar(600)  NOT NULL,
  `msgContent` text  NOT NULL,
  `sendDT` datetime NULL DEFAULT NULL,
  `memberId` bigint(20) NOT NULL,
  `isSys` int(11) NOT NULL,
  `isRead` int(6) NOT NULL,
  PRIMARY KEY (`msgId`) USING BTREE,
  INDEX `memId`(`memberId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_message_template
-- ----------------------------
DROP TABLE IF EXISTS `member_message_template`;
CREATE TABLE `member_message_template`  (
  `mtId` bigint(20) NOT NULL AUTO_INCREMENT,
  `mtFlag` varchar(60)  NOT NULL,
  `templateName` varchar(200)  NULL DEFAULT NULL,
  `templateTitle` varchar(300)  NULL DEFAULT NULL,
  `templateContent` text  NULL,
  `creator` varchar(60)  NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`mtId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member_message_template
-- ----------------------------
INSERT INTO `member_message_template` VALUES (9, 'regMember', '注册成功', '注册成功', '&lt;p&gt;祝贺您!&amp;nbsp;&amp;nbsp;&lt;span style=&quot;color: #ff0000&quot;&gt;${memberName}&amp;nbsp;&lt;/span&gt;&amp;nbsp;已成功注册为演示门户站会员。s&lt;/p&gt;', 'adminX', 5);

-- ----------------------------
-- Table structure for member_message_template_param
-- ----------------------------
DROP TABLE IF EXISTS `member_message_template_param`;
CREATE TABLE `member_message_template_param`  (
  `tpId` bigint(20) NOT NULL AUTO_INCREMENT,
  `paramName` varchar(80)  NOT NULL,
  `paramFlag` varchar(100)  NOT NULL,
  `creator` varchar(60)  NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`tpId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member_message_template_param
-- ----------------------------
INSERT INTO `member_message_template_param` VALUES (8, '会员名', 'memberName', 'adminX', 5);
INSERT INTO `member_message_template_param` VALUES (9, '真实名称', 'trueName', 'adminX', 5);

-- ----------------------------
-- Table structure for member_rank
-- ----------------------------
DROP TABLE IF EXISTS `member_rank`;
CREATE TABLE `member_rank`  (
  `rankId` bigint(20) NOT NULL AUTO_INCREMENT,
  `rankName` varchar(200)  NOT NULL,
  `rankLevel` int(11) NOT NULL,
  `minScore` bigint(20) NOT NULL,
  `maxScore` bigint(20) NOT NULL,
  `rankDesc` varchar(600)  NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`rankId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member_rank
-- ----------------------------
INSERT INTO `member_rank` VALUES (1, '普通会员2', 999, 12, 39, '', 5);
INSERT INTO `member_rank` VALUES (2, '高级会员', 999, 402, 499, '', 5);
INSERT INTO `member_rank` VALUES (3, 'VIP会员', 3444, 5001, 12456000, '似的似的　', 5);

-- ----------------------------
-- Table structure for member_relate_role
-- ----------------------------
DROP TABLE IF EXISTS `member_relate_role`;
CREATE TABLE `member_relate_role`  (
  `userId` bigint(20) NULL DEFAULT NULL,
  `roleId` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_role
-- ----------------------------
DROP TABLE IF EXISTS `member_role`;
CREATE TABLE `member_role`  (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `siteId` bigint(20) NOT NULL,
  `roleName` varchar(100)  NOT NULL,
  `roleDesc` varchar(200)  NULL DEFAULT NULL,
  `useState` int(6) NOT NULL,
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member_role
-- ----------------------------
INSERT INTO `member_role` VALUES (5, 5, '初级会员', '限制访问', 1);
INSERT INTO `member_role` VALUES (7, 5, '高级会员组', '', 1);

-- ----------------------------
-- Table structure for member_role_relate_resource
-- ----------------------------
DROP TABLE IF EXISTS `member_role_relate_resource`;
CREATE TABLE `member_role_relate_resource`  (
  `roleId` bigint(20) NOT NULL,
  `secResId` bigint(20) NOT NULL,
  INDEX `sec`(`secResId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member_role_relate_resource
-- ----------------------------
INSERT INTO `member_role_relate_resource` VALUES (5, 4);
INSERT INTO `member_role_relate_resource` VALUES (7, 7);
INSERT INTO `member_role_relate_resource` VALUES (7, 13);
INSERT INTO `member_role_relate_resource` VALUES (5, 13);
INSERT INTO `member_role_relate_resource` VALUES (7, 15);
INSERT INTO `member_role_relate_resource` VALUES (7, 16);
INSERT INTO `member_role_relate_resource` VALUES (5, 16);
INSERT INTO `member_role_relate_resource` VALUES (7, 4);
INSERT INTO `member_role_relate_resource` VALUES (5, 17);
INSERT INTO `member_role_relate_resource` VALUES (5, 15);
INSERT INTO `member_role_relate_resource` VALUES (5, 14);
INSERT INTO `member_role_relate_resource` VALUES (7, 14);
INSERT INTO `member_role_relate_resource` VALUES (7, 8);
INSERT INTO `member_role_relate_resource` VALUES (7, 17);

-- ----------------------------
-- Table structure for member_score_act
-- ----------------------------
DROP TABLE IF EXISTS `member_score_act`;
CREATE TABLE `member_score_act`  (
  `saId` bigint(20) NOT NULL AUTO_INCREMENT,
  `actName` varchar(60)  NOT NULL,
  `actFlag` int(11) NOT NULL,
  `actScore` int(11) NOT NULL,
  `targetCmd` varchar(120)  NULL DEFAULT NULL,
  `actClass` varchar(500)  NULL DEFAULT NULL,
  `actDesc` varchar(600)  NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`saId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member_score_act
-- ----------------------------
INSERT INTO `member_score_act` VALUES (4, '会员登录', 1, 12, 'memberLogin', '', '', 5);
INSERT INTO `member_score_act` VALUES (5, '关联qq_weibo', 1, 5, 'relateMember', '', '', 5);
INSERT INTO `member_score_act` VALUES (6, '会员注册', 1, 3, 'regMember', '', '', 5);
INSERT INTO `member_score_act` VALUES (7, '邮箱认证', 1, 2, 'memberEmailActive', '', '', 5);
INSERT INTO `member_score_act` VALUES (8, '投递稿件', 1, 2, 'clientAddContent', '', '', 5);
INSERT INTO `member_score_act` VALUES (9, '评论内容', 1, 1, 'clientAddComment', '', '', 5);
INSERT INTO `member_score_act` VALUES (10, '评论被删除', 0, 1, 'deleteComment', '', '', 5);
INSERT INTO `member_score_act` VALUES (11, '删除稿件', 0, 2, 'deleteContentToTrash', '', '', 5);
INSERT INTO `member_score_act` VALUES (12, '评论被删除_会员动作', 0, 1, 'deleteMemComm', '', '', 5);

-- ----------------------------
-- Table structure for member_securityresource
-- ----------------------------
DROP TABLE IF EXISTS `member_securityresource`;
CREATE TABLE `member_securityresource`  (
  `secResId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sysFlag` varchar(80)  NULL DEFAULT NULL,
  `resourceName` varchar(50)  NOT NULL,
  `icon` varchar(300)  NULL DEFAULT NULL,
  `resourceDesc` varchar(300)  NULL DEFAULT NULL,
  `resourceType` int(6) NOT NULL COMMENT '1=功能菜单 ， 2=模块入口， 3=功能集合',
  `useState` int(6) NOT NULL,
  `dataProtectType` int(6) NOT NULL,
  `creator` varchar(20)  NOT NULL,
  `target` varchar(300)  NULL DEFAULT NULL,
  `parentResId` bigint(20) NULL DEFAULT NULL,
  `isLeaf` int(6) NOT NULL,
  `layer` int(6) NOT NULL,
  `isLastChild` int(6) NOT NULL,
  `linearOrderFlag` varchar(400)  NOT NULL,
  `dataSecLevel` int(6) NULL DEFAULT NULL,
  `dataSecTypeId` bigint(20) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`secResId`) USING BTREE,
  INDEX `target_key`(`target`) USING BTREE,
  INDEX `sec-resT`(`dataSecTypeId`, `resourceType`) USING BTREE,
  INDEX `dp-linear`(`dataProtectType`, `linearOrderFlag`(383)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member_securityresource
-- ----------------------------
INSERT INTO `member_securityresource` VALUES (4, '', '演示会员总入口', '', '', 1, 1, 1, 'admin', '', -1, 0, 1, 1, '002', NULL, 1, 5);
INSERT INTO `member_securityresource` VALUES (7, '', '高级管理', '', '', 2, 1, 1, 'adminX', '', 4, 0, 2, 1, '002003', NULL, 0, 5);
INSERT INTO `member_securityresource` VALUES (8, '', '收费项目', '', '', 3, 1, 1, 'admin', '', 7, 1, 3, 1, '002003001', NULL, 1, 5);
INSERT INTO `member_securityresource` VALUES (13, '', '基础功能', '', '', 2, 1, 1, 'adminX', '', 4, 0, 2, 0, '002002', NULL, 1, 5);
INSERT INTO `member_securityresource` VALUES (14, '', '管理我的留言', '', '', 3, 1, 1, 'adminX', '', 13, 0, 3, 1, '002002002', NULL, 1, 5);
INSERT INTO `member_securityresource` VALUES (15, '', '留言管理', '', '', 4, 1, 1, 'adminX', '', 14, 0, 4, 1, '002002002001', NULL, 0, 5);
INSERT INTO `member_securityresource` VALUES (16, '123', '删除留言222', '123', '123123', 5, 1, 1, 'admin', '123', 15, 1, 5, 1, '002002002001001', NULL, 1, 5);
INSERT INTO `member_securityresource` VALUES (17, '', '会员主页', '', '', 3, 1, 1, 'adminX', '/demo/template/member_main.jsp', 13, 1, 3, 0, '002002001', NULL, 1, 5);

-- ----------------------------
-- Table structure for member_third_login
-- ----------------------------
DROP TABLE IF EXISTS `member_third_login`;
CREATE TABLE `member_third_login`  (
  `selfMemberId` bigint(20) NOT NULL,
  `loginType` varchar(15)  NOT NULL,
  `openUid` varchar(300)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for model
-- ----------------------------
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model`  (
  `dataModelId` bigint(20) NOT NULL AUTO_INCREMENT,
  `modelName` varchar(100)  NOT NULL,
  `modelSign` varchar(50)  NOT NULL COMMENT '标记：此标记唯一',
  `relateTableName` varchar(100)  NOT NULL COMMENT '关联的表名，要注意建立的表不能有数据库系统使用单词',
  `modelResType` int(6) NOT NULL,
  `modelType` int(6) NOT NULL,
  `remark` varchar(500)  NULL DEFAULT NULL,
  `titleMode` int(11) NULL DEFAULT NULL,
  `titleCol` varchar(80)  NULL DEFAULT NULL,
  `kwMode` int(11) NULL DEFAULT NULL,
  `simpleMode` int(11) NULL DEFAULT NULL,
  `useState` int(6) NOT NULL,
  `mainEditorFieldSign` varchar(20)  NULL DEFAULT NULL,
  `validateBehavior` varchar(300)  NULL DEFAULT NULL,
  `beforeBehavior` varchar(300)  NULL DEFAULT NULL,
  `privateMode` int(6) NOT NULL,
  `afterBehavior` varchar(300)  NULL DEFAULT NULL,
  `specialListPage` varchar(300)  NULL DEFAULT NULL,
  `specialAddPage` varchar(300)  NULL DEFAULT NULL,
  `specialEditPage` varchar(300)  NULL DEFAULT NULL,
  `ico` varchar(160)  NULL DEFAULT NULL,
  `isManageEdit` int(6) NULL DEFAULT NULL,
  `isMemberEdit` int(6) NULL DEFAULT NULL,
  `mustCensor` int(11) NULL DEFAULT NULL,
  `mustToken` int(6) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`dataModelId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model
-- ----------------------------
INSERT INTO `model` VALUES (82, '新闻', 'mhxw', 'jtopcms_model_mhxw', 1, 2, '门户新闻,为门户独有', 1, '', 1, NULL, 1, 'mh_wz', '', '', 0, '', NULL, NULL, NULL, 'document-attribute-t.png', 1, 1, 1, 1, 5);
INSERT INTO `model` VALUES (83, '演示站留言扩展', 'mh_ly', 'jtopcms_model_mh_ly', 0, 4, '', NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 'document.png', NULL, NULL, NULL, NULL, 5);
INSERT INTO `model` VALUES (98, '视频', 'mh_sp', 'jtopcms_model_mh_sp', 0, 2, '', 0, 'jt_spmc', 0, NULL, 1, NULL, '', '', 0, '', NULL, NULL, NULL, 'document-attribute-v.png', 1, 1, 1, 1, 5);
INSERT INTO `model` VALUES (99, '图片集', 'mh_tpj', 'jtopcms_model_mh_tpj', 0, 2, '', 1, '', 1, NULL, 1, NULL, '', '', 0, '', NULL, NULL, NULL, 'document.png', 1, 1, 1, 1, 5);
INSERT INTO `model` VALUES (101, '个人简历', 'mh_jl', 'jtopcms_model_mh_jl', 0, 9, '', NULL, NULL, NULL, NULL, 1, NULL, '', '', 0, '', NULL, NULL, NULL, 'document.png', 0, 0, 1, 1, 5);
INSERT INTO `model` VALUES (111, '文库', 'wenku', 'jtopcms_model_wenku', 0, 2, '', 1, '', 1, NULL, 1, NULL, '', '', 0, '', NULL, NULL, NULL, 'document.png', 1, 1, 1, 1, 5);

-- ----------------------------
-- Table structure for model_def_from_data_id
-- ----------------------------
DROP TABLE IF EXISTS `model_def_from_data_id`;
CREATE TABLE `model_def_from_data_id`  (
  `defFormDataId` bigint(20) NOT NULL AUTO_INCREMENT,
  `modelId` bigint(20) NULL DEFAULT NULL,
  `censorState` int(255) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`defFormDataId`) USING BTREE,
  INDEX `ms_id`(`modelId`, `siteId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model_def_from_data_id
-- ----------------------------
INSERT INTO `model_def_from_data_id` VALUES (1, 101, 0, 5);

-- ----------------------------
-- Table structure for model_filed_metadata
-- ----------------------------
DROP TABLE IF EXISTS `model_filed_metadata`;
CREATE TABLE `model_filed_metadata`  (
  `metaDataId` bigint(20) NOT NULL AUTO_INCREMENT,
  `dataModelId` bigint(20) NOT NULL COMMENT '所属的模型ID',
  `showName` varchar(60)  NOT NULL,
  `relateFiledName` varchar(70)  NOT NULL,
  `perdureType` varchar(30)  NOT NULL COMMENT '对应的持久层类型',
  `capacity` bigint(20) NULL DEFAULT NULL,
  `filedSign` varchar(60)  NOT NULL,
  `queryFlag` int(6) NULL DEFAULT NULL,
  `orderFlag` int(6) NULL DEFAULT NULL,
  `searchFlag` int(6) NULL DEFAULT NULL,
  `pickFlag` int(11) NULL DEFAULT NULL,
  `orderId` double NULL DEFAULT NULL,
  PRIMARY KEY (`metaDataId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 558 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model_filed_metadata
-- ----------------------------
INSERT INTO `model_filed_metadata` VALUES (476, 82, '新闻内容', 'jtopcms_def_mh_wz', '3', NULL, 'mh_wz', NULL, NULL, 1, NULL, 1);
INSERT INTO `model_filed_metadata` VALUES (477, 83, '留言类型', 'jtopcms_def_mh_lylx', '4', 60, 'mh_lylx', NULL, NULL, NULL, NULL, 1);
INSERT INTO `model_filed_metadata` VALUES (478, 83, '联系电话', 'jtopcms_def_mh_ly_lxdh', '1', 60, 'mh_ly_lxdh', NULL, NULL, NULL, NULL, 2);
INSERT INTO `model_filed_metadata` VALUES (479, 83, 'QQ号码', 'jtopcms_def_mh_ly_qqhm', '1', 60, 'mh_ly_qqhm', NULL, NULL, NULL, NULL, 3);
INSERT INTO `model_filed_metadata` VALUES (480, 83, '电子邮箱', 'jtopcms_def_mh_ly_mail', '1', 60, 'mh_ly_mail', NULL, NULL, NULL, NULL, 4);
INSERT INTO `model_filed_metadata` VALUES (481, 83, '单位地址', 'jtopcms_def_mh_ly_dwdz', '1', 300, 'mh_ly_dwdz', NULL, NULL, NULL, NULL, 5);
INSERT INTO `model_filed_metadata` VALUES (482, 83, '目标受理人', 'jtopcms_def_mh_ly_slr', '1', 200, 'mh_ly_slr', NULL, NULL, NULL, NULL, 6);
INSERT INTO `model_filed_metadata` VALUES (483, 83, '资料图片', 'jtopcms_def_mh_ly_zltp', '1', 200, 'mh_ly_zltp', NULL, NULL, NULL, NULL, 7);
INSERT INTO `model_filed_metadata` VALUES (494, 98, '视频', 'jtopcms_def_mh_sp_file', '1', 200, 'mh_sp_file', NULL, NULL, NULL, NULL, 2);
INSERT INTO `model_filed_metadata` VALUES (495, 99, '图片集1', 'jtopcms_def_mh_tpj1', '1', 200, 'mh_tpj1', NULL, NULL, NULL, NULL, 2);
INSERT INTO `model_filed_metadata` VALUES (496, 99, '图片集2', 'jtopcms_def_mh_tpj2', '1', 200, 'mh_tpj2', NULL, NULL, NULL, NULL, 1.1);
INSERT INTO `model_filed_metadata` VALUES (497, 101, '姓名', 'jtopcms_def_jt_jl_xm', '1', 60, 'jt_jl_xm', NULL, NULL, 1, NULL, 1);
INSERT INTO `model_filed_metadata` VALUES (498, 101, '简介', 'jtopcms_def_jt_jl_jieshao', '1', 2000, 'jt_jl_jieshao', NULL, NULL, NULL, NULL, 3);
INSERT INTO `model_filed_metadata` VALUES (502, 101, '投递岗位', 'jtopcms_def_jt_mh_jl_gw', '4', 60, 'jt_mh_jl_gw', 1, NULL, NULL, NULL, 4);
INSERT INTO `model_filed_metadata` VALUES (503, 101, '学历', 'jtopcms_def_jt_jl_xl', '4', 60, 'jt_jl_xl', NULL, NULL, NULL, NULL, 1.1);
INSERT INTO `model_filed_metadata` VALUES (504, 101, '照片啊', 'jtopcms_def_jt_asdasdasd', '1', 200, 'jt_asdasdasd', NULL, NULL, NULL, NULL, 5);
INSERT INTO `model_filed_metadata` VALUES (536, 111, '上传文件', 'jtopcms_def_jt_wk_doc', '1', 200, 'jt_wk_doc', NULL, NULL, NULL, NULL, 1);
INSERT INTO `model_filed_metadata` VALUES (557, 98, '名称', 'jtopcms_def_jt_spmc', '1', 60, 'jt_spmc', NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for model_html_config
-- ----------------------------
DROP TABLE IF EXISTS `model_html_config`;
CREATE TABLE `model_html_config`  (
  `htmlConfigId` bigint(20) NOT NULL AUTO_INCREMENT,
  `metaDataId` bigint(20) NOT NULL,
  `htmlElementId` bigint(20) NOT NULL,
  `htmlDesc` varchar(400)  NULL DEFAULT NULL,
  `isMustFill` int(6) NULL DEFAULT NULL,
  `maxLength` int(11) NULL DEFAULT NULL,
  `defaultValue` varchar(60)  NULL DEFAULT NULL,
  `choiceValue` text  NULL,
  `errorMessage` varchar(500)  NULL DEFAULT NULL,
  `htmlContent` longtext  NULL,
  `allowableFile` varchar(200)  NULL DEFAULT NULL,
  `dataType` int(6) NULL DEFAULT NULL,
  `style` varchar(3000)  NULL DEFAULT NULL,
  `cssClass` varchar(40)  NULL DEFAULT NULL,
  `javascript` text  NULL,
  `checkRegex` varchar(2000)  NULL DEFAULT NULL,
  `defaultValidate` varchar(300)  NULL DEFAULT NULL,
  `needMark` int(6) NULL DEFAULT NULL COMMENT '是否需要水印',
  `imageH` int(11) NULL DEFAULT NULL,
  `imageW` int(11) NULL DEFAULT NULL,
  `imageDisposeMode` int(6) NULL DEFAULT NULL,
  `linkModelId` bigint(20) NULL DEFAULT NULL,
  `linkType` int(6) NULL DEFAULT NULL COMMENT '是否选择类型数据关联',
  `linkFieldId` bigint(20) NULL DEFAULT NULL COMMENT '各选择类型元素数据关联id',
  `useSysUrl` int(6) NULL DEFAULT NULL COMMENT '文件是否使用系统生成地址，隐藏真实路径',
  `fullEditor` int(6) NULL DEFAULT NULL COMMENT '是否为全功能编辑器',
  `mainEditor` int(6) NULL DEFAULT NULL COMMENT '是否为主编辑器，一个模型只存在一个主编辑器',
  `editorW` int(11) NULL DEFAULT NULL,
  `editorH` int(11) NULL DEFAULT NULL,
  `blankCount` int(11) NULL DEFAULT NULL,
  `isListField` int(11) NULL DEFAULT NULL,
  `listShowSize` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`htmlConfigId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 596 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model_html_config
-- ----------------------------
INSERT INTO `model_html_config` VALUES (294, 258, 1, '', 1, 60, '', '', '', '', '', 1, '', 'form-input', '', '', '', 0, 0, 0, 0, -1, 0, -1, 0, 0, NULL, 0, 0, 0, 0, 10);
INSERT INTO `model_html_config` VALUES (295, 259, 5, '', 0, 60, '1', 'xp=1,vista=2 win 7=3,linux=4,unix=5', '', '', '', 3, '', 'form-radio', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (296, 260, 2, '', 0, 700, '', '', '', '', '', 1, 'width:300px;height:80px', 'form-textarea', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (297, 261, 10, '', 0, 60, '', '', '', '', '', NULL, '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (298, 262, 12, '', 0, 60, '', '', '', '', '', 1, '', '', '', '', '', 0, 0, 0, 0, -1, 0, -1, 0, 0, NULL, 0, 0, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (310, 274, 4, '', 0, 60, '', '擦拭=1', '', '', '', 3, '', '', '', '', '', 1, 0, 0, 0, NULL, 0, NULL, 1, 0, NULL, 648, 535, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (512, 476, 3, '', 0, 100000, '', '', '', '', '', 2, '', '', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 1, 1, 736, 565, 0, 0, 10);
INSERT INTO `model_html_config` VALUES (513, 477, 5, '', 0, 60, '', '业务咨询=1,改进建议=2,服务投诉=3,需求确认=4,人员查询=5', '', '', '', 3, '', 'form-radio', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (514, 478, 1, '', 0, 60, '', '', '', '', '', 1, '', 'form-input', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (515, 479, 1, '', 0, 60, '', '', '', '', '', 1, '', 'form-input', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (516, 480, 1, '', 0, 60, '', '', '', '', '', 1, '', 'form-input', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (517, 481, 1, '', 0, 300, '', '', '', '', '', 1, '', 'form-input', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (518, 482, 1, '', 0, 200, '', '', '', '', '', 1, '', 'form-input', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (519, 483, 11, '', 0, 60, '', '', '', '', '', 1, '', '', '', '', '', 0, 600, 800, 1, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (526, 490, 1, '', 0, 300, '', '', '', '', '', 1, '', 'form-input', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (527, 491, 1, '', 0, 120, '', '', '', '', '', 1, '', 'form-input', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, 0, 10);
INSERT INTO `model_html_config` VALUES (528, 492, 4, '', 0, 60, '-1', '博士=1,硕士=2,本科=3,大专=4,高中=5', '', '', '', 3, '', 'form-select', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (529, 493, 2, '', 0, 2000, '', '', '', '', '', 1, '', 'form-textarea', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (530, 494, 12, '', 0, 60, '', '', '', '', '', 1, '', '', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (531, 495, 14, '', 0, 200, '', '', '', '', '', 1, '', '', '', '', '', 0, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, 0, 10);
INSERT INTO `model_html_config` VALUES (532, 496, 14, '', 0, 60, '', '', '', '', '', 1, '', '', '', '', '', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, NULL, NULL);
INSERT INTO `model_html_config` VALUES (533, 497, 1, '', 1, 60, '', '', '', '', '', 1, 'width:30px', 'form-input', '', '', '9999', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 40, 1, 10);
INSERT INTO `model_html_config` VALUES (534, 498, 2, '', 0, 2000, '', '', '', '', '', 1, 'width:490px;height:120px', 'form-textarea', '', '', '9999', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, 0, 0);
INSERT INTO `model_html_config` VALUES (538, 502, 5, '', 0, 60, '', '项目经理=1,设计总监=2,工程师=3,前端美工=4,软件测试=5', '', '', '', 3, '', 'form-radio', '', '', '9999', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, 1, 10);
INSERT INTO `model_html_config` VALUES (539, 503, 4, '', 0, 60, '', '----- 请选择您的学历 -----=-1,博士=1,研究生=2,本科=3,大专=4,高中=5', '', '', '', 3, '', 'form-select', '', '', '9999', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, 0, 10);
INSERT INTO `model_html_config` VALUES (540, 504, 11, '', 0, 200, '', '', '', '', '', 1, '', 'form-select', '', '', '9999', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, 0, 10);
INSERT INTO `model_html_config` VALUES (574, 536, 10, '', 0, 200, '', '', '', '', '', 1, '', '', '', '', '9999', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, 0, 10);
INSERT INTO `model_html_config` VALUES (575, 537, 1, '', 0, 60, '', '', '', '', '', 4, '', 'form-input', '', '', '9999', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, 0, 10);
INSERT INTO `model_html_config` VALUES (576, 538, 1, '', 0, 60, '', '', '', '', '', 4, '', 'form-input', '', '', '9999', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 648, 235, 0, 0, 10);
INSERT INTO `model_html_config` VALUES (595, 557, 1, '', 1, 60, '', '', '', '', '', 1, 'width:302px;', 'form-input', '', '', '9999', 1, 0, 0, 0, -1, 0, -1, 1, 0, NULL, 752, 255, 0, 0, 10);

-- ----------------------------
-- Table structure for model_html_element
-- ----------------------------
DROP TABLE IF EXISTS `model_html_element`;
CREATE TABLE `model_html_element`  (
  `htmlElementId` bigint(20) NOT NULL,
  `htmlElementName` varchar(50)  NOT NULL,
  `htmlInputTemplet` mediumtext  NOT NULL,
  `htmlEditTemplet` mediumtext  NOT NULL,
  `layoutParam` varchar(300)  NULL DEFAULT NULL,
  `valueParam` varchar(300)  NULL DEFAULT NULL,
  PRIMARY KEY (`htmlElementId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model_html_element
-- ----------------------------
INSERT INTO `model_html_element` VALUES (1, '文本输入框', '<input type=\"text\"  placeholder=\"${ph}\"  maxLength=\"${maxLength}\" id=\"${filedSign}\" name=\"${filedSign}\" ${css} ${jsEvent} ${style}></input>', '<input type=\"text\" placeholder=\"${ph}\"  maxLength=\"${maxLength}\" id=\"${filedSign}\" name=\"${filedSign}\" value=\"$V{filedSign}\" ${css} ${jsEvent} ${style}></input>', '${maxLength},${filedSign}', '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (2, '多行文本框', '<textarea placeholder=\"${ph}\" id=\"${filedSign}\" name=\"${filedSign}\" ${css} ${jsEvent} ${style} ></textarea>', '<textarea placeholder=\"${ph}\" id=\"${filedSign}\" name=\"${filedSign}\" ${css} ${jsEvent} ${style}>$V{filedSign}</textarea>', '${filedSign}', '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (3, '文本编辑器', '<script type=\"text/plain\" id=\"${filedSign}\" name=\"${filedSign}\" ${css} ${jsEvent} ${style} style=\"width:${editorW}px;height:${editorH}px;\"></script><input type=\"hidden\" id=\"${filedSign}_jtop_sys_hidden_temp_html\" name=\"${filedSign}_jtop_sys_hidden_temp_html\" ></input><script type=\"text/javascript\">var editor1_${filedSign} = UE.getEditor(\'${filedSign}\',{zIndex : 99, autoFloatEnabled:false, allowDivTransToP: false, enableAutoSave:false, scaleEnabled:true, maximumWords:20000${simpleMode}});   </script>', '<script type=\"text/plain\" id=\"${filedSign}\" name=\"${filedSign}\" ${css} ${jsEvent} ${style} style=\"width:${editorW}px;height:${editorH}px;\">$V{filedSign}</script><input type=\"hidden\" id=\"${filedSign}_jtop_sys_hidden_temp_html\" name=\"${filedSign}_jtop_sys_hidden_temp_html\" ></input><script type=\"text/javascript\">var ue_${filedSign} = UE.getEditor(\'${filedSign}\',{zIndex : 99, autoFloatEnabled:false, allowDivTransToP: false, enableAutoSave:false, scaleEnabled:true, maximumWords:20000${simpleMode}});    </script>', '${filedSign},${siteBase},${editorH},${editorW},${simpleMode}', '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (4, '下拉选择框', ' <select id=\"${filedSign}\" name=\"${filedSign}\" ${css} ${jsEvent} ${style}>,<option value=\"${value}\" ${style}>${key}</option>,<option value=\"${value}\" ${style} selected>${key}</option>,</select>&nbsp;&nbsp;${ps}', ' <select id=\"${filedSign}\" name=\"${filedSign}\" ${css} ${jsEvent} ${style}>,<option value=\"${value}\" ${style}>${key}</option>,<option value=\"${value}\" ${style} selected>${key}</option>,</select>&nbsp;&nbsp;${ps}', '${filedSign},${value},${key}', '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (5, '单选框', '<input name=\"${filedSign}\" type=\"radio\" value=\"${value}\" ${css} ${jsEvent} /><span ${style}>${key}</span>\r\n,<input name=\"${filedSign}\" type=\"radio\" value=\"${value}\"  ${css} ${jsEvent}  checked/><span ${style}>${key}</span>&nbsp;&nbsp;&nbsp;${ps}\r\n\r\n', '<input name=\"${filedSign}\" type=\"radio\" value=\"${value}\" ${css} ${jsEvent}/><span ${style}>${key}</span>\r\n,<input name=\"${filedSign}\" type=\"radio\" value=\"${value}\"  ${css} ${jsEvent} checked/><span ${style}>${key}</span>&nbsp;&nbsp;&nbsp;${ps}\r\n', '${filedSign},${value},${key}', '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (6, '复选框', '<input type=\"checkbox\" name=\"${filedSign}\"  value=\"${value}\" ${css} ${jsEvent} ><span ${style}>${key}</span></input>&nbsp;,<input type=\"checkbox\" name=\"${filedSign}\"  value=\"${value}\" ${css} ${jsEvent} checked><span ${style}>${key}</span></input>${ps}', '<input type=\"checkbox\" name=\"${filedSign}\" value=\"${value}\" ${css} ${jsEvent}><span ${style}>${key}</span></input>&nbsp;,<input type=\"checkbox\" name=\"${filedSign}\" value=\"${value}\" ${css} ${jsEvent} checked><span ${style}>${key}</span></input>${ps}', '${filedSign},${value},${key}', '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (7, '日期', '<input id=\"${filedSign}\" name=\"${filedSign}\" style=\"width:302px;\"   maxLength=\"40\" type=\"text\" class=\"form-input-date\" onmousedown=\"javascript:WdatePicker({skin:\'twoer\',dateFmt:\'yyyy-MM-dd\'});\"/>${ps}', '<input id=\"${filedSign}\" name=\"${filedSign}\"  value=\"$V{filedSign}\" style=\"width:302px;\"  maxLength=\"30\" type=\"text\" class=\"form-input-date\" onmousedown=\"javascript:WdatePicker({skin:\'twoer\',dateFmt:\'yyyy-MM-dd\'});\"/>${ps}', '${filedSign}', '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (8, '时间', '<input id=\"${filedSign}\" name=\"${filedSign}\" style=\"width:302px;\"  maxlength=\"40\" type=\"text\" class=\"form-input-date\" onmousedown=\"javascript:WdatePicker({skin:\'twoer\',dateFmt:\'HH:mm:ss\'});\"/>${ps}', '<input id=\"${filedSign}\" name=\"${filedSign}\"  value=\"$V{filedSign}\" style=\"width:302px;\"  maxlength=\"40\" type=\"text\" class=\"form-input-date\" onmousedown=\"javascript:WdatePicker({skin:\'twoer\',dateFmt:\'HH:mm:ss\'});\"/>${ps}', '${filedSign}', '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (9, '日期时间', '<input id=\"${filedSign}\" name=\"${filedSign}\" style=\"width:302px;\"  maxlength=\"40\" type=\"text\" class=\"form-input-date\" onmousedown=\"javascript:WdatePicker({skin:\'twoer\',dateFmt:\'yyyy-MM-dd HH:mm:ss\'});\"/>${ps}', '<input id=\"${filedSign}\" name=\"${filedSign}\"  value=\"$V{filedSign}\" style=\"width:302px;\"  maxlength=\"40\" type=\"text\" class=\"form-input-date\" onmousedown=\"javascript:WdatePicker({skin:\'twoer\',dateFmt:\'yyyy-MM-dd HH:mm:ss\'});\"/>${ps}', '${filedSign}', '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (10, '文件上传', '<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"form-table-upload\">\r\n											<tr>\r\n												<td>\r\n													<input id=\"${filedSign}_sys_jtopcms_file_info\" style=\"width: 253px;\" type=\"text\" class=\"form-input\" readonly/>&nbsp; \r\n												</td>\r\n												<td>\r\n													<img style=\"cursor:pointer\" src=\"${siteBase}/core/style/icon/del.gif\" height=\"16\" width=\"16\" onclick=\"javascript:deleteFile(\'${filedSign}\');\" title=\"删除文件\" class=\"img-icon\" />&nbsp;<img style=\"cursor:pointer\" src=\"${siteBase}/core/style/icon/upload_file.png\" height=\"16\" width=\"16\" onclick=\"javascript:showModuleFileDialog(\'${filedSign}\',\'${filedSign}\');\" title=\"上传文件\" class=\"img-icon\" />\r\n													<input id=\"${filedSign}\" name=\"${filedSign}\" type=\"hidden\" />												\r\n												</td>\r\n											</tr>\r\n										</table>${ps}', '<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"form-table-upload\">\r\n											<tr>\r\n												<td>\r\n													<input id=\"${filedSign}_sys_jtopcms_file_info\" style=\"width: 243px;\" type=\"text\" class=\"form-input\" value=\"$V{fileInfo}\" readonly/>&nbsp; \r\n												</td>\r\n												<td>\r\n													<img style=\"cursor:pointer\" src=\"${siteBase}/core/style/icon/del.gif\" height=\"16\" width=\"16\" onclick=\"javascript:deleteFile(\'${filedSign}\');\" title=\"删除文件\" class=\"img-icon\" />&nbsp;<img style=\"cursor:pointer\" src=\"${siteBase}/core/style/icons/disk.png\" height=\"16\" width=\"16\" onclick=\"javascript:systemDownloadFile(\'${filedSign}\');\" title=\"下载文件\" class=\"img-icon\" />&nbsp;<img style=\"cursor:pointer\" src=\"${siteBase}/core/style/icon/upload_file.png\" height=\"16\" width=\"16\" onclick=\"javascript:showModuleFileDialog(\'${filedSign}\',\'${filedSign}\');\" title=\"上传文件\" class=\"img-icon\" />\r\n													<input id=\"${filedSign}\" name=\"${filedSign}\" type=\"hidden\" value=\"$V{filedSign}\" />\r\n													<input id=\"${filedSign}_sys_jtopcms_old\" name=\"${filedSign}_sys_jtopcms_old\" type=\"hidden\" value=\"$V{filedSign}\"/>\r\n												</td>\r\n											</tr>\r\n										</table>${ps}', '${filedSign},${siteBase}', '$V{filedSign},$V{fileInfo}');
INSERT INTO `model_html_element` VALUES (11, '图片上传', '<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"form-table-upload\">\r\n										<tr>\r\n											<td>\r\n												<a id=\"${filedSign}CmsSysShowSingleImage\" class=\"cmsSysShowSingleImage\" href=\"${siteBase}/core/style/blue/images/no-image.gif\"><img id=\"${filedSign}CmsSysImgShow\" src=\"${siteBase}/core/style/blue/images/no-image.gif\"/></a>\r\n											</td>\r\n											<td> \r\n												<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"65px\" class=\"form-table-big\">\r\n													<tr>\r\n														<td>\r\n															&nbsp;\r\n															<input type=\"button\" onclick=\"javascript:showModuleImageDialog(\'${filedSign}CmsSysImgShow\',\'${filedSign}\',\'${imageW}\',\'${imageH}\',\'${imageDisposeMode}\',\'${needMark}\')\" value=\"上传\" onclick=\"\" class=\"btn-1\" />\r\n															<input type=\"button\" value=\"裁剪\" onclick=\"javascript:disposeImage(\'${filedSign}\',\'${imageW}\',\'${imageH}\',false,\'-1\');\" class=\"btn-1\" />\r\n															<input type=\"button\" value=\"删除\" onclick=\"javascript:deleteImage(\'${filedSign}\');\" class=\"btn-1\" />\r\n														</td>\r\n													</tr>\r\n													<tr>\r\n														<td>\r\n															&nbsp;&nbsp;宽&nbsp;&nbsp;\r\n															<input id=\"${filedSign}CmsSysImgWidth\"  class=\"form-input\" readonly type=\"text\" style=\"width:44px\"/>\r\n															&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;\r\n															<input id=\"${filedSign}CmsSysImgHeight\" class=\"form-input\" readonly type=\"text\" style=\"width:44px\"/>\r\n																			\r\n																				\r\n														</td>\r\n													</tr>\r\n												</table>\r\n												<input id=\"${filedSign}\" name=\"${filedSign}\" type=\"hidden\" />\r\n												\r\n\r\n											</td>\r\n										</tr>\r\n									</table>${ps}', '<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"form-table-upload\">\r\n										<tr>\r\n											<td>\r\n												<a id=\"${filedSign}CmsSysShowSingleImage\" class=\"cmsSysShowSingleImage\" href=\"$V{imageSrc}\"><img id=\"${filedSign}CmsSysImgShow\" src=\"$V{imageFiledSign}\" width=\"90\" height=\"67\"/></a>\r\n											</td>\r\n											<td> \r\n												<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"65px\" class=\"form-table-big\">\r\n													<tr>\r\n														<td>\r\n															&nbsp;\r\n															<input type=\"button\" onclick=\"javascript:showModuleImageDialog(\'${filedSign}CmsSysImgShow\',\'${filedSign}\',\'${imageW}\',\'${imageH}\',\'${imageDisposeMode}\',\'${needMark}\')\" value=\"上传\" onclick=\"\" class=\"btn-1\" />\r\n															<input type=\"button\" value=\"裁剪\" onclick=\"javascript:disposeImage(\'${filedSign}\',\'${imageW}\',\'${imageH}\',false,\'-1\');\" class=\"btn-1\" />\r\n															<input type=\"button\" value=\"删除\" onclick=\"javascript:deleteImage(\'${filedSign}\');\" class=\"btn-1\" />\r\n														</td>\r\n													</tr>\r\n													<tr>\r\n														<td>\r\n															&nbsp;&nbsp;宽&nbsp;&nbsp;\r\n															<input id=\"${filedSign}CmsSysImgWidth\"  class=\"form-input\" type=\"text\" readonly style=\"width:44px\" value=\"$V{width}\"/>\r\n															&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;\r\n															<input id=\"${filedSign}CmsSysImgHeight\" class=\"form-input\" type=\"text\" readonly style=\"width:44px\" value=\"$V{height}\"/>\r\n																				\r\n														</td>\r\n													</tr>\r\n												</table>\r\n												<input id=\"${filedSign}\" name=\"${filedSign}\" type=\"hidden\" value=\"$V{filedSign}\"/>\r\n												<input id=\"${filedSign}_sys_jtopcms_old\" name=\"${filedSign}_sys_jtopcms_old\" type=\"hidden\" value=\"$V{filedSign}\"/>\r\n											</td>\r\n										</tr>\r\n									</table>${ps}', '${filedSign},${siteBase},${imageW},${imageH},${needMark},${imageDisposeMode}', '$V{filedSign},$V{imageFiledSign},$V{imageSrc},$V{imageInfo},$V{width},$V{height}');
INSERT INTO `model_html_element` VALUES (12, '媒体上传', '<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"form-table-upload\">\r\n											<tr>\r\n												<td width=\"193\">\r\n													<iframe id=\"${filedSign}_sys_jtopcms_iframe\" frameborder=\"0\" src=\"${siteBase}/core/content/UploadVideoModule.jsp\" width=\"752\" height=\"325\" scrolling=no></iframe>\r\n												</td>\r\n											</tr>\r\n\r\n											<tr>\r\n												<td>\r\n													视频信息:&nbsp;<input id=\"${filedSign}_sys_jtopcms_media_name\" readonly style=\"width: 226px;\" maxlength=\"22\" type=\"text\" class=\"form-input\" />&nbsp;&nbsp;<input id=\"${filedSign}_sys_jtopcms_media_showtime\" readonly style=\"width: 50px;\" maxlength=\"20\" type=\"text\" class=\"form-input\" />&nbsp;秒&nbsp;&nbsp;&nbsp;<input id=\"${filedSign}_sys_jtopcms_media_width\" readonly style=\"width: 40px;\" maxlength=\"20\" type=\"text\" class=\"form-input\" />&nbsp;宽&nbsp;&nbsp;<input id=\"${filedSign}_sys_jtopcms_media_height\" readonly style=\"width: 40px;\" maxlength=\"20\" type=\"text\" class=\"form-input\" />&nbsp;高&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"button\" value=\"阅图\" onclick=\"javascript:showCover(\'${filedSign}\');\" class=\"btn-1\" />&nbsp;<input type=\"button\" value=\"截图\" onclick=\"javascript:cutCover(\'${filedSign}_sys_jtopcms_iframe\',\'${filedSign}\');\" class=\"btn-1\" />&nbsp;<input type=\"button\" value=\"删除\" onclick=\"javascript:deleteMedia(\'${filedSign}\');\" class=\"btn-1\" />&nbsp;<input type=\"button\" onclick=\"javascript:showModuleMediaDialog(\'${filedSign}\',\'${filedSign}\');\" value=\"上传\" class=\"btn-1\" />\r\n													<input id=\"${filedSign}\" name=\"${filedSign}\" type=\"hidden\" />\r\n													<input id=\"${filedSign}_delete_flag\" name=\"${filedSign}_delete_flag\" type=\"hidden\"/>\r\n                          <input id=\"${filedSign}_sys_jtopcms_media_type\" name=\"${filedSign}_sys_jtopcms_media_type\" type=\"hidden\" />\r\n                          <input id=\"${filedSign}_sys_jtopcms_media_cover_src\" name=\"${filedSign}_sys_jtopcms_media_cover_src\" type=\"hidden\" />\r\n													<input id=\"${filedSign}_sys_jtopcms_media_cover_w\" name=\"${filedSign}_sys_jtopcms_media_cover_w\" type=\"hidden\" />\r\n													<input id=\"${filedSign}_sys_jtopcms_media_cover_h\" name=\"${filedSign}_sys_jtopcms_media_cover_h\" type=\"hidden\" />\r\n													<input id=\"${filedSign}_sys_jtopcms_media_cover_n\" name=\"${filedSign}_sys_jtopcms_media_cover_n\" type=\"hidden\" />\r\n												</td>\r\n											</tr>\r\n										</table>${ps}', '<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"form-table-upload\">\r\n											<tr>\r\n												<td width=\"193\">\r\n													<iframe id=\"${filedSign}_sys_jtopcms_iframe\" frameborder=\"0\" src=\"${siteBase}/core/content/UploadVideoModule.jsp?fileUrl=$V{mediaFiledSign}&autoStart=false&cover=$V{cover}\" width=\"782\" height=\"325\" scrolling=no></iframe>\r\n												</td>\r\n											</tr>\r\n\r\n											<tr>\r\n												<td>\r\n													视频信息:&nbsp;<input id=\"${filedSign}_sys_jtopcms_media_name\" readonly style=\"width: 224px;\" maxlength=\"22\" type=\"text\" class=\"form-input\" value=\"$V{name}\"/>&nbsp;&nbsp;&nbsp;<input id=\"${filedSign}_sys_jtopcms_media_showtime\" readonly style=\"width: 50px;\" maxlength=\"20\" type=\"text\" class=\"form-input\" value=\"$V{showTime}\"/>&nbsp;秒&nbsp;&nbsp;&nbsp;<input id=\"${filedSign}_sys_jtopcms_media_width\" readonly style=\"width: 40px;\" maxlength=\"20\" type=\"text\" class=\"form-input\" value=\"$V{width}\"/>&nbsp;宽&nbsp;&nbsp;<input id=\"${filedSign}_sys_jtopcms_media_height\" readonly style=\"width: 40px;\" maxlength=\"20\" type=\"text\" class=\"form-input\" value=\"$V{height}\"/>&nbsp;高&nbsp;&nbsp;&nbsp;<input type=\"button\" value=\"阅图\" onclick=\"javascript:showCover(\'${filedSign}\');\" class=\"btn-1\" />												\r\n													<input type=\"button\" value=\"截图\" onclick=\"javascript:cutCover(\'${filedSign}_sys_jtopcms_iframe\',\'${filedSign}\');\" class=\"btn-1\" />\r\n													<input type=\"button\" value=\"删除\" onclick=\"javascript:deleteMedia(\'${filedSign}\');\" class=\"btn-1\" />\r\n													<input type=\"button\" onclick=\"javascript:showModuleMediaDialog(\'${filedSign}\',\'${filedSign}\');\" value=\"上传\" class=\"btn-1\" />\r\n													<input id=\"${filedSign}\" name=\"${filedSign}\" type=\"hidden\" value=\"$V{filedSign}\"/>\r\n													<input id=\"${filedSign}_sys_jtopcms_old\" name=\"${filedSign}_sys_jtopcms_old\" type=\"hidden\" value=\"$V{filedSign}\"/>\r\n													<input id=\"${filedSign}_sys_jtopcms_old_cover\" name=\"${filedSign}_sys_jtopcms_old_cover\" type=\"hidden\" value=\"$V{coverReUrl}\"/>\r\n													<input id=\"${filedSign}_delete_flag\" name=\"${filedSign}_delete_flag\" type=\"hidden\"/>\r\n                          <input id=\"${filedSign}_sys_jtopcms_media_type\" name=\"${filedSign}_sys_jtopcms_media_type\" type=\"hidden\" value=\"$V{type}\"/>\r\n                          <input id=\"${filedSign}_sys_jtopcms_media_cover_src\" name=\"${filedSign}_sys_jtopcms_media_cover_src\" type=\"hidden\" value=\"$V{cover}\"/>\r\n													<input id=\"${filedSign}_sys_jtopcms_media_cover_w\" name=\"${filedSign}_sys_jtopcms_media_cover_w\" type=\"hidden\" value=\"$V{width}\"/>\r\n													<input id=\"${filedSign}_sys_jtopcms_media_cover_h\" name=\"${filedSign}_sys_jtopcms_media_cover_h\" type=\"hidden\" value=\"$V{height}\"/>\r\n													<input id=\"${filedSign}_sys_jtopcms_media_cover_n\" name=\"${filedSign}_sys_jtopcms_media_cover_n\" type=\"hidden\" value=\"$V{coverName}\"/>\r\n												</td>\r\n											</tr>\r\n										</table>${ps}', '${filedSign},${siteBase}', '$V{filedSign},$V{mediaFiledSign},$V{cover},$V{coverReUrl},$V{coverName},$V{showTime},$V{name},$V{width},$V{height},$V{type}');
INSERT INTO `model_html_element` VALUES (13, '自定义HTML', '', '', NULL, '$V{filedSign}');
INSERT INTO `model_html_element` VALUES (14, '图片集', '', '', '', '');

-- ----------------------------
-- Table structure for model_icon_config
-- ----------------------------
DROP TABLE IF EXISTS `model_icon_config`;
CREATE TABLE `model_icon_config`  (
  `modelId` bigint(20) NOT NULL DEFAULT 0,
  `iconFile` varchar(200)  NULL DEFAULT NULL,
  PRIMARY KEY (`modelId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model_icon_config
-- ----------------------------
INSERT INTO `model_icon_config` VALUES (0, 'channel_default.png');
INSERT INTO `model_icon_config` VALUES (1, 'channel_document.png');
INSERT INTO `model_icon_config` VALUES (2, 'channel_image.png');
INSERT INTO `model_icon_config` VALUES (3, 'channel_video.png');
INSERT INTO `model_icon_config` VALUES (4, 'channel_download.png');

-- ----------------------------
-- Table structure for model_persistence_code
-- ----------------------------
DROP TABLE IF EXISTS `model_persistence_code`;
CREATE TABLE `model_persistence_code`  (
  `dataModelId` bigint(20) NOT NULL DEFAULT 0,
  `insertSql` mediumtext  NULL,
  `deleteSql` mediumtext  NULL,
  `updateSql` mediumtext  NULL,
  `selectSql` mediumtext  NULL,
  `selectColumn` mediumtext  NULL,
  `listSelectColumn` mediumtext  NULL,
  PRIMARY KEY (`dataModelId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model_persistence_code
-- ----------------------------
INSERT INTO `model_persistence_code` VALUES (82, 'insert into jtopcms_model_mhxw (jtopcms_def_mh_wz, contentId ) values (?, ? )', 'delete from jtopcms_model_mhxw where contentId=?', 'update jtopcms_model_mhxw set jtopcms_def_mh_wz=? where contentId=?', 'select jtopcms_def_mh_wz, contentId from jtopcms_model_mhxw where contentId=?', 'tmp.jtopcms_def_mh_wz as mh_wz', 'ci.contentId');
INSERT INTO `model_persistence_code` VALUES (83, 'insert into jtopcms_model_mh_ly (jtopcms_def_mh_lylx, jtopcms_def_mh_ly_lxdh, jtopcms_def_mh_ly_qqhm, jtopcms_def_mh_ly_mail, jtopcms_def_mh_ly_dwdz, jtopcms_def_mh_ly_slr, jtopcms_def_mh_ly_zltp, contentId ) values (?, ?, ?, ?, ?, ?, ?, ? )', 'delete from jtopcms_model_mh_ly where contentId=?', 'update jtopcms_model_mh_ly set jtopcms_def_mh_lylx=?, jtopcms_def_mh_ly_lxdh=?, jtopcms_def_mh_ly_qqhm=?, jtopcms_def_mh_ly_mail=?, jtopcms_def_mh_ly_dwdz=?, jtopcms_def_mh_ly_slr=?, jtopcms_def_mh_ly_zltp=? where contentId=?', 'select jtopcms_def_mh_lylx, jtopcms_def_mh_ly_lxdh, jtopcms_def_mh_ly_qqhm, jtopcms_def_mh_ly_mail, jtopcms_def_mh_ly_dwdz, jtopcms_def_mh_ly_slr, jtopcms_def_mh_ly_zltp, contentId from jtopcms_model_mh_ly where contentId=?', 'tmp.jtopcms_def_mh_lylx as mh_lylx, tmp.jtopcms_def_mh_ly_lxdh as mh_ly_lxdh, tmp.jtopcms_def_mh_ly_qqhm as mh_ly_qqhm, tmp.jtopcms_def_mh_ly_mail as mh_ly_mail, tmp.jtopcms_def_mh_ly_dwdz as mh_ly_dwdz, tmp.jtopcms_def_mh_ly_slr as mh_ly_slr, tmp.jtopcms_def_mh_ly_zltp as mh_ly_zltp', 'ci.contentId');
INSERT INTO `model_persistence_code` VALUES (98, 'insert into jtopcms_model_mh_sp (jtopcms_def_jt_spmc, jtopcms_def_mh_sp_file, contentId ) values (?, ?, ? )', 'delete from jtopcms_model_mh_sp where contentId=?', 'update jtopcms_model_mh_sp set jtopcms_def_jt_spmc=?, jtopcms_def_mh_sp_file=? where contentId=?', 'select jtopcms_def_jt_spmc, jtopcms_def_mh_sp_file, contentId from jtopcms_model_mh_sp where contentId=?', 'tmp.jtopcms_def_jt_spmc as jt_spmc, tmp.jtopcms_def_mh_sp_file as mh_sp_file', 'ci.contentId');
INSERT INTO `model_persistence_code` VALUES (99, 'insert into jtopcms_model_mh_tpj (jtopcms_def_mh_tpj2, jtopcms_def_mh_tpj1, contentId ) values (?, ?, ? )', 'delete from jtopcms_model_mh_tpj where contentId=?', 'update jtopcms_model_mh_tpj set jtopcms_def_mh_tpj2=?, jtopcms_def_mh_tpj1=? where contentId=?', 'select jtopcms_def_mh_tpj2, jtopcms_def_mh_tpj1, contentId from jtopcms_model_mh_tpj where contentId=?', 'tmp.jtopcms_def_mh_tpj2 as mh_tpj2, tmp.jtopcms_def_mh_tpj1 as mh_tpj1', 'ci.contentId');
INSERT INTO `model_persistence_code` VALUES (101, 'insert into jtopcms_model_mh_jl (jtopcms_def_jt_jl_xm, jtopcms_def_jt_jl_xl, jtopcms_def_jt_jl_jieshao, jtopcms_def_jt_mh_jl_gw, jtopcms_def_jt_asdasdasd, contentId ) values (?, ?, ?, ?, ?, ? )', 'delete from jtopcms_model_mh_jl where contentId=?', 'update jtopcms_model_mh_jl set jtopcms_def_jt_jl_xm=?, jtopcms_def_jt_jl_xl=?, jtopcms_def_jt_jl_jieshao=?, jtopcms_def_jt_mh_jl_gw=?, jtopcms_def_jt_asdasdasd=? where contentId=?', 'select jtopcms_def_jt_jl_xm, jtopcms_def_jt_jl_xl, jtopcms_def_jt_jl_jieshao, jtopcms_def_jt_mh_jl_gw, jtopcms_def_jt_asdasdasd, contentId from jtopcms_model_mh_jl where contentId=?', 'tmp.jtopcms_def_jt_jl_xm as jt_jl_xm, tmp.jtopcms_def_jt_jl_xl as jt_jl_xl, tmp.jtopcms_def_jt_jl_jieshao as jt_jl_jieshao, tmp.jtopcms_def_jt_mh_jl_gw as jt_mh_jl_gw, tmp.jtopcms_def_jt_asdasdasd as jt_asdasdasd', 'ci.contentId');
INSERT INTO `model_persistence_code` VALUES (111, 'insert into jtopcms_model_wenku (jtopcms_def_jt_wk_doc, contentId ) values (?, ? )', 'delete from jtopcms_model_wenku where contentId=?', 'update jtopcms_model_wenku set jtopcms_def_jt_wk_doc=? where contentId=?', 'select jtopcms_def_jt_wk_doc, contentId from jtopcms_model_wenku where contentId=?', 'tmp.jtopcms_def_jt_wk_doc as jt_wk_doc', 'ci.contentId');

-- ----------------------------
-- Table structure for model_res_path_inject_assist
-- ----------------------------
DROP TABLE IF EXISTS `model_res_path_inject_assist`;
CREATE TABLE `model_res_path_inject_assist`  (
  `modelId` bigint(20) NOT NULL,
  `fieldName` varchar(240)  NOT NULL,
  `resType` int(6) NOT NULL,
  `metaDataId` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model_res_path_inject_assist
-- ----------------------------
INSERT INTO `model_res_path_inject_assist` VALUES (98, 'mh_sp_file', 2, 494);
INSERT INTO `model_res_path_inject_assist` VALUES (99, 'mh_tpj2', 4, 496);
INSERT INTO `model_res_path_inject_assist` VALUES (83, 'mh_ly_zltp', 1, 483);
INSERT INTO `model_res_path_inject_assist` VALUES (101, 'jt_asdasdasd', 1, 504);
INSERT INTO `model_res_path_inject_assist` VALUES (111, 'jt_wk_doc', 3, 536);
INSERT INTO `model_res_path_inject_assist` VALUES (99, 'mh_tpj1', 4, 495);

-- ----------------------------
-- Table structure for model_validate_config
-- ----------------------------
DROP TABLE IF EXISTS `model_validate_config`;
CREATE TABLE `model_validate_config`  (
  `validateConfigId` bigint(20) NOT NULL AUTO_INCREMENT,
  `validateName` varchar(60)  NOT NULL,
  `regulation` varchar(400)  NOT NULL,
  `errorMessage` varchar(600)  NOT NULL,
  PRIMARY KEY (`validateConfigId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model_validate_config
-- ----------------------------
INSERT INTO `model_validate_config` VALUES (1, '电话传真', '/^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$/', '不正确的电话号码');
INSERT INTO `model_validate_config` VALUES (2, '手机号码', '/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/', '不正确的手机号码');
INSERT INTO `model_validate_config` VALUES (3, '电子邮件', '/^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$/', '电子邮件格式错误');
INSERT INTO `model_validate_config` VALUES (4, '邮政编码', '/^\\d{6}$/', '不正确的邮政编码');
INSERT INTO `model_validate_config` VALUES (5, '网页链接', '/^(https|http|ftp|rtsp|mms):\\/\\/[A-Za-z0-9]+\\.[A-Za-z0-9]+[\\/=\\?%\\-&_~`@[\\]\\\':+!]*([^<>\\\"\\\"])*$/', '网页链接格式错误');
INSERT INTO `model_validate_config` VALUES (6, 'IP地址 ', '/^[0-9.]{1,20}$/', 'IP地址格式错误');
INSERT INTO `model_validate_config` VALUES (7, '用户名称', '/^[a-zA-Z]{1,30}$/', '非法的用户名称');
INSERT INTO `model_validate_config` VALUES (8, '密码数据', '/^[\\w~!@#$%^&*()_+]{6,20}$/', '密码规则错误');
INSERT INTO `model_validate_config` VALUES (9, '中文字符', '/^[\\u0391-\\uFFE5]+$/', '输入内容非中文字符');
INSERT INTO `model_validate_config` VALUES (10, '英文字母', '/^[a-zA-Z]+$/', '输入内容非英文字符');
INSERT INTO `model_validate_config` VALUES (11, '任意数字', '/^\\d+\\.{0,1}\\d+$/', '输入内容非数字字符');
INSERT INTO `model_validate_config` VALUES (12, '整数', '/^[-+]?\\d*$/', '输入内容非整数');
INSERT INTO `model_validate_config` VALUES (13, '小数', '/^[-\\+]?\\d+(\\.\\d+)?$/', '输入内容非小数');
INSERT INTO `model_validate_config` VALUES (14, '日期', '/^(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})$/', '输入内容非日期格式');
INSERT INTO `model_validate_config` VALUES (15, '时间', '/^((20|21|22|23|[0-1]\\d)\\:[0-5][0-9])(\\:[0-5][0-9])?$/', '输入内容非时间格式');
INSERT INTO `model_validate_config` VALUES (16, '日期时间', '/^(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})(\\d{1,2}):(\\d{1,2}):(\\d{1,2})$/', '输入内容非日期时间格式');

-- ----------------------------
-- Table structure for pick_content_rule
-- ----------------------------
DROP TABLE IF EXISTS `pick_content_rule`;
CREATE TABLE `pick_content_rule`  (
  `pickCfgId` bigint(20) NOT NULL AUTO_INCREMENT,
  `configName` varchar(80)  NOT NULL,
  `extModelId` bigint(20) NULL DEFAULT NULL,
  `listHeadUrlRule` varchar(500)  NULL DEFAULT NULL,
  `listUrlRule` varchar(700)  NULL DEFAULT NULL,
  `contentUrlRule` text  NULL,
  `contentPageUrlRule` text  NULL,
  `prefixSiteUrl` varchar(300)  NULL DEFAULT NULL,
  `configDesc` varchar(600)  NULL DEFAULT NULL,
  `timeFormat` varchar(30)  NULL DEFAULT NULL,
  `listStart` text  NULL,
  `listEnd` text  NULL,
  `titleStart` text  NULL,
  `titleEnd` text  NULL,
  `contentStart` text  NULL,
  `contentEnd` text  NULL,
  `summaryStart` text  NULL,
  `summaryEnd` text  NULL,
  `addDateStart` text  NULL,
  `addDateEnd` text  NULL,
  `authorStart` text  NULL,
  `authorEnd` text  NULL,
  `sourceStart` text  NULL,
  `sourceEnd` text  NULL,
  `keywordStart` text  NULL,
  `keywordEnd` text  NULL,
  `htmlMode` int(6) NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`pickCfgId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pick_content_rule
-- ----------------------------
INSERT INTO `pick_content_rule` VALUES (15, 'sina 国内新闻', NULL, '', 'http://roll.news.sina.com.cn/news/gnxw/gdxw1/index_{1-50}.shtml', 'http://news.sina.com.cn/c/{数字}[4]-{数字}[1,2]-{数字}[1,2]/{任意}[8,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (16, 'sohu 国际要闻', NULL, 'http://news.sohu.com/1/0903/62/subject212846267.shtml', 'http://news.sohu.com/1/0903/62/subject212846267_{1476-1200}.shtml', 'http://news.sohu.com/{数字}[7,8]/{任意}[8,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;h1 itemprop=&quot;headline&quot;&gt;', '&lt;/h1&gt;', '&lt;div itemprop=&quot;articleBody&quot;&gt;', '&lt;!-- seo标签描述 --&gt;', 'id=&quot;description&quot;', '', NULL, NULL, '', '', 'id=&quot;sourceOrganization&quot;', '', '', '', 1, 5);
INSERT INTO `pick_content_rule` VALUES (22, 'sina 综合分析', NULL, '', 'http://roll.news.sina.com.cn/news/gnxw/zs-pl/index_{1-50}.shtml', 'http://news.sina.com.cn/c/{数字}[4]-{数字}[1,2]-{数字}[1,2]/{任意}[8,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (23, 'sohu 世态万象', NULL, 'http://news.sohu.com/shehuixinwen.shtml', 'http://news.sohu.com/shehuixinwen_{1193-1100}.shtml', 'http://news.sohu.com/{数字}[7,8]/{任意}[8,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;h1 itemprop=&quot;headline&quot;&gt;', '&lt;/h1&gt;', '&lt;div itemprop=&quot;articleBody&quot;&gt;', '&lt;!-- seo标签描述 --&gt;', 'id=&quot;description&quot;', '', NULL, NULL, '', '', 'id=&quot;sourceOrganization&quot;', '', '', '', 1, 5);
INSERT INTO `pick_content_rule` VALUES (24, 'sina 娱乐', NULL, '', 'http://roll.ent.sina.com.cn/ent_more/mxqjc/ndqd/index_1{1-100}.shtml', 'http://ent.sina.com.cn/s/m/{数字}[4]-{数字}[1,2]-{数字}[1,2]/{任意}[7,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (25, 'sina 财经', NULL, '', 'http://roll.finance.sina.com.cn/finance/cj4/index_{1~100}.shtml', 'http://finance.sina.com.cn/chanjing/{任意}[1,20]/{任意}[7,20]/{任意}[8,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (26, 'sina 体育', NULL, '', 'http://sports.sina.com.cn/', 'http://sports.sina.com.cn/{任意}[1,20]/{任意}[7,15]/{任意}[8,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (27, 'sina 科技', NULL, '', 'http://tech.sina.com.cn/', 'http://tech.sina.com.cn/{任意}[1,15]/{任意}[7,12]/{任意}[7,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (28, 'sina 读书', NULL, '', 'http://book.sina.com.cn/', 'http://book.sina.com.cn/news/{任意}[1,15]/{任意}[7,20]/{任意}[7,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (29, 'sina 健康', NULL, '', 'http://health.sina.com.cn/', 'http://health.sina.com.cn/{任意}[1,15]/{任意}[1,15]/{任意}[7,20]/{任意}[7,30].shtml,\r\nhttp://health.sina.com.cn/{任意}[1,15]/{任意}[7,15]/{任意}[1,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (30, 'sina 军事', NULL, '', 'http://roll.mil.news.sina.com.cn/col/gjjq/index_{1-100}.shtml', 'http://mil.news.sina.com.cn/{任意}[7,15]/{任意}[8,30].html', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (31, 'sina 港澳台', NULL, '', 'http://roll.news.sina.com.cn/news/gnxw/gatxw/index_{1-50}.shtml', 'http://news.sina.com.cn/c/{数字}[4]-{数字}[1,2]-{数字}[1,2]/{任意}[8,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);
INSERT INTO `pick_content_rule` VALUES (32, 'sina 环球新闻', NULL, '', 'http://roll.news.sina.com.cn/news/gjxw/gjmtjj/index_[1-50].shtml', 'http://news.sina.com.cn/w/{数字}[4]-{数字}[1,2]-{数字}[1,2]/{任意}[7,30].shtml', NULL, '', '', NULL, NULL, NULL, '&lt;meta property=&quot;og:title&quot; content=&quot;', '&quot; /&gt;', 'id=&quot;artibody&quot;', '', '&lt;meta property=&quot;og:description&quot; content=&quot;', '&quot; /&gt;', NULL, NULL, '', '', '&lt;meta name=&quot;mediaid&quot; content=&quot;', '&quot; /&gt;', '&lt;meta name=&quot;tags&quot; content=&quot;', '&quot; /&gt;', 1, 5);

-- ----------------------------
-- Table structure for pick_content_task
-- ----------------------------
DROP TABLE IF EXISTS `pick_content_task`;
CREATE TABLE `pick_content_task`  (
  `pickTaskId` bigint(20) NOT NULL AUTO_INCREMENT,
  `taskName` varchar(100)  NOT NULL,
  `taskDesc` varchar(400)  NULL DEFAULT NULL,
  `ruleId` bigint(20) NOT NULL,
  `rssUrl` varchar(200)  NULL DEFAULT NULL,
  `rssRuleBeClass` varchar(120)  NULL DEFAULT NULL,
  `dbRuleBeClass` varchar(120)  NULL DEFAULT NULL,
  `classId` bigint(20) NOT NULL,
  `deleteHref` int(6) NULL DEFAULT NULL,
  `downOutImg` int(6) NULL DEFAULT NULL,
  `guideImgPos` int(11) NULL DEFAULT NULL,
  `pickThreadCount` int(11) NULL DEFAULT NULL,
  `pickInterval` int(11) NULL DEFAULT NULL,
  `pickMaxListPage` int(11) NULL DEFAULT NULL,
  `pickMaxContent` int(11) NULL DEFAULT NULL,
  `censorMode` int(6) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `excuteDT` datetime NULL DEFAULT NULL,
  `selfJobId` bigint(20) NULL DEFAULT NULL,
  `period` int(6) NULL DEFAULT NULL,
  `periodType` int(6) NULL DEFAULT NULL,
  `pickType` int(11) NULL DEFAULT NULL COMMENT '1=html 2=out 3=db',
  PRIMARY KEY (`pickTaskId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pick_content_task
-- ----------------------------
INSERT INTO `pick_content_task` VALUES (7, 'sina 国内 - 国内新闻', '', 15, NULL, NULL, NULL, 10658, NULL, NULL, 3, NULL, NULL, 1, 10, 0, 5, '2016-05-10 18:32:46', NULL, 0, 2, NULL);
INSERT INTO `pick_content_task` VALUES (8, 'sohu 国际 - 国际要闻', '', 16, NULL, NULL, NULL, 10664, NULL, NULL, 3, NULL, NULL, 2, 5, 0, 5, '2016-02-23 09:37:20', NULL, 0, 2, NULL);
INSERT INTO `pick_content_task` VALUES (9, 'sina 国内综合 - 时政聚焦', '', 22, NULL, NULL, NULL, 10659, NULL, NULL, 3, NULL, NULL, 5, 30, 0, 5, '2014-11-21 10:54:02', NULL, 0, 4, NULL);
INSERT INTO `pick_content_task` VALUES (10, 'sohu世态万象 - 世态万象', '', 33, NULL, NULL, NULL, 10661, NULL, NULL, 3, NULL, NULL, 6, 5, 0, 5, '2015-10-27 17:02:37', NULL, 0, 2, NULL);
INSERT INTO `pick_content_task` VALUES (11, 'sine 娱乐 - 娱乐', '', 24, NULL, NULL, NULL, 10653, NULL, NULL, 3, NULL, NULL, 10, 20, 0, 5, '2014-11-21 00:03:19', NULL, 0, 4, NULL);
INSERT INTO `pick_content_task` VALUES (12, 'sian 财经 -财经', '', 34, NULL, NULL, NULL, 10652, NULL, NULL, 3, NULL, NULL, 1, 10, 1, 5, '2016-02-19 16:14:23', NULL, 0, 4, NULL);
INSERT INTO `pick_content_task` VALUES (13, 'sina 体育 - 体育', '', 26, NULL, NULL, NULL, 10650, NULL, NULL, 3, NULL, NULL, 1, 10, 0, 5, '2015-04-16 11:14:56', NULL, 0, 4, NULL);
INSERT INTO `pick_content_task` VALUES (14, 'sina 科技 - 科技', '', 27, NULL, NULL, NULL, 10651, NULL, NULL, 3, NULL, NULL, 1, 20, 0, 5, '2016-03-01 11:00:17', NULL, 0, 4, NULL);
INSERT INTO `pick_content_task` VALUES (15, 'sina 读书 - 读书', '', 28, NULL, NULL, NULL, 10720, NULL, NULL, 3, NULL, NULL, 1, 10, 0, 5, '2016-02-25 22:25:57', NULL, 0, 4, NULL);
INSERT INTO `pick_content_task` VALUES (16, 'sina 健康 - 健康', '', 29, NULL, NULL, NULL, 10721, NULL, NULL, 3, NULL, NULL, 1, 10, 0, 5, '2014-11-20 23:51:02', NULL, 0, 4, NULL);
INSERT INTO `pick_content_task` VALUES (17, 'sina 军事 - 军事', '', 30, NULL, NULL, NULL, 10646, NULL, NULL, 3, NULL, NULL, 1, 5, 0, 5, '2016-02-25 22:25:33', NULL, 0, 4, NULL);
INSERT INTO `pick_content_task` VALUES (18, 'sina 港澳台 - 港澳台新闻', '', 31, NULL, NULL, NULL, 10660, NULL, NULL, 3, NULL, NULL, 1, 20, 0, 5, '2014-11-22 14:18:09', NULL, 0, 4, NULL);

-- ----------------------------
-- Table structure for pick_model_ext
-- ----------------------------
DROP TABLE IF EXISTS `pick_model_ext`;
CREATE TABLE `pick_model_ext`  (
  `eprId` bigint(20) NOT NULL AUTO_INCREMENT,
  `modelId` bigint(20) NULL DEFAULT NULL,
  `eprName` varchar(60)  NULL DEFAULT NULL,
  `eprDesc` varchar(300)  NULL DEFAULT NULL,
  PRIMARY KEY (`eprId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pick_model_ext_field
-- ----------------------------
DROP TABLE IF EXISTS `pick_model_ext_field`;
CREATE TABLE `pick_model_ext_field`  (
  `eprId` bigint(20) NOT NULL,
  `colStart` varchar(500)  NULL DEFAULT NULL,
  `colEnd` varchar(500)  NULL DEFAULT NULL,
  `fieldSign` varchar(60)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pick_web_trace
-- ----------------------------
DROP TABLE IF EXISTS `pick_web_trace`;
CREATE TABLE `pick_web_trace`  (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(500)  NULL DEFAULT NULL,
  `targetUrl` varchar(450)  NULL DEFAULT NULL,
  `classId` bigint(20) NULL DEFAULT NULL,
  `selfRuleId` bigint(20) NULL DEFAULT NULL,
  `eventDT` datetime NULL DEFAULT NULL,
  `pickSucc` int(6) NULL DEFAULT NULL,
  `tags` varchar(200)  NULL DEFAULT NULL,
  `author` varchar(60)  NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for publish_file_track
-- ----------------------------
DROP TABLE IF EXISTS `publish_file_track`;
CREATE TABLE `publish_file_track`  (
  `action` int(11) NOT NULL,
  `fileId` bigint(20) NOT NULL DEFAULT 0,
  `systemHandleTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sysDisposeStatus` int(11) NOT NULL,
  PRIMARY KEY (`fileId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for publish_page_assistant
-- ----------------------------
DROP TABLE IF EXISTS `publish_page_assistant`;
CREATE TABLE `publish_page_assistant`  (
  `queryKey` varchar(400)  NULL DEFAULT NULL,
  `classTemplateUrl` varchar(200)  NOT NULL,
  `classId` bigint(20) NOT NULL,
  `lastPn` int(11) NULL DEFAULT NULL,
  `lastPageStaticUrl` varchar(500)  NULL DEFAULT NULL,
  `ruleId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of publish_page_assistant
-- ----------------------------
INSERT INTO `publish_page_assistant` VALUES ('10644:list.jsp?cid={class-id}', 'list.jsp?cid={class-id}', 10644, 1, 'channel/list/10644.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10658:list.jsp?cid={class-id}', 'list.jsp?cid={class-id}', 10658, 2, 'channel/list/10658_2.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10659:list.jsp?cid={class-id}', 'list.jsp?cid={class-id}', 10659, 1, 'channel/list/10659.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10660:list.jsp?cid={class-id}', 'list.jsp?cid={class-id}', 10660, 1, 'channel/list/10660.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10661:list.jsp?cid={class-id}', 'list.jsp?cid={class-id}', 10661, 10, 'channel/list/10661_10.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10662:list.jsp?cid={class-id}', 'list.jsp?cid={class-id}', 10662, 1, 'channel/list/10662.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10651:list_channel.jsp?channelId={class-id}', 'list_channel.jsp?channelId={class-id}', 10651, 2, 'channel/list/10651_2.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10658:list_news.jsp?cid={class-id}', 'list_news.jsp?cid={class-id}', 10658, 5, 'channel/list/10658_5.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10724:channel.jsp', 'channel.jsp', 10724, 1, 'channel/list/10724.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10659:list_news.jsp?cid={class-id}', 'list_news.jsp?cid={class-id}', 10659, 1, 'channel/list/10659.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10660:list_news.jsp?cid={class-id}', 'list_news.jsp?cid={class-id}', 10660, 1, 'channel/list/10660.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10661:list_news.jsp?cid={class-id}', 'list_news.jsp?cid={class-id}', 10661, 1, 'channel/list/10661.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10662:list_news.jsp?cid={class-id}', 'list_news.jsp?cid={class-id}', 10662, 1, 'channel/list/10662.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10664:list_news.jsp?cid={class-id}', 'list_news.jsp?cid={class-id}', 10664, 1, 'channel/list/10664.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10650:list_channel.jsp?channelId={class-id}', 'list_channel.jsp?channelId={class-id}', 10650, 1, 'channel/list/10650.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10651:list_channel.thtml?channelId={class-id}', 'list_channel.thtml?channelId={class-id}', 10651, 1, 'channel/list/10651.html', 23);
INSERT INTO `publish_page_assistant` VALUES ('10652:list_channel.jsp?channelId={class-id}', 'list_channel.jsp?channelId={class-id}', 10652, 1, 'channel/list/10652.html', 23);

-- ----------------------------
-- Table structure for publish_rule
-- ----------------------------
DROP TABLE IF EXISTS `publish_rule`;
CREATE TABLE `publish_rule`  (
  `ruleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ruleName` varchar(200)  NOT NULL,
  `type` int(11) NOT NULL,
  `pagePathParam` varchar(400)  NULL DEFAULT NULL,
  `pagePathRule` varchar(500)  NULL DEFAULT NULL,
  `savePathParam` varchar(500)  NULL DEFAULT NULL,
  `savePathRule` varchar(600)  NULL DEFAULT NULL,
  `pageSize` int(11) NULL DEFAULT NULL,
  `pageFileNameRule` varchar(200)  NULL DEFAULT NULL,
  `pageFileNameParam` varchar(200)  NULL DEFAULT NULL,
  `fileNameParam` varchar(500)  NULL DEFAULT NULL,
  `fileNameRule` varchar(500)  NULL DEFAULT NULL,
  `suffixRule` varchar(20)  NOT NULL,
  `needPage` int(11) NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`ruleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of publish_rule
-- ----------------------------
INSERT INTO `publish_rule` VALUES (23, '/channel/list/{栏目ID}.html', 2, '', 'channel/list', '', 'channel/list', NULL, '{class-id}_{pn}', '{class-id},{pn}', '{class-id}', '{class-id}', '.html', NULL, 1);
INSERT INTO `publish_rule` VALUES (24, '/spec/{栏目ID}/{专题子分类ID}.html', 3, '{class-id}', 'spec/{class-id}', '{class-id}', 'spec/{class-id}', NULL, '{type-id}_{pn}', '{type-id},{pn}', '{type-id}', '{type-id}', '.html', NULL, 1);
INSERT INTO `publish_rule` VALUES (25, '/content/{年}/{栏目代码}/{内容ID}.html', 4, '{year},{class-flag}', 'content/{year}/{class-flag}', '{year},{class-flag}', 'content/{year}/{class-flag}', NULL, '{content-id}_{pn}', '{content-id},{pn}', '{content-id}', '{content-id}', '.html', NULL, 1);
INSERT INTO `publish_rule` VALUES (34, '/spec/{栏目ID}/index.html', 1, '{class-id}', 'spec/{class-id}', '{class-id}', 'spec/{class-id}', NULL, 'index_{pn}', '{pn}', '', 'index', '.html', NULL, 1);
INSERT INTO `publish_rule` VALUES (36, '/channel/{栏目ID}/index.html', 1, '{class-id}', 'channel/{class-id}', '{class-id}', 'channel/{class-id}', NULL, 'index_{pn}', '{pn}', '', 'index', '.html', NULL, 1);
INSERT INTO `publish_rule` VALUES (38, '/single/{栏目ID}/{内容ID}.html', 4, '{class-id}', 'single/{class-id}', '{class-id}', 'single/{class-id}', NULL, '{content-id}_{pn}', '{content-id},{pn}', '{content-id}', '{content-id}', '.html', NULL, 3);
INSERT INTO `publish_rule` VALUES (39, '/content2/{栏目代码}/{内容ID}.shtml', 4, '{class-flag}', 'content2/{class-flag}', '{class-flag}', 'content2/{class-flag}', NULL, '{content-id}_{pn}', '{content-id},{pn}', '{content-id}', '{content-id}', '.shtml', NULL, 1);
INSERT INTO `publish_rule` VALUES (40, '/content/{年}/{内容ID}.html', 4, '{year}', 'content/{year}', '{year}', 'content/{year}', NULL, '{content-id}_{pn}', '{content-id},{pn}', '{content-id}', '{content-id}', '.html', NULL, 5);

-- ----------------------------
-- Table structure for publish_temporary_content
-- ----------------------------
DROP TABLE IF EXISTS `publish_temporary_content`;
CREATE TABLE `publish_temporary_content`  (
  `contentId` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role_range_fl_org_relate_res
-- ----------------------------
DROP TABLE IF EXISTS `role_range_fl_org_relate_res`;
CREATE TABLE `role_range_fl_org_relate_res`  (
  `resId` bigint(20) NOT NULL,
  `orgId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role_range_fl_org_relate_site
-- ----------------------------
DROP TABLE IF EXISTS `role_range_fl_org_relate_site`;
CREATE TABLE `role_range_fl_org_relate_site`  (
  `orgId` bigint(20) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_range_fl_org_relate_site
-- ----------------------------
INSERT INTO `role_range_fl_org_relate_site` VALUES (2, 5);

-- ----------------------------
-- Table structure for role_range_org_relate_res_acc
-- ----------------------------
DROP TABLE IF EXISTS `role_range_org_relate_res_acc`;
CREATE TABLE `role_range_org_relate_res_acc`  (
  `orgId` bigint(20) NOT NULL,
  `resId` bigint(20) NOT NULL,
  `accId` bigint(20) NOT NULL,
  `dataSecTypeId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  INDEX `ots`(`orgId`, `accId`, `siteId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_range_org_relate_res_acc
-- ----------------------------
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 255, 10777, 3, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10686, 3, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 76, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 256, 76, 4, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 255, 10778, 3, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10762, 3, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10779, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10779, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10780, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10780, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10781, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10663, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10781, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10663, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10782, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10663, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10782, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10663, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 77, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 256, 77, 4, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 255, 10779, 3, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10762, 3, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10780, 1, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10780, 2, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10781, 1, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10781, 2, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10782, 1, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10780, 1, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10782, 2, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10780, 2, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10783, 1, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10780, 1, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10783, 2, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10780, 2, 8);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10784, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10784, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10786, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10786, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10787, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10787, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10788, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10788, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10789, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10789, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, -1, 10644, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 151, 10785, 1, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (1, 202, 10785, 2, 5);
INSERT INTO `role_range_org_relate_res_acc` VALUES (2, 311, 9, 5, 5);

-- ----------------------------
-- Table structure for role_range_org_relate_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_range_org_relate_resource`;
CREATE TABLE `role_range_org_relate_resource`  (
  `orgId` bigint(20) NOT NULL,
  `resId` bigint(20) NOT NULL,
  INDEX `orgId`(`orgId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role_range_org_relate_site
-- ----------------------------
DROP TABLE IF EXISTS `role_range_org_relate_site`;
CREATE TABLE `role_range_org_relate_site`  (
  `orgId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `parOrgId` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_range_org_relate_site
-- ----------------------------
INSERT INTO `role_range_org_relate_site` VALUES (2, 5, NULL);
INSERT INTO `role_range_org_relate_site` VALUES (1, 5, NULL);

-- ----------------------------
-- Table structure for role_range_root_org_relate_site
-- ----------------------------
DROP TABLE IF EXISTS `role_range_root_org_relate_site`;
CREATE TABLE `role_range_root_org_relate_site`  (
  `orgId` bigint(20) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role_relate_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_relate_resource`;
CREATE TABLE `role_relate_resource`  (
  `roleId` bigint(20) NOT NULL,
  `secResId` bigint(20) NOT NULL,
  INDEX `sec`(`secResId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_relate_resource
-- ----------------------------
INSERT INTO `role_relate_resource` VALUES (83, 102);
INSERT INTO `role_relate_resource` VALUES (83, 103);
INSERT INTO `role_relate_resource` VALUES (83, 110);
INSERT INTO `role_relate_resource` VALUES (83, 111);
INSERT INTO `role_relate_resource` VALUES (83, 108);
INSERT INTO `role_relate_resource` VALUES (83, 109);
INSERT INTO `role_relate_resource` VALUES (83, 106);
INSERT INTO `role_relate_resource` VALUES (83, 107);
INSERT INTO `role_relate_resource` VALUES (83, 104);
INSERT INTO `role_relate_resource` VALUES (83, 105);
INSERT INTO `role_relate_resource` VALUES (83, 119);
INSERT INTO `role_relate_resource` VALUES (83, 116);
INSERT INTO `role_relate_resource` VALUES (83, 114);
INSERT INTO `role_relate_resource` VALUES (83, 113);
INSERT INTO `role_relate_resource` VALUES (83, 112);
INSERT INTO `role_relate_resource` VALUES (83, 127);
INSERT INTO `role_relate_resource` VALUES (83, 125);
INSERT INTO `role_relate_resource` VALUES (83, 124);
INSERT INTO `role_relate_resource` VALUES (83, 122);
INSERT INTO `role_relate_resource` VALUES (83, 121);
INSERT INTO `role_relate_resource` VALUES (83, 120);
INSERT INTO `role_relate_resource` VALUES (83, 136);
INSERT INTO `role_relate_resource` VALUES (83, 139);
INSERT INTO `role_relate_resource` VALUES (83, 138);
INSERT INTO `role_relate_resource` VALUES (83, 135);
INSERT INTO `role_relate_resource` VALUES (83, 134);
INSERT INTO `role_relate_resource` VALUES (83, 157);
INSERT INTO `role_relate_resource` VALUES (83, 158);
INSERT INTO `role_relate_resource` VALUES (83, 159);
INSERT INTO `role_relate_resource` VALUES (83, 148);
INSERT INTO `role_relate_resource` VALUES (83, 171);
INSERT INTO `role_relate_resource` VALUES (83, 175);
INSERT INTO `role_relate_resource` VALUES (83, 173);
INSERT INTO `role_relate_resource` VALUES (83, 172);
INSERT INTO `role_relate_resource` VALUES (83, 163);
INSERT INTO `role_relate_resource` VALUES (83, 162);
INSERT INTO `role_relate_resource` VALUES (83, 160);
INSERT INTO `role_relate_resource` VALUES (83, 166);
INSERT INTO `role_relate_resource` VALUES (83, 165);
INSERT INTO `role_relate_resource` VALUES (83, 164);
INSERT INTO `role_relate_resource` VALUES (83, 186);
INSERT INTO `role_relate_resource` VALUES (83, 185);
INSERT INTO `role_relate_resource` VALUES (83, 178);
INSERT INTO `role_relate_resource` VALUES (83, 179);
INSERT INTO `role_relate_resource` VALUES (83, 176);
INSERT INTO `role_relate_resource` VALUES (83, 177);
INSERT INTO `role_relate_resource` VALUES (83, 182);
INSERT INTO `role_relate_resource` VALUES (83, 183);
INSERT INTO `role_relate_resource` VALUES (83, 180);
INSERT INTO `role_relate_resource` VALUES (83, 181);
INSERT INTO `role_relate_resource` VALUES (83, 204);
INSERT INTO `role_relate_resource` VALUES (83, 207);
INSERT INTO `role_relate_resource` VALUES (83, 220);
INSERT INTO `role_relate_resource` VALUES (83, 223);
INSERT INTO `role_relate_resource` VALUES (83, 216);
INSERT INTO `role_relate_resource` VALUES (83, 219);
INSERT INTO `role_relate_resource` VALUES (83, 212);
INSERT INTO `role_relate_resource` VALUES (83, 213);
INSERT INTO `role_relate_resource` VALUES (83, 214);
INSERT INTO `role_relate_resource` VALUES (83, 215);
INSERT INTO `role_relate_resource` VALUES (83, 208);
INSERT INTO `role_relate_resource` VALUES (83, 209);
INSERT INTO `role_relate_resource` VALUES (83, 210);
INSERT INTO `role_relate_resource` VALUES (83, 238);
INSERT INTO `role_relate_resource` VALUES (83, 236);
INSERT INTO `role_relate_resource` VALUES (83, 235);
INSERT INTO `role_relate_resource` VALUES (83, 234);
INSERT INTO `role_relate_resource` VALUES (83, 233);
INSERT INTO `role_relate_resource` VALUES (83, 231);
INSERT INTO `role_relate_resource` VALUES (83, 230);
INSERT INTO `role_relate_resource` VALUES (83, 229);
INSERT INTO `role_relate_resource` VALUES (83, 227);
INSERT INTO `role_relate_resource` VALUES (83, 226);
INSERT INTO `role_relate_resource` VALUES (83, 225);
INSERT INTO `role_relate_resource` VALUES (83, 224);
INSERT INTO `role_relate_resource` VALUES (83, 248);
INSERT INTO `role_relate_resource` VALUES (83, 249);
INSERT INTO `role_relate_resource` VALUES (83, 246);
INSERT INTO `role_relate_resource` VALUES (83, 247);
INSERT INTO `role_relate_resource` VALUES (83, 244);
INSERT INTO `role_relate_resource` VALUES (83, 242);
INSERT INTO `role_relate_resource` VALUES (83, 243);
INSERT INTO `role_relate_resource` VALUES (83, 241);
INSERT INTO `role_relate_resource` VALUES (83, 274);
INSERT INTO `role_relate_resource` VALUES (83, 273);
INSERT INTO `role_relate_resource` VALUES (83, 272);
INSERT INTO `role_relate_resource` VALUES (83, 279);
INSERT INTO `role_relate_resource` VALUES (83, 278);
INSERT INTO `role_relate_resource` VALUES (83, 277);
INSERT INTO `role_relate_resource` VALUES (83, 283);
INSERT INTO `role_relate_resource` VALUES (83, 282);
INSERT INTO `role_relate_resource` VALUES (83, 280);
INSERT INTO `role_relate_resource` VALUES (83, 287);
INSERT INTO `role_relate_resource` VALUES (83, 286);
INSERT INTO `role_relate_resource` VALUES (83, 285);
INSERT INTO `role_relate_resource` VALUES (83, 284);
INSERT INTO `role_relate_resource` VALUES (83, 262);
INSERT INTO `role_relate_resource` VALUES (83, 266);
INSERT INTO `role_relate_resource` VALUES (83, 264);
INSERT INTO `role_relate_resource` VALUES (83, 270);
INSERT INTO `role_relate_resource` VALUES (83, 271);
INSERT INTO `role_relate_resource` VALUES (83, 305);
INSERT INTO `role_relate_resource` VALUES (83, 304);
INSERT INTO `role_relate_resource` VALUES (83, 307);
INSERT INTO `role_relate_resource` VALUES (83, 306);
INSERT INTO `role_relate_resource` VALUES (83, 309);
INSERT INTO `role_relate_resource` VALUES (83, 308);
INSERT INTO `role_relate_resource` VALUES (83, 310);
INSERT INTO `role_relate_resource` VALUES (83, 317);
INSERT INTO `role_relate_resource` VALUES (83, 316);
INSERT INTO `role_relate_resource` VALUES (83, 319);
INSERT INTO `role_relate_resource` VALUES (83, 318);
INSERT INTO `role_relate_resource` VALUES (83, 288);
INSERT INTO `role_relate_resource` VALUES (83, 289);
INSERT INTO `role_relate_resource` VALUES (83, 290);
INSERT INTO `role_relate_resource` VALUES (83, 292);
INSERT INTO `role_relate_resource` VALUES (83, 294);
INSERT INTO `role_relate_resource` VALUES (83, 295);
INSERT INTO `role_relate_resource` VALUES (83, 296);
INSERT INTO `role_relate_resource` VALUES (83, 297);
INSERT INTO `role_relate_resource` VALUES (83, 298);
INSERT INTO `role_relate_resource` VALUES (83, 299);
INSERT INTO `role_relate_resource` VALUES (83, 300);
INSERT INTO `role_relate_resource` VALUES (83, 301);
INSERT INTO `role_relate_resource` VALUES (83, 302);
INSERT INTO `role_relate_resource` VALUES (83, 303);
INSERT INTO `role_relate_resource` VALUES (83, 343);
INSERT INTO `role_relate_resource` VALUES (83, 342);
INSERT INTO `role_relate_resource` VALUES (83, 341);
INSERT INTO `role_relate_resource` VALUES (83, 340);
INSERT INTO `role_relate_resource` VALUES (83, 339);
INSERT INTO `role_relate_resource` VALUES (83, 338);
INSERT INTO `role_relate_resource` VALUES (83, 337);
INSERT INTO `role_relate_resource` VALUES (83, 336);
INSERT INTO `role_relate_resource` VALUES (83, 351);
INSERT INTO `role_relate_resource` VALUES (83, 350);
INSERT INTO `role_relate_resource` VALUES (83, 349);
INSERT INTO `role_relate_resource` VALUES (83, 348);
INSERT INTO `role_relate_resource` VALUES (83, 347);
INSERT INTO `role_relate_resource` VALUES (83, 346);
INSERT INTO `role_relate_resource` VALUES (83, 345);
INSERT INTO `role_relate_resource` VALUES (83, 344);
INSERT INTO `role_relate_resource` VALUES (83, 326);
INSERT INTO `role_relate_resource` VALUES (83, 327);
INSERT INTO `role_relate_resource` VALUES (83, 324);
INSERT INTO `role_relate_resource` VALUES (83, 325);
INSERT INTO `role_relate_resource` VALUES (83, 322);
INSERT INTO `role_relate_resource` VALUES (83, 323);
INSERT INTO `role_relate_resource` VALUES (83, 320);
INSERT INTO `role_relate_resource` VALUES (83, 321);
INSERT INTO `role_relate_resource` VALUES (83, 334);
INSERT INTO `role_relate_resource` VALUES (83, 335);
INSERT INTO `role_relate_resource` VALUES (83, 332);
INSERT INTO `role_relate_resource` VALUES (83, 333);
INSERT INTO `role_relate_resource` VALUES (83, 330);
INSERT INTO `role_relate_resource` VALUES (83, 331);
INSERT INTO `role_relate_resource` VALUES (83, 328);
INSERT INTO `role_relate_resource` VALUES (83, 329);
INSERT INTO `role_relate_resource` VALUES (83, 373);
INSERT INTO `role_relate_resource` VALUES (83, 372);
INSERT INTO `role_relate_resource` VALUES (83, 375);
INSERT INTO `role_relate_resource` VALUES (83, 374);
INSERT INTO `role_relate_resource` VALUES (83, 369);
INSERT INTO `role_relate_resource` VALUES (83, 368);
INSERT INTO `role_relate_resource` VALUES (83, 371);
INSERT INTO `role_relate_resource` VALUES (83, 370);
INSERT INTO `role_relate_resource` VALUES (83, 381);
INSERT INTO `role_relate_resource` VALUES (83, 380);
INSERT INTO `role_relate_resource` VALUES (83, 383);
INSERT INTO `role_relate_resource` VALUES (83, 382);
INSERT INTO `role_relate_resource` VALUES (83, 377);
INSERT INTO `role_relate_resource` VALUES (83, 376);
INSERT INTO `role_relate_resource` VALUES (83, 379);
INSERT INTO `role_relate_resource` VALUES (83, 356);
INSERT INTO `role_relate_resource` VALUES (83, 357);
INSERT INTO `role_relate_resource` VALUES (83, 358);
INSERT INTO `role_relate_resource` VALUES (83, 359);
INSERT INTO `role_relate_resource` VALUES (83, 352);
INSERT INTO `role_relate_resource` VALUES (83, 353);
INSERT INTO `role_relate_resource` VALUES (83, 354);
INSERT INTO `role_relate_resource` VALUES (83, 355);
INSERT INTO `role_relate_resource` VALUES (83, 364);
INSERT INTO `role_relate_resource` VALUES (83, 365);
INSERT INTO `role_relate_resource` VALUES (83, 366);
INSERT INTO `role_relate_resource` VALUES (83, 367);
INSERT INTO `role_relate_resource` VALUES (83, 360);
INSERT INTO `role_relate_resource` VALUES (83, 361);
INSERT INTO `role_relate_resource` VALUES (83, 362);
INSERT INTO `role_relate_resource` VALUES (83, 363);
INSERT INTO `role_relate_resource` VALUES (83, 410);
INSERT INTO `role_relate_resource` VALUES (83, 411);
INSERT INTO `role_relate_resource` VALUES (83, 408);
INSERT INTO `role_relate_resource` VALUES (83, 409);
INSERT INTO `role_relate_resource` VALUES (83, 414);
INSERT INTO `role_relate_resource` VALUES (83, 415);
INSERT INTO `role_relate_resource` VALUES (83, 412);
INSERT INTO `role_relate_resource` VALUES (83, 413);
INSERT INTO `role_relate_resource` VALUES (83, 402);
INSERT INTO `role_relate_resource` VALUES (83, 403);
INSERT INTO `role_relate_resource` VALUES (83, 400);
INSERT INTO `role_relate_resource` VALUES (83, 401);
INSERT INTO `role_relate_resource` VALUES (83, 406);
INSERT INTO `role_relate_resource` VALUES (83, 407);
INSERT INTO `role_relate_resource` VALUES (83, 404);
INSERT INTO `role_relate_resource` VALUES (83, 405);
INSERT INTO `role_relate_resource` VALUES (83, 395);
INSERT INTO `role_relate_resource` VALUES (83, 394);
INSERT INTO `role_relate_resource` VALUES (83, 393);
INSERT INTO `role_relate_resource` VALUES (83, 392);
INSERT INTO `role_relate_resource` VALUES (83, 399);
INSERT INTO `role_relate_resource` VALUES (83, 398);
INSERT INTO `role_relate_resource` VALUES (83, 397);
INSERT INTO `role_relate_resource` VALUES (83, 396);
INSERT INTO `role_relate_resource` VALUES (83, 387);
INSERT INTO `role_relate_resource` VALUES (83, 386);
INSERT INTO `role_relate_resource` VALUES (83, 385);
INSERT INTO `role_relate_resource` VALUES (83, 384);
INSERT INTO `role_relate_resource` VALUES (83, 391);
INSERT INTO `role_relate_resource` VALUES (83, 390);
INSERT INTO `role_relate_resource` VALUES (83, 389);
INSERT INTO `role_relate_resource` VALUES (83, 388);
INSERT INTO `role_relate_resource` VALUES (83, 440);
INSERT INTO `role_relate_resource` VALUES (83, 441);
INSERT INTO `role_relate_resource` VALUES (83, 442);
INSERT INTO `role_relate_resource` VALUES (83, 443);
INSERT INTO `role_relate_resource` VALUES (83, 444);
INSERT INTO `role_relate_resource` VALUES (83, 445);
INSERT INTO `role_relate_resource` VALUES (83, 446);
INSERT INTO `role_relate_resource` VALUES (83, 447);
INSERT INTO `role_relate_resource` VALUES (83, 432);
INSERT INTO `role_relate_resource` VALUES (83, 433);
INSERT INTO `role_relate_resource` VALUES (83, 434);
INSERT INTO `role_relate_resource` VALUES (83, 435);
INSERT INTO `role_relate_resource` VALUES (83, 436);
INSERT INTO `role_relate_resource` VALUES (83, 437);
INSERT INTO `role_relate_resource` VALUES (83, 438);
INSERT INTO `role_relate_resource` VALUES (83, 439);
INSERT INTO `role_relate_resource` VALUES (83, 425);
INSERT INTO `role_relate_resource` VALUES (83, 424);
INSERT INTO `role_relate_resource` VALUES (83, 427);
INSERT INTO `role_relate_resource` VALUES (83, 426);
INSERT INTO `role_relate_resource` VALUES (83, 429);
INSERT INTO `role_relate_resource` VALUES (83, 428);
INSERT INTO `role_relate_resource` VALUES (83, 431);
INSERT INTO `role_relate_resource` VALUES (83, 430);
INSERT INTO `role_relate_resource` VALUES (83, 417);
INSERT INTO `role_relate_resource` VALUES (83, 416);
INSERT INTO `role_relate_resource` VALUES (83, 419);
INSERT INTO `role_relate_resource` VALUES (83, 418);
INSERT INTO `role_relate_resource` VALUES (83, 421);
INSERT INTO `role_relate_resource` VALUES (83, 420);
INSERT INTO `role_relate_resource` VALUES (83, 423);
INSERT INTO `role_relate_resource` VALUES (83, 422);
INSERT INTO `role_relate_resource` VALUES (83, 478);
INSERT INTO `role_relate_resource` VALUES (83, 479);
INSERT INTO `role_relate_resource` VALUES (83, 476);
INSERT INTO `role_relate_resource` VALUES (83, 477);
INSERT INTO `role_relate_resource` VALUES (83, 474);
INSERT INTO `role_relate_resource` VALUES (83, 475);
INSERT INTO `role_relate_resource` VALUES (83, 472);
INSERT INTO `role_relate_resource` VALUES (83, 473);
INSERT INTO `role_relate_resource` VALUES (83, 470);
INSERT INTO `role_relate_resource` VALUES (83, 471);
INSERT INTO `role_relate_resource` VALUES (83, 468);
INSERT INTO `role_relate_resource` VALUES (83, 469);
INSERT INTO `role_relate_resource` VALUES (83, 466);
INSERT INTO `role_relate_resource` VALUES (83, 467);
INSERT INTO `role_relate_resource` VALUES (83, 464);
INSERT INTO `role_relate_resource` VALUES (83, 465);
INSERT INTO `role_relate_resource` VALUES (83, 463);
INSERT INTO `role_relate_resource` VALUES (83, 462);
INSERT INTO `role_relate_resource` VALUES (83, 461);
INSERT INTO `role_relate_resource` VALUES (83, 460);
INSERT INTO `role_relate_resource` VALUES (83, 459);
INSERT INTO `role_relate_resource` VALUES (83, 458);
INSERT INTO `role_relate_resource` VALUES (83, 457);
INSERT INTO `role_relate_resource` VALUES (83, 456);
INSERT INTO `role_relate_resource` VALUES (83, 455);
INSERT INTO `role_relate_resource` VALUES (83, 454);
INSERT INTO `role_relate_resource` VALUES (83, 453);
INSERT INTO `role_relate_resource` VALUES (83, 452);
INSERT INTO `role_relate_resource` VALUES (83, 451);
INSERT INTO `role_relate_resource` VALUES (83, 450);
INSERT INTO `role_relate_resource` VALUES (83, 449);
INSERT INTO `role_relate_resource` VALUES (83, 448);
INSERT INTO `role_relate_resource` VALUES (83, 504);
INSERT INTO `role_relate_resource` VALUES (83, 505);
INSERT INTO `role_relate_resource` VALUES (83, 502);
INSERT INTO `role_relate_resource` VALUES (83, 503);
INSERT INTO `role_relate_resource` VALUES (83, 496);
INSERT INTO `role_relate_resource` VALUES (83, 497);
INSERT INTO `role_relate_resource` VALUES (83, 493);
INSERT INTO `role_relate_resource` VALUES (83, 492);
INSERT INTO `role_relate_resource` VALUES (83, 495);
INSERT INTO `role_relate_resource` VALUES (83, 494);
INSERT INTO `role_relate_resource` VALUES (83, 489);
INSERT INTO `role_relate_resource` VALUES (83, 488);
INSERT INTO `role_relate_resource` VALUES (83, 491);
INSERT INTO `role_relate_resource` VALUES (83, 490);
INSERT INTO `role_relate_resource` VALUES (83, 485);
INSERT INTO `role_relate_resource` VALUES (83, 484);
INSERT INTO `role_relate_resource` VALUES (83, 487);
INSERT INTO `role_relate_resource` VALUES (83, 486);
INSERT INTO `role_relate_resource` VALUES (83, 481);
INSERT INTO `role_relate_resource` VALUES (83, 480);
INSERT INTO `role_relate_resource` VALUES (83, 483);
INSERT INTO `role_relate_resource` VALUES (83, 482);
INSERT INTO `role_relate_resource` VALUES (83, 550);
INSERT INTO `role_relate_resource` VALUES (83, 551);
INSERT INTO `role_relate_resource` VALUES (83, 544);
INSERT INTO `role_relate_resource` VALUES (83, 556);
INSERT INTO `role_relate_resource` VALUES (83, 557);
INSERT INTO `role_relate_resource` VALUES (83, 554);
INSERT INTO `role_relate_resource` VALUES (83, 555);
INSERT INTO `role_relate_resource` VALUES (83, 552);
INSERT INTO `role_relate_resource` VALUES (83, 553);
INSERT INTO `role_relate_resource` VALUES (83, 567);
INSERT INTO `role_relate_resource` VALUES (83, 566);
INSERT INTO `role_relate_resource` VALUES (83, 565);
INSERT INTO `role_relate_resource` VALUES (83, 564);
INSERT INTO `role_relate_resource` VALUES (83, 563);
INSERT INTO `role_relate_resource` VALUES (83, 562);
INSERT INTO `role_relate_resource` VALUES (83, 561);
INSERT INTO `role_relate_resource` VALUES (83, 575);
INSERT INTO `role_relate_resource` VALUES (83, 574);
INSERT INTO `role_relate_resource` VALUES (83, 573);
INSERT INTO `role_relate_resource` VALUES (83, 572);
INSERT INTO `role_relate_resource` VALUES (83, 571);
INSERT INTO `role_relate_resource` VALUES (83, 570);
INSERT INTO `role_relate_resource` VALUES (83, 569);
INSERT INTO `role_relate_resource` VALUES (83, 568);
INSERT INTO `role_relate_resource` VALUES (83, 519);
INSERT INTO `role_relate_resource` VALUES (83, 520);
INSERT INTO `role_relate_resource` VALUES (83, 521);
INSERT INTO `role_relate_resource` VALUES (83, 522);
INSERT INTO `role_relate_resource` VALUES (83, 523);
INSERT INTO `role_relate_resource` VALUES (83, 528);
INSERT INTO `role_relate_resource` VALUES (83, 541);
INSERT INTO `role_relate_resource` VALUES (83, 540);
INSERT INTO `role_relate_resource` VALUES (83, 543);
INSERT INTO `role_relate_resource` VALUES (83, 542);
INSERT INTO `role_relate_resource` VALUES (83, 539);
INSERT INTO `role_relate_resource` VALUES (83, 538);
INSERT INTO `role_relate_resource` VALUES (83, 610);
INSERT INTO `role_relate_resource` VALUES (83, 611);
INSERT INTO `role_relate_resource` VALUES (83, 608);
INSERT INTO `role_relate_resource` VALUES (83, 609);
INSERT INTO `role_relate_resource` VALUES (83, 614);
INSERT INTO `role_relate_resource` VALUES (83, 615);
INSERT INTO `role_relate_resource` VALUES (83, 612);
INSERT INTO `role_relate_resource` VALUES (83, 613);
INSERT INTO `role_relate_resource` VALUES (83, 618);
INSERT INTO `role_relate_resource` VALUES (83, 619);
INSERT INTO `role_relate_resource` VALUES (83, 616);
INSERT INTO `role_relate_resource` VALUES (83, 617);
INSERT INTO `role_relate_resource` VALUES (83, 622);
INSERT INTO `role_relate_resource` VALUES (83, 623);
INSERT INTO `role_relate_resource` VALUES (83, 620);
INSERT INTO `role_relate_resource` VALUES (83, 621);
INSERT INTO `role_relate_resource` VALUES (83, 627);
INSERT INTO `role_relate_resource` VALUES (83, 626);
INSERT INTO `role_relate_resource` VALUES (83, 625);
INSERT INTO `role_relate_resource` VALUES (83, 624);
INSERT INTO `role_relate_resource` VALUES (83, 631);
INSERT INTO `role_relate_resource` VALUES (83, 630);
INSERT INTO `role_relate_resource` VALUES (83, 629);
INSERT INTO `role_relate_resource` VALUES (83, 628);
INSERT INTO `role_relate_resource` VALUES (83, 635);
INSERT INTO `role_relate_resource` VALUES (83, 634);
INSERT INTO `role_relate_resource` VALUES (83, 633);
INSERT INTO `role_relate_resource` VALUES (83, 632);
INSERT INTO `role_relate_resource` VALUES (83, 639);
INSERT INTO `role_relate_resource` VALUES (83, 638);
INSERT INTO `role_relate_resource` VALUES (83, 637);
INSERT INTO `role_relate_resource` VALUES (83, 636);
INSERT INTO `role_relate_resource` VALUES (83, 576);
INSERT INTO `role_relate_resource` VALUES (83, 577);
INSERT INTO `role_relate_resource` VALUES (83, 578);
INSERT INTO `role_relate_resource` VALUES (83, 579);
INSERT INTO `role_relate_resource` VALUES (83, 580);
INSERT INTO `role_relate_resource` VALUES (83, 581);
INSERT INTO `role_relate_resource` VALUES (83, 582);
INSERT INTO `role_relate_resource` VALUES (83, 583);
INSERT INTO `role_relate_resource` VALUES (83, 584);
INSERT INTO `role_relate_resource` VALUES (83, 585);
INSERT INTO `role_relate_resource` VALUES (83, 586);
INSERT INTO `role_relate_resource` VALUES (83, 587);
INSERT INTO `role_relate_resource` VALUES (83, 588);
INSERT INTO `role_relate_resource` VALUES (83, 589);
INSERT INTO `role_relate_resource` VALUES (83, 590);
INSERT INTO `role_relate_resource` VALUES (83, 591);
INSERT INTO `role_relate_resource` VALUES (83, 593);
INSERT INTO `role_relate_resource` VALUES (83, 592);
INSERT INTO `role_relate_resource` VALUES (83, 595);
INSERT INTO `role_relate_resource` VALUES (83, 594);
INSERT INTO `role_relate_resource` VALUES (83, 596);
INSERT INTO `role_relate_resource` VALUES (83, 599);
INSERT INTO `role_relate_resource` VALUES (83, 598);
INSERT INTO `role_relate_resource` VALUES (83, 601);
INSERT INTO `role_relate_resource` VALUES (83, 600);
INSERT INTO `role_relate_resource` VALUES (83, 603);
INSERT INTO `role_relate_resource` VALUES (83, 602);
INSERT INTO `role_relate_resource` VALUES (83, 605);
INSERT INTO `role_relate_resource` VALUES (83, 604);
INSERT INTO `role_relate_resource` VALUES (83, 607);
INSERT INTO `role_relate_resource` VALUES (83, 606);
INSERT INTO `role_relate_resource` VALUES (83, 653);
INSERT INTO `role_relate_resource` VALUES (83, 652);
INSERT INTO `role_relate_resource` VALUES (83, 655);
INSERT INTO `role_relate_resource` VALUES (83, 654);
INSERT INTO `role_relate_resource` VALUES (83, 649);
INSERT INTO `role_relate_resource` VALUES (83, 648);
INSERT INTO `role_relate_resource` VALUES (83, 651);
INSERT INTO `role_relate_resource` VALUES (83, 650);
INSERT INTO `role_relate_resource` VALUES (83, 645);
INSERT INTO `role_relate_resource` VALUES (83, 644);
INSERT INTO `role_relate_resource` VALUES (83, 647);
INSERT INTO `role_relate_resource` VALUES (83, 646);
INSERT INTO `role_relate_resource` VALUES (83, 641);
INSERT INTO `role_relate_resource` VALUES (83, 640);
INSERT INTO `role_relate_resource` VALUES (83, 643);
INSERT INTO `role_relate_resource` VALUES (83, 642);
INSERT INTO `role_relate_resource` VALUES (83, 660);
INSERT INTO `role_relate_resource` VALUES (83, 656);
INSERT INTO `role_relate_resource` VALUES (83, 657);
INSERT INTO `role_relate_resource` VALUES (83, 658);

-- ----------------------------
-- Table structure for role_relate_resource_acc
-- ----------------------------
DROP TABLE IF EXISTS `role_relate_resource_acc`;
CREATE TABLE `role_relate_resource_acc`  (
  `roleId` bigint(20) NOT NULL,
  `secResId` bigint(20) NOT NULL,
  `accId` bigint(20) NOT NULL,
  `dataSecTypeId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  INDEX `sec_acc`(`secResId`, `accId`) USING BTREE,
  INDEX `role_res`(`roleId`, `secResId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_relate_resource_acc
-- ----------------------------
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10780, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10780, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10780, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10780, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10780, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10780, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10780, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10780, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10780, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10781, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10781, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10781, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10781, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10781, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10781, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10781, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10781, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10781, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10782, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10782, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10782, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10782, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10782, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10782, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10782, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10782, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10782, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10783, 1, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10783, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10783, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10783, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10783, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10783, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10783, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10783, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10783, 2, 8);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 49, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 52, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 53, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 54, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 58, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 59, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 60, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 61, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 62, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 63, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 64, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 66, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 67, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 68, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 257, 69, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 49, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 52, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 53, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 54, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 58, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 59, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 60, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 61, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 62, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 63, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 64, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 66, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 67, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 68, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 512, 69, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 49, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 52, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 53, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 54, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 58, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 59, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 60, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 61, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 62, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 63, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 64, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 66, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 67, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 68, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 513, 69, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 49, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 52, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 53, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 54, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 58, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 59, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 60, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 61, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 62, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 63, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 64, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 66, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 67, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 68, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 514, 69, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 49, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 52, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 53, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 54, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 58, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 59, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 60, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 61, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 62, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 63, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 64, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 66, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 67, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 68, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 515, 69, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 49, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 52, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 53, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 54, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 58, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 59, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 60, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 61, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 62, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 63, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 64, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 66, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 67, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 68, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 516, 69, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 49, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 52, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 53, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 54, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 58, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 59, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 60, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 61, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 62, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 63, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 64, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 66, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 67, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 68, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 256, 69, 4, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 196, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 250, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 251, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 252, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 258, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 498, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 499, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 500, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 517, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 518, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10644, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10658, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10659, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10660, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10661, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10662, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10645, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10664, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10715, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10716, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10717, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10718, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10719, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10648, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10682, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10683, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10684, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10685, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10657, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10665, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10670, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10671, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10672, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10673, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10668, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10674, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10675, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10676, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10677, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10669, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10678, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10679, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10680, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10681, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10646, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10650, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10651, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10652, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10653, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10720, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10721, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10714, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10687, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10655, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10688, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10724, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10784, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 151, 10785, 1, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 260, 10686, 3, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 529, 10686, 3, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 530, 10686, 3, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 531, 10686, 3, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 255, 10686, 3, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10644, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10658, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10659, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10660, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10661, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10662, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10645, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10664, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10715, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10716, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10717, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10718, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10719, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10648, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10682, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10683, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10684, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10685, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10657, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10665, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10670, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10671, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10672, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10673, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10668, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10674, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10675, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10676, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10677, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10669, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10678, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10679, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10680, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10681, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10646, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10650, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10651, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10652, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10653, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10720, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10721, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10714, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10687, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10655, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10688, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10724, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10784, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 261, 10785, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10644, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10658, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10659, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10660, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10661, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10662, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10645, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10664, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10715, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10716, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10717, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10718, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10719, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10648, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10682, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10683, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10684, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10685, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10657, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10665, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10670, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10671, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10672, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10673, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10668, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10674, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10675, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10676, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10677, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10669, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10678, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10679, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10680, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10681, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10646, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10650, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10651, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10652, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10653, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10720, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10721, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10714, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10687, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10655, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10688, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10724, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10784, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 506, 10785, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10644, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10658, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10659, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10660, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10661, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10662, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10645, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10664, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10715, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10716, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10717, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10718, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10719, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10648, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10682, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10683, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10684, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10685, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10657, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10665, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10670, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10671, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10672, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10673, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10668, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10674, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10675, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10676, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10677, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10669, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10678, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10679, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10680, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10681, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10646, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10650, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10651, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10652, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10653, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10720, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10721, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10714, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10687, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10655, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10688, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10724, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10784, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 507, 10785, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10644, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10658, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10659, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10660, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10661, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10662, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10645, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10664, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10715, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10716, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10717, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10718, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10719, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10648, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10682, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10683, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10684, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10685, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10657, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10665, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10670, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10671, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10672, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10673, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10668, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10674, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10675, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10676, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10677, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10669, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10678, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10679, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10680, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10681, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10646, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10650, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10651, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10652, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10653, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10720, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10721, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10714, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10687, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10655, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10688, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10724, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10784, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 508, 10785, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10644, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10658, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10659, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10660, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10661, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10662, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10645, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10664, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10715, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10716, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10717, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10718, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10719, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10648, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10682, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10683, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10684, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10685, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10657, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10665, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10670, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10671, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10672, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10673, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10668, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10674, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10675, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10676, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10677, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10669, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10678, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10679, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10680, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10681, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10646, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10650, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10651, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10652, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10653, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10720, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10721, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10714, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10687, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10655, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10688, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10724, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10784, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 509, 10785, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10644, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10658, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10659, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10660, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10661, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10662, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10645, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10664, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10715, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10716, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10717, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10718, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10719, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10648, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10682, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10683, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10684, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10685, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10657, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10665, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10670, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10671, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10672, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10673, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10668, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10674, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10675, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10676, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10677, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10669, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10678, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10679, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10680, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10681, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10646, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10650, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10651, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10652, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10653, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10720, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10721, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10714, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10687, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10655, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10688, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10724, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10784, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 510, 10785, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10644, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10658, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10659, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10660, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10661, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10662, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10645, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10664, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10715, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10716, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10717, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10718, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10719, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10648, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10682, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10683, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10684, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10685, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10657, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10665, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10670, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10671, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10672, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10673, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10668, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10674, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10675, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10676, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10677, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10669, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10678, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10679, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10680, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10681, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10646, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10650, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10651, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10652, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10653, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10720, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10721, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10714, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10687, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10655, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10688, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10724, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10784, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 511, 10785, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10644, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10658, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10659, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10660, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10661, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10662, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10645, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10664, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10715, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10716, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10717, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10718, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10719, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10648, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10682, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10683, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10684, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10685, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10657, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10665, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10670, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10671, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10672, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10673, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10668, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10674, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10675, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10676, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10677, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10669, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10678, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10679, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10680, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10681, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10646, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10650, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10651, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10652, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10653, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10720, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10721, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10714, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10687, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10655, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10688, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10724, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10784, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 202, 10785, 2, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 312, 9, 5, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 313, 9, 5, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 314, 9, 5, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 315, 9, 5, 5);
INSERT INTO `role_relate_resource_acc` VALUES (83, 311, 9, 5, 5);

-- ----------------------------
-- Table structure for role_relate_site
-- ----------------------------
DROP TABLE IF EXISTS `role_relate_site`;
CREATE TABLE `role_relate_site`  (
  `roleId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role_relate_workflow_acc
-- ----------------------------
DROP TABLE IF EXISTS `role_relate_workflow_acc`;
CREATE TABLE `role_relate_workflow_acc`  (
  `roleId` bigint(20) NOT NULL,
  `accId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for schedule_job_detail
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_detail`;
CREATE TABLE `schedule_job_detail`  (
  `jobId` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(60)  NOT NULL,
  `jobDesc` varchar(500)  NULL DEFAULT NULL,
  `jobExecuteClass` varchar(450)  NOT NULL,
  `triggerType` int(11) NOT NULL,
  `periodSegment` int(11) NULL DEFAULT NULL,
  `periodVar` int(11) NULL DEFAULT NULL,
  `dayExeTime` varchar(50)  NULL DEFAULT NULL,
  `cronExpression` varchar(100)  NULL DEFAULT NULL,
  `lastExcuteTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `jobStartDate` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `jobEndDate` timestamp NULL DEFAULT NULL,
  `systemJob` int(11) NOT NULL,
  `useState` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`jobId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of schedule_job_detail
-- ----------------------------
INSERT INTO `schedule_job_detail` VALUES (20, 'SiteDirectoryMonitor', '站点:总站, 文件:html文件监视', 'cn.com.mjsoft.cms.site.job.FileTransferDataCollectJob', 1, 1, 20, NULL, NULL, '2013-11-29 12:00:18', NULL, NULL, 0, 1, 0);
INSERT INTO `schedule_job_detail` VALUES (21, 'SiteFileTransferToServer', '站点:总站, 文件:html传输文件', 'cn.com.mjsoft.cms.site.job.FileTransferDataJob', 1, 1, 30, NULL, NULL, '2013-11-29 12:00:25', NULL, NULL, 0, 1, 0);
INSERT INTO `schedule_job_detail` VALUES (22, 'SiteDirectoryMonitor', '站点:总站, 文件:video文件监视', 'cn.com.mjsoft.cms.site.job.FileTransferDataCollectJob', 1, 1, 20, NULL, NULL, '2013-11-29 12:00:51', NULL, NULL, 0, 1, 0);
INSERT INTO `schedule_job_detail` VALUES (23, 'SiteFileTransferToServer', '站点:总站, 文件:video传输文件', 'cn.com.mjsoft.cms.site.job.FileTransferDataJob', 1, 1, 30, NULL, NULL, '2013-11-29 12:00:29', NULL, NULL, 0, 1, 0);
INSERT INTO `schedule_job_detail` VALUES (24, 'DisposeSearchIndexDataJob', '站点:总站, 索引处理', 'cn.com.mjsoft.cms.search.job.DisposeSearchIndexDataJob', 1, 2, 1, NULL, NULL, '2014-09-18 22:08:11', '0000-00-00 00:00:00', NULL, 1, 1, 0);
INSERT INTO `schedule_job_detail` VALUES (25, 'CollectVisitorInfoAndAnalyseJob', '系统任务：访问统计', 'cn.com.mjsoft.cms.stat.job.CollectVisitorInfoAndAnalyseJob', 5, -1, -1, NULL, '0 00 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * ?', '2013-11-29 12:01:07', '0000-00-00 00:00:00', NULL, 1, 1, 0);
INSERT INTO `schedule_job_detail` VALUES (26, 'CollectUserVoteInfo', '系统任务：投票统计', 'cn.com.mjsoft.cms.questionnaire.job.CollectUserVoteJob', 5, -1, -1, NULL, '0 0/15 0-23 * * ?', '2013-11-29 12:01:11', '0000-00-00 00:00:00', NULL, 1, 1, 0);
INSERT INTO `schedule_job_detail` VALUES (27, 'PublishAndDisposeContentJob', '系统任务：处理内容发布', 'cn.com.mjsoft.cms.content.job.PublishAndDisposeContentJob', 1, 2, 1, NULL, NULL, '2014-02-08 11:54:16', '0000-00-00 00:00:00', NULL, 1, 1, 0);
INSERT INTO `schedule_job_detail` VALUES (49, 'PublishSiteHtmlContentJob', '每分钟将需要发布的栏目发布', 'cn.com.mjsoft.cms.publish.job.PublishSiteContentToHtmlJob', 1, 2, 1, '', NULL, '2016-09-04 23:12:24', NULL, NULL, 0, 0, 5);

-- ----------------------------
-- Table structure for schedule_publish_job_target
-- ----------------------------
DROP TABLE IF EXISTS `schedule_publish_job_target`;
CREATE TABLE `schedule_publish_job_target`  (
  `selfJobId` bigint(20) NOT NULL DEFAULT 0,
  `homePage` varchar(6)  NULL DEFAULT NULL,
  `channel` varchar(6)  NULL DEFAULT NULL,
  `spec` varchar(6)  NULL DEFAULT NULL,
  PRIMARY KEY (`selfJobId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of schedule_publish_job_target
-- ----------------------------
INSERT INTO `schedule_publish_job_target` VALUES (49, '1', '1', '1');

-- ----------------------------
-- Table structure for search_config
-- ----------------------------
DROP TABLE IF EXISTS `search_config`;
CREATE TABLE `search_config`  (
  `indexConfigId` bigint(20) NOT NULL AUTO_INCREMENT,
  `siteId` bigint(20) NOT NULL,
  `jobId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`indexConfigId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for search_index_content_state
-- ----------------------------
DROP TABLE IF EXISTS `search_index_content_state`;
CREATE TABLE `search_index_content_state`  (
  `indexStateId` bigint(20) NOT NULL AUTO_INCREMENT,
  `contentId` bigint(20) NULL DEFAULT 0,
  `siteId` bigint(20) NOT NULL,
  `classId` bigint(20) NULL DEFAULT NULL,
  `modelId` bigint(20) NULL DEFAULT NULL,
  `boost` float NULL DEFAULT NULL,
  `censor` int(6) NULL DEFAULT NULL,
  `indexDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `eventFlag` int(11) NOT NULL,
  `clusterId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`indexStateId`) USING BTREE,
  INDEX `s_i`(`siteId`, `indexStateId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for search_key_count
-- ----------------------------
DROP TABLE IF EXISTS `search_key_count`;
CREATE TABLE `search_key_count`  (
  `skId` bigint(20) NOT NULL AUTO_INCREMENT,
  `queryKey` varchar(200)  NOT NULL,
  `count` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`skId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of search_key_count
-- ----------------------------
INSERT INTO `search_key_count` VALUES (2, 'test', 4, 5);

-- ----------------------------
-- Table structure for security_data_type
-- ----------------------------
DROP TABLE IF EXISTS `security_data_type`;
CREATE TABLE `security_data_type`  (
  `dataTypeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(60)  NOT NULL,
  `dtDesc` varchar(120)  NULL DEFAULT NULL,
  `accSymbol` varchar(80)  NOT NULL,
  `isSys` int(11) NULL DEFAULT NULL,
  `accBehaviorClass` varchar(500)  NULL DEFAULT NULL,
  PRIMARY KEY (`dataTypeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of security_data_type
-- ----------------------------
INSERT INTO `security_data_type` VALUES (1, '内容维护', '栏目内容数据作为细粒度控制', 'classId', 1, 'cn.com.mjsoft.cms.content.behavior.ContentAccAuthBehavior');
INSERT INTO `security_data_type` VALUES (2, '栏目管理', '栏目配置数据作为细粒度控制', 'classId', 1, NULL);
INSERT INTO `security_data_type` VALUES (3, '专题管理', '专题配置和数据维护为细粒度控制', 'classId', 1, NULL);
INSERT INTO `security_data_type` VALUES (4, '推荐位管理', '推荐位配置和数据维护为细粒度控制', 'typeId', 1, NULL);
INSERT INTO `security_data_type` VALUES (5, '留言回复', '留言数据作为细粒度控制', 'configId', 1, '');

-- ----------------------------
-- Table structure for securityresource
-- ----------------------------
DROP TABLE IF EXISTS `securityresource`;
CREATE TABLE `securityresource`  (
  `secResId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sysFlag` varchar(80)  NULL DEFAULT NULL,
  `resourceName` varchar(50)  NOT NULL,
  `icon` varchar(300)  NULL DEFAULT NULL,
  `resourceDesc` varchar(300)  NULL DEFAULT NULL,
  `resourceType` int(6) NOT NULL COMMENT '1=功能菜单 ， 2=模块入口， 3=功能集合',
  `useState` int(6) NOT NULL,
  `dataProtectType` int(6) NOT NULL,
  `creator` varchar(20)  NOT NULL,
  `target` varchar(300)  NULL DEFAULT NULL,
  `parentResId` bigint(20) NULL DEFAULT NULL,
  `isLeaf` int(6) NOT NULL,
  `layer` int(6) NOT NULL,
  `isLastChild` int(6) NOT NULL,
  `linearOrderFlag` varchar(400)  NOT NULL,
  `dataSecLevel` int(6) NULL DEFAULT NULL,
  `dataSecTypeId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`secResId`) USING BTREE,
  INDEX `target_key`(`target`) USING BTREE,
  INDEX `sec-resT`(`dataSecTypeId`, `resourceType`) USING BTREE,
  INDEX `dp-linear`(`dataProtectType`, `linearOrderFlag`(383)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 661 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of securityresource
-- ----------------------------
INSERT INTO `securityresource` VALUES (102, '', '文档管理', 'briefcase.png', '', 1, 1, 1, 'a', '/core/console/ListClassFramePage.jsp', -1, 0, 1, 0, '001', NULL, 1);
INSERT INTO `securityresource` VALUES (103, '', '交互信息', 'balloon-smiley.png', '', 1, 1, 1, 'a', '', -1, 0, 1, 0, '003', NULL, 0);
INSERT INTO `securityresource` VALUES (104, NULL, '站点维护', 'globe--pencil.png', '', 1, 1, 1, '', '', -1, 0, 1, 0, '004', NULL, 0);
INSERT INTO `securityresource` VALUES (105, NULL, '发布与采集', 'network-clouds.png', '', 1, 1, 1, '', '', -1, 0, 1, 0, '005', NULL, 0);
INSERT INTO `securityresource` VALUES (106, NULL, '数据分析', 'chart.png', '', 1, 1, 1, 'a', '', -1, 0, 1, 0, '006', NULL, 0);
INSERT INTO `securityresource` VALUES (107, '', '站点会员', 'users.png', '', 1, 1, 1, 'adminX', '', -1, 0, 1, 0, '007', NULL, 0);
INSERT INTO `securityresource` VALUES (108, 'sys_config', '系统配置', 'wrench-screwdriver.png', '', 1, 1, 1, '', '', -1, 0, 1, 1, '008', NULL, 0);
INSERT INTO `securityresource` VALUES (109, NULL, '工作流', NULL, '', 2, 1, 1, '', '', 108, 0, 2, 0, '008002', NULL, 0);
INSERT INTO `securityresource` VALUES (110, NULL, '工作流管理', 'node-select-child.png', '', 3, 1, 1, '', '/core/workflow/ManageWorkflow.jsp', 109, 0, 3, 1, '008002001', NULL, 0);
INSERT INTO `securityresource` VALUES (111, '', '工作流维护', '', '', 4, 1, 1, 'a', '', 110, 0, 4, 1, '008002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (112, 'create_workFlow_jsp', '添加页面', '', '', 5, 1, 1, 'a', '/core/workflow/CreateWorkFlow.jsp', 111, 1, 5, 0, '008002001001001', NULL, 1);
INSERT INTO `securityresource` VALUES (113, '', '创建工作流命令', '', '', 5, 1, 1, 'a', '/workflow/createWorkflow.do', 111, 1, 5, 0, '008002001001002', NULL, 1);
INSERT INTO `securityresource` VALUES (114, '', '自定义模型', '', '', 2, 1, 1, 'admin', '', 108, 0, 2, 0, '008003', NULL, 0);
INSERT INTO `securityresource` VALUES (116, NULL, '系统信息模型', 'block--pencil.png', '', 3, 1, 1, '', '/core/metadata/ManageDataModel.jsp', 114, 0, 3, 0, '008003001', NULL, 0);
INSERT INTO `securityresource` VALUES (119, '', '会员扩展模型', 'user-share.png', '', 3, 1, 1, 'a', '/core/metadata/ManageMemberDataModel.jsp', 114, 1, 3, 0, '008003004', NULL, 0);
INSERT INTO `securityresource` VALUES (120, NULL, '系统权限', NULL, '', 2, 1, 1, '', '', 108, 0, 2, 0, '008004', NULL, 0);
INSERT INTO `securityresource` VALUES (121, '', '站点角色管理', 'users.png', '', 3, 1, 1, 'a', '/core/security/ManagerRole.jsp', 120, 0, 3, 0, '008004002', NULL, 1);
INSERT INTO `securityresource` VALUES (122, '', '管理员管理', 'user-business.png', '', 3, 1, 1, 'a', '/core/security/ManagerUser.jsp', 120, 0, 3, 0, '008004001', NULL, 1);
INSERT INTO `securityresource` VALUES (124, NULL, '部门机构', 'balance.png', '', 3, 1, 1, '', '/core/organization/ManageOrganization.jsp', 120, 0, 3, 1, '008004004', NULL, 0);
INSERT INTO `securityresource` VALUES (125, '', '安全与日志', '', '', 2, 1, 1, 'a', '', 108, 0, 2, 1, '008006', NULL, 1);
INSERT INTO `securityresource` VALUES (127, '', '系统防火墙记录', 'wall-brick.png', '', 3, 1, 1, 'a', '/core/stat/ViewDangerAccessIpTrace.jsp', 125, 0, 3, 0, '008006002', NULL, 1);
INSERT INTO `securityresource` VALUES (132, '', '内容管理', '', '', 2, 1, 1, 'a', '', 102, 0, 2, 1, '001001', NULL, 0);
INSERT INTO `securityresource` VALUES (134, NULL, '静态化发布', NULL, '', 2, 1, 1, '', '', 105, 0, 2, 0, '005001', NULL, 0);
INSERT INTO `securityresource` VALUES (135, NULL, '发布页规则', 'property.png', '', 3, 1, 1, '', '/core/deploy/ManagePublishUrlRule.jsp', 134, 0, 3, 1, '005001007', NULL, 0);
INSERT INTO `securityresource` VALUES (136, '', '站点内容发布页', 'computer--arrow.png', '', 3, 1, 1, 'a', '/core/deploy/ManageStaicPublishContent.jsp', 134, 0, 3, 0, '005001001', NULL, 0);
INSERT INTO `securityresource` VALUES (138, NULL, '用户评论', '', '', 2, 1, 1, '', '', 103, 0, 2, 0, '003001', NULL, 0);
INSERT INTO `securityresource` VALUES (139, NULL, '评论管理', 'balloon-ellipsis.png', '', 3, 1, 1, '', '/core/comment/ManageContentComment.jsp', 138, 0, 3, 1, '003001001', NULL, 0);
INSERT INTO `securityresource` VALUES (141, NULL, '留言反馈', NULL, '', 2, 1, 1, '', '', 103, 0, 2, 0, '003002', NULL, 0);
INSERT INTO `securityresource` VALUES (142, NULL, '留言管理', 'socket--pencil.png', '', 3, 1, 1, 'a', '/core/guestbook/ManageGuestbook.jsp', 141, 0, 3, 1, '003002001', NULL, 0);
INSERT INTO `securityresource` VALUES (146, 'system_commend_acc_class', '推荐内容管理', 'medal.png', '', 3, 1, 2, 'admin', '', 132, 0, 3, 0, '001001001', NULL, 4);
INSERT INTO `securityresource` VALUES (147, 'system_content_acc_class', '系统内容管理', '', '', 3, 1, 2, '', '', 132, 0, 3, 1, '001001002', NULL, 1);
INSERT INTO `securityresource` VALUES (148, '', '角色维护', 'user--pencil.png', '', 4, 1, 1, 'a', '', 121, 0, 4, 0, '008004002001', NULL, 1);
INSERT INTO `securityresource` VALUES (151, 'maintain-content-accredit', '维护', '', '', 4, 1, 2, 'a', '', 147, 0, 4, 0, '001001002003', NULL, 1);
INSERT INTO `securityresource` VALUES (157, NULL, '分发数据', NULL, '', 2, 1, 1, '', '', 105, 0, 2, 0, '005002', NULL, 0);
INSERT INTO `securityresource` VALUES (158, NULL, '分发服务器设置', 'servers-network.png', '', 3, 1, 1, 'a', '/core/deploy/ManageDispenseServer.jsp', 157, 0, 3, 0, '005002001', NULL, 0);
INSERT INTO `securityresource` VALUES (159, NULL, '文件发布点管理', 'folder-network.png', '', 3, 1, 1, 'a', '/core/deploy/ManagePublishGateway.jsp', 157, 0, 3, 0, '005002003', NULL, 0);
INSERT INTO `securityresource` VALUES (160, NULL, '内容组织', '', '', 2, 1, 1, '', '', 104, 0, 2, 0, '004001', NULL, 0);
INSERT INTO `securityresource` VALUES (162, NULL, '网站系统参数', '', '', 2, 1, 1, '', '', 104, 0, 2, 1, '004006', NULL, 0);
INSERT INTO `securityresource` VALUES (163, '', '编辑器组件模板', 'script-block.png', '', 3, 1, 1, 'a', '/core/tool/ManageEditorModuleCode.jsp', 162, 0, 3, 0, '004006003', NULL, 1);
INSERT INTO `securityresource` VALUES (164, '', '分类管理', 'sticky-notes-pin.png', '', 3, 1, 1, 'a', '/core/channel/ManageContentType.jsp', 160, 0, 3, 0, '004001003', NULL, 0);
INSERT INTO `securityresource` VALUES (165, NULL, '站点资源', '', '', 2, 1, 1, '', '', 104, 0, 2, 0, '004005', NULL, 0);
INSERT INTO `securityresource` VALUES (166, '', '模板管理', 'blogs.png', '', 3, 1, 1, 'a', '/core/templet/ManageTemplate.jsp?parentFolder=*template', 165, 0, 3, 0, '004005001', NULL, 1);
INSERT INTO `securityresource` VALUES (171, NULL, '内容采集', '', '', 2, 1, 1, '', '', 105, 0, 2, 0, '005003', NULL, 0);
INSERT INTO `securityresource` VALUES (172, NULL, 'Web采集规则', 'truck--arrow.png', '', 3, 1, 1, 'a', '/core/pick/ManagePickConfig.jsp', 171, 0, 3, 0, '005003001', NULL, 0);
INSERT INTO `securityresource` VALUES (173, NULL, '内容推荐位', 'medal.png', '', 3, 1, 1, '', '/core/channel/ManageDefineCommendType.jsp', 160, 0, 3, 0, '004001004', NULL, 0);
INSERT INTO `securityresource` VALUES (175, NULL, '碎片区块', NULL, '', 2, 1, 1, '', '', 104, 0, 2, 0, '004003', NULL, 0);
INSERT INTO `securityresource` VALUES (176, '', '页面区块管理页', 'layers-group.png', '', 3, 1, 1, 'a', '/core/block/ManageBlock.jsp', 175, 0, 3, 0, '004003001', NULL, 0);
INSERT INTO `securityresource` VALUES (177, '', '区块碎片分类页', 'layers-stack-arrange-back.png', '', 3, 1, 1, 'a', '/core/block/ManageBlockType.jsp', 175, 0, 3, 1, '004003002', NULL, 0);
INSERT INTO `securityresource` VALUES (178, NULL, '留言本类别', 'sockets.png', '', 3, 1, 1, 'a', '/core/guestbook/ManageGuestbookConfig.jsp', 162, 0, 3, 0, '004006001', NULL, 0);
INSERT INTO `securityresource` VALUES (179, '', '广告代码配置', 'color-adjustment-green.png', '', 3, 1, 1, 'admin', 'core/trevda/ManageTrevdaConfig.jsp', 162, 0, 3, 0, '004006002', NULL, -1);
INSERT INTO `securityresource` VALUES (180, NULL, '广告管理', NULL, '', 2, 1, 1, '', '', 103, 0, 2, 0, '003004', NULL, 0);
INSERT INTO `securityresource` VALUES (181, '', '广告版位管理', 'ui-scroll-pane-image.png', '', 3, 1, 1, 'admin', 'core/trevda/ManageTrevdaPosition.jsp', 180, 0, 3, 0, '003004001', NULL, -1);
INSERT INTO `securityresource` VALUES (182, '', '广告内容', 'megaphone.png', '', 3, 1, 1, 'admin', 'core/trevda/ManageTrevdaContent.jsp', 180, 0, 3, 1, '003004002', NULL, -1);
INSERT INTO `securityresource` VALUES (183, NULL, '投票与问卷', NULL, '', 2, 1, 1, '', '', 103, 0, 2, 0, '003005', NULL, 0);
INSERT INTO `securityresource` VALUES (185, NULL, '调查问卷管理', 'report--pencil.png', '', 3, 1, 1, '', '/core/question/ManageSurveyGroup.jsp', 183, 0, 3, 1, '003005001', NULL, 0);
INSERT INTO `securityresource` VALUES (186, NULL, '站点文件夹', 'folder-stand.png', '', 3, 1, 1, 'a', '/core/resources/ManageSiteFile.jsp', 165, 1, 3, 0, '004005003', NULL, 0);
INSERT INTO `securityresource` VALUES (196, 'manage_content_jsp', '内容管理入口页', '', '', 5, 1, 2, 'admin', '/core/content/ManageGeneralContent.jsp', 151, 1, 5, 0, '001001002003004', NULL, 1);
INSERT INTO `securityresource` VALUES (198, 'system_class_acc_class', '栏目维护与管理', '', '', 3, 1, 2, 'admin', '', 160, 0, 3, 0, '004001002', NULL, 2);
INSERT INTO `securityresource` VALUES (202, 'manage-accredit-class', '栏目维护', '', '', 4, 1, 2, 'admin', '', 198, 0, 4, 1, '004001002002', NULL, 2);
INSERT INTO `securityresource` VALUES (204, NULL, '栏目扩展模型', 'application-tile.png', '', 3, 1, 1, 'a', '/core/metadata/ManageChannelDataModel.jsp', 114, 1, 3, 0, '008003006', NULL, 0);
INSERT INTO `securityresource` VALUES (207, '', '节点与全局配置', '', '', 2, 1, 1, 'a', '', 108, 0, 2, 0, '008001', NULL, 1);
INSERT INTO `securityresource` VALUES (208, NULL, '站点节点管理', 'sitemap-image.png', '', 3, 1, 1, 'a', '/core/channel/ManageSiteNode.jsp', 207, 0, 3, 0, '008001001', NULL, 0);
INSERT INTO `securityresource` VALUES (209, '', '图片规格管理', 'image-crop.png', '', 3, 1, 1, 'a', '/core/tool/ManageImageResolution.jsp', 162, 0, 3, 1, '004006004', NULL, 1);
INSERT INTO `securityresource` VALUES (210, NULL, '留言扩展模型', 'balloons-box.png', '', 3, 1, 1, 'a', '/core/metadata/ManageGbDataModel.jsp', 114, 1, 3, 0, '008003007', NULL, 0);
INSERT INTO `securityresource` VALUES (212, NULL, '专题分类', 'folder-open-table.png', '', 3, 1, 1, 'a', '/core/channel/ManageSpecialClass.jsp', 160, 0, 3, 0, '004001005', NULL, 0);
INSERT INTO `securityresource` VALUES (213, NULL, '友情链接', '', '', 2, 1, 1, 'a', '', 103, 0, 2, 0, '003006', NULL, 0);
INSERT INTO `securityresource` VALUES (214, '', '友情链接管理', 'chain--arrow.png', '', 3, 1, 1, 'a', '/core/interflow/ManageSiteLink.jsp', 213, 0, 3, 0, '003006001', NULL, 0);
INSERT INTO `securityresource` VALUES (215, NULL, '公告管理', NULL, '', 2, 1, 1, 'a', '', 103, 0, 2, 0, '003007', NULL, 0);
INSERT INTO `securityresource` VALUES (216, NULL, '公告内容管理', 'clipboard-task.png', '', 3, 1, 1, 'a', '/core/interflow/ManageAnnounce.jsp', 215, 0, 3, 1, '003007001', NULL, 0);
INSERT INTO `securityresource` VALUES (219, NULL, '内容搜索', NULL, '', 2, 1, 1, 'a', '', 105, 0, 2, 1, '005004', NULL, 0);
INSERT INTO `securityresource` VALUES (220, '', '内容索引管理', 'sort-price.png', '', 3, 1, 1, 'admin', '/core/search/ManageSearchIndex.jsp', 219, 0, 3, 0, '005004001', NULL, -1);
INSERT INTO `securityresource` VALUES (223, '', '定时发布管理', 'task--pencil.png', '', 3, 1, 1, 'a', '/core/deploy/ManagePublishJob.jsp', 134, 0, 3, 0, '005001006', NULL, 1);
INSERT INTO `securityresource` VALUES (224, NULL, '采集任务管理', 'task--pencil.png', '', 3, 1, 1, 'a', '/core/pick/ManagePickJob.jsp', 171, 0, 3, 0, '005003002', NULL, 0);
INSERT INTO `securityresource` VALUES (225, '', '相关词汇', '', '', 2, 1, 1, 'admin', '', 104, 0, 2, 0, '004002', NULL, -1);
INSERT INTO `securityresource` VALUES (226, '', '站点Tag词管理', 'tags-label.png', '', 3, 1, 1, 'a', '/core/words/ManageSiteTag.jsp', 225, 0, 3, 0, '004002001', NULL, 0);
INSERT INTO `securityresource` VALUES (227, NULL, '信息来源管理', 'direction.png', '', 3, 1, 1, 'a', '/core/words/ManageContentSource.jsp', 225, 0, 3, 1, '004002005', NULL, 0);
INSERT INTO `securityresource` VALUES (229, NULL, '敏感词管理', 'traffic-cone--exclamation.png', '', 3, 1, 1, 'a', '/core/words/ManageSensitiveWord.jsp', 225, 0, 3, 0, '004002004', NULL, 0);
INSERT INTO `securityresource` VALUES (230, NULL, '站内通信', NULL, '', 2, 1, 1, 'a', '', 103, 0, 2, 1, '003008', NULL, 0);
INSERT INTO `securityresource` VALUES (231, NULL, '短消息管理', 'mails.png', '', 3, 1, 1, 'a', '/core/message/ManageSiteMessage.jsp', 230, 0, 3, 1, '003008001', NULL, 0);
INSERT INTO `securityresource` VALUES (233, '', '会员相关', '', '', 2, 1, 1, 'admin', '', 107, 0, 2, 0, '007001', NULL, 0);
INSERT INTO `securityresource` VALUES (234, NULL, '会员帐户管理', 'users.png', '', 3, 1, 1, 'zcmad', '/core/member/ManageMember.jsp', 233, 0, 3, 0, '007001001', NULL, 0);
INSERT INTO `securityresource` VALUES (235, '', '会员等级管理', 'crown.png', '', 3, 1, 1, 'adminX', '/core/member/ManageMemeberRank.jsp', 233, 0, 3, 0, '007001002', NULL, 0);
INSERT INTO `securityresource` VALUES (236, '', '通知与邮件', '', '', 2, 1, 1, 'admin', '', 107, 0, 2, 0, '007002', NULL, 0);
INSERT INTO `securityresource` VALUES (238, '', '邮件与消息模板', 'mail-open-table.png', '', 3, 1, 1, 'adminX', '/core/member/ManageMessageTemplate.jsp', 236, 0, 3, 0, '007002002', NULL, 0);
INSERT INTO `securityresource` VALUES (241, '', '邮件与消息模板参数', 'mail--pencil.png', '', 3, 1, 1, 'adminX', '/core/member/ManageMessageTemplateParam.jsp', 236, 0, 3, 1, '007002004', NULL, 0);
INSERT INTO `securityresource` VALUES (242, '', '会员权限', '', '', 2, 1, 1, 'adminX', '', 107, 0, 2, 1, '007003', NULL, 0);
INSERT INTO `securityresource` VALUES (243, '', '权限资源管理', 'puzzle--plus.png', '', 3, 1, 1, 'adminX', '/core/member/ManageMemberResource.jsp', 242, 0, 3, 1, '007003005', NULL, 0);
INSERT INTO `securityresource` VALUES (244, '', '会员组管理', 'users.png', '', 3, 1, 1, 'adminX', '/core/member/ManageMemberRole.jsp', 242, 0, 3, 0, '007003001', NULL, 0);
INSERT INTO `securityresource` VALUES (246, NULL, '广告配置模型', 'ui-scroll-pane-image.png', '', 3, 1, 1, 'a', '/core/metadata/ManageAdCfgDataModel.jsp', 114, 1, 3, 0, '008003008', NULL, 0);
INSERT INTO `securityresource` VALUES (247, NULL, '广告内容模型', 'megaphone.png', '', 3, 1, 1, 'a', '/core/metadata/ManageAdContentDataModel.jsp', 114, 1, 3, 1, '008003009', NULL, 0);
INSERT INTO `securityresource` VALUES (248, '', '积分规则管理', 'address-book-open.png', '', 3, 1, 1, 'adminX', '/core/member/ManageMemeberScoreAct.jsp', 233, 0, 3, 0, '007001003', NULL, 0);
INSERT INTO `securityresource` VALUES (249, NULL, '站点扩展模型', 'application-block.png', '', 3, 1, 1, 'a', '/core/metadata/ManageSiteDataModel.jsp', 114, 1, 3, 0, '008003005', NULL, 0);
INSERT INTO `securityresource` VALUES (250, '', '新增内容命令', '', '', 5, 1, 2, 'a', '/content/addContent.do', 151, 1, 5, 0, '001001002003005', NULL, 1);
INSERT INTO `securityresource` VALUES (251, '', '编辑内容页面', '', '', 5, 1, 2, 'admin', '/core/content/EditUserDefineModelContent.jsp', 151, 1, 5, 0, '001001002003006', NULL, 1);
INSERT INTO `securityresource` VALUES (252, '', '录入内容页面', '', '', 5, 1, 2, 'admin', '/core/content/UserDefineModelGeneralAdd.jsp', 151, 1, 5, 0, '001001002003007', NULL, 1);
INSERT INTO `securityresource` VALUES (253, 'system_spec_acc_class', '专题维护与管理', '', '', 3, 1, 2, 'a', '', 160, 0, 3, 1, '004001006', NULL, 3);
INSERT INTO `securityresource` VALUES (254, 'manage-spec-accredit', '专题管理', '', '现专题的管理与内容细粒度权限合二为一,故在此不放数据', 4, 1, 2, 'admin', '', 253, 1, 4, 0, '004001006001', NULL, 3);
INSERT INTO `securityresource` VALUES (255, 'maintain-spec-accredit', '专题维护', '', '', 4, 1, 2, 'adminX', '', 253, 0, 4, 1, '004001006002', NULL, 3);
INSERT INTO `securityresource` VALUES (256, 'manage-commend-accredit', '管理与维护', '', '', 4, 1, 2, 'a', '', 146, 0, 4, 1, '001001001001', NULL, 4);
INSERT INTO `securityresource` VALUES (257, 'manage_commend_jsp', '管理入口页', '', '', 5, 1, 2, 'admin', '/core/content/ManageCommendContent_nouse.jsp', 256, 1, 5, 0, '001001001001001', NULL, 4);
INSERT INTO `securityresource` VALUES (258, '', '编辑内容命令', '', '', 5, 1, 2, 'admin', '/content/editContent.do', 151, 1, 5, 0, '001001002003008', NULL, 1);
INSERT INTO `securityresource` VALUES (260, 'manage_spec_jsp', '专题维护入口页', '', '', 5, 1, 2, 'admin', '/core/content/special/ManageSpecialSubject.jsp_nu', 255, 1, 5, 0, '004001006002001', NULL, 3);
INSERT INTO `securityresource` VALUES (261, 'edit_content_class_jsp', '管理栏目页', '', '', 5, 1, 2, 'admin', '/core/channel/EditContentClass.jsp', 202, 1, 5, 0, '004001002002001', NULL, 2);
INSERT INTO `securityresource` VALUES (262, '', '用户登录记录', 'webcam.png', '', 3, 1, 1, 'a', '/core/stat/ViewManagerLoginTrace.jsp', 125, 1, 3, 0, '008006005', NULL, 1);
INSERT INTO `securityresource` VALUES (264, '', '系统全局配置', 'wrench-screwdriver.png', '', 3, 1, 1, 'a', '/core/tool/ManagerSystemRuntimeConfig.jsp', 207, 0, 3, 0, '008001002', NULL, 1);
INSERT INTO `securityresource` VALUES (266, '', '限制黑名单', 'xfn-colleague.png', '', 3, 1, 1, 'a', '/core/stat/ManageBlackIp.jsp', 125, 1, 3, 0, '008006003', NULL, 1);
INSERT INTO `securityresource` VALUES (270, '', '系统实时日志', 'application-terminal.png', '', 3, 1, 1, 'a', '/core/stat/ViewSystemLogInfo.jsp', 125, 1, 3, 1, '008006006', NULL, 1);
INSERT INTO `securityresource` VALUES (271, '', '采集结果记录', 'shopping-basket.png', '', 3, 1, 1, 'a', '/core/pick/ViewPickWebTrace.jsp', 171, 0, 3, 1, '005003004', NULL, 1);
INSERT INTO `securityresource` VALUES (272, '', '权限资源', NULL, '', 2, 1, 1, 'a', '', 108, 0, 2, 0, '008005', NULL, 0);
INSERT INTO `securityresource` VALUES (273, '', '权限资源管理', 'puzzle.png', '', 3, 1, 1, 'a', '/core/security/ManageResource.jsp', 272, 1, 3, 0, '008005001', NULL, 1);
INSERT INTO `securityresource` VALUES (274, '', '分类授权类型', 'zones-stack.png', '', 3, 1, 1, 'a', '/core/security/ManageSecAccType.jsp', 272, 0, 3, 1, '008005002', NULL, 1);
INSERT INTO `securityresource` VALUES (277, '', '友链类别管理', 'chain-unchain.png', '', 3, 1, 1, 'a', '/core/interflow/ManageSiteLinkType.jsp', 213, 0, 3, 1, '003006002', NULL, 0);
INSERT INTO `securityresource` VALUES (278, '', '用户搜索词', 'magnifier-history.png', '', 3, 1, 1, 'a', '/core/search/ManageClientSearchKey.jsp', 219, 0, 3, 0, '005004002', NULL, 0);
INSERT INTO `securityresource` VALUES (279, '', 'Tag词汇类别', 'tag-label-green.png', '', 3, 1, 1, 'a', '/core/channel/ManageTagType.jsp', 225, 0, 3, 0, '004002002', NULL, 0);
INSERT INTO `securityresource` VALUES (280, '', '综合报告', '', '', 2, 1, 1, 'a', '', 106, 0, 2, 0, '006002', NULL, 0);
INSERT INTO `securityresource` VALUES (282, '', '总体访问情况', 'chart-up.png', '', 3, 1, 1, 'a', '/core/stat/ViewCommonVisitSiteInfo.jsp', 280, 1, 3, 0, '006002001', NULL, 0);
INSERT INTO `securityresource` VALUES (283, '', '日点击报告', 'chart_bar.png', '', 3, 1, 1, 'a', '/core/stat/ViewDayVisitSiteInfo.jsp', 280, 1, 3, 0, '006002002', NULL, 0);
INSERT INTO `securityresource` VALUES (284, '', '站群分析', 'chart-pie-separate.png', '', 3, 1, 1, 'a', '/core/stat/ViewHostVisitSiteInfo.jsp', 280, 1, 3, 1, '006002003', NULL, 0);
INSERT INTO `securityresource` VALUES (285, '', '访问者数据', '', '', 2, 1, 1, 'a', '', 106, 0, 2, 0, '006003', NULL, 0);
INSERT INTO `securityresource` VALUES (286, '', '近期记录', 'clipboard-invoice.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteInfo.jsp', 285, 1, 3, 0, '006003001', NULL, 0);
INSERT INTO `securityresource` VALUES (287, '', '小时数据分析', 'chart_bar.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteHourInfo.jsp', 285, 1, 3, 0, '006003002', NULL, 0);
INSERT INTO `securityresource` VALUES (288, '', '回头客分析', 'chart_bar.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteBackManInfo.jsp', 285, 1, 3, 0, '006003003', NULL, 0);
INSERT INTO `securityresource` VALUES (289, '', '地区分析', 'chart_bar.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteAeraInfo.jsp', 285, 1, 3, 1, '006003004', NULL, 0);
INSERT INTO `securityresource` VALUES (290, '', '访问源分析', '', '', 2, 1, 1, 'a', '', 106, 0, 2, 0, '006004', NULL, 0);
INSERT INTO `securityresource` VALUES (292, '', '搜索引擎数据', 'chart-pie-separate.png', '', 3, 1, 1, 'a', '/core/stat/ViewSearchHostVisitSiteInfo.jsp', 290, 1, 3, 1, '006004003', NULL, 0);
INSERT INTO `securityresource` VALUES (294, '', '来源网站', 'chart-pie-separate.png', '', 3, 1, 1, 'a', '/core/stat/ViewReffHostVisitSiteInfo.jsp', 290, 1, 3, 0, '006004002', NULL, 0);
INSERT INTO `securityresource` VALUES (295, '', '点击数据排行', '', '', 2, 1, 1, 'a', '', 106, 0, 2, 0, '006005', NULL, 0);
INSERT INTO `securityresource` VALUES (296, '', '入口排行', 'chart_bar.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteGateUrlInfo.jsp', 295, 1, 3, 0, '006005001', NULL, 0);
INSERT INTO `securityresource` VALUES (297, '', '出口排行', 'chart_bar.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteOutUrlInfo.jsp', 295, 1, 3, 0, '006005002', NULL, 0);
INSERT INTO `securityresource` VALUES (298, '', '内容排行', 'chart_bar.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteContentPvInfo.jsp', 295, 1, 3, 0, '006005004', NULL, 0);
INSERT INTO `securityresource` VALUES (299, '', '栏目排行', 'chart_bar.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteClassPvInfo.jsp', 295, 1, 3, 0, '006005003', NULL, 0);
INSERT INTO `securityresource` VALUES (300, '', '链接点击排行', 'chart_bar.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteClickUrlInfo.jsp', 295, 1, 3, 1, '006005005', NULL, 0);
INSERT INTO `securityresource` VALUES (301, '', '访问者客户端', '', '', 2, 1, 1, 'a', '', 106, 0, 2, 1, '006006', NULL, 0);
INSERT INTO `securityresource` VALUES (302, '', '操作系统', 'chart-pie-separate.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteClientOsInfo.jsp', 301, 1, 3, 0, '006006001', NULL, 0);
INSERT INTO `securityresource` VALUES (303, '', '浏览器', 'chart-pie-separate.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteClientBrInfo.jsp', 301, 1, 3, 0, '006006002', NULL, 0);
INSERT INTO `securityresource` VALUES (304, '', '使用语言', 'chart-pie-separate.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteClientLaInfo.jsp', 301, 1, 3, 0, '006006003', NULL, 0);
INSERT INTO `securityresource` VALUES (305, '', '分辩率', 'chart-pie-separate.png', '', 3, 1, 1, 'a', '/core/stat/ViewVisitSiteClientScInfo.jsp', 301, 1, 3, 1, '006006004', NULL, 0);
INSERT INTO `securityresource` VALUES (306, '', '友链维护', '', '', 4, 1, 1, 'a', '', 214, 0, 4, 1, '003006001001', NULL, 0);
INSERT INTO `securityresource` VALUES (307, '', '增加友链命令', '', '', 5, 1, 1, 'a', '/interflow/addFSite.do', 306, 1, 5, 0, '003006001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (308, '', '评论维护', '', '', 4, 1, 1, 'a', '', 139, 0, 4, 1, '003001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (309, '', '删除评论命令', '', '', 5, 1, 1, 'a', '/comment/deleteComment.do', 308, 1, 5, 0, '003001001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (310, '', '审核评论命令', '', '', 5, 1, 1, 'a', '/comment/censorComment.do', 308, 1, 5, 1, '003001001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (311, 'manage-guestbook-accredit', '留言维护', '', '', 4, 1, 2, 'admin', '', 142, 0, 4, 1, '003002001001', NULL, 5);
INSERT INTO `securityresource` VALUES (312, 'manage-guestbook-accredit', '留言状态命令', '', '', 5, 1, 2, 'admin', '/guestbook/changeStatus.do', 311, 1, 5, 0, '003002001001001', NULL, 5);
INSERT INTO `securityresource` VALUES (313, 'manage-guestbook-accredit', '删除留言命令', '', '', 5, 1, 2, 'admin', '/guestbook/deleteGb.do', 311, 1, 5, 0, '003002001001002', NULL, 5);
INSERT INTO `securityresource` VALUES (314, '', '回复留言命令', '', '', 5, 1, 2, 'admin', '/guestbook/replayGbInfo.do', 311, 1, 5, 0, '003002001001003', NULL, 5);
INSERT INTO `securityresource` VALUES (315, 'manage-guestbook-accredit', '指定回复人消息页', '', '', 5, 1, 2, 'admin', '/core/guestbook/SelectManagerToReply.jsp', 311, 1, 5, 1, '003002001001004', NULL, 5);
INSERT INTO `securityresource` VALUES (316, '', '广告版位维护', '', '', 4, 1, 1, 'a', '', 181, 0, 4, 1, '003004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (317, '', '增加广告版位命令', '', '', 5, 1, 1, 'a', '/advert/createAdvertPos.do', 316, 1, 5, 0, '003004001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (318, '', '编辑广告版位命令', '', '', 5, 1, 1, 'a', '/advert/editAdvertPos.do', 316, 1, 5, 0, '003004001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (319, '', '删除广告版位命令', '', '', 5, 1, 1, 'a', '/advert/deleteAdvertPos.do', 316, 1, 5, 1, '003004001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (320, '', '广告内容维护', '', '', 4, 1, 1, 'a', '', 182, 0, 4, 1, '003004002001', NULL, 0);
INSERT INTO `securityresource` VALUES (321, '', '添加广告内容', '', '', 5, 1, 1, 'a', '/advert/createAdvert.do', 320, 1, 5, 0, '003004002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (322, '', '编辑广告内容', '', '', 5, 1, 1, 'a', '/advert/editAdvert.do', 320, 1, 5, 0, '003004002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (323, '', '改变广告状态命令', '', '', 5, 1, 1, 'a', '/advert/changeAdStatus.do', 320, 1, 5, 0, '003004002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (324, '', '删除广告命令', '', '', 5, 1, 1, 'a', '/advert/deleteAdvert.do', 320, 1, 5, 1, '003004002001004', NULL, 0);
INSERT INTO `securityresource` VALUES (325, '', '调查问卷维护', '', '', 4, 1, 1, 'a', '', 185, 0, 4, 1, '003005001001', NULL, 0);
INSERT INTO `securityresource` VALUES (326, '', '创建调查组命令', '', '', 5, 1, 1, 'a', '/survey/createSurveyGroup.do', 325, 1, 5, 0, '003005001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (327, '', '编辑调查组命令', '', '', 5, 1, 1, 'a', '/survey/editSurveyGroup.do', 325, 1, 5, 0, '003005001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (328, '', '删除调查组命令', '', '', 5, 1, 1, 'a', '/survey/deleteSurveyGroup.do', 325, 1, 5, 0, '003005001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (329, '', '创建调查项命令', '', '', 5, 1, 1, 'a', '/survey/createSurvey.do', 325, 1, 5, 0, '003005001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (330, '', '编辑调查项命令', '', '', 5, 1, 1, 'a', '/survey/editSurvey.do', 325, 1, 5, 0, '003005001001005', NULL, 0);
INSERT INTO `securityresource` VALUES (331, '', '删除调查项命令', '', '', 5, 1, 1, 'a', '/survey/deleteSurvey.do', 325, 1, 5, 1, '003005001001006', NULL, 0);
INSERT INTO `securityresource` VALUES (332, '', '编辑友链命令', '', '', 5, 1, 1, 'a', '/interflow/editFSite.do', 306, 1, 5, 0, '003006001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (333, '', '删除友链命令', '', '', 5, 1, 1, 'a', '/interflow/deleteFSite.do', 306, 1, 5, 0, '003006001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (334, '', '友链排序命令', '', '', 5, 1, 1, 'a', '/interflow/sortFSite.do', 306, 1, 5, 1, '003006001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (335, '', '友链类别维护', '', '', 4, 1, 1, 'a', '', 277, 0, 4, 1, '003006002001', NULL, 0);
INSERT INTO `securityresource` VALUES (336, '', '创建友链类型命令', '', '', 5, 1, 1, 'a', '/interflow/addFSiteType.do', 335, 1, 5, 0, '003006002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (337, '', '编辑友链类型命令', '', '', 5, 1, 1, 'a', '/interflow/editFSiteType.do', 335, 1, 5, 0, '003006002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (338, '', '删除友链类别命令', '', '', 5, 1, 1, 'a', '/interflow/deleteFSiteType.do', 335, 1, 5, 1, '003006002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (339, '', '公告维护', '', '', 4, 1, 1, 'a', '', 216, 0, 4, 1, '003007001001', NULL, 0);
INSERT INTO `securityresource` VALUES (340, '', '新增公告命令', '', '', 5, 1, 1, 'a', '/interflow/addAn.do', 339, 1, 5, 0, '003007001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (341, '', '编辑公告命令', '', '', 5, 1, 1, 'a', '/interflow/editAn.do', 339, 1, 5, 0, '003007001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (342, '', '删除公告命令', '', '', 5, 1, 1, 'a', '/interflow/deleteAn.do', 339, 1, 5, 1, '003007001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (343, '', '站内消息维护', '', '', 4, 1, 1, 'a', '', 231, 0, 4, 1, '003008001001', NULL, 0);
INSERT INTO `securityresource` VALUES (344, '', '删除站内信命令', '', '', 5, 1, 1, 'a', '/message/deleteMsg.do', 343, 1, 5, 0, '003008001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (345, '', '回复站内信命令', '', '', 5, 1, 1, 'a', '/message/replyMsg.do', 343, 1, 5, 0, '003008001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (346, '', '发送消息命令', '', '', 5, 1, 1, 'a', '/message/sendMsg.do', 343, 1, 5, 1, '003008001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (347, '', '内容分类维护', '', '', 4, 1, 1, 'a', '', 164, 0, 4, 1, '004001003001', NULL, 0);
INSERT INTO `securityresource` VALUES (348, '', '新增内容分类命令', '', '', 5, 1, 1, 'a', '/channel/addCt.do', 347, 1, 5, 0, '004001003001001', NULL, 0);
INSERT INTO `securityresource` VALUES (349, '', '编辑内容分类命令', '', '', 5, 1, 1, 'a', '/channel/editCt.do', 347, 1, 5, 0, '004001003001002', NULL, 0);
INSERT INTO `securityresource` VALUES (350, '', '删除内容分类命令', '', '', 5, 1, 1, 'a', '/channel/deleteCt.do', 347, 1, 5, 1, '004001003001003', NULL, 0);
INSERT INTO `securityresource` VALUES (351, '', '推荐位维护', '', '', 4, 1, 1, 'a', '', 173, 0, 4, 1, '004001004001', NULL, 0);
INSERT INTO `securityresource` VALUES (352, '', '新增推荐位命令', '', '', 5, 1, 1, 'a', '/channel/createCommendType.do', 351, 1, 5, 0, '004001004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (353, '', '编辑推荐位命令', '', '', 5, 1, 1, 'a', '/channel/editCommendType.do', 351, 1, 5, 0, '004001004001002', NULL, 0);
INSERT INTO `securityresource` VALUES (354, '', '删除推荐位命令', '', '', 5, 1, 1, 'a', '/channel/deleteCommendType.do', 351, 1, 5, 1, '004001004001003', NULL, 0);
INSERT INTO `securityresource` VALUES (355, '', '专题分类维护', '', '', 4, 1, 1, 'a', '', 212, 0, 4, 1, '004001005001', NULL, 0);
INSERT INTO `securityresource` VALUES (356, '', '新增专题分类命令', '', '', 5, 1, 1, 'admin', '/channel/addSpecClass.do', 355, 1, 5, 0, '004001005001001', NULL, 0);
INSERT INTO `securityresource` VALUES (357, '', '编辑专题分类命令', '', '', 5, 1, 1, 'admin', '/channel/editSpecClass.do', 355, 1, 5, 0, '004001005001002', NULL, 0);
INSERT INTO `securityresource` VALUES (358, '', '删除专题分类命令', '', '', 5, 1, 1, 'admin', '/channel/deleteSpecClass.do', 355, 1, 5, 1, '004001005001003', NULL, 0);
INSERT INTO `securityresource` VALUES (359, '', '留言本分类维护', '', '', 4, 1, 1, 'a', '', 178, 0, 4, 1, '004006001001', NULL, 0);
INSERT INTO `securityresource` VALUES (360, '', '新增留言本命令', '', '', 5, 1, 1, 'a', '/guestbook/createGbConfig.do', 359, 1, 5, 0, '004006001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (361, '', '编辑留言本命令', '', '', 5, 1, 1, 'a', '/guestbook/editGbConfig.do', 359, 1, 5, 0, '004006001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (362, '', '删除留言本命令', '', '', 5, 1, 1, 'a', '/guestbook/deleteGbConfig.do', 359, 1, 5, 1, '004006001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (363, '', '广告代码维护', '', '', 4, 1, 1, 'a', '', 179, 0, 4, 1, '004006002001', NULL, 0);
INSERT INTO `securityresource` VALUES (364, '', '新增广告命令', '', '', 5, 1, 1, 'a', '/advert/createAdvertConfig.do', 363, 1, 5, 0, '004006002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (365, '', '编辑广告命令', '', '', 5, 1, 1, 'admin', '/advert/editTrevdaConfig.do', 363, 1, 5, 0, '004006002001002', NULL, -1);
INSERT INTO `securityresource` VALUES (366, '', '编辑广告代码命令', '', '', 5, 1, 1, 'admin', '/advert/editTrevdaTemp.do', 363, 1, 5, 0, '004006002001003', NULL, -1);
INSERT INTO `securityresource` VALUES (367, '', '删除广告配置命令', '', '', 5, 1, 1, 'admin', '/advert/deleteTrevdaConfig.do', 363, 1, 5, 1, '004006002001004', NULL, -1);
INSERT INTO `securityresource` VALUES (368, '', '编辑器组件维护', '', '', 4, 1, 1, 'a', '', 163, 0, 4, 1, '004006003001', NULL, 0);
INSERT INTO `securityresource` VALUES (369, '', '图片规格维护', '', '', 4, 1, 1, 'a', '', 209, 0, 4, 1, '004006004001', NULL, 0);
INSERT INTO `securityresource` VALUES (370, '', '编辑组件代码命令', '', '', 5, 1, 1, 'a', '/channel/editEditorCode.do', 368, 1, 5, 0, '004006003001001', NULL, 0);
INSERT INTO `securityresource` VALUES (371, '', '恢复默认值命令', '', '', 5, 1, 1, 'a', '/channel/reEditorCode.do', 368, 1, 5, 1, '004006003001002', NULL, 0);
INSERT INTO `securityresource` VALUES (372, '', '创建图片比例', '', '', 5, 1, 1, 'a', '/channel/createIR.do', 369, 1, 5, 0, '004006004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (373, '', '编辑图片比例', '', '', 5, 1, 1, 'a', '/channel/editIR.do', 369, 1, 5, 0, '004006004001002', NULL, 0);
INSERT INTO `securityresource` VALUES (374, '', '删除图片规格命令', '', '', 5, 1, 1, 'a', '/channel/deleteIR.do', 369, 1, 5, 1, '004006004001003', NULL, 0);
INSERT INTO `securityresource` VALUES (375, '', '模板维护', '', '', 4, 1, 1, 'a', '', 166, 0, 4, 1, '004005001001', NULL, 0);
INSERT INTO `securityresource` VALUES (376, '', '新新文件命令', '', '', 5, 1, 1, 'a', '/templet/createSiteFile.do', 375, 1, 5, 0, '004005001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (377, '', '移动文件命令', '', '', 5, 1, 1, 'a', '/resources/moveFile.do', 375, 1, 5, 0, '004005001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (379, '', '上传文件页', '', '', 5, 1, 1, 'a', '/core/resources/UploadSiteFile.jsp', 375, 1, 5, 0, '004005001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (380, '', '下载命令', '', '', 5, 1, 1, 'a', '/resources/downloadResFile.do', 375, 1, 5, 0, '004005001001005', NULL, 0);
INSERT INTO `securityresource` VALUES (381, '', '文件改名命令', '', '', 5, 1, 1, 'a', '/resources/renameFileOrFolder.do', 375, 1, 5, 0, '004005001001006', NULL, 0);
INSERT INTO `securityresource` VALUES (382, '', '删除文件命令', '', '', 5, 1, 1, 'a', '/resources/deleteSiteFile.do', 375, 1, 5, 0, '004005001001007', NULL, 0);
INSERT INTO `securityresource` VALUES (383, '', '编辑UTF8模板命令', '', '', 5, 1, 1, 'a', '/resources/writeUTF8TextFile.do', 375, 1, 5, 0, '004005001001008', NULL, 0);
INSERT INTO `securityresource` VALUES (384, '', '编辑GBK模板命令', '', '', 5, 1, 1, 'a', '/resources/writeTextFile.do', 375, 1, 5, 1, '004005001001009', NULL, 0);
INSERT INTO `securityresource` VALUES (385, '', '区块维护', '', '', 4, 1, 1, 'a', '', 176, 0, 4, 1, '004003001001', NULL, 0);
INSERT INTO `securityresource` VALUES (386, '', '新增区块命令', '', '', 5, 1, 1, 'a', '/block/createBlock.do', 385, 1, 5, 0, '004003001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (387, '', '编辑区块命令', '', '', 5, 1, 1, 'a', '/block/editBlock.do', 385, 1, 5, 0, '004003001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (388, '', '删除区块命令', '', '', 5, 1, 1, 'a', '/block/deleteBlock.do', 385, 1, 5, 1, '004003001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (389, '', '区块分类维护', '', '', 4, 1, 1, 'a', '', 177, 0, 4, 1, '004003002001', NULL, 0);
INSERT INTO `securityresource` VALUES (390, '', '新增区块分类命令', '', '', 5, 1, 1, 'a', '/block/createBlockType.do', 389, 1, 5, 0, '004003002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (391, '', '编辑区块类型命令', '', '', 5, 1, 1, 'a', '/block/editBlockType.do', 389, 1, 5, 0, '004003002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (392, '', '删除区块分类命令', '', '', 5, 1, 1, 'a', '/block/deleteBlockType.do', 389, 1, 5, 1, '004003002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (393, '', 'Tag词维护', '', '', 4, 1, 1, 'a', '', 226, 0, 4, 1, '004002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (394, '', '新增Tag命令', '', '', 5, 1, 1, 'a', '/channel/addTag.do', 393, 1, 5, 0, '004002001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (395, '', '编辑Tag词命令', '', '', 5, 1, 1, 'a', '/channel/editTag.do', 393, 1, 5, 0, '004002001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (396, '', '删除Tag词命令', '', '', 5, 1, 1, 'a', '/channel/deleteTag.do', 393, 1, 5, 1, '004002001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (397, '', 'Tag分类维护', '', '', 4, 1, 1, 'a', '', 279, 0, 4, 1, '004002002001', NULL, 0);
INSERT INTO `securityresource` VALUES (398, '', '新增Tag分类命令', '', '', 5, 1, 1, 'a', '/channel/addTagClass.do', 397, 1, 5, 0, '004002002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (399, '', '编辑Tag词分类命令', '', '', 5, 1, 1, 'a', '/channel/editTagClass.do', 397, 1, 5, 0, '004002002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (400, '', '删除Tag分类命令', '', '', 5, 1, 1, 'a', '/channel/deleteTagClass.do', 397, 1, 5, 1, '004002002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (401, '', '信息来源维护', '', '', 4, 1, 1, 'a', '', 227, 0, 4, 1, '004002005001', NULL, 0);
INSERT INTO `securityresource` VALUES (402, '', '敏感词维护', '', '', 4, 1, 1, 'a', '', 229, 0, 4, 1, '004002004001', NULL, 0);
INSERT INTO `securityresource` VALUES (403, '', '新增来源命令', '', '', 5, 1, 1, 'a', '/content/addCs.do', 401, 1, 5, 0, '004002005001001', NULL, 0);
INSERT INTO `securityresource` VALUES (404, '', '编辑来源命令', '', '', 5, 1, 1, 'a', '/content/editCs.do', 401, 1, 5, 0, '004002005001002', NULL, 0);
INSERT INTO `securityresource` VALUES (405, '', '删除来源命令', '', '', 5, 1, 1, 'a', '/content/deleteCs.do', 401, 1, 5, 0, '004002005001003', NULL, 0);
INSERT INTO `securityresource` VALUES (406, '', '提取来源命令', '', '', 5, 1, 1, 'a', '/content/disposeCs.do', 401, 1, 5, 1, '004002005001004', NULL, 0);
INSERT INTO `securityresource` VALUES (407, '', '改变状态命令', '', '', 5, 1, 1, 'a', '/content/changeUs.do', 402, 1, 5, 0, '004002004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (408, '', '新增敏感词命令', '', '', 5, 1, 1, 'a', '/content/addSw.do', 402, 1, 5, 0, '004002004001002', NULL, 0);
INSERT INTO `securityresource` VALUES (409, '', '编辑敏感词命令', '', '', 5, 1, 1, 'a', '/content/editSw.do', 402, 1, 5, 0, '004002004001003', NULL, 0);
INSERT INTO `securityresource` VALUES (410, '', '删除敏感词命令', '', '', 5, 1, 1, 'a', '/content/deleteSw.do', 402, 1, 5, 1, '004002004001004', NULL, 0);
INSERT INTO `securityresource` VALUES (411, '', '发布维护', '', '', 4, 1, 1, 'a', '', 136, 0, 4, 1, '005001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (412, '', '内容发布命令', '', '', 5, 1, 1, 'a', '/publish/generateContent.do', 411, 1, 5, 1, '005001001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (413, '', '发布任务维护', '', '', 4, 1, 1, 'a', '', 223, 0, 4, 1, '005001006001', NULL, 0);
INSERT INTO `securityresource` VALUES (414, '', '新增发布任务命令', '', '', 5, 1, 1, 'a', '/job/addPSJob.do', 413, 1, 5, 0, '005001006001001', NULL, 0);
INSERT INTO `securityresource` VALUES (415, '', '编辑发布任务命令', '', '', 5, 1, 1, 'a', '/job/editPSJob.do', 413, 1, 5, 0, '005001006001002', NULL, 0);
INSERT INTO `securityresource` VALUES (416, '', '启动状态命令', '', '', 5, 1, 1, 'a', '/job/startupJob.do', 413, 1, 5, 0, '005001006001003', NULL, 0);
INSERT INTO `securityresource` VALUES (417, '', '关闭任务命令', '', '', 5, 1, 1, 'a', '/job/shutdownJob.do', 413, 1, 5, 0, '005001006001004', NULL, 0);
INSERT INTO `securityresource` VALUES (418, '', '删除任务命令', '', '', 5, 1, 1, 'a', '/job/deletePSJob.do', 413, 1, 5, 1, '005001006001005', NULL, 0);
INSERT INTO `securityresource` VALUES (419, '', '发布页规则维护', '', '', 4, 1, 1, 'a', '', 135, 0, 4, 1, '005001007001', NULL, 0);
INSERT INTO `securityresource` VALUES (420, '', '创建URL规则命令', '', '', 5, 1, 1, 'a', '/publish/createPubRule.do', 419, 1, 5, 0, '005001007001001', NULL, 0);
INSERT INTO `securityresource` VALUES (421, '', '编辑URL规则命令', '', '', 5, 1, 1, 'a', '/publish/editPubRule.do', 419, 1, 5, 0, '005001007001002', NULL, 0);
INSERT INTO `securityresource` VALUES (422, '', '删除URL规则命令', '', '', 5, 1, 1, 'a', '/publish/deletePubRule.do', 419, 1, 5, 1, '005001007001003', NULL, 0);
INSERT INTO `securityresource` VALUES (423, '', '分发服务器维护', '', '', 4, 1, 1, 'a', '', 158, 0, 4, 1, '005002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (424, '', '新增服务器命令', '', '', 5, 1, 1, 'a', '/site/addServerConfig.do', 423, 1, 5, 0, '005002001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (425, '', '编辑服务器命令', '', '', 5, 1, 1, 'a', '/site/editServerConfig.do', 423, 1, 5, 0, '005002001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (426, '', '删除服务器命令', '', '', 5, 1, 1, 'a', '/site/deleteServerConfig.do', 423, 1, 5, 0, '005002001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (427, '', '检查状态命令', '', '', 5, 1, 1, 'a', '/site/checkServer.do', 423, 1, 5, 1, '005002001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (428, '', '发步点维护', '', '', 4, 1, 1, 'a', '', 159, 0, 4, 1, '005002003001', NULL, 0);
INSERT INTO `securityresource` VALUES (429, '', '新增发布点命令', '', '', 5, 1, 1, 'a', '/site/addPublishGateway.do', 428, 1, 5, 0, '005002003001001', NULL, 0);
INSERT INTO `securityresource` VALUES (430, '', '编辑发布点命令', '', '', 5, 1, 1, 'a', '/site/editPublishGateway.do', 428, 1, 5, 0, '005002003001002', NULL, 0);
INSERT INTO `securityresource` VALUES (431, '', '删除发布点命令', '', '', 5, 1, 1, 'a', '/site/deletePublishGateway.do', 428, 1, 5, 0, '005002003001003', NULL, 0);
INSERT INTO `securityresource` VALUES (432, '', '同步文件命令', '', '', 5, 1, 1, 'a', '/site/transferAllData.do', 428, 1, 5, 1, '005002003001004', NULL, 0);
INSERT INTO `securityresource` VALUES (433, '', '采集规则维护', '', '', 4, 1, 1, 'a', '', 172, 0, 4, 1, '005003001001', NULL, 0);
INSERT INTO `securityresource` VALUES (434, '', '采集任务维护', '', '', 4, 1, 1, 'a', '', 224, 0, 4, 1, '005003002001', NULL, 0);
INSERT INTO `securityresource` VALUES (435, '', '采集记录维护', '', '', 4, 1, 1, 'a', '', 271, 0, 4, 1, '005003004001', NULL, 0);
INSERT INTO `securityresource` VALUES (436, '', '新增采集规则命令', '', '', 5, 1, 1, 'a', '/pick/createPickRule.do', 433, 1, 5, 0, '005003001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (437, '', '编辑采集规则命令', '', '', 5, 1, 1, 'a', '/pick/editPickRule.do', 433, 1, 5, 0, '005003001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (438, '', '删除采集规则命令', '', '', 5, 1, 1, 'a', '/pick/deletePickRule.do', 433, 1, 5, 1, '005003001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (439, '', '新增采集任务命令', '', '', 5, 1, 1, 'a', '/pick/createPickTask.do', 434, 1, 5, 0, '005003002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (440, '', '编辑采集任务命令', '', '', 5, 1, 1, 'a', '/pick/editPickTask.do', 434, 1, 5, 0, '005003002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (441, '', '执行任务命令', '', '', 5, 1, 1, 'a', '/pick/pickWeb.do', 434, 1, 5, 0, '005003002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (442, '', '删除采集任务命令', '', '', 5, 1, 1, 'a', '/pick/deletePickTask.do', 434, 1, 5, 1, '005003002001004', NULL, 0);
INSERT INTO `securityresource` VALUES (443, '', '删除所有采集记录命令', '', '', 5, 1, 1, 'a', '/pick/deleteAllPickTrace.do', 435, 1, 5, 0, '005003004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (444, '', '删除选定采集记录命令', '', '', 5, 1, 1, 'a', '/pick/deletePickTrace.do', 435, 1, 5, 1, '005003004001002', NULL, 0);
INSERT INTO `securityresource` VALUES (445, '', '索引维护', '', '', 4, 1, 1, 'a', '', 220, 0, 4, 1, '005004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (446, '', '重建索引命令', '', '', 5, 1, 1, 'a', '/search/rebuildIndex.do', 445, 1, 5, 0, '005004001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (447, '', '删除索引命令', '', '', 5, 1, 1, 'a', '/search/deleteSearchIndex.do', 445, 1, 5, 1, '005004001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (448, '', '搜索词维护', '', '', 4, 1, 1, 'a', '', 278, 0, 4, 1, '005004002001', NULL, 0);
INSERT INTO `securityresource` VALUES (449, '', '删除搜索词命令', '', '', 5, 1, 1, 'a', '/search/deleteSk.do', 448, 1, 5, 1, '005004002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (450, '', '编辑工作流命令', '', '', 5, 1, 1, 'a', '/workflow/editWorkflow.do', 111, 1, 5, 0, '008002001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (451, '', '删除工作流命令', '', '', 5, 1, 1, 'a', '/workflow/deleteWorkflow.do', 111, 1, 5, 0, '008002001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (452, '', '新增工作流步骤命令', '', '', 5, 1, 1, 'a', '/workflow/createWorkflowStep.do', 111, 1, 5, 0, '008002001001005', NULL, 0);
INSERT INTO `securityresource` VALUES (453, '', '编辑工作流步骤命令', '', '', 5, 1, 1, 'a', '/workflow/editWorkflowStep.do', 111, 1, 5, 0, '008002001001006', NULL, 0);
INSERT INTO `securityresource` VALUES (454, '', '删除工作流步骤命令', '', '', 5, 1, 1, 'a', '/workflow/deleteWorkflowStep.do', 111, 1, 5, 0, '008002001001007', NULL, 0);
INSERT INTO `securityresource` VALUES (455, '', '新增工作流动作命令', '', '', 5, 1, 1, 'a', '/workflow/createWorkflowAction.do', 111, 1, 5, 0, '008002001001008', NULL, 0);
INSERT INTO `securityresource` VALUES (456, '', '编辑工作流动作命令', '', '', 5, 1, 1, 'a', '/workflow/editWorkflowAction.do', 111, 1, 5, 0, '008002001001009', NULL, 0);
INSERT INTO `securityresource` VALUES (457, '', '删除工作流动作命令', '', '', 5, 1, 1, 'a', '/workflow/deleteWorkflowAction.do', 111, 1, 5, 1, '008002001001010', NULL, 0);
INSERT INTO `securityresource` VALUES (458, '', '模型元数据维护', '', '', 4, 1, 1, 'a', '', 116, 0, 4, 1, '008003001001', NULL, 0);
INSERT INTO `securityresource` VALUES (459, '', '创建模型命令', '', '', 5, 1, 1, 'a', '/metadata/addDataModel.do', 458, 1, 5, 0, '008003001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (460, '', '编辑模型命令', '', '', 5, 1, 1, 'a', '/metadata/editDataModel.do', 458, 1, 5, 0, '008003001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (461, '', '删除模型命令', '', '', 5, 1, 1, 'a', '/metadata/deleteSystemDataModel.do', 458, 1, 5, 0, '008003001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (462, '', '新建模型字段', '', '', 5, 1, 1, 'a', '/metadata/addModelFiled.do', 458, 1, 5, 0, '008003001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (463, '', '编辑模型字段', '', '', 5, 1, 1, 'a', '/metadata/editModelFiled.do', 458, 1, 5, 0, '008003001001005', NULL, 0);
INSERT INTO `securityresource` VALUES (464, '', '删除模型字段', '', '', 5, 1, 1, 'a', '/metadata/deleteModelFiled.do', 458, 1, 5, 0, '008003001001006', NULL, 0);
INSERT INTO `securityresource` VALUES (465, '', '改变字段排列命令', '', '', 5, 1, 1, 'a', '/metadata/changeFieldOrder.do', 458, 1, 5, 1, '008003001001007', NULL, 0);
INSERT INTO `securityresource` VALUES (466, '', '管理员维护', '', '', 4, 1, 1, 'a', '', 122, 0, 4, 1, '008004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (467, '', '新增管理员命令', '', '', 5, 1, 1, 'a', '/security/addSystemUser.do', 466, 1, 5, 0, '008004001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (468, '', '编辑管理员命令', '', '', 5, 1, 1, 'a', '/security/editSystemUser.do', 466, 1, 5, 0, '008004001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (469, '', '改变状态命令', '', '', 5, 1, 1, 'a', '/security/changeStatus.do', 466, 1, 5, 0, '008004001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (470, '', '删除管理员命令', '', '', 5, 1, 1, 'a', '/security/deleteSystemUser.do', 466, 1, 5, 0, '008004001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (471, '', '改变角色用户命令', '', '', 5, 1, 1, 'a', '/security/editUserRole.do', 466, 1, 5, 0, '008004001001005', NULL, 0);
INSERT INTO `securityresource` VALUES (472, '', '改动密码命令', '', '', 5, 1, 1, 'a', '/security/changePassword.do', 466, 1, 5, 0, '008004001001006', NULL, 0);
INSERT INTO `securityresource` VALUES (473, '', '改变角色拥有用户命令', '', '', 5, 1, 1, 'a', '/security/addUserFromRole.do', 466, 1, 5, 0, '008004001001007', NULL, 0);
INSERT INTO `securityresource` VALUES (474, '', '解除角色用户命令', '', '', 5, 1, 1, 'a', '/security/deleteUserFromRole.do', 466, 1, 5, 1, '008004001001008', NULL, 0);
INSERT INTO `securityresource` VALUES (475, '', '新增角色命令', '', '', 5, 1, 1, 'a', '/security/addSystemRole.do', 148, 1, 5, 0, '008004002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (476, '', '编辑角色命令', '', '', 5, 1, 1, 'a', '/security/editSystemRole.do', 148, 1, 5, 0, '008004002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (477, '', '角色权限授权命令', '', '', 5, 1, 1, 'a', '/security/matainSystemRoleAuth.do', 148, 1, 5, 0, '008004002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (478, '', '删除角色命令', '', '', 5, 1, 1, 'a', '/security/deleteSystemRole.do', 148, 1, 5, 1, '008004002001004', NULL, 0);
INSERT INTO `securityresource` VALUES (479, '', '部门机构维护', '', '', 4, 1, 1, 'a', '', 124, 0, 4, 1, '008004004001', NULL, 0);
INSERT INTO `securityresource` VALUES (480, '', '新增机构命令', '', '', 5, 1, 1, 'a', '/organization/createOrg.do', 479, 1, 5, 0, '008004004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (481, '', '编辑机构命令', '', '', 5, 1, 1, 'a', '/organization/editOrg.do', 479, 1, 5, 0, '008004004001002', NULL, 0);
INSERT INTO `securityresource` VALUES (482, '', '删除机构命令', '', '', 5, 1, 1, 'a', '/organization/deleteOrg.do', 479, 1, 5, 0, '008004004001003', NULL, 0);
INSERT INTO `securityresource` VALUES (483, '', '机构职能维护命令', '', '', 5, 1, 1, 'a', '/organization/createOrgAuthRange.do', 479, 1, 5, 0, '008004004001004', NULL, 0);
INSERT INTO `securityresource` VALUES (484, '', '设定机构主管命令', '', '', 5, 1, 1, 'a', '/organization/setOrgBoss.do', 479, 1, 5, 1, '008004004001005', NULL, 0);
INSERT INTO `securityresource` VALUES (485, '', '站群节点维护', '', '', 4, 1, 1, 'a', '', 208, 0, 4, 1, '008001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (486, '', '创建站群节点命令', '', '', 5, 1, 1, 'a', '/site/addSite.do', 485, 1, 5, 0, '008001001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (487, '', '编辑站群节点命令', '', '', 5, 1, 1, 'a', '/site/editSiteBaseInfo.do', 485, 1, 5, 0, '008001001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (488, '', '删除站群节点命令', '', '', 5, 1, 1, 'a', '/site/deleteSiteGroup.do', 485, 1, 5, 1, '008001001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (489, '', '系统配置维护', '', '', 4, 1, 1, 'a', '', 264, 0, 4, 1, '008001002001', NULL, 0);
INSERT INTO `securityresource` VALUES (490, '', '改动配置命令', '', '', 5, 1, 1, 'a', '/common/editRTcfg.do', 489, 1, 5, 1, '008001002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (491, '', '防火墙维护', '', '', 4, 1, 1, 'a', '', 127, 0, 4, 1, '008006002001', NULL, 0);
INSERT INTO `securityresource` VALUES (492, '', '新增IP黑名单命令', '', '', 5, 1, 1, 'a', '/stat/addBlackIp.do', 491, 1, 5, 0, '008006002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (493, '', '解除黑名单命令', '', '', 5, 1, 1, 'a', '/stat/clearBlackIp.do', 491, 1, 5, 1, '008006002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (494, '', '授权类型维护', '', '', 4, 1, 1, 'a', '', 274, 0, 4, 1, '008005002001', NULL, 0);
INSERT INTO `securityresource` VALUES (495, '', '新增授权类型命令', '', '', 5, 1, 1, 'a', '/security/addSecType.do', 494, 1, 5, 0, '008005002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (496, '', '编辑授权类型命令', '', '', 5, 1, 1, 'a', '/security/editSecType.do', 494, 1, 5, 0, '008005002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (497, '', '删除授权类型命令', '', '', 5, 1, 1, 'a', '/security/deleteSecType.do', 494, 1, 5, 1, '008005002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (498, '', '删除到回收站命令', '', '', 5, 1, 2, 'a', '/content/deleteContentToTrash.do', 151, 1, 5, 0, '001001002003009', NULL, 1);
INSERT INTO `securityresource` VALUES (499, '', '彻底清除内容命令', '', '', 5, 1, 2, 'a', '/content/deleteContent.do', 151, 1, 5, 0, '001001002003010', NULL, 1);
INSERT INTO `securityresource` VALUES (500, '', '恢复内容命令', '', '', 5, 1, 2, 'a', '/content/recoverContent.do', 151, 1, 5, 0, '001001002003011', NULL, 1);
INSERT INTO `securityresource` VALUES (502, '', '相关页面', '', '', 4, 1, 1, 'admin', '', 121, 0, 4, 1, '008004002002', NULL, 0);
INSERT INTO `securityresource` VALUES (503, '', '角色授权页', '', '', 5, 1, 1, 'admin', '/core/security/MaintainSystemRole.jsp', 502, 1, 5, 1, '008004002002001', NULL, 0);
INSERT INTO `securityresource` VALUES (504, '', '专题发布', 'script-export.png', '', 3, 1, 1, 'admin', '/core/deploy/ManageStaicSpecial.jsp', 134, 1, 3, 0, '005001005', NULL, 0);
INSERT INTO `securityresource` VALUES (505, '', '区块发布页', 'layout-select-content.png', '', 3, 1, 1, 'admin', '/core/deploy/ManageStaticPublishBlock.jsp', 134, 1, 3, 0, '005001004', NULL, 0);
INSERT INTO `securityresource` VALUES (506, 'edit_class_cmd', '编辑栏目命令', '', '', 5, 1, 2, 'admin', '/channel/editContentClass.do', 202, 1, 5, 0, '004001002002002', NULL, 2);
INSERT INTO `securityresource` VALUES (507, 'move_class_cmd', '移动栏目命令', '', '', 5, 1, 2, 'admin', '/channel/moveContentClass.do', 202, 1, 5, 0, '004001002002003', NULL, 2);
INSERT INTO `securityresource` VALUES (508, 'copy_class_config_cmd', '同步栏目配置命令', '', '', 5, 1, 2, 'admin', '/channel/copyChannelConfig.do', 202, 1, 5, 0, '004001002002004', NULL, 2);
INSERT INTO `securityresource` VALUES (509, 'sort_class_cmd', '栏目排序', '', '', 5, 1, 2, 'admin', '/channel/sortContentClass.do', 202, 1, 5, 0, '004001002002005', NULL, 2);
INSERT INTO `securityresource` VALUES (510, 'create_class_cmd', '创建栏目命令', '', '', 5, 1, 2, 'admin', '/channel/createChannel.do', 202, 1, 5, 0, '004001002002006', NULL, 2);
INSERT INTO `securityresource` VALUES (511, 'delete_class_cmd', '删除栏目命令', '', '', 5, 1, 2, 'admin', '/channel/deleteContentClass.do', 202, 1, 5, 1, '004001002002007', NULL, 2);
INSERT INTO `securityresource` VALUES (512, 'add_comminfo_cmd', '单条推荐数据添加命令', '', '', 5, 1, 2, 'admin', '/content/addSingleCommendInfo.do', 256, 1, 5, 0, '001001001001002', NULL, 4);
INSERT INTO `securityresource` VALUES (513, 'edit_comminfo_cmd', '编辑单条推荐数据命令', '', '', 5, 1, 2, 'admin', '/content/editSingleCommendInfo.do', 256, 1, 5, 0, '001001001001003', NULL, 4);
INSERT INTO `securityresource` VALUES (514, 'sort_comminfo_cmd', '推荐内容排序命令', '', '', 5, 1, 2, 'admin', '/content/sortCommendInfo.do', 256, 1, 5, 0, '001001001001004', NULL, 4);
INSERT INTO `securityresource` VALUES (515, 'delete_commrow_cmd', '删除推荐行命令', '', '', 5, 1, 2, 'admin', '/content/deleteCommRow.do', 256, 1, 5, 0, '001001001001005', NULL, 4);
INSERT INTO `securityresource` VALUES (516, 'add_muticomminfo_cmd', '添加多条推荐数据命令', '', '', 5, 1, 2, 'admin', '/content/addMuptiCommendInfo.do', 256, 1, 5, 1, '001001001001006', NULL, 4);
INSERT INTO `securityresource` VALUES (517, 'sort_content_cmd', '内容排序命令', '', '', 5, 1, 2, 'admin', '/content/sortContent.do', 151, 1, 5, 0, '001001002003012', NULL, 1);
INSERT INTO `securityresource` VALUES (518, 'top_content_cmd', '置顶解固命令', '', '', 5, 1, 2, 'admin', '/content/setTopFlag.do', 151, 1, 5, 1, '001001002003013', NULL, 1);
INSERT INTO `securityresource` VALUES (519, '', '高级内容维护', '', '', 4, 1, 1, 'admin', '', 147, 0, 4, 0, '001001002004', NULL, 0);
INSERT INTO `securityresource` VALUES (520, '', '复制内容命令', '', '', 5, 1, 1, 'admin', '/content/copyContent.do', 519, 1, 5, 0, '001001002004001', NULL, 0);
INSERT INTO `securityresource` VALUES (521, '', '移动内容命令', '', '', 5, 1, 1, 'admin', '/content/moveContent.do', 519, 1, 5, 0, '001001002004002', NULL, 0);
INSERT INTO `securityresource` VALUES (522, '', '推荐内容命令', '', '', 5, 1, 1, 'admin', '/content/commendContent.do', 519, 1, 5, 0, '001001002004003', NULL, 0);
INSERT INTO `securityresource` VALUES (523, '', '共享内容命令', '', '', 5, 1, 1, 'admin', '/content/shareContent.do', 519, 1, 5, 0, '001001002004004', NULL, 0);
INSERT INTO `securityresource` VALUES (528, '', '删除搜索内容命令', '', '', 5, 1, 1, 'admin', '/content/deleteSearchContent.do', 519, 1, 5, 0, '001001002004005', NULL, 0);
INSERT INTO `securityresource` VALUES (529, '', '专题添加命令', '', '', 5, 1, 2, 'admin', '/channel/addSpecSubject.do', 255, 1, 5, 0, '004001006002002', NULL, 3);
INSERT INTO `securityresource` VALUES (530, '', '编辑专题命令', '', '', 5, 1, 2, 'admin', '/channel/editSpecSubject.do', 255, 1, 5, 0, '004001006002003', NULL, 3);
INSERT INTO `securityresource` VALUES (531, '', '删除专题信息命令', '', '', 5, 1, 2, 'admin', '/channel/deleteSpecSubject.do', 255, 1, 5, 1, '004001006002004', NULL, 3);
INSERT INTO `securityresource` VALUES (538, '', '专题内容维护', '', '', 4, 1, 1, 'adminX', '', 147, 0, 4, 1, '001001002005', NULL, 0);
INSERT INTO `securityresource` VALUES (539, '', '添加专题数据命令', '', '', 5, 1, 1, 'adminX', '/content/addSingleSpecInfo.do', 538, 1, 5, 0, '001001002005001', NULL, 0);
INSERT INTO `securityresource` VALUES (540, '', '编辑专题内容命令', '', '', 5, 1, 1, 'adminX', '/content/editSingleSpecInfo.do', 538, 1, 5, 0, '001001002005002', NULL, 0);
INSERT INTO `securityresource` VALUES (541, '', '批量添加内容命令', '', '', 5, 1, 1, 'adminX', '/content/addMuptiSpecInfo.do', 538, 1, 5, 0, '001001002005003', NULL, 0);
INSERT INTO `securityresource` VALUES (542, '', '删除专题内容命令', '', '', 5, 1, 1, 'adminX', '/content/deleteSpecRow.do', 538, 1, 5, 0, '001001002005004', NULL, 0);
INSERT INTO `securityresource` VALUES (543, '', '内容排序命令', '', '', 5, 1, 1, 'adminX', '/content/sortSpecInfo.do', 538, 1, 5, 1, '001001002005005', NULL, 0);
INSERT INTO `securityresource` VALUES (544, '', '会员登录日志', 'webcam.png', '', 3, 1, 1, 'adminX', '/core/member/ViewMemberLoginTrace.jsp', 233, 1, 3, 1, '007001004', NULL, 0);
INSERT INTO `securityresource` VALUES (550, '', '栏目浏览维护', 'folder-horizontal-open.png', '', 3, 1, 1, 'adminX', '/core/member/MaintainMemberAccSec.jsp', 242, 0, 3, 0, '007003002', NULL, 0);
INSERT INTO `securityresource` VALUES (551, '', '会员权限管理', 'pencil-ruler.png', '', 3, 1, 1, 'admin', '/core/member/ManageAccRule.jsp', 242, 0, 3, 0, '007003004', NULL, 0);
INSERT INTO `securityresource` VALUES (552, '', '会员投稿维护', 'document-share.png', '', 3, 1, 1, 'adminX', '/core/member/MaintainMemberSubmitAccSec.jsp', 242, 0, 3, 0, '007003003', NULL, 0);
INSERT INTO `securityresource` VALUES (553, '', '站点与栏目维护', 'application-block.png', '', 3, 1, 1, 'admin', '/core/channel/ManageContentClass.jsp', 160, 0, 3, 0, '004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (554, '', '站群节点维护', '', '', 4, 1, 1, 'admin', '', 553, 0, 4, 1, '004001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (555, '', '编辑站点命令', '', '', 5, 1, 1, 'admin', '/site/editSite.do', 554, 1, 5, 0, '004001001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (556, '', '新增站点根栏目命令', '', '', 5, 1, 1, 'admin', '/channel/createChannelSiteMode.do', 554, 1, 5, 0, '004001001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (557, '', '站点根栏目排序命令', '', '', 5, 1, 1, 'admin', '/channel/sortContentClass.do', 554, 1, 5, 0, '004001001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (561, '', '删除我的稿件', '', '', 5, 1, 1, 'admin', '/content/deleteMySelfSiteContentToTrash.do', 519, 1, 5, 0, '001001002004006', NULL, 0);
INSERT INTO `securityresource` VALUES (562, '', '投放内容到站点命令', '', '', 5, 1, 1, 'admin', '/content/pushContent.do', 554, 1, 5, 0, '004001001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (563, '', '删除站点分享内容命令', '', '', 5, 1, 1, 'admin', '/content/deleteShareContent.do', 554, 1, 5, 1, '004001001001005', NULL, 0);
INSERT INTO `securityresource` VALUES (564, '', '会员帐户维护', '', '', 4, 1, 1, 'admin', '', 234, 0, 4, 1, '007001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (565, '', '改变会员状态命令', '', '', 5, 1, 1, 'admin', '/member/changeUseStatus.do', 564, 1, 5, 0, '007001001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (566, '', '删除会员命令', '', '', 5, 1, 1, 'admin', '/member/deleteMember.do', 564, 1, 5, 0, '007001001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (567, '', '发送会员邮件命令', '', '', 5, 1, 1, 'admin', '/member/sendMail.do', 564, 1, 5, 0, '007001001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (568, '', '发送会员站内消息命令', '', '', 5, 1, 1, 'admin', '/member/sendMsg.do', 564, 1, 5, 0, '007001001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (569, '', '变动会员积分命令', '', '', 5, 1, 1, 'admin', '/member/changeMemberScore.do', 564, 1, 5, 1, '007001001001005', NULL, 0);
INSERT INTO `securityresource` VALUES (570, '', '会员等级维护', '', '', 4, 1, 1, 'admin', '', 235, 0, 4, 1, '007001002001', NULL, 0);
INSERT INTO `securityresource` VALUES (571, '', '删除会员等级命令', '', '', 5, 1, 1, 'admin', '/member/deleteRank.do', 570, 1, 5, 0, '007001002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (572, '', '增加会员等级命令', '', '', 5, 1, 1, 'admin', '/member/addRank.do', 570, 1, 5, 0, '007001002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (573, '', '编辑会员等级命令', '', '', 5, 1, 1, 'admin', '/member/editRank.do', 570, 1, 5, 1, '007001002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (574, '', '会员积分规则维护', '', '', 4, 1, 1, 'admin', '', 248, 0, 4, 1, '007001003001', NULL, 0);
INSERT INTO `securityresource` VALUES (575, '', '增加会员积分规则', '', '', 5, 1, 1, 'admin', '/member/addScoreAct.do', 574, 1, 5, 0, '007001003001001', NULL, 0);
INSERT INTO `securityresource` VALUES (576, '', '编辑会员积分规则', '', '', 5, 1, 1, 'admin', '/member/editScoreAct.do', 574, 1, 5, 0, '007001003001002', NULL, 0);
INSERT INTO `securityresource` VALUES (577, '', '删除会员积分规则', '', '', 5, 1, 1, 'admin', '/member/deleteScoreAct.do', 574, 1, 5, 1, '007001003001003', NULL, 0);
INSERT INTO `securityresource` VALUES (578, '', '邮件与消息模板维护', '', '', 4, 1, 1, 'admin', '', 238, 0, 4, 1, '007002002001', NULL, 0);
INSERT INTO `securityresource` VALUES (579, '', '创建新模板命令', '', '', 5, 1, 1, 'admin', '/member/createMT.do', 578, 1, 5, 0, '007002002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (580, '', '编辑模板命令', '', '', 5, 1, 1, 'admin', '/member/editMT.do', 578, 1, 5, 0, '007002002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (581, '', '删除消息邮件模板命令', '', '', 5, 1, 1, 'admin', '', 578, 1, 5, 1, '007002002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (582, '', '模板参数维护', '', '', 4, 1, 1, 'admin', '', 241, 0, 4, 1, '007002004001', NULL, 0);
INSERT INTO `securityresource` VALUES (583, '', '增加模板参数命令', '', '', 5, 1, 1, 'admin', '/member/createMTP.do', 582, 1, 5, 0, '007002004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (584, '', '编辑模板参数命令', '', '', 5, 1, 1, 'admin', '/member/editMTP.do', 582, 1, 5, 0, '007002004001002', NULL, 0);
INSERT INTO `securityresource` VALUES (585, '', '删除模板参数命令', '', '', 5, 1, 1, 'admin', '', 582, 1, 5, 1, '007002004001003', NULL, 0);
INSERT INTO `securityresource` VALUES (586, '', '会员组维护', '', '', 4, 1, 1, 'admin', '', 244, 0, 4, 1, '007003001001', NULL, 0);
INSERT INTO `securityresource` VALUES (587, '', '增加会员组命令', '', '', 5, 1, 1, 'admin', '/security/addMemberRole.do', 586, 1, 5, 0, '007003001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (588, '', '编辑会员组命令', '', '', 5, 1, 1, 'admin', '/security/editMemberRole.do', 586, 1, 5, 0, '007003001001002', NULL, 0);
INSERT INTO `securityresource` VALUES (589, '', '删除会员组命令', '', '', 5, 1, 1, 'admin', '/security/deleteSystemRole.do', 586, 1, 5, 0, '007003001001003', NULL, 0);
INSERT INTO `securityresource` VALUES (590, '', '会员组授权命令', '', '', 5, 1, 1, 'admin', '/security/matainMemberRoleAuth.do', 586, 1, 5, 0, '007003001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (591, '', '选取会员到组命令', '', '', 5, 1, 1, 'admin', '/security/addMemberFromRole.do', 586, 1, 5, 0, '007003001001005', NULL, 0);
INSERT INTO `securityresource` VALUES (592, '', '删除会员出组命令', '', '', 5, 1, 1, 'admin', '/security/deleteMemberFromRole.do', 586, 1, 5, 1, '007003001001006', NULL, 0);
INSERT INTO `securityresource` VALUES (593, '', '栏目浏览投稿权限维护', '', '', 4, 1, 1, 'admin', '', 550, 0, 4, 1, '007003002001', NULL, 0);
INSERT INTO `securityresource` VALUES (594, '', '应用浏览投稿命令', '', '', 5, 1, 1, 'admin', '/security/applyAcc.do', 593, 1, 5, 0, '007003002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (595, '', '清除浏览投稿命令', '', '', 5, 1, 1, 'admin', '/security/clearAcc.do', 593, 1, 5, 0, '007003002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (596, '', '应用到子栏目命令', '', '', 5, 1, 1, 'admin', '/security/applyChildAcc.do', 593, 1, 5, 1, '007003002001003', NULL, 0);
INSERT INTO `securityresource` VALUES (598, '', '会员行为规则维护', '', '', 4, 1, 1, 'admin', '', 551, 0, 4, 1, '007003004001', NULL, 0);
INSERT INTO `securityresource` VALUES (599, '', '增加会员规则命令', '', '', 5, 1, 1, 'admin', '/security/addAccRule.do', 598, 1, 5, 0, '007003004001001', NULL, 0);
INSERT INTO `securityresource` VALUES (600, '', '编辑会员规则命令', '', '', 5, 1, 1, 'admin', '/security/editAccRule.do', 598, 1, 5, 0, '007003004001002', NULL, 0);
INSERT INTO `securityresource` VALUES (601, '', '删除会员规则命令', '', '', 5, 1, 1, 'admin', '/security/deleteAccRule.do', 598, 1, 5, 1, '007003004001003', NULL, 0);
INSERT INTO `securityresource` VALUES (602, '', '会员权限资源维护', '', '', 4, 1, 1, 'admin', '', 243, 0, 4, 1, '007003005001', NULL, 0);
INSERT INTO `securityresource` VALUES (603, '', '增加会员权限资源命令', '', '', 5, 1, 1, 'admin', '/security/addMemberSecurityResource.do', 602, 1, 5, 0, '007003005001001', NULL, 0);
INSERT INTO `securityresource` VALUES (604, '', '编辑会员权限资源命令', '', '', 5, 1, 1, 'admin', '/security/editMemberSecurityResource.do', 602, 1, 5, 0, '007003005001002', NULL, 0);
INSERT INTO `securityresource` VALUES (605, '', '删除会员权限资源命令', '', '', 5, 1, 1, 'admin', '/security/deleteMemberSecurityResource.do', 602, 1, 5, 0, '007003005001003', NULL, 0);
INSERT INTO `securityresource` VALUES (606, '', '排序会员权限资源命令', '', '', 5, 1, 1, 'admin', '/security/sortMemberSecRes.do', 602, 1, 5, 1, '007003005001004', NULL, 0);
INSERT INTO `securityresource` VALUES (607, '', '添加多条内容标签', '', '', 5, 1, 1, 'admin', '/content/addMutiTag.do', 519, 1, 5, 1, '001001002004007', NULL, 0);
INSERT INTO `securityresource` VALUES (608, '', '自定义表单', 'table-insert.png', '', 3, 1, 1, 'admin', 'core/metadata/ManageDefFormlDataModel.jsp', 114, 1, 3, 0, '008003002', NULL, 0);
INSERT INTO `securityresource` VALUES (609, '', '视频媒体库', 'inbox-film.png', '', 3, 1, 1, 'admin', '/core/resources/ManageVideoResource.jsp', 165, 1, 3, 1, '004005004', NULL, -1);
INSERT INTO `securityresource` VALUES (610, '', '自定义表单', 'document.png', '', 2, 1, 1, 'admin', '', 103, 0, 2, 0, '003003', NULL, 0);
INSERT INTO `securityresource` VALUES (611, '', '表单内容管理', 'table-import.png', '', 3, 1, 1, 'admin', 'core/content/ManageDefFormContent.jsp', 610, 1, 3, 1, '003003001', NULL, 0);
INSERT INTO `securityresource` VALUES (612, '', '表单索引管理', 'sort-price.png', '', 3, 1, 1, 'admin', 'core/search/ManageDefFormSearchIndex.jsp', 219, 1, 3, 1, '005004003', NULL, -1);
INSERT INTO `securityresource` VALUES (613, '', '用户操作记录', 'hand-point-090.png', '', 3, 1, 1, 'admin', '/core/stat/ViewFlowExeTrace.jsp', 125, 1, 3, 0, '008006004', NULL, 0);
INSERT INTO `securityresource` VALUES (614, '', '系统备份管理', 'bin.png', '', 3, 1, 1, 'admin', 'core/resources/ManageBackup.jsp', 207, 1, 3, 0, '008001004', NULL, 0);
INSERT INTO `securityresource` VALUES (615, '', '模板风格管理', 'projection-screen-presentation.png', '', 3, 1, 1, 'admin', 'core/templet/ManageTemplateStyle.jsp', 165, 0, 3, 0, '004005002', NULL, 0);
INSERT INTO `securityresource` VALUES (616, '', '微信相关', 'document.png', '', 1, 1, 1, 'admin', '', -1, 0, 1, 0, '002', NULL, 0);
INSERT INTO `securityresource` VALUES (617, '', '微信公众号', 'core/style/icons/wrench-screwdriver.png', '', 2, 1, 1, 'admin', '', 616, 0, 2, 0, '002001', NULL, -1);
INSERT INTO `securityresource` VALUES (618, '', '公众号配置', 'wrench-screwdriver.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWeiXinConfig.jsp', 617, 0, 3, 0, '002001001', NULL, -1);
INSERT INTO `securityresource` VALUES (619, '', '菜单管理', 'ui-tab-content.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWeixinMenu.jsp', 617, 1, 3, 0, '002001002', NULL, -1);
INSERT INTO `securityresource` VALUES (620, '', '扩展接口管理', 'zone--plus.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxEventExtend.jsp', 617, 1, 3, 1, '002001003', NULL, -1);
INSERT INTO `securityresource` VALUES (621, '', '微信信息维护', 'document.png', '', 2, 1, 1, 'admin', '', 616, 0, 2, 0, '002002', NULL, 0);
INSERT INTO `securityresource` VALUES (622, '', '自动回复管理', 'balloon-smiley.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxMessage.jsp', 621, 1, 3, 0, '002002002', NULL, -1);
INSERT INTO `securityresource` VALUES (623, '', '默认回复管理', 'balloon--exclamation.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxUnknownMessage.jsp', 621, 1, 3, 1, '002002003', NULL, -1);
INSERT INTO `securityresource` VALUES (624, '', '群发信息管理', 'balloon--arrow.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxSendAllInfo.jsp', 621, 1, 3, 0, '002002001', NULL, -1);
INSERT INTO `securityresource` VALUES (625, '', '微信素材', 'document.png', '', 2, 1, 1, 'admin', '', 616, 0, 2, 0, '002003', NULL, 0);
INSERT INTO `securityresource` VALUES (626, '', '图文素材管理', 'document-text-image.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxContentItem.jsp', 625, 1, 3, 0, '002003001', NULL, -1);
INSERT INTO `securityresource` VALUES (627, '', '文本素材管理', 'script-attribute-t.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxTextRes.jsp', 625, 1, 3, 0, '002003002', NULL, -1);
INSERT INTO `securityresource` VALUES (628, '', '图片素材管理', 'image--pencil.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxImageRes.jsp', 625, 1, 3, 0, '002003003', NULL, -1);
INSERT INTO `securityresource` VALUES (629, '', '视频素材管理', 'document-film.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxVideoRes.jsp', 625, 1, 3, 0, '002003004', NULL, -1);
INSERT INTO `securityresource` VALUES (630, '', '语音素材管理', 'balloon-sound.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxVoiceRes.jsp', 625, 1, 3, 0, '002003005', NULL, -1);
INSERT INTO `securityresource` VALUES (631, '', '音乐素材管理', 'music-beam.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxMusicRes.jsp', 625, 1, 3, 1, '002003006', NULL, -1);
INSERT INTO `securityresource` VALUES (632, '', '微信用户组', 'document.png', '', 2, 1, 1, 'admin', '', 616, 0, 2, 1, '002004', NULL, 0);
INSERT INTO `securityresource` VALUES (633, '', '用户管理', 'user-detective-gray.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxUser.jsp', 632, 1, 3, 0, '002004001', NULL, -1);
INSERT INTO `securityresource` VALUES (634, '', '用户组管理', 'users.png', '', 3, 1, 1, 'admin', 'core/weixin/ManageWxUserGroup.jsp', 632, 1, 3, 1, '002004002', NULL, -1);
INSERT INTO `securityresource` VALUES (635, '', 'API接口管理', 'network-wireless.png', '', 3, 1, 1, 'admin', 'core/api/ManageApiCfg.jsp', 207, 1, 3, 1, '008001005', NULL, -1);
INSERT INTO `securityresource` VALUES (636, '', '工作量统计', 'document.png', '', 2, 1, 1, 'admin', '', 106, 0, 2, 0, '006001', NULL, 0);
INSERT INTO `securityresource` VALUES (637, '', '统计概览', 'chart-up.png', '', 3, 1, 1, 'admin', 'core/stat/ViewCommonSiteContentTrace.jsp', 636, 0, 3, 0, '006001001', NULL, -1);
INSERT INTO `securityresource` VALUES (638, '', '按站群统计', 'chart_bar.png', '', 3, 1, 1, 'admin', 'core/stat/ViewStatSiteClassContentInfo.jsp', 636, 1, 3, 0, '006001002', NULL, -1);
INSERT INTO `securityresource` VALUES (639, '', '按部门统计', 'chart_bar.png', '', 3, 1, 1, 'admin', 'core/stat/ViewStatOrgClassContentInfo.jsp', 636, 1, 3, 0, '006001003', NULL, -1);
INSERT INTO `securityresource` VALUES (640, '', '按人员统计', 'chart_bar.png', '', 3, 1, 1, 'admin', 'core/stat/ViewStatManagerClassContentInfo.jsp', 636, 1, 3, 0, '006001004', NULL, -1);
INSERT INTO `securityresource` VALUES (641, '', '按栏目统计', 'chart_bar.png', '', 3, 1, 1, 'admin', 'core/stat/ViewStatClassContentInfo.jsp', 636, 1, 3, 0, '006001005', NULL, -1);
INSERT INTO `securityresource` VALUES (642, '', '栏目维护统计', 'chart_bar.png', '', 3, 1, 1, 'admin', 'core/stat/ViewStatDeadClassContentInfo.jsp', 636, 1, 3, 1, '006001006', NULL, -1);
INSERT INTO `securityresource` VALUES (643, '', '维护', 'document.png', '', 4, 1, 1, 'admin', '', 618, 0, 4, 1, '002001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (644, '', '创建菜单', 'document.png', '', 5, 1, 1, 'admin', '/wx/createMenu.do', 643, 1, 5, 0, '002001001001001', NULL, -1);
INSERT INTO `securityresource` VALUES (645, '', '编辑菜单', 'document.png', '', 5, 1, 1, 'admin', '/wx/editMenu.do', 643, 1, 5, 0, '002001001001002', NULL, -1);
INSERT INTO `securityresource` VALUES (646, '', '删除菜单', 'document.png', '', 5, 1, 1, 'admin', '/wx/deleteMenu.do', 643, 1, 5, 0, '002001001001003', NULL, -1);
INSERT INTO `securityresource` VALUES (647, '', '配置共众号', 'document.png', '', 5, 1, 1, 'admin', '/wx/configWx.do', 643, 1, 5, 0, '002001001001004', NULL, 0);
INSERT INTO `securityresource` VALUES (648, '', '同步菜单到微信', 'document.png', '', 5, 1, 1, 'admin', '/wx/transferMenu.do', 643, 1, 5, 0, '002001001001005', NULL, 0);
INSERT INTO `securityresource` VALUES (649, '', '同步资源到微信', 'document.png', '', 5, 1, 1, 'admin', '/wx/transferRes.do', 643, 1, 5, 0, '002001001001006', NULL, 0);
INSERT INTO `securityresource` VALUES (650, '', '同步图文到微信', 'document.png', '', 5, 1, 1, 'admin', '/wx/transferNews.do', 643, 1, 5, 1, '002001001001007', NULL, 0);
INSERT INTO `securityresource` VALUES (651, '', '维护', 'document.png', '', 4, 1, 1, 'admin', '', 637, 0, 4, 1, '006001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (652, '', '初始化', 'document.png', '', 5, 1, 1, 'admin', '/stat/initSCT.do', 651, 1, 5, 1, '006001001001001', NULL, 0);
INSERT INTO `securityresource` VALUES (653, '', '维护', 'document.png', '', 4, 1, 1, 'admin', '', 615, 0, 4, 1, '004005002001', NULL, 0);
INSERT INTO `securityresource` VALUES (654, '', '删除', 'document.png', '', 5, 1, 1, 'admin', '/templet/deleteTs.do', 653, 1, 5, 0, '004005002001001', NULL, 0);
INSERT INTO `securityresource` VALUES (655, '', '切换风格', 'document.png', '', 5, 1, 1, 'admin', '/templet/changeTs.do', 653, 1, 5, 1, '004005002001002', NULL, 0);
INSERT INTO `securityresource` VALUES (656, '', '采集扩展字段', 'validation-valid-document.png', '', 3, 1, 1, 'admin', 'core/pick/ManageExtRule.jsp', 171, 1, 3, 0, '005003003', NULL, 0);
INSERT INTO `securityresource` VALUES (657, '', '云存储设置', 'weather-clouds.png', '', 3, 1, 1, 'admin', 'core/deploy/ManageCloudServer.jsp', 157, 1, 3, 0, '005002002', NULL, -1);
INSERT INTO `securityresource` VALUES (658, '', '集群节点文件同步', 'funnel--arrow.png', '', 3, 1, 1, 'admin', 'core/deploy/ManageClusterServerFile.jsp', 157, 1, 3, 1, '005002004', NULL, -1);
INSERT INTO `securityresource` VALUES (660, '', '集群服务器管理', 'weather-clouds.png', '', 3, 1, 1, 'admin', 'core/deploy/ManageClusterServer.jsp', 207, 1, 3, 0, '008001003', NULL, 0);

-- ----------------------------
-- Table structure for site_announce
-- ----------------------------
DROP TABLE IF EXISTS `site_announce`;
CREATE TABLE `site_announce`  (
  `anId` bigint(20) NOT NULL AUTO_INCREMENT,
  `anTitle` varchar(300)  NOT NULL,
  `content` text  NULL,
  `showStartDate` datetime NULL DEFAULT NULL,
  `showEndDate` datetime NULL DEFAULT NULL,
  `anOrder` bigint(20) NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`anId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_cloud_cfg
-- ----------------------------
DROP TABLE IF EXISTS `site_cloud_cfg`;
CREATE TABLE `site_cloud_cfg`  (
  `cloId` bigint(20) NOT NULL AUTO_INCREMENT,
  `appId` varchar(50)  NULL DEFAULT NULL,
  `accessKey` varchar(120)  NULL DEFAULT NULL,
  `accessSecret` varchar(300)  NULL DEFAULT NULL,
  `endPoint` varchar(200)  NULL DEFAULT NULL,
  `accessUrl` varchar(200)  NULL DEFAULT NULL,
  `bucketName` varchar(60)  NULL DEFAULT NULL,
  `location` varchar(60)  NULL DEFAULT NULL,
  `cloudType` varchar(50)  NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`cloId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_cms_server
-- ----------------------------
DROP TABLE IF EXISTS `site_cms_server`;
CREATE TABLE `site_cms_server`  (
  `serverId` bigint(20) NOT NULL AUTO_INCREMENT,
  `domain` varchar(300)  NOT NULL,
  `context` varchar(100)  NULL DEFAULT NULL,
  `port` int(11) NULL DEFAULT NULL,
  `firstInitOver` int(11) NOT NULL,
  PRIMARY KEY (`serverId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_de_access
-- ----------------------------
DROP TABLE IF EXISTS `site_de_access`;
CREATE TABLE `site_de_access`  (
  `infoId` bigint(20) NOT NULL AUTO_INCREMENT,
  `blackIp` varchar(50)  NOT NULL,
  `startDT` datetime NULL DEFAULT NULL,
  `endDT` datetime NULL DEFAULT NULL,
  `effectHour` int(11) NOT NULL,
  `isDeForever` int(6) NOT NULL,
  PRIMARY KEY (`infoId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_de_access_trace
-- ----------------------------
DROP TABLE IF EXISTS `site_de_access_trace`;
CREATE TABLE `site_de_access_trace`  (
  `trId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50)  NOT NULL,
  `targetUrl` varchar(400)  NOT NULL,
  `dangerStr` varchar(3000)  NOT NULL,
  `queryStr` text  NULL,
  `eventDT` datetime NULL DEFAULT NULL,
  `eventDay` date NULL DEFAULT NULL,
  PRIMARY KEY (`trId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_def_template_config
-- ----------------------------
DROP TABLE IF EXISTS `site_def_template_config`;
CREATE TABLE `site_def_template_config`  (
  `siteId` bigint(20) NULL DEFAULT NULL,
  `modelId` bigint(20) NULL DEFAULT NULL,
  `templateType` bigint(20) NULL DEFAULT NULL,
  `listTemplateUrl` varchar(180)  NULL DEFAULT NULL,
  `contentTemplateUrl` varchar(180)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of site_def_template_config
-- ----------------------------
INSERT INTO `site_def_template_config` VALUES (5, 111, NULL, '', '');
INSERT INTO `site_def_template_config` VALUES (5, 99, NULL, '', '');
INSERT INTO `site_def_template_config` VALUES (5, 98, NULL, '', '');
INSERT INTO `site_def_template_config` VALUES (5, 82, NULL, '', '');
INSERT INTO `site_def_template_config` VALUES (5, 57, NULL, '', '');

-- ----------------------------
-- Table structure for site_delete_trace
-- ----------------------------
DROP TABLE IF EXISTS `site_delete_trace`;
CREATE TABLE `site_delete_trace`  (
  `siteId` bigint(20) NULL DEFAULT NULL,
  `siteFlag` varchar(100)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_dispense_server
-- ----------------------------
DROP TABLE IF EXISTS `site_dispense_server`;
CREATE TABLE `site_dispense_server`  (
  `serverId` bigint(20) NOT NULL AUTO_INCREMENT,
  `protocol` int(11) NOT NULL,
  `serverName` varchar(60)  NOT NULL,
  `serverIP` varchar(30)  NOT NULL,
  `serverPort` varchar(10)  NOT NULL,
  `serverUrl` varchar(80)  NULL DEFAULT NULL,
  `fileRoot` varchar(500)  NULL DEFAULT NULL,
  `connectStatus` int(6) NULL DEFAULT NULL,
  `status` int(11) NOT NULL,
  `loginName` varchar(60)  NULL DEFAULT NULL,
  `loginPassword` varchar(80)  NULL DEFAULT NULL,
  `filterFlag` varchar(500)  NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`serverId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of site_dispense_server
-- ----------------------------
INSERT INTO `site_dispense_server` VALUES (7, 1, '阿萨德撒的', '192.168.0.123', '21', 'http://www', '', NULL, 1, '', '', NULL, 5);

-- ----------------------------
-- Table structure for site_group
-- ----------------------------
DROP TABLE IF EXISTS `site_group`;
CREATE TABLE `site_group`  (
  `siteId` bigint(20) NOT NULL AUTO_INCREMENT,
  `siteRoot` varchar(40)  NOT NULL,
  `siteFlag` varchar(60)  NOT NULL,
  `siteName` varchar(100)  NOT NULL,
  `siteUrl` varchar(255)  NOT NULL,
  `orderFlag` int(11) NULL DEFAULT NULL,
  `home301Url` varchar(300)  NULL DEFAULT NULL,
  `mobSiteUrl` varchar(255)  NULL DEFAULT NULL,
  `padSiteUrl` varchar(255)  NULL DEFAULT NULL,
  `pcVm` int(255) NULL DEFAULT NULL,
  `mobVm` int(255) NULL DEFAULT NULL,
  `padVm` int(255) NULL DEFAULT NULL,
  `logoImage` varchar(120)  NULL DEFAULT NULL,
  `copyright` varchar(140)  NULL DEFAULT NULL,
  `icp` varchar(50)  NULL DEFAULT NULL,
  `homePageTemplate` varchar(200)  NULL DEFAULT NULL,
  `mobHomePageTemplate` varchar(200)  NULL DEFAULT NULL,
  `padHomePageTemplate` varchar(200)  NULL DEFAULT NULL,
  `siteDesc` varchar(450)  NULL DEFAULT NULL,
  `staticFileType` int(6) NULL DEFAULT NULL,
  `homePageProduceType` int(6) NOT NULL,
  `homePageStaticUrl` varchar(200)  NULL DEFAULT NULL,
  `mobHomePageStaticUrl` varchar(200)  NULL DEFAULT NULL,
  `padHomePageStaticUrl` varchar(200)  NULL DEFAULT NULL,
  `publishRoot` varchar(40)  NULL DEFAULT NULL,
  `imageRoot` varchar(40)  NULL DEFAULT NULL,
  `mediaRoot` varchar(40)  NULL DEFAULT NULL,
  `fileRoot` varchar(40)  NULL DEFAULT NULL,
  `templateCharset` varchar(12)  NOT NULL,
  `seoTitle` varchar(300)  NULL DEFAULT NULL,
  `seoKeyword` varchar(300)  NULL DEFAULT NULL,
  `seoDesc` varchar(500)  NULL DEFAULT NULL,
  `shareMode` int(6) NOT NULL,
  `downOutImage` int(6) NOT NULL,
  `deleteOutLink` int(6) NOT NULL,
  `sameTitle` int(6) NULL DEFAULT NULL,
  `genKw` int(6) NULL DEFAULT NULL,
  `summaryLength` int(11) NULL DEFAULT NULL,
  `defClickCount` int(11) NULL DEFAULT NULL,
  `managerIP` varchar(600)  NULL DEFAULT NULL,
  `siteCollType` int(6) NULL DEFAULT NULL,
  `outSiteCollUrl` varchar(200)  NULL DEFAULT NULL,
  `sendMailHost` varchar(60)  NULL DEFAULT NULL,
  `mail` varchar(60)  NULL DEFAULT NULL,
  `mailUserName` varchar(30)  NULL DEFAULT NULL,
  `mailUserPW` varchar(40)  NULL DEFAULT NULL,
  `mailSSL` int(6) NULL DEFAULT NULL,
  `smsApiUrl` varchar(500)  NULL DEFAULT NULL,
  `smsAccount` varchar(100)  NULL DEFAULT NULL,
  `smsPW` varchar(100)  NULL DEFAULT NULL,
  `smsSendOnceSec` int(11) NULL DEFAULT NULL,
  `smsMaxCount` int(11) NULL DEFAULT NULL,
  `smsIpDayCount` int(11) NULL DEFAULT NULL,
  `managerLoginTime` int(11) NULL DEFAULT NULL,
  `searchFun` int(6) NULL DEFAULT NULL,
  `useFW` int(6) NULL DEFAULT NULL,
  `imageAllowType` varchar(220)  NULL DEFAULT NULL,
  `mediaAllowType` varchar(220)  NULL DEFAULT NULL,
  `fileAllowType` varchar(320)  NULL DEFAULT NULL,
  `imageMaxC` int(11) NULL DEFAULT NULL,
  `mediaMaxC` int(11) NULL DEFAULT NULL,
  `fileMaxC` int(11) NULL DEFAULT NULL,
  `useImageMark` int(6) NULL DEFAULT NULL,
  `imageMarkType` int(6) NULL DEFAULT NULL,
  `offSetX` int(11) NULL DEFAULT NULL,
  `offSetY` int(11) NULL DEFAULT NULL,
  `imageMarkPos` varchar(50)  NULL DEFAULT NULL,
  `imageMarkChar` varchar(60)  NULL DEFAULT NULL,
  `imageMark` varchar(200)  NULL DEFAULT NULL,
  `imageMarkDis` int(11) NULL DEFAULT NULL,
  `defEditorImageW` int(11) NULL DEFAULT NULL,
  `defEditorImageH` int(11) NULL DEFAULT NULL,
  `defEditorImageDM` int(6) NULL DEFAULT NULL,
  `defHomeImageW` int(11) NULL DEFAULT NULL,
  `defHomeImageH` int(11) NULL DEFAULT NULL,
  `defHomeImageDM` int(6) NULL DEFAULT NULL,
  `defClassImageW` int(11) NULL DEFAULT NULL,
  `defClassImageH` int(11) NULL DEFAULT NULL,
  `defClassImageDM` int(6) NULL DEFAULT NULL,
  `defListImageW` int(11) NULL DEFAULT NULL,
  `defListImageH` int(11) NULL DEFAULT NULL,
  `defListImageDM` int(6) NULL DEFAULT NULL,
  `defContentImageW` int(11) NULL DEFAULT NULL,
  `defContentImageH` int(11) NULL DEFAULT NULL,
  `defContentImageDM` int(6) NULL DEFAULT NULL,
  `extDataModelId` bigint(20) NULL DEFAULT NULL,
  `mobJump` int(11) NULL DEFAULT NULL,
  `mobSiteId` bigint(20) NULL DEFAULT NULL,
  `extMemberModelId` bigint(20) NULL DEFAULT NULL,
  `allowMemberReg` int(11) NULL DEFAULT NULL,
  `wxAppId` varchar(50)  NULL DEFAULT NULL,
  `wxAppKey` varchar(100)  NULL DEFAULT NULL,
  `wxPrevUid` varchar(100)  NULL DEFAULT NULL,
  `qqAppId` varchar(50)  NULL DEFAULT NULL,
  `qqAppKey` varchar(100)  NULL DEFAULT NULL,
  `qqBackUri` varchar(500)  NULL DEFAULT NULL,
  `wbAppId` varchar(50)  NULL DEFAULT NULL,
  `wbAppKey` varchar(100)  NULL DEFAULT NULL,
  `wbBackUri` varchar(500)  NULL DEFAULT NULL,
  `regMailText` varchar(2000)  NULL DEFAULT NULL,
  `mailRegBackUri` varchar(300)  NULL DEFAULT NULL,
  `relateMemberUri` varchar(100)  NULL DEFAULT NULL,
  `thirdLoginErrorUri` varchar(100)  NULL DEFAULT NULL,
  `thirdLoginSuccessUri` varchar(100)  NULL DEFAULT NULL,
  `resetPwText` varchar(2000)  NULL DEFAULT NULL,
  `resetPwBackUri` varchar(300)  NULL DEFAULT NULL,
  `memberLoginOnce` int(11) NULL DEFAULT NULL,
  `memberDefRoleId` bigint(20) NULL DEFAULT NULL,
  `memberDefLv` int(6) NULL DEFAULT NULL,
  `memberDefSc` bigint(20) NULL DEFAULT NULL,
  `memberExpire` int(11) NULL DEFAULT NULL,
  `memberLoginUri` varchar(300)  NULL DEFAULT NULL,
  `useState` int(6) NOT NULL,
  PRIMARY KEY (`siteId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of site_group
-- ----------------------------
INSERT INTO `site_group` VALUES (5, 'demo', 'demo', '演示门户站', 'http://127.0.0.1:8080/', NULL, 'http://jtopteam.com/,http://www.jtcms.com/', 'http://127.0.0.1:8080/mob/', 'http://127.0.0.1:8080/pab/', 1, NULL, NULL, '', '', '', '/index.jsp', 'mob/index.jsp', 'news_content.jsp', '', 1, 1, 'index.html', NULL, NULL, 'html', 'upload', 'video', 'file', 'UTF-8', '', '', '', 1, 1, 1, 0, 1, 110, 100, NULL, 1, NULL, 'smtp.qq.com', 'www@qq.com', '演示门户站', '123456', 1, '', '', '', 1, 50, 10, 40, 1, 1, 'png,jpeg,gif,bmp,jpg', 'swf,flv,f4v,avi,mpg,mpeg,mp4,rm,rmvb,,3gp,wmv,wma,mp3,wav', 'txt,pdf,swf,flv,png,jpeg,gif,bmp,jpg,f4v,avi,mpg,mpeg,mp3,wav,mp4,doc,docx,pptx,ppt,xlsx,xls,vsd,zip,rar,dat', 10, 500, 5, 1, 1, 10, 10, 'southeast', NULL, '', 130, 0, 0, 0, 0, 0, 0, 0, 0, 0, 124, 77, 0, 0, 0, 0, -1, 0, -1, -1, 1, NULL, NULL, NULL, '101179826', '6e888963019eff9394d9cbe9a6264f3d', NULL, '644659925', '13dba4b420d3ac1f42c30456c3ab430f', NULL, '&lt;p&gt;&lt;span style=&quot;COLOR: #ff0000&quot;&gt;${memberName}&lt;!--**sp--&gt;，&lt;!--**sp--&gt;您好&lt;br/&gt;感谢您注册${siteName}， 您的邮箱账号为 &lt;span style=&quot;COLOR: #ff0000&quot;&gt;${email}&lt;!--**sp--&gt; &lt;br/&gt;请点击下面的连接完成邮箱激活，激活邮箱后，您可使用${siteName}的更多服务。&amp;#39;asd as&amp;#39;d a&amp;#39;s d&amp;lt;&amp;gt;&lt;br/&gt;&lt;a href=&quot;http://127.0.0.1:8080//core/channel/$%7BsysRegMailcallback%7D&quot;&gt;${sysRegMailcallback}&lt;!--*--&gt;&lt;!--*--&gt;&lt;/a&gt;&lt;/span&gt;&lt;/span&gt;&lt;/p&gt;', 'member/member_reg_mail_status.jsp', 'member/member_reg_third.jsp', 'member/member_login.jsp', 'member/member_main_page.jsp', '&lt;p&gt;亲爱的&lt;span style=&quot;COLOR: #ff0000&quot;&gt;${memberName}&lt;!--**sp--&gt;，&lt;!--**sp--&gt;您好&lt;br/&gt;请点击下面的连接进入登陆密码重置页面。&lt;br/&gt;&lt;a href=&quot;http://127.0.0.1:8080//core/channel/$%7BsysResetPWcallback%7D&quot;&gt;${sysResetPWcallback}&lt;!--*--&gt;&lt;!--*--&gt;&lt;/a&gt;&lt;/span&gt;&lt;/p&gt;', 'member/member_forget_pw3.jsp', NULL, 5, -1, 0, 29, 'member/member_login.jsp', 1);

-- ----------------------------
-- Table structure for site_mail_queue
-- ----------------------------
DROP TABLE IF EXISTS `site_mail_queue`;
CREATE TABLE `site_mail_queue`  (
  `mailId` varchar(60)  NOT NULL,
  `sendTo` varchar(4000)  NULL DEFAULT NULL,
  `subject` varchar(800)  NULL DEFAULT NULL,
  `mailContent` mediumtext  NULL,
  `createDT` datetime NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`mailId`) USING BTREE,
  INDEX `dt`(`createDT`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_publish_gateway
-- ----------------------------
DROP TABLE IF EXISTS `site_publish_gateway`;
CREATE TABLE `site_publish_gateway`  (
  `gatewayId` bigint(20) NOT NULL AUTO_INCREMENT,
  `collectJobId` bigint(20) NULL DEFAULT NULL,
  `transferJobId` bigint(20) NULL DEFAULT NULL,
  `name` varchar(60)  NOT NULL,
  `transfeType` int(11) NOT NULL,
  `sourcePath` varchar(500)  NULL DEFAULT NULL,
  `targetServerId` bigint(20) NULL DEFAULT NULL,
  `targetCloudId` bigint(20) NULL DEFAULT NULL,
  `siteRoot` varchar(200)  NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  `targetServerRoot` varchar(200)  NULL DEFAULT NULL,
  `useState` int(11) NOT NULL,
  PRIMARY KEY (`gatewayId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_res_media_cov
-- ----------------------------
DROP TABLE IF EXISTS `site_res_media_cov`;
CREATE TABLE `site_res_media_cov`  (
  `mediaResId` bigint(20) NOT NULL AUTO_INCREMENT,
  `resId` bigint(20) NULL DEFAULT NULL,
  `classId` bigint(20) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`mediaResId`) USING BTREE,
  INDEX `sid`(`siteId`) USING BTREE,
  INDEX `cid`(`classId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_resource
-- ----------------------------
DROP TABLE IF EXISTS `site_resource`;
CREATE TABLE `site_resource`  (
  `resId` bigint(20) NOT NULL AUTO_INCREMENT,
  `classId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `fileType` varchar(12)  NULL DEFAULT NULL,
  `resType` int(11) NOT NULL,
  `resName` varchar(100)  NULL DEFAULT NULL,
  `resSource` varchar(220)  NULL DEFAULT NULL,
  `resSize` bigint(20) NULL DEFAULT NULL,
  `width` int(11) NULL DEFAULT NULL,
  `height` int(11) NULL DEFAULT NULL,
  `duration` int(11) NULL DEFAULT NULL,
  `resolution` varchar(30)  NULL DEFAULT NULL,
  `cover` varchar(80)  NULL DEFAULT NULL,
  `haveMark` int(6) NULL DEFAULT NULL,
  `downloadCount` bigint(20) NULL DEFAULT NULL,
  `modifyTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`resId`) USING BTREE,
  INDEX `rc`(`classId`, `resType`, `resId`) USING BTREE,
  INDEX `re_src`(`resSource`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of site_resource
-- ----------------------------
INSERT INTO `site_resource` VALUES (31, 10658, 5, 'jpg', 1, 'cs1', '2018-06-15/1529062876629402880e76403344e3050164033fe5d500b6.jpg', 32920, 480, 320, NULL, NULL, NULL, 0, NULL, '2018-06-15 19:41:16');

-- ----------------------------
-- Table structure for site_resource_upload_trace
-- ----------------------------
DROP TABLE IF EXISTS `site_resource_upload_trace`;
CREATE TABLE `site_resource_upload_trace`  (
  `resId` bigint(20) NOT NULL,
  `uploadDate` datetime NULL DEFAULT NULL,
  `isUse` int(6) NOT NULL,
  PRIMARY KEY (`resId`) USING BTREE,
  INDEX `date`(`uploadDate`, `isUse`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of site_resource_upload_trace
-- ----------------------------
INSERT INTO `site_resource_upload_trace` VALUES (31, '2018-06-15 19:41:16', 1);

-- ----------------------------
-- Table structure for site_sensitive_word
-- ----------------------------
DROP TABLE IF EXISTS `site_sensitive_word`;
CREATE TABLE `site_sensitive_word`  (
  `swId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sensitiveStr` varchar(60)  NULL DEFAULT NULL,
  `replaceStr` varchar(60)  NOT NULL,
  `useStatus` int(11) NOT NULL,
  PRIMARY KEY (`swId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of site_sensitive_word
-- ----------------------------
INSERT INTO `site_sensitive_word` VALUES (1, 'SB,Sb,sb', '**', 1);
INSERT INTO `site_sensitive_word` VALUES (2, 'asdasdasd', 'asdasd', 1);
INSERT INTO `site_sensitive_word` VALUES (3, 'fuck,FUCK,Fuck,', '文明上网', 1);

-- ----------------------------
-- Table structure for site_share_content
-- ----------------------------
DROP TABLE IF EXISTS `site_share_content`;
CREATE TABLE `site_share_content`  (
  `sscId` bigint(20) NOT NULL AUTO_INCREMENT,
  `contentId` bigint(20) NOT NULL DEFAULT 0,
  `fromSiteId` bigint(20) NOT NULL,
  `toSiteId` bigint(20) NOT NULL,
  `modelId` bigint(20) NOT NULL,
  `fromClassId` bigint(20) NOT NULL,
  `isPub` int(6) NOT NULL,
  PRIMARY KEY (`sscId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for site_share_content_trace
-- ----------------------------
DROP TABLE IF EXISTS `site_share_content_trace`;
CREATE TABLE `site_share_content_trace`  (
  `contentId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sitetemplet
-- ----------------------------
DROP TABLE IF EXISTS `sitetemplet`;
CREATE TABLE `sitetemplet`  (
  `templetId` bigint(20) NOT NULL AUTO_INCREMENT,
  `templetFileName` varchar(80)  NOT NULL DEFAULT '0',
  `templetDisplayName` varchar(60)  NULL DEFAULT NULL,
  `type` int(6) NOT NULL COMMENT '1.栏目展示模板 2.内容展示模板',
  `relatedTempletFilePath` varchar(350)  NULL DEFAULT NULL,
  `templetFullPath` varchar(350)  NOT NULL,
  `siteName` varchar(50)  NOT NULL,
  `templetDesc` varchar(300)  NULL DEFAULT NULL,
  PRIMARY KEY (`templetId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stat_class_content_trace
-- ----------------------------
DROP TABLE IF EXISTS `stat_class_content_trace`;
CREATE TABLE `stat_class_content_trace`  (
  `classId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `addCount` bigint(20) NULL DEFAULT NULL,
  `pubCount` bigint(20) NULL DEFAULT NULL,
  `imgCount` bigint(20) NULL DEFAULT NULL,
  `videoCount` bigint(20) NULL DEFAULT NULL,
  `upYear` int(11) NULL DEFAULT NULL,
  `upMon` int(11) NULL DEFAULT NULL,
  `upDay` int(11) NULL DEFAULT NULL,
  `upDT` date NULL DEFAULT NULL,
  INDEX `key1`(`upYear`, `upMon`, `upDay`, `siteId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_class_content_trace
-- ----------------------------
INSERT INTO `stat_class_content_trace` VALUES (10644, 5, 0, 0, 0, 0, 2017, 2, 20, '2017-02-20');
INSERT INTO `stat_class_content_trace` VALUES (10644, 5, 0, 0, 0, 0, 2017, 2, 21, '2017-02-21');
INSERT INTO `stat_class_content_trace` VALUES (10658, 5, 0, 0, 0, 0, 2017, 10, 26, '2017-10-26');
INSERT INTO `stat_class_content_trace` VALUES (10724, 5, 1, 1, 0, 0, 2017, 10, 27, '2017-10-27');
INSERT INTO `stat_class_content_trace` VALUES (10724, 5, 0, 0, 0, 0, 2017, 10, 29, '2017-10-29');
INSERT INTO `stat_class_content_trace` VALUES (10784, 5, 1, 1, 0, 0, 2017, 10, 29, '2017-10-29');
INSERT INTO `stat_class_content_trace` VALUES (10658, 5, 0, 0, 0, 0, 2017, 10, 29, '2017-10-29');
INSERT INTO `stat_class_content_trace` VALUES (10785, 5, 0, 0, 0, 0, 2017, 11, NULL, '2017-11-09');
INSERT INTO `stat_class_content_trace` VALUES (10658, 5, 1, 1, 1, 0, 2018, 1, 17, '2018-01-17');
INSERT INTO `stat_class_content_trace` VALUES (10644, 5, 1, 0, 0, 0, 2018, 1, 17, '2018-01-17');
INSERT INTO `stat_class_content_trace` VALUES (10658, 5, 1, 1, 0, 0, 2018, 6, 15, '2018-06-15');
INSERT INTO `stat_class_content_trace` VALUES (10661, 5, 1, 0, 0, 0, 2018, 6, 15, '2018-06-15');

-- ----------------------------
-- Table structure for stat_class_update_trace
-- ----------------------------
DROP TABLE IF EXISTS `stat_class_update_trace`;
CREATE TABLE `stat_class_update_trace`  (
  `classId` bigint(20) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  `updateYear` int(11) NULL DEFAULT NULL,
  `updateMon` int(11) NULL DEFAULT NULL,
  `updateDay` int(11) NULL DEFAULT NULL,
  `updateDate` date NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_class_update_trace
-- ----------------------------
INSERT INTO `stat_class_update_trace` VALUES (10644, 5, 2018, 1, 17, '2018-01-17');
INSERT INTO `stat_class_update_trace` VALUES (10658, 5, 2018, 6, 15, '2018-06-15');
INSERT INTO `stat_class_update_trace` VALUES (10724, 5, 2017, 10, 29, '2017-10-29');
INSERT INTO `stat_class_update_trace` VALUES (10784, 5, 2017, 10, 29, '2017-10-29');
INSERT INTO `stat_class_update_trace` VALUES (10785, NULL, 2017, 11, 9, '2017-11-09');
INSERT INTO `stat_class_update_trace` VALUES (10661, 5, 2018, 6, 15, '2018-06-15');

-- ----------------------------
-- Table structure for stat_content_pub_trace
-- ----------------------------
DROP TABLE IF EXISTS `stat_content_pub_trace`;
CREATE TABLE `stat_content_pub_trace`  (
  `contentId` bigint(20) NOT NULL,
  `isPub` int(11) NULL DEFAULT NULL,
  `imgCount` int(11) NULL DEFAULT NULL,
  `videoCount` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_content_pub_trace
-- ----------------------------
INSERT INTO `stat_content_pub_trace` VALUES (4, 1, 0, 0);
INSERT INTO `stat_content_pub_trace` VALUES (5, 1, 0, 0);
INSERT INTO `stat_content_pub_trace` VALUES (6, 1, 1, 0);
INSERT INTO `stat_content_pub_trace` VALUES (7, 0, 0, 0);
INSERT INTO `stat_content_pub_trace` VALUES (10, 1, 0, 0);
INSERT INTO `stat_content_pub_trace` VALUES (11, 0, 0, 0);

-- ----------------------------
-- Table structure for stat_date_helper
-- ----------------------------
DROP TABLE IF EXISTS `stat_date_helper`;
CREATE TABLE `stat_date_helper`  (
  `prevDay` date NULL DEFAULT NULL,
  `currentDay` date NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_date_helper
-- ----------------------------
INSERT INTO `stat_date_helper` VALUES (NULL, '2018-06-15');

-- ----------------------------
-- Table structure for stat_flow_exe_trace
-- ----------------------------
DROP TABLE IF EXISTS `stat_flow_exe_trace`;
CREATE TABLE `stat_flow_exe_trace`  (
  `actUserName` varchar(400)  NULL DEFAULT NULL,
  `flowCommand` varchar(120)  NULL DEFAULT NULL,
  `flowName` varchar(200)  NULL DEFAULT NULL,
  `executeDt` datetime NULL DEFAULT NULL,
  `params` text  NULL,
  `ip` varchar(50)  NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_flow_exe_trace
-- ----------------------------

-- ----------------------------
-- Table structure for stat_manager_content_trace
-- ----------------------------
DROP TABLE IF EXISTS `stat_manager_content_trace`;
CREATE TABLE `stat_manager_content_trace`  (
  `userId` bigint(20) NOT NULL,
  `addCount` bigint(20) NULL DEFAULT NULL,
  `pubCount` bigint(20) NULL DEFAULT NULL,
  `imgCount` bigint(20) NULL DEFAULT NULL,
  `videoCount` bigint(20) NULL DEFAULT NULL,
  `upYear` int(11) NULL DEFAULT NULL,
  `upMon` int(11) NULL DEFAULT NULL,
  `upDay` int(11) NULL DEFAULT NULL,
  `upDT` date NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_manager_content_trace
-- ----------------------------
INSERT INTO `stat_manager_content_trace` VALUES (104, 2, 1, 0, 0, 2018, 6, 15, '2018-06-15');

-- ----------------------------
-- Table structure for stat_manager_login_trace
-- ----------------------------
DROP TABLE IF EXISTS `stat_manager_login_trace`;
CREATE TABLE `stat_manager_login_trace`  (
  `ip` varchar(60)  NOT NULL,
  `eventDT` datetime NOT NULL,
  `loginSuccess` int(6) NOT NULL,
  `userName` varchar(100)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_manager_login_trace
-- ----------------------------


-- ----------------------------
-- Table structure for stat_org_content_trace
-- ----------------------------
DROP TABLE IF EXISTS `stat_org_content_trace`;
CREATE TABLE `stat_org_content_trace`  (
  `orgId` bigint(20) NOT NULL,
  `addCount` bigint(20) NULL DEFAULT NULL,
  `pubCount` bigint(20) NULL DEFAULT NULL,
  `imgCount` bigint(20) NULL DEFAULT NULL,
  `videoCount` bigint(20) NULL DEFAULT NULL,
  `upYear` int(11) NULL DEFAULT NULL,
  `upMon` int(11) NULL DEFAULT NULL,
  `upDay` int(11) NULL DEFAULT NULL,
  `upDT` date NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_org_content_trace
-- ----------------------------
INSERT INTO `stat_org_content_trace` VALUES (1, 0, 0, 0, 0, 2017, 2, 20, '2017-02-20');
INSERT INTO `stat_org_content_trace` VALUES (1, 0, 0, 0, 0, 2017, 2, 21, '2017-02-21');
INSERT INTO `stat_org_content_trace` VALUES (1, 0, 0, 0, 0, 2017, 10, 26, '2017-10-26');
INSERT INTO `stat_org_content_trace` VALUES (1, 1, 1, 0, 0, 2017, 10, 27, '2017-10-27');
INSERT INTO `stat_org_content_trace` VALUES (1, 1, 1, 0, 0, 2017, 10, 29, '2017-10-29');
INSERT INTO `stat_org_content_trace` VALUES (1, 2, 1, 1, 0, 2018, 1, 17, '2018-01-17');
INSERT INTO `stat_org_content_trace` VALUES (1, 2, 1, 0, 0, 2018, 6, 15, '2018-06-15');

-- ----------------------------
-- Table structure for stat_uvid_trace
-- ----------------------------
DROP TABLE IF EXISTS `stat_uvid_trace`;
CREATE TABLE `stat_uvid_trace`  (
  `uvid` varchar(60)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stat_visit_info
-- ----------------------------
DROP TABLE IF EXISTS `stat_visit_info`;
CREATE TABLE `stat_visit_info`  (
  `visitorId` bigint(20) NOT NULL AUTO_INCREMENT,
  `uvId` varchar(60)  NOT NULL,
  `host` varchar(300)  NULL DEFAULT NULL,
  `reffer` varchar(2000)  NULL DEFAULT NULL,
  `refferHost` varchar(200)  NULL DEFAULT NULL,
  `refferType` int(4) NULL DEFAULT NULL,
  `refferKey` varchar(200)  NULL DEFAULT NULL,
  `visitTimeIn` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `visitTimeOut` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `ip` varchar(50)  NOT NULL,
  `url` varchar(550)  NOT NULL,
  `title` varchar(460)  NULL DEFAULT NULL,
  `browser` varchar(240)  NULL DEFAULT NULL,
  `screen` varchar(50)  NULL DEFAULT NULL,
  `system` varchar(45)  NULL DEFAULT NULL,
  `lang` varchar(15)  NULL DEFAULT NULL,
  `source` varchar(50)  NULL DEFAULT NULL,
  `visitYear` int(11) NOT NULL,
  `visitMonth` int(11) NOT NULL,
  `visitDay` int(11) NOT NULL,
  `visitHour` int(11) NOT NULL,
  `contentId` bigint(20) NULL DEFAULT NULL,
  `classId` bigint(20) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`visitorId`) USING BTREE,
  INDEX `visit-date`(`visitDay`, `visitHour`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visit_info
-- ----------------------------
INSERT INTO `stat_visit_info` VALUES (11, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', '', NULL, 0, '', '2018-06-15 20:28:51', '2018-06-15 20:28:51', '127.0.0.1', 'http://127.0.0.1:8080/', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, -1, 5);
INSERT INTO `stat_visit_info` VALUES (12, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/channel_video.jsp?channelId=10657', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:00', '2018-06-15 20:29:00', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10646, 5);
INSERT INTO `stat_visit_info` VALUES (13, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:01', '2018-06-15 20:29:01', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10650', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10650, 5);
INSERT INTO `stat_visit_info` VALUES (14, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10650', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:02', '2018-06-15 20:29:02', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10651', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10651, 5);
INSERT INTO `stat_visit_info` VALUES (15, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10651', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:02', '2018-06-15 20:29:02', '127.0.0.1', 'http://127.0.0.1:8080/channel/10652/index.html', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10652, 5);
INSERT INTO `stat_visit_info` VALUES (16, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/channel/10652/index.html', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:03', '2018-06-15 20:29:03', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10720', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10720, 5);
INSERT INTO `stat_visit_info` VALUES (17, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/channel_video.jsp?channelId=10657', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:09', '2018-06-15 20:29:09', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10646, 5);
INSERT INTO `stat_visit_info` VALUES (18, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:10', '2018-06-15 20:29:10', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10650', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10650, 5);
INSERT INTO `stat_visit_info` VALUES (19, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10650', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:11', '2018-06-15 20:29:11', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10646, 5);
INSERT INTO `stat_visit_info` VALUES (20, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/channel_video.jsp?channelId=10657', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:12', '2018-06-15 20:29:12', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10646, 5);
INSERT INTO `stat_visit_info` VALUES (21, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/channel_video.jsp?channelId=10657', '127.0.0.1:8080', 6, '', '2018-06-15 20:29:15', '2018-06-15 20:29:15', '127.0.0.1', 'http://127.0.0.1:8080/', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, -1, 5);
INSERT INTO `stat_visit_info` VALUES (22, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/add_def_form_data.jsp?channelId=10721', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:05', '2018-06-15 20:46:05', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10720', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10720, 5);
INSERT INTO `stat_visit_info` VALUES (23, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10720', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:06', '2018-06-15 20:46:06', '127.0.0.1', 'http://127.0.0.1:8080/channel/10652/index.html', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10652, 5);
INSERT INTO `stat_visit_info` VALUES (24, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/channel/10652/index.html', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:06', '2018-06-15 20:46:06', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10651', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10651, 5);
INSERT INTO `stat_visit_info` VALUES (25, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10651', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:07', '2018-06-15 20:46:07', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10646, 5);
INSERT INTO `stat_visit_info` VALUES (26, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/photo.jsp?channelId=10648', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:10', '2018-06-15 20:46:10', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10646, 5);
INSERT INTO `stat_visit_info` VALUES (27, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:12', '2018-06-15 20:46:12', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10650', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10650, 5);
INSERT INTO `stat_visit_info` VALUES (28, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10650', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:13', '2018-06-15 20:46:13', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (29, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:16', '2018-06-15 20:46:16', '127.0.0.1', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (30, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:18', '2018-06-15 20:46:18', '127.0.0.1', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=72', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (31, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=72', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:19', '2018-06-15 20:46:19', '127.0.0.1', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (32, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:46:20', '2018-06-15 20:46:20', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (33, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:47:52', '2018-06-15 20:47:52', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (34, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:47:54', '2018-06-15 20:47:54', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (35, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:47:54', '2018-06-15 20:47:54', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (36, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:47:54', '2018-06-15 20:47:54', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (37, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:47:54', '2018-06-15 20:47:54', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (38, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:47:54', '2018-06-15 20:47:54', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (39, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:47:55', '2018-06-15 20:47:55', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (40, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:47:55', '2018-06-15 20:47:55', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (41, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:47:55', '2018-06-15 20:47:55', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (42, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', '127.0.0.1:8080', 6, '', '2018-06-15 20:48:13', '2018-06-15 20:48:13', '127.0.0.1', 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10687, 5);
INSERT INTO `stat_visit_info` VALUES (43, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/channel_video.jsp?channelId=10657', '127.0.0.1:8080', 6, '', '2018-06-15 20:48:15', '2018-06-15 20:48:15', '127.0.0.1', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, 10646, 5);
INSERT INTO `stat_visit_info` VALUES (44, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', '127.0.0.1:8080', 6, '', '2018-06-15 20:48:17', '2018-06-15 20:48:17', '127.0.0.1', 'http://127.0.0.1:8080/', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, -1, 5);
INSERT INTO `stat_visit_info` VALUES (45, '402880e7638120644110163816e3a6c0112', '127.0.0.1:8080', 'http://127.0.0.1:8080/index.thtml', '127.0.0.1:8080', 6, '', '2018-06-15 20:53:59', '2018-06-15 20:53:59', '127.0.0.1', 'http://127.0.0.1:8080/vote.jsp?channelId=10655', NULL, 'Chrome', '1920*1080', 'Windows', '中文', '', 2018, 6, 15, 20, -1, -1, 5);

-- ----------------------------
-- Table structure for stat_visitor_area_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_area_analysis`;
CREATE TABLE `stat_visitor_area_analysis`  (
  `pvCount` bigint(20) NOT NULL,
  `area` varchar(80)  NOT NULL,
  `siteId` bigint(20) NOT NULL,
  INDEX `sid-ar`(`siteId`, `area`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_area_analysis
-- ----------------------------
INSERT INTO `stat_visitor_area_analysis` VALUES (10, 'IANA', 5);
INSERT INTO `stat_visitor_area_analysis` VALUES (35, '本机地址', 5);

-- ----------------------------
-- Table structure for stat_visitor_br_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_br_analysis`;
CREATE TABLE `stat_visitor_br_analysis`  (
  `pvCount` bigint(20) NOT NULL,
  `brName` varchar(80)  NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_br_analysis
-- ----------------------------
INSERT INTO `stat_visitor_br_analysis` VALUES (45, 'Chrome', 5);

-- ----------------------------
-- Table structure for stat_visitor_class_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_class_analysis`;
CREATE TABLE `stat_visitor_class_analysis`  (
  `pvCount` bigint(20) NOT NULL,
  `classId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_class_analysis
-- ----------------------------
INSERT INTO `stat_visitor_class_analysis` VALUES (1, 10658, 5);
INSERT INTO `stat_visitor_class_analysis` VALUES (3, 10724, 5);
INSERT INTO `stat_visitor_class_analysis` VALUES (7, 10646, 5);
INSERT INTO `stat_visitor_class_analysis` VALUES (3, 10650, 5);
INSERT INTO `stat_visitor_class_analysis` VALUES (2, 10651, 5);
INSERT INTO `stat_visitor_class_analysis` VALUES (2, 10652, 5);
INSERT INTO `stat_visitor_class_analysis` VALUES (2, 10720, 5);
INSERT INTO `stat_visitor_class_analysis` VALUES (15, 10687, 5);

-- ----------------------------
-- Table structure for stat_visitor_content_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_content_analysis`;
CREATE TABLE `stat_visitor_content_analysis`  (
  `pvCount` bigint(20) NOT NULL,
  `contentId` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_content_analysis
-- ----------------------------
INSERT INTO `stat_visitor_content_analysis` VALUES (1, 2, 5);
INSERT INTO `stat_visitor_content_analysis` VALUES (3, 3, 5);

-- ----------------------------
-- Table structure for stat_visitor_day_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_day_analysis`;
CREATE TABLE `stat_visitor_day_analysis`  (
  `uvCount` int(11) NOT NULL,
  `ipCount` int(11) NOT NULL,
  `pvCount` int(11) NOT NULL,
  `visitYear` int(11) NOT NULL,
  `visitMonth` int(11) NOT NULL,
  `visitDay` int(11) NOT NULL,
  `vdt` datetime NULL DEFAULT NULL,
  `newUv` int(11) NOT NULL,
  `oldUv` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  INDEX `sda_vd`(`visitYear`, `visitMonth`, `visitDay`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_day_analysis
-- ----------------------------
INSERT INTO `stat_visitor_day_analysis` VALUES (0, 0, 0, 2017, 2, 20, '2017-02-20 00:00:00', 0, 0, 5);
INSERT INTO `stat_visitor_day_analysis` VALUES (1, 1, 7, 2017, 10, 26, '2017-10-26 00:00:00', 1, 0, 5);
INSERT INTO `stat_visitor_day_analysis` VALUES (1, 1, 3, 2017, 10, 27, '2017-10-27 00:00:00', 1, 0, 5);
INSERT INTO `stat_visitor_day_analysis` VALUES (0, 0, 0, 2018, 1, 11, '2018-01-11 00:00:00', 0, 0, 5);
INSERT INTO `stat_visitor_day_analysis` VALUES (0, 0, 0, 2018, 1, 17, '2018-01-17 00:00:00', 0, 0, 5);
INSERT INTO `stat_visitor_day_analysis` VALUES (0, 0, 0, 2018, 5, 16, '2018-05-16 00:00:00', 0, 0, 5);
INSERT INTO `stat_visitor_day_analysis` VALUES (1, 1, 35, 2018, 6, 15, '2018-06-15 00:00:00', 1, 0, 5);

-- ----------------------------
-- Table structure for stat_visitor_day_exit_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_day_exit_analysis`;
CREATE TABLE `stat_visitor_day_exit_analysis`  (
  `url` varchar(800)  NOT NULL,
  `visitYear` int(11) NOT NULL,
  `visitMonth` int(11) NOT NULL,
  `visitDay` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stat_visitor_day_gate_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_day_gate_analysis`;
CREATE TABLE `stat_visitor_day_gate_analysis`  (
  `url` varchar(800)  NOT NULL,
  `visitYear` int(11) NOT NULL,
  `visitMonth` int(11) NOT NULL,
  `visitDay` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stat_visitor_host_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_host_analysis`;
CREATE TABLE `stat_visitor_host_analysis`  (
  `pvCount` bigint(20) NOT NULL,
  `host` varchar(200)  NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_host_analysis
-- ----------------------------
INSERT INTO `stat_visitor_host_analysis` VALUES (10, '127.0.0.1', 5);
INSERT INTO `stat_visitor_host_analysis` VALUES (35, '127.0.0.1:8080', 5);

-- ----------------------------
-- Table structure for stat_visitor_hour_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_hour_analysis`;
CREATE TABLE `stat_visitor_hour_analysis`  (
  `uvCount` int(70) NOT NULL,
  `ipCount` int(50) NOT NULL,
  `pvCount` int(11) NOT NULL,
  `visitYear` int(11) NOT NULL,
  `visitMonth` int(11) NOT NULL,
  `visitDay` int(11) NOT NULL,
  `visitHour` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `newUv` int(11) NOT NULL,
  `oldUv` int(11) NOT NULL,
  INDEX `sha_vd`(`visitYear`, `visitMonth`, `visitDay`, `visitHour`) USING BTREE,
  INDEX `sId-h`(`siteId`, `visitHour`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_hour_analysis
-- ----------------------------
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2017, 10, 26, 16, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (1, 1, 7, 2017, 10, 26, 17, 5, 1, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2017, 10, 26, 18, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2017, 10, 26, 19, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2017, 10, 26, 20, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2017, 10, 26, 21, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2017, 10, 27, 9, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2017, 10, 27, 10, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2017, 10, 27, 11, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (1, 1, 3, 2017, 10, 27, 14, 5, 1, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2018, 1, 11, 15, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2018, 1, 17, 17, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2018, 1, 17, 18, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2018, 5, 16, 10, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2018, 5, 16, 11, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2018, 6, 15, 19, 5, 0, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (1, 1, 35, 2018, 6, 15, 20, 5, 1, 0);
INSERT INTO `stat_visitor_hour_analysis` VALUES (0, 0, 0, 2018, 6, 15, 21, 5, 0, 0);

-- ----------------------------
-- Table structure for stat_visitor_la_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_la_analysis`;
CREATE TABLE `stat_visitor_la_analysis`  (
  `pvCount` bigint(20) NOT NULL,
  `laName` varchar(40)  NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_la_analysis
-- ----------------------------
INSERT INTO `stat_visitor_la_analysis` VALUES (45, '中文', 5);

-- ----------------------------
-- Table structure for stat_visitor_os_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_os_analysis`;
CREATE TABLE `stat_visitor_os_analysis`  (
  `pvCount` bigint(20) NOT NULL,
  `osName` varchar(50)  NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_os_analysis
-- ----------------------------
INSERT INTO `stat_visitor_os_analysis` VALUES (45, 'Windows', 5);

-- ----------------------------
-- Table structure for stat_visitor_resol_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_resol_analysis`;
CREATE TABLE `stat_visitor_resol_analysis`  (
  `pvCount` bigint(20) NOT NULL,
  `resVal` varchar(30)  NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_resol_analysis
-- ----------------------------
INSERT INTO `stat_visitor_resol_analysis` VALUES (45, '1920*1080', 5);

-- ----------------------------
-- Table structure for stat_visitor_search_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_search_analysis`;
CREATE TABLE `stat_visitor_search_analysis`  (
  `uvCount` bigint(20) NOT NULL,
  `searchName` varchar(60)  NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stat_visitor_seh_key_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_seh_key_analysis`;
CREATE TABLE `stat_visitor_seh_key_analysis`  (
  `uvCount` bigint(20) NOT NULL,
  `keyVal` varchar(200)  NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stat_visitor_source_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_source_analysis`;
CREATE TABLE `stat_visitor_source_analysis`  (
  `manUvCount` int(70) NOT NULL,
  `searchUvCount` int(50) NOT NULL,
  `sprUvCount` int(11) NOT NULL,
  `visitYear` int(11) NOT NULL,
  `visitMonth` int(11) NOT NULL,
  `visitDay` int(11) NOT NULL,
  `vdt` datetime NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  INDEX `visitDate`(`visitYear`, `visitMonth`, `visitDay`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stat_visitor_src_s_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_src_s_analysis`;
CREATE TABLE `stat_visitor_src_s_analysis`  (
  `uvCount` bigint(20) NOT NULL,
  `siteUrl` varchar(200)  NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stat_visitor_url_analysis
-- ----------------------------
DROP TABLE IF EXISTS `stat_visitor_url_analysis`;
CREATE TABLE `stat_visitor_url_analysis`  (
  `clickCount` bigint(20) NOT NULL,
  `url` varchar(500)  NOT NULL,
  `siteId` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stat_visitor_url_analysis
-- ----------------------------
INSERT INTO `stat_visitor_url_analysis` VALUES (6, 'http://127.0.0.1/', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (1, 'http://127.0.0.1/news_content.jsp?id=2&___sys_cms_preview___=e48a0faca3ad057b118af952c1cb4965952f37785cc0091c22d1608e3fcf1c20', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (1, 'http://127.0.0.1/news_content.jsp?id=3&___sys_cms_preview___=9c668ed74c5d7c0d89e4ae8bc2dea77dac52118f257de7c2343c3d340de7c2d7', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (2, 'http://127.0.0.1/content/2017/3.html', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (3, 'http://127.0.0.1:8080/', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (7, 'http://127.0.0.1:8080/list_channel.jsp?channelId=10646', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (3, 'http://127.0.0.1:8080/list_channel.jsp?channelId=10650', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (2, 'http://127.0.0.1:8080/list_channel.jsp?channelId=10651', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (2, 'http://127.0.0.1:8080/channel/10652/index.html', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (2, 'http://127.0.0.1:8080/list_channel.jsp?channelId=10720', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (12, 'http://127.0.0.1:8080/channel_spec.thtml?channelId=10687', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (2, 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=71', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (1, 'http://127.0.0.1:8080/spec_list.jsp?channelId=10687&tid=72', 5);
INSERT INTO `stat_visitor_url_analysis` VALUES (1, 'http://127.0.0.1:8080/vote.jsp?channelId=10655', 5);

-- ----------------------------
-- Table structure for survey_base_info
-- ----------------------------
DROP TABLE IF EXISTS `survey_base_info`;
CREATE TABLE `survey_base_info`  (
  `surveyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `groupFlag` varchar(60)  NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `optionType` int(6) NOT NULL,
  `surveyTitle` varchar(500)  NOT NULL,
  `haveText` int(6) NOT NULL,
  `addiTitle` varchar(300)  NULL DEFAULT NULL,
  `siteFlag` varchar(40)  NOT NULL,
  `orderFlag` int(11) NOT NULL,
  PRIMARY KEY (`surveyId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of survey_base_info
-- ----------------------------
INSERT INTO `survey_base_info` VALUES (45, 'mh_wj1', 15, 2, '觉得哪些功能需要加强?', 0, '', 'demo', 2);
INSERT INTO `survey_base_info` VALUES (55, 'mh_wj1', 15, 1, 'asdasdsadsad', 0, '', 'demo', 3);
INSERT INTO `survey_base_info` VALUES (56, 'mh_wj1', 15, 5, 'asdasddadadasd撒旦', 0, '', 'demo', 4);

-- ----------------------------
-- Table structure for survey_group
-- ----------------------------
DROP TABLE IF EXISTS `survey_group`;
CREATE TABLE `survey_group`  (
  `groupId` bigint(20) NOT NULL AUTO_INCREMENT,
  `classId` bigint(20) NULL DEFAULT NULL,
  `questName` varchar(200)  NOT NULL,
  `flagName` varchar(120)  NOT NULL,
  `questDesc` varchar(500)  NULL DEFAULT NULL,
  `restriction` int(11) NOT NULL,
  `restInterval` int(11) NULL DEFAULT NULL,
  `startDate` datetime NULL DEFAULT NULL,
  `endDate` datetime NULL DEFAULT NULL,
  `needCaptcha` int(11) NOT NULL,
  `useState` int(11) NOT NULL,
  `handlers` varchar(80)  NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of survey_group
-- ----------------------------
INSERT INTO `survey_group` VALUES (15, 10658, '演示门户问卷', 'mh_wj1', '', 1, 1, '2014-10-04 00:00:00', '9999-12-31 00:00:00', 1, 1, 'a', 5);

-- ----------------------------
-- Table structure for survey_option_info
-- ----------------------------
DROP TABLE IF EXISTS `survey_option_info`;
CREATE TABLE `survey_option_info`  (
  `optionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `surveyId` bigint(20) NOT NULL,
  `optionText` varchar(500)  NULL DEFAULT NULL,
  `optionImage` varchar(200)  NULL DEFAULT NULL,
  `vote` int(11) NOT NULL,
  `votePer` int(11) NOT NULL,
  `target` varchar(300)  NULL DEFAULT NULL,
  `inputText` varchar(4000)  NULL DEFAULT NULL,
  `inputTextCount` int(11) NULL DEFAULT NULL,
  `siteFlag` varchar(40)  NOT NULL,
  PRIMARY KEY (`optionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 252 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of survey_option_info
-- ----------------------------
INSERT INTO `survey_option_info` VALUES (239, 45, '采集', NULL, 12, 7, NULL, NULL, NULL, 'demo');
INSERT INTO `survey_option_info` VALUES (240, 45, '内容维护', NULL, 26, 16, NULL, NULL, NULL, 'demo');
INSERT INTO `survey_option_info` VALUES (241, 45, '站点管理', NULL, 57, 35, NULL, NULL, NULL, 'demo');
INSERT INTO `survey_option_info` VALUES (242, 45, '会员开发', NULL, 7, 4, NULL, NULL, NULL, 'demo');
INSERT INTO `survey_option_info` VALUES (243, 45, 'API接口', NULL, 57, 35, NULL, NULL, NULL, 'demo');
INSERT INTO `survey_option_info` VALUES (248, 55, 'dasdsad', NULL, 0, 0, NULL, NULL, NULL, 'demo');
INSERT INTO `survey_option_info` VALUES (249, 55, 'dsadsadsadsdsad', NULL, 0, 0, NULL, NULL, NULL, 'demo');
INSERT INTO `survey_option_info` VALUES (251, 56, NULL, NULL, 0, 0, NULL, NULL, 200, 'demo');

-- ----------------------------
-- Table structure for survey_vote_info
-- ----------------------------
DROP TABLE IF EXISTS `survey_vote_info`;
CREATE TABLE `survey_vote_info`  (
  `optId` bigint(20) NOT NULL,
  `surveyId` bigint(20) NOT NULL,
  `voteText` varchar(4000)  NULL DEFAULT NULL,
  `voteMan` varchar(60)  NULL DEFAULT NULL,
  `ip` varchar(50)  NOT NULL,
  `voteDate` datetime NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of survey_vote_info
-- ----------------------------
INSERT INTO `survey_vote_info` VALUES (-1, 56, 'asdasdsadsadsadsadsadsadsadsad', '匿名', '192.168.0.100', '2017-01-27 13:30:55');

-- ----------------------------
-- Table structure for survey_vote_ip_trace
-- ----------------------------
DROP TABLE IF EXISTS `survey_vote_ip_trace`;
CREATE TABLE `survey_vote_ip_trace`  (
  `ip` varchar(24)  NOT NULL,
  `surveyGroupId` bigint(20) NOT NULL,
  `lastVoteDateTime` varchar(20)  NULL DEFAULT NULL,
  INDEX `ip_gid`(`lastVoteDateTime`, `ip`, `surveyGroupId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for syscacheclasslayer
-- ----------------------------
DROP TABLE IF EXISTS `syscacheclasslayer`;
CREATE TABLE `syscacheclasslayer`  (
  `classId` bigint(20) NOT NULL,
  `layerQuerySqlCache` mediumtext  NOT NULL,
  PRIMARY KEY (`classId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of syscacheclasslayer
-- ----------------------------
INSERT INTO `syscacheclasslayer` VALUES (10644, 'classId=10644 or classId=10659 or classId=10660 or classId=10661 or classId=10662 or classId=10658 ');
INSERT INTO `syscacheclasslayer` VALUES (10645, 'classId=10645 or classId=10664 or classId=10715 or classId=10716 or classId=10717 or classId=10718 or classId=10719 ');
INSERT INTO `syscacheclasslayer` VALUES (10646, 'classId=10646 ');
INSERT INTO `syscacheclasslayer` VALUES (10648, 'classId=10648 or classId=10682 or classId=10683 or classId=10684 or classId=10685 ');
INSERT INTO `syscacheclasslayer` VALUES (10650, 'classId=10650 ');
INSERT INTO `syscacheclasslayer` VALUES (10651, 'classId=10651 ');
INSERT INTO `syscacheclasslayer` VALUES (10652, 'classId=10652 ');
INSERT INTO `syscacheclasslayer` VALUES (10653, 'classId=10653 ');
INSERT INTO `syscacheclasslayer` VALUES (10655, 'classId=10655 ');
INSERT INTO `syscacheclasslayer` VALUES (10657, 'classId=10657 or classId=10665 or classId=10668 or classId=10669 or classId=10670 or classId=10671 or classId=10672 or classId=10673 or classId=10674 or classId=10675 or classId=10676 or classId=10677 or classId=10678 or classId=10679 or classId=10680 or classId=10681 ');
INSERT INTO `syscacheclasslayer` VALUES (10658, 'classId=10658 ');
INSERT INTO `syscacheclasslayer` VALUES (10659, 'classId=10659 ');
INSERT INTO `syscacheclasslayer` VALUES (10660, 'classId=10660 ');
INSERT INTO `syscacheclasslayer` VALUES (10661, 'classId=10661 ');
INSERT INTO `syscacheclasslayer` VALUES (10662, 'classId=10662 ');
INSERT INTO `syscacheclasslayer` VALUES (10664, 'classId=10664 ');
INSERT INTO `syscacheclasslayer` VALUES (10665, 'classId=10665 or classId=10670 or classId=10671 or classId=10672 or classId=10673 ');
INSERT INTO `syscacheclasslayer` VALUES (10668, 'classId=10668 or classId=10674 or classId=10675 or classId=10676 or classId=10677 ');
INSERT INTO `syscacheclasslayer` VALUES (10669, 'classId=10669 or classId=10678 or classId=10679 or classId=10680 or classId=10681 ');
INSERT INTO `syscacheclasslayer` VALUES (10670, 'classId=10670 ');
INSERT INTO `syscacheclasslayer` VALUES (10671, 'classId=10671 ');
INSERT INTO `syscacheclasslayer` VALUES (10672, 'classId=10672 ');
INSERT INTO `syscacheclasslayer` VALUES (10673, 'classId=10673 ');
INSERT INTO `syscacheclasslayer` VALUES (10674, 'classId=10674 ');
INSERT INTO `syscacheclasslayer` VALUES (10675, 'classId=10675 ');
INSERT INTO `syscacheclasslayer` VALUES (10676, 'classId=10676 ');
INSERT INTO `syscacheclasslayer` VALUES (10677, 'classId=10677 ');
INSERT INTO `syscacheclasslayer` VALUES (10678, 'classId=10678 ');
INSERT INTO `syscacheclasslayer` VALUES (10679, 'classId=10679 ');
INSERT INTO `syscacheclasslayer` VALUES (10680, 'classId=10680 ');
INSERT INTO `syscacheclasslayer` VALUES (10681, 'classId=10681 ');
INSERT INTO `syscacheclasslayer` VALUES (10682, 'classId=10682 ');
INSERT INTO `syscacheclasslayer` VALUES (10683, 'classId=10683 ');
INSERT INTO `syscacheclasslayer` VALUES (10684, 'classId=10684 ');
INSERT INTO `syscacheclasslayer` VALUES (10685, 'classId=10685 ');
INSERT INTO `syscacheclasslayer` VALUES (10686, 'classId=10686 or classId=10713 ');
INSERT INTO `syscacheclasslayer` VALUES (10687, 'classId=10687 ');
INSERT INTO `syscacheclasslayer` VALUES (10688, 'classId=10688 ');
INSERT INTO `syscacheclasslayer` VALUES (10713, 'classId=10713 ');
INSERT INTO `syscacheclasslayer` VALUES (10714, 'classId=10714 ');
INSERT INTO `syscacheclasslayer` VALUES (10715, 'classId=10715 ');
INSERT INTO `syscacheclasslayer` VALUES (10716, 'classId=10716 ');
INSERT INTO `syscacheclasslayer` VALUES (10717, 'classId=10717 ');
INSERT INTO `syscacheclasslayer` VALUES (10718, 'classId=10718 ');
INSERT INTO `syscacheclasslayer` VALUES (10719, 'classId=10719 ');
INSERT INTO `syscacheclasslayer` VALUES (10720, 'classId=10720 ');
INSERT INTO `syscacheclasslayer` VALUES (10721, 'classId=10721 ');
INSERT INTO `syscacheclasslayer` VALUES (10724, 'classId=10724 ');
INSERT INTO `syscacheclasslayer` VALUES (10784, 'classId=10784 ');
INSERT INTO `syscacheclasslayer` VALUES (10785, 'classId=10785 ');

-- ----------------------------
-- Table structure for system_api_config
-- ----------------------------
DROP TABLE IF EXISTS `system_api_config`;
CREATE TABLE `system_api_config`  (
  `apiId` bigint(20) NOT NULL AUTO_INCREMENT,
  `apiName` varchar(200)  NULL DEFAULT NULL,
  `flowPath` varchar(300)  NULL DEFAULT NULL,
  `reqMethod` varchar(20)  NULL DEFAULT NULL,
  `mustTok` int(11) NULL DEFAULT NULL,
  `mustEnc` int(11) NULL DEFAULT NULL,
  `mustSecTok` int(11) NULL DEFAULT NULL,
  `extBehaviorClass` varchar(300)  NULL DEFAULT NULL,
  `apiParams` varchar(2000)  NULL DEFAULT NULL,
  PRIMARY KEY (`apiId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for system_cfg
-- ----------------------------
DROP TABLE IF EXISTS `system_cfg`;
CREATE TABLE `system_cfg`  (
  `serverId` bigint(20) NOT NULL AUTO_INCREMENT,
  `managerIp` varchar(4000)  NULL DEFAULT NULL,
  `loginTime` varchar(50)  NULL DEFAULT NULL,
  `openOfficePath` varchar(600)  NULL DEFAULT NULL,
  `rootOrgName` varchar(200)  NULL DEFAULT NULL,
  `baiduMapDefCity` varchar(100)  NULL DEFAULT NULL,
  `dangerAccessCount` varchar(10)  NULL DEFAULT NULL,
  `otherVCUrl` varchar(400)  NULL DEFAULT NULL,
  `backupDay` int(11) NULL DEFAULT NULL,
  `backupHourExe` int(11) NULL DEFAULT NULL,
  `backupFtpId` bigint(20) NULL DEFAULT NULL,
  `aldOpt` int(11) NULL DEFAULT NULL,
  `alhOpt` int(11) NULL DEFAULT NULL,
  `alStartHour` int(11) NULL DEFAULT NULL,
  `alEndHour` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`serverId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_cfg
-- ----------------------------
INSERT INTO `system_cfg` VALUES (1, '', '60', '', '总公司', '上海', '3', '', 7, 23, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for system_cluster_server
-- ----------------------------
DROP TABLE IF EXISTS `system_cluster_server`;
CREATE TABLE `system_cluster_server`  (
  `serverId` bigint(20) NOT NULL AUTO_INCREMENT,
  `serverName` varchar(255)  NULL DEFAULT NULL,
  `clusterUrl` varchar(255)  NULL DEFAULT NULL,
  `serverPort` int(11) NULL DEFAULT NULL,
  `isActive` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`serverId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for system_organization
-- ----------------------------
DROP TABLE IF EXISTS `system_organization`;
CREATE TABLE `system_organization`  (
  `orgId` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) NOT NULL,
  `orgFlag` varchar(60)  NOT NULL,
  `orgName` varchar(300)  NOT NULL,
  `orgDesc` varchar(400)  NULL DEFAULT NULL,
  `isLeaf` int(11) NOT NULL,
  `layer` int(11) NOT NULL,
  `linearOrderFlag` varchar(400)  NOT NULL,
  `directorIds` varchar(500)  NULL DEFAULT NULL,
  `orgBossId` bigint(20) NOT NULL,
  `creator` varchar(80)  NULL DEFAULT NULL,
  PRIMARY KEY (`orgId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_organization
-- ----------------------------
INSERT INTO `system_organization` VALUES (1, -1, 'group', '总公司', NULL, 0, 1, '001', NULL, 104, 'system');
INSERT INTO `system_organization` VALUES (2, 1, 'gdff', 'sdf', '', 1, 2, '001001', NULL, -1, 'admin');

-- ----------------------------
-- Table structure for systemrole
-- ----------------------------
DROP TABLE IF EXISTS `systemrole`;
CREATE TABLE `systemrole`  (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `orgId` bigint(20) NOT NULL,
  `roleName` varchar(100)  NOT NULL,
  `roleDesc` varchar(200)  NULL DEFAULT NULL,
  `useState` int(6) NOT NULL,
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of systemrole
-- ----------------------------
INSERT INTO `systemrole` VALUES (83, 1, '系统管理员', '全权限', 1);

-- ----------------------------
-- Table structure for systemuser
-- ----------------------------
DROP TABLE IF EXISTS `systemuser`;
CREATE TABLE `systemuser`  (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `manageOrgId` bigint(20) NOT NULL,
  `userName` varchar(200)  NOT NULL,
  `userTrueName` varchar(300)  NOT NULL,
  `password` varchar(500)  NOT NULL,
  `remark` varchar(300)  NULL DEFAULT NULL,
  `phone` varchar(60)  NULL DEFAULT NULL,
  `email` varchar(200)  NULL DEFAULT NULL,
  `weixinName` varchar(120)  NULL DEFAULT NULL,
  `addTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `relateOrgCode` varchar(300)  NULL DEFAULT NULL,
  `creator` varchar(30)  NULL DEFAULT NULL,
  `useState` int(6) NOT NULL,
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of systemuser
-- ----------------------------
INSERT INTO `systemuser` VALUES (104, -1, 'admin', '超级管理员', '6034d621d5020f48c9047b9f467a743868e76b79b4754f8ffcf827903bfa3b2b9dbd6a77eab5c910', '系统管理员,拥有所有基础权限', '13988888888', 'admin@jtopcms.com', NULL, '2017-10-26 17:35:40', '001', 'sysAdmin', 1);

-- ----------------------------
-- Table structure for tag_relate_content
-- ----------------------------
DROP TABLE IF EXISTS `tag_relate_content`;
CREATE TABLE `tag_relate_content`  (
  `contentId` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL,
  INDEX `c-t-id`(`contentId`, `tagId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tag_relate_content
-- ----------------------------
INSERT INTO `tag_relate_content` VALUES (647, 2);
INSERT INTO `tag_relate_content` VALUES (701, 2);
INSERT INTO `tag_relate_content` VALUES (714, 2);
INSERT INTO `tag_relate_content` VALUES (808, 1);
INSERT INTO `tag_relate_content` VALUES (808, 2);
INSERT INTO `tag_relate_content` VALUES (809, 1);
INSERT INTO `tag_relate_content` VALUES (809, 2);
INSERT INTO `tag_relate_content` VALUES (810, 1);
INSERT INTO `tag_relate_content` VALUES (810, 2);
INSERT INTO `tag_relate_content` VALUES (811, 1);
INSERT INTO `tag_relate_content` VALUES (811, 2);
INSERT INTO `tag_relate_content` VALUES (830, 2);

-- ----------------------------
-- Table structure for tag_type
-- ----------------------------
DROP TABLE IF EXISTS `tag_type`;
CREATE TABLE `tag_type`  (
  `tagTypeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `tagTypeName` varchar(300)  NOT NULL,
  `flag` varchar(80)  NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`tagTypeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tag_type
-- ----------------------------
INSERT INTO `tag_type` VALUES (1, '古迹', 'guji', 5);

-- ----------------------------
-- Table structure for tag_word
-- ----------------------------
DROP TABLE IF EXISTS `tag_word`;
CREATE TABLE `tag_word`  (
  `tagId` bigint(20) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(100)  NOT NULL,
  `tagFlag` varchar(40)  NULL DEFAULT NULL,
  `tagTypeId` bigint(20) NOT NULL,
  `firstChar` varchar(5)  NULL DEFAULT NULL,
  `clickCount` bigint(20) NOT NULL,
  `contentCount` bigint(20) NOT NULL,
  `creator` varchar(80)  NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`tagId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tag_word
-- ----------------------------
INSERT INTO `tag_word` VALUES (1, '大中华', '', -9999, 'd', 0, 4, 'admin', 5);
INSERT INTO `tag_word` VALUES (2, '中国', '', 1, 'z', 0, 8, 'admin', 5);
INSERT INTO `tag_word` VALUES (3, 'asd', '', -9999, 'a', 0, 0, 'admin', 5);

-- ----------------------------
-- Table structure for template_edition
-- ----------------------------
DROP TABLE IF EXISTS `template_edition`;
CREATE TABLE `template_edition`  (
  `editionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `templateName` varchar(200)  NOT NULL,
  `filePath` varchar(400)  NOT NULL,
  `editDT` datetime NULL DEFAULT NULL,
  `editDesc` varchar(3000)  NULL DEFAULT NULL,
  `editor` bigint(20) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  `tpStyle` varchar(120)  NULL DEFAULT NULL,
  PRIMARY KEY (`editionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of template_edition
-- ----------------------------
INSERT INTO `template_edition` VALUES (1, 'list.jsp', '*sys_template_temp*list_ff8080814c18520a196014c185446e80005.jsp', '2015-03-14 20:48:38', '初始版本模板', 52, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (2, 'list.jsp', '*sys_template_temp*list_ff8080814c18520a510014c185446e80004.jsp', '2015-03-14 20:48:38', '测试版本1111', 52, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (3, 'list.jsp', '*sys_template_temp*list_ff8080814c18520a661014c1854eb9f0007.jsp', '2015-03-14 20:49:20', '还原到版本1', 52, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (11, 'head.jsp', '*sys_template_temp*head_402881e94fe97327844014fe9847cda008a.jsp', '2015-09-20 14:50:18', '初始版本模板', 96, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (12, 'head.jsp', '*sys_template_temp*head_402881e94fe97327671014fe9847cda0089.jsp', '2015-09-20 14:50:18', '', 96, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (13, 'head.jsp', '*sys_template_temp*head_402881e94fe97327613014fe987edc6009d.jsp', '2015-09-20 14:54:03', '还原到版本11', 96, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (18, 'news.jsp', '*sys_template_temp*news_402881e94fe97327841014fe98c551800dc.jsp', '2015-09-20 14:58:52', '初始版本模板', 96, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (19, 'news.jsp', '*sys_template_temp*news_402881e94fe97327834014fe98c551800db.jsp', '2015-09-20 14:58:52', '', 96, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (20, 'channel_video.jsp', '*sys_template_temp*channel_video_402881e54feb0fcf852014feb1ec1c9004a.jsp', '2015-09-20 22:18:25', '初始版本模板', 96, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (21, 'channel_video.jsp', '*sys_template_temp*channel_video_402881e54feb0fcf472014feb1ec1c90049.jsp', '2015-09-20 22:18:25', '', 96, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (22, 'channel_spec.jsp', '*sys_template_temp*channel_spec_402881e550668ab3220015066dbfb7a0151.jsp', '2015-10-14 22:58:24', '初始版本模板', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (23, 'channel_spec.jsp', '*sys_template_temp*channel_spec_402881e550668ab3536015066dbfb7a0150.jsp', '2015-10-14 22:58:24', '', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (24, 'channel_spec.jsp', '*sys_template_temp*channel_spec_402881e550668ab3737015066dc0e850154.jsp', '2015-10-14 22:58:29', '', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (25, 'channel_spec.jsp', '*sys_template_temp*channel_spec_402881e550668ab3188015066dc38730159.jsp', '2015-10-14 22:58:39', '', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (26, 'photo.jsp', '*sys_template_temp*photo_402881e550a71e46150150a738f791006d.jsp', '2015-10-27 10:55:39', '初始版本模板', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (27, 'photo.jsp', '*sys_template_temp*photo_402881e550a71e465230150a738f791006c.jsp', '2015-10-27 10:55:39', '', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (30, 'comment_list.jsp', '*sys_template_temp*comment_list_402881e550f993eb9120150faa58e6f00cd.jsp', '2015-11-12 15:42:45', '初始版本模板', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (31, 'comment_list.jsp', '*sys_template_temp*comment_list_402881e550f993eb580150faa58e6f00cc.jsp', '2015-11-12 15:42:45', 'asdasdasd', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (32, 'comment_list.jsp', '*sys_template_temp*comment_list_402881e550f993eb890150faa59a9400d0.jsp', '2015-11-12 15:42:48', 'asdasd', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (33, 'comment_list.jsp', '*sys_template_temp*comment_list_402881e550f993eb4680150faa5a35e00d4.jsp', '2015-11-12 15:42:50', 'asdasd', 97, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (38, 'member_left.jsp', '*sys_template_temp*member_left_402881e45317cdcb106015318bd51e400c9.jsp', '2016-02-25 22:02:53', '初始版本模板', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (39, 'member_left.jsp', '*sys_template_temp*member_left_402881e45317cdcb618015318bd51e400c8.jsp', '2016-02-25 22:02:53', '', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (40, 'member_left.jsp', '*sys_template_temp*member_left_402881e45317cdcb478015318c5582700e1.jsp', '2016-02-25 22:11:39', '', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (41, 'member_left.jsp', '*sys_template_temp*member_left_402881e45317cdcb56015318c596f500e9.jsp', '2016-02-25 22:11:55', '', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (42, 'member_left.jsp', '*sys_template_temp*member_left_402881e45317cdcb191015318c5b15300ed.jsp', '2016-02-25 22:12:02', '按时的按时', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (43, 'member_left.jsp', '*sys_template_temp*member_left_402881e45317cdcb484015318c5d29700f1.jsp', '2016-02-25 22:12:11', 'sasdasd', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (44, 'member_left.jsp', '*sys_template_temp*member_left_402881e45317cdcb771015318c845e900fa.jsp', '2016-02-25 22:14:51', '还原到版本39', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (45, 'member_left.jsp', '*sys_template_temp*member_left_402881e45317cdcb960015318c880de00ff.jsp', '2016-02-25 22:15:06', '还原到版本43', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (46, 'member_left.jsp', '*sys_template_temp*member_left_402881e45317cdcb866015318c8db040104.jsp', '2016-02-25 22:15:29', '还原到版本38', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (47, 'foot.jsp', '*sys_template_temp*foot_402881e45317cdcb804015318c91d3e010b.jsp', '2016-02-25 22:15:46', '初始版本模板', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (48, 'foot.jsp', '*sys_template_temp*foot_402881e45317cdcb224015318c91d3e010a.jsp', '2016-02-25 22:15:46', '', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (49, 'index_left_top.jsp', '*sys_template_temp*index_left_top_402881e45317cdcb82015318c9511f0111.jsp', '2016-02-25 22:16:00', '初始版本模板', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (50, 'index_left_top.jsp', '*sys_template_temp*index_left_top_402881e45317cdcb815015318c9511f0110.jsp', '2016-02-25 22:16:00', '', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (51, 'channel_video.jsp', '*sys_template_temp*channel_video_402881e4532826da1380153282bdf240031.jsp', '2016-02-28 21:57:57', '', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (52, 'channel.jsp', '*sys_template_temp*channel_402881e4532826da540153282bf9140036.jsp', '2016-02-28 21:58:03', '初始版本模板', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (53, 'channel.jsp', '*sys_template_temp*channel_402881e4532826da5700153282bf9140035.jsp', '2016-02-28 21:58:03', '', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (54, 'comment_list.jsp', '*sys_template_temp*comment_list_402881e4532826da2050153282c10170039.jsp', '2016-02-28 21:58:09', '', 98, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (55, 'foot.jsp', '*sys_template_temp*foot_402881e4537d30e499101537d782c09010b.jsp', '2016-03-16 11:29:00', '', 99, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (56, 'channel.jsp', '*sys_template_temp*channel_402881e4562bc14710301562bc2af180025.jsp', '2016-07-27 17:49:54', '', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (57, 'channel.jsp', '*sys_template_temp*channel_402881e4562bc14730401562bc2ed3a0029.jsp', '2016-07-27 17:50:10', '', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (58, 'guahao.jsp', '*sys_template_temp*guahao_402881e4562bc14738601562bc3c2d90042.jsp', '2016-07-27 17:51:04', '初始版本模板', 101, 5, 'yyzd');
INSERT INTO `template_edition` VALUES (59, 'guahao.jsp', '*sys_template_temp*guahao_402881e4562bc14756801562bc3c2d90041.jsp', '2016-07-27 17:51:10', '', 101, 5, 'yyzd');
INSERT INTO `template_edition` VALUES (82, 'add_def_form_data.jsp', '*sys_template_temp*add_def_form_data_402881e4562c9ea388401562ce4200400be.jsp', '2016-07-27 23:06:03', '初始版本模板', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (83, 'add_def_form_data.jsp', '*sys_template_temp*add_def_form_data_402881e4562c9ea318101562ce4200400bd.jsp', '2016-07-27 23:06:03', '', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (84, 'add_def_form_data.jsp', '*sys_template_temp*add_def_form_data_402881e4562c9ea34501562ce4283200c1.jsp', '2016-07-27 23:06:05', '', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (85, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea363401562ce8520d00cd.jsp', '2016-07-27 23:10:38', '初始版本模板', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (86, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea323201562ce8520d00cc.jsp', '2016-07-27 23:10:38', '', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (87, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea384301562ce85c9c00d0.jsp', '2016-07-27 23:10:40', '', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (88, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea359201562ce8698d00d4.jsp', '2016-07-27 23:10:44', '', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (89, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea372101562cedc04000e6.jsp', '2016-07-27 23:16:34', '初始版本模板', 101, 5, 'yyzd');
INSERT INTO `template_edition` VALUES (90, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea390501562cedc04000e5.jsp', '2016-07-27 23:16:34', '', 101, 5, 'yyzd');
INSERT INTO `template_edition` VALUES (91, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea312201562cedd45500e9.jsp', '2016-07-27 23:16:39', '', 101, 5, 'yyzd');
INSERT INTO `template_edition` VALUES (94, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea399801562d00ad6701f0.jsp', '2016-07-27 23:37:14', '还原到版本85', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (95, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea329501562d00d23501f4.jsp', '2016-07-27 23:37:23', '', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (96, 'index.jsp', '*sys_template_temp*index_402881e4562c9ea317001562d0166c401f9.jsp', '2016-07-27 23:38:01', '还原到版本85', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (101, 'add_def_form_data.jsp', '*sys_template_temp*add_def_form_data_402880e458e7ae269710158e7d394710cce.jsp', '2016-12-10 16:22:32', '', 101, 5, '_sys_def_');
INSERT INTO `template_edition` VALUES (102, 'add_def_form_data.jsp', '*sys_template_temp*add_def_form_data_402880e458e7ae261380158e7d6df5a0dcb.jsp', '2016-12-10 16:26:08', '', 101, 5, '_sys_def_');

-- ----------------------------
-- Table structure for template_helper
-- ----------------------------
DROP TABLE IF EXISTS `template_helper`;
CREATE TABLE `template_helper`  (
  `templetFileName` varchar(700)  NOT NULL DEFAULT '0',
  `templetDisplayName` varchar(90)  NULL DEFAULT NULL,
  `siteId` bigint(20) NOT NULL,
  `tpStyle` varchar(120)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of template_helper
-- ----------------------------
INSERT INTO `template_helper` VALUES ('common.js', '通用js', 5, '_sys_def_');
INSERT INTO `template_helper` VALUES ('list.jsp', '新闻列表页', 5, '_sys_def_');
INSERT INTO `template_helper` VALUES ('index.jsp', '首页', 5, '_sys_def_');
INSERT INTO `template_helper` VALUES ('vote_result.jsp', '投票结果页', 5, '_sys_def_');
INSERT INTO `template_helper` VALUES ('about.jsp', '撒旦', 5, '_sys_def_');
INSERT INTO `template_helper` VALUES ('add_def_form_data.jsp', '士大夫士大夫', 5, '_sys_def_');
INSERT INTO `template_helper` VALUES ('index.jsp', '士大夫大师傅士大夫地方', 5, 'yyzd');
INSERT INTO `template_helper` VALUES ('guestbook.jsp', '留言', 5, '_sys_def_');
INSERT INTO `template_helper` VALUES ('guahao.jsp', '画好阿大声道', 5, 'yyzd');

-- ----------------------------
-- Table structure for template_style
-- ----------------------------
DROP TABLE IF EXISTS `template_style`;
CREATE TABLE `template_style`  (
  `tsId` bigint(20) NOT NULL AUTO_INCREMENT,
  `styleName` varchar(255)  NULL DEFAULT NULL,
  `stylePic` varchar(400)  NULL DEFAULT NULL,
  `styleFlag` varchar(120)  NULL DEFAULT NULL,
  `isUse` int(11) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`tsId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_editor_module_code
-- ----------------------------
DROP TABLE IF EXISTS `tool_editor_module_code`;
CREATE TABLE `tool_editor_module_code`  (
  `emId` bigint(20) NOT NULL AUTO_INCREMENT,
  `emName` varchar(60)  NOT NULL,
  `emType` int(6) NOT NULL,
  `code` text  NULL,
  `emDesc` varchar(200)  NULL DEFAULT NULL,
  `useState` int(6) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`emId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tool_editor_module_code
-- ----------------------------
INSERT INTO `tool_editor_module_code` VALUES (22, '视频组件', 1, '<p style=\'text-align: center\'><embed id=\'jtopcms_content_media_@{resId}\' height=\'@{height}\' width=\'@{width}\' allowfullscreen=\'@{fs}\' type=\'application/x-shockwave-flash\' wmode=\'transparent\' src=\'@{basePath}core/extools/player/jwplayer/5.9/player.swf?screencolor=black&file=@{fileFullPath}&autostart=@{autostart}&allowfullscreen=@{fs}&repeat=@{repeat}\'></embed><br/><br/></p>', '支持显示视频以及视频引导图', 1, 5);
INSERT INTO `tool_editor_module_code` VALUES (23, '图片组件', 2, '<p style=\'text-align: center\'><img id=\'jtopcms_content_image_@{resId}\' name=\'jtopcms_content_image\' src=\'@{fileFullPath}\' alt=\'@{name}\'   width=\'@{width}\'/>&nbsp; </p>', '支持可编辑以及裁剪处理的图片', 1, 5);
INSERT INTO `tool_editor_module_code` VALUES (24, '附件组件', 3, '<p><a id=\'jtopcms_content_file_@{resId}\' href=\'/content/clientDf.do?id=@{resId}\' >@{name}</a><br/></p>', '使用隐藏地址以及直接地址的附件下载', 1, 5);

-- ----------------------------
-- Table structure for tool_image_ratio
-- ----------------------------
DROP TABLE IF EXISTS `tool_image_ratio`;
CREATE TABLE `tool_image_ratio`  (
  `irId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ratioName` varchar(80)  NOT NULL,
  `ratioWidth` int(80) NOT NULL,
  `ratioHeight` int(11) NOT NULL,
  `siteId` bigint(20) NOT NULL,
  PRIMARY KEY (`irId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tool_image_ratio
-- ----------------------------
INSERT INTO `tool_image_ratio` VALUES (3, '首页幻灯', 310, 220, 5);
INSERT INTO `tool_image_ratio` VALUES (4, '图集频道幻灯', 1000, 550, 5);

-- ----------------------------
-- Table structure for user_relate_role
-- ----------------------------
DROP TABLE IF EXISTS `user_relate_role`;
CREATE TABLE `user_relate_role`  (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  INDEX `ur`(`userId`, `roleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_relate_role
-- ----------------------------
INSERT INTO `user_relate_role` VALUES (64, 83);
INSERT INTO `user_relate_role` VALUES (65, 83);
INSERT INTO `user_relate_role` VALUES (104, 83);

-- ----------------------------
-- Table structure for workflow
-- ----------------------------
DROP TABLE IF EXISTS `workflow`;
CREATE TABLE `workflow`  (
  `flowId` bigint(20) NOT NULL AUTO_INCREMENT,
  `flowName` varchar(60)  NOT NULL,
  `flowDesc` varchar(260)  NULL DEFAULT NULL,
  `step` int(11) NOT NULL,
  `creator` varchar(30)  NOT NULL,
  `bypassFlag` int(11) NULL DEFAULT NULL,
  `conjunctFlag` int(11) NULL DEFAULT NULL,
  `systemHandleTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `siteId` bigint(20) NOT NULL,
  `updateDT` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`flowId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of workflow
-- ----------------------------
 

-- ----------------------------
-- Table structure for workflow_actor
-- ----------------------------
DROP TABLE IF EXISTS `workflow_actor`;
CREATE TABLE `workflow_actor`  (
  `flowId` bigint(20) NOT NULL DEFAULT 0,
  `censorStep` bigint(20) NOT NULL,
  `auditManId` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  INDEX `all`(`censorStep`, `flowId`, `auditManId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of workflow_actor
-- ----------------------------
INSERT INTO `workflow_actor` VALUES (53, 74, 1, 3);
INSERT INTO `workflow_actor` VALUES (53, 73, 1, 3);

-- ----------------------------
-- Table structure for workflow_op_trace
-- ----------------------------
DROP TABLE IF EXISTS `workflow_op_trace`;
CREATE TABLE `workflow_op_trace`  (
  `actionId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `contentId` bigint(20) NOT NULL,
  `infoType` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for workflow_oper_info
-- ----------------------------
DROP TABLE IF EXISTS `workflow_oper_info`;
CREATE TABLE `workflow_oper_info`  (
  `contentId` bigint(20) NULL DEFAULT NULL,
  `pUserName` varchar(300)  NULL DEFAULT NULL,
  `actionId` bigint(20) NULL DEFAULT NULL,
  `currStepName` varchar(300)  NULL DEFAULT NULL,
  `fromStepId` bigint(20) NULL DEFAULT NULL,
  `toStepId` bigint(20) NULL DEFAULT NULL,
  `eventDT` datetime NULL DEFAULT NULL,
  `infoType` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of workflow_oper_info
-- ----------------------------
 

-- ----------------------------
-- Table structure for workflow_operation
-- ----------------------------
DROP TABLE IF EXISTS `workflow_operation`;
CREATE TABLE `workflow_operation`  (
  `opId` bigint(20) NOT NULL AUTO_INCREMENT,
  `contentId` bigint(20) NOT NULL DEFAULT 0,
  `classId` bigint(20) NOT NULL,
  `flowId` bigint(20) NOT NULL,
  `currentStep` bigint(20) NOT NULL,
  `operStatus` int(11) NOT NULL,
  `currentAuditUser` varchar(80)  NULL DEFAULT NULL,
  `possessStatus` int(11) NOT NULL,
  `possessUserId` bigint(20) NULL DEFAULT NULL,
  `wkUpdateDT` bigint(20) NULL DEFAULT NULL,
  `infoType` int(11) NULL DEFAULT NULL,
  `startCensor` int(11) NULL DEFAULT NULL,
  `flowTarget` varchar(20)  NULL DEFAULT NULL,
  PRIMARY KEY (`opId`) USING BTREE,
  INDEX `status-step`(`operStatus`, `currentStep`) USING BTREE,
  INDEX `cid`(`contentId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of workflow_operation
-- ----------------------------
 

-- ----------------------------
-- Table structure for workflow_step_action
-- ----------------------------
DROP TABLE IF EXISTS `workflow_step_action`;
CREATE TABLE `workflow_step_action`  (
  `actionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `flowId` bigint(20) NOT NULL,
  `actDesc` varchar(600)  NULL DEFAULT NULL,
  `passActionName` varchar(80)  NOT NULL,
  `fromStepId` bigint(20) NOT NULL,
  `toStepId` bigint(20) NOT NULL,
  `fromStepNodeName` varchar(70)  NOT NULL,
  `toStepNodeName` varchar(70)  NOT NULL,
  `orgBossMode` int(6) NOT NULL,
  `conjunctOrgFlag` int(6) NOT NULL,
  `conjunctRoleFlag` int(6) NOT NULL,
  `conjunctManFlag` int(6) NOT NULL,
  `needRequest` int(11) NOT NULL,
  `recallMode` int(11) NULL DEFAULT NULL,
  `directMode` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`actionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of workflow_step_action
-- ----------------------------
 

-- ----------------------------
-- Table structure for workflow_step_info
-- ----------------------------
DROP TABLE IF EXISTS `workflow_step_info`;
CREATE TABLE `workflow_step_info`  (
  `stepId` bigint(20) NOT NULL AUTO_INCREMENT,
  `flowId` bigint(20) NOT NULL DEFAULT 0,
  `stepDesc` varchar(400)  NULL DEFAULT NULL,
  `conjunctOrgFlag` int(11) NOT NULL,
  `conjunctRoleFlag` int(6) NOT NULL,
  `conjunctManFlag` int(6) NOT NULL,
  `avoidFlag` int(11) NOT NULL,
  `stepNodeName` varchar(80)  NOT NULL,
  `isStart` int(6) NULL DEFAULT NULL,
  `mustReq` int(11) NULL DEFAULT NULL,
  `orgMode` int(11) NULL DEFAULT NULL,
  `pubDefActId` bigint(20) NULL DEFAULT NULL,
  `deleteDefActId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`stepId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of workflow_step_info
-- ----------------------------
 

-- ----------------------------
-- Table structure for wx_account
-- ----------------------------
DROP TABLE IF EXISTS `wx_account`;
CREATE TABLE `wx_account`  (
  `acId` bigint(20) NOT NULL AUTO_INCREMENT,
  `wxName` varchar(120)  NULL DEFAULT NULL,
  `appId` varchar(200)  NULL DEFAULT NULL,
  `appsSecret` varchar(200)  NULL DEFAULT NULL,
  `apiToken` varchar(60)  NULL DEFAULT NULL,
  `mainId` varchar(100)  NULL DEFAULT NULL,
  `subWelInfoId` bigint(20) NULL DEFAULT NULL,
  `subWelResId` bigint(20) NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`acId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_event_be_extend
-- ----------------------------
DROP TABLE IF EXISTS `wx_event_be_extend`;
CREATE TABLE `wx_event_be_extend`  (
  `ebId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ebName` varchar(80)  NULL DEFAULT NULL,
  `beClass` varchar(180)  NULL DEFAULT NULL,
  `eventType` varchar(50)  NULL DEFAULT NULL,
  `msgType` varchar(50)  NULL DEFAULT NULL,
  `isMsg` int(11) NULL DEFAULT NULL,
  `isMenu` int(11) NULL DEFAULT NULL,
  `isInput` int(11) NULL DEFAULT NULL,
  `useStatus` int(11) NULL DEFAULT NULL,
  `wxCode` varchar(120)  NULL DEFAULT NULL,
  PRIMARY KEY (`ebId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_menu
-- ----------------------------
DROP TABLE IF EXISTS `wx_menu`;
CREATE TABLE `wx_menu`  (
  `btId` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) NULL DEFAULT NULL,
  `mtId` bigint(20) NULL DEFAULT NULL,
  `resId` bigint(20) NULL DEFAULT NULL,
  `btLayer` int(11) NULL DEFAULT NULL,
  `btOrder` int(11) NULL DEFAULT NULL,
  `btName` varchar(50)  NULL DEFAULT NULL,
  `btType` varchar(60)  NULL DEFAULT NULL,
  `btKey` varchar(60)  NULL DEFAULT NULL,
  `mediaId` varchar(60)  NULL DEFAULT NULL,
  `btUrl` varchar(500)  NULL DEFAULT NULL,
  `msgType` int(255) NULL DEFAULT NULL,
  `behaviorClass` varchar(160)  NULL DEFAULT NULL,
  `siteFlag` varchar(60)  NULL DEFAULT NULL,
  `wxCode` varchar(60)  NULL DEFAULT NULL,
  `wxId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`btId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_message
-- ----------------------------
DROP TABLE IF EXISTS `wx_message`;
CREATE TABLE `wx_message`  (
  `msgId` bigint(20) NOT NULL AUTO_INCREMENT,
  `inputKey` varchar(400)  NULL DEFAULT NULL,
  `isInclude` int(11) NULL DEFAULT NULL,
  `isText` int(11) NULL DEFAULT NULL,
  `msgType` varchar(40)  NULL DEFAULT NULL,
  `infoId` bigint(20) NULL DEFAULT NULL,
  `resId` bigint(20) NULL DEFAULT NULL,
  `textMsg` text  NULL,
  `wxCode` varchar(60)  NULL DEFAULT NULL,
  PRIMARY KEY (`msgId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_message_unk
-- ----------------------------
DROP TABLE IF EXISTS `wx_message_unk`;
CREATE TABLE `wx_message_unk`  (
  `msgType` varchar(40)  NULL DEFAULT NULL,
  `infoId` bigint(20) NULL DEFAULT NULL,
  `resId` bigint(20) NULL DEFAULT NULL,
  `wxCode` varchar(60)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_news_info_item
-- ----------------------------
DROP TABLE IF EXISTS `wx_news_info_item`;
CREATE TABLE `wx_news_info_item`  (
  `infoId` bigint(20) NOT NULL AUTO_INCREMENT,
  `rowFlag` bigint(20) NOT NULL,
  `rowOrder` int(11) NOT NULL,
  `contentId` bigint(20) NULL DEFAULT 0,
  `resType` varchar(50)  NULL DEFAULT NULL,
  `resTag` varchar(120)  NULL DEFAULT NULL,
  `title` varchar(200)  NULL DEFAULT NULL,
  `url` varchar(300)  NULL DEFAULT NULL,
  `img` varchar(200)  NULL DEFAULT NULL,
  `showCover` int(255) NULL DEFAULT NULL,
  `summary` varchar(3000)  NULL DEFAULT NULL,
  `addTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modelId` bigint(20) NULL DEFAULT NULL,
  `classId` bigint(20) NULL DEFAULT NULL,
  `commendTypeId` bigint(20) NULL DEFAULT NULL,
  `orderFlag` int(11) NULL DEFAULT NULL,
  `typeFlag` varchar(100)  NULL DEFAULT NULL,
  `commendFlag` varchar(100)  NULL DEFAULT NULL,
  `commendMan` varchar(30)  NULL DEFAULT NULL,
  `articleText` text  NULL,
  `createTime` datetime NULL DEFAULT NULL,
  `siteFlag` varchar(50)  NULL DEFAULT NULL,
  `isTranSucc` int(11) NULL DEFAULT NULL,
  `mediaId` varchar(300)  NULL DEFAULT NULL,
  `wxCode` varchar(60)  NULL DEFAULT NULL,
  `wxId` bigint(60) NOT NULL,
  PRIMARY KEY (`infoId`) USING BTREE,
  INDEX `rfro`(`commendFlag`, `rowFlag`, `rowOrder`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_news_type
-- ----------------------------
DROP TABLE IF EXISTS `wx_news_type`;
CREATE TABLE `wx_news_type`  (
  `commendTypeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `siteFlag` varchar(60)  NOT NULL,
  `isSpec` int(11) NOT NULL,
  `classLinerFlag` varchar(400)  NULL DEFAULT NULL,
  `classId` bigint(20) NOT NULL,
  `commendName` varchar(60)  NOT NULL,
  `commFlag` varchar(30)  NOT NULL,
  `typeDesc` varchar(400)  NULL DEFAULT NULL,
  `listTemplateUrl` varchar(120)  NULL DEFAULT NULL,
  `listProduceType` int(11) NOT NULL,
  `listPublishRuleId` bigint(20) NULL DEFAULT NULL,
  `listStaticUrl` varchar(150)  NULL DEFAULT NULL,
  `childClassMode` int(11) NOT NULL,
  `mustCensor` int(11) NOT NULL,
  `creator` varchar(80)  NULL DEFAULT NULL,
  `imageWidth` int(11) NULL DEFAULT NULL,
  `imageHeight` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`commendTypeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_resource
-- ----------------------------
DROP TABLE IF EXISTS `wx_resource`;
CREATE TABLE `wx_resource`  (
  `wrId` bigint(20) NOT NULL AUTO_INCREMENT,
  `resName` varchar(120)  NULL DEFAULT NULL,
  `resTitle` varchar(200)  NULL DEFAULT NULL,
  `resDesc` varchar(500)  NULL DEFAULT NULL,
  `imageRes` varchar(500)  NULL DEFAULT NULL,
  `videoRes` varchar(500)  NULL DEFAULT NULL,
  `voiceRes` varchar(500)  NULL DEFAULT NULL,
  `musicRes` varchar(500)  NULL DEFAULT NULL,
  `resType` varchar(15)  NULL DEFAULT NULL,
  `resTag` varchar(100)  NULL DEFAULT NULL,
  `isTranSucc` int(11) NULL DEFAULT NULL,
  `tranDT` datetime NULL DEFAULT NULL,
  `musicUrl` varchar(300)  NULL DEFAULT NULL,
  `hqMusicUrl` varchar(300)  NULL DEFAULT NULL,
  `musicThumb` varchar(500)  NULL DEFAULT '',
  `thumbMediaId` varchar(350)  NULL DEFAULT NULL,
  `thumbUrl` varchar(350)  NULL DEFAULT NULL,
  `resContent` text  NULL,
  `mediaId` varchar(350)  NULL DEFAULT NULL,
  `mediaImgUrl` varchar(350)  NULL DEFAULT NULL,
  `wxCode` varchar(60)  NULL DEFAULT NULL,
  `siteId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`wrId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_resource_tag
-- ----------------------------
DROP TABLE IF EXISTS `wx_resource_tag`;
CREATE TABLE `wx_resource_tag`  (
  `rtId` bigint(20) NOT NULL AUTO_INCREMENT,
  `resTagName` varchar(80)  NULL DEFAULT NULL,
  `resType` varchar(50)  NULL DEFAULT NULL,
  `wxCode` varchar(120)  NULL DEFAULT NULL,
  PRIMARY KEY (`rtId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_send_all_info
-- ----------------------------
DROP TABLE IF EXISTS `wx_send_all_info`;
CREATE TABLE `wx_send_all_info`  (
  `saId` bigint(20) NOT NULL AUTO_INCREMENT,
  `msgType` varchar(120)  NULL DEFAULT NULL,
  `msgId` bigint(20) NULL DEFAULT NULL,
  `exeMan` varchar(255)  NULL DEFAULT NULL,
  `exeTime` datetime NULL DEFAULT NULL,
  `sendTarget` int(11) NULL DEFAULT NULL,
  `isSend` int(11) NULL DEFAULT NULL,
  `sendDT` datetime NULL DEFAULT NULL,
  `censor` int(11) NULL DEFAULT NULL,
  `returnMsg` varchar(300)  NULL DEFAULT NULL,
  `wxCode` varchar(60)  NOT NULL,
  `wxId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`saId`) USING BTREE,
  INDEX `key1`(`wxCode`, `censor`, `exeMan`) USING BTREE,
  INDEX `key2`(`wxCode`, `isSend`, `exeMan`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user`  (
  `openId` varchar(120)  NOT NULL,
  `wuCity` varchar(200)  NULL DEFAULT NULL,
  `wuCountry` varchar(200)  NULL DEFAULT '',
  `wuGroupid` varchar(50)  NULL DEFAULT NULL,
  `wuHeadimgurl` varchar(400)  NULL DEFAULT NULL,
  `wuLanguage` varchar(50)  NULL DEFAULT NULL,
  `wuNickname` varchar(120)  NULL DEFAULT NULL,
  `wuProvince` varchar(100)  NULL DEFAULT NULL,
  `wuRemark` varchar(2000)  NULL DEFAULT NULL,
  `wuSex` varchar(50)  NULL DEFAULT NULL,
  `wuSubscribe_time` varchar(60)  NULL DEFAULT NULL,
  `wuUnionid` varchar(120)  NULL DEFAULT NULL,
  `wuTagid_list` varchar(300)  NULL DEFAULT NULL,
  `isPrev` int(11) NULL DEFAULT NULL,
  `wxCode` varchar(120)  NULL DEFAULT NULL,
  `subStatus` int(11) NULL DEFAULT NULL,
  `userRemark` varchar(3000)  NULL DEFAULT NULL,
  PRIMARY KEY (`openId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wx_user_group
-- ----------------------------
DROP TABLE IF EXISTS `wx_user_group`;
CREATE TABLE `wx_user_group`  (
  `wuGroupId` varchar(20)  NULL DEFAULT NULL,
  `wuGroupName` varchar(120)  NULL DEFAULT NULL,
  `wuUserCount` varchar(20)  NULL DEFAULT NULL,
  `wxCode` varchar(120)  NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

 
