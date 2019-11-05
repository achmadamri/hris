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
import com.gwt.hris.client.service.admin.companyinfo.LocationsInterface;
import com.gwt.hris.client.service.admin.companyinfo.LocationsInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLocationBeanModel;
import com.gwt.hris.client.service.bean.TbNegaraBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowLocationsAddEdit extends WindowMain {
	private static WindowLocationsAddEdit instance = new WindowLocationsAddEdit();

	public static WindowLocationsAddEdit getInstance() {
		return instance;
	}

	public WindowLocationsAddEdit() {
		window = new Window();
		window.setId("WindowLocationsAddEdit");
		window.setSize(400, 450);
		window.setHeading("Company Info : Locations");
	}

	public String strNav = "";
	public int intTblId = 0;

	public void show(String strNav, int intTblId) {
		this.strNav = strNav;
		this.intTblId = intTblId;

		super.show(true);
	}

	GeneralInterfaceAsync generalInterfaceAsync = GWT.create(GeneralInterface.class);
	LocationsInterfaceAsync locationsInterfaceAsync = GWT.create(LocationsInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();
	
	ComboBox<ComboBoxData> cmbCountry = new ComboBox<ComboBoxData>();
	ComboBoxData cmbCountrySelectedData;
	ListStore<ComboBoxData> cmbCountryStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfCountryId = new HiddenField<String>();
	
	TextArea txtaAddress = new TextArea();
	TextField<String> txtCity = new TextField<String>();
	TextField<String> txtProvince = new TextField<String>();
	TextField<String> txtZIPCode = new TextField<String>();
	TextField<String> txtPhone = new TextField<String>();
	TextField<String> txtFax = new TextField<String>();
	TextArea txtaComments = new TextArea();
	
	ComboBox<ComboBoxData> cmbPerusahaan = new ComboBox<ComboBoxData>();
	ComboBoxData cmbPerusahaanSelectedData;
	ListStore<ComboBoxData> cmbPerusahaanStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfPerusahaanId = new HiddenField<String>();

	AsyncCallback<TbPerusahaanBeanModel> tbPerusahaanAllCallback = new AsyncCallback<TbPerusahaanBeanModel>() {
		@Override
		public void onSuccess(TbPerusahaanBeanModel result) {
			if (result.getOperationStatus()) {
				cmbPerusahaanStore.removeAll();

				for (TbPerusahaanBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpId().toString(), obj.getTbpName());
					cmbPerusahaanStore.add(data);

					if (hfPerusahaanId.getValue() != null) {
						if (!"".equals(hfPerusahaanId.getValue())) {
							if (obj.getTbpId() == Integer.parseInt(hfPerusahaanId.getValue())) {
								cmbPerusahaanSelectedData = data;
								cmbPerusahaan.setValue(cmbPerusahaanSelectedData);
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
								cmbCountrySelectedData = data;
								cmbCountry.setValue(cmbCountrySelectedData);
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

	AsyncCallback<TbLocationBeanModel> getLocationsCallback = new AsyncCallback<TbLocationBeanModel>() {
		@Override
		public void onSuccess(TbLocationBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTblId().toString());

				lblCode.setValue(result.getTblLocationId());
				txtName.setValue(result.getTblName());
				hfPerusahaanId.setValue(result.getTbpId().toString());
				hfCountryId.setValue(result.getFkTbnId().toString());
				txtaAddress.setValue(result.getTblAddress());
				txtCity.setValue(result.getTblCity());
				txtProvince.setValue(result.getTblProvince());
				txtZIPCode.setValue(result.getTblZipCode());
				txtPhone.setValue(result.getTblPhone());
				txtFax.setValue(result.getTblFax());
				txtaComments.setValue(result.getTblComments());

				locationsInterfaceAsync.getTbPerusahaanAll(tbPerusahaanAllCallback);
				generalInterfaceAsync.getTbNegaraAll(tbNegaraAllCallback);

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

	AsyncCallback<ReturnBean> submitLocationsCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbLocationBeanModel tbLocationBeanModel = (TbLocationBeanModel) result.get("model");
				hfId.setValue(tbLocationBeanModel.getTblId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					generalInterfaceAsync.getTbNegaraAll(tbNegaraAllCallback);

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

				WindowLocations.getInstance().bottomToolBar.refresh();
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

		cmbPerusahaan.setId("cmbPerusahaan");
		cmbPerusahaan.setFieldLabel("Company *");
		cmbPerusahaan.setEmptyText("Select");
		cmbPerusahaan.setDisplayField("value");
		cmbPerusahaan.setForceSelection(true);
		cmbPerusahaan.setTypeAhead(true);
		cmbPerusahaan.setTriggerAction(TriggerAction.ALL);
		cmbPerusahaan.setStore(cmbPerusahaanStore);
		cmbPerusahaan.setAllowBlank(false);
		formPanel.add(cmbPerusahaan, formData);

		formPanel.add(hfPerusahaanId, formData);

		cmbCountry.setId("cmbCountry");
		cmbCountry.setFieldLabel("Country *");
		cmbCountry.setEmptyText("Select");
		cmbCountry.setDisplayField("value");
		cmbCountry.setForceSelection(true);
		cmbCountry.setTypeAhead(true);
		cmbCountry.setTriggerAction(TriggerAction.ALL);
		cmbCountry.setStore(cmbCountryStore);
		cmbCountry.setAllowBlank(false);
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
		txtaAddress.setFieldLabel("Address *");
		txtaAddress.setAllowBlank(false);
		formPanel.add(txtaAddress, formData);

		txtZIPCode.setId("txtZIPCode");
		txtZIPCode.setFieldLabel("ZIP Code *");
		txtZIPCode.setAllowBlank(false);
		formPanel.add(txtZIPCode, formData);

		txtPhone.setId("txtPhone");
		txtPhone.setFieldLabel("Phone");
		txtPhone.setAllowBlank(true);
		formPanel.add(txtPhone, formData);

		txtFax.setId("txtFax");
		txtFax.setFieldLabel("Fax");
		txtFax.setAllowBlank(true);
		formPanel.add(txtFax, formData);

		txtaComments.setId("txtaComments");
		txtaComments.setFieldLabel("Comments");
		txtaComments.setAllowBlank(true);
		formPanel.add(txtaComments, formData);

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
					TbLocationBeanModel tbLocationBeanModel = new TbLocationBeanModel();
					tbLocationBeanModel.setTblId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					tbLocationBeanModel.setTblName(StringUtil.getInstance().getString(txtName.getValue()));
					tbLocationBeanModel.setTbpId(Integer.parseInt(cmbPerusahaan.getValue().get("id").toString()));
					tbLocationBeanModel.setFkTbnId(Integer.parseInt(cmbCountry.getValue().get("id").toString()));
					tbLocationBeanModel.setTblProvince(StringUtil.getInstance().getString(txtProvince.getValue()));
					tbLocationBeanModel.setTblCity(StringUtil.getInstance().getString(txtCity.getValue()));
					tbLocationBeanModel.setTblAddress(StringUtil.getInstance().getString(txtaAddress.getValue()));
					tbLocationBeanModel.setTblZipCode(StringUtil.getInstance().getString(txtZIPCode.getValue()));
					tbLocationBeanModel.setTblPhone(StringUtil.getInstance().getString(txtPhone.getValue()));
					tbLocationBeanModel.setTblFax(StringUtil.getInstance().getString(txtFax.getValue()));
					tbLocationBeanModel.setTblComments(StringUtil.getInstance().getString(txtaComments.getValue()));
					locationsInterfaceAsync.submitLocations(tbLocationBeanModel, submitLocationsCallback);
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
				locationsInterfaceAsync.getLocation(intTblId, getLocationsCallback);

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
				WindowLocations window = WindowLocations.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfPerusahaanId.setValue("");
		hfCountryId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			locationsInterfaceAsync.getTbPerusahaanAll(tbPerusahaanAllCallback);
			generalInterfaceAsync.getTbNegaraAll(tbNegaraAllCallback);

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

			locationsInterfaceAsync.getLocation(intTblId, getLocationsCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
