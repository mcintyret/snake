package com.mcintyret.snake.core;

public class Bearing {
    private final int x;

    private final int y;

    private final Direction direction;

    public Bearing(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
