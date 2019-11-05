package com.gwt.hris.client.window.loan;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLoanBeanModel;
import com.gwt.hris.client.service.bean.ViewAssignedLoanBeanModel;
import com.gwt.hris.client.service.loan.AssignedLoanInterface;
import com.gwt.hris.client.service.loan.AssignedLoanInterfaceAsync;
import com.gwt.hris.client.window.WindowMain;

public class WindowApprovalLoanView extends WindowMain {
	private static WindowApprovalLoanView instance = new WindowApprovalLoanView();

	public static WindowApprovalLoanView getInstance() {
		return instance;
	}

	public WindowApprovalLoanView() {
		window = new Window();
		window.setId("WindowApprovalLoanView");
		window.setSize(500, 260);
		window.setHeading("Loan : Approval Loan");
	}

	public int id = 0;

	public void show(int id) {
		this.id = id;

		super.show(true);
	}

	AssignedLoanInterfaceAsync interfaceAsync = GWT.create(AssignedLoanInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblLoanName = new LabelField();
	LabelField lblNominal = new LabelField();
	LabelField lblInterest = new LabelField();
	LabelField lblTenor = new LabelField();
	LabelField lblTotalLoan = new LabelField();
	LabelField lblMonthlyPayment = new LabelField();
	LabelField lblStatus = new LabelField();

	AsyncCallback<ViewAssignedLoanBeanModel> getCallback = new AsyncCallback<ViewAssignedLoanBeanModel>() {
		@Override
		public void onSuccess(ViewAssignedLoanBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbaloId().toString());

				NumberFormat fmt = NumberFormat.getFormat("#,###,###");

				lblLoanName.setValue(result.getTbaloName());
				lblNominal.setValue(fmt.format(result.getTbaloNominal()));
				lblInterest.setValue(result.getTbaloInterest().toString());
				lblTenor.setValue(result.getTbaloTenor().toString());
				lblTotalLoan.setValue(fmt.format(result.getTbaloNominalTotal()));
				lblMonthlyPayment.setValue(fmt.format(result.getTbaloMonthlyPayment()));
				lblStatus.setValue(result.getTbaloStatus() == 0 ? "Pending" : (result.getTbaloStatus() == 1 ? "Approved" : "Rejected"));

				formPanel.unmask();
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
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbAssignedLoanBeanModel beanModel = (TbAssignedLoanBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbaloId().toString());

				doLockedComponent(window);

				btnBack.setVisible(true);

				WindowAssignedLoan.getInstance().bottomToolBar.refresh();
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

	@Override
	public void addComponents() {
		formPanel.setLabelWidth(115);

		lblLoanName.setId("lblLoanName");
		lblLoanName.setFieldLabel("Loan Name *");
		formPanel.add(lblLoanName, formData);

		lblNominal.setId("lblNominal");
		lblNominal.setFieldLabel("Nominal *");
		formPanel.add(lblNominal, formData);

		lblInterest.setId("lblInterest");
		lblInterest.setFieldLabel("Interest (%/month) *");
		formPanel.add(lblInterest, formData);

		lblTenor.setId("lblTenor");
		lblTenor.setFieldLabel("Tenor (month) *");
		formPanel.add(lblTenor, formData);

		lblMonthlyPayment.setId("lblMonthlyPayment");
		lblMonthlyPayment.setFieldLabel("Monthly Payment");
		formPanel.add(lblMonthlyPayment, formData);

		lblTotalLoan.setId("lblTotalLoan");
		lblTotalLoan.setFieldLabel("Total Loan");
		formPanel.add(lblTotalLoan, formData);

		lblStatus.setId("lblStatus");
		lblStatus.setFieldLabel("Status");
		formPanel.add(lblStatus, formData);

		window.add(formPanel);
	}

	Button btnBack;

	@Override
	public void addButtons() {
		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowApprovalLoan window = WindowApprovalLoan.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		formPanel.setLabelWidth(130);

		formPanel.mask("loading");
		btnBack.setEnabled(false);

		doLockedComponent(window);

		interfaceAsync.getAssignedLoan(id, getCallback);

		btnBack.setVisible(true);
	}
}
