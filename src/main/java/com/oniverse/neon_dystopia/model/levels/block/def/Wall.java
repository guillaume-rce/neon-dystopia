package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

public class Wall extends Block {
    public Wall(Coordinate coordinate) {
        super(coordinate, BlockId.WALL, BlockType.VOID, true, VERTICAL);
    }

    @Override
    public boolean haveMultipleTextures(){
        return true;
    }
}
