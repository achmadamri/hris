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

public class TbEmploymentStatusBeanService
{
	private static final long serialVersionUID = -3529719179698283196L;
	
    private String tbesName;

    private String tbesEmploymentStatusId;

    private Integer tbesId;

	
	private TbEmploymentStatusBeanService beanServices[];
	
	public TbEmploymentStatusBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(TbEmploymentStatusBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public String getTbesName()
    {
		return tbesName;
    }

    public void setTbesName(String newVal)
    {
		this.tbesName = newVal;
    }


    public String getTbesEmploymentStatusId()
    {
		return tbesEmploymentStatusId;
    }

    public void setTbesEmploymentStatusId(String newVal)
    {
		this.tbesEmploymentStatusId = newVal;
    }


    public Integer getTbesId()
    {
		return tbesId;
    }

    public void setTbesId(Integer newVal)
    {
		this.tbesId = newVal;
    }

    public void setTbesId(int newVal)
    {
        setTbesId(new Integer(newVal));
    }

}
