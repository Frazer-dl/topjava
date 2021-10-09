<%@ page import="ru.javawebinar.topjava.DAO.MealToDao" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Locale" %>
<%@ page import="ru.javawebinar.topjava.DTO.MealMealToImpl" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="" %>
<%
    try {
        MealToDao cache = (MealToDao) request.getAttribute("cache");
        Long id = cache.getId();
        String str = request.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        MealMealToImpl meal = new MealMealToImpl(new Meal(id, dateTime, description, calories));
        cache.saveCache(meal.getMealTo());
    } catch (Exception e) {
        System.out.println("Can't add");
    }
%>
<c:redirect url="http://localhost:8080/topjava/meals"/>
