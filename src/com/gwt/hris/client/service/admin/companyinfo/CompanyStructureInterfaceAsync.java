package com.gwt.hris.client.service.admin.companyinfo;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbOrganizationBeanModel;
import com.gwt.hris.client.service.bean.ViewCompanyOrganizationBeanModel;

public interface CompanyStructureInterfaceAsync {
	void getViewCompanyOrganizationsList(ViewCompanyOrganizationBeanModel _tbLocationBeanModel, AsyncCallback<List<ViewCompanyOrganizationBeanModel>> callback);

	void getTbOrganizations(TbOrganizationBeanModel tbOrganizationBeanModel, AsyncCallback<TbOrganizationBeanModel> callback);

	void getTbOrganization(int tblId, AsyncCallback<TbOrganizationBeanModel> callback);

	void submitTbOrganization(TbOrganizationBeanModel TbOrganizationBeanModel, AsyncCallback<ReturnBean> callback);

	void deleteTbOrganization(Integer tblId, AsyncCallback<ReturnBean> callback);
}
