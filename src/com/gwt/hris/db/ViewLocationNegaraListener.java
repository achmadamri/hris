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
 * Listener that is notified of view_location_negara table changes.
 * @author sql2java
 */
public interface ViewLocationNegaraListener
{
    /**
     * Invoked just before inserting a ViewLocationNegaraBean record into the database.
     *
     * @param bean the ViewLocationNegaraBean that is about to be inserted
     */
    public void beforeInsert(ViewLocationNegaraBean bean) throws DAOException;


    /**
     * Invoked just after a ViewLocationNegaraBean record is inserted in the database.
     *
     * @param bean the ViewLocationNegaraBean that was just inserted
     */
    public void afterInsert(ViewLocationNegaraBean bean) throws DAOException;


    /**
     * Invoked just before updating a ViewLocationNegaraBean record in the database.
     *
     * @param bean the ViewLocationNegaraBean that is about to be updated
     */
    public void beforeUpdate(ViewLocationNegaraBean bean) throws DAOException;


    /**
     * Invoked just after updating a ViewLocationNegaraBean record in the database.
     *
     * @param bean the ViewLocationNegaraBean that was just updated
     */
    public void afterUpdate(ViewLocationNegaraBean bean) throws DAOException;


    /**
     * Invoked just before deleting a ViewLocationNegaraBean record in the database.
     *
     * @param bean the ViewLocationNegaraBean that is about to be deleted
     */
    public void beforeDelete(ViewLocationNegaraBean bean) throws DAOException;


    /**
     * Invoked just after deleting a ViewLocationNegaraBean record in the database.
     *
     * @param bean the ViewLocationNegaraBean that was just deleted
     */
    public void afterDelete(ViewLocationNegaraBean bean) throws DAOException;

}
