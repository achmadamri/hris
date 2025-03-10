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
 * Listener that is notified of view_employee_skills table changes.
 * @author sql2java
 */
public interface ViewEmployeeSkillsListener
{
    /**
     * Invoked just before inserting a ViewEmployeeSkillsBean record into the database.
     *
     * @param bean the ViewEmployeeSkillsBean that is about to be inserted
     */
    public void beforeInsert(ViewEmployeeSkillsBean bean) throws DAOException;


    /**
     * Invoked just after a ViewEmployeeSkillsBean record is inserted in the database.
     *
     * @param bean the ViewEmployeeSkillsBean that was just inserted
     */
    public void afterInsert(ViewEmployeeSkillsBean bean) throws DAOException;


    /**
     * Invoked just before updating a ViewEmployeeSkillsBean record in the database.
     *
     * @param bean the ViewEmployeeSkillsBean that is about to be updated
     */
    public void beforeUpdate(ViewEmployeeSkillsBean bean) throws DAOException;


    /**
     * Invoked just after updating a ViewEmployeeSkillsBean record in the database.
     *
     * @param bean the ViewEmployeeSkillsBean that was just updated
     */
    public void afterUpdate(ViewEmployeeSkillsBean bean) throws DAOException;


    /**
     * Invoked just before deleting a ViewEmployeeSkillsBean record in the database.
     *
     * @param bean the ViewEmployeeSkillsBean that is about to be deleted
     */
    public void beforeDelete(ViewEmployeeSkillsBean bean) throws DAOException;


    /**
     * Invoked just after deleting a ViewEmployeeSkillsBean record in the database.
     *
     * @param bean the ViewEmployeeSkillsBean that was just deleted
     */
    public void afterDelete(ViewEmployeeSkillsBean bean) throws DAOException;

}
