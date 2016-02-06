package com.mcintyret.snake.core;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Snake {

    private final LinkedList<Rectangle> parts = new LinkedList<>();

    private int speedInPixelsPerSecond = 100;

    private final int width;

    private int length;

    public Snake(Bearing initialHeadPos, int initialLength, int snakeWidth) {
        this.length = initialLength;
        this.width = snakeWidth;

        Direction initialDirection = initialHeadPos.getDirection();
        boolean vertical = initialDirection.isVertical();

        parts.add(new Rectangle(
            initialHeadPos.getX(),
            initialHeadPos.getY(),
            vertical ? width : initialLength,
            vertical ? initialLength : width,
            initialDirection
        ));
    }

    private Direction pendingDirection = null;
    void update(long millisSinceLastUpdate, Direction newDirection) {
        final int pixelsMoved = (int) (speedInPixelsPerSecond * millisSinceLastUpdate / 1000D);

        if (pixelsMoved == 0) {
            return;
        }

        if (pendingDirection != null) {
            newDirection = pendingDirection;
        } else {
            newDirection = validateNewDirection(newDirection);
        }

        Rectangle prevHead = getHead();
        if (newDirection != null) {
            int distanceToPixelBoundary = getDistanceToPixelBoundary(prevHead);

            if (distanceToPixelBoundary > pixelsMoved) {
                pendingDirection = newDirection;
                prevHead.extend(pixelsMoved);
            } else {
                prevHead.extend(distanceToPixelBoundary);
                parts.addLast(createNextRectangle(newDirection, prevHead, pixelsMoved - distanceToPixelBoundary));
                pendingDirection = null;
            }

        } else {
            prevHead.extend(pixelsMoved);
        }

        ListIterator<Rectangle> it = parts.listIterator();
        int pixelsToRemove = pixelsMoved;
        while (pixelsToRemove > 0 && it.hasNext()) {
            Rectangle part = it.next();
            int partLength = getSnakeLength(part);

            if (partLength <= pixelsToRemove) {
                it.remove();
            } else {
                // update this rectangle
                part.contract(pixelsToRemove);
            }
            pixelsToRemove -= pixelsToRemove;
        }
    }

    private int getDistanceToPixelBoundary(Rectangle prevHead) {
        switch (prevHead.getDirection()) {
            case UP:
                return prevHead.getY() % width;
            case DOWN:
                return width - ((prevHead.getY() + prevHead.getHeight()) % width);
            case LEFT:
                return prevHead.getX() % width;
            case RIGHT:
                return width - ((prevHead.getX() + prevHead.getWidth()) % width);
            default:
                throw new AssertionError();

        }
    }

    private Rectangle createNextRectangle(Direction newDirection, Rectangle prevHead, int initialSize) {
        Direction prevDirection = prevHead.getDirection();
        if (prevDirection.isVertical()) {
            int newY = prevDirection == Direction.UP ? prevHead.getY() : prevHead.getY() + prevHead.getHeight() - width;
            if (newDirection == Direction.LEFT) {
                return new Rectangle(prevHead.getX() - initialSize, newY, initialSize, width, newDirection);
            } else {
                return new Rectangle(prevHead.getX() + width, newY, initialSize, width, newDirection);
            }
        } else {
            int newX = prevDirection == Direction.LEFT ? prevHead.getX() : prevHead.getX() + prevHead.getWidth() - width;
            if (newDirection == Direction.UP) {
                return new Rectangle(newX, prevHead.getY() - initialSize, width, initialSize, newDirection);
            } else {
                return new Rectangle(newX, prevHead.getY() + width, width, initialSize, newDirection);
            }
        }
    }


    private static int getSnakeLength(Rectangle rectangle) {
        return rectangle.getDirection().isVertical() ? rectangle.getHeight() : rectangle.getWidth();
    }

    private Direction validateNewDirection(Direction newDirection) {
        if (newDirection == null) {
            return null;
        }

        Direction currentDirection = getCurrentDirection();
        return currentDirection == newDirection.opposite() || currentDirection == newDirection ? null : newDirection;
    }

    public List<Rectangle> getParts() {
        return parts;
    }

    public int getWidth() {
        return width;
    }

    public Direction getCurrentDirection() {
        return getHead().getDirection();
    }

    public int getHeadX() {
        return getHead().getGrowingX();
    }

    public int getHeadY() {
        return getHead().getGrowingY();
    }

    private Rectangle getHead() {
        return parts.getLast();
    }

    boolean isEatingSelf() {
        if (parts.size() == 1) {
            return false;
        }
        ListIterator<Rectangle> it = parts.listIterator(parts.size());
        Rectangle head = it.previous();

        while (it.hasPrevious()) {
            if (head.overlaps(it.previous())) {
                return true;
            }
        }
        return false;
    }
}
