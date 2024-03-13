package cinema;

import java.util.ArrayList;

public class Cinema {

    private static final int ROWS_NUMBER = 9;
    private static final int COLUMNS_NUMBER = 9;

    private int rowsNumber;
    private int columnsNumber;

    private ArrayList<Seat> seats;
    private ArrayList<SeatToDisplay> seatsToDisplay;
    private ArrayList<SeatToDisplay> seatsBooked;

    public Cinema() {
        rowsNumber = ROWS_NUMBER;
        columnsNumber = COLUMNS_NUMBER;

        this.seats = new ArrayList<>();
        this.seatsToDisplay = new ArrayList<>();
        this.seatsBooked = new ArrayList<>();

        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                Seat newSeat = new Seat(i + 1, j + 1);
                seats.add(newSeat);
                seatsToDisplay.add(new SeatToDisplay(i + 1, j + 1, newSeat.getPrice()));
            }
        }
    }


    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public ArrayList<SeatToDisplay> getSeatsToDisplay() {
        ArrayList<SeatToDisplay> result = (ArrayList<SeatToDisplay>) seatsToDisplay.clone();
                result.removeAll(seatsBooked);
        return result;
    }

    public void setSeatsToDisplay(ArrayList<SeatToDisplay> seatsToDisplay) {
        this.seatsToDisplay = seatsToDisplay;
    }

    public int getRowsNumber() {
        return rowsNumber;
    }

    public void setRowsNumber(int rowsNumber) {
        this.rowsNumber = rowsNumber;
    }

    public int getColumnsNumber() {
        return columnsNumber;
    }

    public void setColumnsNumber(int columnsNumber) {
        this.columnsNumber = columnsNumber;
    }


    public Seat findSeat(int row, int column) {
        return seats.get((row - 1) * ROWS_NUMBER + column - 1);
    }

    public SeatToDisplay hideSeatFromDisplay(int row, int column) {
        SeatToDisplay result = seatsToDisplay.get((row - 1) * ROWS_NUMBER + column - 1);
        seatsBooked.add(result);
        return result;
    }

    public boolean isSeatValid(int row, int column) {
        return row > 0 && column > 0 && row <= ROWS_NUMBER && column <= COLUMNS_NUMBER;
    }
}