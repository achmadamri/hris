// ______________________________________________________
// Generated by sql2java - http://sql2java.sourceforge.net/
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
//
// Please help us improve this tool by reporting:
// - problems and suggestions to
//   http://sourceforge.net/tracker/?group_id=54687
// - feedbacks and ideas on
//   http://sourceforge.net/forum/forum.php?forum_id=182208
// ______________________________________________________

package com.gwt.hris.db;

import com.gwt.hris.db.exception.DAOException;

/**
 * Listener that is notified of view_renumeration table changes.
 * @author sql2java
 */
public interface ViewRenumerationListener
{
    /**
     * Invoked just before inserting a ViewRenumerationBean record into the database.
     *
     * @param bean the ViewRenumerationBean that is about to be inserted
     */
    public void beforeInsert(ViewRenumerationBean bean) throws DAOException;


    /**
     * Invoked just after a ViewRenumerationBean record is inserted in the database.
     *
     * @param bean the ViewRenumerationBean that was just inserted
     */
    public void afterInsert(ViewRenumerationBean bean) throws DAOException;


    /**
     * Invoked just before updating a ViewRenumerationBean record in the database.
     *
     * @param bean the ViewRenumerationBean that is about to be updated
     */
    public void beforeUpdate(ViewRenumerationBean bean) throws DAOException;


    /**
     * Invoked just after updating a ViewRenumerationBean record in the database.
     *
     * @param bean the ViewRenumerationBean that was just updated
     */
    public void afterUpdate(ViewRenumerationBean bean) throws DAOException;


    /**
     * Invoked just before deleting a ViewRenumerationBean record in the database.
     *
     * @param bean the ViewRenumerationBean that is about to be deleted
     */
    public void beforeDelete(ViewRenumerationBean bean) throws DAOException;


    /**
     * Invoked just after deleting a ViewRenumerationBean record in the database.
     *
     * @param bean the ViewRenumerationBean that was just deleted
     */
    public void afterDelete(ViewRenumerationBean bean) throws DAOException;

}
