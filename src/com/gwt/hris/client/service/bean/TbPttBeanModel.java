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

public class TbPttBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = 2680468258523135691L;
	
	private TbPttBeanModel beanModels[];
	
	public TbPttBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(TbPttBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public String getTbpttName()
    {
		return (String) get("tbpttName");
    }

    public void setTbpttName(String newVal)
    {
		set("tbpttName", newVal);
    }


    public String getTbpttPttId()
    {
		return (String) get("tbpttPttId");
    }

    public void setTbpttPttId(String newVal)
    {
		set("tbpttPttId", newVal);
    }


    public Integer getTbpttId()
    {
		return (Integer) get("tbpttId");
    }

    public void setTbpttId(Integer newVal)
    {
		set("tbpttId", newVal);
    }

    public void setTbpttId(int newVal)
    {
        setTbpttId(new Integer(newVal));
    }

}
