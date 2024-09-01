package com.oniverse.neon_dystopia.vue.utils;

import com.oniverse.neon_dystopia.model.Coordinate;
import javafx.geometry.BoundingBox;
import javafx.scene.Group;
import javafx.scene.image.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A class to display an image. It can be used to display a texture or a sprite.
 * @see Image
 */
public class ImageVue {
    /**
     * The bounding box of the image.
     * Not very useful because the ImageView has already a bounding box.
     */
    private final BoundingBox boundingBox;
    /**
     * If the image is smooth or not.
     */
    private final boolean smooth;
    /**
     * The ImageView that contains the image.
     */
    private final Group group = new Group();
    /**
     * The tags of the image.
     */
    public final ArrayList<Object> tags;

    private Image image;

    /**
     * The constructor of the class. Smoothing is set to false. Tags are empty.
     * @param imagePath The path to the image.
     * @param coordinate The coordinate of the image.
     * @param width The width of the image.
     * @param height The height of the image.
     */
    public ImageVue(String imagePath, Coordinate coordinate, double width, double height) {
        this(imagePath, coordinate, width, height, false);
    }

    /**
     * The constructor of the class. The tags are set to an empty ArrayList.
     * @param imagePath The path to the image.
     * @param coordinate The coordinate of the image.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param smooth If the image is smooth or not.
     */
    public ImageVue(String imagePath, Coordinate coordinate, double width, double height, boolean smooth) {
        this(imagePath, coordinate, width, height, smooth, new ArrayList<>());
    }

    /**
     * The constructor of the class.
     * @param imagePath The path to the image.
     * @param coordinate The coordinate of the image.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param smooth If the image is smooth or not.
     * @param tags The tags of the image.
     */
    public ImageVue(String imagePath, Coordinate coordinate,
                    double width, double height, boolean smooth, ArrayList<Object> tags) {
        this.tags = tags;
        this.boundingBox = new BoundingBox(coordinate.getX(), coordinate.getY(),
                width, height);
        this.smooth = smooth;
        try {
            this.draw(imagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * The draw method of the image.
     * @throws FileNotFoundException If the image is not found.
     */
    public void draw(String imagePath) throws FileNotFoundException {
        this.group.getChildren().clear();
        ImageView imageView = new ImageView();
        InputStream stream = new FileInputStream(imagePath);
        image = new Image(stream, this.boundingBox.getWidth(), this.boundingBox.getHeight(),
                false, false);
        imageView.setImage(image);
        imageView.setSmooth(this.smooth);
        imageView.setX(this.boundingBox.getMinX());
        imageView.setY(this.boundingBox.getMinY());
        group.getChildren().add(imageView);
    }

    /**
     * The setter of the opacity of the image.
     * @param opacity The opacity of the image.
     */
    public void setOpacity(double opacity) {
        this.group.setOpacity(opacity);
    }

    public boolean isSolid(int x, int y) {
        x = (int) (x - this.boundingBox.getMinX());
        y = (int) (y - this.boundingBox.getMinY());
        if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight())
            return false;
        return image.getPixelReader().getColor(x, y).getOpacity() > 0;
    }

    /**
     * The getter of the ImageView.
     * @return The ImageView of the image.
     */
    public Group getGroup() {
        return group;
    }

    public Image getImage() {
        return image;
    }

    /**
     * The getter of the bounding box.
     * @return The bounding box of the image.
     */
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void reverse() {
        // Reverse image
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int[] pixels = new int[width * height];
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
        for (int i = 0; i < pixels.length / 2; i++) {
            int temp = pixels[i];
            pixels[i] = pixels[pixels.length - i - 1];
            pixels[pixels.length - i - 1] = temp;
        }
        WritableImage writableImage = new WritableImage(width, height);
        writableImage.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
        image = writableImage;
        // Reverse image view
        ImageView imageView = (ImageView) group.getChildren().get(0);
        imageView.setImage(image);
    }

    public void move(double x, double y) {
        this.group.setTranslateX(x - this.boundingBox.getMinX());
        this.group.setTranslateY(y - this.boundingBox.getMinY());
    }
}
