USE hrm;

CREATE TABLE `tb_membership_types` (
  `tbmt_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbmt_membership_types_id` varchar(45) DEFAULT NULL,
  `tbmt_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbmt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_membership_types`(`tbmt_id`,`tbmt_membership_types_id`,`tbmt_name`) values (241,'MET1','Membership Type 1');
insert into `hrm`.`tb_membership_types`(`tbmt_id`,`tbmt_membership_types_id`,`tbmt_name`) values (242,'MET2','Membership Type 2');
