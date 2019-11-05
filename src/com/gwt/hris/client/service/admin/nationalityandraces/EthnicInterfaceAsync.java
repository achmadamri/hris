package com.gwt.hris.client.service.admin.nationalityandraces;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEthnicRacesBeanModel;

public interface EthnicInterfaceAsync {
	void submitEthnic(TbEthnicRacesBeanModel TbEthnicRacesBeanModel, AsyncCallback<ReturnBean> callback);

	void getEthnic(int tbesId, AsyncCallback<TbEthnicRacesBeanModel> callback);

	void deleteEthnic(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkEthnic(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getEthnicPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbEthnicRacesBeanModel>> callback);

	void getTbEthnicRacesAll(AsyncCallback<TbEthnicRacesBeanModel> callback);
}
