package GUI;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Reservation;
import services.ReservationService;

public class IntAcceuil {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Abon_SU;

    @FXML
    private Label Animaux;

    @FXML
    private Label Planning_SU;

    @FXML
    private Label Produits_SU;

    @FXML
    private Label Profile_SU;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField dateField;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField durationField;

    @FXML
    private TextField endTimeField;

    @FXML
    private Button insertButton;

    @FXML
    private TextField placesField;

    @FXML
    private TextField pricingField;

    @FXML
    private TextField reservationField;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField statusField;

    @FXML
    private Button updateButton;

    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, Integer> reservationIdColumn;
    @FXML
    private TableColumn<Reservation, Integer> placesColumn;
    @FXML
    private TableColumn<Reservation, String> categoryColumn;
    @FXML
    private TableColumn<Reservation, String> dateColumn;
    @FXML
    private TableColumn<Reservation, String> startTimeColumn;
    @FXML
    private TableColumn<Reservation, String> endTimeColumn;
    @FXML
    private TableColumn<Reservation, String> statusColumn;
    @FXML
    private TableColumn<Reservation, Integer> durationColumn;
    @FXML
    private TableColumn<Reservation, Integer> pricingColumn;



    @FXML
    void AfficherProfile(MouseEvent event) {

    }

    @FXML
    void modifyReservation(ActionEvent event) {
        try {
            int reservationID = Integer.parseInt(reservationField.getText());
            ReservationService reservationService = new ReservationService();
            Reservation reservation = new Reservation();

            // Get the data from the TextFields
            reservation.setReservationID(reservationID);
            reservation.setPlaces(Integer.parseInt(placesField.getText()));
            reservation.setCategory(categoryField.getText());
            reservation.setDate(dateField.getText());
            reservation.setStartTime(startTimeField.getText());
            reservation.setEndTime(endTimeField.getText());
            reservation.setStatus(statusField.getText());
            reservation.setDuration(Integer.parseInt(durationField.getText()));
            reservation.setPricing(Integer.parseInt(pricingField.getText()));

            // Update the Reservation in the database
            reservationService.modifier(reservation);

            // Update the Reservation in the TableView
            int index = reservationTable.getSelectionModel().getSelectedIndex();
            reservationTable.getItems().set(index, reservation);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La réservation a été mise à jour avec succès");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrer des nombres valides pour les champs appropriés");
            alert.show();
        }
    }

    @FXML
    void ajouterReservation(ActionEvent event) {
        try {
            int places = Integer.parseInt(placesField.getText());
            int pricing = Integer.parseInt(pricingField.getText());
            Reservation reservation = new Reservation(places, categoryField.getText(), dateField.getText(), startTimeField.getText(),
                    endTimeField.getText(), statusField.getText(), Integer.parseInt(durationField.getText()), pricing);

            ReservationService reservationService = new ReservationService();
            reservationService.ajouter(reservation);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La réservation a été ajoutée avec succès");
            alert.show();

            // Mettre à jour le tableau
            //reservationTable.getItems().add(reservation);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrer un nombre valide pour les places et le prix");
            alert.show();
        }
    }





    @FXML
    public void initialize() {
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        placesColumn.setCellValueFactory(new PropertyValueFactory<>("places"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        pricingColumn.setCellValueFactory(new PropertyValueFactory<>("pricing"));

        ReservationService reservationService = new ReservationService();
        try {
            reservationTable.setItems(FXCollections.observableArrayList(reservationService.recuperer()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        reservationTable.setRowFactory(tv -> {
            TableRow<Reservation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Reservation rowData = row.getItem();
                    // Load the data into the TextFields
                    reservationField.setText(String.valueOf(rowData.getReservationID()));
                    placesField.setText(String.valueOf(rowData.getPlaces()));
                    categoryField.setText(rowData.getCategory());
                    dateField.setText(rowData.getDate());
                    startTimeField.setText(rowData.getStartTime());
                    endTimeField.setText(rowData.getEndTime());
                    statusField.setText(rowData.getStatus());
                    durationField.setText(String.valueOf(rowData.getDuration()));
                    pricingField.setText(String.valueOf(rowData.getPricing()));
                }
            });
            return row;
        });
        deleteButton.setOnAction(event -> {
            Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                try {
                    // Delete the Reservation from the database
                    ReservationService reservationService1 = new ReservationService();
                    reservationService.supprimer(selectedReservation.getReservationID());

                    // Delete the Reservation from the TableView
                    reservationTable.getItems().remove(selectedReservation);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La réservation a été supprimée avec succès");
                    alert.show();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Veuillez sélectionner une réservation à supprimer");
                alert.show();
            }
        });

    }

}
