package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealToDao {
    void saveCache(Meal meal);
    List<Meal> getAll();
    Meal getCacheById(Long id);
    void deleteCache(Long id);
    void init();
    Long getId();
}
