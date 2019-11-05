package com.gwt.hris.client.service.admin.systemsettings;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMenuBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuBeanModel;

public interface MenuInterfaceAsync {
	void getMenu(AsyncCallback<TbMenuBeanModel> callback);

	void submitMenu(TbMenuBeanModel beanModel, AsyncCallback<ReturnBean> callback);

	void getMenu(int id, AsyncCallback<TbMenuBeanModel> callback);

	void deleteMenu(Integer id, AsyncCallback<ReturnBean> callback);

	void deleteBulkMenu(Integer ids[], AsyncCallback<ReturnBean> callback);

	void getTbMenuPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewMenuBeanModel>> callback);

	void getTbMenuAll(AsyncCallback<TbMenuBeanModel> callback);

	void getMenuTree(AsyncCallback<TbMenuBeanModel> callback);
}
