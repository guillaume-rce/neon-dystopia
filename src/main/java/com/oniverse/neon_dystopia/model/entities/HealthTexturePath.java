package com.oniverse.neon_dystopia.model.entities;

/**
 * HealthTexturePath : Class that represents the path to the health texture.
 * Can be used to set different textures for the health of the player.
 */
public class HealthTexturePath {
    /**
     * The path to the health texture.
     */
    private static final String path = "src/main/resources/com/oniverse/neon_dystopia/textures/entities/info/heart.png";

    /**
     * Get the path to the health texture.
     * @return The path to the health texture.
     */
    public static String getPath() {
        return HealthTexturePath.path;
    }
}
