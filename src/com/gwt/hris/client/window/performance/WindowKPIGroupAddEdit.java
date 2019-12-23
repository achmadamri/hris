package com.gwt.hris.client.window.performance;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbKpiGroupBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;
import com.gwt.hris.client.service.performance.KPIGroupInterface;
import com.gwt.hris.client.service.performance.KPIGroupInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowKPIGroupAddEdit extends WindowMain {
	private static WindowKPIGroupAddEdit instance = new WindowKPIGroupAddEdit();

	public static WindowKPIGroupAddEdit getInstance() {
		return instance;
	}

	public WindowKPIGroupAddEdit() {
		window = new Window();
		window.setId("WindowKpiGroupAddEdit");
		window.setSize(500, 150);
		window.setHeading("Performance : KPI Group");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	KPIGroupInterfaceAsync interfaceAsync = GWT.create(KPIGroupInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	
	ComboBox<ComboBoxData> cmbProject = new ComboBox<ComboBoxData>();
	ComboBoxData selectedData;
	ListStore<ComboBoxData> cmbProjectStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfProjectId = new HiddenField<String>();
	
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();
	
	AsyncCallback<TbProjectBeanModel> getTbProjectAllCallback = new AsyncCallback<TbProjectBeanModel>() {
		@Override
		public void onSuccess(TbProjectBeanModel result) {
			if (result.getOperationStatus()) {
				cmbProjectStore.removeAll();
				
				cmbProjectStore.add(new ComboBoxData("0", "-"));

				for (TbProjectBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpId().toString(), obj.getTbpProjectId() + " - " + obj.getTbpName());
					cmbProjectStore.add(data);

					if (hfProjectId.getValue() != null) {
						if (!"".equals(hfProjectId.getValue())) {
							if (obj.getTbpId() == Integer.parseInt(hfProjectId.getValue())) {
								selectedData = data;
								cmbProject.setValue(selectedData);
							}
						}
					} else {
						cmbProject.setValue(null);
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

	AsyncCallback<TbKpiGroupBeanModel> getCallback = new AsyncCallback<TbKpiGroupBeanModel>() {
		@Override
		public void onSuccess(TbKpiGroupBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbkgId().toString());

				lblCode.setValue(result.getTbkgKpiGroupId());
				txtName.setValue(result.getTbkgName());
				
				if (result.getTbpId() == null) {
					hfProjectId.setValue("");
				} else {
					hfProjectId.setValue(result.getTbpId().toString());
				}

				formPanel.unmask();
				btnEdit.setEnabled(true);
				btnBack.setEnabled(true);
				
				interfaceAsync.getTbProjectAll(getTbProjectAllCallback);
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

				TbKpiGroupBeanModel beanModel = (TbKpiGroupBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbkgId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
					
					interfaceAsync.getTbProjectAll(getTbProjectAllCallback);
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowKPIGroup.getInstance().bottomToolBar.refresh();
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
		
		cmbProject.setId("cmbProject");
		cmbProject.setFieldLabel("Project");
		cmbProject.setEmptyText("Select");
		cmbProject.setDisplayField("value");
		cmbProject.setForceSelection(true);
		cmbProject.setTypeAhead(true);
		cmbProject.setTriggerAction(TriggerAction.ALL);
		cmbProject.setStore(cmbProjectStore);
		cmbProject.setAllowBlank(true);
		formPanel.add(cmbProject, formData);

		formPanel.add(hfProjectId, formData);

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
					TbKpiGroupBeanModel beanModel = new TbKpiGroupBeanModel();
					beanModel.setTbkgId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbkgName(StringUtil.getInstance().getString(txtName.getValue()));
					beanModel.setTbpId(Integer.parseInt(cmbProject.getValue().get("id").toString()));
					interfaceAsync.submitKpiGroup(beanModel, submitCallback);
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

				interfaceAsync.getKpiGroup(id, getCallback);

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
				WindowKPIGroup window = WindowKPIGroup.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfProjectId.setValue("");
		
		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);
			
			interfaceAsync.getTbProjectAll(getTbProjectAllCallback);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getKpiGroup(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
