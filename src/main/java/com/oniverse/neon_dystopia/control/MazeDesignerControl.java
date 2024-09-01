package com.oniverse.neon_dystopia.control;

import com.oniverse.neon_dystopia.control.designer.BlockSelectionControl;
import com.oniverse.neon_dystopia.control.designer.InteractedMenuControl;
import com.oniverse.neon_dystopia.control.designer.MazeControl;
import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.utils.Link;
import com.oniverse.neon_dystopia.model.utils.LinkPoint;
import com.oniverse.neon_dystopia.vue.designer.MazeDesignerVue;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MazeDesignerControl {
    private final LevelDesigner levelDesigner;
    private final MazeDesignerVue mazeDesignerVue;
    private final MazeControl mazeControl;
    private final InteractedMenuControl interactedMenuControl;
    private final BlockSelectionControl blockSelectionControl;

    public MazeDesignerControl(Scene scene, LevelDesigner levelDesigner, MazeDesignerVue mazeDesignerVue, int size) {
        this.levelDesigner = levelDesigner;
        this.mazeDesignerVue = mazeDesignerVue;
        this.blockSelectionControl = new BlockSelectionControl(this.mazeDesignerVue.getBlocksVue());
        this.mazeControl = new MazeControl(this.mazeDesignerVue.getMazeVue());
        this.interactedMenuControl = new InteractedMenuControl(this.mazeDesignerVue.getInteractedMenuButtonVue(),
                this.levelDesigner, scene, size);

        scene.setOnMouseClicked(this::onClick);
        scene.setOnMouseMoved(this::onMove);
        scene.setOnMouseDragged(this::onDragged);

        this.mazeDesignerVue.getPropertiesVue().layersSpinner.setOnMouseClicked(this::onLayersSpinnerClick);
        this.mazeDesignerVue.getPropertiesVue().heightSpinner.setOnMouseClicked(this::onHeightSpinnerClick);
        this.mazeDesignerVue.getPropertiesVue().widthSpinner.setOnMouseClicked(this::onWidthSpinnerClick);
        this.mazeDesignerVue.getPropertiesVue().nameField.setOnKeyPressed(this::onNameFieldKeyPressed);
        this.mazeDesignerVue.getPropertiesVue().authorField.setOnKeyPressed(this::onAuthorFieldKeyPressed);
        this.mazeDesignerVue.getPropertiesVue().versionField.setOnKeyPressed(this::onVersionFieldKeyPressed);
        this.mazeDesignerVue.getLayersSelectorVue().getLayersSpinner().setOnMouseClicked(this::onActiveLayerSpinnerClick);
    }

    private void onClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        int layer = this.mazeDesignerVue.getLayersSelectorVue().getSelectedLayer() - 1;

        // TODO: Switch case
        if (event.getButton().equals(MouseButton.PRIMARY) &&
                this.interactedMenuControl.contains((int) x, (int) y)) {
            this.interactedMenuControl.clicked();
        } else if (event.getButton().equals(MouseButton.PRIMARY) &&
                this.interactedMenuControl.interactedMenuContains((int) x, (int) y)) {
            this.interactedMenuControl.menuClicked((int) x, (int) y, this.levelDesigner);
        } else if (this.interactedMenuControl.isClicked()) {
            this.interactedMenuControl.clicked();
        } else {

            if (!this.interactedMenuControl.isClicked())
                if (!this.interactedMenuControl.isInLinksMode()) {
                    if (event.getButton().equals(MouseButton.PRIMARY)) { // Left click
                        if (this.blockSelectionControl.contains((int) x, (int) y)) { // Select block or change texture
                            this.blockSelectionControl.changeTexture((int) x, (int) y);
                            this.blockSelectionControl.selectBlock((int) x, (int) y);

                        } else if (this.mazeControl.contains((int) x, (int) y)) {
                            if (this.blockSelectionControl.isSelected()) { // Drop block on maze
                                this.mazeControl.dropBlock((int) x, (int) y, layer,
                                        this.blockSelectionControl.getSelectedBlock(),
                                        this.levelDesigner.getCurrentMaze());
                            } else {
                                if (!this.mazeDesignerVue.getMazeVue().parametersGroup.contains((int) x, (int) y)) {
                                    this.mazeDesignerVue.getMazeVue().unDisplayParameters();
                                    this.mazeControl.displayParameters((int) x, (int) y, layer,
                                            this.levelDesigner.getCurrentMaze());
                                }
                            }
                        }
                    } else if (event.getButton().equals(MouseButton.SECONDARY)) { // Right click
                        if (this.blockSelectionControl.contains((int) x, (int) y)) {
                            this.blockSelectionControl.unselectBlock((int) x, (int) y);

                        } else if (this.mazeControl.contains((int) x, (int) y)) {
                            this.mazeControl.removeBlock((int) x, (int) y, layer,
                                    this.levelDesigner.getCurrentMaze());
                        }

                    } else if (event.getButton().equals(MouseButton.MIDDLE)) {
                        if (this.mazeControl.contains((int) x, (int) y)) {
                            Block block = this.mazeControl.getBlockCopy((int) x, (int) y, layer,
                                    this.levelDesigner.getCurrentMaze());
                            if (block != null) {
                                this.blockSelectionControl.selectBlock(block);
                            }
                        }
                    }
                } else {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            this.interactedMenuControl.setInLinksMode(false);
                            this.mazeControl.unSelectBlock();
                        }

                        if (this.mazeControl.contains((int) x, (int) y))
                            try {
                                if (!this.mazeControl.isSelected()) {
                                    this.mazeControl.selectBlock((int) x, (int) y, layer,
                                            this.levelDesigner.getCurrentMaze());
                                } else {
                                    Coordinate source = this.mazeControl.getSelectedBlock().getCoordinate();
                                    Coordinate target = this.mazeControl.getCoordinate((int) x, (int) y, layer);

                                    this.levelDesigner.addLink(new Link(
                                            new LinkPoint(this.mazeControl.getSelectedMazeId(), source),
                                            new LinkPoint(this.levelDesigner.getCurrentMaze().getProperties().getId(), target),
                                            this.levelDesigner
                                    ));
                                    this.mazeControl.unSelectBlock();
                                    this.interactedMenuControl.setInLinksMode(false);
                                    this.mazeControl.unSelectBlock();
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                this.interactedMenuControl.setInLinksMode(false);
                                this.mazeControl.unSelectBlock();
                            }
                    }
                }
        }
    }

    private void onMove(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        this.interactedMenuControl.onMove((int) x, (int) y);

        if (this.blockSelectionControl.contains((int) x, (int) y)) {
            if (!this.blockSelectionControl.isTextureHovered((int) x, (int) y))
                this.blockSelectionControl.displayText((int) x, (int) y, this.mazeDesignerVue);
            else {
                this.mazeDesignerVue.removeText();
                this.blockSelectionControl.onTextureHovered((int) x, (int) y);
            }

        } else {
            this.mazeDesignerVue.removeText();
            this.blockSelectionControl.onUnHover();
        }
    }


    private void onDragged(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        int layer = this.mazeDesignerVue.getLayersSelectorVue().getSelectedLayer() - 1;

        if (!this.interactedMenuControl.isClicked() && !this.interactedMenuControl.isInLinksMode()) {
            if (event.getButton().equals(MouseButton.PRIMARY) && this.blockSelectionControl.isSelected() &&
                    this.mazeControl.contains((int) x, (int) y)) {
                this.mazeControl.dropBlock((int) x, (int) y, layer,
                        this.blockSelectionControl.getSelectedBlock(), this.levelDesigner.getCurrentMaze());

            } else if (event.getButton().equals(MouseButton.SECONDARY) &&
                    this.mazeControl.contains((int) x, (int) y)) {
                this.mazeControl.removeBlock((int) x, (int) y, layer, this.levelDesigner.getCurrentMaze());
            }
        }
    }

    private void onWidthSpinnerClick(MouseEvent event) {
        this.mazeDesignerVue.getPropertiesVue().heightSpinner.getValueFactory().setValue(
                this.mazeDesignerVue.getPropertiesVue().widthSpinner.getValue());
        this.levelDesigner.getCurrentMaze().getProperties().setHeight(
                this.mazeDesignerVue.getPropertiesVue().heightSpinner.getValue());
        this.levelDesigner.getCurrentMaze().getProperties().setWidth(
                this.mazeDesignerVue.getPropertiesVue().widthSpinner.getValue());
    }

    private void onHeightSpinnerClick(MouseEvent event) {
        this.mazeDesignerVue.getPropertiesVue().widthSpinner.getValueFactory().setValue(
                this.mazeDesignerVue.getPropertiesVue().heightSpinner.getValue());
        this.levelDesigner.getCurrentMaze().getProperties().setWidth(
                this.mazeDesignerVue.getPropertiesVue().widthSpinner.getValue());
        this.levelDesigner.getCurrentMaze().getProperties().setHeight(
                this.mazeDesignerVue.getPropertiesVue().heightSpinner.getValue());
    }

    private void onLayersSpinnerClick(MouseEvent event) {
        this.levelDesigner.getCurrentMaze().getProperties().setLayers(
                this.mazeDesignerVue.getPropertiesVue().layersSpinner.getValue());
        // Update layers selector
        this.mazeDesignerVue.getLayersSelectorVue().setMinMax(1,
                this.levelDesigner.getCurrentMaze().getProperties().getLayers());
        this.mazeDesignerVue.getLayersSelectorVue().setLayer(
                this.levelDesigner.getCurrentMaze().getProperties().getLayers());
        // Update maze vue
        this.mazeDesignerVue.getMazeVue().setActiveLayer(
                this.mazeDesignerVue.getLayersSelectorVue().getSelectedLayer()-1);
        this.mazeDesignerVue.getMazeVue().draw();
    }

    private void onNameFieldKeyPressed(KeyEvent keyEvent) {
        this.levelDesigner.getProperties().setName(
                this.mazeDesignerVue.getPropertiesVue().nameField.getText());
    }

    private void onAuthorFieldKeyPressed(KeyEvent keyEvent) {
        this.levelDesigner.getProperties().setAuthor(
                this.mazeDesignerVue.getPropertiesVue().authorField.getText());
    }

    private void onVersionFieldKeyPressed(KeyEvent keyEvent) {
        this.levelDesigner.getProperties().setVersion(
                this.mazeDesignerVue.getPropertiesVue().versionField.getText());
    }

    private void onActiveLayerSpinnerClick(MouseEvent event) {
        this.mazeDesignerVue.getMazeVue().setActiveLayer(
                this.mazeDesignerVue.getLayersSelectorVue().getSelectedLayer()-1);
        this.mazeDesignerVue.getMazeVue().draw();
    }
}
