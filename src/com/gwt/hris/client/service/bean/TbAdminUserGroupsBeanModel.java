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

public class TbAdminUserGroupsBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = -497297766953803440L;
	
	private TbAdminUserGroupsBeanModel beanModels[];
	
	public TbAdminUserGroupsBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(TbAdminUserGroupsBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public String getTbaugName()
    {
		return (String) get("tbaugName");
    }

    public void setTbaugName(String newVal)
    {
		set("tbaugName", newVal);
    }


    public String getTbaugAdminUserGroupsId()
    {
		return (String) get("tbaugAdminUserGroupsId");
    }

    public void setTbaugAdminUserGroupsId(String newVal)
    {
		set("tbaugAdminUserGroupsId", newVal);
    }


    public Integer getTbaugId()
    {
		return (Integer) get("tbaugId");
    }

    public void setTbaugId(Integer newVal)
    {
		set("tbaugId", newVal);
    }

    public void setTbaugId(int newVal)
    {
        setTbaugId(new Integer(newVal));
    }

}
