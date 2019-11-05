package com.gwt.hris.client.service.performance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbKpiBeanModel;
import com.gwt.hris.client.service.bean.TbKpiGroupBeanModel;
import com.gwt.hris.client.service.bean.ViewKpiBeanModel;

@RemoteServiceRelativePath("kpi")
public interface KPIInterface extends RemoteService {
	ReturnBean submitKpi(TbKpiBeanModel TbKpiBeanModel);

	TbKpiBeanModel getKpi(int tbesId);

	ReturnBean deleteKpi(Integer tbesId);

	ReturnBean deleteBulkKpi(Integer tbesIds[]);

	PagingLoadResult<ViewKpiBeanModel> getKpiPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbKpiGroupBeanModel getTbKpiGroupAll();
}
