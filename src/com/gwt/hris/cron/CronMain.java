package com.gwt.hris.cron;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.db.TbCronBean;
import com.gwt.hris.db.TbCronManager;
import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.util.ClassUtil;

public class CronMain {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void run() {
		SchedulerFactory sf = new StdSchedulerFactory();

		try {
			Scheduler sched = sf.getScheduler();
			log.debug("Starting Scheduler");
			sched.start();
			
			TbCronBean tbCronBeans[] = TbCronManager.getInstance().loadAll();
			for (TbCronBean tbCronBean : tbCronBeans) {
				String strID = tbCronBean.getTbcName() + "_" + tbCronBean.getTbcId();
				JobDetail job = new JobDetail("job" + strID, "group" + strID, Class.forName(tbCronBean.getTbcClass()));
				CronTrigger trigger = new CronTrigger("trigger" + strID, "group" + strID, "job" + strID, "group" + strID, tbCronBean.getTbcCronExp());
				sched.addJob(job, true);
				sched.scheduleJob(trigger);
				log.debug("Schedule Job : " + strID);
			}
		} catch (SchedulerException e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} catch (ParseException e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} catch (DAOException e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}
	}
}