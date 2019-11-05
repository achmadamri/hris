package com.gwt.hris.client.window.admin.time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.Orientation;
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
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.admin.time.EmployeeShiftInterface;
import com.gwt.hris.client.service.admin.time.EmployeeShiftInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewAttendanceBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.service.time.attendance.AttendanceInterface;
import com.gwt.hris.client.service.time.attendance.AttendanceInterfaceAsync;
import com.gwt.hris.client.window.WindowMain;

public class WindowEmployeeShiftList extends WindowMain {
	private static WindowEmployeeShiftList instance = new WindowEmployeeShiftList();

	public static WindowEmployeeShiftList getInstance() {
		return instance;
	}

	public void show(int id) {
		this.id = id;

		super.show(true);
	}

	@Override
	public void init() {
		employeeListInterfaceAsync.getEmployee(id, getEmployeeCallback);
	}

	public WindowEmployeeShiftList() {
		window = new Window();
		window.setId("WindowEmployeeShiftList");
		window.setSize(840, 420);
		window.setHeading("Time : Employee Shift List");
	}

	public int id = 0;

	EmployeeShiftInterfaceAsync interfaceAsync = GWT.create(EmployeeShiftInterface.class);
	AttendanceInterfaceAsync attendanceInterfaceAsync = GWT.create(AttendanceInterface.class);
	EmployeeListInterfaceAsync employeeListInterfaceAsync = GWT.create(EmployeeListInterface.class);

	SimpleComboBox<String> cmbMonth;
	SimpleComboBox<String> cmbSearch;
	TextField<String> txtSearch = new TextField<String>();
	Button btnGo;
	Button btnSearch;
	Button btnSearchReset;
	RpcProxy<PagingLoadResult<ViewAttendanceBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewAttendanceBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(31);
	Grid<ViewAttendanceBeanModel> grid;

	LabelField lblEmployeeName = new LabelField();

	int deleteID;

	AsyncCallback<TbEmployeeBeanModel> getEmployeeCallback = new AsyncCallback<TbEmployeeBeanModel>() {
		@Override
		public void onSuccess(TbEmployeeBeanModel result) {
			if (result.getOperationStatus()) {
				lblEmployeeName.setText(result.getTbeName());
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

	AsyncCallback<ReturnBean> getMonthCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				cmbMonth.clear();

				Date dateNow = new Date();
				DateTimeFormat fmt = DateTimeFormat.getFormat("MM");

				ReturnBean datas[] = result.get("datas");
				for (ReturnBean data : datas) {
					cmbMonth.add(data.get("month").toString());
					if (fmt.format(dateNow).equals(data.get("month").toString())) {
						cmbMonth.setSimpleValue(data.get("month").toString());
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
	
	ContentPanel panel;

	@Override
	public void addComponents() {
		ContentPanel mainPanel = new ContentPanel();
		mainPanel.setHeaderVisible(false);
		mainPanel.setFrame(false);
		mainPanel.setLayout(new RowLayout(Orientation.VERTICAL));

		lblEmployeeName.setFieldLabel("Employee Name");
		formPanel.add(lblEmployeeName, formData);

		mainPanel.add(formPanel, new RowData(1, -1));

		panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		ToolBar toolBar = new ToolBar();
		bottomToolBar = new PagingToolBar(20);

		toolBar.add(new LabelToolItem("Month : "));

		cmbMonth = new SimpleComboBox<String>();
		cmbMonth.setTriggerAction(TriggerAction.ALL);
		cmbMonth.setEditable(false);
		cmbMonth.setWidth(50);
		toolBar.add(cmbMonth);
		attendanceInterfaceAsync.getMonth(getMonthCallback);

		btnGo = new Button("Go", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				bottomToolBar.refresh();
			}
		});
		toolBar.add(btnGo);

		toolBar.add(new LabelToolItem("Search By : "));

		cmbSearch = new SimpleComboBox<String>();
		cmbSearch.setTriggerAction(TriggerAction.ALL);
		cmbSearch.setEditable(false);
		cmbSearch.setWidth(110);
		cmbSearch.add("Project");
		cmbSearch.add("Activity");
		cmbSearch.add("Start of Week");
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

		CheckBoxSelectionModel<ViewAttendanceBeanModel> checkBoxSelectionModel = new CheckBoxSelectionModel<ViewAttendanceBeanModel>();
		proxy = new RpcProxy<PagingLoadResult<ViewAttendanceBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewAttendanceBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getTimesheetPaging(pagingLoadConfig, id, cmbMonth.getSimpleValue(), cmbSearch.getSimpleValue(), txtSearch.getValue(), new AsyncCallback<PagingLoadResult<ViewAttendanceBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewAttendanceBeanModel> result) {
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

		pagingStore = new ListStore<ViewAttendanceBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(checkBoxSelectionModel.getColumn());
		columns.add(new ColumnConfig("tbaDate", "<center><b>Date</b></center>", 70));

		ColumnConfig columnConfig = new ColumnConfig("tbaInTime", "<center><b>Punch In</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbaInTime");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaOutTime", "<center><b>Punch Out</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbaOutTime");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbsName", "<center><b>Shift</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbsName");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbsInTime", "<center><b>Shift In</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbsInTime");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbsOutTime", "<center><b>Shift Out</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbsOutTime");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaInTimeDiff", "<center><b>In Late</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbaInTimeDiff");
				if (data == null) {
					return "-";
				} else if (data > 0) {
					int hours = (int) ((data / (1000 * 60 * 60)) % 24);
					int minutes = (int) ((data / (1000 * 60)) % 60);
					int seconds = (int) (data / 1000) % 60;

					String strHours = hours < 9 ? "0" + hours : "" + hours;
					String strMinutes = minutes < 9 ? "0" + minutes : "" + minutes;
					String strSeconds = seconds < 9 ? "0" + seconds : "" + seconds;

					return strHours + ":" + strMinutes + ":" + strSeconds;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaOutTimeDiff", "<center><b>Undertime</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbaOutTimeDiff");
				if (data == null) {
					return "-";
				} else if (data < 0) {
					data = data * -1;
					int hours = (int) ((data / (1000 * 60 * 60)) % 24);
					int minutes = (int) ((data / (1000 * 60)) % 60);
					int seconds = (int) (data / 1000) % 60;

					String strHours = hours < 9 ? "0" + hours : "" + hours;
					String strMinutes = minutes < 9 ? "0" + minutes : "" + minutes;
					String strSeconds = seconds < 9 ? "0" + seconds : "" + seconds;

					return strHours + ":" + strMinutes + ":" + strSeconds;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaOutTimeDiff", "<center><b>Overtime</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbaOutTimeDiff");
				if (data == null) {
					return "-";
				} else if (data > 0) {
					int hours = (int) ((data / (1000 * 60 * 60)) % 24);
					int minutes = (int) ((data / (1000 * 60)) % 60);
					int seconds = (int) (data / 1000) % 60;

					String strHours = hours < 9 ? "0" + hours : "" + hours;
					String strMinutes = minutes < 9 ? "0" + minutes : "" + minutes;
					String strSeconds = seconds < 9 ? "0" + seconds : "" + seconds;

					return strHours + ":" + strMinutes + ":" + strSeconds;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		ColumnConfig ccActions = new ColumnConfig();
		ccActions.setId("actions");
		ccActions.setWidth(120);
		ccActions.setHeader("<center><b>Actions</b></center>");
		ccActions.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(final ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				LayoutContainer layoutContainer = new LayoutContainer();

				String inTime = model.get("tbaInTime");
				String outTime = model.get("tbaOutTime");

				if (inTime != null) {
					HBoxLayout hBoxLayout = new HBoxLayout();
					hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
					hBoxLayout.setPack(BoxLayoutPack.CENTER);
					layoutContainer.setLayout(hBoxLayout);

					HBoxLayoutData hBoxLayoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

					Button btnApprovalView = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							String strDate = model.get("tbaDate").toString();
							WindowEmployeeShiftDetail windowEmployeeShiftDetail = WindowEmployeeShiftDetail.getInstance();
							windowEmployeeShiftDetail.show("inDetail", strDate);
						}
					});
					btnApprovalView.setToolTip("punch in detail");
					btnApprovalView.setIcon(Resources.ICONS.text());
					layoutContainer.add(btnApprovalView, hBoxLayoutData);
				}

				if (outTime != null) {
					HBoxLayout hBoxLayout = new HBoxLayout();
					hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
					hBoxLayout.setPack(BoxLayoutPack.CENTER);
					layoutContainer.setLayout(hBoxLayout);

					HBoxLayoutData hBoxLayoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

					Button btnApprovalView = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							String strDate = model.get("tbaDate").toString();
							WindowEmployeeShiftDetail windowEmployeeShiftDetail = WindowEmployeeShiftDetail.getInstance();
							windowEmployeeShiftDetail.show("outDetail", strDate);
						}
					});
					btnApprovalView.setToolTip("punch out detail");
					btnApprovalView.setIcon(Resources.ICONS.text());
					layoutContainer.add(btnApprovalView, hBoxLayoutData);
				}

				return layoutContainer;
			}
		});
		columns.add(ccActions);

		ccActions = new ColumnConfig();
		ccActions.setId("space");
		ccActions.setHeader("");
		ccActions.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(final ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return null;
			}
		});
		columns.add(ccActions);

		ColumnModel cm = new ColumnModel(columns);

		grid = new Grid<ViewAttendanceBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewAttendanceBeanModel>>() {
			public void handleEvent(GridEvent<ViewAttendanceBeanModel> be) {
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
		grid.addPlugin(checkBoxSelectionModel);
		grid.setSelectionModel(checkBoxSelectionModel);

		panel.add(grid);

		panel.setBottomComponent(bottomToolBar);

		mainPanel.add(panel, new RowData(1, 1));

		window.add(mainPanel);
	}

	Button btnSetShift;

	Button btnBack;

	@Override
	public void addButtons() {
		btnSetShift = new Button("Set Shift", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (grid.getSelectionModel().getSelectedItems().size() > 0) {
					WindowEmployeeShiftSet windowEmployeeShiftSet = WindowEmployeeShiftSet.getInstance();
					windowEmployeeShiftSet.show(id);
				} else {
					MessageBox.alert("Alert", "No data selected", null);
				}
			}
		});
		window.addButton(btnSetShift);

		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowEmployeeShift window = WindowEmployeeShift.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}
}
