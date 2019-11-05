package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbReportToBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeReportToBeanModel;

public interface ReportToInterfaceAsync {
	void submitReportTo(TbReportToBeanModel TbReportToBeanModel, AsyncCallback<ReturnBean> callback);

	void getReportToPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeReportToBeanModel>> callback);

	void deleteReportTo(Integer id, AsyncCallback<ReturnBean> callback);

	void getReportTo(int id, AsyncCallback<ViewEmployeeReportToBeanModel> callback);
}
