package com.oniverse.neon_dystopia.vue.designer;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.Textures;
import com.oniverse.neon_dystopia.vue.designer.others.TextureSelectorVue;
import com.oniverse.neon_dystopia.vue.utils.ImageVue;
import com.oniverse.neon_dystopia.vue.utils.Vue;
import javafx.geometry.BoundingBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class BlocksVue extends Vue {
    private final int margin;
    private final ArrayList<ImageVue> images = new ArrayList<>();
    private final ArrayList<Block> blocks = new ArrayList<>();
    private final ArrayList<TextureSelectorVue> textureSelectorVues = new ArrayList<>();
    private final int blockSize;
    private Rectangle select;

    public BlocksVue(Coordinate coordinate, int height, int blockSize, int margin) {
        super(coordinate, height, 0);
        this.margin = margin;
        this.blockSize = blockSize;
        for (BlockId blockId: BlockId.values()){
            Block block = blockId.getBlockFromId(new Coordinate(0, 0));
            if (block.getTexture().getTexture() == null)
                continue;
            this.blocks.add(block);
        }
        this.draw();
    }

    @Override
    public void draw() {
        // Clear
        this.group.getChildren().clear();
        this.images.clear();
        this.textureSelectorVues.clear();
        // Get position and size
        int x = (int) this.boundingBox.getMinX();
        int y = (int) this.boundingBox.getMinY();
        int height = (int) this.boundingBox.getHeight();
        // Get size of blocks
        //We want to have the maximum number of blocks per column
        int numberOfBlocksPerColumn = (int) Math.floor((double) (height - this.margin) / (this.blockSize + this.margin));
        int numberOfColumn = (int) Math.ceil((double) this.blocks.size() / numberOfBlocksPerColumn);
        // Update bounding box
        this.boundingBox = new BoundingBox(x, y,
                numberOfColumn * (blockSize + this.margin) + this.margin, height);
        // Draw background
        Rectangle rectangle = new Rectangle(this.boundingBox.getMinX(), this.boundingBox.getMinY(),
                this.boundingBox.getWidth(), this.boundingBox.getHeight());
        rectangle.setFill(Color.WHITE); rectangle.setOpacity(0.8);
        rectangle.setArcHeight(10); rectangle.setArcWidth(10);
        this.group.getChildren().add(rectangle);
        // Draw blocks
        int i = 0;
        int xBlock = x + this.margin;
        for (Block block: this.blocks){
            if (i!= 0 && i % numberOfBlocksPerColumn == 0){
                i = 0;
                xBlock += blockSize + this.margin;
            }
            int yBlock = y + margin + i * (blockSize + this.margin);
            ArrayList<Object> tags = new ArrayList<>();
            tags.add(block.id); tags.add(block);
            ImageVue imageVue = new ImageVue(block.getTexture().getTexture(),
                    new Coordinate(xBlock, yBlock), blockSize, blockSize, false, tags);
            this.images.add(imageVue);
            this.group.getChildren().add(imageVue.getGroup());

            if (block.haveMultipleTextures()) {
                TextureSelectorVue textureSelectorVue = new TextureSelectorVue(
                        new Coordinate(xBlock + blockSize/2 - 20, yBlock + blockSize - 18),
                        block);
                this.group.getChildren().add(textureSelectorVue.getGroup());
                this.textureSelectorVues.add(textureSelectorVue);
            }
            i++;
        }
        if (this.select != null)
            this.group.getChildren().add(this.select);
    }

    public ArrayList<ImageVue> getImages() {
        return images;
    }

    public ArrayList<TextureSelectorVue> getTextureSelectorVues() {
        return textureSelectorVues;
    }

    public Block getBlock(Coordinate coordinate) {
        for (ImageVue imageVue: this.images)
            if (imageVue.getBoundingBox().contains(coordinate.getX(), coordinate.getY())) {
                Block block = ((BlockId) imageVue.tags.get(0)).getBlockFromId(coordinate);
                block.setTexture(new Textures(((Block) imageVue.tags.get(1)).getTexture()));
                return block;
            }
        return null;
    }

    public void selectBlock(Block block){
        this.unselectBlock();

        for (Block blockDisplay: this.blocks)
            if (blockDisplay.id == block.id)
                blockDisplay.setTexture(new Textures(block.getTexture()));

        for (ImageVue imageVue: this.images)
            if (imageVue.tags.get(0) == block.id) {
                Rectangle selection = new Rectangle(
                        imageVue.getBoundingBox().getMinX(),
                        imageVue.getBoundingBox().getMinY(),
                        imageVue.getBoundingBox().getWidth(),
                        imageVue.getBoundingBox().getHeight());
                selection.setFill(null);
                selection.setStroke(Color.BLUE); selection.setStrokeWidth(2);
                selection.setArcHeight(5); selection.setArcWidth(5);
                this.select = selection;
                this.group.getChildren().add(selection);
            }
    }

    public void unselectBlock(){
        if (this.select != null) {
            this.group.getChildren().remove(this.select);
            this.select = null;
        }
    }
}
