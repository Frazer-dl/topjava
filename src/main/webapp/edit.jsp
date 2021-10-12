<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <Title>${meal.id != defaultMeal.id ? "Edit meal" : "Add meal"}</Title>
</head>
<h3><a href="index.html">Home</a></h3>
<body>
<hr>
<h2>${meal.id != defaultMeal.id ? "Edit meal" : "Add meal"}</h2>
<form action="${pageContext.request.contextPath}/meals" method="post">
    <input type="hidden" name="id" value="${meal.id}">
    <p>
        <label for="date">DateTime: </label>
        <input type="datetime-local" id="date" name="date" value="${meal.dateTime}">
    </p>
    <p>
        <label for="description">Description: </label>
        <input type="text" id="description" name="description" value="${meal.description}">
    </p>
    <p>
        <label for="calories">Calories: </label>
        <input type="number" id="calories" name="calories" value="${meal.calories != defaultMeal.calories ? meal.calories : ""}">
    </p>
    <p>
        <input type="submit" value="Save">
        <a href="${pageContext.request.contextPath}/meals"> <button type="button">Cancel</button></a>
    </p>
</form>
</body>
</html>