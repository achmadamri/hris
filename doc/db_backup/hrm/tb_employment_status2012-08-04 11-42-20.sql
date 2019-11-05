USE hrm;

CREATE TABLE `tb_employment_status` (
  `tbes_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbes_employment_status_id` varchar(45) DEFAULT NULL,
  `tbes_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbes_id`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=latin1;


insert into `tb_employment_status`(`tbes_id`,`tbes_employment_status_id`,`tbes_name`) values (241,'EST001','Permanent');
insert into `tb_employment_status`(`tbes_id`,`tbes_employment_status_id`,`tbes_name`) values (242,'EST002','Contract');
