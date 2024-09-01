package com.oniverse.neon_dystopia.model.levels.block;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.Level;
import com.oniverse.neon_dystopia.model.utils.TeleportationTarget;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to define a block.
 */
public class Block implements PropertyChangeListener{
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    public final int orientation;

    /**
     * This boolean variable is used to define if the player is on the block.
     */
    protected boolean isOnIt = false;
    /**
     * Used to define if the block need to be displayed.
     */
    private boolean display;
    /**
     * Used to define if the block is solid.
     */
    private boolean isSolid;
    /**
     * This variable is used to fire a property change.
     */
    protected final PropertyChangeSupport propertyChangeSupport;
    /**
     * This list is used to store the activators of the block.
     */
    protected List<Block> activators;
    /**
     * The list of the blocks that activated the block.
     */
    protected List<Block> activated;
    /**
     * The id of the block.
     *
     * @see BlockId
     */
    public BlockId id;
    /**
     * The type of the block.
     *
     * @see BlockType
     */
    public BlockType type;
    /**
     * The coordinates of the block.
     */
    private final Coordinate coordinate;
    /**
     * The texture of the block.
     * @see Textures
     */
    protected Textures textures;
    /**
     * The teleportation target of the block.
     *
     * @see TeleportationTarget
     */
    protected TeleportationTarget teleportationTarget;
    /**
     * The level of the block.
     *
     * @see Level
     */
    protected Level level;


    /**
     * The constructor of the Block class.
     *
     * @param id The id of the block.
     *           @see BlockId
     * @param type The type of the block.
     *           @see BlockType
     * @param isSolid Used to define if the player can walk on the block.
     */
    public Block(Coordinate coordinate, BlockId id, BlockType type, boolean isSolid, int orientation){
        this(coordinate, id, type, isSolid, true, orientation);
    }

    /**
     * The constructor of the Block class.
     *
     * @param id The id of the block.
     *           @see BlockId
     * @param type The type of the block.
     *           @see BlockType
     * @param isSolid Used to define if the player can walk on the block.
     * @param display Used to define if the block need to be displayed.
     */
    public Block(Coordinate coordinate, BlockId id, BlockType type, boolean isSolid,
                 boolean display, int orientation){
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.coordinate = coordinate;
        this.id = id; this.type = type; this.isSolid = isSolid;
        this.activators = new ArrayList<>(); this.activated = new ArrayList<>();
        this.display = display;
        this.textures = new Textures(id);
        this.orientation = orientation;
    }

    /**
     * The constructor of the Block class. Used to copy a block.
     *
     * @param block The block to copy.
     */
    public Block(Block block){
        this(
            new Coordinate(block.getCoordinate()),
            block.id,
            block.type,
            block.isSolid,
            block.display,
            block.orientation
        );
        this.level = block.level;
        this.isOnIt = block.isOnIt;
        this.textures = new Textures(block.textures);
        this.teleportationTarget = block.teleportationTarget;
    }

    /**
     * The constructor of the Block class. Used to copy a block and change the texture.
     * @param block The block to copy.
     * @param textures The new textures of the block.
     */
    public Block(Block block, Textures textures){
        this(
                new Coordinate(block.getCoordinate()),
                block.id,
                block.type,
                block.isSolid,
                block.display,
                block.orientation
        );
        this.isOnIt = block.isOnIt;
        this.textures = textures;
        this.teleportationTarget = block.teleportationTarget;
        this.level = block.level;
    }

    /**
     * Get the block textures.
     *
     * @return The block textures.
     * @see Textures
     */
    public Textures getTexture() {
        return this.textures;
    }

    /**
     * Set the texture of the block.
     *
     * @param textures The new texture of the block.
     *                 @see Textures
     */
    public void setTexture(Textures textures){
        this.textures = textures;
    }

    /**
     * Define if the block has multiple textures.
     * @return True if the block have multiple textures, false otherwise.
     */
    public boolean haveMultipleTextures(){
        return false;  // TODO: Refactor this method
    }

    /**
     * Define if the block is a transporter.
     * @return True if the block is a transporter, false otherwise.
     */
    public boolean isTransporter(){
        return false;  // TODO: Refactor this method
    }

    /**
     * Method to move the block to a new coordinate.
     * Fire a property change.
     *
     * @param coordinate The new coordinate of the block.
     * @see Coordinate
     * @see java.beans.PropertyChangeSupport#firePropertyChange(PropertyChangeEvent)
     */
    public void moveTo(Coordinate coordinate){
        this.propertyChangeSupport.firePropertyChange("Block.Coordinate",
                this.coordinate, coordinate);
        this.coordinate.setCoordinate(coordinate);
    }

    /**
     * Add a listener to the block.
     *
     * @param listener The listener to add.
     */
    public void addListener(PropertyChangeListener listener){
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove a listener to the block.
     *
     * @param listener The listener to remove.
     */
    public void removeListener(PropertyChangeListener listener){
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Add an activator to the block.
     *
     * @param activator The activator to add.
     */
    public void addActivator(Block activator){
        this.activators.add(activator);
        activator.addListener(this);
    }

    /**
     * Remove an activator to the block.
     *
     * @param activator The activator to remove.
     */
    public void removeActivator(Block activator){
        this.activators.remove(activator);
        activator.removeListener(this);
    }

    /**
     * Said if the player enter on the block.
     */
    public void enter(){
        if(!this.isOnIt) {
            this.isOnIt = true;
        }
    }

    /**
     * Lunch the action of the block.
     */
    protected void activate(){
        // Define in subclasses
    }

    /**
     * Said if the player exit on the block.
     */
    public void exit(){
        if(this.isOnIt) {
            this.isOnIt = false;
        }
    }

    /**
     * Update the block. Used to update the display property.
     */
    public void update() {
        if (this.isTransporter() && this.activators.isEmpty())
            this.display = true;
        // Define in subclasses but please call super.update()
    }

    /**
     * Set the level of the block.
     * @param level The new level of the block.
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Set the solid property of the block.
     *
     * @param isSolid The new value of the solid property.
     */
    public void setSolid(boolean isSolid){
        this.isSolid = isSolid;
    }

    /**
     * Set the teleportation target of the transporter.
     *
     * @param teleportationTarget The teleportation target of the transporter.
     */
    public void setTeleportationTarget(TeleportationTarget teleportationTarget) {
        this.teleportationTarget = teleportationTarget;
    }

    /**
     * Set the display property of the block.
     *
     * @param display The new value of the display property.
     */
    public void setDisplay(boolean display){
        this.display = display;
    }

    /**
     * Get the teleportation target of the transporter.
     *
     * @return The teleportation target of the transporter.
     */
    public TeleportationTarget getTeleportationTarget() {
        return this.teleportationTarget;
    }

    /**
     * Getter for the coordinate of the block.
     *
     * @return The coordinate of the block.
     */
    public Coordinate getCoordinate(){
        return this.coordinate;
    }

    /**
     * Said if the block is solid.
     *
     * @return True if the block is solid, false otherwise.
     */
    public boolean isSolid() {
        return this.isSolid;
    }

    /**
     * Said if the block needs to be displayed.
     *
     * @return True if the block need to be displayed, false otherwise.
     */
    public boolean isDisplay(){
        return this.display;
    }

    /**
     * Call when a property change is fired.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Define in subclasses
    }

    /**
     * Override the toString method.
     *
     * @return A string representation of the object.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return "Block " + id + "{" +
                "type=" + type + ", " +
                "coordinate=" + coordinate + ", " +
                ", display=" + display +
                ", isSolid=" + isSolid +
                ", activators=" + activators +
                '}';
    }

    @Override
    public boolean equals(Object e){
        if (!(e instanceof Block))
            return false;
        Block b = (Block) e;
        return this.id == b.id && this.coordinate.equals(b.coordinate)
                && this.isOnIt == b.isOnIt && this.display == b.display
                && this.isSolid == b.isSolid && this.activators.equals(b.activators);
    }

    public void previousTexture() {
        Textures oldTextures = new Textures(this.textures);
        this.textures.previousTexture();
        this.propertyChangeSupport.firePropertyChange(
                "Block.texture", oldTextures, this.textures);
    }

    public void nextTexture() {
        Textures oldTextures = new Textures(this.textures);
        this.textures.nextTexture();
        this.propertyChangeSupport.firePropertyChange(
                "Block.texture", oldTextures, this.textures);
    }

    protected void setTextureAsOpen() {
        Textures oldTextures = new Textures(this.textures);
        this.textures.setCurrentTexture(this.textures.textures.getTextureContains("open"));
        this.propertyChangeSupport.firePropertyChange(
                "Block.texture", oldTextures, this.textures);
    }

    protected void setTextureAsClose() {
        Textures oldTextures = new Textures(this.textures);
        this.textures.setCurrentTexture(this.textures.textures.getTextureContains("close"));
        this.propertyChangeSupport.firePropertyChange(
                "Block.texture", oldTextures, this.textures);
    }
}
