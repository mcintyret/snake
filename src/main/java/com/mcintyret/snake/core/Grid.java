package com.mcintyret.snake.core;

import java.util.Random;

public class Grid {

    private static final int DEFAULT_INITIAL_LENGTH = 200;

    private static final Random RANDOM = new Random();

    private final Snake snake;

    private final int snakeWidth;

    private final int width;

    private final int height;

    private boolean alive = true;

    private Rectangle food;

    public Grid(int width, int height, int snakeWidth) {
        this.snakeWidth = snakeWidth;
        this.width = calculateClosest(width);
        this.height = calculateClosest(height);
        this.snake = new Snake(new Point(this.width / 2, this.height / 2), Direction.LEFT, DEFAULT_INITIAL_LENGTH, snakeWidth);
        updateFood();
    }

    private Point randomPoint() {
        return new Point(
            calculateClosest(RANDOM.nextInt(this.width - snakeWidth)),
            calculateClosest(RANDOM.nextInt(this.height - snakeWidth))
        );
    }

    private void updateFood() {
        Rectangle food;
        while (true) {
            Point foodPoint = randomPoint();
            food = new Rectangle(
                foodPoint.getX(),
                foodPoint.getY(),
                snakeWidth,
                snakeWidth,
                null
            );

            boolean overlapsSnake = false;
            for (Rectangle part : snake.getParts()) {
                if (part.overlaps(food)) {
                    overlapsSnake = true;
                    break;
                }
            }

            if (!overlapsSnake) {
                this.food = food;
                return;
            }
        }
    }

    private int calculateClosest(int val) {
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

        if (alive) {
            if (snake.getHead().overlaps(food)) {
                snake.setSpeedInPixelsPerSecond(snake.getSpeedInPixelsPerSecond() + 100);
                // TODO get longer
                // TODO: update score
                updateFood();
            }
        }
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

    public Rectangle getFood() {
        return food;
    }

    public void setFood(Rectangle food) {
        this.food = food;
    }
}
