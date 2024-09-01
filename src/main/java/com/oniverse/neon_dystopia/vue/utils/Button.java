package com.oniverse.neon_dystopia.vue.utils;

import com.oniverse.neon_dystopia.model.Coordinate;
import javafx.scene.Group;

/**
 * A simple button that can be clicked with a function draw that draws the button.
 */
public class Button {
    /**
     * The coordinate of the button.
     */
    public final Coordinate coordinate;
    /**
     * The width of the button.
     */
    public final int width;
    /**
     * The height of the button.
     */
    public final int height;
    /**
     * The text of the button.
     */
    public final String text;
    /**
     * The action of the button.
     */
    private final Runnable action;
    /**
     * The group of the button.
     */
    private final Group group;

    /**
     * Creates a new button. The font size is 20 and the radius is 30.
     *
     * @param coordinate the coordinate of the button
     * @param width  the width of the button
     * @param height the height of the button
     * @param text   the text of the button
     * @param action the action of the button
     */
    public Button(Coordinate coordinate, int width, int height, String text, Runnable action) {
        this(coordinate, width, height, text, action, 20, 30);
    }

    /**
     * Creates a new button.
     *
     * @param coordinate the coordinate of the button
     * @param width  the width of the button
     * @param height the height of the button
     * @param text   the text of the button
     * @param action the action of the button
     * @param fontSize the font size of the button
     * @param radius the radius of the button
     */
    public Button(Coordinate coordinate, int width, int height, String text,
                  Runnable action, int fontSize, int radius) {
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;
        this.text = text;
        this.action = action;
        this.group = new Group();
        this.draw(fontSize, radius);
    }

    /**
     * Draws the button.
     *
     * @param fontSize the font size of the button
     * @param radius the radius of the button
     */
    public void draw(int fontSize, int radius){
        this.group.getChildren().clear();
        javafx.scene.control.Button button = new javafx.scene.control.Button(this.text);
        button.setLayoutX(this.coordinate.getX());
        button.setLayoutY(this.coordinate.getY());
        button.setPrefWidth(this.width);
        button.setPrefHeight(this.height);
        button.setStyle("-fx-font-size: " + fontSize + "px");
        button.setStyle("-fx-font-color: #000000");
        button.setStyle("-fx-background-color: #ffffff");
        button.setStyle("-fx-background-radius: " + radius);
        button.setStyle("-fx-border-color: #ffffff");
        button.setStyle("-fx-border-radius: " + radius);
        if (this.action != null)
            button.setOnAction(event -> this.action.run());
        this.group.getChildren().add(button);
    }

    /**
     * Sets the style of the button.
     *
     * @param style the style of the button
     * @see javafx.scene.control.Button#setStyle(String)
     */
    public void setStyle(String style) {
        this.group.getChildren().get(0).setStyle(style);
    }

    /**
     * Returns the action of the button.
     *
     * @return the action of the button
     */
    public Runnable getAction() {
        return this.action;
    }

    /**
     * Returns the group of the button.
     *
     * @return the group of the button
     */
    public Group getGroup() {
        return this.group;
    }
}
