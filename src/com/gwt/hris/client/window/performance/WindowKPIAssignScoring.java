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
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbKpiAssignBeanModel;
import com.gwt.hris.client.service.bean.ViewKpiAssignBeanModel;
import com.gwt.hris.client.service.performance.KPIAssignInterface;
import com.gwt.hris.client.service.performance.KPIAssignInterfaceAsync;
import com.gwt.hris.client.service.performance.KPIInterface;
import com.gwt.hris.client.service.performance.KPIInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowKPIAssignScoring extends WindowMain {
	private static WindowKPIAssignScoring instance = new WindowKPIAssignScoring();

	public static WindowKPIAssignScoring getInstance() {
		return instance;
	}

	public WindowKPIAssignScoring() {
		window = new Window();
		window.setId("WindowKPIAssignScoring");
		window.setSize(770, 380);
		window.setHeading("Performance : KPI Assign");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	KPIAssignInterfaceAsync kpiAssignInterfaceAsync = GWT.create(KPIAssignInterface.class);
	KPIInterfaceAsync kpiInterfaceAsync = GWT.create(KPIInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();

	LabelField lblKpiGroup = new LabelField();
	LabelField lblDescription = new LabelField();
	LabelField lblTargetNilai1 = new LabelField();
	LabelField lblTargetNilai2 = new LabelField();
	LabelField lblTargetNilai3 = new LabelField();
	LabelField lblTargetNilai4 = new LabelField();
	LabelField lblTargetNilai5 = new LabelField();
	LabelField lblBobot = new LabelField();
	TextField<String> txtEvaluation = new TextField<String>();
	TextField<String> txtSuggestedAction = new TextField<String>();
	
	ComboBox<ComboBoxData> cmbPoin = new ComboBox<ComboBoxData>();
	ComboBoxData cmbPoinSelectedData;
	ListStore<ComboBoxData> cmbPoinStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfPoinId = new HiddenField<String>();
	
	public void fillCmbStatus() {
		cmbPoinStore.removeAll();

		cmbPoinStore.add(new ComboBoxData("0", "0"));
		cmbPoinStore.add(new ComboBoxData("1", "1"));
		cmbPoinStore.add(new ComboBoxData("2", "2"));
		cmbPoinStore.add(new ComboBoxData("3", "3"));
		cmbPoinStore.add(new ComboBoxData("4", "4"));
		cmbPoinStore.add(new ComboBoxData("5", "5"));

		if (hfPoinId.getValue() != null) {
			if (!"".equals(hfPoinId.getValue())) {
				if (0 == Integer.parseInt(hfPoinId.getValue())) {
					cmbPoinSelectedData = new ComboBoxData("0", "0");
					cmbPoin.setValue(cmbPoinSelectedData);
				} else if (1 == Integer.parseInt(hfPoinId.getValue())) {
					cmbPoinSelectedData = new ComboBoxData("1", "1");
					cmbPoin.setValue(cmbPoinSelectedData);
				}  else if (2 == Integer.parseInt(hfPoinId.getValue())) {
					cmbPoinSelectedData = new ComboBoxData("2", "2");
					cmbPoin.setValue(cmbPoinSelectedData);
				} else if (3 == Integer.parseInt(hfPoinId.getValue())) {
					cmbPoinSelectedData = new ComboBoxData("3", "3");
					cmbPoin.setValue(cmbPoinSelectedData);
				} else if (4 == Integer.parseInt(hfPoinId.getValue())) {
					cmbPoinSelectedData = new ComboBoxData("4", "4");
					cmbPoin.setValue(cmbPoinSelectedData);
				} else if (5 == Integer.parseInt(hfPoinId.getValue())) {
					cmbPoinSelectedData = new ComboBoxData("5", "5");
					cmbPoin.setValue(cmbPoinSelectedData);
				}
			}
		}
	}
	
	AsyncCallback<ViewKpiAssignBeanModel> getCallback = new AsyncCallback<ViewKpiAssignBeanModel>() {
		@Override
		public void onSuccess(ViewKpiAssignBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbkaId().toString());

				lblKpiGroup.setValue(result.getTbkgName());
				lblDescription.setValue(result.getTbkDescription());
				lblTargetNilai1.setValue(result.getTbkTargetNilai1());
				lblTargetNilai2.setValue(result.getTbkTargetNilai2());
				lblTargetNilai3.setValue(result.getTbkTargetNilai3());
				lblTargetNilai4.setValue(result.getTbkTargetNilai4());
				lblTargetNilai5.setValue(result.getTbkTargetNilai5());
				lblBobot.setValue(result.getTbkBobot().toString());
				txtEvaluation.setValue(result.getTbkaEvaluasi() == null ? "" : result.getTbkaEvaluasi().toString());
				txtSuggestedAction.setValue(result.getTbkaAction() == null ? "" : result.getTbkaAction().toString());
				
				hfPoinId.setValue(result.getTbkaPoin() == null ? "" : result.getTbkaPoin().toString());
				
				fillCmbStatus();

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
			if (result.getOperationStatus()) {
				formPanel.unmask();
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbKpiAssignBeanModel beanModel = (TbKpiAssignBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbkaId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
					
					fillCmbStatus();
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowKPIAssign.getInstance().bottomToolBar.refresh();
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

		lblKpiGroup.setId("lblKpiGroup");
		lblKpiGroup.setFieldLabel("Kpi Group");
		formPanel.add(lblKpiGroup, formData);
		
		lblDescription.setId("lblDescription");
		lblDescription.setFieldLabel("Description");
		formPanel.add(lblDescription, formData);

		lblTargetNilai1.setId("lblTargetNilai1");
		lblTargetNilai1.setFieldLabel("Score 1");
		formPanel.add(lblTargetNilai1, formData);

		lblTargetNilai2.setId("lblTargetNilai2");
		lblTargetNilai2.setFieldLabel("Score 2");
		formPanel.add(lblTargetNilai2, formData);

		lblTargetNilai3.setId("lblTargetNilai3");
		lblTargetNilai3.setFieldLabel("Score 3");
		formPanel.add(lblTargetNilai3, formData);

		lblTargetNilai4.setId("lblTargetNilai4");
		lblTargetNilai4.setFieldLabel("Score 4");
		formPanel.add(lblTargetNilai4, formData);

		lblTargetNilai5.setId("lblTargetNilai5");
		lblTargetNilai5.setFieldLabel("Score 5");
		formPanel.add(lblTargetNilai5, formData);

		lblBobot.setId("lblBobot");
		lblBobot.setFieldLabel("Weight (%)");
		formPanel.add(lblBobot, formData);
		
		cmbPoin.setId("cmbPoin");
		cmbPoin.setFieldLabel("Score *");
		cmbPoin.setEmptyText("Select");
		cmbPoin.setDisplayField("value");
		cmbPoin.setForceSelection(true);
		cmbPoin.setTypeAhead(true);
		cmbPoin.setTriggerAction(TriggerAction.ALL);
		cmbPoin.setStore(cmbPoinStore);
		cmbPoin.setAllowBlank(false);
		formPanel.add(cmbPoin, formData);

		txtEvaluation.setId("txtEvaluation");
		txtEvaluation.setFieldLabel("Evaluation *");
		txtEvaluation.setAllowBlank(false);
		formPanel.add(txtEvaluation, formData);

		txtSuggestedAction.setId("txtSuggestedAction");
		txtSuggestedAction.setFieldLabel("Suggested Action *");
		txtSuggestedAction.setAllowBlank(false);
		formPanel.add(txtSuggestedAction, formData);

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
					TbKpiAssignBeanModel beanModel = new TbKpiAssignBeanModel();
					beanModel.setTbkaId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbkaEvaluasi(StringUtil.getInstance().getString(txtEvaluation.getValue()));
					beanModel.setTbkaAction(StringUtil.getInstance().getString(txtSuggestedAction.getValue()));
					beanModel.setTbkaPoin(Integer.parseInt(cmbPoin.getValue().get("id").toString()));
					kpiAssignInterfaceAsync.submitKpiScoring(beanModel, submitCallback);
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

				kpiAssignInterfaceAsync.getViewKpiAssign(id, getCallback);

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
				WindowKPIAssign window = WindowKPIAssign.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfPoinId.setValue("");
		
		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);
			
			fillCmbStatus();
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			kpiAssignInterfaceAsync.getViewKpiAssign(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
	
	@Override
	public void doUnlockedComponent(Window window) {
		super.doUnlockedComponent(window);
	}
}
