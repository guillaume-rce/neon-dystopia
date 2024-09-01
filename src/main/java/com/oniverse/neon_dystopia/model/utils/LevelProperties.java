package com.oniverse.neon_dystopia.model.utils;

import java.beans.PropertyChangeSupport;

/**
 * This class is used to define the properties of a level. (name, version, author,...)
 */
public class LevelProperties {
    /**
     * The name of the level.
     */
    private String name;
    /**
     * The version of the level.
     */
    private String version;
    /**
     * The author of the level.
     */
    private String author;
    /**
     * The property change support of the level. It is used to notify the view when a property changes.
     */
    private final PropertyChangeSupport propertyChangeSupport;

    /**
     * The constructor of the LevelProperties class.
     * It is used to define the properties of a level with default values.
     */
    public LevelProperties() {
        this("Maze", "0.0", "Unknown");
    }

    /**
     * The constructor of the LevelProperties class.
     * It is used to define the properties of a level with given values.
     * @param name The name of the level.
     * @param version The version of the level.
     * @param author The author of the level.
     */
    public LevelProperties(String name, String version, String author) {
        this.name = name;
        this.version = version;
        this.author = author;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Getter for the name of the level.
     * @return The name of the level.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for the name of the level. It will fire a property change event.
     * @param name The new name of the level.
     */
    public void setName(String name) {
        this.propertyChangeSupport.firePropertyChange("LevelProperties.name",
                this.name, name);
        this.name = name;
    }

    /**
     * Getter for the version of the level.
     * @return The version of the level.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Setter for the version of the level. It will fire a property change event.
     * @param version The new version of the level.
     */
    public void setVersion(String version) {
        this.propertyChangeSupport.firePropertyChange("LevelProperties.version",
                this.version, version);
        this.version = version;
    }

    /**
     * Getter for the author of the level.
     * @return The author of the level.
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Setter for the author of the level. It will fire a property change event.
     * @param author The new author of the level.
     */
    public void setAuthor(String author) {
        this.propertyChangeSupport.firePropertyChange("LevelProperties.author",
                this.author, author);
        this.author = author;
    }

    /**
     * Add a listener to the property change support.
     * @param listener The listener to add.
     */
    public void addListener(java.beans.PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove a listener to the property change support.
     * @param listener The listener to remove.
     */
    public void removeListener(java.beans.PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "LevelProperties{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
