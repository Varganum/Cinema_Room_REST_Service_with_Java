package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        result.put("seats", CINEMA.getSeatsToDisplay());
        //System.out.println(result);
        return result;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseSeat(@RequestBody UserRequest userRequest) {

        ResponseEntity<Object> resultResponse;
        int row = userRequest.getRow();
        int column = userRequest.getColumn();

        //validness of userRequest must be checked here

        if (CINEMA.isSeatValid(row, column)) {
            Seat requestedSeat = CINEMA.findSeat(row, column);

            if (!requestedSeat.isBooked()) {
                requestedSeat.setBooked(true);
                SeatToDisplay seatToDisplay = CINEMA.hideSeatFromDisplay(row, column);
                resultResponse = ResponseEntity.ok().body(seatToDisplay);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "The ticket has been already purchased!");
                resultResponse = ResponseEntity.badRequest().body(errorResponse);
            }
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "The number of a row or a column is out of bounds!");
            resultResponse = ResponseEntity.badRequest().body(errorResponse);
        }

        return resultResponse;
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