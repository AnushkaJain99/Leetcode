# Write your MySQL query statement below
select user_id, COUNT(follower_id) AS followers_count from Followers GROUP BY user_id ORDER BY user_id;