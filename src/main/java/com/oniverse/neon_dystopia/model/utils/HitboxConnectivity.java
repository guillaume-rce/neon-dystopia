package com.oniverse.neon_dystopia.model.utils;

public enum HitboxConnectivity {
    NONE(0, 0, 0, 0),
    TOP(0, 1, 0, 0),
    BOTTOM(0, 0, 0, 1),
    LEFT(1, 0, 0, 0),
    RIGHT(0, 0, 1, 0),
    TOP_LEFT(1, 1, 0, 0),
    TOP_RIGHT(0, 1, 1, 0),
    BOTTOM_LEFT(1, 0, 0, 1),
    BOTTOM_RIGHT(0, 0, 1, 1),
    TOP_BOTTOM(0, 1, 0, 1),
    LEFT_RIGHT(1, 0, 1, 0),
    TOP_LEFT_RIGHT(1, 1, 1, 0),
    BOTTOM_LEFT_RIGHT(1, 0, 1, 1),
    TOP_BOTTOM_LEFT(1, 1, 0, 1),
    TOP_BOTTOM_RIGHT(0, 1, 1, 1),
    LEFT_RIGHT_TOP(1, 1, 1, 0),
    LEFT_RIGHT_BOTTOM(1, 0, 1, 1),
    TOP_BOTTOM_LEFT_RIGHT(1, 1, 1, 1);

    private final int left;
    private final int right;
    private final int top;
    private final int bottom;

    HitboxConnectivity(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public int getLeft() {
        return this.left;
    }

    public boolean hasLeft() {
        return this.left == 1;
    }

    public int getRight() {
        return this.right;
    }

    public boolean hasRight() {
        return this.right == 1;
    }

    public int getTop() {
        return this.top;
    }

    public boolean hasTop() {
        return this.top == 1;
    }

    public int getBottom() {
        return this.bottom;
    }

    public boolean hasBottom() {
        return this.bottom == 1;
    }

    public int[] getConnectivity() {
        return new int[]{this.left, this.right, this.top, this.bottom};
    }
}
