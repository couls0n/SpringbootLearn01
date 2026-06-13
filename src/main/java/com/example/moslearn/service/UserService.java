package com.example.moslearn.service;

import com.example.moslearn.dto.CreateUserRequest;
import com.example.moslearn.dto.UpdateUserRequest;
import com.example.moslearn.exception.DuplicateEmailException;
import com.example.moslearn.exception.UserNotFoundException;
import com.example.moslearn.model.Order;
import com.example.moslearn.model.User;
import java.util.List;

import com.example.moslearn.repository.OrderRepository;
import com.example.moslearn.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return requireUser(id);
    }

    public User create(CreateUserRequest request) {
        ensureEmailAvailable(request.email(), null);
        User user = new User(null, request.name(), request.email());
        return userRepository.save(user);
    }

    public User update(Long id, UpdateUserRequest request) {
        User user = requireUser(id);
        ensureEmailAvailable(request.email(), id);
        user.setName(request.name());
        user.setEmail(request.email());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(requireUser(id));
    }

    @Transactional(readOnly = true)
    private User requireUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    private void ensureEmailAvailable(String email, Long currentUserId) {
        userRepository.findByEmail(email)
                .ifPresent(existingUser -> {
                    boolean sameUser = currentUserId != null && currentUserId.equals(existingUser.getId());
                    if (!sameUser) {
                        throw new DuplicateEmailException(email);
                    }
                });
    }


}
