package com.gwt.hris.server.service.recruitment;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbApplicantsBeanModel;
import com.gwt.hris.client.service.bean.TbVacancyBeanModel;
import com.gwt.hris.client.service.bean.ViewApplicantsBeanModel;
import com.gwt.hris.client.service.recruitment.ApplicantsInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbApplicantsBean;
import com.gwt.hris.db.TbApplicantsManager;
import com.gwt.hris.db.TbVacancyBean;
import com.gwt.hris.db.TbVacancyManager;
import com.gwt.hris.db.ViewApplicantsBean;
import com.gwt.hris.db.ViewApplicantsManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ApplicantsImpl extends MainRemoteServiceServlet implements ApplicantsInterface {
	
	private static final long serialVersionUID = 7587496690521381080L;

	public ReturnBean submitApplicants(TbApplicantsBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbApplicantsBean bean = null;

			if (beanModel.getTbaId() != null) {
				bean = TbApplicantsManager.getInstance().loadByPrimaryKey(beanModel.getTbaId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 77, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbApplicantsManager.getInstance().createTbApplicantsBean();

				bean = TbApplicantsManager.getInstance().toBean(beanModel, bean);

				bean.setTbaName(bean.getTbaFirstName() + " " + bean.getTbaMiddleName() + " " + bean.getTbaLastName());
				bean.setTbaDate(new Date().getTime());
				bean.setTbaStatus(0);

				TbApplicantsManager.getInstance().save(bean);

				bean.setTbaApplicantsId("APPL" + bean.getTbaId());

				TbApplicantsManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 77, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				if (bean.getTbaDate() == null) {
					beanModel.setTbaDate(new Date().getTime());
				} else {
					beanModel.setTbaDate(bean.getTbaDate());
				}

				bean = TbApplicantsManager.getInstance().toBean(beanModel, bean);

				TbApplicantsManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbApplicantsManager.getInstance().toBeanModel(bean);
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

	public TbApplicantsBeanModel getApplicants(int id) {
		TbApplicantsBeanModel returnValue = new TbApplicantsBeanModel();

		try {
			TbApplicantsBean TbApplicantsBean = TbApplicantsManager.getInstance().loadByPrimaryKey(id);

			if (TbApplicantsBean != null) {
				returnValue = TbApplicantsManager.getInstance().toBeanModel(TbApplicantsBean);

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

	public ReturnBean deleteApplicants(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 77, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			try {
				String folder = getServletContext().getRealPath("upload/resume");
				TbApplicantsBean tbApplicantsBean = TbApplicantsManager.getInstance().loadByPrimaryKey(id);
				File file = new File(folder, tbApplicantsBean.getTbaResumeFileName());
				file.delete();
			} catch (Exception e) {
			}

			TbApplicantsManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkApplicants(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 77, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String folder = getServletContext().getRealPath("upload/resume");
			for (Integer id : ids) {
				try {
					TbApplicantsBean tbApplicantsBean = TbApplicantsManager.getInstance().loadByPrimaryKey(id);
					File file = new File(folder, tbApplicantsBean.getTbaResumeFileName());
					file.delete();
				} catch (Exception e) {
				}
			}

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbApplicantsManager.getInstance().deleteByWhere("where tba_id in (" + strId + ")");

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

	public PagingLoadResult<ViewApplicantsBeanModel> getApplicantsPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewApplicantsBeanModel> list = new ArrayList<ViewApplicantsBeanModel>();

		ViewApplicantsBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 77, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";

			if ("Vac. ID".equals(searchBy)) {
				strWhere = "where tbv_vacancy_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Vac. Name".equals(searchBy)) {
				strWhere = "where tbv_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("App. ID".equals(searchBy)) {
				strWhere = "where tba_applicants_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Candidate".equals(searchBy)) {
				strWhere = "where tba_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Hiring Manager".equals(searchBy)) {
				strWhere = "where tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Submit Date".equals(searchBy)) {
				strWhere = "where tba_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Status".equals(searchBy)) {
				strWhere = "where tba_status = " + ("initiated".equalsIgnoreCase(searchValue) ? "0" : "open".equalsIgnoreCase(searchValue) ? "1" : "2") + " "; // TODO
			}

			if ("".equals(strWhere)) {
				size = ViewApplicantsManager.getInstance().countAll();
			} else {
				size = ViewApplicantsManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewApplicantsManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewApplicantsBeanModel data = ViewApplicantsManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewApplicantsBeanModel data = new ViewApplicantsBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewApplicantsBeanModel>(list, config.getOffset(), size);
	}

	public TbVacancyBeanModel getTbVacancyAll() {
		TbVacancyBeanModel returnValue = new TbVacancyBeanModel();

		try {
			TbVacancyBean tbVacancyBeans[] = TbVacancyManager.getInstance().loadByWhere("where tbv_active = 1");
			TbVacancyBeanModel tbVacancyBeanModels[] = TbVacancyManager.getInstance().toBeanModels(tbVacancyBeans);

			returnValue.setModels(tbVacancyBeanModels);
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
