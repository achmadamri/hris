package com.gwt.hris.client.service.admin.companyinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCompanyPropertyBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewPropertyEmployeeBeanModel;

@RemoteServiceRelativePath("companyproperty")
public interface CompanyPropertyInterface extends RemoteService {
	ReturnBean submitCompanyProperty(TbCompanyPropertyBeanModel beanModel);

	TbCompanyPropertyBeanModel getCompanyProperty(int tblId);

	ReturnBean deleteCompanyProperty(Integer tblId);

	ReturnBean deleteBulkCompanyProperty(Integer tblIds[]);

	PagingLoadResult<ViewPropertyEmployeeBeanModel> getCompanyPropertyPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	PagingLoadResult<TbEmployeeBeanModel> getEmployeePaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean assignCompanyProperty(int tbcpId, int tbeId);

	ReturnBean removeAssignCompanyProperty(int tbcpId);
}
