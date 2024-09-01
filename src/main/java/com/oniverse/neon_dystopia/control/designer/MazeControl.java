package com.oniverse.neon_dystopia.control.designer;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.Maze;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.vue.designer.MazeVue;

public class MazeControl {
    private final MazeVue mazeVue;
    private Block selectedBlock;
    private int selectedMazeId;

    public MazeControl(MazeVue mazeVue) {
        this.mazeVue = mazeVue;
    }

    public boolean contains(int x, int y) {
        return this.mazeVue.getBoundingBox().contains(x, y);
    }

    public Coordinate getCoordinate(int x, int y, int layer) {
        double mazeVueBorderX = this.mazeVue.getBoundingBox().getMinX();
        double mazeVueBorderY = this.mazeVue.getBoundingBox().getMinY();
        return new Coordinate(
                (int) ((x - mazeVueBorderX) / this.mazeVue.getDisplaySize()),
                (int) ((y - mazeVueBorderY) / this.mazeVue.getDisplaySize()),
                layer);
    }

    public void dropBlock(int x, int y, int layer, Block block, Maze currentMaze) {
        if (currentMaze.getBlock(this.getCoordinate(x, y, layer)) == null) {
            Block blockToAdd = block.id.getBlockFromId(this.getCoordinate(x, y, layer));
            blockToAdd.setTexture(block.getTexture());
            try {
                currentMaze.addBlock(blockToAdd);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void removeBlock(int x, int y, int layer, Maze currentMaze) {
        Block block = currentMaze.getBlock(this.getCoordinate(x, y, layer));
        if (block != null)
            currentMaze.removeBlock(block);
    }

    public Block getBlockCopy(int x, int y, int layer, Maze currentMaze) {
        if (currentMaze.getBlock(this.getCoordinate(x, y, layer)) == null)
            return null;
        Block block = currentMaze.getBlock(
                this.getCoordinate(x, y, layer)).id.getBlockFromId(this.getCoordinate(x, y, layer));
        block.setTexture(currentMaze.getBlock(this.getCoordinate(x, y, layer)).getTexture());
        return block;
    }

    public void selectBlock(int x, int y, int layer, Maze currentMaze) {
        if (this.selectedBlock != null)
            this.mazeVue.unselectBlock();

        this.selectedBlock = currentMaze.getBlock(this.getCoordinate(x, y, layer));
        this.selectedMazeId = currentMaze.getProperties().getId();
        if (this.selectedBlock != null)
            this.mazeVue.selectBlock(this.selectedBlock);
    }

    public void unSelectBlock() {
        if (this.selectedBlock != null)
            this.mazeVue.unselectBlock();
        this.selectedBlock = null;
    }

    public void unSelectBlock(int x, int y, int layer, Maze currentMaze) {
        if (this.selectedBlock != null &&
                currentMaze.getBlock(this.getCoordinate(x, y, layer)).id == this.selectedBlock.id) {
            this.mazeVue.unselectBlock();
            this.selectedBlock = null;
        }
    }

    public Block getSelectedBlock() {
        return this.selectedBlock;
    }

    public int getSelectedMazeId() {
        return this.selectedMazeId;
    }

    public boolean isSelected() {
        return this.selectedBlock != null;
    }

    public void displayParameters(int x, int y, int layer, Maze currentMaze) {
        Block block = currentMaze.getBlock(this.getCoordinate(x, y, layer));
        if (block != null)
            this.mazeVue.displayParameters(block);
    }
}
