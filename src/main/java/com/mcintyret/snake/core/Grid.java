package com.mcintyret.snake.core;

public class Grid {

    private static final int DEFAULT_INITIAL_LENGTH = 200;

    private final Snake snake;

    private final int width;

    private final int height;

    private boolean alive = true;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.snake = new Snake(new Bearing(width / 2, height / 2, Direction.LEFT), DEFAULT_INITIAL_LENGTH);
    }

    public void update(long millisSinceLastUpdate, Direction newDirection) {
        if (!alive) {
            throw new IllegalStateException();
        }
        snake.update(millisSinceLastUpdate, newDirection);

        checkAlive();
    }

    private void checkAlive() {
        Direction direction = snake.getCurrentDirection();
        int headX = snake.getHeadX();
        int headY = snake.getHeadY();
        if (headX <= 0 && direction == Direction.LEFT) {
            alive = false;
        } else if (headX >= width && direction == Direction.RIGHT) {
            alive = false;
        } else if (headY <= 0 && direction == Direction.DOWN) {
            alive = false;
        } else if (headY >= height && direction == Direction.UP) {
            alive = false;
        } else {
            alive = !snake.isEatingSelf();
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getHeight() {
        return height;
    }

    public Snake getSnake() {
        return snake;
    }

    public int getWidth() {
        return width;
    }
}
