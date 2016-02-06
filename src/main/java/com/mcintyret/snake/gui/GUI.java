package com.mcintyret.snake.gui;

import com.mcintyret.snake.core.Grid;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.EventQueue;

public class GUI {
    static final JFrame FRAME = new JFrame("Snake");

    private static final int FRAMES_PER_SECOND = 25;

    private static final int DEFAULT_SNAKE_WIDTH = 10;

    private static final long MILLIS_PER_FRAME = 1000 / FRAMES_PER_SECOND;

    public static void main(String[] args) throws InterruptedException {

        FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Grid grid = new Grid(1000, 700, DEFAULT_SNAKE_WIDTH);

        GridPanel gridPanel = new GridPanel(grid);
        FRAME.getContentPane().add(gridPanel);

        EventQueue.invokeLater(() -> {
            FRAME.pack();
            FRAME.setSize(gridPanel.getSize());
            FRAME.setLocationRelativeTo(null);
            FRAME.setVisible(true);
        });

        while (grid.isAlive()) {
            gridPanel.updateEverything();
            Thread.sleep(MILLIS_PER_FRAME);
        }

        JOptionPane.showMessageDialog(GUI.FRAME.getContentPane(), "Oops", "You Died!", JOptionPane.ERROR_MESSAGE);

    }

}
