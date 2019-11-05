package com.gwt.hris.client.window.loan;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
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
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewAssignedLoanBeanModel;
import com.gwt.hris.client.service.loan.AssignedLoanInterface;
import com.gwt.hris.client.service.loan.AssignedLoanInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowAssignedLoan extends WindowMain {
	private static WindowAssignedLoan instance = new WindowAssignedLoan();

	public static WindowAssignedLoan getInstance() {
		return instance;
	}

	public WindowAssignedLoan() {
		window = new Window();
		window.setId("WindowAssignedLoan");
		window.setSize(1000, 380);
		window.setHeading("Loan : Assigned Loan");
	}

	AssignedLoanInterfaceAsync interfaceAsync = GWT.create(AssignedLoanInterface.class);
	SimpleComboBox<String> cmbSearch;
	TextField<String> txtSearch = new TextField<String>();
	TextField<String> txtPageSize = new TextField<String>();
	Button btnGo;
	Button btnSearch;
	Button btnSearchReset;
	RpcProxy<PagingLoadResult<ViewAssignedLoanBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewAssignedLoanBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(20);
	Grid<ViewAssignedLoanBeanModel> grid;

	int deleteID;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				interfaceAsync.deleteAssignedLoan(deleteID, callback);
			}
		}
	};

	Listener<MessageBoxEvent> deleteBulkListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				List<ViewAssignedLoanBeanModel> lst = grid.getSelectionModel().getSelectedItems();

				Integer ids[] = new Integer[lst.size()];
				Iterator<ViewAssignedLoanBeanModel> itr = lst.iterator();
				int i = 0;
				while (itr.hasNext()) {
					ViewAssignedLoanBeanModel data = (ViewAssignedLoanBeanModel) itr.next();
					ids[i] = data.getTbaloId();
					i++;
				}
				interfaceAsync.deleteBulkAssignedLoan(ids, callback);
			}
		}
	};

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
		cmbSearch.add("Loan Name");
		cmbSearch.add("Nominal");
		cmbSearch.add("Interest");
		cmbSearch.add("Tenor");
		cmbSearch.add("Total Loan");
		cmbSearch.add("Start Date");
		cmbSearch.add("End Date");
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

		CheckBoxSelectionModel<ViewAssignedLoanBeanModel> checkBoxSelectionModel = new CheckBoxSelectionModel<ViewAssignedLoanBeanModel>();

		proxy = new RpcProxy<PagingLoadResult<ViewAssignedLoanBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewAssignedLoanBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getAssignedLoanPaging(pagingLoadConfig, cmbSearch.getSimpleValue(), txtSearch.getValue(), new AsyncCallback<PagingLoadResult<ViewAssignedLoanBeanModel>>() {
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
				
				panel.getBottomComponent().setEnabled(true);
			}
		};

		pagingLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
		pagingLoader.setRemoteSort(true);

		pagingStore = new ListStore<ViewAssignedLoanBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(checkBoxSelectionModel.getColumn());
		columns.add(new ColumnConfig("tbaloName", "<center><b>Loan Name</b></center>", 150));
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

		columnConfig = new ColumnConfig("tbaloInterest", "<center><b>Interest</b></center>", 75);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return model.get("tbaloInterest") + " %";
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaloTenor", "<center><b>Tenor</b></center>", 75);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return model.get("tbaloTenor") + " month(s)";
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaloNominalTotal", "<center><b>Total Loan</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbaloNominalTotal");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

		columns.add(new ColumnConfig("tbaloStartDate", "<center><b>Start Date</b></center>", 75));

		columns.add(new ColumnConfig("tbaloEndDate", "<center><b>End Date</b></center>", 75));

		columnConfig = new ColumnConfig("tbaloMonthlyPayment", "<center><b>Payment</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbaloMonthlyPayment");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaloStatus", "<center><b>Status</b></center>", 75);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbaloStatus");
				return (data == 0 ? "Pending" : (data == 1 ? "Approved" : "Rejected"));
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
				Integer data = model.get("tbaloStatus");
				
				if (data == 0 || data == 2) {
					LayoutContainer layoutContainer = new LayoutContainer();
					HBoxLayout hBoxLayout = new HBoxLayout();
					hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
					hBoxLayout.setPack(BoxLayoutPack.CENTER);
					layoutContainer.setLayout(hBoxLayout);

					HBoxLayoutData hBoxLayoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

					if (data == 0) {
						Button btnEdit = new Button("", new SelectionListener<ButtonEvent>() {
							@Override
							public void componentSelected(ButtonEvent ce) {
								int id = Integer.parseInt(model.get("tbaloId").toString());
								WindowAssignedLoanAddEdit windowAssignedLoanAddEdit = WindowAssignedLoanAddEdit.getInstance();
								windowAssignedLoanAddEdit.show("edit", id);
							}
						});
						btnEdit.setToolTip("edit");
						btnEdit.setIcon(Resources.ICONS.form());
						layoutContainer.add(btnEdit, hBoxLayoutData);
					}

					Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							deleteID = Integer.parseInt(model.get("tbaloId").toString());

							MessageBox.confirm("Confirmation", "Are you sure you want to delete loan \"" + model.get("tbaloName") + "\" ?", deleteListener);
						}
					});
					btnDelete.setToolTip("delete");
					btnDelete.setIcon(Resources.ICONS.delete());
					layoutContainer.add(btnDelete, hBoxLayoutData);

					return layoutContainer;
				} else {
					LayoutContainer layoutContainer = new LayoutContainer();
					HBoxLayout hBoxLayout = new HBoxLayout();
					hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
					hBoxLayout.setPack(BoxLayoutPack.CENTER);
					layoutContainer.setLayout(hBoxLayout);

					HBoxLayoutData hBoxLayoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

					Button btnView = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							int id = Integer.parseInt(model.get("tbaloId").toString());
							WindowAssignedLoanView windowAssignedLoanView = WindowAssignedLoanView.getInstance();
							windowAssignedLoanView.show("view", id);
						}
					});
					btnView.setToolTip("view");
					btnView.setIcon(Resources.ICONS.text());
					layoutContainer.add(btnView, hBoxLayoutData);

					return layoutContainer;
				}
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

		grid = new Grid<ViewAssignedLoanBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewAssignedLoanBeanModel>>() {
			public void handleEvent(GridEvent<ViewAssignedLoanBeanModel> be) {
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

		window.add(panel);
	}

	Button btnAdd;

	Button btnDelete;

	Button btnClose;

	@Override
	public void addButtons() {
		btnAdd = new Button("Add", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowAssignedLoanAddEdit windowAssignedLoanAddEdit = WindowAssignedLoanAddEdit.getInstance();
				windowAssignedLoanAddEdit.show("add", 0);
			}
		});
		window.addButton(btnAdd);

		btnDelete = new Button("Delete", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (grid.getSelectionModel().getSelectedItems().size() > 0) {
					MessageBox.confirm("Confirmation", "Are you sure you want to delete the selected data ?", deleteBulkListener);
				}
			}
		});
		window.addButton(btnDelete);

		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MainTabLayout.closeTab(window.getHeading());
			}
		});
		window.addButton(btnClose);
	}
}
