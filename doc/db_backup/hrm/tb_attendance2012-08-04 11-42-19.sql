USE hrm;

CREATE TABLE `tb_attendance` (
  `tba_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tba_date` varchar(10) DEFAULT NULL,
  `tba_in_time` varchar(8) DEFAULT NULL,
  `tba_in_time_diff` int(11) DEFAULT NULL,
  `tba_out_time` varchar(8) DEFAULT NULL,
  `tba_out_time_diff` int(11) DEFAULT NULL,
  `tba_in_note` varchar(500) DEFAULT NULL,
  `tba_out_note` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tba_id`),
  KEY `tba_fk_1` (`tbe_id`),
  CONSTRAINT `tba_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;


insert into `tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (2,2,'2012-07-25',null,null,null,null,'Medical','Medical');
insert into `tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (3,2,'2012-07-26',null,null,null,null,'Medical','Medical');
insert into `tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (4,2,'2012-07-27',null,null,null,null,'Medical','Medical');
insert into `tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (5,2,'2012-07-31',null,null,null,null,'Casual','Casual');
insert into `tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (6,2,'2012-08-01',null,null,null,null,'Casual','Casual');
insert into `tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (7,2,'2012-08-02',null,null,null,null,'Casual','Casual');
insert into `tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (8,1,'2012-07-26','14:52:37',28357168,'14:53:06',-7613836,'','');
