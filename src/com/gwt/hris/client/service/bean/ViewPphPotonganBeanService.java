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

public class ViewPphPotonganBeanService
{
	private static final long serialVersionUID = -3544591537238374940L;
	
    private Double tbpphpNominal;

    private String tbpphpName;

    private Integer tbpphpId;

    private java.sql.Timestamp tbpphDate;

    private Integer tbpphId;

    private String tbeName;

    private Integer tbeId;

	
	private ViewPphPotonganBeanService beanServices[];
	
	public ViewPphPotonganBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(ViewPphPotonganBeanService[] _beanServices) {
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

    public java.sql.Timestamp getTbpphDate()
    {
		return tbpphDate;
    }

    public void setTbpphDate(java.sql.Timestamp newVal)
    {
		this.tbpphDate = newVal;
    }

    public void setTbpphDate(long newVal)
    {
        setTbpphDate(new java.sql.Timestamp(newVal));
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

    public String getTbeName()
    {
		return tbeName;
    }

    public void setTbeName(String newVal)
    {
		this.tbeName = newVal;
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

}
