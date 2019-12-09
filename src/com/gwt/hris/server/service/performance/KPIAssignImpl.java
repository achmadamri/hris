package com.gwt.hris.server.service.performance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbKpiAssignBeanModel;
import com.gwt.hris.client.service.bean.ViewKpiAssignBeanModel;
import com.gwt.hris.client.service.performance.KPIAssignInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbKpiAssignBean;
import com.gwt.hris.db.TbKpiAssignManager;
import com.gwt.hris.db.ViewKpiAssignBean;
import com.gwt.hris.db.ViewKpiAssignManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class KPIAssignImpl extends MainRemoteServiceServlet implements KPIAssignInterface {
	
	private static final long serialVersionUID = 1161349876683522341L;

	public ReturnBean submitKpiAssign(TbKpiAssignBeanModel beanModel) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 114, SystemUtil.ACCESS_INSERT) == false) {
				throw new SystemException("No insert access");
			}

			Manager.getInstance().beginTransaction();

			Integer ids[] = (Integer[]) beanModel.get("tbkIds");
			for (int i = 0; i < ids.length; i++) {
				TbKpiAssignBean bean = null;
				bean = TbKpiAssignManager.getInstance().createTbKpiAssignBean();
				bean = TbKpiAssignManager.getInstance().toBean(beanModel, bean);
				bean.setTbkId(ids[i]);
				bean.setTbkaStatus(0);
				bean.setTbkaPoin(0);
				bean.setTbkaSpvId(tbEmployeeBeanModel.getTbeId());
				TbKpiAssignManager.getInstance().save(bean);
			}

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Saved");
			
			SystemUtil.getInstance().notification(SystemUtil.CHANNEL_EMAIL, 3, SystemUtil.UI_KPI_APPROVAL_ASSIGN, tbEmployeeBeanModel.getTbeId(), 0, new String[]{""});

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

	public TbKpiAssignBeanModel getKpiAssign(int id) {
		TbKpiAssignBeanModel returnValue = new TbKpiAssignBeanModel();

		try {
			TbKpiAssignBean TbKpiAssignBean = TbKpiAssignManager.getInstance().loadByPrimaryKey(id);

			if (TbKpiAssignBean != null) {
				returnValue = TbKpiAssignManager.getInstance().toBeanModel(TbKpiAssignBean);

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

	public ReturnBean deleteKpiAssign(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 114, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			if (TbKpiAssignManager.getInstance().countWhere("where tbka_id in (" + id + ") and tbka_status in (0, 2, 5)") > 0) {
				TbKpiAssignManager.getInstance().deleteByPrimaryKey(id);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Deleted");

				commit = true;
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Fail Deleted");
			}
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

	public ReturnBean approveBulkKpiAssign(Integer ids[], Integer status) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 115, SystemUtil.ACCESS_APPROVE) == false) {
				throw new SystemException("No approve access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);

			TbKpiAssignBean tbKpiAssignBeans[] = TbKpiAssignManager.getInstance().loadByWhere("where tbka_id in (" + strId + ") and tbka_status in (0, 3)");

			if (tbKpiAssignBeans.length == ids.length) {
				if (status == 1) {
					for (TbKpiAssignBean tbKpiAssignBean : tbKpiAssignBeans) {
						if (tbKpiAssignBean.getTbkaStatus() == 0) {
							tbKpiAssignBean.setTbkaStatus(1);
						} else if (tbKpiAssignBean.getTbkaStatus() == 3) {
							tbKpiAssignBean.setTbkaStatus(4);
						}
						TbKpiAssignManager.getInstance().save(tbKpiAssignBean);
					}

					returnValue.setOperationStatus(true);
					returnValue.setMessage("Success Approve");
				} else {
					for (TbKpiAssignBean tbKpiAssignBean : tbKpiAssignBeans) {
						if (tbKpiAssignBean.getTbkaStatus() == 0) {
							tbKpiAssignBean.setTbkaStatus(2);
						} else if (tbKpiAssignBean.getTbkaStatus() == 3) {
							tbKpiAssignBean.setTbkaStatus(5);
						}
						TbKpiAssignManager.getInstance().save(tbKpiAssignBean);
					}

					returnValue.setOperationStatus(true);
					returnValue.setMessage("Success Reject");
				}

				commit = true;
			} else {
				returnValue.setOperationStatus(false);

				if (status == 1) {
					returnValue.setMessage("Fail Approve");
				} else {
					returnValue.setMessage("Fail Reject");
				}
			}
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

	public ReturnBean deleteBulkKpiAssign(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 114, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);

			TbKpiAssignBean tbKpiAssignBeans[] = TbKpiAssignManager.getInstance().loadByWhere("where tbka_id in (" + strId + ") and tbka_status in (0, 2, 5)");

			if (tbKpiAssignBeans.length == ids.length) {
				TbKpiAssignManager.getInstance().deleteByWhere("where tbka_id in (" + strId + ") and tbka_status in (0, 2, 5)");

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Deleted");

				commit = true;
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Fail Deleted");
			}
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
	
	public PagingLoadResult<ViewKpiAssignBeanModel> getKpiApprovalAssignPaging(final PagingLoadConfig config, String tbeName, String searchBy, String searchValue) {
		List<ViewKpiAssignBeanModel> list = new ArrayList<ViewKpiAssignBeanModel>();

		ViewKpiAssignBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 115, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_name = '" + tbeName + "' and tbka_status = 0 ";
			
			if ("KPI Group".equals(searchBy)) {
				strWhere += "and tbkg_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Description".equals(searchBy)) {
				strWhere += "and tbk_description like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewKpiAssignManager.getInstance().countAll();
			} else {
				size = ViewKpiAssignManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewKpiAssignManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewKpiAssignBeanModel data = ViewKpiAssignManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewKpiAssignBeanModel data = new ViewKpiAssignBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewKpiAssignBeanModel>(list, config.getOffset(), size);
	}
	
	public PagingLoadResult<ViewKpiAssignBeanModel> getKpiApprovalScoringPaging(final PagingLoadConfig config, String tbeName, String searchBy, String searchValue) {
		List<ViewKpiAssignBeanModel> list = new ArrayList<ViewKpiAssignBeanModel>();

		ViewKpiAssignBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 141, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_name = '" + tbeName + "' and tbka_status = 3 ";
			
			if ("KPI Group".equals(searchBy)) {
				strWhere += "and tbkg_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Description".equals(searchBy)) {
				strWhere += "and tbk_description like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewKpiAssignManager.getInstance().countAll();
			} else {
				size = ViewKpiAssignManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewKpiAssignManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewKpiAssignBeanModel data = ViewKpiAssignManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewKpiAssignBeanModel data = new ViewKpiAssignBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewKpiAssignBeanModel>(list, config.getOffset(), size);
	}

	public PagingLoadResult<ViewKpiAssignBeanModel> getKpiAssignPaging(final PagingLoadConfig config, String tbeName, String searchBy, String searchValue) {
		List<ViewKpiAssignBeanModel> list = new ArrayList<ViewKpiAssignBeanModel>();

		ViewKpiAssignBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 114, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_name = '" + tbeName + "' ";
			
			if ("KPI Group".equals(searchBy)) {
				strWhere += "and tbkg_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Description".equals(searchBy)) {
				strWhere += "and tbk_description like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewKpiAssignManager.getInstance().countAll();
			} else {
				size = ViewKpiAssignManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewKpiAssignManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewKpiAssignBeanModel data = ViewKpiAssignManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewKpiAssignBeanModel data = new ViewKpiAssignBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewKpiAssignBeanModel>(list, config.getOffset(), size);
	}

	public TbEmployeeBeanModel getTbEmployeeBeanAssign() {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		TbEmployeeBeanModel returnValue = new TbEmployeeBeanModel();

		try {
			String strWhere = "where tbe_id in (select tbe_id from tb_report_to where tbrt_reporting_method = 0 and tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + ") ";

			TbEmployeeBean tbEmployeeBeans[] = TbEmployeeManager.getInstance().loadByWhere(strWhere);
			TbEmployeeBeanModel tbEmployeeBeanModels[] = TbEmployeeManager.getInstance().toBeanModels(tbEmployeeBeans);

			returnValue.setModels(tbEmployeeBeanModels);
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

	public TbEmployeeBeanModel getTbEmployeeBeanApprovalAssign() {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		TbEmployeeBeanModel returnValue = new TbEmployeeBeanModel();

		try {
			String strWhere = "where tbe_id in (select tbe_id from tb_report_to where tbrt_reporting_method = 0 and tbrt_spv in (select tbe_id from tb_report_to where tbrt_reporting_method = 0 and tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + "))";

			TbEmployeeBean tbEmployeeBeans[] = TbEmployeeManager.getInstance().loadByWhere(strWhere);
			TbEmployeeBeanModel tbEmployeeBeanModels[] = TbEmployeeManager.getInstance().toBeanModels(tbEmployeeBeans);

			returnValue.setModels(tbEmployeeBeanModels);
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

	public TbEmployeeBeanModel getTbEmployeeBeanApprovalScoring() {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		TbEmployeeBeanModel returnValue = new TbEmployeeBeanModel();

		try {
			String strWhere = "where tbe_id in (select tbe_id from tb_report_to where tbrt_reporting_method = 0 and tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + ")";

			TbEmployeeBean tbEmployeeBeans[] = TbEmployeeManager.getInstance().loadByWhere(strWhere);
			TbEmployeeBeanModel tbEmployeeBeanModels[] = TbEmployeeManager.getInstance().toBeanModels(tbEmployeeBeans);

			returnValue.setModels(tbEmployeeBeanModels);
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

	public ViewKpiAssignBeanModel getViewKpiAssign(int id) {
		ViewKpiAssignBeanModel returnValue = new ViewKpiAssignBeanModel();

		try {
			ViewKpiAssignBean ViewKpiAssignBean = ViewKpiAssignManager.getInstance().loadByWhere("where tbka_id = " + id)[0];

			if (ViewKpiAssignBean != null) {
				returnValue = ViewKpiAssignManager.getInstance().toBeanModel(ViewKpiAssignBean);

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

	public ReturnBean submitKpiScoring(TbKpiAssignBeanModel beanModel) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 114, SystemUtil.ACCESS_UPDATE) == false
				&&
				SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 116, SystemUtil.ACCESS_UPDATE) == false) {
				throw new SystemException("No update access");
			}
			
			Manager.getInstance().beginTransaction();

			TbKpiAssignBean bean = TbKpiAssignManager.getInstance().loadByPrimaryKey(beanModel.getTbkaId());

			bean = TbKpiAssignManager.getInstance().toBean(beanModel, bean);
			bean.setTbkaStatus(3);

			TbKpiAssignManager.getInstance().save(bean);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Updated");

			beanModel = TbKpiAssignManager.getInstance().toBeanModel(bean);
			returnValue.set("model", beanModel);
			
			SystemUtil.getInstance().notification(SystemUtil.CHANNEL_EMAIL, 3, SystemUtil.UI_KPI_APPROVAL_ASSIGN, tbEmployeeBeanModel.getTbeId(), 0, new String[]{""});

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
	
	public ReturnBean approveKpiAssign(Integer id, Integer status) {
		ReturnBean returnValue = new ReturnBean();
		
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 115, SystemUtil.ACCESS_APPROVE) == false) {
				throw new SystemException("No approve access");
			}

			Manager.getInstance().beginTransaction();

			TbKpiAssignBean tbKpiAssignBean = TbKpiAssignManager.getInstance().loadByPrimaryKey(id);

			if (status == 1) {
				if (tbKpiAssignBean.getTbkaStatus() == 0) {
					tbKpiAssignBean.setTbkaStatus(1);
				} else if (tbKpiAssignBean.getTbkaStatus() == 3) {
					tbKpiAssignBean.setTbkaStatus(4);
				}
				TbKpiAssignManager.getInstance().save(tbKpiAssignBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Approve");
			} else {
				if (tbKpiAssignBean.getTbkaStatus() == 0) {
					tbKpiAssignBean.setTbkaStatus(2);
				} else if (tbKpiAssignBean.getTbkaStatus() == 3) {
					tbKpiAssignBean.setTbkaStatus(5);
				}
				TbKpiAssignManager.getInstance().save(tbKpiAssignBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Reject");
			}
			
			SystemUtil.getInstance().notification(SystemUtil.CHANNEL_EMAIL, 1, SystemUtil.UI_KPI_ASSIGN, tbEmployeeBeanModel.getTbeId(), tbKpiAssignBean.getTbeId(), new String[]{""});

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
}