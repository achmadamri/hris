package com.gwt.hris.client.service.admin.nationalityandraces;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbNationalityBeanModel;

@RemoteServiceRelativePath("nationality")
public interface NationalityInterface extends RemoteService {
	ReturnBean submitNationality(TbNationalityBeanModel TbNationalityBeanModel);

	TbNationalityBeanModel getNationality(int tbesId);

	ReturnBean deleteNationality(Integer tbesId);

	ReturnBean deleteBulkNationality(Integer tbesIds[]);

	PagingLoadResult<TbNationalityBeanModel> getNationalityPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbNationalityBeanModel getTbNationalityAll();
}
