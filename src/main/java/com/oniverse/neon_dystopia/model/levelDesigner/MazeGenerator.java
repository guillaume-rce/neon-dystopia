package com.oniverse.neon_dystopia.model.levelDesigner;

import com.oniverse.neon_dystopia.model.utils.XMLWriter;

import java.io.InputStream;

/**
 * MazeGenerator : Class that generates a maze from a MazeDesigner.
 * The generated maze is saved in the resources/com/oniverse/neon_dystopia/mazes/player folder.
 * The name of the generated file is playerMaze_<mazeName>.xml
 * The generated file is an XML file that can be loaded by the game.
 * @see LevelDesigner
 * @see XMLWriter
 */
public class MazeGenerator {
    /**
     * The path where the generated level is saved. (user documents folder + neon_dystopia/mazes/player)
     */
    public static final String outputPath = System.getProperty("user.home") + "/neon_dystopia/mazes/player";

    /**
     * The path where the generated level is saved, in the resources' folder.
     */
    public static final String resourcesPath = "/com/oniverse/neon_dystopia/mazes/player";

    /**
     * The level designer used to generate the maze.
     * @see LevelDesigner
     */
    private final LevelDesigner levelDesigner;

    /**
     * Constructor of the MazeGenerator class.
     * @param levelDesigner The maze designer used to generate the maze.
     *                     @see LevelDesigner
     */
    public MazeGenerator(LevelDesigner levelDesigner) {
        this.levelDesigner = levelDesigner;
    }

    /**
     * This method is used to generate the maze.
     * The generated maze is saved in the resources/com/oniverse/neon_dystopia/mazes/player folder.
     * The name of the generated file is playerMaze_<mazeName>.xml
     * The generated file is an XML file that can be loaded by the game.
     * @see XMLWriter The class used to write the XML file.
     */
    public void generate() {
        String mazeName = this.levelDesigner.getProperties().getName().replace(" ", "-");
        String mazeVersion = this.levelDesigner.getProperties().getVersion().replace(".", "-");
        String outputPath = MazeGenerator.outputPath + "/" + "playerMaze_" + mazeName + "_" + mazeVersion + ".xml";
        try {
            XMLWriter xmlWriter = new XMLWriter(outputPath);
            xmlWriter.mazeDesignerToXML(this.levelDesigner);
            xmlWriter.save(outputPath);
        } catch (Exception e) {
            System.out.println("Error while generating maze " + mazeName + " : " + e.getMessage());
        }
    }
}
