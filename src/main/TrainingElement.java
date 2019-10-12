package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TrainingElement {
    private BufferedImage image;
    private int value;

    TrainingElement(BufferedImage image, int value) {
//        this.image = main.Utils.resize(image, image.getWidth()/2, image.getHeight()/2);
        this.image = image;
        this.value = value;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getValue() {
        return value;
    }
}
