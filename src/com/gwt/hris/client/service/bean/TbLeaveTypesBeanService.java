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

public class TbLeaveTypesBeanService
{
	private static final long serialVersionUID = -3524761730048230812L;
	
    private Integer tbltReduction;

    private String tbltName;

    private String tbltLeaveTypesId;

    private Integer tbltId;

	
	private TbLeaveTypesBeanService beanServices[];
	
	public TbLeaveTypesBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(TbLeaveTypesBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public Integer getTbltReduction()
    {
		return tbltReduction;
    }

    public void setTbltReduction(Integer newVal)
    {
		this.tbltReduction = newVal;
    }

    public void setTbltReduction(int newVal)
    {
        setTbltReduction(new Integer(newVal));
    }

    public String getTbltName()
    {
		return tbltName;
    }

    public void setTbltName(String newVal)
    {
		this.tbltName = newVal;
    }


    public String getTbltLeaveTypesId()
    {
		return tbltLeaveTypesId;
    }

    public void setTbltLeaveTypesId(String newVal)
    {
		this.tbltLeaveTypesId = newVal;
    }


    public Integer getTbltId()
    {
		return tbltId;
    }

    public void setTbltId(Integer newVal)
    {
		this.tbltId = newVal;
    }

    public void setTbltId(int newVal)
    {
        setTbltId(new Integer(newVal));
    }

}
