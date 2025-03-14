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

public class TbPaygradeCurrencyBeanService
{
	private static final long serialVersionUID = -3536329116859987004L;
	
    private Double tbpcOvertime;

    private Double tbpcLate;

    private Double tbpcStep;

    private Double tbpcMax;

    private Double tbpcMin;

    private Integer tbcId;

    private Integer tbpId;

    private Integer tbpcId;

	
	private TbPaygradeCurrencyBeanService beanServices[];
	
	public TbPaygradeCurrencyBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(TbPaygradeCurrencyBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public Double getTbpcOvertime()
    {
		return tbpcOvertime;
    }

    public void setTbpcOvertime(Double newVal)
    {
		this.tbpcOvertime = newVal;
    }

    public void setTbpcOvertime(double newVal)
    {
        setTbpcOvertime(new Double(newVal));
    }

    public Double getTbpcLate()
    {
		return tbpcLate;
    }

    public void setTbpcLate(Double newVal)
    {
		this.tbpcLate = newVal;
    }

    public void setTbpcLate(double newVal)
    {
        setTbpcLate(new Double(newVal));
    }

    public Double getTbpcStep()
    {
		return tbpcStep;
    }

    public void setTbpcStep(Double newVal)
    {
		this.tbpcStep = newVal;
    }

    public void setTbpcStep(double newVal)
    {
        setTbpcStep(new Double(newVal));
    }

    public Double getTbpcMax()
    {
		return tbpcMax;
    }

    public void setTbpcMax(Double newVal)
    {
		this.tbpcMax = newVal;
    }

    public void setTbpcMax(double newVal)
    {
        setTbpcMax(new Double(newVal));
    }

    public Double getTbpcMin()
    {
		return tbpcMin;
    }

    public void setTbpcMin(Double newVal)
    {
		this.tbpcMin = newVal;
    }

    public void setTbpcMin(double newVal)
    {
        setTbpcMin(new Double(newVal));
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

    public Integer getTbpcId()
    {
		return tbpcId;
    }

    public void setTbpcId(Integer newVal)
    {
		this.tbpcId = newVal;
    }

    public void setTbpcId(int newVal)
    {
        setTbpcId(new Integer(newVal));
    }

}
