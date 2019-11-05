USE hrm;

CREATE TABLE `tb_paygrade_currency` (
  `tbpc_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbp_id` int(11) DEFAULT NULL COMMENT 'NAME+ID',
  `tbc_id` int(11) DEFAULT NULL,
  `tbpc_min` double DEFAULT NULL,
  `tbpc_max` double DEFAULT NULL,
  `tbpc_step` double DEFAULT NULL,
  PRIMARY KEY (`tbpc_id`),
  KEY `tbpc_fk_1` (`tbp_id`),
  KEY `tbpc_fk_2` (`tbc_id`),
  CONSTRAINT `tbpc_fk_1` FOREIGN KEY (`tbp_id`) REFERENCES `tb_paygrade` (`tbp_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbpc_fk_2` FOREIGN KEY (`tbc_id`) REFERENCES `tb_currency` (`tbc_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;


insert into `tb_paygrade_currency`(`tbpc_id`,`tbp_id`,`tbc_id`,`tbpc_min`,`tbpc_max`,`tbpc_step`) values (23,11,56,20000000,39000000,750000);
insert into `tb_paygrade_currency`(`tbpc_id`,`tbp_id`,`tbc_id`,`tbpc_min`,`tbpc_max`,`tbpc_step`) values (24,12,56,5000000,19000000,500000);
insert into `tb_paygrade_currency`(`tbpc_id`,`tbp_id`,`tbc_id`,`tbpc_min`,`tbpc_max`,`tbpc_step`) values (25,1,56,50000000,100000000,1000000);
insert into `tb_paygrade_currency`(`tbpc_id`,`tbp_id`,`tbc_id`,`tbpc_min`,`tbpc_max`,`tbpc_step`) values (26,1,138,5000,10000,1000);
insert into `tb_paygrade_currency`(`tbpc_id`,`tbp_id`,`tbc_id`,`tbpc_min`,`tbpc_max`,`tbpc_step`) values (27,12,138,1000,10000,100);
