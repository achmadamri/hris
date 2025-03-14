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
 * TbKpiBean is a mapping of tb_kpi Table.
 * @author sql2java
*/
public class TbKpiBean
    implements Serializable, GeneratedBean
{
	private static final long serialVersionUID = -1897561318385902685L;
	
    private Integer tbkBobot;

    private boolean tbkBobotIsModified = false;
    private boolean tbkBobotIsInitialized = false;

    private String tbkTargetNilai5;

    private boolean tbkTargetNilai5IsModified = false;
    private boolean tbkTargetNilai5IsInitialized = false;

    private String tbkTargetNilai4;

    private boolean tbkTargetNilai4IsModified = false;
    private boolean tbkTargetNilai4IsInitialized = false;

    private String tbkTargetNilai3;

    private boolean tbkTargetNilai3IsModified = false;
    private boolean tbkTargetNilai3IsInitialized = false;

    private String tbkTargetNilai2;

    private boolean tbkTargetNilai2IsModified = false;
    private boolean tbkTargetNilai2IsInitialized = false;

    private String tbkTargetNilai1;

    private boolean tbkTargetNilai1IsModified = false;
    private boolean tbkTargetNilai1IsInitialized = false;

    private String tbkDescription;

    private boolean tbkDescriptionIsModified = false;
    private boolean tbkDescriptionIsInitialized = false;

    private Integer tbeId;

    private boolean tbeIdIsModified = false;
    private boolean tbeIdIsInitialized = false;

    private Integer tbkgId;

    private boolean tbkgIdIsModified = false;
    private boolean tbkgIdIsInitialized = false;

    private Integer tbkId;

    private boolean tbkIdIsModified = false;
    private boolean tbkIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered methods to create a TbKpiBean is via the createTbKpiBean method in TbKpiManager or
     * via the factory class TbKpiFactory create method
     */
    protected TbKpiBean()
    {
    }

    /**
     * Getter method for tbkBobot.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbk_bobot</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbkBobot
     */
    public Integer getTbkBobot()
    {
        return tbkBobot;
    }

    /**
     * Setter method for tbkBobot.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbkBobot
     */
    public void setTbkBobot(Integer newVal)
    {
        if ((newVal != null && tbkBobot != null && (newVal.compareTo(tbkBobot) == 0)) ||
            (newVal == null && tbkBobot == null && tbkBobotIsInitialized)) {
            return;
        }
        tbkBobot = newVal;
        tbkBobotIsModified = true;
        tbkBobotIsInitialized = true;
    }

    /**
     * Setter method for tbkBobot.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbkBobot
     */
    public void setTbkBobot(int newVal)
    {
        setTbkBobot(new Integer(newVal));
    }

    /**
     * Determines if the tbkBobot has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbkBobotModified()
    {
        return tbkBobotIsModified;
    }

    /**
     * Determines if the tbkBobot has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbkBobotInitialized()
    {
        return tbkBobotIsInitialized;
    }

    /**
     * Getter method for tbkTargetNilai5.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbk_target_nilai_5</li>
     * <li>column size: 500</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbkTargetNilai5
     */
    public String getTbkTargetNilai5()
    {
        return tbkTargetNilai5;
    }

    /**
     * Setter method for tbkTargetNilai5.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbkTargetNilai5
     */
    public void setTbkTargetNilai5(String newVal)
    {
        if ((newVal != null && tbkTargetNilai5 != null && (newVal.compareTo(tbkTargetNilai5) == 0)) ||
            (newVal == null && tbkTargetNilai5 == null && tbkTargetNilai5IsInitialized)) {
            return;
        }
        tbkTargetNilai5 = newVal;
        tbkTargetNilai5IsModified = true;
        tbkTargetNilai5IsInitialized = true;
    }

    /**
     * Determines if the tbkTargetNilai5 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbkTargetNilai5Modified()
    {
        return tbkTargetNilai5IsModified;
    }

    /**
     * Determines if the tbkTargetNilai5 has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbkTargetNilai5Initialized()
    {
        return tbkTargetNilai5IsInitialized;
    }

    /**
     * Getter method for tbkTargetNilai4.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbk_target_nilai_4</li>
     * <li>column size: 500</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbkTargetNilai4
     */
    public String getTbkTargetNilai4()
    {
        return tbkTargetNilai4;
    }

    /**
     * Setter method for tbkTargetNilai4.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbkTargetNilai4
     */
    public void setTbkTargetNilai4(String newVal)
    {
        if ((newVal != null && tbkTargetNilai4 != null && (newVal.compareTo(tbkTargetNilai4) == 0)) ||
            (newVal == null && tbkTargetNilai4 == null && tbkTargetNilai4IsInitialized)) {
            return;
        }
        tbkTargetNilai4 = newVal;
        tbkTargetNilai4IsModified = true;
        tbkTargetNilai4IsInitialized = true;
    }

    /**
     * Determines if the tbkTargetNilai4 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbkTargetNilai4Modified()
    {
        return tbkTargetNilai4IsModified;
    }

    /**
     * Determines if the tbkTargetNilai4 has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbkTargetNilai4Initialized()
    {
        return tbkTargetNilai4IsInitialized;
    }

    /**
     * Getter method for tbkTargetNilai3.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbk_target_nilai_3</li>
     * <li>column size: 500</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbkTargetNilai3
     */
    public String getTbkTargetNilai3()
    {
        return tbkTargetNilai3;
    }

    /**
     * Setter method for tbkTargetNilai3.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbkTargetNilai3
     */
    public void setTbkTargetNilai3(String newVal)
    {
        if ((newVal != null && tbkTargetNilai3 != null && (newVal.compareTo(tbkTargetNilai3) == 0)) ||
            (newVal == null && tbkTargetNilai3 == null && tbkTargetNilai3IsInitialized)) {
            return;
        }
        tbkTargetNilai3 = newVal;
        tbkTargetNilai3IsModified = true;
        tbkTargetNilai3IsInitialized = true;
    }

    /**
     * Determines if the tbkTargetNilai3 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbkTargetNilai3Modified()
    {
        return tbkTargetNilai3IsModified;
    }

    /**
     * Determines if the tbkTargetNilai3 has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbkTargetNilai3Initialized()
    {
        return tbkTargetNilai3IsInitialized;
    }

    /**
     * Getter method for tbkTargetNilai2.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbk_target_nilai_2</li>
     * <li>column size: 500</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbkTargetNilai2
     */
    public String getTbkTargetNilai2()
    {
        return tbkTargetNilai2;
    }

    /**
     * Setter method for tbkTargetNilai2.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbkTargetNilai2
     */
    public void setTbkTargetNilai2(String newVal)
    {
        if ((newVal != null && tbkTargetNilai2 != null && (newVal.compareTo(tbkTargetNilai2) == 0)) ||
            (newVal == null && tbkTargetNilai2 == null && tbkTargetNilai2IsInitialized)) {
            return;
        }
        tbkTargetNilai2 = newVal;
        tbkTargetNilai2IsModified = true;
        tbkTargetNilai2IsInitialized = true;
    }

    /**
     * Determines if the tbkTargetNilai2 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbkTargetNilai2Modified()
    {
        return tbkTargetNilai2IsModified;
    }

    /**
     * Determines if the tbkTargetNilai2 has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbkTargetNilai2Initialized()
    {
        return tbkTargetNilai2IsInitialized;
    }

    /**
     * Getter method for tbkTargetNilai1.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbk_target_nilai_1</li>
     * <li>column size: 500</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbkTargetNilai1
     */
    public String getTbkTargetNilai1()
    {
        return tbkTargetNilai1;
    }

    /**
     * Setter method for tbkTargetNilai1.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbkTargetNilai1
     */
    public void setTbkTargetNilai1(String newVal)
    {
        if ((newVal != null && tbkTargetNilai1 != null && (newVal.compareTo(tbkTargetNilai1) == 0)) ||
            (newVal == null && tbkTargetNilai1 == null && tbkTargetNilai1IsInitialized)) {
            return;
        }
        tbkTargetNilai1 = newVal;
        tbkTargetNilai1IsModified = true;
        tbkTargetNilai1IsInitialized = true;
    }

    /**
     * Determines if the tbkTargetNilai1 has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbkTargetNilai1Modified()
    {
        return tbkTargetNilai1IsModified;
    }

    /**
     * Determines if the tbkTargetNilai1 has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbkTargetNilai1Initialized()
    {
        return tbkTargetNilai1IsInitialized;
    }

    /**
     * Getter method for tbkDescription.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbk_description</li>
     * <li>column size: 500</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of tbkDescription
     */
    public String getTbkDescription()
    {
        return tbkDescription;
    }

    /**
     * Setter method for tbkDescription.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbkDescription
     */
    public void setTbkDescription(String newVal)
    {
        if ((newVal != null && tbkDescription != null && (newVal.compareTo(tbkDescription) == 0)) ||
            (newVal == null && tbkDescription == null && tbkDescriptionIsInitialized)) {
            return;
        }
        tbkDescription = newVal;
        tbkDescriptionIsModified = true;
        tbkDescriptionIsInitialized = true;
    }

    /**
     * Determines if the tbkDescription has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbkDescriptionModified()
    {
        return tbkDescriptionIsModified;
    }

    /**
     * Determines if the tbkDescription has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbkDescriptionInitialized()
    {
        return tbkDescriptionIsInitialized;
    }

    /**
     * Getter method for tbeId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbe_Id</li>
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
     * Getter method for tbkgId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbkg_id</li>
     * <li> foreign key: tb_kpi_group.tbkg_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbkgId
     */
    public Integer getTbkgId()
    {
        return tbkgId;
    }

    /**
     * Setter method for tbkgId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbkgId
     */
    public void setTbkgId(Integer newVal)
    {
        if ((newVal != null && tbkgId != null && (newVal.compareTo(tbkgId) == 0)) ||
            (newVal == null && tbkgId == null && tbkgIdIsInitialized)) {
            return;
        }
        tbkgId = newVal;
        tbkgIdIsModified = true;
        tbkgIdIsInitialized = true;
    }

    /**
     * Setter method for tbkgId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbkgId
     */
    public void setTbkgId(int newVal)
    {
        setTbkgId(new Integer(newVal));
    }

    /**
     * Determines if the tbkgId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbkgIdModified()
    {
        return tbkgIdIsModified;
    }

    /**
     * Determines if the tbkgId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbkgIdInitialized()
    {
        return tbkgIdIsInitialized;
    }

    /**
     * Getter method for tbkId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: tb_kpi.tbk_id</li>
     * <li> imported key: tb_kpi_assign.tbk_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of tbkId
     */
    public Integer getTbkId()
    {
        return tbkId;
    }

    /**
     * Setter method for tbkId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to tbkId
     */
    public void setTbkId(Integer newVal)
    {
        if ((newVal != null && tbkId != null && (newVal.compareTo(tbkId) == 0)) ||
            (newVal == null && tbkId == null && tbkIdIsInitialized)) {
            return;
        }
        tbkId = newVal;
        tbkIdIsModified = true;
        tbkIdIsInitialized = true;
    }

    /**
     * Setter method for tbkId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to tbkId
     */
    public void setTbkId(int newVal)
    {
        setTbkId(new Integer(newVal));
    }

    /**
     * Determines if the tbkId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isTbkIdModified()
    {
        return tbkIdIsModified;
    }

    /**
     * Determines if the tbkId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isTbkIdInitialized()
    {
        return tbkIdIsInitialized;
    }

    /** The TbKpiGroup referenced by this bean. */
    private TbKpiGroupBean referencedTbKpiGroup;
    /** Getter method for TbKpiGroupBean. */
    public TbKpiGroupBean getTbKpiGroupBean() {
        return this.referencedTbKpiGroup;
    }
    /** Setter method for TbKpiGroupBean. */
    public void setTbKpiGroupBean(TbKpiGroupBean reference) {
        this.referencedTbKpiGroup = reference;
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
        return tbkBobotIsModified 		|| tbkTargetNilai5IsModified  		|| tbkTargetNilai4IsModified  		|| tbkTargetNilai3IsModified  		|| tbkTargetNilai2IsModified  		|| tbkTargetNilai1IsModified  		|| tbkDescriptionIsModified  		|| tbeIdIsModified  		|| tbkgIdIsModified  		|| tbkIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified()
    {
        tbkBobotIsModified = false;
        tbkTargetNilai5IsModified = false;
        tbkTargetNilai4IsModified = false;
        tbkTargetNilai3IsModified = false;
        tbkTargetNilai2IsModified = false;
        tbkTargetNilai1IsModified = false;
        tbkDescriptionIsModified = false;
        tbeIdIsModified = false;
        tbkgIdIsModified = false;
        tbkIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(TbKpiBean bean)
    {
        setTbkBobot(bean.getTbkBobot());
        setTbkTargetNilai5(bean.getTbkTargetNilai5());
        setTbkTargetNilai4(bean.getTbkTargetNilai4());
        setTbkTargetNilai3(bean.getTbkTargetNilai3());
        setTbkTargetNilai2(bean.getTbkTargetNilai2());
        setTbkTargetNilai1(bean.getTbkTargetNilai1());
        setTbkDescription(bean.getTbkDescription());
        setTbeId(bean.getTbeId());
        setTbkgId(bean.getTbkgId());
        setTbkId(bean.getTbkId());
    }

    /**
     * return a dictionnary of the object
     */
    public Map getDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbk_bobot", getTbkBobot() == null ? "" : getTbkBobot().toString());
        dictionnary.put("tbk_target_nilai_5", getTbkTargetNilai5() == null ? "" : getTbkTargetNilai5().toString());
        dictionnary.put("tbk_target_nilai_4", getTbkTargetNilai4() == null ? "" : getTbkTargetNilai4().toString());
        dictionnary.put("tbk_target_nilai_3", getTbkTargetNilai3() == null ? "" : getTbkTargetNilai3().toString());
        dictionnary.put("tbk_target_nilai_2", getTbkTargetNilai2() == null ? "" : getTbkTargetNilai2().toString());
        dictionnary.put("tbk_target_nilai_1", getTbkTargetNilai1() == null ? "" : getTbkTargetNilai1().toString());
        dictionnary.put("tbk_description", getTbkDescription() == null ? "" : getTbkDescription().toString());
        dictionnary.put("tbe_id", getTbeId() == null ? "" : getTbeId().toString());
        dictionnary.put("tbkg_id", getTbkgId() == null ? "" : getTbkgId().toString());
        dictionnary.put("tbk_id", getTbkId() == null ? "" : getTbkId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map getPkDictionnary()
    {
        Map dictionnary = new HashMap();
        dictionnary.put("tbk_id", getTbkId() == null ? "" : getTbkId().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("tbk_bobot".equalsIgnoreCase(column) || "tbkBobot".equalsIgnoreCase(column)) {
            return getTbkBobot() == null ? "" : getTbkBobot().toString();
        } else if ("tbk_target_nilai_5".equalsIgnoreCase(column) || "tbkTargetNilai5".equalsIgnoreCase(column)) {
            return getTbkTargetNilai5() == null ? "" : getTbkTargetNilai5().toString();
        } else if ("tbk_target_nilai_4".equalsIgnoreCase(column) || "tbkTargetNilai4".equalsIgnoreCase(column)) {
            return getTbkTargetNilai4() == null ? "" : getTbkTargetNilai4().toString();
        } else if ("tbk_target_nilai_3".equalsIgnoreCase(column) || "tbkTargetNilai3".equalsIgnoreCase(column)) {
            return getTbkTargetNilai3() == null ? "" : getTbkTargetNilai3().toString();
        } else if ("tbk_target_nilai_2".equalsIgnoreCase(column) || "tbkTargetNilai2".equalsIgnoreCase(column)) {
            return getTbkTargetNilai2() == null ? "" : getTbkTargetNilai2().toString();
        } else if ("tbk_target_nilai_1".equalsIgnoreCase(column) || "tbkTargetNilai1".equalsIgnoreCase(column)) {
            return getTbkTargetNilai1() == null ? "" : getTbkTargetNilai1().toString();
        } else if ("tbk_description".equalsIgnoreCase(column) || "tbkDescription".equalsIgnoreCase(column)) {
            return getTbkDescription() == null ? "" : getTbkDescription().toString();
        } else if ("tbe_Id".equalsIgnoreCase(column) || "tbeId".equalsIgnoreCase(column)) {
            return getTbeId() == null ? "" : getTbeId().toString();
        } else if ("tbkg_id".equalsIgnoreCase(column) || "tbkgId".equalsIgnoreCase(column)) {
            return getTbkgId() == null ? "" : getTbkgId().toString();
        } else if ("tbk_id".equalsIgnoreCase(column) || "tbkId".equalsIgnoreCase(column)) {
            return getTbkId() == null ? "" : getTbkId().toString();
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof TbKpiBean)) {
            return false;
        }

		TbKpiBean obj = (TbKpiBean) object;
		return new EqualsBuilder()
            .append(getTbkBobot(), obj.getTbkBobot())
            .append(getTbkTargetNilai5(), obj.getTbkTargetNilai5())
            .append(getTbkTargetNilai4(), obj.getTbkTargetNilai4())
            .append(getTbkTargetNilai3(), obj.getTbkTargetNilai3())
            .append(getTbkTargetNilai2(), obj.getTbkTargetNilai2())
            .append(getTbkTargetNilai1(), obj.getTbkTargetNilai1())
            .append(getTbkDescription(), obj.getTbkDescription())
            .append(getTbeId(), obj.getTbeId())
            .append(getTbkgId(), obj.getTbkgId())
            .append(getTbkId(), obj.getTbkId())
            .isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getTbkBobot())
            .append(getTbkTargetNilai5())
            .append(getTbkTargetNilai4())
            .append(getTbkTargetNilai3())
            .append(getTbkTargetNilai2())
            .append(getTbkTargetNilai1())
            .append(getTbkDescription())
            .append(getTbeId())
            .append(getTbkgId())
            .append(getTbkId())
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
            .append("tbk_bobot", getTbkBobot())
            .append("tbk_target_nilai_5", getTbkTargetNilai5())
            .append("tbk_target_nilai_4", getTbkTargetNilai4())
            .append("tbk_target_nilai_3", getTbkTargetNilai3())
            .append("tbk_target_nilai_2", getTbkTargetNilai2())
            .append("tbk_target_nilai_1", getTbkTargetNilai1())
            .append("tbk_description", getTbkDescription())
            .append("tbe_Id", getTbeId())
            .append("tbkg_id", getTbkgId())
            .append("tbk_id", getTbkId())
            .toString();
	}


    public int compareTo(Object object)
    {
        TbKpiBean obj = (TbKpiBean) object;
        return new CompareToBuilder()
            .append(getTbkBobot(), obj.getTbkBobot())
            .append(getTbkTargetNilai5(), obj.getTbkTargetNilai5())
            .append(getTbkTargetNilai4(), obj.getTbkTargetNilai4())
            .append(getTbkTargetNilai3(), obj.getTbkTargetNilai3())
            .append(getTbkTargetNilai2(), obj.getTbkTargetNilai2())
            .append(getTbkTargetNilai1(), obj.getTbkTargetNilai1())
            .append(getTbkDescription(), obj.getTbkDescription())
            .append(getTbeId(), obj.getTbeId())
            .append(getTbkgId(), obj.getTbkgId())
            .append(getTbkId(), obj.getTbkId())
            .toComparison();
   }
}
