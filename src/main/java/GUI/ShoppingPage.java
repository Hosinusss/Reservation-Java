package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Reservation;
import services.ReservationService;
import utils.MyDatabase;

import javafx.scene.image.ImageView;

public class ShoppingPage {
    // The ID of the currently logged-in user
    private int userID; // Initialize this with the actual user ID when the user logs in

    // The HBox in the Cart.fxml file
    // @FXML
    //private VBox cartitems; // Initialize this when you load the Cart.fxml file

    @FXML
    private AnchorPane cartPane;
    @FXML
    private Button proceedToCheckoutButton;

    @FXML
    private ImageView cartimg;
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
    private Label Profile_SU1;

    @FXML
    private TextField reservationField;


    @FXML
    void AfficherProfile(MouseEvent event) {

    }


    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private VBox courseContainer;
    @FXML
    private VBox courseBox;
    @FXML
    private VBox cartitems; // Initialize this when you load the Cart.fxml file


    private final int itemCount = 0;

    private List<CartItem> getCartItemsFromDatabase() {
        // Replace with your actual data retrieval logic
        // Return a list of CartItem objects
        // For demonstration purposes, create a sample list:
        return List.of(
                new CartItem(20, 1, 30), // Product A
                new CartItem(30, 2, 50) // Product B
        );
    }

    // Initialize the ChoiceBox and load categories


    // Example CartItem class (replace with your actual data model)
    static class CartItem {
        private final int places;
        private final int quantity;
        private final int pricing;

        public CartItem(int places, int quantity, int pricing) {
            this.places = places;
            this.quantity = quantity;
            this.pricing = pricing;
        }

        // Getters for places, quantity, and pricing
        public int getPlaces() {
            return places;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getTotalPrice() {
            return pricing + pricing;
        }
    }


    private void displayCourses(String selectedCategory) {
        // Clear the courseContainer
        courseContainer.getChildren().clear();
        try {
            // Get a connection to the database
            Connection conn = MyDatabase.getInstance().getConnection();
            // Create a statement
            Statement stmt = conn.createStatement();
            // Execute a query to get the courses in the selected category
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservation WHERE category = '" + selectedCategory + "'");
            // Loop through the result set and create a UI component for each course
            while (rs.next()) {
                VBox courseBox = new VBox();
                courseBox.setStyle("-fx-border-color: #ccc; -fx-padding: 10px; -fx-margin: 10px; -fx-background-color: #506e8e;");
                courseBox.getChildren().add(new Label("Date: " + rs.getString("date")));
                courseBox.getChildren().add(new Label("Places: " + rs.getInt("places")));
                courseBox.getChildren().add(new Label("Duration: " + rs.getInt("duration")));
                courseBox.getChildren().add(new Label("Pricing: " + rs.getInt("pricing")+"€"));

                Button addToCartButton = new Button("Add to Cart");
                addToCartButton.setStyle("-fx-background-color: #0460c2; -fx-text-fill: white;");
                final int reservationID = rs.getInt("reservationID");

                addToCartButton.setOnAction(e -> {
                    try {
                        addToCart(reservationID, this.cartitems);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                Button proceedToCheckoutButton = new Button("Proceed to Checkout");
                proceedToCheckoutButton.setStyle("-fx-background-color: #0460c2; -fx-text-fill: white;");
                proceedToCheckoutButton.setOnAction(e -> processPayment());

                courseBox.getChildren().add(addToCartButton);
                courseBox.getChildren().add(proceedToCheckoutButton);

                courseContainer.getChildren().add(courseBox);
            }
            // Close the statement and result set
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    private void addToCart(int reservationID, VBox cartitems) throws SQLException {
        // Get a connection to the database
        Connection conn = MyDatabase.getInstance().getConnection();

        // Create a statement
        Statement stmt = conn.createStatement();

        // Execute a query to get the reservation details
        ResultSet rs = stmt.executeQuery("SELECT * FROM reservation WHERE reservationID = " + reservationID);
        if (rs.next()) {
            int places = rs.getInt("places");

            // Close the statement and result set
            rs.close();
            stmt.close();

            // Insert the reservation into the Cart database
            String query = "INSERT INTO cart (reservationID, places, quantity, timestamp) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reservationID);
            pstmt.setInt(2, places);
            pstmt.setInt(3, 1); // replace 1 with the actual quantity
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();

            // Update the cartitems HBox in the Cart.fxml file
            cartitems = new VBox();
            cartitems.getChildren().add(new Label("Reservation ID: " + reservationID));
            cartitems.getChildren().add(new Label("Places: " + places));
            cartitems.getChildren().add(new Label("Quantity: 1")); // replace 1 with the actual quantity

            // Show a success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Added to cart successfully!");
            successAlert.showAndWait();
        }
    }


    private void proceedToCheckout() {
        // Implement the logic to proceed to checkout
    }


    // Event handler for "Add to Cart" button
    @FXML
    private void addToCartClicked() {
        // Add selected course to Cart (update itemCount)
        // Update Cart label
    }

    // Event handler for "Proceed to Checkout" button
    @FXML
    private void proceedToCheckoutClicked() {
        processPayment();
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setHeaderText("Payment successful!");
        successAlert.showAndWait();
    }

    private void processPayment() {
        try {
// Set your secret key here
            Stripe.apiKey = "sk_test_51OqHSwLgc390JJd8ROSmj5KdACOWpmxPfmOcdVZlZExwJv0QpN3uRS1sIIZ5ZVaTQnxkuU07xAA0SPMxbl5sVGF300HIaP2hRe";

// Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(1000L) // Amount in cents (e.g., $10.00)
                    .setCurrency("eur")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

// If the payment was successful, display a success message
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
            // Show a success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Payment successful!");
            successAlert.showAndWait();
        } catch (StripeException e) {
// If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }


    public void cartopen(MouseEvent mouseEvent) {
        try {
            // Load the cart.fxml view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cart.fxml"));
            AnchorPane cartView = loader.load();

            // Create a new scene with the cart view
            Scene cartScene = new Scene(cartView);

            // Get the window (stage) associated with the cartPane
            Stage primaryStage = (Stage) cartPane.getScene().getWindow();
            primaryStage.setScene(cartScene);
            primaryStage.setTitle("Your Cart"); // Set an appropriate title

            // Show the cart view
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions related to loading the cart view
        }
    }


    public void initialize() {

        List<String> validCategories = Arrays.asList("Gym", "Boxing", "Cardio", "Crossfit", "Cycling", "Dance", "Pilates", "Yoga");

        // Populate the ChoiceBox with the categories
        categoryChoiceBox.setItems(FXCollections.observableArrayList(validCategories));

        // Add a listener to the categoryChoiceBox
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Call a method to display courses based on the selected category
                displayCourses(newSelection);
            }
        });

        List<CartItem> cartItems = getCartItemsFromDatabase();

        for (CartItem cartItem : cartItems) {
            Label itemLabel = new Label();
            itemLabel.setText(String.format(
                    "Product: Places %d, Quantity %d, Total Price: $%d",
                    cartItem.getPlaces(),
                    cartItem.getQuantity(),
                    cartItem.getTotalPrice()
            ));

            // Add CSS styling to the label
            itemLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3a3a3a; -fx-padding: 10px;");
            // Add the label to the VBox
            cartitems.getChildren().add(itemLabel);
            // Add an action to the proceedToCheckoutButton to call the processPayment method when clicked
            //proceedToCheckoutButton.setOnAction(e -> processPayment());
            //Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            //successAlert.setHeaderText("Payment successful!");
          //  successAlert.showAndWait();
        }


    }


    @FXML
    void proceedToCheckoutClicked(ActionEvent event) {

    }


}

