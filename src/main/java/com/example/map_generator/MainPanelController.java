package com.example.map_generator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable {

    @FXML
    TextField widthField;

    @FXML
    TextField heightField;

    @FXML
    TextField pixelSizeField;

    @FXML
    Slider scaleSlider;

    @FXML
    Label sliderChosenScaleLabel;
    float chosenScale;

    @FXML
    TextField seedField;

    @FXML
    Label noticeLabel;

    ArrayList<TextField> fields = new ArrayList<>();

    public void onBuildClick(ActionEvent event) throws Exception{
        if (areFieldsEmpty()){
            noticeLabel.setText("Some Fields have not been filled");
            noticeLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");


        } else {
            int canvasWidth = Integer.parseInt(widthField.getText());
            int canvasHeight = Integer.parseInt(heightField.getText());
            int pixelSize = Integer.parseInt(pixelSizeField.getText());
            float scale = chosenScale;
            long seed = Long.parseLong(seedField.getText());

            OpenSimplexGenerator testGen = new OpenSimplexGenerator(canvasWidth, canvasHeight, pixelSize, scale, seed, false);
            testGen.generateMap();

        }
    }

    public void onGrayBuildClick(ActionEvent event) throws Exception{

        if (areFieldsEmpty()){
            noticeLabel.setText("Some Fields have not been filled");
            noticeLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");


        } else {
            int canvasWidth = Integer.parseInt(widthField.getText());
            int canvasHeight = Integer.parseInt(heightField.getText());
            int pixelSize = Integer.parseInt(pixelSizeField.getText());
            float scale = chosenScale;
            long seed = Long.parseLong(seedField.getText());

            OpenSimplexGenerator testGen = new OpenSimplexGenerator(canvasWidth, canvasHeight, pixelSize, scale, seed, true);
            testGen.generateMap();

        }

    }

    public void noValueClick(ActionEvent event) throws Exception{
        OpenSimplexGenerator testGen = new OpenSimplexGenerator();
        testGen.generateMap();

    }




    public boolean areFieldsEmpty(){
        fields.add(widthField);
        fields.add(heightField);
        fields.add(pixelSizeField);
        fields.add(seedField);

        for(TextField field: fields){
            if (field.getText().isEmpty()){
                return true;
            }
        }

        return false;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        scaleSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                chosenScale = (float) (Math.round(scaleSlider.getValue() * 100.0) / 100.0);
                sliderChosenScaleLabel.setText(Float.toString(chosenScale) + "x");
            }
        });
    }


}