package com.gwt.hris.client.util;

import com.google.gwt.i18n.client.NumberFormat;


public class StringUtil {
	private static StringUtil instance = new StringUtil();

	public static StringUtil getInstance() {
		return instance;
	}

	public String getString(String strData) {
		return strData == null ? "" : strData;
	}

	public Integer getInteger(String strData) {
		return strData == null ? 0 : Integer.parseInt(strData);
	}

	public Double getDouble(String strData) {
		return strData == null ? 0 : Double.parseDouble(strData);
	}

	public String toString(Double data) {
		NumberFormat fmt = NumberFormat.getFormat("###.######");
		return fmt.format(data);
	}

	public String toString(Integer data) {
		NumberFormat fmt = NumberFormat.getFormat("###.######");
		return fmt.format(data);
	}
}
