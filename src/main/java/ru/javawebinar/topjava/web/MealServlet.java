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
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private MealDao mealDao;
    private final Meal defaultMeal = new Meal(null, null, "", 0);

    @Override
    public void init() throws ServletException {
        mealDao = new MealDaoInMemory();
        if (mealDao.read().isEmpty()) {
            log.debug("Meals not initialized");
        } else {
            log.debug("Meals initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");
        request.setAttribute("formatter", formatter);
        request.setAttribute("defaultMeal", defaultMeal);

        if (action != null) {
            Long id = getId(request);
            switch (action) {
                case "create":
                    log.debug("Creating...");
                    request.setAttribute("meal", new Meal(defaultMeal.getId(), defaultMeal.getDateTime(), defaultMeal.getDescription(), defaultMeal.getCalories()));
                    request.getRequestDispatcher("edit.jsp").forward(request, response);
                    break;
                case "update":
                    log.debug("Updating...");
                    request.setAttribute("meal", mealDao.read(id));
                    request.getRequestDispatcher("edit.jsp").forward(request, response);
                    break;
                case "delete":
                    log.debug("Deleting...");
                    mealDao.delete(id);
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
        } else {
            List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.read(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("list", meals);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Long id = getId(request);
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (Objects.equals(id, defaultMeal.getId())) {
            Meal meal = mealDao.create(new Meal(defaultMeal.getId(), dateTime, description, calories));
            System.out.println(meal);
            if (meal == null) {
                log.debug("Meal not created");
            } else {
                log.debug("Meal created");
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

    private Long getId(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            return null;
        }
        return Long.parseLong(id);
    }
}
