package edu.duke.ece651.risk.client.view;

import edu.duke.ece651.risk.client.view.StyleMapping;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class LeaderCharacter {
    private final String name;
    private final int id;
    private final String imagePath;
    private final Circle circleButton;
    private final Label label;

    public LeaderCharacter (Circle circleButton, Label label, StyleMapping mapping){
        this.circleButton = circleButton;
        this.label = label;
        this.id = Integer.parseInt(circleButton.getId().substring(4));
        this.name = mapping.getCharacterNames(id-1);
        this.imagePath = "ui/static-images/figures/" + name.toLowerCase().replace(" ", "-") + ".jpg";
    }

    public void setImage(){
        //Image image = new Image(getClass().getClassLoader().getResource(imagePath).toString());
        Image image = new Image(imagePath);
        circleButton.setFill(new ImagePattern(image));
        circleButton.getStyleClass().add("character-"+String.valueOf(id));
    }

    public void setLabel(){
        label.setText(name);
    }
}
