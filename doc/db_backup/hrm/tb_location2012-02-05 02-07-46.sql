USE hrm;

CREATE TABLE `tb_location` (
  `tbl_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbl_location_id` varchar(45) DEFAULT NULL COMMENT 'NAME+ID',
  `tbl_name` varchar(45) DEFAULT NULL,
  `tbl_country` varchar(45) DEFAULT NULL,
  `fk_tbn_id` int(11) DEFAULT NULL,
  `tbl_province` varchar(45) DEFAULT NULL,
  `tbl_city` varchar(45) DEFAULT NULL,
  `tbl_address` varchar(200) DEFAULT NULL,
  `tbl_zip_code` varchar(45) DEFAULT NULL,
  `tbl_phone` varchar(45) DEFAULT NULL,
  `tbl_fax` varchar(45) DEFAULT NULL,
  `tbl_comments` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tbl_id`),
  KEY `tbl_fk_1` (`fk_tbn_id`),
  CONSTRAINT `tbl_fk_1` FOREIGN KEY (`fk_tbn_id`) REFERENCES `tb_negara` (`tbn_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_location`(`tbl_id`,`tbl_location_id`,`tbl_name`,`tbl_country`,`fk_tbn_id`,`tbl_province`,`tbl_city`,`tbl_address`,`tbl_zip_code`,`tbl_phone`,`tbl_fax`,`tbl_comments`) values (1,'LOC1','Kantor Cabang Bekasi',null,100,'Jawa Barat','Bekasi','Jl. Gunung Gede V Blok C No.127
Kel. Kayuringin Jaya. Kec. Bekasi Selatan','12345','0218844467','w','Kantor Cabang Bekasi');
insert into `hrm`.`tb_location`(`tbl_id`,`tbl_location_id`,`tbl_name`,`tbl_country`,`fk_tbn_id`,`tbl_province`,`tbl_city`,`tbl_address`,`tbl_zip_code`,`tbl_phone`,`tbl_fax`,`tbl_comments`) values (2,'LOC2','Kantor Cabang Palembang',null,100,'Sumatera Selatan','Palembang','Jl. Volley','12345','w','w','Kantor Cabang');
