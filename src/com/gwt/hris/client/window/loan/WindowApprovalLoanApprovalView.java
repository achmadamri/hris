package com.gwt.hris.client.window.loan;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
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
import com.gwt.hris.client.service.bean.ViewAssignedLoanBeanModel;
import com.gwt.hris.client.service.loan.AssignedLoanInterface;
import com.gwt.hris.client.service.loan.AssignedLoanInterfaceAsync;
import com.gwt.hris.client.window.WindowMain;

public class WindowApprovalLoanApprovalView extends WindowMain {
	private static WindowApprovalLoanApprovalView instance = new WindowApprovalLoanApprovalView();

	public static WindowApprovalLoanApprovalView getInstance() {
		return instance;
	}

	public WindowApprovalLoanApprovalView() {
		window = new Window();
		window.setId("WindowApprovalLoanApprovalView");
		window.setSize(500, 270);
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
	LabelField lblCurrencyName = new LabelField();
	LabelField lblNominal = new LabelField();
	LabelField lblInterest = new LabelField();
	LabelField lblTenor = new LabelField();
	LabelField lblTotalLoan = new LabelField();
	LabelField lblMonthlyPayment = new LabelField();

	AsyncCallback<ReturnBean> approvalAssignedLoanCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				WindowApprovalLoan window = WindowApprovalLoan.getInstance();
				window.show(true);
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

	AsyncCallback<ViewAssignedLoanBeanModel> getCallback = new AsyncCallback<ViewAssignedLoanBeanModel>() {
		@Override
		public void onSuccess(ViewAssignedLoanBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbaloId().toString());

				NumberFormat fmt = NumberFormat.getFormat("#,###,###");

				lblLoanName.setValue(result.getTbaloName());
				lblNominal.setValue(fmt.format(result.getTbaloNominal()));
				lblCurrencyName.setValue(result.getTbcCurrencyId());
				lblInterest.setValue(result.getTbaloInterest().toString());
				lblTenor.setValue(result.getTbaloTenor().toString());
				lblTotalLoan.setValue(fmt.format(result.getTbaloNominalTotal()));
				lblMonthlyPayment.setValue(fmt.format(result.getTbaloMonthlyPayment()));

				formPanel.unmask();
				btnApproval.setEnabled(true);
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

	@Override
	public void addComponents() {
		formPanel.setLabelWidth(115);

		lblLoanName.setId("lblLoanName");
		lblLoanName.setFieldLabel("Loan Name *");
		formPanel.add(lblLoanName, formData);

		lblCurrencyName.setId("lblCurrencyName");
		lblCurrencyName.setFieldLabel("Currency *");
		formPanel.add(lblCurrencyName, formData);

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

		window.add(formPanel);
	}

	Button btnApproval;
	Button btnReject;
	Button btnBack;

	Listener<MessageBoxEvent> approveListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				formPanel.mask("Saving...");
				interfaceAsync.approvalAssignedLoan(id, 1, approvalAssignedLoanCallback);
			}
		}
	};

	Listener<MessageBoxEvent> rejectListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				formPanel.mask("Saving...");
				interfaceAsync.approvalAssignedLoan(id, 2, approvalAssignedLoanCallback);
			}
		}
	};

	@Override
	public void addButtons() {
		btnApproval = new Button("Approval", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MessageBox.confirm("Confirmation", "Are you sure you want to approve this loan ?", approveListener);
			}
		});
		window.addButton(btnApproval);

		btnReject = new Button("Reject", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MessageBox.confirm("Confirmation", "Are you sure you want to reject this loan ?", rejectListener);
			}
		});
		window.addButton(btnReject);

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
