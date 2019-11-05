package com.gwt.hris.client.window.admin.memberships;

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
import com.gwt.hris.client.service.admin.memberships.MembershipInterface;
import com.gwt.hris.client.service.admin.memberships.MembershipInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMembershipBeanModel;
import com.gwt.hris.client.service.bean.TbMembershipTypesBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowMembershipAddEdit extends WindowMain {
	private static WindowMembershipAddEdit instance = new WindowMembershipAddEdit();

	public static WindowMembershipAddEdit getInstance() {
		return instance;
	}

	public WindowMembershipAddEdit() {
		window = new Window();
		window.setId("WindowMembershipAddEdit");
		window.setSize(500, 170);
		window.setHeading("Membership : Membership");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	MembershipInterfaceAsync interfaceAsync = GWT.create(MembershipInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();

	ComboBox<ComboBoxData> cmbMembershipType = new ComboBox<ComboBoxData>();
	ComboBoxData cmbMembershipTypeSelectedData;
	ListStore<ComboBoxData> cmbMembershipTypeStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfTbmtId = new HiddenField<String>();

	AsyncCallback<TbMembershipTypesBeanModel> tbNegaraAllCallback = new AsyncCallback<TbMembershipTypesBeanModel>() {
		@Override
		public void onSuccess(TbMembershipTypesBeanModel result) {
			if (result.getOperationStatus()) {
				cmbMembershipTypeStore.removeAll();

				for (TbMembershipTypesBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbmtId().toString(), obj.getTbmtName());
					cmbMembershipTypeStore.add(data);

					if (hfTbmtId.getValue() != null) {
						if (!"".equals(hfTbmtId.getValue())) {
							if (obj.getTbmtId() == Integer.parseInt(hfTbmtId.getValue())) {
								cmbMembershipTypeSelectedData = data;
								cmbMembershipType.setValue(cmbMembershipTypeSelectedData);
							}
						}
					}
				}

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

	AsyncCallback<TbMembershipBeanModel> getCallback = new AsyncCallback<TbMembershipBeanModel>() {
		@Override
		public void onSuccess(TbMembershipBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbmId().toString());

				lblCode.setValue(result.getTbmMembershipId());
				txtName.setValue(result.getTbmName());
				hfTbmtId.setValue(result.getTbmtId().toString());

				interfaceAsync.getTbMembershipTypesAll(tbNegaraAllCallback);
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

				TbMembershipBeanModel beanModel = (TbMembershipBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbmId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					btnEdit.setVisible(false);
					btnClose.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);

					interfaceAsync.getTbMembershipTypesAll(tbNegaraAllCallback);
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnClose.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowMembership.getInstance().bottomToolBar.refresh();
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

		cmbMembershipType.setId("cmbMembershipType");
		cmbMembershipType.setFieldLabel("Country *");
		cmbMembershipType.setEmptyText("Select");
		cmbMembershipType.setDisplayField("value");
		cmbMembershipType.setForceSelection(true);
		cmbMembershipType.setTypeAhead(true);
		cmbMembershipType.setTriggerAction(TriggerAction.ALL);
		cmbMembershipType.setStore(cmbMembershipTypeStore);
		cmbMembershipType.setAllowBlank(false);
		formPanel.add(cmbMembershipType, formData);

		formPanel.add(hfTbmtId, formData);

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
					TbMembershipBeanModel beanModel = new TbMembershipBeanModel();
					beanModel.setTbmId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbmName(StringUtil.getInstance().getString(txtName.getValue()));
					beanModel.setTbmtId(Integer.parseInt(cmbMembershipType.getValue().get("id").toString()));
					interfaceAsync.submitMembership(beanModel, submitCallback);
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

				interfaceAsync.getMembership(id, getCallback);

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
		hfTbmtId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnClose.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			interfaceAsync.getTbMembershipTypesAll(tbNegaraAllCallback);

			lblCode.setVisible(false);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnClose.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getMembership(id, getCallback);

			btnEdit.setVisible(true);
			btnClose.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
