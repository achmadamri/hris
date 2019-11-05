package com.gwt.hris.client.service.performance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ViewKpiAssignBeanModel;

@RemoteServiceRelativePath("kpiemployee")
public interface KPIEmployeeInterface extends RemoteService {
	PagingLoadResult<ViewKpiAssignBeanModel> getKpiAssignPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
