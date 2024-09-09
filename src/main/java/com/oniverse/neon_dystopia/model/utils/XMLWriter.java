package com.oniverse.neon_dystopia.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.model.levelDesigner.Maze;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.def.AmbientLight;
import org.w3c.dom.*;

/**
 * This class is used to write a xml file from a maze.
 * It specially designed to write the mazes to the xml file.
 * WARNING: I don't know why, but it creates the file just when we close the program.
 */
public class XMLWriter {
    /**
     * The document of the xml file.
     * @see Document
     */
    private final Document document;

    /**
     * The constructor of the XMLWriter class.
     * It will create the document.
     *
     * @param filePath The path of the xml file. It can be null, but it will not check if the file exists.
     * @throws ParserConfigurationException If the parser is not configured correctly.
     */
    public XMLWriter(String filePath) throws ParserConfigurationException {
        System.out.println("Generating XML file from " + filePath);
        if (filePath == null) {
            throw new IllegalArgumentException("The file path can't be null");
        }

        // Generate an empty document if the file doesn't exist
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File doesn't exist, generating empty file");
            file.getParentFile().mkdirs();
            this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            return;
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        this.document = db.newDocument();
    }

    /**
     * This method is used to set the maze designer to the xml file.
     * @param levelDesigner The maze designer to set.
     * @see LevelDesigner
     */
    public void mazeDesignerToXML(LevelDesigner levelDesigner) {
        Element levelElement = document.createElement("level");
        levelElement.setAttribute("name", levelDesigner.getProperties().getName());
        levelElement.setAttribute("author", levelDesigner.getProperties().getAuthor());
        levelElement.setAttribute("version", levelDesigner.getProperties().getVersion());
        document.appendChild(levelElement);

        if (!levelDesigner.getMazes().isEmpty()) {
            Element mazesElement = document.createElement("mazes");
            levelElement.appendChild(mazesElement);
            for (Maze maze : levelDesigner.getMazes()) {
                // ---- Root ----
                Element mazeElement = document.createElement("maze");
                mazeElement.setAttribute("id", String.valueOf(maze.getProperties().getId()));
                mazeElement.setAttribute("width", String.valueOf(maze.getProperties().getWidth()));
                mazeElement.setAttribute("height", String.valueOf(maze.getProperties().getHeight()));
                mazeElement.setAttribute("layers", String.valueOf(maze.getProperties().getLayers()));
                mazesElement.appendChild(mazeElement);

                // ---- Blocks ----
                if (!maze.getBlocks().isEmpty()) {
                    for (Block block : maze.getBlocks()) {
                        // ---- Block ----
                        Element blockElement = document.createElement("block");
                        blockElement.setAttribute("id", String.valueOf(block.id.getValue()));
                        if (block.id == BlockId.AMBIENT_LIGHT) {
                            blockElement.setAttribute("strength",
                                    String.valueOf(((AmbientLight) block).getStrength()));
                            blockElement.setAttribute("red",
                                    String.valueOf(((AmbientLight) block).getColor().getRed()));
                            blockElement.setAttribute("green",
                                    String.valueOf(((AmbientLight) block).getColor().getGreen()));
                            blockElement.setAttribute("blue",
                                    String.valueOf(((AmbientLight) block).getColor().getBlue()));
                            blockElement.setAttribute("alpha",
                                    String.valueOf(((AmbientLight) block).getColor().getOpacity()));
                        }

                        mazeElement.appendChild(blockElement);

                        // ---- Coordinate ----
                        Element coordinate = document.createElement("coordinate");
                        Element layer = document.createElement("layer");
                        layer.setTextContent(String.valueOf(block.getCoordinate().getLayer()));
                        coordinate.appendChild(layer);
                        Element x = document.createElement("x");
                        x.setTextContent(String.valueOf(block.getCoordinate().getX()));
                        coordinate.appendChild(x);
                        Element y = document.createElement("y");
                        y.setTextContent(String.valueOf(block.getCoordinate().getY()));
                        coordinate.appendChild(y);
                        blockElement.appendChild(coordinate);

                        // ---- Textures ----
                        Element textures = document.createElement("textures");
                        textures.setTextContent(block.getTexture().getCurrentTexture());
                        blockElement.appendChild(textures);
                    }
                }
            }
        }

        // ---- Links ----
        if (!levelDesigner.getLinks().isEmpty()) {
            Element links = document.createElement("links");
            levelElement.appendChild(links);

            for (Link link : levelDesigner.getLinks()) {
                // ---- Link ----
                Element linkElement = document.createElement("link");
                links.appendChild(linkElement);

                // ---- Source ----
                Element source = document.createElement("source");
                linkElement.appendChild(source);
                Element mazeName = document.createElement("mazeName");
                mazeName.setTextContent(String.valueOf(link.getSource().getMazeId()));
                source.appendChild(mazeName);
                Element layer = document.createElement("layer");
                layer.setTextContent(String.valueOf(link.getSource().getCoordinate().getLayer()));
                source.appendChild(layer);
                Element x = document.createElement("x");
                x.setTextContent(String.valueOf(link.getSource().getCoordinate().getX()));
                source.appendChild(x);
                Element y = document.createElement("y");
                y.setTextContent(String.valueOf(link.getSource().getCoordinate().getY()));
                source.appendChild(y);

                // ---- Target ----
                Element target = document.createElement("target");
                linkElement.appendChild(target);
                mazeName = document.createElement("mazeName");
                mazeName.setTextContent(String.valueOf(link.getTarget().getMazeId()));
                target.appendChild(mazeName);
                layer = document.createElement("layer");
                layer.setTextContent(String.valueOf(link.getTarget().getCoordinate().getLayer()));
                target.appendChild(layer);
                x = document.createElement("x");
                x.setTextContent(String.valueOf(link.getTarget().getCoordinate().getX()));
                target.appendChild(x);
                y = document.createElement("y");
                y.setTextContent(String.valueOf(link.getTarget().getCoordinate().getY()));
                target.appendChild(y);
            }
        }
    }

    /**
     * This method is used to save the xml file.
     * @param filePath The path of the xml file.
     * @throws TransformerException If the transformer is not configured correctly.
     * @throws FileNotFoundException If the file is not found.
     */
    public void save(String filePath) throws TransformerException, FileNotFoundException {
        // Use a Transformer for output
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        // send DOM to file
        tr.transform(new DOMSource(document),
                new StreamResult(new FileOutputStream(filePath)));
    }
}

// From https://stackoverflow.com/questions/7373567/how-to-read-and-write-xml-files
