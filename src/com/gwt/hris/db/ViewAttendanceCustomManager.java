package com.gwt.hris.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.db.exception.DataAccessException;
import com.gwt.hris.db.exception.ObjectRetrievalException;

public class ViewAttendanceCustomManager extends ViewAttendanceManager {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static ViewAttendanceCustomManager singleton = new ViewAttendanceCustomManager();

	public static ViewAttendanceCustomManager getInstance() {
		return singleton;
	}

	public int countWhereUnion(int tbeId, String strYearMonth) throws DAOException {
		String strQuery = "SELECT COUNT(*) AS MCOUNT FROM (";
		strQuery += " SELECT view_attendance.tbs_out_time,";
		strQuery += " view_attendance.tbs_in_time,";
		strQuery += " view_attendance.tbs_name,";
		strQuery += " view_attendance.tbs_shift_id,";
		strQuery += " view_attendance.tbs_id,";
		strQuery += " view_attendance.tba_out_photo,";
		strQuery += " view_attendance.tba_in_photo,";
		strQuery += " view_attendance.tba_out_latitude,";
		strQuery += " view_attendance.tba_out_longitude,";
		strQuery += " view_attendance.tba_in_latitude,";
		strQuery += " view_attendance.tba_in_longitude,";
		strQuery += " view_attendance.tba_out_time_diff,";
		strQuery += " view_attendance.tba_in_time_diff,";
		strQuery += " view_attendance.tba_out_note,";
		strQuery += " view_attendance.tba_in_note,";
		strQuery += " view_attendance.tba_out_time,";
		strQuery += " view_attendance.tba_in_time,";
		strQuery += " view_attendance.tba_date,";
		strQuery += " view_attendance.tbe_name,";
		strQuery += " view_attendance.tbe_id";
		strQuery += " FROM view_attendance";
		strQuery += " WHERE view_attendance.tbe_id = " + tbeId + " AND view_attendance.tba_date >= (SELECT tbe_joined_date FROM tb_employee WHERE tbe_id = " + tbeId + ")";
		strQuery += " AND view_attendance.tba_date LIKE '" + strYearMonth + "%'";
		strQuery += " UNION";
		strQuery += " SELECT tb_shift.tbs_out_time,";
		strQuery += " tb_shift.tbs_in_time,";
		strQuery += " tb_shift.tbs_name,";
		strQuery += " tb_shift.tbs_shift_id,";
		strQuery += " tb_shift.tbs_id,";
		strQuery += " NULL AS tba_out_photo,";
		strQuery += " NULL AS tba_in_photo,";
		strQuery += " NULL AS tba_out_latitude,";
		strQuery += " NULL AS tba_out_longitude,";
		strQuery += " NULL AS tba_in_latitude,";
		strQuery += " NULL AS tba_in_longitude,";
		strQuery += " NULL AS tba_out_time_diff,";
		strQuery += " NULL AS tba_in_time_diff,";
		strQuery += " NULL AS tba_out_note,";
		strQuery += " NULL AS tba_in_note,";
		strQuery += " NULL AS tba_out_time,";
		strQuery += " NULL AS tba_in_time,";
		strQuery += " tb_calendar.tbc_date AS tba_date,";
		strQuery += " NULL AS tbe_name,";
		strQuery += " NULL AS tbe_id";
		strQuery += " FROM tb_shift tb_shift";
		strQuery += " CROSS JOIN ( tb_calendar tb_calendar";
		strQuery += " LEFT OUTER JOIN";
		strQuery += " (SELECT *";
		strQuery += " FROM tb_employee_shift";
		strQuery += " WHERE tb_employee_shift.tbe_id = " + tbeId + ") tb_employee_shift";
		strQuery += " ON (tb_calendar.tbc_date = tb_employee_shift.tbes_date))";
		strQuery += " LEFT OUTER JOIN (SELECT *";
		strQuery += " FROM tb_attendance";
		strQuery += " WHERE tb_attendance.tbe_id = " + tbeId + ") tb_attendance";
		strQuery += " ON (tb_calendar.tbc_date = tb_attendance.tba_date)";
		strQuery += " WHERE (tb_shift.tbs_id = 3) AND (tb_calendar.tbc_date LIKE '" + strYearMonth + "%')";
		strQuery += " AND (tb_employee_shift.tbes_date IS NULL";
		strQuery += " AND tb_attendance.tba_date IS NULL)";
		strQuery += " UNION";
		strQuery += " SELECT tb_shift.tbs_out_time,";
		strQuery += " tb_shift.tbs_in_time,";
		strQuery += " tb_shift.tbs_name,";
		strQuery += " tb_shift.tbs_shift_id,";
		strQuery += " tb_shift.tbs_id,";
		strQuery += " NULL AS tba_out_photo,";
		strQuery += " NULL AS tba_in_photo,";
		strQuery += " NULL AS tba_out_latitude,";
		strQuery += " NULL AS tba_out_longitude,";
		strQuery += " NULL AS tba_in_latitude,";
		strQuery += " NULL AS tba_in_longitude,";
		strQuery += " NULL AS tba_out_time_diff,";
		strQuery += " NULL AS tba_in_time_diff,";
		strQuery += " NULL AS tba_out_note,";
		strQuery += " NULL AS tba_in_note,";
		strQuery += " NULL AS tba_out_time,";
		strQuery += " NULL AS tba_in_time,";
		strQuery += " tb_employee_shift.tbes_date AS tba_date,";
		strQuery += " tb_employee.tbe_name,";
		strQuery += " tb_employee.tbe_id";
		strQuery += " FROM ( ( tb_employee_shift tb_employee_shift";
		strQuery += " INNER JOIN";
		strQuery += " tb_employee tb_employee";
		strQuery += " ON (tb_employee_shift.tbe_id = tb_employee.tbe_id))";
		strQuery += " INNER JOIN";
		strQuery += " tb_shift tb_shift";
		strQuery += " ON (tb_employee_shift.tbs_id = tb_shift.tbs_id))";
		strQuery += " LEFT OUTER JOIN";
		strQuery += " tb_attendance tb_attendance";
		strQuery += " ON (tb_employee_shift.tbes_date = tb_attendance.tba_date)";
		strQuery += " WHERE ( tb_employee.tbe_id = " + tbeId + "";
		strQuery += " AND tb_attendance.tba_date IS NULL";
		strQuery += " AND tb_employee_shift.tbes_date LIKE '" + strYearMonth + "%')";
		strQuery += " ORDER BY tba_date";
		strQuery += " ) a";

		Connection c = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			int iReturn = -1;
			log.debug("countWhereUnion: " + strQuery.toString());
			c = this.getConnection();
			st = c.createStatement();
			rs = st.executeQuery(strQuery);
			if (rs.next()) {
				iReturn = rs.getInt("MCOUNT");
			}
			if (iReturn != -1) {
				return iReturn;
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			this.getManager().close(st, rs);
			this.freeConnection(c);
			strQuery = null;
		}
		throw new DataAccessException("Error in countWhere where=[" + strQuery + "]");
	}

	public ViewAttendanceBean[] loadByWhereUnion(int tbeId, String strYearMonth) throws DAOException {
		String strQuery = "";
		strQuery += " SELECT view_attendance.tbs_out_time,";
		strQuery += " view_attendance.tbs_in_time,";
		strQuery += " view_attendance.tbs_name,";
		strQuery += " view_attendance.tbs_shift_id,";
		strQuery += " view_attendance.tbs_id,";
		strQuery += " view_attendance.tba_out_photo,";
		strQuery += " view_attendance.tba_in_photo,";
		strQuery += " view_attendance.tba_out_latitude,";
		strQuery += " view_attendance.tba_out_longitude,";
		strQuery += " view_attendance.tba_in_latitude,";
		strQuery += " view_attendance.tba_in_longitude,";
		strQuery += " view_attendance.tba_out_time_diff,";
		strQuery += " view_attendance.tba_in_time_diff,";
		strQuery += " view_attendance.tba_out_note,";
		strQuery += " view_attendance.tba_in_note,";
		strQuery += " view_attendance.tba_out_time,";
		strQuery += " view_attendance.tba_in_time,";
		strQuery += " view_attendance.tba_date,";
		strQuery += " view_attendance.tbe_name,";
		strQuery += " view_attendance.tbe_id";
		strQuery += " FROM view_attendance";
		strQuery += " WHERE view_attendance.tbe_id = " + tbeId + " AND view_attendance.tba_date >= (SELECT tbe_joined_date FROM tb_employee WHERE tbe_id = " + tbeId + ")";
		strQuery += " AND view_attendance.tba_date LIKE '" + strYearMonth + "%'";
		strQuery += " UNION";
		strQuery += " SELECT tb_shift.tbs_out_time,";
		strQuery += " tb_shift.tbs_in_time,";
		strQuery += " tb_shift.tbs_name,";
		strQuery += " tb_shift.tbs_shift_id,";
		strQuery += " tb_shift.tbs_id,";
		strQuery += " NULL AS tba_out_photo,";
		strQuery += " NULL AS tba_in_photo,";
		strQuery += " NULL AS tba_out_latitude,";
		strQuery += " NULL AS tba_out_longitude,";
		strQuery += " NULL AS tba_in_latitude,";
		strQuery += " NULL AS tba_in_longitude,";
		strQuery += " NULL AS tba_out_time_diff,";
		strQuery += " NULL AS tba_in_time_diff,";
		strQuery += " NULL AS tba_out_note,";
		strQuery += " NULL AS tba_in_note,";
		strQuery += " NULL AS tba_out_time,";
		strQuery += " NULL AS tba_in_time,";
		strQuery += " tb_calendar.tbc_date AS tba_date,";
		strQuery += " NULL AS tbe_name,";
		strQuery += " NULL AS tbe_id";
		strQuery += " FROM tb_shift tb_shift";
		strQuery += " CROSS JOIN ( tb_calendar tb_calendar";
		strQuery += " LEFT OUTER JOIN";
		strQuery += " (SELECT *";
		strQuery += " FROM tb_employee_shift";
		strQuery += " WHERE tb_employee_shift.tbe_id = " + tbeId + ") tb_employee_shift";
		strQuery += " ON (tb_calendar.tbc_date = tb_employee_shift.tbes_date))";
		strQuery += " LEFT OUTER JOIN (SELECT *";
		strQuery += " FROM tb_attendance";
		strQuery += " WHERE tb_attendance.tbe_id = " + tbeId + ") tb_attendance";
		strQuery += " ON (tb_calendar.tbc_date = tb_attendance.tba_date)";
		strQuery += " WHERE (tb_shift.tbs_id = 3) AND (tb_calendar.tbc_date LIKE '" + strYearMonth + "%')";
		strQuery += " AND (tb_employee_shift.tbes_date IS NULL";
		strQuery += " AND tb_attendance.tba_date IS NULL)";
		strQuery += " UNION";
		strQuery += " SELECT tb_shift.tbs_out_time,";
		strQuery += " tb_shift.tbs_in_time,";
		strQuery += " tb_shift.tbs_name,";
		strQuery += " tb_shift.tbs_shift_id,";
		strQuery += " tb_shift.tbs_id,";
		strQuery += " NULL AS tba_out_photo,";
		strQuery += " NULL AS tba_in_photo,";
		strQuery += " NULL AS tba_out_latitude,";
		strQuery += " NULL AS tba_out_longitude,";
		strQuery += " NULL AS tba_in_latitude,";
		strQuery += " NULL AS tba_in_longitude,";
		strQuery += " NULL AS tba_out_time_diff,";
		strQuery += " NULL AS tba_in_time_diff,";
		strQuery += " NULL AS tba_out_note,";
		strQuery += " NULL AS tba_in_note,";
		strQuery += " NULL AS tba_out_time,";
		strQuery += " NULL AS tba_in_time,";
		strQuery += " tb_employee_shift.tbes_date AS tba_date,";
		strQuery += " tb_employee.tbe_name,";
		strQuery += " tb_employee.tbe_id";
		strQuery += " FROM ( ( tb_employee_shift tb_employee_shift";
		strQuery += " INNER JOIN";
		strQuery += " tb_employee tb_employee";
		strQuery += " ON (tb_employee_shift.tbe_id = tb_employee.tbe_id))";
		strQuery += " INNER JOIN";
		strQuery += " tb_shift tb_shift";
		strQuery += " ON (tb_employee_shift.tbs_id = tb_shift.tbs_id))";
		strQuery += " LEFT OUTER JOIN";
		strQuery += " tb_attendance tb_attendance";
		strQuery += " ON (tb_employee_shift.tbes_date = tb_attendance.tba_date)";
		strQuery += " WHERE ( tb_employee.tbe_id = " + tbeId + "";
		strQuery += " AND tb_attendance.tba_date IS NULL";
		strQuery += " AND tb_employee_shift.tbes_date LIKE '" + strYearMonth + "%')";
		strQuery += " ORDER BY tba_date";

		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			log.debug("loadByWhereUnion: " + strQuery.toString());
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
