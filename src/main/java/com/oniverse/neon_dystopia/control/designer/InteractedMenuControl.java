package com.oniverse.neon_dystopia.control.designer;

import com.oniverse.neon_dystopia.control.MazeDesignerControl;
import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.model.levelDesigner.Maze;
import com.oniverse.neon_dystopia.model.utils.Link;
import com.oniverse.neon_dystopia.model.utils.MazeProperties;
import com.oniverse.neon_dystopia.model.utils.XMLReader;
import com.oniverse.neon_dystopia.vue.designer.MazeDesignerVue;
import com.oniverse.neon_dystopia.vue.designer.others.InteractedMenuButtonVue;
import com.oniverse.neon_dystopia.vue.designer.others.InteractedMenuVue;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class InteractedMenuControl {
    public static final String HOME_VUE = "home_vue";
    public static final String LINKS_VUE = "links_vue";
    public static final String MAZES_VUE = "mazes_vue";
    public static final String LEVELS_VUE = "levels_vue";

    private final InteractedMenuButtonVue interactedMenuButtonVue;
    private final LevelDesigner levelDesigner;
    private String currentVue;
    private boolean isInLinksMode = false;
    private final Scene scene;
    private final int size;

    public InteractedMenuControl(InteractedMenuButtonVue interactedMenuButtonVue, LevelDesigner levelDesigner,
                                 Scene scene, int size) {
        this.interactedMenuButtonVue = interactedMenuButtonVue;
        this.levelDesigner = levelDesigner;
        this.currentVue = null;

        this.scene = scene;
        this.size = size;
    }

    public boolean contains(int x, int y) {
        return this.interactedMenuButtonVue.getBoundingBox().contains(x, y);
    }

    public boolean isClicked() {
        return this.interactedMenuButtonVue.isClicked();
    }

    public boolean interactedMenuContains(int x, int y) {
        return this.isClicked() &&
               this.interactedMenuButtonVue.getInteractedMenuVue().getBoundingBox().contains(x, y);
    }

    public void clicked() {
        this.interactedMenuButtonVue.setClicked();
        if (this.interactedMenuButtonVue.isClicked())
            this.currentVue = HOME_VUE;
        else
            this.currentVue = null;
    }

    public void onMove(int x, int y) {
        this.interactedMenuButtonVue.setHovered(this.contains(x, y) || this.interactedMenuContains(x, y));
        if (this.currentVue != null)
            this.interactedMenuButtonVue.getInteractedMenuVue().setHovered(x, y);
    }

    public void menuClicked(int x, int y, LevelDesigner levelDesigner) {
        if (Objects.equals(this.currentVue, HOME_VUE)) {
            if ((this.interactedMenuButtonVue.getInteractedMenuVue().buttons.get(0)).getBoundingBox().contains(x, y)) {
                this.currentVue = LINKS_VUE;
                this.interactedMenuButtonVue.getInteractedMenuVue().linksMode(levelDesigner.getLinks());
                this.interactedMenuButtonVue.getInteractedMenuVue().linksVue.setOnMouseClicked(this::deleteLink);

            } else if ((this.interactedMenuButtonVue.getInteractedMenuVue().buttons.get(1)).getBoundingBox().contains(x, y)) {
                this.currentVue = MAZES_VUE;
                this.interactedMenuButtonVue.getInteractedMenuVue().mazeMode(
                        levelDesigner.getMazes().size(), levelDesigner.getCurrentMaze().getProperties().getId()+1);

            } else if ((this.interactedMenuButtonVue.getInteractedMenuVue().buttons.get(2)).getBoundingBox().contains(x, y)) {
                this.currentVue = LEVELS_VUE;
                this.interactedMenuButtonVue.getInteractedMenuVue().levelsMode();
                this.interactedMenuButtonVue.getInteractedMenuVue().levelsVue.setOnMouseClicked(this::selectLevel);

            } else if ((this.interactedMenuButtonVue.getInteractedMenuVue().buttons.get(3)).getBoundingBox().contains(x, y)) {
                this.currentVue = null;
                levelDesigner.export();
            }

        } else if (Objects.equals(this.currentVue, LINKS_VUE)) {
            if (this.interactedMenuButtonVue.getInteractedMenuVue().buttons.get(0).getBoundingBox().contains(x, y)) {
                this.isInLinksMode = true;
                this.close();
            }
        } else if (Objects.equals(this.currentVue, MAZES_VUE)) {
            for (InteractedMenuVue.Button button : this.interactedMenuButtonVue.getInteractedMenuVue().buttons) {
                if (button.getBoundingBox().contains(x, y)) {
                    if (Objects.equals(button.getName(), "MAZE_PREVIOUS"))
                        this.levelDesigner.previousMaze();
                    else if (Objects.equals(button.getName(), "MAZE_NEXT"))
                        this.levelDesigner.nextMaze();
                    else if (Objects.equals(button.getName(), "MAZE_ADD"))
                        this.levelDesigner.addMaze();
                    else if (Objects.equals(button.getName(), "MAZE_REMOVE"))
                        this.levelDesigner.removeMaze();
                }
            }
        }
    }

    public void deleteLink(MouseEvent event) {
        ListView<Link> lv = this.interactedMenuButtonVue.getInteractedMenuVue().linksVue;
        if (event.getButton().equals(MouseButton.SECONDARY) && lv.getSelectionModel().getSelectedItem() != null)
            this.levelDesigner.removeLink(lv.getSelectionModel().getSelectedItem());
    }

    public void selectLevel(MouseEvent event) {
        ListView<XMLReader> lv = this.interactedMenuButtonVue.getInteractedMenuVue().levelsVue;
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2 &&
                lv.getSelectionModel().getSelectedItem() != null) {
            XMLReader xmlReader = lv.getSelectionModel().getSelectedItem();
            LevelDesigner levelDesigner = new LevelDesigner(
                    xmlReader.getMazesForDesigner(), xmlReader.getLinks(true), xmlReader.getProperties()
            );
            levelDesigner.addMaze(new Maze(new MazeProperties(0)));
            MazeDesignerVue mazeDesignerVue = new MazeDesignerVue(
                    new Coordinate(0,0), size, size, levelDesigner);
            new MazeDesignerControl(scene, levelDesigner, mazeDesignerVue, size);
            scene.setRoot(mazeDesignerVue.getGroup());
        }
    }

    public boolean isInLinksMode() {
        return this.isInLinksMode;
    }

    public void setInLinksMode(boolean isInLinksMode) {
        this.isInLinksMode = isInLinksMode;
    }

    public void close() {
        this.interactedMenuButtonVue.setClicked();
        this.currentVue = null;
    }
}
