package com.gwt.hris.cron;

import java.sql.SQLException;
import java.text.DateFormat;
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
import com.gwt.hris.db.RptTbAttendanceBean;
import com.gwt.hris.db.RptTbAttendanceManager;
import com.gwt.hris.db.TbAttendanceBean;
import com.gwt.hris.db.TbAttendanceManager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbEmployeeShiftBean;
import com.gwt.hris.db.TbEmployeeShiftManager;
import com.gwt.hris.db.TbHolidayManager;
import com.gwt.hris.db.TbShiftBean;
import com.gwt.hris.db.TbShiftManager;
import com.gwt.hris.db.ViewAttendanceBean;
import com.gwt.hris.db.ViewAttendanceCustomManager;
import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.util.ClassUtil;

public class CronAttendance implements Job {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static boolean isRunning = false;

	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		CronAttendance.isRunning = isRunning;
	}
	
	public static void main(String args[]) {
		CronAttendance cron = new CronAttendance();
		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();
			
			cron.run();

			commit = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
			
			log.debug("Job CronAttendance start");
			
			TbEmployeeBean tbEmployeeBeans[] = TbEmployeeManager.getInstance().loadByWhere("where tbe_status = 0");
			for (TbEmployeeBean tbEmployeeBean : tbEmployeeBeans) {
				createReport(tbEmployeeBean);
			}

			for (TbEmployeeBean tbEmployeeBean : tbEmployeeBeans) {
				createAttendance(tbEmployeeBean);
			}
			
			log.debug("Job CronAttendance stop");
			
			setRunning(false);
		} else {
			log.debug("Job CronAttendance still running");
		}
	}
	
	public void createAttendance(TbEmployeeBean tbEmployeeBean) throws DAOException, ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		
//		TbShiftBean tbShiftBeans[] = TbShiftManager.getInstance().loadByWhere("where tbs_name not in ('HOLIDAY', 'LEAVE', 'SICK')");
//		ViewAttendanceBean viewAttendanceBeans_Absent[] = ViewAttendanceCustomManager.getInstance().loadByWhereUnion(tbEmployeeBean.getTbeId(), simpleDateFormat.format(cal.getTime()));
//		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String strDateYYYYMM = simpleDateFormat.format(cal.getTime());
//		Date dateYYYYMM = simpleDateFormat.parse(strDateYYYYMM);
//		for (ViewAttendanceBean viewAttendanceBeans_Absent_ : viewAttendanceBeans_Absent) {
//			if (viewAttendanceBeans_Absent_.getTbaInTime() == null) {
//				simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//				Date dateTba = simpleDateFormat.parse(viewAttendanceBeans_Absent_.getTbaDate());
//				
//				if (dateTba.before(dateYYYYMM) || dateTba.equals(dateYYYYMM)) {
//					for (TbShiftBean tbShiftBean : tbShiftBeans) {
//						if (viewAttendanceBeans_Absent_.getTbsId().intValue() == tbShiftBean.getTbsId().intValue()) {
//							simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//							Date dateOut = simpleDateFormat.parse(viewAttendanceBeans_Absent_.getTbsOutTime());
//							String strDateHHMMSS = simpleDateFormat.format(cal.getTime());
//							Date dateHHMMSS = simpleDateFormat.parse(strDateHHMMSS);
//							if (dateTba.before(dateYYYYMM) || dateOut.before(dateHHMMSS) || dateOut.equals(dateHHMMSS)) {
//								if (viewAttendanceBeans_Absent_.getTbaInTime() == null) {
//									String strDelete = "where tbe_id = " + tbEmployeeBean.getTbeId() + " and tba_date = '" + viewAttendanceBeans_Absent_.getTbaDate() + "'";
//									TbAttendanceManager.getInstance().deleteByWhere(strDelete);
//									
//									TbAttendanceBean tbAttendanceBean = TbAttendanceManager.getInstance().createTbAttendanceBean();
//									tbAttendanceBean.setTbeId(tbEmployeeBean.getTbeId());
//									tbAttendanceBean.setTbaDate(viewAttendanceBeans_Absent_.getTbaDate());
//									tbAttendanceBean.setTbaInTime(viewAttendanceBeans_Absent_.getTbsOutTime());
//
//									Date dateIn = simpleDateFormat.parse(viewAttendanceBeans_Absent_.getTbsInTime());
//									Calendar calIn = Calendar.getInstance();
//									calIn.setTime(dateIn);
//									Calendar calOut = Calendar.getInstance();
//									calOut.setTime(dateOut);
//									Long l = new Long(calOut.getTimeInMillis() - calIn.getTimeInMillis());
//									tbAttendanceBean.setTbaInTimeDiff(l.intValue());
//									
//									tbAttendanceBean.setTbaOutTime(viewAttendanceBeans_Absent_.getTbsOutTime());
//									tbAttendanceBean.setTbaOutTimeDiff(0);
//									
//									tbAttendanceBean.setTbaInNote("SYSTEM");
//									tbAttendanceBean.setTbaOutNote("SYSTEM");
//									TbAttendanceManager.getInstance().save(tbAttendanceBean);
//									
//									TbEmployeeShiftBean tbEmployeeShiftBean = TbEmployeeShiftManager.getInstance().createTbEmployeeShiftBean();
//									tbEmployeeShiftBean.setTbeId(tbEmployeeBean.getTbeId());
//									tbEmployeeShiftBean.setTbsId(viewAttendanceBeans_Absent_.getTbsId());
//									tbEmployeeShiftBean.setTbesDate(viewAttendanceBeans_Absent_.getTbaDate());
//
//									TbEmployeeShiftManager.getInstance().save(tbEmployeeShiftBean);
//								}
//							}
//						}
//					}
//				}
//			}
//		}
	}
	
	public void createReport(TbEmployeeBean tbEmployeeBean) throws DAOException, ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		
		RptTbAttendanceManager.getInstance().deleteByWhere("where tbe_id = " + tbEmployeeBean.getTbeId() + " and rtba_date like '" + simpleDateFormat.format(cal.getTime()) + "%'");
		
		ViewAttendanceBean viewAttendanceBeans_Report[] = ViewAttendanceCustomManager.getInstance().loadByWhereUnion(tbEmployeeBean.getTbeId(), simpleDateFormat.format(cal.getTime()));
		
		for (ViewAttendanceBean viewAttendanceBean : viewAttendanceBeans_Report) {
			RptTbAttendanceBean rptTbAttendanceBean = RptTbAttendanceManager.getInstance().createRptTbAttendanceBean();
			rptTbAttendanceBean.setTbeId(tbEmployeeBean.getTbeId());
			rptTbAttendanceBean.setRtbaDate(viewAttendanceBean.getTbaDate());
			rptTbAttendanceBean.setRtbaInTime(viewAttendanceBean.getTbaInTime());
			rptTbAttendanceBean.setRtbaInTimeDiff(viewAttendanceBean.getTbaInTimeDiff());
			rptTbAttendanceBean.setRtbaOutTime(viewAttendanceBean.getTbaOutTime());
			rptTbAttendanceBean.setRtbaOutTimeDiff(viewAttendanceBean.getTbaOutTimeDiff());
			rptTbAttendanceBean.setRtbaInLongitude(viewAttendanceBean.getTbaInLongitude());
			rptTbAttendanceBean.setRtbaInLatitude(viewAttendanceBean.getTbaInLatitude());
			rptTbAttendanceBean.setRtbaOutLongitude(viewAttendanceBean.getTbaOutLongitude());
			rptTbAttendanceBean.setRtbaOutLatitude(viewAttendanceBean.getTbaOutLatitude());
			rptTbAttendanceBean.setRtbaInPhoto(viewAttendanceBean.getTbaInPhoto());
			rptTbAttendanceBean.setRtbaOutPhoto(viewAttendanceBean.getTbaOutPhoto());
			rptTbAttendanceBean.setRtbaInNote(viewAttendanceBean.getTbaInNote());
			rptTbAttendanceBean.setRtbaOutNote(viewAttendanceBean.getTbaOutNote());
			
			Calendar calendar = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(dateFormat.parse(viewAttendanceBean.getTbaDate()));
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			String strWhereHoliday = "where DATE_FORMAT(tbh_date, '%Y/%m/%d') = '" + df.format(calendar.getTime()) + "'";
			int intHolidayCount = TbHolidayManager.getInstance().countWhere(strWhereHoliday);
			
//			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || intHolidayCount > 0) {
//				if (viewAttendanceBean.getTbsId() == 3) {
//					rptTbAttendanceBean.setTbsId(0);
//					
//					String strWhere = "where tbe_id = " + tbEmployeeBean.getTbeId() + " and tbes_date = '" + viewAttendanceBean.getTbaDate() + "'";
//					TbEmployeeShiftBean tbEmployeeShiftBeans[] = TbEmployeeShiftManager.getInstance().loadByWhere(strWhere);
//					if (tbEmployeeShiftBeans.length > 0) {
//						TbEmployeeShiftManager.getInstance().deleteByWhere(strWhere);
//					}
//					
//					TbEmployeeShiftBean tbEmployeeShiftBean = TbEmployeeShiftManager.getInstance().createTbEmployeeShiftBean();
//					tbEmployeeShiftBean.setTbeId(tbEmployeeBean.getTbeId());
//					tbEmployeeShiftBean.setTbsId(0);
//					tbEmployeeShiftBean.setTbesDate(viewAttendanceBean.getTbaDate());
//					TbEmployeeShiftManager.getInstance().save(tbEmployeeShiftBean);
//				} else {
//					rptTbAttendanceBean.setTbsId(viewAttendanceBean.getTbsId());				
//				}				
//			} else {
//				rptTbAttendanceBean.setTbsId(viewAttendanceBean.getTbsId());
//			}
			rptTbAttendanceBean.setTbsId(3);
			
			RptTbAttendanceManager.getInstance().save(rptTbAttendanceBean);
		}
	}
}
