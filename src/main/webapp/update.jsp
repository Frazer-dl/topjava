<%@ page import="ru.javawebinar.topjava.DAO.MealToDao" %>
<%@ page import="ru.javawebinar.topjava.DAO.MealToDaoImpl" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    try {
        String id= request.getParameter("id");
        MealToDao mealToDao = new MealToDaoImpl();
        String str = request.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        boolean isExcess = Boolean.parseBoolean(request.getParameter("isExcess"));

        Long ids = Long.parseLong(request.getParameter("id"));
        MealTo meal = new MealTo(ids, dateTime, description, calories, isExcess);
        mealToDao.saveCache(meal, ids);

    } catch (Exception e) {
        System.out.println("Invalid id");
    }
%>
<c:redirect url="http://localhost:8080/topjava/meals"/>