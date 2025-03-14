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

public class ViewEmployeeLicensesBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = 7867502430468858276L;
	
	private ViewEmployeeLicensesBeanModel beanModels[];
	
	public ViewEmployeeLicensesBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(ViewEmployeeLicensesBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public java.sql.Timestamp getTbalEndDate()
    {
		return (java.sql.Timestamp) get("tbalEndDate");
    }

    public void setTbalEndDate(java.sql.Timestamp newVal)
    {
		set("tbalEndDate", newVal);
    }

    public void setTbalEndDate(long newVal)
    {
        setTbalEndDate(new java.sql.Timestamp(newVal));
    }

    public java.sql.Timestamp getTbalStartDate()
    {
		return (java.sql.Timestamp) get("tbalStartDate");
    }

    public void setTbalStartDate(java.sql.Timestamp newVal)
    {
		set("tbalStartDate", newVal);
    }

    public void setTbalStartDate(long newVal)
    {
        setTbalStartDate(new java.sql.Timestamp(newVal));
    }

    public Integer getTbalId()
    {
		return (Integer) get("tbalId");
    }

    public void setTbalId(Integer newVal)
    {
		set("tbalId", newVal);
    }

    public void setTbalId(int newVal)
    {
        setTbalId(new Integer(newVal));
    }

    public String getTblDescription()
    {
		return (String) get("tblDescription");
    }

    public void setTblDescription(String newVal)
    {
		set("tblDescription", newVal);
    }


    public String getTblName()
    {
		return (String) get("tblName");
    }

    public void setTblName(String newVal)
    {
		set("tblName", newVal);
    }


    public String getTblLicensesId()
    {
		return (String) get("tblLicensesId");
    }

    public void setTblLicensesId(String newVal)
    {
		set("tblLicensesId", newVal);
    }


    public Integer getTblId()
    {
		return (Integer) get("tblId");
    }

    public void setTblId(Integer newVal)
    {
		set("tblId", newVal);
    }

    public void setTblId(int newVal)
    {
        setTblId(new Integer(newVal));
    }

    public String getTbeName()
    {
		return (String) get("tbeName");
    }

    public void setTbeName(String newVal)
    {
		set("tbeName", newVal);
    }


    public String getTbeNickName()
    {
		return (String) get("tbeNickName");
    }

    public void setTbeNickName(String newVal)
    {
		set("tbeNickName", newVal);
    }


    public String getTbeLastName()
    {
		return (String) get("tbeLastName");
    }

    public void setTbeLastName(String newVal)
    {
		set("tbeLastName", newVal);
    }


    public String getTbeMiddleName()
    {
		return (String) get("tbeMiddleName");
    }

    public void setTbeMiddleName(String newVal)
    {
		set("tbeMiddleName", newVal);
    }


    public String getTbeFirstName()
    {
		return (String) get("tbeFirstName");
    }

    public void setTbeFirstName(String newVal)
    {
		set("tbeFirstName", newVal);
    }


    public String getTbeEmployeeId()
    {
		return (String) get("tbeEmployeeId");
    }

    public void setTbeEmployeeId(String newVal)
    {
		set("tbeEmployeeId", newVal);
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
