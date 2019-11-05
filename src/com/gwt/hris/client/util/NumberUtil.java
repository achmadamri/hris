package com.gwt.hris.client.util;

import java.math.BigDecimal;

public class NumberUtil {
	private static NumberUtil instance = new NumberUtil();

	public static NumberUtil getInstance() {
		return instance;
	}

	public static double round(double d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
}
