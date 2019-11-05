package com.gwt.hris.client.service.tax;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbPttBeanModel;

@RemoteServiceRelativePath("ptt")
public interface PTTInterface extends RemoteService {
	ReturnBean submitPTT(TbPttBeanModel TbPttBeanModel);

	TbPttBeanModel getPTT(int tbesId);

	ReturnBean deletePTT(Integer tbesId);

	ReturnBean deleteBulkPTT(Integer tbesIds[]);

	PagingLoadResult<TbPttBeanModel> getPTTPaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	TbPttBeanModel getTbPttAll();
}
