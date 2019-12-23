package com.gwt.hris.client.service.performance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbKpiGroupBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;

public interface KPIGroupInterfaceAsync {
	void submitKpiGroup(TbKpiGroupBeanModel TbKpiGroupBeanModel, AsyncCallback<ReturnBean> callback);

	void getKpiGroup(int tbesId, AsyncCallback<TbKpiGroupBeanModel> callback);

	void deleteKpiGroup(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkKpiGroup(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getKpiGroupPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbKpiGroupBeanModel>> callback);

	void getTbKpiGroupAll(AsyncCallback<TbKpiGroupBeanModel> callback);
	
	void getTbProjectAll(AsyncCallback<TbProjectBeanModel> callback);
}
