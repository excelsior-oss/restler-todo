package org.restler.todo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Todo implements Serializable {

    public final String id;
    public final String name;
    public final String description;
    public final boolean done;

    @JsonCreator
    public Todo(String id, String name, String description, boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.done = done;
    }

    public Todo(String name, String description, boolean done) {
        this(null, name, description, done);
    }

    public Todo withId(String id) {
        return new Todo(id, name, description, done);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }

}
