package com.gwt.hris.client.window.recruitment;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbApplicantsBeanModel;
import com.gwt.hris.client.service.bean.TbVacancyBeanModel;
import com.gwt.hris.client.service.recruitment.ApplicantsInterface;
import com.gwt.hris.client.service.recruitment.ApplicantsInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowApplicantsAddEdit extends WindowMain {
	private static WindowApplicantsAddEdit instance = new WindowApplicantsAddEdit();

	public static WindowApplicantsAddEdit getInstance() {
		return instance;
	}

	public WindowApplicantsAddEdit() {
		window = new Window();
		window.setId("WindowApplicantsAddEdit");
		window.setSize(550, 390);
		window.setHeading("Recruitment : Applicants");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	ApplicantsInterfaceAsync interfaceAsync = GWT.create(ApplicantsInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	
	TextField<String> txtFirstName = new TextField<String>();
	TextField<String> txtMiddleName = new TextField<String>();
	TextField<String> txtLastName = new TextField<String>();
	TextField<String> txtEmail = new TextField<String>();
	TextField<String> txtPhoneNo = new TextField<String>();
	TextField<String> txtMobileNo = new TextField<String>();

	ComboBox<ComboBoxData> cmbVacancy = new ComboBox<ComboBoxData>();
	ComboBoxData cmbVacancySelectedData;
	ListStore<ComboBoxData> cmbVacancyStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfVacancyId = new HiddenField<String>();
	
	TextArea txtaDescription = new TextArea();
	
	FileUploadField fufResumeFileName;
	HiddenField<String> hftbeResumeFileName;
	
	AsyncCallback<TbVacancyBeanModel> tbVacancyAllCallback = new AsyncCallback<TbVacancyBeanModel>() {
		@Override
		public void onSuccess(TbVacancyBeanModel result) {
			if (result.getOperationStatus()) {
				cmbVacancyStore.removeAll();

				for (TbVacancyBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbvId().toString(), obj.getTbvName());
					cmbVacancyStore.add(data);

					if (hfVacancyId.getValue() != null) {
						if (!"".equals(hfVacancyId.getValue())) {
							if (obj.getTbvId() == Integer.parseInt(hfVacancyId.getValue())) {
								cmbVacancySelectedData = data;
								cmbVacancy.setValue(cmbVacancySelectedData);
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

	AsyncCallback<TbApplicantsBeanModel> getCallback = new AsyncCallback<TbApplicantsBeanModel>() {
		@Override
		public void onSuccess(TbApplicantsBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbaId().toString());

				lblCode.setValue(result.getTbaApplicantsId());
				txtFirstName.setValue(result.getTbaFirstName());
				
				txtMiddleName.setValue(result.getTbaMiddleName());
				txtLastName.setValue(result.getTbaLastName());
				txtEmail.setValue(result.getTbaEmail());
				txtPhoneNo.setValue(result.getTbaPhone());
				txtMobileNo.setValue(result.getTbaMobile());
				
				txtaDescription.setValue(result.getTbaComments());
				hfVacancyId.setValue(result.getTbvId().toString());

				interfaceAsync.getTbVacancyAll(tbVacancyAllCallback);
				
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

				TbApplicantsBeanModel beanModel = (TbApplicantsBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbvId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);
					
					interfaceAsync.getTbVacancyAll(tbVacancyAllCallback);
					
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

				WindowApplicants.getInstance().bottomToolBar.refresh();
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
		
		cmbVacancy.setId("cmbVacancy");
		cmbVacancy.setFieldLabel("Job Title *");
		cmbVacancy.setEmptyText("Select");
		cmbVacancy.setDisplayField("value");
		cmbVacancy.setForceSelection(true);
		cmbVacancy.setTypeAhead(true);
		cmbVacancy.setTriggerAction(TriggerAction.ALL);
		cmbVacancy.setStore(cmbVacancyStore);
		cmbVacancy.setAllowBlank(false);
		formPanel.add(cmbVacancy, formData);
		formPanel.add(hfVacancyId, formData);

		txtFirstName.setId("txtFullName");
		txtFirstName.setFieldLabel("Full Name *");
		txtFirstName.setAllowBlank(false);
		formPanel.add(txtFirstName, formData);

		txtMiddleName.setId("txtMiddleName");
		txtMiddleName.setFieldLabel("Middle Name");
		txtMiddleName.setAllowBlank(true);
		formPanel.add(txtMiddleName, formData);

		txtLastName.setId("txtLastName");
		txtLastName.setFieldLabel("Last Name *");
		txtLastName.setAllowBlank(false);
		formPanel.add(txtLastName, formData);

		txtEmail.setId("txtEmail");
		txtEmail.setFieldLabel("Email *");
		txtEmail.setAllowBlank(false);
		formPanel.add(txtEmail, formData);

		txtPhoneNo.setId("txtPhoneNo");
		txtPhoneNo.setFieldLabel("Phone No");
		txtPhoneNo.setAllowBlank(true);
		formPanel.add(txtPhoneNo, formData);

		txtMobileNo.setId("txtMobileNo");
		txtMobileNo.setFieldLabel("Mobile No");
		txtMobileNo.setAllowBlank(true);
		formPanel.add(txtMobileNo, formData);

		txtaDescription.setId("txtaDescription");
		txtaDescription.setFieldLabel("Description");
		txtaDescription.setAllowBlank(true);
		formPanel.add(txtaDescription, formData);

		fufResumeFileName = new FileUploadField();
		fufResumeFileName.setName("uploadedfile");
		fufResumeFileName.setFieldLabel("Resume *");
		fufResumeFileName.setAllowBlank(false);
		formPanel.add(fufResumeFileName, formData);

		formPanel.setAction("hris/resumeupload");
		formPanel.setEncoding(Encoding.MULTIPART);
		formPanel.setMethod(Method.POST);

		hftbeResumeFileName = new HiddenField<String>();

		formPanel.addListener(Events.Submit, new Listener<FormEvent>() {
			@Override
			public void handleEvent(FormEvent be) {
				if (be.getResultHtml().contains("filename")) {
					hftbeResumeFileName.setValue(be.getResultHtml().replaceAll("filename:", ""));
					submitData();
				} else {
					MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + be.getResultHtml(), null);
				}
			}
		});

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
					formPanel.submit();
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

				interfaceAsync.getApplicants(id, getCallback);

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
				WindowApplicants window = WindowApplicants.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		formPanel.setLabelWidth(120);

		hfVacancyId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			interfaceAsync.getTbVacancyAll(tbVacancyAllCallback);
			
			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);
		} else if ("edit".equals(strNav)) {
			doLockedComponent(window);

			interfaceAsync.getApplicants(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);

			isEdit = false;
		}
	}

	public void submitData() {
		TbApplicantsBeanModel beanModel = new TbApplicantsBeanModel();
		beanModel.setTbaId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
		beanModel.setTbaFirstName(StringUtil.getInstance().getString(txtFirstName.getValue()));
		beanModel.setTbaMiddleName(StringUtil.getInstance().getString(txtMiddleName.getValue()));
		beanModel.setTbaLastName(StringUtil.getInstance().getString(txtLastName.getValue()));
		beanModel.setTbaEmail(StringUtil.getInstance().getString(txtEmail.getValue()));
		beanModel.setTbaPhone(StringUtil.getInstance().getString(txtPhoneNo.getValue()));
		beanModel.setTbaMobile(StringUtil.getInstance().getString(txtMobileNo.getValue()));
		beanModel.setTbvId(Integer.parseInt(cmbVacancy.getValue().get("id").toString()));
		beanModel.setTbaComments(StringUtil.getInstance().getString(txtaDescription.getValue()));
		beanModel.setTbaResumeFileName(StringUtil.getInstance().getString(hftbeResumeFileName.getValue()));
		beanModel.setTbaResumeFileNameExisting(StringUtil.getInstance().getString(fufResumeFileName.getValue()));
		interfaceAsync.submitApplicants(beanModel, submitCallback);
	}
}
