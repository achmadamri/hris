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
 * Listener that is notified of tb_notification_channel table changes.
 * @author sql2java
 */
public interface TbNotificationChannelListener
{
    /**
     * Invoked just before inserting a TbNotificationChannelBean record into the database.
     *
     * @param bean the TbNotificationChannelBean that is about to be inserted
     */
    public void beforeInsert(TbNotificationChannelBean bean) throws DAOException;


    /**
     * Invoked just after a TbNotificationChannelBean record is inserted in the database.
     *
     * @param bean the TbNotificationChannelBean that was just inserted
     */
    public void afterInsert(TbNotificationChannelBean bean) throws DAOException;


    /**
     * Invoked just before updating a TbNotificationChannelBean record in the database.
     *
     * @param bean the TbNotificationChannelBean that is about to be updated
     */
    public void beforeUpdate(TbNotificationChannelBean bean) throws DAOException;


    /**
     * Invoked just after updating a TbNotificationChannelBean record in the database.
     *
     * @param bean the TbNotificationChannelBean that was just updated
     */
    public void afterUpdate(TbNotificationChannelBean bean) throws DAOException;


    /**
     * Invoked just before deleting a TbNotificationChannelBean record in the database.
     *
     * @param bean the TbNotificationChannelBean that is about to be deleted
     */
    public void beforeDelete(TbNotificationChannelBean bean) throws DAOException;


    /**
     * Invoked just after deleting a TbNotificationChannelBean record in the database.
     *
     * @param bean the TbNotificationChannelBean that was just deleted
     */
    public void afterDelete(TbNotificationChannelBean bean) throws DAOException;

}
