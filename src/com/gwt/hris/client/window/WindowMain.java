package com.gwt.hris.client.window;

import java.util.Iterator;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.DualListField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.gwt.hris.client.util.bean.ComboBoxData;

public class WindowMain {
	public ContentPanel window = new ContentPanel();

	public FormData formData = new FormData("95%");

	public FormPanel formPanel = new FormPanel();
	
	public void show(boolean parent) {
		String strHeading = window.getHeading();
		
		window = new ContentPanel();
		window.setBodyBorder(false);
		window.setHeading(strHeading);
		window.setLayout(new FitLayout());
		window.setHeaderVisible(false);
		window.setHeaderVisible(false);
		window.setFrame(false);

		formPanel = new FormPanel();
		formPanel.setLabelWidth(100);
		formPanel.setHeaderVisible(false);
		formPanel.setFrame(false);
		formPanel.setStyleAttribute("backgroundColor", "#F2F2F2");
		formPanel.setScrollMode(Scroll.AUTOY);

		formData = new FormData("95%");
		formData.setMargins(new Margins(0, 25, 0, 0));

		addComponents();

		addButtons();

		init();

		MainTabLayout.addTab(window, parent);
	}

	public void init() {
	}

	public void addComponents() {
	}

	public void addButtons() {
	}

	public void doResetComponent(Window window) {
		doResetComponent(window, -1, false);
	}

	public void doResetComponent(ContentPanel window) {
		doResetComponent(window, -1, false);
	}

	public void doResetComponent(Window window, int intTabIndex, boolean inContentPanel) {
		FormPanel formPanel = null;

		if (intTabIndex < 0) {
			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) window.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) window.getItem(0);
			}
		} else {
			TabPanel tabPanel = (TabPanel) window.getItem(0);
			TabItem tabItem = tabPanel.getItem(intTabIndex);

			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) tabItem.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) tabItem.getItem(0);
			}
		}

		Iterator<Component> itr = formPanel.getItems().iterator();
		while (itr.hasNext()) {
			Component component = itr.next();
			if ("com.extjs.gxt.ui.client.widget.form.TextField".equals(component.getClass().getName())) {
				TextField<String> x = (TextField<String>) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.TextArea".equals(component.getClass().getName())) {
				TextArea x = (TextArea) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.ComboBox".equals(component.getClass().getName())) {
				ComboBox<ComboBoxData> x = (ComboBox<ComboBoxData>) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.LabelField".equals(component.getClass().getName())) {
				LabelField x = (LabelField) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.FileUploadField".equals(component.getClass().getName())) {
				FileUploadField x = (FileUploadField) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.DateField".equals(component.getClass().getName())) {
				DateField x = (DateField) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.RadioGroup".equals(component.getClass().getName())) {
				RadioGroup x = (RadioGroup) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.SimpleComboBox".equals(component.getClass().getName())) {
				SimpleComboBox x = (SimpleComboBox) component;
				x.clear();
			}
		}
	}

	public void doResetComponent(ContentPanel window, int intTabIndex, boolean inContentPanel) {
		FormPanel formPanel = null;

		if (intTabIndex < 0) {
			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) window.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) window.getItem(0);
			}
		} else {
			TabPanel tabPanel = (TabPanel) window.getItem(0);
			TabItem tabItem = tabPanel.getItem(intTabIndex);

			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) tabItem.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) tabItem.getItem(0);
			}
		}

		Iterator<Component> itr = formPanel.getItems().iterator();
		while (itr.hasNext()) {
			Component component = itr.next();
			if ("com.extjs.gxt.ui.client.widget.form.TextField".equals(component.getClass().getName())) {
				TextField<String> x = (TextField<String>) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.TextArea".equals(component.getClass().getName())) {
				TextArea x = (TextArea) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.ComboBox".equals(component.getClass().getName())) {
				ComboBox<ComboBoxData> x = (ComboBox<ComboBoxData>) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.LabelField".equals(component.getClass().getName())) {
				LabelField x = (LabelField) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.FileUploadField".equals(component.getClass().getName())) {
				FileUploadField x = (FileUploadField) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.DateField".equals(component.getClass().getName())) {
				DateField x = (DateField) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.RadioGroup".equals(component.getClass().getName())) {
				RadioGroup x = (RadioGroup) component;
				x.clear();
			} else if ("com.extjs.gxt.ui.client.widget.form.SimpleComboBox".equals(component.getClass().getName())) {
				SimpleComboBox x = (SimpleComboBox) component;
				x.clear();
			}
		}
	}

	public void doLockedComponent(Window window) {
		doLockedComponent(window, -1, false);
	}

	public void doLockedComponent(ContentPanel window) {
		doLockedComponent(window, -1, false);
	}

	public void doLockedComponent(Window window, int intTabIndex, boolean inContentPanel) {
		FormPanel formPanel = null;

		if (intTabIndex < 0) {
			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) window.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) window.getItem(0);
			}
		} else {
			TabPanel tabPanel = (TabPanel) window.getItem(0);
			TabItem tabItem = tabPanel.getItem(intTabIndex);

			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) tabItem.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) tabItem.getItem(0);
			}
		}

		Iterator<Component> itr = formPanel.getItems().iterator();
		while (itr.hasNext()) {
			Component component = itr.next();
			if ("com.extjs.gxt.ui.client.widget.form.TextField".equals(component.getClass().getName())) {
				TextField<String> x = (TextField<String>) component;
				x.setReadOnly(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.TextArea".equals(component.getClass().getName())) {
				TextArea x = (TextArea) component;
				x.setReadOnly(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.ComboBox".equals(component.getClass().getName())) {
				ComboBox<ComboBoxData> x = (ComboBox<ComboBoxData>) component;
				x.setReadOnly(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.DualListField".equals(component.getClass().getName())) {
				DualListField<ComboBoxData> x = (DualListField<ComboBoxData>) component;
				x.setEnabled(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.DateField".equals(component.getClass().getName())) {
				DateField x = (DateField) component;
				x.setEnabled(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.RadioGroup".equals(component.getClass().getName())) {
				RadioGroup x = (RadioGroup) component;
				x.setEnabled(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.SimpleComboBox".equals(component.getClass().getName())) {
				SimpleComboBox x = (SimpleComboBox) component;
				x.setEnabled(false);
			}
		}
	}
	
	public void doLockedComponent(ContentPanel window, int intTabIndex, boolean inContentPanel) {
		FormPanel formPanel = null;

		if (intTabIndex < 0) {
			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) window.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) window.getItem(0);
			}
		} else {
			TabPanel tabPanel = (TabPanel) window.getItem(0);
			TabItem tabItem = tabPanel.getItem(intTabIndex);

			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) tabItem.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) tabItem.getItem(0);
			}
		}

		Iterator<Component> itr = formPanel.getItems().iterator();
		while (itr.hasNext()) {
			Component component = itr.next();
			if ("com.extjs.gxt.ui.client.widget.form.TextField".equals(component.getClass().getName())) {
				TextField<String> x = (TextField<String>) component;
				x.setReadOnly(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.TextArea".equals(component.getClass().getName())) {
				TextArea x = (TextArea) component;
				x.setReadOnly(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.ComboBox".equals(component.getClass().getName())) {
				ComboBox<ComboBoxData> x = (ComboBox<ComboBoxData>) component;
				x.setReadOnly(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.DualListField".equals(component.getClass().getName())) {
				DualListField<ComboBoxData> x = (DualListField<ComboBoxData>) component;
				x.setEnabled(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.DateField".equals(component.getClass().getName())) {
				DateField x = (DateField) component;
				x.setEnabled(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.RadioGroup".equals(component.getClass().getName())) {
				RadioGroup x = (RadioGroup) component;
				x.setEnabled(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.SimpleComboBox".equals(component.getClass().getName())) {
				SimpleComboBox x = (SimpleComboBox) component;
				x.setEnabled(false);
			}
		}
	}

	public void doUnlockedComponent(Window window) {
		doUnlockedComponent(window, -1, false);
	}

	public void doUnlockedComponent(ContentPanel window) {
		doUnlockedComponent(window, -1, false);
	}

	public void doUnlockedComponent(Window window, int intTabIndex, boolean inContentPanel) {
		FormPanel formPanel = null;

		if (intTabIndex < 0) {
			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) window.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) window.getItem(0);
			}
		} else {
			TabPanel tabPanel = (TabPanel) window.getItem(0);
			TabItem tabItem = tabPanel.getItem(intTabIndex);

			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) tabItem.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) tabItem.getItem(0);
			}
		}

		Iterator<Component> itr = formPanel.getItems().iterator();
		while (itr.hasNext()) {
			Component component = itr.next();
			if ("com.extjs.gxt.ui.client.widget.form.TextField".equals(component.getClass().getName())) {
				TextField<String> x = (TextField<String>) component;
				x.setReadOnly(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.TextArea".equals(component.getClass().getName())) {
				TextArea x = (TextArea) component;
				x.setReadOnly(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.ComboBox".equals(component.getClass().getName())) {
				ComboBox<ComboBoxData> x = (ComboBox<ComboBoxData>) component;
				x.setReadOnly(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.DualListField".equals(component.getClass().getName())) {
				DualListField<ComboBoxData> x = (DualListField<ComboBoxData>) component;
				x.setEnabled(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.DateField".equals(component.getClass().getName())) {
				DateField x = (DateField) component;
				x.setEnabled(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.RadioGroup".equals(component.getClass().getName())) {
				RadioGroup x = (RadioGroup) component;
				x.setEnabled(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.SimpleComboBox".equals(component.getClass().getName())) {
				SimpleComboBox x = (SimpleComboBox) component;
				x.setEnabled(true);
			}
		}
	}
	
	public void doUnlockedComponent(ContentPanel window, int intTabIndex, boolean inContentPanel) {
		FormPanel formPanel = null;

		if (intTabIndex < 0) {
			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) window.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) window.getItem(0);
			}
		} else {
			TabPanel tabPanel = (TabPanel) window.getItem(0);
			TabItem tabItem = tabPanel.getItem(intTabIndex);

			if (inContentPanel) {
				ContentPanel mainPanel = (ContentPanel) tabItem.getItem(0);
				formPanel = (FormPanel) mainPanel.getItem(0);
			} else {
				formPanel = (FormPanel) tabItem.getItem(0);
			}
		}

		Iterator<Component> itr = formPanel.getItems().iterator();
		while (itr.hasNext()) {
			Component component = itr.next();
			if ("com.extjs.gxt.ui.client.widget.form.TextField".equals(component.getClass().getName())) {
				TextField<String> x = (TextField<String>) component;
				x.setReadOnly(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.TextArea".equals(component.getClass().getName())) {
				TextArea x = (TextArea) component;
				x.setReadOnly(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.ComboBox".equals(component.getClass().getName())) {
				ComboBox<ComboBoxData> x = (ComboBox<ComboBoxData>) component;
				x.setReadOnly(false);
			} else if ("com.extjs.gxt.ui.client.widget.form.DualListField".equals(component.getClass().getName())) {
				DualListField<ComboBoxData> x = (DualListField<ComboBoxData>) component;
				x.setEnabled(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.DateField".equals(component.getClass().getName())) {
				DateField x = (DateField) component;
				x.setEnabled(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.RadioGroup".equals(component.getClass().getName())) {
				RadioGroup x = (RadioGroup) component;
				x.setEnabled(true);
			} else if ("com.extjs.gxt.ui.client.widget.form.SimpleComboBox".equals(component.getClass().getName())) {
				SimpleComboBox x = (SimpleComboBox) component;
				x.setEnabled(true);
			}
		}
	}
}
