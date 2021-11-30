**Example of curl command.**

_get All Meals_

`curl -s http://localhost:8080/topjava/rest/meals --user user@yandex.ru:password
`

_get Meals 100003_

`curl -s http://localhost:8080/topjava/rest/meals/100003 --user user@yandex.ru:password
`

_filter Meals_

`curl -s "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00:00&endDate=2020-01-31&endTime=10:00:00" --user user@yandex.ru:password
`

_get Meals not found_

`curl -s -v http://localhost:8080/topjava/rest/meals/100008 --user user@yandex.ru:password
`

_delete Meals_

`curl -s -X DELETE http://localhost:8080/topjava/rest/meals/100002 --user user@yandex.ru:password
`

_create Meals_

`curl -s -X POST -d '{"dateTime":"2021-11-29T10:00","description":"New meal","calories":150}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/meals --user user@yandex.ru:password
`

_update Meals_

`curl -s -X PUT -d '{"dateTime":"2021-11-29T10:00", "description":"Updated meal", "calories":300}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/100011 --user user@yandex.ru:password`