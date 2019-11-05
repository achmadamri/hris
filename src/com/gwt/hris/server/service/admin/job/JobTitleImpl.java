package com.gwt.hris.server.service.admin.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.job.JobTitleInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.client.service.bean.TbJobSpecificationsBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbEmploymentStatusBean;
import com.gwt.hris.db.TbEmploymentStatusManager;
import com.gwt.hris.db.TbJobEmploymentStatusBean;
import com.gwt.hris.db.TbJobEmploymentStatusManager;
import com.gwt.hris.db.TbJobRenumerationBean;
import com.gwt.hris.db.TbJobRenumerationManager;
import com.gwt.hris.db.TbJobSpecificationsBean;
import com.gwt.hris.db.TbJobSpecificationsManager;
import com.gwt.hris.db.TbJobTitleBean;
import com.gwt.hris.db.TbJobTitleManager;
import com.gwt.hris.db.TbMenuAccessBean;
import com.gwt.hris.db.TbMenuAccessManager;
import com.gwt.hris.db.TbMenuBean;
import com.gwt.hris.db.TbMenuManager;
import com.gwt.hris.db.TbPaygradeBean;
import com.gwt.hris.db.TbPaygradeManager;
import com.gwt.hris.db.TbRenumerationBean;
import com.gwt.hris.db.TbRenumerationManager;
import com.gwt.hris.db.ViewMenuAccessBean;
import com.gwt.hris.db.ViewMenuAccessManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class JobTitleImpl extends MainRemoteServiceServlet implements JobTitleInterface {
	
	private static final long serialVersionUID = -9130232253110656682L;

	public ReturnBean submitJobTitle(TbJobTitleBeanModel tbJobTitleBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbJobTitleBean tbJobTitleBean = null;

			if (tbJobTitleBeanModel.getTbjtId() != null) {
				tbJobTitleBean = TbJobTitleManager.getInstance().loadByPrimaryKey(tbJobTitleBeanModel.getTbjtId());
			}

			if (tbJobTitleBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				tbJobTitleBean = TbJobTitleManager.getInstance().createTbJobTitleBean();

				tbJobTitleBean = TbJobTitleManager.getInstance().toBean(tbJobTitleBeanModel, tbJobTitleBean);

				TbJobTitleManager.getInstance().save(tbJobTitleBean);

				tbJobTitleBean.setTbjtJobTitleId("JOB" + tbJobTitleBean.getTbjtId());

				TbJobTitleManager.getInstance().update(tbJobTitleBean);

				TbMenuBean tbMenuBeans[] = TbMenuManager.getInstance().loadAll();
				TbMenuAccessBean tbMenuAccessBeans[] = new TbMenuAccessBean[tbMenuBeans.length];
				for (int i = 0; i < tbMenuAccessBeans.length; i++) {
					tbMenuAccessBeans[i] = TbMenuAccessManager.getInstance().createTbMenuAccessBean();
					tbMenuAccessBeans[i].setTbmId(tbMenuBeans[i].getTbmId());
					tbMenuAccessBeans[i].setTbjtId(tbJobTitleBean.getTbjtId());
				}
				TbMenuAccessManager.getInstance().save(tbMenuAccessBeans);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				tbJobTitleBean = TbJobTitleManager.getInstance().toBean(tbJobTitleBeanModel, tbJobTitleBean);

				TbJobTitleManager.getInstance().save(tbJobTitleBean);

				List<ViewMenuAccessBeanModel> lstViewMenuAccessBeanModel = tbJobTitleBeanModel.get("lstViewMenuAccessBeanModel");

				if (lstViewMenuAccessBeanModel.size() > 0) {
					TbMenuAccessManager.getInstance().deleteBytbma_fk_2(tbJobTitleBean.getTbjtId());

					Iterator<ViewMenuAccessBeanModel> itrViewMenuAccessBeanModel = lstViewMenuAccessBeanModel.iterator();
					TbMenuAccessBean tbMenuAccessBeans[] = new TbMenuAccessBean[lstViewMenuAccessBeanModel.size()];
					int i = 0;
					while (itrViewMenuAccessBeanModel.hasNext()) {
						ViewMenuAccessBeanModel viewMenuAccessBeanModel = itrViewMenuAccessBeanModel.next();
						tbMenuAccessBeans[i] = TbMenuAccessManager.getInstance().createTbMenuAccessBean();
						tbMenuAccessBeans[i].setTbmId(viewMenuAccessBeanModel.getTbmId());
						tbMenuAccessBeans[i].setTbjtId(tbJobTitleBean.getTbjtId());

						if (viewMenuAccessBeanModel.get("tbmaEnabledBoolean")) {
							tbMenuAccessBeans[i].setTbmaEnabled(1);
						} else {
							tbMenuAccessBeans[i].setTbmaEnabled(0);
						}

						if (viewMenuAccessBeanModel.get("tbmaInsertBoolean")) {
							tbMenuAccessBeans[i].setTbmaInsert(1);
						} else {
							tbMenuAccessBeans[i].setTbmaInsert(0);
						}

						if (viewMenuAccessBeanModel.get("tbmaUpdateBoolean")) {
							tbMenuAccessBeans[i].setTbmaUpdate(1);
						} else {
							tbMenuAccessBeans[i].setTbmaUpdate(0);
						}

						if (viewMenuAccessBeanModel.get("tbmaDeleteBoolean")) {
							tbMenuAccessBeans[i].setTbmaDelete(1);
						} else {
							tbMenuAccessBeans[i].setTbmaDelete(0);
						}

						if (viewMenuAccessBeanModel.get("tbmaViewBoolean")) {
							tbMenuAccessBeans[i].setTbmaView(1);
						} else {
							tbMenuAccessBeans[i].setTbmaView(0);
						}

						if (viewMenuAccessBeanModel.get("tbmaApproveBoolean")) {
							tbMenuAccessBeans[i].setTbmaApprove(1);
						} else {
							tbMenuAccessBeans[i].setTbmaApprove(0);
						}

						i++;
					}
					TbMenuAccessManager.getInstance().save(tbMenuAccessBeans);
				}

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			TbJobEmploymentStatusManager.getInstance().deleteBytbjes_fk_1(tbJobTitleBean.getTbjtId());
			String tbesIds[] = tbJobTitleBeanModel.get("tbesIds");
			TbJobEmploymentStatusBean tbJobEmploymentStatusBeans[] = new TbJobEmploymentStatusBean[tbesIds.length];
			for (int i = 0; i < tbJobEmploymentStatusBeans.length; i++) {
				tbJobEmploymentStatusBeans[i] = TbJobEmploymentStatusManager.getInstance().createTbJobEmploymentStatusBean();
				tbJobEmploymentStatusBeans[i].setTbesId(Integer.parseInt(tbesIds[i]));
				tbJobEmploymentStatusBeans[i].setTbjtId(tbJobTitleBean.getTbjtId());
			}
			TbJobEmploymentStatusManager.getInstance().save(tbJobEmploymentStatusBeans);

			TbJobRenumerationManager.getInstance().deleteBytbjr_fk_1(tbJobTitleBean.getTbjtId());
			String tbrIds[] = tbJobTitleBeanModel.get("tbrIds");
			TbJobRenumerationBean tbJobRenumerationBeans[] = new TbJobRenumerationBean[tbrIds.length];
			for (int i = 0; i < tbJobRenumerationBeans.length; i++) {
				tbJobRenumerationBeans[i] = TbJobRenumerationManager.getInstance().createTbJobRenumerationBean();
				tbJobRenumerationBeans[i].setTbrId(Integer.parseInt(tbrIds[i]));
				tbJobRenumerationBeans[i].setTbjtId(tbJobTitleBean.getTbjtId());
			}
			TbJobRenumerationManager.getInstance().save(tbJobRenumerationBeans);

			tbJobTitleBeanModel = TbJobTitleManager.getInstance().toBeanModel(tbJobTitleBean);
			returnValue.set("model", tbJobTitleBeanModel);

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

	public TbJobTitleBeanModel getJobTitle(int tbjtId) {
		TbJobTitleBeanModel returnValue = new TbJobTitleBeanModel();

		try {
			TbJobTitleBean tbJobTitleBean = TbJobTitleManager.getInstance().loadByPrimaryKey(tbjtId);

			if (tbJobTitleBean != null) {
				returnValue = TbJobTitleManager.getInstance().toBeanModel(tbJobTitleBean);

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

	public ReturnBean deleteJobTitle(Integer tbjtId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbJobEmploymentStatusManager.getInstance().deleteBytbjes_fk_1(tbjtId);
			TbJobRenumerationManager.getInstance().deleteBytbjr_fk_1(tbjtId);
			TbMenuAccessManager.getInstance().deleteBytbma_fk_2(tbjtId);
			TbJobTitleManager.getInstance().deleteByPrimaryKey(tbjtId);

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

	public ReturnBean deleteBulkJobTitle(Integer tbjtIds[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : tbjtIds) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);

			TbJobEmploymentStatusManager.getInstance().deleteByWhere("where tbjt_id in (" + strId + ")");
			TbJobRenumerationManager.getInstance().deleteByWhere("where tbjt_id in (" + strId + ")");
			TbMenuAccessManager.getInstance().deleteByWhere("where tbjt_id in (" + strId + ")");
			TbJobTitleManager.getInstance().deleteByWhere("where tbjt_id in (" + strId + ")");

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

	public PagingLoadResult<TbJobTitleBeanModel> getJobTitlePaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbJobTitleBeanModel> list = new ArrayList<TbJobTitleBeanModel>();

		TbJobTitleBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Job Title ID".equals(searchBy)) {
				strWhere = "where tbjt_job_title_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Job Title Name".equals(searchBy)) {
				strWhere = "where tbjt_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Leave Entitled".equals(searchBy)) {
				strWhere = "where tbjt_leave_entitled like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbJobTitleManager.getInstance().countAll();
			} else {
				size = TbJobTitleManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbJobTitleManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbJobTitleBeanModel data = TbJobTitleManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbJobTitleBeanModel data = new TbJobTitleBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbJobTitleBeanModel>(list, config.getOffset(), size);
	}

	public TbJobSpecificationsBeanModel getTbJobSpecificationsAll() {
		TbJobSpecificationsBeanModel returnValue = new TbJobSpecificationsBeanModel();

		try {
			TbJobSpecificationsBean tbCurrencyBeans[] = TbJobSpecificationsManager.getInstance().loadAll();
			TbJobSpecificationsBeanModel tbCurrencyBeanModels[] = TbJobSpecificationsManager.getInstance().toBeanModels(tbCurrencyBeans);

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

	public TbPaygradeBeanModel getTbPaygradeAll() {
		TbPaygradeBeanModel returnValue = new TbPaygradeBeanModel();

		try {
			TbPaygradeBean tbCurrencyBeans[] = TbPaygradeManager.getInstance().loadAll();
			TbPaygradeBeanModel tbCurrencyBeanModels[] = TbPaygradeManager.getInstance().toBeanModels(tbCurrencyBeans);

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

	public TbEmploymentStatusBeanModel getTbEmploymentStatusAll() {
		TbEmploymentStatusBeanModel returnValue = new TbEmploymentStatusBeanModel();

		try {
			TbEmploymentStatusBean tbCurrencyBeans[] = TbEmploymentStatusManager.getInstance().loadAll();
			TbEmploymentStatusBeanModel tbCurrencyBeanModels[] = TbEmploymentStatusManager.getInstance().toBeanModels(tbCurrencyBeans);

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

	public TbEmploymentStatusBeanModel getTbEmploymentStatusByTbjtId(int tbjtId) {
		TbEmploymentStatusBeanModel returnValue = new TbEmploymentStatusBeanModel();

		try {
			TbEmploymentStatusBean tbCurrencyBeans[] = TbEmploymentStatusManager.getInstance().loadByWhere("where tbes_id in (select tbes_id from tb_job_employment_status where tbjt_id = " + tbjtId + ")");
			TbEmploymentStatusBeanModel tbCurrencyBeanModels[] = TbEmploymentStatusManager.getInstance().toBeanModels(tbCurrencyBeans);

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

	public TbEmploymentStatusBeanModel getTbEmploymentStatusByNotTbjtId(int tbjtId) {
		TbEmploymentStatusBeanModel returnValue = new TbEmploymentStatusBeanModel();

		try {
			TbEmploymentStatusBean tbCurrencyBeans[] = TbEmploymentStatusManager.getInstance().loadByWhere("where tbes_id not in (select tbes_id from tb_job_employment_status where tbjt_id = " + tbjtId + ")");
			TbEmploymentStatusBeanModel tbCurrencyBeanModels[] = TbEmploymentStatusManager.getInstance().toBeanModels(tbCurrencyBeans);

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

	public TbRenumerationBeanModel getTbRenumerationAll() {
		TbRenumerationBeanModel returnValue = new TbRenumerationBeanModel();

		try {
			TbRenumerationBean tbCurrencyBeans[] = TbRenumerationManager.getInstance().loadAll();
			TbRenumerationBeanModel tbCurrencyBeanModels[] = TbRenumerationManager.getInstance().toBeanModels(tbCurrencyBeans);

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

	public TbRenumerationBeanModel getTbRenumerationByTbjtId(int tbjtId) {
		TbRenumerationBeanModel returnValue = new TbRenumerationBeanModel();

		try {
			TbRenumerationBean tbRenumerationBeans[] = TbRenumerationManager.getInstance().loadByWhere("where tbr_id in (select tbr_id from tb_job_renumeration where tbjt_id = " + tbjtId + ")");
			TbRenumerationBeanModel tbRenumerationBeanModels[] = TbRenumerationManager.getInstance().toBeanModels(tbRenumerationBeans);

			returnValue.setModels(tbRenumerationBeanModels);
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

	public TbRenumerationBeanModel getTbRenumerationByNotTbjtId(int tbjtId) {
		TbRenumerationBeanModel returnValue = new TbRenumerationBeanModel();

		try {
			TbRenumerationBean tbRenumerationBeans[] = TbRenumerationManager.getInstance().loadByWhere("where tbr_id not in (select tbr_id from tb_job_renumeration where tbjt_id = " + tbjtId + ")");
			TbRenumerationBeanModel tbRenumerationBeanModels[] = TbRenumerationManager.getInstance().toBeanModels(tbRenumerationBeans);

			returnValue.setModels(tbRenumerationBeanModels);
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

	public PagingLoadResult<ViewMenuAccessBeanModel> getMenuAccessPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewMenuAccessBeanModel> list = new ArrayList<ViewMenuAccessBeanModel>();

		ViewMenuAccessBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbm_disabled = 0 ";
			if ("tbjtId".equals(searchBy)) {
				strWhere += "and tbjt_id = " + searchValue.replaceAll("'", "") + " ";
			}

			if ("".equals(strWhere)) {
				size = ViewMenuAccessManager.getInstance().countAll();
			} else {
				size = ViewMenuAccessManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by tbm_id_parent, tbm_id, " + StringUtil.getInstance().getTableField(config.getSortField().replaceAll("Boolean", "")) + " " + config.getSortDir() + " ";
				}
			} else {
				strWhere = strWhere + " order by tbm_id_parent, tbm_id ";
			}

			datas = ViewMenuAccessManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewMenuAccessBeanModel data = ViewMenuAccessManager.getInstance().toBeanModel(datas[i]);

				if (data.getTbmaEnabled() == null) {
					data.set("tbmaEnabledBoolean", false);
				} else {
					if (data.getTbmaEnabled() == 1) {
						data.set("tbmaEnabledBoolean", true);
					} else {
						data.set("tbmaEnabledBoolean", false);
					}
				}

				if (data.getTbmaInsert() == null) {
					data.set("tbmaInsertBoolean", false);
				} else {
					if (data.getTbmaInsert() == 1) {
						data.set("tbmaInsertBoolean", true);
					} else {
						data.set("tbmaInsertBoolean", false);
					}
				}

				if (data.getTbmaUpdate() == null) {
					data.set("tbmaUpdateBoolean", false);
				} else {
					if (data.getTbmaUpdate() == 1) {
						data.set("tbmaUpdateBoolean", true);
					} else {
						data.set("tbmaUpdateBoolean", false);
					}
				}

				if (data.getTbmaDelete() == null) {
					data.set("tbmaDeleteBoolean", false);
				} else {
					if (data.getTbmaDelete() == 1) {
						data.set("tbmaDeleteBoolean", true);
					} else {
						data.set("tbmaDeleteBoolean", false);
					}
				}

				if (data.getTbmaView() == null) {
					data.set("tbmaViewBoolean", false);
				} else {
					if (data.getTbmaView() == 1) {
						data.set("tbmaViewBoolean", true);
					} else {
						data.set("tbmaViewBoolean", false);
					}
				}

				if (data.getTbmaApprove() == null) {
					data.set("tbmaApproveBoolean", false);
				} else {
					if (data.getTbmaApprove() == 1) {
						data.set("tbmaApproveBoolean", true);
					} else {
						data.set("tbmaApproveBoolean", false);
					}
				}
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewMenuAccessBeanModel data = new ViewMenuAccessBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewMenuAccessBeanModel>(list, config.getOffset(), size);
	}
}
