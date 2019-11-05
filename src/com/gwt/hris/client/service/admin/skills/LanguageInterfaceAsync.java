package com.gwt.hris.client.service.admin.skills;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLanguageBeanModel;

public interface LanguageInterfaceAsync {
	void submitLanguage(TbLanguageBeanModel TbLanguageBeanModel, AsyncCallback<ReturnBean> callback);

	void getLanguage(int tbesId, AsyncCallback<TbLanguageBeanModel> callback);

	void deleteLanguage(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkLanguage(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getLanguagePaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbLanguageBeanModel>> callback);
}
