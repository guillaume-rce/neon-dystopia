package com.oniverse.neon_dystopia.model.entities;

public enum PlayerTexturesPath {
    VAGABOND_IDLE("src/main/resources/com/oniverse/neon_dystopia/textures/entities/player/vagabond/vagabond-idle.gif"),
    VAGABOND_RUN("src/main/resources/com/oniverse/neon_dystopia/textures/entities/player/vagabond/vagabond-run.gif"),
    VAGABOND_RUN_END("src/main/resources/com/oniverse/neon_dystopia/textures/entities/player/vagabond/vagabond-run-end.gif"),
    VAGABOND_KNOCKBACK("src/main/resources/com/oniverse/neon_dystopia/textures/entities/player/vagabond/vagabond-knockback.gif"),
    VAGABOND_DEATH("src/main/resources/com/oniverse/neon_dystopia/textures/entities/player/vagabond/vagabond-death.gif");

    private final String path;

    PlayerTexturesPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
