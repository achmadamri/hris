package com.gwt.hris.client.window.admin.systemsettings;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.systemsettings.MenuInterface;
import com.gwt.hris.client.service.admin.systemsettings.MenuInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMenuBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowMenuAddEdit extends WindowMain {
	private static WindowMenuAddEdit instance = new WindowMenuAddEdit();

	public static WindowMenuAddEdit getInstance() {
		return instance;
	}

	public WindowMenuAddEdit() {
		window = new Window();
		window.setId("WindowMenuAddEdit");
		window.setSize(400, 200);
		window.setHeading("System Settings : Menu");
	}

	public String strNav = "";
	public int intTblId = 0;

	public void show(String strNav, int intTblId) {
		this.strNav = strNav;
		this.intTblId = intTblId;

		super.show(true);
	}

	MenuInterfaceAsync menuInterfaceAsync = GWT.create(MenuInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtNama = new TextField<String>();
	Radio rdoYes;
	Radio rdoNo;
	RadioGroup rdgEnabled;
	TextField<String> txtIndex = new TextField<String>();

	ComboBox<ComboBoxData> cmbMenu = new ComboBox<ComboBoxData>();
	ComboBoxData cmbMenuSelectedData;
	ListStore<ComboBoxData> cmbMenuStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfMenuId = new HiddenField<String>();

	AsyncCallback<TbMenuBeanModel> tbMenuAllCallback = new AsyncCallback<TbMenuBeanModel>() {
		@Override
		public void onSuccess(TbMenuBeanModel result) {
			if (result.getOperationStatus()) {
				cmbMenuStore.removeAll();

				cmbMenuStore.add(new ComboBoxData("0", "-"));

				for (TbMenuBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbmId().toString(), obj.getTbmNama() + " (" + obj.getTbmId() + ")");
					cmbMenuStore.add(data);

					if (hfMenuId.getValue() != null) {
						if (!"".equals(hfMenuId.getValue())) {
							if (obj.getTbmId() == Integer.parseInt(hfMenuId.getValue())) {
								cmbMenuSelectedData = data;
								cmbMenu.setValue(cmbMenuSelectedData);
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

	AsyncCallback<TbMenuBeanModel> getMenuCallback = new AsyncCallback<TbMenuBeanModel>() {
		@Override
		public void onSuccess(TbMenuBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbmId().toString());

				txtNama.setValue(result.getTbmNama());
				hfMenuId.setValue(result.getTbmParentId() == null ? null : result.getTbmParentId().toString());
				txtIndex.setValue(result.getTbmIndex());

				if (result.getTbmDisabled() == null) {
					rdgEnabled.clear();
				} else {
					rdgEnabled.setValue(result.getTbmDisabled() == 0 ? rdoYes : (result.getTbmDisabled() == 1 ? rdoNo : null));
				}

				menuInterfaceAsync.getTbMenuAll(tbMenuAllCallback);

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

	AsyncCallback<ReturnBean> submitMenuCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbMenuBeanModel tbMenuBeanModel = (TbMenuBeanModel) result.get("model");
				hfId.setValue(tbMenuBeanModel.getTbmId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					menuInterfaceAsync.getTbMenuAll(tbMenuAllCallback);

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

				WindowMenu.getInstance().bottomToolBar.refresh();
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
		txtNama.setId("txtName");
		txtNama.setFieldLabel("Name *");
		txtNama.setAllowBlank(false);
		formPanel.add(txtNama, formData);

		cmbMenu.setId("cmbCountry");
		cmbMenu.setFieldLabel("Parent");
		cmbMenu.setEmptyText("Select");
		cmbMenu.setDisplayField("value");
		cmbMenu.setForceSelection(true);
		cmbMenu.setTypeAhead(true);
		cmbMenu.setTriggerAction(TriggerAction.ALL);
		cmbMenu.setStore(cmbMenuStore);
		cmbMenu.setAllowBlank(true);
		formPanel.add(cmbMenu, formData);
		formPanel.add(hfMenuId, formData);

		txtIndex.setId("txtIndex");
		txtIndex.setFieldLabel("Index");
		txtIndex.setAllowBlank(true);
		formPanel.add(txtIndex, formData);

		rdoYes = new Radio();
		rdoYes.setBoxLabel("Yes");

		rdoNo = new Radio();
		rdoNo.setBoxLabel("No");

		rdgEnabled = new RadioGroup();
		rdgEnabled.setFieldLabel("Enabled");
		rdgEnabled.add(rdoYes);
		rdgEnabled.add(rdoNo);
		formPanel.add(rdgEnabled, formData);

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
					TbMenuBeanModel tbMenuBeanModel = new TbMenuBeanModel();
					tbMenuBeanModel.setTbmId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					tbMenuBeanModel.setTbmNama(StringUtil.getInstance().getString(txtNama.getValue()));
					tbMenuBeanModel.setTbmDisabled(rdoYes.getValue() == true ? 0 : (rdoNo.getValue() == true ? 1 : null));
					tbMenuBeanModel.setTbmParentId(cmbMenu.getValue() == null ? null : Integer.parseInt(cmbMenu.getValue().get("id").toString()));
					tbMenuBeanModel.setTbmIndex(StringUtil.getInstance().getString(txtIndex.getValue()));

					if (rdoNo.getValue() == true) {
						tbMenuBeanModel.setTbmIndex("9999");
					}

					menuInterfaceAsync.submitMenu(tbMenuBeanModel, submitMenuCallback);
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
				menuInterfaceAsync.getMenu(intTblId, getMenuCallback);

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
				WindowMenu window = WindowMenu.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfMenuId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			menuInterfaceAsync.getTbMenuAll(tbMenuAllCallback);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			menuInterfaceAsync.getMenu(intTblId, getMenuCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
