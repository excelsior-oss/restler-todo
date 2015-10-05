package org.restler.todo.client;

import com.fasterxml.jackson.module.paranamer.ParanamerModule;
import org.restler.Restler;
import org.restler.Service;
import org.restler.spring.mvc.SpringMvcSupport;
import org.restler.todo.Todo;
import org.restler.todo.Todos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

public class TodoClient {

    private HashMap<String, Function<String[], Result>> handlers;
    private Todos todos;

    public TodoClient() {
        handlers = new HashMap<>();
        handlers.put("e", this::exitHandler);
        handlers.put("l", this::listHandler);
        handlers.put("c", this::createHandler);
        handlers.put("u", this::updateHandler);
        handlers.put("d", this::deleteHandler);
        handlers.put("g", this::getHandler);
    }

    public static void main(String[] args) throws IOException {
        new TodoClient().run();
    }

    private void run() throws IOException {
        SpringMvcSupport springMvcSupport = new SpringMvcSupport();
        springMvcSupport.addJacksonModule(new ParanamerModule());
        Restler builder = new Restler("http://localhost:8080", springMvcSupport);
        Service todosService = builder.build();
        todos = todosService.produceClient(Todos.class);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            reader.lines().
                    map(command -> {
                        String[] cmdParts = command.split(" ");
                        return handlers.getOrDefault(cmdParts[0], this::defaultHandler).apply(tail(cmdParts));
                    }).
                    filter(Result.BREAK::equals).findAny();
        }
    }

    private Result createHandler(String[] args) {
        todos.create(todo(args));
        return Result.CONTINUE;
    }

    private Result updateHandler(String[] args) {
        todos.update(args[0], todo(tail(args)));
        return Result.CONTINUE;
    }

    private Result deleteHandler(String[] args) {
        todos.delete(args[0]);
        return Result.CONTINUE;
    }

    private Result listHandler(String[] args) {
        Arrays.stream(todos.list()).
                map(todo -> todo.id).
                forEach(System.out::println);
        return Result.CONTINUE;
    }

    private Result getHandler(String[] args) {
        System.out.println(todos.get(args[0]));
        return Result.CONTINUE;
    }

    private Result exitHandler(String[] args) {
        return Result.BREAK;
    }

    private Result defaultHandler(String[] args) {
        return Result.CONTINUE;
    }

    private Todo todo(String[] args) {
        return new Todo(args[0], args[1], Boolean.valueOf(args[2]));
    }

    private String[] tail(String[] cmdParts) {
        return Arrays.copyOfRange(cmdParts, 1, cmdParts.length);
    }

    private enum Result {CONTINUE, BREAK}
}
