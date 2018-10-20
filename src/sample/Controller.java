package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Controller {
    @FXML
    private ProgressIndicator frequencyKnob;
    @FXML
    private Line tuningLine;
    @FXML
    private ToggleButton powerButton;

    /* TODO: list? */
    @FXML
    private Cylinder tuningU;
    @FXML
    private Cylinder tuningD;
    @FXML
    private Cylinder tuningS;
    @FXML
    private Cylinder tuningK;

    private static List<Cylinder> tuningGuides;


    private static boolean frequencyKnobMoving = false;
    private double deltaX = 0;
    private static PhongMaterial offMaterial = new PhongMaterial();
    private static PhongMaterial onMaterial = new PhongMaterial();

    static {
        offMaterial.setDiffuseColor(Color.WHITE);
        onMaterial.setDiffuseColor(Color.WHEAT);
    }

    {
        tuningGuides = new ArrayList<>();
        tuningGuides.add(tuningU);
        tuningGuides.add(tuningD);
        tuningGuides.add(tuningS);
        tuningGuides.add(tuningK);
    }

    @FXML
    private void onFrequencyKnobMouseMove(MouseEvent mouseEvent) {
        double vX = (mouseEvent.getX() - deltaX) / 1000.0;
        double progress = frequencyKnob.getProgress() + vX;
        if (progress > 0.9999) progress = 0.9999;
        if (progress < 0.001) progress = 0.001;
        frequencyKnob.setProgress(progress);
        // warning code below hurts really bad !
        tuningLine.setLayoutX(198+(progress*(656-198)));
        //Logger.getAnonymousLogger().info("mouse moved! ("+(frequencyKnob.getProgress() + vX)+")");
        deltaX = mouseEvent.getX();
    }

    @FXML
    private void onFrequencyKnobMouseDown(MouseEvent mouseEvent) {
        frequencyKnobMoving = true;
        //Logger.getAnonymousLogger().info("mouse pressed!");
        deltaX = mouseEvent.getX();
    }

    @FXML
    private void onFrequencyKnobMouseUp(MouseEvent mouseEvent) {
        frequencyKnobMoving = false;
        //Logger.getAnonymousLogger().info("mouse released!");

    }

    @FXML
    private void onBandSelectorUAction(ActionEvent actionEvent) {
        changeBand(0);
    }

    @FXML
    private void onBandSelectorDAction(ActionEvent actionEvent) {
        changeBand(1);
    }

    @FXML
    private void onBandSelectorSAction(ActionEvent actionEvent) {
        changeBand(2);
    }

    @FXML
    private void onBandSelectorKAction(ActionEvent actionEvent) {
        changeBand(3);
    }

    private void changeBand(int band) {
        Main.setCurrentBand(band, frequencyKnob);
        //Logger.getAnonymousLogger().info("Band set to " + band);
    }

    @FXML
    private void onPowerButtonToggle(ActionEvent actionEvent) {
        if(powerButton.isSelected()) {
            Logger.getAnonymousLogger().info("powered on");
            tuningU.setMaterial(onMaterial);
            tuningD.setMaterial(onMaterial);
            tuningS.setMaterial(onMaterial);
            tuningK.setMaterial(onMaterial);
            Main.setMute(false);
        } else {
            Logger.getAnonymousLogger().info("unitra off");
            tuningU.setMaterial(offMaterial);
            tuningD.setMaterial(offMaterial);
            tuningS.setMaterial(offMaterial);
            tuningK.setMaterial(offMaterial);
            Main.setMute(true);
        }
    }
}
