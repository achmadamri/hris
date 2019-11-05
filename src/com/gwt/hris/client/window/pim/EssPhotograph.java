package com.gwt.hris.client.window.pim;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;

public class EssPhotograph extends EssMainFP {
	EssPhotograph formPanel = this;

	public EssPhotograph(FormData formData, int id_, int callerId_) {
		super(formData);

		id = id_;
		callerId = callerId_;

		addComponents();

		addButtons();

		formPanel.mask("loading");
		btnEdit.setEnabled(false);
		btnBack.setEnabled(false);

		doLockedComponent(formPanel);

		employeeListInterfaceAsync.getEmployee(this.id, getCallback);

		btnEdit.setVisible(true);
		btnBack.setVisible(true);
		btnCancel.setVisible(false);
		btnSave.setVisible(false);
	}

	public int id = 0;
	public int callerId;

	EmployeeListInterfaceAsync employeeListInterfaceAsync = GWT.create(EmployeeListInterface.class);

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
				doResetComponent(formPanel);

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

	FileUploadField fufPhoto;
	HiddenField<String> hftbePhotoFileName;

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

		fufPhoto = new FileUploadField();
		fufPhoto.setName("uploadedfile");
		fufPhoto.setFieldLabel("Photo");
		add(fufPhoto, formData);

		setAction("hris/photoupload");
		setEncoding(Encoding.MULTIPART);
		setMethod(Method.POST);

		hftbePhotoFileName = new HiddenField<String>();

		addListener(Events.Submit, new Listener<FormEvent>() {
			@Override
			public void handleEvent(FormEvent be) {
				if (be.getResultHtml().contains("filename")) {
					hftbePhotoFileName.setValue(be.getResultHtml().replaceAll("filename:", ""));
					submitData();
				} else {
					MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + be.getResultHtml(), null);
				}
			}
		});
	}

	Button btnSave;
	Button btnEdit;
	Button btnCancel;
	Button btnBack;

	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
					submit();
				} else {
					MessageBox.alert("Alert", "Required field is still empty", null);
				}
			}
		});
		addButton(btnSave);

		btnEdit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				doUnlockedComponent(formPanel);

				btnEdit.setVisible(false);
				btnBack.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
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

	public void submitData() {
		TbEmployeeBeanModel beanModel = new TbEmployeeBeanModel();
		beanModel.setTbeId(id);
		beanModel.setTbePhotoFileName(hftbePhotoFileName.getValue());

		employeeListInterfaceAsync.submitEmployee(beanModel, submitCallback);
	}
}
