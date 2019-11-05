package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;

@RemoteServiceRelativePath("projectactivitiesgroup")
public interface ProjectActivitiesGroupInterface extends RemoteService {
	ReturnBean submitProjectActivitiesGroup(TbProjectActivitiesGroupBeanModel TbProjectActivitiesGroupBeanModel);

	TbProjectActivitiesGroupBeanModel getProjectActivitiesGroup(int tbesId);

	ReturnBean deleteProjectActivitiesGroup(Integer tbesId);

	ReturnBean deleteBulkProjectActivitiesGroup(Integer tbesIds[]);

	PagingLoadResult<TbProjectActivitiesGroupBeanModel> getProjectActivitiesGroupPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
