SELECT a.tbe_name,
       tba_date,
       a.tbs_name,
       count(a.tbs_name) AS count_tbs_name
  FROM (SELECT view_attendance.tbs_name,
               view_attendance.tbe_name,
               date_format(view_attendance.tba_date, '%Y-%m') AS tba_date
          FROM view_attendance
         WHERE view_attendance.tbe_id = 2
               AND view_attendance.tba_date LIKE '2012-07%'
        UNION ALL
        SELECT tb_shift.tbs_name,
               NULL AS tbe_name,
               date_format(tb_calendar.tbc_date, '%Y-%m') AS tba_date
          FROM tb_shift tb_shift
               CROSS JOIN (   tb_calendar tb_calendar
                           LEFT OUTER JOIN
                              (SELECT *
                                 FROM tb_employee_shift
                                WHERE tb_employee_shift.tbe_id = 2) tb_employee_shift
                           ON (tb_calendar.tbc_date =
                                  tb_employee_shift.tbes_date))
               LEFT OUTER JOIN (SELECT *
                                  FROM tb_attendance
                                 WHERE tb_attendance.tbe_id = 2) tb_attendance
                  ON (tb_calendar.tbc_date = tb_attendance.tba_date)
         WHERE (tb_shift.tbs_id = 3)
               AND (tb_calendar.tbc_date LIKE '2012-07%')
               AND (tb_employee_shift.tbes_date IS NULL
                    AND tb_attendance.tba_date IS NULL)
        UNION ALL
        SELECT tb_shift.tbs_name,
               tb_employee.tbe_name,
               date_format(tb_employee_shift.tbes_date, '%Y-%m') AS tba_date
          FROM    (   (   tb_employee_shift tb_employee_shift
                       INNER JOIN
                          tb_employee tb_employee
                       ON (tb_employee_shift.tbe_id = tb_employee.tbe_id))
                   INNER JOIN
                      tb_shift tb_shift
                   ON (tb_employee_shift.tbs_id = tb_shift.tbs_id))
               LEFT OUTER JOIN
                  tb_attendance tb_attendance
               ON (tb_employee_shift.tbes_date = tb_attendance.tba_date)
         WHERE (    tb_employee.tbe_id = 2
                AND tb_attendance.tba_date IS NULL
                AND tb_employee_shift.tbes_date LIKE '2012-07%')) a
GROUP BY a.tbe_name, a.tba_date, a.tbs_name