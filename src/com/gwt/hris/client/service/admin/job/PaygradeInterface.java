package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeCurrencyBeanModel;
import com.gwt.hris.client.service.bean.ViewPaygradeCurrencyBeanModel;

@RemoteServiceRelativePath("paygrade")
public interface PaygradeInterface extends RemoteService {
	ReturnBean submitPaygrade(TbPaygradeBeanModel TbPaygradeBeanModel);

	TbPaygradeBeanModel getPaygrade(int tblId);

	ReturnBean deletePaygrade(Integer tblId);

	ReturnBean deleteBulkPaygrade(Integer tblIds[]);

	PagingLoadResult<TbPaygradeBeanModel> getPaygradePaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbCurrencyBeanModel getTbCurrencyAll();

	ReturnBean submitPaygradeCurrency(TbPaygradeCurrencyBeanModel tbPaygradeBeanModel);

	PagingLoadResult<ViewPaygradeCurrencyBeanModel> getPaygradeCurrencyPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deletePaygradeCurrency(Integer tbpcId);
	
	TbPaygradeCurrencyBeanModel getPaygradeCurrency(int tbpcId);
}
