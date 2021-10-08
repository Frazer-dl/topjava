package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealToDao {
    void saveCache(MealTo meal);
    void saveCache(MealTo meal, Long id);
    List<MealTo> getAll();
    MealTo getCacheById(Long id);
    void deleteCache(Long id);
    void init();
}
