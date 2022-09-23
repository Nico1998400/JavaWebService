package com.example.javawebserviceproject.services;

import com.example.javawebserviceproject.dto.DtoRequest;
import com.example.javawebserviceproject.dto.DtoResponse;
import com.example.javawebserviceproject.entities.AppUser;
import com.example.javawebserviceproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final UserRepository userRepository;


    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public List<DtoResponse> findAll(String contains) {
        return userRepository
                .findAppUsersByUsernameContaining(contains)
                .stream()
                .map(appUser -> new DtoResponse(appUser.getId(), appUser.getUsername()))
                .toList();
    }

    public AppUser findById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    public AppUser addUser(AppUser user) {

        return userRepository.save(user);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public AppUser changeUserById(int id, DtoRequest dtoRequest) {

        AppUser updateUser = userRepository.findById(id).orElseThrow();
        updateUser.setUsername(dtoRequest.username());
        updateUser.setPassword(dtoRequest.password());

        return userRepository.save(updateUser);
    }


}
