package com.gwt.hris.client.window.tax;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.users.HRAdminUsersInterface;
import com.gwt.hris.client.service.admin.users.HRAdminUsersInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeePttBeanModel;
import com.gwt.hris.client.service.bean.TbPttBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.service.pim.EmployeeSalaryInterface;
import com.gwt.hris.client.service.pim.EmployeeSalaryInterfaceAsync;
import com.gwt.hris.client.service.tax.EmployeePTTInterface;
import com.gwt.hris.client.service.tax.EmployeePTTInterfaceAsync;
import com.gwt.hris.client.service.tax.PTTInterface;
import com.gwt.hris.client.service.tax.PTTInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowEmployeePTTAddEdit extends WindowMain {
	private static WindowEmployeePTTAddEdit instance = new WindowEmployeePTTAddEdit();

	public static WindowEmployeePTTAddEdit getInstance() {
		return instance;
	}

	public WindowEmployeePTTAddEdit() {
		window = new Window();
		window.setId("WindowEmployeePTTAddEdit");
		window.setSize(500, 210);
		window.setHeading("Tax : Employee PTT");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	EmployeePTTInterfaceAsync interfaceAsync = GWT.create(EmployeePTTInterface.class);
	HRAdminUsersInterfaceAsync hrAdminUsersInterfaceAsync = GWT.create(HRAdminUsersInterface.class);
	EmployeeSalaryInterfaceAsync employeeSalaryInterfaceAsync = GWT.create(EmployeeSalaryInterface.class);
	PTTInterfaceAsync pttInterfaceAsync = GWT.create(PTTInterface.class);
	EmployeeListInterfaceAsync employeeListInterfaceAsync = GWT.create(EmployeeListInterface.class);
	
	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtNominal = new TextField<String>();
	
	ComboBox<ComboBoxData> cmbEmployee;
	ComboBoxData cmbEmployeeSelectedData;
	ListStore<ComboBoxData> cmbEmployeeStore;
	HiddenField<String> hfEmployeeId;
	
	ComboBox<ComboBoxData> cmbPtt;
	ComboBoxData cmbPttSelectedData;
	ListStore<ComboBoxData> cmbPttStore;
	HiddenField<String> hfPttId;

	ComboBox<ComboBoxData> cmbCurrency;
	ComboBoxData cmbCurrencySelectedData;
	ListStore<ComboBoxData> cmbCurrencyStore;
	HiddenField<String> hfCurrencyId;

	AsyncCallback<TbEmployeeBeanModel> tbEmployeeAllCallback = new AsyncCallback<TbEmployeeBeanModel>() {
		@Override
		public void onSuccess(TbEmployeeBeanModel result) {
			if (result.getOperationStatus()) {
				cmbEmployee.clear();
				cmbEmployeeStore.removeAll();

				for (TbEmployeeBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbeId().toString(), obj.getTbeName() + " (" + obj.getTbeEmployeeId() + ")");
					cmbEmployeeStore.add(data);

					if (hfEmployeeId.getValue() != null) {
						if (!"".equals(hfEmployeeId.getValue())) {
							if (obj.getTbeId() == Integer.parseInt(hfEmployeeId.getValue())) {
								cmbEmployeeSelectedData = data;
								cmbEmployee.setValue(cmbEmployeeSelectedData);
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
	
	AsyncCallback<TbPttBeanModel> tbPttAllCallback = new AsyncCallback<TbPttBeanModel>() {
		@Override
		public void onSuccess(TbPttBeanModel result) {
			if (result.getOperationStatus()) {
				cmbPtt.clear();
				cmbPttStore.removeAll();

				for (TbPttBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpttId().toString(), obj.getTbpttName() + " (" + obj.getTbpttPttId() + ")");
					cmbPttStore.add(data);

					if (hfPttId.getValue() != null) {
						if (!"".equals(hfPttId.getValue())) {
							if (obj.getTbpttId() == Integer.parseInt(hfPttId.getValue())) {
								cmbPttSelectedData = data;
								cmbPtt.setValue(cmbPttSelectedData);
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

	AsyncCallback<TbCurrencyBeanModel> tbCurrencyAllCallback = new AsyncCallback<TbCurrencyBeanModel>() {
		@Override
		public void onSuccess(TbCurrencyBeanModel result) {
			if (result.getOperationStatus()) {
				cmbCurrency.clear();
				cmbCurrencyStore.removeAll();

				for (TbCurrencyBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbcId().toString(), obj.getTbcName() + " (" + obj.getTbcCurrencyId() + ")");
					cmbCurrencyStore.add(data);

					if (hfCurrencyId.getValue() != null) {
						if (!"".equals(hfCurrencyId.getValue())) {
							if (obj.getTbcId() == Integer.parseInt(hfCurrencyId.getValue())) {
								cmbCurrencySelectedData = data;
								cmbCurrency.setValue(cmbCurrencySelectedData);
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
	
	AsyncCallback<TbEmployeePttBeanModel> getCallback = new AsyncCallback<TbEmployeePttBeanModel>() {
		@Override
		public void onSuccess(TbEmployeePttBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbepttId().toString());

				txtNominal.setValue(StringUtil.getInstance().toString(result.getTbepttNominal()));

				hfCurrencyId.setValue(result.getTbcId() == null ? "" : result.getTbcId().toString());

				hfEmployeeId.setValue(result.getTbeId() == null ? "" : result.getTbeId().toString());
				employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);
				employeeSalaryInterfaceAsync.getTbCurrencyByEmployee(StringUtil.getInstance().getInteger(hfEmployeeId.getValue()), tbCurrencyAllCallback);
				
				hfPttId.setValue(result.getTbpttId() == null ? "" : result.getTbpttId().toString());
				pttInterfaceAsync.getTbPttAll(tbPttAllCallback);
				
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
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbEmployeePttBeanModel beanModel = (TbEmployeePttBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbepttId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);
					pttInterfaceAsync.getTbPttAll(tbPttAllCallback);
					
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

					isEdit = false;
				}

				WindowEmployeePTT.getInstance().bottomToolBar.refresh();
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

	boolean isEdit = false;
	
	@Override
	public void addComponents() {
		formPanel.setLabelWidth(115);

		cmbEmployee = new ComboBox<ComboBoxData>();
		cmbEmployeeStore = new ListStore<ComboBoxData>();
		cmbEmployee.setId("cmbEmployee");
		cmbEmployee.setFieldLabel("Employee *");
		cmbEmployee.setEmptyText("Select");
		cmbEmployee.setDisplayField("value");
		cmbEmployee.setForceSelection(true);
		cmbEmployee.setTypeAhead(true);
		cmbEmployee.setTriggerAction(TriggerAction.ALL);
		cmbEmployee.setStore(cmbEmployeeStore);
		cmbEmployee.setAllowBlank(false);
		cmbEmployee.addListener(Events.Select, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				cmbCurrency.clear();
				employeeSalaryInterfaceAsync.getTbCurrencyByEmployee(Integer.parseInt(cmbEmployee.getValue().get("id").toString()), tbCurrencyAllCallback);
			}
		});
		formPanel.add(cmbEmployee, formData);
		hfEmployeeId = new HiddenField<String>();
		formPanel.add(hfEmployeeId, formData);

		cmbPtt = new ComboBox<ComboBoxData>();
		cmbPttStore = new ListStore<ComboBoxData>();
		cmbPtt.setId("cmbPtt");
		cmbPtt.setFieldLabel("Ptt *");
		cmbPtt.setEmptyText("Select");
		cmbPtt.setDisplayField("value");
		cmbPtt.setForceSelection(true);
		cmbPtt.setTypeAhead(true);
		cmbPtt.setTriggerAction(TriggerAction.ALL);
		cmbPtt.setStore(cmbPttStore);
		cmbPtt.setAllowBlank(false);
		formPanel.add(cmbPtt, formData);
		hfPttId = new HiddenField<String>();
		formPanel.add(hfPttId, formData);

		cmbCurrency = new ComboBox<ComboBoxData>();
		cmbCurrencyStore = new ListStore<ComboBoxData>();
		cmbCurrency.setId("cmbCurrency");
		cmbCurrency.setFieldLabel("Currency *");
		cmbCurrency.setEmptyText("Select");
		cmbCurrency.setDisplayField("value");
		cmbCurrency.setForceSelection(true);
		cmbCurrency.setTypeAhead(true);
		cmbCurrency.setTriggerAction(TriggerAction.ALL);
		cmbCurrency.setStore(cmbCurrencyStore);
		cmbCurrency.setAllowBlank(false);
		formPanel.add(cmbCurrency, formData);
		hfCurrencyId = new HiddenField<String>();
		formPanel.add(hfCurrencyId, formData);

		txtNominal.setId("txtNominal");
		txtNominal.setFieldLabel("Nominal *");
		txtNominal.setAllowBlank(false);
		formPanel.add(txtNominal, formData);

		window.add(formPanel);
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
					TbEmployeePttBeanModel beanModel = new TbEmployeePttBeanModel();
					beanModel.setTbepttId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbepttNominal(StringUtil.getInstance().getDouble(txtNominal.getValue()));

					if (cmbEmployee.getValue() != null)
						beanModel.setTbeId(Integer.parseInt(cmbEmployee.getValue().get("id").toString()));

					if (cmbPtt.getValue() != null)
						beanModel.setTbpttId(Integer.parseInt(cmbPtt.getValue().get("id").toString()));

					if (cmbCurrency.getValue() != null)
						beanModel.setTbcId(Integer.parseInt(cmbCurrency.getValue().get("id").toString()));

					beanModel.setTbepttCurrencyName(cmbCurrency.getValue().get("value").toString());
					
					interfaceAsync.submitPtt(beanModel, submitCallback);
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

				isEdit = true;
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

				interfaceAsync.getPtt(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);

				isEdit = false;
			}
		});
		window.addButton(btnCancel);

		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowEmployeePTT window = WindowEmployeePTT.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfEmployeeId.setValue("");
		hfPttId.setValue("");
		hfCurrencyId.setValue("");
		
		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);
			pttInterfaceAsync.getTbPttAll(tbPttAllCallback);
			
			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getPtt(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			isEdit = false;
		}
	}
}
