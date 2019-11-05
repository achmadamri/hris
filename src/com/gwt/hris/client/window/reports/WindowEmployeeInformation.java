package com.gwt.hris.client.window.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewReportEmployeeInformationBeanModel;
import com.gwt.hris.client.service.reports.EmployeeInformationInterface;
import com.gwt.hris.client.service.reports.EmployeeInformationInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowEmployeeInformation extends WindowMain {
	private static WindowEmployeeInformation instance = new WindowEmployeeInformation();

	public static WindowEmployeeInformation getInstance() {
		return instance;
	}

	public WindowEmployeeInformation() {
		window = new Window();
		window.setId("WindowEmployeeInformation");
		window.setSize(940, 380);
		window.setHeading("Reports : Employee Information");
	}

	EmployeeInformationInterfaceAsync interfaceAsync = GWT.create(EmployeeInformationInterface.class);
	SimpleComboBox<String> cmbSearch;
	TextField<String> txtSearch = new TextField<String>();
	TextField<String> txtPageSize = new TextField<String>();
	Button btnGo;
	Button btnSearch;
	Button btnSearchReset;
	RpcProxy<PagingLoadResult<ViewReportEmployeeInformationBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewReportEmployeeInformationBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(20);
	Grid<ViewReportEmployeeInformationBeanModel> grid;

	int deleteID;

	AsyncCallback<ReturnBean> callback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);
				bottomToolBar.refresh();
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
	
	ContentPanel panel;

	@Override
	public void addComponents() {
		panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		ToolBar toolBar = new ToolBar();
		bottomToolBar = new PagingToolBar(20);

		toolBar.add(new LabelToolItem("Size : "));

		txtPageSize.setWidth(40);
		txtPageSize.setAutoValidate(true);
		txtPageSize.setRegex("[\\d]+");
		txtPageSize.setValue(String.valueOf(bottomToolBar.getPageSize()));
		toolBar.add(txtPageSize);

		btnGo = new Button("Go", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				bottomToolBar.setPageSize(Integer.parseInt(txtPageSize.getValue()));
				bottomToolBar.refresh();
			}
		});
		toolBar.add(btnGo);

		toolBar.add(new LabelToolItem("Search By : "));

		cmbSearch = new SimpleComboBox<String>();
		cmbSearch.setTriggerAction(TriggerAction.ALL);
		cmbSearch.setEditable(false);
		cmbSearch.setWidth(110);
		cmbSearch.add("City");
		cmbSearch.add("Country");
		cmbSearch.add("Currency");
		cmbSearch.add("DOB");
		cmbSearch.add("Driver Lic. No");
		cmbSearch.add("Driver Lic. Exp.");
		cmbSearch.add("Emp. Joined Date");
		cmbSearch.add("Employee ID");
		cmbSearch.add("Employee Email");
		cmbSearch.add("Employee Name");
		cmbSearch.add("Employment Status");
		cmbSearch.add("Gender");
		cmbSearch.add("Home Phone");
		cmbSearch.add("ID No");
		cmbSearch.add("Job Title");
		cmbSearch.add("Job Joined Date");
		cmbSearch.add("Marital Status");
		cmbSearch.add("Mobile Phone");
		cmbSearch.add("Organization");
		cmbSearch.add("Province");
		cmbSearch.add("Report To");
		cmbSearch.add("Salary Grade");
		cmbSearch.add("Smoker");
		cmbSearch.add("Street");
		cmbSearch.add("Tax No");
		cmbSearch.add("Work Phone");
		cmbSearch.add("Zip");
		toolBar.add(cmbSearch);

		toolBar.add(new LabelToolItem("Search For : "));

		toolBar.add(txtSearch);

		btnSearch = new Button("Search", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				bottomToolBar.refresh();
			}
		});
		toolBar.add(btnSearch);

		btnSearchReset = new Button("Reset", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				cmbSearch.reset();
				txtSearch.setValue("");
				bottomToolBar.refresh();
			}
		});
		toolBar.add(btnSearchReset);

		panel.setTopComponent(toolBar);

		proxy = new RpcProxy<PagingLoadResult<ViewReportEmployeeInformationBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewReportEmployeeInformationBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getPaging(pagingLoadConfig, cmbSearch.getSimpleValue(), txtSearch.getValue(), new AsyncCallback<PagingLoadResult<ViewReportEmployeeInformationBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewReportEmployeeInformationBeanModel> result) {
						if (result.getData().size() > 0) {
							if (result.getData().get(0).get("messages") == null) {
								callback.onSuccess(result);
							} else {
								MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getData().get(0).get("messages").toString(), null);
								callback.onSuccess(null);
							}
						} else {
							callback.onSuccess(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

						caught.printStackTrace();

						callback.onFailure(caught);
					}
				});
				
				panel.getBottomComponent().setEnabled(true);
			}
		};

		pagingLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
		pagingLoader.setRemoteSort(true);

		pagingStore = new ListStore<ViewReportEmployeeInformationBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbeEmployeeId", "<center><b>Employee ID</b></center>", 100));
		columns.add(new ColumnConfig("tbeEmail", "<center><b>Employee Email</b></center>", 100));
		columns.add(new ColumnConfig("tbeName", "<center><b>Employee Name</b></center>", 100));

		ColumnConfig columnConfig = new ColumnConfig("tbeStatus", "<center><b>Status</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbeStatus");
				if (data == null) {
					return "-";
				} else if (data == 0) {
					return "Active";
				} else {
					return "Not Active";
				}
			}
		});
		columns.add(columnConfig);
		
		columns.add(new ColumnConfig("tbeIdNo", "<center><b>ID No</b></center>", 100));
		columns.add(new ColumnConfig("tbeTaxNo", "<center><b>Tax No</b></center>", 100));
		
		columnConfig = new ColumnConfig("tbeDob", "<center><b>DOB</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				if (model.get("tbeDob") != null) {
					Date date = (Date) (model.get("tbeDob"));
					DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
					return fmt.format(date);
				} else {
					return "";
				}
			}
		});
		columns.add(columnConfig);
		
		columns.add(new ColumnConfig("tbeMaritalStatus", "<center><b>Marital Status</b></center>", 100));
		columns.add(new ColumnConfig("tbeSmoker", "<center><b>Smoker</b></center>", 100));
		columns.add(new ColumnConfig("tbeGender", "<center><b>Gender</b></center>", 100));
		columns.add(new ColumnConfig("tbeDriverLicenseNo", "<center><b>Driver Lic. No</b></center>", 100));
		columns.add(new ColumnConfig("tbeDriverLicenseExpiry", "<center><b>Driver Lic. Exp.</b></center>", 100));
		
		columnConfig = new ColumnConfig("tbeJoinedDate", "<center><b>Emp. Joined Date</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				if (model.get("tbeJoinedDate") != null) {
					Date date = (Date) (model.get("tbeJoinedDate"));
					DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
					return fmt.format(date);
				} else {
					return "";
				}
			}
		});
		columns.add(columnConfig);
		
		columns.add(new ColumnConfig("tbcdStreet", "<center><b>Street</b></center>", 100));
		columns.add(new ColumnConfig("tbcdCity", "<center><b>City</b></center>", 100));
		columns.add(new ColumnConfig("tbcdProvince", "<center><b>Province</b></center>", 100));
		columns.add(new ColumnConfig("tbcdZipCode", "<center><b>Zip</b></center>", 100));
		columns.add(new ColumnConfig("tbcdHomePhone", "<center><b>Home Phone</b></center>", 100));
		columns.add(new ColumnConfig("tbcdMobilePhone", "<center><b>Mobile Phone</b></center>", 100));
		columns.add(new ColumnConfig("tbcdWorkPhone", "<center><b>Work Phone</b></center>", 100));
		columns.add(new ColumnConfig("tbnNama", "<center><b>Country</b></center>", 100));
		columns.add(new ColumnConfig("tbjtName", "<center><b>Job Title</b></center>", 100));
		
		columnConfig = new ColumnConfig("tbjJoinedDate", "<center><b>Job Joined Date</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				if (model.get("tbjJoinedDate") != null) {
					Date date = (Date) (model.get("tbjJoinedDate"));
					DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
					return fmt.format(date);
				} else {
					return "";
				}
			}
		});
		columns.add(columnConfig);
		
		columns.add(new ColumnConfig("tboNama", "<center><b>Organization</b></center>", 100));
		columns.add(new ColumnConfig("tbeNameReportTo", "<center><b>Report To</b></center>", 150));
		columns.add(new ColumnConfig("tbesName", "<center><b>Employment Status</b></center>", 100));
		columns.add(new ColumnConfig("tbpName", "<center><b>Salary Grade</b></center>", 100));
		columns.add(new ColumnConfig("tbesCurrencyName", "<center><b>Currency</b></center>", 150));

		columnConfig = new ColumnConfig("tbesBasicSalary", "<center><b>Basic Salary</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = (Double) (model.get("tbesBasicSalary") == null ? (double) 0 : model.get("tbesBasicSalary"));
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbesPayFrequency", "<center><b>Pay Frequency</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				int data = (Integer) (model.get("tbesPayFrequency") == null ? 0 : model.get("tbesPayFrequency"));
				return data == 0 ? "Hourly" : (data == 1 ? "Weekly" : (data == 2 ? "Bi Weekly" : data == 3 ? "Monthly" : null));
			}
		});
		columns.add(columnConfig);

		columns.add(new ColumnConfig("tblName", "<center><b>Language</b></center>", 100));
		columns.add(new ColumnConfig("tbsName", "<center><b>Skill</b></center>", 100));

		ColumnConfig ccActions = new ColumnConfig();
		ccActions.setId("space");
		ccActions.setHeader("");
		ccActions.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(final ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return null;
			}
		});
		columns.add(ccActions);

		ColumnModel cm = new ColumnModel(columns);

		grid = new Grid<ViewReportEmployeeInformationBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewReportEmployeeInformationBeanModel>>() {
			public void handleEvent(GridEvent<ViewReportEmployeeInformationBeanModel> be) {
				PagingLoadConfig config = new BasePagingLoadConfig();
				config.setOffset(0);
				config.setLimit(50);

				Map<String, Object> state = grid.getState();
				if (state.containsKey("offset")) {
					int offset = (Integer) state.get("offset");
					int limit = (Integer) state.get("limit");
					config.setOffset(offset);
					config.setLimit(limit);
				}
				if (state.containsKey("sortField")) {
					config.setSortField((String) state.get("sortField"));
					config.setSortDir(SortDir.valueOf((String) state.get("sortDir")));
				}
				pagingLoader.load(config);
			}
		});
		grid.setLoadMask(true);
		grid.setAutoExpandColumn("space");

		panel.add(grid);

		panel.setBottomComponent(bottomToolBar);

		window.add(panel);
		window.add(formPanel);
	}

	Button btnExportExcel;

	Button btnClose;

	@Override
	public void addButtons() {
		btnExportExcel = new Button("Export Excel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				String url = GWT.getHostPageBaseURL() + "excel" + "?name=employeeinformation&searchkey=" + cmbSearch.getSimpleValue() + "&searchvalue=" + txtSearch.getValue();

				com.google.gwt.user.client.Window.open(url, "", "");
			}
		});
		btnExportExcel.setIcon(Resources.ICONS.excel16());
		window.addButton(btnExportExcel);

		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MainTabLayout.closeTab(window.getHeading());
			}
		});
		window.addButton(btnClose);
	}
}
