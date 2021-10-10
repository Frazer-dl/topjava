package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void create(Meal meal);
    List<Meal> read();
    Meal read(long id);
    void update(Meal meal);
    void delete(long id);
}
