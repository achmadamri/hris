package com.gwt.hris.client.service.admin.companyinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLocationBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;
import com.gwt.hris.client.service.bean.ViewLocationNegaraBeanModel;

@RemoteServiceRelativePath("locations")
public interface LocationsInterface extends RemoteService {
	ReturnBean submitLocations(TbLocationBeanModel tbLocationBeanModel);

	TbLocationBeanModel getLocation(int tblId);

	ReturnBean deleteLocations(Integer tblId);

	ReturnBean deleteBulkLocations(Integer tblIds[]);

	PagingLoadResult<ViewLocationNegaraBeanModel> getTbLocationPaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	TbPerusahaanBeanModel getTbPerusahaanAll();
}
