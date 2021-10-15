package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.Collection;

public class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal, int userId) {
        log.info("create {} userId={}", meal, userId);
        return service.create(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {} userId={}", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id, int userId) {
        log.info("get {} userId={}", id, userId);
        return service.get(id, userId);
    }

    public Collection<Meal> getAll(int userId) {
        log.info("getAll userId={}", userId);
        return service.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        log.info("update {} userId={}", meal, userId);
        service.update(meal, userId);
    }

    public Collection<MealTo> getFiltered(int userId, LocalDateTime start, LocalDateTime end, int calories) {
        return service.getFiltered(userId, start, end, calories);
    }
}
