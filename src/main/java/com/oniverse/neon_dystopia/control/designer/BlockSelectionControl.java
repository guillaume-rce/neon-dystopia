package com.oniverse.neon_dystopia.control.designer;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.vue.designer.BlocksVue;
import com.oniverse.neon_dystopia.vue.designer.MazeDesignerVue;
import com.oniverse.neon_dystopia.vue.designer.others.TextureSelectorVue;

public class BlockSelectionControl {
    private final BlocksVue blocksVue;
    private Block selectedBlock;

    public BlockSelectionControl(BlocksVue blocksVue) {
        this.blocksVue = blocksVue;
    }

    public boolean contains(int x, int y) {
        return this.blocksVue.getBoundingBox().contains(x, y);
    }

    public void selectBlock(int x, int y) {
        if (this.selectedBlock != null)
            this.blocksVue.unselectBlock();

        Block block = this.blocksVue.getBlock(new Coordinate(x, y));
        if (block != null) {
            this.selectedBlock = block;
            this.blocksVue.selectBlock(block);
        }
    }

    public void selectBlock(Block block) {
        if (this.selectedBlock != null)
            this.blocksVue.unselectBlock();

        if (block != null) {
            this.selectedBlock = block;
            this.blocksVue.selectBlock(block);
        }
    }

    public void unselectBlock() {
        if (this.selectedBlock != null) {
            this.blocksVue.unselectBlock();
            this.selectedBlock = null;
        }
    }

    public void unselectBlock(int x, int y) {
        if (this.selectedBlock != null && this.getBlock(x, y).id == this.selectedBlock.id) {
            this.blocksVue.unselectBlock();
            this.selectedBlock = null;
        }
    }

    public Block getSelectedBlock() {
        return this.selectedBlock;
    }

    public boolean isSelected() {
        return this.selectedBlock != null;
    }

    public Block getBlock(int x, int y) {
        return this.blocksVue.getBlock(new Coordinate(x, y));
    }

    public void changeTexture(int x, int y) {
        try {
            for (TextureSelectorVue textureSelectorVue : this.blocksVue.getTextureSelectorVues()) {
                if (textureSelectorVue.getBoundingBox().contains(x, y) &&
                        textureSelectorVue.getMinus().contains(x, y)) {
                    textureSelectorVue.getBlock().previousTexture();
                    this.blocksVue.draw();
                } else if (textureSelectorVue.getBoundingBox().contains(x, y) &&
                        textureSelectorVue.getPlus().contains(x, y)) {
                    textureSelectorVue.getBlock().nextTexture();
                    this.blocksVue.draw();
                }
            }
        } catch (Exception ignored) {}
    }

    public void onTextureHovered(int x, int y) {
        for (TextureSelectorVue textureSelectorVue : this.blocksVue.getTextureSelectorVues()) {
            if (textureSelectorVue.getBoundingBox().contains(x, y) &&
                    textureSelectorVue.getMinus().contains(x, y)) {
                // On hover
                textureSelectorVue.onMinusHover();
            } else if (textureSelectorVue.getBoundingBox().contains(x, y) &&
                    textureSelectorVue.getPlus().contains(x, y)) {
                // On hover
                textureSelectorVue.onPlusHover();
            } else {
                // On exit
                textureSelectorVue.onMinusUnhover();
                textureSelectorVue.onPlusUnhover();
            }
        }
    }
    public boolean isTextureHovered(int x, int y) {
        Boolean isHovered = false;
        for (TextureSelectorVue textureSelectorVue : this.blocksVue.getTextureSelectorVues())
            if (textureSelectorVue.getBoundingBox().contains(x, y) &&
                    textureSelectorVue.getMinus().contains(x, y)) {
                // On hover
                isHovered = true;
            } else if (textureSelectorVue.getBoundingBox().contains(x, y) &&
                    textureSelectorVue.getPlus().contains(x, y)) {
                // On hover
                isHovered = true;
            }
        return isHovered;
    }

    public void onUnHover() {
        for (TextureSelectorVue textureSelectorVue : this.blocksVue.getTextureSelectorVues()) {
            textureSelectorVue.onMinusUnhover();
            textureSelectorVue.onPlusUnhover();
        }
    }

    public void displayText(int x, int y, MazeDesignerVue mazeDesignerVue) {
        Block block = this.blocksVue.getBlock(new Coordinate((int) x, (int) y));
        if (block != null)
            mazeDesignerVue.displayText(new Coordinate((int) x, (int) y),
                    block.id.toString());
        else
            mazeDesignerVue.removeText();
    }
}
