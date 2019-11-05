package com.gwt.hris.client.service.admin.systemsettings;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCronBeanModel;

@RemoteServiceRelativePath("cron")
public interface CronInterface extends RemoteService {
	ReturnBean submitCron(TbCronBeanModel TbCronBeanModel);

	TbCronBeanModel getCron(int tbesId);
	
	ReturnBean runManualCron(Integer tbesId);

	ReturnBean deleteCron(Integer tbesId);

	ReturnBean deleteBulkCron(Integer tbesIds[]);

	PagingLoadResult<TbCronBeanModel> getCronPaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	TbCronBeanModel getTbCronAll();
}
