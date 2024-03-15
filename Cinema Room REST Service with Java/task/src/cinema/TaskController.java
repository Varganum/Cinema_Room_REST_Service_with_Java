package cinema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TaskController {

    private final static Cinema CINEMA = new Cinema();

    //Method returns the list of Seats available for order
    @GetMapping("/seats")
    public Map<String, Object> getCinema() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", CINEMA.getRowsNumber());
        result.put("columns", CINEMA.getColumnsNumber());
        result.put("seats", CINEMA.getSeatsToDisplay());
        //System.out.println(result);
        return result;
    }

    //Method returns short statistics about sold and free seats and total income
    //The password is needed to get the statistics
    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam(value = "password", required = false) String password) {

        ResponseEntity<Object> resultResponse;
        Map<String, Object> result = new LinkedHashMap<>();;

        if (Objects.nonNull(password) && password.equals("super_secret")) {
            result.put("income", CINEMA.getIncome());
            result.put("available", CINEMA.getAvailableSeatsNumber());
            result.put("purchased", CINEMA.getPurchasedSeatsNumber());
            resultResponse = ResponseEntity.ok().body(result);
        } else {
            result.put("error", "The password is wrong!");
            resultResponse = ResponseEntity.status(401).body(result);
        }

        return resultResponse;
    }

    /*Method handle the Purchase request, checks validness of row and column of seat ordered,
    generate UUID for the ticket and return ticket info (token(UUID), row, column, price).
    Method returns the error, if the ordered seat is requested again.
     */
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

    /*Method handle Return ticket request. It accepts ticket's token,
    find the ticket by token and make seat available for order again.
    Method returns the error if token is not found.
    It returns ticket info if token is valid.
     */
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

    //Method generates ResponseEntity body for valid Return request
    private Object getReturnedTicketResponseBody(Seat seatToReturn) {

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("ticket", CINEMA.getSeatToDisplay(seatToReturn));

        return result;

    }

    //Method generates ResponseEntity body for valid Purchase request
    private Object getPurchasedTicketResponseBody(SeatToDisplay seatToDisplay, Seat requestedSeat) {

        Map<String, Object> result = new LinkedHashMap<>();

        //generate new token
        String token = String.valueOf(UUID.randomUUID());
        requestedSeat.setToken(token);

        result.put("token", token);
        result.put("ticket", seatToDisplay);

        return result;
    }

}