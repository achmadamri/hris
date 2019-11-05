package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbBudgetResourcesGroupBeanModel;

public interface BudgetResourcesGroupInterfaceAsync {
	void submitBudgetResourcesGroup(TbBudgetResourcesGroupBeanModel TbBudgetResourcesGroupBeanModel, AsyncCallback<ReturnBean> callback);

	void getBudgetResourcesGroup(int tbesId, AsyncCallback<TbBudgetResourcesGroupBeanModel> callback);

	void deleteBudgetResourcesGroup(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkBudgetResourcesGroup(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getBudgetResourcesGroupPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbBudgetResourcesGroupBeanModel>> callback);
}
