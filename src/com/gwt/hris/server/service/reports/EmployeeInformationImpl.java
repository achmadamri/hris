package com.gwt.hris.server.service.reports;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewReportEmployeeInformationBeanModel;
import com.gwt.hris.client.service.reports.EmployeeInformationInterface;
import com.gwt.hris.db.ViewReportEmployeeInformationBean;
import com.gwt.hris.db.ViewReportEmployeeInformationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeeInformationImpl extends MainRemoteServiceServlet implements EmployeeInformationInterface {
	
	private static final long serialVersionUID = -6923124747045112411L;

	public PagingLoadResult<ViewReportEmployeeInformationBeanModel> getPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");
		
		List<ViewReportEmployeeInformationBeanModel> list = new ArrayList<ViewReportEmployeeInformationBeanModel>();

		ViewReportEmployeeInformationBean datas[] = null;
		int size = 0;
		try {
			if (this.getThreadLocalRequest() != null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 120, SystemUtil.ACCESS_VIEW) == false) {
					throw new SystemException("No view access");
				}
			}

			String strWhere = "where 1=1 ";
			
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere += "and tbe_id in (select tbe_id from tb_report_to where tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + " or tbe_id = " + tbEmployeeBeanModel.getTbeId() + ") ";
			}
			
			if ("City".equals(searchBy)) {
				strWhere += "where tbcd_city like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Country".equals(searchBy)) {
				strWhere += "where tbn_nama like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Currency".equals(searchBy)) {
				strWhere += "where tbes_currency_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("DOB".equals(searchBy)) {
				strWhere += "where tbe_dob like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Driver Lic. No".equals(searchBy)) {
				strWhere += "where tbe_driver_license_no like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Driver Lic. Exp.".equals(searchBy)) {
				strWhere += "where tbe_driver_license_expiry like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Emp. Joined Date".equals(searchBy)) {
				strWhere += "where tbe_joined_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Employee ID".equals(searchBy)) {
				strWhere += "where tbe_employee_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Employee Name".equals(searchBy)) {
				strWhere += "where tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Employment Status".equals(searchBy)) {
				strWhere += "where tbes_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Gender".equals(searchBy)) {
				strWhere += "where tbe_gender like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Home Phone".equals(searchBy)) {
				strWhere += "where tbcd_home_phone like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("ID No".equals(searchBy)) {
				strWhere += "where tbe_id_no like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Job Title".equals(searchBy)) {
				strWhere += "where tbjt_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Job Joined Date".equals(searchBy)) {
				strWhere += "where tbj_joined_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Marital Status".equals(searchBy)) {
				strWhere += "where tbe_marital_status like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Mobile Phone".equals(searchBy)) {
				strWhere += "where tbcd_mobile_phone like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Organization".equals(searchBy)) {
				strWhere += "where tbo_nama like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Province".equals(searchBy)) {
				strWhere += "where tbcd_province like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Report To".equals(searchBy)) {
				strWhere += "where tbe_name_report_to like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Salary Grade".equals(searchBy)) {
				strWhere += "where tbp_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Smoker".equals(searchBy)) {
				strWhere += "where tbe_smoker like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Street".equals(searchBy)) {
				strWhere += "where tbcd_street like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Tax No".equals(searchBy)) {
				strWhere += "where tbe_tax_no like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Work Phone".equals(searchBy)) {
				strWhere += "where tbcd_work_phone like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Zip".equals(searchBy)) {
				strWhere += "where tbcd_zip_code like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewReportEmployeeInformationManager.getInstance().countAll();
			} else {
				size = ViewReportEmployeeInformationManager.getInstance().countWhere(strWhere);
			}

			if (config != null) {
				if (config.getSortField() != null) {
					if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
						strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
					}
				}

				datas = ViewReportEmployeeInformationManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());
			} else {
				datas = ViewReportEmployeeInformationManager.getInstance().loadByWhere(strWhere);
			}

			for (int i = 0; i < datas.length; i++) {
				ViewReportEmployeeInformationBeanModel data = ViewReportEmployeeInformationManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewReportEmployeeInformationBeanModel data = new ViewReportEmployeeInformationBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		if (config != null) {
			return new BasePagingLoadResult<ViewReportEmployeeInformationBeanModel>(list, config.getOffset(), size);
		} else {
			return new BasePagingLoadResult<ViewReportEmployeeInformationBeanModel>(list, 0, size);
		}
	}
}
