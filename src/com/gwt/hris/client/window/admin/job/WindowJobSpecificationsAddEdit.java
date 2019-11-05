package com.gwt.hris.client.window.admin.job;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.job.JobSpecificationsInterface;
import com.gwt.hris.client.service.admin.job.JobSpecificationsInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbJobSpecificationsBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowJobSpecificationsAddEdit extends WindowMain {
	private static WindowJobSpecificationsAddEdit instance = new WindowJobSpecificationsAddEdit();

	public static WindowJobSpecificationsAddEdit getInstance() {
		return instance;
	}

	public WindowJobSpecificationsAddEdit() {
		window = new Window();
		window.setId("WindowJobSpecificationsAddEdit");
		window.setSize(500, 250);
		window.setHeading("Job : Job Specifications");
	}

	public String strNav = "";
	public int intTbjsId = 0;

	public void show(String strNav, int intTbjsId) {
		this.strNav = strNav;
		this.intTbjsId = intTbjsId;

		super.show(true);
	}

	JobSpecificationsInterfaceAsync jobSpecificationsInterfaceAsync = GWT.create(JobSpecificationsInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	TextField<String> txtName = new TextField<String>();
	TextArea txtaDescription = new TextArea();
	TextArea txtaDuties = new TextArea();

	AsyncCallback<TbJobSpecificationsBeanModel> getLocationsCallback = new AsyncCallback<TbJobSpecificationsBeanModel>() {
		@Override
		public void onSuccess(TbJobSpecificationsBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbjsId().toString());

				txtName.setValue(result.getTbjsName());
				txtaDescription.setValue(result.getTbjsDescription());
				txtaDuties.setValue(result.getTbjsDuties());

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

	AsyncCallback<ReturnBean> submitLocationsCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbJobSpecificationsBeanModel TbJobSpecificationsBeanModel = (TbJobSpecificationsBeanModel) result.get("model");
				hfId.setValue(TbJobSpecificationsBeanModel.getTbjsId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

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

				WindowJobSpecifications.getInstance().bottomToolBar.refresh();
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
		txtName.setId("txtName");
		txtName.setFieldLabel("Name *");
		txtName.setAllowBlank(false);
		formPanel.add(txtName, formData);

		txtaDescription.setId("txtaDescription");
		txtaDescription.setFieldLabel("Description");
		txtaDescription.setAllowBlank(true);
		formPanel.add(txtaDescription, formData);

		txtaDuties.setId("txtaDuties");
		txtaDuties.setFieldLabel("Duties");
		txtaDuties.setAllowBlank(true);
		formPanel.add(txtaDuties, formData);

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
					TbJobSpecificationsBeanModel tbJobSpecificationsBeanModel = new TbJobSpecificationsBeanModel();
					tbJobSpecificationsBeanModel.setTbjsId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					tbJobSpecificationsBeanModel.setTbjsName(StringUtil.getInstance().getString(txtName.getValue()));
					tbJobSpecificationsBeanModel.setTbjsDescription(StringUtil.getInstance().getString(txtaDescription.getValue()));
					tbJobSpecificationsBeanModel.setTbjsDuties(StringUtil.getInstance().getString(txtaDuties.getValue()));
					jobSpecificationsInterfaceAsync.submitJobSpecifications(tbJobSpecificationsBeanModel, submitLocationsCallback);
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

				jobSpecificationsInterfaceAsync.getJobSpecifications(intTbjsId, getLocationsCallback);

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
				WindowJobSpecifications window = WindowJobSpecifications.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			jobSpecificationsInterfaceAsync.getJobSpecifications(intTbjsId, getLocationsCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
