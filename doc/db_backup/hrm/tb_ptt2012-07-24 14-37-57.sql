USE hrm;

CREATE TABLE `tb_ptt` (
  `tbptt_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbptt_ptt_id` varchar(45) DEFAULT NULL,
  `tbptt_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbptt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_ptt`(`tbptt_id`,`tbptt_ptt_id`,`tbptt_name`) values (164,'PTT164','THR');
insert into `hrm`.`tb_ptt`(`tbptt_id`,`tbptt_ptt_id`,`tbptt_name`) values (165,'PTT165','Bonus');
insert into `hrm`.`tb_ptt`(`tbptt_id`,`tbptt_ptt_id`,`tbptt_name`) values (166,'PTT166','Medical Claim');
insert into `hrm`.`tb_ptt`(`tbptt_id`,`tbptt_ptt_id`,`tbptt_name`) values (167,'PTT167','Subsidi KPR');
