package com.oniverse.neon_dystopia.vue.levels;

import com.oniverse.neon_dystopia.model.levels.Level;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.maze.Maze;
import com.oniverse.neon_dystopia.vue.entities.PlayerVue;
import com.oniverse.neon_dystopia.vue.utils.ImageVue;
import javafx.scene.Group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class LevelVue implements PropertyChangeListener {
    private final Level level;
    private final PlayerVue playerVue;
    private final MazeVue mazeVue;
    private final Group group;
    private final int windowSize;
    private double displaySize;

    public LevelVue(Level level, int size) {
        this.level = level;
        this.level.addListener(this);
        this.level.getPlayer().addListener(this);
        this.windowSize = size;
        this.displaySize = (double) this.windowSize/this.level.getCurrentMaze().getWidth();
        // Vue
        this.playerVue = new PlayerVue(this.level.getPlayer(), this.displaySize);
        this.mazeVue = new MazeVue(this.level, this.displaySize);
        // Display
        this.group = new Group();
        this.draw();
    }

    public void draw(){
        this.group.getChildren().clear();

        // Draw a black background
        this.group.getChildren().add(
                new javafx.scene.shape.Rectangle(0, 0, this.windowSize, this.windowSize));

        // Draw the maze
        this.mazeVue.setSize(displaySize);
        this.group.getChildren().add(mazeVue.getGroup());

        // Draw the player
        this.playerVue.setSize(displaySize);
        this.mazeVue.verticalGroup.getChildren().add(this.playerVue.getGroup());
    }

    public Level getLevel() {
        return this.level;
    }

    public Group getGroup() {
        return this.group;
    }

    public PlayerVue getPlayerVue() {
        return this.playerVue;
    }

    public void playerMoved(double x, double y) {
        ArrayList<ImageVue> blockVue = this.mazeVue.getBlockVue(x, y);
        for (ImageVue imageVue : blockVue) {
            if (((Block) imageVue.tags.get(0)).orientation == Block.VERTICAL) {
                this.mazeVue.verticalGroup.getChildren().remove(playerVue.getGroup());
                if (!this.mazeVue.horizontalGroup.getChildren().contains(playerVue.getGroup()))
                    this.mazeVue.horizontalGroup.getChildren().add(playerVue.getGroup());
            } else {
                this.mazeVue.horizontalGroup.getChildren().remove(playerVue.getGroup());
                if (!this.mazeVue.verticalGroup.getChildren().contains(playerVue.getGroup()))
                    this.mazeVue.verticalGroup.getChildren().add(playerVue.getGroup());
            }
        }
    }

    public MazeVue getMazeVue() {
        return this.mazeVue;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Level.maze")){
            this.displaySize = (double) this.windowSize / ((Maze) evt.getNewValue()).getWidth();
            this.playerVue.setSize(this.displaySize);
            this.mazeVue.setSize(this.displaySize);
        }
        if (evt.getPropertyName().equals("Player.vuePosition"))
            this.playerMoved(this.level.getPlayer().getCenterY(), this.level.getPlayer().getMaxX());
    }
}
