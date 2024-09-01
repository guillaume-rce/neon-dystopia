package com.oniverse.neon_dystopia.model.entities;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.Level;
import com.oniverse.neon_dystopia.model.levels.maze.Maze;
import com.oniverse.neon_dystopia.model.utils.HitBox;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Player : Class that represents the player in the game. It has a coordinate, a level and a health.
 * It can move to a coordinate, and it can be damaged.
 * It has a property change support to notify the level and the view when the player moves or gets damaged.
 *
 * @see Coordinate
 * @see Level
 * @see PropertyChangeSupport
 */
public class Player extends HitBox {
    /**
     * The last coordinate of the player. Used to fire a property change.
     * Not very useful, but it's here for the moment.
     */
    private Coordinate lastCoordinate = new Coordinate(0, 0);
    /**
     * The current coordinate of the player.
     */
    private Coordinate coordinate;
    private int speed = 5;
    /**
     * The maximum health of the player.
     * It's a constant, and it's used to set the health of the player and to check when the player get health.
     * @see #setHealth
     */
    public final int maxHealth = 10;
    /**
     * The current health of the player.
     */
    private int health = maxHealth; // TODO: Maybe change it in an object?
    /**
     * The level of the player link to the player.
     * @see Level
     */
    private final Level level;
    /**
     * The property change support of the player.
     * It's used to notify the level and the view when the player moves or gets damaged.
     * @see PropertyChangeSupport
     */
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private final PlayerTextures playerTextures = new PlayerTextures();

    /**
     * The constructor of the player.
     * It takes a coordinate and a level.
     * @param level The level of the player.
     * @param vueCoordinate The coordinate of the player.
     * @param blockSize The size of a block.
     */
    public Player(Level level, Coordinate vueCoordinate, double blockSize) {
        super(vueCoordinate, blockSize, blockSize);
        this.coordinate = new Coordinate(
                (int) (this.getX() / this.getHeight()),
                (int) (this.getY() / this.getWidth()),
                vueCoordinate.getLayer());
        this.level = level;
    }

    /**
     * Get the coordinate of the player.
     * @return The coordinate of the player.
     * @see Coordinate
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public double getBlockSize() {
        return this.getHeight();
    }

    public void setBlockSize(double blockSize) {
        this.setHeight(blockSize);
        this.setWidth(blockSize);
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PlayerTextures getPlayerTextures() {
        return this.playerTextures;
    }

    /**
     * This method is private. It's used to check if the player can move to a coordinate.
     * It checks if the coordinate is inside the maze.
     * @param coordinate The coordinate to check.
     * @throws IllegalArgumentException If the coordinate is outside the maze.
     */
    private void mazeSizeCheck(Coordinate coordinate) throws IllegalArgumentException {
        Maze maze = this.level.getCurrentMaze();
        if (coordinate.getX() + 0.5 < 0 || coordinate.getX() + 0.5 >= maze.getWidth() ||
                coordinate.getY() + 0.5 < 0 || coordinate.getY() + 0.5 >= maze.getHeight() ||
                coordinate.getLayer() < 0 || coordinate.getLayer() >= this.level.getCurrentMaze().getLayer())
            throw new IllegalArgumentException("Player can't move outside the maze");
    }

    /**
     * This method is private. It's used to check if the player can move to a coordinate.
     * It checks if the coordinate is a solid block.
     * @param coordinate The coordinate to check.
     * @throws IllegalArgumentException If the coordinate is a solid block.
     */
    private void solidBlockCheck(Coordinate coordinate) throws IllegalArgumentException {
        for (int mazeLayer = 0; mazeLayer < this.level.getCurrentMaze().getLayer(); mazeLayer++) {
            Maze maze = this.level.getCurrentMaze();
            if (maze.getBlock(coordinate.getX(), coordinate.getY(), mazeLayer) != null &&
                    maze.getBlock(coordinate.getX(), coordinate.getY(), mazeLayer).isSolid())
                throw new IllegalArgumentException("Player can't move to a solid block");
        }
    }


    /**
     * Move the player to an vueX and vueY.
     * @param vueX The vueX coordinate.
     * @param vueY The vueY coordinate.
     * @throws IllegalArgumentException If the coordinate is outside the maze or if the coordinate is a solid block.
     */
    public void moveTo(int vueX, int vueY) throws IllegalArgumentException {
        this.moveTo(new Coordinate(vueX, vueY));
    }

    public void moveToModelCoordinate(Coordinate coordinate) {
        this.moveTo(new Coordinate(
                (int) (coordinate.getX() * this.getHeight()),
                (int) (coordinate.getY() * this.getWidth())));
    }

    /**
     * Move the player to a vueCoordinate.
     * @param vueCoordinate The vueCoordinate to move to.
     * @throws IllegalArgumentException If the vueCoordinate is outside the maze or if the vueCoordinate is a solid block.
     * @see Coordinate
     */
    public void moveTo(Coordinate vueCoordinate) throws IllegalArgumentException {
        if (vueCoordinate == null)
            throw new IllegalArgumentException("Player can't move to a null vueCoordinate");
        Coordinate coordinate = new Coordinate(
                (int) (vueCoordinate.getX() / this.getHeight() + 0.5),
                (int) (vueCoordinate.getY() / this.getWidth() + 0.5));

        HitBox playerHitBox = new HitBox(vueCoordinate.getX(), vueCoordinate.getY(),
                this.getHeight(), this.getWidth());
        this.mazeSizeCheck(coordinate);
        this.solidBlockCheck(coordinate);

        this.setCoordinate(vueCoordinate);
        this.propertyChangeSupport.firePropertyChange("Player.vuePosition",
                null, new double[] {this.getX(), this.getY()});

        this.lastCoordinate.setCoordinate(this.coordinate);

        this.coordinate.setCoordinate(coordinate);
        this.level.playerMoved(this.lastCoordinate, this.coordinate);
    }

    /**
     * Move the player by dx, dy and dl.
     * @param dx The x coordinate to add.
     * @param dy The y coordinate to add.
     * @throws IllegalArgumentException If the coordinate is outside the maze or if the coordinate is a solid block.
     * @see #moveTo(Coordinate)
     */
    public void move(int dx, int dy) throws IllegalArgumentException {
        this.moveTo((int) (this.getX() + dx),
                (int) (this.getY() + dy));
    }

    /**
     * Getter for the health of the player.
     * @return The health of the player.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Setter for the health of the player.
     * @param health The health of the player.
     * @throws IllegalArgumentException If the health is more than the maximum health.
     */
    public void setHealth(int health) throws IllegalArgumentException {
        if (health > this.maxHealth)
            throw new IllegalArgumentException("Player can't have more than " + this.maxHealth + " health");

        this.propertyChangeSupport.firePropertyChange("Player.health",
                this.health,
                health);
        this.health = health;
    }

    /**
     * Add health to the player. It didn't throw an exception if the health is more than the maximum health.
     * It just set let it to the last health.
     * @param health The health to add.
     */
    public void addHealth(int health) {
        try{
            this.setHealth(this.health + health);
        } catch (IllegalArgumentException e) {
            System.out.println("Player can't have more than " + this.maxHealth + " health");
        }
    }

    /**
     * Remove health to the player. It didn't throw an exception if the health is less than 0.
     * It just set let it to the last health.
     * @param health The health to remove.
     */
    public void removeHealth(int health) {
        try {
            this.setHealth(this.health - health);
        } catch (IllegalArgumentException e) {
            System.out.println("Player died");
        }
    }

    /**
     * Add a listener to the player.
     * @param listener The listener to add.
     *                 @see PropertyChangeListener
     */
    public void addListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove a listener to the player.
     * @param listener The listener to remove.
     *                 @see PropertyChangeListener
     */
    public void removeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Player{" +
                "vueCoordinate=" + super.toString() +
                ", coordinate=" + coordinate +
                ", maxHealth=" + maxHealth +
                ", health=" + health +
                '}';
    }

    public void teleport(Coordinate coordinate) {
        System.out.println(coordinate);
        Coordinate newCoordinate = new Coordinate(
                (int) (coordinate.getX() * this.getHeight()),
                (int) (coordinate.getY() * this.getWidth()));
        this.lastCoordinate = new Coordinate(coordinate);
        this.coordinate = new Coordinate(coordinate);
        this.setCoordinate(newCoordinate);
        this.propertyChangeSupport.firePropertyChange("Player.vuePosition",
                null, new double[] {this.getX(), this.getY()});
    }

    public void moveRight(boolean run) {
        if (this.getPlayerTextures().getCurrentTexture() != PlayerTexturesPath.VAGABOND_RUN)
            this.getPlayerTextures().setCurrentTexture(PlayerTexturesPath.VAGABOND_RUN);
        if (this.getPlayerTextures().isReverse())
            this.getPlayerTextures().setReverse(false);
        this.move(0, run ? this.getSpeed() * 2 : this.getSpeed());
    }

    public void moveLeft(boolean run) {
        if (this.getPlayerTextures().getCurrentTexture() != PlayerTexturesPath.VAGABOND_RUN)
            this.getPlayerTextures().setCurrentTexture(PlayerTexturesPath.VAGABOND_RUN);
        if (!this.getPlayerTextures().isReverse())
            this.getPlayerTextures().setReverse(true);
        this.move(0, run ? -this.getSpeed() * 2 : -this.getSpeed());
    }

    public void moveUp(boolean run) {
        if (this.getPlayerTextures().getCurrentTexture() != PlayerTexturesPath.VAGABOND_RUN)
            this.getPlayerTextures().setCurrentTexture(PlayerTexturesPath.VAGABOND_RUN);
        if (this.getPlayerTextures().isReverse())
            this.getPlayerTextures().setReverse(false);
        this.move(run ? -this.getSpeed() * 2 : -this.getSpeed(), 0);
    }

    public void moveDown(boolean run) {
        if (this.getPlayerTextures().getCurrentTexture() != PlayerTexturesPath.VAGABOND_RUN)
            this.getPlayerTextures().setCurrentTexture(PlayerTexturesPath.VAGABOND_RUN);
        if (this.getPlayerTextures().isReverse())
            this.getPlayerTextures().setReverse(false);
        this.move(run ? this.getSpeed() * 2 : this.getSpeed(), 0);
    }

    public void stopMoving() {
        this.getPlayerTextures().setCurrentTexture(PlayerTexturesPath.VAGABOND_IDLE);
    }
}
