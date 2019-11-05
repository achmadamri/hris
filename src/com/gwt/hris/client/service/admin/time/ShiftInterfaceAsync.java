package com.gwt.hris.client.service.admin.time;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbShiftBeanModel;

public interface ShiftInterfaceAsync {
	void submitShift(TbShiftBeanModel TbShiftBeanModel, AsyncCallback<ReturnBean> callback);

	void getShift(int tbesId, AsyncCallback<TbShiftBeanModel> callback);

	void deleteShift(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkShift(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getShiftPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbShiftBeanModel>> callback);

	void getTbShiftAll(AsyncCallback<TbShiftBeanModel> callback);
}
