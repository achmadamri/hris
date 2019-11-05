package com.gwt.hris.client.window.pim;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.gwt.hris.client.service.admin.nationalityandraces.EthnicInterface;
import com.gwt.hris.client.service.admin.nationalityandraces.EthnicInterfaceAsync;
import com.gwt.hris.client.service.admin.nationalityandraces.NationalityInterface;
import com.gwt.hris.client.service.admin.nationalityandraces.NationalityInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbEthnicRacesBeanModel;
import com.gwt.hris.client.service.bean.TbNationalityBeanModel;
import com.gwt.hris.client.service.bean.TbPtkpBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.service.tax.PTKPInterface;
import com.gwt.hris.client.service.tax.PTKPInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.MainTabLayout;

public class EssPersonalDetails extends EssMainFP {
	EssPersonalDetails formPanel = this;

	public EssPersonalDetails(FormData formData, int id_, int callerId_) {
		super(formData);

		id = id_;
		callerId = callerId_;
		
		if (this.callerId == 0) {
			tbAugId = WindowEss.getInstance().tbLoginBeanModel.getTbaugId();
		} else {
			tbAugId = WindowEmployeeListEss.getInstance().tbLoginBeanModel.getTbaugId();
		}

		addComponents();

		addButtons();

		formPanel.setLabelWidth(110);

		formPanel.mask("loading");
		btnEdit.setEnabled(false);
		btnBack.setEnabled(false);

		doLockedComponent(formPanel);

		employeeListInterfaceAsync.getEmployee(this.id, getCallback);
		
		if (this.callerId == 0) {
			if (tbAugId != null) {
				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);			
			} else {
				btnEdit.setVisible(false);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		} else {
			if (WindowEmployeeListEss.getInstance().tbLoginBeanModel.getTbaugId() != null) {
				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);			
			} else {
				btnEdit.setVisible(false);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		}
	}

	public int id;
	public int callerId;
	public Integer tbAugId;

	EmployeeListInterfaceAsync employeeListInterfaceAsync = GWT.create(EmployeeListInterface.class);
	NationalityInterfaceAsync nationalityInterfaceAsync = GWT.create(NationalityInterface.class);
	EthnicInterfaceAsync ethnicInterfaceAsync = GWT.create(EthnicInterface.class);
	PTKPInterfaceAsync ptkpInterfaceAsync = GWT.create(PTKPInterface.class);

	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbEmployeeBeanModel beanModel = (TbEmployeeBeanModel) result.get("model");
				id = beanModel.getTbeId();

				formPanel.mask("loading");
				btnEdit.setEnabled(false);
				btnBack.setEnabled(false);

				doLockedComponent(formPanel);

				employeeListInterfaceAsync.getEmployee(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);

				if (callerId == 1) {
					WindowEmployeeList.getInstance().refreshToolBar();
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

	AsyncCallback<TbEmployeeBeanModel> getCallback = new AsyncCallback<TbEmployeeBeanModel>() {
		@Override
		public void onSuccess(TbEmployeeBeanModel result) {
			if (result.getOperationStatus()) {
				if (result.getTbePhotoFileName() != null) {
					fileName = result.getTbePhotoFileName();
					String ext = "";
					int mid = fileName.lastIndexOf(".");
					ext = fileName.substring(mid + 1, fileName.length());
					img.setUrl(folderPath + result.getTbePhotoFileName().replaceAll("." + ext, "") + "_thumb." + ext);
				}
				
				DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");

				lblCode.setValue(result.getTbeEmployeeId());
				lblJoinedDate.setValue(fmt.format(result.getTbeJoinedDate()));
				txtEmail.setValue(result.getTbeEmail());
				txtFirstName.setValue(result.getTbeFirstName());
				txtLastName.setValue(result.getTbeLastName());
				txtMiddleName.setValue(result.getTbeMiddleName());
				txtNickName.setValue(result.getTbeNickName());
				txtIDNo.setValue(result.getTbeIdNo());
				txtTaxNo.setValue(result.getTbeTaxNo());
				hfNationalityId.setValue(result.getTbnId() == null ? "" : result.getTbnId().toString());
				dateDateOfBirth.setValue(result.getTbeDob());

				if (result.getTbeMaritalStatus() == null) {
					rdgMaritalStatus.clear();
				} else {
					rdgMaritalStatus.setValue(result.getTbeMaritalStatus() == 0 ? rdoMarried : (result.getTbeMaritalStatus() == 1 ? rdoUnmarried : (result.getTbeMaritalStatus() == 2 ? rdoDivorced : (result.getTbeMaritalStatus() == 3 ? rdoOthers : null))));
				}

				hfPtkpId.setValue(result.getTbptkpId() == null ? "" : result.getTbptkpId().toString());

				if (result.getTbeSmoker() == null) {
					rdgSmoker.clear();
				} else {
					rdgSmoker.setValue(result.getTbeSmoker() == 0 ? rdoSmokerYes : (result.getTbeSmoker() == 1 ? rdoSmokerNo : null));
				}

				if (result.getTbeGender() == null) {
					rdgGender.clear();
				} else {
					rdgGender.setValue(result.getTbeGender() == 0 ? rdoMale : (result.getTbeGender() == 1 ? rdoFemale : null));
				}
				
				txtPhone.setValue(result.getTbePhone());
				txtMobile.setValue(result.getTbeMobile());

				if (result.getTbeStatus() == null) {
					rdgStatus.clear();
				} else {
					rdgStatus.setValue(result.getTbeStatus() == 0 ? rdoStatusActive : (result.getTbeStatus() == 1 ? rdoStatusNotActive : null));
				}

				txtDriversLicenseNumber.setValue(result.getTbeDriverLicenseNo());
				dateLicenseExpiryDate.setValue(result.getTbeDriverLicenseExpiry());
				hfEthnicRacesId.setValue(result.getTberId() == null ? "" : result.getTberId().toString());
				
				ptkpInterfaceAsync.getTbPtkpAll(tbPtkpAllCallback);
				nationalityInterfaceAsync.getTbNationalityAll(tbNationalityAllCallback);
				ethnicInterfaceAsync.getTbEthnicRacesAll(tbEthnicRacesAllCallback);

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

	AsyncCallback<TbPtkpBeanModel> tbPtkpAllCallback = new AsyncCallback<TbPtkpBeanModel>() {
		@Override
		public void onSuccess(TbPtkpBeanModel result) {
			if (result.getOperationStatus()) {
				cmbPtkp.clear();
				cmbPtkpStore.removeAll();

				for (TbPtkpBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbptkpId().toString(), obj.getTbptkpStatus() + ". " + obj.getTbptkpKeterangan());
					cmbPtkpStore.add(data);

					if (hfPtkpId.getValue() != null) {
						if (!"".equals(hfPtkpId.getValue())) {
							if (obj.getTbptkpId() == Integer.parseInt(hfPtkpId.getValue())) {
								cmbPtkpSelectedData = data;
								cmbPtkp.setValue(cmbPtkpSelectedData);
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

	AsyncCallback<TbNationalityBeanModel> tbNationalityAllCallback = new AsyncCallback<TbNationalityBeanModel>() {
		@Override
		public void onSuccess(TbNationalityBeanModel result) {
			if (result.getOperationStatus()) {
				cmbNationality.clear();
				cmbNationalityStore.removeAll();

				for (TbNationalityBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbnId().toString(), obj.getTbnName());
					cmbNationalityStore.add(data);

					if (hfNationalityId.getValue() != null) {
						if (!"".equals(hfNationalityId.getValue())) {
							if (obj.getTbnId() == Integer.parseInt(hfNationalityId.getValue())) {
								cmbNationalitySelectedData = data;
								cmbNationality.setValue(cmbNationalitySelectedData);
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

	AsyncCallback<TbEthnicRacesBeanModel> tbEthnicRacesAllCallback = new AsyncCallback<TbEthnicRacesBeanModel>() {
		@Override
		public void onSuccess(TbEthnicRacesBeanModel result) {
			if (result.getOperationStatus()) {
				cmbEthnicRaces.clear();
				cmbEthnicRacesStore.removeAll();

				for (TbEthnicRacesBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTberId().toString(), obj.getTberName());
					cmbEthnicRacesStore.add(data);

					if (hfEthnicRacesId.getValue() != null) {
						if (!"".equals(hfEthnicRacesId.getValue())) {
							if (obj.getTberId() == Integer.parseInt(hfEthnicRacesId.getValue())) {
								cmbEthnicRacesSelectedData = data;
								cmbEthnicRaces.setValue(cmbEthnicRacesSelectedData);
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

	String folderPath;
	String fileName;

	Image img;
	LabelField lblCode;
	TextField<String> txtEmail;
	TextField<String> txtFirstName;
	TextField<String> txtLastName;
	TextField<String> txtMiddleName;
	TextField<String> txtNickName;
	TextField<String> txtIDNo;
	TextField<String> txtTaxNo;

	ComboBox<ComboBoxData> cmbNationality;
	ComboBoxData cmbNationalitySelectedData;
	ListStore<ComboBoxData> cmbNationalityStore;
	HiddenField<String> hfNationalityId;

	DateField dateDateOfBirth;

	Radio rdoUnmarried;
	Radio rdoMarried;
	Radio rdoDivorced;
	Radio rdoOthers;
	RadioGroup rdgMaritalStatus;

	ComboBox<ComboBoxData> cmbPtkp;
	ComboBoxData cmbPtkpSelectedData;
	ListStore<ComboBoxData> cmbPtkpStore;
	HiddenField<String> hfPtkpId;

	Radio rdoSmokerYes;
	Radio rdoSmokerNo;
	RadioGroup rdgSmoker;

	Radio rdoMale;
	Radio rdoFemale;
	RadioGroup rdgGender;

	TextField<String> txtDriversLicenseNumber;
	DateField dateLicenseExpiryDate;

	ComboBox<ComboBoxData> cmbEthnicRaces;
	ComboBoxData cmbEthnicRacesSelectedData;
	ListStore<ComboBoxData> cmbEthnicRacesStore;
	HiddenField<String> hfEthnicRacesId;
	
	TextField<String> txtPhone;
	TextField<String> txtMobile;

	Radio rdoStatusActive;
	Radio rdoStatusNotActive;
	RadioGroup rdgStatus;
	
	LabelField lblJoinedDate;

	public void addComponents() {
		folderPath = GWT.getHostPageBaseURL() + "upload/photo/";
		img = new Image(folderPath + "nopic_thumb.jpg");
		img.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window wndImage = new Window();
				wndImage.setSize(500, 500);
				wndImage.setLayout(new FitLayout());
				wndImage.setClosable(true);
				wndImage.setMaximizable(true);

				Image image = new Image(folderPath + (fileName == null ? "nopic.jpg" : fileName));
				wndImage.add(image);

				wndImage.show();
			}
		});
		add(img);

		lblCode = new LabelField();
		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Employee ID");
		add(lblCode, formData);

		lblJoinedDate = new LabelField();
		lblJoinedDate.setId("lblJoinedDate");
		lblJoinedDate.setFieldLabel("Joined Date");
		add(lblJoinedDate, formData);

		txtEmail = new TextField<String>();
		txtEmail.setId("txtEmail");
		txtEmail.setFieldLabel("Email *");
		txtEmail.setAllowBlank(false);
		add(txtEmail, formData);

		txtFirstName = new TextField<String>();
		txtFirstName.setId("txtName");
		txtFirstName.setFieldLabel("First Name *");
		txtFirstName.setAllowBlank(false);
		add(txtFirstName, formData);

		txtMiddleName = new TextField<String>();
		txtMiddleName.setId("txtMiddleName");
		txtMiddleName.setFieldLabel("Middle Name");
		txtMiddleName.setAllowBlank(true);
		add(txtMiddleName, formData);

		txtLastName = new TextField<String>();
		txtLastName.setId("txtLastName");
		txtLastName.setFieldLabel("Last Name *");
		txtLastName.setAllowBlank(false);
		add(txtLastName, formData);

		txtNickName = new TextField<String>();
		txtNickName.setId("txtNickName");
		txtNickName.setFieldLabel("Nick Name");
		txtNickName.setAllowBlank(true);
		add(txtNickName, formData);

		txtIDNo = new TextField<String>();
		txtIDNo.setId("txtIDNo");
		txtIDNo.setFieldLabel("ID No.");
		txtIDNo.setAllowBlank(true);
		add(txtIDNo, formData);

		txtTaxNo = new TextField<String>();
		txtTaxNo.setId("txtTaxNo");
		txtTaxNo.setFieldLabel("Tax No.");
		txtTaxNo.setAllowBlank(true);
		add(txtTaxNo, formData);

		cmbNationality = new ComboBox<ComboBoxData>();
		cmbNationalityStore = new ListStore<ComboBoxData>();
		cmbNationality.setId("cmbNationality");
		cmbNationality.setFieldLabel("Nationality");
		cmbNationality.setEmptyText("Select");
		cmbNationality.setDisplayField("value");
		cmbNationality.setForceSelection(true);
		cmbNationality.setTypeAhead(true);
		cmbNationality.setTriggerAction(TriggerAction.ALL);
		cmbNationality.setStore(cmbNationalityStore);
		cmbNationality.setAllowBlank(true);
		add(cmbNationality, formData);

		hfNationalityId = new HiddenField<String>();
		add(hfNationalityId, formData);

		dateDateOfBirth = new DateField();
		dateDateOfBirth.setFieldLabel("Date Of Birth");
		dateDateOfBirth.setAllowBlank(true);
		add(dateDateOfBirth, formData);

		rdoMarried = new Radio();
		rdoMarried.setBoxLabel("Married");

		rdoUnmarried = new Radio();
		rdoUnmarried.setBoxLabel("Unmarried");

		rdoDivorced = new Radio();
		rdoDivorced.setBoxLabel("Divorced");

		rdoOthers = new Radio();
		rdoOthers.setBoxLabel("Others");

		rdgMaritalStatus = new RadioGroup();
		rdgMaritalStatus.setFieldLabel("Marital Status");
		rdgMaritalStatus.add(rdoMarried);
		rdgMaritalStatus.add(rdoUnmarried);
		rdgMaritalStatus.add(rdoDivorced);
		rdgMaritalStatus.add(rdoOthers);
		add(rdgMaritalStatus, formData);

		cmbPtkp = new ComboBox<ComboBoxData>();
		cmbPtkpStore = new ListStore<ComboBoxData>();
		cmbPtkp.setId("cmbPtkp");
		cmbPtkp.setFieldLabel("PTKP");
		cmbPtkp.setEmptyText("Select");
		cmbPtkp.setDisplayField("value");
		cmbPtkp.setForceSelection(true);
		cmbPtkp.setTypeAhead(true);
		cmbPtkp.setTriggerAction(TriggerAction.ALL);
		cmbPtkp.setStore(cmbPtkpStore);
		cmbPtkp.setAllowBlank(true);
		add(cmbPtkp, formData);

		hfPtkpId = new HiddenField<String>();
		add(hfPtkpId, formData);

		rdoSmokerYes = new Radio();
		rdoSmokerYes.setBoxLabel("Yes");

		rdoSmokerNo = new Radio();
		rdoSmokerNo.setBoxLabel("No");

		rdgSmoker = new RadioGroup();
		rdgSmoker.setFieldLabel("Smoker");
		rdgSmoker.add(rdoSmokerYes);
		rdgSmoker.add(rdoSmokerNo);
		add(rdgSmoker, formData);

		rdoMale = new Radio();
		rdoMale.setBoxLabel("Male");

		rdoFemale = new Radio();
		rdoFemale.setBoxLabel("Female");

		rdgGender = new RadioGroup();
		rdgGender.setFieldLabel("Gender");
		rdgGender.add(rdoMale);
		rdgGender.add(rdoFemale);
		add(rdgGender, formData);

		txtDriversLicenseNumber = new TextField<String>();
		txtDriversLicenseNumber.setId("txtDriversLicenseNumber");
		txtDriversLicenseNumber.setFieldLabel("Drivers License No.");
		txtDriversLicenseNumber.setAllowBlank(true);
		add(txtDriversLicenseNumber, formData);

		dateLicenseExpiryDate = new DateField();
		dateLicenseExpiryDate.setFieldLabel("License Exp. Date");
		dateLicenseExpiryDate.setAllowBlank(true);
		add(dateLicenseExpiryDate, formData);

		cmbEthnicRaces = new ComboBox<ComboBoxData>();
		cmbEthnicRacesStore = new ListStore<ComboBoxData>();
		cmbEthnicRaces.setId("cmbEthnicRaces");
		cmbEthnicRaces.setFieldLabel("Ethnic Races");
		cmbEthnicRaces.setEmptyText("Select");
		cmbEthnicRaces.setDisplayField("value");
		cmbEthnicRaces.setForceSelection(true);
		cmbEthnicRaces.setTypeAhead(true);
		cmbEthnicRaces.setTriggerAction(TriggerAction.ALL);
		cmbEthnicRaces.setStore(cmbEthnicRacesStore);
		cmbEthnicRaces.setAllowBlank(true);
		add(cmbEthnicRaces, formData);

		hfEthnicRacesId = new HiddenField<String>();
		add(hfEthnicRacesId, formData);

		txtPhone = new TextField<String>();
		txtPhone.setId("txtPhoneNo");
		txtPhone.setFieldLabel("Phone");
		txtPhone.setAllowBlank(true);
		add(txtPhone, formData);

		txtMobile = new TextField<String>();
		txtMobile.setId("txtMobileNo");
		txtMobile.setFieldLabel("Mobile");
		txtMobile.setAllowBlank(true);
		add(txtMobile, formData);

		rdoStatusActive = new Radio();
		rdoStatusActive.setBoxLabel("Active");

		rdoStatusNotActive = new Radio();
		rdoStatusNotActive.setBoxLabel("Not Active");

		rdgStatus = new RadioGroup();
		rdgStatus.setFieldLabel("Status");
		rdgStatus.add(rdoStatusActive);
		rdgStatus.add(rdoStatusNotActive);
		add(rdgStatus, formData);
	}

	Button btnSave;
	Button btnEdit;
	Button btnCancel;
	Button btnBack;

	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (tbAugId != null) {
					if (formPanel.isValid()) {
						TbEmployeeBeanModel beanModel = new TbEmployeeBeanModel();
						beanModel.setTbeId(id);
						beanModel.setTbeEmail(StringUtil.getInstance().getString(txtEmail.getValue()));
						beanModel.setTbeFirstName(StringUtil.getInstance().getString(txtFirstName.getValue()));
						beanModel.setTbeLastName(StringUtil.getInstance().getString(txtLastName.getValue()));
						beanModel.setTbeMiddleName(StringUtil.getInstance().getString(txtMiddleName.getValue()));
						beanModel.setTbeNickName(StringUtil.getInstance().getString(txtNickName.getValue()));
						beanModel.setTbeIdNo(StringUtil.getInstance().getString(txtIDNo.getValue()));
						beanModel.setTbeTaxNo(StringUtil.getInstance().getString(txtTaxNo.getValue()));

						if (cmbNationality.getValue() != null)
							beanModel.setTbnId(Integer.parseInt(cmbNationality.getValue().get("id").toString()));

						if (dateDateOfBirth.getValue() != null)
							beanModel.setTbeDob(dateDateOfBirth.getValue().getTime());

						if (rdgMaritalStatus.getValue() != null)
							beanModel.setTbeMaritalStatus(rdoMarried.getValue() == true ? 0 : (rdoUnmarried.getValue() == true ? 1 : (rdoDivorced.getValue() == true ? 2 : (rdoOthers.getValue() == true ? 3 : null))));

						if (cmbPtkp.getValue() != null)
							beanModel.setTbptkpId(Integer.parseInt(cmbPtkp.getValue().get("id").toString()));

						if (rdgSmoker.getValue() != null)
							beanModel.setTbeSmoker(rdoSmokerYes.getValue() == true ? 0 : (rdoSmokerNo.getValue() == true ? 1 : null));

						if (rdgGender.getValue() != null)
							beanModel.setTbeGender(rdoMale.getValue() == true ? 0 : (rdoFemale.getValue() == true ? 1 : null));
						
						if (rdgStatus.getValue() != null)
							beanModel.setTbeStatus(rdoStatusActive.getValue() == true ? 0 : (rdoStatusNotActive.getValue() == true ? 1 : null));

						beanModel.setTbeDriverLicenseNo(StringUtil.getInstance().getString(txtDriversLicenseNumber.getValue()));

						if (dateLicenseExpiryDate.getValue() != null)
							beanModel.setTbeDriverLicenseExpiry(dateLicenseExpiryDate.getValue().getTime());

						if (cmbEthnicRaces.getValue() != null)
							beanModel.setTberId(Integer.parseInt(cmbEthnicRaces.getValue().get("id").toString()));
						
						beanModel.setTbePhone(StringUtil.getInstance().getString(txtPhone.getValue()));
						beanModel.setTbeMobile(StringUtil.getInstance().getString(txtMobile.getValue()));

						employeeListInterfaceAsync.submitEmployee(beanModel, submitCallback);
					} else {
						MessageBox.alert("Alert", "Required field is still empty", null);
					}
				}
			}
		});
		addButton(btnSave);

		btnEdit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (tbAugId != null) {
					doUnlockedComponent(formPanel);

					btnEdit.setVisible(false);
					btnBack.setVisible(false);
					btnCancel.setVisible(true);
					btnSave.setVisible(true);
				}
			}
		});
		addButton(btnEdit);

		btnCancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				formPanel.mask("loading");

				doLockedComponent(formPanel);

				employeeListInterfaceAsync.getEmployee(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		addButton(btnCancel);
		
		if (this.callerId == 1) {
			btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					WindowEmployeeList window = WindowEmployeeList.getInstance();
					window.show(true);
				}
			});
		} else {
			btnBack = new Button("Close", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					MainTabLayout.closeTab(WindowEss.getInstance().window.getHeading());
				}
			});
		}
		
		addButton(btnBack);
	}
}
