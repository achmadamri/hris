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

public class ViewKpiBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = -8807734337778090459L;
	
	private ViewKpiBeanModel beanModels[];
	
	public ViewKpiBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(ViewKpiBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
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

    public Integer getTbkBobot()
    {
		return (Integer) get("tbkBobot");
    }

    public void setTbkBobot(Integer newVal)
    {
		set("tbkBobot", newVal);
    }

    public void setTbkBobot(int newVal)
    {
        setTbkBobot(new Integer(newVal));
    }

    public String getTbkTargetNilai5()
    {
		return (String) get("tbkTargetNilai5");
    }

    public void setTbkTargetNilai5(String newVal)
    {
		set("tbkTargetNilai5", newVal);
    }


    public String getTbkTargetNilai4()
    {
		return (String) get("tbkTargetNilai4");
    }

    public void setTbkTargetNilai4(String newVal)
    {
		set("tbkTargetNilai4", newVal);
    }


    public String getTbkTargetNilai3()
    {
		return (String) get("tbkTargetNilai3");
    }

    public void setTbkTargetNilai3(String newVal)
    {
		set("tbkTargetNilai3", newVal);
    }


    public String getTbkTargetNilai2()
    {
		return (String) get("tbkTargetNilai2");
    }

    public void setTbkTargetNilai2(String newVal)
    {
		set("tbkTargetNilai2", newVal);
    }


    public String getTbkTargetNilai1()
    {
		return (String) get("tbkTargetNilai1");
    }

    public void setTbkTargetNilai1(String newVal)
    {
		set("tbkTargetNilai1", newVal);
    }


    public String getTbkDescription()
    {
		return (String) get("tbkDescription");
    }

    public void setTbkDescription(String newVal)
    {
		set("tbkDescription", newVal);
    }


    public Integer getTbkId()
    {
		return (Integer) get("tbkId");
    }

    public void setTbkId(Integer newVal)
    {
		set("tbkId", newVal);
    }

    public void setTbkId(int newVal)
    {
        setTbkId(new Integer(newVal));
    }

    public String getTbeName()
    {
		return (String) get("tbeName");
    }

    public void setTbeName(String newVal)
    {
		set("tbeName", newVal);
    }


    public String getTbkgName()
    {
		return (String) get("tbkgName");
    }

    public void setTbkgName(String newVal)
    {
		set("tbkgName", newVal);
    }


    public String getTbkgKpiGroupId()
    {
		return (String) get("tbkgKpiGroupId");
    }

    public void setTbkgKpiGroupId(String newVal)
    {
		set("tbkgKpiGroupId", newVal);
    }


    public Integer getTbkgId()
    {
		return (Integer) get("tbkgId");
    }

    public void setTbkgId(Integer newVal)
    {
		set("tbkgId", newVal);
    }

    public void setTbkgId(int newVal)
    {
        setTbkgId(new Integer(newVal));
    }

}
