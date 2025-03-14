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
 * Listener that is notified of tb_menu table changes.
 * @author sql2java
 */
public interface TbMenuListener
{
    /**
     * Invoked just before inserting a TbMenuBean record into the database.
     *
     * @param bean the TbMenuBean that is about to be inserted
     */
    public void beforeInsert(TbMenuBean bean) throws DAOException;


    /**
     * Invoked just after a TbMenuBean record is inserted in the database.
     *
     * @param bean the TbMenuBean that was just inserted
     */
    public void afterInsert(TbMenuBean bean) throws DAOException;


    /**
     * Invoked just before updating a TbMenuBean record in the database.
     *
     * @param bean the TbMenuBean that is about to be updated
     */
    public void beforeUpdate(TbMenuBean bean) throws DAOException;


    /**
     * Invoked just after updating a TbMenuBean record in the database.
     *
     * @param bean the TbMenuBean that was just updated
     */
    public void afterUpdate(TbMenuBean bean) throws DAOException;


    /**
     * Invoked just before deleting a TbMenuBean record in the database.
     *
     * @param bean the TbMenuBean that is about to be deleted
     */
    public void beforeDelete(TbMenuBean bean) throws DAOException;


    /**
     * Invoked just after deleting a TbMenuBean record in the database.
     *
     * @param bean the TbMenuBean that was just deleted
     */
    public void afterDelete(TbMenuBean bean) throws DAOException;

}
