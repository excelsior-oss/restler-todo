package org.restler.todo.client;

import org.restler.todo.Todo;
import org.restler.todo.Todos;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class ManualTodosImpl implements Todos {

    private static final String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Todo create(Todo todo) {
        return restTemplate.postForObject(baseUrl, todo, Todo.class);
    }

    @Override
    public Todo update(String id, @RequestBody Todo todo) {
        try {
            return restTemplate.exchange(new RequestEntity<>(todo, null, HttpMethod.PUT, new URI(baseUrl + "/" + id)), Todo.class).getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    @Override
    public Todo delete(@PathVariable String id) {
        try {
            return restTemplate.exchange(new RequestEntity<>(null, null, HttpMethod.DELETE, new URI(baseUrl + "/" + id)), Todo.class).getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    @Override
    public Todo get(@PathVariable String id) {
        return restTemplate.getForObject(baseUrl + "/" + id, Todo.class);
    }

    @Override
    public Todo[] list() {
        return restTemplate.getForObject(baseUrl, Todo[].class);
    }

}
