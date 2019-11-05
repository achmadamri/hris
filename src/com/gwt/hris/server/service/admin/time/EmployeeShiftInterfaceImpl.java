package com.gwt.hris.server.service.admin.time;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.time.EmployeeShiftInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbShiftBeanModel;
import com.gwt.hris.client.service.bean.ViewAttendanceBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAttendanceManager;
import com.gwt.hris.db.TbEmployeeShiftBean;
import com.gwt.hris.db.TbEmployeeShiftManager;
import com.gwt.hris.db.TbShiftBean;
import com.gwt.hris.db.TbShiftManager;
import com.gwt.hris.db.ViewAttendanceBean;
import com.gwt.hris.db.ViewAttendanceCustomManager;
import com.gwt.hris.db.ViewAttendanceManager;
import com.gwt.hris.db.ViewEmployeeInformationBean;
import com.gwt.hris.db.ViewEmployeeInformationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeeShiftInterfaceImpl extends MainRemoteServiceServlet implements EmployeeShiftInterface {
	
	private static final long serialVersionUID = 3188088111822942218L;

	public ReturnBean submitEmployeeShifts(Integer tbeId, Integer tbsId, String strDates[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			String strDatesSQL = "";
			for (String strDate : strDates) {
				strDatesSQL += "'" + strDate + "',";
			}

			int count = TbAttendanceManager.getInstance().countWhere("where tba_date in (" + strDatesSQL + "'') and tbe_id = " + tbeId);

			if (count == 0) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 105, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				TbEmployeeShiftManager.getInstance().deleteByWhere("where tbes_date in (" + strDatesSQL + "'') and tbe_id = " + tbeId);

				TbEmployeeShiftBean tbEmployeeShiftBeans[] = new TbEmployeeShiftBean[strDates.length];
				for (int i = 0; i < tbEmployeeShiftBeans.length; i++) {
					tbEmployeeShiftBeans[i] = TbEmployeeShiftManager.getInstance().createTbEmployeeShiftBean();
					tbEmployeeShiftBeans[i].setTbeId(tbeId);
					tbEmployeeShiftBeans[i].setTbsId(tbsId);
					tbEmployeeShiftBeans[i].setTbesDate(strDates[i]);
				}
				TbEmployeeShiftManager.getInstance().save(tbEmployeeShiftBeans);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");

				commit = true;
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Fail Saved");
			}
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

	public TbShiftBeanModel getTbShiftAll() {
		TbShiftBeanModel returnValue = new TbShiftBeanModel();

		try {
			TbShiftBean tbShiftBeans[] = TbShiftManager.getInstance().loadAll();
			TbShiftBeanModel tbShiftBeanModels[] = TbShiftManager.getInstance().toBeanModels(tbShiftBeans);

			returnValue.setModels(tbShiftBeanModels);
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

	public PagingLoadResult<ViewEmployeeInformationBeanModel> getEmployeePaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeeInformationBeanModel> list = new ArrayList<ViewEmployeeInformationBeanModel>();

		ViewEmployeeInformationBean datas[] = null;
		int size = 0;
		try {
			String strWhere = "";
			if ("Employee ID".equals(searchBy)) {
				strWhere = "where tbe_employee_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Employee Name".equals(searchBy)) {
				strWhere = "where tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Job Title".equals(searchBy)) {
				strWhere = "where tbjt_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Employment Status".equals(searchBy)) {
				strWhere = "where tbes_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Sub-Division".equals(searchBy)) {
				strWhere = "where tbo_nama like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewEmployeeInformationManager.getInstance().countAll();
			} else {
				size = ViewEmployeeInformationManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeInformationManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeInformationBeanModel data = ViewEmployeeInformationManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeInformationBeanModel data = new ViewEmployeeInformationBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeInformationBeanModel>(list, config.getOffset(), size);
	}

	public PagingLoadResult<ViewAttendanceBeanModel> getTimesheetPaging(final PagingLoadConfig config, Integer intTbeId, String month, String searchBy, String searchValue) {
		List<ViewAttendanceBeanModel> list = new ArrayList<ViewAttendanceBeanModel>();

		ViewAttendanceBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 105, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

			if ("".equals(month)) {
				simpleDateFormat = new SimpleDateFormat("yyyy-MM");
				month = simpleDateFormat.format(cal.getTime());
			} else {
				simpleDateFormat = new SimpleDateFormat("yyyy-");
				month = simpleDateFormat.format(cal.getTime()) + month;
			}

			size = ViewAttendanceCustomManager.getInstance().countWhereUnion(intTbeId, month);

			datas = ViewAttendanceCustomManager.getInstance().loadByWhereUnion(intTbeId, month);

			for (int i = 0; i < datas.length; i++) {
				ViewAttendanceBeanModel data = ViewAttendanceManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewAttendanceBeanModel data = new ViewAttendanceBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewAttendanceBeanModel>(list, config.getOffset(), size);
	}
}
