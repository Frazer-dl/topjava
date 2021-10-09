package ru.javawebinar.topjava.DAO;

import org.ehcache.Cache;
import ru.javawebinar.topjava.config.CacheHelper;
import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class MealToDaoImpl implements MealToDao {
    CacheHelper cacheHelper = CacheHelper.getInstance();
    AtomicLong size = new AtomicLong();
    Long id;

    @Override
    public void saveCache(Meal m) {
        id = m.getId();
        cacheHelper.getCache().put(id, m);
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> mList = new ArrayList<>();
        for (Cache.Entry<Long, Object> entry: cacheHelper.getCache()) {
            Meal m = (Meal) entry.getValue();
            mList.add(m);
        }
        return mList;
    }

    @Override
    public Meal getCacheById(Long id) {
        return (Meal) cacheHelper.getCache().get(id);
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
        } else {
            meals = new ArrayList<>(getAll());
        }

        meals.forEach(this::saveCache);
    }

    @Override
    public Long getId() {
        return size.incrementAndGet();
    }
}
