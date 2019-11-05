package com.gwt.hris.server.service.reports;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewReportEmployeeAttendanceBeanModel;
import com.gwt.hris.client.service.reports.EmployeeAttendanceInterface;
import com.gwt.hris.db.ViewReportEmployeeAttendanceBean;
import com.gwt.hris.db.ViewReportEmployeeAttendanceManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeeAttendanceImpl extends MainRemoteServiceServlet implements EmployeeAttendanceInterface {
	
	private static final long serialVersionUID = -6923124747045112411L;

	public PagingLoadResult<ViewReportEmployeeAttendanceBeanModel> getPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");
		
		List<ViewReportEmployeeAttendanceBeanModel> list = new ArrayList<ViewReportEmployeeAttendanceBeanModel>();

		ViewReportEmployeeAttendanceBean datas[] = null;
		int size = 0;
		try {
			if (this.getThreadLocalRequest() != null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 128, SystemUtil.ACCESS_VIEW) == false) {
					throw new SystemException("No view access");
				}
			}

			String strWhere = "where 1=1 ";
			
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere += "and (tbe_id = " + tbEmployeeBeanModel.getTbeId() + " or tbe_id in (select tbe_id from tb_report_to where tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + " or tbe_id = " + tbEmployeeBeanModel.getTbeId() + ")) ";
			}
			
			if ("Name".equals(searchBy)) {
				strWhere += "and tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Date".equals(searchBy)) {
				strWhere += "and rtba_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("In Time".equals(searchBy)) {
				strWhere += "and rtba_in_time like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("In Time Diff".equals(searchBy)) {
				strWhere += "and rtba_in_time_diff like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Out Time".equals(searchBy)) {
				strWhere += "and rtba_out_time like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Out Time Diff".equals(searchBy)) {
				strWhere += "and rtba_out_time_diff like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("In Note".equals(searchBy)) {
				strWhere += "and rtba_in_note like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Out Note".equals(searchBy)) {
				strWhere += "and rtba_out_note like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Shift ID".equals(searchBy)) {
				strWhere += "and tbs_shift_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Shift Name".equals(searchBy)) {
				strWhere += "and tbs_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("In Name".equals(searchBy)) {
				strWhere += "and tbs_in_time like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Out Name".equals(searchBy)) {
				strWhere += "and tbs_out_time like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewReportEmployeeAttendanceManager.getInstance().countAll();
			} else {
				size = ViewReportEmployeeAttendanceManager.getInstance().countWhere(strWhere);
			}

			if (config != null) {
				if (config.getSortField() != null) {
					if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
						strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
					}
				}

				datas = ViewReportEmployeeAttendanceManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());
			} else {
				datas = ViewReportEmployeeAttendanceManager.getInstance().loadByWhere(strWhere);
			}

			for (int i = 0; i < datas.length; i++) {
				ViewReportEmployeeAttendanceBeanModel data = ViewReportEmployeeAttendanceManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewReportEmployeeAttendanceBeanModel data = new ViewReportEmployeeAttendanceBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		if (config != null) {
			return new BasePagingLoadResult<ViewReportEmployeeAttendanceBeanModel>(list, config.getOffset(), size);
		} else {
			return new BasePagingLoadResult<ViewReportEmployeeAttendanceBeanModel>(list, 0, size);
		}
	}
}
