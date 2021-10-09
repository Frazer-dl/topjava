<%@ page import="ru.javawebinar.topjava.DAO.MealToDao" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Locale" %>
<%@ page import="ru.javawebinar.topjava.DTO.MealToMealToImpl" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: genov
  Date: 09.10.2021
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MealToDao cache = (MealToDao) request.getAttribute("cache");
    Long ids = cache.getId();
    String str = request.getParameter("date");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
    String description = request.getParameter("description");
    int calories = Integer.parseInt(request.getParameter("calories"));
    MealToMealToImpl meal = new MealToMealToImpl(new Meal(ids, dateTime, description, calories));

    cache.saveCache(meal.getMealTo());
%>
<c:redirect url="http://localhost:8080/topjava/meals"/>
