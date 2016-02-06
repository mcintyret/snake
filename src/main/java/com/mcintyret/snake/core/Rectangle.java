package com.mcintyret.snake.core;

public class Rectangle {
    private final int x;

    private final int y;

    private final int width;

    private final int height;

    private final Direction direction;

    public Rectangle(int x, int y, int width, int height, Direction direction) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle contract(int size) {
        if (size < 0) {
            throw new IllegalArgumentException();
        }

        if (size == 0) {
            return this;
        }

        switch (direction) {
            case UP:
                return new Rectangle(x, y, width, height - size, direction);
            case DOWN:
                return new Rectangle(x, y + size, width, height - size, direction);
            case LEFT:
                return new Rectangle(x, y, width - size, height, direction);
            case RIGHT:
                return new Rectangle(x + size, y, width - size, height, direction);
            default:
                throw new AssertionError();
        }
    }

    public Rectangle extend(int size) {
        if (size < 0) {
            throw new IllegalArgumentException();
        }

        if (size == 0) {
            return this;
        }

        switch (direction) {
            case UP:
                return new Rectangle(x, y - size, width, height + size, direction);
            case DOWN:
                return new Rectangle(x, y, width, height + size, direction);
            case LEFT:
                return new Rectangle(x - size, y, width + size, height, direction);
            case RIGHT:
                return new Rectangle(x, y, width + size, height, direction);
            default:
                throw new AssertionError();
        }
    }

    public int getGrowingX() {
        switch (direction) {
            case UP:
            case DOWN:
            case LEFT:
                return x;
            case RIGHT:
                return x + width;
            default:
                throw new AssertionError();
        }
    }

    public int getGrowingY() {
        switch (direction) {
            case UP:
            case LEFT:
            case RIGHT:
                return y;
            case DOWN:
                return y + height;
            default:
                throw new AssertionError();
        }
    }

    public boolean overlaps(Rectangle b) {
        Rectangle left = x < b.x ? this : b;
        Rectangle right = left == this ? b : this;

        if (left.x + left.width <= right.x) {
            return false;
        }

        Rectangle lower = this.y < b.y ? this : b;
        Rectangle upper = lower == this ? b : this;

        if (lower.y + lower.height <= upper.y) {
            return false;
        }

        return true;
    }
}
