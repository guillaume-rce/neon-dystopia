package com.oniverse.neon_dystopia.model.levels;

import com.oniverse.neon_dystopia.model.entities.Player;
import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.maze.Maze;
import com.oniverse.neon_dystopia.model.utils.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Level : Class that represents a level. It's the master of the game.
 * It contains some mazes, the maze feed and the player.
 * It also implements the PropertyChangeListener interface to listen to the player and the blocks.
 * @see PropertyChangeListener
 * @see Player
 * @see Block
 * @see MazesFeed
 * @see Maze
 */
public class Level implements PropertyChangeListener {
    /**
     * The maze of the level.
     */
    private final ArrayList<Maze> mazes;
    /**
     * The current maze of the level.
     */
    private int currentMaze;
    /**
     * The player of the level.
     */
    private final Player player;
    /**
     * The properties of the level.
     */
    private final LevelProperties properties;
    /**
     * The property change support of the level.
     */
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Constructor, it takes a mazes. It also sets the player and the block listeners.
     * @param mazes The mazes of the level.
     * @throws IllegalArgumentException If the mazes are null.
     */
    public Level(ArrayList<Maze> mazes, LevelProperties properties, int windowSize) throws Exception {
        if (mazes == null || properties == null)
            throw new IllegalArgumentException("Mazes and properties can't be null");

        if (mazes.size() > 1)
            mazes.sort(Comparator.comparingInt(maze -> maze.getMazeProperties().getId()));

        this.mazes = mazes;
        this.properties = properties;

        if (this.getStart() == null || this.getEnd() == null)
            throw new Exception("Maze must have a start and an end");

        this.setCurrentMaze((int) this.getStart()[0]);
        this.setBlocksListener();
        for (Maze maze : this.mazes)
            maze.setLevel(this);

        int blockSize = windowSize / this.getCurrentMaze().getWidth();
        Coordinate startCoordinate = ((Block) this.getStart()[1]).getCoordinate();
        this.player = new Player(this, new Coordinate(
                startCoordinate.getY() * blockSize,
                startCoordinate.getX() * blockSize), blockSize);
        this.player.addListener(this);
    }

    /**
     * Set link between blocks. If the block is a transporter, it will set the teleportation target.
     * @param links The links to set.
     */
    public void setLinks(ArrayList<Link> links) {
        for (Link link : links) {
            Maze mazeSource = this.getMazeById(link.getSource().getMazeId());
            Maze mazeTarget = this.getMazeById(link.getTarget().getMazeId());
            Block blockSource = mazeSource.getBlock(link.getSource().getCoordinate());
            Block blockTarget = mazeTarget.getBlock(link.getTarget().getCoordinate());
            if (blockSource == null || blockTarget == null) {
                System.out.println("Link " + link + " not found");
                continue;
            }
            if (blockSource.isTransporter() && blockTarget.isTransporter()) {
                blockSource.setTeleportationTarget(new TeleportationTarget(
                        mazeTarget.getMazeProperties().getId(),
                        blockTarget.getCoordinate()));

                blockTarget.setTeleportationTarget(new TeleportationTarget(
                        mazeSource.getMazeProperties().getId(),
                        blockSource.getCoordinate()));

            } else {
                blockTarget.addActivator(blockSource);
            }
        }
    }

    /**
     * Method used to get the current maze of the level.
     * @return The current maze of the level.
     */
    public Maze getCurrentMaze() {
        return this.getMazeById(this.currentMaze);
    }

    public Maze getMazeById(int id) {
        for (Maze maze : this.mazes)
            if (maze.getMazeProperties().getId() == id)
                return maze;
        return null;
    }

    /**
     * Method used to set the current maze of the level.
     * @param currentMaze The current maze of the level.
     */
    public void setCurrentMaze(int currentMaze) {
        this.currentMaze = currentMaze;
        this.propertyChangeSupport.firePropertyChange("Level.maze",
                null, this.getCurrentMaze());
        this.setBlocksListener();
    }

    /**
     * Used to get the start block of the level.
     * @return The start block of the level.
     */
    public Object[] getStart() {
        for (int i = 0; i < this.mazes.size(); i++)
            for (Block block : this.mazes.get(i).getBlocks())
                if (block.id == BlockId.START)
                    return new Object[]{i, block};

        return null;
    }

    /**
     * Method used to get the end block of the level.
     * @return The end block of the level.
     */
    public Object[] getEnd() {
        for (int i = 0; i < this.mazes.size(); i++)
            for (Block block : this.mazes.get(i).getBlocks())
                if (block.id == BlockId.END)
                    return new Object[]{i, block};

        return null;
    }

    /**
     * Private method used to set the blocks listeners.
     */
    private void setBlocksListener(){
        for (Maze maze : this.mazes)
            for (Block block : maze.getBlocks())
                block.removeListener(this);
        for (Block block : this.getCurrentMaze().getBlocks())
            block.addListener(this);
    }

    /**
     * Method used to go to the next level.
     * It fire a property change for the level id and the maze to update the view.
     * @throws Exception If there is no more levels.
     */
    public void nextLevel() throws Exception {
        System.out.println("Next level");
        this.propertyChangeSupport.firePropertyChange("Level.playerWin", null, null);
    }

    /**
     * Method used to enter and exit blocks when the player moves.
     * @param lastCoordinate The last coordinate of the player.
     * @param newCoordinate The new coordinate of the player.
     */
    public void playerMoved(Coordinate lastCoordinate, Coordinate newCoordinate){
        if (lastCoordinate != null && newCoordinate != null &&
                (lastCoordinate.getX() != newCoordinate.getX() || lastCoordinate.getY() != newCoordinate.getY())) {
            System.out.println("Player moved from " + lastCoordinate + " to " + newCoordinate);
            for (int mazeLayer = 0; mazeLayer < this.getCurrentMaze().getLayer(); mazeLayer++) {

                Coordinate lastCoordinate1 = new Coordinate(
                        lastCoordinate.getX(), lastCoordinate.getY(), mazeLayer);
                if (this.getCurrentMaze().getBlock(lastCoordinate1) != null)
                    this.getCurrentMaze().getBlock(lastCoordinate1).exit();

                Coordinate newCoordinate1 = new Coordinate(
                        newCoordinate.getX(), newCoordinate.getY(), mazeLayer);
                if (this.getCurrentMaze().getBlock(newCoordinate1) != null)
                    this.getCurrentMaze().getBlock(newCoordinate1).enter();
            }
        }
    }

    /**
     * Method used to add a property change listener to the level.
     * @param listener The listener to add.
     */
    public void addListener(PropertyChangeListener listener){
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Method used to remove a property change listener to the level.
     * @param listener The listener to remove.
     */
    public void removeListener(PropertyChangeListener listener){
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Getter for the maze of the level.
     * @return The maze of the level.
     */
    public ArrayList<Maze> getMazes(){
        return this.mazes;
    }

    /**
     * Getter for the player of the level.
     * @return The player of the level.
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Called when the player died.
     */
    public void playerDied(){
        System.out.println("Player died");
        this.propertyChangeSupport.firePropertyChange("Level.playerDied", null, null);
    }

    public void teleport(TeleportationTarget teleportationTarget) {
        for (Block block : this.getCurrentMaze().getBlocks())
            block.exit();

        this.setCurrentMaze(teleportationTarget.getMazeId());
        this.player.teleport(teleportationTarget.getCoordinate());
    }

    /**
     * The property change method of the level.
     * <p>
     *     It is used to listen to the player and the blocks.
     *     It is used to listen to the player health, the block activation and the block textures.
     * </p>
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Player.health") &&
                (int) evt.getNewValue() == 0)
            this.playerDied();

        if (evt.getPropertyName().equals("Block.texture"))
            this.propertyChangeSupport.firePropertyChange(
                    new PropertyChangeEvent(evt.getSource(), "Level.block.texture",
                            evt.getOldValue(), evt.getNewValue()));
        if (evt.getPropertyName().equals("Block.display"))
            this.propertyChangeSupport.firePropertyChange(
                    new PropertyChangeEvent(evt.getSource(), "Level.block.display",
                            evt.getOldValue(), evt.getNewValue()));
    }

    @Override
    public String toString() {
        return "Level {" +
                "maze=" + mazes +
                ", player=" + player +
                '}';
    }

    public LevelProperties getProperties() {
        return properties;
    }
}
