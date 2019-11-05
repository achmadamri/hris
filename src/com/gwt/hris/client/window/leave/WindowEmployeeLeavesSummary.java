package com.gwt.hris.client.window.leave;

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
import com.extjs.gxt.ui.client.store.GroupingStore;
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
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewEmployeeLeavesSummaryBeanModel;
import com.gwt.hris.client.service.leave.EmployeeLeavesSummaryInterface;
import com.gwt.hris.client.service.leave.EmployeeLeavesSummaryInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowEmployeeLeavesSummary extends WindowMain {
	private static WindowEmployeeLeavesSummary instance = new WindowEmployeeLeavesSummary();

	public static WindowEmployeeLeavesSummary getInstance() {
		return instance;
	}

	public WindowEmployeeLeavesSummary() {
		window = new Window();
		window.setId("WindowEmployeeLeavesSummary");
		window.setSize(600, 380);
		window.setHeading("Leave : Employee Leaves Summary");
	}

	EmployeeLeavesSummaryInterfaceAsync employeeLeavesInterfaceAsync = GWT.create(EmployeeLeavesSummaryInterface.class);
	SimpleComboBox<String> cmbSearch;
	TextField<String> txtSearch = new TextField<String>();
	TextField<String> txtPageSize = new TextField<String>();
	Button btnGo;
	Button btnSearch;
	Button btnSearchReset;
	RpcProxy<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewEmployeeLeavesSummaryBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(20);
	Grid<ViewEmployeeLeavesSummaryBeanModel> grid;

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
		cmbSearch.setWidth(100);
		cmbSearch.add("Employee Name");
		cmbSearch.add("Leave Type");
		cmbSearch.add("Leave Entitled");
		cmbSearch.add("Leave Taken");
		cmbSearch.add("Leave Available");
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

		proxy = new RpcProxy<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				employeeLeavesInterfaceAsync.getTbAssignedLeavesPaging(pagingLoadConfig, cmbSearch.getSimpleValue(), txtSearch.getValue(), new AsyncCallback<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel> result) {
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

		pagingStore = new GroupingStore<ViewEmployeeLeavesSummaryBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbeName", "<center><b>Employee Name</b></center>", 100));

		ColumnConfig columnConfig = new ColumnConfig("tbltName", "<center><b>Leave Type</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbltName");
				return (data == null ? "-" : data);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbjtLeaveEntitled", "<center><b>Leave Entitled</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String strData = model.get("tbjtLeaveEntitled") == null ? "0 day" : model.get("tbjtLeaveEntitled") + " day(s)";
				if (Integer.parseInt(model.get("tbltReduction").toString()) == 0) {
					strData = "~";
				}
				return strData;
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("sumTbaleLeaveTaken", "<center><b>Leave Taken</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return model.get("sumTbaleLeaveTaken") == null ? "0 day" : model.get("sumTbaleLeaveTaken") + " day(s)";
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("minTbaleLeaveAvailable", "<center><b>Leave Available</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String strData = model.get("minTbaleLeaveAvailable") == null ? "0 day" : (model.get("minTbaleLeaveAvailable").toString().equals("999") ? (model.get("tbjtLeaveEntitled") == null ? "0 day" : model.get("tbjtLeaveEntitled") + " day(s)") : model.get("minTbaleLeaveAvailable") + " day(s)");
				if (Integer.parseInt(model.get("tbltReduction").toString()) == 0) {
					strData = "~";
				}
				return strData;
			}
		});
		columns.add(columnConfig);

		ColumnModel cm = new ColumnModel(columns);

		grid = new Grid<ViewEmployeeLeavesSummaryBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewEmployeeLeavesSummaryBeanModel>>() {
			public void handleEvent(GridEvent<ViewEmployeeLeavesSummaryBeanModel> be) {
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
		grid.setAutoExpandColumn("minTbaleLeaveAvailable");

		panel.add(grid);

		panel.setBottomComponent(bottomToolBar);

		window.add(panel);
	}

	Button btnClose;

	@Override
	public void addButtons() {
		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MainTabLayout.closeTab(window.getHeading());
			}
		});
		window.addButton(btnClose);
	}
}
