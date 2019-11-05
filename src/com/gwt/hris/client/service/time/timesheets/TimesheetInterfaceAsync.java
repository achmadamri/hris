package com.gwt.hris.client.service.time.timesheets;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;
import com.gwt.hris.client.service.bean.TbTimesheetBeanModel;
import com.gwt.hris.client.service.bean.ViewTimesheetBeanModel;

public interface TimesheetInterfaceAsync {
	void submitTimesheet(TbTimesheetBeanModel TbTimesheetBeanModel, AsyncCallback<ReturnBean> callback);

	void getTimesheet(int tbesId, AsyncCallback<TbTimesheetBeanModel> callback);

	void deleteTimesheet(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkTimesheet(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getTimesheetPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewTimesheetBeanModel>> callback);

	void getTbCustomerAll(AsyncCallback<TbCustomerBeanModel> callback);

	void getTbProjectByCustomer(int tbcId, AsyncCallback<TbProjectBeanModel> callback);

	void getTbProjectActivitiesGroupAll(AsyncCallback<TbProjectActivitiesGroupBeanModel> callback);

	void getTbProjectActivitiesByGroup(int tbpagId, AsyncCallback<TbProjectActivitiesBeanModel> callback);

	void getTimesheetDefault(AsyncCallback<TbTimesheetBeanModel> callback);

	void getTbProjectActivities(int id, AsyncCallback<TbProjectActivitiesBeanModel> callback);
}
