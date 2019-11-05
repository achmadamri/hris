package com.gwt.hris.listener;

import com.gwt.hris.db.TbEmergencyContactBean;
import com.gwt.hris.db.TbEmergencyContactListener;
import com.gwt.hris.db.exception.DAOException;

public class TbEmergencyContactListenerImpl implements TbEmergencyContactListener {

	@Override
	public void beforeInsert(TbEmergencyContactBean bean) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(TbEmergencyContactBean bean) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeUpdate(TbEmergencyContactBean bean) throws DAOException {
//		TbEmergencyContactBean beanBefore = TbEmergencyContactManager.getInstance().loadByPrimaryKey(bean.getTbecId());
//
//		System.out.println("BEFORE");
//		System.out.println(beanBefore);
//
//		System.out.println("");
//
//		System.out.println("AFTER");
//		System.out.println(bean);
	}

	@Override
	public void afterUpdate(TbEmergencyContactBean bean) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(TbEmergencyContactBean bean) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterDelete(TbEmergencyContactBean bean) throws DAOException {
		// TODO Auto-generated method stub

	}

}
