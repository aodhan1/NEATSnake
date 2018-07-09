package com.aodnap.SnakeGame;

import com.aodnap.SnakeGame.SnakeQueue.SnakeQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, ActionListener {

    private static final int BLOCK_WIDTH = 25;
    private static final int FRAME_DELAY = 100;
    private char[][] grid = new char[29][25];
    private Direction snakesDirection;

    private ImageIcon snakeHead;
    private ImageIcon snakeBody;
    private ImageIcon berry;

    private SnakeQueue snake;
    private int score;
    private int snakeLength;

    private Timer timer;

    public Game () {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(FRAME_DELAY, this);

        snakeHead = new ImageIcon("assets/sprites/head.png");
        snakeBody = new ImageIcon("assets/sprites/body.png");
        berry = new ImageIcon("assets/sprites/berry.png");

        initGrid();
        timer.start();
    }

    public void paint(Graphics g) {
        //game border
        g.setColor(Color.WHITE);
        g.drawRect(20, 20, 727, 627);

        //game panel
        g.setColor(Color.YELLOW);
        g.fillRect(22, 22, 725, 625);

        //draw grid
        for (int i = 0; i < 29; i++) {
            for (int j =0; j < 25; j++) {
                int posX = i * BLOCK_WIDTH;
                int posY = j * BLOCK_WIDTH;
                switch(grid[i][j]){
                    case 'h': snakeHead.paintIcon(this, g, posX, posY);
                        break;
                    case 'b': snakeBody.paintIcon(this, g, posX, posY);
                        break;
                    case 'B': berry.paintIcon(this, g, posX, posY);
                        break;
                    default:
                        break;
                }
            }
        }
        g.dispose();
    }

    private void initGrid () {
        //empty Grid
        for (int i = 0; i < 29; i++) {
            for (int j = 0; i < 25; i++) {
                grid[i][j] = 'e';
            }
        }
        //snake
        snake = new SnakeQueue();
        grid[16][12] = 'h';
        grid[15][12] = 'b';
        grid[14][12] = 'b';
        //set snake starter state
        snakesDirection = Direction.EAST;
        score = 0;
        snakeLength = 3;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && snakesDirection != Direction.WEST){
            snakesDirection = Direction.EAST;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && snakesDirection != Direction.EAST){
            snakesDirection = Direction.WEST;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && snakesDirection != Direction.NORTH){
            snakesDirection = Direction.SOUTH;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP && snakesDirection != Direction.SOUTH){
            snakesDirection = Direction.NORTH;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
