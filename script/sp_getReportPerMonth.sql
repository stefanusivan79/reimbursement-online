CREATE PROCEDURE sp_getReportPerMonth(IN year_month_report VARCHAR(30))
BEGIN
	SET @row_number = 0;

	SELECT (@row_number := @row_number + 1) AS 'id', e.full_name, SUM(r.amount) AS 'total'
	FROM reimbursements r
	LEFT JOIN employee e ON r.employee_id = e.id
	WHERE EXTRACT(YEAR_MONTH FROM r.date_purchase) = year_month_report AND r.status = 'COMPLETED'
	GROUP BY e.full_name;
END