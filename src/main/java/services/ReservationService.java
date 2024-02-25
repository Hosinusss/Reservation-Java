package services;
import utils.MyDatabase;
import models.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {
    private Connection connection;
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
}


