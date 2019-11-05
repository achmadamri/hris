package com.gwt.hris.client.service.admin.memberships;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMembershipBeanModel;
import com.gwt.hris.client.service.bean.TbMembershipTypesBeanModel;
import com.gwt.hris.client.service.bean.ViewMembershipTypesBeanModel;

@RemoteServiceRelativePath("membership")
public interface MembershipInterface extends RemoteService {
	ReturnBean submitMembership(TbMembershipBeanModel TbMembershipBeanModel);

	TbMembershipBeanModel getMembership(int tbesId);

	ReturnBean deleteMembership(Integer tbesId);

	ReturnBean deleteBulkMembership(Integer tbesIds[]);

	PagingLoadResult<ViewMembershipTypesBeanModel> getMembershipPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbMembershipTypesBeanModel getTbMembershipTypesAll();
}
