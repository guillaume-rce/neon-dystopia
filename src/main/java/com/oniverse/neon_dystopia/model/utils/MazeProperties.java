package com.oniverse.neon_dystopia.model.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MazeProperties {
    private int id;
    private int width;
    private int height;
    private int layers;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public MazeProperties(int id) {
        this(id, 20, 20, 1);
    }

    public MazeProperties(int id, int width, int height, int layers) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.layers = layers;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
        propertyChangeSupport.firePropertyChange("MazeProperties.id", null, id);
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
        propertyChangeSupport.firePropertyChange("MazeProperties.width", null, width);
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
        propertyChangeSupport.firePropertyChange("MazeProperties.height", null, height);
    }

    public int getLayers() {
        return this.layers;
    }

    public void setLayers(int layers) {
        this.layers = layers;
        propertyChangeSupport.firePropertyChange("MazeProperties.layers", null, layers);
    }

    public void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "MazeProperties{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", layers=" + layers +
                '}';
    }
}
