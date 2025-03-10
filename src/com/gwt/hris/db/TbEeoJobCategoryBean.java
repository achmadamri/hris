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
 * TbEeoJobCategoryBean is a mapping of tb_eeo_job_category Table.
 * @author sql2java
*/
public class TbEeoJobCategoryBean
    implements Serializable, GeneratedBean
{
	private static final long serialVersionUID = 4357522861028486474L;
	
    private String tbejcTitle;

    private boolean tbejcTitleIsModified = false;
    private boolean tbejcTitleIsInitialized = false;

    private String tbejcEeoJobCategoryId;

    private boolean tbejcEeoJobCategoryIdIsModified = false;
    private boolean tbejcEeoJobCategoryIdIsInitialized = false;

    private Integer tbejcId;

    private boolean tbejcIdIsModified = false;
    private boolean tbejcIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered methods to create a TbEeoJobCategoryBean is via the createTbEeoJobCategoryBean method in TbEeoJobCategoryManager or
     * via the factory class TbEeoJobCategoryFactory create method
     */
    protected TbEeoJobCategoryBean()
    {
    }

    /**
     * Getter method for tbejcTitle.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_eeo_job_category.tbejc_title</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbejcTitle
     */
    public String getTbejcTitle()
    {
        return tbejcTitle;
    }

    /**
     * Setter method for tbejcTitle.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbejcTitle
     */
    public void setTbejcTitle(String newVal)
    {
        if ((newVal != null && tbejcTitle != null && (newVal.compareTo(tbejcTitle) == 0)) ||
            (newVal == null && tbejcTitle == null && tbejcTitleIsInitialized)) {
            return;
        }
        tbejcTitle = newVal;
        tbejcTitleIsModified = true;
        tbejcTitleIsInitialized = true;
    }

    /**
     * Determines if the tbejcTitle has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbejcTitleModified()
    {
        return tbejcTitleIsModified;
    }

    /**
     * Determines if the tbejcTitle has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbejcTitleInitialized()
    {
        return tbejcTitleIsInitialized;
    }

    /**
     * Getter method for tbejcEeoJobCategoryId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_eeo_job_category.tbejc_eeo_job_category_id</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbejcEeoJobCategoryId
     */
    public String getTbejcEeoJobCategoryId()
    {
        return tbejcEeoJobCategoryId;
    }

    /**
     * Setter method for tbejcEeoJobCategoryId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbejcEeoJobCategoryId
     */
    public void setTbejcEeoJobCategoryId(String newVal)
    {
        if ((newVal != null && tbejcEeoJobCategoryId != null && (newVal.compareTo(tbejcEeoJobCategoryId) == 0)) ||
            (newVal == null && tbejcEeoJobCategoryId == null && tbejcEeoJobCategoryIdIsInitialized)) {
            return;
        }
        tbejcEeoJobCategoryId = newVal;
        tbejcEeoJobCategoryIdIsModified = true;
        tbejcEeoJobCategoryIdIsInitialized = true;
    }

    /**
     * Determines if the tbejcEeoJobCategoryId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbejcEeoJobCategoryIdModified()
    {
        return tbejcEeoJobCategoryIdIsModified;
    }

    /**
     * Determines if the tbejcEeoJobCategoryId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbejcEeoJobCategoryIdInitialized()
    {
        return tbejcEeoJobCategoryIdIsInitialized;
    }

    /**
     * Getter method for tbejcId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_eeo_job_category.tbejc_id</li>
     * <li> imported key: tb_job.tbejc_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbejcId
     */
    public Integer getTbejcId()
    {
        return tbejcId;
    }

    /**
     * Setter method for tbejcId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbejcId
     */
    public void setTbejcId(Integer newVal)
    {
        if ((newVal != null && tbejcId != null && (newVal.compareTo(tbejcId) == 0)) ||
            (newVal == null && tbejcId == null && tbejcIdIsInitialized)) {
            return;
        }
        tbejcId = newVal;
        tbejcIdIsModified = true;
        tbejcIdIsInitialized = true;
    }

    /**
     * Setter method for tbejcId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbejcId
     */
    public void setTbejcId(int newVal)
    {
        setTbejcId(new Integer(newVal));
    }

    /**
     * Determines if the tbejcId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbejcIdModified()
    {
        return tbejcIdIsModified;
    }

    /**
     * Determines if the tbejcId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbejcIdInitialized()
    {
        return tbejcIdIsInitialized;
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
        return tbejcTitleIsModified 		|| tbejcEeoJobCategoryIdIsModified  		|| tbejcIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        tbejcTitleIsModified = false;
        tbejcEeoJobCategoryIdIsModified = false;
        tbejcIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(TbEeoJobCategoryBean bean)
    {
        setTbejcTitle(bean.getTbejcTitle());
        setTbejcEeoJobCategoryId(bean.getTbejcEeoJobCategoryId());
        setTbejcId(bean.getTbejcId());
    }

    /**
     * return a dictionnary of the object
     */
    public Map getDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbejc_title", getTbejcTitle() == null ? "" : getTbejcTitle().toString());
        dictionnary.put("tbejc_eeo_job_category_id", getTbejcEeoJobCategoryId() == null ? "" : getTbejcEeoJobCategoryId().toString());
        dictionnary.put("tbejc_id", getTbejcId() == null ? "" : getTbejcId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map getPkDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbejc_id", getTbejcId() == null ? "" : getTbejcId().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("tbejc_title".equalsIgnoreCase(column) || "tbejcTitle".equalsIgnoreCase(column)) {
            return getTbejcTitle() == null ? "" : getTbejcTitle().toString();
        } else if ("tbejc_eeo_job_category_id".equalsIgnoreCase(column) || "tbejcEeoJobCategoryId".equalsIgnoreCase(column)) {
            return getTbejcEeoJobCategoryId() == null ? "" : getTbejcEeoJobCategoryId().toString();
        } else if ("tbejc_id".equalsIgnoreCase(column) || "tbejcId".equalsIgnoreCase(column)) {
            return getTbejcId() == null ? "" : getTbejcId().toString();
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof TbEeoJobCategoryBean)) {
            return false;
        }

		TbEeoJobCategoryBean obj = (TbEeoJobCategoryBean) object;
		return new EqualsBuilder()
            .append(getTbejcTitle(), obj.getTbejcTitle())
            .append(getTbejcEeoJobCategoryId(), obj.getTbejcEeoJobCategoryId())
            .append(getTbejcId(), obj.getTbejcId())
            .isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getTbejcTitle())
            .append(getTbejcEeoJobCategoryId())
            .append(getTbejcId())
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
            .append("tbejc_title", getTbejcTitle())
            .append("tbejc_eeo_job_category_id", getTbejcEeoJobCategoryId())
            .append("tbejc_id", getTbejcId())
            .toString();
	}


    public int compareTo(Object object)
    {
        TbEeoJobCategoryBean obj = (TbEeoJobCategoryBean) object;
        return new CompareToBuilder()
            .append(getTbejcTitle(), obj.getTbejcTitle())
            .append(getTbejcEeoJobCategoryId(), obj.getTbejcEeoJobCategoryId())
            .append(getTbejcId(), obj.getTbejcId())
            .toComparison();
   }
}
