package com.gwt.hris.client.service.admin.users;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAdminUserGroupsBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;

public interface HRAdminUsersInterfaceAsync {
	void submitLogin(TbLoginBeanModel TbLoginBeanModel, AsyncCallback<ReturnBean> callback);

	void getLogin(int tbesId, AsyncCallback<TbLoginBeanModel> callback);

	void deleteLogin(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkLogin(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getLoginPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbLoginBeanModel>> callback);

	void getTbAdminUserGroupsAll(AsyncCallback<TbAdminUserGroupsBeanModel> callback);

	void getTbEmployeeAll(AsyncCallback<TbEmployeeBeanModel> callback);

	void getTbEmployeeById(int id, AsyncCallback<TbEmployeeBeanModel> callback);
}
