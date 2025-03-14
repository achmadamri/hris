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

public class ViewTimesheetProjectBeanModel extends ReturnBean implements Serializable
{
	private static final long serialVersionUID = -3529001562923432231L;
	
	private ViewTimesheetProjectBeanModel beanModels[];
	
	public ViewTimesheetProjectBeanModel[] getModels() {
		return beanModels;
	}

	public void setModels(ViewTimesheetProjectBeanModel[] _beanModels) {
		this.beanModels = _beanModels;
	}

    public Integer getTbtApprovalStatus()
    {
		return (Integer) get("tbtApprovalStatus");
    }

    public void setTbtApprovalStatus(Integer newVal)
    {
		set("tbtApprovalStatus", newVal);
    }

    public void setTbtApprovalStatus(int newVal)
    {
        setTbtApprovalStatus(new Integer(newVal));
    }

    public Integer getTbtGrandTotalHour()
    {
		return (Integer) get("tbtGrandTotalHour");
    }

    public void setTbtGrandTotalHour(Integer newVal)
    {
		set("tbtGrandTotalHour", newVal);
    }

    public void setTbtGrandTotalHour(int newVal)
    {
        setTbtGrandTotalHour(new Integer(newVal));
    }

    public Long getSumTbtTotalHour()
    {
		return (Long) get("sumTbtTotalHour");
    }

    public void setSumTbtTotalHour(Long newVal)
    {
		set("sumTbtTotalHour", newVal);
    }

    public void setSumTbtTotalHour(long newVal)
    {
        setSumTbtTotalHour(new Long(newVal));
    }

    public String getTbtStartOfWeek()
    {
		return (String) get("tbtStartOfWeek");
    }

    public void setTbtStartOfWeek(String newVal)
    {
		set("tbtStartOfWeek", newVal);
    }


    public Integer getTbpaPayable()
    {
		return (Integer) get("tbpaPayable");
    }

    public void setTbpaPayable(Integer newVal)
    {
		set("tbpaPayable", newVal);
    }

    public void setTbpaPayable(int newVal)
    {
        setTbpaPayable(new Integer(newVal));
    }

    public String getTbpaName()
    {
		return (String) get("tbpaName");
    }

    public void setTbpaName(String newVal)
    {
		set("tbpaName", newVal);
    }


    public Integer getTbpaId()
    {
		return (Integer) get("tbpaId");
    }

    public void setTbpaId(Integer newVal)
    {
		set("tbpaId", newVal);
    }

    public void setTbpaId(int newVal)
    {
        setTbpaId(new Integer(newVal));
    }

    public String getTbpagName()
    {
		return (String) get("tbpagName");
    }

    public void setTbpagName(String newVal)
    {
		set("tbpagName", newVal);
    }


    public String getTbpagProjectActivitiesGroupId()
    {
		return (String) get("tbpagProjectActivitiesGroupId");
    }

    public void setTbpagProjectActivitiesGroupId(String newVal)
    {
		set("tbpagProjectActivitiesGroupId", newVal);
    }


    public Integer getTbpagId()
    {
		return (Integer) get("tbpagId");
    }

    public void setTbpagId(Integer newVal)
    {
		set("tbpagId", newVal);
    }

    public void setTbpagId(int newVal)
    {
        setTbpagId(new Integer(newVal));
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

    public String getTbcCustomerId()
    {
		return (String) get("tbcCustomerId");
    }

    public void setTbcCustomerId(String newVal)
    {
		set("tbcCustomerId", newVal);
    }


    public String getTbcName()
    {
		return (String) get("tbcName");
    }

    public void setTbcName(String newVal)
    {
		set("tbcName", newVal);
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

    public String getTbeName()
    {
		return (String) get("tbeName");
    }

    public void setTbeName(String newVal)
    {
		set("tbeName", newVal);
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
