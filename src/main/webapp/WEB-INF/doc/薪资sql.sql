DROP TABLE IF EXISTS `employee_salary`
CREATE TABLE `employee_salary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `emp_id` int(11) NOT NULL COMMENT 'Ա��id',
  `emp_name` varchar(50) NOT NULL COMMENT 'Ա������',
  `base_salary`  varchar(255) NOT NULL COMMENT 'Ա������н��',
  `punish_amount` varchar(255) NOT NULL COMMENT '�����ܼ�',
  `award_amount` varchar(255) NOT NULL COMMENT '�����ܼ�',
  `final_salary` varchar(255) NOT NULL COMMENT '�������ù���',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS `employee_evection`
CREATE TABLE `employee_evection` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `emp_id` varchar(50) NOT NULL COMMENT 'Ա��id',
  `emp_name` varchar(255) NOT NULL COMMENT 'Ա������',
  `evection_timebegin`  date NOT NULL COMMENT '���ʼʱ��',
  `evection_timeover`  date NOT NULL COMMENT '�������ʱ��',
  `evection_reason` varchar(255) NOT NULL COMMENT '��������',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

---------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS `employee_reward`
CREATE TABLE `employee_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `emp_id` varchar(50) NOT NULL COMMENT 'Ա��id',
  `emp_name` varchar(255) NOT NULL COMMENT 'Ա������ -��ʵ����',
  `reward_state` varchar(10) NOT NULL COMMENT '��or�� 0-�� 1-��',
  `reward_time`  date NOT NULL COMMENT '��������',
  `reward_money` varchar(50) NOT NULL COMMENT '�������',
  `reward_reason` varchar(255) NOT NULL COMMENT '��������',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

