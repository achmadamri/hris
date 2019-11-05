USE hrm;

CREATE TABLE `tb_ethnic_races` (
  `tber_id` int(11) NOT NULL AUTO_INCREMENT,
  `tber_ethnic_races_id` varchar(45) DEFAULT NULL,
  `tber_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tber_id`)
) ENGINE=InnoDB AUTO_INCREMENT=247 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_ethnic_races`(`tber_id`,`tber_ethnic_races_id`,`tber_name`) values (244,'ETH244','Jawa');
insert into `hrm`.`tb_ethnic_races`(`tber_id`,`tber_ethnic_races_id`,`tber_name`) values (245,'ETH245','Palembang');
insert into `hrm`.`tb_ethnic_races`(`tber_id`,`tber_ethnic_races_id`,`tber_name`) values (246,'ETH246','Sunda');
