package com.gwt.hris.client.service.reports;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ViewTimesheetProjectBeanModel;

@RemoteServiceRelativePath("employeetimesheet")
public interface EmployeeTimesheetInterface extends RemoteService {
	PagingLoadResult<ViewTimesheetProjectBeanModel> getPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
