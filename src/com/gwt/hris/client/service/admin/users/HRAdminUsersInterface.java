package com.gwt.hris.client.service.admin.users;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAdminUserGroupsBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;

@RemoteServiceRelativePath("hradminusers")
public interface HRAdminUsersInterface extends RemoteService {
	ReturnBean submitLogin(TbLoginBeanModel TbLoginBeanModel);

	TbLoginBeanModel getLogin(int tbesId);

	ReturnBean deleteLogin(Integer tbesId);

	ReturnBean deleteBulkLogin(Integer tbesIds[]);

	PagingLoadResult<TbLoginBeanModel> getLoginPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbAdminUserGroupsBeanModel getTbAdminUserGroupsAll();

	TbEmployeeBeanModel getTbEmployeeAll();

	TbEmployeeBeanModel getTbEmployeeById(int id);
}
