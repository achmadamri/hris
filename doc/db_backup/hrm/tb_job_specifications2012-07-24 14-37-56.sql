USE hrm;

CREATE TABLE `tb_job_specifications` (
  `tbjs_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbjs_name` varchar(45) DEFAULT NULL,
  `tbjs_description` varchar(500) DEFAULT NULL,
  `tbjs_duties` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tbjs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_job_specifications`(`tbjs_id`,`tbjs_name`,`tbjs_description`,`tbjs_duties`) values (1,'Direktur','Deskripsi Direktur','Duties Direktur');
insert into `hrm`.`tb_job_specifications`(`tbjs_id`,`tbjs_name`,`tbjs_description`,`tbjs_duties`) values (2,'Manager','Deskripsi Manager','Duties Manager');
insert into `hrm`.`tb_job_specifications`(`tbjs_id`,`tbjs_name`,`tbjs_description`,`tbjs_duties`) values (3,'Staff','Deskripsi Staff','Duties Staff');
