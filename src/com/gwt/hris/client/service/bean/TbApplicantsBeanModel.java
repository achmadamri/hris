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

public class TbApplicantsBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = -497297766953803440L;
	
	private TbApplicantsBeanModel beanModels[];
	
	public TbApplicantsBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(TbApplicantsBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public Integer getTbvId()
    {
		return (Integer) get("tbvId");
    }

    public void setTbvId(Integer newVal)
    {
		set("tbvId", newVal);
    }

    public void setTbvId(int newVal)
    {
        setTbvId(new Integer(newVal));
    }

    public Integer getTbaStatus()
    {
		return (Integer) get("tbaStatus");
    }

    public void setTbaStatus(Integer newVal)
    {
		set("tbaStatus", newVal);
    }

    public void setTbaStatus(int newVal)
    {
        setTbaStatus(new Integer(newVal));
    }

    public String getTbaResumeFileNameExisting()
    {
		return (String) get("tbaResumeFileNameExisting");
    }

    public void setTbaResumeFileNameExisting(String newVal)
    {
		set("tbaResumeFileNameExisting", newVal);
    }


    public String getTbaResumeFileName()
    {
		return (String) get("tbaResumeFileName");
    }

    public void setTbaResumeFileName(String newVal)
    {
		set("tbaResumeFileName", newVal);
    }


    public java.sql.Timestamp getTbaDate()
    {
		return (java.sql.Timestamp) get("tbaDate");
    }

    public void setTbaDate(java.sql.Timestamp newVal)
    {
		set("tbaDate", newVal);
    }

    public void setTbaDate(long newVal)
    {
        setTbaDate(new java.sql.Timestamp(newVal));
    }

    public String getTbaComments()
    {
		return (String) get("tbaComments");
    }

    public void setTbaComments(String newVal)
    {
		set("tbaComments", newVal);
    }


    public String getTbaMobile()
    {
		return (String) get("tbaMobile");
    }

    public void setTbaMobile(String newVal)
    {
		set("tbaMobile", newVal);
    }


    public String getTbaPhone()
    {
		return (String) get("tbaPhone");
    }

    public void setTbaPhone(String newVal)
    {
		set("tbaPhone", newVal);
    }


    public String getTbaEmail()
    {
		return (String) get("tbaEmail");
    }

    public void setTbaEmail(String newVal)
    {
		set("tbaEmail", newVal);
    }


    public String getTbaName()
    {
		return (String) get("tbaName");
    }

    public void setTbaName(String newVal)
    {
		set("tbaName", newVal);
    }


    public String getTbaNickName()
    {
		return (String) get("tbaNickName");
    }

    public void setTbaNickName(String newVal)
    {
		set("tbaNickName", newVal);
    }


    public String getTbaLastName()
    {
		return (String) get("tbaLastName");
    }

    public void setTbaLastName(String newVal)
    {
		set("tbaLastName", newVal);
    }


    public String getTbaMiddleName()
    {
		return (String) get("tbaMiddleName");
    }

    public void setTbaMiddleName(String newVal)
    {
		set("tbaMiddleName", newVal);
    }


    public String getTbaFirstName()
    {
		return (String) get("tbaFirstName");
    }

    public void setTbaFirstName(String newVal)
    {
		set("tbaFirstName", newVal);
    }


    public String getTbaApplicantsId()
    {
		return (String) get("tbaApplicantsId");
    }

    public void setTbaApplicantsId(String newVal)
    {
		set("tbaApplicantsId", newVal);
    }


    public Integer getTbaId()
    {
		return (Integer) get("tbaId");
    }

    public void setTbaId(Integer newVal)
    {
		set("tbaId", newVal);
    }

    public void setTbaId(int newVal)
    {
        setTbaId(new Integer(newVal));
    }

}
