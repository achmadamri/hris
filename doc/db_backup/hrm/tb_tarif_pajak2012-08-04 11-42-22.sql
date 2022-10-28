USE hrm;

CREATE TABLE `tb_tarif_pajak` (
  `tbtp_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbtp_pkp_dari` double DEFAULT NULL,
  `tbtp_pkp_sampai` double DEFAULT NULL,
  `tbtp_npwp` int(11) DEFAULT NULL,
  `tbtp_non_npwp` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbtp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;


insert into `tb_tarif_pajak`(`tbtp_id`,`tbtp_pkp_dari`,`tbtp_pkp_sampai`,`tbtp_npwp`,`tbtp_non_npwp`) values (2,0,50000000,5,6);
insert into `tb_tarif_pajak`(`tbtp_id`,`tbtp_pkp_dari`,`tbtp_pkp_sampai`,`tbtp_npwp`,`tbtp_non_npwp`) values (3,50000000.01,250000000,15,18);
insert into `tb_tarif_pajak`(`tbtp_id`,`tbtp_pkp_dari`,`tbtp_pkp_sampai`,`tbtp_npwp`,`tbtp_non_npwp`) values (4,250000000.01,500000000,25,30);
insert into `tb_tarif_pajak`(`tbtp_id`,`tbtp_pkp_dari`,`tbtp_pkp_sampai`,`tbtp_npwp`,`tbtp_non_npwp`) values (5,500000000.01,9999999999999,30,36);
