package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import sample.RadioStation.RadioStationBand;

public class Main extends Application {

    private static List<RadioStation> stations = new ArrayList<>();
    private static RadioStationBand selectedBand = RadioStationBand.BAND_U;

    public static void setCurrentBand(int currentBand, ProgressIndicator frequencyKnob) {
        selectedBand = RadioStationBand.values()[currentBand];
        stations.forEach(station -> station.tune(frequencyKnob.getProgress(), selectedBand));
    }

    public static void setMute(boolean mute) {
        stations.forEach(station -> station.setMute(mute));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stations.add(new RadioStation("1.mp3", 0.25, RadioStationBand.BAND_U));
        stations.add(new RadioStation("2.mp3", 0.43, RadioStationBand.BAND_D));
        stations.add(new RadioStation("3.mp3", 0.66, RadioStationBand.BAND_U));
        stations.add(new RadioStation("4.mp3", 0.12, RadioStationBand.BAND_D));

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene rootScene = new Scene(root, 750, 300);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Unitra ślązak");
        primaryStage.setScene(rootScene);

        Line tuningLine = (Line) rootScene.lookup("#tuningLine");
        tuningLine.setLayoutX(198+(0.5*(656-198)));

        ProgressIndicator frequencyKnob = (ProgressIndicator) rootScene.lookup("#frequencyKnob");
        frequencyKnob.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stations.forEach(station -> station.tune((double) newValue, selectedBand));
            }
        });

        Slider volumeCtrl = (Slider) rootScene.lookup("#volumeCtrl");
        volumeCtrl.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stations.forEach(station -> station.setVolume((double) newValue / 100.0));
            }
        });

        Slider equalizerCtrl = (Slider) rootScene.lookup("#equalizerCtrl");
        equalizerCtrl.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stations.forEach(station -> station.setEQShape((double) newValue));
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
