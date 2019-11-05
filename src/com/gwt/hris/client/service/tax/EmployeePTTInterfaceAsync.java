package com.gwt.hris.client.service.tax;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeePttBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeePttBeanModel;

public interface EmployeePTTInterfaceAsync {
	void submitPtt(TbEmployeePttBeanModel TbEmployeePttBeanModel, AsyncCallback<ReturnBean> callback);

	void getPtt(int tbesId, AsyncCallback<TbEmployeePttBeanModel> callback);

	void deletePtt(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkPtt(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getPttPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeePttBeanModel>> callback);

	void getViewEmployeePttAll(AsyncCallback<ViewEmployeePttBeanModel> callback);

	void getPttPagingESS(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeePttBeanModel>> callback);
}
