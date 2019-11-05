package com.gwt.hris.client.window.time.timesheets;

import java.util.Date;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;
import com.gwt.hris.client.service.bean.TbTimesheetBeanModel;
import com.gwt.hris.client.service.time.timesheets.TimesheetInterface;
import com.gwt.hris.client.service.time.timesheets.TimesheetInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowTimesheetAddEdit extends WindowMain {
	private static WindowTimesheetAddEdit instance = new WindowTimesheetAddEdit();

	public static WindowTimesheetAddEdit getInstance() {
		return instance;
	}

	public WindowTimesheetAddEdit() {
		window = new Window();
		window.setId("WindowTimesheetAddEdit");
		window.setSize(930, 320);
		window.setHeading("Timesheets : Timesheet");
	}

	public String strNav = "";
	public int id = 0;
	public int totalHour = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	TimesheetInterfaceAsync interfaceAsync = GWT.create(TimesheetInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblStartOfWeek = new LabelField();

	LabelField lblTotal = new LabelField();

	ComboBox<ComboBoxData> cmbCustomer = new ComboBox<ComboBoxData>();
	ComboBoxData cmbCustomerSelectedData;
	ListStore<ComboBoxData> cmbCustomerStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfCustomerId = new HiddenField<String>();

	ComboBox<ComboBoxData> cmbProject = new ComboBox<ComboBoxData>();
	ComboBoxData cmbProjectSelectedData;
	ListStore<ComboBoxData> cmbProjectStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfProjectId = new HiddenField<String>();

	ComboBox<ComboBoxData> cmbProjectActivitiesGroup = new ComboBox<ComboBoxData>();
	ComboBoxData cmbProjectActivitiesGroupSelectedData;
	ListStore<ComboBoxData> cmbProjectActivitiesGroupStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfProjectActivitiesGroupId = new HiddenField<String>();

	ComboBox<ComboBoxData> cmbProjectActivities = new ComboBox<ComboBoxData>();
	ComboBoxData cmbProjectActivitiesSelectedData;
	ListStore<ComboBoxData> cmbProjectActivitiesStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfProjectActivitiesId = new HiddenField<String>();

	TextField<String> txtDay1 = new TextField<String>();
	TextField<String> txtDay2 = new TextField<String>();
	TextField<String> txtDay3 = new TextField<String>();
	TextField<String> txtDay4 = new TextField<String>();
	TextField<String> txtDay5 = new TextField<String>();
	TextField<String> txtDay6 = new TextField<String>();
	TextField<String> txtDay7 = new TextField<String>();
	
	LabelField lblPayable = new LabelField();
	
	AsyncCallback<TbProjectActivitiesBeanModel> tbProjectActivitiesCallback = new AsyncCallback<TbProjectActivitiesBeanModel>() {
		@Override
		public void onSuccess(TbProjectActivitiesBeanModel result) {
			if (result.getOperationStatus()) {
				lblPayable.setValue(result.getTbpaPayable() == 1 ? "Payable" : "Non Payable");
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

	AsyncCallback<TbProjectActivitiesGroupBeanModel> tbProjectActivitiesGroupAllCallback = new AsyncCallback<TbProjectActivitiesGroupBeanModel>() {
		@Override
		public void onSuccess(TbProjectActivitiesGroupBeanModel result) {
			if (result.getOperationStatus()) {
				cmbProjectActivitiesGroupStore.removeAll();

				for (TbProjectActivitiesGroupBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpagId().toString(), obj.getTbpagName());
					cmbProjectActivitiesGroupStore.add(data);

					if (hfProjectActivitiesGroupId.getValue() != null) {
						if (!"".equals(hfProjectActivitiesGroupId.getValue())) {
							if (obj.getTbpagId() == Integer.parseInt(hfProjectActivitiesGroupId.getValue())) {
								cmbProjectActivitiesGroupSelectedData = data;
								cmbProjectActivitiesGroup.setValue(cmbProjectActivitiesGroupSelectedData);
							}
						}
					}
				}

				if (hfProjectActivitiesGroupId.getValue() != null) {
					interfaceAsync.getTbProjectActivitiesByGroup(Integer.parseInt(hfProjectActivitiesGroupId.getValue()), tbProjectActivitiesAllCallback);
				} else {
					cmbProjectActivitiesStore.removeAll();
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

	AsyncCallback<TbProjectActivitiesBeanModel> tbProjectActivitiesAllCallback = new AsyncCallback<TbProjectActivitiesBeanModel>() {
		@Override
		public void onSuccess(TbProjectActivitiesBeanModel result) {
			if (result.getOperationStatus()) {
				cmbProjectActivitiesStore.removeAll();

				for (TbProjectActivitiesBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpaId().toString(), obj.getTbpaName());
					cmbProjectActivitiesStore.add(data);

					if (hfProjectActivitiesId.getValue() != null) {
						if (!"".equals(hfProjectActivitiesId.getValue())) {
							if (obj.getTbpaId() == Integer.parseInt(hfProjectActivitiesId.getValue())) {
								cmbProjectActivitiesSelectedData = data;
								cmbProjectActivities.setValue(cmbProjectActivitiesSelectedData);
								interfaceAsync.getTbProjectActivities(Integer.parseInt(hfProjectActivitiesId.getValue()), tbProjectActivitiesCallback);
							}
						}
					}
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

	AsyncCallback<TbCustomerBeanModel> tbCustomerAllCallback = new AsyncCallback<TbCustomerBeanModel>() {
		@Override
		public void onSuccess(TbCustomerBeanModel result) {
			if (result.getOperationStatus()) {
				cmbCustomerStore.removeAll();

				for (TbCustomerBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbcId().toString(), obj.getTbcName().toString());
					cmbCustomerStore.add(data);

					if (hfCustomerId.getValue() != null) {
						if (!"".equals(hfCustomerId.getValue())) {
							if (obj.getTbcId() == Integer.parseInt(hfCustomerId.getValue())) {
								cmbCustomerSelectedData = data;
								cmbCustomer.setValue(cmbCustomerSelectedData);
							}
						}
					}
				}

				if (hfProjectId.getValue() != null) {
					interfaceAsync.getTbProjectByCustomer(Integer.parseInt(hfCustomerId.getValue()), tbProjectByCustomerCallback);
				} else {
					cmbProjectStore.removeAll();
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

	AsyncCallback<TbProjectBeanModel> tbProjectByCustomerCallback = new AsyncCallback<TbProjectBeanModel>() {
		@Override
		public void onSuccess(TbProjectBeanModel result) {
			if (result.getOperationStatus()) {
				cmbProjectStore.removeAll();

				for (TbProjectBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpId().toString(), obj.getTbpName().toString() + " (" + obj.getTbpProjectId().toString() + ")");
					cmbProjectStore.add(data);

					if (hfProjectId.getValue() != null) {
						if (!"".equals(hfProjectId.getValue())) {
							if (obj.getTbpId() == Integer.parseInt(hfProjectId.getValue())) {
								cmbProjectSelectedData = data;
								cmbProject.setValue(cmbProjectSelectedData);
							}
						}
					}
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

	AsyncCallback<TbTimesheetBeanModel> getCallback = new AsyncCallback<TbTimesheetBeanModel>() {
		@Override
		public void onSuccess(TbTimesheetBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbtId().toString());

				DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
				lblStartOfWeek.setValue(fmt.format(result.getTbtStartOfWeek()));

				txtDay1.setFieldLabel("Monday<br>" + fmt.format((Date) result.get("tbtDay1Date")));
				txtDay2.setFieldLabel("Tuesday<br>" + fmt.format((Date) result.get("tbtDay2Date")));
				txtDay3.setFieldLabel("Wednesday<br>" + fmt.format((Date) result.get("tbtDay3Date")));
				txtDay4.setFieldLabel("Thursday<br>" + fmt.format((Date) result.get("tbtDay4Date")));
				txtDay5.setFieldLabel("Friday<br>" + fmt.format((Date) result.get("tbtDay5Date")));
				txtDay6.setFieldLabel("<font color='red'>Saturday<br>" + fmt.format((Date) result.get("tbtDay6Date")) + "</font>");
				txtDay7.setFieldLabel("<font color='red'>Monday<br>" + fmt.format((Date) result.get("tbtDay7Date")) + "</font>");

				txtDay1.setValue(result.getTbtDay1Hour().toString());
				txtDay2.setValue(result.getTbtDay2Hour().toString());
				txtDay3.setValue(result.getTbtDay3Hour().toString());
				txtDay4.setValue(result.getTbtDay4Hour().toString());
				txtDay5.setValue(result.getTbtDay5Hour().toString());
				txtDay6.setValue(result.getTbtDay6Hour().toString());
				txtDay7.setValue(result.getTbtDay7Hour().toString());

				hfCustomerId.setValue(result.getTbcId().toString());
				hfProjectId.setValue(result.getTbpId().toString());
				hfProjectActivitiesGroupId.setValue(result.getTbpagId().toString());
				hfProjectActivitiesId.setValue(result.getTbpaId().toString());

				interfaceAsync.getTbCustomerAll(tbCustomerAllCallback);
				interfaceAsync.getTbProjectActivitiesGroupAll(tbProjectActivitiesGroupAllCallback);

				formPanel.unmask();
				btnEdit.setEnabled(true);
				btnBack.setEnabled(true);

				setTotal();
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

	AsyncCallback<TbTimesheetBeanModel> getCallbackDefault = new AsyncCallback<TbTimesheetBeanModel>() {
		@Override
		public void onSuccess(TbTimesheetBeanModel result) {
			if (result.getOperationStatus()) {
				DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
				lblStartOfWeek.setValue(fmt.format((Date) result.get("tbtDay1Date")));

				txtDay1.setFieldLabel("Monday<br>" + fmt.format((Date) result.get("tbtDay1Date")));
				txtDay2.setFieldLabel("Tuesday<br>" + fmt.format((Date) result.get("tbtDay2Date")));
				txtDay3.setFieldLabel("Wednesday<br>" + fmt.format((Date) result.get("tbtDay3Date")));
				txtDay4.setFieldLabel("Thursday<br>" + fmt.format((Date) result.get("tbtDay4Date")));
				txtDay5.setFieldLabel("Friday<br>" + fmt.format((Date) result.get("tbtDay5Date")));
				txtDay6.setFieldLabel("<font color='red'>Saturday<br>" + fmt.format((Date) result.get("tbtDay6Date")) + "</font>");
				txtDay7.setFieldLabel("<font color='red'>Sunday<br>" + fmt.format((Date) result.get("tbtDay7Date")) + "</font>");

				interfaceAsync.getTbCustomerAll(tbCustomerAllCallback);
				interfaceAsync.getTbProjectActivitiesGroupAll(tbProjectActivitiesGroupAllCallback);

				formPanel.unmask();
				btnEdit.setEnabled(true);
				btnBack.setEnabled(true);
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

	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				formPanel.unmask();
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbTimesheetBeanModel beanModel = (TbTimesheetBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbtId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					interfaceAsync.getTbCustomerAll(tbCustomerAllCallback);
					interfaceAsync.getTbProjectActivitiesGroupAll(tbProjectActivitiesGroupAllCallback);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowTimesheet.getInstance().bottomToolBar.refresh();
			} else {
				MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			formPanel.unmask();
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};

	@Override
	public void addComponents() {
		lblStartOfWeek.setId("lblCode");
		lblStartOfWeek.setFieldLabel("Start of Week");
		formPanel.add(lblStartOfWeek, formData);

		cmbCustomer.setId("cmbCustomer");
		cmbCustomer.setFieldLabel("Customer *");
		cmbCustomer.setEmptyText("Select");
		cmbCustomer.setDisplayField("value");
		cmbCustomer.setForceSelection(true);
		cmbCustomer.setTypeAhead(true);
		cmbCustomer.setTriggerAction(TriggerAction.ALL);
		cmbCustomer.setStore(cmbCustomerStore);
		cmbCustomer.setAllowBlank(false);
		cmbCustomer.addListener(Events.Select, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				cmbProject.clear();
				interfaceAsync.getTbProjectByCustomer(Integer.parseInt(cmbCustomer.getValue().get("id").toString()), tbProjectByCustomerCallback);
			}
		});
		formPanel.add(cmbCustomer, formData);
		formPanel.add(hfCustomerId, formData);

		cmbProject.setId("cmbProject");
		cmbProject.setFieldLabel("Project *");
		cmbProject.setEmptyText("Select");
		cmbProject.setDisplayField("value");
		cmbProject.setForceSelection(true);
		cmbProject.setTypeAhead(true);
		cmbProject.setTriggerAction(TriggerAction.ALL);
		cmbProject.setStore(cmbProjectStore);
		cmbProject.setAllowBlank(false);
		formPanel.add(cmbProject, formData);
		formPanel.add(hfProjectId, formData);

		cmbProjectActivitiesGroup.setId("cmbProjectActivitiesGroup");
		cmbProjectActivitiesGroup.setFieldLabel("Activity Group *");
		cmbProjectActivitiesGroup.setEmptyText("Select");
		cmbProjectActivitiesGroup.setDisplayField("value");
		cmbProjectActivitiesGroup.setForceSelection(true);
		cmbProjectActivitiesGroup.setTypeAhead(true);
		cmbProjectActivitiesGroup.setTriggerAction(TriggerAction.ALL);
		cmbProjectActivitiesGroup.setStore(cmbProjectActivitiesGroupStore);
		cmbProjectActivitiesGroup.setAllowBlank(false);
		cmbProjectActivitiesGroup.addListener(Events.Select, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				cmbProjectActivities.clear();
				interfaceAsync.getTbProjectActivitiesByGroup(Integer.parseInt(cmbProjectActivitiesGroup.getValue().get("id").toString()), tbProjectActivitiesAllCallback);
			}
		});
		formPanel.add(cmbProjectActivitiesGroup, formData);
		formPanel.add(hfProjectActivitiesGroupId, formData);

		cmbProjectActivities.setId("cmbProjectActivities");
		cmbProjectActivities.setFieldLabel("Activity *");
		cmbProjectActivities.setEmptyText("Select");
		cmbProjectActivities.setDisplayField("value");
		cmbProjectActivities.setForceSelection(true);
		cmbProjectActivities.setTypeAhead(true);
		cmbProjectActivities.setTriggerAction(TriggerAction.ALL);
		cmbProjectActivities.setStore(cmbProjectActivitiesStore);
		cmbProjectActivities.setAllowBlank(false);
		cmbProjectActivities.addListener(Events.Select, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				interfaceAsync.getTbProjectActivities(Integer.parseInt(cmbProjectActivities.getValue().get("id").toString()), tbProjectActivitiesCallback);
			}
		});
		formPanel.add(cmbProjectActivities, formData);
		formPanel.add(hfProjectActivitiesId, formData);

		lblPayable.setId("lblPayable");
		lblPayable.setFieldLabel("Payable");
		formPanel.add(lblPayable, formData);

		LayoutContainer main = new LayoutContainer();
		main.setLayout(new ColumnLayout());

		LayoutContainer layoutContainer = new LayoutContainer();
		FormLayout layout = new FormLayout();
		layout.setLabelAlign(LabelAlign.TOP);
		layoutContainer.setLayout(layout);
		txtDay1 = new TextField<String>();
		txtDay1.setRegex("\\d+");
		txtDay1.getMessages().setRegexText("Value is not valid");
		txtDay1.setAutoValidate(true);
		txtDay1.setWidth(100);
		txtDay1.setId("txtDay1");
		txtDay1.setFieldLabel("Monday<br>(01/01/2012)");
		txtDay1.setAllowBlank(false);
		txtDay1.addListener(Events.OnChange, new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				if (txtDay1.getValue().matches("\\d+")) {
					setTotal();
				}
			}
		});

		layoutContainer.add(txtDay1, formData);
		main.add(layoutContainer, new ColumnData());

		layoutContainer = new LayoutContainer();
		layout = new FormLayout();
		layout.setLabelAlign(LabelAlign.TOP);
		layoutContainer.setLayout(layout);
		txtDay2 = new TextField<String>();
		txtDay2.setRegex("\\d+");
		txtDay2.getMessages().setRegexText("Value is not valid");
		txtDay2.setAutoValidate(true);
		txtDay2.setWidth(100);
		txtDay2.setId("txtDay2");
		txtDay2.setFieldLabel("Tuesday<br>(01/01/2012)");
		txtDay2.setAllowBlank(false);
		txtDay2.addListener(Events.OnChange, new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				if (txtDay2.getValue().matches("\\d+")) {
					setTotal();
				}
			}
		});
		layoutContainer.add(txtDay2, formData);
		main.add(layoutContainer, new ColumnData());

		layoutContainer = new LayoutContainer();
		layout = new FormLayout();
		layout.setLabelAlign(LabelAlign.TOP);
		layoutContainer.setLayout(layout);
		txtDay3 = new TextField<String>();
		txtDay3.setRegex("\\d+");
		txtDay3.getMessages().setRegexText("Value is not valid");
		txtDay3.setAutoValidate(true);
		txtDay3.setWidth(100);
		txtDay3.setId("txtDay3");
		txtDay3.setFieldLabel("Wednesday<br>(01/01/2012)");
		txtDay3.setAllowBlank(false);
		txtDay3.addListener(Events.OnChange, new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				if (txtDay3.getValue().matches("\\d+")) {
					setTotal();
				}
			}
		});
		layoutContainer.add(txtDay3, formData);
		main.add(layoutContainer, new ColumnData());

		layoutContainer = new LayoutContainer();
		layout = new FormLayout();
		layout.setLabelAlign(LabelAlign.TOP);
		layoutContainer.setLayout(layout);
		txtDay4 = new TextField<String>();
		txtDay4.setRegex("\\d+");
		txtDay4.getMessages().setRegexText("Value is not valid");
		txtDay4.setAutoValidate(true);
		txtDay4.setWidth(100);
		txtDay4.setId("txtDay4");
		txtDay4.setFieldLabel("Thursday<br>(01/01/2012)");
		txtDay4.setAllowBlank(false);
		txtDay4.addListener(Events.OnChange, new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				if (txtDay4.getValue().matches("\\d+")) {
					setTotal();
				}
			}
		});
		layoutContainer.add(txtDay4, formData);
		main.add(layoutContainer, new ColumnData());

		layoutContainer = new LayoutContainer();
		layout = new FormLayout();
		layout.setLabelAlign(LabelAlign.TOP);
		layoutContainer.setLayout(layout);
		txtDay5 = new TextField<String>();
		txtDay5.setRegex("\\d+");
		txtDay5.getMessages().setRegexText("Value is not valid");
		txtDay5.setAutoValidate(true);
		txtDay5.setWidth(100);
		txtDay5.setId("txtDay5");
		txtDay5.setFieldLabel("Friday<br>(01/01/2012)");
		txtDay5.setAllowBlank(false);
		txtDay5.addListener(Events.OnChange, new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				if (txtDay5.getValue().matches("\\d+")) {
					setTotal();
				}
			}
		});
		layoutContainer.add(txtDay5, formData);
		main.add(layoutContainer, new ColumnData());

		layoutContainer = new LayoutContainer();
		layout = new FormLayout();
		layout.setLabelAlign(LabelAlign.TOP);
		layoutContainer.setLayout(layout);
		txtDay6 = new TextField<String>();
		txtDay6.setRegex("\\d+");
		txtDay6.getMessages().setRegexText("Value is not valid");
		txtDay6.setAutoValidate(true);
		txtDay6.setWidth(100);
		txtDay6.setId("txtDay6");
		txtDay6.setFieldLabel("<font color='red'>Saturday<br>(01/01/2012)</font>");
		txtDay6.setAllowBlank(false);
		txtDay6.addListener(Events.OnChange, new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				if (txtDay6.getValue().matches("\\d+")) {
					setTotal();
				}
			}
		});
		layoutContainer.add(txtDay6, formData);
		main.add(layoutContainer, new ColumnData());

		layoutContainer = new LayoutContainer();
		layout = new FormLayout();
		layout.setLabelAlign(LabelAlign.TOP);
		layoutContainer.setLayout(layout);
		txtDay7 = new TextField<String>();
		txtDay7.setRegex("\\d+");
		txtDay7.getMessages().setRegexText("Value is not valid");
		txtDay7.setAutoValidate(true);
		txtDay7.setWidth(100);
		txtDay7.setId("txtDay7");
		txtDay7.setFieldLabel("<font color='red'>Monday<br>(01/01/2012)</font>");
		txtDay7.setAllowBlank(false);
		txtDay7.addListener(Events.OnChange, new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				if (txtDay7.getValue().matches("\\d+")) {
					setTotal();
				}
			}
		});
		layoutContainer.add(txtDay7, formData);
		main.add(layoutContainer, new ColumnData());

		formPanel.add(main);

		lblTotal.setId("lblTotal");
		lblTotal.setFieldLabel("Total");
		formPanel.add(lblTotal, formData);

		window.add(formPanel);
	}

	public void setTotal() {
		totalHour = Integer.parseInt(txtDay1.getValue() == null ? "0" : txtDay1.getValue()) + Integer.parseInt(txtDay2.getValue() == null ? "0" : txtDay2.getValue()) + Integer.parseInt(txtDay3.getValue() == null ? "0" : txtDay3.getValue()) + Integer.parseInt(txtDay4.getValue() == null ? "0" : txtDay4.getValue()) + Integer.parseInt(txtDay5.getValue() == null ? "0" : txtDay5.getValue()) + Integer.parseInt(txtDay6.getValue() == null ? "0" : txtDay6.getValue()) + Integer.parseInt(txtDay7.getValue() == null ? "0" : txtDay7.getValue());
		lblTotal.setValue(totalHour + " hour(s)");
	}

	@Override
	public void doUnlockedComponent(Window window) {
		super.doUnlockedComponent(window);

		txtDay1.setEnabled(true);
		txtDay2.setEnabled(true);
		txtDay3.setEnabled(true);
		txtDay4.setEnabled(true);
		txtDay5.setEnabled(true);
		txtDay6.setEnabled(true);
		txtDay7.setEnabled(true);
	}

	@Override
	public void doLockedComponent(Window window) {
		super.doLockedComponent(window);

		txtDay1.setEnabled(false);
		txtDay2.setEnabled(false);
		txtDay3.setEnabled(false);
		txtDay4.setEnabled(false);
		txtDay5.setEnabled(false);
		txtDay6.setEnabled(false);
		txtDay7.setEnabled(false);
	}

	@Override
	public void doResetComponent(Window window) {
		super.doResetComponent(window);

		txtDay1.clear();
		txtDay2.clear();
		txtDay3.clear();
		txtDay4.clear();
		txtDay5.clear();
		txtDay6.clear();
		txtDay7.clear();
	}

	Button btnSave;
	Button btnEdit;
	Button btnCancel;
	Button btnBack;

	@Override
	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
					formPanel.mask("Saving...");
					TbTimesheetBeanModel beanModel = new TbTimesheetBeanModel();
					beanModel.setTbtId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbcId(Integer.parseInt(cmbCustomer.getValue().get("id").toString()));
					beanModel.setTbpId(Integer.parseInt(cmbProject.getValue().get("id").toString()));
					beanModel.setTbpagId(Integer.parseInt(cmbProjectActivitiesGroup.getValue().get("id").toString()));
					beanModel.setTbpaId(Integer.parseInt(cmbProjectActivities.getValue().get("id").toString()));

					beanModel.setTbtDay1Hour(Integer.parseInt(txtDay1.getValue()));
					beanModel.setTbtDay2Hour(Integer.parseInt(txtDay2.getValue()));
					beanModel.setTbtDay3Hour(Integer.parseInt(txtDay3.getValue()));
					beanModel.setTbtDay4Hour(Integer.parseInt(txtDay4.getValue()));
					beanModel.setTbtDay5Hour(Integer.parseInt(txtDay5.getValue()));
					beanModel.setTbtDay6Hour(Integer.parseInt(txtDay6.getValue()));
					beanModel.setTbtDay7Hour(Integer.parseInt(txtDay7.getValue()));

					beanModel.setTbtTotalHour(totalHour);

					interfaceAsync.submitTimesheet(beanModel, submitCallback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty or invalid", null);
				}
			}
		});
		window.addButton(btnSave);

		btnEdit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				doUnlockedComponent(window);

				btnEdit.setVisible(false);
				btnBack.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
			}
		});
		window.addButton(btnEdit);

		btnCancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				formPanel.mask("loading");
				btnEdit.setEnabled(false);
				btnBack.setEnabled(false);

				doLockedComponent(window);

				interfaceAsync.getTimesheet(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		window.addButton(btnCancel);

		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowTimesheet window = WindowTimesheet.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfCustomerId.setValue("");
		hfProjectId.setValue("");
		hfProjectActivitiesGroupId.setValue("");
		hfProjectActivitiesId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			interfaceAsync.getTbCustomerAll(tbCustomerAllCallback);
			interfaceAsync.getTbProjectActivitiesGroupAll(tbProjectActivitiesGroupAllCallback);
			interfaceAsync.getTimesheetDefault(getCallbackDefault);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getTimesheet(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
