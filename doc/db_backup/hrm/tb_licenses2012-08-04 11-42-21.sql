USE hrm;

CREATE TABLE `tb_licenses` (
  `tbl_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbl_licenses_id` varchar(45) DEFAULT NULL,
  `tbl_name` varchar(45) DEFAULT NULL,
  `tbl_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tbl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


insert into `tb_licenses`(`tbl_id`,`tbl_licenses_id`,`tbl_name`,`tbl_description`) values (3,'LIC3','ABC','xxx');
insert into `tb_licenses`(`tbl_id`,`tbl_licenses_id`,`tbl_name`,`tbl_description`) values (4,'LIC4','DE','xx');
