package com.oniverse.neon_dystopia.model.utils;

import java.io.File;
import java.util.ArrayList;

/**
 * This class is used to get the maps from the resource.
 * It will find the maps in the resource and store them in an ArrayList of XMLReader.
 * @see XMLReader
 */
public class MapsGetter {
    /**
     * The path of the maps.
     */
    public static final String path = "src/main/resources/com/oniverse/neon_dystopia/mazes/";
    /**
     * The list of the player maps.
     */
    private final ArrayList<XMLReader> playersMaps = new ArrayList<>();
    /**
     * The list of the history maps.
     */
    private final ArrayList<XMLReader> historyMaps = new ArrayList<>();

    /**
     * Constructor: It will find the maps in the resource and store them in an ArrayList of XMLReader.
     * @see XMLReader
     */
    public MapsGetter() {
        this.findPlayerMaps();
        this.findHistoryMaps();
    }

    /**
     * This method is used to find the player maps in the resource.
     * It will load every .xml file in the given path and add it to the list.
     */
    private void findPlayerMaps() {
        File folder = new File(path + "player/");
        if (!folder.exists())
            return;

        File[] listOfFiles = folder.listFiles();

        try{
            assert listOfFiles != null;
        } catch (AssertionError e) {
            System.out.println("No player maps found");
            return;
        }

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".xml")) {
                try {
                    XMLReader xmlReader = new XMLReader(path + "player/" + file.getName());
                    this.playersMaps.add(xmlReader);
                } catch (Exception e) {
                    System.out.println("Error while reading player map " + file.getName() + " : " + e.getMessage());
                }
            }
        }
    }

    /**
     * This method is used to find the history maps in the resource.
     * It will load every .xml file in the given path and add it to the list.
     */
    private void findHistoryMaps() {
        File folder = new File(path + "game/");
        if (!folder.exists())
            return;

        File[] listOfFiles = folder.listFiles();

        try{
            assert listOfFiles != null;
        } catch (AssertionError e) {
            System.out.println("No history maps found");
            return;
        }

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".xml")) {
                try {
                    XMLReader xmlReader = new XMLReader(path + "history/" + file.getName());
                    this.historyMaps.add(xmlReader);
                } catch (Exception e) {
                    System.out.println("Error while reading history map " + file.getName() + " : " + e.getMessage());
                }
            }
        }
    }

    public ArrayList<XMLReader> getPlayerMaps() {
        return this.playersMaps;
    }

    public ArrayList<XMLReader> getHistoryMaps() {
        return this.historyMaps;
    }
}
