package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbProjectActivitiesBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;
import com.gwt.hris.client.service.bean.ViewProjectActivitiesGroupBeanModel;

@RemoteServiceRelativePath("projectactivities")
public interface ProjectActivitiesInterface extends RemoteService {
	ReturnBean submitProjectActivities(TbProjectActivitiesBeanModel TbProjectActivitiesBeanModel);

	TbProjectActivitiesBeanModel getProjectActivities(int tbesId);

	ReturnBean deleteProjectActivities(Integer tbesId);

	ReturnBean deleteBulkProjectActivities(Integer tbesIds[]);

	PagingLoadResult<ViewProjectActivitiesGroupBeanModel> getProjectActivitiesPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbProjectActivitiesGroupBeanModel getTbProjectActivitiesGroupAll();
}
