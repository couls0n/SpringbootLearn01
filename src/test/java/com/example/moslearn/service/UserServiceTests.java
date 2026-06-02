package com.example.moslearn.service;

import com.example.moslearn.dto.CreateUserRequest;
import com.example.moslearn.dto.UpdateUserRequest;
import com.example.moslearn.exception.UserNotFoundException;
import com.example.moslearn.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTests {

    private final UserService userService = new UserService();

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
}
