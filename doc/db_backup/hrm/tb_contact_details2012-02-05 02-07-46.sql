USE hrm;

CREATE TABLE `tb_contact_details` (
  `tbe_id` int(11) NOT NULL,
  `tbn_id` int(11) DEFAULT NULL,
  `tbcd_street` varchar(500) DEFAULT NULL,
  `tbcd_city` varchar(45) DEFAULT NULL,
  `tbcd_province` varchar(45) DEFAULT NULL,
  `tbcd_zip_code` varchar(45) DEFAULT NULL,
  `tbcd_home_phone` varchar(45) DEFAULT NULL,
  `tbcd_mobile_phone` varchar(45) DEFAULT NULL,
  `tbcd_work_phone` varchar(45) DEFAULT NULL,
  `tbcd_work_email` varchar(45) DEFAULT NULL,
  `tbcd_other_email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbe_id`),
  KEY `tbcd_fk_1` (`tbe_id`),
  CONSTRAINT `tbcd_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_contact_details`(`tbe_id`,`tbn_id`,`tbcd_street`,`tbcd_city`,`tbcd_province`,`tbcd_zip_code`,`tbcd_home_phone`,`tbcd_mobile_phone`,`tbcd_work_phone`,`tbcd_work_email`,`tbcd_other_email`) values (1,100,'Jl. Gunung Gede V Blok C No 79. Kel. Kayuringin Jaya Kec. Bekasi Selatan','Depok','Jawa Barat','','0218844467','085881398697','','achmad.amri@gmail.com','');
insert into `hrm`.`tb_contact_details`(`tbe_id`,`tbn_id`,`tbcd_street`,`tbcd_city`,`tbcd_province`,`tbcd_zip_code`,`tbcd_home_phone`,`tbcd_mobile_phone`,`tbcd_work_phone`,`tbcd_work_email`,`tbcd_other_email`) values (2,null,null,null,null,null,null,null,null,null,null);
insert into `hrm`.`tb_contact_details`(`tbe_id`,`tbn_id`,`tbcd_street`,`tbcd_city`,`tbcd_province`,`tbcd_zip_code`,`tbcd_home_phone`,`tbcd_mobile_phone`,`tbcd_work_phone`,`tbcd_work_email`,`tbcd_other_email`) values (5,null,null,null,null,null,null,null,null,null,null);
