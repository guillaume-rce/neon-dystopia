package com.oniverse.neon_dystopia.model.levels.block;

/**
 * TexturesPath : Enum that represents the path to find the textures of the blocks.
 * Add a new path if you want to add a new block.
 * @see Textures
 */
public enum TexturesPath {
    WALL("wall/"),
    LEVER("lever/"),
    SPIKE("spike/"),
    FLOOR("floor/"),
    DOOR("door/"),
    HEART("heart/"),
    START("start/"),
    END("end/"),
    KEY("key/"),
    VOID("void/"),
    TRAPPED_SLAB("trapped_slab/"),
    STAIRS("stairs/"),
    AMBIENT_LIGHT("ambient_light/"),
    DEFAULT("default/");

    /**
     * The path to find the texture of the block.
     */
    private final String path;

    /**
     * Constructor: It will create a new TexturesPath object.
     *
     * @param path The path to find the texture of the block.
     */
    TexturesPath(String path) {
        this.path = path;
    }

    /**
     * This method is used to get the path to find the texture of the block.
     *
     * @return The path to find the texture of the block.
     */
    public String getPath() {
        return path;
    }

    /**
     * This method is used to get the path from the id of the block.
     *
     * @param id The id of the block.
     * @return The path to find the texture of the block.
     */
    public static String getTexturePath(BlockId id) {
        switch (id) {
            case WALL:
                return WALL.getPath();
            case LEVER:
                return LEVER.getPath();
            case SPIKE:
                return SPIKE.getPath();
            case DOOR:
                return DOOR.getPath();
            case LIFE:
                return HEART.getPath();
            case START:
                return START.getPath();
            case END:
                return END.getPath();
            case FLOOR:
                return FLOOR.getPath();
            case KEY:
                return KEY.getPath();
            case VOID:
                return VOID.getPath();
            case TRAPPED_SLAB:
                return TRAPPED_SLAB.getPath();
            case STAIRS:
                return STAIRS.getPath();
            case AMBIENT_LIGHT:
                return AMBIENT_LIGHT.getPath();
            default:
                return DEFAULT.getPath();
        }
    }
}
