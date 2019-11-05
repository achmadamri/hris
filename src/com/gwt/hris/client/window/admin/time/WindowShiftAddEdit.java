package com.gwt.hris.client.window.admin.time;

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
import com.gwt.hris.client.service.admin.time.ShiftInterface;
import com.gwt.hris.client.service.admin.time.ShiftInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbShiftBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowShiftAddEdit extends WindowMain {
	private static WindowShiftAddEdit instance = new WindowShiftAddEdit();

	public static WindowShiftAddEdit getInstance() {
		return instance;
	}

	public WindowShiftAddEdit() {
		window = new Window();
		window.setId("WindowShiftAddEdit");
		window.setSize(500, 200);
		window.setHeading("Time : Shift");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	ShiftInterfaceAsync interfaceAsync = GWT.create(ShiftInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();
	TextField<String> txtInTime = new TextField<String>();
	TextField<String> txtOutTime = new TextField<String>();

	AsyncCallback<TbShiftBeanModel> getCallback = new AsyncCallback<TbShiftBeanModel>() {
		@Override
		public void onSuccess(TbShiftBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbsId().toString());

				lblCode.setValue(result.getTbsShiftId());
				txtName.setValue(result.getTbsName());
				txtInTime.setValue(result.getTbsInTime());
				txtOutTime.setValue(result.getTbsOutTime());

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

				TbShiftBeanModel beanModel = (TbShiftBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbsId().toString());

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

				WindowShift.getInstance().bottomToolBar.refresh();
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

		txtInTime.setId("txtInTime");
		txtInTime.setFieldLabel("In Time *");
		txtInTime.setAllowBlank(false);
		txtInTime.setRegex("\\d\\d:\\d\\d:\\d\\d");
		txtInTime.getMessages().setRegexText("Value is not valid");
		txtInTime.setAutoValidate(true);
		txtInTime.setEmptyText("HH:mm:ss");
		formPanel.add(txtInTime, formData);

		txtOutTime.setId("txtOutTime");
		txtOutTime.setFieldLabel("Out Time *");
		txtOutTime.setAllowBlank(false);
		txtOutTime.setRegex("\\d\\d:\\d\\d:\\d\\d");
		txtOutTime.getMessages().setRegexText("Value is not valid");
		txtOutTime.setAutoValidate(true);
		txtOutTime.setEmptyText("HH:mm:ss");
		formPanel.add(txtOutTime, formData);

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
					TbShiftBeanModel beanModel = new TbShiftBeanModel();
					beanModel.setTbsId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbsName(StringUtil.getInstance().getString(txtName.getValue()));
					beanModel.setTbsInTime(StringUtil.getInstance().getString(txtInTime.getValue()));
					beanModel.setTbsOutTime(StringUtil.getInstance().getString(txtOutTime.getValue()));
					interfaceAsync.submitShift(beanModel, submitCallback);
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

				interfaceAsync.getShift(id, getCallback);

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
				WindowShift window = WindowShift.getInstance();
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

			interfaceAsync.getShift(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
