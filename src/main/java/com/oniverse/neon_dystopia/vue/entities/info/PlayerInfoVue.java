package com.oniverse.neon_dystopia.vue.entities.info;

import com.oniverse.neon_dystopia.model.entities.Player;
import com.oniverse.neon_dystopia.model.Coordinate;
import javafx.scene.Group;

import java.beans.PropertyChangeListener;

public class PlayerInfoVue implements PropertyChangeListener {
    private final Player player;
    private final HealthVue healthVue;
    private final Group group;

    public PlayerInfoVue(Player player, int size) {
        this.player = player;
        this.healthVue = new HealthVue(this.player,
                new Coordinate(size /2 - 5*40, size - 50), 40);
        this.group = new Group();
        this.draw();
    }

    public void draw(){
        this.group.getChildren().clear();

        // Draw the health
        this.group.getChildren().add(this.healthVue.getGroup());
    }

    public Player getPlayer() {
        return this.player;
    }

    public Group getGroup() {
        return this.group;
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        // TODO: implement
    }
}
