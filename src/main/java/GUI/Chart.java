package GUI;


import java.net.URL;
import java.sql.SQLException;

import java.util.List;

import javafx.fxml.FXML;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import javafx.collections.FXCollections;

import models.Reservation;
import services.ReservationService;

public class Chart {


    @FXML
    private CategoryAxis xAxis;

    @FXML
    private URL location;

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    ReservationService reservationService = new ReservationService();


    @FXML
    public void initialize() {
        try {
            List<Reservation> reservations = reservationService.recuperer(); // Retrieve users from the database


            int full = 0;
            int available = 0;
            int expiring = 0;

            for (Reservation reservation : reservations) {
                int places = reservation.getPlaces();
                if (places>5) {
                    available++;
                } else if (places<5) {
                    expiring++;
                } else if (places==0) {
                    full++;
                }
            }

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("full", full));
            series.getData().add(new XYChart.Data<>("available", available));
            series.getData().add(new XYChart.Data<>("expiring", expiring));
            series.setName("Course places:");
            xAxis.setCategories(FXCollections.observableArrayList("full", "available", "expiring"));
            barChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
