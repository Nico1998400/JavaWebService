package com.example.javawebserviceproject;

import com.example.javawebserviceproject.entities.AppUser;
import com.example.javawebserviceproject.entities.Role;
import com.example.javawebserviceproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@SpringBootApplication
public class JavaWebServiceProjectApplication implements CommandLineRunner {

    @Value("${token.secret}")
    private String secret;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(JavaWebServiceProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


       userRepository.save(new AppUser("nico", passwordEncoder.encode("nico!"), List.of(Role.ADMIN)));
        userRepository.save(new AppUser("tim", passwordEncoder.encode("tim!"), List.of(Role.USER)));
        userRepository.save(new AppUser("elvis", passwordEncoder.encode("elvis!"), List.of(Role.USER)));
        userRepository.findAppUsersByUsernameIgnoreCase("gunnar").ifPresent(System.out::println);
        System.out.println(secret);



    }
}
