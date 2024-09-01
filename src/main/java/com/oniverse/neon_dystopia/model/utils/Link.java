package com.oniverse.neon_dystopia.model.utils;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.model.levelDesigner.Maze;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockType;

import java.util.ArrayList;

/**
 * This class is used to define a link between two blocks using their coordinates.
 * @see Coordinate
 */
public class Link {
    /**
     * The coordinates of the source block.
     */
    private final LinkPoint source;
    /**
     * The coordinates of the target block.
     */
    private final LinkPoint target;

    /**
     * The constructor of the LinkCoordinate class.
     * @param source The coordinates of the source block.
     * @param target The coordinates of the target block.
     * @throws IllegalArgumentException If the source or the target is null or if the source and the target are the same.
     * @see Coordinate
     */
    public Link(LinkPoint source, LinkPoint target) throws IllegalArgumentException {
        this.securityCheck(source, target);
        this.source = source;
        this.target = target;
    }

    /**
     * The constructor of the LinkCoordinate class with a security check on the level designer.
     * @param source The coordinates of the source block.
     * @param target The coordinates of the target block.
     * @param levelDesigner The level designer.
     * @throws IllegalArgumentException If it's not possible to create a link between the source and the target.
     */
    public Link(LinkPoint source, LinkPoint target, LevelDesigner levelDesigner) throws IllegalArgumentException {
        this.securityCheck(source, target, levelDesigner);
        Maze mazeSource = levelDesigner.getMaze(source.getMazeId());
        Maze mazeTarget = levelDesigner.getMaze(target.getMazeId());
        Block blockSource = mazeSource.getBlock(source.getCoordinate());
        Block blockTarget = mazeTarget.getBlock(target.getCoordinate());
        if (!(blockSource.isTransporter() && blockTarget.isTransporter())) {
            this.source = (blockSource.type == BlockType.SENSOR) ? source : target;
            this.target = (blockTarget.type == BlockType.ACTUATOR) ? target : source;
        } else {
            this.source = source;
            this.target = target;
        }
    }

    /**
     * This method is used to check if the source and the target are valid.
     * @param source The coordinates of the source block.
     * @param target The coordinates of the target block.
     * @throws IllegalArgumentException If the source or the target is null or if the source and the target are the same.
     * @see Coordinate
     */
    private void securityCheck(LinkPoint source, LinkPoint target) throws IllegalArgumentException{
        if (source == null || target == null)
            throw new IllegalArgumentException("The source and the target of a link can't be null.");
        if (source.equals(target))
            throw new IllegalArgumentException("The source and the target of a link can't be the same block.");
    }

    /**
     * This method is used to check if the source and the target are valid.
     * @param source The coordinates of the source block.
     * @param target The coordinates of the target block.
     * @param levelDesigner The level designer.
     * @throws IllegalArgumentException If the source or the target is null or if the source and the target are the same or if the source and the target are not compatible.
     */
    public void securityCheck(LinkPoint source, LinkPoint target, LevelDesigner levelDesigner) throws IllegalArgumentException{
        this.securityCheck(source, target);
        Maze mazeSource = levelDesigner.getMaze(source.getMazeId());
        Maze mazeTarget = levelDesigner.getMaze(target.getMazeId());
        Block blockSource = mazeSource.getBlock(source.getCoordinate());
        Block blockTarget = mazeTarget.getBlock(target.getCoordinate());
        if (blockSource == null || blockTarget == null)
            throw new IllegalArgumentException("The source and the target of a link can't be null.");
        if (blockSource.equals(blockTarget) &&
                mazeSource.getProperties().getId() == mazeTarget.getProperties().getId())
            throw new IllegalArgumentException("The source and the target of a link can't be the same block."
                    + "\nSource: " + blockSource + "\nTarget: " + blockTarget);

        if (blockSource.type == BlockType.SENSOR && blockTarget.type == BlockType.SENSOR)
            throw new IllegalArgumentException("It's not possible to link two sensors together.");

        if (!(blockSource.isTransporter() && blockTarget.isTransporter()) &&
                blockSource.type == BlockType.ACTUATOR && blockTarget.type == BlockType.ACTUATOR)
            throw new IllegalArgumentException("It's not possible to link two actuators together.");

        if (!(blockSource.isTransporter() && blockTarget.isTransporter()) &&
                !(blockSource.type == BlockType.ACTUATOR && blockTarget.type == BlockType.SENSOR ||
                blockSource.type == BlockType.SENSOR && blockTarget.type == BlockType.ACTUATOR))
            throw new IllegalArgumentException("The source and the target of a link must be a sensor and an actuator."
                    + "\nSource: " + blockSource + "\nTarget: " + blockTarget);
    }

    /**
     * This method is used to get the source of the link.
     * @return The source of the link.
     */
    public LinkPoint getSource() {
        return this.source;
    }

    /**
     * This method is used to get the target of the link.
     * @return The target of the link.
     */
    public LinkPoint getTarget() {
        return this.target;
    }

    /**
     * Getter of the link points.
     * @return The link points.
     */
    public ArrayList<LinkPoint> getLinkPoints() {
        ArrayList<LinkPoint> coordinates = new ArrayList<>();
        coordinates.add(this.source);
        coordinates.add(this.target);
        return coordinates;
    }

    @Override
    public String toString() {
        return "(" + this.source + ") -> (" + this.target + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Link))
            return false;
        Link link = (Link) obj;
        return this.source.equals(link.source) && this.target.equals(link.target);
    }
}
