package cinema;

public class Seat {

    private int row;
    private int column;

    private int price;

    private boolean isBooked;

    private String token;


    public Seat (int row, int column) {
        this.row = row;
        this.column = column;
        if (row < 5) {
            this.price = 10;
        } else {
            this.price = 8;
        }
        this.isBooked = false;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void tokenReset() {
        this.token = null;
    }
}