package com.gwt.hris.client.window.pim;

import java.util.Iterator;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.DualListField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.gwt.hris.client.util.bean.ComboBoxData;

public class EssMainCP extends ContentPanel {
	public FormData formData;

	public EssMainCP(FormData formData) {
		setHeaderVisible(false);
		setFrame(false);
		setScrollMode(Scroll.AUTO);

		this.formData = formData;

		init();
	}

	public void addComponents() {
	}

	public void addButtons() {
	}

	public void init() {
	}

	public void doLockedComponent(FormPanel formPanel) {
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
			}
		}
	}

	public void doUnlockedComponent(FormPanel formPanel) {
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
			}
		}
	}

	public void doResetComponent(FormPanel formPanel) {
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
			}
		}
	}
}
