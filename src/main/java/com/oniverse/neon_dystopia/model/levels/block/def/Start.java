package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

public class Start extends Block {
    private boolean isActivated = false;

    public Start(Coordinate coordinate) {
        super(coordinate, BlockId.START, BlockType.SENSOR, false, true, HORIZONTAL);
    }

    @Override
    public void activate() {
        if (!this.isActivated) {
            this.isActivated = true;
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
