USE hrm;

CREATE TABLE `tb_timesheet` (
  `tbt_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbc_id` int(11) DEFAULT NULL,
  `tbp_id` int(11) DEFAULT NULL,
  `tbpag_id` int(11) DEFAULT NULL,
  `tbpa_id` int(11) DEFAULT NULL,
  `tbt_update_time` datetime DEFAULT NULL,
  `tbt_start_of_week` datetime DEFAULT NULL,
  `tbt_day_1_hour` int(11) DEFAULT NULL,
  `tbt_day_2_hour` int(11) DEFAULT NULL,
  `tbt_day_3_hour` int(11) DEFAULT NULL,
  `tbt_day_4_hour` int(11) DEFAULT NULL,
  `tbt_day_5_hour` int(11) DEFAULT NULL,
  `tbt_day_6_hour` int(11) DEFAULT NULL,
  `tbt_day_7_hour` int(11) DEFAULT NULL,
  `tbt_total_hour` int(11) DEFAULT NULL,
  `tbt_grand_total_hour` int(11) DEFAULT NULL,
  `tbt_approval_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbt_id`),
  KEY `tbt_fk_1` (`tbe_id`),
  KEY `tbt_fk_2` (`tbc_id`),
  KEY `tbt_fk_3` (`tbp_id`),
  KEY `tbt_fk_4` (`tbpag_id`),
  KEY `tbt_fk_5` (`tbpa_id`),
  CONSTRAINT `tbt_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbt_fk_2` FOREIGN KEY (`tbc_id`) REFERENCES `tb_customer` (`tbc_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbt_fk_3` FOREIGN KEY (`tbp_id`) REFERENCES `tb_project` (`tbp_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbt_fk_4` FOREIGN KEY (`tbpag_id`) REFERENCES `tb_project_activities_group` (`tbpag_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbt_fk_5` FOREIGN KEY (`tbpa_id`) REFERENCES `tb_project_activities` (`tbpa_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (1,2,1,7,16,23,'2012-04-08 16:17:28','2012-04-02 16:17:28',1,0,0,0,0,0,0,1,3,1);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (2,2,1,7,16,23,'2012-04-08 16:17:39','2012-04-02 16:17:39',1,0,0,0,0,0,0,1,3,1);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (3,2,1,7,16,23,'2012-04-08 16:17:48','2012-04-02 16:17:48',1,0,0,0,0,0,0,1,3,1);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (4,2,1,7,16,23,'2012-07-08 07:23:21','2012-04-02 00:00:00',1,0,0,11,0,0,0,12,4,0);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (11,2,1,7,16,23,'2012-04-16 16:10:33','2012-04-16 16:10:33',8,8,8,8,0,0,0,32,47,1);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (12,2,1,7,18,29,'2012-04-16 16:10:12','2012-04-16 16:10:12',0,0,0,0,8,0,0,8,47,1);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (13,2,1,7,16,23,'2012-06-15 14:28:40','2012-06-11 14:28:40',1,1,1,1,1,1,9,15,15,1);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (14,1,1,7,16,23,'2012-01-02 10:08:54','2012-07-02 06:49:52',8,8,8,8,8,0,0,40,80,0);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (15,1,6,8,18,29,'2012-07-08 00:10:04','2012-07-02 00:10:04',8,8,8,8,0,0,0,32,80,0);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (16,1,6,8,16,25,'2012-07-08 00:10:25','2012-07-02 00:10:25',0,0,0,0,8,0,0,8,80,0);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (17,2,1,7,16,23,'2012-05-31 11:41:06','2012-05-28 11:41:06',8,0,0,0,0,0,0,8,40,1);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (18,2,1,7,19,32,'2012-07-08 07:23:48','2012-05-28 11:41:25',8,8,8,8,8,0,0,40,40,0);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (19,2,1,7,16,23,'2012-07-08 06:50:23','2012-07-02 06:50:23',8,8,8,8,8,0,0,40,80,0);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (20,2,1,7,16,23,'2012-07-08 07:24:14','2012-07-02 07:24:14',8,8,8,8,8,0,0,40,80,0);
insert into `hrm`.`tb_timesheet`(`tbt_id`,`tbe_id`,`tbc_id`,`tbp_id`,`tbpag_id`,`tbpa_id`,`tbt_update_time`,`tbt_start_of_week`,`tbt_day_1_hour`,`tbt_day_2_hour`,`tbt_day_3_hour`,`tbt_day_4_hour`,`tbt_day_5_hour`,`tbt_day_6_hour`,`tbt_day_7_hour`,`tbt_total_hour`,`tbt_grand_total_hour`,`tbt_approval_status`) values (21,1,1,7,16,23,'2012-07-16 22:37:09','2012-07-16 22:36:56',8,8,8,8,0,0,8,40,40,0);
