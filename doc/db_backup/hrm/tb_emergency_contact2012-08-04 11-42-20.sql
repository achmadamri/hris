USE hrm;

CREATE TABLE `tb_emergency_contact` (
  `tbec_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbec_name` varchar(45) DEFAULT NULL,
  `tbec_relationship` varchar(45) DEFAULT NULL,
  `tbec_home_phone` varchar(45) DEFAULT NULL,
  `tbec_mobile_phone` varchar(45) DEFAULT NULL,
  `tbec_work_phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbec_id`),
  KEY `tbec_fk_1` (`tbe_id`),
  CONSTRAINT `tbec_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=latin1;


insert into `tb_emergency_contact`(`tbec_id`,`tbe_id`,`tbec_name`,`tbec_relationship`,`tbec_home_phone`,`tbec_mobile_phone`,`tbec_work_phone`) values (251,1,'Djamaludin','Father in law','','','');
