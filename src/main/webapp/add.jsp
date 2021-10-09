<%@ page import="ru.javawebinar.topjava.DAO.MealToDao" %>
<%@ page import="ru.javawebinar.topjava.DAO.MealToDaoImpl" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Locale" %>
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
    MealToDao mealToDao = new MealToDaoImpl();
    String str = request.getParameter("date");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
    String description = request.getParameter("description");
    int calories = Integer.parseInt(request.getParameter("calories"));

    Long ids = Long.valueOf(mealToDao.getAll().size()) +1L;
    System.out.println(Long.valueOf(mealToDao.getAll().size()));
    System.out.println(ids);
    MealTo meal = new MealTo(ids, dateTime, description, calories, false);
    mealToDao.saveCache(meal);
%>
<c:redirect url="http://localhost:8080/topjava/meals"/>
