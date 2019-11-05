package com.gwt.hris.client.window.admin.users;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.users.HRAdminUsersInterface;
import com.gwt.hris.client.service.admin.users.HRAdminUsersInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowESSUsersAddEdit extends WindowMain {
	private static WindowESSUsersAddEdit instance = new WindowESSUsersAddEdit();

	public static WindowESSUsersAddEdit getInstance() {
		return instance;
	}

	public WindowESSUsersAddEdit() {
		window = new Window();
		window.setId("WindowESSUsersAddEdit");
		window.setSize(500, 250);
		window.setHeading("Users : ESS");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;
		
		txtPassword = new TextField<String>();
		txtConfirmPassword = new TextField<String>();

		super.show(true);
	}

	HRAdminUsersInterfaceAsync interfaceAsync = GWT.create(HRAdminUsersInterface.class);
	EmployeeListInterfaceAsync employeeListInterfaceAsync = GWT.create(EmployeeListInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtUsername = new TextField<String>();
	TextField<String> txtPassword = new TextField<String>();
	TextField<String> txtConfirmPassword = new TextField<String>();
	
	ComboBox<ComboBoxData> cmbEmployee;
	ComboBoxData cmbEmployeeSelectedData;
	ListStore<ComboBoxData> cmbEmployeeStore;
	HiddenField<String> hfEmployeeId;

	ComboBox<ComboBoxData> cmbStatus = new ComboBox<ComboBoxData>();
	ComboBoxData cmbStatusSelectedData;
	ListStore<ComboBoxData> cmbStatusStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfStatusId = new HiddenField<String>();

	public void fillCmbStatus() {
		cmbStatusStore.removeAll();

		cmbStatusStore.add(new ComboBoxData("1", "Enabled"));
		cmbStatusStore.add(new ComboBoxData("0", "Disabled"));

		if (hfStatusId.getValue() != null) {
			if (!"".equals(hfStatusId.getValue())) {
				if (1 == Integer.parseInt(hfStatusId.getValue())) {
					cmbStatusSelectedData = new ComboBoxData("1", "Enabled");
					cmbStatus.setValue(cmbStatusSelectedData);
				} else {
					cmbStatusSelectedData = new ComboBoxData("0", "Disabled");
					cmbStatus.setValue(cmbStatusSelectedData);
				}
			}
		}
	}
	
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

	AsyncCallback<TbLoginBeanModel> getCallback = new AsyncCallback<TbLoginBeanModel>() {
		@Override
		public void onSuccess(TbLoginBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTblId().toString());

				lblCode.setValue(result.getTblLoginId());
				txtUsername.setValue(result.getTblUsername());
				txtPassword.setValue(result.getTblPassword());
				txtConfirmPassword.setValue(result.getTblPassword());

				hfStatusId.setValue(result.getTblStatus().toString());
				hfEmployeeId.setValue(result.getTbeId() == null ? "" : result.getTbeId().toString());
				employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);

				fillCmbStatus();

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

				TbLoginBeanModel beanModel = (TbLoginBeanModel) result.get("model");
				hfId.setValue(beanModel.getTblId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
					
					employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);
					fillCmbStatus();
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);

					isEdit = false;
				}

				WindowESSUsers.getInstance().bottomToolBar.refresh();
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
		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanel.add(lblCode, formData);

		txtUsername.setId("txtUsername");
		txtUsername.setFieldLabel("Username *");
		txtUsername.setAllowBlank(false);
		formPanel.add(txtUsername, formData);

		txtPassword.setId("txtPassword");
		txtPassword.setFieldLabel("Password *");
		txtPassword.setAllowBlank(false);
		txtPassword.setPassword(true);
		formPanel.add(txtPassword, formData);

		txtConfirmPassword.setId("txtConfirmPassword");
		txtConfirmPassword.setFieldLabel("Confirm Password *");
		txtConfirmPassword.setAllowBlank(false);
		txtConfirmPassword.setPassword(true);
		formPanel.add(txtConfirmPassword, formData);

		cmbStatus.setId("cmbStatus");
		cmbStatus.setFieldLabel("Status *");
		cmbStatus.setEmptyText("Select");
		cmbStatus.setDisplayField("value");
		cmbStatus.setForceSelection(true);
		cmbStatus.setTypeAhead(true);
		cmbStatus.setTriggerAction(TriggerAction.ALL);
		cmbStatus.setStore(cmbStatusStore);
		cmbStatus.setAllowBlank(false);
		formPanel.add(cmbStatus, formData);

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
		formPanel.add(cmbEmployee, formData);
		hfEmployeeId = new HiddenField<String>();
		formPanel.add(hfEmployeeId, formData);

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
					TbLoginBeanModel beanModel = new TbLoginBeanModel();
					beanModel.setTblId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTblUsername(StringUtil.getInstance().getString(txtUsername.getValue()));
					beanModel.setTblPassword(StringUtil.getInstance().getString(txtPassword.getValue()));
					beanModel.setTblStatus(Integer.parseInt(cmbStatus.getValue().get("id").toString()));
					
					if (cmbEmployee.getValue() != null)
						beanModel.setTbeId(Integer.parseInt(cmbEmployee.getValue().get("id").toString()));
					
					interfaceAsync.submitLogin(beanModel, submitCallback);
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

				interfaceAsync.getLogin(id, getCallback);

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
				WindowESSUsers window = WindowESSUsers.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		formPanel.setLabelWidth(120);

		hfStatusId.setValue("");
		hfEmployeeId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);
			fillCmbStatus();

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);
		} else if ("edit".equals(strNav)) {
			doLockedComponent(window);

			interfaceAsync.getLogin(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);

			isEdit = false;
		}
	}
}
