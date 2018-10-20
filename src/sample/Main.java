package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main extends Application {

    private static boolean radioMute;
    private static MediaPlayer mediaPlayer;
    private static List<Media> mediaList;
    private static int currentBand;
    private static double accuracy;
    private static double volume;
    private static double eqPercentage;

    public static void setMute(boolean muted) {
        if(mediaPlayer != null) {
            radioMute = muted;
            mediaPlayer.setMute(muted);
            if(!radioMute) mediaPlayer.setVolume(accuracy*volume);
        }
    }

    public static void setEQShape() {
        setEQShape(eqPercentage);
    }

    public static void setEQShape(double percentage) {
        // band 3 = 250Hz
        // band 7 = 4000Hz
        eqPercentage = percentage;
        if(percentage >= 50.0) {
            mediaPlayer.getAudioEqualizer().getBands().get(3).setGain(0);
            mediaPlayer.getAudioEqualizer().getBands().get(7).setGain((percentage-50.0)/(50.0/12.0));
        } else {
            mediaPlayer.getAudioEqualizer().getBands().get(3).setGain((50.0-percentage)/(50.0/12.0));
            mediaPlayer.getAudioEqualizer().getBands().get(7).setGain(0);
        }
    }

    public static void setCurrentBand(int band) {
        currentBand = band;
        if(mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(mediaList.get(band));
        mediaPlayer.setVolume(0); // make sure we don't play track before tuning
        mediaPlayer.play();
        setEQShape();
    }

    private static void setAccuracy(double mAccuracy) {
        accuracy = mAccuracy;
        if(!radioMute) mediaPlayer.setVolume(accuracy*volume);
    }

    public static void setVolume(double mVolume) {
        volume = mVolume;
        if(!radioMute) mediaPlayer.setVolume(accuracy*volume);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mediaList = new ArrayList<>();
        mediaList.add(new Media(getClass().getResource("1.mp3").toURI().toString()));
        mediaList.add(new Media(getClass().getResource("2.mp3").toURI().toString()));
        mediaList.add(new Media(getClass().getResource("3.mp3").toURI().toString()));
        mediaList.add(new Media(getClass().getResource("4.mp3").toURI().toString()));
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene rootScene = new Scene(root, 750, 300);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Unitra ślązak");
        primaryStage.setScene(rootScene);
        primaryStage.show();

        setCurrentBand(0);
        setMute(true);
        setVolume(0.5);

        ProgressIndicator frequencyKnob = (ProgressIndicator) rootScene.lookup("#frequencyKnob");
        frequencyKnob.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                final double[] CORRECT_FREQ_TABLE = {0.25,0.43,0.66,0.50};
                final double MAX_FREQ_ERROR = 0.10;

                Logger.getAnonymousLogger().info((double)newValue+" - "+CORRECT_FREQ_TABLE[currentBand]+"="+Math.abs((double)newValue - CORRECT_FREQ_TABLE[currentBand]));

                if(Math.abs((double)newValue - CORRECT_FREQ_TABLE[currentBand]) < MAX_FREQ_ERROR) {
                    setAccuracy(1-(Math.abs((double)newValue - CORRECT_FREQ_TABLE[currentBand])/MAX_FREQ_ERROR));
                }
            }
        });

        Slider volumeCtrl = (Slider) rootScene.lookup("#volumeCtrl");
        volumeCtrl.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setVolume((double)newValue / 100.0);
            }
        });

        Slider equalizerCtrl = (Slider) rootScene.lookup("#equalizerCtrl");
        equalizerCtrl.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double regulator = (double) newValue;
                setEQShape(regulator);
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
