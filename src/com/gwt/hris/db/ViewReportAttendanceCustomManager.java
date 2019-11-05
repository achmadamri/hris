package com.gwt.hris.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.db.exception.ObjectRetrievalException;

public class ViewReportAttendanceCustomManager extends ViewReportAttendanceManager {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static ViewReportAttendanceCustomManager singleton = new ViewReportAttendanceCustomManager();

	public static ViewReportAttendanceCustomManager getInstance() {
		return singleton;
	}

	public ViewReportAttendanceBean[] loadByCustom(String strYearMonth) throws DAOException {
		List<ViewReportAttendanceBean> lst = new ArrayList<ViewReportAttendanceBean>();
		TbEmployeeBean tbEmployeeBeans[] = TbEmployeeManager.getInstance().loadAll();
		for (TbEmployeeBean tbEmployeeBean : tbEmployeeBeans) {
			ViewReportAttendanceBean viewReportAttendanceBeans[] = loadBy(tbEmployeeBean.getTbeId(), strYearMonth);
			for (ViewReportAttendanceBean viewReportAttendanceBean : viewReportAttendanceBeans) {
				if (viewReportAttendanceBean.getTbeName() == null) {
					viewReportAttendanceBean.setTbeName(tbEmployeeBean.getTbeName());
				}
				lst.add(viewReportAttendanceBean);
			}
		}

		ViewReportAttendanceBean viewReportAttendanceBeans[] = new ViewReportAttendanceBean[lst.size()];
		for (int i = 0; i < lst.size(); i++) {
			viewReportAttendanceBeans[i] = ViewReportAttendanceManager.getInstance().createViewReportAttendanceBean();
			viewReportAttendanceBeans[i] = lst.get(i);
		}

		return viewReportAttendanceBeans;
	}

	private ViewReportAttendanceBean[] loadBy(int intTbeId, String strYearMonth) throws DAOException {
		String strQuery = "";
		strQuery += "SELECT count(a.tbs_name) AS count_tbs_name,";
		strQuery += "a.tbs_name,";
		strQuery += "tba_date,";
		strQuery += "a.tbe_name";
		strQuery += "  FROM (SELECT view_attendance.tbs_name,";
		strQuery += " NULL AS tbe_name,";
		strQuery += " date_format(view_attendance.tba_date, '%Y-%m') AS tba_date";
		strQuery += "   FROM view_attendance";
		strQuery += "  WHERE view_attendance.tbe_id = " + intTbeId;
		strQuery += " AND view_attendance.tba_date LIKE '" + strYearMonth + "%'";
		strQuery += " UNION ALL";
		strQuery += " SELECT tb_shift.tbs_name,";
		strQuery += " NULL AS tbe_name,";
		strQuery += " date_format(tb_calendar.tbc_date, '%Y-%m') AS tba_date";
		strQuery += "   FROM tb_shift tb_shift";
		strQuery += " CROSS JOIN (   tb_calendar tb_calendar";
		strQuery += "      LEFT OUTER JOIN";
		strQuery += "  (SELECT *";
		strQuery += "     FROM tb_employee_shift";
		strQuery += "    WHERE tb_employee_shift.tbe_id = " + intTbeId + ") tb_employee_shift";
		strQuery += "      ON (tb_calendar.tbc_date =";
		strQuery += "      tb_employee_shift.tbes_date))";
		strQuery += " LEFT OUTER JOIN (SELECT *";
		strQuery += "      FROM tb_attendance";
		strQuery += "     WHERE tb_attendance.tbe_id = " + intTbeId + ") tb_attendance";
		strQuery += "    ON (tb_calendar.tbc_date = tb_attendance.tba_date)";
		strQuery += "  WHERE (tb_shift.tbs_id = 3)";
		strQuery += " AND (tb_calendar.tbc_date LIKE '" + strYearMonth + "%')";
		strQuery += " AND (tb_employee_shift.tbes_date IS NULL";
		strQuery += "      AND tb_attendance.tba_date IS NULL)";
		strQuery += " UNION ALL";
		strQuery += " SELECT tb_shift.tbs_name,";
		strQuery += " tb_employee.tbe_name,";
		strQuery += " date_format(tb_employee_shift.tbes_date, '%Y-%m') AS tba_date";
		strQuery += "   FROM    (   (   tb_employee_shift tb_employee_shift";
		strQuery += "  INNER JOIN";
		strQuery += "     tb_employee tb_employee";
		strQuery += "  ON (tb_employee_shift.tbe_id = tb_employee.tbe_id))";
		strQuery += "     INNER JOIN";
		strQuery += " tb_shift tb_shift";
		strQuery += "     ON (tb_employee_shift.tbs_id = tb_shift.tbs_id))";
		strQuery += " LEFT OUTER JOIN";
		strQuery += "    tb_attendance tb_attendance";
		strQuery += " ON (tb_employee_shift.tbes_date = tb_attendance.tba_date)";
		strQuery += "  WHERE (    tb_employee.tbe_id = " + intTbeId;
		strQuery += "  AND tb_attendance.tba_date IS NULL";
		strQuery += "  AND tb_employee_shift.tbes_date LIKE '" + strYearMonth + "%')) a ";
		strQuery += "GROUP BY a.tbe_name, a.tba_date, a.tbs_name";

		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			log.debug("loadBy: " + strQuery.toString());
			c = this.getConnection();
			st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(strQuery);
			return this.decodeResultSet(rs, null, 1, -1);
		} catch (SQLException e) {
			throw new ObjectRetrievalException(e);
		} finally {
			this.getManager().close(st, rs);
			this.freeConnection(c);
		}
	}
}
