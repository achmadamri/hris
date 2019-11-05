package com.gwt.hris.client.service.admin.companyinfo;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbOrganizationBeanModel;
import com.gwt.hris.client.service.bean.ViewCompanyOrganizationBeanModel;

@RemoteServiceRelativePath("companystructure")
public interface CompanyStructureInterface extends RemoteService {
	List<ViewCompanyOrganizationBeanModel> getViewCompanyOrganizationsList(ViewCompanyOrganizationBeanModel _tbLocationBeanModel);

	TbOrganizationBeanModel getTbOrganizations(TbOrganizationBeanModel tbOrganizationBeanModel);

	TbOrganizationBeanModel getTbOrganization(int tblId);

	ReturnBean submitTbOrganization(TbOrganizationBeanModel TbOrganizationBeanModel);

	ReturnBean deleteTbOrganization(Integer tblId);
}
