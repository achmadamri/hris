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

public class TbImmigrationBeanService
{
	private static final long serialVersionUID = -3529719179698283196L;
	
    private String tbiComments;

    private java.sql.Timestamp tbiExpiryDate;

    private java.sql.Timestamp tbiIssuedDate;

    private Integer tbnId;

    private java.sql.Timestamp tbiL9ReviewDate;

    private String tbiL9Status;

    private String tbiImmigrationNo;

    private Integer tbiImmigrationType;

    private Integer tbeId;

	
	private TbImmigrationBeanService beanServices[];
	
	public TbImmigrationBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(TbImmigrationBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public String getTbiComments()
    {
		return tbiComments;
    }

    public void setTbiComments(String newVal)
    {
		this.tbiComments = newVal;
    }


    public java.sql.Timestamp getTbiExpiryDate()
    {
		return tbiExpiryDate;
    }

    public void setTbiExpiryDate(java.sql.Timestamp newVal)
    {
		this.tbiExpiryDate = newVal;
    }

    public void setTbiExpiryDate(long newVal)
    {
        setTbiExpiryDate(new java.sql.Timestamp(newVal));
    }

    public java.sql.Timestamp getTbiIssuedDate()
    {
		return tbiIssuedDate;
    }

    public void setTbiIssuedDate(java.sql.Timestamp newVal)
    {
		this.tbiIssuedDate = newVal;
    }

    public void setTbiIssuedDate(long newVal)
    {
        setTbiIssuedDate(new java.sql.Timestamp(newVal));
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

    public java.sql.Timestamp getTbiL9ReviewDate()
    {
		return tbiL9ReviewDate;
    }

    public void setTbiL9ReviewDate(java.sql.Timestamp newVal)
    {
		this.tbiL9ReviewDate = newVal;
    }

    public void setTbiL9ReviewDate(long newVal)
    {
        setTbiL9ReviewDate(new java.sql.Timestamp(newVal));
    }

    public String getTbiL9Status()
    {
		return tbiL9Status;
    }

    public void setTbiL9Status(String newVal)
    {
		this.tbiL9Status = newVal;
    }


    public String getTbiImmigrationNo()
    {
		return tbiImmigrationNo;
    }

    public void setTbiImmigrationNo(String newVal)
    {
		this.tbiImmigrationNo = newVal;
    }


    public Integer getTbiImmigrationType()
    {
		return tbiImmigrationType;
    }

    public void setTbiImmigrationType(Integer newVal)
    {
		this.tbiImmigrationType = newVal;
    }

    public void setTbiImmigrationType(int newVal)
    {
        setTbiImmigrationType(new Integer(newVal));
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
