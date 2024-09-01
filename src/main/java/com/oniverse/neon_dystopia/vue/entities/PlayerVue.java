package com.oniverse.neon_dystopia.vue.entities;

import com.oniverse.neon_dystopia.model.entities.Player;
import com.oniverse.neon_dystopia.model.entities.PlayerTexturesPath;
import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.vue.utils.ImageVue;
import javafx.scene.Group;

import java.beans.PropertyChangeListener;

public class PlayerVue implements PropertyChangeListener {
    private final Group group;
    private Player player;
    private ImageVue imageVue;
    private double size;
    private PlayerTexturesPath currentTexture;
    private double x;
    private double y;

    public PlayerVue(Player player, double size){
        player.addListener(this);
        player.getPlayerTextures().addListener(this);
        this.player = player;
        this.size = size;
        this.group = new Group();
        this.currentTexture = player.getPlayerTextures().getCurrentTexture();
        this.x = player.getY();
        this.y = player.getX();
        this.draw();
    }

    public void draw(){
        this.group.getChildren().clear();
        imageVue = new ImageVue(
                this.currentTexture.getPath(),
                new Coordinate((int) (x - size/2), (int) (y - size - 3)),
                size * 2, size * 2);
        // resize the group

        this.group.getChildren().add(imageVue.getGroup());
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void setSize(double size){
        this.size = size;
        this.player.setBlockSize(size);
    }

    public Group getGroup(){
        return this.group;
    }

    public void move(double x, double y){
        this.x = x;
        this.y = y;
        System.out.println("x: " + x + " y: " + y);
        this.imageVue.move(x - size/2, y - size - 3);
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Player.vuePosition"))
            this.move(((double[]) evt.getNewValue())[1], ((double[]) evt.getNewValue())[0]);
        if (evt.getPropertyName().equals("PlayerTextures.currentTexture")) {
            this.currentTexture = (PlayerTexturesPath) evt.getNewValue();
            this.draw();
        }
        if (evt.getPropertyName().equals("PlayerTextures.reverse")) {
            System.out.println("reverse");
        }
    }

    public ImageVue getImageVue() {
        return imageVue;
    }

    public double getSize() {
        return size;
    }
}
