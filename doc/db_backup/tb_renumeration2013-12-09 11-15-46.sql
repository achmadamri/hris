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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_renumeration`(`tbr_id`,`tbc_id`,`tbr_renumeration_id`,`tbr_name`,`tbr_nominal`) values (1,56,'REN1','Renumeration A',1000000);
insert into `hrm`.`tb_renumeration`(`tbr_id`,`tbc_id`,`tbr_renumeration_id`,`tbr_name`,`tbr_nominal`) values (2,56,'REN2','Renumeration B',2000000);
insert into `hrm`.`tb_renumeration`(`tbr_id`,`tbc_id`,`tbr_renumeration_id`,`tbr_name`,`tbr_nominal`) values (3,56,'REN3','Renumeration C',3000000);
insert into `hrm`.`tb_renumeration`(`tbr_id`,`tbc_id`,`tbr_renumeration_id`,`tbr_name`,`tbr_nominal`) values (4,56,'REN4','Renumeration D',4000000);
