<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<h3><a href="index.html">Home</a></h3>
<a href="meals?action=create">Add Meal</a>
<body>
<hr>
<h2>Meals</h2>

<table  border="1" width="90%%">
    <thead>
    <tr>
        <th>DATE</th>
        <th>DESCRIPTION</th>
        <th>CALORIES</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items= "${list}">
        <tr style="color:${meal.isExcess() ? 'red' : 'green'}">
            <td>${meal.dateTime.format(dateTimeFormatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?id=${meal.getId()}&action=update"> <button type="button">Update</button></a></td>
            <td><a href="meals?id=${meal.getId()}&action=delete"> <button type="button">Delete</button></a></td>
        </tr>
    </c:forEach>
        </tbody>
</table>
</body>
</html>
