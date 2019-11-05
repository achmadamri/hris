package com.gwt.hris.client.window.admin.job;

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
import com.gwt.hris.client.service.admin.job.PaygradeInterface;
import com.gwt.hris.client.service.admin.job.PaygradeInterfaceAsync;
import com.gwt.hris.client.service.admin.job.RenumerationInterface;
import com.gwt.hris.client.service.admin.job.RenumerationInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbRenumerationBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowRenumerationAddEdit extends WindowMain {
	private static WindowRenumerationAddEdit instance = new WindowRenumerationAddEdit();

	public static WindowRenumerationAddEdit getInstance() {
		return instance;
	}

	public WindowRenumerationAddEdit() {
		window = new Window();
		window.setId("WindowRenumerationAddEdit");
		window.setSize(500, 200);
		window.setHeading("Job : Renumeration");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	RenumerationInterfaceAsync interfaceAsync = GWT.create(RenumerationInterface.class);
	PaygradeInterfaceAsync paygradeInterfaceAsync = GWT.create(PaygradeInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();
	TextField<String> txtNominal = new TextField<String>();

	ComboBox<ComboBoxData> cmbCurrency = new ComboBox<ComboBoxData>();
	ComboBoxData selectedData;
	ListStore<ComboBoxData> cmbCurrencyStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfCurrencyId = new HiddenField<String>();

	AsyncCallback<TbCurrencyBeanModel> getTbCurrencyAllCallback = new AsyncCallback<TbCurrencyBeanModel>() {
		@Override
		public void onSuccess(TbCurrencyBeanModel result) {
			if (result.getOperationStatus()) {
				cmbCurrencyStore.removeAll();

				for (TbCurrencyBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbcId().toString(), obj.getTbcCurrencyId() + " - " + obj.getTbcName());
					cmbCurrencyStore.add(data);

					if (hfCurrencyId.getValue() != null) {
						if (!"".equals(hfCurrencyId.getValue())) {
							if (obj.getTbcId() == Integer.parseInt(hfCurrencyId.getValue())) {
								selectedData = data;
								cmbCurrency.setValue(selectedData);
							}
						}
					}
				}

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

	AsyncCallback<TbRenumerationBeanModel> getCallback = new AsyncCallback<TbRenumerationBeanModel>() {
		@Override
		public void onSuccess(TbRenumerationBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbrId().toString());

				lblCode.setValue(result.getTbrRenumerationId());
				txtName.setValue(result.getTbrName());
				txtNominal.setValue(result.getTbrNominal().toString());
				hfCurrencyId.setValue(result.getTbcId().toString());

				formPanel.unmask();
				btnEdit.setEnabled(true);
				btnBack.setEnabled(true);

				paygradeInterfaceAsync.getTbCurrencyAll(getTbCurrencyAllCallback);
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

				TbRenumerationBeanModel beanModel = (TbRenumerationBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbrId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);

					paygradeInterfaceAsync.getTbCurrencyAll(getTbCurrencyAllCallback);
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowRenumeration.getInstance().bottomToolBar.refresh();
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
					TbRenumerationBeanModel beanModel = new TbRenumerationBeanModel();
					beanModel.setTbrId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbrName(StringUtil.getInstance().getString(txtName.getValue()));
					beanModel.setTbrNominal(StringUtil.getInstance().getInteger(txtNominal.getValue()));
					beanModel.setTbcId(Integer.parseInt(cmbCurrency.getValue().get("id").toString()));
					interfaceAsync.submitRenumeration(beanModel, submitCallback);
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

				interfaceAsync.getRenumeration(id, getCallback);

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
				WindowRenumeration window = WindowRenumeration.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfCurrencyId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);

			paygradeInterfaceAsync.getTbCurrencyAll(getTbCurrencyAllCallback);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getRenumeration(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
