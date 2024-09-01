package com.oniverse.neon_dystopia.model.utils;

import com.oniverse.neon_dystopia.model.levels.Level;
import com.oniverse.neon_dystopia.model.levels.maze.Maze;

import java.util.ArrayList;

/**
 * This class is used to define a level feed.
 * It's used to iterate through the levels.
 * It's an ArrayList of mazes.
 *
 * @see Level
 * @see ArrayList
 */
public class MazesFeed {
    /**
     * The levels of the feed.
     * @see Level
     * @see ArrayList
     */
    private final ArrayList<Level> levels;
    /**
     * The current index of the feed.
     */
    private int currentIndex = 0;

    /**
     * The constructor of the MazesFeed class.
     * It is used to define the level feed.
     *
     * @see Level
     * @see ArrayList
     */
    public MazesFeed() {
        this.levels = new ArrayList<>();
    }

    /**
     * This method is used to add a level to the feed.
     * @param level The level to add.
     * @see Maze
     */
    public void addLevel(Level level) {
        this.levels.add(level);
    }

    /**
     * This method is used to know if there are next levels.
     * @return True if there is a next levels, false otherwise.
     */
    public boolean hasNext() {
        return this.currentIndex < this.levels.size();
    }

    /**
     * This method is used to reset the feed.
     */
    public void reset() {
        this.currentIndex = 0;
    }

    /**
     * This method is used to iterate through the feed.
     */
    public void iterate() {
        this.currentIndex++;
    }

    /**
     * This method is used to get the current maze. It also iterate through the feed.
     * @return The current maze.
     * @throws IndexOutOfBoundsException If there is no more mazes.
     */
    public Level getCurrentLevel() throws IndexOutOfBoundsException {
        if (this.currentIndex >= this.levels.size())
            throw new IndexOutOfBoundsException("No more mazes");
        Level level = this.levels.get(this.currentIndex);
        return level;
    }
}
