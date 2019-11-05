package com.gwt.hris.server.service.admin.projectinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.projectinfo.ProjectInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;
import com.gwt.hris.client.service.bean.TbProjectEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewProjectCustomerBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbCustomerBean;
import com.gwt.hris.db.TbCustomerManager;
import com.gwt.hris.db.TbProjectBean;
import com.gwt.hris.db.TbProjectEmployeeBean;
import com.gwt.hris.db.TbProjectEmployeeManager;
import com.gwt.hris.db.TbProjectManager;
import com.gwt.hris.db.ViewProjectCustomerBean;
import com.gwt.hris.db.ViewProjectCustomerManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ProjectImpl extends MainRemoteServiceServlet implements ProjectInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitProject(TbProjectBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbProjectBean bean = null;

			if (beanModel.getTbpId() != null) {
				bean = TbProjectManager.getInstance().loadByPrimaryKey(beanModel.getTbpId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 34, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbProjectManager.getInstance().createTbProjectBean();

				bean = TbProjectManager.getInstance().toBean(beanModel, bean);

				TbProjectManager.getInstance().save(bean);

				bean.setTbpProjectId("PRJ" + bean.getTbpId());

				TbProjectManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 34, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbProjectManager.getInstance().toBean(beanModel, bean);

				TbProjectManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbProjectManager.getInstance().toBeanModel(bean);
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

	public TbProjectBeanModel getProject(int id) {
		TbProjectBeanModel returnValue = new TbProjectBeanModel();

		try {
			TbProjectBean TbProjectBean = TbProjectManager.getInstance().loadByPrimaryKey(id);

			if (TbProjectBean != null) {
				returnValue = TbProjectManager.getInstance().toBeanModel(TbProjectBean);

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

	public ReturnBean deleteProject(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 34, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbProjectManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkProject(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 34, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbProjectManager.getInstance().deleteByWhere("where tbp_id in (" + strId + ")");

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

	public PagingLoadResult<ViewProjectCustomerBeanModel> getProjectPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewProjectCustomerBeanModel> list = new ArrayList<ViewProjectCustomerBeanModel>();

		ViewProjectCustomerBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 34, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Project ID".equals(searchBy)) {
				strWhere = "where tbp_project_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Project Name".equals(searchBy)) {
				strWhere = "where tbp_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Customer Name".equals(searchBy)) {
				strWhere = "where tbc_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewProjectCustomerManager.getInstance().countAll();
			} else {
				size = ViewProjectCustomerManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewProjectCustomerManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewProjectCustomerBeanModel data = ViewProjectCustomerManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewProjectCustomerBeanModel data = new ViewProjectCustomerBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewProjectCustomerBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean deleteProjectEmployee(Integer tbeId, Integer tbpId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 34, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbProjectEmployeeManager.getInstance().deleteByWhere("where tbe_id = " + tbeId + " and tbp_id =" + tbpId);

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

	public ReturnBean submitProjectEmployee(TbProjectEmployeeBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 34, SystemUtil.ACCESS_INSERT) == false) {
				throw new SystemException("No insert access");
			}

			Manager.getInstance().beginTransaction();

			TbProjectEmployeeBean tbPaygradeBean = TbProjectEmployeeManager.getInstance().createTbProjectEmployeeBean();

			tbPaygradeBean = TbProjectEmployeeManager.getInstance().toBean(beanModel, tbPaygradeBean);

			TbProjectEmployeeManager.getInstance().save(tbPaygradeBean);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Saved");

			beanModel = TbProjectEmployeeManager.getInstance().toBeanModel(tbPaygradeBean);
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

	public TbCustomerBeanModel getTbCustomerAll() {
		TbCustomerBeanModel returnValue = new TbCustomerBeanModel();

		try {
			TbCustomerBean beans[] = TbCustomerManager.getInstance().loadAll();
			TbCustomerBeanModel beanModels[] = TbCustomerManager.getInstance().toBeanModels(beans);

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
}
