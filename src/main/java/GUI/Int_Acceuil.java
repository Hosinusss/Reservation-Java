package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Int_Acceuil extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Int_Acceuil.fxml"));
        try {
            Parent root=loader.load();
            Scene scene=new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            /*System.out.println("loaded");*/
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }
}
