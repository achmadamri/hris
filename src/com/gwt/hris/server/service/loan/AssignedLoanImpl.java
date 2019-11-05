package com.gwt.hris.server.service.loan;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLoanBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewAssignedLoanBeanModel;
import com.gwt.hris.client.service.loan.AssignedLoanInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAssignedLoanBean;
import com.gwt.hris.db.TbAssignedLoanManager;
import com.gwt.hris.db.ViewAssignedLoanBean;
import com.gwt.hris.db.ViewAssignedLoanManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class AssignedLoanImpl extends MainRemoteServiceServlet implements AssignedLoanInterface {
	
	private static final long serialVersionUID = 9055517397471375473L;

	public ReturnBean approvalAssignedLoan(int tbaloId, int tbaleStatus) {
		ReturnBean returnValue = new ReturnBean();

		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			String strWhere = "where tbalo_id = " + tbaloId + " and tbalo_status = 0 ";
			
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere += "and tbe_id in (select tbe_id from tb_report_to where tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + ") ";
			}

			TbAssignedLoanBean tbAssignedLoanBeans[] = TbAssignedLoanManager.getInstance().loadByWhere(strWhere);

			if (tbAssignedLoanBeans.length > 0) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 108, SystemUtil.ACCESS_APPROVE) == false) {
					throw new SystemException("No approval access");
				}

				TbAssignedLoanBean tbAssignedLoanBean = tbAssignedLoanBeans[0];
				tbAssignedLoanBean.setTbaloUpdatedTime(new Date().getTime());
				tbAssignedLoanBean.setTbaloStatus(tbaleStatus);
				TbAssignedLoanManager.getInstance().save(tbAssignedLoanBean);

				returnValue.setOperationStatus(true);

				if (tbaleStatus == 1) {
					returnValue.setMessage("Success Approve");
				} else {
					returnValue.setMessage("Success Reject");
				}
				
				SystemUtil.getInstance().notification(SystemUtil.CHANNEL_EMAIL, 1, SystemUtil.UI_LOAN_LIST, tbEmployeeBeanModel.getTbeId(), tbAssignedLoanBean.getTbeId(), new String[]{""});
				
				commit = true;
			} else {
				returnValue.setOperationStatus(false);

				if (tbaleStatus == 1) {
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

	public ReturnBean submitAssignedLoan(TbAssignedLoanBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbAssignedLoanBean bean = null;

			if (beanModel.getTbaloId() != null) {
				bean = TbAssignedLoanManager.getInstance().loadByPrimaryKey(beanModel.getTbaloId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 108, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbAssignedLoanManager.getInstance().createTbAssignedLoanBean();

				bean = TbAssignedLoanManager.getInstance().toBean(beanModel, bean);

				bean.setTbeId(tbEmployeeBeanModel.getTbeId());
				bean.setTbaloCreatedTime(new Date().getTime());

				double nominal = bean.getTbaloNominal();
				double interest = bean.getTbaloInterest();
				double tenor = bean.getTbaloTenor();
				double nominalTotal = (((interest / 100) * tenor) * nominal) + nominal;
				bean.setTbaloNominalTotal(nominalTotal);
				bean.setTbaloNominalTotalLeft(0);

				double monthlyPayment = nominalTotal / tenor;
				bean.setTbaloMonthlyPayment(monthlyPayment);

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
				bean.setTbaloStartDate(simpleDateFormat.format(new Date()));

				Calendar calEnd = Calendar.getInstance();
				calEnd.setTime(new Date());
				for (int i = 1; i <= tenor; i++) {
					calEnd.add(Calendar.MONTH, 1);
				}
				bean.setTbaloEndDate(simpleDateFormat.format(calEnd.getTime()));

				bean.setTbaloStatus(0);

				TbAssignedLoanManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
				
				SystemUtil.getInstance().notification(SystemUtil.CHANNEL_EMAIL, 3, SystemUtil.UI_LOAN, tbEmployeeBeanModel.getTbeId(), 0, new String[]{""});
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 108, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				beanModel.setTbaloCreatedTime(bean.getTbaloCreatedTime());
				beanModel.setTbaloUpdatedTime(new Date().getTime());

				bean = TbAssignedLoanManager.getInstance().toBean(beanModel, bean);

				TbAssignedLoanManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbAssignedLoanManager.getInstance().toBeanModel(bean);
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

	public ViewAssignedLoanBeanModel getAssignedLoan(int id) {
		ViewAssignedLoanBeanModel returnValue = new ViewAssignedLoanBeanModel();

		try {
			ViewAssignedLoanBean ViewAssignedLoanBean = ViewAssignedLoanManager.getInstance().loadByWhere("where tbalo_id = " + id)[0];

			if (ViewAssignedLoanBean != null) {
				returnValue = ViewAssignedLoanManager.getInstance().toBeanModel(ViewAssignedLoanBean);

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

	public ReturnBean deleteAssignedLoan(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 108, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbAssignedLoanManager.getInstance().deleteByWhere("where tbalo_id = " + id + " and tbalo_status = 0");

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

	public ReturnBean deleteBulkAssignedLoan(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 108, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbAssignedLoanManager.getInstance().deleteByWhere("where tbalo_id in (" + strId + ") and tbalo_status = 0");

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

	public PagingLoadResult<ViewAssignedLoanBeanModel> getAssignedLoanPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		List<ViewAssignedLoanBeanModel> list = new ArrayList<ViewAssignedLoanBeanModel>();

		ViewAssignedLoanBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 108, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_id = " + tbEmployeeBeanModel.getTbeId() + " ";

			if ("Loan Name".equals(searchBy)) {
				strWhere += "and tbalo_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Nominal".equals(searchBy)) {
				strWhere += "and tbalo_nominal like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Interest".equals(searchBy)) {
				strWhere += "and tbalo_interest like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Tenor".equals(searchBy)) {
				strWhere += "and tbalo_tenor like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Total Loan".equals(searchBy)) {
				strWhere += "and tbalo_nominal_total like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Start Date".equals(searchBy)) {
				strWhere += "and tbalo_start_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("End Date".equals(searchBy)) {
				strWhere += "and tbalo_end_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewAssignedLoanManager.getInstance().countAll();
			} else {
				size = ViewAssignedLoanManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewAssignedLoanManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewAssignedLoanBeanModel data = ViewAssignedLoanManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewAssignedLoanBeanModel data = new ViewAssignedLoanBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewAssignedLoanBeanModel>(list, config.getOffset(), size);
	}

	public TbAssignedLoanBeanModel getTbAssignedLoanAll() {
		TbAssignedLoanBeanModel returnValue = new TbAssignedLoanBeanModel();

		try {
			TbAssignedLoanBean tbAssignedLoanBeans[] = TbAssignedLoanManager.getInstance().loadAll();
			TbAssignedLoanBeanModel tbAssignedLoanBeanModels[] = TbAssignedLoanManager.getInstance().toBeanModels(tbAssignedLoanBeans);

			returnValue.setModels(tbAssignedLoanBeanModels);
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

	public PagingLoadResult<ViewAssignedLoanBeanModel> getApprovalLoanPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");

		List<ViewAssignedLoanBeanModel> list = new ArrayList<ViewAssignedLoanBeanModel>();

		ViewAssignedLoanBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 108, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where 1=1 ";
			
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere += "and tbe_id in (select tbe_id from tb_report_to where tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + ") ";
			}
			
			if ("Employee ID".equals(searchBy)) {
				strWhere += "and tbe_employee_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Employee Name".equals(searchBy)) {
				strWhere += "and tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Loan Name".equals(searchBy)) {
				strWhere += "and tbalo_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Nominal".equals(searchBy)) {
				strWhere += "and tbalo_nominal like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Interest".equals(searchBy)) {
				strWhere += "and tbalo_interest like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Tenor".equals(searchBy)) {
				strWhere += "and tbalo_tenor like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Total Loan".equals(searchBy)) {
				strWhere += "and tbalo_nominal_total like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Start Date".equals(searchBy)) {
				strWhere += "and tbalo_start_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("End Date".equals(searchBy)) {
				strWhere += "and tbalo_end_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewAssignedLoanManager.getInstance().countAll();
			} else {
				size = ViewAssignedLoanManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewAssignedLoanManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewAssignedLoanBeanModel data = ViewAssignedLoanManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewAssignedLoanBeanModel data = new ViewAssignedLoanBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewAssignedLoanBeanModel>(list, config.getOffset(), size);
	}

	public PagingLoadResult<ViewAssignedLoanBeanModel> getAssignedLoanPagingEss(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewAssignedLoanBeanModel> list = new ArrayList<ViewAssignedLoanBeanModel>();

		ViewAssignedLoanBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 108, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where " + searchBy + " = " + searchValue.replaceAll("'", "") + " and tbalo_status = 1 ";

			if ("".equals(strWhere)) {
				size = ViewAssignedLoanManager.getInstance().countAll();
			} else {
				size = ViewAssignedLoanManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewAssignedLoanManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewAssignedLoanBeanModel data = ViewAssignedLoanManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewAssignedLoanBeanModel data = new ViewAssignedLoanBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewAssignedLoanBeanModel>(list, config.getOffset(), size);
	}
}
