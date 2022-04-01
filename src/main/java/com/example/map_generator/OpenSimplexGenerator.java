package com.example.map_generator;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class OpenSimplexGenerator {

    int canvasSizeX;
    int canvasSizeY;
    int pixelSize;
    double scale;
    long seed;
    boolean isPlain;

    //float frequency;
    int octaves = 10;
    float persistence = 1;
    float lacunarity = 0.5F;
    float amplitude = 0.5F;


    OpenSimplexNoise noise;

    Random rand = new Random();

    public OpenSimplexGenerator(int givenCanvasSizeX, int givenCanvasSizeY, int givenPixelSize, double givenScale, long givenSeed, boolean plain){
        this.canvasSizeX = givenCanvasSizeX;
        this.canvasSizeY = givenCanvasSizeY;

        pixelSize = givenPixelSize;

        scale = givenScale;

        seed = givenSeed;

        isPlain = plain;

        noise = new OpenSimplexNoise(seed);

    }

    public OpenSimplexGenerator(){
        this.canvasSizeX = 1080;
        this.canvasSizeY = 720;

        pixelSize = 5;

        scale = 0.02;

        seed = rand.nextInt(10000);

        isPlain = false;

        noise = new OpenSimplexNoise(seed);

    }

    //Function to get greyscale color
    public float getOpenSimplexGrayColor(double noiseValue) {

        if (noiseValue < -0.8) {
            return (float) 0.95;
        } else if (noiseValue < -0.6) {
            return (float) 0.9;
        } else if (noiseValue < -0.4) {
            return (float) 0.8;
        } else if (noiseValue < -0.2) {
            return (float) 0.7;
        } else if (noiseValue < 0) {
            return (float) 0.6;
        } else if (noiseValue < 0.2) {
            return (float) 0.5;
        } else if (noiseValue < 0.4) {
            return (float) 0.4;
        } else if (noiseValue < 0.6) {
            return (float) 0.3;
        } else if (noiseValue < 0.8) {
            return (float) 0.2;
        } else {
            return (float) 0.1;
        }

    }

    public Color getOpenSimplexColor(float noiseValue) {

        if (noiseValue < -0.8){
            return Color.rgb(0, 0, 153);
        } else if (noiseValue < -0.6){
            return Color.rgb(0, 0, 255);
        } else if (noiseValue < -0.4){
            return Color.rgb(0 , 102, 204);
        } else if (noiseValue < -0.2){
            return Color.rgb(0 , 128, 255);
        } else if (noiseValue < 0){
            return Color.rgb(204, 204, 0);
        } else if (noiseValue < 0.4){
            return Color.rgb(0 , 153, 0);
        } else if (noiseValue < 0.6){
            return Color.rgb(0 , 102, 0);
        } else if (noiseValue < 0.85) {
            return Color.rgb(128, 128, 128);
        } else if(noiseValue < 0.95) {
            return Color.rgb(153, 153, 153);
        } else {
            return Color.rgb(224, 224, 224);
        }

    }

    public void generateMap() throws Exception{

        Stage generationStage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, canvasSizeX, canvasSizeY);

        OpenSimplexNoise experimentalNoise = new OpenSimplexNoise(seed);

        for (int x = 0; x < canvasSizeX; x += pixelSize) {
            for (int y = 0; y < canvasSizeY; y += pixelSize) {

                Rectangle rect = new Rectangle(x, y, pixelSize, pixelSize);

                float frequencyScale = (float) scale;

                float amplitudeSetter = amplitude;

                //The actual value
                float value = 0;

                for (int i = 0; i < octaves; i++) {
                    value += amplitude * experimentalNoise.eval(x * frequencyScale, y * frequencyScale);
                    amplitudeSetter *= persistence;
                    frequencyScale *= lacunarity;


                }

                if (value > 1 ){
                    value = (float) 0.999;
                }




                System.out.println("value: " + value);


                if (isPlain){
                    rect.setFill(Color.rgb(0, 0, 0, getOpenSimplexGrayColor(value)));
                } else {
                    rect.setFill(getOpenSimplexColor(value));
                }


                root.getChildren().add(rect);
            }
        }

        generationStage.setScene(scene);
        generationStage.show();


    }




}
