package com.example.javawebserviceproject.services;

import com.example.javawebserviceproject.entities.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServices {

    public static boolean isNamedNico() { //bara om man heter gunnar får man komma åt denna metoden

        AppUser appUser = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal(); //Den som är "inloggad" den som autentierats
        return appUser.getUsername().equalsIgnoreCase("nico");

    }


    public Boolean idMatchesUser(int id) {

        AppUser appUser = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return (id == appUser.getId());
    }

}
