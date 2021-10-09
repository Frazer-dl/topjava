package ru.javawebinar.topjava.DTO;

import ru.javawebinar.topjava.DAO.MealToDao;
import ru.javawebinar.topjava.DAO.MealToDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;

public class MealMealToImpl implements MealMealTo {
    private final MealToDao cache = new MealToDaoImpl();
    private final MealTo mealTo;
    private final LocalDateTime date;

    public MealMealToImpl(Meal meal) {
        Long id = meal.getId();
        this.date = meal.getDateTime();
        String description = meal.getDescription();
        int calories = meal.getCalories();
        boolean isExcess = getExcees();

        this.mealTo = new MealTo(id, date, description, calories, isExcess);
    }

    @Override
    public boolean getExcees() {
        boolean isExcess;
        try {
            isExcess = cache.getAll().stream()
                    .filter(m -> m.getDateTime().toLocalDate().equals(date.toLocalDate()))
                    .findFirst()
                    .get()
                    .isExcess();
        } catch (Exception e) {
            isExcess = true;
        }
        return isExcess;
    }

    @Override
    public MealTo getMealTo() {
        return mealTo;
    }
}