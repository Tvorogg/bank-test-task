--Получить количество неактивных (не было сессий) клиентов

SELECT COUNT(u.userid) AS inactive_clients
FROM users u
LEFT JOIN usersessions us ON u.userid = us.userid
WHERE us.sessionid IS NULL;

--Получить список уникальных UserId активных пользователей, которые не пользовались каналом 1
SELECT DISTINCT u.userid
FROM users u
INNER JOIN usersessions us ON u.userid = us.userid
WHERE us.channeltype != 1;

--Получить максимальный UserId активного пользователя, статус которого также не равен 1
SELECT MAX(u.userid) AS max_active_userid
FROM users u
INNER JOIN usersessions us ON u.userid = us.userid
WHERE u.status != 1;

--Получить список количества сессий с разделением на MobAppVersion
SELECT us.mobappversion, COUNT(*) AS session_count
FROM usersessions us
WHERE us.mobosversion < 80 OR us.mobosversion IS NULL
GROUP BY us.mobappversion;