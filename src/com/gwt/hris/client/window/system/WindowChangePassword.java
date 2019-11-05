package com.gwt.hris.client.window.system;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.system.ChangePasswordInterface;
import com.gwt.hris.client.service.system.ChangePasswordInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowChangePassword extends WindowMain {
	private static WindowChangePassword instance = new WindowChangePassword();

	public static WindowChangePassword getInstance() {
		return instance;
	}

	public WindowChangePassword() {
		window = new Window();
		window.setId("WindowChangePassword");
		window.setSize(350, 200);
		window.setHeading("System : Change Password");
	}
	
	public void show(boolean parent) {
		txtPassword = new TextField<String>();
		txtConfirmPassword = new TextField<String>();
		
		super.show(parent);
	};

	ChangePasswordInterfaceAsync interfaceAsync = GWT.create(ChangePasswordInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	LabelField lblUsername = new LabelField();
	TextField<String> txtPassword = new TextField<String>();
	TextField<String> txtConfirmPassword = new TextField<String>();

	AsyncCallback<TbLoginBeanModel> getCallback = new AsyncCallback<TbLoginBeanModel>() {
		@Override
		public void onSuccess(TbLoginBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTblId().toString());

				lblCode.setValue(result.getTblLoginId());
				lblUsername.setValue(result.getTblUsername());
				txtPassword.setValue(result.getTblPassword());
				txtConfirmPassword.setValue(result.getTblPassword());

				formPanel.unmask();
				btnEdit.setEnabled(true);
				btnClose.setEnabled(true);
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
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbLoginBeanModel beanModel = (TbLoginBeanModel) result.get("model");
				hfId.setValue(beanModel.getTblId().toString());

				doLockedComponent(window);

				btnEdit.setVisible(true);
				btnClose.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);

				isEdit = false;
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

	boolean isEdit = false;

	@Override
	public void addComponents() {
		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanel.add(lblCode, formData);
		
		lblUsername.setId("lblUsername");
		lblUsername.setFieldLabel("Username");
		formPanel.add(lblUsername, formData);

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

		window.add(formPanel);
	}

	Button btnSave;
	Button btnEdit;
	Button btnCancel;
	Button btnClose;

	@Override
	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid() && StringUtil.getInstance().getString(txtPassword.getValue()).equals(StringUtil.getInstance().getString(txtConfirmPassword.getValue()))) {
					TbLoginBeanModel beanModel = new TbLoginBeanModel();
					beanModel.setTblPassword(StringUtil.getInstance().getString(txtPassword.getValue()));
					interfaceAsync.changePassword(beanModel, submitCallback);
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
				btnClose.setVisible(false);
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
				btnClose.setEnabled(false);

				doLockedComponent(window);

				interfaceAsync.getLogin(getCallback);

				btnEdit.setVisible(true);
				btnClose.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);

				isEdit = false;
			}
		});
		window.addButton(btnCancel);

		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MainTabLayout.closeTab(window.getHeading());
			}
		});
		window.addButton(btnClose);
	}

	@Override
	public void init() {
		formPanel.setLabelWidth(120);

		doLockedComponent(window);

		interfaceAsync.getLogin(getCallback);

		btnEdit.setVisible(true);
		btnClose.setVisible(true);
		btnCancel.setVisible(false);
		btnSave.setVisible(false);

		lblCode.setVisible(true);

		isEdit = false;
	}
}
