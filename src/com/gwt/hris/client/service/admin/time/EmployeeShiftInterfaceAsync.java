package com.gwt.hris.client.service.admin.time;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbShiftBeanModel;
import com.gwt.hris.client.service.bean.ViewAttendanceBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;

public interface EmployeeShiftInterfaceAsync {
	void getEmployeePaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeInformationBeanModel>> callback);

	void getTimesheetPaging(PagingLoadConfig config, Integer intTbeId, String month, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewAttendanceBeanModel>> callback);

	void getTbShiftAll(AsyncCallback<TbShiftBeanModel> callback);

	void submitEmployeeShifts(Integer tbeId, Integer tbsId, String strDates[], AsyncCallback<ReturnBean> callback);
}
