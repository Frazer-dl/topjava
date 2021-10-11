package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
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

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    private MealDao mealDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        Long id = getId(request.getParameter("id"));
        String action = request.getParameter("action");
        request.setAttribute("dateTimeFormatter", dateTimeFormatter);

        if (id == null && action == null) {
            List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.read(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("list", meals);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        } else {
            Meal meal;
            switch (action) {
                case "create":
                    request.setAttribute("title", "Add meal");
                    log.debug("Creating...");
                    request.getRequestDispatcher("edit.jsp").forward(request, response);
                    break;
                case "update":
                    meal = mealDao.read(id);
                    request.setAttribute("meal", meal);
                    request.setAttribute("title", "Edit meal");
                    log.debug("Updating...");
                    request.getRequestDispatcher("edit.jsp").forward(request, response);
                    break;
                case "delete":
                    mealDao.delete(mealDao.read(id).getId());
                    if (mealDao.read(Long.parseLong(request.getParameter("id"))) == null) {
                        log.debug("Meal id=" + request.getParameter("id") + " deleted");
                    } else {
                        log.debug("Meal id=" + request.getParameter("id") + " not deleted");
                    }
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/meals");
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/meals");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Long id = getId(request.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (id == null) {
            Meal meal = mealDao.create(new Meal(null, dateTime, description, calories));
            if (meal == null) {
                log.debug("Meal id=" + request.getParameter("id") + " not created");
            } else {
                log.debug("Meal id=" + request.getParameter("id") + " created");
            }
        } else {
            Meal meal = mealDao.update(new Meal(id, dateTime, description, calories));
            if (meal == null) {
                log.debug("Meal id=" + request.getParameter("id") + " not updated");
            } else {
                log.debug("Meal id=" + request.getParameter("id") + " updated");
            }
        }

        response.sendRedirect(request.getContextPath() + "/meals");
    }

    @Override
    public void init() throws ServletException {
        mealDao = new MealDaoInMemory();
        System.out.println(mealDao.read().size());
        System.out.println(mealDao.read().isEmpty());
        if (mealDao.read().isEmpty()) {
            log.debug("Meals not initialized");
        } else {
            log.debug("Meals initialized");
        }
    }

    private Long getId(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return Long.parseLong(id);
    }
}
