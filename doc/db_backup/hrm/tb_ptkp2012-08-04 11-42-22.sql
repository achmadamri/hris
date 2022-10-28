USE hrm;

CREATE TABLE `tb_ptkp` (
  `tbptkp_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbptkp_status` varchar(45) DEFAULT NULL,
  `tbptkp_keterangan` varchar(500) DEFAULT NULL,
  `tbptkp_jumlah` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbptkp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;


insert into `tb_ptkp`(`tbptkp_id`,`tbptkp_status`,`tbptkp_keterangan`,`tbptkp_jumlah`) values (4,'TK0','Tidak kawin tanpa tanggungan',15840000);
insert into `tb_ptkp`(`tbptkp_id`,`tbptkp_status`,`tbptkp_keterangan`,`tbptkp_jumlah`) values (5,'TK1','Tidak kawin dengan 1 tanggungan',17160000);
insert into `tb_ptkp`(`tbptkp_id`,`tbptkp_status`,`tbptkp_keterangan`,`tbptkp_jumlah`) values (6,'TK2','Tidak kawin dengan 2 tanggungan',18480000);
insert into `tb_ptkp`(`tbptkp_id`,`tbptkp_status`,`tbptkp_keterangan`,`tbptkp_jumlah`) values (7,'TK3','Tidak kawin dengan 3 tanggungan',19800000);
insert into `tb_ptkp`(`tbptkp_id`,`tbptkp_status`,`tbptkp_keterangan`,`tbptkp_jumlah`) values (8,'K0','Kawin tanpa tanggungan',17160000);
insert into `tb_ptkp`(`tbptkp_id`,`tbptkp_status`,`tbptkp_keterangan`,`tbptkp_jumlah`) values (9,'K1','Kawin dengan 1 tanggungan',18480000);
insert into `tb_ptkp`(`tbptkp_id`,`tbptkp_status`,`tbptkp_keterangan`,`tbptkp_jumlah`) values (10,'K2','Kawin dengan 2 tanggungan',19800000);
insert into `tb_ptkp`(`tbptkp_id`,`tbptkp_status`,`tbptkp_keterangan`,`tbptkp_jumlah`) values (11,'K3','Kawin dengan 3 tanggungan',21120000);
