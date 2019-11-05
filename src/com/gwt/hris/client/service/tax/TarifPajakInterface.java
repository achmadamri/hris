package com.gwt.hris.client.service.tax;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbTarifPajakBeanModel;

@RemoteServiceRelativePath("tarifpajak")
public interface TarifPajakInterface extends RemoteService {
	ReturnBean submitTarifPajak(TbTarifPajakBeanModel TbTarifPajakBeanModel);

	TbTarifPajakBeanModel getTarifPajak(int tbesId);

	ReturnBean deleteTarifPajak(Integer tbesId);

	ReturnBean deleteBulkTarifPajak(Integer tbesIds[]);

	PagingLoadResult<TbTarifPajakBeanModel> getTarifPajakPaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	TbTarifPajakBeanModel getTbTarifPajakAll();
}
