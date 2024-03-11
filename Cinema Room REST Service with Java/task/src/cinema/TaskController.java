package cinema;

import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class TaskController {

    private final static Cinema CINEMA = new Cinema();


    @GetMapping("/seats")
    public Map<String, Object> getCinema() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", CINEMA.getRowsNumber());
        result.put("columns", CINEMA.getColumnsNumber());
        result.put("seats", CINEMA.getSeats());
        //System.out.println(result);
        return result;
    }

    /* just examples

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable int id) {
        return taskList.get(id - 1); // list indices start from 0
    }


    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTasks(@PathVariable int id) {
        return new ResponseEntity<>(taskList.get(id - 1), HttpStatus.ACCEPTED);
    }

    */

}