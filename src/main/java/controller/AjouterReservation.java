package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import models.Reservation;
import services.ReservationService;

public class AjouterReservation {

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
    private TableColumn<?, ?> categoryColumn;

    @FXML
    private TextField categoryField;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TextField dateField;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<?, ?> durationColumn;

    @FXML
    private TextField durationField;

    @FXML
    private TableColumn<?, ?> endTimeColumn;

    @FXML
    private TextField endTimeField;

    @FXML
    private Button insertButton;

    @FXML
    private TableColumn<?, ?> pricingColumn;

    @FXML
    private TextField pricingField;

    @FXML
    private TableColumn<?, ?> reservationIdColumn;

    @FXML
    private TextField reservationIdField;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<?, ?> startTimeColumn;

    @FXML
    private TextField startTimeField;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private TextField statusField;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<?, ?> userIdColumn;

    @FXML
    void AfficherProfile(MouseEvent event) {

    }

    @FXML
    void ajouterReservation(ActionEvent event) {
        try {
            int reservationId = Integer.parseInt(reservationIdField.getText());
            int pricing = Integer.parseInt(pricingField.getText());
            Reservation reservation = new Reservation(categoryField.getText(), dateField.getText(), startTimeField.getText(),
                    endTimeField.getText(), statusField.getText(), durationField.getText(), pricingField.getText());

            ReservationService reservationService = new ReservationService();
            reservationService.ajouter(reservation);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La réservation a été ajoutée avec succès");
            alert.show();

            // Mettre à jour le tableau
            reservationTable.getItems().add(reservation);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrer un nombre valide pour l'ID de réservation et le prix");
            alert.show();
        }
    }



    @FXML
    void initialize() {


    }

}
