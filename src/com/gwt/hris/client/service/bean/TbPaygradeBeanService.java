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

public class TbPaygradeBeanService
{
	private static final long serialVersionUID = -3536329116859987004L;
	
    private String tbpName;

    private String tbpPaygradeId;

    private Integer tbpId;

	
	private TbPaygradeBeanService beanServices[];
	
	public TbPaygradeBeanService[] getServices() {
		return beanServices;
	}

	public void setServices(TbPaygradeBeanService[] _beanServices) {
		this.beanServices = _beanServices;
	}

    public String getTbpName()
    {
		return tbpName;
    }

    public void setTbpName(String newVal)
    {
		this.tbpName = newVal;
    }


    public String getTbpPaygradeId()
    {
		return tbpPaygradeId;
    }

    public void setTbpPaygradeId(String newVal)
    {
		this.tbpPaygradeId = newVal;
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
