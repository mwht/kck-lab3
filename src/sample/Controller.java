package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.logging.Logger;

public class Controller {
    @FXML
    private ProgressIndicator frequencyKnob;
    @FXML
    private Line tuningLine;
    @FXML
    private ToggleButton powerButton;
    private static boolean frequencyKnobMoving = false;
    private double deltaX = 0;

    @FXML
    private void onFrequencyKnobMouseMove(MouseEvent mouseEvent) {
        double vX = (mouseEvent.getX() - deltaX) / 100.0;
        double progress = frequencyKnob.getProgress() + vX;
        if (progress > 0.9999) progress = 0.9999;
        if (progress < 0.001) progress = 0.001;
        frequencyKnob.setProgress(progress);
        // warning code below hurts really bad !
        tuningLine.setLayoutX(198+(progress*(656-198)));
        Logger.getAnonymousLogger().info("mouse moved! ("+(frequencyKnob.getProgress() + vX)+")");
        deltaX = mouseEvent.getX();
    }

    @FXML
    private void onFrequencyKnobMouseDown(MouseEvent mouseEvent) {
        frequencyKnobMoving = true;
        Logger.getAnonymousLogger().info("mouse pressed!");
        deltaX = mouseEvent.getX();
    }

    @FXML
    private void onFrequencyKnobMouseUp(MouseEvent mouseEvent) {
        frequencyKnobMoving = false;
        Logger.getAnonymousLogger().info("mouse released!");

    }

    @FXML
    private void onPowerButtonToggle(ActionEvent actionEvent) {
        if(powerButton.isSelected()) {
            Logger.getAnonymousLogger().info("powered on");
        } else {
            Logger.getAnonymousLogger().info("unitra off");
        }
    }
}
