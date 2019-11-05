package com.gwt.hris.client.window.admin.companyinfo;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.companyinfo.CompanyPropertyInterface;
import com.gwt.hris.client.service.admin.companyinfo.CompanyPropertyInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCompanyPropertyBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowCompanyPropertyAddEdit extends WindowMain {
	private static WindowCompanyPropertyAddEdit instance = new WindowCompanyPropertyAddEdit();

	public static WindowCompanyPropertyAddEdit getInstance() {
		return instance;
	}

	public WindowCompanyPropertyAddEdit() {
		window = new Window();
		window.setId("WindowCompanyPropertyAddEdit");
		window.setSize(400, 150);
		window.setHeading("Company Info : Company Property");
	}

	public String strNav = "";
	public int intId = 0;

	public void show(String strNav, int intTblId) {
		this.strNav = strNav;
		this.intId = intTblId;

		super.show(true);
	}

	CompanyPropertyInterfaceAsync companyPropertyInterfaceAsync = GWT.create(CompanyPropertyInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtNama = new TextField<String>();

	AsyncCallback<TbCompanyPropertyBeanModel> getLocationsCallback = new AsyncCallback<TbCompanyPropertyBeanModel>() {
		@Override
		public void onSuccess(TbCompanyPropertyBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbcpId().toString());

				txtNama.setValue(result.getTbcpNama());

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

				TbCompanyPropertyBeanModel tbLocationBeanModel = (TbCompanyPropertyBeanModel) result.get("model");
				hfId.setValue(tbLocationBeanModel.getTbcpId().toString());

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

				WindowCompanyProperty.getInstance().bottomToolBar.refresh();
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
					TbCompanyPropertyBeanModel beanModel = new TbCompanyPropertyBeanModel();
					beanModel.setTbcpId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbcpNama(StringUtil.getInstance().getString(txtNama.getValue()));
					companyPropertyInterfaceAsync.submitCompanyProperty(beanModel, submitLocationsCallback);
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

				companyPropertyInterfaceAsync.getCompanyProperty(intId, getLocationsCallback);

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
				WindowCompanyProperty window = WindowCompanyProperty.getInstance();
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
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			companyPropertyInterfaceAsync.getCompanyProperty(intId, getLocationsCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
