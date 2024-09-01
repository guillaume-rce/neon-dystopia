package com.oniverse.neon_dystopia.vue.entities.info;

import com.oniverse.neon_dystopia.model.entities.HealthTexturePath;
import com.oniverse.neon_dystopia.model.entities.Player;
import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.vue.utils.ImageVue;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class HealthVue implements PropertyChangeListener {
    private final Group group;
    private double size;
    private final ArrayList<ImageVue> hearts = new ArrayList<>();
    private final Coordinate coordinate;

    public HealthVue(Player player, Coordinate coordinate, double size) {
        player.addListener(this);
        this.size = size;
        this.group = new Group();
        this.coordinate = coordinate;
        this.draw(player.maxHealth);
    }

    public void draw(int maxHealth){
        this.group.getChildren().clear();
        Rectangle rectangle = new Rectangle(
                this.coordinate.getX(),
                this.coordinate.getY(),
                this.size * 10,
                this.size
        );
        rectangle.setStyle("-fx-fill: #ffffff");
        rectangle.setOpacity(0.5);
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        this.group.getChildren().add(rectangle);
        for (int i = 0; i < maxHealth; i++) {
            ImageVue heart = new ImageVue(
                    HealthTexturePath.getPath(),
                    new Coordinate(
                            (int) (this.coordinate.getX() + (i * this.size)),
                            this.coordinate.getY()
                    ),
                    this.size, this.size);
            this.hearts.add(heart);
            this.group.getChildren().add(
                    heart.getGroup());
        }
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Group getGroup() {
        return this.group;
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Player.health")) {
            int health = (int) evt.getNewValue();
            for (int i = 0; i < this.hearts.size(); i++) {
                if (i < health && !this.group.getChildren().contains(this.hearts.get(i).getGroup()))
                    this.getGroup().getChildren().add(this.hearts.get(i).getGroup());
                else if (i >= health && this.group.getChildren().contains(this.hearts.get(i).getGroup()))
                    this.getGroup().getChildren().remove(this.hearts.get(i).getGroup());
            }
        }
    }
}
