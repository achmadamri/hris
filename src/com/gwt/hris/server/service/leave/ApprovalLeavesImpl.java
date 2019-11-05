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
import com.gwt.hris.client.service.bean.ViewEmployeeLeavesBeanModel;
import com.gwt.hris.client.service.leave.ApprovalLeavesInterface;
import com.gwt.hris.cron.CronAttendance;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAssignedLeavesBean;
import com.gwt.hris.db.TbAssignedLeavesManager;
import com.gwt.hris.db.TbAttendanceBean;
import com.gwt.hris.db.TbAttendanceManager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbEmployeeShiftBean;
import com.gwt.hris.db.TbEmployeeShiftManager;
import com.gwt.hris.db.TbHolidayManager;
import com.gwt.hris.db.TbLeaveTypesBean;
import com.gwt.hris.db.TbLeaveTypesManager;
import com.gwt.hris.db.ViewEmployeeLeavesBean;
import com.gwt.hris.db.ViewEmployeeLeavesManager;
import com.gwt.hris.db.ViewEmployeeLeavesSummaryBean;
import com.gwt.hris.db.ViewEmployeeLeavesSummaryManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.server.service.QueryManager;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.DateUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ApprovalLeavesImpl extends MainRemoteServiceServlet implements ApprovalLeavesInterface {
	
	private static final long serialVersionUID = -691939291296534124L;

	public ReturnBean approvalAssignedLeaves(int tbaleId, int tbaleStatus) {
		ReturnBean returnValue = new ReturnBean();

		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 52, SystemUtil.ACCESS_APPROVE) == false) {
				throw new SystemException("No approval access");
			}

			Manager.getInstance().beginTransaction();

			String strWhere = "where tbale_id = " + tbaleId + " and tbe_id in (select tbe_id from tb_report_to where tbrt_reporting_method = 0 and tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + ") and tbale_status = 0";
			TbAssignedLeavesBean tbAssignedLeavesBeans[] = TbAssignedLeavesManager.getInstance().loadByWhere(strWhere);

			if (tbAssignedLeavesBeans.length > 0) {
				TbAssignedLeavesBean tbAssignedLeavesBean = tbAssignedLeavesBeans[0];
				
				TbEmployeeBean tbEmployeeBean = TbEmployeeManager.getInstance().loadByPrimaryKey(tbAssignedLeavesBean.getTbeId());

				if (tbaleStatus == 1) {
					TbLeaveTypesBean leaveTypesBean = TbLeaveTypesManager.getInstance().loadByPrimaryKey(tbAssignedLeavesBean.getTbltId());

					boolean approvalSuccess = false;
					
					if (leaveTypesBean.getTbltReduction() == 1) {
						int leaveAvailable = 0;
						ViewEmployeeLeavesSummaryBean viewEmployeeLeavesSummaryBeans[] = ViewEmployeeLeavesSummaryManager.getInstance().loadByWhere("where tbe_id = " + tbAssignedLeavesBean.getTbeId());
						if (viewEmployeeLeavesSummaryBeans.length > 0) {
							leaveAvailable = viewEmployeeLeavesSummaryBeans[0].getMinTbaleLeaveAvailable().intValue();
						}
						
						int leaveTaken = tbAssignedLeavesBean.getTbaleLeaveTaken().intValue();
						
						if (leaveAvailable >= leaveTaken) {
							tbAssignedLeavesBean.setTbaleUpdatedTime(new Date().getTime());
							tbAssignedLeavesBean.setTbaleStatus(tbaleStatus);
							TbAssignedLeavesManager.getInstance().save(tbAssignedLeavesBean);
							
							QueryManager.getInstance().approveTbAssignedLeaves(leaveAvailable - tbAssignedLeavesBean.getTbaleLeaveTaken(), tbAssignedLeavesBean.getTbeId());
							approvalSuccess = true;
						}
					} else {
						tbAssignedLeavesBean.setTbaleUpdatedTime(new Date().getTime());
						tbAssignedLeavesBean.setTbaleStatus(tbaleStatus);
						TbAssignedLeavesManager.getInstance().save(tbAssignedLeavesBean);
						
						approvalSuccess = true;
					}
					
					if (approvalSuccess == true) {
						DateUtil dateUtil = new DateUtil();
						int dateGap = dateUtil.countDaysBetween(tbAssignedLeavesBean.getTbaleStartDate(), tbAssignedLeavesBean.getTbaleEndDate());

						for (int i = 0; i <= dateGap; i++) {
							Calendar calStart = Calendar.getInstance();
							calStart.setTime(tbAssignedLeavesBean.getTbaleStartDate());
							calStart.add(Calendar.DATE, i);
							
							String strYear = calStart.get(Calendar.YEAR) + "-";
							int intMonth = calStart.get(Calendar.MONTH) + 1;
							String strMonth = intMonth < 10 ? "0" + intMonth + "-" : intMonth + "-";
							int intDate = calStart.get(Calendar.DATE);
							String strDate = intDate < 10 ? "0" + intDate : intDate + "";

							String strYearMonthDate = strYear + strMonth + strDate;

							TbAttendanceManager.getInstance().deleteByWhere("where tbe_id = " + tbAssignedLeavesBean.getTbeId() + " and tba_date = '" + strYearMonthDate + "'");

							TbLeaveTypesBean tbLeaveTypesBean = TbLeaveTypesManager.getInstance().loadByPrimaryKey(tbAssignedLeavesBean.getTbltId());
							TbAttendanceBean tbAttendanceBean = TbAttendanceManager.getInstance().createTbAttendanceBean();
							tbAttendanceBean.setTbeId(tbAssignedLeavesBean.getTbeId());
							tbAttendanceBean.setTbaDate(strYearMonthDate);
							tbAttendanceBean.setTbaInNote(tbLeaveTypesBean.getTbltName());
							tbAttendanceBean.setTbaOutNote(tbLeaveTypesBean.getTbltName());
							TbAttendanceManager.getInstance().save(tbAttendanceBean);

							TbEmployeeShiftManager.getInstance().deleteByWhere("where tbe_id = " + tbAssignedLeavesBean.getTbeId() + " and tbes_date = '" + strYearMonthDate + "'");
							
							TbEmployeeShiftBean tbEmployeeShiftBean = TbEmployeeShiftManager.getInstance().createTbEmployeeShiftBean();
							tbEmployeeShiftBean.setTbeId(tbAssignedLeavesBean.getTbeId());
							if (tbAssignedLeavesBean.getTbltId().intValue() == 1 || tbAssignedLeavesBean.getTbltId().intValue() == 3) {
								tbEmployeeShiftBean.setTbsId(1);
							} else {
								tbEmployeeShiftBean.setTbsId(2);
							}
							tbEmployeeShiftBean.setTbesDate(strYearMonthDate);
							TbEmployeeShiftManager.getInstance().save(tbEmployeeShiftBean);
						}
						
						CronAttendance cronAttendance = new CronAttendance();
						cronAttendance.createReport(tbEmployeeBean);
						cronAttendance.createAttendance(tbEmployeeBean);

						returnValue.setOperationStatus(true);
						returnValue.setMessage("Success Approve");
					} else {
						returnValue.setOperationStatus(false);
						returnValue.setMessage("Fail Approve");
					}
				} else {
					tbAssignedLeavesBean.setTbaleUpdatedTime(new Date().getTime());
					tbAssignedLeavesBean.setTbaleStatus(tbaleStatus);
					TbAssignedLeavesManager.getInstance().save(tbAssignedLeavesBean);
					
					returnValue.setOperationStatus(true);
					returnValue.setMessage("Success Reject");
				}
				
				SystemUtil.getInstance().notification(SystemUtil.CHANNEL_EMAIL, 1, SystemUtil.UI_LEAVE_LIST, tbEmployeeBeanModel.getTbeId(), tbEmployeeBean.getTbeId(), new String[]{""});
				
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

	public PagingLoadResult<ViewEmployeeLeavesBeanModel> getTbAssignedLeavesPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		List<ViewEmployeeLeavesBeanModel> list = new ArrayList<ViewEmployeeLeavesBeanModel>();

		ViewEmployeeLeavesBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 52, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_id in (select tbe_id from tb_report_to where tbrt_reporting_method = 0 and tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + ") and (tbale_start_date is not null and tbale_end_date is not null)";

			if ("Employee Name".equals(searchBy)) {
				strWhere += "and tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Leave Types".equals(searchBy)) {
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

	public BaseTreeModel getLeaveCount(int tbaleId, Date dateStart, Date dateEnd, int intType) {
		BaseTreeModel returnValue = new BaseTreeModel();

		try {
			TbAssignedLeavesBean tbAssignedLeavesBean = TbAssignedLeavesManager.getInstance().loadByPrimaryKey(tbaleId);
			
			int leaveEntitled = 0;
			int leaveAvailable = 0;

			ViewEmployeeLeavesSummaryBean viewEmployeeLeavesSummaryBeans[] = ViewEmployeeLeavesSummaryManager.getInstance().loadByWhere("where tbe_id = " + tbAssignedLeavesBean.getTbeId());
			if (viewEmployeeLeavesSummaryBeans.length > 0) {
				leaveEntitled = viewEmployeeLeavesSummaryBeans[0].getTbjtLeaveEntitled();
				leaveAvailable = viewEmployeeLeavesSummaryBeans[0].getMinTbaleLeaveAvailable().intValue();
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
