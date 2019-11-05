package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;

public interface CustomersInterfaceAsync {
	void submitCustomer(TbCustomerBeanModel TbCustomerBeanModel, AsyncCallback<ReturnBean> callback);

	void getCustomer(int tbesId, AsyncCallback<TbCustomerBeanModel> callback);

	void deleteCustomer(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkCustomer(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getCustomerPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbCustomerBeanModel>> callback);
}
