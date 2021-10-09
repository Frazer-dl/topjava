package ru.javawebinar.topjava.DAO;
import org.ehcache.Cache;
import ru.javawebinar.topjava.config.CacheHelper;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MealToDaoImpl implements MealToDao {
    CacheHelper cacheHelper = CacheHelper.getInstance();
    Long id;

    @Override
    public void saveCache(MealTo meal) {
        id = meal.getId();
        cacheHelper.getCache().put(id, meal);
    }

    @Override
    public void saveCache(MealTo meal, Long id) {
        cacheHelper.getCache().put(id, meal);
    }

    @Override
    public List<MealTo> getAll() {
        List<MealTo> meals = new ArrayList<>();
        for (Cache.Entry<Long, Object> entry: cacheHelper.getCache()) {
            MealTo entity = (MealTo) entry.getValue();
            meals.add(entity);
        }
        return meals;
    }

    @Override
    public MealTo getCacheById(Long id) {
        return (MealTo) cacheHelper.getCache().get(id);
    }

    @Override
    public void deleteCache(Long id) {
        cacheHelper.getCache().remove(id);
    }

    public void init() {
        if (getAll().isEmpty()) {
            List<Meal> meals = Arrays.asList(
                    new Meal(1L, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                    new Meal(2L, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                    new Meal(3L, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                    new Meal(4L, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                    new Meal(5L, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                    new Meal(6L, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                    new Meal(7L, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
            );
            MealsUtil.filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(21, 0), 2000).forEach(this::saveCache);
        } else {
            MealsUtil.filteredByStreamsTemp(getAll(), LocalTime.of(7, 0), LocalTime.of(21, 0), 2000).forEach(this::saveCache);
        }
    }
}
