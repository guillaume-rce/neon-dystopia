package com.oniverse.neon_dystopia.control;

import com.oniverse.neon_dystopia.model.entities.Player;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.vue.entities.PlayerVue;
import com.oniverse.neon_dystopia.vue.levels.LevelVue;
import com.oniverse.neon_dystopia.vue.utils.ImageVue;
import javafx.scene.Scene;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;

public class PlayerControl {
    private final LevelVue levelVue;

    public PlayerControl(LevelVue levelVue) {
        this.levelVue = levelVue;
    }

    public void linkMovement(Scene scene) {
        scene.setOnKeyPressed(event -> {
            Player player = this.levelVue.getLevel().getPlayer();
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.Z) {
                try {
                    player.moveUp(event.isShiftDown());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                try {
                    player.moveDown(event.isShiftDown());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.Q) {
                try {
                    player.moveLeft(event.isShiftDown());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                try {
                    player.moveRight(event.isShiftDown());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        scene.setOnKeyReleased(event -> {
            Player player = this.levelVue.getLevel().getPlayer();
            player.stopMoving();
        });
    }

    public void stop(Scene scene) {
        scene.setOnKeyPressed(event -> {});
    }
}
