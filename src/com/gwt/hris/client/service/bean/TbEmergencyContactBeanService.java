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

public class TbEmergencyContactBeanService
{
	private static final long serialVersionUID = -3528066696481599068L;
	
    private String tbecWorkPhone;

    private String tbecMobilePhone;

    private String tbecHomePhone;

    private String tbecRelationship;

    private String tbecName;

    private Integer tbeId;

    private Integer tbecId;

	
	private TbEmergencyContactBeanService beanServices[];
	
	public TbEmergencyContactBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(TbEmergencyContactBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public String getTbecWorkPhone()
    {
		return tbecWorkPhone;
    }

    public void setTbecWorkPhone(String newVal)
    {
		this.tbecWorkPhone = newVal;
    }


    public String getTbecMobilePhone()
    {
		return tbecMobilePhone;
    }

    public void setTbecMobilePhone(String newVal)
    {
		this.tbecMobilePhone = newVal;
    }


    public String getTbecHomePhone()
    {
		return tbecHomePhone;
    }

    public void setTbecHomePhone(String newVal)
    {
		this.tbecHomePhone = newVal;
    }


    public String getTbecRelationship()
    {
		return tbecRelationship;
    }

    public void setTbecRelationship(String newVal)
    {
		this.tbecRelationship = newVal;
    }


    public String getTbecName()
    {
		return tbecName;
    }

    public void setTbecName(String newVal)
    {
		this.tbecName = newVal;
    }


    public Integer getTbeId()
    {
		return tbeId;
    }

    public void setTbeId(Integer newVal)
    {
		this.tbeId = newVal;
    }

    public void setTbeId(int newVal)
    {
        setTbeId(new Integer(newVal));
    }

    public Integer getTbecId()
    {
		return tbecId;
    }

    public void setTbecId(Integer newVal)
    {
		this.tbecId = newVal;
    }

    public void setTbecId(int newVal)
    {
        setTbecId(new Integer(newVal));
    }

}
