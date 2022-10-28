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


insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (1,1,'2012-05-23','16:27:03',34023992,'16:28:42',-1877238,'','');
insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (2,1,'2012-05-25','17:44:21',38661518,null,null,'',null);
insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (3,1,'2012-06-18','12:03:26',18206410,'12:03:43',-17776233,'','');
insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (4,1,'2012-06-22','15:01:25',28885507,'15:01:59',-7080783,'','');
insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (5,1,'2012-07-06','00:05:59',-24840801,'00:06:11',-60828828,'','');
insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (6,1,'2012-07-07','19:38:21',45501023,'19:39:17',9557092,'','');
insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (7,1,'2012-01-01','18:20:39',40839152,'22:13:32',18812397,'','');
insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (8,1,'2012-07-24','12:40:10',20410673,null,null,'',null);
insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (9,2,'2012-07-24','12:49:39',20979869,null,null,'',null);
insert into `hrm`.`tb_attendance`(`tba_id`,`tbe_id`,`tba_date`,`tba_in_time`,`tba_in_time_diff`,`tba_out_time`,`tba_out_time_diff`,`tba_in_note`,`tba_out_note`) values (10,2,'2012-07-23','12:56:14',21374664,null,null,'',null);
