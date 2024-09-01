package com.oniverse.neon_dystopia.vue.levels;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.Level;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.def.AmbientLight;
import com.oniverse.neon_dystopia.model.levels.maze.Maze;
import com.oniverse.neon_dystopia.vue.utils.AmbientLightVue;
import com.oniverse.neon_dystopia.vue.utils.ImageVue;
import javafx.scene.Group;

import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeVue implements PropertyChangeListener {
    private final Group group;
    public final Group verticalGroup = new Group();
    public final Group horizontalGroup = new Group();
    public final ArrayList<AmbientLightVue> ambientLightsVue = new ArrayList<>();
    private final Group ambientLightGroup = new Group();
    private final Level level;
    private double size;
    private final ArrayList<ImageVue> imageVues = new ArrayList<>();

    public MazeVue(Level level, double size) {
        level.addListener(this);
        level.getPlayer().addListener(this);
        this.level = level;
        this.size = size;
        this.group = new Group();
        this.draw(level.getCurrentMaze());
    }

    public void draw(Maze maze) {
        this.group.getChildren().clear();
        this.horizontalGroup.getChildren().clear();
        this.verticalGroup.getChildren().clear();
        this.imageVues.clear();

        for (AmbientLightVue ambientLightVue: this.ambientLightsVue)
            ambientLightVue.clear();
        this.ambientLightsVue.clear();
        this.ambientLightGroup.getChildren().clear();

        System.gc();

        for (Block block : maze.getBlocks())
            if (block.isDisplay()) {
                ImageVue imageVue = new ImageVue(
                        block.getTexture().getTexture(),
                        new Coordinate((int) (block.getCoordinate().getY() * this.size),
                                (int) (block.getCoordinate().getX() * this.size)),
                        this.size, this.size, false, new ArrayList<>(){{ add(block);}});

                if (block.id == BlockId.FLOOR)
                    imageVue.setOpacity(0.8);

                this.imageVues.add(imageVue);
                if (block.orientation == Block.VERTICAL) {
                    this.verticalGroup.getChildren().add(imageVue.getGroup());
                } else {
                    this.horizontalGroup.getChildren().add(imageVue.getGroup());
                }
            }

        for (Block block: maze.getBlocks())
            if (block.id == BlockId.AMBIENT_LIGHT && block.isDisplay()) {
                AmbientLightVue ambientLightVue = new AmbientLightVue(this.level.getPlayer(),
                        (AmbientLight) block, this.imageVues, (int) this.size);
                this.ambientLightsVue.add(ambientLightVue);
                this.ambientLightGroup.getChildren().add(ambientLightVue.getGroup());
            }

        this.group.getChildren().addAll(this.horizontalGroup, this.verticalGroup, this.ambientLightGroup);
    }


    public void setSize(double size) {
        this.size = size;
    }

    public double getSize() {
        return this.size;
    }

    public Group getGroup() {
        return this.group;
    }

    public ArrayList<ImageVue> getBlockVue(double x, double y) {
        ArrayList<ImageVue> blockVue = new ArrayList<>();
        for (ImageVue imageVue : this.imageVues)
            if (imageVue.getBoundingBox().contains(x, y))
                blockVue.add(imageVue);
        return blockVue;
    }

    public ImageVue getBlockVue(Block block) {
        for (ImageVue imageVue : this.imageVues)
            if (imageVue.tags.get(0) == block)
                return imageVue;
        return null;
    }

    public void updateTextures(Block block) {
        for (ImageVue imageVue : this.imageVues)
            if (imageVue.tags.get(0) == block)
                try {
                    imageVue.draw(block.getTexture().getTexture());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
    }

    public void updateDisplay(Block block, boolean display) {
        for (ImageVue imageVue : this.imageVues)
            if (imageVue.tags.get(0) == block) {
                if (!display && this.imageVues.contains(imageVue)){
                    this.imageVues.remove(imageVue);
                    this.horizontalGroup.getChildren().remove(imageVue.getGroup());
                    this.verticalGroup.getChildren().remove(imageVue.getGroup());
                    return;
                }
            }

        if (display) {
            ImageVue imageVue = new ImageVue(
                    block.getTexture().getTexture(),
                    new Coordinate((int) (block.getCoordinate().getY() * this.size),
                            (int) (block.getCoordinate().getX() * this.size)),
                    this.size, this.size, false, new ArrayList<>(){{ add(block);}});
            this.imageVues.add(imageVue);
            if (block.orientation == Block.VERTICAL)
                this.verticalGroup.getChildren().add(imageVue.getGroup());
            else
                this.horizontalGroup.getChildren().add(imageVue.getGroup());
        }
    }

    public void updateAmbientLight(AmbientLight ambientLight, boolean display) {
        for (AmbientLightVue ambientLightVue : this.ambientLightsVue)
            if (ambientLightVue.getAmbientLight() == ambientLight)
                if (!display)
                    ambientLightVue.clear();
                else
                    ambientLightVue.draw();
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Level.maze"))
            this.draw((Maze) evt.getNewValue());
        else if (evt.getPropertyName().equals("Level.block.texture"))
            this.updateTextures((Block) evt.getSource());
        else if (evt.getPropertyName().equals("Level.block.display")) {
            this.updateDisplay((Block) evt.getSource(), (boolean) evt.getNewValue());
            if (((Block) evt.getSource()).id == BlockId.AMBIENT_LIGHT)
                this.updateAmbientLight((AmbientLight) evt.getSource(), (boolean) evt.getNewValue());
        }
    }
}
