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

public class TbAssignedLanguagesBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = 4835798638794196781L;
	
	private TbAssignedLanguagesBeanModel beanModels[];
	
	public TbAssignedLanguagesBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(TbAssignedLanguagesBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public Integer getTbalCompetency()
    {
		return (Integer) get("tbalCompetency");
    }

    public void setTbalCompetency(Integer newVal)
    {
		set("tbalCompetency", newVal);
    }

    public void setTbalCompetency(int newVal)
    {
        setTbalCompetency(new Integer(newVal));
    }

    public Integer getTbalFluency()
    {
		return (Integer) get("tbalFluency");
    }

    public void setTbalFluency(Integer newVal)
    {
		set("tbalFluency", newVal);
    }

    public void setTbalFluency(int newVal)
    {
        setTbalFluency(new Integer(newVal));
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

}
