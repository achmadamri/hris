package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;

public interface EmployeeListInterfaceAsync {
	void submitEmployee(TbEmployeeBeanModel TbEmployeeBeanModel, AsyncCallback<ReturnBean> callback);

	void getEmployee(int tbesId, AsyncCallback<TbEmployeeBeanModel> callback);

	void disableEmployee(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void disableBulkEmployee(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getEmployeePaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeInformationBeanModel>> callback);

	void getTbEmployeeAll(AsyncCallback<TbEmployeeBeanModel> callback);
}