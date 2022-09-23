package com.example.javawebserviceproject.controller;

import com.example.javawebserviceproject.dto.Todo;
import com.example.javawebserviceproject.services.AppUserService;
import com.example.javawebserviceproject.services.JsonPlaceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name ="JwtAuth")
public class JsonPlaceController {

    private final JsonPlaceService jsonPlaceService;

    public JsonPlaceController(JsonPlaceService jsonPlaceService) {
        this.jsonPlaceService = jsonPlaceService;
    }

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> findAllPosts() {
        return jsonPlaceService.findAllPosts();
    }

    @GetMapping("/posts/{userId}")
    public List<Todo> findPostsByUserId(@PathVariable int userId) {
        return jsonPlaceService.findPostsByUserId(userId);
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Todo> addPlayer(@RequestBody Todo user) {
        return jsonPlaceService.addPlayer(user);
    }

    @PutMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Todo> changePlayerById(
            @PathVariable int id,
            @RequestBody Todo user) {
        return jsonPlaceService.updateUserById(id, user);
    }

    @PatchMapping("/posts/{id}")
    public Mono<Todo> patchPost(
            @PathVariable int id,
            @RequestBody Map<String, String> userMap) {
        return jsonPlaceService.patchUserById(id, userMap);
    }

    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> deletePostById(@PathVariable int id) {
        return jsonPlaceService.deletePostById(id);
    }
}

