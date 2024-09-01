package com.oniverse.neon_dystopia.model.utils;

import com.oniverse.neon_dystopia.model.Coordinate;

/**
 * LinkPoint : Class that represents a point (coordinate + mazeId) that can be linked to another point.
 */
public class LinkPoint {
    /**
     * The maze id of the point.
     */
    private final int mazeId;
    /**
     * The coordinate of the point.
     */
    public final Coordinate coordinate;

    /**
     * Constructor
     *
     * @param mazeId     The maze id of the point.
     * @param coordinate The coordinate of the point.
     */
    public LinkPoint(int mazeId, Coordinate coordinate) {
        this.mazeId = mazeId;
        this.coordinate = coordinate;
    }

    /**
     * Getter for the maze id.
     *
     * @return The maze id of the point.
     */
    public int getMazeId() {
        return this.mazeId;
    }

    /**
     * Getter for the coordinate.
     *
     * @return The coordinate of the point.
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LinkPoint))
            return false;
        LinkPoint linkPoint = (LinkPoint) o;
        return this.mazeId == linkPoint.getMazeId() && this.coordinate.equals(linkPoint.getCoordinate());
    }

    @Override
    public String toString() {
        return "X: " + this.coordinate.getX() + " Y: " + this.coordinate.getY() +
                " Layer: " + this.coordinate.getLayer() + " Maze: " + this.mazeId;
    }
}
