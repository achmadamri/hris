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

public class TbPphPotonganBeanService
{
	private static final long serialVersionUID = -3531371662914967324L;
	
    private Double tbpphpNominal;

    private String tbpphpName;

    private Integer tbpphId;

    private Integer tbpphpId;

	
	private TbPphPotonganBeanService beanServices[];
	
	public TbPphPotonganBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(TbPphPotonganBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public Double getTbpphpNominal()
    {
		return tbpphpNominal;
    }

    public void setTbpphpNominal(Double newVal)
    {
		this.tbpphpNominal = newVal;
    }

    public void setTbpphpNominal(double newVal)
    {
        setTbpphpNominal(new Double(newVal));
    }

    public String getTbpphpName()
    {
		return tbpphpName;
    }

    public void setTbpphpName(String newVal)
    {
		this.tbpphpName = newVal;
    }


    public Integer getTbpphId()
    {
		return tbpphId;
    }

    public void setTbpphId(Integer newVal)
    {
		this.tbpphId = newVal;
    }

    public void setTbpphId(int newVal)
    {
        setTbpphId(new Integer(newVal));
    }

    public Integer getTbpphpId()
    {
		return tbpphpId;
    }

    public void setTbpphpId(Integer newVal)
    {
		this.tbpphpId = newVal;
    }

    public void setTbpphpId(int newVal)
    {
        setTbpphpId(new Integer(newVal));
    }

}
