package com.gwt.hris.client.service.performance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbKpiBeanModel;
import com.gwt.hris.client.service.bean.TbKpiGroupBeanModel;
import com.gwt.hris.client.service.bean.ViewKpiBeanModel;

public interface KPIInterfaceAsync {
	void submitKpi(TbKpiBeanModel TbKpiBeanModel, AsyncCallback<ReturnBean> callback);

	void getKpi(int tbesId, AsyncCallback<TbKpiBeanModel> callback);

	void deleteKpi(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkKpi(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getKpiPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewKpiBeanModel>> callback);

	void getTbKpiGroupAll(AsyncCallback<TbKpiGroupBeanModel> callback);
}
