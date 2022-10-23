package com.ahinski.blog.repositories;

import com.ahinski.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}