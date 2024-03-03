package models;

public class Reservation {


    private int reservationID;
    private int places;
    private String category;
    private String date;
    private String startTime;
    private String endTime;
    private String status;
    private int duration;
    private int pricing;



    // Other fields and methods...



    public Reservation(int places, String category, String date, String startTime, String endTime, String status, int duration, int pricing) {
        this.places = places;
        this.category = category;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.duration = duration;
        this.pricing = pricing;
    }
    public Reservation() {
        this.places = places;
        this.category = category;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.duration = duration;
        this.pricing = pricing;
    }

    public Reservation(int reservationID,int places,String category,String date,String startTime,String endTime,String status,int duration,int pricing) {
        this.reservationID = reservationID;
        this.places = places;
        this.category = category;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.duration = duration;
        this.pricing = pricing;
    }

    public Reservation(String text, String text1, String text2, String text3, String text4, String text5, String text6) {
    }

    public Reservation(String text, String text1, String text2, String text3, String text4, String text5, String text6, String text7) {
    }

    public Reservation(String category) {
    }

    public Reservation(int places, String category, String date) {
        this.places=places;
        this.category=category;
        this.date=date;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public int getPlaces() {
        return this.places;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPricing() {
        return pricing;
    }

    public void setPricing(int pricing) {
        this.pricing = pricing;
    }



    @Override
    public String toString() {
        return "Reservation{" +
                "reservationID=" + reservationID +
                ", places=" + places +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status='" + status + '\'' +
                ", duration=" + duration +
                ", pricing=" + pricing +
                '}';
    }
}
