package com.oniverse.neon_dystopia.model.levels.maze;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.Level;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.utils.MazeProperties;
import com.oniverse.neon_dystopia.model.utils.XMLReader;

import java.util.ArrayList;

/**
 * This class is used to define a maze.
 * It's an ArrayList of blocks.
 *
 * @see Block
 * @see ArrayList
 */
public class Maze {
    /**
     * The properties of the maze. (size, id, ...)
     * @see MazeProperties
     */
    private final MazeProperties mazeProperties;
    /**
     * The blocks of the maze.
     * We didn't take care of the order of the blocks because we use the coordinate to get the block.
     * @see Block
     * @see Coordinate
     * @see ArrayList
     */
    private final ArrayList<Block> blocks;

    public Maze (MazeProperties mazeProperties) {
        this(mazeProperties, new ArrayList<>());
    }

    /**
     * The constructor of the Maze class.
     * It is used to define the maze.
     *
     * @param mazeProperties The properties of the maze
     * @param blocks The blocks of the maze. Take from the xml file.
     * @see Block
     * @see MazeProperties
     * @see ArrayList
     * @see XMLReader
     */
    public Maze (MazeProperties mazeProperties, ArrayList<Block> blocks) {
        this.mazeProperties = mazeProperties;
        this.blocks = blocks;
    }

    /**
     * This method is used to get a block from the maze.
     * @param coordinate The coordinate of the block.
     * @return The block with the coordinate.
     * @see Block
     * @see Coordinate
     */
    public Block getBlock(Coordinate coordinate){
        for (Block block: this.getBlocks())
            if (block.getCoordinate().equals(coordinate)) {
                return block;
            }
        return null;
    }

    public Block getBlock(double x, double y, double z) {
        return this.getBlock(new Coordinate((int) x, (int) y, (int) z));
    }

    public ArrayList<Block> getBlocksOnLine(double x1, double y1, double x2, double y2) {
        ArrayList<Block> blocks = new ArrayList<>();
        double x = x1;
        double y = y1;
        double dx = x2 - x1;
        double dy = y2 - y1;
        double step = Math.max(Math.abs(dx), Math.abs(dy));
        dx /= step;
        dy /= step;
        for (int i = 0; i < step; i++) {
            blocks.add(this.getBlock(x, y, 0));
            x += dx;
            y += dy;
        }
        return blocks;
    }

    /**
     * Add a block to the maze.
     * @param block The block to add.
     */
    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    /**
     * Add a block to the maze at a specific index.
     * @param block The block to add.
     * @param index The index where to add the block.
     */
    public void setBlock(Block block, int index) {
        this.blocks.set(index, block);
    }

    /**
     * Remove a block from the maze.
     * @param block The block to remove.
     */
    public void removeBlock(Block block) {
        this.blocks.remove(block);
    }

    /**
     * Remove a block from the maze at a specific index.
     * @param index The index of the block to remove.
     */
    public void removeBlock(int index) {
        this.blocks.remove(index);
    }

    /**
     * Get the spawn of the player. It's the block with the id START. It's used to spawn the player.
     * @return The start block.
     */
    public Block getPlayersSpawn() {
        if (this.blocks == null)
            return null;
        for (Block block: this.getBlocks())
            if (block.id == BlockId.START)
                return block;
        return null;
    }

    /**
     * Get the end of the maze. It's the block with the id END. It's used to know if the player has finished the maze.
     * @return The end block.
     */
    public Block getPlayerEnd() {
        for (Block block: this.getBlocks())
            if (block.id == BlockId.END)
                return block;
        return null;
    }

    /**
     * Get all the blocks of the maze.
     * @return The blocks of the maze.
     */
    public ArrayList<Block> getBlocks() {
        // Sort the blocks by layer
        this.blocks.sort((block1, block2) -> {
            if (block1.getCoordinate().getLayer() < block2.getCoordinate().getLayer())
                return -1;
            else if (block1.getCoordinate().getLayer() > block2.getCoordinate().getLayer())
                return 1;
            else
                return 0;
        });
        return this.blocks;
    }

    public ArrayList<Block> getBlocks(double x, double y) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (int layer = 0; layer < this.getLayer(); layer++) {
            Block block = this.getBlock(x, y, layer);
            if (block != null)
                blocks.add(block);
        }
        return blocks;
    }

    /**
     * Get width of the maze.
     * @return The width of the maze.
     */
    public int getWidth() {
        return this.mazeProperties.getWidth();
    }

    /**
     * Get height of the maze.
     * @return The height of the maze.
     */
    public int getHeight() {
        return this.mazeProperties.getHeight();
    }

    /**
     * Get the number of layers in the maze.
     * @return The number of layers in the maze.
     */
    public int getLayer() {
        return this.mazeProperties.getLayers();
    }

    /**
     * Get the properties of the maze.
     * @return The properties of the maze.
     * @see MazeProperties
     */
    public MazeProperties getMazeProperties() {
        return mazeProperties;
    }

    public void update() {
        for (Block block: this.getBlocks())
            block.update();
    }

    public void setLevel(Level level) {
        for (Block block: this.getBlocks())
            block.setLevel(level);
    }

    @Override
    public String toString() {
        return "Maze{" +
                "\n\tmazeProperties=" + mazeProperties +
                "\n}";
    }

    @Override
    public boolean equals(Object e){
        if (!(e instanceof Maze))
            return false;

        if (this.mazeProperties != ((Maze) e).mazeProperties &&
                this.blocks.size() != ((Maze) e).blocks.size())
            return false;

        // verify if all blocks are equals
        for (Block block: this.getBlocks()) {
            if (!((Maze) e).getBlocks().contains(block))
                return false;
        }

        return true;
    }
}
