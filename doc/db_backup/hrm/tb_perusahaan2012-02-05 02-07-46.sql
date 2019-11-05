USE hrm;

CREATE TABLE `tb_perusahaan` (
  `tbp_id` int(11) NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`tbp_id`),
  KEY `tbp_fk_1` (`fk_tbn_id`),
  CONSTRAINT `tbp_fk_1` FOREIGN KEY (`fk_tbn_id`) REFERENCES `tb_negara` (`tbn_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_perusahaan`(`tbp_id`,`tbp_name`,`tbp_tax_id`,`tbp_phone`,`tbp_fax`,`fk_tbn_id`,`tbp_address1`,`tbp_city`,`tbp_province`,`tbp_zip_code`,`tbp_comments`) values (1,'PT Dafba Multistore Indonesia','085881398697','081574119899','085881398697',100,'Jl. Gunung Gede V Blok C No.79
Kel. Kayuringin Jaya. Kec. Bekasi Selatan','Bekasi','Jawa Barat','12345','Toko Online terbesar di Indonesia');
