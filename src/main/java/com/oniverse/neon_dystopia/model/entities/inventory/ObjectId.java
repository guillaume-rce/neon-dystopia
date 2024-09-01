package com.oniverse.neon_dystopia.model.entities.inventory;

import com.oniverse.neon_dystopia.model.entities.inventory.object.Key;
import com.oniverse.neon_dystopia.model.entities.inventory.object.Potion;
import com.oniverse.neon_dystopia.model.entities.inventory.object.Weapon;
import com.oniverse.neon_dystopia.model.entities.inventory.object.Armor;
import com.oniverse.neon_dystopia.model.entities.inventory.object.Shield;

public enum ObjectId {
    KEY("Key"),
    POTION("Potion"),
    WEAPON("Weapon"),
    ARMOR("Armor"),
    SHIELD("Shield");

    private final String name;

    ObjectId(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Object toObject(String name, String description) {
        switch (this) {
            case KEY:
                return new Key(name, description);
            case POTION:
                return new Potion(name, description);
            case WEAPON:
                return new Weapon(name, description);
            case ARMOR:
                return new Armor(name, description);
            case SHIELD:
                return new Shield(name, description);
            default:
                return null;
        }
    }

    public static ObjectId fromName(String name) {
        for (ObjectId objectId : ObjectId.values()) {
            if (objectId.getName().equals(name)) {
                return objectId;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
