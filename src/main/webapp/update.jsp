<%@ page import="ru.javawebinar.topjava.DAO.MealToDao" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Locale" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    try {
        MealToDao cache = (MealToDao) request.getAttribute("cache");
        Long id = Long.parseLong(request.getParameter("id"));
        String str = request.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal m = new Meal(id, dateTime, description, calories);
        cache.saveCache(m);
    } catch (Exception e) {
        System.out.println("Can't update");
    }
%>
<c:redirect url="http://localhost:8080/topjava/meals"/>