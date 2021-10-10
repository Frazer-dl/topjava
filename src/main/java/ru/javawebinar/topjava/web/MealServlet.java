package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealDao;
import ru.javawebinar.topjava.DAO.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealDao mealDao = new MealDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.read(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        if (request.getParameter("id") == null && request.getParameter("action") == null) {
            request.setAttribute("list", meals);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        if (request.getParameter("action").equals("update")) {
            Meal m = mealDao.read().stream()
                    .filter(elem -> elem.getId() == Long.parseLong(request.getParameter("id")))
                    .findFirst()
                    .get();
            request.setAttribute("meal", m);
            request.getRequestDispatcher("edit.jsp").forward(request, response);
        }
        if (request.getParameter("action").equals("create")) {
            request.getRequestDispatcher("edit.jsp").forward(request, response);
        }
        if (request.getParameter("action").equals("delete")) {
            mealDao.delete(mealDao.read().stream()
                    .filter(elem -> elem.getId() == Long.parseLong(request.getParameter("id")))
                    .findFirst()
                    .get().getId());
            request.getRequestDispatcher("redirect.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        boolean save = request.getParameter("save") != null;
        String str = request.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (save && request.getParameter("id").isEmpty()) {
            try {
                Meal m = new Meal(mealDao.read().size()+1L, dateTime, description, calories);
                mealDao.create(m);
            } catch (Exception e) {
                log.debug("Can't create");
            }
        }

        if (save && !request.getParameter("id").isEmpty()) {
            try {
                Meal meal = new Meal(mealDao.read(Long.parseLong(request.getParameter("id"))).getId(),
                        dateTime,
                        description,
                        calories);
                mealDao.update(meal);
            } catch (Exception e) {
                log.debug("Can't update");
            }
        }
        request.getRequestDispatcher("redirect.jsp").forward(request, response);
    }
}
