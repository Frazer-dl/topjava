<%@ page import="ru.javawebinar.topjava.DAO.MealToDao" %>
<%@ page import="ru.javawebinar.topjava.DAO.MealToDaoImpl" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    try {
        String id= (String)request.getSession().getAttribute("id");
        MealToDao mealToDao = new MealToDaoImpl();
        if (id != null) {
            Long ids = Long.parseLong(request.getParameter("name"));
            mealToDao.saveCache(mealToDao.getCacheById(ids), ids);
        }
        Long ids = (long) mealToDao.getAll().size() +1L;
        String str = request.getParameter("dateTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        int calories = Integer.parseInt(request.getParameter("calories"));

        MealTo meal = new MealTo(ids, dateTime, request.getParameter("description"), calories, false);
        mealToDao.saveCache(meal);
    } catch (Exception e) {
        System.out.println("Invalid id");
    }
%>
<c:redirect url="http://localhost:8080/topjava/meals"/>