package com.gwt.hris.db;

import com.gwt.hris.db.exception.DAOException;

public class Test {
	public static void main(String args[]) {
		TbPerusahaanBean tbPerusahaanBean = TbPerusahaanManager.getInstance().createTbPerusahaanBean();
		try {
			tbPerusahaanBean.setTbpName("test");
			TbPerusahaanManager.getInstance().save(tbPerusahaanBean);
			System.out.println(tbPerusahaanBean);
			tbPerusahaanBean.setTbpPerusahaanId("COM" + tbPerusahaanBean.getTbpId());
			TbPerusahaanManager.getInstance().update(tbPerusahaanBean);
			System.out.println(tbPerusahaanBean);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
