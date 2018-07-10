package com.aodnap.SnakeGame;

public class SnakeBodyPart extends GameObject {

    private SnakeBodyPart prev;

    public SnakeBodyPart (int x, int y, char pType, Game game, SnakeBodyPart prev) {
        super(x, y, pType, game);
        this.prev = prev;
    }

    public void updatePosition(){
        if (type == 'h') {
            switch (game.getSnakesDirection()) {
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
