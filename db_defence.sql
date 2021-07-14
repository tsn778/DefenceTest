# Host: localhost  (Version 5.7.21-log)
# Date: 2021-07-14 13:29:09
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "group_sort"
#

CREATE TABLE `group_sort` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `group_student_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '学生id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `time_period` int(11) DEFAULT NULL COMMENT '时间段：1为上午，2为下午',
  `defence_time` varchar(255) DEFAULT NULL COMMENT '答辩时间',
  `defence_place` varchar(255) DEFAULT NULL COMMENT '答辩地点',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='学生排序';

#
# Data for table "group_sort"
#

INSERT INTO `group_sort` VALUES (1,1,3,3,NULL,NULL,NULL),(2,1,6,1,NULL,NULL,NULL),(3,1,9,5,NULL,NULL,NULL),(4,1,10,1,NULL,NULL,NULL),(5,1,11,2,NULL,NULL,NULL),(6,35,14,3,2,'2021-04-29 14:30:00','8号楼312'),(7,30,16,2,2,'2021-04-29 14:00:00','8号楼312'),(8,45,8,1,2,'2021-06-22 14:00:00','8号楼312'),(9,42,12,1,1,'2021-06-22 09:00:00','8号楼312'),(10,41,11,3,2,'2021-06-22 15:00:00','8号楼312'),(11,46,7,2,2,'2021-06-22 14:30:00','8号楼312'),(12,48,18,1,2,'2021-04-29 13:30:00','8号楼312'),(13,47,17,1,1,'2021-04-29 09:00:00','8号楼312'),(14,49,7,NULL,2,NULL,NULL),(15,54,7,NULL,2,NULL,NULL),(16,52,8,NULL,2,NULL,NULL),(17,53,11,NULL,1,NULL,NULL),(18,55,12,NULL,2,NULL,NULL),(19,59,10,1,1,'2021-07-21 09:00:00',NULL),(20,60,26,2,1,'2021-07-21 09:30:00',NULL);

#
# Structure for table "group_student"
#

CREATE TABLE `group_student` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL COMMENT '分组id',
  `group_name` varchar(255) DEFAULT NULL COMMENT '分组名称',
  `user_id` int(11) DEFAULT NULL COMMENT '学生id',
  `teacher_id` int(11) DEFAULT NULL COMMENT '教师id',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `achievement` varchar(255) DEFAULT NULL COMMENT '成绩',
  `status` int(11) DEFAULT NULL COMMENT '状态:1为正常，0为删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `test` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='学生具体分组情况';

#
# Data for table "group_student"
#

INSERT INTO `group_student` VALUES (6,4,NULL,10,NULL,NULL,NULL,1,NULL,'2021-04-19 23:26:37',424),(30,5,NULL,16,1,NULL,NULL,1,'2021-05-20 16:54:18','2021-06-26 13:14:38',NULL),(34,8,NULL,7,NULL,NULL,NULL,NULL,'2021-05-21 13:40:48','2021-05-21 13:40:48',NULL),(35,5,NULL,14,2,NULL,NULL,NULL,'2021-05-21 14:00:02','2021-06-26 13:14:39',NULL),(47,5,NULL,17,22,1,NULL,1,'2021-05-25 18:45:18','2021-06-26 13:14:24',NULL),(48,5,NULL,18,22,NULL,NULL,1,'2021-05-25 18:45:21','2021-06-26 13:14:33',NULL),(52,3,NULL,8,NULL,NULL,NULL,NULL,'2021-06-05 16:09:32','2021-06-05 16:09:32',NULL),(53,3,NULL,11,NULL,NULL,NULL,NULL,'2021-06-05 16:09:34','2021-06-05 16:09:34',NULL),(54,3,NULL,7,NULL,NULL,NULL,NULL,'2021-06-05 16:09:35','2021-06-05 16:09:35',NULL),(55,3,NULL,12,NULL,NULL,NULL,NULL,'2021-06-05 16:09:36','2021-06-05 16:09:36',NULL),(59,25,NULL,10,20,1,NULL,0,'2021-07-12 14:22:59','2021-07-12 15:04:37',NULL),(60,25,NULL,26,1,1,NULL,0,'2021-07-12 14:22:59','2021-07-12 15:04:47',NULL);

#
# Structure for table "group_teacher"
#

CREATE TABLE `group_teacher` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL COMMENT '分组id',
  `group_name` varchar(255) DEFAULT NULL COMMENT '分组名称',
  `user_id` int(11) DEFAULT NULL COMMENT '教师id',
  `status` int(11) DEFAULT NULL COMMENT '状态:1为正常，0为删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='老师分组情况';

#
# Data for table "group_teacher"
#

INSERT INTO `group_teacher` VALUES (1,5,'3233',1,NULL,NULL,NULL),(2,5,'343',2,NULL,NULL,NULL),(3,5,'23',3,NULL,NULL,NULL),(4,5,'3434',4,NULL,NULL,NULL),(8,3,'组1',19,1,'2021-04-21 14:13:30','2021-04-21 14:13:30'),(10,3,'组1',20,1,'2021-04-21 14:18:14','2021-04-21 14:18:14'),(11,3,'组1',21,1,'2021-05-07 17:27:15','2021-05-07 17:27:15'),(12,4,'asd',19,NULL,NULL,NULL),(13,2,'组1',22,1,'2021-06-03 19:52:35','2021-06-03 19:52:35'),(14,5,'组1',22,1,'2021-06-03 20:00:17','2021-06-03 20:00:17'),(15,3,'组1',2,1,'2021-06-04 14:49:52','2021-06-04 14:49:52'),(16,5,'组1',23,1,'2021-06-30 16:11:00','2021-06-30 16:11:00'),(17,5,'组1',22,1,'2021-06-30 16:11:12','2021-06-30 16:11:12'),(18,5,'组1',24,1,'2021-07-11 17:15:26','2021-07-11 17:15:26'),(19,23,'2025信息工程学院组3',27,NULL,'2021-07-12 14:14:28','2021-07-12 14:14:28'),(20,23,'2025信息工程学院组3',21,NULL,'2021-07-12 14:14:28','2021-07-12 14:14:28'),(21,24,'2025信息工程学院组4',20,1,'2021-07-12 14:18:47','2021-07-12 14:18:47'),(22,24,'2025信息工程学院组4',19,1,'2021-07-12 14:18:47','2021-07-12 14:18:47'),(23,25,'2022信息工程学院组4',20,1,'2021-07-12 14:21:10','2021-07-12 14:21:10'),(24,25,'2022信息工程学院组4',1,1,'2021-07-12 14:21:10','2021-07-12 14:21:10'),(25,25,'组1',19,1,'2021-07-12 15:31:39','2021-07-12 15:31:39'),(26,25,'组1',21,1,'2021-07-12 15:31:39','2021-07-12 15:31:39');

#
# Structure for table "institute"
#

CREATE TABLE `institute` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='学院';

#
# Data for table "institute"
#

INSERT INTO `institute` VALUES (1,'信息工程学院'),(2,'人文学院'),(3,'机械工程与自动化学院'),(4,'经法学院'),(5,'管理学院'),(6,'设计艺术学院'),(7,'生命科学与材料化学学院'),(8,'建筑工程学院');

#
# Structure for table "permission"
#

CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='权限';

#
# Data for table "permission"
#

INSERT INTO `permission` VALUES (1,'look','查看',NULL,NULL,NULL),(2,'select','查询',NULL,NULL,NULL),(3,'upload','上传',NULL,NULL,NULL),(4,'sort','排序',NULL,NULL,NULL),(5,'review','审阅',NULL,NULL,NULL),(6,'manage','管理',NULL,NULL,NULL);

#
# Structure for table "record"
#

CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '学号',
  `group_id` int(11) DEFAULT NULL,
  `user_thesis` varchar(255) DEFAULT '' COMMENT '论文内容',
  `user_ppt` varchar(255) DEFAULT NULL COMMENT 'ppt',
  `user_video` varchar(255) DEFAULT NULL COMMENT '演示视频',
  `user_addr` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `user_data` varchar(255) DEFAULT '' COMMENT '相关资料',
  `user_record` text COMMENT '答辩记录',
  `deleted` int(10) DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Data for table "record"
#

INSERT INTO `record` VALUES (1,17,5,'人文学院/2021/论文/组3/184173117 陶宋楠 形势与政策.doc','人文学院/2021/PPT/游戏资讯网站策划.pptx','人文学院/2021/视频/184173117.mp4',NULL,'人文学院/2021/材料/5/实验8.zip',NULL,NULL);

#
# Structure for table "role"
#

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色';

#
# Data for table "role"
#

INSERT INTO `role` VALUES (1,'admin',NULL,NULL,NULL),(2,'leader',NULL,NULL,NULL),(3,'secretary',NULL,NULL,NULL),(4,'instructor',NULL,NULL,NULL),(5,'student',NULL,NULL,NULL),(6,'sysadmin',NULL,NULL,NULL);

#
# Structure for table "role_permission"
#

CREATE TABLE `role_permission` (
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "role_permission"
#

INSERT INTO `role_permission` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(2,2),(2,3),(2,5),(3,2),(3,3),(3,4),(3,5),(4,5),(5,3),(1,6);

#
# Structure for table "router"
#

CREATE TABLE `router` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "router"
#


#
# Structure for table "sys_token"
#

CREATE TABLE `sys_token` (
  `user_id` int(11) NOT NULL,
  `expire_time` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "sys_token"
#

INSERT INTO `sys_token` VALUES (1,'2021-07-13 00:39:30','1a60dcf8530ded359808029608d2e42d','2021-07-12 12:39:30'),(2,'2021-06-27 00:53:59','d33f6bcecb883050cadc60bf87278bed','2021-06-26 12:53:59'),(3,'2021-06-02 05:46:16','1a45e22271dac8e80da98b6f1f023704','2021-06-01 17:46:16'),(16,'2021-06-27 01:17:26','892965505f006480e5fc6abf7c361433','2021-06-26 13:17:26'),(17,'2021-06-27 01:18:49','3b09c02ef7465c356bc5961491ae5e86','2021-06-26 13:18:49'),(19,'2021-06-04 08:59:35','eae2108efe9294dd8d7b17f51536540c','2021-06-03 20:59:35'),(20,'2021-06-04 11:29:38','8ace31d30d2cf33a44898d20aed6537e','2021-06-03 23:29:38'),(21,'2021-06-04 09:08:37','cba532ad11406fc1071b9f4090287600','2021-06-03 21:08:37'),(26,'2021-07-13 02:54:28','766e5e4c5e2a3029a6212d4b4339c962','2021-07-12 14:54:28');

#
# Structure for table "tb_group"
#

CREATE TABLE `tb_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) DEFAULT NULL COMMENT '分组名称',
  `institute_id` int(11) DEFAULT NULL COMMENT '学院id',
  `grade_year` varchar(255) DEFAULT NULL COMMENT '年级',
  `group_leader_id` int(11) DEFAULT NULL COMMENT '组长id',
  `group_secretary_id` int(11) DEFAULT NULL COMMENT '秘书id',
  `status` int(11) DEFAULT NULL COMMENT '状态;1为正常状态；2为失效',
  `action` int(11) DEFAULT NULL COMMENT '答辩开始：空未开始，1开始，2暂停，3结束',
  `group_defence_time` varchar(255) DEFAULT NULL COMMENT '小组具体答辩时间',
  `group_defence_place` varchar(255) DEFAULT NULL COMMENT '小组答辩地址',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='分组';

#
# Data for table "tb_group"
#

INSERT INTO `tb_group` VALUES (3,'组1',1,'2021',19,20,NULL,NULL,'2021-06-22','8号楼312',NULL,NULL),(4,'组2',1,'2021',23,24,NULL,NULL,'2021-06-22','8号楼312','2021-04-18 22:21:52','2021-04-18 22:21:52'),(5,'组3',2,'2021',2,1,NULL,2,'2021-04-29','8号楼312','2021-04-18 22:32:06','2021-07-11 17:16:43'),(6,'组4',1,'2021',23,24,NULL,NULL,'2021-05-28','8号楼301','2021-05-11 23:40:42','2021-05-11 23:40:42'),(7,'组5',1,'2021',23,24,NULL,NULL,'2021-05-30','8号楼306','2021-05-11 23:46:45','2021-05-11 23:46:45'),(8,'组6',1,'2021',23,24,NULL,NULL,'2021-06-22','8号楼308','2021-05-11 23:47:42','2021-05-11 23:47:42'),(10,'组1',NULL,'2021',21,22,NULL,NULL,'2021-05-26','56','2021-05-18 14:16:24','2021-05-18 14:16:24'),(11,'组1',NULL,'2022',21,22,NULL,NULL,'2021-05-21','56','2021-05-18 14:17:13','2021-05-18 14:17:13'),(12,'2021信息工程学院组5',1,'2021',2,5,NULL,NULL,'2021-07-14',NULL,'2021-07-12 13:01:50','2021-07-12 13:01:50'),(13,'2022信息工程学院组1',1,'2022',21,27,NULL,NULL,'2021-07-16',NULL,'2021-07-12 13:28:19','2021-07-12 13:28:19'),(14,'2025信息工程学院组1',1,'2025',21,27,NULL,NULL,'2021-07-16',NULL,'2021-07-12 13:29:19','2021-07-12 13:29:19'),(15,'2022信息工程学院组2',1,'2022',27,20,NULL,NULL,'2021-07-15',NULL,'2021-07-12 13:34:32','2021-07-12 13:34:32'),(16,'2022信息工程学院组3',1,'2022',27,20,NULL,NULL,'2021-07-15',NULL,'2021-07-12 13:39:04','2021-07-12 13:39:04'),(17,'2028信息工程学院组1',1,'2028',20,27,NULL,NULL,'2021-07-14',NULL,'2021-07-12 13:39:28','2021-07-12 13:39:28'),(18,'2028信息工程学院组2',1,'2028',20,27,NULL,NULL,'2021-07-14',NULL,'2021-07-12 13:41:37','2021-07-12 13:41:37'),(19,'2028信息工程学院组3',1,'2028',20,27,NULL,NULL,'2021-07-14',NULL,'2021-07-12 13:41:48','2021-07-12 13:41:48'),(20,'2028信息工程学院组4',1,'2028',20,27,NULL,NULL,'2021-07-14',NULL,'2021-07-12 13:43:55','2021-07-12 13:43:55'),(22,'2025信息工程学院组2',1,'2025',20,1,NULL,0,'2021-07-14',NULL,'2021-07-12 13:46:26','2021-07-12 13:46:26'),(23,'2025信息工程学院组3',1,'2025',27,21,NULL,0,'2021-07-22',NULL,'2021-07-12 14:14:28','2021-07-12 14:14:28'),(24,'2025信息工程学院组4',1,'2025',20,19,NULL,NULL,'2021-07-21',NULL,'2021-07-12 14:18:47','2021-07-12 14:18:47'),(25,'2022信息工程学院组4',1,'2022',20,1,NULL,3,'2021-07-21',NULL,'2021-07-12 14:21:10','2021-07-12 15:04:53');

#
# Structure for table "user"
#

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `institute` int(11) DEFAULT NULL COMMENT '学院',
  `user_class` varchar(255) DEFAULT NULL COMMENT '班级',
  `state` int(11) DEFAULT NULL COMMENT '类型：0为需要参加的人；1为不用答辩的人',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `deleted` int(11) DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES (1,'1001','123','12','学院管理员',1,NULL,1,NULL,NULL,NULL),(2,'1002','123',NULL,'1组教师组长',1,NULL,1,NULL,NULL,NULL),(3,'1003','123',NULL,'学生',1,NULL,2,NULL,NULL,NULL),(4,'1004','123',NULL,'2323',1,NULL,1,NULL,NULL,NULL),(5,'1005','123',NULL,'34',1,NULL,1,NULL,NULL,NULL),(6,'1006','123456',NULL,NULL,1,NULL,2,NULL,NULL,NULL),(7,'100001','123456',NULL,NULL,1,NULL,2,NULL,NULL,NULL),(8,'100002','123456',NULL,NULL,1,NULL,2,NULL,NULL,NULL),(9,'100003','123456',NULL,'林三',1,NULL,2,NULL,NULL,NULL),(10,'100004','123456',NULL,NULL,1,NULL,2,NULL,NULL,NULL),(11,'100005','123456',NULL,NULL,1,NULL,2,NULL,NULL,NULL),(12,'100006','123456',NULL,NULL,1,NULL,2,NULL,NULL,NULL),(13,'100007','123456',NULL,NULL,2,NULL,2,NULL,NULL,NULL),(14,'100008','123456',NULL,NULL,2,NULL,2,NULL,NULL,NULL),(15,'100009','123456',NULL,NULL,2,NULL,2,NULL,NULL,NULL),(16,'100010','123456',NULL,NULL,2,NULL,2,NULL,NULL,NULL),(17,'100011','123456',NULL,NULL,2,NULL,2,NULL,NULL,NULL),(18,'100012','123456',NULL,NULL,2,NULL,2,NULL,NULL,NULL),(19,'10001','111111',NULL,'张申',1,NULL,1,NULL,NULL,NULL),(20,'10002','111111',NULL,'王五',1,NULL,1,NULL,NULL,NULL),(21,'10003','111111',NULL,'赵位',1,NULL,1,NULL,NULL,NULL),(22,'10004','111111',NULL,'李索',2,NULL,1,NULL,NULL,NULL),(23,'10005','111111',NULL,'黄璇',2,NULL,1,NULL,NULL,NULL),(24,'10006','111111',NULL,'吴晓',2,NULL,1,NULL,NULL,NULL),(25,'9324949','111111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,'100024','111111',NULL,'测试2',1,NULL,2,'2021-07-09 15:37:16',NULL,NULL),(27,'10014','123456',NULL,'测试3',1,NULL,1,'2021-07-09 16:50:16',NULL,NULL);

#
# Structure for table "user_info"
#

CREATE TABLE `user_info` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL COMMENT '学号或教工号',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `institute_id` int(11) DEFAULT NULL COMMENT '学院',
  `grade_year` varchar(255) DEFAULT NULL COMMENT '结业年级',
  `major` varchar(255) DEFAULT NULL COMMENT '专业',
  `user_class` varchar(255) DEFAULT NULL COMMENT '用户班级',
  `thesis_title` varchar(255) DEFAULT NULL COMMENT '论文题目，学生需要',
  `user_Instructor` varchar(255) DEFAULT NULL COMMENT '指导老师',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

#
# Data for table "user_info"
#

INSERT INTO `user_info` VALUES (1,'100001','杨一',7,1,'2021','计算机','计算机1','网络工程','张申',1),(2,'100002','宋二',8,1,'2021','计算机','计算机2','信息安全','张申',1),(3,'100003','林三',9,1,'2021','电信','电信1','电信','王五',0),(4,'100004','赵武',10,1,'2022','软工','软工1','APP','王五',1),(5,'100005','李卅',11,1,'2021','软工','软工2','客户端','赵位',1),(6,'100006','齐成',12,1,'2021','电信','电信2','电信','赵位',1),(7,'100007','张迪',13,2,'2021','汉语言','汉语言1','民俗','李索',0),(8,'100008','颜中',14,2,'2021','汉语言','汉语言2','古代文学','李索',1),(9,'100009','许南',15,2,'2022','哲学','哲学1','唯物','黄璇',0),(10,'100010','叶婷',16,2,'2021','哲学','哲学2','唯心','黄璇',1),(11,'100011','陈岳',17,2,'2021','社会','社会1','社会调查','吴晓',1),(12,'100012','王坤',18,2,'2021','社会','社会2','材料分析','吴晓',1),(13,'100029','测试',NULL,1,'2021-12-31T16:00:00.000Z',NULL,NULL,NULL,NULL,NULL),(15,'100024','测试2',26,1,'2022',NULL,NULL,NULL,NULL,1);

#
# Structure for table "user_role"
#

CREATE TABLE `user_role` (
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user_role"
#

INSERT INTO `user_role` VALUES (1,1),(2,2),(3,5),(17,5),(19,2),(21,4),(26,5),(21,2),(27,2),(20,2),(27,3),(1,3),(21,3),(19,3);
