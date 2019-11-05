package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeSalaryBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeCurrencyBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeSalaryBeanModel;

public interface EmployeeSalaryInterfaceAsync {
	void submitEmployeeSalary(TbEmployeeSalaryBeanModel TbEmployeeSalaryBeanModel, AsyncCallback<ReturnBean> callback);

	void getEmployeeSalaryPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeSalaryBeanModel>> callback);

	void deleteEmployeeSalary(Integer id, AsyncCallback<ReturnBean> callback);

	void getEmployeeSalary(int id, AsyncCallback<TbEmployeeSalaryBeanModel> callback);

	void getTbPaygradeAll(AsyncCallback<TbPaygradeBeanModel> callback);

	void getTbCurrencyByPaygrade(int tbpId, AsyncCallback<TbCurrencyBeanModel> callback);

	void getTbPaygradeCurrency(int tbpId, int tbcId, AsyncCallback<TbPaygradeCurrencyBeanModel> callback);

	void getTbCurrencyByEmployee(int intTbeId, AsyncCallback<TbCurrencyBeanModel> callback);
}
