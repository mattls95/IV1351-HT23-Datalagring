SELECT instructor_id,c.first_name AS "First Name",c.last_name as "Last Name", COUNT(*) as "No of Lessons" FROM lesson A
INNER JOIN instructor B ON A.instructor_id = B.id
INNER JOIN person C ON B.person_id = C.id
WHERE EXTRACT(YEAR FROM time) = '2023'
AND EXTRACT(MONTH FROM time) = date_part('month', (SELECT current_timestamp)) 
GROUP BY instructor_id, c.first_name,c.last_name  
HAVING COUNT(*) > 1
ORDER BY COUNT(*) DESC;