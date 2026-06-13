package com.example.moslearn.service;

import com.example.moslearn.dto.CreateUserRequest;
import com.example.moslearn.dto.UpdateUserRequest;
import com.example.moslearn.exception.DuplicateEmailException;
import com.example.moslearn.exception.UserNotFoundException;
import com.example.moslearn.model.User;
import com.example.moslearn.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void createAndListUsers() {
        userService.create(new CreateUserRequest("Alice", "alice@example.com"));
        userService.create(new CreateUserRequest("Bob", "bob@example.com"));

        List<User> users = userService.findAll();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getName).containsExactly("Alice", "Bob");
    }

    @Test
    void updateUserReplacesUserData() {
        User createdUser = userService.create(new CreateUserRequest("Alice", "alice@example.com"));

        User updatedUser = userService.update(
                createdUser.getId(),
                new UpdateUserRequest("Alice Updated", "alice.updated@example.com")
        );

        assertThat(updatedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(updatedUser.getName()).isEqualTo("Alice Updated");
        assertThat(updatedUser.getEmail()).isEqualTo("alice.updated@example.com");
    }

    @Test
    void deletingMissingUserThrowsException() {
        assertThatThrownBy(() -> userService.delete(999L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id 999 was not found.");
    }

    @Test
    void creatingDuplicateEmailThrowsException() {
        userService.create(new CreateUserRequest("Alice", "alice@example.com"));

        assertThatThrownBy(() -> userService.create(new CreateUserRequest("Alice 2", "alice@example.com")))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("User with email alice@example.com already exists.");
    }
}
