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
 * Listener that is notified of view_employee_report_to table changes.
 * @author sql2java
 */
public interface ViewEmployeeReportToListener
{
    /**
     * Invoked just before inserting a ViewEmployeeReportToBean record into the database.
     *
     * @param bean the ViewEmployeeReportToBean that is about to be inserted
     */
    public void beforeInsert(ViewEmployeeReportToBean bean) throws DAOException;


    /**
     * Invoked just after a ViewEmployeeReportToBean record is inserted in the database.
     *
     * @param bean the ViewEmployeeReportToBean that was just inserted
     */
    public void afterInsert(ViewEmployeeReportToBean bean) throws DAOException;


    /**
     * Invoked just before updating a ViewEmployeeReportToBean record in the database.
     *
     * @param bean the ViewEmployeeReportToBean that is about to be updated
     */
    public void beforeUpdate(ViewEmployeeReportToBean bean) throws DAOException;


    /**
     * Invoked just after updating a ViewEmployeeReportToBean record in the database.
     *
     * @param bean the ViewEmployeeReportToBean that was just updated
     */
    public void afterUpdate(ViewEmployeeReportToBean bean) throws DAOException;


    /**
     * Invoked just before deleting a ViewEmployeeReportToBean record in the database.
     *
     * @param bean the ViewEmployeeReportToBean that is about to be deleted
     */
    public void beforeDelete(ViewEmployeeReportToBean bean) throws DAOException;


    /**
     * Invoked just after deleting a ViewEmployeeReportToBean record in the database.
     *
     * @param bean the ViewEmployeeReportToBean that was just deleted
     */
    public void afterDelete(ViewEmployeeReportToBean bean) throws DAOException;

}
