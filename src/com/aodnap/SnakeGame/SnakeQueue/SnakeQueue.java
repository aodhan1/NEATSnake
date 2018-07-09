package com.aodnap.SnakeGame.SnakeQueue;

import com.aodnap.SnakeGame.Direction;

import java.util.Queue;

public class SnakeQueue {

    private SnakeBodyPart first;
    private SnakeBodyPart last;

    private Direction direction;

    public SnakeQueue() {

    }

    public void add(int xPos, int yPos, char type) {
        SnakeBodyPart newPart = new SnakeBodyPart(xPos, yPos, null, type, this);
        add(newPart);
    }

    private void add(SnakeBodyPart newPart) {
        //head of the snake
        if(first == null && last == null) {
            first = newPart;
            last = newPart;
        } else {    //all subsequent steps
            last.setNext(newPart);
            newPart.setPrev(last);
            last = newPart;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
