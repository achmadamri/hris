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
 * TbReportToBean is a mapping of tb_report_to Table.
 * @author sql2java
*/
public class TbReportToBean
    implements Serializable, GeneratedBean
{
	private static final long serialVersionUID = -4706594956444383291L;
	
    private Integer tbrtReportingMethod;

    private boolean tbrtReportingMethodIsModified = false;
    private boolean tbrtReportingMethodIsInitialized = false;

    private Integer tbrtSpv;

    private boolean tbrtSpvIsModified = false;
    private boolean tbrtSpvIsInitialized = false;

    private Integer tbeId;

    private boolean tbeIdIsModified = false;
    private boolean tbeIdIsInitialized = false;

    private Integer tbrtId;

    private boolean tbrtIdIsModified = false;
    private boolean tbrtIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered methods to create a TbReportToBean is via the createTbReportToBean method in TbReportToManager or
     * via the factory class TbReportToFactory create method
     */
    protected TbReportToBean()
    {
    }

    /**
     * Getter method for tbrtReportingMethod.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_report_to.tbrt_reporting_method</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbrtReportingMethod
     */
    public Integer getTbrtReportingMethod()
    {
        return tbrtReportingMethod;
    }

    /**
     * Setter method for tbrtReportingMethod.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbrtReportingMethod
     */
    public void setTbrtReportingMethod(Integer newVal)
    {
        if ((newVal != null && tbrtReportingMethod != null && (newVal.compareTo(tbrtReportingMethod) == 0)) ||
            (newVal == null && tbrtReportingMethod == null && tbrtReportingMethodIsInitialized)) {
            return;
        }
        tbrtReportingMethod = newVal;
        tbrtReportingMethodIsModified = true;
        tbrtReportingMethodIsInitialized = true;
    }

    /**
     * Setter method for tbrtReportingMethod.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbrtReportingMethod
     */
    public void setTbrtReportingMethod(int newVal)
    {
        setTbrtReportingMethod(new Integer(newVal));
    }

    /**
     * Determines if the tbrtReportingMethod has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbrtReportingMethodModified()
    {
        return tbrtReportingMethodIsModified;
    }

    /**
     * Determines if the tbrtReportingMethod has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbrtReportingMethodInitialized()
    {
        return tbrtReportingMethodIsInitialized;
    }

    /**
     * Getter method for tbrtSpv.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_report_to.tbrt_spv</li>
     * <li> foreign key: tb_employee.tbe_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbrtSpv
     */
    public Integer getTbrtSpv()
    {
        return tbrtSpv;
    }

    /**
     * Setter method for tbrtSpv.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbrtSpv
     */
    public void setTbrtSpv(Integer newVal)
    {
        if ((newVal != null && tbrtSpv != null && (newVal.compareTo(tbrtSpv) == 0)) ||
            (newVal == null && tbrtSpv == null && tbrtSpvIsInitialized)) {
            return;
        }
        tbrtSpv = newVal;
        tbrtSpvIsModified = true;
        tbrtSpvIsInitialized = true;
    }

    /**
     * Setter method for tbrtSpv.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbrtSpv
     */
    public void setTbrtSpv(int newVal)
    {
        setTbrtSpv(new Integer(newVal));
    }

    /**
     * Determines if the tbrtSpv has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbrtSpvModified()
    {
        return tbrtSpvIsModified;
    }

    /**
     * Determines if the tbrtSpv has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbrtSpvInitialized()
    {
        return tbrtSpvIsInitialized;
    }

    /**
     * Getter method for tbeId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_report_to.tbe_id</li>
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
     * Getter method for tbrtId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_report_to.tbrt_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbrtId
     */
    public Integer getTbrtId()
    {
        return tbrtId;
    }

    /**
     * Setter method for tbrtId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbrtId
     */
    public void setTbrtId(Integer newVal)
    {
        if ((newVal != null && tbrtId != null && (newVal.compareTo(tbrtId) == 0)) ||
            (newVal == null && tbrtId == null && tbrtIdIsInitialized)) {
            return;
        }
        tbrtId = newVal;
        tbrtIdIsModified = true;
        tbrtIdIsInitialized = true;
    }

    /**
     * Setter method for tbrtId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbrtId
     */
    public void setTbrtId(int newVal)
    {
        setTbrtId(new Integer(newVal));
    }

    /**
     * Determines if the tbrtId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbrtIdModified()
    {
        return tbrtIdIsModified;
    }

    /**
     * Determines if the tbrtId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbrtIdInitialized()
    {
        return tbrtIdIsInitialized;
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
        return tbrtReportingMethodIsModified 		|| tbrtSpvIsModified  		|| tbeIdIsModified  		|| tbrtIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        tbrtReportingMethodIsModified = false;
        tbrtSpvIsModified = false;
        tbeIdIsModified = false;
        tbrtIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(TbReportToBean bean)
    {
        setTbrtReportingMethod(bean.getTbrtReportingMethod());
        setTbrtSpv(bean.getTbrtSpv());
        setTbeId(bean.getTbeId());
        setTbrtId(bean.getTbrtId());
    }

    /**
     * return a dictionnary of the object
     */
    public Map getDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbrt_reporting_method", getTbrtReportingMethod() == null ? "" : getTbrtReportingMethod().toString());
        dictionnary.put("tbrt_spv", getTbrtSpv() == null ? "" : getTbrtSpv().toString());
        dictionnary.put("tbe_id", getTbeId() == null ? "" : getTbeId().toString());
        dictionnary.put("tbrt_id", getTbrtId() == null ? "" : getTbrtId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map getPkDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbrt_id", getTbrtId() == null ? "" : getTbrtId().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("tbrt_reporting_method".equalsIgnoreCase(column) || "tbrtReportingMethod".equalsIgnoreCase(column)) {
            return getTbrtReportingMethod() == null ? "" : getTbrtReportingMethod().toString();
        } else if ("tbrt_spv".equalsIgnoreCase(column) || "tbrtSpv".equalsIgnoreCase(column)) {
            return getTbrtSpv() == null ? "" : getTbrtSpv().toString();
        } else if ("tbe_id".equalsIgnoreCase(column) || "tbeId".equalsIgnoreCase(column)) {
            return getTbeId() == null ? "" : getTbeId().toString();
        } else if ("tbrt_id".equalsIgnoreCase(column) || "tbrtId".equalsIgnoreCase(column)) {
            return getTbrtId() == null ? "" : getTbrtId().toString();
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof TbReportToBean)) {
            return false;
        }

		TbReportToBean obj = (TbReportToBean) object;
		return new EqualsBuilder()
            .append(getTbrtReportingMethod(), obj.getTbrtReportingMethod())
            .append(getTbrtSpv(), obj.getTbrtSpv())
            .append(getTbeId(), obj.getTbeId())
            .append(getTbrtId(), obj.getTbrtId())
            .isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getTbrtReportingMethod())
            .append(getTbrtSpv())
            .append(getTbeId())
            .append(getTbrtId())
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
            .append("tbrt_reporting_method", getTbrtReportingMethod())
            .append("tbrt_spv", getTbrtSpv())
            .append("tbe_id", getTbeId())
            .append("tbrt_id", getTbrtId())
            .toString();
	}


    public int compareTo(Object object)
    {
        TbReportToBean obj = (TbReportToBean) object;
        return new CompareToBuilder()
            .append(getTbrtReportingMethod(), obj.getTbrtReportingMethod())
            .append(getTbrtSpv(), obj.getTbrtSpv())
            .append(getTbeId(), obj.getTbeId())
            .append(getTbrtId(), obj.getTbrtId())
            .toComparison();
   }
}
