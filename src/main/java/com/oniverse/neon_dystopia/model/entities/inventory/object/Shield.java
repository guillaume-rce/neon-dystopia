package com.oniverse.neon_dystopia.model.entities.inventory.object;

import com.oniverse.neon_dystopia.model.entities.inventory.Object;
import com.oniverse.neon_dystopia.model.entities.inventory.ObjectId;

public class Shield extends Object {
    public Shield(String name, String description) {
        super(ObjectId.SHIELD, name, description);
    }

    @Override
    public void use() {
        System.out.println("You used a shield");
    }
}
