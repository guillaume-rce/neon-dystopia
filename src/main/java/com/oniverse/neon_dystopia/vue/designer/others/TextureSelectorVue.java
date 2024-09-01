package com.oniverse.neon_dystopia.vue.designer.others;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.vue.utils.Vue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TextureSelectorVue extends Vue {
    private final Group plusGroup = new Group();
    private final Group minusGroup = new Group();
    private final Block block;

    public TextureSelectorVue(Coordinate coordinate, Block block) {
        super(coordinate, 17, 40);
        this.block = block;
        this.draw();
    }

    @Override
    public void draw() {
        this.group.getChildren().clear();

        Rectangle minus = new Rectangle(
                this.getBoundingBox().getMinX(), this.getBoundingBox().getMinY(),
                this.getBoundingBox().getWidth()/2 - 2.5, this.getBoundingBox().getHeight());
        minus.setFill(Color.WHITE);
        minus.setArcHeight(5); minus.setArcWidth(5);
        minus.setOpacity(0.5);
        Text minusText = new Text("<");
        minusText.setFill(Color.BLACK);
        minusText.setTranslateX(this.getBoundingBox().getMinX() + 4);
        minusText.setTranslateY(this.getBoundingBox().getMinY() + 13);
        minusText.setStroke(Color.BLACK); minusText.setStrokeWidth(1);
        this.minusGroup.getChildren().addAll(minus, minusText);

        Rectangle plus = new Rectangle(
                this.getBoundingBox().getMinX() + this.getBoundingBox().getWidth()/2 + 2.5, this.getBoundingBox().getMinY(),
                this.getBoundingBox().getWidth()/2 - 2.5, this.getBoundingBox().getHeight());
        plus.setFill(Color.WHITE);
        plus.setArcHeight(5); plus.setArcWidth(5);
        plus.setOpacity(0.5);
        Text plusText = new Text(">");
        plusText.setFill(Color.BLACK);
        plusText.setTranslateX(this.getBoundingBox().getMinX() + this.getBoundingBox().getWidth()/2 + 8);
        plusText.setTranslateY(this.getBoundingBox().getMinY() + 13);
        plusText.setStroke(Color.BLACK); plusText.setStrokeWidth(1);
        this.plusGroup.getChildren().addAll(plus, plusText);

        this.group.getChildren().addAll(this.minusGroup, this.plusGroup);
    }

    public Block getBlock() {
        return block;
    }

    public void onMinusHover() {
        this.minusGroup.getChildren().get(0).setOpacity(1);
    }

    public void onMinusUnhover() {
        this.minusGroup.getChildren().get(0).setOpacity(0.5);
    }

    public void onPlusHover() {
        this.plusGroup.getChildren().get(0).setOpacity(1);
    }

    public void onPlusUnhover() {
        this.plusGroup.getChildren().get(0).setOpacity(0.5);
    }

    public Group getPlus() {
        return plusGroup;
    }

    public Group getMinus() {
        return minusGroup;
    }
}
