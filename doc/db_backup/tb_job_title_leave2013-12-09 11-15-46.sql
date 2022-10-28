USE hrm;

CREATE TABLE `tb_job_title_leave` (
  `tbjtl_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbjt_id` int(11) DEFAULT NULL,
  `tblt_id` int(11) DEFAULT NULL,
  `tbjtl_min` int(11) DEFAULT NULL,
  `tbjtl_prior_date` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbjtl_id`),
  KEY `tbjtl_fk_1` (`tbjt_id`),
  KEY `tbjtl_fk_2` (`tblt_id`),
  CONSTRAINT `tbjtl_fk_1` FOREIGN KEY (`tbjt_id`) REFERENCES `tb_job_title` (`tbjt_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbjtl_fk_2` FOREIGN KEY (`tblt_id`) REFERENCES `tb_leave_types` (`tblt_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (1,1,1,1,7);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (2,1,1,7,30);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (3,2,1,1,7);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (4,2,1,7,30);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (5,3,1,1,7);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (6,3,1,7,30);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (7,4,1,1,7);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (8,4,1,7,30);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (9,1,2,1,1);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (10,1,3,1,-9999);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (11,2,2,1,-9999);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (12,2,3,1,-9999);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (13,3,2,1,-9999);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (14,3,3,1,-9999);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (15,4,2,1,-9999);
insert into `hrm`.`tb_job_title_leave`(`tbjtl_id`,`tbjt_id`,`tblt_id`,`tbjtl_min`,`tbjtl_prior_date`) values (16,4,3,1,-9999);
