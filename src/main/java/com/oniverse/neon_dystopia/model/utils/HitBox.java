package com.oniverse.neon_dystopia.model.utils;

import com.oniverse.neon_dystopia.model.Coordinate;

public class HitBox {
    private double x;
    private double y;
    private double width;
    private double height;

    public HitBox(Coordinate coordinate, double width, double height) {
        this(coordinate.getX(), coordinate.getY(), width, height);
    }

    public HitBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width  = width;
        this.height = height;
    }

    public HitBox(HitBox hitBox) {
        this(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
    }

    public boolean contains(double x, double y) {
        return x >= this.x && x <= this.x + this.width &&
               y >= this.y && y <= this.y + this.height;
    }

    public boolean contains(HitBox hitBox) {
        return this.contains(hitBox.getX(), hitBox.getY()) &&
               this.contains(hitBox.getX() + hitBox.getWidth(), hitBox.getY() + hitBox.getHeight());
    }

    public boolean intersects(HitBox hitBox, HitboxConnectivity connectivity) {
        return (connectivity.hasLeft() && this.intersectsLeft(hitBox)) ||
               (connectivity.hasRight() && this.intersectsRight(hitBox)) ||
               (connectivity.hasTop() && this.intersectsTop(hitBox)) ||
               (connectivity.hasBottom() && this.intersectsBottom(hitBox));
    }

    public boolean intersects(HitBox hitBox) {
        return this.intersects(hitBox, HitboxConnectivity.TOP_BOTTOM_LEFT_RIGHT);
    }

    public boolean intersectsLeft(HitBox hitBox) {
        return hitBox.contains(this.getX(), this.getCenterY());
    }

    public boolean intersectsRight(HitBox hitBox) {
        return hitBox.contains(this.getMaxX(), this.getCenterY());
    }

    public boolean intersectsTop(HitBox hitBox) {
        return hitBox.contains(this.getCenterX(), this.getY());
    }

    public boolean intersectsBottom(HitBox hitBox) {
        return hitBox.contains(this.getCenterX(), this.getMaxY());
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public double getCenterX() {
        return this.x + this.width / 2;
    }

    public double getCenterY() {
        return this.y + this.height / 2;
    }

    public double getMaxX() {
        return this.x + this.width;
    }

    public double getMaxY() {
        return this.y + this.height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.x = coordinate.getX();
        this.y = coordinate.getY();
    }

    public void setCenter(double x, double y) {
        this.x = x - this.width / 2;
        this.y = y - this.height / 2;
    }

    public void setCenter(Coordinate coordinate) {
        this.setCenter(coordinate.getX(), coordinate.getY());
    }

    @Override
    public String toString() {
        return "HitBox(" + this.x + ", " + this.y + ", " + this.width + ", " + this.height + ")";
    }
}
