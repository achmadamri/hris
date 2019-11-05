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
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.companyinfo.CompanyStructureInterface;
import com.gwt.hris.client.service.admin.companyinfo.CompanyStructureInterfaceAsync;
import com.gwt.hris.client.service.admin.companyinfo.LocationsInterface;
import com.gwt.hris.client.service.admin.companyinfo.LocationsInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbOrganizationBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowCompanyStructureAddEdit extends WindowMain {
	private static WindowCompanyStructureAddEdit instance = new WindowCompanyStructureAddEdit();

	public static WindowCompanyStructureAddEdit getInstance() {
		return instance;
	}

	public WindowCompanyStructureAddEdit() {
		window = new Window();
		window.setId("WindowCompanyStructureAddEdit");
		window.setSize(400, 160);
		window.setHeading("Company Info : Company Structure");
	}

	public String strNav = "";
	public int intTblId = 0;

	public void show(String strNav, int intTblId) {
		this.strNav = strNav;
		this.intTblId = intTblId;

		super.show(true);
	}

	CompanyStructureInterfaceAsync companyStructureInterfaceAsync = GWT.create(CompanyStructureInterface.class);
	LocationsInterfaceAsync locationsInterfaceAsync = GWT.create(LocationsInterface.class);
	
	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtNama = new TextField<String>();
	ComboBox<ComboBoxData> cmbParent = new ComboBox<ComboBoxData>();
	ComboBoxData cmbParentSelectedData;
	ListStore<ComboBoxData> cmbParentStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfParentId = new HiddenField<String>();
	
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
	
	AsyncCallback<TbOrganizationBeanModel> getTbOrganizationsCallback = new AsyncCallback<TbOrganizationBeanModel>() {
		@Override
		public void onSuccess(TbOrganizationBeanModel result) {
			if (result.getOperationStatus()) {
				cmbParentStore.removeAll();

				cmbParentStore.add(new ComboBoxData("0", "ROOT (0)"));

				for (TbOrganizationBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTboId().toString(), obj.getTboNama() + " (" + obj.getTboId().toString() + ")");
					cmbParentStore.add(data);

					if (hfParentId.getValue() != null) {
						if (obj.getTboId() == Integer.parseInt(hfParentId.getValue())) {
							cmbParentSelectedData = data;
							cmbParent.setValue(cmbParentSelectedData);
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

	AsyncCallback<TbOrganizationBeanModel> getTbOrganizationCallback = new AsyncCallback<TbOrganizationBeanModel>() {
		@Override
		public void onSuccess(TbOrganizationBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTboId().toString());
				txtNama.setValue(result.getTboNama());
				hfPerusahaanId.setValue(result.getTbpId().toString());

				if (result.getTboParentId() != null) {
					hfParentId.setValue(result.getTboParentId().toString());
				}

				locationsInterfaceAsync.getTbPerusahaanAll(tbPerusahaanAllCallback);
				companyStructureInterfaceAsync.getTbOrganizations(null, getTbOrganizationsCallback);
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

	AsyncCallback<ReturnBean> submitTbOrganizationCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

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
				
				companyStructureInterfaceAsync.getTbOrganizations(null, getTbOrganizationsCallback);

				WindowCompanyStructure.getInstance().loader.load();
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
		
		cmbParent.setId("cmbParent");
		cmbParent.setFieldLabel("Parent *");
		cmbParent.setEmptyText("Select");
		cmbParent.setDisplayField("value");
		cmbParent.setForceSelection(true);
		cmbParent.setTypeAhead(true);
		cmbParent.setTriggerAction(TriggerAction.ALL);
		cmbParent.setStore(cmbParentStore);
		cmbParent.setAllowBlank(false);
		formPanel.add(cmbParent, formData);

		txtNama.setId("txtNama");
		txtNama.setFieldLabel("Name *");
		txtNama.setAllowBlank(false);
		formPanel.add(txtNama, formData);

		formPanel.add(hfParentId, formData);

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
					TbOrganizationBeanModel tbOrganizationBeanModel = new TbOrganizationBeanModel();
					tbOrganizationBeanModel.setTboId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					tbOrganizationBeanModel.setTboParentId(Integer.parseInt(cmbParent.getValue().get("id").toString()));
					tbOrganizationBeanModel.setTboNama(StringUtil.getInstance().getString(txtNama.getValue()));
					tbOrganizationBeanModel.setTbpId(Integer.parseInt(cmbPerusahaan.getValue().get("id").toString()));
					
					companyStructureInterfaceAsync.submitTbOrganization(tbOrganizationBeanModel, submitTbOrganizationCallback);

					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
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
				companyStructureInterfaceAsync.getTbOrganization(intTblId, getTbOrganizationCallback);

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
				WindowCompanyStructure window = WindowCompanyStructure.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfPerusahaanId.setValue("");
		hfParentId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			locationsInterfaceAsync.getTbPerusahaanAll(tbPerusahaanAllCallback);
			companyStructureInterfaceAsync.getTbOrganizations(null, getTbOrganizationsCallback);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			companyStructureInterfaceAsync.getTbOrganization(intTblId, getTbOrganizationCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
