package cinema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<Object> purchaseSeat(@RequestBody UserPurchaseRequest purchaseRequest) {

        ResponseEntity<Object> resultResponse;
        int row = purchaseRequest.getRow();
        int column = purchaseRequest.getColumn();

        //check seat's row or column validness
        if (CINEMA.isSeatValid(row, column)) {
            //case of valid seat's row or column
            Seat requestedSeat = CINEMA.findSeat(row, column);

            if (!requestedSeat.isBooked()) {
                requestedSeat.setBooked(true);
                SeatToDisplay seatToDisplay = CINEMA.hideSeatFromDisplay(row, column);
                resultResponse = ResponseEntity.ok().body(getPurchasedTicketResponseBody(seatToDisplay, requestedSeat));
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "The ticket has been already purchased!");
                resultResponse = ResponseEntity.badRequest().body(errorResponse);
            }

        } else {
            //case of invalid seat's row or column
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "The number of a row or a column is out of bounds!");
            resultResponse = ResponseEntity.badRequest().body(errorResponse);
        }

        return resultResponse;
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnTicket(@RequestBody UserReturnRequest returnRequest) {

        ResponseEntity<Object> resultResponse;
        String token = returnRequest.getToken();

        //try to find Seat by token
        Seat seatToReturn = CINEMA.findSeat(token);

        if (Objects.nonNull(seatToReturn)) {
            //token found case
            CINEMA.returnTicket(seatToReturn);
            resultResponse = ResponseEntity.ok().body(getReturnedTicketResponseBody(seatToReturn));
        } else {
            //token not found case
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Wrong token!");
            resultResponse = ResponseEntity.badRequest().body(errorResponse);
        }

        return resultResponse;
    }

    private Object getReturnedTicketResponseBody(Seat seatToReturn) {

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("ticket", CINEMA.getSeatToDisplay(seatToReturn));

        return result;

    }

    private Object getPurchasedTicketResponseBody(SeatToDisplay seatToDisplay, Seat requestedSeat) {

        Map<String, Object> result = new LinkedHashMap<>();

        //generate new token
        String token = String.valueOf(UUID.randomUUID());
        requestedSeat.setToken(token);

        result.put("token", token);
        result.put("ticket", seatToDisplay);

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