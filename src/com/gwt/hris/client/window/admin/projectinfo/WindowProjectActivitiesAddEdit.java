package com.gwt.hris.client.window.admin.projectinfo;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.projectinfo.ProjectActivitiesInterface;
import com.gwt.hris.client.service.admin.projectinfo.ProjectActivitiesInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbProjectActivitiesBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowProjectActivitiesAddEdit extends WindowMain {
	private static WindowProjectActivitiesAddEdit instance = new WindowProjectActivitiesAddEdit();

	public static WindowProjectActivitiesAddEdit getInstance() {
		return instance;
	}

	public WindowProjectActivitiesAddEdit() {
		window = new Window();
		window.setId("WindowProjectActivitiesAddEdit");
		window.setSize(500, 215);
		window.setHeading("Project Info : Activities");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	ProjectActivitiesInterfaceAsync interfaceAsync = GWT.create(ProjectActivitiesInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtName = new TextField<String>();
	TextArea txtaDescription = new TextArea();

	ComboBox<ComboBoxData> cmbProjectActivitiesGroup = new ComboBox<ComboBoxData>();
	ComboBoxData cmbProjectActivitiesGroupSelectedData;
	ListStore<ComboBoxData> cmbProjectActivitiesGroupStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfProjectActivitiesGroupId = new HiddenField<String>();

	AsyncCallback<TbProjectActivitiesGroupBeanModel> tbProjectActivitiesGroupAllCallback = new AsyncCallback<TbProjectActivitiesGroupBeanModel>() {
		@Override
		public void onSuccess(TbProjectActivitiesGroupBeanModel result) {
			if (result.getOperationStatus()) {
				cmbProjectActivitiesGroupStore.removeAll();

				for (TbProjectActivitiesGroupBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpagId().toString(), obj.getTbpagName());
					cmbProjectActivitiesGroupStore.add(data);

					if (hfProjectActivitiesGroupId.getValue() != null) {
						if (!"".equals(hfProjectActivitiesGroupId.getValue())) {
							if (obj.getTbpagId() == Integer.parseInt(hfProjectActivitiesGroupId.getValue())) {
								cmbProjectActivitiesGroupSelectedData = data;
								cmbProjectActivitiesGroup.setValue(cmbProjectActivitiesGroupSelectedData);
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

	AsyncCallback<TbProjectActivitiesBeanModel> getCallback = new AsyncCallback<TbProjectActivitiesBeanModel>() {
		@Override
		public void onSuccess(TbProjectActivitiesBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbpaId().toString());

				hfProjectActivitiesGroupId.setValue(result.getTbpagId().toString());

				txtName.setValue(result.getTbpaName());
				txtaDescription.setValue(result.getTbpaDescription());

				interfaceAsync.getTbProjectActivitiesGroupAll(tbProjectActivitiesGroupAllCallback);

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

				TbProjectActivitiesBeanModel beanModel = (TbProjectActivitiesBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbpaId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					interfaceAsync.getTbProjectActivitiesGroupAll(tbProjectActivitiesGroupAllCallback);

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

				WindowProjectActivities.getInstance().bottomToolBar.refresh();
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
		cmbProjectActivitiesGroup.setId("cmbCountry");
		cmbProjectActivitiesGroup.setFieldLabel("Activity Group *");
		cmbProjectActivitiesGroup.setEmptyText("Select");
		cmbProjectActivitiesGroup.setDisplayField("value");
		cmbProjectActivitiesGroup.setForceSelection(true);
		cmbProjectActivitiesGroup.setTypeAhead(true);
		cmbProjectActivitiesGroup.setTriggerAction(TriggerAction.ALL);
		cmbProjectActivitiesGroup.setStore(cmbProjectActivitiesGroupStore);
		cmbProjectActivitiesGroup.setAllowBlank(false);
		formPanel.add(cmbProjectActivitiesGroup, formData);
		formPanel.add(hfProjectActivitiesGroupId, formData);

		txtName.setId("txtName");
		txtName.setFieldLabel("Name *");
		txtName.setAllowBlank(false);
		formPanel.add(txtName, formData);

		txtaDescription.setId("txtaComments");
		txtaDescription.setFieldLabel("Description");
		txtaDescription.setAllowBlank(true);
		formPanel.add(txtaDescription, formData);

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
					TbProjectActivitiesBeanModel beanModel = new TbProjectActivitiesBeanModel();
					beanModel.setTbpaId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbpaName(StringUtil.getInstance().getString(txtName.getValue()));
					beanModel.setTbpagId(Integer.parseInt(cmbProjectActivitiesGroup.getValue().get("id").toString()));
					beanModel.setTbpaDescription(StringUtil.getInstance().getString(txtaDescription.getValue()));
					interfaceAsync.submitProjectActivities(beanModel, submitCallback);
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

				interfaceAsync.getProjectActivities(id, getCallback);

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
				WindowProjectActivities window = WindowProjectActivities.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfProjectActivitiesGroupId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			interfaceAsync.getTbProjectActivitiesGroupAll(tbProjectActivitiesGroupAllCallback);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getProjectActivities(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
