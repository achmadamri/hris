package com.gwt.hris.client.service.admin.systemsettings;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;

public interface CurrencyInterfaceAsync {
	void submitCurrency(TbCurrencyBeanModel TbCurrencyBeanModel, AsyncCallback<ReturnBean> callback);

	void getCurrency(int tbesId, AsyncCallback<TbCurrencyBeanModel> callback);

	void deleteCurrency(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkCurrency(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getCurrencyPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbCurrencyBeanModel>> callback);

	void getTbCurrencyAll(AsyncCallback<TbCurrencyBeanModel> callback);
}
