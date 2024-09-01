package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

/**
 * Lever : Class that represents a lever block. It used to know when the player has activated the lever.
 */
public class Lever extends Block {
    /**
     * Boolean to know if the lever is activated.
     */
    private boolean isActivated = false;

    /**
     * Constructor
     *
     * @param coordinate The coordinate of the block.
     */
    public Lever(Coordinate coordinate) {
        super(coordinate, BlockId.LEVER, BlockType.SENSOR, false, true, HORIZONTAL);
        this.setTextureAsClose();
    }

    /**
     * Called when the player has activated the lever. It will change the texture of the block.
     * @see #nextTexture()
     */
    @Override
    public void activate() {
        if (!this.isActivated) {
            this.isActivated = true;
            this.setTextureAsOpen();
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
