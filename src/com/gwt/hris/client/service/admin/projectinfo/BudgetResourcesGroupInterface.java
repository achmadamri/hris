package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbBudgetResourcesGroupBeanModel;

@RemoteServiceRelativePath("budgetresourcesgroup")
public interface BudgetResourcesGroupInterface extends RemoteService {
	ReturnBean submitBudgetResourcesGroup(TbBudgetResourcesGroupBeanModel TbBudgetResourcesGroupBeanModel);

	TbBudgetResourcesGroupBeanModel getBudgetResourcesGroup(int tbesId);

	ReturnBean deleteBudgetResourcesGroup(Integer tbesId);

	ReturnBean deleteBulkBudgetResourcesGroup(Integer tbesIds[]);

	PagingLoadResult<TbBudgetResourcesGroupBeanModel> getBudgetResourcesGroupPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
