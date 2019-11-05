package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLicensesBeanModel;
import com.gwt.hris.client.service.bean.TbLicensesBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLicensesBeanModel;

@RemoteServiceRelativePath("assignedlicenses")
public interface AssignedLicensesInterface extends RemoteService {
	ReturnBean submitAssignedLicenses(TbAssignedLicensesBeanModel TbAssignedLicensesBeanModel);

	PagingLoadResult<ViewEmployeeLicensesBeanModel> getAssignedLicensesPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteAssignedLicenses(Integer id);

	TbAssignedLicensesBeanModel getAssignedLicenses(int id);

	TbLicensesBeanModel getTbLicensesAll();
}
