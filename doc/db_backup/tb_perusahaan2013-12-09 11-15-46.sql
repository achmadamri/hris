USE hrm;

CREATE TABLE `tb_perusahaan` (
  `tbp_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbp_perusahaan_id` varchar(45) DEFAULT NULL,
  `tbp_name` varchar(45) DEFAULT NULL,
  `tbp_tax_id` varchar(45) DEFAULT NULL,
  `tbp_phone` varchar(45) DEFAULT NULL,
  `tbp_fax` varchar(45) DEFAULT NULL,
  `fk_tbn_id` int(11) DEFAULT NULL,
  `tbp_address1` varchar(500) DEFAULT NULL,
  `tbp_city` varchar(45) DEFAULT NULL,
  `tbp_province` varchar(45) DEFAULT NULL,
  `tbp_zip_code` varchar(45) DEFAULT NULL,
  `tbp_comments` varchar(500) DEFAULT NULL,
  `tbp_jkk` double DEFAULT NULL,
  `tbp_jkm` double DEFAULT NULL,
  `tbp_local_currency_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbp_id`),
  KEY `tbp_fk_1` (`fk_tbn_id`),
  KEY `tbp_fk_2` (`tbp_local_currency_id`),
  CONSTRAINT `tbp_fk_1` FOREIGN KEY (`fk_tbn_id`) REFERENCES `tb_negara` (`tbn_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbp_fk_2` FOREIGN KEY (`tbp_local_currency_id`) REFERENCES `tb_currency` (`tbc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_perusahaan`(`tbp_id`,`tbp_perusahaan_id`,`tbp_name`,`tbp_tax_id`,`tbp_phone`,`tbp_fax`,`fk_tbn_id`,`tbp_address1`,`tbp_city`,`tbp_province`,`tbp_zip_code`,`tbp_comments`,`tbp_jkk`,`tbp_jkm`,`tbp_local_currency_id`) values (1,'COM1','PT HRIS Indonesia','58.547.666.6.-432.000','02112345678','',100,'Jl. Jendral Soedirman','Jakarta','DKI Jakarta','12345','',0.3,0.24,56);
insert into `hrm`.`tb_perusahaan`(`tbp_id`,`tbp_perusahaan_id`,`tbp_name`,`tbp_tax_id`,`tbp_phone`,`tbp_fax`,`fk_tbn_id`,`tbp_address1`,`tbp_city`,`tbp_province`,`tbp_zip_code`,`tbp_comments`,`tbp_jkk`,`tbp_jkm`,`tbp_local_currency_id`) values (4,'COM4','PT HRIS Malaysia','58.547.666.6.-432.001','02112345678','2',129,'Jl. jalan','Kuala Lumpur','Selangor','12345','',0.3,0.24,93);
