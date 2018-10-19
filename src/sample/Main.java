package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static MediaPlayer mediaPlayer;
    private static List<Media> mediaList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mediaList = new ArrayList<>();
        mediaList.add(new Media(getClass().getResource("1.mp3").toURI().toString()));
        mediaList.add(new Media(getClass().getResource("2.mp3").toURI().toString()));
        mediaList.add(new Media(getClass().getResource("3.mp3").toURI().toString()));
        mediaList.add(new Media(getClass().getResource("4.mp3").toURI().toString()));
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
