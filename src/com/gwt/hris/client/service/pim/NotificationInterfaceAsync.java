package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewNotificationBeanModel;

public interface NotificationInterfaceAsync {
	void getNotificationPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewNotificationBeanModel>> callback);

	void submitNotificationRead(ViewNotificationBeanModel beanModel, AsyncCallback<ReturnBean> callback);
}
