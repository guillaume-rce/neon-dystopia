package com.oniverse.neon_dystopia.model.levels.block.def;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

import java.beans.PropertyChangeEvent;

public class Stairs extends Block {
    private boolean isActivated = false;

    public Stairs(Coordinate coordinate) {
        super(coordinate, BlockId.STAIRS, BlockType.ACTUATOR, false, false, HORIZONTAL);
    }

    /**
     * Called when the player enters the block.
     * <p>
     *     If the door is activated, it calls the teleport method.
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
     * Activate the strairs.
     * <p>
     *     The strairs is activated when all the activators are activated.
     *     When the strairs is activated, it becomes solid and invisible.
     *     Then, the player can go to the next floor.
     * </p>
     * It also fires a property change event to notify the observers.
     *
     * @see java.beans.PropertyChangeSupport
     */
    @Override
    protected void activate() {
        this.setDisplay(true);
        this.propertyChangeSupport.firePropertyChange("Block.display",
                false, true);
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
