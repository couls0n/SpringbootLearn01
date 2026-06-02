package com.example.moslearn.service;

import com.example.moslearn.dto.CreateUserRequest;
import com.example.moslearn.dto.UpdateUserRequest;
import com.example.moslearn.exception.UserNotFoundException;
import com.example.moslearn.model.User;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public List<User> findAll() {
        return users.values()
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .toList();
    }

    public User findById(Long id) {
        return requireUser(id);
    }

    public User create(CreateUserRequest request) {
        Long id = idGenerator.incrementAndGet();
        User user = new User(id, request.name(), request.email());
        users.put(id, user);
        return user;
    }

    public User update(Long id, UpdateUserRequest request) {
        requireUser(id);
        User updatedUser = new User(id, request.name(), request.email());
        users.put(id, updatedUser);
        return updatedUser;
    }

    public void delete(Long id) {
        requireUser(id);
        users.remove(id);
    }

    private User requireUser(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }
}
