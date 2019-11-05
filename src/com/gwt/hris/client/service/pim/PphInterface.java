package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.TbPphBeanModel;

@RemoteServiceRelativePath("pph")
public interface PphInterface extends RemoteService {
	PagingLoadResult<TbPphBeanModel> getPphPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
