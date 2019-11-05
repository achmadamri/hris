package com.gwt.hris.client.service.admin.companyinfo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbNegaraBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;

@RemoteServiceRelativePath("general")
public interface GeneralInterface extends RemoteService {
	TbNegaraBeanModel getTbNegaraAll();

	ReturnBean submitPerusahaan(TbPerusahaanBeanModel tbPerusahaanBeanModel);

	TbPerusahaanBeanModel getPerusahaan();
}
