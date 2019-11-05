package com.gwt.hris.server.service.admin.users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.users.AdminUserGroupsInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAdminUserGroupsBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessAdminBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAdminUserGroupsBean;
import com.gwt.hris.db.TbAdminUserGroupsManager;
import com.gwt.hris.db.TbMenuAccessAdminBean;
import com.gwt.hris.db.TbMenuAccessAdminManager;
import com.gwt.hris.db.TbMenuBean;
import com.gwt.hris.db.TbMenuManager;
import com.gwt.hris.db.ViewMenuAccessAdminBean;
import com.gwt.hris.db.ViewMenuAccessAdminManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class AdminUserGroupsImpl extends MainRemoteServiceServlet implements AdminUserGroupsInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitAdminUserGroups(TbAdminUserGroupsBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbAdminUserGroupsBean bean = null;

			if (beanModel.getTbaugId() != null) {
				bean = TbAdminUserGroupsManager.getInstance().loadByPrimaryKey(beanModel.getTbaugId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 28, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbAdminUserGroupsManager.getInstance().createTbAdminUserGroupsBean();

				bean = TbAdminUserGroupsManager.getInstance().toBean(beanModel, bean);

				TbAdminUserGroupsManager.getInstance().save(bean);

				bean.setTbaugAdminUserGroupsId("USG" + bean.getTbaugId());

				TbAdminUserGroupsManager.getInstance().update(bean);

				TbMenuBean tbMenuBeans[] = TbMenuManager.getInstance().loadAll();
				TbMenuAccessAdminBean tbMenuAccessAdminBeans[] = new TbMenuAccessAdminBean[tbMenuBeans.length];
				for (int i = 0; i < tbMenuAccessAdminBeans.length; i++) {
					tbMenuAccessAdminBeans[i] = TbMenuAccessAdminManager.getInstance().createTbMenuAccessAdminBean();
					tbMenuAccessAdminBeans[i].setTbmId(tbMenuBeans[i].getTbmId());
					tbMenuAccessAdminBeans[i].setTbaugId(bean.getTbaugId());
				}
				TbMenuAccessAdminManager.getInstance().save(tbMenuAccessAdminBeans);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 28, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbAdminUserGroupsManager.getInstance().toBean(beanModel, bean);

				TbAdminUserGroupsManager.getInstance().save(bean);

				TbMenuAccessAdminManager.getInstance().deleteBytbmaa_fk_2(bean.getTbaugId());

				List<ViewMenuAccessAdminBeanModel> lstViewMenuAccessAdminBeanModel = beanModel.get("lstViewMenuAccessAdminBeanModel");
				Iterator<ViewMenuAccessAdminBeanModel> itrViewMenuAccessAdminBeanModel = lstViewMenuAccessAdminBeanModel.iterator();
				TbMenuAccessAdminBean tbMenuAccessAdminBeans[] = new TbMenuAccessAdminBean[lstViewMenuAccessAdminBeanModel.size()];
				int i = 0;
				while (itrViewMenuAccessAdminBeanModel.hasNext()) {
					ViewMenuAccessAdminBeanModel viewMenuAccessAdminBeanModel = itrViewMenuAccessAdminBeanModel.next();
					tbMenuAccessAdminBeans[i] = TbMenuAccessAdminManager.getInstance().createTbMenuAccessAdminBean();
					tbMenuAccessAdminBeans[i].setTbmId(viewMenuAccessAdminBeanModel.getTbmId());
					tbMenuAccessAdminBeans[i].setTbaugId(bean.getTbaugId());

					if (viewMenuAccessAdminBeanModel.get("tbmaaEnabledBoolean")) {
						tbMenuAccessAdminBeans[i].setTbmaaEnabled(1);
					} else {
						tbMenuAccessAdminBeans[i].setTbmaaEnabled(0);
					}

					if (viewMenuAccessAdminBeanModel.get("tbmaaInsertBoolean")) {
						tbMenuAccessAdminBeans[i].setTbmaaInsert(1);
					} else {
						tbMenuAccessAdminBeans[i].setTbmaaInsert(0);
					}

					if (viewMenuAccessAdminBeanModel.get("tbmaaUpdateBoolean")) {
						tbMenuAccessAdminBeans[i].setTbmaaUpdate(1);
					} else {
						tbMenuAccessAdminBeans[i].setTbmaaUpdate(0);
					}

					if (viewMenuAccessAdminBeanModel.get("tbmaaDeleteBoolean")) {
						tbMenuAccessAdminBeans[i].setTbmaaDelete(1);
					} else {
						tbMenuAccessAdminBeans[i].setTbmaaDelete(0);
					}

					if (viewMenuAccessAdminBeanModel.get("tbmaaViewBoolean")) {
						tbMenuAccessAdminBeans[i].setTbmaaView(1);
					} else {
						tbMenuAccessAdminBeans[i].setTbmaaView(0);
					}

					if (viewMenuAccessAdminBeanModel.get("tbmaaApproveBoolean")) {
						tbMenuAccessAdminBeans[i].setTbmaaApprove(1);
					} else {
						tbMenuAccessAdminBeans[i].setTbmaaApprove(0);
					}
					i++;
				}
				TbMenuAccessAdminManager.getInstance().save(tbMenuAccessAdminBeans);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbAdminUserGroupsManager.getInstance().toBeanModel(bean);
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

	public TbAdminUserGroupsBeanModel getAdminUserGroups(int id) {
		TbAdminUserGroupsBeanModel returnValue = new TbAdminUserGroupsBeanModel();

		try {
			TbAdminUserGroupsBean TbAdminUserGroupsBean = TbAdminUserGroupsManager.getInstance().loadByPrimaryKey(id);

			if (TbAdminUserGroupsBean != null) {
				returnValue = TbAdminUserGroupsManager.getInstance().toBeanModel(TbAdminUserGroupsBean);

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

	public ReturnBean deleteAdminUserGroups(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 28, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbMenuAccessAdminManager.getInstance().deleteBytbmaa_fk_2(id);
			TbAdminUserGroupsManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkAdminUserGroups(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 28, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbMenuAccessAdminManager.getInstance().deleteByWhere("where tbaug_id in (" + strId + ")");
			TbAdminUserGroupsManager.getInstance().deleteByWhere("where tbaug_id in (" + strId + ")");

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

	public PagingLoadResult<TbAdminUserGroupsBeanModel> getAdminUserGroupsPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbAdminUserGroupsBeanModel> list = new ArrayList<TbAdminUserGroupsBeanModel>();

		TbAdminUserGroupsBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 28, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("User Group ID".equals(searchBy)) {
				strWhere = "where tbaug_admin_user_groups_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("User Group Name".equals(searchBy)) {
				strWhere = "where tbaug_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbAdminUserGroupsManager.getInstance().countAll();
			} else {
				size = TbAdminUserGroupsManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbAdminUserGroupsManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbAdminUserGroupsBeanModel data = TbAdminUserGroupsManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbAdminUserGroupsBeanModel data = new TbAdminUserGroupsBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbAdminUserGroupsBeanModel>(list, config.getOffset(), size);
	}

	public PagingLoadResult<ViewMenuAccessAdminBeanModel> getMenuAccessPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewMenuAccessAdminBeanModel> list = new ArrayList<ViewMenuAccessAdminBeanModel>();

		ViewMenuAccessAdminBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 28, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbm_disabled = 0 ";
			if ("tbaugId".equals(searchBy)) {
				strWhere += "and tbaug_id = " + searchValue.replaceAll("'", "") + " ";
			}

			if ("".equals(strWhere)) {
				size = ViewMenuAccessAdminManager.getInstance().countAll();
			} else {
				size = ViewMenuAccessAdminManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewMenuAccessAdminManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewMenuAccessAdminBeanModel data = ViewMenuAccessAdminManager.getInstance().toBeanModel(datas[i]);

				if (data.getTbmaaEnabled() == null) {
					data.set("tbmaaEnabledBoolean", false);
				} else {
					if (data.getTbmaaEnabled() == 1) {
						data.set("tbmaaEnabledBoolean", true);
					} else {
						data.set("tbmaaEnabledBoolean", false);
					}
				}

				if (data.getTbmaaInsert() == null) {
					data.set("tbmaaInsertBoolean", false);
				} else {
					if (data.getTbmaaInsert() == 1) {
						data.set("tbmaaInsertBoolean", true);
					} else {
						data.set("tbmaaInsertBoolean", false);
					}
				}

				if (data.getTbmaaUpdate() == null) {
					data.set("tbmaaUpdateBoolean", false);
				} else {
					if (data.getTbmaaUpdate() == 1) {
						data.set("tbmaaUpdateBoolean", true);
					} else {
						data.set("tbmaaUpdateBoolean", false);
					}
				}

				if (data.getTbmaaDelete() == null) {
					data.set("tbmaaDeleteBoolean", false);
				} else {
					if (data.getTbmaaDelete() == 1) {
						data.set("tbmaaDeleteBoolean", true);
					} else {
						data.set("tbmaaDeleteBoolean", false);
					}
				}

				if (data.getTbmaaView() == null) {
					data.set("tbmaaViewBoolean", false);
				} else {
					if (data.getTbmaaView() == 1) {
						data.set("tbmaaViewBoolean", true);
					} else {
						data.set("tbmaaViewBoolean", false);
					}
				}

				if (data.getTbmaaApprove() == null) {
					data.set("tbmaaApproveBoolean", false);
				} else {
					if (data.getTbmaaApprove() == 1) {
						data.set("tbmaaApproveBoolean", true);
					} else {
						data.set("tbmaaApproveBoolean", false);
					}
				}
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewMenuAccessAdminBeanModel data = new ViewMenuAccessAdminBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewMenuAccessAdminBeanModel>(list, config.getOffset(), size);
	}
}
