USE hrm;

CREATE TABLE `tb_paygrade` (
  `tbp_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbp_paygrade_id` varchar(45) DEFAULT NULL COMMENT 'NAME+ID',
  `tbp_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_paygrade`(`tbp_id`,`tbp_paygrade_id`,`tbp_name`) values (1,'SAL1','Salary Grade A');
insert into `hrm`.`tb_paygrade`(`tbp_id`,`tbp_paygrade_id`,`tbp_name`) values (2,'SAL2','Salary Grade B');
insert into `hrm`.`tb_paygrade`(`tbp_id`,`tbp_paygrade_id`,`tbp_name`) values (3,'SAL3','Salary Grade C');
insert into `hrm`.`tb_paygrade`(`tbp_id`,`tbp_paygrade_id`,`tbp_name`) values (4,'SAL4','Salary Grade D');
insert into `hrm`.`tb_paygrade`(`tbp_id`,`tbp_paygrade_id`,`tbp_name`) values (5,'SAL5','Salary Grade E');
