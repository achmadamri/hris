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
 * Comparator class is used to sort the TbPaygradeCurrencyBean objects.
 * @author sql2java
 */
public class TbPaygradeCurrencyComparator implements Comparator
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
     * Constructor class for TbPaygradeCurrencyComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new TbPaygradeCurrencyComparator(TbPaygradeCurrencyManager.ID_TBPC_OVERTIME, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_OVERTIME
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_LATE
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_STEP
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_MAX
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_MIN
     *   <li>TbPaygradeCurrencyManager.ID_TBC_ID
     *   <li>TbPaygradeCurrencyManager.ID_TBP_ID
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_ID
     * </ul>
     */
    public TbPaygradeCurrencyComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for TbPaygradeCurrencyComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new TbPaygradeCurrencyComparator(TbPaygradeCurrencyManager.ID_TBPC_OVERTIME, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_OVERTIME
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_LATE
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_STEP
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_MAX
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_MIN
     *   <li>TbPaygradeCurrencyManager.ID_TBC_ID
     *   <li>TbPaygradeCurrencyManager.ID_TBP_ID
     *   <li>TbPaygradeCurrencyManager.ID_TBPC_ID
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public TbPaygradeCurrencyComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    /**
     * Implementation of the compare method.
     */
    public int compare(Object pObj1, Object pObj2)
    {
        TbPaygradeCurrencyBean b1 = (TbPaygradeCurrencyBean)pObj1;
        TbPaygradeCurrencyBean b2 = (TbPaygradeCurrencyBean)pObj2;
        int iReturn = 0;
        switch(iType)
        {
            case TbPaygradeCurrencyManager.ID_TBPC_OVERTIME:
                if (b1.getTbpcOvertime() == null && b2.getTbpcOvertime() != null) {
                    iReturn = -1;
                } else if (b1.getTbpcOvertime() == null && b2.getTbpcOvertime() == null) {
                    iReturn = 0;
                } else if (b1.getTbpcOvertime() != null && b2.getTbpcOvertime() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpcOvertime().compareTo(b2.getTbpcOvertime());
                }
                break;
            case TbPaygradeCurrencyManager.ID_TBPC_LATE:
                if (b1.getTbpcLate() == null && b2.getTbpcLate() != null) {
                    iReturn = -1;
                } else if (b1.getTbpcLate() == null && b2.getTbpcLate() == null) {
                    iReturn = 0;
                } else if (b1.getTbpcLate() != null && b2.getTbpcLate() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpcLate().compareTo(b2.getTbpcLate());
                }
                break;
            case TbPaygradeCurrencyManager.ID_TBPC_STEP:
                if (b1.getTbpcStep() == null && b2.getTbpcStep() != null) {
                    iReturn = -1;
                } else if (b1.getTbpcStep() == null && b2.getTbpcStep() == null) {
                    iReturn = 0;
                } else if (b1.getTbpcStep() != null && b2.getTbpcStep() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpcStep().compareTo(b2.getTbpcStep());
                }
                break;
            case TbPaygradeCurrencyManager.ID_TBPC_MAX:
                if (b1.getTbpcMax() == null && b2.getTbpcMax() != null) {
                    iReturn = -1;
                } else if (b1.getTbpcMax() == null && b2.getTbpcMax() == null) {
                    iReturn = 0;
                } else if (b1.getTbpcMax() != null && b2.getTbpcMax() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpcMax().compareTo(b2.getTbpcMax());
                }
                break;
            case TbPaygradeCurrencyManager.ID_TBPC_MIN:
                if (b1.getTbpcMin() == null && b2.getTbpcMin() != null) {
                    iReturn = -1;
                } else if (b1.getTbpcMin() == null && b2.getTbpcMin() == null) {
                    iReturn = 0;
                } else if (b1.getTbpcMin() != null && b2.getTbpcMin() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpcMin().compareTo(b2.getTbpcMin());
                }
                break;
            case TbPaygradeCurrencyManager.ID_TBC_ID:
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
            case TbPaygradeCurrencyManager.ID_TBP_ID:
                if (b1.getTbpId() == null && b2.getTbpId() != null) {
                    iReturn = -1;
                } else if (b1.getTbpId() == null && b2.getTbpId() == null) {
                    iReturn = 0;
                } else if (b1.getTbpId() != null && b2.getTbpId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpId().compareTo(b2.getTbpId());
                }
                break;
            case TbPaygradeCurrencyManager.ID_TBPC_ID:
                if (b1.getTbpcId() == null && b2.getTbpcId() != null) {
                    iReturn = -1;
                } else if (b1.getTbpcId() == null && b2.getTbpcId() == null) {
                    iReturn = 0;
                } else if (b1.getTbpcId() != null && b2.getTbpcId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpcId().compareTo(b2.getTbpcId());
                }
                break;
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
