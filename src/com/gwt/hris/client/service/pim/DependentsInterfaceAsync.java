package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbDependentsBeanModel;

public interface DependentsInterfaceAsync {
	void submitDependents(TbDependentsBeanModel TbDependentsBeanModel, AsyncCallback<ReturnBean> callback);

	void getDependentsPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbDependentsBeanModel>> callback);

	void deleteDependents(Integer id, AsyncCallback<ReturnBean> callback);

	void getDependents(int id, AsyncCallback<TbDependentsBeanModel> callback);
}
