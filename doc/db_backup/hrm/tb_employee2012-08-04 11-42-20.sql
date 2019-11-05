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
  `tbe_tax_no` varchar(45) DEFAULT NULL,
  `tbe_tax_no_date` datetime DEFAULT NULL,
  `tbe_dob` datetime DEFAULT NULL,
  `tbe_marital_status` int(11) DEFAULT NULL,
  `tbe_smoker` int(11) DEFAULT NULL,
  `tbe_gender` int(11) DEFAULT NULL,
  `tbe_driver_license_no` varchar(45) DEFAULT NULL,
  `tbe_driver_license_expiry` datetime DEFAULT NULL,
  `tbn_id` int(11) DEFAULT NULL,
  `tber_id` int(11) DEFAULT NULL,
  `tbptkp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbe_id`),
  KEY `tbe_fk_1` (`tbn_id`),
  KEY `tbe_fk_2` (`tber_id`),
  KEY `tbe_fk_3` (`tbptkp_id`),
  CONSTRAINT `tbe_fk_1` FOREIGN KEY (`tbn_id`) REFERENCES `tb_nationality` (`tbn_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbe_fk_2` FOREIGN KEY (`tber_id`) REFERENCES `tb_ethnic_races` (`tber_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbe_fk_3` FOREIGN KEY (`tbptkp_id`) REFERENCES `tb_ptkp` (`tbptkp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;


insert into `tb_employee`(`tbe_id`,`tbe_employee_id`,`tbe_first_name`,`tbe_middle_name`,`tbe_last_name`,`tbe_nick_name`,`tbe_name`,`tbe_photo_file_name`,`tbe_id_no`,`tbe_tax_no`,`tbe_tax_no_date`,`tbe_dob`,`tbe_marital_status`,`tbe_smoker`,`tbe_gender`,`tbe_driver_license_no`,`tbe_driver_license_expiry`,`tbn_id`,`tber_id`,`tbptkp_id`) values (1,'EMP12010001','g','','g','g','g g','k3uzw7x3vju2mjn08d58dgdm0.jpg
','','12345','2012-01-01 00:00:00','1981-08-19 00:00:00',0,1,0,'',null,2,245,10);
insert into `tb_employee`(`tbe_id`,`tbe_employee_id`,`tbe_first_name`,`tbe_middle_name`,`tbe_last_name`,`tbe_nick_name`,`tbe_name`,`tbe_photo_file_name`,`tbe_id_no`,`tbe_tax_no`,`tbe_tax_no_date`,`tbe_dob`,`tbe_marital_status`,`tbe_smoker`,`tbe_gender`,`tbe_driver_license_no`,`tbe_driver_license_expiry`,`tbn_id`,`tber_id`,`tbptkp_id`) values (2,'EMP12010002','n','','n','n','n n','r5xbcpzsrnvuxsm9y80iyt00i.jpg
','','','2012-07-26 13:42:48',null,0,1,1,'',null,2,246,10);
insert into `tb_employee`(`tbe_id`,`tbe_employee_id`,`tbe_first_name`,`tbe_middle_name`,`tbe_last_name`,`tbe_nick_name`,`tbe_name`,`tbe_photo_file_name`,`tbe_id_no`,`tbe_tax_no`,`tbe_tax_no_date`,`tbe_dob`,`tbe_marital_status`,`tbe_smoker`,`tbe_gender`,`tbe_driver_license_no`,`tbe_driver_license_expiry`,`tbn_id`,`tber_id`,`tbptkp_id`) values (5,'EMP12010005','m','','m','','m m','tqkmyh4nw9jueuxw4hgqs7ofs.jpg
','','','2012-07-26 13:42:39','2008-10-29 00:00:00',1,1,0,'',null,2,245,4);
insert into `tb_employee`(`tbe_id`,`tbe_employee_id`,`tbe_first_name`,`tbe_middle_name`,`tbe_last_name`,`tbe_nick_name`,`tbe_name`,`tbe_photo_file_name`,`tbe_id_no`,`tbe_tax_no`,`tbe_tax_no_date`,`tbe_dob`,`tbe_marital_status`,`tbe_smoker`,`tbe_gender`,`tbe_driver_license_no`,`tbe_driver_license_expiry`,`tbn_id`,`tber_id`,`tbptkp_id`) values (6,'EMP12030006','abc','','def','','abc def',null,'','12345','2012-01-01 00:00:00',null,null,null,null,'',null,null,null,4);
insert into `tb_employee`(`tbe_id`,`tbe_employee_id`,`tbe_first_name`,`tbe_middle_name`,`tbe_last_name`,`tbe_nick_name`,`tbe_name`,`tbe_photo_file_name`,`tbe_id_no`,`tbe_tax_no`,`tbe_tax_no_date`,`tbe_dob`,`tbe_marital_status`,`tbe_smoker`,`tbe_gender`,`tbe_driver_license_no`,`tbe_driver_license_expiry`,`tbn_id`,`tber_id`,`tbptkp_id`) values (7,'EMP12070007','ghi','','jkl','','ghi jkl',null,'','12345',null,null,null,null,null,'',null,null,null,4);
insert into `tb_employee`(`tbe_id`,`tbe_employee_id`,`tbe_first_name`,`tbe_middle_name`,`tbe_last_name`,`tbe_nick_name`,`tbe_name`,`tbe_photo_file_name`,`tbe_id_no`,`tbe_tax_no`,`tbe_tax_no_date`,`tbe_dob`,`tbe_marital_status`,`tbe_smoker`,`tbe_gender`,`tbe_driver_license_no`,`tbe_driver_license_expiry`,`tbn_id`,`tber_id`,`tbptkp_id`) values (8,'EMP12010008','user1','user','satu','','user1 satu',null,'','21345','2012-01-01 23:24:36',null,null,null,null,'',null,null,null,4);
