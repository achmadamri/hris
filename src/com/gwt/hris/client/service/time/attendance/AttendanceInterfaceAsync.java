package com.gwt.hris.client.service.time.attendance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAttendanceBeanModel;
import com.gwt.hris.client.service.bean.ViewAttendanceBeanModel;

public interface AttendanceInterfaceAsync {
	void getTimesheetPaging(PagingLoadConfig config, String startOfWeek, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewAttendanceBeanModel>> callback);

	void getMonth(AsyncCallback<ReturnBean> callback);

	void getPunchStatus(String strDate, AsyncCallback<ReturnBean> callback);

	void submitAttendance(TbAttendanceBeanModel beanModel, AsyncCallback<ReturnBean> callback);
}
