USE hrm;

CREATE TABLE `tb_holiday` (
  `tbh_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbh_holiday_id` varchar(45) DEFAULT NULL,
  `tbh_name` varchar(45) DEFAULT NULL,
  `tbh_date` datetime DEFAULT NULL,
  `tbh_repeat_annually` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbh_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;


insert into `tb_holiday`(`tbh_id`,`tbh_holiday_id`,`tbh_name`,`tbh_date`,`tbh_repeat_annually`) values (7,'HOL7','Holiday 1','2012-02-01 00:00:00',0);
insert into `tb_holiday`(`tbh_id`,`tbh_holiday_id`,`tbh_name`,`tbh_date`,`tbh_repeat_annually`) values (8,'HOL8','Holiday 2','2008-02-29 00:00:00',1);
