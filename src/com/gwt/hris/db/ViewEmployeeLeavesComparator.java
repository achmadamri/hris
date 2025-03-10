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
 * Comparator class is used to sort the ViewEmployeeLeavesBean objects.
 * @author sql2java
 */
public class ViewEmployeeLeavesComparator implements Comparator
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
     * Constructor class for ViewEmployeeLeavesComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new ViewEmployeeLeavesComparator(ViewEmployeeLeavesManager.ID_TBLT_NAME, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>ViewEmployeeLeavesManager.ID_TBLT_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBLT_LEAVE_TYPES_ID
     *   <li>ViewEmployeeLeavesManager.ID_TBLT_ID
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_LEAVE_TAKEN
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_STATUS
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_COMMENTS
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_END_DATE
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_START_DATE
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_ID
     *   <li>ViewEmployeeLeavesManager.ID_TBE_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_NICK_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_LAST_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_MIDDLE_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_FIRST_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_EMPLOYEE_ID
     *   <li>ViewEmployeeLeavesManager.ID_TBE_ID
     * </ul>
     */
    public ViewEmployeeLeavesComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for ViewEmployeeLeavesComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new ViewEmployeeLeavesComparator(ViewEmployeeLeavesManager.ID_TBLT_NAME, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>ViewEmployeeLeavesManager.ID_TBLT_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBLT_LEAVE_TYPES_ID
     *   <li>ViewEmployeeLeavesManager.ID_TBLT_ID
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_LEAVE_TAKEN
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_STATUS
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_COMMENTS
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_END_DATE
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_START_DATE
     *   <li>ViewEmployeeLeavesManager.ID_TBALE_ID
     *   <li>ViewEmployeeLeavesManager.ID_TBE_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_NICK_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_LAST_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_MIDDLE_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_FIRST_NAME
     *   <li>ViewEmployeeLeavesManager.ID_TBE_EMPLOYEE_ID
     *   <li>ViewEmployeeLeavesManager.ID_TBE_ID
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public ViewEmployeeLeavesComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    /**
     * Implementation of the compare method.
     */
    public int compare(Object pObj1, Object pObj2)
    {
        ViewEmployeeLeavesBean b1 = (ViewEmployeeLeavesBean)pObj1;
        ViewEmployeeLeavesBean b2 = (ViewEmployeeLeavesBean)pObj2;
        int iReturn = 0;
        switch(iType)
        {
            case ViewEmployeeLeavesManager.ID_TBLT_NAME:
                if (b1.getTbltName() == null && b2.getTbltName() != null) {
                    iReturn = -1;
                } else if (b1.getTbltName() == null && b2.getTbltName() == null) {
                    iReturn = 0;
                } else if (b1.getTbltName() != null && b2.getTbltName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbltName().compareTo(b2.getTbltName());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBLT_LEAVE_TYPES_ID:
                if (b1.getTbltLeaveTypesId() == null && b2.getTbltLeaveTypesId() != null) {
                    iReturn = -1;
                } else if (b1.getTbltLeaveTypesId() == null && b2.getTbltLeaveTypesId() == null) {
                    iReturn = 0;
                } else if (b1.getTbltLeaveTypesId() != null && b2.getTbltLeaveTypesId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbltLeaveTypesId().compareTo(b2.getTbltLeaveTypesId());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBLT_ID:
                if (b1.getTbltId() == null && b2.getTbltId() != null) {
                    iReturn = -1;
                } else if (b1.getTbltId() == null && b2.getTbltId() == null) {
                    iReturn = 0;
                } else if (b1.getTbltId() != null && b2.getTbltId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbltId().compareTo(b2.getTbltId());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBALE_LEAVE_TAKEN:
                if (b1.getTbaleLeaveTaken() == null && b2.getTbaleLeaveTaken() != null) {
                    iReturn = -1;
                } else if (b1.getTbaleLeaveTaken() == null && b2.getTbaleLeaveTaken() == null) {
                    iReturn = 0;
                } else if (b1.getTbaleLeaveTaken() != null && b2.getTbaleLeaveTaken() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbaleLeaveTaken().compareTo(b2.getTbaleLeaveTaken());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBALE_STATUS:
                if (b1.getTbaleStatus() == null && b2.getTbaleStatus() != null) {
                    iReturn = -1;
                } else if (b1.getTbaleStatus() == null && b2.getTbaleStatus() == null) {
                    iReturn = 0;
                } else if (b1.getTbaleStatus() != null && b2.getTbaleStatus() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbaleStatus().compareTo(b2.getTbaleStatus());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBALE_COMMENTS:
                if (b1.getTbaleComments() == null && b2.getTbaleComments() != null) {
                    iReturn = -1;
                } else if (b1.getTbaleComments() == null && b2.getTbaleComments() == null) {
                    iReturn = 0;
                } else if (b1.getTbaleComments() != null && b2.getTbaleComments() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbaleComments().compareTo(b2.getTbaleComments());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBALE_END_DATE:
                if (b1.getTbaleEndDate() == null && b2.getTbaleEndDate() != null) {
                    iReturn = -1;
                } else if (b1.getTbaleEndDate() == null && b2.getTbaleEndDate() == null) {
                    iReturn = 0;
                } else if (b1.getTbaleEndDate() != null && b2.getTbaleEndDate() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbaleEndDate().compareTo(b2.getTbaleEndDate());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBALE_START_DATE:
                if (b1.getTbaleStartDate() == null && b2.getTbaleStartDate() != null) {
                    iReturn = -1;
                } else if (b1.getTbaleStartDate() == null && b2.getTbaleStartDate() == null) {
                    iReturn = 0;
                } else if (b1.getTbaleStartDate() != null && b2.getTbaleStartDate() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbaleStartDate().compareTo(b2.getTbaleStartDate());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBALE_ID:
                if (b1.getTbaleId() == null && b2.getTbaleId() != null) {
                    iReturn = -1;
                } else if (b1.getTbaleId() == null && b2.getTbaleId() == null) {
                    iReturn = 0;
                } else if (b1.getTbaleId() != null && b2.getTbaleId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbaleId().compareTo(b2.getTbaleId());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBE_NAME:
                if (b1.getTbeName() == null && b2.getTbeName() != null) {
                    iReturn = -1;
                } else if (b1.getTbeName() == null && b2.getTbeName() == null) {
                    iReturn = 0;
                } else if (b1.getTbeName() != null && b2.getTbeName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbeName().compareTo(b2.getTbeName());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBE_NICK_NAME:
                if (b1.getTbeNickName() == null && b2.getTbeNickName() != null) {
                    iReturn = -1;
                } else if (b1.getTbeNickName() == null && b2.getTbeNickName() == null) {
                    iReturn = 0;
                } else if (b1.getTbeNickName() != null && b2.getTbeNickName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbeNickName().compareTo(b2.getTbeNickName());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBE_LAST_NAME:
                if (b1.getTbeLastName() == null && b2.getTbeLastName() != null) {
                    iReturn = -1;
                } else if (b1.getTbeLastName() == null && b2.getTbeLastName() == null) {
                    iReturn = 0;
                } else if (b1.getTbeLastName() != null && b2.getTbeLastName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbeLastName().compareTo(b2.getTbeLastName());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBE_MIDDLE_NAME:
                if (b1.getTbeMiddleName() == null && b2.getTbeMiddleName() != null) {
                    iReturn = -1;
                } else if (b1.getTbeMiddleName() == null && b2.getTbeMiddleName() == null) {
                    iReturn = 0;
                } else if (b1.getTbeMiddleName() != null && b2.getTbeMiddleName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbeMiddleName().compareTo(b2.getTbeMiddleName());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBE_FIRST_NAME:
                if (b1.getTbeFirstName() == null && b2.getTbeFirstName() != null) {
                    iReturn = -1;
                } else if (b1.getTbeFirstName() == null && b2.getTbeFirstName() == null) {
                    iReturn = 0;
                } else if (b1.getTbeFirstName() != null && b2.getTbeFirstName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbeFirstName().compareTo(b2.getTbeFirstName());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBE_EMPLOYEE_ID:
                if (b1.getTbeEmployeeId() == null && b2.getTbeEmployeeId() != null) {
                    iReturn = -1;
                } else if (b1.getTbeEmployeeId() == null && b2.getTbeEmployeeId() == null) {
                    iReturn = 0;
                } else if (b1.getTbeEmployeeId() != null && b2.getTbeEmployeeId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbeEmployeeId().compareTo(b2.getTbeEmployeeId());
                }
                break;
            case ViewEmployeeLeavesManager.ID_TBE_ID:
                if (b1.getTbeId() == null && b2.getTbeId() != null) {
                    iReturn = -1;
                } else if (b1.getTbeId() == null && b2.getTbeId() == null) {
                    iReturn = 0;
                } else if (b1.getTbeId() != null && b2.getTbeId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbeId().compareTo(b2.getTbeId());
                }
                break;
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
