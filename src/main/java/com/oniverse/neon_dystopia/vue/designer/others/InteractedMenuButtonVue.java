package com.oniverse.neon_dystopia.vue.designer.others;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.vue.utils.Vue;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * This class is used to display the interacted menu in the top right corner of the designer.
 */
public class InteractedMenuButtonVue extends Vue {
    private Boolean clicked = false;
    private final InteractedMenuVue interactedMenuVue;

    /**
     * Creates a new interacted menu view. Height and width are 50.
     * @param coordinate the coordinate of the interacted menu view
     */
    public InteractedMenuButtonVue(Coordinate coordinate, int interactedMenuHeight,
                                   LevelDesigner levelDesigner) {
        super(coordinate, 30, 30);
        this.interactedMenuVue = new InteractedMenuVue(new Coordinate((int) this.boundingBox.getMinX(),
                (int) this.boundingBox.getMaxY() + 10), interactedMenuHeight, 350, levelDesigner);
        this.draw();
    }

    /**
     * Draw the interacted menu view.
     */
    @Override
    public void draw() {
        this.group.getChildren().clear();
        Rectangle rectangle = new Rectangle((int) this.boundingBox.getMinX(), (int) this.boundingBox.getMinY(),
                this.boundingBox.getWidth(), this.boundingBox.getHeight());
        rectangle.setFill(Color.WHITE);
        rectangle.setOpacity(0.8);
        rectangle.setArcHeight(10); rectangle.setArcWidth(10);
        this.group.getChildren().add(rectangle);
        // Create 3 horizontal lines
        int border = 5;
        Line line1 = new Line(this.boundingBox.getMinX() + border,
                this.boundingBox.getMinY() + this.boundingBox.getHeight()/4,
                this.boundingBox.getMaxX() - border,
                this.boundingBox.getMinY() + this.boundingBox.getHeight()/4);
        Line line2 = new Line(this.boundingBox.getMinX() + border,
                this.boundingBox.getMinY() + this.boundingBox.getHeight()/2,
                this.boundingBox.getMaxX() - border,
                this.boundingBox.getMinY() + this.boundingBox.getHeight()/2);
        Line line3 = new Line(this.boundingBox.getMinX() + border,
                this.boundingBox.getMinY() + 3*this.boundingBox.getHeight()/4,
                this.boundingBox.getMaxX() - border,
                this.boundingBox.getMinY() + 3*this.boundingBox.getHeight()/4);
        this.group.getChildren().addAll(line1, line2, line3);
    }

    /**
     * Set if the interacted menu view is hovered or not.
     * @param hovered true if the interacted menu view is hovered, false otherwise
     */
    public void setHovered(Boolean hovered) {
        if (hovered)
            this.group.setOpacity(1);
        else
            this.group.setOpacity(0.8);
    }

    /**
     * Set if the interacted menu view is clicked or not.
     */
    public void setClicked() {
        if (!clicked) {
            this.group.getChildren().clear();
            Rectangle rectangle = new Rectangle((int) this.boundingBox.getMinX(), (int) this.boundingBox.getMinY(),
                    this.boundingBox.getWidth(), this.boundingBox.getHeight());
            rectangle.setFill(Color.WHITE);
            rectangle.setOpacity(1);
            rectangle.setArcHeight(10); rectangle.setArcWidth(10);
            this.group.getChildren().add(rectangle);
            // Create a cross
            int border = 5;
            Line line1 = new Line(this.boundingBox.getMinX() + border,
                    this.boundingBox.getMinY() + border,
                    this.boundingBox.getMaxX() - border,
                    this.boundingBox.getMaxY() - border);
            Line line2 = new Line(this.boundingBox.getMinX() + border,
                    this.boundingBox.getMaxY() - border,
                    this.boundingBox.getMaxX() - border,
                    this.boundingBox.getMinY() + border);
            this.group.getChildren().addAll(line1, line2);

            this.interactedMenuVue.draw();
            this.group.getChildren().add(this.interactedMenuVue.getGroup());
            this.clicked = true;
        } else {
             this.clicked = false;
             this.interactedMenuVue.clear();
             this.draw();
        }
    }

    /**
     * Set the height of the interacted menu view.
     * @param height the height of the interacted menu view
     */
    public void setHeight(int height) {
        this.interactedMenuVue.setHeight(height);
        this.draw();
    }

    /**
     * Getter for the interacted menu view.
     * @return the interacted menu view
     */
    public InteractedMenuVue getInteractedMenuVue() {
        return this.interactedMenuVue;
    }

    /**
     * Return if the interacted menu view is clicked or not.
     * @return true if the interacted menu view is clicked, false otherwise
     */
    public boolean isClicked() {
        return this.clicked;
    }
}
