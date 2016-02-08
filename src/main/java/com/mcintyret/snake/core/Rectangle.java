package com.mcintyret.snake.core;

public class Rectangle {

    private int x;

    private int y;

    private int width;

    private int height;

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


    public void contract(int size) {
        if (size < 0) {
            throw new IllegalArgumentException();
        }

        if (size == 0) {
            return;
        }

        switch (direction) {
            case UP:
                height -= size;
                break;
            case DOWN:
                height -= size;
                y += size;
                break;
            case LEFT:
                width -= size;
                break;
            case RIGHT:
                x += size;
                width -= size;
                break;
            default:
                throw new AssertionError();
        }
    }

    public void extend(int size) {
        if (size == 0) {
            return;
        }

        switch (direction) {
            case UP:
                y -= size;
                height += size;
                break;
            case DOWN:
                height += size;
                break;
            case LEFT:
                x -= size;
                width += size;
                break;
            case RIGHT:
                width += size;
                break;
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

    public boolean overlaps(Rectangle other) {
        if (this.height == 0 || this.width == 0) {
            return false;
        }

        if (other.height == 0 || other.width == 0) {
            return false;
        }

        Rectangle left = x < other.x ? this : other;
        Rectangle right = left == this ? other : this;

        if (left.x + left.width <= right.x) {
            return false;
        }

        Rectangle lower = this.y < other.y ? this : other;
        Rectangle upper = lower == this ? other : this;

        if (lower.y + lower.height <= upper.y) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Rectangle(" + x + ", " + y + ", " + width + ", " + height + ", " + direction + ")";
    }
}
