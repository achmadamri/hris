USE hrm;

CREATE TABLE `tb_customer` (
  `tbc_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbc_customer_id` varchar(45) DEFAULT NULL,
  `tbc_name` varchar(45) DEFAULT NULL,
  `tbc_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tbc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_customer`(`tbc_id`,`tbc_customer_id`,`tbc_name`,`tbc_description`) values (1,'CUS1','PT. Sampoerna Telekomunikasi Indonesia','asd');
insert into `hrm`.`tb_customer`(`tbc_id`,`tbc_customer_id`,`tbc_name`,`tbc_description`) values (6,'CUS6','PT. Kliring Penjaminan Efek Indonesia',null);
insert into `hrm`.`tb_customer`(`tbc_id`,`tbc_customer_id`,`tbc_name`,`tbc_description`) values (7,'CUS7','PT. Pertamina',null);
