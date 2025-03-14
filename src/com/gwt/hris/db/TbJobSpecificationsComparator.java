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

import java.util.Comparator;


/**
 * Comparator class is used to sort the TbJobSpecificationsBean objects.
 * @author sql2java
 */
public class TbJobSpecificationsComparator implements Comparator
{
    /**
     * Holds the field on which the comparison is performed.
     */
    private int iType;
    /**
     * Value that will contain the information about the order of the sort: normal or reversal.
     */
    private boolean bReverse;

    /**
     * Constructor class for TbJobSpecificationsComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new TbJobSpecificationsComparator(TbJobSpecificationsManager.ID_TBJS_DUTIES, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>TbJobSpecificationsManager.ID_TBJS_DUTIES
     *   <li>TbJobSpecificationsManager.ID_TBJS_DESCRIPTION
     *   <li>TbJobSpecificationsManager.ID_TBJS_NAME
     *   <li>TbJobSpecificationsManager.ID_TBJS_ID
     * </ul>
     */
    public TbJobSpecificationsComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for TbJobSpecificationsComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new TbJobSpecificationsComparator(TbJobSpecificationsManager.ID_TBJS_DUTIES, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>TbJobSpecificationsManager.ID_TBJS_DUTIES
     *   <li>TbJobSpecificationsManager.ID_TBJS_DESCRIPTION
     *   <li>TbJobSpecificationsManager.ID_TBJS_NAME
     *   <li>TbJobSpecificationsManager.ID_TBJS_ID
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public TbJobSpecificationsComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    /**
     * Implementation of the compare method.
     */
    public int compare(Object pObj1, Object pObj2)
    {
        TbJobSpecificationsBean b1 = (TbJobSpecificationsBean)pObj1;
        TbJobSpecificationsBean b2 = (TbJobSpecificationsBean)pObj2;
        int iReturn = 0;
        switch(iType)
        {
            case TbJobSpecificationsManager.ID_TBJS_DUTIES:
                if (b1.getTbjsDuties() == null && b2.getTbjsDuties() != null) {
                    iReturn = -1;
                } else if (b1.getTbjsDuties() == null && b2.getTbjsDuties() == null) {
                    iReturn = 0;
                } else if (b1.getTbjsDuties() != null && b2.getTbjsDuties() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbjsDuties().compareTo(b2.getTbjsDuties());
                }
                break;
            case TbJobSpecificationsManager.ID_TBJS_DESCRIPTION:
                if (b1.getTbjsDescription() == null && b2.getTbjsDescription() != null) {
                    iReturn = -1;
                } else if (b1.getTbjsDescription() == null && b2.getTbjsDescription() == null) {
                    iReturn = 0;
                } else if (b1.getTbjsDescription() != null && b2.getTbjsDescription() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbjsDescription().compareTo(b2.getTbjsDescription());
                }
                break;
            case TbJobSpecificationsManager.ID_TBJS_NAME:
                if (b1.getTbjsName() == null && b2.getTbjsName() != null) {
                    iReturn = -1;
                } else if (b1.getTbjsName() == null && b2.getTbjsName() == null) {
                    iReturn = 0;
                } else if (b1.getTbjsName() != null && b2.getTbjsName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbjsName().compareTo(b2.getTbjsName());
                }
                break;
            case TbJobSpecificationsManager.ID_TBJS_ID:
                if (b1.getTbjsId() == null && b2.getTbjsId() != null) {
                    iReturn = -1;
                } else if (b1.getTbjsId() == null && b2.getTbjsId() == null) {
                    iReturn = 0;
                } else if (b1.getTbjsId() != null && b2.getTbjsId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbjsId().compareTo(b2.getTbjsId());
                }
                break;
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
