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
 * Listener that is notified of tb_job_employment_status table changes.
 * @author sql2java
 */
public interface TbJobEmploymentStatusListener
{
    /**
     * Invoked just before inserting a TbJobEmploymentStatusBean record into the database.
     *
     * @param bean the TbJobEmploymentStatusBean that is about to be inserted
     */
    public void beforeInsert(TbJobEmploymentStatusBean bean) throws DAOException;


    /**
     * Invoked just after a TbJobEmploymentStatusBean record is inserted in the database.
     *
     * @param bean the TbJobEmploymentStatusBean that was just inserted
     */
    public void afterInsert(TbJobEmploymentStatusBean bean) throws DAOException;


    /**
     * Invoked just before updating a TbJobEmploymentStatusBean record in the database.
     *
     * @param bean the TbJobEmploymentStatusBean that is about to be updated
     */
    public void beforeUpdate(TbJobEmploymentStatusBean bean) throws DAOException;


    /**
     * Invoked just after updating a TbJobEmploymentStatusBean record in the database.
     *
     * @param bean the TbJobEmploymentStatusBean that was just updated
     */
    public void afterUpdate(TbJobEmploymentStatusBean bean) throws DAOException;


    /**
     * Invoked just before deleting a TbJobEmploymentStatusBean record in the database.
     *
     * @param bean the TbJobEmploymentStatusBean that is about to be deleted
     */
    public void beforeDelete(TbJobEmploymentStatusBean bean) throws DAOException;


    /**
     * Invoked just after deleting a TbJobEmploymentStatusBean record in the database.
     *
     * @param bean the TbJobEmploymentStatusBean that was just deleted
     */
    public void afterDelete(TbJobEmploymentStatusBean bean) throws DAOException;

}
