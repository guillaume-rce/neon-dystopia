package com.oniverse.neon_dystopia.model.levelDesigner;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.utils.LevelProperties;
import com.oniverse.neon_dystopia.model.utils.Link;
import com.oniverse.neon_dystopia.model.utils.LinkPoint;
import com.oniverse.neon_dystopia.model.utils.MazeProperties;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * This class is used to design a level.
 */
public class LevelDesigner implements PropertyChangeListener {
    /**
     * This list is used to store the mazes of the level.
     */
    private final ArrayList<Maze> mazes;
    /**
     * This is the index of the current maze that is being designed.
     */
    private int currentMazeIndex = 0;
    /**
     * This list is used to store the links of the level.
     */
    private final ArrayList<Link> links;
    /**
     * The properties of the maze.
     * @see LevelProperties
     */
    private final LevelProperties properties;
    /**
     * The property change support of the maze.
     */
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * This constructor is used to create a maze designer with an empty maze list.
     * @see Maze
     */
    public LevelDesigner() {
        this(new ArrayList<>(), new LevelProperties());
    }

    /**
     * This constructor is used to create a maze designer with the given maze list.
     * @param mazes The maze list.
     *              It can be used to load a maze.
     *              @see Maze
     * @param properties The properties of the level.
     */
    public LevelDesigner(ArrayList<Maze> mazes, LevelProperties properties) {
        this(mazes, new ArrayList<>(), properties);
    }

    /**
     * This constructor is used to create a maze designer with the given maze list and link list.
     * @param mazes The maze list.
     * @param links The link list.
     * @param properties The properties of the level.
     */
    public LevelDesigner(ArrayList<Maze> mazes, ArrayList<Link> links, LevelProperties properties) {
        this.mazes = mazes;
        for (Maze maze : mazes) {
            maze.addListener(this);
        }
        this.links = links;
        this.properties = properties;
    }

    /**
     * Add a maze to the designer.
     * @param maze The maze to add.
     */
    public void addMaze(Maze maze) {
        maze.addListener(this);
        mazes.add(maze);
        this.propertyChangeSupport.firePropertyChange("LevelDesigner.mazes",
                null, this.mazes);
    }

    public void addMaze() {
        addMaze(new Maze(new MazeProperties(this.mazes.size(), 20, 20, 5)));
    }

    /**
     * Remove a maze from the designer.
     * @param maze The maze to remove.
     */
    public void removeMaze(Maze maze) {
        mazes.remove(maze);
        this.propertyChangeSupport.firePropertyChange("LevelDesigner.mazes",
                null, this.mazes);
    }

    public void removeMaze(int index) {
        mazes.remove(index);
        this.propertyChangeSupport.firePropertyChange("LevelDesigner.mazes",
                null, this.mazes);
    }

    public void removeMaze() {
        removeMaze(this.mazes.size() - 1);
    }

    /**
     * Get the maze list.
     * @return The maze list.
     */
    public ArrayList<Maze> getMazes() {
        return mazes;
    }

    /**
     * Get the maze at the given index.
     * @param index The index of the maze.
     */
    public Maze getMaze(int index) {
        return mazes.get(index);
    }

    /**
     * Get the current maze.
     * @return The current maze.
     */
    public Maze getCurrentMaze() {
        return mazes.get(currentMazeIndex);
    }

    /**
     * Set the current maze.
     * @param maze The maze to set as current.
     */
    public void setCurrentMaze(Maze maze) {
        this.propertyChangeSupport.firePropertyChange("LevelDesigner.currentMazeIndex",
                currentMazeIndex, mazes.indexOf(maze));
        currentMazeIndex = mazes.indexOf(maze);
    }

    public void setCurrentMaze(int index) {
        if (0 <= index && index < mazes.size()) {
            this.propertyChangeSupport.firePropertyChange("LevelDesigner.currentMazeIndex",
                    currentMazeIndex, index);
            currentMazeIndex = index;
        }
    }

    public void nextMaze() {
        setCurrentMaze(currentMazeIndex + 1);
    }

    public void previousMaze() {
        setCurrentMaze(currentMazeIndex - 1);
    }

    /**
     * Get the level properties.
     * @return The level properties.
     */
    public LevelProperties getProperties() {
        return properties;
    }

    /**
     * Get the link list.
     * @return The link list.
     */
    public ArrayList<Link> getLinks() {
        return links;
    }

    /**
     * Get the links that are connected to the given coordinate and a given maze id.
     * @param mazeId The id of the maze.
     * @param coordinate The coordinate of the link.
     * @return The links that are connected to the given coordinate and a given maze id.
     */
    public ArrayList<Link> getLinks(int mazeId, Coordinate coordinate) {
        ArrayList<Link> links = new ArrayList<>();
        for (Link link : this.links)
            if (link.getLinkPoints().contains(new LinkPoint(mazeId, coordinate)))
                links.add(link);
        return links;
    }

    /**
     * Add a link to the link list.
     * @param link The link to add.
     */
    public void addLink(Link link) throws IllegalArgumentException {
        if (links.contains(link))
            throw new IllegalArgumentException("The link already exists.");

        links.add(link);
        this.propertyChangeSupport.firePropertyChange("LevelDesigner.links",
                null, this.links);
    }

    /**
     * Remove a link from the link list.
     * @param link The link to remove.
     */
    public void removeLink(Link link) {
        links.remove(link);
        this.propertyChangeSupport.firePropertyChange("LevelDesigner.links",
                null, this.links);
    }

    /**
     * Remove a list of links from the link list.
     * @param links The links to remove.
     */
    public void removeLinks(ArrayList<Link> links) {
        this.links.removeAll(links);
        this.propertyChangeSupport.firePropertyChange("LevelDesigner.links",
                null, this.links);
    }

    /**
     * Add a property change listener to the maze.
     * @param listener The listener to add.
     */
    public void addListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove a property change listener from the maze.
     * @param listener The listener to remove.
     */
    public void removeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Export the maze to a file.
     * @see MazeGenerator
     */
    public void export() {
        MazeGenerator mazeGenerator = new MazeGenerator(this);
        mazeGenerator.generate();
    }

    /**
     * This method is called when a property is changed.
     * @param evt The event that triggered the method.
     */
    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("MazeDesigner.blocks")) {
            ArrayList<Block> newBlocks = (ArrayList<Block>) evt.getNewValue();
            ArrayList<Block> oldBlocks = (ArrayList<Block>) evt.getOldValue();
            if (newBlocks.size() < oldBlocks.size()) {
                for (Block block : oldBlocks)
                    if (!newBlocks.contains(block)) {
                        this.removeLinks(this.getLinks(
                                ((Maze) evt.getSource()).getProperties().getId(), block.getCoordinate()));
                    }
            }
        }
    }
}
