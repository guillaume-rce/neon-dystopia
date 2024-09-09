package com.oniverse.neon_dystopia.vue.mapSelection;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.utils.MapsGetter;
import com.oniverse.neon_dystopia.model.utils.XMLReader;
import com.oniverse.neon_dystopia.vue.utils.Vue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;

/**
 * The map selector view. It's a view that contains a list of all the maps.
 * @see Vue
 */
public class MapSelectorVue extends Vue {
    /**
     * The map getter. It's the object that will get all the maps.
     * @see MapsGetter
     */
    private final MapsGetter mapsGetter;
    /**
     * The list of all the map names.
     */
    private ListView<XMLReader> list = new ListView<>();

    /**
     * Creates a new map selector view.
     * @param coordinate the coordinate of the map selector view
     * @param height the height of the map selector view
     * @param width the width of the map selector view
     * @param mapsGetter the map getter
     */
    public MapSelectorVue(Coordinate coordinate, int height, int width, MapsGetter mapsGetter) {
        super(coordinate, height, width);
        this.mapsGetter = mapsGetter;
        this.draw();
    }

    /**
     * Draws the map selector view.
     */
    @Override
    public void draw(){
        this.group.getChildren().clear();
        list = new ListView<>();
        ObservableList<XMLReader> items = FXCollections.observableArrayList();
        items.addAll(this.mapsGetter.getPlayerMaps());

        list.setItems(items);
        list.setPrefWidth(this.boundingBox.getWidth());
        list.setPrefHeight(this.boundingBox.getHeight());
        list.setLayoutX(this.boundingBox.getMinX());
        list.setLayoutY(this.boundingBox.getMinY());
        this.group.getChildren().add(list);
    }

    public void update() {
        ObservableList<XMLReader> items = FXCollections.observableArrayList();
        items.addAll(this.mapsGetter.getPlayerMaps());
        list.setItems(items);
    }

    /**
     * Gets the list of all the maps.
     * @return the list of all the maps
     */
    public ListView<XMLReader> getList() {
        return list;
    }
}
