package cinema;

import java.util.ArrayList;

public class Cinema {

    private static final int ROWS_NUMBER = 9;
    private static final int COLUMNS_NUMBER = 9;

    private int rowsNumber;
    private int columnsNumber;

    private ArrayList<Seat> seats;

    public Cinema() {
        rowsNumber = ROWS_NUMBER;
        columnsNumber = COLUMNS_NUMBER;
        this.seats = new ArrayList<>();

        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                seats.add(new Seat(i + 1, j + 1));
            }
        }
    }


    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
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


}