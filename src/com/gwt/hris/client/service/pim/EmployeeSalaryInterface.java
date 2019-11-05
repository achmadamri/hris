package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeSalaryBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeCurrencyBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeSalaryBeanModel;

@RemoteServiceRelativePath("employeesalary")
public interface EmployeeSalaryInterface extends RemoteService {
	ReturnBean submitEmployeeSalary(TbEmployeeSalaryBeanModel TbEmployeeSalaryBeanModel);

	PagingLoadResult<ViewEmployeeSalaryBeanModel> getEmployeeSalaryPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteEmployeeSalary(Integer id);

	TbEmployeeSalaryBeanModel getEmployeeSalary(int id);

	TbPaygradeBeanModel getTbPaygradeAll();

	TbCurrencyBeanModel getTbCurrencyByPaygrade(int tbpId);

	TbPaygradeCurrencyBeanModel getTbPaygradeCurrency(int tbpId, int tbcId);
	
	TbCurrencyBeanModel getTbCurrencyByEmployee(int intTbeId);
}
