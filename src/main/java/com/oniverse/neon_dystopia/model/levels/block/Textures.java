package com.oniverse.neon_dystopia.model.levels.block;

/**
 * Textures : Class that represents the textures of a block.
 * It will store the current texture and the BlockTextures object.
 */
public class Textures {
    /**
     * The path to find the textures.
     */
    private static final String basicPath = "/com/oniverse/neon_dystopia/textures/blocks/";
    /**
     * The id of the block.
     */
    public BlockId id;
    /**
     * The current texture.
     */
    private String currentTexture;
    /**
     * The BlockTextures object.
     * @see BlockTextures
     */
    public final BlockTextures textures;

    public Textures(BlockId id) {
        this(id, null);
    }

    /**
     * Constructor: It will create a new BlockTextures object and store the current texture.
     *
     * @param id The id of the block.
     * @param currentTexture The current texture.
     */
    public Textures(BlockId id, String currentTexture) {
        this.id = id;
        String path = TexturesPath.getTexturePath(id);
        this.textures = new BlockTextures(basicPath + path);
        this.currentTexture = currentTexture == null? this.textures.getTexture(0) : currentTexture;
    }

    /**
     * Constructor: It will create a new BlockTextures object and store the current texture.
     *
     * @param textures The textures to copy.
     */
    public Textures(Textures textures) {
        this(textures.getId(), textures.getCurrentTexture());
    }

    /**
     * This method is used to change the current texture to the next one.
     */
    public void nextTexture() {
        if (this.textures.getNbTextures() > this.textures.getTextureIndex(this.currentTexture) + 1)
            this.currentTexture = this.textures.getTexture(
                    this.textures.getTextureIndex(this.currentTexture) + 1);
    }

    /**
     * This method is used to change the current texture to the previous one.
     */
    public void previousTexture() {
        if (this.textures.getTextureIndex(this.currentTexture) > 0)
            this.currentTexture = this.textures.getTexture(
                    this.textures.getTextureIndex(this.currentTexture) - 1);
    }

    /**
     * This method is used to set the current texture to the given one.
     *
     * @param newTexture The new current texture.
     */
    public void setCurrentTexture(String newTexture) {
        this.currentTexture = newTexture;
    }

    /**
     * This method is used to get the current texture.
     *
     * @return The current texture.
     */
    public String getCurrentTexture() {
        return currentTexture;
    }

    /**
     * This method is used to get the id of the block.
     *
     * @return The id of the block.
     */
    public BlockId getId() {
        return id;
    }

    /**
     * This method is used to get the path of the current texture.
     * <p>
     * It will return the first texture if the current texture doesn't exist.
     * It will prevent the game from crashing. (For example with a map made with an older version of the game)
     * </p>
     *
     * @return The path of the current texture.
     */
    public String getTexture() {
        try{
            return basicPath + TexturesPath.getTexturePath(id) + this.textures.getTexture(currentTexture);
        } catch (IndexOutOfBoundsException e) {
            return basicPath + TexturesPath.getTexturePath(id) + this.textures.getTexture(0);
        }
    }

    /**
     * This method is used to get the number of textures of the block.
     *
     * @param id The id of the block.
     * @return The number of textures of the block.
     */
    public static int getNbTextures(BlockId id) {
        String path = TexturesPath.getTexturePath(id);
        BlockTextures textures = new BlockTextures(basicPath + path);
        return textures.getNbTextures();
    }
}
