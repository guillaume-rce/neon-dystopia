package com.oniverse.neon_dystopia.model.utils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This class is used to get the maps from the resource.
 * It will find the maps in the resource and store them in an ArrayList of XMLReader.
 * @see XMLReader
 */
public class MapsGetter {
    /**
     * The path of the file containing the saved directory.
     */
    public static final String SAVED_DIRECTORY_FILE = "/com/oniverse/neon_dystopia/saved_directory.txt";
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
        this.findOtherFiles();
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
                            XMLReader xmlReader = new XMLReader(filePath, true);
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
                        XMLReader xmlReader = new XMLReader(filePath, true);
                        this.playersMaps.add(xmlReader);
                    } catch (Exception e) {
                        System.out.println("Error while reading player map " + file.getName() + " : " + e.getMessage());
                    }
                }
            }
        }
    }

    private void findOtherFiles() {
        try (InputStream in = getClass().getResourceAsStream(SAVED_DIRECTORY_FILE)) {
            if (in == null) {
                System.out.println("No saved directory found");
                return;
            }
            try {
                // Every line is a path to a directory
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    String directoryPath = new String(buffer, 0, bytesRead);
                    File directory = new File(directoryPath);
                    this.loadMapsFromDirectory(directory);
                }
            } catch (IOException e) {
                System.out.println("Error while reading saved directory : " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error while opening saved directory file : " + e.getMessage());
        }
    }

    /**
     * This method is used to load the maps from a directory.
     * It will load every .xml file in the given directory and add it to the list.
     * @see XMLReader
     *
     * @param directory The directory to load the maps from.
     */
    public void loadMapsFromDirectory(File directory) {
        System.out.println("Loading maps from directory " + directory.getPath());
        File[] listOfFiles = directory.listFiles();
        if (listOfFiles == null) {
            System.out.println("No player maps found");
            return;
        }

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".xml")) {
                try {
                    String filePath = file.getPath();
                    XMLReader xmlReader = new XMLReader(filePath, false);
                    this.playersMaps.add(xmlReader);
                } catch (Exception e) {
                    System.out.println("Error while reading player map " + file.getName() + " : " + e.getMessage());
                }
            }
        }

        // Add the directory to the file if it has not been added already
        try {
            addDirectoryToFile(directory.getPath());
        } catch (IOException e) {
            System.out.println("Error while adding directory to saved directory file: " + e.getMessage());
        }
    }

    private void addDirectoryToFile(String directoryPath) throws IOException {
        // Get the file path of the saved directory file
        URL url = getClass().getResource(SAVED_DIRECTORY_FILE);
        if (url == null) {
            System.out.println("No saved directory file found");
            return;
        }

        File file = new File(url.getPath());

        // Read the file to check if the directory is already in it
        Set<String> directories = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                directories.add(line);
            }
        }

        // Add the directory to the set if it is not already in it
        if (!directories.contains(directoryPath)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(directoryPath);
                writer.newLine();
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
                    XMLReader xmlReader = new XMLReader(url.getPath() + file.getName(), true);
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
