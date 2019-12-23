package com.gwt.hris.client.service.performance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbKpiGroupBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;

@RemoteServiceRelativePath("kpigroup")
public interface KPIGroupInterface extends RemoteService {
	ReturnBean submitKpiGroup(TbKpiGroupBeanModel TbKpiGroupBeanModel);

	TbKpiGroupBeanModel getKpiGroup(int tbesId);

	ReturnBean deleteKpiGroup(Integer tbesId);

	ReturnBean deleteBulkKpiGroup(Integer tbesIds[]);

	PagingLoadResult<TbKpiGroupBeanModel> getKpiGroupPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbKpiGroupBeanModel getTbKpiGroupAll();
	
	TbProjectBeanModel getTbProjectAll();
}
