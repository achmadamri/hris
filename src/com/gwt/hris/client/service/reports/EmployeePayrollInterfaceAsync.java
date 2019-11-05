package com.gwt.hris.client.service.reports;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ViewReportEmployeePayrollBeanModel;

public interface EmployeePayrollInterfaceAsync {
	void getPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewReportEmployeePayrollBeanModel>> callback);
}
