package com.gwt.hris.server.service.recruitment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbVacancyBeanModel;
import com.gwt.hris.client.service.bean.ViewVacancyBeanModel;
import com.gwt.hris.client.service.recruitment.VacanciesInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbJobTitleBean;
import com.gwt.hris.db.TbJobTitleManager;
import com.gwt.hris.db.TbVacancyBean;
import com.gwt.hris.db.TbVacancyManager;
import com.gwt.hris.db.ViewVacancyBean;
import com.gwt.hris.db.ViewVacancyManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class VacanciesImpl extends MainRemoteServiceServlet implements VacanciesInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitVacancy(TbVacancyBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbVacancyBean bean = null;

			if (beanModel.getTbvId() != null) {
				bean = TbVacancyManager.getInstance().loadByPrimaryKey(beanModel.getTbvId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 76, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbVacancyManager.getInstance().createTbVacancyBean();

				bean = TbVacancyManager.getInstance().toBean(beanModel, bean);

				TbVacancyManager.getInstance().save(bean);

				bean.setTbvVacancyId("VCCY" + bean.getTbvId());

				TbVacancyManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 76, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbVacancyManager.getInstance().toBean(beanModel, bean);

				TbVacancyManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbVacancyManager.getInstance().toBeanModel(bean);
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

	public TbVacancyBeanModel getVacancy(int id) {
		TbVacancyBeanModel returnValue = new TbVacancyBeanModel();

		try {
			TbVacancyBean TbVacancyBean = TbVacancyManager.getInstance().loadByPrimaryKey(id);

			if (TbVacancyBean != null) {
				returnValue = TbVacancyManager.getInstance().toBeanModel(TbVacancyBean);

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

	public ReturnBean deleteVacancy(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 76, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbVacancyManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkVacancy(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 76, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbVacancyManager.getInstance().deleteByWhere("where tbv_id in (" + strId + ")");

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

	public PagingLoadResult<ViewVacancyBeanModel> getVacancyPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewVacancyBeanModel> list = new ArrayList<ViewVacancyBeanModel>();

		ViewVacancyBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 76, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";

			if ("Vac. ID".equals(searchBy)) {
				strWhere = "where tbv_vacancy_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Vac. Name".equals(searchBy)) {
				strWhere = "where tbv_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Job Title".equals(searchBy)) {
				strWhere = "where tbjt_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Hiring Manager".equals(searchBy)) {
				strWhere = "where tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Status".equals(searchBy)) {
				strWhere = "where tbv_active = " + ("closed".equalsIgnoreCase(searchValue) ? "0" : "open".equalsIgnoreCase(searchValue) ? "1" : "2") + " ";
			}

			if ("".equals(strWhere)) {
				size = ViewVacancyManager.getInstance().countAll();
			} else {
				size = ViewVacancyManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewVacancyManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewVacancyBeanModel data = ViewVacancyManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewVacancyBeanModel data = new ViewVacancyBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewVacancyBeanModel>(list, config.getOffset(), size);
	}

	public TbEmployeeBeanModel getTbEmployeeById(int id) {
		TbEmployeeBeanModel returnValue = new TbEmployeeBeanModel();

		try {
			TbEmployeeBean bean = TbEmployeeManager.getInstance().loadByPrimaryKey(id);
			returnValue = TbEmployeeManager.getInstance().toBeanModel(bean);

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

	public TbJobTitleBeanModel getTbJobTitleAll() {
		TbJobTitleBeanModel returnValue = new TbJobTitleBeanModel();

		try {
			TbJobTitleBean tbJobTitleBeans[] = TbJobTitleManager.getInstance().loadAll();
			TbJobTitleBeanModel tbJobTitleBeanModels[] = TbJobTitleManager.getInstance().toBeanModels(tbJobTitleBeans);

			returnValue.setModels(tbJobTitleBeanModels);
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
