package com.oniverse.neon_dystopia.model.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.Textures;
import com.oniverse.neon_dystopia.model.levels.block.def.AmbientLight;
import com.oniverse.neon_dystopia.model.levels.maze.Maze;
import javafx.scene.paint.Color;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * This class is used to read a xml file and create a maze from it.
 * It specially designed to read the mazes from the xml file.
 */
public class XMLReader {
    /**
     * The document of the xml file.
     * @see Document
     */
    private final Document document;

    /**
     * The constructor of the XMLReader class.
     * It will read the xml file and create the document.
     *
     * @param file The path of the xml file.
     * @throws ParserConfigurationException If the parser is not configured correctly.
     * @throws IOException If an I/O error occurs.
     * @throws SAXException If any parse errors occur.
     * @see Document
     */
    public XMLReader(String file) throws ParserConfigurationException, IOException, SAXException {
        InputStream fileObj = getClass().getResourceAsStream(file);
        if (fileObj == null) {
            throw new IOException("The file " + file + " doesn't exist");
        }


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        this.document = db.parse(fileObj);
        this.document.getDocumentElement().normalize();
    }

    /**
     * This method is used to get the document of the xml file.
     * @return The document of the xml file.
     * @see Document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * This method is used to get the name of the root element.
     * @return The name of the root element.
     */
    public String getRootName() {
        return document.getDocumentElement().getNodeName();
    }

    /**
     * This method is used to get the properties of the maze.
     * @return The properties of the maze.
     * @see LevelProperties
     */
    public LevelProperties getProperties() {
        NamedNodeMap attributes = document.getDocumentElement().getAttributes();
        String name = attributes.getNamedItem("name").getNodeValue();
        String author = attributes.getNamedItem("author").getNodeValue();
        String version = attributes.getNamedItem("version").getNodeValue();
        return new LevelProperties(name, version, author);
    }

    public ArrayList<com.oniverse.neon_dystopia.model.levelDesigner.Maze> getMazesForDesigner() {
        ArrayList<com.oniverse.neon_dystopia.model.levelDesigner.Maze> mazes = new ArrayList<>();
        Element mazesElement = (Element) document.getElementsByTagName("mazes").item(0);
        NodeList mazeNodeList = mazesElement.getElementsByTagName("maze");
        for (int temp = 0; temp < mazeNodeList.getLength(); temp++) {
            Node mazeNode = mazeNodeList.item(temp);
            if (mazeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element mazeElement = (Element) mazeNode;
                // ---- Properties ----
                MazeProperties mazeProperties = new MazeProperties(
                        Integer.parseInt(mazeElement.getAttribute("id")),
                        Integer.parseInt(mazeElement.getAttribute("width")),
                        Integer.parseInt(mazeElement.getAttribute("height")),
                        Integer.parseInt(mazeElement.getAttribute("layers")));
                // ---- Maze ----
                com.oniverse.neon_dystopia.model.levelDesigner.Maze maze =
                        new com.oniverse.neon_dystopia.model.levelDesigner.Maze(mazeProperties);

                // ---- Blocks ----
                NodeList blockNodeList = mazeElement.getElementsByTagName("block");
                for (int i = 0; i < blockNodeList.getLength(); i++) {
                    Node blockNode = blockNodeList.item(i);
                    if (blockNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element blockElement = (Element) blockNode;
                        // ---- Id ----
                        BlockId blockId = BlockId.getBlockIdFromId(
                                Integer.parseInt(blockElement.getAttribute("id")));
                        // ---- Coordinates ----
                        Element coordinateElement = (Element) blockElement.getElementsByTagName("coordinate").item(0);
                        int y = Integer.parseInt(coordinateElement.getElementsByTagName("y").item(0).getTextContent());
                        int x = Integer.parseInt(coordinateElement.getElementsByTagName("x").item(0).getTextContent());
                        int layer = Integer.parseInt(coordinateElement.getElementsByTagName("layer").item(0).getTextContent());
                        Coordinate coordinate = new Coordinate(x, y, layer);
                        // ---- Textures ----
                        Textures textures = new Textures(blockId,
                                blockElement.getElementsByTagName("textures").item(0).getTextContent());
                        // ---- Block ----
                        Block block = blockId.getBlockFromId(coordinate);
                        block.setTexture(textures);
                        if (blockId == BlockId.AMBIENT_LIGHT) {
                            if (blockElement.hasAttribute("strength")) {
                                ((AmbientLight) block).setStrength(
                                        Integer.parseInt(blockElement.getAttribute("strength")));
                            }

                            if (blockElement.hasAttribute("red") && blockElement.hasAttribute("green") &&
                                    blockElement.hasAttribute("blue") && blockElement.hasAttribute("alpha")) {
                                ((AmbientLight) block).setColor(
                                        new Color(
                                                Double.parseDouble(blockElement.getAttribute("red")),
                                                Double.parseDouble(blockElement.getAttribute("green")),
                                                Double.parseDouble(blockElement.getAttribute("blue")),
                                                Double.parseDouble(blockElement.getAttribute("alpha"))));
                            }
                        }
                        maze.addBlock(block);
                    }
                }
                mazes.add(maze);
            }
        }
        return mazes;
    }

    /**
     * This method is used to get the blocks of the maze.
     * Returns an empty ArrayList if there is no blocks.
     * @return The blocks of the maze.
     * @see Block
     */
    public ArrayList<Maze> getMazesForGame() {
        ArrayList<Maze> mazes = new ArrayList<>();
        Element mazesElement = (Element) document.getElementsByTagName("mazes").item(0);
        NodeList mazeNodeList = mazesElement.getElementsByTagName("maze");
        for (int temp = 0; temp < mazeNodeList.getLength(); temp++) {
            Node mazeNode = mazeNodeList.item(temp);
            if (mazeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element mazeElement = (Element) mazeNode;
                // ---- Properties ----
                MazeProperties mazeProperties = new MazeProperties(
                        Integer.parseInt(mazeElement.getAttribute("id")),
                        Integer.parseInt(mazeElement.getAttribute("width")),
                        Integer.parseInt(mazeElement.getAttribute("height")),
                        Integer.parseInt(mazeElement.getAttribute("layers")));
                // ---- Maze ----
                Maze maze = new Maze(mazeProperties);

                // ---- Blocks ----
                NodeList blockNodeList = mazeElement.getElementsByTagName("block");
                for (int i = 0; i < blockNodeList.getLength(); i++) {
                    Node blockNode = blockNodeList.item(i);
                    if (blockNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element blockElement = (Element) blockNode;
                        // ---- Id ----
                        BlockId blockId = BlockId.getBlockIdFromId(
                                Integer.parseInt(blockElement.getAttribute("id")));
                        // ---- Coordinates ----
                        Element coordinateElement = (Element) blockElement.getElementsByTagName("coordinate").item(0);
                        // Switch x and y because of the way the xml is written
                        int y = Integer.parseInt(coordinateElement.getElementsByTagName("x").item(0).getTextContent());
                        int x = Integer.parseInt(coordinateElement.getElementsByTagName("y").item(0).getTextContent());
                        int layer = Integer.parseInt(coordinateElement.getElementsByTagName("layer").item(0).getTextContent());
                        Coordinate coordinate = new Coordinate(x, y, layer);
                        // ---- Textures ----
                        Textures textures = new Textures(blockId,
                                blockElement.getElementsByTagName("textures").item(0).getTextContent());
                        // ---- Block ----
                        Block block = blockId.getBlockFromId(coordinate);
                        block.setTexture(textures);

                        if (blockId == BlockId.AMBIENT_LIGHT) {
                            if (blockElement.hasAttribute("strength")) {
                                ((AmbientLight) block).setStrength(
                                        Integer.parseInt(blockElement.getAttribute("strength")));
                            }

                            if (blockElement.hasAttribute("red") && blockElement.hasAttribute("green") &&
                                    blockElement.hasAttribute("blue") && blockElement.hasAttribute("alpha")) {
                                ((AmbientLight) block).setColor(
                                        new Color(
                                                Double.parseDouble(blockElement.getAttribute("red")),
                                                Double.parseDouble(blockElement.getAttribute("green")),
                                                Double.parseDouble(blockElement.getAttribute("blue")),
                                                Double.parseDouble(blockElement.getAttribute("alpha"))));
                            }
                        }

                        maze.addBlock(block);
                    }
                }
                mazes.add(maze);
            }
        }
        return mazes;
    }

    /**
     * This method is used to get the links of the maze.
     * Returns an empty ArrayList if there is no links.
     * @return The links of the maze.
     * @see Link
     */
    public ArrayList<Link> getLinks(boolean forDesigner) {
        ArrayList<Link> links = new ArrayList<>();
        Element linksElement = (Element) document.getElementsByTagName("links").item(0);
        if (linksElement == null) {
            return links;
        }
        NodeList nList = linksElement.getElementsByTagName("link");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node linkNode = nList.item(temp);
            if (linkNode.getNodeType() == Node.ELEMENT_NODE) {
                Element linkElement = (Element) linkNode;
                // ---- Source ----
                NodeList sourceNodeList = linkElement.getElementsByTagName("source");
                Element sourceCoordinateElement = (Element) sourceNodeList.item(0);

                Coordinate source;
                int sourceLayer = Integer.parseInt(sourceCoordinateElement.getElementsByTagName("layer").item(0).getTextContent());
                int sourceY = Integer.parseInt(sourceCoordinateElement.getElementsByTagName("y").item(0).getTextContent());
                int sourceX = Integer.parseInt(sourceCoordinateElement.getElementsByTagName("x").item(0).getTextContent());

                if (!forDesigner) {
                    // Switch x and y because of the way the xml is written
                    source = new Coordinate(sourceY, sourceX, sourceLayer);
                } else {
                    source = new Coordinate(sourceX, sourceY, sourceLayer);
                }

                // ---- Target ----
                NodeList targetNodeList = linkElement.getElementsByTagName("target");
                Element targetCoordinateElement = (Element) targetNodeList.item(0);

                Coordinate target;
                int targetLayer = Integer.parseInt(targetCoordinateElement.getElementsByTagName("layer").item(0).getTextContent());
                int targetY = Integer.parseInt(targetCoordinateElement.getElementsByTagName("y").item(0).getTextContent());
                int targetX = Integer.parseInt(targetCoordinateElement.getElementsByTagName("x").item(0).getTextContent());

                if (!forDesigner) {
                    // Switch x and y because of the way the xml is written
                    target = new Coordinate(targetY, targetX, targetLayer);
                } else {
                    target = new Coordinate(targetX, targetY, targetLayer);
                }

                // ---- Maze name ----
                int sourceMazeId = Integer.parseInt(sourceCoordinateElement.getElementsByTagName("mazeName").item(0).getTextContent());
                int targetMazeId = Integer.parseInt(targetCoordinateElement.getElementsByTagName("mazeName").item(0).getTextContent());

                // ---- Link ----
                Link link = new Link(new LinkPoint(sourceMazeId, source), new LinkPoint(targetMazeId, target));
                links.add(link);
            }
        }
        return links;
    }

    @Override
    public String toString() {
        LevelProperties mazeProperties = this.getProperties();
        return mazeProperties.getName() + "\t\t" +
                mazeProperties.getAuthor() + "\t\t" +
                mazeProperties.getVersion();
    }
}

// From https://www.delftstack.com/fr/howto/java/java-read-xml/