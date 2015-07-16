package org.restler.todo;

import org.springframework.web.bind.annotation.*;

@RestController("")
public interface Todos {

    @RequestMapping(value = "", method = RequestMethod.POST)
    Todo create(@RequestBody Todo todo);

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    Todo update(@PathVariable String id, @RequestBody Todo todo);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    Todo delete(@PathVariable String id);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Todo get(@PathVariable String id);

    @RequestMapping(value = "", method = RequestMethod.GET)
    Todo[] list();

}
