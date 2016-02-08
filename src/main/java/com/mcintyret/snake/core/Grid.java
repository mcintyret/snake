package com.mcintyret.snake.core;

public class Grid {

    private static final int DEFAULT_INITIAL_LENGTH = 200;

    private final Snake snake;

    private final int width;

    private final int height;

    private boolean alive = true;

    public Grid(int width, int height, int snakeWidth) {
        this.width = calculateClosest(width, snakeWidth);
        this.height = calculateClosest(height, snakeWidth);
        this.snake = new Snake(new Point(this.width / 2, this.height / 2), Direction.LEFT, DEFAULT_INITIAL_LENGTH, snakeWidth);
    }

    private int calculateClosest(int val, int snakeWidth) {
        int rem = val % snakeWidth;
        if (rem < snakeWidth / 2) {
            return val - rem;
        } else {
            return val + (snakeWidth - rem);
        }
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
        } else if (headY >= height && direction == Direction.DOWN) {
            alive = false;
        } else if (headY <= 0 && direction == Direction.UP) {
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
