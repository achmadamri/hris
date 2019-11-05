package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLicensesBeanModel;
import com.gwt.hris.client.service.bean.TbLicensesBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLicensesBeanModel;

public interface AssignedLicensesInterfaceAsync {
	void submitAssignedLicenses(TbAssignedLicensesBeanModel TbAssignedLicensesBeanModel, AsyncCallback<ReturnBean> callback);

	void getAssignedLicensesPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeLicensesBeanModel>> callback);

	void deleteAssignedLicenses(Integer id, AsyncCallback<ReturnBean> callback);

	void getAssignedLicenses(int id, AsyncCallback<TbAssignedLicensesBeanModel> callback);

	void getTbLicensesAll(AsyncCallback<TbLicensesBeanModel> callback);
}
