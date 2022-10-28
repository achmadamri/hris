USE hrm;

CREATE TABLE `tb_shift` (
  `tbs_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbs_shift_id` varchar(45) DEFAULT NULL,
  `tbs_name` varchar(45) DEFAULT NULL,
  `tbs_in_time` varchar(45) DEFAULT NULL,
  `tbs_out_time` varchar(45) DEFAULT NULL,
  `tbs_default` int(1) DEFAULT NULL,
  PRIMARY KEY (`tbs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;


insert into `tb_shift`(`tbs_id`,`tbs_shift_id`,`tbs_name`,`tbs_in_time`,`tbs_out_time`,`tbs_default`) values (1,'LEAVE','Leave','07:00:00','17:00:00',null);
insert into `tb_shift`(`tbs_id`,`tbs_shift_id`,`tbs_name`,`tbs_in_time`,`tbs_out_time`,`tbs_default`) values (2,'SICK','Sick','07:00:00','17:00:00',null);
insert into `tb_shift`(`tbs_id`,`tbs_shift_id`,`tbs_name`,`tbs_in_time`,`tbs_out_time`,`tbs_default`) values (3,'NORMAL','Normal','07:00:00','17:00:00',null);
insert into `tb_shift`(`tbs_id`,`tbs_shift_id`,`tbs_name`,`tbs_in_time`,`tbs_out_time`,`tbs_default`) values (4,'SFT1','Shift 1','08:00:00','16:00:00',null);
insert into `tb_shift`(`tbs_id`,`tbs_shift_id`,`tbs_name`,`tbs_in_time`,`tbs_out_time`,`tbs_default`) values (5,'SFT2','Shift 2','16:00:00','24:00:00',null);
insert into `tb_shift`(`tbs_id`,`tbs_shift_id`,`tbs_name`,`tbs_in_time`,`tbs_out_time`,`tbs_default`) values (6,'SFT3','Shift 3','24:00:00','08:00:00',null);
