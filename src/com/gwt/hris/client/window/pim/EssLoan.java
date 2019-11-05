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
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ViewAssignedLoanBeanModel;
import com.gwt.hris.client.service.loan.AssignedLoanInterface;
import com.gwt.hris.client.service.loan.AssignedLoanInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;

public class EssLoan extends EssMainCP {
	FormPanel formPanel;

	public EssLoan(FormData formData, int id_, int callerId_) {
		super(formData);

		id = id_;
		callerId = callerId_;

		addComponents();

		addButtons();

		btnBack.setVisible(true);

		bottomToolBar.refresh();
	}

	AssignedLoanInterfaceAsync interfaceAsync = GWT.create(AssignedLoanInterface.class);

	public int id = 0;
	public int callerId;

	RpcProxy<PagingLoadResult<ViewAssignedLoanBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewAssignedLoanBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<ViewAssignedLoanBeanModel> grid;

	public void addComponents() {
		setLayout(new RowLayout(Orientation.VERTICAL));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewAssignedLoanBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewAssignedLoanBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getAssignedLoanPagingEss(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<ViewAssignedLoanBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewAssignedLoanBeanModel> result) {
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

		pagingStore = new ListStore<ViewAssignedLoanBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		columns.add(new ColumnConfig("tbaloStartDate", "<center><b>Start Date</b></center>", 75));
		columns.add(new ColumnConfig("tbaloEndDate", "<center><b>End Date</b></center>", 75));
		columns.add(new ColumnConfig("tbaloName", "<center><b>Name</b></center>", 150));
		columns.add(new ColumnConfig("tbcCurrencyId", "<center><b>Currency</b></center>", 75));

		ColumnConfig columnConfig = new ColumnConfig("tbaloNominal", "<center><b>Nominal</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbaloNominal");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);
		
		columns.add(new ColumnConfig("tbaloInterest", "<center><b>Interest</b></center>", 75));
		columns.add(new ColumnConfig("tbaloTenor", "<center><b>Tenor</b></center>", 75));
		
		columnConfig = new ColumnConfig("tbaloNominalTotal", "<center><b>Nominal Total</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbaloNominalTotal");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);
		
		columnConfig = new ColumnConfig("tbaloMonthlyPayment", "<center><b>Monthly Payment</b></center>", 110);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbaloMonthlyPayment");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);
		
		columnConfig = new ColumnConfig("tbaloNominalTotalLeft", "<center><b>Nominal Total Left</b></center>", 110);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbaloNominalTotalLeft");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
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

		grid = new Grid<ViewAssignedLoanBeanModel>(pagingStore, cm);
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
