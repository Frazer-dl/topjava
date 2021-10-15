package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
public class MealRestController extends AbstractMealController {

    @Override
    public Meal create(Meal meal, int userId) {
        return super.create(meal, userId);
    }

    @Override
    public void delete(int id, int userId) {
        super.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) {
        return super.get(id, userId);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return super.getAll(userId);
    }

    @Override
    public void update(Meal meal, int userId) {
        super.update(meal, userId);
    }

    @Override
    public Collection<MealTo> getFiltered(int userId, LocalDateTime start, LocalDateTime end, int calories) {
        return super.getFiltered(userId, start, end, calories);
    }
}