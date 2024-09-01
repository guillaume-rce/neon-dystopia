package com.oniverse.neon_dystopia.model.levelDesigner;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.utils.LevelProperties;
import com.oniverse.neon_dystopia.model.utils.MazeProperties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * This class is used to define a maze for the designer.
 */
public class Maze implements PropertyChangeListener {
    /**
     * This list is used to store the blocks of the maze.
     */
    private final ArrayList<Block> blocks;
    /**
     * The properties of the maze.
     * @see MazeProperties
     */
    private final MazeProperties properties;
    /**
     * The property change support of the maze.
     */
    private final PropertyChangeSupport propertyChangeSupport;


    /**
     * This constructor is used to create a maze designer with the given properties.
     * @see LevelProperties
     */
    public Maze(MazeProperties mazeProperties) {
        this.properties = mazeProperties;
        this.properties.addListener(this);
        this.blocks = new ArrayList<>();
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * This constructor is used to create a maze designer with the given properties and blocks.
     * It can be used to load a maze.
     * @param blocks The blocks of the maze.
     * @see LevelProperties
     * @see Block
     */
    public Maze(MazeProperties mazeProperties, ArrayList<Block> blocks) {
        this(mazeProperties);
        this.blocks.addAll(blocks);
    }

    /**
     * This method is used to add a block to the maze. It will check if the block is in the maze and fire a property change.
     * @param block The block to add.
     * @throws IllegalArgumentException If the block is out of bounds.
     * @see Block
     * @see PropertyChangeListener
     */
    public void addBlock(Block block) throws IllegalArgumentException {
        if (block.getCoordinate().getX() < 0 || block.getCoordinate().getX() >= this.properties.getWidth()
                || block.getCoordinate().getY() < 0 || block.getCoordinate().getY() >= this.properties.getHeight())
            throw new IllegalArgumentException("Block out of bounds");

        ArrayList<Block> blocksCopy = new ArrayList<>(this.blocks){{add(block);}};
        this.propertyChangeSupport.firePropertyChange("MazeDesigner.blocks",
                this.blocks, blocksCopy);

        this.blocks.add(block);
    }

    /**
     * Remove a block from the maze. It will fire a property change to update the view.
     * @param block The block to remove.
     * @see Block
     * @see PropertyChangeListener
     */
    public void removeBlock(Block block) {
        this.removeBlocks(new ArrayList<>(){{add(block);}});
    }

    /**
     * Remove a list of blocks from the maze. It will fire a property change to update the view.
     *
     * @param blocks The array list of blocks to remove. It will remove all the blocks in the list.
     * @see Block
     * @see ArrayList
     * @see PropertyChangeListener
     */
    public void removeBlocks(ArrayList<Block> blocks) {
        ArrayList<Block> blocksCopy = new ArrayList<>(this.blocks){{removeAll(blocks);}};
        this.propertyChangeSupport.firePropertyChange("MazeDesigner.blocks",
                this.blocks, blocksCopy);

        this.blocks.removeAll(blocks);
    }

    /**
     * Get all blocks stored in the maze. It will sort the blocks by layer.
     * @return The array list of blocks.
     */
    public ArrayList<Block> getBlocks() {
        // Sort blocks by layer
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

    /**
     * Get a block at the given coordinate.
     * @param coordinate The coordinate of the block.
     * @return The block at the given coordinate or null if there is no block at the given coordinate.
     * @see Coordinate
     */
    public Block getBlock(Coordinate coordinate){
        for (Block block: this.blocks)
            if (block.getCoordinate().equals(coordinate))
                return block;
        return null;
    }

    /**
     * Add a property change listener to the maze designer.
     * @param listener The property change listener to add.
     * @see PropertyChangeListener
     */
    public void addListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove a property change listener from the maze designer.
     * @param listener The property change listener to remove.
     * @see PropertyChangeListener
     */
    public void removeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public MazeProperties getProperties() {
        return properties;
    }

    /**
     * Call when a property is changed. It will be called when the maze properties are changed.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     * @see PropertyChangeEvent
     * @see PropertyChangeListener
     * @see LevelProperties
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("MazeProperties.height")) {
            ArrayList<Block> blocksToRemove = new ArrayList<>();
            for (Block block : this.blocks)
                if (block.getCoordinate().getY() >= (int) evt.getNewValue())
                    blocksToRemove.add(block);
            this.removeBlocks(blocksToRemove);
        }
        if (evt.getPropertyName().equals("MazeProperties.width")) {
            ArrayList<Block> blocksToRemove = new ArrayList<>();
            for (Block block : this.blocks)
                if (block.getCoordinate().getX() >= (int) evt.getNewValue())
                    blocksToRemove.add(block);
            this.removeBlocks(blocksToRemove);
        }
        if (evt.getPropertyName().equals("MazeProperties.layers")) {
            ArrayList<Block> blocksToRemove = new ArrayList<>();
            for (Block block : this.blocks)
                if (block.getCoordinate().getLayer() >= (int) evt.getNewValue())
                    blocksToRemove.add(block);
            this.removeBlocks(blocksToRemove);
        }
    }
}
