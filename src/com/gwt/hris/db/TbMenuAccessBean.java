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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TbMenuAccessBean is a mapping of tb_menu_access Table.
 * @author sql2java
*/
public class TbMenuAccessBean
    implements Serializable, GeneratedBean
{
	private static final long serialVersionUID = -1579052871032156008L;
	
    private Integer tbmaApprove;

    private boolean tbmaApproveIsModified = false;
    private boolean tbmaApproveIsInitialized = false;

    private Integer tbmaView;

    private boolean tbmaViewIsModified = false;
    private boolean tbmaViewIsInitialized = false;

    private Integer tbmaDelete;

    private boolean tbmaDeleteIsModified = false;
    private boolean tbmaDeleteIsInitialized = false;

    private Integer tbmaUpdate;

    private boolean tbmaUpdateIsModified = false;
    private boolean tbmaUpdateIsInitialized = false;

    private Integer tbmaInsert;

    private boolean tbmaInsertIsModified = false;
    private boolean tbmaInsertIsInitialized = false;

    private Integer tbmaEnabled;

    private boolean tbmaEnabledIsModified = false;
    private boolean tbmaEnabledIsInitialized = false;

    private Integer tbjtId;

    private boolean tbjtIdIsModified = false;
    private boolean tbjtIdIsInitialized = false;

    private Integer tbmId;

    private boolean tbmIdIsModified = false;
    private boolean tbmIdIsInitialized = false;

    private Integer tbmaId;

    private boolean tbmaIdIsModified = false;
    private boolean tbmaIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered methods to create a TbMenuAccessBean is via the createTbMenuAccessBean method in TbMenuAccessManager or
     * via the factory class TbMenuAccessFactory create method
     */
    protected TbMenuAccessBean()
    {
    }

    /**
     * Getter method for tbmaApprove.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_menu_access.tbma_approve</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbmaApprove
     */
    public Integer getTbmaApprove()
    {
        return tbmaApprove;
    }

    /**
     * Setter method for tbmaApprove.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbmaApprove
     */
    public void setTbmaApprove(Integer newVal)
    {
        if ((newVal != null && tbmaApprove != null && (newVal.compareTo(tbmaApprove) == 0)) ||
            (newVal == null && tbmaApprove == null && tbmaApproveIsInitialized)) {
            return;
        }
        tbmaApprove = newVal;
        tbmaApproveIsModified = true;
        tbmaApproveIsInitialized = true;
    }

    /**
     * Setter method for tbmaApprove.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbmaApprove
     */
    public void setTbmaApprove(int newVal)
    {
        setTbmaApprove(new Integer(newVal));
    }

    /**
     * Determines if the tbmaApprove has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbmaApproveModified()
    {
        return tbmaApproveIsModified;
    }

    /**
     * Determines if the tbmaApprove has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbmaApproveInitialized()
    {
        return tbmaApproveIsInitialized;
    }

    /**
     * Getter method for tbmaView.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_menu_access.tbma_view</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbmaView
     */
    public Integer getTbmaView()
    {
        return tbmaView;
    }

    /**
     * Setter method for tbmaView.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbmaView
     */
    public void setTbmaView(Integer newVal)
    {
        if ((newVal != null && tbmaView != null && (newVal.compareTo(tbmaView) == 0)) ||
            (newVal == null && tbmaView == null && tbmaViewIsInitialized)) {
            return;
        }
        tbmaView = newVal;
        tbmaViewIsModified = true;
        tbmaViewIsInitialized = true;
    }

    /**
     * Setter method for tbmaView.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbmaView
     */
    public void setTbmaView(int newVal)
    {
        setTbmaView(new Integer(newVal));
    }

    /**
     * Determines if the tbmaView has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbmaViewModified()
    {
        return tbmaViewIsModified;
    }

    /**
     * Determines if the tbmaView has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbmaViewInitialized()
    {
        return tbmaViewIsInitialized;
    }

    /**
     * Getter method for tbmaDelete.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_menu_access.tbma_delete</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbmaDelete
     */
    public Integer getTbmaDelete()
    {
        return tbmaDelete;
    }

    /**
     * Setter method for tbmaDelete.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbmaDelete
     */
    public void setTbmaDelete(Integer newVal)
    {
        if ((newVal != null && tbmaDelete != null && (newVal.compareTo(tbmaDelete) == 0)) ||
            (newVal == null && tbmaDelete == null && tbmaDeleteIsInitialized)) {
            return;
        }
        tbmaDelete = newVal;
        tbmaDeleteIsModified = true;
        tbmaDeleteIsInitialized = true;
    }

    /**
     * Setter method for tbmaDelete.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbmaDelete
     */
    public void setTbmaDelete(int newVal)
    {
        setTbmaDelete(new Integer(newVal));
    }

    /**
     * Determines if the tbmaDelete has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbmaDeleteModified()
    {
        return tbmaDeleteIsModified;
    }

    /**
     * Determines if the tbmaDelete has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbmaDeleteInitialized()
    {
        return tbmaDeleteIsInitialized;
    }

    /**
     * Getter method for tbmaUpdate.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_menu_access.tbma_update</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbmaUpdate
     */
    public Integer getTbmaUpdate()
    {
        return tbmaUpdate;
    }

    /**
     * Setter method for tbmaUpdate.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbmaUpdate
     */
    public void setTbmaUpdate(Integer newVal)
    {
        if ((newVal != null && tbmaUpdate != null && (newVal.compareTo(tbmaUpdate) == 0)) ||
            (newVal == null && tbmaUpdate == null && tbmaUpdateIsInitialized)) {
            return;
        }
        tbmaUpdate = newVal;
        tbmaUpdateIsModified = true;
        tbmaUpdateIsInitialized = true;
    }

    /**
     * Setter method for tbmaUpdate.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbmaUpdate
     */
    public void setTbmaUpdate(int newVal)
    {
        setTbmaUpdate(new Integer(newVal));
    }

    /**
     * Determines if the tbmaUpdate has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbmaUpdateModified()
    {
        return tbmaUpdateIsModified;
    }

    /**
     * Determines if the tbmaUpdate has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbmaUpdateInitialized()
    {
        return tbmaUpdateIsInitialized;
    }

    /**
     * Getter method for tbmaInsert.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_menu_access.tbma_insert</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbmaInsert
     */
    public Integer getTbmaInsert()
    {
        return tbmaInsert;
    }

    /**
     * Setter method for tbmaInsert.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbmaInsert
     */
    public void setTbmaInsert(Integer newVal)
    {
        if ((newVal != null && tbmaInsert != null && (newVal.compareTo(tbmaInsert) == 0)) ||
            (newVal == null && tbmaInsert == null && tbmaInsertIsInitialized)) {
            return;
        }
        tbmaInsert = newVal;
        tbmaInsertIsModified = true;
        tbmaInsertIsInitialized = true;
    }

    /**
     * Setter method for tbmaInsert.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbmaInsert
     */
    public void setTbmaInsert(int newVal)
    {
        setTbmaInsert(new Integer(newVal));
    }

    /**
     * Determines if the tbmaInsert has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbmaInsertModified()
    {
        return tbmaInsertIsModified;
    }

    /**
     * Determines if the tbmaInsert has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbmaInsertInitialized()
    {
        return tbmaInsertIsInitialized;
    }

    /**
     * Getter method for tbmaEnabled.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_menu_access.tbma_enabled</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbmaEnabled
     */
    public Integer getTbmaEnabled()
    {
        return tbmaEnabled;
    }

    /**
     * Setter method for tbmaEnabled.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbmaEnabled
     */
    public void setTbmaEnabled(Integer newVal)
    {
        if ((newVal != null && tbmaEnabled != null && (newVal.compareTo(tbmaEnabled) == 0)) ||
            (newVal == null && tbmaEnabled == null && tbmaEnabledIsInitialized)) {
            return;
        }
        tbmaEnabled = newVal;
        tbmaEnabledIsModified = true;
        tbmaEnabledIsInitialized = true;
    }

    /**
     * Setter method for tbmaEnabled.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbmaEnabled
     */
    public void setTbmaEnabled(int newVal)
    {
        setTbmaEnabled(new Integer(newVal));
    }

    /**
     * Determines if the tbmaEnabled has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbmaEnabledModified()
    {
        return tbmaEnabledIsModified;
    }

    /**
     * Determines if the tbmaEnabled has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbmaEnabledInitialized()
    {
        return tbmaEnabledIsInitialized;
    }

    /**
     * Getter method for tbjtId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_menu_access.tbjt_id</li>
     * <li> foreign key: tb_job_title.tbjt_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbjtId
     */
    public Integer getTbjtId()
    {
        return tbjtId;
    }

    /**
     * Setter method for tbjtId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbjtId
     */
    public void setTbjtId(Integer newVal)
    {
        if ((newVal != null && tbjtId != null && (newVal.compareTo(tbjtId) == 0)) ||
            (newVal == null && tbjtId == null && tbjtIdIsInitialized)) {
            return;
        }
        tbjtId = newVal;
        tbjtIdIsModified = true;
        tbjtIdIsInitialized = true;
    }

    /**
     * Setter method for tbjtId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbjtId
     */
    public void setTbjtId(int newVal)
    {
        setTbjtId(new Integer(newVal));
    }

    /**
     * Determines if the tbjtId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbjtIdModified()
    {
        return tbjtIdIsModified;
    }

    /**
     * Determines if the tbjtId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbjtIdInitialized()
    {
        return tbjtIdIsInitialized;
    }

    /**
     * Getter method for tbmId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_menu_access.tbm_id</li>
     * <li> foreign key: tb_menu.tbm_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbmId
     */
    public Integer getTbmId()
    {
        return tbmId;
    }

    /**
     * Setter method for tbmId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbmId
     */
    public void setTbmId(Integer newVal)
    {
        if ((newVal != null && tbmId != null && (newVal.compareTo(tbmId) == 0)) ||
            (newVal == null && tbmId == null && tbmIdIsInitialized)) {
            return;
        }
        tbmId = newVal;
        tbmIdIsModified = true;
        tbmIdIsInitialized = true;
    }

    /**
     * Setter method for tbmId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbmId
     */
    public void setTbmId(int newVal)
    {
        setTbmId(new Integer(newVal));
    }

    /**
     * Determines if the tbmId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbmIdModified()
    {
        return tbmIdIsModified;
    }

    /**
     * Determines if the tbmId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbmIdInitialized()
    {
        return tbmIdIsInitialized;
    }

    /**
     * Getter method for tbmaId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_menu_access.tbma_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbmaId
     */
    public Integer getTbmaId()
    {
        return tbmaId;
    }

    /**
     * Setter method for tbmaId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbmaId
     */
    public void setTbmaId(Integer newVal)
    {
        if ((newVal != null && tbmaId != null && (newVal.compareTo(tbmaId) == 0)) ||
            (newVal == null && tbmaId == null && tbmaIdIsInitialized)) {
            return;
        }
        tbmaId = newVal;
        tbmaIdIsModified = true;
        tbmaIdIsInitialized = true;
    }

    /**
     * Setter method for tbmaId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbmaId
     */
    public void setTbmaId(int newVal)
    {
        setTbmaId(new Integer(newVal));
    }

    /**
     * Determines if the tbmaId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbmaIdModified()
    {
        return tbmaIdIsModified;
    }

    /**
     * Determines if the tbmaId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbmaIdInitialized()
    {
        return tbmaIdIsInitialized;
    }

    /** The TbMenu referenced by this bean. */
    private TbMenuBean referencedTbMenu;
    /** Getter method for TbMenuBean. */
    public TbMenuBean getTbMenuBean() {
        return this.referencedTbMenu;
    }
    /** Setter method for TbMenuBean. */
    public void setTbMenuBean(TbMenuBean reference) {
        this.referencedTbMenu = reference;
    }
    
    /** The TbJobTitle referenced by this bean. */
    private TbJobTitleBean referencedTbJobTitle;
    /** Getter method for TbJobTitleBean. */
    public TbJobTitleBean getTbJobTitleBean() {
        return this.referencedTbJobTitle;
    }
    /** Setter method for TbJobTitleBean. */
    public void setTbJobTitleBean(TbJobTitleBean reference) {
        this.referencedTbJobTitle = reference;
    }
    
    /**
     * Determines if the current object is new.
     *
     * @return true if the current object is new, false if the object is not new
     */
    public boolean isNew()
    {
        return _isNew;
    }

    /**
     * Specifies to the object if it has been set as new.
     *
     * @param isNew the boolean value to be assigned to the isNew field
     */
    public void isNew(boolean isNew)
    {
        this._isNew = isNew;
    }

    /**
     * Determines if the object has been modified since the last time this method was called.
     * <br>
     * We can also determine if this object has ever been modified since its creation.
     *
     * @return true if the object has been modified, false if the object has not been modified
     */
    public boolean isModified()
    {
        return tbmaApproveIsModified 		|| tbmaViewIsModified  		|| tbmaDeleteIsModified  		|| tbmaUpdateIsModified  		|| tbmaInsertIsModified  		|| tbmaEnabledIsModified  		|| tbjtIdIsModified  		|| tbmIdIsModified  		|| tbmaIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        tbmaApproveIsModified = false;
        tbmaViewIsModified = false;
        tbmaDeleteIsModified = false;
        tbmaUpdateIsModified = false;
        tbmaInsertIsModified = false;
        tbmaEnabledIsModified = false;
        tbjtIdIsModified = false;
        tbmIdIsModified = false;
        tbmaIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(TbMenuAccessBean bean)
    {
        setTbmaApprove(bean.getTbmaApprove());
        setTbmaView(bean.getTbmaView());
        setTbmaDelete(bean.getTbmaDelete());
        setTbmaUpdate(bean.getTbmaUpdate());
        setTbmaInsert(bean.getTbmaInsert());
        setTbmaEnabled(bean.getTbmaEnabled());
        setTbjtId(bean.getTbjtId());
        setTbmId(bean.getTbmId());
        setTbmaId(bean.getTbmaId());
    }

    /**
     * return a dictionnary of the object
     */
    public Map getDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbma_approve", getTbmaApprove() == null ? "" : getTbmaApprove().toString());
        dictionnary.put("tbma_view", getTbmaView() == null ? "" : getTbmaView().toString());
        dictionnary.put("tbma_delete", getTbmaDelete() == null ? "" : getTbmaDelete().toString());
        dictionnary.put("tbma_update", getTbmaUpdate() == null ? "" : getTbmaUpdate().toString());
        dictionnary.put("tbma_insert", getTbmaInsert() == null ? "" : getTbmaInsert().toString());
        dictionnary.put("tbma_enabled", getTbmaEnabled() == null ? "" : getTbmaEnabled().toString());
        dictionnary.put("tbjt_id", getTbjtId() == null ? "" : getTbjtId().toString());
        dictionnary.put("tbm_id", getTbmId() == null ? "" : getTbmId().toString());
        dictionnary.put("tbma_id", getTbmaId() == null ? "" : getTbmaId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map getPkDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbma_id", getTbmaId() == null ? "" : getTbmaId().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("tbma_approve".equalsIgnoreCase(column) || "tbmaApprove".equalsIgnoreCase(column)) {
            return getTbmaApprove() == null ? "" : getTbmaApprove().toString();
        } else if ("tbma_view".equalsIgnoreCase(column) || "tbmaView".equalsIgnoreCase(column)) {
            return getTbmaView() == null ? "" : getTbmaView().toString();
        } else if ("tbma_delete".equalsIgnoreCase(column) || "tbmaDelete".equalsIgnoreCase(column)) {
            return getTbmaDelete() == null ? "" : getTbmaDelete().toString();
        } else if ("tbma_update".equalsIgnoreCase(column) || "tbmaUpdate".equalsIgnoreCase(column)) {
            return getTbmaUpdate() == null ? "" : getTbmaUpdate().toString();
        } else if ("tbma_insert".equalsIgnoreCase(column) || "tbmaInsert".equalsIgnoreCase(column)) {
            return getTbmaInsert() == null ? "" : getTbmaInsert().toString();
        } else if ("tbma_enabled".equalsIgnoreCase(column) || "tbmaEnabled".equalsIgnoreCase(column)) {
            return getTbmaEnabled() == null ? "" : getTbmaEnabled().toString();
        } else if ("tbjt_id".equalsIgnoreCase(column) || "tbjtId".equalsIgnoreCase(column)) {
            return getTbjtId() == null ? "" : getTbjtId().toString();
        } else if ("tbm_id".equalsIgnoreCase(column) || "tbmId".equalsIgnoreCase(column)) {
            return getTbmId() == null ? "" : getTbmId().toString();
        } else if ("tbma_id".equalsIgnoreCase(column) || "tbmaId".equalsIgnoreCase(column)) {
            return getTbmaId() == null ? "" : getTbmaId().toString();
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof TbMenuAccessBean)) {
            return false;
        }

		TbMenuAccessBean obj = (TbMenuAccessBean) object;
		return new EqualsBuilder()
            .append(getTbmaApprove(), obj.getTbmaApprove())
            .append(getTbmaView(), obj.getTbmaView())
            .append(getTbmaDelete(), obj.getTbmaDelete())
            .append(getTbmaUpdate(), obj.getTbmaUpdate())
            .append(getTbmaInsert(), obj.getTbmaInsert())
            .append(getTbmaEnabled(), obj.getTbmaEnabled())
            .append(getTbjtId(), obj.getTbjtId())
            .append(getTbmId(), obj.getTbmId())
            .append(getTbmaId(), obj.getTbmaId())
            .isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getTbmaApprove())
            .append(getTbmaView())
            .append(getTbmaDelete())
            .append(getTbmaUpdate())
            .append(getTbmaInsert())
            .append(getTbmaEnabled())
            .append(getTbjtId())
            .append(getTbmId())
            .append(getTbmaId())
            .toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
	    return toString(ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * you can use the following styles:
	 * <li>ToStringStyle.DEFAULT_STYLE</li>
	 * <li>ToStringStyle.MULTI_LINE_STYLE</li>
     * <li>ToStringStyle.NO_FIELD_NAMES_STYLE</li>
     * <li>ToStringStyle.SHORT_PREFIX_STYLE</li>
     * <li>ToStringStyle.SIMPLE_STYLE</li>
	 */
	public String toString(ToStringStyle style) {
		return new ToStringBuilder(this, style)
            .append("tbma_approve", getTbmaApprove())
            .append("tbma_view", getTbmaView())
            .append("tbma_delete", getTbmaDelete())
            .append("tbma_update", getTbmaUpdate())
            .append("tbma_insert", getTbmaInsert())
            .append("tbma_enabled", getTbmaEnabled())
            .append("tbjt_id", getTbjtId())
            .append("tbm_id", getTbmId())
            .append("tbma_id", getTbmaId())
            .toString();
	}


    public int compareTo(Object object)
    {
        TbMenuAccessBean obj = (TbMenuAccessBean) object;
        return new CompareToBuilder()
            .append(getTbmaApprove(), obj.getTbmaApprove())
            .append(getTbmaView(), obj.getTbmaView())
            .append(getTbmaDelete(), obj.getTbmaDelete())
            .append(getTbmaUpdate(), obj.getTbmaUpdate())
            .append(getTbmaInsert(), obj.getTbmaInsert())
            .append(getTbmaEnabled(), obj.getTbmaEnabled())
            .append(getTbjtId(), obj.getTbjtId())
            .append(getTbmId(), obj.getTbmId())
            .append(getTbmaId(), obj.getTbmaId())
            .toComparison();
   }
}
