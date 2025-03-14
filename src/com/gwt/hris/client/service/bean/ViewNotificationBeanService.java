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

public class ViewNotificationBeanService
{
	private static final long serialVersionUID = -8589361042192898839L;
	
    private java.sql.Timestamp tbnCreateDate;

    private java.sql.Timestamp tbnReadDate;

    private java.sql.Timestamp tbnSentDate;

    private String tbnData;

    private String tbcdOtherEmailTo;

    private String tbeEmailTo;

    private String tbeNameTo;

    private Integer tbeIdTo;

    private String tbcdOtherEmailFrom;

    private String tbeEmailFrom;

    private String tbeNameFrom;

    private Integer tbeIdFrom;

    private String tbnuName;

    private Integer tbnuId;

    private String tbntSubject;

    private Integer tbntId;

    private String tbncName;

    private Integer tbncId;

    private Integer tbnId;

	
	private ViewNotificationBeanService beanServices[];
	
	public ViewNotificationBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(ViewNotificationBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public java.sql.Timestamp getTbnCreateDate()
    {
		return tbnCreateDate;
    }

    public void setTbnCreateDate(java.sql.Timestamp newVal)
    {
		this.tbnCreateDate = newVal;
    }

    public void setTbnCreateDate(long newVal)
    {
        setTbnCreateDate(new java.sql.Timestamp(newVal));
    }

    public java.sql.Timestamp getTbnReadDate()
    {
		return tbnReadDate;
    }

    public void setTbnReadDate(java.sql.Timestamp newVal)
    {
		this.tbnReadDate = newVal;
    }

    public void setTbnReadDate(long newVal)
    {
        setTbnReadDate(new java.sql.Timestamp(newVal));
    }

    public java.sql.Timestamp getTbnSentDate()
    {
		return tbnSentDate;
    }

    public void setTbnSentDate(java.sql.Timestamp newVal)
    {
		this.tbnSentDate = newVal;
    }

    public void setTbnSentDate(long newVal)
    {
        setTbnSentDate(new java.sql.Timestamp(newVal));
    }

    public String getTbnData()
    {
		return tbnData;
    }

    public void setTbnData(String newVal)
    {
		this.tbnData = newVal;
    }


    public String getTbcdOtherEmailTo()
    {
		return tbcdOtherEmailTo;
    }

    public void setTbcdOtherEmailTo(String newVal)
    {
		this.tbcdOtherEmailTo = newVal;
    }


    public String getTbeEmailTo()
    {
		return tbeEmailTo;
    }

    public void setTbeEmailTo(String newVal)
    {
		this.tbeEmailTo = newVal;
    }


    public String getTbeNameTo()
    {
		return tbeNameTo;
    }

    public void setTbeNameTo(String newVal)
    {
		this.tbeNameTo = newVal;
    }


    public Integer getTbeIdTo()
    {
		return tbeIdTo;
    }

    public void setTbeIdTo(Integer newVal)
    {
		this.tbeIdTo = newVal;
    }

    public void setTbeIdTo(int newVal)
    {
        setTbeIdTo(new Integer(newVal));
    }

    public String getTbcdOtherEmailFrom()
    {
		return tbcdOtherEmailFrom;
    }

    public void setTbcdOtherEmailFrom(String newVal)
    {
		this.tbcdOtherEmailFrom = newVal;
    }


    public String getTbeEmailFrom()
    {
		return tbeEmailFrom;
    }

    public void setTbeEmailFrom(String newVal)
    {
		this.tbeEmailFrom = newVal;
    }


    public String getTbeNameFrom()
    {
		return tbeNameFrom;
    }

    public void setTbeNameFrom(String newVal)
    {
		this.tbeNameFrom = newVal;
    }


    public Integer getTbeIdFrom()
    {
		return tbeIdFrom;
    }

    public void setTbeIdFrom(Integer newVal)
    {
		this.tbeIdFrom = newVal;
    }

    public void setTbeIdFrom(int newVal)
    {
        setTbeIdFrom(new Integer(newVal));
    }

    public String getTbnuName()
    {
		return tbnuName;
    }

    public void setTbnuName(String newVal)
    {
		this.tbnuName = newVal;
    }


    public Integer getTbnuId()
    {
		return tbnuId;
    }

    public void setTbnuId(Integer newVal)
    {
		this.tbnuId = newVal;
    }

    public void setTbnuId(int newVal)
    {
        setTbnuId(new Integer(newVal));
    }

    public String getTbntSubject()
    {
		return tbntSubject;
    }

    public void setTbntSubject(String newVal)
    {
		this.tbntSubject = newVal;
    }


    public Integer getTbntId()
    {
		return tbntId;
    }

    public void setTbntId(Integer newVal)
    {
		this.tbntId = newVal;
    }

    public void setTbntId(int newVal)
    {
        setTbntId(new Integer(newVal));
    }

    public String getTbncName()
    {
		return tbncName;
    }

    public void setTbncName(String newVal)
    {
		this.tbncName = newVal;
    }


    public Integer getTbncId()
    {
		return tbncId;
    }

    public void setTbncId(Integer newVal)
    {
		this.tbncId = newVal;
    }

    public void setTbncId(int newVal)
    {
        setTbncId(new Integer(newVal));
    }

    public Integer getTbnId()
    {
		return tbnId;
    }

    public void setTbnId(Integer newVal)
    {
		this.tbnId = newVal;
    }

    public void setTbnId(int newVal)
    {
        setTbnId(new Integer(newVal));
    }

}
