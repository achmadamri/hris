USE hrm;

CREATE TABLE `tb_nationality` (
  `tbn_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbn_nationality_id` varchar(45) DEFAULT NULL,
  `tbn_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


insert into `tb_nationality`(`tbn_id`,`tbn_nationality_id`,`tbn_name`) values (2,'NAT2','Indonesian');
insert into `tb_nationality`(`tbn_id`,`tbn_nationality_id`,`tbn_name`) values (3,'NAT3','Malaysian');
insert into `tb_nationality`(`tbn_id`,`tbn_nationality_id`,`tbn_name`) values (4,'NAT4','American');
