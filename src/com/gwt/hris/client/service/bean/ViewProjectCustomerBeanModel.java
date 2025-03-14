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

public class ViewProjectCustomerBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = 2534406024720858056L;
	
	private ViewProjectCustomerBeanModel beanModels[];
	
	public ViewProjectCustomerBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(ViewProjectCustomerBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public String getTbcDescription()
    {
		return (String) get("tbcDescription");
    }

    public void setTbcDescription(String newVal)
    {
		set("tbcDescription", newVal);
    }


    public String getTbcName()
    {
		return (String) get("tbcName");
    }

    public void setTbcName(String newVal)
    {
		set("tbcName", newVal);
    }


    public String getTbcCustomerId()
    {
		return (String) get("tbcCustomerId");
    }

    public void setTbcCustomerId(String newVal)
    {
		set("tbcCustomerId", newVal);
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

    public String getTbpDescription()
    {
		return (String) get("tbpDescription");
    }

    public void setTbpDescription(String newVal)
    {
		set("tbpDescription", newVal);
    }


    public String getTbpName()
    {
		return (String) get("tbpName");
    }

    public void setTbpName(String newVal)
    {
		set("tbpName", newVal);
    }


    public String getTbpProjectId()
    {
		return (String) get("tbpProjectId");
    }

    public void setTbpProjectId(String newVal)
    {
		set("tbpProjectId", newVal);
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

}
