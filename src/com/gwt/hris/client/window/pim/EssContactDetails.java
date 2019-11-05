package com.gwt.hris.client.window.pim;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.companyinfo.GeneralInterface;
import com.gwt.hris.client.service.admin.companyinfo.GeneralInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbContactDetailsBeanModel;
import com.gwt.hris.client.service.bean.TbNegaraBeanModel;
import com.gwt.hris.client.service.pim.ContactDetailsInterface;
import com.gwt.hris.client.service.pim.ContactDetailsInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.MainTabLayout;

public class EssContactDetails extends EssMainFP {
	EssContactDetails formPanel = this;

	public EssContactDetails(FormData formData, int id_, int callerId_) {
		super(formData);

		id = id_;
		callerId = callerId_;
		
		if (this.callerId == 0) {
			tbAugId = WindowEss.getInstance().tbLoginBeanModel.getTbaugId();
		} else {
			tbAugId = WindowEmployeeListEss.getInstance().tbLoginBeanModel.getTbaugId();
		}

		addComponents();

		addButtons();

		formPanel.mask("loading");
		btnEdit.setEnabled(false);
		btnBack.setEnabled(false);

		doLockedComponent(formPanel);

		contactDetailsInterfaceAsync.getContactDetails(this.id, getCallback);

		btnEdit.setVisible(true);
		btnBack.setVisible(true);
		btnCancel.setVisible(false);
		btnSave.setVisible(false);
	}

	public int id = 0;
	public int callerId;
	public Integer tbAugId;

	ContactDetailsInterfaceAsync contactDetailsInterfaceAsync = GWT.create(ContactDetailsInterface.class);
	GeneralInterfaceAsync generalInterfaceAsync = GWT.create(GeneralInterface.class);

	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbContactDetailsBeanModel beanModel = (TbContactDetailsBeanModel) result.get("model");
				id = beanModel.getTbeId();

				formPanel.mask("loading");
				btnEdit.setEnabled(false);
				btnBack.setEnabled(false);

				doLockedComponent(formPanel);

				contactDetailsInterfaceAsync.getContactDetails(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);

				if (callerId == 1) {
					WindowEmployeeList.getInstance().refreshToolBar();
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

	AsyncCallback<TbContactDetailsBeanModel> getCallback = new AsyncCallback<TbContactDetailsBeanModel>() {
		@Override
		public void onSuccess(TbContactDetailsBeanModel result) {
			if (result.getOperationStatus()) {
				hfCountryId.setValue(result.getTbnId() == null ? "" : result.getTbnId().toString());

				txtAddress.setValue(result.getTbcdStreet());
				txtCity.setValue(result.getTbcdCity());
				txtProvince.setValue(result.getTbcdProvince());
				txtZipCode.setValue(result.getTbcdZipCode());
				txtHomePhone.setValue(result.getTbcdHomePhone());
				txtMobilePhone.setValue(result.getTbcdMobilePhone());
				if (tbAugId != null) {
					txtWorkPhone.setValue(result.getTbcdWorkPhone());
				} else {
					lblWorkPhone.setValue(result.getTbcdWorkPhone());
				}
				txtOtherEmail.setValue(result.getTbcdOtherEmail());

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

	AsyncCallback<TbNegaraBeanModel> tbNegaraAllCallback = new AsyncCallback<TbNegaraBeanModel>() {
		@Override
		public void onSuccess(TbNegaraBeanModel result) {
			if (result.getOperationStatus()) {
				cmbCountry.clear();
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

	ComboBox<ComboBoxData> cmbCountry;
	ComboBoxData cmbCountrySelectedData;
	ListStore<ComboBoxData> cmbCountryStore;
	HiddenField<String> hfCountryId;

	TextField<String> txtAddress;
	TextField<String> txtCity;
	TextField<String> txtProvince;
	TextField<String> txtZipCode;
	TextField<String> txtHomePhone;
	TextField<String> txtMobilePhone;
	TextField<String> txtWorkPhone;
	LabelField lblWorkPhone;
	TextField<String> txtOtherEmail;

	public void addComponents() {
		cmbCountry = new ComboBox<ComboBoxData>();
		cmbCountryStore = new ListStore<ComboBoxData>();
		cmbCountry.setId("cmbCountry");
		cmbCountry.setFieldLabel("Country");
		cmbCountry.setEmptyText("Select");
		cmbCountry.setDisplayField("value");
		cmbCountry.setForceSelection(true);
		cmbCountry.setTypeAhead(true);
		cmbCountry.setTriggerAction(TriggerAction.ALL);
		cmbCountry.setStore(cmbCountryStore);
		cmbCountry.setAllowBlank(true);
		add(cmbCountry, formData);

		txtAddress = new TextField<String>();
		txtAddress.setId("txtAddress");
		txtAddress.setFieldLabel("Address");
		txtAddress.setAllowBlank(true);
		add(txtAddress, formData);

		txtCity = new TextField<String>();
		txtCity.setId("txtCity");
		txtCity.setFieldLabel("City/Town");
		txtCity.setAllowBlank(true);
		add(txtCity, formData);

		txtProvince = new TextField<String>();
		txtProvince.setId("txtProvince");
		txtProvince.setFieldLabel("State/Province");
		txtProvince.setAllowBlank(true);
		add(txtProvince, formData);

		txtZipCode = new TextField<String>();
		txtZipCode.setId("txtZipCode");
		txtZipCode.setFieldLabel("Zip Code");
		txtZipCode.setAllowBlank(true);
		add(txtZipCode, formData);

		txtHomePhone = new TextField<String>();
		txtHomePhone.setId("txtHomePhone");
		txtHomePhone.setFieldLabel("Home Phone");
		txtHomePhone.setAllowBlank(true);
		add(txtHomePhone, formData);

		txtMobilePhone = new TextField<String>();
		txtMobilePhone.setId("txtMobilePhone");
		txtMobilePhone.setFieldLabel("Mobile Phone");
		txtMobilePhone.setAllowBlank(true);
		add(txtMobilePhone, formData);
		
		if (tbAugId != null) {
			txtWorkPhone = new TextField<String>();
			txtWorkPhone.setId("txtWorkPhone");
			txtWorkPhone.setFieldLabel("Work Phone");
			txtWorkPhone.setAllowBlank(true);
			add(txtWorkPhone, formData);
		} else {
			lblWorkPhone = new LabelField();
			lblWorkPhone.setId("lblWorkPhone");
			lblWorkPhone.setFieldLabel("Work Phone");
			add(lblWorkPhone, formData);
		}
		
		txtOtherEmail = new TextField<String>();
		txtOtherEmail.setId("txtOtherEmail");
		txtOtherEmail.setFieldLabel("Other Email");
		txtOtherEmail.setAllowBlank(true);
		add(txtOtherEmail, formData);

		hfCountryId = new HiddenField<String>();
		add(hfCountryId, formData);
	}

	Button btnSave;
	Button btnEdit;
	Button btnCancel;
	Button btnBack;

	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
					TbContactDetailsBeanModel beanModel = new TbContactDetailsBeanModel();
					beanModel.setTbeId(id);

					if (cmbCountry.getValue() != null)
						beanModel.setTbnId(Integer.parseInt(cmbCountry.getValue().get("id").toString()));

					beanModel.setTbcdStreet(StringUtil.getInstance().getString(txtAddress.getValue()));
					beanModel.setTbcdCity(StringUtil.getInstance().getString(txtCity.getValue()));
					beanModel.setTbcdProvince(StringUtil.getInstance().getString(txtProvince.getValue()));
					beanModel.setTbcdZipCode(StringUtil.getInstance().getString(txtZipCode.getValue()));
					beanModel.setTbcdHomePhone(StringUtil.getInstance().getString(txtHomePhone.getValue()));
					beanModel.setTbcdMobilePhone(StringUtil.getInstance().getString(txtMobilePhone.getValue()));
					if (tbAugId != null) {
						beanModel.setTbcdWorkPhone(StringUtil.getInstance().getString(txtWorkPhone.getValue()));
					}
					beanModel.setTbcdOtherEmail(StringUtil.getInstance().getString(txtOtherEmail.getValue()));

					contactDetailsInterfaceAsync.submitContactDetails(beanModel, submitCallback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty", null);
				}
			}
		});
		addButton(btnSave);

		btnEdit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				doUnlockedComponent(formPanel);

				btnEdit.setVisible(false);
				btnBack.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
			}
		});
		addButton(btnEdit);

		btnCancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				formPanel.mask("loading");

				doLockedComponent(formPanel);

				contactDetailsInterfaceAsync.getContactDetails(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		addButton(btnCancel);

		if (this.callerId == 0) {
			btnBack = new Button("Close", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					MainTabLayout.closeTab(WindowEss.getInstance().window.getHeading());
				}
			});
		} else {
			btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					WindowEmployeeList window = WindowEmployeeList.getInstance();
					window.show(true);
				}
			});
		}
		addButton(btnBack);
	}
}
