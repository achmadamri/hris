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
 * Listener that is notified of tb_employee_salary table changes.
 * @author sql2java
 */
public interface TbEmployeeSalaryListener
{
    /**
     * Invoked just before inserting a TbEmployeeSalaryBean record into the database.
     *
     * @param bean the TbEmployeeSalaryBean that is about to be inserted
     */
    public void beforeInsert(TbEmployeeSalaryBean bean) throws DAOException;


    /**
     * Invoked just after a TbEmployeeSalaryBean record is inserted in the database.
     *
     * @param bean the TbEmployeeSalaryBean that was just inserted
     */
    public void afterInsert(TbEmployeeSalaryBean bean) throws DAOException;


    /**
     * Invoked just before updating a TbEmployeeSalaryBean record in the database.
     *
     * @param bean the TbEmployeeSalaryBean that is about to be updated
     */
    public void beforeUpdate(TbEmployeeSalaryBean bean) throws DAOException;


    /**
     * Invoked just after updating a TbEmployeeSalaryBean record in the database.
     *
     * @param bean the TbEmployeeSalaryBean that was just updated
     */
    public void afterUpdate(TbEmployeeSalaryBean bean) throws DAOException;


    /**
     * Invoked just before deleting a TbEmployeeSalaryBean record in the database.
     *
     * @param bean the TbEmployeeSalaryBean that is about to be deleted
     */
    public void beforeDelete(TbEmployeeSalaryBean bean) throws DAOException;


    /**
     * Invoked just after deleting a TbEmployeeSalaryBean record in the database.
     *
     * @param bean the TbEmployeeSalaryBean that was just deleted
     */
    public void afterDelete(TbEmployeeSalaryBean bean) throws DAOException;

}
