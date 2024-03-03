package services;
import models.Cart;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    public void addToCart(Cart cart) throws SQLException {
        Connection conn = MyDatabase.getInstance().getConnection();
        String query = "INSERT INTO cart (userID, reservationID, places, quantity) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, cart.getUserID());
        pstmt.setInt(2, cart.getReservationID());
        pstmt.setInt(3, cart.getPlaces());
        pstmt.setInt(4, cart.getQuantity());
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void removeFromCart(int cart_id) throws SQLException {
        Connection conn = MyDatabase.getInstance().getConnection();
        String query = "DELETE FROM cart WHERE cart_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, cart_id);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public List<Cart> getCartItems(int userID) throws SQLException {
        List<Cart> cartItems = new ArrayList<>();
        Connection conn = MyDatabase.getInstance().getConnection();
        String query = "SELECT * FROM cart WHERE userID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, userID);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Cart cart = new Cart();
            cart.setCart_id(rs.getInt("cart_id"));
            cart.setUserID(rs.getInt("userID"));
            cart.setReservationID(rs.getInt("reservationID"));
            cart.setPlaces(rs.getInt("places"));
            cart.setQuantity(rs.getInt("quantity"));
            cart.setTimestamp(rs.getTimestamp("timestamp"));
            cartItems.add(cart);
        }
        rs.close();
        pstmt.close();
        return cartItems;
    }
}
