package com.gwt.hris.client.service.admin.systemsettings;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMenuBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuBeanModel;

@RemoteServiceRelativePath("menu")
public interface MenuInterface extends RemoteService {
	TbMenuBeanModel getMenu();

	ReturnBean submitMenu(TbMenuBeanModel beanModel);

	TbMenuBeanModel getMenu(int id);

	ReturnBean deleteMenu(Integer id);

	ReturnBean deleteBulkMenu(Integer ids[]);

	PagingLoadResult<ViewMenuBeanModel> getTbMenuPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbMenuBeanModel getTbMenuAll();
	
	TbMenuBeanModel getMenuTree();
}
