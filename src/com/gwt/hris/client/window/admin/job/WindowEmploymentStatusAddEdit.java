package com.gwt.hris.client.window.admin.job;

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
import com.gwt.hris.client.service.admin.job.EmploymentStatusInterface;
import com.gwt.hris.client.service.admin.job.EmploymentStatusInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowEmploymentStatusAddEdit extends WindowMain {
	private static WindowEmploymentStatusAddEdit instance = new WindowEmploymentStatusAddEdit();

	public static WindowEmploymentStatusAddEdit getInstance() {
		return instance;
	}

	public WindowEmploymentStatusAddEdit() {
		window = new Window();
		window.setId("WindowEmploymentStatusAddEdit");
		window.setSize(500, 140);
		window.setHeading("Job : Employment Status");
	}

	public String strNav = "";
	public int intTbesId = 0;

	public void show(String strNav, int intTbesId) {
		this.strNav = strNav;
		this.intTbesId = intTbesId;

		super.show(true);
	}

	EmploymentStatusInterfaceAsync employmentStatusInterfaceAsync = GWT.create(EmploymentStatusInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();

	AsyncCallback<TbEmploymentStatusBeanModel> getEmploymentStatusCallback = new AsyncCallback<TbEmploymentStatusBeanModel>() {
		@Override
		public void onSuccess(TbEmploymentStatusBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbesId().toString());

				lblCode.setValue(result.getTbesEmploymentStatusId());
				txtName.setValue(result.getTbesName());

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

	AsyncCallback<ReturnBean> submitEmploymentStatusCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbEmploymentStatusBeanModel tbEmploymentStatusBeanModel = (TbEmploymentStatusBeanModel) result.get("model");
				hfId.setValue(tbEmploymentStatusBeanModel.getTbesId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

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

				WindowEmploymentStatus.getInstance().bottomToolBar.refresh();
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
		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanel.add(lblCode, formData);

		txtName.setId("txtName");
		txtName.setFieldLabel("Name *");
		txtName.setAllowBlank(false);
		formPanel.add(txtName, formData);

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
					TbEmploymentStatusBeanModel tbEmploymentStatusBeanModel = new TbEmploymentStatusBeanModel();
					tbEmploymentStatusBeanModel.setTbesId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					tbEmploymentStatusBeanModel.setTbesName(StringUtil.getInstance().getString(txtName.getValue()));
					employmentStatusInterfaceAsync.submitEmploymentStatus(tbEmploymentStatusBeanModel, submitEmploymentStatusCallback);
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

				employmentStatusInterfaceAsync.getEmploymentStatus(intTbesId, getEmploymentStatusCallback);

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
				WindowEmploymentStatus window = WindowEmploymentStatus.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			employmentStatusInterfaceAsync.getEmploymentStatus(intTbesId, getEmploymentStatusCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
