USE hrm;

CREATE TABLE `tb_employee_ptt` (
  `tbeptt_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbptt_id` int(11) DEFAULT NULL,
  `tbe_id` int(11) DEFAULT NULL,
  `tbc_id` int(11) DEFAULT NULL,
  `tbeptt_insert_time` datetime DEFAULT NULL,
  `tbeptt_currency_name` varchar(45) DEFAULT NULL,
  `tbeptt_nominal` double DEFAULT NULL,
  PRIMARY KEY (`tbeptt_id`),
  KEY `tbmptt_fk_1` (`tbptt_id`),
  KEY `tbmptt_fk_2` (`tbe_id`),
  KEY `tbmptt_fk_3` (`tbc_id`),
  CONSTRAINT `tbmptt_fk_1` FOREIGN KEY (`tbptt_id`) REFERENCES `tb_ptt` (`tbptt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbmptt_fk_2` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbmptt_fk_3` FOREIGN KEY (`tbc_id`) REFERENCES `tb_currency` (`tbc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_employee_ptt`(`tbeptt_id`,`tbptt_id`,`tbe_id`,`tbc_id`,`tbeptt_insert_time`,`tbeptt_currency_name`,`tbeptt_nominal`) values (10,165,1,56,'2012-07-16 16:26:05','Indonesian Rupiah (IDR)',9000000);
insert into `hrm`.`tb_employee_ptt`(`tbeptt_id`,`tbptt_id`,`tbe_id`,`tbc_id`,`tbeptt_insert_time`,`tbeptt_currency_name`,`tbeptt_nominal`) values (11,165,2,56,'2012-07-16 16:26:18','Indonesian Rupiah (IDR)',9000000);
insert into `hrm`.`tb_employee_ptt`(`tbeptt_id`,`tbptt_id`,`tbe_id`,`tbc_id`,`tbeptt_insert_time`,`tbeptt_currency_name`,`tbeptt_nominal`) values (13,165,6,56,'2012-01-01 23:16:13','Indonesian Rupiah (IDR)',9000000);
