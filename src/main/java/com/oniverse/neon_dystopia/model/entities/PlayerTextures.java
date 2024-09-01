package com.oniverse.neon_dystopia.model.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayerTextures {
    private PlayerTexturesPath currentTexture;
    private boolean reverse;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public PlayerTextures() {
        this.currentTexture = PlayerTexturesPath.VAGABOND_IDLE;
    }

    public PlayerTexturesPath getCurrentTexture() {
        return this.currentTexture;
    }

    public void setCurrentTexture(PlayerTexturesPath currentTexture) {
        this.support.firePropertyChange("PlayerTextures.currentTexture",
                this.currentTexture, currentTexture);
        this.currentTexture = currentTexture;
    }

    public void addListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

    public void setReverse(boolean reverse) {
        this.support.firePropertyChange("PlayerTextures.reverse",
                this.reverse, reverse);
        this.reverse = reverse;
    }

    public boolean isReverse() {
        return this.reverse;
    }
}
