package com.gwt.hris.server.service.performance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbKpiGroupBeanModel;
import com.gwt.hris.client.service.performance.KPIGroupInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbKpiGroupBean;
import com.gwt.hris.db.TbKpiGroupManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class KPIGroupImpl extends MainRemoteServiceServlet implements KPIGroupInterface {
	
	private static final long serialVersionUID = -1334064523467300937L;

	public ReturnBean submitKpiGroup(TbKpiGroupBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbKpiGroupBean bean = null;

			if (beanModel.getTbkgId() != null) {
				bean = TbKpiGroupManager.getInstance().loadByPrimaryKey(beanModel.getTbkgId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 111, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbKpiGroupManager.getInstance().createTbKpiGroupBean();

				bean = TbKpiGroupManager.getInstance().toBean(beanModel, bean);

				TbKpiGroupManager.getInstance().save(bean);

				bean.setTbkgKpiGroupId("KPG" + bean.getTbkgId());

				TbKpiGroupManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 111, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbKpiGroupManager.getInstance().toBean(beanModel, bean);

				TbKpiGroupManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbKpiGroupManager.getInstance().toBeanModel(bean);
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

	public TbKpiGroupBeanModel getKpiGroup(int id) {
		TbKpiGroupBeanModel returnValue = new TbKpiGroupBeanModel();

		try {
			TbKpiGroupBean TbKpiGroupBean = TbKpiGroupManager.getInstance().loadByPrimaryKey(id);

			if (TbKpiGroupBean != null) {
				returnValue = TbKpiGroupManager.getInstance().toBeanModel(TbKpiGroupBean);

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

	public ReturnBean deleteKpiGroup(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 111, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbKpiGroupManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkKpiGroup(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 111, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbKpiGroupManager.getInstance().deleteByWhere("where tbkg_id in (" + strId + ")");

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

	public PagingLoadResult<TbKpiGroupBeanModel> getKpiGroupPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbKpiGroupBeanModel> list = new ArrayList<TbKpiGroupBeanModel>();

		TbKpiGroupBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 111, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("KPI Group ID".equals(searchBy)) {
				strWhere += "where tbkg_kpi_group_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("KPI Group".equals(searchBy)) {
				strWhere += "where tbkg_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbKpiGroupManager.getInstance().countAll();
			} else {
				size = TbKpiGroupManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbKpiGroupManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbKpiGroupBeanModel data = TbKpiGroupManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbKpiGroupBeanModel data = new TbKpiGroupBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbKpiGroupBeanModel>(list, config.getOffset(), size);
	}

	public TbKpiGroupBeanModel getTbKpiGroupAll() {
		TbKpiGroupBeanModel returnValue = new TbKpiGroupBeanModel();

		try {
			TbKpiGroupBean tbKpiGroupBeans[] = TbKpiGroupManager.getInstance().loadAll();
			TbKpiGroupBeanModel tbKpiGroupBeanModels[] = TbKpiGroupManager.getInstance().toBeanModels(tbKpiGroupBeans);

			returnValue.setModels(tbKpiGroupBeanModels);
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
}
