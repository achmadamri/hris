package com.gwt.hris.client.util.bean;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class ComboBoxData extends BaseModelData {
	private static final long serialVersionUID = -5318216889245460280L;

	public ComboBoxData(String strId, String strValue) {
		set("id", strId);
		set("value", strValue);
	}
}
