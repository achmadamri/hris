package com.gwt.hris.server.service.pim;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.TbPphBeanModel;
import com.gwt.hris.client.service.pim.PphInterface;
import com.gwt.hris.db.TbPphBean;
import com.gwt.hris.db.TbPphManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class PphImpl extends MainRemoteServiceServlet implements PphInterface {
	
	private static final long serialVersionUID = -3373326569009469271L;

	public PagingLoadResult<TbPphBeanModel> getPphPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbPphBeanModel> list = new ArrayList<TbPphBeanModel>();

		TbPphBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}
			
			String strWhere = "where tbpph_dummy = 0 and " + searchBy + "=" + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = TbPphManager.getInstance().countAll();
			} else {
				size = TbPphManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbPphManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbPphBeanModel data = TbPphManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbPphBeanModel data = new TbPphBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbPphBeanModel>(list, config.getOffset(), size);
	}
}
