package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class AmbientLight extends Block {
    public static final ArrayList<Color> COLORS = new ArrayList<>(){{
        add(Color.LIGHTYELLOW);
        add(Color.LIGHTBLUE);
        add(Color.LIGHTGREEN);
        add(Color.LIGHTPINK);
        add(Color.LIGHTCORAL);
        add(Color.LIGHTSALMON);
        add(Color.LIGHTSKYBLUE);
        add(Color.DARKRED);
        add(Color.DARKBLUE);
        add(Color.DARKGREEN);
        add(Color.DARKORANGE);
        add(Color.DARKVIOLET);
        add(Color.MEDIUMVIOLETRED);
        add(Color.MEDIUMSPRINGGREEN);
        add(Color.MEDIUMSLATEBLUE);
        add(Color.MEDIUMORCHID);
        add(Color.MEDIUMAQUAMARINE);
        add(Color.YELLOW);
        add(Color.BLUE);
        add(Color.GREEN);
        add(Color.PINK);
        add(Color.ORANGE);
        add(Color.RED);
        add(Color.PURPLE);
        add(Color.BROWN);
        add(Color.BLACK);
        add(Color.WHITE);
    }};
    private int strength;
    private Color color;

    /**
     * Constructor
     */
    public AmbientLight(Coordinate coordinate) {
        super(coordinate, BlockId.AMBIENT_LIGHT, BlockType.ACTUATOR, false, HORIZONTAL);
        this.strength = 7;
        this.color = Color.LIGHTYELLOW;
    }

    public void setStrength(int strength) {
        this.propertyChangeSupport.firePropertyChange("AmbientLight.strength",
                this.strength, strength);
        this.strength = strength;
    }

    public void setColor(Color color) {
        this.propertyChangeSupport.firePropertyChange("AmbientLight.color",
                this.color, color);
        this.color = color;
    }

    public int getStrength() {
        return this.strength;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public boolean haveMultipleTextures() {
        return true;
    }

    @Override
    public void update() {
        super.update();
        if (this.activators.isEmpty())
            this.activate();
    }

    @Override
    protected void activate() {
        this.setDisplay(true);
        this.propertyChangeSupport.firePropertyChange("Block.display",
                false, true);
    }

    public void previousColor() {
        int index = COLORS.indexOf(this.color);
        if (index == 0)
            this.setColor(COLORS.get(COLORS.size() - 1));
        else
            this.setColor(COLORS.get(index - 1));
    }

    public void nextColor() {
        int index = COLORS.indexOf(this.color);
        if (index == COLORS.size() - 1)
            this.setColor(COLORS.get(0));
        else
            this.setColor(COLORS.get(index + 1));
    }

    public void increaseStrength() {
        if (this.strength < 10)
            this.setStrength(this.strength + 1);
    }

    public void decreaseStrength() {
        if (this.strength > 0)
            this.setStrength(this.strength - 1);
    }
}
