package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLanguagesBeanModel;
import com.gwt.hris.client.service.bean.TbLanguageBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLanguagesBeanModel;

@RemoteServiceRelativePath("assignedlanguages")
public interface AssignedLanguagesInterface extends RemoteService {
	ReturnBean submitAssignedLanguages(TbAssignedLanguagesBeanModel TbAssignedLanguagesBeanModel);

	PagingLoadResult<ViewEmployeeLanguagesBeanModel> getAssignedLanguagesPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteAssignedLanguages(Integer id);

	TbAssignedLanguagesBeanModel getAssignedLanguages(int id);

	TbLanguageBeanModel getTbLanguageAll();
}
