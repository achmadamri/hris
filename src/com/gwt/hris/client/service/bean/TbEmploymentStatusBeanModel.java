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

public class TbEmploymentStatusBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = 86951176845241694L;
	
	private TbEmploymentStatusBeanModel beanModels[];
	
	public TbEmploymentStatusBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(TbEmploymentStatusBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public String getTbesName()
    {
		return (String) get("tbesName");
    }

    public void setTbesName(String newVal)
    {
		set("tbesName", newVal);
    }


    public String getTbesEmploymentStatusId()
    {
		return (String) get("tbesEmploymentStatusId");
    }

    public void setTbesEmploymentStatusId(String newVal)
    {
		set("tbesEmploymentStatusId", newVal);
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
