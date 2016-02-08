package com.mcintyret.snake.gui;

import com.mcintyret.snake.core.Direction;
import com.mcintyret.snake.core.Grid;
import com.mcintyret.snake.core.Rectangle;
import com.mcintyret.snake.core.Snake;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicReference;

public class GridPanel extends JPanel {

    private final Grid grid;

    private static final Color SNAKE_COLOR = Color.BLACK;

    public GridPanel(Grid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(grid.getWidth(), grid.getHeight()));

        registerKeystroke("left", KeyEvent.VK_LEFT, Direction.LEFT);
        registerKeystroke("right", KeyEvent.VK_RIGHT, Direction.RIGHT);
        registerKeystroke("up", KeyEvent.VK_UP, Direction.UP);
        registerKeystroke("down", KeyEvent.VK_DOWN, Direction.DOWN);
    }

    private final AtomicReference<Direction> nextDirection = new AtomicReference<>();

    private void registerKeystroke(String name, int keyEvent, Direction moveDirection) {
        getInputMap().put(KeyStroke.getKeyStroke(keyEvent, 0), name);
        getActionMap().put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextDirection.compareAndSet(null, moveDirection);
            }
        });
    }


    @Override
    public void paint(Graphics g) {
        // white background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(SNAKE_COLOR);

        Snake snake = grid.getSnake();

        for (Rectangle rectangle : snake.getParts()) {
            drawRectangle(g, rectangle);
        }

        drawRectangle(g, grid.getFood());
    }

    private void drawRectangle(Graphics g, Rectangle rectangle) {
        g.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    private long lastUpdate = System.currentTimeMillis();

    void updateEverything() {
        long now = System.currentTimeMillis();

        grid.update(now - lastUpdate, nextDirection.getAndSet(null));
        lastUpdate = now;

        revalidate();
        repaint();
    }

}
