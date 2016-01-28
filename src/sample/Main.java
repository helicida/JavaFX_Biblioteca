package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by 46465442z on 24/01/16.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Biblioteca");
        primaryStage.setScene(new Scene(root, 400, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
