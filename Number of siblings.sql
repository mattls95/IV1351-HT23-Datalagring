SELECT number_of_siblings as "No of Siblings",count(*) as "No of Students" FROM (
	SELECT COUNT(sibling_id) AS number_of_siblings FROM student
		FULL JOIN student_sibling on id=student_id
		Group BY id
		ORDER BY id ASC 
		) as all_siblings
GROUP BY number_of_siblings
ORDER BY number_of_siblings