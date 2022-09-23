package com.example.javawebserviceproject.controller;

import com.example.javawebserviceproject.dto.DtoRequest;
import com.example.javawebserviceproject.dto.DtoResponse;
import com.example.javawebserviceproject.entities.AppUser;
import com.example.javawebserviceproject.services.AppUserService;
import com.example.javawebserviceproject.services.AuthServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "JwtAuth")
@Secured("ROLE_ADMIN, ROLE_USER")
public class AppUserController {

    private final AppUserService appUserService;
    private final AuthServices authServices;

    public AppUserController(AppUserService appUserService, AuthServices authServices) {
        this.appUserService = appUserService;
        this.authServices = authServices;
    }

    @GetMapping
    @PreAuthorize("@authServices.isNamedNico()") //Bara dom som heter "Gunnar" får göra det.
    public List<DtoResponse> findAll(
            @RequestParam(required = false, defaultValue = "") String user
    ) {
        return appUserService.findAll(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authServices.idMatchesUser(#id)")
    public AppUser findById(@PathVariable int id) {
        return appUserService.findById(id);
    }

    @PostMapping
    public AppUser addUser(@RequestBody AppUser user) {
        return appUserService.addUser(user);
    }

    @PutMapping("{id}")
    public AppUser changePlayerById(
            @PathVariable int id,
            @RequestBody DtoRequest dtoRequest) {
        return appUserService.changeUserById(id, dtoRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        appUserService.deleteById(id);
    }
}
