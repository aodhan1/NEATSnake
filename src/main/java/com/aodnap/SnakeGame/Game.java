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
    private boolean running;

    public Game () {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(FRAME_DELAY, this);

        imgSnakeHead = new ImageIcon(getClass().getClassLoader().getResource("assets/sprites/head.png"));
        imgSnakeBody = new ImageIcon(getClass().getClassLoader().getResource("assets/sprites/body.png"));
        imgBerry = new ImageIcon(getClass().getClassLoader().getResource("assets/sprites/berry.png"));

        initGame();
        timer.start();
        running = false;
    }

    public void paint(Graphics g) {
        System.out.println("PAINT CALLED");
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
        if(running) {
            tick();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.print("Key Pressed: ");
        running = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && snakesDirection != Direction.WEST){
            snakesDirection = Direction.EAST;
            System.out.println("Right Arrow");
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && snakesDirection != Direction.EAST){
            snakesDirection = Direction.WEST;
            System.out.println("Left Arrow");
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && snakesDirection != Direction.NORTH){
            snakesDirection = Direction.SOUTH;
            System.out.println("Down Arrow");
        }
        if(e.getKeyCode() == KeyEvent.VK_UP && snakesDirection != Direction.SOUTH){
            snakesDirection = Direction.NORTH;
            System.out.println("Up Arrow");
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
        System.out.println("BERRY POSITION XPOS: "+berry.getxPos() + " YPOS: "+berry.getyPos());
    }

    private void eatBerry(SnakeBodyPart newPart) {
        score++;
        placeBerry();
        growSnake(newPart);
    }

    private void growSnake(SnakeBodyPart snakeTail) {
        snake.add(new SnakeBodyPart(snakeTail.getxPos(), snakeTail.getyPos(), 'b', this, snake.getLast()));
    }

    private void start(){
        running = true;
    }

    private void stop() {
        running = false;
    }

    private void tick() {
        //store last position incase snake grows
        SnakeBodyPart snakeTail = snake.getLast();

        //update each position of the snake
        ListIterator<SnakeBodyPart> it = snake.listIterator(snake.size());
        System.out.println("SNAKE LOCATION");
        while (it.hasPrevious()) {
            SnakeBodyPart sbp = (SnakeBodyPart) it.previous();
            sbp.updatePosition();
            System.out.println("\tPART POSITION XPOS: "+sbp.getxPos() + " YPOS: "+sbp.getyPos());
        }
        //check collisions
        //get snake head position
        SnakeBodyPart snakeHead = snake.get(0);
        it = snake.listIterator();
        while (it.hasNext()) {
            SnakeBodyPart sbp = (SnakeBodyPart) it.next();

            if (sbp.getType() == 'b' && sbp.getxPos() == snakeHead.getxPos() && sbp.getyPos() == snakeHead.getyPos()) {
                running = false;
            }
        }

        //check collisions with berry
        if (snakeHead.getxPos() == berry.getxPos() && snakeHead.getyPos() == berry.getyPos()) {
            eatBerry(snakeTail);
        }

        plotGrid();
    }
}
