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
 * Listener that is notified of tb_job table changes.
 * @author sql2java
 */
public interface TbJobListener
{
    /**
     * Invoked just before inserting a TbJobBean record into the database.
     *
     * @param bean the TbJobBean that is about to be inserted
     */
    public void beforeInsert(TbJobBean bean) throws DAOException;


    /**
     * Invoked just after a TbJobBean record is inserted in the database.
     *
     * @param bean the TbJobBean that was just inserted
     */
    public void afterInsert(TbJobBean bean) throws DAOException;


    /**
     * Invoked just before updating a TbJobBean record in the database.
     *
     * @param bean the TbJobBean that is about to be updated
     */
    public void beforeUpdate(TbJobBean bean) throws DAOException;


    /**
     * Invoked just after updating a TbJobBean record in the database.
     *
     * @param bean the TbJobBean that was just updated
     */
    public void afterUpdate(TbJobBean bean) throws DAOException;


    /**
     * Invoked just before deleting a TbJobBean record in the database.
     *
     * @param bean the TbJobBean that is about to be deleted
     */
    public void beforeDelete(TbJobBean bean) throws DAOException;


    /**
     * Invoked just after deleting a TbJobBean record in the database.
     *
     * @param bean the TbJobBean that was just deleted
     */
    public void afterDelete(TbJobBean bean) throws DAOException;

}
