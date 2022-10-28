USE hrm;

CREATE TABLE `tb_employee` (
  `tbe_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_employee_id` varchar(45) DEFAULT NULL,
  `tbe_first_name` varchar(45) DEFAULT NULL,
  `tbe_middle_name` varchar(45) DEFAULT NULL,
  `tbe_last_name` varchar(45) DEFAULT NULL,
  `tbe_nick_name` varchar(45) DEFAULT NULL,
  `tbe_name` varchar(100) DEFAULT NULL,
  `tbe_photo_file_name` varchar(45) DEFAULT NULL,
  `tbe_id_no` varchar(45) DEFAULT NULL,
  `tbe_dob` datetime DEFAULT NULL,
  `tbe_marital_status` int(11) DEFAULT NULL,
  `tbe_smoker` int(11) DEFAULT NULL,
  `tbe_gender` int(11) DEFAULT NULL,
  `tbe_driver_license_no` varchar(45) DEFAULT NULL,
  `tbe_driver_license_expiry` datetime DEFAULT NULL,
  `tbn_id` int(11) DEFAULT NULL,
  `tber_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbe_id`),
  KEY `tbe_fk_1` (`tbn_id`),
  KEY `tbe_fk_2` (`tber_id`),
  CONSTRAINT `tbe_fk_1` FOREIGN KEY (`tbn_id`) REFERENCES `tb_nationality` (`tbn_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbe_fk_2` FOREIGN KEY (`tber_id`) REFERENCES `tb_ethnic_races` (`tber_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_employee`(`tbe_id`,`tbe_employee_id`,`tbe_first_name`,`tbe_middle_name`,`tbe_last_name`,`tbe_nick_name`,`tbe_name`,`tbe_photo_file_name`,`tbe_id_no`,`tbe_dob`,`tbe_marital_status`,`tbe_smoker`,`tbe_gender`,`tbe_driver_license_no`,`tbe_driver_license_expiry`,`tbn_id`,`tber_id`) values (1,'EMP12010001','Achmad','','Amri','Aan','Achmad Amri','k3uzw7x3vju2mjn08d58dgdm0.jpg
','','2012-01-31 00:00:00',null,null,null,'',null,null,null);
insert into `hrm`.`tb_employee`(`tbe_id`,`tbe_employee_id`,`tbe_first_name`,`tbe_middle_name`,`tbe_last_name`,`tbe_nick_name`,`tbe_name`,`tbe_photo_file_name`,`tbe_id_no`,`tbe_dob`,`tbe_marital_status`,`tbe_smoker`,`tbe_gender`,`tbe_driver_license_no`,`tbe_driver_license_expiry`,`tbn_id`,`tber_id`) values (2,'EMP12010002','Nurul','','Ana','Ana','Nurul Ana','r5xbcpzsrnvuxsm9y80iyt00i.jpg
','','1983-07-27 00:00:00',0,1,1,'',null,2,246);
insert into `hrm`.`tb_employee`(`tbe_id`,`tbe_employee_id`,`tbe_first_name`,`tbe_middle_name`,`tbe_last_name`,`tbe_nick_name`,`tbe_name`,`tbe_photo_file_name`,`tbe_id_no`,`tbe_dob`,`tbe_marital_status`,`tbe_smoker`,`tbe_gender`,`tbe_driver_license_no`,`tbe_driver_license_expiry`,`tbn_id`,`tber_id`) values (5,'EMP12010005','Miykaal','','Abrar','','Miykaal Abrar','tqkmyh4nw9jueuxw4hgqs7ofs.jpg
','','2008-10-29 00:00:00',1,1,0,'',null,2,245);
