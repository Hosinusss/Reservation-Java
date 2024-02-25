package tests;
import java.sql.SQLException;

import models.Reservation;
import services.ReservationService;

public class Main {

    public static void main(String [] args){

        //MyDatabase d=MyDatabase.getInstance();

        ReservationService rs=new ReservationService();
        //try {
          //  rs.ajouter(new Reservation(1,"2024-02-01","20:42:09","22:42:09","Courant",2,50));
        //} catch (SQLException e) {
          //  System.out.println(e.getMessage());
        //}

        try {
            rs.modifier(new Reservation(
            ));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
           System.out.println(rs.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
