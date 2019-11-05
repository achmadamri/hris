USE hrm;

CREATE TABLE `tb_assigned_licenses` (
  `tbal_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbl_id` int(11) DEFAULT NULL,
  `tbal_start_date` datetime DEFAULT NULL,
  `tbal_end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tbal_id`),
  KEY `tbes_fk_1` (`tbe_id`),
  KEY `tbali_fk_1` (`tbe_id`),
  KEY `tbali_fk_2` (`tbl_id`),
  CONSTRAINT `tbali_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbali_fk_2` FOREIGN KEY (`tbl_id`) REFERENCES `tb_licenses` (`tbl_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_assigned_licenses`(`tbal_id`,`tbe_id`,`tbl_id`,`tbal_start_date`,`tbal_end_date`) values (1,1,3,'2012-02-01 00:00:00','2012-02-02 00:00:00');
insert into `hrm`.`tb_assigned_licenses`(`tbal_id`,`tbe_id`,`tbl_id`,`tbal_start_date`,`tbal_end_date`) values (8,1,4,'2012-02-06 00:00:00','2012-02-07 00:00:00');
