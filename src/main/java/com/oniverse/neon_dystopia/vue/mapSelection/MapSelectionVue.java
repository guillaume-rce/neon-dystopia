package com.oniverse.neon_dystopia.vue.mapSelection;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.utils.MapsGetter;
import com.oniverse.neon_dystopia.vue.utils.Vue;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MapSelectionVue extends Vue {
    private MapSelectorVue mapSelectorVue;
    private final MapsGetter mapsGetter;

    public MapSelectionVue(int height, int width, MapsGetter mapsGetter) {
        super(new Coordinate(0, 0), height, width);
        this.mapsGetter = mapsGetter;
        this.draw();
    }

    @Override
    public void draw() {
        this.group.getChildren().clear();

        // ---- Background ----
        Rectangle background = new Rectangle(
                (int) this.boundingBox.getMinX(), (int) this.boundingBox.getMinY(),
                (int) this.boundingBox.getWidth(), (int) this.boundingBox.getHeight());
        this.group.getChildren().add(background);

        // ---- Title ----
        Text title = new Text("Map selection");
        title.setX(this.boundingBox.getMinX() + 20);
        title.setY(this.boundingBox.getMinY() + 50);
        title.setFont(new Font(40));
        title.setFill(Color.WHITE);
        this.group.getChildren().add(title);

        // ---- Map selector ----
        this.mapSelectorVue = new MapSelectorVue(
                new Coordinate((int) (this.boundingBox.getMinX() + 20), (int) (this.boundingBox.getMinY() + 70)),
                (int) (this.boundingBox.getWidth() - 90), (int) (this.boundingBox.getHeight() - 40),
                mapsGetter);
        this.group.getChildren().add(this.mapSelectorVue.getGroup());
    }

    public MapSelectorVue getMapSelectorVue() {
        return this.mapSelectorVue;
    }

    public MapsGetter getMapsGetter() {
        return this.mapsGetter;
    }
}
