package com.gwt.hris.client.window.pim;

import java.util.Date;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.companyinfo.LocationsInterface;
import com.gwt.hris.client.service.admin.companyinfo.LocationsInterfaceAsync;
import com.gwt.hris.client.service.admin.job.JobSpecificationsInterface;
import com.gwt.hris.client.service.admin.job.JobSpecificationsInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.client.service.bean.TbJobBeanModel;
import com.gwt.hris.client.service.bean.TbJobSpecificationsBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbLocationBeanModel;
import com.gwt.hris.client.service.bean.TbOrganizationBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;
import com.gwt.hris.client.service.pim.JobInterface;
import com.gwt.hris.client.service.pim.JobInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.MainTabLayout;

public class EssJob extends EssMainFP {
	EssJob formPanel = this;

	public EssJob(FormData formData, int id_, int callerId_) {
		super(formData);

		formPanel.setLabelWidth(115);

		id = id_;
		callerId = callerId_;
		
		if (this.callerId == 0) {
			tbAugId = WindowEss.getInstance().tbLoginBeanModel.getTbaugId();
		} else {
			tbAugId = WindowEmployeeListEss.getInstance().tbLoginBeanModel.getTbaugId();
		}

		addComponents();

		addButtons();

		formPanel.mask("loading");
		btnEdit.setEnabled(false);
		btnBack.setEnabled(false);

		doLockedComponent(formPanel);

		jobInterfaceAsync.getJob(this.id, getCallback);

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
	}

	public int id = 0;
	public int callerId;
	public Integer tbAugId;

	JobInterfaceAsync jobInterfaceAsync = GWT.create(JobInterface.class);
	JobSpecificationsInterfaceAsync jobSpecificationsInterfaceAsync = GWT.create(JobSpecificationsInterface.class);
	LocationsInterfaceAsync locationsInterfaceAsync = GWT.create(LocationsInterface.class);
	
	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbJobBeanModel beanModel = (TbJobBeanModel) result.get("model");
				id = beanModel.getTbeId();

				formPanel.mask("loading");
				btnEdit.setEnabled(false);
				btnBack.setEnabled(false);

				doLockedComponent(formPanel);

				jobInterfaceAsync.getJob(id, getCallback);

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

	AsyncCallback<TbJobBeanModel> getCallback = new AsyncCallback<TbJobBeanModel>() {
		@Override
		public void onSuccess(TbJobBeanModel result) {
			if (result.getOperationStatus()) {
				hfJobTitleId.setValue(result.getTbjtId() == null ? "" : result.getTbjtId().toString());
				jobInterfaceAsync.getTbJobTitleAll(tbJobTitleAllCallback);

				hfEmploymentStatusId.setValue(result.getTbesId() == null ? "" : result.getTbesId().toString());

				jobSpecificationsInterfaceAsync.getJobSpecificationsByJobTitle(result.getTbjtId() == null ? 0 : result.getTbjtId(), getJobSpecificationsCallback);
				jobInterfaceAsync.getTbEmploymentStatus(result.getTbjtId() == null ? 0 : result.getTbjtId(), tbEmploymentStatusAllCallback);
				
				hfPerusahaanId.setValue(result.getTbpId() == null ? "" : result.getTbpId().toString());
				locationsInterfaceAsync.getTbPerusahaanAll(tbPerusahaanAllCallback);

				hfOrganizationId.setValue(result.getTboId() == null ? "" : result.getTboId().toString());
				jobInterfaceAsync.getTbOrganization(result.getTbpId() == null ? 0 : result.getTbpId(), tbOrganizationAllCallback);

				hfLocationId.setValue(result.getTblId() == null ? "" : result.getTblId().toString());
				jobInterfaceAsync.getTbLocation(result.getTbpId() == null ? 0 : result.getTbpId(), tbLocationAllCallback);

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

	AsyncCallback<TbJobTitleBeanModel> tbJobTitleAllCallback = new AsyncCallback<TbJobTitleBeanModel>() {
		@Override
		public void onSuccess(TbJobTitleBeanModel result) {
			if (result.getOperationStatus()) {
				cmbJobTitle.clear();
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

	AsyncCallback<TbEmploymentStatusBeanModel> tbEmploymentStatusAllCallback = new AsyncCallback<TbEmploymentStatusBeanModel>() {
		@Override
		public void onSuccess(TbEmploymentStatusBeanModel result) {
			if (result.getOperationStatus()) {
				cmbEmploymentStatus.clear();
				cmbEmploymentStatusStore.removeAll();

				for (TbEmploymentStatusBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbesId().toString(), obj.getTbesName());
					cmbEmploymentStatusStore.add(data);

					if (hfEmploymentStatusId.getValue() != null) {
						if (!"".equals(hfEmploymentStatusId.getValue())) {
							if (obj.getTbesId() == Integer.parseInt(hfEmploymentStatusId.getValue())) {
								cmbEmploymentStatusSelectedData = data;
								cmbEmploymentStatus.setValue(cmbEmploymentStatusSelectedData);
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

	AsyncCallback<TbPerusahaanBeanModel> tbPerusahaanAllCallback = new AsyncCallback<TbPerusahaanBeanModel>() {
		@Override
		public void onSuccess(TbPerusahaanBeanModel result) {
			if (result.getOperationStatus()) {
				cmbPerusahaanStore.removeAll();

				for (TbPerusahaanBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpId().toString(), obj.getTbpName());
					cmbPerusahaanStore.add(data);

					if (hfPerusahaanId.getValue() != null) {
						if (!"".equals(hfPerusahaanId.getValue())) {
							if (obj.getTbpId() == Integer.parseInt(hfPerusahaanId.getValue())) {
								cmbPerusahaanSelectedData = data;
								cmbPerusahaan.setValue(cmbPerusahaanSelectedData);
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

	AsyncCallback<TbOrganizationBeanModel> tbOrganizationAllCallback = new AsyncCallback<TbOrganizationBeanModel>() {
		@Override
		public void onSuccess(TbOrganizationBeanModel result) {
			if (result.getOperationStatus()) {
				cmbOrganization.clear();
				cmbOrganizationStore.removeAll();

				for (TbOrganizationBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTboId().toString(), obj.getTboNama());
					cmbOrganizationStore.add(data);

					if (hfOrganizationId.getValue() != null) {
						if (!"".equals(hfOrganizationId.getValue())) {
							if (obj.getTboId() == Integer.parseInt(hfOrganizationId.getValue())) {
								cmbOrganizationSelectedData = data;
								cmbOrganization.setValue(cmbOrganizationSelectedData);
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

	AsyncCallback<TbLocationBeanModel> tbLocationAllCallback = new AsyncCallback<TbLocationBeanModel>() {
		@Override
		public void onSuccess(TbLocationBeanModel result) {
			if (result.getOperationStatus()) {
				cmbLocation.clear();
				cmbLocationStore.removeAll();

				for (TbLocationBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTblId().toString(), obj.getTblName());
					cmbLocationStore.add(data);

					if (hfLocationId.getValue() != null) {
						if (!"".equals(hfLocationId.getValue())) {
							if (obj.getTblId() == Integer.parseInt(hfLocationId.getValue())) {
								cmbLocationSelectedData = data;
								cmbLocation.setValue(cmbLocationSelectedData);
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

	AsyncCallback<TbJobSpecificationsBeanModel> getJobSpecificationsCallback = new AsyncCallback<TbJobSpecificationsBeanModel>() {
		@Override
		public void onSuccess(TbJobSpecificationsBeanModel result) {
			if (result.getOperationStatus()) {
				lblJobSpecification.setValue(result.getTbjsName());
				lblJobDuties.setValue(result.getTbjsDuties());
			} else {
				if (!"Data Not Found".equals(result.getMessage())) {
					MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
				} else {
					lblJobSpecification.setValue(result.getTbjsName());
					lblJobDuties.setValue(result.getTbjsDuties());
				}
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};

	ComboBox<ComboBoxData> cmbJobTitle;
	ComboBoxData cmbJobTitleSelectedData;
	ListStore<ComboBoxData> cmbJobTitleStore;
	HiddenField<String> hfJobTitleId;

	ComboBox<ComboBoxData> cmbEmploymentStatus;
	ComboBoxData cmbEmploymentStatusSelectedData;
	ListStore<ComboBoxData> cmbEmploymentStatusStore;
	HiddenField<String> hfEmploymentStatusId;

	LabelField lblJobSpecification;
	LabelField lblJobDuties;

	ComboBox<ComboBoxData> cmbOrganization;
	ComboBoxData cmbOrganizationSelectedData;
	ListStore<ComboBoxData> cmbOrganizationStore;
	HiddenField<String> hfOrganizationId;

	ComboBox<ComboBoxData> cmbLocation;
	ComboBoxData cmbLocationSelectedData;
	ListStore<ComboBoxData> cmbLocationStore;
	HiddenField<String> hfLocationId;

	ComboBox<ComboBoxData> cmbPerusahaan;
	ComboBoxData cmbPerusahaanSelectedData;
	ListStore<ComboBoxData> cmbPerusahaanStore;
	HiddenField<String> hfPerusahaanId;

	public void addComponents() {
		cmbJobTitle = new ComboBox<ComboBoxData>();
		cmbJobTitleStore = new ListStore<ComboBoxData>();
		cmbJobTitle.setId("cmbJobTitle");
		cmbJobTitle.setFieldLabel("Job Title");
		cmbJobTitle.setEmptyText("Select");
		cmbJobTitle.setDisplayField("value");
		cmbJobTitle.setForceSelection(true);
		cmbJobTitle.setTypeAhead(true);
		cmbJobTitle.setTriggerAction(TriggerAction.ALL);
		cmbJobTitle.setStore(cmbJobTitleStore);
		cmbJobTitle.setAllowBlank(true);
		cmbJobTitle.addListener(Events.Select, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				jobSpecificationsInterfaceAsync.getJobSpecificationsByJobTitle(Integer.parseInt(cmbJobTitle.getValue().get("id").toString()), getJobSpecificationsCallback);
				jobInterfaceAsync.getTbEmploymentStatus(Integer.parseInt(cmbJobTitle.getValue().get("id").toString()), tbEmploymentStatusAllCallback);
			}
		});
		add(cmbJobTitle, formData);
		hfJobTitleId = new HiddenField<String>();
		add(hfJobTitleId, formData);

		lblJobSpecification = new LabelField();
		lblJobSpecification.setId("lblJobSpecification");
		lblJobSpecification.setFieldLabel("Job Specification");
		add(lblJobSpecification, formData);

		lblJobDuties = new LabelField();
		lblJobDuties.setId("lblJobDuties");
		lblJobDuties.setFieldLabel("Job Duties");
		add(lblJobDuties, formData);

		cmbEmploymentStatus = new ComboBox<ComboBoxData>();
		cmbEmploymentStatusStore = new ListStore<ComboBoxData>();
		cmbEmploymentStatus.setId("cmbEmploymentStatus");
		cmbEmploymentStatus.setFieldLabel("Employment Status");
		cmbEmploymentStatus.setEmptyText("Select");
		cmbEmploymentStatus.setDisplayField("value");
		cmbEmploymentStatus.setForceSelection(true);
		cmbEmploymentStatus.setTypeAhead(true);
		cmbEmploymentStatus.setTriggerAction(TriggerAction.ALL);
		cmbEmploymentStatus.setStore(cmbEmploymentStatusStore);
		cmbEmploymentStatus.setAllowBlank(true);
		add(cmbEmploymentStatus, formData);
		hfEmploymentStatusId = new HiddenField<String>();
		add(hfEmploymentStatusId, formData);
		
		cmbPerusahaan = new ComboBox<ComboBoxData>();
		cmbPerusahaanStore = new ListStore<ComboBoxData>();
		cmbPerusahaan.setId("cmbPerusahaan");
		cmbPerusahaan.setFieldLabel("Company");
		cmbPerusahaan.setEmptyText("Select");
		cmbPerusahaan.setDisplayField("value");
		cmbPerusahaan.setForceSelection(true);
		cmbPerusahaan.setTypeAhead(true);
		cmbPerusahaan.setTriggerAction(TriggerAction.ALL);
		cmbPerusahaan.setStore(cmbPerusahaanStore);
		cmbPerusahaan.setAllowBlank(true);
		cmbPerusahaan.addListener(Events.Select, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				jobInterfaceAsync.getTbOrganization(Integer.parseInt(cmbPerusahaan.getValue().get("id").toString()), tbOrganizationAllCallback);
				jobInterfaceAsync.getTbLocation(Integer.parseInt(cmbPerusahaan.getValue().get("id").toString()), tbLocationAllCallback);
			}
		});
		add(cmbPerusahaan, formData);
		hfPerusahaanId = new HiddenField<String>();
		add(hfPerusahaanId, formData);

		cmbLocation = new ComboBox<ComboBoxData>();
		cmbLocationStore = new ListStore<ComboBoxData>();
		cmbLocation.setId("cmbLocation");
		cmbLocation.setFieldLabel("Location");
		cmbLocation.setEmptyText("Select");
		cmbLocation.setDisplayField("value");
		cmbLocation.setForceSelection(true);
		cmbLocation.setTypeAhead(true);
		cmbLocation.setTriggerAction(TriggerAction.ALL);
		cmbLocation.setStore(cmbLocationStore);
		cmbLocation.setAllowBlank(true);
		add(cmbLocation, formData);
		hfLocationId = new HiddenField<String>();
		add(hfLocationId, formData);

		cmbOrganization = new ComboBox<ComboBoxData>();
		cmbOrganizationStore = new ListStore<ComboBoxData>();
		cmbOrganization.setId("cmbOrganization");
		cmbOrganization.setFieldLabel("Organization");
		cmbOrganization.setEmptyText("Select");
		cmbOrganization.setDisplayField("value");
		cmbOrganization.setForceSelection(true);
		cmbOrganization.setTypeAhead(true);
		cmbOrganization.setTriggerAction(TriggerAction.ALL);
		cmbOrganization.setStore(cmbOrganizationStore);
		cmbOrganization.setAllowBlank(true);
		add(cmbOrganization, formData);
		hfOrganizationId = new HiddenField<String>();
		add(hfOrganizationId, formData);
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
						TbJobBeanModel beanModel = new TbJobBeanModel();
						beanModel.setTbeId(id);

						if (cmbJobTitle.getValue() != null)
							beanModel.setTbjtId(Integer.parseInt(cmbJobTitle.getValue().get("id").toString()));

						if (cmbEmploymentStatus.getValue() != null)
							beanModel.setTbesId(Integer.parseInt(cmbEmploymentStatus.getValue().get("id").toString()));
						
						if (cmbPerusahaan.getValue() != null)
							beanModel.setTbpId(Integer.parseInt(cmbPerusahaan.getValue().get("id").toString()));

						if (cmbOrganization.getValue() != null)
							beanModel.setTboId(Integer.parseInt(cmbOrganization.getValue().get("id").toString()));
						
						if (cmbLocation.getValue() != null)
							beanModel.setTblId(Integer.parseInt(cmbLocation.getValue().get("id").toString()));

						beanModel.setTbjJoinedDate((new Date()).getTime());

						jobInterfaceAsync.submitJob(beanModel, submitCallback);
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

				jobInterfaceAsync.getJob(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		addButton(btnCancel);

		if (this.callerId == 0) {
			btnBack = new Button("Close", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					MainTabLayout.closeTab(WindowEss.getInstance().window.getHeading());
				}
			});
		} else {
			btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					WindowEmployeeList window = WindowEmployeeList.getInstance();
					window.show(true);
				}
			});
		}
		addButton(btnBack);
	}
}
