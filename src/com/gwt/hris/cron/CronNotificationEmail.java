package com.gwt.hris.cron;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbNotificationBean;
import com.gwt.hris.db.TbNotificationManager;
import com.gwt.hris.db.ViewNotificationBean;
import com.gwt.hris.db.ViewNotificationManager;
import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.ComplexEmail;

public class CronNotificationEmail implements Job {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static boolean isRunning = false;

	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		CronNotificationEmail.isRunning = isRunning;
	}
	
	public static void main(String args[]) {
		CronNotificationEmail cron = new CronNotificationEmail();
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
			
			log.debug("Job CronNotificationEmail start");
			
			String strWhere = "where tbn_sent_date is null";
			
			ViewNotificationBean viewNotificationBeans[] = ViewNotificationManager.getInstance().loadByWhere(strWhere);
			for (ViewNotificationBean viewNotificationBean : viewNotificationBeans) {
				ComplexEmail.getInstance().mail(viewNotificationBean.getTbntSubject(), viewNotificationBean.getTbeEmailTo(), null, new String(viewNotificationBean.getTbnData()));
				
				TbNotificationBean tbNotificationBean = TbNotificationManager.getInstance().loadByPrimaryKey(viewNotificationBean.getTbnId());
				tbNotificationBean.setTbnSentDate(new Date().getTime());
				TbNotificationManager.getInstance().save(tbNotificationBean);
			}
			
			log.debug("Job CronNotificationEmail stop");
			
			setRunning(false);
		} else {
			log.debug("Job CronNotificationEmail still running");
		}
	}
}
