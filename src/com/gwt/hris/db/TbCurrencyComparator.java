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
 * Comparator class is used to sort the TbCurrencyBean objects.
 * @author sql2java
 */
public class TbCurrencyComparator implements Comparator
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
     * Constructor class for TbCurrencyComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new TbCurrencyComparator(TbCurrencyManager.ID_TBC_LOCAL_CURRENCY_KURS, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>TbCurrencyManager.ID_TBC_LOCAL_CURRENCY_KURS
     *   <li>TbCurrencyManager.ID_TBC_NAME
     *   <li>TbCurrencyManager.ID_TBC_CURRENCY_ID
     *   <li>TbCurrencyManager.ID_TBC_ID
     * </ul>
     */
    public TbCurrencyComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for TbCurrencyComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new TbCurrencyComparator(TbCurrencyManager.ID_TBC_LOCAL_CURRENCY_KURS, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>TbCurrencyManager.ID_TBC_LOCAL_CURRENCY_KURS
     *   <li>TbCurrencyManager.ID_TBC_NAME
     *   <li>TbCurrencyManager.ID_TBC_CURRENCY_ID
     *   <li>TbCurrencyManager.ID_TBC_ID
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public TbCurrencyComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    /**
     * Implementation of the compare method.
     */
    public int compare(Object pObj1, Object pObj2)
    {
        TbCurrencyBean b1 = (TbCurrencyBean)pObj1;
        TbCurrencyBean b2 = (TbCurrencyBean)pObj2;
        int iReturn = 0;
        switch(iType)
        {
            case TbCurrencyManager.ID_TBC_LOCAL_CURRENCY_KURS:
                if (b1.getTbcLocalCurrencyKurs() == null && b2.getTbcLocalCurrencyKurs() != null) {
                    iReturn = -1;
                } else if (b1.getTbcLocalCurrencyKurs() == null && b2.getTbcLocalCurrencyKurs() == null) {
                    iReturn = 0;
                } else if (b1.getTbcLocalCurrencyKurs() != null && b2.getTbcLocalCurrencyKurs() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbcLocalCurrencyKurs().compareTo(b2.getTbcLocalCurrencyKurs());
                }
                break;
            case TbCurrencyManager.ID_TBC_NAME:
                if (b1.getTbcName() == null && b2.getTbcName() != null) {
                    iReturn = -1;
                } else if (b1.getTbcName() == null && b2.getTbcName() == null) {
                    iReturn = 0;
                } else if (b1.getTbcName() != null && b2.getTbcName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbcName().compareTo(b2.getTbcName());
                }
                break;
            case TbCurrencyManager.ID_TBC_CURRENCY_ID:
                if (b1.getTbcCurrencyId() == null && b2.getTbcCurrencyId() != null) {
                    iReturn = -1;
                } else if (b1.getTbcCurrencyId() == null && b2.getTbcCurrencyId() == null) {
                    iReturn = 0;
                } else if (b1.getTbcCurrencyId() != null && b2.getTbcCurrencyId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbcCurrencyId().compareTo(b2.getTbcCurrencyId());
                }
                break;
            case TbCurrencyManager.ID_TBC_ID:
                if (b1.getTbcId() == null && b2.getTbcId() != null) {
                    iReturn = -1;
                } else if (b1.getTbcId() == null && b2.getTbcId() == null) {
                    iReturn = 0;
                } else if (b1.getTbcId() != null && b2.getTbcId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbcId().compareTo(b2.getTbcId());
                }
                break;
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
