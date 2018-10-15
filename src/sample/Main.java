package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene rootScene = new Scene(root, 750, 300);
        primaryStage.setTitle("Unitra ślązak");
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
