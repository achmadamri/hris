package com.gwt.hris.client.window.performance;

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
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbKpiBeanModel;
import com.gwt.hris.client.service.bean.TbKpiGroupBeanModel;
import com.gwt.hris.client.service.performance.KPIInterface;
import com.gwt.hris.client.service.performance.KPIInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowKPIAddEdit extends WindowMain {
	private static WindowKPIAddEdit instance = new WindowKPIAddEdit();

	public static WindowKPIAddEdit getInstance() {
		return instance;
	}

	public WindowKPIAddEdit() {
		window = new Window();
		window.setId("WindowKPIAddEdit");
		window.setSize(770, 320);
		window.setHeading("Performance : KPI");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	KPIInterfaceAsync interfaceAsync = GWT.create(KPIInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();

	ComboBox<ComboBoxData> cmbKpiGroup = new ComboBox<ComboBoxData>();
	ComboBoxData selectedData;
	ListStore<ComboBoxData> cmbKpiGroupStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfKpiGroupId = new HiddenField<String>();

	TextField<String> txtDescription = new TextField<String>();
	TextField<String> txtTargetNilai1 = new TextField<String>();
	TextField<String> txtTargetNilai2 = new TextField<String>();
	TextField<String> txtTargetNilai3 = new TextField<String>();
	TextField<String> txtTargetNilai4 = new TextField<String>();
	TextField<String> txtaTargetNilai5 = new TextField<String>();
	TextField<String> txtBobot = new TextField<String>();

	AsyncCallback<TbKpiGroupBeanModel> getTbKpiGroupAllCallback = new AsyncCallback<TbKpiGroupBeanModel>() {
		@Override
		public void onSuccess(TbKpiGroupBeanModel result) {
			if (result.getOperationStatus()) {
				cmbKpiGroupStore.removeAll();

				for (TbKpiGroupBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbkgId().toString(), obj.getTbkgKpiGroupId() + " - " + obj.getTbkgName());
					cmbKpiGroupStore.add(data);

					if (hfKpiGroupId.getValue() != null) {
						if (!"".equals(hfKpiGroupId.getValue())) {
							if (obj.getTbkgId() == Integer.parseInt(hfKpiGroupId.getValue())) {
								selectedData = data;
								cmbKpiGroup.setValue(selectedData);
							}
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

	AsyncCallback<TbKpiBeanModel> getCallback = new AsyncCallback<TbKpiBeanModel>() {
		@Override
		public void onSuccess(TbKpiBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbkId().toString());

				txtDescription.setValue(result.getTbkDescription());
				txtTargetNilai1.setValue(result.getTbkTargetNilai1());
				txtTargetNilai2.setValue(result.getTbkTargetNilai2());
				txtTargetNilai3.setValue(result.getTbkTargetNilai3());
				txtTargetNilai4.setValue(result.getTbkTargetNilai4());
				txtaTargetNilai5.setValue(result.getTbkTargetNilai5());
				txtBobot.setValue(result.getTbkBobot().toString());
				hfKpiGroupId.setValue(result.getTbkgId().toString());

				formPanel.unmask();
				btnEdit.setEnabled(true);
				btnBack.setEnabled(true);

				interfaceAsync.getTbKpiGroupAll(getTbKpiGroupAllCallback);
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

				TbKpiBeanModel beanModel = (TbKpiBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbkId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);

					interfaceAsync.getTbKpiGroupAll(getTbKpiGroupAllCallback);
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowKPI.getInstance().bottomToolBar.refresh();
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
		cmbKpiGroup.setId("cmbKpiGroup");
		cmbKpiGroup.setFieldLabel("Kpi Group *");
		cmbKpiGroup.setEmptyText("Select");
		cmbKpiGroup.setDisplayField("value");
		cmbKpiGroup.setForceSelection(true);
		cmbKpiGroup.setTypeAhead(true);
		cmbKpiGroup.setTriggerAction(TriggerAction.ALL);
		cmbKpiGroup.setStore(cmbKpiGroupStore);
		cmbKpiGroup.setAllowBlank(false);
		formPanel.add(cmbKpiGroup, formData);

		formPanel.add(hfKpiGroupId, formData);

		txtDescription.setId("txtDescription");
		txtDescription.setFieldLabel("Description *");
		txtDescription.setAllowBlank(false);
		formPanel.add(txtDescription, formData);

		txtTargetNilai1.setId("txtTargetNilai1");
		txtTargetNilai1.setFieldLabel("Score 1");
		txtTargetNilai1.setAllowBlank(true);
		formPanel.add(txtTargetNilai1, formData);

		txtTargetNilai2.setId("txtTargetNilai2");
		txtTargetNilai2.setFieldLabel("Score 2");
		txtTargetNilai2.setAllowBlank(true);
		formPanel.add(txtTargetNilai2, formData);

		txtTargetNilai3.setId("txtTargetNilai3");
		txtTargetNilai3.setFieldLabel("Score 3");
		txtTargetNilai3.setAllowBlank(true);
		formPanel.add(txtTargetNilai3, formData);

		txtTargetNilai4.setId("txtTargetNilai4");
		txtTargetNilai4.setFieldLabel("Score 4");
		txtTargetNilai4.setAllowBlank(true);
		formPanel.add(txtTargetNilai4, formData);

		txtaTargetNilai5.setId("txtaTargetNilai5");
		txtaTargetNilai5.setFieldLabel("Score 5");
		txtaTargetNilai5.setAllowBlank(true);
		formPanel.add(txtaTargetNilai5, formData);

		txtBobot.setId("txtBobot");
		txtBobot.setFieldLabel("Weight (%) *");
		txtBobot.setAllowBlank(false);
		formPanel.add(txtBobot, formData);

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
					TbKpiBeanModel beanModel = new TbKpiBeanModel();
					beanModel.setTbkId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbkDescription(StringUtil.getInstance().getString(txtDescription.getValue()));
					beanModel.setTbkTargetNilai1(StringUtil.getInstance().getString(txtTargetNilai1.getValue()));
					beanModel.setTbkTargetNilai2(StringUtil.getInstance().getString(txtTargetNilai2.getValue()));
					beanModel.setTbkTargetNilai3(StringUtil.getInstance().getString(txtTargetNilai3.getValue()));
					beanModel.setTbkTargetNilai4(StringUtil.getInstance().getString(txtTargetNilai4.getValue()));
					beanModel.setTbkTargetNilai5(StringUtil.getInstance().getString(txtaTargetNilai5.getValue()));
					beanModel.setTbkBobot(StringUtil.getInstance().getInteger(txtBobot.getValue()));
					beanModel.setTbkgId(Integer.parseInt(cmbKpiGroup.getValue().get("id").toString()));
					interfaceAsync.submitKpi(beanModel, submitCallback);
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

				interfaceAsync.getKpi(id, getCallback);

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
				WindowKPI window = WindowKPI.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfKpiGroupId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			interfaceAsync.getTbKpiGroupAll(getTbKpiGroupAllCallback);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getKpi(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
