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

public class ViewMembershipTypesBeanService
{
	private static final long serialVersionUID = -3549548986888427325L;
	
    private String tbmtName;

    private String tbmtMembershipTypesId;

    private Integer tbmtId;

    private String tbmName;

    private String tbmMembershipId;

    private Integer tbmId;

	
	private ViewMembershipTypesBeanService beanServices[];
	
	public ViewMembershipTypesBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(ViewMembershipTypesBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public String getTbmtName()
    {
		return tbmtName;
    }

    public void setTbmtName(String newVal)
    {
		this.tbmtName = newVal;
    }


    public String getTbmtMembershipTypesId()
    {
		return tbmtMembershipTypesId;
    }

    public void setTbmtMembershipTypesId(String newVal)
    {
		this.tbmtMembershipTypesId = newVal;
    }


    public Integer getTbmtId()
    {
		return tbmtId;
    }

    public void setTbmtId(Integer newVal)
    {
		this.tbmtId = newVal;
    }

    public void setTbmtId(int newVal)
    {
        setTbmtId(new Integer(newVal));
    }

    public String getTbmName()
    {
		return tbmName;
    }

    public void setTbmName(String newVal)
    {
		this.tbmName = newVal;
    }


    public String getTbmMembershipId()
    {
		return tbmMembershipId;
    }

    public void setTbmMembershipId(String newVal)
    {
		this.tbmMembershipId = newVal;
    }


    public Integer getTbmId()
    {
		return tbmId;
    }

    public void setTbmId(Integer newVal)
    {
		this.tbmId = newVal;
    }

    public void setTbmId(int newVal)
    {
        setTbmId(new Integer(newVal));
    }

}
