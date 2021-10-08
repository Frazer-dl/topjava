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
    CacheHelper cacheHelper = new CacheHelper();
    Long id = size();

    @Override
    public void saveCache(MealTo meal) {
        id += 1L;
        System.out.println(id);
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

    private Long size() {
        Long result = 0L;
        for (Cache.Entry<Long, Object> entry: cacheHelper.getCache()) {
            result++;
        }
        return result;
    }

    public void init() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        System.out.println(MealsUtil.filteredByStreams(meals, LocalTime.of(7, 0),
                LocalTime.of(21, 0),
                2000));
        MealsUtil.filteredByStreams(meals, LocalTime.of(7, 0),
                LocalTime.of(21, 0),
                2000).forEach(this::saveCache);

    }
}
