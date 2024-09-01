package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * Key : Class that represents a key block. It used to know when the player has picked up the key.
 */
public class Key extends Block {
    /**
     * Boolean to know if the key has been picked up.
     */
    private boolean isActivated = false;

    /**
     * Constructor
     *
     * @param coordinate The coordinate of the block.
     */
    public Key(Coordinate coordinate) {
        super(coordinate, BlockId.KEY, BlockType.SENSOR, false, true, HORIZONTAL);
    }

    /**
     * Called when the player has picked up the key. It will set the key as picked up, fire the property change and hide it.
     * @see PropertyChangeSupport#firePropertyChange(PropertyChangeEvent)
     */
    @Override
    public void activate() {
        if (!this.isActivated) {
            this.isActivated = true;
            this.setDisplay(false);
            this.propertyChangeSupport.firePropertyChange("Block.display",
                    true, false);
            this.propertyChangeSupport.firePropertyChange(
                    "Block.isActivated", false, true);
        }
    }

    @Override
    public void enter() {
        if(!this.isOnIt) {
            this.isOnIt = true;
            this.activate();
        }
    }
}
