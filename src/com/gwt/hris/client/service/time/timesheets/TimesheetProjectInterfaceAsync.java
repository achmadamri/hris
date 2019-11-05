package com.gwt.hris.client.service.time.timesheets;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ViewTimesheetProjectBeanModel;

public interface TimesheetProjectInterfaceAsync {
	void getTimesheetPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewTimesheetProjectBeanModel>> callback);
}
