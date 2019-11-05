package com.gwt.hris.client.service.tax;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbPttBeanModel;

public interface PTTInterfaceAsync {
	void submitPTT(TbPttBeanModel TbPttBeanModel, AsyncCallback<ReturnBean> callback);

	void getPTT(int tbesId, AsyncCallback<TbPttBeanModel> callback);

	void deletePTT(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkPTT(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getPTTPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbPttBeanModel>> callback);

	void getTbPttAll(AsyncCallback<TbPttBeanModel> callback);
}
