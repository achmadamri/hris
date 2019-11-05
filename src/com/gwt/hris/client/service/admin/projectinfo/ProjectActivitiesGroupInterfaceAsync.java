package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;

public interface ProjectActivitiesGroupInterfaceAsync {
	void submitProjectActivitiesGroup(TbProjectActivitiesGroupBeanModel TbProjectActivitiesGroupBeanModel, AsyncCallback<ReturnBean> callback);

	void getProjectActivitiesGroup(int tbesId, AsyncCallback<TbProjectActivitiesGroupBeanModel> callback);

	void deleteProjectActivitiesGroup(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkProjectActivitiesGroup(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getProjectActivitiesGroupPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbProjectActivitiesGroupBeanModel>> callback);
}
