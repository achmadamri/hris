package com.gwt.hris.client.service.admin.skills;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLanguageBeanModel;

@RemoteServiceRelativePath("language")
public interface LanguageInterface extends RemoteService {
	ReturnBean submitLanguage(TbLanguageBeanModel TbLanguageBeanModel);

	TbLanguageBeanModel getLanguage(int tbesId);

	ReturnBean deleteLanguage(Integer tbesId);

	ReturnBean deleteBulkLanguage(Integer tbesIds[]);

	PagingLoadResult<TbLanguageBeanModel> getLanguagePaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
