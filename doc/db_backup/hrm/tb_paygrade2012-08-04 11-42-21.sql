USE hrm;

CREATE TABLE `tb_paygrade` (
  `tbp_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbp_paygrade_id` varchar(45) DEFAULT NULL COMMENT 'NAME+ID',
  `tbp_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;


insert into `tb_paygrade`(`tbp_id`,`tbp_paygrade_id`,`tbp_name`) values (1,'SAL1','Salary Grade 001');
insert into `tb_paygrade`(`tbp_id`,`tbp_paygrade_id`,`tbp_name`) values (11,'SAL11','Salary Grade 002');
insert into `tb_paygrade`(`tbp_id`,`tbp_paygrade_id`,`tbp_name`) values (12,'SAL12','Salary Grade 003');
