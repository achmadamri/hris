package com.gwt.hris.client.service.admin.systemsettings;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbNotificationTemplateBeanModel;

@RemoteServiceRelativePath("notificationtemplate")
public interface NotificationTemplateInterface extends RemoteService {
	ReturnBean submitNotificationTemplate(TbNotificationTemplateBeanModel TbNotificationTemplateBeanModel);

	TbNotificationTemplateBeanModel getNotificationTemplate(int tbesId);

	ReturnBean deleteNotificationTemplate(Integer tbesId);

	ReturnBean deleteBulkNotificationTemplate(Integer tbesIds[]);

	PagingLoadResult<TbNotificationTemplateBeanModel> getNotificationTemplatePaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbNotificationTemplateBeanModel getTbNotificationTemplateAll();
}
