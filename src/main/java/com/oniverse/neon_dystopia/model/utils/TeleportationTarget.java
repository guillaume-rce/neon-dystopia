package com.oniverse.neon_dystopia.model.utils;

import com.oniverse.neon_dystopia.model.Coordinate;

public class TeleportationTarget {
    private final int MazeId;
    private final Coordinate coordinate;

    public TeleportationTarget(int MazeId, Coordinate coordinate) {
        this.MazeId = MazeId;
        this.coordinate = new Coordinate(coordinate);
    }

    public int getMazeId() {
        return this.MazeId;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public String toString() {
        return "TeleportationTarget{" +
                "MazeId=" + MazeId +
                ", coordinate=" + coordinate +
                '}';
    }
}
