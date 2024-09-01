package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

import java.beans.PropertyChangeEvent;

/**
 * This class represents a door.
 * <p>
 *     A door is a block that can be activated by activators.
 *     When all the activators are activated, the door becomes un-solid and change its texture.
 * </p>
 * @see Block
 */
public class Door extends Block {
    private boolean isActivated = false;

    /**
     * Create a door.
     * The door is solid and visible by default.
     * @param coordinate The coordinate of the door.
     */
    public Door(Coordinate coordinate) {
        super(coordinate, BlockId.DOOR, BlockType.ACTUATOR, true, VERTICAL);
        this.setTextureAsClose();
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

    /**
     * Activate the door.
     * <p>
     *     The door is activated when all the activators are activated.
     *     When the door is activated, it becomes un-solid and invisible.
     * </p>
     * It also fires a property change event to notify the observers.
     *
     * @see java.beans.PropertyChangeSupport
     */
    @Override
    protected void activate() {
        this.setSolid(false);
        this.setTextureAsOpen();
        this.isActivated = true;
    }

    /**
     * This method is called when a property change event is fired from an activator.
     * It checks if the event is from an activator and if the activator is in the activators list.
     * If it's the case, it checks if the activator is activated and if it's not already in the activated list.
     * If it's the case, it adds the activator to the activated list.
     * If the activated list is the same size as the activators list, it calls the activate method.
     *
     * @see PropertyChangeEvent
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
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
