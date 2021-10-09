package ru.javawebinar.topjava.DAO;

import org.ehcache.Cache;
import ru.javawebinar.topjava.DTO.MealToMeal;
import ru.javawebinar.topjava.DTO.MealToMealImpl;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


public class MealToDaoImpl implements MealToDao {
    CacheHelper cacheHelper = CacheHelper.getInstance();
    AtomicLong size = new AtomicLong();
    Long id;

    @Override
    public void saveCache(MealTo meal) {
        id = meal.getId();
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

    @Override
    public void init() {
        List<Meal> meals;

        if (getAll().isEmpty()) {
            meals = Arrays.asList(
                    new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                    new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                    new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                    new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                    new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                    new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                    new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
            );
            MealsUtil.filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(21, 0), 2000).forEach(this::saveCache);
        } else {
            meals = getAll().stream()
                    .map(m->{
                        MealToMeal dto = new MealToMealImpl(m);
                        return dto.getMealTo();
                    })
                    .collect(Collectors.toList());
            getAll().forEach(System.out::println);
            cacheHelper.clearCache();
            MealsUtil.filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(21, 0), 2000).forEach(this::saveCache);
        }
    }

    @Override
    public Long getId() {
        return size.incrementAndGet();
    }
}
