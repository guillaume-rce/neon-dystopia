package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

public class Spike extends Block {
    private boolean isActivated = false;

    public Spike(Coordinate coordinate){
        super(coordinate, BlockId.SPIKE, BlockType.VOID, false, true, HORIZONTAL);
        this.setTextureAsClose();
    }

    @Override
    public void activate() {
        if (!this.isActivated) {
            this.setTextureAsOpen();
            this.isActivated = true;
            this.level.getPlayer().removeHealth(1);
        }
    }

    @Override
    public void enter() {
        if(!this.isOnIt) {
            this.isOnIt = true;
            this.activate();
        }
    }

    @Override
    public void exit() {
        if(this.isOnIt) {
            this.isOnIt = false;
            this.deactivate();
        }
    }

    private void deactivate() {
        if (this.isActivated) {
            this.setTextureAsClose();
            this.isActivated = false;
        }
    }
}
