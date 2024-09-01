package com.oniverse.neon_dystopia.control;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.model.levelDesigner.Maze;
import com.oniverse.neon_dystopia.model.levels.Level;
import com.oniverse.neon_dystopia.model.utils.MapsGetter;
import com.oniverse.neon_dystopia.model.utils.MazeProperties;
import com.oniverse.neon_dystopia.vue.mapSelection.MapSelectionVue;
import com.oniverse.neon_dystopia.vue.designer.MazeDesignerVue;
import com.oniverse.neon_dystopia.vue.entities.info.PlayerInfoVue;
import com.oniverse.neon_dystopia.vue.levels.LevelVue;
import javafx.scene.Group;
import javafx.scene.Scene;

public class MenuControl {
    public static void start(Scene scene, int size) {
        try {
            Level level = new Level(null, null, 1);
            LevelVue levelVue = new LevelVue(level, size);
            PlayerInfoVue playerInfoVue = new PlayerInfoVue(level.getPlayer(), size);

            scene.setRoot(new Group(levelVue.getGroup(), playerInfoVue.getGroup()));
            (new PlayerControl(levelVue)).linkMovement(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void levelDesigner(Scene scene, int size) {
        LevelDesigner levelDesigner = new LevelDesigner();
        levelDesigner.addMaze(new Maze(new MazeProperties(0, 20, 20, 5)));
        MazeDesignerVue mazeDesignerVue = new MazeDesignerVue(
                new Coordinate(0,0), size, size, levelDesigner);
        new MazeDesignerControl(scene, levelDesigner, mazeDesignerVue, size);
        scene.setRoot(mazeDesignerVue.getGroup());
    }

    public static void fromFile(Scene scene, int size) {
        MapSelectionVue mapSelectionVue = new MapSelectionVue(size, size, new MapsGetter());
        new MazeSelectorControl(mapSelectionVue, scene, size);
        scene.setRoot(mapSelectionVue.getGroup());
    }
}
