package com.gwt.hris.client.service.leave;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbHolidayBeanModel;

@RemoteServiceRelativePath("holiday")
public interface HolidayInterface extends RemoteService {
	ReturnBean submitHoliday(TbHolidayBeanModel TbHolidayBeanModel);

	TbHolidayBeanModel getHoliday(int tbesId);

	ReturnBean deleteHoliday(Integer tbesId);

	ReturnBean deleteBulkHoliday(Integer tbesIds[]);

	PagingLoadResult<TbHolidayBeanModel> getHolidayPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbHolidayBeanModel getTbHolidayAll();
}
