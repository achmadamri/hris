package com.gwt.hris.client.service.time.timesheets;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ViewTimesheetProjectBeanModel;

@RemoteServiceRelativePath("timesheetproject")
public interface TimesheetProjectInterface extends RemoteService {
	PagingLoadResult<ViewTimesheetProjectBeanModel> getTimesheetPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
