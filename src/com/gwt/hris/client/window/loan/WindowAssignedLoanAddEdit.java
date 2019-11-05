package com.gwt.hris.client.window.loan;

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
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.job.PaygradeInterface;
import com.gwt.hris.client.service.admin.job.PaygradeInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLoanBeanModel;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.ViewAssignedLoanBeanModel;
import com.gwt.hris.client.service.loan.AssignedLoanInterface;
import com.gwt.hris.client.service.loan.AssignedLoanInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowAssignedLoanAddEdit extends WindowMain {
	private static WindowAssignedLoanAddEdit instance = new WindowAssignedLoanAddEdit();

	public static WindowAssignedLoanAddEdit getInstance() {
		return instance;
	}

	public WindowAssignedLoanAddEdit() {
		window = new Window();
		window.setId("WindowAssignedLoanAddEdit");
		window.setSize(500, 270);
		window.setHeading("Loan : Assigned Loan");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	AssignedLoanInterfaceAsync interfaceAsync = GWT.create(AssignedLoanInterface.class);
	PaygradeInterfaceAsync paygradeInterfaceAsync = GWT.create(PaygradeInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtLoanName = new TextField<String>();
	TextField<String> txtNominal = new TextField<String>();
	TextField<String> txtInterest = new TextField<String>();
	TextField<String> txtTenor = new TextField<String>();
	LabelField lblTotalLoan = new LabelField();
	LabelField lblMonthlyPayment = new LabelField();

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

	AsyncCallback<ViewAssignedLoanBeanModel> getCallback = new AsyncCallback<ViewAssignedLoanBeanModel>() {
		@Override
		public void onSuccess(ViewAssignedLoanBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbaloId().toString());

				txtLoanName.setValue(result.getTbaloName());
				txtNominal.setValue(result.getTbaloNominal().toString());
				txtInterest.setValue(result.getTbaloInterest().toString());
				txtTenor.setValue(result.getTbaloTenor().toString());
				lblTotalLoan.setValue(result.getTbaloNominalTotal());
				lblMonthlyPayment.setValue(result.getTbaloMonthlyPayment());
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

				TbAssignedLoanBeanModel beanModel = (TbAssignedLoanBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbaloId().toString());

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

				WindowAssignedLoan.getInstance().bottomToolBar.refresh();
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

	public void count() {
		if (txtNominal.getValue() != null && txtInterest.getValue() != null && txtTenor.getValue() != null) {
			double nominal = Double.parseDouble(txtNominal.getValue());
			double interest = Double.parseDouble(txtInterest.getValue());
			double tenor = Double.parseDouble(txtTenor.getValue());
			double nominalTotal = (((interest / 100) * tenor) * nominal) + nominal;
			double monthlyPayment = nominalTotal / tenor;

			NumberFormat fmt = NumberFormat.getFormat("#,###,###");

			lblMonthlyPayment.setText(fmt.format(monthlyPayment));
			lblTotalLoan.setText(fmt.format(nominalTotal));
		}
	}

	@Override
	public void addComponents() {
		formPanel.setLabelWidth(115);

		txtLoanName.setId("txtLoanName");
		txtLoanName.setFieldLabel("Loan Name *");
		txtLoanName.setAllowBlank(false);
		formPanel.add(txtLoanName, formData);

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
		txtNominal.addListener(Events.Change, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				count();
			}
		});
		formPanel.add(txtNominal, formData);

		txtInterest.setId("txtInterest");
		txtInterest.setFieldLabel("Interest (%/month) *");
		txtInterest.setAllowBlank(false);
		txtInterest.addListener(Events.Change, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				count();
			}
		});
		formPanel.add(txtInterest, formData);

		txtTenor.setId("txtTenor");
		txtTenor.setFieldLabel("Tenor (month) *");
		txtTenor.setAllowBlank(false);
		txtTenor.addListener(Events.Change, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				count();
			}
		});
		formPanel.add(txtTenor, formData);

		lblMonthlyPayment.setId("lblMonthlyPayment");
		lblMonthlyPayment.setFieldLabel("Monthly Payment");
		formPanel.add(lblMonthlyPayment, formData);

		lblTotalLoan.setId("lblTotalLoan");
		lblTotalLoan.setFieldLabel("Total Loan");
		formPanel.add(lblTotalLoan, formData);

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
					TbAssignedLoanBeanModel beanModel = new TbAssignedLoanBeanModel();
					beanModel.setTbaloId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbaloName(StringUtil.getInstance().getString(txtLoanName.getValue()));
					beanModel.setTbaloNominal(StringUtil.getInstance().getDouble(txtNominal.getValue()));
					beanModel.setTbaloInterest(StringUtil.getInstance().getDouble(txtInterest.getValue()));
					beanModel.setTbaloTenor(StringUtil.getInstance().getInteger(txtTenor.getValue()));
					beanModel.setTbcId(Integer.parseInt(cmbCurrency.getValue().get("id").toString()));
					interfaceAsync.submitAssignedLoan(beanModel, submitCallback);
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

				interfaceAsync.getAssignedLoan(id, getCallback);

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
				WindowAssignedLoan window = WindowAssignedLoan.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		formPanel.setLabelWidth(130);
		hfCurrencyId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			paygradeInterfaceAsync.getTbCurrencyAll(getTbCurrencyAllCallback);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getAssignedLoan(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
