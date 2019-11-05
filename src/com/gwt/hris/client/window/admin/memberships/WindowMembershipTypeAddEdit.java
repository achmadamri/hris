package com.gwt.hris.client.window.admin.memberships;

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
import com.gwt.hris.client.service.admin.memberships.MembershipTypeInterface;
import com.gwt.hris.client.service.admin.memberships.MembershipTypeInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMembershipTypesBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowMembershipTypeAddEdit extends WindowMain {
	private static WindowMembershipTypeAddEdit instance = new WindowMembershipTypeAddEdit();

	public static WindowMembershipTypeAddEdit getInstance() {
		return instance;
	}

	public WindowMembershipTypeAddEdit() {
		window = new Window();
		window.setId("WindowMembershipTypesAddEdit");
		window.setSize(500, 140);
		window.setHeading("Membership : Membership Type");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	MembershipTypeInterfaceAsync interfaceAsync = GWT.create(MembershipTypeInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();

	AsyncCallback<TbMembershipTypesBeanModel> getCallback = new AsyncCallback<TbMembershipTypesBeanModel>() {
		@Override
		public void onSuccess(TbMembershipTypesBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbmtId().toString());

				lblCode.setValue(result.getTbmtMembershipTypesId());
				txtName.setValue(result.getTbmtName());

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

				TbMembershipTypesBeanModel beanModel = (TbMembershipTypesBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbmtId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					btnEdit.setVisible(false);
					btnClose.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnClose.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowMembershipType.getInstance().bottomToolBar.refresh();
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
	Button btnClose;

	@Override
	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
					TbMembershipTypesBeanModel beanModel = new TbMembershipTypesBeanModel();
					beanModel.setTbmtId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbmtName(StringUtil.getInstance().getString(txtName.getValue()));
					interfaceAsync.submitMembershipTypes(beanModel, submitCallback);
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

				interfaceAsync.getMembershipTypes(id, getCallback);

				btnEdit.setVisible(true);
				btnClose.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		window.addButton(btnCancel);

		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				window.hide();
			}
		});
		window.addButton(btnClose);
	}

	@Override
	public void init() {
		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnClose.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnClose.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getMembershipTypes(id, getCallback);

			btnEdit.setVisible(true);
			btnClose.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
