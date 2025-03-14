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

package com.gwt.hris.client.service.bean;

import java.io.Serializable;

public class TbEmployeeSalaryBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = 8013564664271135912L;
	
	private TbEmployeeSalaryBeanModel beanModels[];
	
	public TbEmployeeSalaryBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(TbEmployeeSalaryBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public String getTbesCurrencyName()
    {
		return (String) get("tbesCurrencyName");
    }

    public void setTbesCurrencyName(String newVal)
    {
		set("tbesCurrencyName", newVal);
    }


    public Integer getTbesPayFrequency()
    {
		return (Integer) get("tbesPayFrequency");
    }

    public void setTbesPayFrequency(Integer newVal)
    {
		set("tbesPayFrequency", newVal);
    }

    public void setTbesPayFrequency(int newVal)
    {
        setTbesPayFrequency(new Integer(newVal));
    }

    public Double getTbesCogs()
    {
		return (Double) get("tbesCogs");
    }

    public void setTbesCogs(Double newVal)
    {
		set("tbesCogs", newVal);
    }

    public void setTbesCogs(double newVal)
    {
        setTbesCogs(new Double(newVal));
    }

    public Double getTbesBasicSalary()
    {
		return (Double) get("tbesBasicSalary");
    }

    public void setTbesBasicSalary(Double newVal)
    {
		set("tbesBasicSalary", newVal);
    }

    public void setTbesBasicSalary(double newVal)
    {
        setTbesBasicSalary(new Double(newVal));
    }

    public Integer getTbcId()
    {
		return (Integer) get("tbcId");
    }

    public void setTbcId(Integer newVal)
    {
		set("tbcId", newVal);
    }

    public void setTbcId(int newVal)
    {
        setTbcId(new Integer(newVal));
    }

    public Integer getTbpId()
    {
		return (Integer) get("tbpId");
    }

    public void setTbpId(Integer newVal)
    {
		set("tbpId", newVal);
    }

    public void setTbpId(int newVal)
    {
        setTbpId(new Integer(newVal));
    }

    public Integer getTbeId()
    {
		return (Integer) get("tbeId");
    }

    public void setTbeId(Integer newVal)
    {
		set("tbeId", newVal);
    }

    public void setTbeId(int newVal)
    {
        setTbeId(new Integer(newVal));
    }

    public Integer getTbesId()
    {
		return (Integer) get("tbesId");
    }

    public void setTbesId(Integer newVal)
    {
		set("tbesId", newVal);
    }

    public void setTbesId(int newVal)
    {
        setTbesId(new Integer(newVal));
    }

}
