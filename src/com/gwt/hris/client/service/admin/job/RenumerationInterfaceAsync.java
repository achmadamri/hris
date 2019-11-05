package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewRenumerationBeanModel;

public interface RenumerationInterfaceAsync {
	void submitRenumeration(TbRenumerationBeanModel TbRenumerationBeanModel, AsyncCallback<ReturnBean> callback);

	void getRenumeration(int tbesId, AsyncCallback<TbRenumerationBeanModel> callback);

	void deleteRenumeration(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkRenumeration(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getRenumerationPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewRenumerationBeanModel>> callback);

	void getRenumerationPagingEss(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeRenumerationBeanModel>> callback);
}
