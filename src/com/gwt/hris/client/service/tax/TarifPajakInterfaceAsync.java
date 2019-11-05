package com.gwt.hris.client.service.tax;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbTarifPajakBeanModel;

public interface TarifPajakInterfaceAsync {
	void submitTarifPajak(TbTarifPajakBeanModel TbTarifPajakBeanModel, AsyncCallback<ReturnBean> callback);

	void getTarifPajak(int tbesId, AsyncCallback<TbTarifPajakBeanModel> callback);

	void deleteTarifPajak(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkTarifPajak(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getTarifPajakPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbTarifPajakBeanModel>> callback);

	void getTbTarifPajakAll(AsyncCallback<TbTarifPajakBeanModel> callback);
}
