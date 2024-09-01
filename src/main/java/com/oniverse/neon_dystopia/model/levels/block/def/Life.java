package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * Life : Class that represents a life block. It used to give a life to the player.
 */
public class Life extends Block {
    /**
     * Boolean to know if the life has been picked up.
     */
    private boolean isActivated = false;
    
    /**
     * Constructor
     *
     * @param coordinate The coordinate of the block.
     */
    public Life(Coordinate coordinate) {
        super(coordinate, BlockId.LIFE, BlockType.VOID, false, true, HORIZONTAL);
    }

    /**
     * Called when the player has picked up the life. It will set the life as picked up, fire the property change and hide it.
     * @see PropertyChangeSupport#firePropertyChange(PropertyChangeEvent)
     */
    @Override
    public void activate() {
        if (!this.isActivated) {
            this.isActivated = true;
            this.setDisplay(false);
            this.propertyChangeSupport.firePropertyChange("Block.display",
                    true, false);
            this.level.getPlayer().addHealth(1);
        }
    }

    /**
     * Called when the player is on the life. It will set the life as on it, fire the property change and activate it.
     * @see PropertyChangeSupport#firePropertyChange(PropertyChangeEvent) 
     * @see #activate()
     */
    @Override
    public void enter() {
        if(!this.isOnIt) {
            this.isOnIt = true;
            this.activate();
        }
    }
}
