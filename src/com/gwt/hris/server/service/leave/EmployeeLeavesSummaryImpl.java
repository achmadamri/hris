package com.gwt.hris.server.service.leave;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLeavesSummaryBeanModel;
import com.gwt.hris.client.service.leave.EmployeeLeavesSummaryInterface;
import com.gwt.hris.db.ViewEmployeeLeavesSummaryBean;
import com.gwt.hris.db.ViewEmployeeLeavesSummaryManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeeLeavesSummaryImpl extends MainRemoteServiceServlet implements EmployeeLeavesSummaryInterface {
	
	private static final long serialVersionUID = 168806129169079368L;

	public PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel> getTbAssignedLeavesPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");
		
		List<ViewEmployeeLeavesSummaryBeanModel> list = new ArrayList<ViewEmployeeLeavesSummaryBeanModel>();

		ViewEmployeeLeavesSummaryBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 47, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where 1=1 ";
			
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere += "and (tbe_id in (select tbe_id from tb_report_to where tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + ") or tbe_id = " + tbEmployeeBeanModel.getTbeId() + ") ";
			}
			
			if ("Employee Name".equals(searchBy)) {
				strWhere += "and tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Leave Type".equals(searchBy)) {
				strWhere += "and tblt_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Leave Entitled".equals(searchBy)) {
				strWhere += "and tbjt_leave_entitled like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Leave Taken".equals(searchBy)) {
				strWhere += "and sum_tbale_leave_taken like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Leave Available".equals(searchBy)) {
				strWhere += "and min_tbale_leave_available like '%" + searchValue.replaceAll("'", "") + "%' or (tbjt_leave_entitled like '%" + searchValue.replaceAll("'", "") + "%' and min_tbale_leave_available = 999)";
			}

			if ("".equals(strWhere)) {
				size = ViewEmployeeLeavesSummaryManager.getInstance().countAll();
			} else {
				size = ViewEmployeeLeavesSummaryManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeLeavesSummaryManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeLeavesSummaryBeanModel data = ViewEmployeeLeavesSummaryManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeLeavesSummaryBeanModel data = new ViewEmployeeLeavesSummaryBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>(list, config.getOffset(), size);
	}
	
	public PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel> getTbAssignedLeavesPagingEss(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeeLeavesSummaryBeanModel> list = new ArrayList<ViewEmployeeLeavesSummaryBeanModel>();

		ViewEmployeeLeavesSummaryBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 47, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where " + searchBy + " = " + searchValue.replaceAll("'", "") + " ";
			
			if ("".equals(strWhere)) {
				size = ViewEmployeeLeavesSummaryManager.getInstance().countAll();
			} else {
				size = ViewEmployeeLeavesSummaryManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeLeavesSummaryManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeLeavesSummaryBeanModel data = ViewEmployeeLeavesSummaryManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeLeavesSummaryBeanModel data = new ViewEmployeeLeavesSummaryBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>(list, config.getOffset(), size);
	}
}
