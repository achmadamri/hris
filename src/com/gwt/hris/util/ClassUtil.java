package com.gwt.hris.util;

import java.lang.reflect.Method;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static ClassUtil singleton = new ClassUtil();

	public static ClassUtil getInstance() {
		return singleton;
	}

	public void logError(Logger classLog, Exception e) {
		classLog.error(e.getMessage());
		StackTraceElement ste[] = e.getStackTrace();
		for (StackTraceElement ste_ : ste) {
			String strLog = "at " + ste_.getClassName() + "." + ste_.getMethodName() + "(" + ste_.getFileName() + ":" + ste_.getLineNumber() + ")";
			classLog.error(strLog);
		}
	}

	public void assign(Object objectFrom, Object objectTo) {
		Class classFrom = objectFrom.getClass();
		Class classTo = objectTo.getClass();

		Method methodsFrom[] = classFrom.getDeclaredMethods();

		for (Method methodFrom : methodsFrom) {
			if ("get".equals(methodFrom.getName().substring(0, 3)) && !("getValue".equals(methodFrom.getName()) || "getDictionnary".equals(methodFrom.getName()) || "getPkDictionnary".equals(methodFrom.getName()))) {
				try {
					Object outputGet = methodFrom.invoke(objectFrom, null);

					if (outputGet != null) {
						Method methodTo = null;

						String strMethodName = "s" + methodFrom.getName().substring(1);

						if ("java.sql.Timestamp".equals(outputGet.getClass().getName())) {
							java.sql.Timestamp outputGetTimeStamp_ = (java.sql.Timestamp) outputGet;

							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(outputGetTimeStamp_.getTime());

							if ("WS".equals(classFrom.getName().substring(classFrom.getName().length() - 2, classFrom.getName().length()))) {
								if (Character.isLowerCase(strMethodName.substring(3, 4).toCharArray()[0]) && Character.isUpperCase(strMethodName.substring(4, 5).toCharArray()[0])) {
									strMethodName = strMethodName.substring(0, 3) + strMethodName.substring(3, 4).toUpperCase() + strMethodName.substring(4);
								}
							} else {
								if (Character.isUpperCase(strMethodName.substring(3, 4).toCharArray()[0]) && Character.isUpperCase(strMethodName.substring(4, 5).toCharArray()[0])) {
									strMethodName = strMethodName.substring(0, 3) + strMethodName.substring(3, 4).toLowerCase() + strMethodName.substring(4);
								}
							}

							methodTo = classTo.getMethod(strMethodName, calendar.getTime().getClass());
						} else {
							if ("WS".equals(classFrom.getName().substring(classFrom.getName().length() - 2, classFrom.getName().length()))) {
								if (Character.isLowerCase(strMethodName.substring(3, 4).toCharArray()[0]) && Character.isUpperCase(strMethodName.substring(4, 5).toCharArray()[0])) {
									strMethodName = strMethodName.substring(0, 3) + strMethodName.substring(3, 4).toUpperCase() + strMethodName.substring(4);
								}
							} else {
								if (Character.isUpperCase(strMethodName.substring(3, 4).toCharArray()[0]) && Character.isUpperCase(strMethodName.substring(4, 5).toCharArray()[0])) {
									strMethodName = strMethodName.substring(0, 3) + strMethodName.substring(3, 4).toLowerCase() + strMethodName.substring(4);
								}
							}

							methodTo = classTo.getMethod(strMethodName, outputGet.getClass());
						}
						methodTo.invoke(objectTo, outputGet);
					}
				} catch (Exception e) {
					ClassUtil.getInstance().logError(log, e);
					e.printStackTrace();
				}
			}
		}

		classFrom = objectFrom.getClass();
	}
}
