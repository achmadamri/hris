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

public class ViewEmployeeLicensesBeanService
{
	private static final long serialVersionUID = -3539634083293355260L;
	
    private java.sql.Timestamp tbalEndDate;

    private java.sql.Timestamp tbalStartDate;

    private Integer tbalId;

    private String tblDescription;

    private String tblName;

    private String tblLicensesId;

    private Integer tblId;

    private String tbeName;

    private String tbeNickName;

    private String tbeLastName;

    private String tbeMiddleName;

    private String tbeFirstName;

    private String tbeEmployeeId;

    private Integer tbeId;

	
	private ViewEmployeeLicensesBeanService beanServices[];
	
	public ViewEmployeeLicensesBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(ViewEmployeeLicensesBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public java.sql.Timestamp getTbalEndDate()
    {
		return tbalEndDate;
    }

    public void setTbalEndDate(java.sql.Timestamp newVal)
    {
		this.tbalEndDate = newVal;
    }

    public void setTbalEndDate(long newVal)
    {
        setTbalEndDate(new java.sql.Timestamp(newVal));
    }

    public java.sql.Timestamp getTbalStartDate()
    {
		return tbalStartDate;
    }

    public void setTbalStartDate(java.sql.Timestamp newVal)
    {
		this.tbalStartDate = newVal;
    }

    public void setTbalStartDate(long newVal)
    {
        setTbalStartDate(new java.sql.Timestamp(newVal));
    }

    public Integer getTbalId()
    {
		return tbalId;
    }

    public void setTbalId(Integer newVal)
    {
		this.tbalId = newVal;
    }

    public void setTbalId(int newVal)
    {
        setTbalId(new Integer(newVal));
    }

    public String getTblDescription()
    {
		return tblDescription;
    }

    public void setTblDescription(String newVal)
    {
		this.tblDescription = newVal;
    }


    public String getTblName()
    {
		return tblName;
    }

    public void setTblName(String newVal)
    {
		this.tblName = newVal;
    }


    public String getTblLicensesId()
    {
		return tblLicensesId;
    }

    public void setTblLicensesId(String newVal)
    {
		this.tblLicensesId = newVal;
    }


    public Integer getTblId()
    {
		return tblId;
    }

    public void setTblId(Integer newVal)
    {
		this.tblId = newVal;
    }

    public void setTblId(int newVal)
    {
        setTblId(new Integer(newVal));
    }

    public String getTbeName()
    {
		return tbeName;
    }

    public void setTbeName(String newVal)
    {
		this.tbeName = newVal;
    }


    public String getTbeNickName()
    {
		return tbeNickName;
    }

    public void setTbeNickName(String newVal)
    {
		this.tbeNickName = newVal;
    }


    public String getTbeLastName()
    {
		return tbeLastName;
    }

    public void setTbeLastName(String newVal)
    {
		this.tbeLastName = newVal;
    }


    public String getTbeMiddleName()
    {
		return tbeMiddleName;
    }

    public void setTbeMiddleName(String newVal)
    {
		this.tbeMiddleName = newVal;
    }


    public String getTbeFirstName()
    {
		return tbeFirstName;
    }

    public void setTbeFirstName(String newVal)
    {
		this.tbeFirstName = newVal;
    }


    public String getTbeEmployeeId()
    {
		return tbeEmployeeId;
    }

    public void setTbeEmployeeId(String newVal)
    {
		this.tbeEmployeeId = newVal;
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
