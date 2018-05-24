DROP TABLE IF EXISTS `employee_salary`
CREATE TABLE `employee_salary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` int(11) NOT NULL COMMENT '员工id',
  `emp_name` varchar(50) NOT NULL COMMENT '员工姓名',
  `base_salary`  varchar(255) NOT NULL COMMENT '员工基本薪资',
  `punish_amount` varchar(255) NOT NULL COMMENT '处罚总计',
  `award_amount` varchar(255) NOT NULL COMMENT '奖励总计',
  `final_salary` varchar(255) NOT NULL COMMENT '最终所得工资',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS `employee_evection`
CREATE TABLE `employee_evection` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` varchar(50) NOT NULL COMMENT '员工id',
  `emp_name` varchar(255) NOT NULL COMMENT '员工姓名',
  `evection_timebegin`  date NOT NULL COMMENT '出差开始时间',
  `evection_timeover`  date NOT NULL COMMENT '出差结束时间',
  `evection_reason` varchar(255) NOT NULL COMMENT '出差理由',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

---------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS `employee_reward`
CREATE TABLE `employee_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` varchar(50) NOT NULL COMMENT '员工id',
  `emp_name` varchar(255) NOT NULL COMMENT '员工姓名 -真实姓名',
  `reward_state` varchar(10) NOT NULL COMMENT '奖or罚 0-罚 1-奖',
  `reward_time`  date NOT NULL COMMENT '奖罚日期',
  `reward_money` varchar(50) NOT NULL COMMENT '奖罚金额',
  `reward_reason` varchar(255) NOT NULL COMMENT '奖罚理由',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

