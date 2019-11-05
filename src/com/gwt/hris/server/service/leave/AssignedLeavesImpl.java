package com.gwt.hris.server.service.leave;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLeavesBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLeaveTypesBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLeavesBeanModel;
import com.gwt.hris.client.service.leave.AssignedLeavesInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAssignedLeavesBean;
import com.gwt.hris.db.TbAssignedLeavesManager;
import com.gwt.hris.db.TbHolidayManager;
import com.gwt.hris.db.TbLeaveTypesBean;
import com.gwt.hris.db.TbLeaveTypesManager;
import com.gwt.hris.db.ViewEmployeeLeavesBean;
import com.gwt.hris.db.ViewEmployeeLeavesManager;
import com.gwt.hris.db.ViewEmployeeLeavesSummaryBean;
import com.gwt.hris.db.ViewEmployeeLeavesSummaryManager;
import com.gwt.hris.db.ViewJobTitleLeaveBean;
import com.gwt.hris.db.ViewJobTitleLeaveManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.DateUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class AssignedLeavesImpl extends MainRemoteServiceServlet implements AssignedLeavesInterface {
	
	private static final long serialVersionUID = -691939291296534124L;

	public ReturnBean submitAssignedLeaves(TbAssignedLeavesBeanModel tbAssignedLeavesBeanModel) {
		ReturnBean returnValue = new ReturnBean();
		ViewEmployeeInformationBeanModel viewEmployeeInformationBeanModel = (ViewEmployeeInformationBeanModel) this.getThreadLocalRequest().getSession().getAttribute("ViewEmployeeInformationBeanModel");

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbAssignedLeavesBean tbAssignedLeavesBean = null;

			if (tbAssignedLeavesBeanModel.getTbaleId() != null) {
				tbAssignedLeavesBean = TbAssignedLeavesManager.getInstance().loadByPrimaryKey(tbAssignedLeavesBeanModel.getTbaleId());
			}

			boolean isEdit = false;
			if (tbAssignedLeavesBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 103, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				tbAssignedLeavesBean = TbAssignedLeavesManager.getInstance().createTbAssignedLeavesBean();

				tbAssignedLeavesBean = TbAssignedLeavesManager.getInstance().toBean(tbAssignedLeavesBeanModel, tbAssignedLeavesBean);
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 53, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				tbAssignedLeavesBean = TbAssignedLeavesManager.getInstance().toBean(tbAssignedLeavesBeanModel, tbAssignedLeavesBean);
				isEdit = true;
			}

			BaseTreeModel baseTreeModel = getLeaveCount(tbAssignedLeavesBean.getTbaleStartDate(), tbAssignedLeavesBean.getTbaleEndDate(), 1, tbAssignedLeavesBean.getTbaleId() == null ? 0 : tbAssignedLeavesBean.getTbaleId());

			boolean leaveValid = false;
			if (((Integer) baseTreeModel.get("leaveAvailable")) >= 0) {
				if (baseTreeModel.get("leaveTaken") != null) {
					if (((Integer) baseTreeModel.get("leaveTaken")) >= 0) {
						int leaveTaken = (Integer) baseTreeModel.get("leaveTaken");
						
						ViewJobTitleLeaveBean viewJobTitleLeaveBeans[] = ViewJobTitleLeaveManager.getInstance().loadByWhere("where tbjt_id = " + viewEmployeeInformationBeanModel.getTbjtId() + " and tblt_id = " + tbAssignedLeavesBeanModel.getTbltId() + " and tbjtl_min <= " + leaveTaken + " order by tbjtl_min asc");
						for (ViewJobTitleLeaveBean viewJobTitleLeaveBean : viewJobTitleLeaveBeans) {
							DateUtil dateUtil = new DateUtil();
							int priorDate = dateUtil.countDaysBetween(new Date(), tbAssignedLeavesBean.getTbaleStartDate());
							
							if ((priorDate + 1) >= viewJobTitleLeaveBean.getTbjtlPriorDate()) {
								leaveValid = true;
							} else {
								leaveValid = false;
								returnValue.setMessage("Prior date must be " + viewJobTitleLeaveBean.getTbjtlPriorDate() + " day(s)");
							}
						}
					}
				}
			}

			if (leaveValid) {
				TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
				tbAssignedLeavesBean.setTbeId(tbEmployeeBeanModel.getTbeId());
				tbAssignedLeavesBean.setTbaleCreatedTime(new Date().getTime());
				tbAssignedLeavesBean.setTbaleLeaveTaken((Integer) baseTreeModel.get("leaveTaken"));
				tbAssignedLeavesBean.setTbaleStatus(0);

				TbAssignedLeavesManager.getInstance().save(tbAssignedLeavesBean);

				returnValue.setOperationStatus(true);

				if (isEdit) {
					returnValue.setMessage("Success Updated");
				} else {
					returnValue.setMessage("Success Saved");
					
					SystemUtil.getInstance().notification(SystemUtil.CHANNEL_EMAIL, 1, SystemUtil.UI_APPROVAL_LEAVE, tbEmployeeBeanModel.getTbeId(), 0, new String[]{""});
				}
			} else {
				returnValue.setOperationStatus(false);
				if (returnValue.getMessage() == null) {
					if (isEdit) {
						returnValue.setMessage("Fail Updated");
					} else {
						returnValue.setMessage("Fail Saved");
					}
				}
			}

			tbAssignedLeavesBeanModel = TbAssignedLeavesManager.getInstance().toBeanModel(tbAssignedLeavesBean);
			returnValue.set("model", tbAssignedLeavesBeanModel);

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

	public TbAssignedLeavesBeanModel getAssignedLeaves(int tblId) {
		TbAssignedLeavesBeanModel returnValue = new TbAssignedLeavesBeanModel();

		try {
			TbAssignedLeavesBean tbAssignedLeavesBean = TbAssignedLeavesManager.getInstance().loadByPrimaryKey(tblId);

			if (tbAssignedLeavesBean != null) {
				returnValue = TbAssignedLeavesManager.getInstance().toBeanModel(tbAssignedLeavesBean);

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

	public ReturnBean deleteAssignedLeaves(Integer tblId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 103, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbAssignedLeavesBean tbAssignedLeavesBean = TbAssignedLeavesManager.getInstance().loadByPrimaryKey(tblId);

			if (tbAssignedLeavesBean.getTbaleStatus() == 0) {
				TbAssignedLeavesManager.getInstance().deleteByPrimaryKey(tblId);

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

	public ReturnBean deleteBulkAssignedLeaves(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 103, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);

			String strWhere = "where tbale_id in (" + strId + ") and tbale_status = 0";
			TbAssignedLeavesBean tbAssignedLeavesBeans[] = TbAssignedLeavesManager.getInstance().loadByWhere(strWhere);

			if (tbAssignedLeavesBeans.length == ids.length) {
				TbAssignedLeavesManager.getInstance().deleteByWhere(strWhere);

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

	public PagingLoadResult<ViewEmployeeLeavesBeanModel> getTbAssignedLeavesPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		List<ViewEmployeeLeavesBeanModel> list = new ArrayList<ViewEmployeeLeavesBeanModel>();

		ViewEmployeeLeavesBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 103, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_id = " + tbEmployeeBeanModel.getTbeId() + " and (tbale_start_date is not null and tbale_end_date is not null) ";

			if ("Leave Types".equals(searchBy)) {
				strWhere += "and tblt_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Start Date".equals(searchBy)) {
				strWhere += "and tbale_start_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("End Date".equals(searchBy)) {
				strWhere += "and tbale_end_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Leave Taken".equals(searchBy)) {
				strWhere += "and tbale_leave_taken like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Approval Status".equals(searchBy)) {
				strWhere += "and tbale_status like '%" + ("pending".equalsIgnoreCase(searchValue) ? "0" : "approved".equalsIgnoreCase(searchValue) ? "1" : "2") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewEmployeeLeavesManager.getInstance().countAll();
			} else {
				size = ViewEmployeeLeavesManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeLeavesManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeLeavesBeanModel data = ViewEmployeeLeavesManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeLeavesBeanModel data = new ViewEmployeeLeavesBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeLeavesBeanModel>(list, config.getOffset(), size);
	}

	public TbLeaveTypesBeanModel getTbLeaveTypesAll() {
		TbLeaveTypesBeanModel returnValue = new TbLeaveTypesBeanModel();

		try {
			TbLeaveTypesBean tbLeaveTypesBeans[] = TbLeaveTypesManager.getInstance().loadAll();
			TbLeaveTypesBeanModel tbLeaveTypesBeanModels[] = TbLeaveTypesManager.getInstance().toBeanModels(tbLeaveTypesBeans);

			returnValue.setModels(tbLeaveTypesBeanModels);
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

	public BaseTreeModel getLeaveCount(Date dateStart, Date dateEnd, int intType, int intTbaleId) {
		BaseTreeModel returnValue = new BaseTreeModel();

		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		try {
			TbAssignedLeavesBean tbAssignedLeavesBean = TbAssignedLeavesManager.getInstance().loadByPrimaryKey(intTbaleId);
			
			int leaveEntitled = 0;
			int leaveAvailable = 0;
			int leaveTaken = 0;

			ViewEmployeeLeavesSummaryBean viewEmployeeLeavesSummaryBeans[] = ViewEmployeeLeavesSummaryManager.getInstance().loadByWhere("where tbe_id = " + tbEmployeeBeanModel.getTbeId());
			if (viewEmployeeLeavesSummaryBeans.length > 0) {
				leaveEntitled = viewEmployeeLeavesSummaryBeans[0].getTbjtLeaveEntitled() == null ? 0 : viewEmployeeLeavesSummaryBeans[0].getTbjtLeaveEntitled();
				leaveAvailable = viewEmployeeLeavesSummaryBeans[0].getMinTbaleLeaveAvailable() == null ? 0 : viewEmployeeLeavesSummaryBeans[0].getMinTbaleLeaveAvailable();
			}
			
			int dateGap = 0;

			if (dateStart != null && dateEnd != null && intType == 1) {
				DateUtil dateUtil = new DateUtil();
				dateGap = dateUtil.countDaysBetween(dateStart, dateEnd);

				if (dateGap == 0 || dateGap > 0) {
					dateGap = dateGap + 1;
				}

				int iloop = dateGap;
				for (int i = 0; i < iloop; i++) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateStart);
					cal.add(Calendar.DATE, i);
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					String strWhere = "where DATE_FORMAT(tbh_date, '%Y/%m/%d') = '" + df.format(cal.getTime()) + "'";
					int intHolidayCount = TbHolidayManager.getInstance().countWhere(strWhere);

					df = new SimpleDateFormat("MM/dd");
					strWhere = "where DATE_FORMAT(tbh_date, '%m/%d') = '" + df.format(cal.getTime()) + "'";
					int intHolidayCountAnnually = TbHolidayManager.getInstance().countWhere(strWhere);
					
					if (cal.get(Calendar.DAY_OF_WEEK) == 7 || cal.get(Calendar.DAY_OF_WEEK) == 1 || intHolidayCount > 0 || intHolidayCountAnnually > 0) {
						dateGap = dateGap - 1;
					}
				}
			} else {
				if (dateStart != null && dateEnd != null) {
					DateUtil dateUtil = new DateUtil();
					dateGap = dateUtil.countDaysBetween(dateStart, dateEnd);

					if (dateGap == 0 || dateGap > 0) {
						dateGap = dateGap + 1;
					}

					int iloop = dateGap;
					for (int i = 0; i < iloop; i++) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(dateStart);
						cal.add(Calendar.DATE, i);
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
						String strWhere = "where DATE_FORMAT(tbh_date, '%Y/%m/%d') = '" + df.format(cal.getTime()) + "'";
						int intHolidayCount = TbHolidayManager.getInstance().countWhere(strWhere);

						df = new SimpleDateFormat("MM/dd");
						strWhere = "where DATE_FORMAT(tbh_date, '%m/%d') = '" + df.format(cal.getTime()) + "'";
						int intHolidayCountAnnually = TbHolidayManager.getInstance().countWhere(strWhere);
						
						if (cal.get(Calendar.DAY_OF_WEEK) == 7 || cal.get(Calendar.DAY_OF_WEEK) == 1 || intHolidayCount > 0 || intHolidayCountAnnually > 0) {
							dateGap = dateGap - 1;
						}
					}
				}
			}
			
			if (intTbaleId != 0 && tbAssignedLeavesBean.getTbaleStatus() != 0) {
				leaveAvailable = leaveAvailable - dateGap;
				leaveTaken = leaveTaken + dateGap;				
			}

			returnValue.set("leaveEntitled", leaveEntitled);
			returnValue.set("leaveAvailable", leaveAvailable);
			returnValue.set("leaveTaken", dateGap);

			return returnValue;
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();

			return null;
		}
	}
}
