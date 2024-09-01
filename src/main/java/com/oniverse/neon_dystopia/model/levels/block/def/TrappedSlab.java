package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

public class TrappedSlab extends Block {
    private boolean isActivated = false;
    private boolean destroyed = false;

    public TrappedSlab(Coordinate coordinate) {
        super(coordinate, BlockId.TRAPPED_SLAB, BlockType.VOID, false, HORIZONTAL);
        this.setTextureAsClose();
    }

    @Override
    public void activate() {
        if (!this.isActivated) {
            this.isActivated = true;
        }
    }

    @Override
    public void enter() {
        if(!this.isOnIt) {
            // TODO: Add animation
            this.isOnIt = true;
            this.activate();
            if (this.destroyed) {
                this.level.getPlayer().setHealth(0);
            }
        }
    }

    @Override
    public void exit() {
        if(this.isOnIt) {
            this.isOnIt = false;
            if (this.isActivated) {
                this.destroyed = true;
                this.setTextureAsOpen();
            }
        }
    }
}
