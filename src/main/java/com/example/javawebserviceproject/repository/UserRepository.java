package com.example.javawebserviceproject.repository;

import com.example.javawebserviceproject.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

    List<AppUser> findAppUsersByUsernameContaining(String containing);

    Optional<AppUser> findAppUsersByUsernameIgnoreCase(String username);

    AppUser findByUsername(String username);
}
