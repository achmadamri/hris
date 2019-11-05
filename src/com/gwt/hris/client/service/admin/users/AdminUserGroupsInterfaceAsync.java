package com.gwt.hris.client.service.admin.users;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAdminUserGroupsBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessAdminBeanModel;

public interface AdminUserGroupsInterfaceAsync {
	void submitAdminUserGroups(TbAdminUserGroupsBeanModel TbAdminUserGroupsBeanModel, AsyncCallback<ReturnBean> callback);

	void getAdminUserGroups(int tbesId, AsyncCallback<TbAdminUserGroupsBeanModel> callback);

	void deleteAdminUserGroups(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkAdminUserGroups(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getAdminUserGroupsPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbAdminUserGroupsBeanModel>> callback);

	void getMenuAccessPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewMenuAccessAdminBeanModel>> callback);
}
