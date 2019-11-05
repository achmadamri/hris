package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewNotificationBeanModel;

@RemoteServiceRelativePath("notification")
public interface NotificationInterface extends RemoteService {
	PagingLoadResult<ViewNotificationBeanModel> getNotificationPaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	ReturnBean submitNotificationRead(ViewNotificationBeanModel beanModel);
}
