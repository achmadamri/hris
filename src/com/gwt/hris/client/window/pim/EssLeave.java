package com.gwt.hris.client.window.pim;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ViewEmployeeLeavesSummaryBeanModel;
import com.gwt.hris.client.service.leave.EmployeeLeavesSummaryInterface;
import com.gwt.hris.client.service.leave.EmployeeLeavesSummaryInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;

public class EssLeave extends EssMainCP {
	FormPanel formPanel;

	public EssLeave(FormData formData, int id_, int callerId_) {
		super(formData);

		id = id_;
		callerId = callerId_;

		addComponents();

		addButtons();

		btnBack.setVisible(true);

		bottomToolBar.refresh();
	}

	EmployeeLeavesSummaryInterfaceAsync interfaceAsync = GWT.create(EmployeeLeavesSummaryInterface.class);

	public int id = 0;
	public int callerId;

	RpcProxy<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewEmployeeLeavesSummaryBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<ViewEmployeeLeavesSummaryBeanModel> grid;

	public void addComponents() {
		setLayout(new RowLayout(Orientation.VERTICAL));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getTbAssignedLeavesPagingEss(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>>() {
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
			}
		};

		pagingLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
		pagingLoader.setRemoteSort(true);

		pagingStore = new ListStore<ViewEmployeeLeavesSummaryBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
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

		grid = new Grid<ViewEmployeeLeavesSummaryBeanModel>(pagingStore, cm);
		grid.setLoadMask(true);
		grid.setAutoExpandColumn("space");

		panel.add(grid);
		panel.setBottomComponent(bottomToolBar);

		add(panel, new RowData(1, 1));
	}

	Button btnBack;

	public void addButtons() {
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
