package com.gwt.hris.util;

import java.util.StringTokenizer;

public class StringUtil {
	private static StringUtil instance = new StringUtil();

	public static StringUtil getInstance() {
		return instance;
	}

	public String getTableField(String data) {
		String field = "";
		for (int i = 0; i < data.length(); i++) {
			String x = data.substring(i, i + 1);
			if (x.equals(x.toUpperCase())) {
				field = field + "_" + x.toLowerCase();
			} else {
				field = field + x;
			}
		}

		return field;
	}

	public String getFieldFormat(String strData) {
		StringTokenizer token = new StringTokenizer(strData, "_");
		String data = token.nextToken();
		String tmp = data;
		while (token.hasMoreTokens()) {
			data = token.nextToken();
			tmp += data.substring(0, 1).toUpperCase() + data.substring(1, data.length());
		}

		return tmp;
	}
}
