package models;

import java.sql.Timestamp;

public class Cart {
    private int cart_id;
    private int userID;
    private int reservationID;
    private int places;
    private int quantity;
    private Timestamp timestamp;

    public Cart(int cart_id, int userID, int reservationID, int places, int quantity, Timestamp timestamp) {
        this.cart_id = cart_id;
        this.userID = userID;
        this.reservationID = reservationID;
        this.places = places;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public Cart() {

    }

    @Override
    public String toString() {
        return "Cart{" +
                "cart_id=" + cart_id +
                ", userID=" + userID +
                ", reservationID=" + reservationID +
                ", places=" + places +
                ", quantity=" + quantity +
                ", timestamp=" + timestamp +
                '}';
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
