package com.gwt.hris.client.service.admin.companyinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCompanyPropertyBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewPropertyEmployeeBeanModel;

public interface CompanyPropertyInterfaceAsync {
	void getCompanyProperty(int tblId, AsyncCallback<TbCompanyPropertyBeanModel> callback);

	void submitCompanyProperty(TbCompanyPropertyBeanModel beanModel, AsyncCallback<ReturnBean> callback);

	void deleteCompanyProperty(Integer tblId, AsyncCallback<ReturnBean> callback);

	void deleteBulkCompanyProperty(Integer tblIds[], AsyncCallback<ReturnBean> callback);

	void getCompanyPropertyPaging(final PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewPropertyEmployeeBeanModel>> callback);

	void getEmployeePaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbEmployeeBeanModel>> callback);

	void assignCompanyProperty(int tbcpId, int tbeId, AsyncCallback<ReturnBean> callback);

	void removeAssignCompanyProperty(int tbcpId, AsyncCallback<ReturnBean> callback);
}
