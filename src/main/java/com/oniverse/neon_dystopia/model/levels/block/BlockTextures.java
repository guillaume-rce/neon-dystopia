package com.oniverse.neon_dystopia.model.levels.block;

import java.io.File;
import java.util.ArrayList;

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
        this.findTextures(path);
    }

    /**
     * This method is used to find the textures in the given path.
     * It will load every .png file in the given path and add it to the list.
     *
     * @param path The path of the textures.
     */
    private void findTextures(String path) {
        // Navigate in the given path and add the textures to the list
        File folder = new File(path);
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
