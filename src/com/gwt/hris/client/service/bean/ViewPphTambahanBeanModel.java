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

public class ViewPphTambahanBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = 2534406024720858056L;
	
	private ViewPphTambahanBeanModel beanModels[];
	
	public ViewPphTambahanBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(ViewPphTambahanBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public Double getTbpphtNominal()
    {
		return (Double) get("tbpphtNominal");
    }

    public void setTbpphtNominal(Double newVal)
    {
		set("tbpphtNominal", newVal);
    }

    public void setTbpphtNominal(double newVal)
    {
        setTbpphtNominal(new Double(newVal));
    }

    public String getTbpphtName()
    {
		return (String) get("tbpphtName");
    }

    public void setTbpphtName(String newVal)
    {
		set("tbpphtName", newVal);
    }


    public Integer getTbpphtId()
    {
		return (Integer) get("tbpphtId");
    }

    public void setTbpphtId(Integer newVal)
    {
		set("tbpphtId", newVal);
    }

    public void setTbpphtId(int newVal)
    {
        setTbpphtId(new Integer(newVal));
    }

    public java.sql.Timestamp getTbpphDate()
    {
		return (java.sql.Timestamp) get("tbpphDate");
    }

    public void setTbpphDate(java.sql.Timestamp newVal)
    {
		set("tbpphDate", newVal);
    }

    public void setTbpphDate(long newVal)
    {
        setTbpphDate(new java.sql.Timestamp(newVal));
    }

    public Integer getTbpphId()
    {
		return (Integer) get("tbpphId");
    }

    public void setTbpphId(Integer newVal)
    {
		set("tbpphId", newVal);
    }

    public void setTbpphId(int newVal)
    {
        setTbpphId(new Integer(newVal));
    }

    public String getTbeName()
    {
		return (String) get("tbeName");
    }

    public void setTbeName(String newVal)
    {
		set("tbeName", newVal);
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

}
