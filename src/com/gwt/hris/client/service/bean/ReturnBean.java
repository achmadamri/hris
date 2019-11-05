package com.gwt.hris.client.service.bean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import com.extjs.gxt.ui.client.data.BaseTreeModel;

public class ReturnBean extends BaseTreeModel implements Serializable {
	private static final long serialVersionUID = -976435447360489942L;

	private int intReturn;

	private boolean operationStatus;

	private String message;

	private Integer maxPage;

	public String toString() {
		String returnValue = this.getClass().getName() + "@" + this.getClass().hashCode() + "[\n";

		Set<String> set = map.keySet();
		Iterator<String> itr = set.iterator();
		while (itr.hasNext()) {
			String strKey = (String) itr.next();
			returnValue += "  " + strKey + "=" + map.get(strKey) + "\n";
		}
		returnValue += "]";

		return returnValue;
	}

	public int getIntReturn() {
		return intReturn;
	}

	public void setIntReturn(int intReturn) {
		this.intReturn = intReturn;
	}

	public boolean getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(boolean status) {
		this.operationStatus = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}
}
