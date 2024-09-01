package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;
import com.oniverse.neon_dystopia.vue.designer.BlocksVue;

/**
 * Floor : Class that represents a floor block.
 */
public class Floor extends Block {
    /**
     * Constructor
     *
     * @param coordinate The coordinate of the block.
     */
    public Floor(Coordinate coordinate) {
        super(coordinate, BlockId.FLOOR, BlockType.VOID, false, HORIZONTAL);
    }

    /**
     * Getter to know if the block has multiple textures. (Used for the rendering)
     * @return True if the block has multiple textures, false otherwise.
     * @see BlocksVue#draw()
     */
    @Override
    public boolean haveMultipleTextures(){
        return true;
    }
}
