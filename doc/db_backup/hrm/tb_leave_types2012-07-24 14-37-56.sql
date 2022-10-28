USE hrm;

CREATE TABLE `tb_leave_types` (
  `tblt_id` int(11) NOT NULL AUTO_INCREMENT,
  `tblt_leave_types_id` varchar(45) DEFAULT NULL,
  `tblt_name` varchar(45) DEFAULT NULL,
  `tblt_reduction` int(11) DEFAULT NULL,
  PRIMARY KEY (`tblt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_leave_types`(`tblt_id`,`tblt_leave_types_id`,`tblt_name`,`tblt_reduction`) values (1,'LVT1','Casual',1);
insert into `hrm`.`tb_leave_types`(`tblt_id`,`tblt_leave_types_id`,`tblt_name`,`tblt_reduction`) values (2,'LVT2','Medical',0);
