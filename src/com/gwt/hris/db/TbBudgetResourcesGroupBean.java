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
 * TbBudgetResourcesGroupBean is a mapping of tb_budget_resources_group Table.
 * @author sql2java
*/
public class TbBudgetResourcesGroupBean
    implements Serializable, GeneratedBean
{
	private static final long serialVersionUID = -6560103239556721976L;
	
    private String tbbrgName;

    private boolean tbbrgNameIsModified = false;
    private boolean tbbrgNameIsInitialized = false;

    private String tbbrgBudgetResourcesGroupId;

    private boolean tbbrgBudgetResourcesGroupIdIsModified = false;
    private boolean tbbrgBudgetResourcesGroupIdIsInitialized = false;

    private Integer tbbrgId;

    private boolean tbbrgIdIsModified = false;
    private boolean tbbrgIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered methods to create a TbBudgetResourcesGroupBean is via the createTbBudgetResourcesGroupBean method in TbBudgetResourcesGroupManager or
     * via the factory class TbBudgetResourcesGroupFactory create method
     */
    protected TbBudgetResourcesGroupBean()
    {
    }

    /**
     * Getter method for tbbrgName.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_budget_resources_group.tbbrg_name</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbbrgName
     */
    public String getTbbrgName()
    {
        return tbbrgName;
    }

    /**
     * Setter method for tbbrgName.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbbrgName
     */
    public void setTbbrgName(String newVal)
    {
        if ((newVal != null && tbbrgName != null && (newVal.compareTo(tbbrgName) == 0)) ||
            (newVal == null && tbbrgName == null && tbbrgNameIsInitialized)) {
            return;
        }
        tbbrgName = newVal;
        tbbrgNameIsModified = true;
        tbbrgNameIsInitialized = true;
    }

    /**
     * Determines if the tbbrgName has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbbrgNameModified()
    {
        return tbbrgNameIsModified;
    }

    /**
     * Determines if the tbbrgName has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbbrgNameInitialized()
    {
        return tbbrgNameIsInitialized;
    }

    /**
     * Getter method for tbbrgBudgetResourcesGroupId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_budget_resources_group.tbbrg_budget_resources_group_id</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbbrgBudgetResourcesGroupId
     */
    public String getTbbrgBudgetResourcesGroupId()
    {
        return tbbrgBudgetResourcesGroupId;
    }

    /**
     * Setter method for tbbrgBudgetResourcesGroupId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbbrgBudgetResourcesGroupId
     */
    public void setTbbrgBudgetResourcesGroupId(String newVal)
    {
        if ((newVal != null && tbbrgBudgetResourcesGroupId != null && (newVal.compareTo(tbbrgBudgetResourcesGroupId) == 0)) ||
            (newVal == null && tbbrgBudgetResourcesGroupId == null && tbbrgBudgetResourcesGroupIdIsInitialized)) {
            return;
        }
        tbbrgBudgetResourcesGroupId = newVal;
        tbbrgBudgetResourcesGroupIdIsModified = true;
        tbbrgBudgetResourcesGroupIdIsInitialized = true;
    }

    /**
     * Determines if the tbbrgBudgetResourcesGroupId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbbrgBudgetResourcesGroupIdModified()
    {
        return tbbrgBudgetResourcesGroupIdIsModified;
    }

    /**
     * Determines if the tbbrgBudgetResourcesGroupId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbbrgBudgetResourcesGroupIdInitialized()
    {
        return tbbrgBudgetResourcesGroupIdIsInitialized;
    }

    /**
     * Getter method for tbbrgId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_budget_resources_group.tbbrg_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbbrgId
     */
    public Integer getTbbrgId()
    {
        return tbbrgId;
    }

    /**
     * Setter method for tbbrgId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbbrgId
     */
    public void setTbbrgId(Integer newVal)
    {
        if ((newVal != null && tbbrgId != null && (newVal.compareTo(tbbrgId) == 0)) ||
            (newVal == null && tbbrgId == null && tbbrgIdIsInitialized)) {
            return;
        }
        tbbrgId = newVal;
        tbbrgIdIsModified = true;
        tbbrgIdIsInitialized = true;
    }

    /**
     * Setter method for tbbrgId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbbrgId
     */
    public void setTbbrgId(int newVal)
    {
        setTbbrgId(new Integer(newVal));
    }

    /**
     * Determines if the tbbrgId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbbrgIdModified()
    {
        return tbbrgIdIsModified;
    }

    /**
     * Determines if the tbbrgId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbbrgIdInitialized()
    {
        return tbbrgIdIsInitialized;
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
        return tbbrgNameIsModified 		|| tbbrgBudgetResourcesGroupIdIsModified  		|| tbbrgIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        tbbrgNameIsModified = false;
        tbbrgBudgetResourcesGroupIdIsModified = false;
        tbbrgIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(TbBudgetResourcesGroupBean bean)
    {
        setTbbrgName(bean.getTbbrgName());
        setTbbrgBudgetResourcesGroupId(bean.getTbbrgBudgetResourcesGroupId());
        setTbbrgId(bean.getTbbrgId());
    }

    /**
     * return a dictionnary of the object
     */
    public Map getDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbbrg_name", getTbbrgName() == null ? "" : getTbbrgName().toString());
        dictionnary.put("tbbrg_budget_resources_group_id", getTbbrgBudgetResourcesGroupId() == null ? "" : getTbbrgBudgetResourcesGroupId().toString());
        dictionnary.put("tbbrg_id", getTbbrgId() == null ? "" : getTbbrgId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map getPkDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbbrg_id", getTbbrgId() == null ? "" : getTbbrgId().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("tbbrg_name".equalsIgnoreCase(column) || "tbbrgName".equalsIgnoreCase(column)) {
            return getTbbrgName() == null ? "" : getTbbrgName().toString();
        } else if ("tbbrg_budget_resources_group_id".equalsIgnoreCase(column) || "tbbrgBudgetResourcesGroupId".equalsIgnoreCase(column)) {
            return getTbbrgBudgetResourcesGroupId() == null ? "" : getTbbrgBudgetResourcesGroupId().toString();
        } else if ("tbbrg_id".equalsIgnoreCase(column) || "tbbrgId".equalsIgnoreCase(column)) {
            return getTbbrgId() == null ? "" : getTbbrgId().toString();
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof TbBudgetResourcesGroupBean)) {
            return false;
        }

		TbBudgetResourcesGroupBean obj = (TbBudgetResourcesGroupBean) object;
		return new EqualsBuilder()
            .append(getTbbrgName(), obj.getTbbrgName())
            .append(getTbbrgBudgetResourcesGroupId(), obj.getTbbrgBudgetResourcesGroupId())
            .append(getTbbrgId(), obj.getTbbrgId())
            .isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getTbbrgName())
            .append(getTbbrgBudgetResourcesGroupId())
            .append(getTbbrgId())
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
            .append("tbbrg_name", getTbbrgName())
            .append("tbbrg_budget_resources_group_id", getTbbrgBudgetResourcesGroupId())
            .append("tbbrg_id", getTbbrgId())
            .toString();
	}


    public int compareTo(Object object)
    {
        TbBudgetResourcesGroupBean obj = (TbBudgetResourcesGroupBean) object;
        return new CompareToBuilder()
            .append(getTbbrgName(), obj.getTbbrgName())
            .append(getTbbrgBudgetResourcesGroupId(), obj.getTbbrgBudgetResourcesGroupId())
            .append(getTbbrgId(), obj.getTbbrgId())
            .toComparison();
   }
}
