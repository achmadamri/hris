package com.gwt.hris.server.service.time.timesheets;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;
import com.gwt.hris.client.service.bean.TbTimesheetBeanModel;
import com.gwt.hris.client.service.bean.ViewTimesheetBeanModel;
import com.gwt.hris.client.service.time.timesheets.ApprovalTimesheetInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbCustomerBean;
import com.gwt.hris.db.TbCustomerManager;
import com.gwt.hris.db.TbProjectActivitiesBean;
import com.gwt.hris.db.TbProjectActivitiesGroupBean;
import com.gwt.hris.db.TbProjectActivitiesGroupManager;
import com.gwt.hris.db.TbProjectActivitiesManager;
import com.gwt.hris.db.TbProjectBean;
import com.gwt.hris.db.TbProjectManager;
import com.gwt.hris.db.TbTimesheetBean;
import com.gwt.hris.db.TbTimesheetManager;
import com.gwt.hris.db.ViewTimesheetBean;
import com.gwt.hris.db.ViewTimesheetManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ApprovalTimesheetImpl extends MainRemoteServiceServlet implements ApprovalTimesheetInterface {
	
	private static final long serialVersionUID = 1147380468542423014L;

	public ReturnBean approval(Integer id, Integer status) {
		ReturnBean returnValue = new ReturnBean();
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 100, SystemUtil.ACCESS_APPROVE) == false) {
				throw new SystemException("No approval access");
			}

			Manager.getInstance().beginTransaction();

			TbTimesheetBean tbTimesheetBean = TbTimesheetManager.getInstance().loadByPrimaryKey(id);
			tbTimesheetBean.setTbtApprovalStatus(status);
			TbTimesheetManager.getInstance().save(tbTimesheetBean);

			returnValue.setOperationStatus(true);

			if (status == 1) {
				returnValue.setMessage("Success Approve");
			} else {
				int grandTotal = 0;

				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				boolean loop = true;
				while (loop) {
					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
						loop = false;
					} else {
						cal.add(Calendar.DATE, -1);
					}
				}

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

				TbTimesheetBean tbTimesheetBeans[] = TbTimesheetManager.getInstance().loadByWhere("where tbe_id = " + tbTimesheetBean.getTbeId() + " and date_format(tbt_start_of_week, '%Y-%m-%d') = '" + simpleDateFormat.format(cal.getTime()) + "' and tbt_approval_status in (0, 1)");
				for (int i = 0; i < tbTimesheetBeans.length; i++) {
					grandTotal += tbTimesheetBeans[i].getTbtTotalHour();
				}
				tbTimesheetBeans = TbTimesheetManager.getInstance().loadByWhere("where tbe_id = " + tbTimesheetBean.getTbeId() + " and date_format(tbt_start_of_week, '%Y-%m-%d') = '" + simpleDateFormat.format(cal.getTime()) + "'");
				for (int i = 0; i < tbTimesheetBeans.length; i++) {
					tbTimesheetBeans[i].setTbtGrandTotalHour(grandTotal);
				}

				TbTimesheetManager.getInstance().save(tbTimesheetBeans);

				returnValue.setMessage("Success Reject");
			}
			
			SystemUtil.getInstance().notification(SystemUtil.CHANNEL_EMAIL, 6, SystemUtil.UI_TIMESHEET, tbEmployeeBeanModel.getTbeId(), tbTimesheetBean.getTbeId(), new String[]{""});

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

	public ReturnBean deleteTimesheet(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 100, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbTimesheetManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkTimesheet(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 100, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbTimesheetManager.getInstance().deleteByWhere("where tbt_id in (" + strId + ")");

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

	public PagingLoadResult<ViewTimesheetBeanModel> getTimesheetPaging(final PagingLoadConfig config, String startOfWeek, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		List<ViewTimesheetBeanModel> list = new ArrayList<ViewTimesheetBeanModel>();

		ViewTimesheetBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 100, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_id in (select tbe_id from tb_report_to where tbrt_reporting_method = 0 and tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + ") ";

			if (startOfWeek.equals("")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				boolean loop = true;
				while (loop) {
					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
						loop = false;
					} else {
						cal.add(Calendar.DATE, -1);
					}
				}

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

				strWhere += "and tbt_start_of_week = '" + simpleDateFormat.format(cal.getTime()) + "' ";
			} else {
				strWhere += "and tbt_start_of_week like '%" + startOfWeek + "%' ";
			}

			if ("Project".equals(searchBy)) {
				strWhere += "and tbp_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Activity".equals(searchBy)) {
				strWhere += "and tbpa_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Start of Week".equals(searchBy)) {
				strWhere += "and tbt_start_of_week like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewTimesheetManager.getInstance().countAll();
			} else {
				size = ViewTimesheetManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewTimesheetManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewTimesheetBeanModel data = ViewTimesheetManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewTimesheetBeanModel data = new ViewTimesheetBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewTimesheetBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean getStartOfWeek() {
		ReturnBean returnValue = new ReturnBean();

		Calendar calNow = Calendar.getInstance();
		calNow.setTime(new Date());

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		boolean loop = true;
		List<String> lst = new ArrayList<String>();
		while (loop) {
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				if (cal.get(Calendar.YEAR) == calNow.get(Calendar.YEAR)) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					lst.add(simpleDateFormat.format(cal.getTime()));
				} else {
					loop = false;
				}
			}
			cal.add(Calendar.DATE, -1);
		}

		int i = 0;
		ReturnBean datas[] = new ReturnBean[lst.size()];
		Iterator<String> itr = lst.iterator();
		while (itr.hasNext()) {
			datas[i] = new ReturnBean();
			datas[i].set("startOfWeek", itr.next());
			i++;
		}

		returnValue.set("datas", datas);

		returnValue.setOperationStatus(true);

		return returnValue;
	}

	public TbProjectActivitiesBeanModel getTbProjectActivitiesByGroup(int tbpagId) {
		TbProjectActivitiesBeanModel returnValue = new TbProjectActivitiesBeanModel();

		try {
			TbProjectActivitiesBean TbProjectActivitiesBeans[] = TbProjectActivitiesManager.getInstance().loadBytbpa_fk_1(tbpagId);
			TbProjectActivitiesBeanModel TbProjectActivitiesBeanModels[] = TbProjectActivitiesManager.getInstance().toBeanModels(TbProjectActivitiesBeans);

			returnValue.setModels(TbProjectActivitiesBeanModels);
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

	public TbProjectBeanModel getTbProjectByCustomer(int tbcId) {
		TbProjectBeanModel returnValue = new TbProjectBeanModel();

		try {
			TbProjectBean TbProjectBeans[] = TbProjectManager.getInstance().loadBytb_project_fk_1(tbcId);
			TbProjectBeanModel TbProjectBeanModels[] = TbProjectManager.getInstance().toBeanModels(TbProjectBeans);

			returnValue.setModels(TbProjectBeanModels);
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

	public TbCustomerBeanModel getTbCustomerAll() {
		TbCustomerBeanModel returnValue = new TbCustomerBeanModel();

		try {
			TbCustomerBean TbCustomerBeans[] = TbCustomerManager.getInstance().loadAll();
			TbCustomerBeanModel TbCustomerBeanModels[] = TbCustomerManager.getInstance().toBeanModels(TbCustomerBeans);

			returnValue.setModels(TbCustomerBeanModels);
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

	public TbProjectActivitiesGroupBeanModel getTbProjectActivitiesGroupAll() {
		TbProjectActivitiesGroupBeanModel returnValue = new TbProjectActivitiesGroupBeanModel();

		try {
			TbProjectActivitiesGroupBean TbProjectActivitiesGroupBeans[] = TbProjectActivitiesGroupManager.getInstance().loadAll();
			TbProjectActivitiesGroupBeanModel TbProjectActivitiesGroupBeanModels[] = TbProjectActivitiesGroupManager.getInstance().toBeanModels(TbProjectActivitiesGroupBeans);

			returnValue.setModels(TbProjectActivitiesGroupBeanModels);
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

	public TbTimesheetBeanModel getTimesheet(int id) {
		TbTimesheetBeanModel returnValue = new TbTimesheetBeanModel();

		try {
			TbTimesheetBean tbTimesheetBean = TbTimesheetManager.getInstance().loadByPrimaryKey(id);

			if (tbTimesheetBean != null) {
				returnValue = TbTimesheetManager.getInstance().toBeanModel(tbTimesheetBean);

				Calendar cal = Calendar.getInstance();
				cal.setTime(returnValue.getTbtStartOfWeek());

				returnValue.set("tbtDay1Date", cal.getTime());

				cal.add(Calendar.DATE, 1);
				returnValue.set("tbtDay2Date", cal.getTime());

				cal.add(Calendar.DATE, 1);
				returnValue.set("tbtDay3Date", cal.getTime());

				cal.add(Calendar.DATE, 1);
				returnValue.set("tbtDay4Date", cal.getTime());

				cal.add(Calendar.DATE, 1);
				returnValue.set("tbtDay5Date", cal.getTime());

				cal.add(Calendar.DATE, 1);
				returnValue.set("tbtDay6Date", cal.getTime());

				cal.add(Calendar.DATE, 1);
				returnValue.set("tbtDay7Date", cal.getTime());

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
}
