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

public class TbProjectEmployeeBeanService
{
	private static final long serialVersionUID = -3531371662914967324L;
	
    private Integer tbeId;

    private Integer tbpId;

    private Integer tbpeId;

	
	private TbProjectEmployeeBeanService beanServices[];
	
	public TbProjectEmployeeBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(TbProjectEmployeeBeanService[] _beanServices) {
		this.beanServices = _beanServices;
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

    public Integer getTbpeId()
    {
		return tbpeId;
    }

    public void setTbpeId(Integer newVal)
    {
		this.tbpeId = newVal;
    }

    public void setTbpeId(int newVal)
    {
        setTbpeId(new Integer(newVal));
    }

}
