USE hrm;

CREATE TABLE `tb_education` (
  `tbe_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_education_id` varchar(45) DEFAULT NULL,
  `tbe_institute` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_education`(`tbe_id`,`tbe_education_id`,`tbe_institute`) values (1,'EDU1','Universitas Gunadarma');
