package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

/**
 * Light : Class that represents a light block.
 */
public class LightBeams extends Block {
    /**
     * Constructor
     *
     * @param coordinate The coordinate of the block.
     */
    public LightBeams(Coordinate coordinate) {
        super(coordinate, BlockId.LIGHT_BEAMS, BlockType.ACTUATOR, false, HORIZONTAL);
    }
}
