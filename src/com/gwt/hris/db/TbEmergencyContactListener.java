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
 * Listener that is notified of tb_emergency_contact table changes.
 * @author sql2java
 */
public interface TbEmergencyContactListener
{
    /**
     * Invoked just before inserting a TbEmergencyContactBean record into the database.
     *
     * @param bean the TbEmergencyContactBean that is about to be inserted
     */
    public void beforeInsert(TbEmergencyContactBean bean) throws DAOException;


    /**
     * Invoked just after a TbEmergencyContactBean record is inserted in the database.
     *
     * @param bean the TbEmergencyContactBean that was just inserted
     */
    public void afterInsert(TbEmergencyContactBean bean) throws DAOException;


    /**
     * Invoked just before updating a TbEmergencyContactBean record in the database.
     *
     * @param bean the TbEmergencyContactBean that is about to be updated
     */
    public void beforeUpdate(TbEmergencyContactBean bean) throws DAOException;


    /**
     * Invoked just after updating a TbEmergencyContactBean record in the database.
     *
     * @param bean the TbEmergencyContactBean that was just updated
     */
    public void afterUpdate(TbEmergencyContactBean bean) throws DAOException;


    /**
     * Invoked just before deleting a TbEmergencyContactBean record in the database.
     *
     * @param bean the TbEmergencyContactBean that is about to be deleted
     */
    public void beforeDelete(TbEmergencyContactBean bean) throws DAOException;


    /**
     * Invoked just after deleting a TbEmergencyContactBean record in the database.
     *
     * @param bean the TbEmergencyContactBean that was just deleted
     */
    public void afterDelete(TbEmergencyContactBean bean) throws DAOException;

}
