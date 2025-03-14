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

public class TbAssignedSkillsBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = 4835798638794196781L;
	
	private TbAssignedSkillsBeanModel beanModels[];
	
	public TbAssignedSkillsBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(TbAssignedSkillsBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public String getTbasComments()
    {
		return (String) get("tbasComments");
    }

    public void setTbasComments(String newVal)
    {
		set("tbasComments", newVal);
    }


    public Integer getTbasYear()
    {
		return (Integer) get("tbasYear");
    }

    public void setTbasYear(Integer newVal)
    {
		set("tbasYear", newVal);
    }

    public void setTbasYear(int newVal)
    {
        setTbasYear(new Integer(newVal));
    }

    public Integer getTbsId()
    {
		return (Integer) get("tbsId");
    }

    public void setTbsId(Integer newVal)
    {
		set("tbsId", newVal);
    }

    public void setTbsId(int newVal)
    {
        setTbsId(new Integer(newVal));
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

    public Integer getTbasId()
    {
		return (Integer) get("tbasId");
    }

    public void setTbasId(Integer newVal)
    {
		set("tbasId", newVal);
    }

    public void setTbasId(int newVal)
    {
        setTbasId(new Integer(newVal));
    }

}
