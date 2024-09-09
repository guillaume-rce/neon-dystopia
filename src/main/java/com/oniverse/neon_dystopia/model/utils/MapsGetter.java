package com.oniverse.neon_dystopia.model.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This class is used to get the maps from the resource.
 * It will find the maps in the resource and store them in an ArrayList of XMLReader.
 * @see XMLReader
 */
public class MapsGetter {
    /**
     * The path of the maps.
     */
    public static final String path = "/com/oniverse/neon_dystopia/mazes/";
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
        // this.findHistoryMaps();
    }

    /**
     * This method is used to find the player maps in the resource.
     * It will load every .xml file in the given path and add it to the list.
     */
    private void findPlayerMaps() {
        URL url = getClass().getResource(path + "player/");
        if (url == null) {
            System.out.println("No player maps found");
            return;
        }

        if (url.getProtocol().equals("jar")) {
            String jarPath = url.getPath().substring(6, url.getPath().indexOf("!"));
            try (JarFile jarFile = new JarFile(jarPath)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.startsWith(path.substring(1) + "player/") && !entry.isDirectory() && name.endsWith(".xml")) {
                        try {
                            String filePath = path + "player/" + name.substring(name.lastIndexOf("/") + 1);
                            System.out.println("filePath: " + filePath);
                            XMLReader xmlReader = new XMLReader(filePath);
                            this.playersMaps.add(xmlReader);
                        } catch (Exception e) {
                            System.out.println("Error while reading player map " + name + " : " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error while reading player maps : " + e.getMessage());
            }
        }
        else
        {
            File folder = new File(url.getPath());
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
                        String filePath = path + "player/" + file.getName();
                        XMLReader xmlReader = new XMLReader(filePath);
                        this.playersMaps.add(xmlReader);
                    } catch (Exception e) {
                        System.out.println("Error while reading player map " + file.getName() + " : " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * This method is used to find the history maps in the resource.
     * It will load every .xml file in the given path and add it to the list.
     */
    private void findHistoryMaps() {
        URL url = getClass().getResource(path + "game/");
        if (url == null) {
            System.out.println("No history maps found");
            return;
        }

        File folder = new File(url.getPath());
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
                    XMLReader xmlReader = new XMLReader(url.getPath() + file.getName());
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
