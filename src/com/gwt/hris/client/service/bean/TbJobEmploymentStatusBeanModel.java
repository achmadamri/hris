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

public class TbJobEmploymentStatusBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = 86951176845241694L;
	
	private TbJobEmploymentStatusBeanModel beanModels[];
	
	public TbJobEmploymentStatusBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(TbJobEmploymentStatusBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
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

    public Integer getTbjtId()
    {
		return (Integer) get("tbjtId");
    }

    public void setTbjtId(Integer newVal)
    {
		set("tbjtId", newVal);
    }

    public void setTbjtId(int newVal)
    {
        setTbjtId(new Integer(newVal));
    }

    public Integer getTbjesId()
    {
		return (Integer) get("tbjesId");
    }

    public void setTbjesId(Integer newVal)
    {
		set("tbjesId", newVal);
    }

    public void setTbjesId(int newVal)
    {
        setTbjesId(new Integer(newVal));
    }

}
