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

package com.gwt.hris.db.exception;

import java.sql.SQLException;

/**
 * @author sql2java
 */
public class DAOException extends SQLException {
	private static final long serialVersionUID = 5165438223391151142L;

	/**
	 * contructor
	 */
	public DAOException() {
		super();
	}

	/**
	 * contructor
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * contructor
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}

	/**
	 * contructor
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
