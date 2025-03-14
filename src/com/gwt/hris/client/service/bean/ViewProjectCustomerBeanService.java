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

public class ViewProjectCustomerBeanService
{
	private static final long serialVersionUID = -3544591537238374940L;
	
    private String tbcDescription;

    private String tbcName;

    private String tbcCustomerId;

    private Integer tbcId;

    private String tbpDescription;

    private String tbpName;

    private String tbpProjectId;

    private Integer tbpId;

	
	private ViewProjectCustomerBeanService beanServices[];
	
	public ViewProjectCustomerBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(ViewProjectCustomerBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public String getTbcDescription()
    {
		return tbcDescription;
    }

    public void setTbcDescription(String newVal)
    {
		this.tbcDescription = newVal;
    }


    public String getTbcName()
    {
		return tbcName;
    }

    public void setTbcName(String newVal)
    {
		this.tbcName = newVal;
    }


    public String getTbcCustomerId()
    {
		return tbcCustomerId;
    }

    public void setTbcCustomerId(String newVal)
    {
		this.tbcCustomerId = newVal;
    }


    public Integer getTbcId()
    {
		return tbcId;
    }

    public void setTbcId(Integer newVal)
    {
		this.tbcId = newVal;
    }

    public void setTbcId(int newVal)
    {
        setTbcId(new Integer(newVal));
    }

    public String getTbpDescription()
    {
		return tbpDescription;
    }

    public void setTbpDescription(String newVal)
    {
		this.tbpDescription = newVal;
    }


    public String getTbpName()
    {
		return tbpName;
    }

    public void setTbpName(String newVal)
    {
		this.tbpName = newVal;
    }


    public String getTbpProjectId()
    {
		return tbpProjectId;
    }

    public void setTbpProjectId(String newVal)
    {
		this.tbpProjectId = newVal;
    }


    public Integer getTbpId()
    {
		return tbpId;
    }

    public void setTbpId(Integer newVal)
    {
		this.tbpId = newVal;
    }

    public void setTbpId(int newVal)
    {
        setTbpId(new Integer(newVal));
    }

}
