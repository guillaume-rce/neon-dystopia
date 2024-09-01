package com.oniverse.neon_dystopia.model.entities.inventory.object;

import com.oniverse.neon_dystopia.model.entities.inventory.Object;
import com.oniverse.neon_dystopia.model.entities.inventory.ObjectId;

public class Armor extends Object {
    public Armor(String name, String description) {
        super(ObjectId.ARMOR, name, description);
    }

    @Override
    public void use() {
        System.out.println("You used an armor");
    }
}
