package com.gwt.hris.client.window.admin.job;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.job.EeoJobCategoryInterface;
import com.gwt.hris.client.service.admin.job.EeoJobCategoryInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEeoJobCategoryBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowEeoJobCategoryAddEdit extends WindowMain {
	private static WindowEeoJobCategoryAddEdit instance = new WindowEeoJobCategoryAddEdit();

	public static WindowEeoJobCategoryAddEdit getInstance() {
		return instance;
	}

	public WindowEeoJobCategoryAddEdit() {
		window = new Window();
		window.setId("WindowEeoJobCategoryAddEdit");
		window.setSize(500, 145);
		window.setHeading("Job : EEO Job Category");
	}

	public String strNav = "";
	public int intTbejcId = 0;

	public void show(String strNav, int intTbejcId) {
		this.strNav = strNav;
		this.intTbejcId = intTbejcId;

		super.show(true);
	}

	EeoJobCategoryInterfaceAsync interfaceAsync = GWT.create(EeoJobCategoryInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtTitle = new TextField<String>();

	AsyncCallback<TbEeoJobCategoryBeanModel> getCallback = new AsyncCallback<TbEeoJobCategoryBeanModel>() {
		@Override
		public void onSuccess(TbEeoJobCategoryBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbejcId().toString());

				lblCode.setValue(result.getTbejcEeoJobCategoryId());
				txtTitle.setValue(result.getTbejcTitle());

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

	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbEeoJobCategoryBeanModel beanModel = (TbEeoJobCategoryBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbejcId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					btnEdit.setVisible(false);
					btnClose.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnClose.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowEeoJobCategory.getInstance().bottomToolBar.refresh();
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
		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanel.add(lblCode, formData);

		txtTitle.setId("txtTitle");
		txtTitle.setFieldLabel("Title *");
		txtTitle.setAllowBlank(false);
		formPanel.add(txtTitle, formData);

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
					TbEeoJobCategoryBeanModel beanModel = new TbEeoJobCategoryBeanModel();
					beanModel.setTbejcId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbejcTitle(StringUtil.getInstance().getString(txtTitle.getValue()));
					interfaceAsync.submitEeoJobCategory(beanModel, submitCallback);
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
				btnClose.setVisible(false);
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
				btnClose.setEnabled(false);

				doLockedComponent(window);

				interfaceAsync.getEeoJobCategory(intTbejcId, getCallback);

				btnEdit.setVisible(true);
				btnClose.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		window.addButton(btnCancel);

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
		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnClose.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnClose.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getEeoJobCategory(intTbejcId, getCallback);

			btnEdit.setVisible(true);
			btnClose.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
