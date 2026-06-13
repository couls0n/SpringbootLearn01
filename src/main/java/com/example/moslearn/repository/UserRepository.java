package com.example.moslearn.repository;

import com.example.moslearn.model.Order;
import com.example.moslearn.model.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
