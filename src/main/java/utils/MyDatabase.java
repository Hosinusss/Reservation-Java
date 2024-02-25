package utils;

import java.sql.*;

public class MyDatabase {
    final String URL="jdbc:mysql://localhost:3306/esprit3a17";
    final String USER="root";
    final String PASS="";
    private Connection connection;
    private static MyDatabase instance;
   private MyDatabase(){
        try {
            connection=DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connected!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MyDatabase getInstance() {
        if (instance == null)
            instance = new MyDatabase();
            return instance;


    }
       public Connection getConnection(){
           return connection;
        }


}
