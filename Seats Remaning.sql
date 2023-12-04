SELECT TO_CHAR(Weekday, 'Dy') as "Day",genre as "Genre",
CASE
	WHEN seats_remaning = max_students THEN 'No Seats'
	WHEN seats_remaning = max_students - 1 OR seats_remaning = max_students - 2 THEN '1 or 2 seats left'
	ELSE 'Many seats'
END AS "No of Free Seats"
FROM(SELECT lesson_id, COUNT(lesson_id) AS seats_remaning,max_students,genre,Weekday
	 FROM (SELECT a.student_id,a.lesson_id,genre,max_students,C.time AS Weekday FROM student_lesson A
		INNER JOIN ensemble B ON A.lesson_id = B.lesson_id
		INNER JOIN lesson C ON A.lesson_id = C.id
		WHERE Extract(WEEK from C.time) = Extract(WEEK from now() + interval '1 week')
		ORDER BY student_id ASC, B.lesson_id ASC) AS ensembles_in_next_week 
		GROUP BY max_students,lesson_id,genre,Weekday) 
		AS unique_ensembles_in_next_week
ORDER BY Weekday