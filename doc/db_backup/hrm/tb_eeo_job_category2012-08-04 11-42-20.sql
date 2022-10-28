USE hrm;

CREATE TABLE `tb_eeo_job_category` (
  `tbejc_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbejc_eeo_job_category_id` varchar(45) DEFAULT NULL,
  `tbejc_title` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbejc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=latin1;


insert into `tb_eeo_job_category`(`tbejc_id`,`tbejc_eeo_job_category_id`,`tbejc_title`) values (243,'EEC243','OFFICIALS AND ADMINISTRATORS');
insert into `tb_eeo_job_category`(`tbejc_id`,`tbejc_eeo_job_category_id`,`tbejc_title`) values (244,'EEC244','PROFESSIONALS');
insert into `tb_eeo_job_category`(`tbejc_id`,`tbejc_eeo_job_category_id`,`tbejc_title`) values (245,'EEC245','TECHNICIANS');
