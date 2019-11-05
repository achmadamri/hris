package com.gwt.hris.client.service.tax;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbPtkpBeanModel;

public interface PTKPInterfaceAsync {
	void submitPtkp(TbPtkpBeanModel TbPtkpBeanModel, AsyncCallback<ReturnBean> callback);

	void getPtkp(int tbesId, AsyncCallback<TbPtkpBeanModel> callback);

	void deletePtkp(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkPtkp(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getPtkpPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbPtkpBeanModel>> callback);

	void getTbPtkpAll(AsyncCallback<TbPtkpBeanModel> callback);
}
