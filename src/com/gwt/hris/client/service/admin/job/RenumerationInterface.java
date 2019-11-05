package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewRenumerationBeanModel;

@RemoteServiceRelativePath("renumeration")
public interface RenumerationInterface extends RemoteService {
	ReturnBean submitRenumeration(TbRenumerationBeanModel TbRenumerationBeanModel);

	TbRenumerationBeanModel getRenumeration(int tbesId);

	ReturnBean deleteRenumeration(Integer tbesId);

	ReturnBean deleteBulkRenumeration(Integer tbesIds[]);

	PagingLoadResult<ViewRenumerationBeanModel> getRenumerationPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	PagingLoadResult<ViewEmployeeRenumerationBeanModel> getRenumerationPagingEss(final PagingLoadConfig config, String searchBy, String searchValue);
}
