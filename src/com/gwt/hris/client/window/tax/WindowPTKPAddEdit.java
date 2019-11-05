package com.gwt.hris.client.window.tax;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbPtkpBeanModel;
import com.gwt.hris.client.service.tax.PTKPInterface;
import com.gwt.hris.client.service.tax.PTKPInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowPTKPAddEdit extends WindowMain {
	private static WindowPTKPAddEdit instance = new WindowPTKPAddEdit();

	public static WindowPTKPAddEdit getInstance() {
		return instance;
	}

	public WindowPTKPAddEdit() {
		window = new ContentPanel();
		window.setId("WindowPTKPAddEdit");
		window.setSize(500, 170);
		window.setHeading("Tax : PTKP");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	PTKPInterfaceAsync interfaceAsync = GWT.create(PTKPInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtStatus = new TextField<String>();
	TextField<String> txtKeterangan = new TextField<String>();
	TextField<String> txtJumlah = new TextField<String>();

	AsyncCallback<TbPtkpBeanModel> getCallback = new AsyncCallback<TbPtkpBeanModel>() {
		@Override
		public void onSuccess(TbPtkpBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbptkpId().toString());

				txtStatus.setValue(result.getTbptkpStatus());
				txtKeterangan.setValue(result.getTbptkpKeterangan());
				txtJumlah.setValue(StringUtil.getInstance().toString(result.getTbptkpJumlah()));

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

				TbPtkpBeanModel beanModel = (TbPtkpBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbptkpId().toString());

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

				WindowPTKP.getInstance().bottomToolBar.refresh();
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

		txtStatus.setId("txtName");
		txtStatus.setFieldLabel("Name *");
		txtStatus.setAllowBlank(false);
		formPanel.add(txtStatus, formData);

		txtKeterangan.setId("txtKeterangan");
		txtKeterangan.setFieldLabel("Keterangan *");
		txtKeterangan.setAllowBlank(false);
		formPanel.add(txtKeterangan, formData);

		txtJumlah.setId("txtJumlah");
		txtJumlah.setFieldLabel("Jumlah *");
		txtJumlah.setAllowBlank(false);
		formPanel.add(txtJumlah, formData);

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
					TbPtkpBeanModel beanModel = new TbPtkpBeanModel();
					beanModel.setTbptkpId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbptkpStatus(StringUtil.getInstance().getString(txtStatus.getValue()));
					beanModel.setTbptkpKeterangan(StringUtil.getInstance().getString(txtKeterangan.getValue()));
					beanModel.setTbptkpJumlah(StringUtil.getInstance().getInteger(txtJumlah.getValue()));
					interfaceAsync.submitPtkp(beanModel, submitCallback);
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

				interfaceAsync.getPtkp(id, getCallback);

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
				WindowPTKP window = WindowPTKP.getInstance();
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

			interfaceAsync.getPtkp(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
