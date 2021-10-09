package ru.javawebinar.topjava.DTO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;

public class MealToMealImpl implements MealToMeal{
    private final Meal meal;

    public MealToMealImpl(MealTo meal) {
        Long id = meal.getId();
        LocalDateTime date = meal.getDateTime();
        String description = meal.getDescription();
        int calories = meal.getCalories();

        this.meal = new Meal(id, date, description, calories);
    }

    @Override
    public Meal getMealTo() {
        return this.meal;
    }
}
