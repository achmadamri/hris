package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;

public interface EmploymentStatusInterfaceAsync {
	void submitEmploymentStatus(TbEmploymentStatusBeanModel TbEmploymentStatusBeanModel, AsyncCallback<ReturnBean> callback);

	void getEmploymentStatus(int tbesId, AsyncCallback<TbEmploymentStatusBeanModel> callback);

	void deleteEmploymentStatus(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkEmploymentStatus(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getEmploymentStatusPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbEmploymentStatusBeanModel>> callback);
}
