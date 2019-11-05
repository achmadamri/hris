package com.gwt.hris.client.service.admin.nationalityandraces;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEthnicRacesBeanModel;

@RemoteServiceRelativePath("ethnic")
public interface EthnicInterface extends RemoteService {
	ReturnBean submitEthnic(TbEthnicRacesBeanModel TbEthnicRacesBeanModel);

	TbEthnicRacesBeanModel getEthnic(int tbesId);

	ReturnBean deleteEthnic(Integer tbesId);

	ReturnBean deleteBulkEthnic(Integer tbesIds[]);

	PagingLoadResult<TbEthnicRacesBeanModel> getEthnicPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbEthnicRacesBeanModel getTbEthnicRacesAll();
}
