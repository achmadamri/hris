USE hrm;

CREATE TABLE `tb_assigned_leaves` (
  `tbale_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tblt_id` int(11) DEFAULT NULL,
  `tbale_created_time` datetime DEFAULT NULL,
  `tbale_updated_time` datetime DEFAULT NULL,
  `tbale_start_date` datetime DEFAULT NULL,
  `tbale_end_date` datetime DEFAULT NULL,
  `tbale_comments` varchar(500) DEFAULT NULL,
  `tbale_status` int(11) DEFAULT NULL,
  `tbale_leave_taken` int(11) DEFAULT NULL,
  `tbale_leave_available` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbale_id`),
  KEY `tbale_fk_1` (`tbe_id`),
  KEY `tbale_fk_2` (`tblt_id`),
  CONSTRAINT `tbale_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbale_fk_2` FOREIGN KEY (`tblt_id`) REFERENCES `tb_leave_types` (`tblt_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;


insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (1,1,1,'2012-04-10 15:31:56',null,null,null,null,1,0,null);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (2,2,1,'2012-04-10 15:31:56',null,null,null,null,1,0,21);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (3,5,1,'2012-04-10 15:31:56',null,null,null,null,1,0,null);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (4,6,1,'2012-04-10 15:31:56',null,null,null,null,1,0,null);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (8,1,2,'2012-04-10 15:32:08',null,null,null,null,1,0,null);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (9,2,2,'2012-04-10 15:32:08',null,null,null,null,1,0,21);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (10,5,2,'2012-04-10 15:32:08',null,null,null,null,1,0,null);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (11,6,2,'2012-04-10 15:32:08',null,null,null,null,1,0,null);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (32,2,2,'2012-07-25 11:52:55','2012-07-25 11:56:10','2012-07-25 00:00:00','2012-07-27 00:00:00','',1,3,21);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (33,2,1,'2012-07-25 11:56:51','2012-07-25 11:57:13','2012-07-31 00:00:00','2012-08-02 00:00:00','',1,3,21);
insert into `tb_assigned_leaves`(`tbale_id`,`tbe_id`,`tblt_id`,`tbale_created_time`,`tbale_updated_time`,`tbale_start_date`,`tbale_end_date`,`tbale_comments`,`tbale_status`,`tbale_leave_taken`,`tbale_leave_available`) values (34,2,1,'2012-07-26 14:26:10',null,'2012-08-01 00:00:00','2012-08-02 00:00:00','',0,2,null);
