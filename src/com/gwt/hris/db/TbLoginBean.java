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
 * TbLoginBean is a mapping of tb_login Table.
 * @author sql2java
*/
public class TbLoginBean
    implements Serializable, GeneratedBean
{
	private static final long serialVersionUID = 1229980767026324598L;
	
    private Integer tbeId;

    private boolean tbeIdIsModified = false;
    private boolean tbeIdIsInitialized = false;

    private Integer tbaugId;

    private boolean tbaugIdIsModified = false;
    private boolean tbaugIdIsInitialized = false;

    private Integer tblStatus;

    private boolean tblStatusIsModified = false;
    private boolean tblStatusIsInitialized = false;

    private String tblPassword;

    private boolean tblPasswordIsModified = false;
    private boolean tblPasswordIsInitialized = false;

    private String tblUsername;

    private boolean tblUsernameIsModified = false;
    private boolean tblUsernameIsInitialized = false;

    private String tblLoginId;

    private boolean tblLoginIdIsModified = false;
    private boolean tblLoginIdIsInitialized = false;

    private Integer tblId;

    private boolean tblIdIsModified = false;
    private boolean tblIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered methods to create a TbLoginBean is via the createTbLoginBean method in TbLoginManager or
     * via the factory class TbLoginFactory create method
     */
    protected TbLoginBean()
    {
    }

    /**
     * Getter method for tbeId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_login.tbe_id</li>
     * <li> foreign key: tb_employee.tbe_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbeId
     */
    public Integer getTbeId()
    {
        return tbeId;
    }

    /**
     * Setter method for tbeId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbeId
     */
    public void setTbeId(Integer newVal)
    {
        if ((newVal != null && tbeId != null && (newVal.compareTo(tbeId) == 0)) ||
            (newVal == null && tbeId == null && tbeIdIsInitialized)) {
            return;
        }
        tbeId = newVal;
        tbeIdIsModified = true;
        tbeIdIsInitialized = true;
    }

    /**
     * Setter method for tbeId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbeId
     */
    public void setTbeId(int newVal)
    {
        setTbeId(new Integer(newVal));
    }

    /**
     * Determines if the tbeId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbeIdModified()
    {
        return tbeIdIsModified;
    }

    /**
     * Determines if the tbeId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbeIdInitialized()
    {
        return tbeIdIsInitialized;
    }

    /**
     * Getter method for tbaugId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_login.tbaug_id</li>
     * <li> foreign key: tb_admin_user_groups.tbaug_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbaugId
     */
    public Integer getTbaugId()
    {
        return tbaugId;
    }

    /**
     * Setter method for tbaugId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbaugId
     */
    public void setTbaugId(Integer newVal)
    {
        if ((newVal != null && tbaugId != null && (newVal.compareTo(tbaugId) == 0)) ||
            (newVal == null && tbaugId == null && tbaugIdIsInitialized)) {
            return;
        }
        tbaugId = newVal;
        tbaugIdIsModified = true;
        tbaugIdIsInitialized = true;
    }

    /**
     * Setter method for tbaugId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbaugId
     */
    public void setTbaugId(int newVal)
    {
        setTbaugId(new Integer(newVal));
    }

    /**
     * Determines if the tbaugId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbaugIdModified()
    {
        return tbaugIdIsModified;
    }

    /**
     * Determines if the tbaugId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbaugIdInitialized()
    {
        return tbaugIdIsInitialized;
    }

    /**
     * Getter method for tblStatus.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_login.tbl_status</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tblStatus
     */
    public Integer getTblStatus()
    {
        return tblStatus;
    }

    /**
     * Setter method for tblStatus.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tblStatus
     */
    public void setTblStatus(Integer newVal)
    {
        if ((newVal != null && tblStatus != null && (newVal.compareTo(tblStatus) == 0)) ||
            (newVal == null && tblStatus == null && tblStatusIsInitialized)) {
            return;
        }
        tblStatus = newVal;
        tblStatusIsModified = true;
        tblStatusIsInitialized = true;
    }

    /**
     * Setter method for tblStatus.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tblStatus
     */
    public void setTblStatus(int newVal)
    {
        setTblStatus(new Integer(newVal));
    }

    /**
     * Determines if the tblStatus has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTblStatusModified()
    {
        return tblStatusIsModified;
    }

    /**
     * Determines if the tblStatus has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTblStatusInitialized()
    {
        return tblStatusIsInitialized;
    }

    /**
     * Getter method for tblPassword.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_login.tbl_password</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tblPassword
     */
    public String getTblPassword()
    {
        return tblPassword;
    }

    /**
     * Setter method for tblPassword.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tblPassword
     */
    public void setTblPassword(String newVal)
    {
        if ((newVal != null && tblPassword != null && (newVal.compareTo(tblPassword) == 0)) ||
            (newVal == null && tblPassword == null && tblPasswordIsInitialized)) {
            return;
        }
        tblPassword = newVal;
        tblPasswordIsModified = true;
        tblPasswordIsInitialized = true;
    }

    /**
     * Determines if the tblPassword has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTblPasswordModified()
    {
        return tblPasswordIsModified;
    }

    /**
     * Determines if the tblPassword has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTblPasswordInitialized()
    {
        return tblPasswordIsInitialized;
    }

    /**
     * Getter method for tblUsername.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_login.tbl_username</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tblUsername
     */
    public String getTblUsername()
    {
        return tblUsername;
    }

    /**
     * Setter method for tblUsername.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tblUsername
     */
    public void setTblUsername(String newVal)
    {
        if ((newVal != null && tblUsername != null && (newVal.compareTo(tblUsername) == 0)) ||
            (newVal == null && tblUsername == null && tblUsernameIsInitialized)) {
            return;
        }
        tblUsername = newVal;
        tblUsernameIsModified = true;
        tblUsernameIsInitialized = true;
    }

    /**
     * Determines if the tblUsername has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTblUsernameModified()
    {
        return tblUsernameIsModified;
    }

    /**
     * Determines if the tblUsername has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTblUsernameInitialized()
    {
        return tblUsernameIsInitialized;
    }

    /**
     * Getter method for tblLoginId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_login.tbl_login_id</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tblLoginId
     */
    public String getTblLoginId()
    {
        return tblLoginId;
    }

    /**
     * Setter method for tblLoginId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tblLoginId
     */
    public void setTblLoginId(String newVal)
    {
        if ((newVal != null && tblLoginId != null && (newVal.compareTo(tblLoginId) == 0)) ||
            (newVal == null && tblLoginId == null && tblLoginIdIsInitialized)) {
            return;
        }
        tblLoginId = newVal;
        tblLoginIdIsModified = true;
        tblLoginIdIsInitialized = true;
    }

    /**
     * Determines if the tblLoginId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTblLoginIdModified()
    {
        return tblLoginIdIsModified;
    }

    /**
     * Determines if the tblLoginId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTblLoginIdInitialized()
    {
        return tblLoginIdIsInitialized;
    }

    /**
     * Getter method for tblId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_login.tbl_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tblId
     */
    public Integer getTblId()
    {
        return tblId;
    }

    /**
     * Setter method for tblId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tblId
     */
    public void setTblId(Integer newVal)
    {
        if ((newVal != null && tblId != null && (newVal.compareTo(tblId) == 0)) ||
            (newVal == null && tblId == null && tblIdIsInitialized)) {
            return;
        }
        tblId = newVal;
        tblIdIsModified = true;
        tblIdIsInitialized = true;
    }

    /**
     * Setter method for tblId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tblId
     */
    public void setTblId(int newVal)
    {
        setTblId(new Integer(newVal));
    }

    /**
     * Determines if the tblId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTblIdModified()
    {
        return tblIdIsModified;
    }

    /**
     * Determines if the tblId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTblIdInitialized()
    {
        return tblIdIsInitialized;
    }

    /** The TbAdminUserGroups referenced by this bean. */
    private TbAdminUserGroupsBean referencedTbAdminUserGroups;
    /** Getter method for TbAdminUserGroupsBean. */
    public TbAdminUserGroupsBean getTbAdminUserGroupsBean() {
        return this.referencedTbAdminUserGroups;
    }
    /** Setter method for TbAdminUserGroupsBean. */
    public void setTbAdminUserGroupsBean(TbAdminUserGroupsBean reference) {
        this.referencedTbAdminUserGroups = reference;
    }
    
    /** The TbEmployee referenced by this bean. */
    private TbEmployeeBean referencedTbEmployee;
    /** Getter method for TbEmployeeBean. */
    public TbEmployeeBean getTbEmployeeBean() {
        return this.referencedTbEmployee;
    }
    /** Setter method for TbEmployeeBean. */
    public void setTbEmployeeBean(TbEmployeeBean reference) {
        this.referencedTbEmployee = reference;
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
        return tbeIdIsModified 		|| tbaugIdIsModified  		|| tblStatusIsModified  		|| tblPasswordIsModified  		|| tblUsernameIsModified  		|| tblLoginIdIsModified  		|| tblIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        tbeIdIsModified = false;
        tbaugIdIsModified = false;
        tblStatusIsModified = false;
        tblPasswordIsModified = false;
        tblUsernameIsModified = false;
        tblLoginIdIsModified = false;
        tblIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(TbLoginBean bean)
    {
        setTbeId(bean.getTbeId());
        setTbaugId(bean.getTbaugId());
        setTblStatus(bean.getTblStatus());
        setTblPassword(bean.getTblPassword());
        setTblUsername(bean.getTblUsername());
        setTblLoginId(bean.getTblLoginId());
        setTblId(bean.getTblId());
    }

    /**
     * return a dictionnary of the object
     */
    public Map getDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbe_id", getTbeId() == null ? "" : getTbeId().toString());
        dictionnary.put("tbaug_id", getTbaugId() == null ? "" : getTbaugId().toString());
        dictionnary.put("tbl_status", getTblStatus() == null ? "" : getTblStatus().toString());
        dictionnary.put("tbl_password", getTblPassword() == null ? "" : getTblPassword().toString());
        dictionnary.put("tbl_username", getTblUsername() == null ? "" : getTblUsername().toString());
        dictionnary.put("tbl_login_id", getTblLoginId() == null ? "" : getTblLoginId().toString());
        dictionnary.put("tbl_id", getTblId() == null ? "" : getTblId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map getPkDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbl_id", getTblId() == null ? "" : getTblId().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("tbe_id".equalsIgnoreCase(column) || "tbeId".equalsIgnoreCase(column)) {
            return getTbeId() == null ? "" : getTbeId().toString();
        } else if ("tbaug_id".equalsIgnoreCase(column) || "tbaugId".equalsIgnoreCase(column)) {
            return getTbaugId() == null ? "" : getTbaugId().toString();
        } else if ("tbl_status".equalsIgnoreCase(column) || "tblStatus".equalsIgnoreCase(column)) {
            return getTblStatus() == null ? "" : getTblStatus().toString();
        } else if ("tbl_password".equalsIgnoreCase(column) || "tblPassword".equalsIgnoreCase(column)) {
            return getTblPassword() == null ? "" : getTblPassword().toString();
        } else if ("tbl_username".equalsIgnoreCase(column) || "tblUsername".equalsIgnoreCase(column)) {
            return getTblUsername() == null ? "" : getTblUsername().toString();
        } else if ("tbl_login_id".equalsIgnoreCase(column) || "tblLoginId".equalsIgnoreCase(column)) {
            return getTblLoginId() == null ? "" : getTblLoginId().toString();
        } else if ("tbl_id".equalsIgnoreCase(column) || "tblId".equalsIgnoreCase(column)) {
            return getTblId() == null ? "" : getTblId().toString();
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof TbLoginBean)) {
            return false;
        }

		TbLoginBean obj = (TbLoginBean) object;
		return new EqualsBuilder()
            .append(getTbeId(), obj.getTbeId())
            .append(getTbaugId(), obj.getTbaugId())
            .append(getTblStatus(), obj.getTblStatus())
            .append(getTblPassword(), obj.getTblPassword())
            .append(getTblUsername(), obj.getTblUsername())
            .append(getTblLoginId(), obj.getTblLoginId())
            .append(getTblId(), obj.getTblId())
            .isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getTbeId())
            .append(getTbaugId())
            .append(getTblStatus())
            .append(getTblPassword())
            .append(getTblUsername())
            .append(getTblLoginId())
            .append(getTblId())
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
            .append("tbe_id", getTbeId())
            .append("tbaug_id", getTbaugId())
            .append("tbl_status", getTblStatus())
            .append("tbl_password", getTblPassword())
            .append("tbl_username", getTblUsername())
            .append("tbl_login_id", getTblLoginId())
            .append("tbl_id", getTblId())
            .toString();
	}


    public int compareTo(Object object)
    {
        TbLoginBean obj = (TbLoginBean) object;
        return new CompareToBuilder()
            .append(getTbeId(), obj.getTbeId())
            .append(getTbaugId(), obj.getTbaugId())
            .append(getTblStatus(), obj.getTblStatus())
            .append(getTblPassword(), obj.getTblPassword())
            .append(getTblUsername(), obj.getTblUsername())
            .append(getTblLoginId(), obj.getTblLoginId())
            .append(getTblId(), obj.getTblId())
            .toComparison();
   }
}
