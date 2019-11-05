package com.gwt.hris.cron;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAssignedLoanBean;
import com.gwt.hris.db.TbAssignedLoanManager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbPerusahaanBean;
import com.gwt.hris.db.TbPerusahaanManager;
import com.gwt.hris.db.TbPphBean;
import com.gwt.hris.db.TbPphManager;
import com.gwt.hris.db.TbPphPotonganBean;
import com.gwt.hris.db.TbPphPotonganManager;
import com.gwt.hris.db.TbPphTambahanBean;
import com.gwt.hris.db.TbPphTambahanManager;
import com.gwt.hris.db.TbPtkpBean;
import com.gwt.hris.db.TbPtkpManager;
import com.gwt.hris.db.TbTarifPajakBean;
import com.gwt.hris.db.TbTarifPajakManager;
import com.gwt.hris.db.ViewAssignedLoanBean;
import com.gwt.hris.db.ViewAssignedLoanManager;
import com.gwt.hris.db.ViewAttendanceBean;
import com.gwt.hris.db.ViewAttendanceCustomManager;
import com.gwt.hris.db.ViewEmployeeInformationBean;
import com.gwt.hris.db.ViewEmployeeInformationManager;
import com.gwt.hris.db.ViewEmployeePttBean;
import com.gwt.hris.db.ViewEmployeePttManager;
import com.gwt.hris.db.ViewEmployeeRenumerationBean;
import com.gwt.hris.db.ViewEmployeeRenumerationManager;
import com.gwt.hris.db.ViewEmployeeSalaryBean;
import com.gwt.hris.db.ViewEmployeeSalaryManager;
import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.SystemUtil;

public class CronPphJob implements Job {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static boolean isRunning = false;

	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		CronPphJob.isRunning = isRunning;
	}
	
	public static void main(String args[]) {
		CronPphJob cron = new CronPphJob();
		try {
			cron.run();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();
			
			run();

			commit = true;
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}
	}

	public void runManual() throws JobExecutionException {
		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();
			
			run();

			commit = true;
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}
	}

	public void run() throws DAOException, ParseException {
		if (isRunning == false) {
			setRunning(true);
			
			log.debug("Job CronPphJob start");
			
			TbEmployeeBean tbEmployeeBeans[] = TbEmployeeManager.getInstance().loadByWhere("where tbe_status = 0");

			// Employee
			for (TbEmployeeBean tbEmployeeBean : tbEmployeeBeans) {
				ViewEmployeeInformationBean viewEmployeeInformationBean = ViewEmployeeInformationManager.getInstance().loadByWhere("where tbe_id = " + tbEmployeeBean.getTbeId())[0];
				
				if (viewEmployeeInformationBean.getTbpId() != null) {
					TbPerusahaanBean tbPerusahaanBean = TbPerusahaanManager.getInstance().loadByPrimaryKey(viewEmployeeInformationBean.getTbpId());
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy");

					Calendar calNow = Calendar.getInstance();
					calNow.setTime(new Date());
					
					int intTbpphCount = TbPphManager.getInstance().countWhere("where tbe_id = " + tbEmployeeBean.getTbeId() + " and DATE_FORMAT(tbpph_date, '%Y') = '" + df.format(new Date()) + " '");

					if (intTbpphCount == 0 & calNow.get(Calendar.MONTH) != 0) {
						// New Employee
						String strYear = calNow.get(Calendar.YEAR) + "-";
						int intMonth = calNow.get(Calendar.MONTH) + 1;
						for (int intMonthLoop = 1; intMonthLoop <= intMonth; intMonthLoop++) {
							String strMonth = intMonthLoop < 10 ? "0" + intMonthLoop : intMonthLoop + "";
							String strYearMonth = strYear + strMonth;
							if (intMonthLoop == intMonth) {
								calc(intMonthLoop, tbEmployeeBean, tbPerusahaanBean, strYearMonth, 0);
							} else {
								calc(intMonthLoop, tbEmployeeBean, tbPerusahaanBean, strYearMonth, 1);						
							}
						}
					} else {
						// Existing Employee
						String strYear = calNow.get(Calendar.YEAR) + "-";
						int intMonth = calNow.get(Calendar.MONTH) + 1;
						String strMonth = intMonth < 10 ? "0" + intMonth : intMonth + "";
						String strYearMonth = strYear + strMonth;
						calc(intMonth, tbEmployeeBean, tbPerusahaanBean, strYearMonth, 0);
					}
					
					SystemUtil.getInstance().notification(SystemUtil.CHANNEL_EMAIL, 3, SystemUtil.UI_EMPLOYEE_PAYROLL, viewEmployeeInformationBean.getTbeId(), 0, new String[]{""});
				}
			}
			
			log.debug("Job CronPphJob stop");
			
			setRunning(false);
		} else {
			log.debug("Job CronPphJob still running");
		}
	}
	
	public void calc(int intMonth, TbEmployeeBean tbEmployeeBean, TbPerusahaanBean tbPerusahaanBean, String strYearMonth, int intDummy) throws DAOException, ParseException {
		TbPphTambahanManager.getInstance().deleteByWhere("where tbpph_id in (select tbpph_id from tb_pph where tbe_id = " + tbEmployeeBean.getTbeId() + " and DATE_FORMAT(tbpph_date, '%Y-%m') = '" + strYearMonth + "'" + " )");
		TbPphPotonganManager.getInstance().deleteByWhere("where tbpph_id in (select tbpph_id from tb_pph where tbe_id = " + tbEmployeeBean.getTbeId() + " and DATE_FORMAT(tbpph_date, '%Y-%m') = '" + strYearMonth + "'" + " )");
		TbPphManager.getInstance().deleteByWhere("where tbe_id = " + tbEmployeeBean.getTbeId() + " and DATE_FORMAT(tbpph_date, '%Y-%m') = '" + strYearMonth + "'");
		
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(new Date());
		calNow.set(Calendar.MONTH, intMonth - 1);
		
		TbPphBean tbPphBean = TbPphManager.getInstance().createTbPphBean();
		tbPphBean.setTbpphDummy(intDummy);
		tbPphBean.setTbpphDate(calNow.getTimeInMillis());

		Calendar calPeriodeAwal = Calendar.getInstance();
		calPeriodeAwal.setTime(new Date());
		calPeriodeAwal.set(Calendar.MONTH, intMonth - 1);
		calPeriodeAwal.set(Calendar.DATE, 1);

		Calendar calPeriodeAkhir = Calendar.getInstance();
		calPeriodeAkhir.setTime(new Date());
		calPeriodeAkhir.set(Calendar.MONTH, intMonth - 1);
		calPeriodeAkhir.set(Calendar.DATE, 1);
		calPeriodeAkhir.add(Calendar.MONTH, 1);
		calPeriodeAkhir.add(Calendar.DATE, -1);

		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		String strPPhPeriode = df.format(calPeriodeAwal.getTime()) + " - " + df.format(calPeriodeAkhir.getTime());
		tbPphBean.setTbpphPeriode(strPPhPeriode);

		log.debug("Employee = " + tbEmployeeBean.getTbeName());
		int intTbeId = tbEmployeeBean.getTbeId();
		tbPphBean.setTbeId(intTbeId);

		NumberFormat numberFormat = NumberFormat.getInstance();
		
		// PTKP
		TbPtkpBean tbPtkpBean = TbPtkpManager.getInstance().loadByPrimaryKey(tbEmployeeBean.getTbptkpId());
		if (tbPtkpBean != null) {
			double dblPTKP = tbPtkpBean.getTbptkpJumlah();
			log.debug("PTKP = " + numberFormat.format(dblPTKP));
			tbPphBean.setTbpphPtkp(dblPTKP);

			// Salary
			ViewEmployeeSalaryBean viewEmployeeSalaryBeans[] = ViewEmployeeSalaryManager.getInstance().loadByWhere("where tbe_id = " + intTbeId);

			if (viewEmployeeSalaryBeans.length > 0) {
				ViewEmployeeSalaryBean viewEmployeeSalaryBean = viewEmployeeSalaryBeans[0];
				log.debug("Salary Currency = " + viewEmployeeSalaryBean.getTbcCurrencyId());

				double dblSalary = viewEmployeeSalaryBean.getTbesBasicSalary();
				int intPayFrequency = viewEmployeeSalaryBean.getTbesPayFrequency();
				
				int intPayFrequencyMultiplier = 0;
				if (intPayFrequency == 0) {
					// Hourly
					intPayFrequencyMultiplier = 176;
				} else if (intPayFrequency == 1) {
					// Weekly
					intPayFrequencyMultiplier = 4;
				} else if (intPayFrequency == 2) {
					// Bi Weekly
					intPayFrequencyMultiplier = 2;
				} else if (intPayFrequency == 3) {
					// Monthly
					intPayFrequencyMultiplier = 1;
				}
				
				if (tbPerusahaanBean.getTbpLocalCurrencyId().intValue() != viewEmployeeSalaryBean.getTbcId().intValue()) {
					log.debug("Salary = " + numberFormat.format(dblSalary));
					log.debug("Pay Frequency = " + intPayFrequency);

					dblSalary = dblSalary * viewEmployeeSalaryBean.getTbcLocalCurrencyKurs() * intPayFrequencyMultiplier;

					log.debug("Kurs = " + numberFormat.format(viewEmployeeSalaryBean.getTbcLocalCurrencyKurs()));
					log.debug("Total Salary = " + numberFormat.format(dblSalary));
				} else {
					log.debug("Salary = " + numberFormat.format(dblSalary));
					log.debug("Pay Frequency = " + intPayFrequency);
					
					dblSalary = dblSalary * intPayFrequencyMultiplier;
					log.debug("Total Salary = " + numberFormat.format(dblSalary));
				}
				tbPphBean.setTbpphSalary(dblSalary);

				// JKK+JKM
				double dblJkkJkm = tbPerusahaanBean.getTbpJkk() + tbPerusahaanBean.getTbpJkm();
				log.debug("JKK+JKM = " + dblJkkJkm);
				tbPphBean.setTbpphJkkjkm(dblJkkJkm);

				// NPWP Awal Tahun
				boolean boolNPWPAwalTahun = false;
				if (tbEmployeeBean.getTbeTaxNoDate() != null) {
					Calendar calNPWPAwalTahun = Calendar.getInstance();
					calNPWPAwalTahun.setTime(tbEmployeeBean.getTbeTaxNoDate());

					if (calNPWPAwalTahun.get(Calendar.MONTH) == Calendar.JANUARY || calNPWPAwalTahun.get(Calendar.YEAR) < calNow.get(Calendar.YEAR)) {
						boolNPWPAwalTahun = true;
					} else {
						boolNPWPAwalTahun = false;
					}
				} else {
					boolNPWPAwalTahun = false;
				}
				log.debug("NPWPAwalTahun = " + boolNPWPAwalTahun);
				tbPphBean.setTbpphNpwpAwalTahun(boolNPWPAwalTahun == true ? 1 : 0);

				// Komponen Gaji
				// Gaji Pokok Gross
				double dblGajiPokokGross = dblSalary;
				log.debug("GajiPokokGross = " + numberFormat.format(dblGajiPokokGross));
				tbPphBean.setTbpphGajiPokokGross(dblGajiPokokGross);

				// Tunjangan Tetap
				double dblTunjanganTetap = 0;
				ViewEmployeeRenumerationBean viewEmployeeRenumerationBeans[] = ViewEmployeeRenumerationManager.getInstance().loadByWhere("where tbe_id = " + tbEmployeeBean.getTbeId());
				TbPphTambahanBean tbPphTambahanBeans_TunjanganTetap[] = new TbPphTambahanBean[viewEmployeeRenumerationBeans.length];
				int a = 0;
				for (ViewEmployeeRenumerationBean viewEmployeeRenumerationBean : viewEmployeeRenumerationBeans) {
					tbPphTambahanBeans_TunjanganTetap[a] = TbPphTambahanManager.getInstance().createTbPphTambahanBean();
					tbPphTambahanBeans_TunjanganTetap[a].setTbpphtName(viewEmployeeRenumerationBean.getTbrName());

					if (tbPerusahaanBean.getTbpLocalCurrencyId().intValue() != viewEmployeeRenumerationBean.getTbcId().intValue()) {
						dblTunjanganTetap += viewEmployeeRenumerationBean.getTbrNominal() * viewEmployeeRenumerationBean.getTbcLocalCurrencyKurs();
						tbPphTambahanBeans_TunjanganTetap[a].setTbpphtNominal(viewEmployeeRenumerationBean.getTbrNominal() * viewEmployeeRenumerationBean.getTbcLocalCurrencyKurs());
					} else {
						dblTunjanganTetap += viewEmployeeRenumerationBean.getTbrNominal();
						tbPphTambahanBeans_TunjanganTetap[a].setTbpphtNominal(viewEmployeeRenumerationBean.getTbrNominal());
					}

					a++;
				}
				log.debug("TunjanganTetap = " + numberFormat.format(dblTunjanganTetap));
				tbPphBean.setTbpphTunjanganTetap(dblTunjanganTetap);

				// Tunjangan Jamsostek JKK + JKM
				double dblTunjanganJamsostekJKKJKM = dblJkkJkm / 100 * dblSalary;
				log.debug("TunjanganJamsostekJKKJKM = " + numberFormat.format(dblTunjanganJamsostekJKKJKM));
				tbPphBean.setTbpphTunjanganJamsostekJkkjkm(dblTunjanganJamsostekJKKJKM);

				// Total Gross
				double dblTotalGross = dblSalary + dblTunjanganTetap + dblTunjanganJamsostekJKKJKM;
				log.debug("TotalGross = " + numberFormat.format(dblTotalGross));
				tbPphBean.setTbpphTotalGross(dblTotalGross);

				// Iuran JHT Jamsostek
				double dblIuranJHTJamsostek = dblSalary * 2 / 100;
				log.debug("IuranJHTJamsostek = " + numberFormat.format(dblIuranJHTJamsostek));
				tbPphBean.setTbpphIuranJhtJamsostek(dblIuranJHTJamsostek);

				// Biaya Jabatan
				double dblBiayaJabatan = dblTotalGross * 5 / 100;
				if (dblBiayaJabatan >= 500000) {
					dblBiayaJabatan = 500000;
				}
				log.debug("BiayaJabatan = " + numberFormat.format(dblBiayaJabatan));
				tbPphBean.setTbpphBiayaJabatan(dblBiayaJabatan);

				// Pendapatan sebulan sebelum pajak
				double dblPendapatanSebulanSebelumPajak = dblTotalGross - dblIuranJHTJamsostek - dblBiayaJabatan;
				log.debug("PendapatanSebulanSebelumPajak = " + numberFormat.format(dblPendapatanSebulanSebelumPajak));
				tbPphBean.setTbpphPendSblnSblmPajak(dblPendapatanSebulanSebelumPajak);

				// Pendapatan YTD sebelum pajak (sampai bulan lalu)
				Calendar calBulanLalu = Calendar.getInstance();
				calBulanLalu.setTime(calNow.getTime());
				calBulanLalu.add(Calendar.MONTH, -1);

				String strYearBulanLalu = calBulanLalu.get(Calendar.YEAR) + "-";
				int intMonthBulanLalu = calBulanLalu.get(Calendar.MONTH) + 1;
				String strMonthBulanLalu = intMonthBulanLalu < 10 ? "0" + intMonthBulanLalu : intMonthBulanLalu + "";
				String strYearMonthBulanLalu = strYearBulanLalu + strMonthBulanLalu;

				TbPphBean tbPphBeanBulanLalus[] = TbPphManager.getInstance().loadByWhere("where DATE_FORMAT(tbpph_date, '%Y-%m') = '" + strYearMonthBulanLalu + "'" + " and tbe_id = " + intTbeId);
				TbPphBean tbPphBeanBulanLalu = null;
				if (tbPphBeanBulanLalus.length > 0) {
					tbPphBeanBulanLalu = tbPphBeanBulanLalus[0];
				}

				double dblPendapatanYTDSebelumPajakSampaiBulanLalu = 0;
				if (calNow.get(Calendar.MONTH) == Calendar.JANUARY) {
					dblPendapatanYTDSebelumPajakSampaiBulanLalu = 0;
				} else {
					// Ambil data bulan sebelumnya
					if (tbPphBeanBulanLalu == null) {
						dblPendapatanYTDSebelumPajakSampaiBulanLalu = 0;
					} else {
						dblPendapatanYTDSebelumPajakSampaiBulanLalu = tbPphBeanBulanLalu.getTbpphPendSblnSblmPajak() + tbPphBeanBulanLalu.getTbpphPendYtdSblmPajakSmpBlnLalu();							
					}
				}
				log.debug("PendapatanYTDSebelumPajakSampaiBulanLalu = " + numberFormat.format(dblPendapatanYTDSebelumPajakSampaiBulanLalu));
				tbPphBean.setTbpphPendYtdSblmPajakSmpBlnLalu(dblPendapatanYTDSebelumPajakSampaiBulanLalu);

				// Pajak disetor YTD (sampai bulan lalu)
				double dblPajakDisetorYTDSampaiBulanLalu = 0;
				if (calNow.get(Calendar.MONTH) == Calendar.JANUARY) {
					dblPajakDisetorYTDSampaiBulanLalu = 0;
				} else {
					// Ambil data bulan sebelumnya
					if (tbPphBeanBulanLalu == null) {
						dblPajakDisetorYTDSampaiBulanLalu = 0;
					} else {
						dblPajakDisetorYTDSampaiBulanLalu = tbPphBeanBulanLalu.getTbpphPajakBlnIni() + tbPphBeanBulanLalu.getTbpphPajakDisetorYtdSmpBlnLalu();							
					}
				}
				log.debug("PajakDisetorYTDSampaiBulanLalu = " + numberFormat.format(dblPajakDisetorYTDSampaiBulanLalu));
				tbPphBean.setTbpphPajakDisetorYtdSmpBlnLalu(dblPajakDisetorYTDSampaiBulanLalu);

				// Faktor pengali untuk disetahunkan
				double dblYears = 12;
				double dblMonths = (calNow.get(Calendar.MONTH) + 1);
				double dblFaktorPengaliUntukDisetahunkan = dblYears / dblMonths;
				log.debug("FaktorPengaliUntukDisetahunkan = " + dblFaktorPengaliUntukDisetahunkan);
				tbPphBean.setTbpphFaktorPengaliUtkDisetahunkan(dblFaktorPengaliUntukDisetahunkan);

				// Pendapatan disetahunkan
				double dblPendapatanDisetahunkan = dblFaktorPengaliUntukDisetahunkan * (dblPendapatanSebulanSebelumPajak + dblPendapatanYTDSebelumPajakSampaiBulanLalu);
				log.debug("PendapatanDisetahunkan = " + numberFormat.format(dblPendapatanDisetahunkan));
				tbPphBean.setTbpphPendapatanDisetahunkan(dblPendapatanDisetahunkan);

				// Pendapatan kena pajak
				double dblPendapatanKenaPajak = dblPendapatanDisetahunkan - dblPTKP;
				log.debug("PendapatanKenaPajak = " + numberFormat.format(dblPendapatanKenaPajak));
				tbPphBean.setTbpphPendapatanKenaPajak(dblPendapatanKenaPajak);

				// Pajak setahun untuk karyawan ber-NPWP
				double dblPajakSetahunUntukKaryawanBerNPWP = 0;
				double dblTarifPajakNPWP = 0;
				double dblSampai = 0;
				TbTarifPajakBean tbTarifPajakBeans[] = TbTarifPajakManager.getInstance().loadAll();
				NumberFormat numberFormatRoundUp = NumberFormat.getInstance();
				numberFormatRoundUp.setMaximumFractionDigits(0);
				numberFormatRoundUp.setRoundingMode(RoundingMode.HALF_UP);
				for (int x = 0; x < tbTarifPajakBeans.length; x++) {
					if (dblPendapatanKenaPajak < tbTarifPajakBeans[x].getTbtpPkpSampai()) {
						dblPajakSetahunUntukKaryawanBerNPWP = ((Double.valueOf(tbTarifPajakBeans[x].getTbtpNpwp()) / 100) * (dblPendapatanKenaPajak - dblSampai)) + dblTarifPajakNPWP;
						log.debug("PajakSetahunUntukKaryawanBerNPWP = " + numberFormatRoundUp.format(dblPajakSetahunUntukKaryawanBerNPWP));
						tbPphBean.setTbpphPajakSetahunUtkKaryBerNpwp(dblPajakSetahunUntukKaryawanBerNPWP);

						x = tbTarifPajakBeans.length;
					} else {
						dblTarifPajakNPWP += (tbTarifPajakBeans[x].getTbtpPkpSampai() - tbTarifPajakBeans[x].getTbtpPkpDari()) * (Double.valueOf(tbTarifPajakBeans[x].getTbtpNpwp()) / 100);

						dblSampai = tbTarifPajakBeans[x].getTbtpPkpSampai();
					}
				}

				// Pajak setahun untuk karyawan TIDAK ber-NPWP
				double dblPajakSetahunUntukKaryawanTidakBerNPWP = 0;
				double dblTarifPajakTidakNPWP = 0;
				dblSampai = 0;
				for (int x = 0; x < tbTarifPajakBeans.length; x++) {
					if (dblPendapatanKenaPajak < tbTarifPajakBeans[x].getTbtpPkpSampai()) {
						dblPajakSetahunUntukKaryawanTidakBerNPWP = ((Double.valueOf(tbTarifPajakBeans[x].getTbtpNonNpwp()) / 100) * (dblPendapatanKenaPajak - dblSampai)) + dblTarifPajakTidakNPWP;
						log.debug("PajakSetahunUntukKaryawanTidakBerNPWP = " + numberFormatRoundUp.format(dblPajakSetahunUntukKaryawanTidakBerNPWP));
						tbPphBean.setTbpphPajakSetahunUtkKaryTdkBerNpwp(dblPajakSetahunUntukKaryawanTidakBerNPWP);

						x = tbTarifPajakBeans.length;
					} else {
						dblTarifPajakTidakNPWP += (tbTarifPajakBeans[x].getTbtpPkpSampai() - tbTarifPajakBeans[x].getTbtpPkpDari()) * (Double.valueOf(tbTarifPajakBeans[x].getTbtpNonNpwp()) / 100);

						dblSampai = tbTarifPajakBeans[x].getTbtpPkpSampai();
					}
				}

				// Pajak terutang sampai bulan ini
				double dblPajakTerutangSampaiBulanIni = 0;
				if (boolNPWPAwalTahun) {
					dblPajakTerutangSampaiBulanIni = dblPajakSetahunUntukKaryawanBerNPWP / dblFaktorPengaliUntukDisetahunkan;
				} else {
					dblPajakTerutangSampaiBulanIni = dblPajakSetahunUntukKaryawanTidakBerNPWP / dblFaktorPengaliUntukDisetahunkan;
				}
				log.debug("PajakTerutangSampaiBulanIni = " + numberFormatRoundUp.format(dblPajakTerutangSampaiBulanIni));
				tbPphBean.setTbpphPajakTerutangSmpBlnIni(dblPajakTerutangSampaiBulanIni);

				// Pajak bulan ini
				double dblPajakBulanIni = dblPajakTerutangSampaiBulanIni - dblPajakDisetorYTDSampaiBulanLalu;
				log.debug("PajakBulanIni = " + numberFormatRoundUp.format(dblPajakBulanIni));
				tbPphBean.setTbpphPajakBlnIni(dblPajakBulanIni);

				// Take home pay
				double dblTakeHomePay = dblTotalGross - dblIuranJHTJamsostek - dblPajakBulanIni - dblTunjanganJamsostekJKKJKM;
				log.debug("TakeHomePay = " + numberFormatRoundUp.format(dblTakeHomePay));
				tbPphBean.setTbpphTakeHomePay(dblTakeHomePay);

				// Pendapatan tidak tetap
				double dblPendapatanTidakTetap = 0;
				ViewEmployeePttBean viewEmployeePttBeans[] = ViewEmployeePttManager.getInstance().loadByWhere("where DATE_FORMAT(tbeptt_insert_time, '%Y-%m') = '" + strYearMonth + "' and tbe_id = " + intTbeId);
				TbPphTambahanBean tbPphTambahanBeans_PendapatanTidakTetap[] = new TbPphTambahanBean[viewEmployeePttBeans.length];
				int b = 0;
				for (ViewEmployeePttBean viewEmployeePttBean : viewEmployeePttBeans) {
					tbPphTambahanBeans_PendapatanTidakTetap[b] = TbPphTambahanManager.getInstance().createTbPphTambahanBean();
					tbPphTambahanBeans_PendapatanTidakTetap[b].setTbpphtName(viewEmployeePttBean.getTbpttName());

					if (tbPerusahaanBean.getTbpLocalCurrencyId().intValue() != viewEmployeePttBean.getTbcId().intValue()) {
						dblPendapatanTidakTetap += viewEmployeePttBean.getTbepttNominal() * viewEmployeePttBean.getTbcLocalCurrencyKurs();
						tbPphTambahanBeans_PendapatanTidakTetap[b].setTbpphtNominal(viewEmployeePttBean.getTbepttNominal() * viewEmployeePttBean.getTbcLocalCurrencyKurs());
					} else {
						dblPendapatanTidakTetap += viewEmployeePttBean.getTbepttNominal();
						tbPphTambahanBeans_PendapatanTidakTetap[b].setTbpphtNominal(viewEmployeePttBean.getTbepttNominal());
					}

					b++;
				}
				log.debug("PendapatanTidakTetap = " + numberFormatRoundUp.format(dblPendapatanTidakTetap));
				tbPphBean.setTbpphPendapatanTidakTetap(dblPendapatanTidakTetap);

				// Pendapatan tidak tetap YTD
				double dblPendapatanTidakTetapYTD = 0;
				if (calNow.get(Calendar.MONTH) == Calendar.JANUARY) {
					dblPendapatanTidakTetapYTD = 0;
				} else {
					// TODO Ambil data bulan sebelumnya
					dblPendapatanTidakTetapYTD = 0;
				}
				log.debug("PendapatanTidakTetapYTD = " + numberFormatRoundUp.format(dblPendapatanTidakTetapYTD));
				tbPphBean.setTbpphPendapatanTidakTetapYtd(dblPendapatanTidakTetapYTD);

				// Pajak sudah disetor u/ pendapatan tidak tetap YTD
				double dblPajakSudahDisetorUtkPendapatanTidakTetapYTD = 0;
				if (calNow.get(Calendar.MONTH) == Calendar.JANUARY) {
					dblPajakSudahDisetorUtkPendapatanTidakTetapYTD = 0;
				} else {
					// TODO Ambil data bulan sebelumnya
					dblPajakSudahDisetorUtkPendapatanTidakTetapYTD = 0;
				}
				log.debug("PajakSudahDisetorUtkPendapatanTidakTetapYTD = " + numberFormatRoundUp.format(dblPajakSudahDisetorUtkPendapatanTidakTetapYTD));
				tbPphBean.setTbpphPajakSdhDisetorUtkPendTdkTtpYtd(dblPajakSudahDisetorUtkPendapatanTidakTetapYTD);

				// Pendapatan disetahunkan + komisi
				double dblPendapatanDisetahunkanPlusKomisi = dblPendapatanKenaPajak + dblPendapatanTidakTetap + dblPendapatanTidakTetapYTD;
				log.debug("PendapatanDisetahunkanPlusKomisi = " + numberFormatRoundUp.format(dblPendapatanDisetahunkanPlusKomisi));
				tbPphBean.setTbpphPendDisetahunkanPlusKomisi(dblPendapatanDisetahunkanPlusKomisi);

				// Pajak total setahun untuk karyawan ber-NPWP
				double dblPajakTotalSetahunUntukKaryawanBerNPWP = 0;
				dblTarifPajakNPWP = 0;
				dblSampai = 0;
				for (int x = 0; x < tbTarifPajakBeans.length; x++) {
					if (dblPendapatanDisetahunkanPlusKomisi < tbTarifPajakBeans[x].getTbtpPkpSampai()) {
						dblPajakTotalSetahunUntukKaryawanBerNPWP = ((Double.valueOf(tbTarifPajakBeans[x].getTbtpNpwp()) / 100) * (dblPendapatanDisetahunkanPlusKomisi - dblSampai)) + dblTarifPajakNPWP;
						log.debug("PajakTotalSetahunUntukKaryawanBerNPWP = " + numberFormatRoundUp.format(dblPajakTotalSetahunUntukKaryawanBerNPWP));
						tbPphBean.setTbpphPajakTotSethnUtkKaryBerNpwp(dblPajakTotalSetahunUntukKaryawanBerNPWP);

						x = tbTarifPajakBeans.length;
					} else {
						dblTarifPajakNPWP += (tbTarifPajakBeans[x].getTbtpPkpSampai() - tbTarifPajakBeans[x].getTbtpPkpDari()) * (Double.valueOf(tbTarifPajakBeans[x].getTbtpNpwp()) / 100);

						dblSampai = tbTarifPajakBeans[x].getTbtpPkpSampai();
					}
				}

				// Pajak total setahun untuk karyawan TIDAK ber-NPWP
				double dblPajakTotalSetahunUntukKaryawanTidakBerNPWP = 0;
				dblTarifPajakTidakNPWP = 0;
				dblSampai = 0;
				for (int x = 0; x < tbTarifPajakBeans.length; x++) {
					if (dblPendapatanDisetahunkanPlusKomisi < tbTarifPajakBeans[x].getTbtpPkpSampai()) {
						dblPajakTotalSetahunUntukKaryawanTidakBerNPWP = ((Double.valueOf(tbTarifPajakBeans[x].getTbtpNonNpwp()) / 100) * (dblPendapatanDisetahunkanPlusKomisi - dblSampai)) + dblTarifPajakTidakNPWP;
						log.debug("PajakTotalSetahunUntukKaryawanTidakBerNPWP = " + numberFormatRoundUp.format(dblPajakTotalSetahunUntukKaryawanTidakBerNPWP));
						tbPphBean.setTbpphPajakTotSethnUtkKaryTdkBerNpwp(dblPajakTotalSetahunUntukKaryawanTidakBerNPWP);

						x = tbTarifPajakBeans.length;
					} else {
						dblTarifPajakTidakNPWP += (tbTarifPajakBeans[x].getTbtpPkpSampai() - tbTarifPajakBeans[x].getTbtpPkpDari()) * (Double.valueOf(tbTarifPajakBeans[x].getTbtpNonNpwp()) / 100);

						dblSampai = tbTarifPajakBeans[x].getTbtpPkpSampai();
					}
				}

				// Pajak komisi = pajak total setahun - pajak setahun
				double dblPajakKomisi = 0;
				if (boolNPWPAwalTahun) {
					dblPajakKomisi = dblPajakTotalSetahunUntukKaryawanBerNPWP - dblPajakSetahunUntukKaryawanBerNPWP - dblPajakSudahDisetorUtkPendapatanTidakTetapYTD;
				} else {
					dblPajakKomisi = dblPajakTotalSetahunUntukKaryawanTidakBerNPWP - dblPajakSetahunUntukKaryawanTidakBerNPWP - dblPajakSudahDisetorUtkPendapatanTidakTetapYTD;
				}
				log.debug("PajakKomisi = " + numberFormatRoundUp.format(dblPajakKomisi));
				tbPphBean.setTbpphPajakKomisi(dblPajakKomisi);

				// Take home pay untuk pendapatan Irreguler
				double dblTakeHomePayUntukPendapatanIrreguler = dblPendapatanTidakTetap - dblPajakKomisi;
				log.debug("TakeHomePayUntukPendapatanIrreguler = " + numberFormatRoundUp.format(dblTakeHomePayUntukPendapatanIrreguler));
				tbPphBean.setTbpphTakeHomePayUtkPendIrreguler(dblTakeHomePayUntukPendapatanIrreguler);

				// Loan
				double dblLoan = 0;
				ViewAssignedLoanBean viewAssignedLoanBeans[] = ViewAssignedLoanManager.getInstance().loadByWhere("where tbe_id = " + tbEmployeeBean.getTbeId() + " and tbalo_status = 1 and tbalo_start_date <= '" + strYearMonth + "' and tbalo_end_date >= '" + strYearMonth + "'");
				TbAssignedLoanBean tbAssignedLoanBeans[] = new TbAssignedLoanBean[viewAssignedLoanBeans.length];
				TbPphPotonganBean tbPphPotonganBean_Loan[] = new TbPphPotonganBean[viewAssignedLoanBeans.length];
				int c = 0;
				for (ViewAssignedLoanBean viewAssignedLoanBean : viewAssignedLoanBeans) {
					tbPphPotonganBean_Loan[c] = TbPphPotonganManager.getInstance().createTbPphPotonganBean();
					tbPphPotonganBean_Loan[c].setTbpphpName(viewAssignedLoanBean.getTbaloName());

					if (tbPerusahaanBean.getTbpLocalCurrencyId().intValue() != viewAssignedLoanBean.getTbcId().intValue()) {
						dblLoan += viewAssignedLoanBean.getTbaloMonthlyPayment() * viewAssignedLoanBean.getTbcLocalCurrencyKurs();
						tbPphPotonganBean_Loan[c].setTbpphpNominal(viewAssignedLoanBean.getTbaloMonthlyPayment() * viewAssignedLoanBean.getTbcLocalCurrencyKurs());
					} else {
						dblLoan += viewAssignedLoanBean.getTbaloMonthlyPayment();
						tbPphPotonganBean_Loan[c].setTbpphpNominal(viewAssignedLoanBean.getTbaloMonthlyPayment());
					}

					SimpleDateFormat dfLoan = new SimpleDateFormat("yyyy-MM");
					String strStartDate = viewAssignedLoanBean.getTbaloStartDate();
					Date startDate = dfLoan.parse(strStartDate);
					Calendar calStartLoan = Calendar.getInstance();
					calStartLoan.setTime(startDate);

					Calendar calNowLoan = Calendar.getInstance();
					calNowLoan.setTime(new Date());

					int intStartYear = calStartLoan.get(Calendar.YEAR);
					int intStartMonth = calStartLoan.get(Calendar.MONTH) + 1;

					int intNowYear = calNowLoan.get(Calendar.YEAR);
					int intNowMonth = calNowLoan.get(Calendar.MONTH) + 1;

					int intYears = intNowYear - intStartYear;

					int intMonths = intNowMonth - intStartMonth + (intYears * 12) + 1;
					
					tbAssignedLoanBeans[c] = TbAssignedLoanManager.getInstance().loadByPrimaryKey(viewAssignedLoanBean.getTbaloId());
					tbAssignedLoanBeans[c].setTbaloNominalTotalLeft(viewAssignedLoanBean.getTbaloNominalTotal() - (viewAssignedLoanBean.getTbaloMonthlyPayment() * intMonths));

					c++;
				}
				log.debug("Loan = " + numberFormatRoundUp.format(dblLoan));
				tbPphBean.setTbpphLoan(dblLoan);
				
				// Late
				ViewAttendanceBean viewAttendanceBeans_Late[] = ViewAttendanceCustomManager.getInstance().loadByWhereUnion(tbEmployeeBean.getTbeId(), strYearMonth);
				int intLate = 0;
				for (ViewAttendanceBean viewAttendanceBean : viewAttendanceBeans_Late) {
					intLate += viewAttendanceBean.getTbaInTimeDiff() == null ? 0 : viewAttendanceBean.getTbaInTimeDiff();
				}
				int intLateHours = intLate / 1000 / 60 / 60;				
				double dblLateDeduct = ((viewEmployeeSalaryBean.getTbpcLate() / 100) * viewEmployeeSalaryBean.getTbesBasicSalary()) * intLateHours;
				TbPphPotonganBean tbPphPotonganBean_Late = TbPphPotonganManager.getInstance().createTbPphPotonganBean();
				tbPphPotonganBean_Late.setTbpphpName("Late");
				tbPphPotonganBean_Late.setTbpphpNominal(dblLateDeduct);
				
				// Overtime
				ViewAttendanceBean viewAttendanceBeans_Overtime[] = ViewAttendanceCustomManager.getInstance().loadByWhereUnion(tbEmployeeBean.getTbeId(), strYearMonth);
				int intOvertime = 0;
				for (ViewAttendanceBean viewAttendanceBean : viewAttendanceBeans_Overtime) {
					intOvertime += viewAttendanceBean.getTbaOutTimeDiff() == null ? 0 : viewAttendanceBean.getTbaOutTimeDiff();
				}
				int intOvertimeHours = ((intOvertime / (1000 * 60 * 60)) % 24);
				double dblOvertimeDeduct = viewEmployeeSalaryBean.getTbpcOvertime() * intOvertimeHours;
				TbPphTambahanBean tbPphTambahanBean_Overtime = TbPphTambahanManager.getInstance().createTbPphTambahanBean();
				tbPphTambahanBean_Overtime.setTbpphtName("Overtime");
				tbPphTambahanBean_Overtime.setTbpphtNominal(dblOvertimeDeduct);

				// TOTAL TAKE HOME PAY
				double dblTotalTakeHomePay = dblTakeHomePay + dblTakeHomePayUntukPendapatanIrreguler - dblLoan - dblLateDeduct + dblOvertimeDeduct;
				log.debug("TotalTakeHomePay = " + numberFormatRoundUp.format(dblTotalTakeHomePay));
				tbPphBean.setTbpphTotalTakeHomePay(dblTotalTakeHomePay);

				tbPphBean.setTbcId(viewEmployeeSalaryBean.getTbcId());
				tbPphBean.setTbpphTotalTakeHomePayKurs(viewEmployeeSalaryBean.getTbcLocalCurrencyKurs());

				TbPphManager.getInstance().save(tbPphBean);

				for (int i = 0; i < tbPphTambahanBeans_TunjanganTetap.length; i++) {
					tbPphTambahanBeans_TunjanganTetap[i].setTbpphId(tbPphBean.getTbpphId());
				}
				TbPphTambahanManager.getInstance().save(tbPphTambahanBeans_TunjanganTetap);

				for (int i = 0; i < tbPphTambahanBeans_PendapatanTidakTetap.length; i++) {
					tbPphTambahanBeans_PendapatanTidakTetap[i].setTbpphId(tbPphBean.getTbpphId());
				}
				TbPphTambahanManager.getInstance().save(tbPphTambahanBeans_PendapatanTidakTetap);

				for (int i = 0; i < tbPphPotonganBean_Loan.length; i++) {
					tbPphPotonganBean_Loan[i].setTbpphId(tbPphBean.getTbpphId());
				}
				TbPphPotonganManager.getInstance().save(tbPphPotonganBean_Loan);

				TbAssignedLoanManager.getInstance().save(tbAssignedLoanBeans);
				
				if (intLate >= 0) {
					tbPphPotonganBean_Late.setTbpphId(tbPphBean.getTbpphId());
					TbPphPotonganManager.getInstance().save(tbPphPotonganBean_Late);					
				}
				
				if (intOvertime >= 0) {
					tbPphTambahanBean_Overtime.setTbpphId(tbPphBean.getTbpphId());
					TbPphTambahanManager.getInstance().save(tbPphTambahanBean_Overtime);
				}
			} else {
				log.debug("Salary = NOT FOUND");
			}
		} else {
			log.debug("PTKP = NOT FOUND");
		}
		log.debug("");
	}
}
