package com.gwt.hris.client.service.admin.users;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAdminUserGroupsBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessAdminBeanModel;

@RemoteServiceRelativePath("adminusergroups")
public interface AdminUserGroupsInterface extends RemoteService {
	ReturnBean submitAdminUserGroups(TbAdminUserGroupsBeanModel TbAdminUserGroupsBeanModel);

	TbAdminUserGroupsBeanModel getAdminUserGroups(int tbesId);

	ReturnBean deleteAdminUserGroups(Integer tbesId);

	ReturnBean deleteBulkAdminUserGroups(Integer tbesIds[]);

	PagingLoadResult<TbAdminUserGroupsBeanModel> getAdminUserGroupsPaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	PagingLoadResult<ViewMenuAccessAdminBeanModel> getMenuAccessPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
