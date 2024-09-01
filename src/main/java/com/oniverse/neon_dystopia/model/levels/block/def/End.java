package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

/**
 * End : Class that represents the end of the level. It used to know when the player has finished the level.
 */
public class End extends Block {
    /**
     * Constructor
     *
     * @param coordinate The coordinate of the block.
     */
    public End(Coordinate coordinate) {
        super(coordinate, BlockId.END, BlockType.SENSOR, false, true, HORIZONTAL);
    }

    @Override
    public void enter() {
        super.enter();
        if (this.level != null)
            try{
                this.level.nextLevel();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
}
