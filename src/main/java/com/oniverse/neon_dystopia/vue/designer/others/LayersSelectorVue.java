package com.oniverse.neon_dystopia.vue.designer.others;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.vue.utils.Vue;
import javafx.scene.Group;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;

public class LayersSelectorVue extends Vue {
    private Spinner<Integer> layersSpinner;

    public LayersSelectorVue(Coordinate coordinate) {
        super(coordinate, 20, 40);
        this.draw();
    }

    @Override
    public void draw() {
        this.group.getChildren().clear();
        Group layersGroup = new Group();
        Text layersText = new Text("Active layer:");
        layersText.setTranslateY(18);
        layersGroup.getChildren().add(layersText);

        this.layersSpinner = new Spinner<>(0, 10, 0,1);
        this.layersSpinner.setPrefWidth(50);
        this.layersSpinner.setLayoutX(68);
        this.layersSpinner.setEditable(true);

        layersGroup.setLayoutX(this.getBoundingBox().getMinX());
        layersGroup.setLayoutY(this.getBoundingBox().getMinY());
        layersGroup.getChildren().add(this.layersSpinner);
        this.group.getChildren().add(layersGroup);
    }

    public void setMinMax(int min, int max) {
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) this.layersSpinner.getValueFactory()).setMax(max);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) this.layersSpinner.getValueFactory()).setMin(min);
        this.layersSpinner.setDisable(min == max);
    }

    public void setLayer(int layer) {
        this.layersSpinner.getValueFactory().setValue(layer);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("MazeProperties.nbLayers"))
            this.setMinMax(1, (int) evt.getNewValue());
    }

    public Spinner<Integer> getLayersSpinner() {
        return this.layersSpinner;
    }

    public int getSelectedLayer() {
        return this.layersSpinner.getValue();
    }
}
