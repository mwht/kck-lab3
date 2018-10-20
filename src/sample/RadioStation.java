package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.logging.Logger;

public class RadioStation {
    private final double MAX_FREQ_ERROR = 0.1;
    private MediaPlayer stationPlayer;
    private Media stationMedia;
    private double volume;
    private double accuracy;
    private double eqPercentage;
    private boolean stationMute;
    private double stationFrequency;

    public enum RadioStationBand {
        BAND_U,
        BAND_D,
        BAND_S,
        BAND_K
    };

    private RadioStationBand stationBand;

    public RadioStation(String src, double stationFrequency, RadioStationBand stationBand) {
        try {
            this.stationMedia = new Media(getClass().getResource(src).toURI().toString());
            this.stationPlayer = new MediaPlayer(this.stationMedia);
            this.stationFrequency = stationFrequency;
            this.stationBand = stationBand;

            stationPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            stationPlayer.play();

            setVolume(0.5);
            setEQShape(50);
            setMute(true);
            tune(0.5, RadioStationBand.BAND_U);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMute(boolean muted) {
        if(stationPlayer != null) {
            stationMute = muted;
            stationPlayer.setMute(muted);
            if(!stationMute) stationPlayer.setVolume(accuracy*volume);
        }
    }

    public void setEQShape() {
        setEQShape(eqPercentage);
    }

    public void tune(double tuning, RadioStationBand currentBand) {
        if(currentBand.equals(stationBand)) {
            if (Math.abs(tuning - stationFrequency) < MAX_FREQ_ERROR) {
                setAccuracy(1 - (Math.abs(tuning - stationFrequency) / MAX_FREQ_ERROR));
            } else {
                setAccuracy(0);
            }
        } else {
            setAccuracy(0);
        }
    }

    public void setEQShape(double percentage) {
        // band 3 = 250Hz
        // band 7 = 4000Hz
        eqPercentage = percentage;
        if(percentage >= 50.0) {
            stationPlayer.getAudioEqualizer().getBands().get(3).setGain(0);
            stationPlayer.getAudioEqualizer().getBands().get(7).setGain((percentage-50.0)/(50.0/12.0)); // max 12dB
        } else {
            stationPlayer.getAudioEqualizer().getBands().get(3).setGain((50.0-percentage)/(50.0/12.0)); // max 12dB
            stationPlayer.getAudioEqualizer().getBands().get(7).setGain(0);
        }
    }

    private void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
        if(!stationMute) stationPlayer.setVolume(accuracy*volume);
        else stationPlayer.setVolume(0);
    }

    public void setVolume(double volume) {
        this.volume = volume;
        if(!stationMute) stationPlayer.setVolume(accuracy*volume);
        else stationPlayer.setVolume(0);

    }

}
