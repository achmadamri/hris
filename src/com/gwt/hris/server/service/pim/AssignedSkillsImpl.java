package com.gwt.hris.server.service.pim;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedSkillsBeanModel;
import com.gwt.hris.client.service.bean.TbSkillsBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeSkillsBeanModel;
import com.gwt.hris.client.service.pim.AssignedSkillsInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAssignedSkillsBean;
import com.gwt.hris.db.TbAssignedSkillsManager;
import com.gwt.hris.db.TbSkillsBean;
import com.gwt.hris.db.TbSkillsManager;
import com.gwt.hris.db.ViewEmployeeSkillsBean;
import com.gwt.hris.db.ViewEmployeeSkillsManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class AssignedSkillsImpl extends MainRemoteServiceServlet implements AssignedSkillsInterface {
	
	private static final long serialVersionUID = -3373326569009469271L;

	public ReturnBean submitAssignedSkills(TbAssignedSkillsBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbAssignedSkillsBean bean = null;

			if (beanModel.getTbasId() != null) {
				bean = TbAssignedSkillsManager.getInstance().loadByPrimaryKey(beanModel.getTbasId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbAssignedSkillsManager.getInstance().createTbAssignedSkillsBean();

				bean = TbAssignedSkillsManager.getInstance().toBean(beanModel, bean);

				TbAssignedSkillsManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbAssignedSkillsManager.getInstance().toBean(beanModel, bean);

				TbAssignedSkillsManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbAssignedSkillsManager.getInstance().toBeanModel(bean);
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

	public PagingLoadResult<ViewEmployeeSkillsBeanModel> getAssignedSkillsPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeeSkillsBeanModel> list = new ArrayList<ViewEmployeeSkillsBeanModel>();

		ViewEmployeeSkillsBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			strWhere = "where " + searchBy + "=" + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = ViewEmployeeSkillsManager.getInstance().countAll();
			} else {
				size = ViewEmployeeSkillsManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeSkillsManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeSkillsBeanModel data = ViewEmployeeSkillsManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeSkillsBeanModel data = new ViewEmployeeSkillsBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeSkillsBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean deleteAssignedSkills(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbAssignedSkillsManager.getInstance().deleteByPrimaryKey(id);

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

	public TbAssignedSkillsBeanModel getAssignedSkills(int id) {
		TbAssignedSkillsBeanModel returnValue = new TbAssignedSkillsBeanModel();

		try {
			TbAssignedSkillsBean bean = TbAssignedSkillsManager.getInstance().loadByPrimaryKey(id);

			if (bean != null) {
				returnValue = TbAssignedSkillsManager.getInstance().toBeanModel(bean);

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

	public TbSkillsBeanModel getTbSkillsAll() {
		TbSkillsBeanModel returnValue = new TbSkillsBeanModel();

		try {
			TbSkillsBean beans[] = TbSkillsManager.getInstance().loadAll();
			TbSkillsBeanModel beanModels[] = TbSkillsManager.getInstance().toBeanModels(beans);

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
