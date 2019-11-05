package com.gwt.hris.server.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gwt.hris.db.Manager;

public class QueryManager {
	private static QueryManager instance = new QueryManager();

	public static QueryManager getInstance() {
		return instance;
	}

	public boolean approveTbAssignedLeaves(int tbaleLeaveAvailable, int tbeId) throws SQLException {
		String strSQL = "update tb_assigned_leaves set tbale_leave_available = " + tbaleLeaveAvailable + " where tbe_id = " + tbeId;

		return query(strSQL);
	}

	public boolean updateTbAssignedLeaves() throws SQLException {
		String strSQL = "";
		strSQL += "INSERT INTO tb_assigned_leaves ";
		strSQL += " SELECT a.tbale_id,";
		strSQL += " a.tbe_id,";
		strSQL += " b.tblt_id,";
		strSQL += " a.tbale_created_time,";
		strSQL += " a.tbale_updated_time,";
		strSQL += " a.tbale_start_date,";
		strSQL += " a.tbale_end_date,";
		strSQL += " a.tbale_comments,";
		strSQL += " a.tbale_status,";
		strSQL += " a.tbale_leave_taken,";
		strSQL += " a.tbale_leave_available";
		strSQL += " FROM (SELECT NULL tbale_id,";
		strSQL += " tbe_id,";
		strSQL += " 0 tblt_id,";
		strSQL += " now() tbale_created_time,";
		strSQL += " NULL tbale_updated_time,";
		strSQL += " NULL tbale_start_date,";
		strSQL += " NULL tbale_end_date,";
		strSQL += " NULL tbale_comments,";
		strSQL += " 1 tbale_status,";
		strSQL += " 0 tbale_leave_taken,";
		strSQL += " NULL tbale_leave_available";
		strSQL += " FROM tb_employee) a";
		strSQL += " JOIN tb_leave_types b";
		strSQL += " ON a.tblt_id <> b.tblt_id";
		strSQL += " WHERE a.tbe_id NOT IN (SELECT tbe_id FROM tb_assigned_leaves)";

		return query(strSQL);
	}

	public boolean query(String strSQL) throws SQLException {
		boolean returnValue = false;

		Connection c = Manager.getInstance().getConnection();
		PreparedStatement ps = c.prepareStatement(strSQL);

		returnValue = ps.execute();

		Manager.getInstance().close(ps);
		Manager.getInstance().releaseConnection(c);

		return returnValue;
	}
}
