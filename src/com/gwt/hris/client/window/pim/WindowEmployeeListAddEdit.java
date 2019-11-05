package com.gwt.hris.client.window.pim;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowEmployeeListAddEdit extends WindowMain {
	private static WindowEmployeeListAddEdit instance = new WindowEmployeeListAddEdit();

	public static WindowEmployeeListAddEdit getInstance() {
		return instance;
	}

	public WindowEmployeeListAddEdit() {
		window = new Window();
		window.setId("WindowEmployeeListAddEdit");
		window.setSize(450, 230);
		window.setHeading("PIM : Employee List");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	EmployeeListInterfaceAsync interfaceAsync = GWT.create(EmployeeListInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtFirstName = new TextField<String>();
	TextField<String> txtLastName = new TextField<String>();
	TextField<String> txtMiddleName = new TextField<String>();
	TextField<String> txtNickName = new TextField<String>();
	FileUploadField fufPhoto = new FileUploadField();
	HiddenField<String> hftbePhotoFileName = new HiddenField<String>();

	AsyncCallback<TbEmployeeBeanModel> getCallback = new AsyncCallback<TbEmployeeBeanModel>() {
		@Override
		public void onSuccess(TbEmployeeBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbeId().toString());

				lblCode.setValue(result.getTbeEmployeeId());
				txtFirstName.setValue(result.getTbeFirstName());
				txtLastName.setValue(result.getTbeLastName());
				txtMiddleName.setValue(result.getTbeMiddleName());
				txtNickName.setValue(result.getTbeNickName());

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

				TbEmployeeBeanModel beanModel = (TbEmployeeBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbeId().toString());

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

				WindowEmployeeList.getInstance().bottomToolBar.refresh();
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

	public void submit() {
		TbEmployeeBeanModel beanModel = new TbEmployeeBeanModel();
		beanModel.setTbeId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
		beanModel.setTbeFirstName(StringUtil.getInstance().getString(txtFirstName.getValue()));
		beanModel.setTbeLastName(StringUtil.getInstance().getString(txtLastName.getValue()));
		beanModel.setTbeMiddleName(StringUtil.getInstance().getString(txtMiddleName.getValue()));
		beanModel.setTbeNickName(StringUtil.getInstance().getString(txtNickName.getValue()));
		beanModel.setTbePhotoFileName(hftbePhotoFileName.getValue());
		interfaceAsync.submitEmployee(beanModel, submitCallback);
	}

	@Override
	public void addComponents() {
		formPanel.setAction("hris/photoupload");
		formPanel.setEncoding(Encoding.MULTIPART);
		formPanel.setMethod(Method.POST);

		formPanel.addListener(Events.Submit, new Listener<FormEvent>() {
			@Override
			public void handleEvent(FormEvent be) {
				if (be.getResultHtml().contains("filename")) {
					hftbePhotoFileName.setValue(be.getResultHtml().replaceAll("filename:", ""));
					submit();
				} else {
					MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + be.getResultHtml(), null);
				}
			}
		});

		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanel.add(lblCode, formData);

		txtFirstName.setId("txtName");
		txtFirstName.setFieldLabel("First Name *");
		txtFirstName.setAllowBlank(false);
		formPanel.add(txtFirstName, formData);

		txtMiddleName.setId("txtMiddleName");
		txtMiddleName.setFieldLabel("Middle Name");
		txtMiddleName.setAllowBlank(true);
		formPanel.add(txtMiddleName, formData);

		txtLastName.setId("txtLastName");
		txtLastName.setFieldLabel("Last Name *");
		txtLastName.setAllowBlank(false);
		formPanel.add(txtLastName, formData);

		txtNickName.setId("txtNickName");
		txtNickName.setFieldLabel("Nick Name");
		txtNickName.setAllowBlank(true);
		formPanel.add(txtNickName, formData);

		fufPhoto.setName("uploadedfile");
		fufPhoto.setFieldLabel("Photo");
		formPanel.add(fufPhoto, formData);

		formPanel.add(hftbePhotoFileName);

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
					if ("".equals(fufPhoto.getValue()) || fufPhoto.getValue() == null) {
						submit();
					} else {
						formPanel.submit();
					}
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

				interfaceAsync.getEmployee(id, getCallback);

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
				WindowEmployeeList window = WindowEmployeeList.getInstance();
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

			interfaceAsync.getEmployee(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
