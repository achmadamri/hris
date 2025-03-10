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
 * ViewRenumerationBean is a mapping of view_renumeration Table.
 * @author sql2java
*/
public class ViewRenumerationBean
    implements Serializable, GeneratedBean
{
	private static final long serialVersionUID = 1866997670323752546L;
	
    private String tbcName;

    private boolean tbcNameIsModified = false;
    private boolean tbcNameIsInitialized = false;

    private String tbcCurrencyId;

    private boolean tbcCurrencyIdIsModified = false;
    private boolean tbcCurrencyIdIsInitialized = false;

    private Double tbrNominal;

    private boolean tbrNominalIsModified = false;
    private boolean tbrNominalIsInitialized = false;

    private String tbrName;

    private boolean tbrNameIsModified = false;
    private boolean tbrNameIsInitialized = false;

    private String tbrRenumerationId;

    private boolean tbrRenumerationIdIsModified = false;
    private boolean tbrRenumerationIdIsInitialized = false;

    private Integer tbcId;

    private boolean tbcIdIsModified = false;
    private boolean tbcIdIsInitialized = false;

    private Integer tbrId;

    private boolean tbrIdIsModified = false;
    private boolean tbrIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered methods to create a ViewRenumerationBean is via the createViewRenumerationBean method in ViewRenumerationManager or
     * via the factory class ViewRenumerationFactory create method
     */
    protected ViewRenumerationBean()
    {
    }

    /**
     * Getter method for tbcName.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: view_renumeration.tbc_name</li>
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
     * Getter method for tbcCurrencyId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: view_renumeration.tbc_currency_id</li>
     * <li>comments: NAME+ID</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbcCurrencyId
     */
    public String getTbcCurrencyId()
    {
        return tbcCurrencyId;
    }

    /**
     * Setter method for tbcCurrencyId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbcCurrencyId
     */
    public void setTbcCurrencyId(String newVal)
    {
        if ((newVal != null && tbcCurrencyId != null && (newVal.compareTo(tbcCurrencyId) == 0)) ||
            (newVal == null && tbcCurrencyId == null && tbcCurrencyIdIsInitialized)) {
            return;
        }
        tbcCurrencyId = newVal;
        tbcCurrencyIdIsModified = true;
        tbcCurrencyIdIsInitialized = true;
    }

    /**
     * Determines if the tbcCurrencyId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbcCurrencyIdModified()
    {
        return tbcCurrencyIdIsModified;
    }

    /**
     * Determines if the tbcCurrencyId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbcCurrencyIdInitialized()
    {
        return tbcCurrencyIdIsInitialized;
    }

    /**
     * Getter method for tbrNominal.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: view_renumeration.tbr_nominal</li>
     * <li>column size: 22</li>
     * <li>jdbc type returned by the driver: Types.DOUBLE</li>
     * </ul>
     *
     * @return the value of tbrNominal
     */
    public Double getTbrNominal()
    {
        return tbrNominal;
    }

    /**
     * Setter method for tbrNominal.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbrNominal
     */
    public void setTbrNominal(Double newVal)
    {
        if ((newVal != null && tbrNominal != null && (newVal.compareTo(tbrNominal) == 0)) ||
            (newVal == null && tbrNominal == null && tbrNominalIsInitialized)) {
            return;
        }
        tbrNominal = newVal;
        tbrNominalIsModified = true;
        tbrNominalIsInitialized = true;
    }

    /**
     * Setter method for tbrNominal.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbrNominal
     */
    public void setTbrNominal(double newVal)
    {
        setTbrNominal(new Double(newVal));
    }

    /**
     * Determines if the tbrNominal has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbrNominalModified()
    {
        return tbrNominalIsModified;
    }

    /**
     * Determines if the tbrNominal has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbrNominalInitialized()
    {
        return tbrNominalIsInitialized;
    }

    /**
     * Getter method for tbrName.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: view_renumeration.tbr_name</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbrName
     */
    public String getTbrName()
    {
        return tbrName;
    }

    /**
     * Setter method for tbrName.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbrName
     */
    public void setTbrName(String newVal)
    {
        if ((newVal != null && tbrName != null && (newVal.compareTo(tbrName) == 0)) ||
            (newVal == null && tbrName == null && tbrNameIsInitialized)) {
            return;
        }
        tbrName = newVal;
        tbrNameIsModified = true;
        tbrNameIsInitialized = true;
    }

    /**
     * Determines if the tbrName has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbrNameModified()
    {
        return tbrNameIsModified;
    }

    /**
     * Determines if the tbrName has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbrNameInitialized()
    {
        return tbrNameIsInitialized;
    }

    /**
     * Getter method for tbrRenumerationId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: view_renumeration.tbr_renumeration_id</li>
     * <li>column size: 45</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbrRenumerationId
     */
    public String getTbrRenumerationId()
    {
        return tbrRenumerationId;
    }

    /**
     * Setter method for tbrRenumerationId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbrRenumerationId
     */
    public void setTbrRenumerationId(String newVal)
    {
        if ((newVal != null && tbrRenumerationId != null && (newVal.compareTo(tbrRenumerationId) == 0)) ||
            (newVal == null && tbrRenumerationId == null && tbrRenumerationIdIsInitialized)) {
            return;
        }
        tbrRenumerationId = newVal;
        tbrRenumerationIdIsModified = true;
        tbrRenumerationIdIsInitialized = true;
    }

    /**
     * Determines if the tbrRenumerationId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbrRenumerationIdModified()
    {
        return tbrRenumerationIdIsModified;
    }

    /**
     * Determines if the tbrRenumerationId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbrRenumerationIdInitialized()
    {
        return tbrRenumerationIdIsInitialized;
    }

    /**
     * Getter method for tbcId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: view_renumeration.tbc_id</li>
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
     * Getter method for tbrId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: view_renumeration.tbr_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbrId
     */
    public Integer getTbrId()
    {
        return tbrId;
    }

    /**
     * Setter method for tbrId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbrId
     */
    public void setTbrId(Integer newVal)
    {
        if ((newVal != null && tbrId != null && (newVal.compareTo(tbrId) == 0)) ||
            (newVal == null && tbrId == null && tbrIdIsInitialized)) {
            return;
        }
        tbrId = newVal;
        tbrIdIsModified = true;
        tbrIdIsInitialized = true;
    }

    /**
     * Setter method for tbrId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbrId
     */
    public void setTbrId(int newVal)
    {
        setTbrId(new Integer(newVal));
    }

    /**
     * Determines if the tbrId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbrIdModified()
    {
        return tbrIdIsModified;
    }

    /**
     * Determines if the tbrId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbrIdInitialized()
    {
        return tbrIdIsInitialized;
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
        return tbcNameIsModified 		|| tbcCurrencyIdIsModified  		|| tbrNominalIsModified  		|| tbrNameIsModified  		|| tbrRenumerationIdIsModified  		|| tbcIdIsModified  		|| tbrIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        tbcNameIsModified = false;
        tbcCurrencyIdIsModified = false;
        tbrNominalIsModified = false;
        tbrNameIsModified = false;
        tbrRenumerationIdIsModified = false;
        tbcIdIsModified = false;
        tbrIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(ViewRenumerationBean bean)
    {
        setTbcName(bean.getTbcName());
        setTbcCurrencyId(bean.getTbcCurrencyId());
        setTbrNominal(bean.getTbrNominal());
        setTbrName(bean.getTbrName());
        setTbrRenumerationId(bean.getTbrRenumerationId());
        setTbcId(bean.getTbcId());
        setTbrId(bean.getTbrId());
    }

    /**
     * return a dictionnary of the object
     */
    public Map getDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbc_name", getTbcName() == null ? "" : getTbcName().toString());
        dictionnary.put("tbc_currency_id", getTbcCurrencyId() == null ? "" : getTbcCurrencyId().toString());
        dictionnary.put("tbr_nominal", getTbrNominal() == null ? "" : getTbrNominal().toString());
        dictionnary.put("tbr_name", getTbrName() == null ? "" : getTbrName().toString());
        dictionnary.put("tbr_renumeration_id", getTbrRenumerationId() == null ? "" : getTbrRenumerationId().toString());
        dictionnary.put("tbc_id", getTbcId() == null ? "" : getTbcId().toString());
        dictionnary.put("tbr_id", getTbrId() == null ? "" : getTbrId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     * no primary key, the regular dictionnary is returned
     */
    public Map getPkDictionnary()
    {
        return getDictionnary();
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
        } else if ("tbc_currency_id".equalsIgnoreCase(column) || "tbcCurrencyId".equalsIgnoreCase(column)) {
            return getTbcCurrencyId() == null ? "" : getTbcCurrencyId().toString();
        } else if ("tbr_nominal".equalsIgnoreCase(column) || "tbrNominal".equalsIgnoreCase(column)) {
            return getTbrNominal() == null ? "" : getTbrNominal().toString();
        } else if ("tbr_name".equalsIgnoreCase(column) || "tbrName".equalsIgnoreCase(column)) {
            return getTbrName() == null ? "" : getTbrName().toString();
        } else if ("tbr_renumeration_id".equalsIgnoreCase(column) || "tbrRenumerationId".equalsIgnoreCase(column)) {
            return getTbrRenumerationId() == null ? "" : getTbrRenumerationId().toString();
        } else if ("tbc_id".equalsIgnoreCase(column) || "tbcId".equalsIgnoreCase(column)) {
            return getTbcId() == null ? "" : getTbcId().toString();
        } else if ("tbr_id".equalsIgnoreCase(column) || "tbrId".equalsIgnoreCase(column)) {
            return getTbrId() == null ? "" : getTbrId().toString();
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof ViewRenumerationBean)) {
            return false;
        }

		ViewRenumerationBean obj = (ViewRenumerationBean) object;
		return new EqualsBuilder()
            .append(getTbcName(), obj.getTbcName())
            .append(getTbcCurrencyId(), obj.getTbcCurrencyId())
            .append(getTbrNominal(), obj.getTbrNominal())
            .append(getTbrName(), obj.getTbrName())
            .append(getTbrRenumerationId(), obj.getTbrRenumerationId())
            .append(getTbcId(), obj.getTbcId())
            .append(getTbrId(), obj.getTbrId())
            .isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getTbcName())
            .append(getTbcCurrencyId())
            .append(getTbrNominal())
            .append(getTbrName())
            .append(getTbrRenumerationId())
            .append(getTbcId())
            .append(getTbrId())
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
            .append("tbc_currency_id", getTbcCurrencyId())
            .append("tbr_nominal", getTbrNominal())
            .append("tbr_name", getTbrName())
            .append("tbr_renumeration_id", getTbrRenumerationId())
            .append("tbc_id", getTbcId())
            .append("tbr_id", getTbrId())
            .toString();
	}


    public int compareTo(Object object)
    {
        ViewRenumerationBean obj = (ViewRenumerationBean) object;
        return new CompareToBuilder()
            .append(getTbcName(), obj.getTbcName())
            .append(getTbcCurrencyId(), obj.getTbcCurrencyId())
            .append(getTbrNominal(), obj.getTbrNominal())
            .append(getTbrName(), obj.getTbrName())
            .append(getTbrRenumerationId(), obj.getTbrRenumerationId())
            .append(getTbcId(), obj.getTbcId())
            .append(getTbrId(), obj.getTbrId())
            .toComparison();
   }
}
