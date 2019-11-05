package com.gwt.hris.client.window.reports;

import java.util.ArrayList;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewTimesheetProjectBeanModel;
import com.gwt.hris.client.service.reports.EmployeeTimesheetInterface;
import com.gwt.hris.client.service.reports.EmployeeTimesheetInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowEmployeeTimesheet extends WindowMain {
	private static WindowEmployeeTimesheet instance = new WindowEmployeeTimesheet();

	public static WindowEmployeeTimesheet getInstance() {
		return instance;
	}

	public WindowEmployeeTimesheet() {
		window = new Window();
		window.setId("WindowEmployeeTimesheet");
		window.setSize(940, 380);
		window.setHeading("Reports : Employee Timesheet");
	}

	EmployeeTimesheetInterfaceAsync interfaceAsync = GWT.create(EmployeeTimesheetInterface.class);
	SimpleComboBox<String> cmbSearch;
	TextField<String> txtSearch = new TextField<String>();
	TextField<String> txtPageSize = new TextField<String>();
	Button btnGo;
	Button btnSearch;
	Button btnSearchReset;
	RpcProxy<PagingLoadResult<ViewTimesheetProjectBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewTimesheetProjectBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(20);
	Grid<ViewTimesheetProjectBeanModel> grid;

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
		cmbSearch.add("Customer Name");
		cmbSearch.add("Project Name");
		cmbSearch.add("Employee Name");
		cmbSearch.add("Activity Group");
		cmbSearch.add("Activity");
		cmbSearch.add("Start Of Week");
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

		proxy = new RpcProxy<PagingLoadResult<ViewTimesheetProjectBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewTimesheetProjectBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getPaging(pagingLoadConfig, cmbSearch.getSimpleValue(), txtSearch.getValue(), new AsyncCallback<PagingLoadResult<ViewTimesheetProjectBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewTimesheetProjectBeanModel> result) {
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

		pagingStore = new ListStore<ViewTimesheetProjectBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbcName", "<center><b>Customer Name</b></center>", 150));
		columns.add(new ColumnConfig("tbpName", "<center><b>Project Name</b></center>", 150));
		columns.add(new ColumnConfig("tbpProjectId", "<center><b>Project ID</b></center>", 75));
		
		ColumnConfig columnConfig = new ColumnConfig("tbtStartOfWeek", "<center><b>Start Of Week</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				if (model.get("tbtStartOfWeek") != null) {
					return model.get("tbtStartOfWeek");
				} else {
					return "";
				}
			}
		});
		columns.add(columnConfig);
		
		columns.add(new ColumnConfig("tbeName", "<center><b>Employee Name</b></center>", 150));
		columns.add(new ColumnConfig("tbpagName", "<center><b>Activity Group</b></center>", 100));
		columns.add(new ColumnConfig("tbpaName", "<center><b>Activity</b></center>", 150));

		columnConfig = new ColumnConfig("tbpaPayable", "<center><b>Payable</b></center>", 75);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbpaPayable");
				return (data == 1 ? "Payable" : "Non Payable");
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("sumTbtTotalHour", "<center><b>Total Hour</b></center>", 75);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return model.get("sumTbtTotalHour").toString() + " hour(s)";
			}
		});
		columns.add(columnConfig);

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

		grid = new Grid<ViewTimesheetProjectBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewTimesheetProjectBeanModel>>() {
			public void handleEvent(GridEvent<ViewTimesheetProjectBeanModel> be) {
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
				String url = GWT.getHostPageBaseURL() + "excel" + "?name=employeetimesheet&searchkey=" + cmbSearch.getSimpleValue() + "&searchvalue=" + txtSearch.getValue();

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
