package com.oniverse.neon_dystopia.model;

/**
 * Coordinate : Class that represents a coordinate in the game.
 */
public class Coordinate {
    /**
     * The x position of the coordinate.
     */
    private int x;
    /**
     * The y position of the coordinate.
     */
    private int y;
    /**
     * The layer of the coordinate.
     */
    private int layer;

    /**
     * Constructor
     *
     * @param x The x position of the coordinate.
     * @param y The y position of the coordinate.
     * @param layer The layer of the coordinate.
     */
    public Coordinate(int x, int y, int layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    /**
     * Constructor
     *
     * @param x The x position of the coordinate.
     * @param y The y position of the coordinate.
     */
    public Coordinate(int x, int y) {
        this(x, y, 0);
    }

    /**
     * Constructor
     *
     * @param coordinate The coordinate to copy.
     */
    public Coordinate(Coordinate coordinate) {
        this(coordinate.getX(), coordinate.getY(), coordinate.getLayer());
    }

    /**
     * Getter for the x position of the coordinate.
     *
     * @return The x position of the coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for the y position of the coordinate.
     *
     * @return The y position of the coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Getter for the layer of the coordinate.
     *
     * @return The layer of the coordinate.
     */
    public int getLayer() {
        return this.layer;
    }

    /**
     * Setter for the x position of the coordinate.
     *
     * @param x The x position of the coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter for the y position of the coordinate.
     *
     * @param y The y position of the coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Setter for the layer of the coordinate.
     *
     * @param layer The layer of the coordinate.
     */
    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * Moves the coordinate to the given position.
     *
     * @param x The x position of the coordinate.
     * @param y The y position of the coordinate.
     * @param layer The layer of the coordinate.
     */
    public void moveTo(int x, int y, int layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    /**
     * Add the given values to the coordinate.
     *
     * @param x The x position to add.
     * @param y The y position to add.
     * @param layer The layer to add.
     */
    public void move(int x, int y, int layer) {
        this.x += x;
        this.y += y;
        this.layer += layer;
    }

    /**
     * Setter for the coordinate.
     *
     * @param coordinate The coordinate to copy.
     */
    public void setCoordinate(Coordinate coordinate) {
        this.x = coordinate.getX();
        this.y = coordinate.getY();
        this.layer = coordinate.getLayer();
    }

    /**
     * Setter for the coordinate.
     *
     * @param x The x position of the coordinate.
     * @param y The y position of the coordinate.
     * @param layer The layer of the coordinate.
     */
    public void setCoordinate(int x, int y, int layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.layer + ")";
    }

    @Override
    public boolean equals(Object b){
        if (b == null)
            return false;
        if (!(b instanceof Coordinate))
            return false;

        return ((Coordinate) b).getX() == this.getX() &&
                ((Coordinate) b).getY() == this.getY() &&
                ((Coordinate) b).getLayer() == this.getLayer();
    }
}
