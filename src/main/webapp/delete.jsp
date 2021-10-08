<%@ page import="ru.javawebinar.topjava.DAO.MealToDao" %>
<%@ page import="ru.javawebinar.topjava.DAO.MealToDaoImpl" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    try {
        Long id = Long.parseLong(request.getParameter("id"));
        MealToDao mealToDao = new MealToDaoImpl();
        mealToDao.deleteCache(id);
    } catch (Exception e) {
        System.out.println("Invalid id");
    }
%>
<c:redirect url="http://localhost:8080/topjava/meals"/>