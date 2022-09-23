package com.example.javawebserviceproject.services;

import com.example.javawebserviceproject.dto.Todo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class JsonPlaceService {

    private final WebClient webClient;

    public JsonPlaceService(WebClient webClient) {
        this.webClient = webClient;
    }


    public List<Todo> findAllPosts() {
        return webClient
                .get()
                .uri("/posts")
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Todo.class))
                .buffer()
                .blockLast();

    }

    public List<Todo> findPostsByUserId(int userId) {
        return webClient
                .get()
                .uri("/posts/?userId=" + userId)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Todo.class))
                .buffer()
                .blockLast();
    }

    public Mono<Todo> addPlayer(Todo user) {
        return webClient
                .post()
                .uri("/posts")
                .body(Mono.just(user), Todo.class)
                .retrieve()
                .bodyToMono(Todo.class);

    }

    public Mono<Todo> updateUserById(int id, Todo user) {
        return webClient.put()
                .uri("/posts/" + id)
                .body(Mono.just(user), Todo.class)
                .retrieve()
                .bodyToMono(Todo.class);

    }

    public Mono<Todo> patchUserById(int id, Map<String, String> userMap) {
        return webClient
                .patch()
                .uri("/posts/" + id)
                .body(Mono.just(userMap), new ParameterizedTypeReference<Map<String, String>>() {
                })
                .retrieve()
                .bodyToMono(Todo.class);
    }

    public Mono<String> deletePostById(int id) {
        return webClient
                .delete()
                .uri("/posts/" + id)
                .retrieve()
                .bodyToMono(String.class);
    }
}
