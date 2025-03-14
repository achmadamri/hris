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
 * TbCountryBean is a mapping of tb_country Table.
 * @author sql2java
*/
public class TbCountryBean
    implements Serializable, GeneratedBean
{
	private static final long serialVersionUID = -3432561154144494692L;
	
    private String tbcName;

    private boolean tbcNameIsModified = false;
    private boolean tbcNameIsInitialized = false;

    private Integer tbcId;

    private boolean tbcIdIsModified = false;
    private boolean tbcIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered methods to create a TbCountryBean is via the createTbCountryBean method in TbCountryManager or
     * via the factory class TbCountryFactory create method
     */
    protected TbCountryBean()
    {
    }

    /**
     * Getter method for tbcName.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_country.tbc_name</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbcName
     */
    public String getTbcName()
    {
        return tbcName;
    }

    /**
     * Setter method for tbcName.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbcName
     */
    public void setTbcName(String newVal)
    {
        if ((newVal != null && tbcName != null && (newVal.compareTo(tbcName) == 0)) ||
            (newVal == null && tbcName == null && tbcNameIsInitialized)) {
            return;
        }
        tbcName = newVal;
        tbcNameIsModified = true;
        tbcNameIsInitialized = true;
    }

    /**
     * Determines if the tbcName has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbcNameModified()
    {
        return tbcNameIsModified;
    }

    /**
     * Determines if the tbcName has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbcNameInitialized()
    {
        return tbcNameIsInitialized;
    }

    /**
     * Getter method for tbcId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_country.tbc_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbcId
     */
    public Integer getTbcId()
    {
        return tbcId;
    }

    /**
     * Setter method for tbcId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbcId
     */
    public void setTbcId(Integer newVal)
    {
        if ((newVal != null && tbcId != null && (newVal.compareTo(tbcId) == 0)) ||
            (newVal == null && tbcId == null && tbcIdIsInitialized)) {
            return;
        }
        tbcId = newVal;
        tbcIdIsModified = true;
        tbcIdIsInitialized = true;
    }

    /**
     * Setter method for tbcId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbcId
     */
    public void setTbcId(int newVal)
    {
        setTbcId(new Integer(newVal));
    }

    /**
     * Determines if the tbcId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbcIdModified()
    {
        return tbcIdIsModified;
    }

    /**
     * Determines if the tbcId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbcIdInitialized()
    {
        return tbcIdIsInitialized;
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
        return tbcNameIsModified 		|| tbcIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        tbcNameIsModified = false;
        tbcIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(TbCountryBean bean)
    {
        setTbcName(bean.getTbcName());
        setTbcId(bean.getTbcId());
    }

    /**
     * return a dictionnary of the object
     */
    public Map getDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbc_name", getTbcName() == null ? "" : getTbcName().toString());
        dictionnary.put("tbc_id", getTbcId() == null ? "" : getTbcId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map getPkDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbc_id", getTbcId() == null ? "" : getTbcId().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("tbc_name".equalsIgnoreCase(column) || "tbcName".equalsIgnoreCase(column)) {
            return getTbcName() == null ? "" : getTbcName().toString();
        } else if ("tbc_id".equalsIgnoreCase(column) || "tbcId".equalsIgnoreCase(column)) {
            return getTbcId() == null ? "" : getTbcId().toString();
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof TbCountryBean)) {
            return false;
        }

		TbCountryBean obj = (TbCountryBean) object;
		return new EqualsBuilder()
            .append(getTbcName(), obj.getTbcName())
            .append(getTbcId(), obj.getTbcId())
            .isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getTbcName())
            .append(getTbcId())
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
            .append("tbc_name", getTbcName())
            .append("tbc_id", getTbcId())
            .toString();
	}


    public int compareTo(Object object)
    {
        TbCountryBean obj = (TbCountryBean) object;
        return new CompareToBuilder()
            .append(getTbcName(), obj.getTbcName())
            .append(getTbcId(), obj.getTbcId())
            .toComparison();
   }
}
