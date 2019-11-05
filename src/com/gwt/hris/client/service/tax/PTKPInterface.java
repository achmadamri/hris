package com.gwt.hris.client.service.tax;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbPtkpBeanModel;

@RemoteServiceRelativePath("ptkp")
public interface PTKPInterface extends RemoteService {
	ReturnBean submitPtkp(TbPtkpBeanModel TbPtkpBeanModel);

	TbPtkpBeanModel getPtkp(int tbesId);

	ReturnBean deletePtkp(Integer tbesId);

	ReturnBean deleteBulkPtkp(Integer tbesIds[]);

	PagingLoadResult<TbPtkpBeanModel> getPtkpPaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	TbPtkpBeanModel getTbPtkpAll();
}
