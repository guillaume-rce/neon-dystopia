package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

import java.beans.PropertyChangeEvent;

public class Transporter extends Block {
    private boolean isActivated = false;

    public Transporter(Coordinate coordinate) {
        super(coordinate, BlockId.TRANSPORTER, BlockType.ACTUATOR, false, HORIZONTAL);
    }

    /**
     * Called when the player enters the block.
     * <p>
     *     If the door is activated, it teleport the player.
     * </p>
     */
    @Override
    public void enter() {
        if (!isOnIt) {
            this.isOnIt = true;
            if (this.isActivated && this.teleportationTarget != null)
                this.level.teleport(this.teleportationTarget);
        }
    }

    @Override
    public boolean isTransporter() {
        return true;
    }

    @Override
    public void update() {
        super.update();
        if (this.activators.isEmpty())
            this.activate();
    }

    @Override
    protected void activate() {
        //this.nextTexture();
        this.isActivated = true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ((evt.getSource() instanceof Block) &&
                (this.activators.contains((Block) evt.getSource()))) {
            if ((evt.getPropertyName().equals("Block.isActivated")) &&
                    (evt.getNewValue()).equals(true) &&
                    (evt.getOldValue()).equals(false) &&
                    !this.activated.contains((Block) evt.getSource())) {
                this.activated.add((Block) evt.getSource());
                if (this.activated.size() == this.activators.size())
                    this.activate();
            }
        }
    }
}
