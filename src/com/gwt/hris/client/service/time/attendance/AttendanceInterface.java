package com.gwt.hris.client.service.time.attendance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAttendanceBeanModel;
import com.gwt.hris.client.service.bean.ViewAttendanceBeanModel;

@RemoteServiceRelativePath("attendance")
public interface AttendanceInterface extends RemoteService {
	PagingLoadResult<ViewAttendanceBeanModel> getTimesheetPaging(final PagingLoadConfig config, String startOfWeek, String searchBy, String searchValue);

	public ReturnBean getMonth();

	public ReturnBean getPunchStatus(String strDate);

	public ReturnBean submitAttendance(TbAttendanceBeanModel beanModel);
}
