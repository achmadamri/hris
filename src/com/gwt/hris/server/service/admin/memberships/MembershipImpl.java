package com.gwt.hris.server.service.admin.memberships;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.memberships.MembershipInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMembershipBeanModel;
import com.gwt.hris.client.service.bean.TbMembershipTypesBeanModel;
import com.gwt.hris.client.service.bean.ViewMembershipTypesBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbMembershipBean;
import com.gwt.hris.db.TbMembershipManager;
import com.gwt.hris.db.TbMembershipTypesBean;
import com.gwt.hris.db.TbMembershipTypesManager;
import com.gwt.hris.db.ViewMembershipTypesBean;
import com.gwt.hris.db.ViewMembershipTypesManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class MembershipImpl extends MainRemoteServiceServlet implements MembershipInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public TbMembershipTypesBeanModel getTbMembershipTypesAll() {
		TbMembershipTypesBeanModel returnValue = new TbMembershipTypesBeanModel();

		try {
			TbMembershipTypesBean beans[] = TbMembershipTypesManager.getInstance().loadAll();
			TbMembershipTypesBeanModel beanModels[] = TbMembershipTypesManager.getInstance().toBeanModels(beans);

			returnValue.setModels(beanModels);
			returnValue.setOperationStatus(true);
			returnValue.setMessage("");
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}

	public ReturnBean submitMembership(TbMembershipBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbMembershipBean bean = null;

			if (beanModel.getTbmId() != null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 21, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbMembershipManager.getInstance().loadByPrimaryKey(beanModel.getTbmId());
			}

			if (bean == null) {
				bean = TbMembershipManager.getInstance().createTbMembershipBean();

				bean = TbMembershipManager.getInstance().toBean(beanModel, bean);

				TbMembershipManager.getInstance().save(bean);

				bean.setTbmMembershipId("MEM" + bean.getTbmId());

				TbMembershipManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				bean = TbMembershipManager.getInstance().toBean(beanModel, bean);

				TbMembershipManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbMembershipManager.getInstance().toBeanModel(bean);
			returnValue.set("model", beanModel);

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public TbMembershipBeanModel getMembership(int id) {
		TbMembershipBeanModel returnValue = new TbMembershipBeanModel();

		try {
			TbMembershipBean TbMembershipBean = TbMembershipManager.getInstance().loadByPrimaryKey(id);

			if (TbMembershipBean != null) {
				returnValue = TbMembershipManager.getInstance().toBeanModel(TbMembershipBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("");
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}

	public ReturnBean deleteMembership(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 21, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbMembershipManager.getInstance().deleteByPrimaryKey(id);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Deleted");

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public ReturnBean deleteBulkMembership(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 21, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbMembershipManager.getInstance().deleteByWhere("where tbm_id in (" + strId + ")");

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Deleted");

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public PagingLoadResult<ViewMembershipTypesBeanModel> getMembershipPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewMembershipTypesBeanModel> list = new ArrayList<ViewMembershipTypesBeanModel>();

		ViewMembershipTypesBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 21, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Membership Type ID".equals(searchBy)) {
				strWhere = "where tbm_membership_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Membership Name".equals(searchBy)) {
				strWhere = "where tbm_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Membership Type".equals(searchBy)) {
				strWhere = "where tbmt_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewMembershipTypesManager.getInstance().countAll();
			} else {
				size = ViewMembershipTypesManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewMembershipTypesManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewMembershipTypesBeanModel data = ViewMembershipTypesManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewMembershipTypesBeanModel data = new ViewMembershipTypesBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewMembershipTypesBeanModel>(list, config.getOffset(), size);
	}
}
