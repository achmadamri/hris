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
 * Listener that is notified of tb_job_specifications table changes.
 * @author sql2java
 */
public interface TbJobSpecificationsListener
{
    /**
     * Invoked just before inserting a TbJobSpecificationsBean record into the database.
     *
     * @param bean the TbJobSpecificationsBean that is about to be inserted
     */
    public void beforeInsert(TbJobSpecificationsBean bean) throws DAOException;


    /**
     * Invoked just after a TbJobSpecificationsBean record is inserted in the database.
     *
     * @param bean the TbJobSpecificationsBean that was just inserted
     */
    public void afterInsert(TbJobSpecificationsBean bean) throws DAOException;


    /**
     * Invoked just before updating a TbJobSpecificationsBean record in the database.
     *
     * @param bean the TbJobSpecificationsBean that is about to be updated
     */
    public void beforeUpdate(TbJobSpecificationsBean bean) throws DAOException;


    /**
     * Invoked just after updating a TbJobSpecificationsBean record in the database.
     *
     * @param bean the TbJobSpecificationsBean that was just updated
     */
    public void afterUpdate(TbJobSpecificationsBean bean) throws DAOException;


    /**
     * Invoked just before deleting a TbJobSpecificationsBean record in the database.
     *
     * @param bean the TbJobSpecificationsBean that is about to be deleted
     */
    public void beforeDelete(TbJobSpecificationsBean bean) throws DAOException;


    /**
     * Invoked just after deleting a TbJobSpecificationsBean record in the database.
     *
     * @param bean the TbJobSpecificationsBean that was just deleted
     */
    public void afterDelete(TbJobSpecificationsBean bean) throws DAOException;

}
