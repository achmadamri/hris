package com.gwt.hris.client.window.admin.projectinfo;

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
import com.gwt.hris.client.service.admin.projectinfo.BudgetResourcesGroupInterface;
import com.gwt.hris.client.service.admin.projectinfo.BudgetResourcesGroupInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbBudgetResourcesGroupBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowBudgetResourcesGroupAddEdit extends WindowMain {
	private static WindowBudgetResourcesGroupAddEdit instance = new WindowBudgetResourcesGroupAddEdit();

	public static WindowBudgetResourcesGroupAddEdit getInstance() {
		return instance;
	}

	public WindowBudgetResourcesGroupAddEdit() {
		window = new Window();
		window.setId("WindowBudgetResourcesGroupAddEdit");
		window.setSize(500, 145);
		window.setHeading("Project Info : Budget Resources Group");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	BudgetResourcesGroupInterfaceAsync interfaceAsync = GWT.create(BudgetResourcesGroupInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();

	AsyncCallback<TbBudgetResourcesGroupBeanModel> getCallback = new AsyncCallback<TbBudgetResourcesGroupBeanModel>() {
		@Override
		public void onSuccess(TbBudgetResourcesGroupBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbbrgId().toString());

				lblCode.setValue(result.getTbbrgBudgetResourcesGroupId());
				txtName.setValue(result.getTbbrgName());

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

				TbBudgetResourcesGroupBeanModel beanModel = (TbBudgetResourcesGroupBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbbrgId().toString());

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

				WindowBudgetResourcesGroup.getInstance().bottomToolBar.refresh();
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

		txtName.setId("txtName");
		txtName.setFieldLabel("Name *");
		txtName.setAllowBlank(false);
		formPanel.add(txtName, formData);

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
					TbBudgetResourcesGroupBeanModel beanModel = new TbBudgetResourcesGroupBeanModel();
					beanModel.setTbbrgId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbbrgName(StringUtil.getInstance().getString(txtName.getValue()));
					interfaceAsync.submitBudgetResourcesGroup(beanModel, submitCallback);
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

				interfaceAsync.getBudgetResourcesGroup(id, getCallback);

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

			interfaceAsync.getBudgetResourcesGroup(id, getCallback);

			btnEdit.setVisible(true);
			btnClose.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
