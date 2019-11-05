package com.gwt.hris.client.service.admin.systemsettings;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCronBeanModel;

public interface CronInterfaceAsync {
	void submitCron(TbCronBeanModel TbCronBeanModel, AsyncCallback<ReturnBean> callback);

	void getCron(int tbesId, AsyncCallback<TbCronBeanModel> callback);
	
	void runManualCron(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteCron(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkCron(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getCronPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbCronBeanModel>> callback);

	void getTbCronAll(AsyncCallback<TbCronBeanModel> callback);
}
