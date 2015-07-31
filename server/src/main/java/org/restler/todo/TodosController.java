package org.restler.todo;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TodosController implements Todos {

    private ConcurrentHashMap<String, Todo> todos = new ConcurrentHashMap<>();

    @Override
    public Todo create(Todo todo) {
        String id = UUID.randomUUID().toString();
        Todo storedTodo = todo.withId(id);
        todos.put(id, storedTodo);
        return storedTodo;
    }

    @Override
    public Todo update(String id, Todo todo) {
        return todos.put(id, todo.withId(id));
    }

    @Override
    public Todo delete(String id) {
        return todos.remove(id);
    }

    @Override
    public Todo get(String id) {
        return todos.get(id);
    }

    @Override
    public Todo[] list() {
        return todos.values().toArray(new Todo[todos.size()]);
    }
}
