package services;

import models.Reservation;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{

    void ajouter(T t)throws SQLException;
    void modifier(T t)throws SQLException;
    void supprimer(int reservationID)throws SQLException;
    List<T> recuperer()throws SQLException;
    List<T> getReservationByPlaces() throws SQLException;

}
