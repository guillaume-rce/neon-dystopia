package com.oniverse.neon_dystopia.model.levels.block;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.def.*;
import com.oniverse.neon_dystopia.model.levels.block.def.Void;

/**
 * This enum is used to define a block.
 * It contains the id of the block.
 */
public enum BlockId {
    VOID(0),
    WALL(1),
    START(2),
    END(3),
    LIFE(4),
    LEVER(5),
    KEY(6),
    DOOR(7),
    STAIRS(8),
    SPIKE(9),
    TRAPPED_SLAB(10),
    LIGHT_BEAMS(11),
    MIRROR(12),
    LIGHT_RECEPTOR(13),
    TRANSPORTER(14),
    FLOOR(15),
    AMBIENT_LIGHT(16);

    /**
     * The id of the block.
     */
    private final int value;

    /**
     * The constructor of the BlockId class.
     * It is used to define the id of the block.
     *
     * @param value The id of the block.
     */
    BlockId(int value){
        this.value = value;
    }

    /**
     * Get the id of the block.
     *
     * @return The id of the block.
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Get the Block from the id.
     *
     * @return The block.
     */
    public Block getBlockFromId(Coordinate coordinate) {
        switch(this.value){
            case 1:
                return new Wall(coordinate);
            case 2:
                return new Start(coordinate);
            case 3:
                return new End(coordinate);
            case 4:
                return new Life(coordinate);
            case 5:
                return new Lever(coordinate);
            case 6:
                return new Key(coordinate);
            case 7:
                return new Door(coordinate);
            case 8:
                return new Stairs(coordinate);
            case 9:
                return new Spike(coordinate);
            case 10:
                return new TrappedSlab(coordinate);
            case 11:
                return new LightBeams(coordinate);
            case 12:
                return new Mirror(coordinate);
            case 13:
                return new LightReceptor(coordinate);
            case 14:
                return new Transporter(coordinate);
            case 15:
                return new Floor(coordinate);
            case 16:
                return new AmbientLight(coordinate);
            default:
                return new Void(coordinate);
        }
    }

    /**
     * Get the BlockId from the id.
     *
     * @param id The id of the block.
     * @return The BlockId.
     */
    public static BlockId getBlockIdFromId(int id){
        for (BlockId blockId: BlockId.values()){
            if (blockId.getValue() == id)
                return blockId;
        }
        return VOID;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
