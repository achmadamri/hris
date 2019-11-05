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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_employee_salary`(`tbes_id`,`tbe_id`,`tbp_id`,`tbc_id`,`tbes_basic_salary`,`tbes_pay_frequency`,`tbes_currency_name`) values (5,2,1,56,50000000,3,'Indonesian Rupiah');
insert into `hrm`.`tb_employee_salary`(`tbes_id`,`tbe_id`,`tbp_id`,`tbc_id`,`tbes_basic_salary`,`tbes_pay_frequency`,`tbes_currency_name`) values (6,5,1,56,100000000,3,'Indonesian Rupiah');
insert into `hrm`.`tb_employee_salary`(`tbes_id`,`tbe_id`,`tbp_id`,`tbc_id`,`tbes_basic_salary`,`tbes_pay_frequency`,`tbes_currency_name`) values (12,1,1,56,100000000,3,'Indonesian Rupiah (IDR)');
insert into `hrm`.`tb_employee_salary`(`tbes_id`,`tbe_id`,`tbp_id`,`tbc_id`,`tbes_basic_salary`,`tbes_pay_frequency`,`tbes_currency_name`) values (13,6,12,56,9000000,3,'Indonesian Rupiah (IDR)');
insert into `hrm`.`tb_employee_salary`(`tbes_id`,`tbe_id`,`tbp_id`,`tbc_id`,`tbes_basic_salary`,`tbes_pay_frequency`,`tbes_currency_name`) values (14,7,12,56,9000000,3,'Indonesian Rupiah (IDR)');
insert into `hrm`.`tb_employee_salary`(`tbes_id`,`tbe_id`,`tbp_id`,`tbc_id`,`tbes_basic_salary`,`tbes_pay_frequency`,`tbes_currency_name`) values (15,8,11,56,25000000,3,'Indonesian Rupiah (IDR)');
