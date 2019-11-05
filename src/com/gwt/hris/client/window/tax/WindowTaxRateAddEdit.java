package com.gwt.hris.client.window.tax;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbTarifPajakBeanModel;
import com.gwt.hris.client.service.tax.TarifPajakInterface;
import com.gwt.hris.client.service.tax.TarifPajakInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowTaxRateAddEdit extends WindowMain {
	private static WindowTaxRateAddEdit instance = new WindowTaxRateAddEdit();

	public static WindowTaxRateAddEdit getInstance() {
		return instance;
	}

	public WindowTaxRateAddEdit() {
		window = new Window();
		window.setId("WindowTaxRateAddEdit");
		window.setSize(500, 210);
		window.setHeading("Tax : Tax Rate");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	TarifPajakInterfaceAsync interfaceAsync = GWT.create(TarifPajakInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtPkpDari = new TextField<String>();
	TextField<String> txtPkpSampai = new TextField<String>();
	TextField<String> txtNpwp = new TextField<String>();
	TextField<String> txtNonNpwp = new TextField<String>();

	AsyncCallback<TbTarifPajakBeanModel> getCallback = new AsyncCallback<TbTarifPajakBeanModel>() {
		@Override
		public void onSuccess(TbTarifPajakBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbtpId().toString());

				txtPkpDari.setValue(StringUtil.getInstance().toString(result.getTbtpPkpDari()));
				txtPkpSampai.setValue(StringUtil.getInstance().toString(result.getTbtpPkpSampai()));
				txtNpwp.setValue(result.getTbtpNpwp().toString());
				txtNonNpwp.setValue(result.getTbtpNonNpwp().toString());

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

	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbTarifPajakBeanModel beanModel = (TbTarifPajakBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbtpId().toString());

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

				WindowTaxRate.getInstance().bottomToolBar.refresh();
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
		formPanel.setLabelWidth(115);

		txtPkpDari.setId("txtPkpDari");
		txtPkpDari.setFieldLabel("PKP Dari *");
		txtPkpDari.setAllowBlank(false);
		formPanel.add(txtPkpDari, formData);

		txtPkpSampai.setId("txtPkpSampai");
		txtPkpSampai.setFieldLabel("PKP Sampai *");
		txtPkpSampai.setAllowBlank(false);
		formPanel.add(txtPkpSampai, formData);

		txtNpwp.setId("txtNpwp");
		txtNpwp.setFieldLabel("NPWP(%) *");
		txtNpwp.setAllowBlank(false);
		formPanel.add(txtNpwp, formData);

		txtNonNpwp.setId("txtNonNpwp");
		txtNonNpwp.setFieldLabel("Non NPWP(%) *");
		txtNonNpwp.setAllowBlank(false);
		formPanel.add(txtNonNpwp, formData);

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
					TbTarifPajakBeanModel beanModel = new TbTarifPajakBeanModel();
					beanModel.setTbtpId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbtpPkpDari(StringUtil.getInstance().getDouble(txtPkpDari.getValue()));
					beanModel.setTbtpPkpSampai(StringUtil.getInstance().getDouble(txtPkpSampai.getValue()));
					beanModel.setTbtpNpwp(StringUtil.getInstance().getInteger(txtNpwp.getValue()));
					beanModel.setTbtpNonNpwp(StringUtil.getInstance().getInteger(txtNonNpwp.getValue()));
					interfaceAsync.submitTarifPajak(beanModel, submitCallback);
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

				interfaceAsync.getTarifPajak(id, getCallback);

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
				WindowTaxRate window = WindowTaxRate.getInstance();
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

			interfaceAsync.getTarifPajak(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
