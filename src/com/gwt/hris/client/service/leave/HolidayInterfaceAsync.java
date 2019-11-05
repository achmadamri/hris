package com.gwt.hris.client.service.leave;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbHolidayBeanModel;

public interface HolidayInterfaceAsync {
	void submitHoliday(TbHolidayBeanModel TbHolidayBeanModel, AsyncCallback<ReturnBean> callback);

	void getHoliday(int tbesId, AsyncCallback<TbHolidayBeanModel> callback);

	void deleteHoliday(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkHoliday(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getHolidayPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbHolidayBeanModel>> callback);

	void getTbHolidayAll(AsyncCallback<TbHolidayBeanModel> callback);
}
