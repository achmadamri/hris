USE hrm;

CREATE TABLE `tb_pph_potongan` (
  `tbpphp_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbpph_id` int(11) DEFAULT NULL,
  `tbpphp_name` varchar(45) DEFAULT NULL,
  `tbpphp_nominal` double DEFAULT NULL,
  PRIMARY KEY (`tbpphp_id`),
  KEY `tbpphp_fk_1` (`tbpph_id`),
  CONSTRAINT `tbpphp_fk_1` FOREIGN KEY (`tbpph_id`) REFERENCES `tb_pph` (`tbpph_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;


insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (3,16,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (4,22,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (5,28,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (6,34,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (7,40,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (8,46,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (9,52,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (10,58,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (11,64,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (12,70,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (13,76,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (14,82,'pinjaman 1',843333.333333333);
insert into `tb_pph_potongan`(`tbpphp_id`,`tbpph_id`,`tbpphp_name`,`tbpphp_nominal`) values (15,88,'pinjaman 1',843333.333333333);
