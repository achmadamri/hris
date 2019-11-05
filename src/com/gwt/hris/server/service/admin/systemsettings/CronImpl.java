package com.gwt.hris.server.service.admin.systemsettings;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.systemsettings.CronInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCronBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbCronBean;
import com.gwt.hris.db.TbCronManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class CronImpl extends MainRemoteServiceServlet implements CronInterface {
	
	private static final long serialVersionUID = -3538084198335408911L;

	public ReturnBean submitCron(TbCronBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbCronBean bean = null;

			if (beanModel.getTbcId() != null) {
				bean = TbCronManager.getInstance().loadByPrimaryKey(beanModel.getTbcId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 131, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbCronManager.getInstance().createTbCronBean();

				bean = TbCronManager.getInstance().toBean(beanModel, bean);

				TbCronManager.getInstance().save(bean);
				
				String strID = bean.getTbcName() + "_" + bean.getTbcId();
				
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sched = sf.getScheduler();
				
				JobDetail job = new JobDetail("job" + strID, "group" + strID, Class.forName(bean.getTbcClass()));
				CronTrigger trigger = new CronTrigger("trigger" + strID, "group" + strID, "job" + strID, "group" + strID, bean.getTbcCronExp());
				sched.addJob(job, true);
				sched.scheduleJob(trigger);
				log.debug("Schedule Job : " + strID);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				bean = TbCronManager.getInstance().toBean(beanModel, bean);

				TbCronManager.getInstance().save(bean);

				String strID = bean.getTbcName() + "_" + bean.getTbcId();
				
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sched = sf.getScheduler();
				
				CronTrigger trigger = new CronTrigger("trigger" + strID, "group" + strID, "job" + strID, "group" + strID, bean.getTbcCronExp());
				sched.rescheduleJob("trigger" + strID, "group" + strID, trigger);
				
				log.debug("Reschedule Job : " + strID);
				
				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbCronManager.getInstance().toBeanModel(bean);
			returnValue.set("model", beanModel);

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public TbCronBeanModel getCron(int id) {
		TbCronBeanModel returnValue = new TbCronBeanModel();

		try {
			TbCronBean TbCronBean = TbCronManager.getInstance().loadByPrimaryKey(id);

			if (TbCronBean != null) {
				returnValue = TbCronManager.getInstance().toBeanModel(TbCronBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("");
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}
	
	public ReturnBean runManualCron(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 131, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}
			
			TbCronBean tbCronBean = TbCronManager.getInstance().loadByPrimaryKey(id);

			Class cls = Class.forName(tbCronBean.getTbcClass());
			Object obj = cls.newInstance();
			
			Method method = cls.getDeclaredMethod("runManual", null);
			method.invoke(obj, null);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Run Manual");

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public ReturnBean deleteCron(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 131, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();
			
			TbCronBean tbCronBean = TbCronManager.getInstance().loadByPrimaryKey(id);

			String strID = tbCronBean.getTbcName() + "_" + tbCronBean.getTbcId();
			
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();
			sched.deleteJob("job" + strID, "group" + strID);
			
			log.debug("Delete Job : " + strID);
			
			TbCronManager.getInstance().deleteByPrimaryKey(id);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Deleted");

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public ReturnBean deleteBulkCron(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 131, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();
			
			String strId = "";
			for (Integer id : ids) {
				TbCronBean tbCronBean = TbCronManager.getInstance().loadByPrimaryKey(id);

				String strID = tbCronBean.getTbcName() + "_" + tbCronBean.getTbcId();
				
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sched = sf.getScheduler();
				sched.deleteJob("job" + strID, "group" + strID);
				
				log.debug("Delete Job : " + strID);
				
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbCronManager.getInstance().deleteByWhere("where tbc_id in (" + strId + ")");

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Deleted");

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public PagingLoadResult<TbCronBeanModel> getCronPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbCronBeanModel> list = new ArrayList<TbCronBeanModel>();

		TbCronBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 131, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Name".equals(searchBy)) {
				strWhere = "where tbc_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Class".equals(searchBy)) {
				strWhere = "where tbc_class like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbCronManager.getInstance().countAll();
			} else {
				size = TbCronManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbCronManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbCronBeanModel data = TbCronManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbCronBeanModel data = new TbCronBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbCronBeanModel>(list, config.getOffset(), size);
	}

	public TbCronBeanModel getTbCronAll() {
		TbCronBeanModel returnValue = new TbCronBeanModel();

		try {
			TbCronBean TbCronBeans[] = TbCronManager.getInstance().loadAll();
			TbCronBeanModel TbCronBeanModels[] = TbCronManager.getInstance().toBeanModels(TbCronBeans);

			returnValue.setModels(TbCronBeanModels);
			returnValue.setOperationStatus(true);
			returnValue.setMessage("");
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}
}
