package com.aodnap.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

public class Game extends JPanel implements KeyListener, ActionListener {

    private static final int BLOCK_WIDTH = 25;
    private static final int FRAME_DELAY = 100;
    private char[][] grid = new char[29][25];

    private Direction snakesDirection;

    private ImageIcon imgSnakeHead;
    private ImageIcon imgSnakeBody;
    private ImageIcon imgBerry;

    private LinkedList<SnakeBodyPart> snake;
    private GameObject berry;
    private int score;

    private Timer timer;

    public Game () {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(FRAME_DELAY, this);

        imgSnakeHead = new ImageIcon("assets/sprites/head.png");
        imgSnakeBody = new ImageIcon("assets/sprites/body.png");
        imgBerry = new ImageIcon("assets/sprites/berry.png");

        initGame();
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
                int posX = 22+ i * BLOCK_WIDTH;
                int posY = 22+ j * BLOCK_WIDTH;
                switch(grid[i][j]){
                    case 'h': imgSnakeHead.paintIcon(this, g, posX, posY);
                        break;
                    case 'b': imgSnakeBody.paintIcon(this, g, posX, posY);
                        break;
                    case 'B': imgBerry.paintIcon(this, g, posX, posY);
                        break;
                    default:
                        break;
                }
            }
        }
        g.dispose();
    }

    public Direction getSnakesDirection() {
        return snakesDirection;
    }

    public void setSnakesDirection(Direction snakesDirection) {
        this.snakesDirection = snakesDirection;
    }

    private void initGame () {
        //plot the grid and snake
        emptyGrid();
        //initial snake
        snake = new LinkedList<>();
        snake.add(new SnakeBodyPart(16, 12, 'h', this, null));
        snake.add(new SnakeBodyPart(15, 12, 'b', this, snake.getLast()));
        snake.add(new SnakeBodyPart(14, 12, 'b', this, snake.getLast()));

        //set snake starter state
        snakesDirection = Direction.EAST;
        score = 0;

        plotSnake();

        //place initial berry in empty space
        placeBerry();

        //plot berry on map
        plotBerry();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

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

    private void plotSnake() {
        ListIterator<SnakeBodyPart> it = snake.listIterator(snake.size());
        while (it.hasPrevious()) {
            SnakeBodyPart sbp = (SnakeBodyPart) it.previous();
            grid[sbp.getxPos()][sbp.getyPos()] = sbp.getType();
        }
    }

    private void emptyGrid() {
        for (int i = 0; i < 29; i++) {
            for (int j = 0; j < 25; j++) {
                grid[i][j] = 'e';
            }
        }
    }

    private void plotGrid() {
        emptyGrid();

        //plot snake
        if (snake != null) {
            plotSnake();
        }

        //plot berry
        if(berry != null){
            plotBerry();
        }
    }

    private void placeBerry() {
        ArrayList<GameObject> availableSquare = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            for (int j = 0; j < 25; j++) {
                if (grid[i][j] == 'e') {
                    availableSquare.add(new GameObject(i, j, 'e', this));
                }
            }
        }

        Collections.shuffle(availableSquare);
        berry = new GameObject(availableSquare.get(0).getxPos(), availableSquare.get(0).getyPos(), 'B', this);
    }

    private void plotBerry() {
        grid[berry.getxPos()][berry.getyPos()] = 'B';
        System.out.println("XPOS: "+berry.getxPos() + " YPOS: "+berry.getyPos());
    }
}
