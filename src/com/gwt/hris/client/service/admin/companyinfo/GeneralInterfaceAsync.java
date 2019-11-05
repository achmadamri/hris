package com.gwt.hris.client.service.admin.companyinfo;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbNegaraBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;

public interface GeneralInterfaceAsync {
	void getTbNegaraAll(AsyncCallback<TbNegaraBeanModel> callback);

	void submitPerusahaan(TbPerusahaanBeanModel tbPerusahaanBeanModel, AsyncCallback<ReturnBean> callback);

	void getPerusahaan(AsyncCallback<TbPerusahaanBeanModel> callback);

}
