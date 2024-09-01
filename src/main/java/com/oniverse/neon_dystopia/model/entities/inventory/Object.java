package com.oniverse.neon_dystopia.model.entities.inventory;

public abstract class Object {
    private final ObjectId id;
    private final String name;
    private final String description;

    public Object(ObjectId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public abstract void use();
}
