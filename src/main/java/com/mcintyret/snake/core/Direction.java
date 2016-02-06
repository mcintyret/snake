package com.mcintyret.snake.core;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public Direction opposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new AssertionError();
        }
    }

    public boolean isVertical() {
        return this == UP || this == DOWN;
    }
}
