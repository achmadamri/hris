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

public class ViewProjectActivitiesGroupBeanService
{
	private static final long serialVersionUID = -3544591537238374940L;
	
    private String tbpaName;

    private Integer tbpaId;

    private String tbpagName;

    private String tbpagProjectActivitiesGroupId;

    private Integer tbpagId;

	
	private ViewProjectActivitiesGroupBeanService beanServices[];
	
	public ViewProjectActivitiesGroupBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(ViewProjectActivitiesGroupBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public String getTbpaName()
    {
		return tbpaName;
    }

    public void setTbpaName(String newVal)
    {
		this.tbpaName = newVal;
    }


    public Integer getTbpaId()
    {
		return tbpaId;
    }

    public void setTbpaId(Integer newVal)
    {
		this.tbpaId = newVal;
    }

    public void setTbpaId(int newVal)
    {
        setTbpaId(new Integer(newVal));
    }

    public String getTbpagName()
    {
		return tbpagName;
    }

    public void setTbpagName(String newVal)
    {
		this.tbpagName = newVal;
    }


    public String getTbpagProjectActivitiesGroupId()
    {
		return tbpagProjectActivitiesGroupId;
    }

    public void setTbpagProjectActivitiesGroupId(String newVal)
    {
		this.tbpagProjectActivitiesGroupId = newVal;
    }


    public Integer getTbpagId()
    {
		return tbpagId;
    }

    public void setTbpagId(Integer newVal)
    {
		this.tbpagId = newVal;
    }

    public void setTbpagId(int newVal)
    {
        setTbpagId(new Integer(newVal));
    }

}
