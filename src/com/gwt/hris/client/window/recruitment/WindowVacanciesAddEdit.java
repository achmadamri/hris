package com.gwt.hris.client.window.recruitment;

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
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbVacancyBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.service.recruitment.VacanciesInterface;
import com.gwt.hris.client.service.recruitment.VacanciesInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowVacanciesAddEdit extends WindowMain {
	private static WindowVacanciesAddEdit instance = new WindowVacanciesAddEdit();

	public static WindowVacanciesAddEdit getInstance() {
		return instance;
	}

	public WindowVacanciesAddEdit() {
		window = new Window();
		window.setId("WindowVacanciesAddEdit");
		window.setSize(550, 500);
		window.setHeading("Recruitment : Vacancies");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	VacanciesInterfaceAsync interfaceAsync = GWT.create(VacanciesInterface.class);
	EmployeeListInterfaceAsync employeeListInterfaceAsync = GWT.create(EmployeeListInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtVacancyName = new TextField<String>();

	ComboBox<ComboBoxData> cmbJobTitle = new ComboBox<ComboBoxData>();
	ComboBoxData cmbJobTitleSelectedData;
	ListStore<ComboBoxData> cmbJobTitleStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfJobTitleId = new HiddenField<String>();
	
	ComboBox<ComboBoxData> cmbEmployee;
	ComboBoxData cmbEmployeeSelectedData;
	ListStore<ComboBoxData> cmbEmployeeStore;
	HiddenField<String> hfEmployeeId;
	
	TextField<String> txtNoPositions = new TextField<String>();
	
	TextArea txtaDescription = new TextArea();
	
	Radio rdoStatusOpen;
	Radio rdoStatusClosed;
	RadioGroup rdgStatus;
	
	Radio rdoPublishRssYes;
	Radio rdoPublishRssNo;
	RadioGroup rdgPublishRss;
	
	Radio rdoPublishWebYes;
	Radio rdoPublishWebNo;
	RadioGroup rdgPublishWeb;

	AsyncCallback<TbEmployeeBeanModel> tbEmployeeAllCallback = new AsyncCallback<TbEmployeeBeanModel>() {
		@Override
		public void onSuccess(TbEmployeeBeanModel result) {
			if (result.getOperationStatus()) {
				cmbEmployee.clear();
				cmbEmployeeStore.removeAll();
	
				for (TbEmployeeBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbeId().toString(), obj.getTbeName() + " (" + obj.getTbeEmployeeId() + ")");
					cmbEmployeeStore.add(data);
	
					if (hfEmployeeId.getValue() != null) {
						if (!"".equals(hfEmployeeId.getValue())) {
							if (obj.getTbeId() == Integer.parseInt(hfEmployeeId.getValue())) {
								cmbEmployeeSelectedData = data;
								cmbEmployee.setValue(cmbEmployeeSelectedData);
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
	
	AsyncCallback<TbJobTitleBeanModel> tbJobTitleAllCallback = new AsyncCallback<TbJobTitleBeanModel>() {
		@Override
		public void onSuccess(TbJobTitleBeanModel result) {
			if (result.getOperationStatus()) {
				cmbJobTitleStore.removeAll();

				for (TbJobTitleBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbjtId().toString(), obj.getTbjtName());
					cmbJobTitleStore.add(data);

					if (hfJobTitleId.getValue() != null) {
						if (!"".equals(hfJobTitleId.getValue())) {
							if (obj.getTbjtId() == Integer.parseInt(hfJobTitleId.getValue())) {
								cmbJobTitleSelectedData = data;
								cmbJobTitle.setValue(cmbJobTitleSelectedData);
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

	AsyncCallback<TbVacancyBeanModel> getCallback = new AsyncCallback<TbVacancyBeanModel>() {
		@Override
		public void onSuccess(TbVacancyBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbvId().toString());

				lblCode.setValue(result.getTbvVacancyId());
				txtVacancyName.setValue(result.getTbvName());
				txtNoPositions.setValue(result.getTbvNoPositions().toString());
				txtaDescription.setValue(result.getTbvDescription());
				hfJobTitleId.setValue(result.getTbjtId().toString());

				if (result.getTbvActive() == null) {
					rdgStatus.clear();
				} else {
					rdgStatus.setValue(result.getTbvActive() == 0 ? rdoStatusClosed : (result.getTbvActive() == 1 ? rdoStatusOpen : null));
				}

				if (result.getTbvPublishRss() == null) {
					rdgPublishRss.clear();
				} else {
					rdgPublishRss.setValue(result.getTbvPublishRss() == 0 ? rdoPublishRssNo : (result.getTbvPublishRss() == 1 ? rdoPublishRssYes : null));
				}

				if (result.getTbvPublishWeb() == null) {
					rdgPublishWeb.clear();
				} else {
					rdgPublishWeb.setValue(result.getTbvPublishWeb() == 0 ? rdoPublishWebNo : (result.getTbvPublishWeb() == 1 ? rdoPublishWebYes : null));
				}
				
				interfaceAsync.getTbJobTitleAll(tbJobTitleAllCallback);
				
				hfEmployeeId.setValue(result.getTbeId() == null ? "" : result.getTbeId().toString());
				employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);
				
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

				TbVacancyBeanModel beanModel = (TbVacancyBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbvId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);
					
					interfaceAsync.getTbJobTitleAll(tbJobTitleAllCallback);
					employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);
					
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

					isEdit = false;
				}

				WindowVacancies.getInstance().bottomToolBar.refresh();
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

	boolean isEdit = false;

	@Override
	public void addComponents() {
		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanel.add(lblCode, formData);
		
		cmbJobTitle.setId("cmbJobTitle");
		cmbJobTitle.setFieldLabel("Job Title *");
		cmbJobTitle.setEmptyText("Select");
		cmbJobTitle.setDisplayField("value");
		cmbJobTitle.setForceSelection(true);
		cmbJobTitle.setTypeAhead(true);
		cmbJobTitle.setTriggerAction(TriggerAction.ALL);
		cmbJobTitle.setStore(cmbJobTitleStore);
		cmbJobTitle.setAllowBlank(false);
		formPanel.add(cmbJobTitle, formData);
		formPanel.add(hfJobTitleId, formData);

		txtVacancyName.setId("txtVacancyName");
		txtVacancyName.setFieldLabel("Vacancy Name *");
		txtVacancyName.setAllowBlank(false);
		formPanel.add(txtVacancyName, formData);
		
		cmbEmployee = new ComboBox<ComboBoxData>();
		cmbEmployeeStore = new ListStore<ComboBoxData>();
		cmbEmployee.setId("cmbEmployee");
		cmbEmployee.setFieldLabel("Hiring Manager *");
		cmbEmployee.setEmptyText("Select");
		cmbEmployee.setDisplayField("value");
		cmbEmployee.setForceSelection(true);
		cmbEmployee.setTypeAhead(true);
		cmbEmployee.setTriggerAction(TriggerAction.ALL);
		cmbEmployee.setStore(cmbEmployeeStore);
		cmbEmployee.setAllowBlank(false);
		formPanel.add(cmbEmployee, formData);
		hfEmployeeId = new HiddenField<String>();
		formPanel.add(hfEmployeeId, formData);

		txtNoPositions.setId("txtNoPositions");
		txtNoPositions.setFieldLabel("No Positions *");
		txtNoPositions.setAllowBlank(false);
		formPanel.add(txtNoPositions, formData);

		txtaDescription.setId("txtaDescription");
		txtaDescription.setFieldLabel("Description *");
		txtaDescription.setAllowBlank(false);
		txtaDescription.setHeight("200px");
		formPanel.add(txtaDescription, formData);

		rdoStatusOpen = new Radio();
		rdoStatusOpen.setBoxLabel("Open");

		rdoStatusClosed = new Radio();
		rdoStatusClosed.setBoxLabel("Closed");

		rdgStatus = new RadioGroup();
		rdgStatus.setFieldLabel("Status *");
		rdgStatus.add(rdoStatusOpen);
		rdgStatus.add(rdoStatusClosed);
		formPanel.add(rdgStatus, formData);

		rdoPublishRssYes = new Radio();
		rdoPublishRssYes.setBoxLabel("Yes");

		rdoPublishRssNo = new Radio();
		rdoPublishRssNo.setBoxLabel("No");

		rdgPublishRss = new RadioGroup();
		rdgPublishRss.setFieldLabel("Publish RSS *");
		rdgPublishRss.add(rdoPublishRssYes);
		rdgPublishRss.add(rdoPublishRssNo);
		formPanel.add(rdgPublishRss, formData);

		rdoPublishWebYes = new Radio();
		rdoPublishWebYes.setBoxLabel("Yes");

		rdoPublishWebNo = new Radio();
		rdoPublishWebNo.setBoxLabel("No");

		rdgPublishWeb = new RadioGroup();
		rdgPublishWeb.setFieldLabel("Publish Web *");
		rdgPublishWeb.add(rdoPublishWebYes);
		rdgPublishWeb.add(rdoPublishWebNo);
		formPanel.add(rdgPublishWeb, formData);

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
					TbVacancyBeanModel beanModel = new TbVacancyBeanModel();
					beanModel.setTbvId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbvName(StringUtil.getInstance().getString(txtVacancyName.getValue()));
					
					if (cmbEmployee.getValue() != null)
						beanModel.setTbeId(Integer.parseInt(cmbEmployee.getValue().get("id").toString()));
					
					beanModel.setTbvNoPositions(StringUtil.getInstance().getInteger(txtNoPositions.getValue()));
					beanModel.setTbjtId(Integer.parseInt(cmbJobTitle.getValue().get("id").toString()));
					beanModel.setTbvDescription(StringUtil.getInstance().getString(txtaDescription.getValue()));
					beanModel.setTbvActive(rdoStatusClosed.getValue() == true ? 0 : (rdoStatusOpen.getValue() == true ? 1 : null));
					beanModel.setTbvPublishRss(rdoPublishRssNo.getValue() == true ? 0 : (rdoPublishRssYes.getValue() == true ? 1 : null));
					beanModel.setTbvPublishWeb(rdoPublishWebNo.getValue() == true ? 0 : (rdoPublishWebYes.getValue() == true ? 1 : null));
					interfaceAsync.submitVacancy(beanModel, submitCallback);
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

				isEdit = true;
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

				interfaceAsync.getVacancy(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);

				isEdit = false;
			}
		});
		window.addButton(btnCancel);

		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowVacancies window = WindowVacancies.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		formPanel.setLabelWidth(120);

		hfJobTitleId.setValue("");
		hfEmployeeId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			interfaceAsync.getTbJobTitleAll(tbJobTitleAllCallback);
			employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);
			
			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);
		} else if ("edit".equals(strNav)) {
			doLockedComponent(window);

			interfaceAsync.getVacancy(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);

			isEdit = false;
		}
	}
}
