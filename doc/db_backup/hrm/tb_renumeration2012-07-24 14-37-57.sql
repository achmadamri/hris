USE hrm;

CREATE TABLE `tb_renumeration` (
  `tbr_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbc_id` int(11) DEFAULT NULL,
  `tbr_renumeration_id` varchar(45) DEFAULT NULL,
  `tbr_name` varchar(45) DEFAULT NULL,
  `tbr_nominal` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbr_id`),
  KEY `tbr_fk_1` (`tbc_id`),
  CONSTRAINT `tbr_fk_1` FOREIGN KEY (`tbc_id`) REFERENCES `tb_currency` (`tbc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_renumeration`(`tbr_id`,`tbc_id`,`tbr_renumeration_id`,`tbr_name`,`tbr_nominal`) values (5,56,'REN5','Tunjangan 1',10000);
insert into `hrm`.`tb_renumeration`(`tbr_id`,`tbc_id`,`tbr_renumeration_id`,`tbr_name`,`tbr_nominal`) values (6,56,'REN6','Tunjangan 2',100000);
insert into `hrm`.`tb_renumeration`(`tbr_id`,`tbc_id`,`tbr_renumeration_id`,`tbr_name`,`tbr_nominal`) values (7,56,'REN7','Tunjangan 3',1000000);
