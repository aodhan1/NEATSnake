package com.aodnap.SnakeGame.SnakeQueue;

import com.aodnap.SnakeGame.Direction;

public class SnakeBodyPart {

    private SnakeQueue snakeQueue;
    private SnakeBodyPart prev;
    private SnakeBodyPart next;

    private int xPos;
    private int yPos;
    private char type;

    public SnakeBodyPart (SnakeBodyPart prev, SnakeQueue sq) {
        this.prev = prev;
        this.xPos = prev.getxPos();
        this.yPos = prev.getyPos();
        this.type = 'b';
        this.snakeQueue = sq;
    }

    public SnakeBodyPart (int x, int y, SnakeBodyPart sbp, char pType, SnakeQueue sq) {
        this.xPos = x;
        this.yPos = y;
        this.type = pType;
        this.prev = sbp;
        this.snakeQueue = sq;
    }

    public SnakeBodyPart getPrev() {
        return prev;
    }

    public void setPrev(SnakeBodyPart prev) {
        this.prev = prev;
    }

    public SnakeBodyPart getNext() {
        return next;
    }

    public void setNext(SnakeBodyPart next) {
        this.next = next;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void updatePosition(){
        if (type == 'h') {
            switch (snakeQueue.getDirection()) {
                case NORTH:
                        if (yPos == 0) {
                            yPos = 24;
                        } else {
                            yPos -= 1;
                        }
                    break;
                case EAST:
                    if (xPos == 28) {
                        xPos = 0;
                    } else {
                        xPos += 1;
                    }
                    break;
                case SOUTH:
                    if (yPos == 24) {
                        yPos = 0;
                    } else {
                        yPos += 1;
                    }
                    break;
                case WEST:
                    if (xPos == 0) {
                        xPos = 28;
                    } else {
                        xPos -= 1;
                    }
                    break;
            }
        } else {
            this.xPos = this.prev.getxPos();
            this.yPos = this.prev.getyPos();
        }
    }
}
