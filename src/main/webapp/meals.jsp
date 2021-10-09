<%@ page import="java.time.format.DateTimeFormatter" %>
<%--
  Created by IntelliJ IDEA.
  User: genov
  Date: 07.10.2021
  Time: 15:33
  To change this template use File | Settings | File Templates.
  <tr style="background-color:${mealWE.exceed ? 'greenyellow' : 'red'}">
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<a href="meals?edit=2">Add Meal</a>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table  border="1" width="90%%">
    <thead>
    <tr>
        <th>ID</th>
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
            <td>${meal.getId()}</td>
            <td>${meal.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td><a href="meals?id=${meal.getId()}&edit=1"> <button type="button">Update</button></a></td>
            <td><a href="delete.jsp?id=${meal.getId()}"> <button type="button">Delete</button></a></td>
        </tr>
    </c:forEach>
        </tbody>
</table>
</body>
</html>
