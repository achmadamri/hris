package com.gwt.hris.client.service.admin.nationalityandraces;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbNationalityBeanModel;

public interface NationalityInterfaceAsync {
	void submitNationality(TbNationalityBeanModel TbNationalityBeanModel, AsyncCallback<ReturnBean> callback);

	void getNationality(int tbesId, AsyncCallback<TbNationalityBeanModel> callback);

	void deleteNationality(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkNationality(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getNationalityPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbNationalityBeanModel>> callback);

	void getTbNationalityAll(AsyncCallback<TbNationalityBeanModel> callback);
}
