
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for attd_approve_info
-- ----------------------------
DROP TABLE IF EXISTS `attd_approve_info`;
CREATE TABLE `attd_approve_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` int(11) NOT NULL COMMENT '所属员工-提交审批人',
  `approve_type` int(11) NOT NULL COMMENT '审批事件类型 1-请假申请 2-调休申请 3-加班申请',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `approve_state` int(11) NOT NULL COMMENT '审批状态 0-等待下一级审批 1-审批通过 2-拒绝申请',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `refuse_reason` varchar(255) DEFAULT NULL COMMENT '拒绝原因(拒绝申请-必填)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attd_approve_info
-- ----------------------------

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` int(11) NOT NULL COMMENT '所属员工',
  `attd_state` int(11) NOT NULL COMMENT '考勤状态 - 0-缺勤(旷工) - 1-正常 2-迟到 3-请假 4-调休',
  `create_time` date DEFAULT NULL COMMENT '考核日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES ('3', '1', '1', '2018-03-25');
INSERT INTO `attendance` VALUES ('4', '1', '1', '2018-03-25');
INSERT INTO `attendance` VALUES ('5', '1', '1', '2018-03-25');

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` int(11) NOT NULL COMMENT '所属员工',
  `contract_url` varchar(255) NOT NULL COMMENT '合同存储的路径',
  `contract_name` varchar(255) NOT NULL COMMENT '合同文件名称',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user_id` int(11) NOT NULL COMMENT '修改用户的ID',
  `modify_time` datetime NOT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contract
-- ----------------------------
INSERT INTO `contract` VALUES ('8', '9', 'F:/graduationdoc/empContract/香妃1ee.docx', '香妃1ee.docx', '1', '2018-03-25 15:25:44', '1', '2018-03-25 15:25:44');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_code` varchar(255) NOT NULL COMMENT '部门编码',
  `deptname` varchar(255) NOT NULL COMMENT '部门名称',
  `deptinfo` varchar(255) DEFAULT NULL COMMENT '部门信息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_dis` int(11) NOT NULL DEFAULT '1' COMMENT '是否能直接分配所属工作-1-分配，2-不分配',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '01', '总经办', null, '2018-03-07 16:53:34', '1');
INSERT INTO `department` VALUES ('2', '02', '研发中心', null, '2018-03-07 16:54:08', '1');
INSERT INTO `department` VALUES ('3', '03', '项目管理部', null, '2018-03-07 16:54:13', '2');
INSERT INTO `department` VALUES ('4', '04', '财务部', null, '2018-03-07 16:54:46', '1');
INSERT INTO `department` VALUES ('5', '05', '人事行政部', null, '2018-03-07 16:54:52', '1');
INSERT INTO `department` VALUES ('6', '06', '市场部', null, '2018-03-07 16:55:08', '1');
INSERT INTO `department` VALUES ('7', '0205', '运维部', '隶属于研发中心', '2018-03-07 17:10:51', '1');
INSERT INTO `department` VALUES ('8', '0210', '研发一部', '隶属于研发中心', '2018-03-07 17:11:00', '1');
INSERT INTO `department` VALUES ('9', '0211', '研发二部', '隶属于研发中心', '2018-03-07 17:11:07', '1');
INSERT INTO `department` VALUES ('10', '0212', '研发三部', '隶属于研发中心', '2018-03-07 17:11:15', '1');
INSERT INTO `department` VALUES ('11', '0301', '项目管理部', '隶属于项目管理部', '2018-03-07 17:11:37', '1');
INSERT INTO `department` VALUES ('12', '0302', '测试部', '隶属于项目管理部', '2018-03-07 17:11:50', '1');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_code` varchar(255) NOT NULL COMMENT '员工编码',
  `loginname` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `realname` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `entry_time` date NOT NULL COMMENT '入职时间',
  `jobpos_id` int(11) NOT NULL COMMENT '所属职位',
  `register_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '001', 'admin', '123456', 'guo', '2018-03-09', '1', '2018-03-09 11:38:41');
INSERT INTO `employee` VALUES ('2', '002', 'test1', 'test1', 'test1', '2018-03-07', '17', '2018-03-10 23:59:00');
INSERT INTO `employee` VALUES ('3', '003', 'test2', 'test2', 'test2', '2018-03-24', '9', '2018-03-23 22:17:07');
INSERT INTO `employee` VALUES ('9', '004', '香妃1ee', '香妃1ee', '香妃1ee', '2018-03-13', '2', '2018-03-25 15:25:30');

-- ----------------------------
-- Table structure for emp_friend
-- ----------------------------
DROP TABLE IF EXISTS `emp_friend`;
CREATE TABLE `emp_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id1` int(11) NOT NULL COMMENT '用户关系表述1',
  `emp_id2` int(11) NOT NULL COMMENT '用户关系表述2',
  `fri_state` int(11) NOT NULL DEFAULT '0' COMMENT '关系状态 0-正常 1-屏蔽',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of emp_friend
-- ----------------------------

-- ----------------------------
-- Table structure for emp_friend_info
-- ----------------------------
DROP TABLE IF EXISTS `emp_friend_info`;
CREATE TABLE `emp_friend_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_fri_id` int(11) NOT NULL COMMENT '关联交互身份信息主键',
  `emp_id` int(11) NOT NULL COMMENT '聊天记录的发送者',
  `chat_info` varchar(255) NOT NULL COMMENT '聊天内容 - 不能为空',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '聊天记录生成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of emp_friend_info
-- ----------------------------

-- ----------------------------
-- Table structure for jobpos
-- ----------------------------
DROP TABLE IF EXISTS `jobpos`;
CREATE TABLE `jobpos` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `jobpos_name` varchar(255) NOT NULL COMMENT '职位名称',
  `jobpos_code` varchar(255) NOT NULL COMMENT '职位编码',
  `jobpos_level` varchar(255) NOT NULL COMMENT '职位层级',
  `dept_id` int(11) NOT NULL COMMENT '所属部门',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jobpos
-- ----------------------------
INSERT INTO `jobpos` VALUES ('1', '董事长', '0101', 'D2', '1', '2018-03-07 17:07:08');
INSERT INTO `jobpos` VALUES ('2', '董事长助理', '010101', 'B3', '1', '2018-03-07 17:07:51');
INSERT INTO `jobpos` VALUES ('3', '副董事长', '0102', 'D1', '1', '2018-03-07 17:08:05');
INSERT INTO `jobpos` VALUES ('4', '副董事长助理', '010201', 'B2', '1', '2018-03-07 17:08:12');
INSERT INTO `jobpos` VALUES ('5', '总经理', '0103', 'C3', '1', '2018-03-07 17:08:31');
INSERT INTO `jobpos` VALUES ('6', '总经理助理', '010301', 'B2', '1', '2018-03-07 17:08:57');
INSERT INTO `jobpos` VALUES ('7', '副总经理', '0104', 'C2', '1', '2018-03-07 17:09:10');
INSERT INTO `jobpos` VALUES ('8', '副总经理助理', '010401', 'B1', '1', '2018-03-07 17:09:25');
INSERT INTO `jobpos` VALUES ('9', '技术总监', '0201', 'C2', '2', '2018-03-07 17:09:36');
INSERT INTO `jobpos` VALUES ('10', '技术副总监', '0202', 'C1', '2', '2018-03-07 17:13:48');
INSERT INTO `jobpos` VALUES ('11', '运维部部门经理', '020501', 'B2', '7', '2018-03-07 17:14:22');
INSERT INTO `jobpos` VALUES ('12', '运维部部门职员-见习', '020500001', 'A1', '7', '2018-03-07 17:16:39');
INSERT INTO `jobpos` VALUES ('13', '运维部部门职员-初级', '020500002', 'A2', '7', '2018-03-07 17:17:09');
INSERT INTO `jobpos` VALUES ('14', '运维部部门职员-中级', '020500003', 'A3', '7', '2018-03-07 17:17:11');
INSERT INTO `jobpos` VALUES ('15', '运维部部门职员-高级', '020500004', 'A4', '7', '2018-03-07 17:17:13');
INSERT INTO `jobpos` VALUES ('16', '运维部部门职员-专家', '020500005', 'A5', '7', '2018-03-07 17:17:14');
INSERT INTO `jobpos` VALUES ('17', '研发一部部门经理', '021001', 'B2', '8', '2018-03-07 17:18:18');
INSERT INTO `jobpos` VALUES ('18', '研发一部部门职员-见习', '021000001', 'A1', '8', '2018-03-07 17:18:59');
INSERT INTO `jobpos` VALUES ('19', '研发一部部门职员-初级', '021000002', 'A2', '8', '2018-03-07 17:19:04');
INSERT INTO `jobpos` VALUES ('20', '研发一部部门职员-中级', '021000003', 'A3', '8', '2018-03-07 17:19:04');
INSERT INTO `jobpos` VALUES ('21', '研发一部部门职员-高级', '021000004', 'A4', '8', '2018-03-07 17:19:05');
INSERT INTO `jobpos` VALUES ('22', '研发一部部门职员-专家', '021000005', 'A5', '8', '2018-03-07 17:19:06');
INSERT INTO `jobpos` VALUES ('23', '研发二部部门经理', '021101', 'B2', '9', '2018-03-07 17:20:48');
INSERT INTO `jobpos` VALUES ('24', '研发二部部门职员-见习', '021100001', 'A1', '9', '2018-03-07 17:20:49');
INSERT INTO `jobpos` VALUES ('25', '研发二部部门职员-初级', '021100002', 'A2', '9', '2018-03-07 17:20:49');
INSERT INTO `jobpos` VALUES ('26', '研发二部部门职员-中级', '021100003', 'A3', '9', '2018-03-07 17:20:50');
INSERT INTO `jobpos` VALUES ('27', '研发二部部门职员-高级', '021100004', 'A4', '9', '2018-03-07 17:20:50');
INSERT INTO `jobpos` VALUES ('28', '研发二部部门职员-专家', '021100005', 'A5', '9', '2018-03-07 17:20:56');
INSERT INTO `jobpos` VALUES ('29', '研发三部部门经理', '021201', 'B2', '10', '2018-03-07 17:22:10');
INSERT INTO `jobpos` VALUES ('30', '研发三部部门职员-见习', '021200001', 'A1', '10', '2018-03-07 17:22:10');
INSERT INTO `jobpos` VALUES ('31', '研发三部部门职员-初级', '021200002', 'A2', '10', '2018-03-07 17:22:10');
INSERT INTO `jobpos` VALUES ('32', '研发三部部门职员-中级', '021200003', 'A3', '10', '2018-03-07 17:22:11');
INSERT INTO `jobpos` VALUES ('33', '研发三部部门职员-高级', '021200004', 'A4', '10', '2018-03-07 17:22:11');
INSERT INTO `jobpos` VALUES ('34', '研发三部部门职员-专家', '021200005', 'A5', '10', '2018-03-07 17:22:15');
INSERT INTO `jobpos` VALUES ('35', '项目管理部部门经理', '030101', 'B2', '11', '2018-03-07 17:23:44');
INSERT INTO `jobpos` VALUES ('36', '项目管理部部门职员-见习', '030100001', 'A1', '11', '2018-03-07 17:23:44');
INSERT INTO `jobpos` VALUES ('37', '项目管理部部门职员-初级', '030100002', 'A2', '11', '2018-03-07 17:23:44');
INSERT INTO `jobpos` VALUES ('38', '项目管理部部门职员-中级', '030100003', 'A3', '11', '2018-03-07 17:23:45');
INSERT INTO `jobpos` VALUES ('39', '项目管理部部门职员-高级', '030100004', 'A4', '11', '2018-03-07 17:23:45');
INSERT INTO `jobpos` VALUES ('40', '项目管理部部门职员-专家', '030100005', 'A5', '11', '2018-03-07 17:23:46');
INSERT INTO `jobpos` VALUES ('41', '测试部部门经理', '030201', 'B2', '12', '2018-03-07 17:25:10');
INSERT INTO `jobpos` VALUES ('42', '测试部部门职员-见习', '030200001', 'A1', '12', '2018-03-07 17:25:11');
INSERT INTO `jobpos` VALUES ('43', '测试部部门职员-初级', '030200002', 'A2', '12', '2018-03-07 17:25:11');
INSERT INTO `jobpos` VALUES ('44', '测试部部门职员-中级', '030200003', 'A3', '12', '2018-03-07 17:25:11');
INSERT INTO `jobpos` VALUES ('45', '测试部部门职员-高级', '030200004', 'A4', '12', '2018-03-07 17:25:11');
INSERT INTO `jobpos` VALUES ('46', '测试部部门职员-专家', '030200005', 'A5', '12', '2018-03-07 17:25:12');
INSERT INTO `jobpos` VALUES ('47', '财务部部门经理', '040101', 'B2', '4', '2018-03-07 17:27:05');
INSERT INTO `jobpos` VALUES ('48', '财务部部门经理助理', '040102', 'B1', '4', '2018-03-07 17:27:32');
INSERT INTO `jobpos` VALUES ('49', '财务部部门职员-见习', '040100001', 'A1', '4', '2018-03-07 17:27:46');
INSERT INTO `jobpos` VALUES ('50', '财务部部门职员-初级', '040100002', 'A2', '4', '2018-03-07 17:27:46');
INSERT INTO `jobpos` VALUES ('51', '财务部部门职员-中级', '040100003', 'A3', '4', '2018-03-07 17:27:46');
INSERT INTO `jobpos` VALUES ('52', '财务部部门职员-高级', '040100004', 'A4', '4', '2018-03-07 17:27:46');
INSERT INTO `jobpos` VALUES ('53', '财务部部门职员-专家', '040100005', 'A5', '4', '2018-03-07 17:27:47');
INSERT INTO `jobpos` VALUES ('54', '人事行政部部门经理', '050101', 'B2', '5', '2018-03-07 17:28:45');
INSERT INTO `jobpos` VALUES ('55', '人事行政部部门经理助理', '050102', 'B1', '5', '2018-03-07 17:28:46');
INSERT INTO `jobpos` VALUES ('56', '人事行政部部门职员-见习', '050100001', 'A1', '5', '2018-03-07 17:28:46');
INSERT INTO `jobpos` VALUES ('57', '人事行政部部门职员-初级', '050100002', 'A2', '5', '2018-03-07 17:28:46');
INSERT INTO `jobpos` VALUES ('58', '人事行政部部门职员-中级', '050100003', 'A3', '5', '2018-03-07 17:28:46');
INSERT INTO `jobpos` VALUES ('59', '人事行政部部门职员-高级', '050100004', 'A4', '5', '2018-03-07 17:28:46');
INSERT INTO `jobpos` VALUES ('60', '人事行政部部门职员-专家', '050100005', 'A5', '5', '2018-03-07 17:28:50');
INSERT INTO `jobpos` VALUES ('61', '市场部部门经理', '060101', 'B2', '6', '2018-03-07 17:28:56');
INSERT INTO `jobpos` VALUES ('62', '市场部部门经理助理', '060102', 'B1', '6', '2018-03-07 17:28:56');
INSERT INTO `jobpos` VALUES ('63', '市场部部门职员-见习', '060100001', 'A1', '6', '2018-03-07 17:28:56');
INSERT INTO `jobpos` VALUES ('64', '市场部部门职员-初级', '060100002', 'A2', '6', '2018-03-07 17:28:57');
INSERT INTO `jobpos` VALUES ('65', '市场部部门职员-中级', '060100003', 'A3', '6', '2018-03-07 17:28:57');
INSERT INTO `jobpos` VALUES ('66', '市场部部门职员-高级', '060100004', 'A4', '6', '2018-03-07 17:28:57');
INSERT INTO `jobpos` VALUES ('67', '市场部部门职员-专家', '060100005', 'A5', '6', '2018-03-07 17:28:57');

-- ----------------------------
-- Table structure for jobs_manage
-- ----------------------------
DROP TABLE IF EXISTS `jobs_manage`;
CREATE TABLE `jobs_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user_id` int(11) NOT NULL COMMENT '创建领导',
  `work_user_id` int(11) NOT NULL COMMENT '指定分配员工',
  `job_info` varchar(255) NOT NULL COMMENT '工作描述',
  `job_state` int(11) NOT NULL COMMENT '工作状态 0-新建 1-进行中 2-已解决 3-已关闭 4-已驳回',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `job_work_info` varchar(500) NOT NULL COMMENT '工作具体小结',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jobs_manage
-- ----------------------------




















