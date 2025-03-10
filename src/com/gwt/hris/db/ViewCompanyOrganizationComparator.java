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
 * Comparator class is used to sort the ViewCompanyOrganizationBean objects.
 * @author sql2java
 */
public class ViewCompanyOrganizationComparator implements Comparator
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
     * Constructor class for ViewCompanyOrganizationComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new ViewCompanyOrganizationComparator(ViewCompanyOrganizationManager.ID_TBO_NAMA, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>ViewCompanyOrganizationManager.ID_TBO_NAMA
     *   <li>ViewCompanyOrganizationManager.ID_TBO_PARENT_ID
     *   <li>ViewCompanyOrganizationManager.ID_TBO_ID
     *   <li>ViewCompanyOrganizationManager.ID_TBP_NAME
     *   <li>ViewCompanyOrganizationManager.ID_TBP_PERUSAHAAN_ID
     *   <li>ViewCompanyOrganizationManager.ID_TBP_ID
     * </ul>
     */
    public ViewCompanyOrganizationComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for ViewCompanyOrganizationComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new ViewCompanyOrganizationComparator(ViewCompanyOrganizationManager.ID_TBO_NAMA, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>ViewCompanyOrganizationManager.ID_TBO_NAMA
     *   <li>ViewCompanyOrganizationManager.ID_TBO_PARENT_ID
     *   <li>ViewCompanyOrganizationManager.ID_TBO_ID
     *   <li>ViewCompanyOrganizationManager.ID_TBP_NAME
     *   <li>ViewCompanyOrganizationManager.ID_TBP_PERUSAHAAN_ID
     *   <li>ViewCompanyOrganizationManager.ID_TBP_ID
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public ViewCompanyOrganizationComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    /**
     * Implementation of the compare method.
     */
    public int compare(Object pObj1, Object pObj2)
    {
        ViewCompanyOrganizationBean b1 = (ViewCompanyOrganizationBean)pObj1;
        ViewCompanyOrganizationBean b2 = (ViewCompanyOrganizationBean)pObj2;
        int iReturn = 0;
        switch(iType)
        {
            case ViewCompanyOrganizationManager.ID_TBO_NAMA:
                if (b1.getTboNama() == null && b2.getTboNama() != null) {
                    iReturn = -1;
                } else if (b1.getTboNama() == null && b2.getTboNama() == null) {
                    iReturn = 0;
                } else if (b1.getTboNama() != null && b2.getTboNama() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTboNama().compareTo(b2.getTboNama());
                }
                break;
            case ViewCompanyOrganizationManager.ID_TBO_PARENT_ID:
                if (b1.getTboParentId() == null && b2.getTboParentId() != null) {
                    iReturn = -1;
                } else if (b1.getTboParentId() == null && b2.getTboParentId() == null) {
                    iReturn = 0;
                } else if (b1.getTboParentId() != null && b2.getTboParentId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTboParentId().compareTo(b2.getTboParentId());
                }
                break;
            case ViewCompanyOrganizationManager.ID_TBO_ID:
                if (b1.getTboId() == null && b2.getTboId() != null) {
                    iReturn = -1;
                } else if (b1.getTboId() == null && b2.getTboId() == null) {
                    iReturn = 0;
                } else if (b1.getTboId() != null && b2.getTboId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTboId().compareTo(b2.getTboId());
                }
                break;
            case ViewCompanyOrganizationManager.ID_TBP_NAME:
                if (b1.getTbpName() == null && b2.getTbpName() != null) {
                    iReturn = -1;
                } else if (b1.getTbpName() == null && b2.getTbpName() == null) {
                    iReturn = 0;
                } else if (b1.getTbpName() != null && b2.getTbpName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpName().compareTo(b2.getTbpName());
                }
                break;
            case ViewCompanyOrganizationManager.ID_TBP_PERUSAHAAN_ID:
                if (b1.getTbpPerusahaanId() == null && b2.getTbpPerusahaanId() != null) {
                    iReturn = -1;
                } else if (b1.getTbpPerusahaanId() == null && b2.getTbpPerusahaanId() == null) {
                    iReturn = 0;
                } else if (b1.getTbpPerusahaanId() != null && b2.getTbpPerusahaanId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpPerusahaanId().compareTo(b2.getTbpPerusahaanId());
                }
                break;
            case ViewCompanyOrganizationManager.ID_TBP_ID:
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
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
