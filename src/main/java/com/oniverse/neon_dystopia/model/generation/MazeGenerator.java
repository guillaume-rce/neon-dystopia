package com.oniverse.neon_dystopia.model.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MazeGenerator class that generates a maze using the recursive backtracking algorithm.
 */
public class MazeGenerator {

    private final int size;
    private final int[] start;
    private final Random random;

    /**
     * Constructor to initialize the MazeGenerator.
     * @param size The size of the maze (size x size).
     * @param seed The seed to use for the random generator (for reproducibility).
     * @param start The start point of the maze.
     */
    public MazeGenerator(int size, long seed, int[] start) {
        this.size = size;
        this.start = start;
        this.random = new Random(seed);
    }

    /**
     * Main method to test maze generation.
     * @param args The command-line arguments (not used here).
     */
    public static void main(String[] args) {
        int size = 10;
        int seed = 123;
        int[] start = {0, 1};
        int[] end = {8, 8};

        MazeGenerator mazeGenerator = new MazeGenerator(size, seed, start);
        int[][] maze = mazeGenerator.generateMaze();
        maze[end[0]][end[1]] = 2;
        maze[start[0]][start[1]] = 3;

        // Display the generated maze
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Generates a maze with the given size, seed, start point, and end point.
     * @return The generated maze as a 2D integer array (1 = wall, 15 = path).
     */
    public int[][] generateMaze() {
        int[][] maze = new int[size][size];
        generateMazeRec(maze, start);
        return maze;
    }

    /**
     * Recursive function to generate the maze.
     * @param maze The maze being generated.
     * @param start The starting point for the current generation.
     */
    private void generateMazeRec(int[][] maze, int[] start) {
        List<String> directions = getDirections(maze, start);
        if (directions.isEmpty()) {
            return;
        }

        String direction = directions.get(random.nextInt(directions.size()));
        int[] nextPoint = getNextPoint(start, direction);
        maze[nextPoint[0]][nextPoint[1]] = 1;
        maze[(start[0] + nextPoint[0]) / 2][(start[1] + nextPoint[1]) / 2] = 1;
        generateMazeRec(maze, nextPoint);
        generateMazeRec(maze, start);
    }

    /**
     * Gets the possible directions from a given point in the maze.
     * @param maze The maze being generated.
     * @param point The point from which to get the possible directions.
     * @return A list containing the possible directions ('N' for north, 'E' for east, 'S' for south, 'W' for west).
     */
    private List<String> getDirections(int[][] maze, int[] point) {
        List<String> directions = new ArrayList<>();
        if (point[0] - 2 >= 0 && maze[point[0] - 2][point[1]] == 0) {
            directions.add("N");
        }
        if (point[1] + 2 < maze[0].length && maze[point[0]][point[1] + 2] == 0) {
            directions.add("E");
        }
        if (point[0] + 2 < maze.length && maze[point[0] + 2][point[1]] == 0) {
            directions.add("S");
        }
        if (point[1] - 2 >= 0 && maze[point[0]][point[1] - 2] == 0) {
            directions.add("W");
        }
        return directions;
    }

    /**
     * Gets the next point based on the given direction.
     * @param point The starting point.
     * @param direction The direction to follow ('N' for north, 'E' for east, 'S' for south, 'W' for west).
     * @return The next point in the given direction.
     */
    private int[] getNextPoint(int[] point, String direction) {
        int[] nextPoint = new int[2];
        switch (direction) {
            case "N" -> {
                nextPoint[0] = point[0] - 2;
                nextPoint[1] = point[1];
            }
            case "E" -> {
                nextPoint[0] = point[0];
                nextPoint[1] = point[1] + 2;
            }
            case "S" -> {
                nextPoint[0] = point[0] + 2;
                nextPoint[1] = point[1];
            }
            case "W" -> {
                nextPoint[0] = point[0];
                nextPoint[1] = point[1] - 2;
            }
            default -> throw new IllegalArgumentException("Direction not valid");
        }
        return nextPoint;
    }
}
