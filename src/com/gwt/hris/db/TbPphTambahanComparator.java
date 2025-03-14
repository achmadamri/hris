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
 * Comparator class is used to sort the TbPphTambahanBean objects.
 * @author sql2java
 */
public class TbPphTambahanComparator implements Comparator
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
     * Constructor class for TbPphTambahanComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new TbPphTambahanComparator(TbPphTambahanManager.ID_TBPPHT_NOMINAL, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>TbPphTambahanManager.ID_TBPPHT_NOMINAL
     *   <li>TbPphTambahanManager.ID_TBPPHT_NAME
     *   <li>TbPphTambahanManager.ID_TBPPH_ID
     *   <li>TbPphTambahanManager.ID_TBPPHT_ID
     * </ul>
     */
    public TbPphTambahanComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for TbPphTambahanComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new TbPphTambahanComparator(TbPphTambahanManager.ID_TBPPHT_NOMINAL, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>TbPphTambahanManager.ID_TBPPHT_NOMINAL
     *   <li>TbPphTambahanManager.ID_TBPPHT_NAME
     *   <li>TbPphTambahanManager.ID_TBPPH_ID
     *   <li>TbPphTambahanManager.ID_TBPPHT_ID
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public TbPphTambahanComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    /**
     * Implementation of the compare method.
     */
    public int compare(Object pObj1, Object pObj2)
    {
        TbPphTambahanBean b1 = (TbPphTambahanBean)pObj1;
        TbPphTambahanBean b2 = (TbPphTambahanBean)pObj2;
        int iReturn = 0;
        switch(iType)
        {
            case TbPphTambahanManager.ID_TBPPHT_NOMINAL:
                if (b1.getTbpphtNominal() == null && b2.getTbpphtNominal() != null) {
                    iReturn = -1;
                } else if (b1.getTbpphtNominal() == null && b2.getTbpphtNominal() == null) {
                    iReturn = 0;
                } else if (b1.getTbpphtNominal() != null && b2.getTbpphtNominal() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpphtNominal().compareTo(b2.getTbpphtNominal());
                }
                break;
            case TbPphTambahanManager.ID_TBPPHT_NAME:
                if (b1.getTbpphtName() == null && b2.getTbpphtName() != null) {
                    iReturn = -1;
                } else if (b1.getTbpphtName() == null && b2.getTbpphtName() == null) {
                    iReturn = 0;
                } else if (b1.getTbpphtName() != null && b2.getTbpphtName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpphtName().compareTo(b2.getTbpphtName());
                }
                break;
            case TbPphTambahanManager.ID_TBPPH_ID:
                if (b1.getTbpphId() == null && b2.getTbpphId() != null) {
                    iReturn = -1;
                } else if (b1.getTbpphId() == null && b2.getTbpphId() == null) {
                    iReturn = 0;
                } else if (b1.getTbpphId() != null && b2.getTbpphId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpphId().compareTo(b2.getTbpphId());
                }
                break;
            case TbPphTambahanManager.ID_TBPPHT_ID:
                if (b1.getTbpphtId() == null && b2.getTbpphtId() != null) {
                    iReturn = -1;
                } else if (b1.getTbpphtId() == null && b2.getTbpphtId() == null) {
                    iReturn = 0;
                } else if (b1.getTbpphtId() != null && b2.getTbpphtId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getTbpphtId().compareTo(b2.getTbpphtId());
                }
                break;
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
