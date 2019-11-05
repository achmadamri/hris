package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLanguagesBeanModel;
import com.gwt.hris.client.service.bean.TbLanguageBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLanguagesBeanModel;

public interface AssignedLanguagesInterfaceAsync {
	void submitAssignedLanguages(TbAssignedLanguagesBeanModel TbAssignedLanguagesBeanModel, AsyncCallback<ReturnBean> callback);

	void getAssignedLanguagesPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeLanguagesBeanModel>> callback);

	void deleteAssignedLanguages(Integer id, AsyncCallback<ReturnBean> callback);

	void getAssignedLanguages(int id, AsyncCallback<TbAssignedLanguagesBeanModel> callback);

	void getTbLanguageAll(AsyncCallback<TbLanguageBeanModel> callback);
}
