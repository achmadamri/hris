package com.gwt.hris.client.window.pim;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.window.WindowMain;

public class WindowEmployeeListEss extends WindowMain {
	private static WindowEmployeeListEss instance = new WindowEmployeeListEss();

	public static WindowEmployeeListEss getInstance() {
		return instance;
	}

	public WindowEmployeeListEss() {
		window = new Window();
		window.setId("WindowEmployeeListEss");
		window.setSize(740, 550);
		window.setHeading("PIM : Employee List");
	}

	public void show(int id, int callerId) {
		this.id = id;
		this.callerId = callerId;

		employeeListInterfaceAsync.getEmployee(id, getCallback);
	}
	
	EmployeeListInterfaceAsync employeeListInterfaceAsync = GWT.create(EmployeeListInterface.class);

	public int id = 0;
	public int callerId = 0;
	public TbLoginBeanModel tbLoginBeanModel = null;

	AsyncCallback<TbEmployeeBeanModel> getCallback = new AsyncCallback<TbEmployeeBeanModel>() {
		@Override
		public void onSuccess(TbEmployeeBeanModel result) {
			if (result.getOperationStatus()) {
				window.setHeading("ESS : " + result.getTbeName());
				
				tbLoginBeanModel = result.get("tbLoginBeanModel");
				
				if (WindowEmployeeListEss.getInstance().callerId == 0) {
					show(false);
				} else {
					show(true);
				}
			} else {
				MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};

	@Override
	public void addComponents() {
		// Start tabPanel
		TabPanel tabPanel = new TabPanel();
		tabPanel.setTabScroll(true);

		// Start tabItemPersonal
		TabItem tabItemPersonal = new TabItem("Personal");
		tabItemPersonal.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemPersonal.setLayout(new FillLayout());

		TabPanel tabPanelPersonal = new TabPanel();
		tabPanelPersonal.setTabScroll(true);

		final TabItem tabItemPersonalDetails = new TabItem("Personal Details");
		tabItemPersonalDetails.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemPersonalDetails.setLayout(new FillLayout());
		tabItemPersonalDetails.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemPersonalDetails.removeAll();
				tabItemPersonalDetails.add(new EssPersonalDetails(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemPersonalDetails);

		final TabItem tabItemContactDetails = new TabItem("Contact Details");
		tabItemContactDetails.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemContactDetails.setLayout(new FillLayout());
		tabItemContactDetails.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemContactDetails.removeAll();
				tabItemContactDetails.add(new EssContactDetails(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemContactDetails);

		final TabItem tabItemEmergencyContacts = new TabItem("Emergency Contacts");
		tabItemEmergencyContacts.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemEmergencyContacts.setLayout(new FitLayout());
		tabItemEmergencyContacts.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemEmergencyContacts.removeAll();
				tabItemEmergencyContacts.add(new EssEmergencyContacts(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemEmergencyContacts);

		final TabItem tabItemDependents = new TabItem("Dependents");
		tabItemDependents.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemDependents.setLayout(new FitLayout());
		tabItemDependents.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemDependents.removeAll();
				tabItemDependents.add(new EssDependents(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemDependents);

		final TabItem tabItemImmigration = new TabItem("Immigration");
		tabItemImmigration.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemImmigration.setLayout(new FillLayout());
		tabItemImmigration.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemImmigration.removeAll();
				tabItemImmigration.add(new EssImmigration(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemImmigration);
		
		final TabItem tabItemWorkExperience = new TabItem("Work Experience");
		tabItemWorkExperience.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemWorkExperience.setLayout(new FillLayout());
		tabItemWorkExperience.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemWorkExperience.removeAll();
				tabItemWorkExperience.add(new EssWorkExperience(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemWorkExperience);

		final TabItem tabItemAssignedEducation = new TabItem("Education");
		tabItemAssignedEducation.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemAssignedEducation.setLayout(new FillLayout());
		tabItemAssignedEducation.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemAssignedEducation.removeAll();
				tabItemAssignedEducation.add(new EssAssignedEducation(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemAssignedEducation);

		final TabItem tabItemAssignedSkills = new TabItem("Skills");
		tabItemAssignedSkills.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemAssignedSkills.setLayout(new FillLayout());
		tabItemAssignedSkills.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemAssignedSkills.removeAll();
				tabItemAssignedSkills.add(new EssAssignedSkills(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemAssignedSkills);

		final TabItem tabItemAssignedLanguage = new TabItem("Languages");
		tabItemAssignedLanguage.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemAssignedLanguage.setLayout(new FillLayout());
		tabItemAssignedLanguage.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemAssignedLanguage.removeAll();
				tabItemAssignedLanguage.add(new EssAssignedLanguages(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemAssignedLanguage);

		final TabItem tabItemAssignedLicenses = new TabItem("Licenses");
		tabItemAssignedLicenses.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemAssignedLicenses.setLayout(new FillLayout());
		tabItemAssignedLicenses.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemAssignedLicenses.removeAll();
				tabItemAssignedLicenses.add(new EssAssignedLicenses(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemAssignedLicenses);

		final TabItem tabItemPhotograph = new TabItem("Photograph");
		tabItemPhotograph.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemPhotograph.setLayout(new FillLayout());
		tabItemPhotograph.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemPhotograph.removeAll();
				tabItemPhotograph.add(new EssPhotograph(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelPersonal.add(tabItemPhotograph);

		tabItemPersonal.add(tabPanelPersonal);

		tabPanel.add(tabItemPersonal);
		// End tabItemPersonal

		// Start tabItemEmployment
		TabItem tabItemEmployment = new TabItem("Employment");
		tabItemEmployment.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemEmployment.setLayout(new FillLayout());

		TabPanel tabPanelEmployment = new TabPanel();
		tabPanelEmployment.setTabScroll(true);

		final TabItem tabItemJob = new TabItem("Job");
		tabItemJob.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemJob.setLayout(new FillLayout());
		tabItemJob.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemJob.removeAll();
				tabItemJob.add(new EssJob(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelEmployment.add(tabItemJob);

		final TabItem tabItemEmployeeSalary = new TabItem("Employee Salary");
		tabItemEmployeeSalary.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemEmployeeSalary.setLayout(new FillLayout());
		tabItemEmployeeSalary.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemEmployeeSalary.removeAll();
				tabItemEmployeeSalary.add(new EssEmployeeSalary(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelEmployment.add(tabItemEmployeeSalary);

		final TabItem tabItemRenumeration = new TabItem("Renumeration");
		tabItemRenumeration.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemRenumeration.setLayout(new FillLayout());
		tabItemRenumeration.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemRenumeration.removeAll();
				tabItemRenumeration.add(new EssRenumeration(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelEmployment.add(tabItemRenumeration);

		final TabItem tabItemLeave = new TabItem("Leave");
		tabItemLeave.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemLeave.setLayout(new FillLayout());
		tabItemLeave.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemLeave.removeAll();
				tabItemLeave.add(new EssLeave(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelEmployment.add(tabItemLeave);

		final TabItem tabItemLoan = new TabItem("Loan");
		tabItemLoan.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemLoan.setLayout(new FillLayout());
		tabItemLoan.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemLoan.removeAll();
				tabItemLoan.add(new EssLoan(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelEmployment.add(tabItemLoan);

		final TabItem tabItemPTT = new TabItem("PTT");
		tabItemPTT.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemPTT.setLayout(new FillLayout());
		tabItemPTT.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemPTT.removeAll();
				tabItemPTT.add(new EssPTT(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelEmployment.add(tabItemPTT);

		final TabItem tabItemPph = new TabItem("Payroll");
		tabItemPph.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemPph.setLayout(new FillLayout());
		tabItemPph.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemPph.removeAll();
				tabItemPph.add(new EssPPH(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelEmployment.add(tabItemPph);

		final TabItem tabItemReportTo = new TabItem("Report To");
		tabItemReportTo.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemReportTo.setLayout(new FillLayout());
		tabItemReportTo.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemReportTo.removeAll();
				tabItemReportTo.add(new EssReportTo(formData, id, callerId));
				window.layout(true);
			}
		});
		tabPanelEmployment.add(tabItemReportTo);

		tabItemEmployment.add(tabPanelEmployment);

		tabPanel.add(tabItemEmployment);
		// End tabItemEmployment

		// End tabPanel
		window.add(tabPanel);
	}
}
