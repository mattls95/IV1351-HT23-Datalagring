SELECT 
CASE
	WHEN "Month" = 1 THEN 'January'
	WHEN "Month" = 2 THEN 'February'
	WHEN "Month" = 3 THEN 'March'
	WHEN "Month" = 4 THEN 'April'
	WHEN "Month" = 5 THEN 'May'
	WHEN "Month" = 6 THEN 'June'
	WHEN "Month" = 7 THEN 'July'
	WHEN "Month" = 8 THEN 'August'
	WHEN "Month" = 9 THEN 'September'
	WHEN "Month" = 10 THEN 'October'
	WHEN "Month" = 11 THEN 'November'
	WHEN "Month" = 12 THEN 'December'
END AS "Month",
"Total", "Individual", "Group","Ensemble" 
FROM(
	SELECT EXTRACT(MONTH FROM time) AS "Month", COUNT(*) AS "Total",
	SUM(CASE WHEN lesson_type = 'Single lesson' THEN 1 ELSE 0 END) AS "Individual",
	SUM(CASE WHEN lesson_type = 'Group lesson' THEN 1 ELSE 0 END) AS "Group",
	SUM(CASE WHEN lesson_type = 'Ensemble' THEN 1 ELSE 0 END) AS "Ensemble"
	FROM lesson WHERE EXTRACT(YEAR FROM time) = '2023' 
	GROUP BY EXTRACT(MONTH FROM time)
	ORDER BY EXTRACT(MONTH FROM time)) AS "Number of lessons"
