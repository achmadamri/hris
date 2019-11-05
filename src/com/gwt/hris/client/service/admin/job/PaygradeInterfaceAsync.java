package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeCurrencyBeanModel;
import com.gwt.hris.client.service.bean.ViewPaygradeCurrencyBeanModel;

public interface PaygradeInterfaceAsync {
	void submitPaygrade(TbPaygradeBeanModel TbPaygradeBeanModel, AsyncCallback<ReturnBean> callback);

	void getPaygrade(int tblId, AsyncCallback<TbPaygradeBeanModel> callback);

	void deletePaygrade(Integer tblId, AsyncCallback<ReturnBean> callback);

	void deleteBulkPaygrade(Integer tblIds[], AsyncCallback<ReturnBean> callback);

	void getPaygradePaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbPaygradeBeanModel>> callback);

	void getTbCurrencyAll(AsyncCallback<TbCurrencyBeanModel> callback);

	void submitPaygradeCurrency(TbPaygradeCurrencyBeanModel tbPaygradeBeanModel, AsyncCallback<ReturnBean> callback);

	void getPaygradeCurrencyPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewPaygradeCurrencyBeanModel>> callback);

	void deletePaygradeCurrency(Integer tbpcId, AsyncCallback<ReturnBean> callback);

	void getPaygradeCurrency(int tbpcId, AsyncCallback<TbPaygradeCurrencyBeanModel> callback);
}
