package com.oniverse.neon_dystopia.model.entities.inventory.object;

import com.oniverse.neon_dystopia.model.entities.inventory.Object;
import com.oniverse.neon_dystopia.model.entities.inventory.ObjectId;

public class Key extends Object {
    public Key(String name, String description) {
        super(ObjectId.KEY, name, description);
    }

    @Override
    public void use() {
        System.out.println("You used a key");
    }
}
