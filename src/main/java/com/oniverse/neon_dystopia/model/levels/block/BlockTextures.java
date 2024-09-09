package com.oniverse.neon_dystopia.model.levels.block;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This class is used to store the textures of a block.
 */
public class BlockTextures {
    /**
     * The list of the textures.
     */
    private final ArrayList<String> textures;

    /**
     * Constructor
     *
     * @param path The path of the textures.
     */
    public BlockTextures(String path) {
        textures = new ArrayList<>();
        try {
            this.findTextures(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to find the textures in the given path.
     * It will load every .png file in the given path and add it to the list.
     *
     * @param path The path of the textures.
     */
    private void findTextures(String path) throws IOException {
        // Navigate in the given path and add the textures to the list
        String pathInJar = path.substring(1);
        URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("The path " + path + " doesn't exist");
            return;
        }

        if (url.getProtocol().equals("jar")) {
            String jarPath = url.getPath().substring(6, url.getPath().indexOf("!"));
            try (JarFile jarFile = new JarFile(jarPath)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.startsWith(pathInJar) && !entry.isDirectory() && name.endsWith(".png")) {
                        textures.add(name.substring(name.lastIndexOf("/") + 1));
                    }
                }
            }
        }
        else
        {
            File folder = new File(url.getPath());
            File[] listOfFiles = folder.listFiles();

            try{
                assert listOfFiles != null;
            } catch (AssertionError e) {
                System.out.println("The path " + path + " doesn't exist");
                return;
            }

            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    textures.add(file.getName());
                }
            }
        }
    }

    /**
     * This method is used to get the number of textures.
     *
     * @return The number of textures.
     */
    public int getNbTextures() {
        return textures.size();
    }

    /**
     * This method is used to get the texture with a given name.
     *
     * @param name The name of the texture.
     * @return The texture at the given index.
     */
    public String getTexture(String name) {
        return textures.get(textures.indexOf(name));
    }

    /**
     * This method is used to get the texture at the given index.
     *
     * @param index The index of the texture.
     * @return The texture at the given index.
     */
    public String getTexture(int index) {
        return textures.get(index);
    }

    /**
     * Get the index of the texture by its name.
     * @param name The name of the texture.
     * @return The index of the texture.
     */
    public int getTextureIndex(String name) {
        return textures.indexOf(name);
    }

    /**
     * Get the texture that contains the given string.
     *
     * @param name The string to find.
     *             It can be a part of the name of the texture.
     * @return The texture that contains the given string.
     */
    public String getTextureContains(String name) {
        for (String texture : textures) {
            if (texture.contains(name))
                return texture;
        }
        return null;
    }
}
