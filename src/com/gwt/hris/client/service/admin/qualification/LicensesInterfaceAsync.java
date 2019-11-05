package com.gwt.hris.client.service.admin.qualification;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLicensesBeanModel;

public interface LicensesInterfaceAsync {
	void submitLicenses(TbLicensesBeanModel TbLicensesBeanModel, AsyncCallback<ReturnBean> callback);

	void getLicenses(int tbesId, AsyncCallback<TbLicensesBeanModel> callback);

	void deleteLicenses(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkLicenses(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getLicensesPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbLicensesBeanModel>> callback);
}
