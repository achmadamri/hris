USE hrm;

CREATE TABLE `tb_employee_salary` (
  `tbes_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbp_id` int(11) DEFAULT NULL,
  `tbc_id` int(11) DEFAULT NULL,
  `tbes_basic_salary` int(11) DEFAULT NULL,
  `tbes_pay_frequency` int(11) DEFAULT NULL,
  `tbes_currency_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbes_id`),
  KEY `tbes_fk_1` (`tbe_id`),
  KEY `tbes_fk_2` (`tbp_id`),
  KEY `tbes_fk_3` (`tbc_id`),
  CONSTRAINT `tbes_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbes_fk_2` FOREIGN KEY (`tbp_id`) REFERENCES `tb_paygrade` (`tbp_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbes_fk_3` FOREIGN KEY (`tbc_id`) REFERENCES `tb_currency` (`tbc_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_employee_salary`(`tbes_id`,`tbe_id`,`tbp_id`,`tbc_id`,`tbes_basic_salary`,`tbes_pay_frequency`,`tbes_currency_name`) values (3,1,1,56,100000000,3,'Indonesian Rupiah');
