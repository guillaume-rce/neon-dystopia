package com.oniverse.neon_dystopia.vue.utils;

import com.oniverse.neon_dystopia.model.entities.Player;
import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.def.AmbientLight;
import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.shape.Line;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class AmbientLightVue implements PropertyChangeListener {
    private final Group group = new Group();
    private final AmbientLight ambientLight;
    private final int size;
    private final Player player;
    private final ArrayList<ImageVue> imageVues;
    private final ArrayList<Line> lines = new ArrayList<>();

    public AmbientLightVue(Player player, AmbientLight ambientLight, ArrayList<ImageVue> imageVues, int size) {
        this.ambientLight = ambientLight;
        this.imageVues = imageVues;
        this.size = size;
        this.player = player;
        player.addListener(this);
        this.draw();
    }

    public void draw() {
        this.group.getChildren().clear();
        this.lines.clear();
        Coordinate vueCoordinate = new Coordinate(
                ambientLight.getCoordinate().getY() * this.size + this.size / 2,
                ambientLight.getCoordinate().getX() * this.size + this.size / 2);
        for (double degree = 0; degree < 360; degree += 1) {
            double radian = Math.toRadians(degree);
            boolean isWall = false;
            for (double i = 0; i < ambientLight.getStrength(); i += 0.20) {
                double x = vueCoordinate.getX() + Math.cos(radian) * this.size / 2 * (i + 1);
                double y = vueCoordinate.getY() + Math.sin(radian) * this.size / 2 * (i + 1);
                for (ImageVue imageVue : this.imageVues)
                    if (imageVue.isSolid((int) x, (int) y) &&
                            (((Block) imageVue.tags.get(0)).id == BlockId.WALL ||
                                    ((Block) imageVue.tags.get(0)).id == BlockId.DOOR))
                        isWall = true;
                if (isWall)
                    break;
                Line line = new Line(vueCoordinate.getX(), vueCoordinate.getY(), x, y);
                line.setStrokeWidth(4);
                line.setStroke(ambientLight.getColor());
                line.setOpacity(1 - i / ambientLight.getStrength());
                this.lines.add(line);
                this.group.getChildren().add(line);
            }
        }
        // Smooth the group
        this.group.setEffect(new BoxBlur(10, 10, 5));
        this.group.setOpacity(0.2);
    }

    public Group getGroup() {
        return this.group;
    }

    public void clear() {
        this.group.getChildren().clear();
    }

    public AmbientLight getAmbientLight() {
        return this.ambientLight;
    }

    public void updatePlayerPosition() {
        // TODO: Add a better way to do this
        for (Line line : this.lines) {
            double lineSize = Math.sqrt(Math.pow(line.getEndX() - line.getStartX(), 2) +
                    Math.pow(line.getEndY() - line.getStartY(), 2));
            if (this.player.contains(line.getEndY(), line.getEndX()) &&
                    lineSize>=((double) this.ambientLight.getStrength() /4)*this.size) {
                this.group.getChildren().remove(line);
            } else if (!this.group.getChildren().contains(line))
                this.group.getChildren().add(line);
        }
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Player.vuePosition"))
            this.updatePlayerPosition();
    }
}
