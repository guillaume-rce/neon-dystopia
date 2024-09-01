package com.oniverse.neon_dystopia.vue.utils;

import com.oniverse.neon_dystopia.model.Coordinate;
import javafx.geometry.BoundingBox;
import javafx.scene.Group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Vue is the abstract class that represents the view of an object.
 * It's very useful to draw any object on the screen.
 * It's also a PropertyChangeListener, so it can be notified of any change.
 * @see PropertyChangeListener
 */
public abstract class Vue implements PropertyChangeListener {
    /**
     * The group that contains all the elements of the view.
     */
    protected final Group group = new Group();
    /**
     * The bounding box of the view.
     */
    protected BoundingBox boundingBox;

    /**
     * The constructor of the class. It will create the bounding box of the view.
     * @param coordinate The coordinate of the view.
     * @param height The height of the view.
     * @param width The width of the view.
     */
    public Vue(Coordinate coordinate, int height, int width) {
        this.boundingBox = new BoundingBox(coordinate.getX(), coordinate.getY(), width, height);
    }

    /**
     * The draw method of the view.
     * It's an abstract method, so it must be implemented in the child class.
     */
    public abstract void draw();

    /**
     * The getter of the group.
     * @return The group of the views.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * The getter of the bounding box.
     * @return The bounding box of the views.
     */
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * The property change method of the view. Need to be overridden in the child class.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO
    }
}
