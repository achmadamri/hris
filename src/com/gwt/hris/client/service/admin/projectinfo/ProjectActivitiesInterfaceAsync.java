package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbProjectActivitiesBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;
import com.gwt.hris.client.service.bean.ViewProjectActivitiesGroupBeanModel;

public interface ProjectActivitiesInterfaceAsync {
	void submitProjectActivities(TbProjectActivitiesBeanModel TbProjectActivitiesBeanModel, AsyncCallback<ReturnBean> callback);

	void getProjectActivities(int tbesId, AsyncCallback<TbProjectActivitiesBeanModel> callback);

	void deleteProjectActivities(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkProjectActivities(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getProjectActivitiesPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewProjectActivitiesGroupBeanModel>> callback);

	void getTbProjectActivitiesGroupAll(AsyncCallback<TbProjectActivitiesGroupBeanModel> callback);
}
