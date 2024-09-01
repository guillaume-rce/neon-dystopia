package com.oniverse.neon_dystopia.model.entities.inventory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static final int MAX_SIZE = 10;
    private final ArrayList<Object> objects = new ArrayList<>();
    private int selected = 0;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public Inventory(Object... objects) {
        this.objects.addAll(List.of(objects));
    }

    public void addObject(Object object) {
        if (this.objects.size() < MAX_SIZE) {
            this.objects.add(object);
            this.support.firePropertyChange("Inventory.objects", null, objects);
        }
    }

    public void removeObject(Object object) {
        this.objects.remove(object);
        this.support.firePropertyChange("Inventory.objects", null, objects);
    }

    public Object getSelected() {
        return this.objects.get(this.selected);
    }

    public ArrayList<Object> getObjects() {
        return this.objects;
    }

    public void select(int index) {
        if (index < this.objects.size()) {
            this.support.firePropertyChange("Inventory.selected", selected, index);
            this.selected = index;
        }
    }

    public void next() {
        this.select((this.selected + 1) % this.objects.size());
    }

    public void previous() {
        this.select((this.selected - 1 + this.objects.size()) % this.objects.size());
    }

    public void addListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }
}
