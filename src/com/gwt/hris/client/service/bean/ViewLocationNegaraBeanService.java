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

public class ViewLocationNegaraBeanService
{
	private static final long serialVersionUID = -3549548986888427325L;
	
    private String tbnNama;

    private Integer tbnId;

    private String tblName;

    private String tblLocationId;

    private Integer tblId;

    private String tbpName;

    private String tbpPerusahaanId;

    private Integer tbpId;

	
	private ViewLocationNegaraBeanService beanServices[];
	
	public ViewLocationNegaraBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(ViewLocationNegaraBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public String getTbnNama()
    {
		return tbnNama;
    }

    public void setTbnNama(String newVal)
    {
		this.tbnNama = newVal;
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

    public String getTblName()
    {
		return tblName;
    }

    public void setTblName(String newVal)
    {
		this.tblName = newVal;
    }


    public String getTblLocationId()
    {
		return tblLocationId;
    }

    public void setTblLocationId(String newVal)
    {
		this.tblLocationId = newVal;
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

    public String getTbpName()
    {
		return tbpName;
    }

    public void setTbpName(String newVal)
    {
		this.tbpName = newVal;
    }


    public String getTbpPerusahaanId()
    {
		return tbpPerusahaanId;
    }

    public void setTbpPerusahaanId(String newVal)
    {
		this.tbpPerusahaanId = newVal;
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

}
