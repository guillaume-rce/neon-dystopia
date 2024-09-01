package com.oniverse.neon_dystopia.model.entities.inventory.object;

import com.oniverse.neon_dystopia.model.entities.inventory.Object;
import com.oniverse.neon_dystopia.model.entities.inventory.ObjectId;

public class Weapon extends Object {
    public Weapon(String name, String description) {
        super(ObjectId.WEAPON, name, description);
    }

    @Override
    public void use() {
        System.out.println("You used a weapon");
    }
}
