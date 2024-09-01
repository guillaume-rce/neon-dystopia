package com.oniverse.neon_dystopia.model.entities.inventory.object;

import com.oniverse.neon_dystopia.model.entities.inventory.Object;
import com.oniverse.neon_dystopia.model.entities.inventory.ObjectId;

public class Potion extends Object {
    public Potion(String name, String description) {
        super(ObjectId.POTION, name, description);
    }

    @Override
    public void use() {
        System.out.println("You used a potion");
    }
}
