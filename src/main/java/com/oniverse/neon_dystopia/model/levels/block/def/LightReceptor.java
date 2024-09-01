package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

public class LightReceptor extends Block {
    public LightReceptor(Coordinate coordinate) {
        super(coordinate, BlockId.LIGHT_RECEPTOR, BlockType.SENSOR, false, HORIZONTAL);
    }
}
