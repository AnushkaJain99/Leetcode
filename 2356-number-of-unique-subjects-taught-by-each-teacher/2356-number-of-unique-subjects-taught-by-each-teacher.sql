# Write your MySQL query statement below
select teacher_id, Count(distinct subject_id) AS cnt from teacher group by teacher_id;