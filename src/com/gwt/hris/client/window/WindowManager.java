
package com.gwt.hris.client.window;

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.MainInterface;
import com.gwt.hris.client.service.MainInterfaceAsync;
import com.gwt.hris.client.window.admin.companyinfo.WindowCompanyProperty;
import com.gwt.hris.client.window.admin.companyinfo.WindowCompanyStructure;
import com.gwt.hris.client.window.admin.companyinfo.WindowLocations;
import com.gwt.hris.client.window.admin.companyinfo.WindowPerusahaan;
import com.gwt.hris.client.window.admin.job.WindowEeoJobCategory;
import com.gwt.hris.client.window.admin.job.WindowEmploymentStatus;
import com.gwt.hris.client.window.admin.job.WindowJobSpecifications;
import com.gwt.hris.client.window.admin.job.WindowJobTitle;
import com.gwt.hris.client.window.admin.job.WindowPaygrade;
import com.gwt.hris.client.window.admin.job.WindowRenumeration;
import com.gwt.hris.client.window.admin.memberships.WindowMembership;
import com.gwt.hris.client.window.admin.memberships.WindowMembershipType;
import com.gwt.hris.client.window.admin.nationalityandraces.WindowEthnicRaces;
import com.gwt.hris.client.window.admin.nationalityandraces.WindowNationality;
import com.gwt.hris.client.window.admin.projectinfo.WindowBudgetResourcesGroup;
import com.gwt.hris.client.window.admin.projectinfo.WindowCustomers;
import com.gwt.hris.client.window.admin.projectinfo.WindowProject;
import com.gwt.hris.client.window.admin.projectinfo.WindowProjectActivities;
import com.gwt.hris.client.window.admin.projectinfo.WindowProjectActivitiesGroup;
import com.gwt.hris.client.window.admin.qualification.WindowEducation;
import com.gwt.hris.client.window.admin.qualification.WindowLicenses;
import com.gwt.hris.client.window.admin.skills.WindowLanguage;
import com.gwt.hris.client.window.admin.skills.WindowSkills;
import com.gwt.hris.client.window.admin.systemsettings.WindowCron;
import com.gwt.hris.client.window.admin.systemsettings.WindowCurrency;
import com.gwt.hris.client.window.admin.systemsettings.WindowMenu;
import com.gwt.hris.client.window.admin.systemsettings.WindowNotificationTemplate;
import com.gwt.hris.client.window.admin.time.WindowAdminAttendance;
import com.gwt.hris.client.window.admin.time.WindowEmployeeShift;
import com.gwt.hris.client.window.admin.time.WindowShift;
import com.gwt.hris.client.window.admin.users.WindowAdminUserGroups;
import com.gwt.hris.client.window.admin.users.WindowESSUsers;
import com.gwt.hris.client.window.admin.users.WindowHRAdminUsers;
import com.gwt.hris.client.window.leave.WindowApprovalLeaves;
import com.gwt.hris.client.window.leave.WindowAssignedLeaves;
import com.gwt.hris.client.window.leave.WindowEmployeeLeavesSummary;
import com.gwt.hris.client.window.leave.WindowHoliday;
import com.gwt.hris.client.window.leave.WindowLeaveTypes;
import com.gwt.hris.client.window.loan.WindowApprovalLoan;
import com.gwt.hris.client.window.loan.WindowAssignedLoan;
import com.gwt.hris.client.window.performance.WindowKPI;
import com.gwt.hris.client.window.performance.WindowKPIApprovalAssign;
import com.gwt.hris.client.window.performance.WindowKPIApprovalScoring;
import com.gwt.hris.client.window.performance.WindowKPIAssign;
import com.gwt.hris.client.window.performance.WindowKPIEmployee;
import com.gwt.hris.client.window.performance.WindowKPIGroup;
import com.gwt.hris.client.window.pim.WindowEmployeeList;
import com.gwt.hris.client.window.pim.WindowEmployeeListAddEdit;
import com.gwt.hris.client.window.pim.WindowEss;
import com.gwt.hris.client.window.recruitment.WindowApplicants;
import com.gwt.hris.client.window.recruitment.WindowVacancies;
import com.gwt.hris.client.window.reports.WindowEmployeeAttendance;
import com.gwt.hris.client.window.reports.WindowEmployeeInformation;
import com.gwt.hris.client.window.reports.WindowEmployeePayroll;
import com.gwt.hris.client.window.reports.WindowEmployeePph;
import com.gwt.hris.client.window.reports.WindowEmployeeTimesheet;
import com.gwt.hris.client.window.system.WindowChangePassword;
import com.gwt.hris.client.window.tax.WindowEmployeePTT;
import com.gwt.hris.client.window.tax.WindowPTKP;
import com.gwt.hris.client.window.tax.WindowPTT;
import com.gwt.hris.client.window.tax.WindowTaxRate;
import com.gwt.hris.client.window.time.attendance.WindowAttendance;
import com.gwt.hris.client.window.time.attendance.WindowPunchInOutAddEdit;
import com.gwt.hris.client.window.time.timesheets.WindowApprovalTimesheet;
import com.gwt.hris.client.window.time.timesheets.WindowProjectTimesheet;
import com.gwt.hris.client.window.time.timesheets.WindowTimesheet;

public class WindowManager {
	public void showWindow(String strName) {
		if (strName.equals("Test Window")) {
			WindowTest window = WindowTest.getInstance();
			window.show(false);
		} else if (strName.equals("Logout")) {
			MessageBox.confirm("Confirmation", "Are you sure you want to logut ?", logoutListener);
		} else if (strName.equals("Company")) {
			WindowPerusahaan window = WindowPerusahaan.getInstance();
			window.show(false);
		} else if (strName.equals("Locations")) {
			WindowLocations window = WindowLocations.getInstance();
			window.show(false);
		} else if (strName.equals("Company Structure")) {
			WindowCompanyStructure window = WindowCompanyStructure.getInstance();
			window.show(false);
		} else if (strName.equals("Company Property")) {
			WindowCompanyProperty window = WindowCompanyProperty.getInstance();
			window.show(false);
		} else if (strName.equals("Salary Grades")) {
			WindowPaygrade window = WindowPaygrade.getInstance();
			window.show(false);
		} else if (strName.equals("Job Specifications")) {
			WindowJobSpecifications window = WindowJobSpecifications.getInstance();
			window.show(false);
		} else if (strName.equals("Employment Status")) {
			WindowEmploymentStatus window = WindowEmploymentStatus.getInstance();
			window.show(false);
		} else if (strName.equals("Job Titles")) {
			WindowJobTitle window = WindowJobTitle.getInstance();
			window.show(false);
		} else if (strName.equals("EEO Job Categories")) {
			WindowEeoJobCategory window = WindowEeoJobCategory.getInstance();
			window.show(false);
		} else if (strName.equals("Education")) {
			WindowEducation window = WindowEducation.getInstance();
			window.show(false);
		} else if (strName.equals("Licenses")) {
			WindowLicenses window = WindowLicenses.getInstance();
			window.show(false);
		} else if (strName.equals("Skills")) {
			WindowSkills window = WindowSkills.getInstance();
			window.show(false);
		} else if (strName.equals("Languages")) {
			WindowLanguage window = WindowLanguage.getInstance();
			window.show(false);
		} else if (strName.equals("Membership Types")) {
			WindowMembershipType window = WindowMembershipType.getInstance();
			window.show(false);
		} else if (strName.equals("Memberships")) {
			WindowMembership window = WindowMembership.getInstance();
			window.show(false);
		} else if (strName.equals("Nationality")) {
			WindowNationality window = WindowNationality.getInstance();
			window.show(false);
		} else if (strName.equals("Ethnic Races")) {
			WindowEthnicRaces window = WindowEthnicRaces.getInstance();
			window.show(false);
		} else if (strName.equals("Admin User Groups")) {
			WindowAdminUserGroups window = WindowAdminUserGroups.getInstance();
			window.show(false);
		} else if (strName.equals("HR Admin Users")) {
			WindowHRAdminUsers window = WindowHRAdminUsers.getInstance();
			window.show(false);
		} else if (strName.equals("ESS Users")) {
			WindowESSUsers window = WindowESSUsers.getInstance();
			window.show(false);
		} else if (strName.equals("Customers")) {
			WindowCustomers window = WindowCustomers.getInstance();
			window.show(false);
		} else if (strName.equals("Projects")) {
			WindowProject window = WindowProject.getInstance();
			window.show(false);
		} else if (strName.equals("Activity Groups")) {
			WindowProjectActivitiesGroup window = WindowProjectActivitiesGroup.getInstance();
			window.show(false);
		} else if (strName.equals("Activities")) {
			WindowProjectActivities window = WindowProjectActivities.getInstance();
			window.show(false);
		} else if (strName.equals("Employee List")) {
			WindowEmployeeList window = WindowEmployeeList.getInstance();
			window.show(false);
		} else if (strName.equals("Add Employee")) {
			WindowEmployeeListAddEdit window = WindowEmployeeListAddEdit.getInstance();
			window.show("add", 0);
		} else if (strName.equals("ESS")) {
			mainInterfaceAsync.getTbeId(new AsyncCallback<Integer>() {
				@Override
				public void onSuccess(Integer result) {
					WindowEss window = WindowEss.getInstance();
					window.show(result, 0);
				}

				@Override
				public void onFailure(Throwable caught) {
					MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

					caught.printStackTrace();
				}
			});
		} else if (strName.equals("Holidays")) {
			WindowHoliday window = WindowHoliday.getInstance();
			window.show(false);
		} else if (strName.equals("Leave Types")) {
			WindowLeaveTypes window = WindowLeaveTypes.getInstance();
			window.show(false);
		} else if (strName.equals("Menu")) {
			WindowMenu window = WindowMenu.getInstance();
			window.show(false);
		} else if (strName.equals("Leave List")) {
			WindowAssignedLeaves window = WindowAssignedLeaves.getInstance();
			window.show(false);
		} else if (strName.equals("Approval Leave")) {
			WindowApprovalLeaves window = WindowApprovalLeaves.getInstance();
			window.show(false);
		} else if (strName.equals("Employee Leave Summary")) {
			WindowEmployeeLeavesSummary window = WindowEmployeeLeavesSummary.getInstance();
			window.show(false);
		} else if (strName.equals("Timesheet")) {
			WindowTimesheet window = WindowTimesheet.getInstance();
			window.show(false);
		} else if (strName.equals("Approval Timesheet")) {
			WindowApprovalTimesheet window = WindowApprovalTimesheet.getInstance();
			window.show(false);
		} else if (strName.equals("Project Timesheet")) {
			WindowProjectTimesheet window = WindowProjectTimesheet.getInstance();
			window.show(false);
		} else if (strName.equals("Attendance")) {
			WindowAttendance window = WindowAttendance.getInstance();
			window.show(false);
		} else if (strName.equals("Punch In/Out")) {
			WindowPunchInOutAddEdit window = WindowPunchInOutAddEdit.getInstance();
			window.show(false);
		} else if (strName.equals("Shifts")) {
			WindowShift window = WindowShift.getInstance();
			window.show(false);
		} else if (strName.equals("Employee Shift")) {
			WindowEmployeeShift window = WindowEmployeeShift.getInstance();
			window.show(false);
		} else if (strName.equals("Renumeration")) {
			WindowRenumeration window = WindowRenumeration.getInstance();
			window.show(false);
		} else if (strName.equals("Loan List")) {
			WindowAssignedLoan window = WindowAssignedLoan.getInstance();
			window.show(false);
		} else if (strName.equals("Approval Loan")) {
			WindowApprovalLoan window = WindowApprovalLoan.getInstance();
			window.show(false);
		} else if (strName.equals("KPI Groups")) {
			WindowKPIGroup window = WindowKPIGroup.getInstance();
			window.show(false);
		} else if (strName.equals("KPI")) {
			WindowKPI window = WindowKPI.getInstance();
			window.show(false);
		} else if (strName.equals("KPI Assign")) {
			WindowKPIAssign window = WindowKPIAssign.getInstance();
			window.show(false);
		} else if (strName.equals("KPI Approval Assign")) {
			WindowKPIApprovalAssign window = WindowKPIApprovalAssign.getInstance();
			window.show(false);
		} else if (strName.equals("KPI Approval Scoring")) {
			WindowKPIApprovalScoring window = WindowKPIApprovalScoring.getInstance();
			window.show(false);
		} else if (strName.equals("KPI Employee")) {
			WindowKPIEmployee window = WindowKPIEmployee.getInstance();
			window.show(false);
		} else if (strName.equals("Employee Information")) {
			WindowEmployeeInformation window = WindowEmployeeInformation.getInstance();
			window.show(false);
		} else if (strName.equals("PTKP")) {
			WindowPTKP window = WindowPTKP.getInstance();
			window.show(false);
		} else if (strName.equals("Tax Rate")) {
			WindowTaxRate window = WindowTaxRate.getInstance();
			window.show(false);
		} else if (strName.equals("PTT")) {
			WindowPTT window = WindowPTT.getInstance();
			window.show(false);
		} else if (strName.equals("Employee PTT")) {
			WindowEmployeePTT window = WindowEmployeePTT.getInstance();
			window.show(false);
		} else if (strName.equals("Currency")) {
			WindowCurrency window = WindowCurrency.getInstance();
			window.show(false);
		} else if (strName.equals("Employee Attendance")) {
			WindowEmployeeAttendance window = WindowEmployeeAttendance.getInstance();
			window.show(false);
		} else if (strName.equals("Change Password")) {
			WindowChangePassword window = WindowChangePassword.getInstance();
			window.show(false);
		} else if (strName.equals("Vacancies")) {
			WindowVacancies window = WindowVacancies.getInstance();
			window.show(false);
		} else if (strName.equals("Applicants")) {
			WindowApplicants window = WindowApplicants.getInstance();
			window.show(false);
		} else if (strName.equals("Budget Resources Group")) {
			WindowBudgetResourcesGroup window = WindowBudgetResourcesGroup.getInstance();
			window.show(false);
		} else if (strName.equals("Cron Job")) {
			WindowCron window = WindowCron.getInstance();
			window.show(false);
		} else if (strName.equals("Employee Pph")) {
			WindowEmployeePph window = WindowEmployeePph.getInstance();
			window.show(false);
		} else if (strName.equals("Employee Timesheet")) {
			WindowEmployeeTimesheet window = WindowEmployeeTimesheet.getInstance();
			window.show(false);
		} else if (strName.equals("Employee Payroll")) {
			WindowEmployeePayroll window = WindowEmployeePayroll.getInstance();
			window.show(false);
		} else if (strName.equals("Notification Template")) {
			WindowNotificationTemplate window = WindowNotificationTemplate.getInstance();
			window.show(false);
		} else if (strName.equals("Admin Attendance")) {
			WindowAdminAttendance window = WindowAdminAttendance.getInstance();
			window.show(false);
		}
	}

	MainInterfaceAsync mainInterfaceAsync = GWT.create(MainInterface.class);

	AsyncCallback<Void> logoutCallback = new AsyncCallback<Void>() {
		@Override
		public void onSuccess(Void result) {
			Window.Location.reload();
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};

	Listener<MessageBoxEvent> logoutListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				mainInterfaceAsync.doLogout(logoutCallback);
			}
		}
	};
}
