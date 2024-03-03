package services;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import utils.MyDatabase;
import models.Reservation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {
    private final Connection connection;
    List<Reservation> cartReservations = new ArrayList<>();
    public ReservationService(){
        connection=MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        String req="insert into reservation(places,category,date, startTime,endTime,status,duration,pricing)"+
                "values('"+reservation.getPlaces()+"','"+reservation.getCategory()+"','"+reservation.getDate()+"','"+reservation.getStartTime()+
                "','"+reservation.getEndTime()+"','"+reservation.getStatus()+"','"+reservation.getDuration()+"',"+
                reservation.getPricing()+ ")";

        Statement statement=connection.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public void modifier(Reservation reservation) throws SQLException {

        String sql="UPDATE reservation SET places=?,category=?,date=?,startTime=?,endTime=?,status=?,duration=?,pricing=? WHERE reservationID=?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1, reservation.getPlaces());
        preparedStatement.setString(2, reservation.getCategory());
        preparedStatement.setString(3, reservation.getDate());
        preparedStatement.setString(4, reservation.getStartTime());
        preparedStatement.setString(5, reservation.getEndTime());
        preparedStatement.setString(6, reservation.getStatus());
        preparedStatement.setInt(7, reservation.getDuration());
        preparedStatement.setInt(8, reservation.getPricing()); // This should be setDouble if your pricing is a double
        preparedStatement.setInt(9, reservation.getReservationID());
        preparedStatement.executeUpdate();
    }


    @Override
    public void supprimer(int reservationID) throws SQLException {

        String req="DELETE FROM `reservation` WHERE reservationID=?";
        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setInt(1,reservationID);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Reservation> recuperer() throws SQLException {
        String sql="select * from reservation";
        Statement statement=connection.createStatement();

        ResultSet rs=statement.executeQuery(sql);
        List<Reservation> list=new ArrayList<>();
        while (rs.next()){
            Reservation r=new Reservation();
            r.setReservationID(rs.getInt("reservationID"));
            r.setPlaces(rs.getInt("places"));
            r.setCategory(rs.getString("category"));
            r.setDate(rs.getString("date"));
            r.setStartTime(rs.getString("startTime"));
            r.setEndTime(rs.getString("endTime"));
            r.setStatus(rs.getString("status"));
            r.setDuration(rs.getInt("duration"));
            r.setPricing(rs.getInt("pricing"));
            list.add(r);
    }
     return list;
}
    public Reservation getReservation(int reservationId) throws SQLException {
        String query = "SELECT * FROM reservation WHERE reservationID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reservationId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Reservation reservation = new Reservation();
            // Assuming you have setters in your Reservation class
            reservation.setPlaces(resultSet.getInt("places"));
            reservation.setCategory(resultSet.getString("category"));
            reservation.setDate(resultSet.getString("date"));
            reservation.setStartTime(resultSet.getString("startTime"));
            reservation.setEndTime(resultSet.getString("endTime"));
            reservation.setStatus(resultSet.getString("status"));
            reservation.setDuration(resultSet.getInt("duration"));
            reservation.setPricing(resultSet.getInt("pricing"));

            return reservation;
        } else {
            return null;
        }
    }
    public List<String> getCategories() throws SQLException {
        List<String> categoryList = new ArrayList<>();
        String sql = "SELECT category FROM reservation"; // Remplacez par votre requête réelle

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                String category = resultSet.getString("category");


                Reservation reservation = new Reservation(category);
                categoryList.add(category);
            }
        }

        return categoryList;
    }

    /*public List<Reservation> getPlacesWithCategories(int places) throws SQLException {
        List<Reservation> reservationList = new ArrayList<>();
        String sql = "SELECT places, category FROM reservation"; // Remplacez par votre requête réelle

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int places = resultSet.getInt("places");
                String category = resultSet.getString("category");


                Reservation reservation = new Reservation(places, category);
                reservationList.add(reservation);
            }
        }

        return reservationList;
    }*/
    public List<Reservation> getReservationByPlaces() throws SQLException {
        List<Reservation> reservationList = new ArrayList<>();
        String sql = "SELECT places, startTime, status FROM reservation"; // Remplacez par votre requête réelle

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int places = resultSet.getInt("places");
                String startTime = resultSet.getString("startTime");
                String status = resultSet.getString("status");

                Reservation reservation = new Reservation(places, startTime, status);
                reservationList.add(reservation);
            }
        }


        return reservationList;
    }


    public List<Reservation> getReservations() throws SQLException {
        List<Reservation> reservationList = new ArrayList<>();
        String sql = "SELECT category FROM reservation"; // Replace with your actual query

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String category = resultSet.getString("category");


                Reservation reservation = new Reservation();
                reservation.setCategory(category);

                reservationList.add(reservation);
            }
        }

        return reservationList;
    }
    public void generatePdfFromTableView(List<Reservation> reservations) {
        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("table_view.pdf"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            document.open();

            // Create a table with three columns
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);

            // Add table headers
            table.addCell("Reservation ID");
            table.addCell("Places");
            table.addCell("Category");
            table.addCell("Date");
            table.addCell("Start Time");
            table.addCell("End Time");
            table.addCell("Status");
            table.addCell("Duration");
            table.addCell("Pricing");

// Populate table with data
            for (Reservation reservation : reservations) {
                table.addCell(String.valueOf(reservation.getReservationID()));
                table.addCell(String.valueOf(reservation.getPlaces()));
                table.addCell(reservation.getCategory());
                table.addCell(reservation.getDate());
                table.addCell(reservation.getStartTime());
                table.addCell(reservation.getEndTime());
                table.addCell(reservation.getStatus());
                table.addCell(String.valueOf(reservation.getDuration()));
                table.addCell(String.valueOf(reservation.getPricing()));
            }




            // Add the table to the document
            document.add(new com.itextpdf.text.Paragraph("Reservation Data Table"));
            document.add(table);

            document.close();
            System.out.println("PDF created successfully. Check table_view.pdf");
        } catch (com.itextpdf.text.DocumentException e) {
            e.printStackTrace();
        }
    }
}


