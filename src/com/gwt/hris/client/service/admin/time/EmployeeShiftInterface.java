package com.gwt.hris.client.service.admin.time;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbShiftBeanModel;
import com.gwt.hris.client.service.bean.ViewAttendanceBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;

@RemoteServiceRelativePath("employeeshifts")
public interface EmployeeShiftInterface extends RemoteService {
	PagingLoadResult<ViewEmployeeInformationBeanModel> getEmployeePaging(final PagingLoadConfig config, String searchBy, String searchValue);

	PagingLoadResult<ViewAttendanceBeanModel> getTimesheetPaging(final PagingLoadConfig config, Integer intTbeId, String month, String searchBy, String searchValue);

	TbShiftBeanModel getTbShiftAll();

	ReturnBean submitEmployeeShifts(Integer tbeId, Integer tbsId, String strDates[]);
}
