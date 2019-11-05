package com.gwt.hris.client.service.admin.qualification;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLicensesBeanModel;

@RemoteServiceRelativePath("licenses")
public interface LicensesInterface extends RemoteService {
	ReturnBean submitLicenses(TbLicensesBeanModel TbLicensesBeanModel);

	TbLicensesBeanModel getLicenses(int tbesId);

	ReturnBean deleteLicenses(Integer tbesId);

	ReturnBean deleteBulkLicenses(Integer tbesIds[]);

	PagingLoadResult<TbLicensesBeanModel> getLicensesPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
