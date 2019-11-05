package com.gwt.hris.client.service.admin.systemsettings;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;

@RemoteServiceRelativePath("currency")
public interface CurrencyInterface extends RemoteService {
	ReturnBean submitCurrency(TbCurrencyBeanModel TbCurrencyBeanModel);

	TbCurrencyBeanModel getCurrency(int tbesId);

	ReturnBean deleteCurrency(Integer tbesId);

	ReturnBean deleteBulkCurrency(Integer tbesIds[]);

	PagingLoadResult<TbCurrencyBeanModel> getCurrencyPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbCurrencyBeanModel getTbCurrencyAll();
}
