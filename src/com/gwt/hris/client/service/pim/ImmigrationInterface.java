package com.gwt.hris.client.service.pim;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbImmigrationBeanModel;

@RemoteServiceRelativePath("immigration")
public interface ImmigrationInterface extends RemoteService {
	ReturnBean submitImmigration(TbImmigrationBeanModel TbImmigrationBeanModel);

	TbImmigrationBeanModel getImmigration(int id);
}
