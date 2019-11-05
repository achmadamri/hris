package com.gwt.hris.client.service.pim;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbImmigrationBeanModel;

public interface ImmigrationInterfaceAsync {
	void submitImmigration(TbImmigrationBeanModel TbImmigrationBeanModel, AsyncCallback<ReturnBean> callback);

	void getImmigration(int id, AsyncCallback<TbImmigrationBeanModel> callback);
}
