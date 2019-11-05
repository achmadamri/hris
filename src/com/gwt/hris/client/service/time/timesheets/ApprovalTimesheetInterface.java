package com.gwt.hris.client.service.time.timesheets;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;
import com.gwt.hris.client.service.bean.TbTimesheetBeanModel;
import com.gwt.hris.client.service.bean.ViewTimesheetBeanModel;

@RemoteServiceRelativePath("approvaltimesheet")
public interface ApprovalTimesheetInterface extends RemoteService {
	ReturnBean deleteTimesheet(Integer tbesId);

	ReturnBean deleteBulkTimesheet(Integer tbesIds[]);

	PagingLoadResult<ViewTimesheetBeanModel> getTimesheetPaging(final PagingLoadConfig config, String startOfWeek, String searchBy, String searchValue);

	ReturnBean getStartOfWeek();

	ReturnBean approval(Integer id, Integer status);

	TbProjectActivitiesBeanModel getTbProjectActivitiesByGroup(int tbpagId);

	TbProjectBeanModel getTbProjectByCustomer(int tbcId);

	TbCustomerBeanModel getTbCustomerAll();

	TbProjectActivitiesGroupBeanModel getTbProjectActivitiesGroupAll();

	TbTimesheetBeanModel getTimesheet(int id);
}
