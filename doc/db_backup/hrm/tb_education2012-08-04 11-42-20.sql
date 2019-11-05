USE hrm;

CREATE TABLE `tb_education` (
  `tbe_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_education_id` varchar(45) DEFAULT NULL,
  `tbe_institute` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


insert into `tb_education`(`tbe_id`,`tbe_education_id`,`tbe_institute`) values (1,'EDU1','Universitas');
insert into `tb_education`(`tbe_id`,`tbe_education_id`,`tbe_institute`) values (2,'EDU2','SMU');
insert into `tb_education`(`tbe_id`,`tbe_education_id`,`tbe_institute`) values (3,'EDU3','SMP');
insert into `tb_education`(`tbe_id`,`tbe_education_id`,`tbe_institute`) values (4,'EDU4','SD');
