get All Meals
curl -s http://localhost:8080/topjava/rest/meals --user user@yandex.ru:password

get Meals 100003
curl -s http://localhost:8080/topjava/rest/meals/100003 --user user@yandex.ru:password

filter Meals
curl -s "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00:00&endDate=2020-01-31&endTime=10:00:00" --user user@yandex.ru:password

get Meals not found
curl -s -v http://localhost:8080/topjava/rest/meals/100008 --user user@yandex.ru:password

delete Meals
curl -s -X DELETE http://localhost:8080/topjava/rest/meals/100002 --user user@yandex.ru:password

create Meals
curl -s -X POST -d '{"dateTime":"2021-11-29T10:00","description":"New meal","calories":150}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/meals --user user@yandex.ru:password

update Meals
curl -s -X PUT -d '{"dateTime":"2021-11-29T10:00", "description":"Updated meal", "calories":300}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/100011 --user user@yandex.ru:password