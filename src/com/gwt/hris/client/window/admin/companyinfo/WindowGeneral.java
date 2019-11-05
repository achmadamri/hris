package com.gwt.hris.client.window.admin.companyinfo;

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
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.companyinfo.GeneralInterface;
import com.gwt.hris.client.service.admin.companyinfo.GeneralInterfaceAsync;
import com.gwt.hris.client.service.admin.job.PaygradeInterface;
import com.gwt.hris.client.service.admin.job.PaygradeInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbNegaraBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowGeneral extends WindowMain {
	private static WindowGeneral instance = new WindowGeneral();

	public static WindowGeneral getInstance() {
		return instance;
	}

	public WindowGeneral() {
		window = new Window();
		window.setId("WindowGeneral");
		window.setSize(400, 470);
		window.setHeading("Company Info : General");
	}

	GeneralInterfaceAsync generalInterfaceAsync = GWT.create(GeneralInterface.class);
	PaygradeInterfaceAsync paygradeInterfaceAsync = GWT.create(PaygradeInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtCompanyName = new TextField<String>();
	LabelField lblNumberOfEmployees = new LabelField();
	TextField<String> txtTaxID = new TextField<String>();
	TextField<String> txtPhone = new TextField<String>();
	TextField<String> txtFax = new TextField<String>();
	ComboBox<ComboBoxData> cmbCountry = new ComboBox<ComboBoxData>();
	ComboBoxData selectedDataCountry;
	ListStore<ComboBoxData> cmbCountryStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfCountryId = new HiddenField<String>();
	TextArea txtaAddress = new TextArea();
	TextField<String> txtCity = new TextField<String>();
	TextField<String> txtProvince = new TextField<String>();
	TextField<String> txtZIPCode = new TextField<String>();
	TextArea txtaComments = new TextArea();
	TextField<String> txtJKK = new TextField<String>();
	TextField<String> txtJKM = new TextField<String>();

	ComboBox<ComboBoxData> cmbCurrency = new ComboBox<ComboBoxData>();
	ComboBoxData selectedDataCurrency;
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
								selectedDataCurrency = data;
								cmbCurrency.setValue(selectedDataCurrency);
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

	AsyncCallback<TbNegaraBeanModel> tbNegaraAllCallback = new AsyncCallback<TbNegaraBeanModel>() {
		@Override
		public void onSuccess(TbNegaraBeanModel result) {
			if (result.getOperationStatus()) {
				cmbCountryStore.removeAll();

				for (TbNegaraBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbnId().toString(), obj.getTbnNama());
					cmbCountryStore.add(data);

					if (hfCountryId.getValue() != null) {
						if (!"".equals(hfCountryId.getValue())) {
							if (obj.getTbnId() == Integer.parseInt(hfCountryId.getValue())) {
								selectedDataCountry = data;
								cmbCountry.setValue(selectedDataCountry);
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

	AsyncCallback<ReturnBean> callback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);
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

	AsyncCallback<TbPerusahaanBeanModel> getPerusahaanCallback = new AsyncCallback<TbPerusahaanBeanModel>() {
		@Override
		public void onSuccess(TbPerusahaanBeanModel result) {
			if (result.getOperationStatus()) {

				hfId.setValue(result.getTbpId().toString());
				txtCompanyName.setValue(result.getTbpName());
				txtTaxID.setValue(result.getTbpTaxId());
				txtPhone.setValue(result.getTbpPhone());
				txtFax.setValue(result.getTbpFax());
				hfCountryId.setValue(result.getFkTbnId().toString());
				txtaAddress.setValue(result.getTbpAddress1());
				txtCity.setValue(result.getTbpCity());
				txtProvince.setValue(result.getTbpProvince());
				txtZIPCode.setValue(result.getTbpZipCode());
				txtaComments.setValue(result.getTbpComments());
				txtJKK.setValue(result.getTbpJkk().toString());
				txtJKM.setValue(result.getTbpJkm().toString());
				lblNumberOfEmployees.setValue(result.get("NoOfEmployees"));
				hfCurrencyId.setValue(result.getTbpLocalCurrencyId().toString());

				generalInterfaceAsync.getTbNegaraAll(tbNegaraAllCallback);
				
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

	@Override
	public void addComponents() {
		txtCompanyName.setId("txtCompanyName");
		txtCompanyName.setFieldLabel("Company Name *");
		txtCompanyName.setAllowBlank(false);
		formPanel.add(txtCompanyName, formData);

		cmbCountry.setId("cmbCountry");
		cmbCountry.setFieldLabel("Country");
		cmbCountry.setEmptyText("Select");
		cmbCountry.setDisplayField("value");
		cmbCountry.setForceSelection(true);
		cmbCountry.setTypeAhead(true);
		cmbCountry.setTriggerAction(TriggerAction.ALL);
		cmbCountry.setStore(cmbCountryStore);
		formPanel.add(cmbCountry, formData);

		formPanel.add(hfCountryId, formData);

		txtProvince.setId("txtProvince");
		txtProvince.setFieldLabel("Province");
		txtProvince.setAllowBlank(true);
		formPanel.add(txtProvince, formData);

		txtCity.setId("txtCity");
		txtCity.setFieldLabel("City");
		txtCity.setAllowBlank(true);
		formPanel.add(txtCity, formData);

		txtaAddress.setId("txtaAddress");
		txtaAddress.setFieldLabel("Address");
		txtaAddress.setAllowBlank(true);
		formPanel.add(txtaAddress, formData);

		txtZIPCode.setId("txtZIPCode");
		txtZIPCode.setFieldLabel("ZIP Code");
		txtZIPCode.setAllowBlank(true);
		formPanel.add(txtZIPCode, formData);

		txtPhone.setId("txtPhone");
		txtPhone.setFieldLabel("Phone");
		txtPhone.setAllowBlank(true);
		formPanel.add(txtPhone, formData);

		txtFax.setId("txtFax");
		txtFax.setFieldLabel("Fax");
		txtFax.setAllowBlank(true);
		formPanel.add(txtFax, formData);

		lblNumberOfEmployees.setId("txtNumberOfEmployees");
		lblNumberOfEmployees.setFieldLabel("No. of Employees");
		formPanel.add(lblNumberOfEmployees, formData);

		txtTaxID.setId("txtTaxID");
		txtTaxID.setFieldLabel("Tax ID");
		txtTaxID.setAllowBlank(true);
		formPanel.add(txtTaxID, formData);
		
		txtJKK.setId("txtJKK");
		txtJKK.setFieldLabel("JKK");
		txtJKK.setAllowBlank(true);
		formPanel.add(txtJKK, formData);

		txtJKM.setId("txtJKM");
		txtJKM.setFieldLabel("JKM");
		txtJKM.setAllowBlank(true);
		formPanel.add(txtJKM, formData);

		cmbCurrency.setId("cmbCurrency");
		cmbCurrency.setFieldLabel("Local Currency *");
		cmbCurrency.setEmptyText("Select");
		cmbCurrency.setDisplayField("value");
		cmbCurrency.setForceSelection(true);
		cmbCurrency.setTypeAhead(true);
		cmbCurrency.setTriggerAction(TriggerAction.ALL);
		cmbCurrency.setStore(cmbCurrencyStore);
		cmbCurrency.setAllowBlank(false);
		formPanel.add(cmbCurrency, formData);

		formPanel.add(hfCurrencyId, formData);

		txtaComments.setId("txtaComments");
		txtaComments.setFieldLabel("Comments");
		txtaComments.setAllowBlank(true);
		formPanel.add(txtaComments, formData);
		
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
					TbPerusahaanBeanModel tbPerusahaanBeanModel = new TbPerusahaanBeanModel();
					tbPerusahaanBeanModel.setTbpId(Integer.parseInt(hfId.getValue()));
					tbPerusahaanBeanModel.setTbpName(StringUtil.getInstance().getString(txtCompanyName.getValue()));
					tbPerusahaanBeanModel.setTbpTaxId(StringUtil.getInstance().getString(txtTaxID.getValue()));
					tbPerusahaanBeanModel.setTbpPhone(StringUtil.getInstance().getString(txtPhone.getValue()));
					tbPerusahaanBeanModel.setTbpFax(StringUtil.getInstance().getString(txtFax.getValue()));
					tbPerusahaanBeanModel.setFkTbnId(Integer.parseInt(cmbCountry.getValue().get("id").toString()));
					tbPerusahaanBeanModel.setTbpAddress1(StringUtil.getInstance().getString(txtaAddress.getValue()));
					tbPerusahaanBeanModel.setTbpCity(StringUtil.getInstance().getString(txtCity.getValue()));
					tbPerusahaanBeanModel.setTbpProvince(StringUtil.getInstance().getString(txtProvince.getValue()));
					tbPerusahaanBeanModel.setTbpZipCode(StringUtil.getInstance().getString(txtZIPCode.getValue()));
					tbPerusahaanBeanModel.setTbpComments(StringUtil.getInstance().getString(txtaComments.getValue()));
					tbPerusahaanBeanModel.setTbpJkk(StringUtil.getInstance().getDouble(txtJKK.getValue()));
					tbPerusahaanBeanModel.setTbpJkm(StringUtil.getInstance().getDouble(txtJKM.getValue()));
					tbPerusahaanBeanModel.setTbpLocalCurrencyId(Integer.parseInt(cmbCurrency.getValue().get("id").toString()));
					generalInterfaceAsync.submitPerusahaan(tbPerusahaanBeanModel, callback);

					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnClose.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				} else {
					MessageBox.alert("Alert", "Required field is still empty", null);
				}
			}
		});
		window.addButton(btnSave);

		btnCancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				formPanel.mask("loading");
				btnEdit.setEnabled(false);
				btnClose.setEnabled(false);

				doLockedComponent(window);

				generalInterfaceAsync.getPerusahaan(getPerusahaanCallback);

				btnEdit.setVisible(true);
				btnClose.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		window.addButton(btnCancel);

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
		hfCurrencyId.setValue("");
		
		formPanel.mask("loading");
		btnEdit.setEnabled(false);
		btnClose.setEnabled(false);

		doLockedComponent(window);

		generalInterfaceAsync.getPerusahaan(getPerusahaanCallback);

		btnEdit.setVisible(true);
		btnClose.setVisible(true);
		btnCancel.setVisible(false);
		btnSave.setVisible(false);
	}
}
