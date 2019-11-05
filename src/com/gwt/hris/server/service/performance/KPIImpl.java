package com.gwt.hris.server.service.performance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbKpiBeanModel;
import com.gwt.hris.client.service.bean.TbKpiGroupBeanModel;
import com.gwt.hris.client.service.bean.ViewKpiBeanModel;
import com.gwt.hris.client.service.performance.KPIInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbKpiBean;
import com.gwt.hris.db.TbKpiGroupBean;
import com.gwt.hris.db.TbKpiGroupManager;
import com.gwt.hris.db.TbKpiManager;
import com.gwt.hris.db.ViewKpiBean;
import com.gwt.hris.db.ViewKpiManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class KPIImpl extends MainRemoteServiceServlet implements KPIInterface {
	
	private static final long serialVersionUID = 1161349876683522341L;

	public ReturnBean submitKpi(TbKpiBeanModel beanModel) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbKpiBean bean = null;

			if (beanModel.getTbkId() != null) {
				bean = TbKpiManager.getInstance().loadByPrimaryKey(beanModel.getTbkId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 112, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbKpiManager.getInstance().createTbKpiBean();

				bean = TbKpiManager.getInstance().toBean(beanModel, bean);

				bean.setTbeId(tbEmployeeBeanModel.getTbeId());

				TbKpiManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 112, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbKpiManager.getInstance().toBean(beanModel, bean);

				TbKpiManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbKpiManager.getInstance().toBeanModel(bean);
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

	public TbKpiBeanModel getKpi(int id) {
		TbKpiBeanModel returnValue = new TbKpiBeanModel();

		try {
			TbKpiBean TbKpiBean = TbKpiManager.getInstance().loadByPrimaryKey(id);

			if (TbKpiBean != null) {
				returnValue = TbKpiManager.getInstance().toBeanModel(TbKpiBean);

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

	public ReturnBean deleteKpi(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 112, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbKpiManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkKpi(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 112, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbKpiManager.getInstance().deleteByWhere("where tbk_id in (" + strId + ")");

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

	public PagingLoadResult<ViewKpiBeanModel> getKpiPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		List<ViewKpiBeanModel> list = new ArrayList<ViewKpiBeanModel>();

		ViewKpiBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 112, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_id = " + tbEmployeeBeanModel.getTbeId() + " ";
			if ("tbeId".equals(searchBy)) {
				strWhere += "and tbk_id not in (select tbk_id from tb_kpi_assign where tbe_id = '" + searchValue.replaceAll("'", "") + "' and tbka_status <> 2) ";
			} else if ("KPI Group".equals(searchBy)) {
				strWhere += "and tbkg_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Description".equals(searchBy)) {
				strWhere += "and tbk_description like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewKpiManager.getInstance().countAll();
			} else {
				size = ViewKpiManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewKpiManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewKpiBeanModel data = ViewKpiManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewKpiBeanModel data = new ViewKpiBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewKpiBeanModel>(list, config.getOffset(), size);
	}

	public TbKpiGroupBeanModel getTbKpiGroupAll() {
		TbKpiGroupBeanModel returnValue = new TbKpiGroupBeanModel();

		try {
			TbKpiGroupBean tbCurrencyBeans[] = TbKpiGroupManager.getInstance().loadAll();
			TbKpiGroupBeanModel tbCurrencyBeanModels[] = TbKpiGroupManager.getInstance().toBeanModels(tbCurrencyBeans);

			returnValue.setModels(tbCurrencyBeanModels);
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
