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

public class ViewEmployeeLeavesSummaryBeanService
{
	private static final long serialVersionUID = -3539634083293355260L;
	
    private Integer tbltReduction;

    private String tbltName;

    private Integer minTbaleLeaveAvailable;

    private Long sumTbaleLeaveTaken;

    private Integer tbaleStatus;

    private Integer tbjtLeaveEntitled;

    private String tbeName;

    private Integer tbeId;

	
	private ViewEmployeeLeavesSummaryBeanService beanServices[];
	
	public ViewEmployeeLeavesSummaryBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(ViewEmployeeLeavesSummaryBeanService[] _beanServices) {
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


    public Integer getMinTbaleLeaveAvailable()
    {
		return minTbaleLeaveAvailable;
    }

    public void setMinTbaleLeaveAvailable(Integer newVal)
    {
		this.minTbaleLeaveAvailable = newVal;
    }

    public void setMinTbaleLeaveAvailable(int newVal)
    {
        setMinTbaleLeaveAvailable(new Integer(newVal));
    }

    public Long getSumTbaleLeaveTaken()
    {
		return sumTbaleLeaveTaken;
    }

    public void setSumTbaleLeaveTaken(Long newVal)
    {
		this.sumTbaleLeaveTaken = newVal;
    }

    public void setSumTbaleLeaveTaken(long newVal)
    {
        setSumTbaleLeaveTaken(new Long(newVal));
    }

    public Integer getTbaleStatus()
    {
		return tbaleStatus;
    }

    public void setTbaleStatus(Integer newVal)
    {
		this.tbaleStatus = newVal;
    }

    public void setTbaleStatus(int newVal)
    {
        setTbaleStatus(new Integer(newVal));
    }

    public Integer getTbjtLeaveEntitled()
    {
		return tbjtLeaveEntitled;
    }

    public void setTbjtLeaveEntitled(Integer newVal)
    {
		this.tbjtLeaveEntitled = newVal;
    }

    public void setTbjtLeaveEntitled(int newVal)
    {
        setTbjtLeaveEntitled(new Integer(newVal));
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
