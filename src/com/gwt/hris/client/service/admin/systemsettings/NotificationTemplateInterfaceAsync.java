package com.gwt.hris.client.service.admin.systemsettings;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbNotificationTemplateBeanModel;

public interface NotificationTemplateInterfaceAsync {
	void submitNotificationTemplate(TbNotificationTemplateBeanModel TbNotificationTemplateBeanModel, AsyncCallback<ReturnBean> callback);

	void getNotificationTemplate(int tbesId, AsyncCallback<TbNotificationTemplateBeanModel> callback);

	void deleteNotificationTemplate(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkNotificationTemplate(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getNotificationTemplatePaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbNotificationTemplateBeanModel>> callback);

	void getTbNotificationTemplateAll(AsyncCallback<TbNotificationTemplateBeanModel> callback);
}
