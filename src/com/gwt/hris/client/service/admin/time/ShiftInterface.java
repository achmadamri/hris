package com.gwt.hris.client.service.admin.time;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbShiftBeanModel;

@RemoteServiceRelativePath("shift")
public interface ShiftInterface extends RemoteService {
	ReturnBean submitShift(TbShiftBeanModel TbShiftBeanModel);

	TbShiftBeanModel getShift(int tbesId);

	ReturnBean deleteShift(Integer tbesId);

	ReturnBean deleteBulkShift(Integer tbesIds[]);

	PagingLoadResult<TbShiftBeanModel> getShiftPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbShiftBeanModel getTbShiftAll();
}
