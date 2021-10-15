package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("inMemoryUserRepository")
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        int id = counter.incrementAndGet();
        if (user.isNew()) {
            return repository.computeIfAbsent(id, v -> new User(id, user.getName(), user.getEmail(), user.getPassword(),
                    user.getCaloriesPerDay(), user.isEnabled(), user.getRoles()));
        } else {
            return repository.computeIfPresent(user.getId(), (k, v) -> new User(user.getId(),
                    user.getName(), user.getEmail(), user.getPassword(), user.getCaloriesPerDay(), user.isEnabled(), user.getRoles()));
        }
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return new ArrayList<>(repository.values()).stream()
                .sorted(Comparator.comparing(AbstractNamedEntity::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .get();
    }
}
