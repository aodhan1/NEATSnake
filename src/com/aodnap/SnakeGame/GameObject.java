package com.aodnap.SnakeGame;

public class GameObject {

    protected Game game;
    protected int xPos;
    protected int yPos;
    protected char type;

    public GameObject (int x, int y, char pType, Game game) {
        this.xPos = x;
        this.yPos = y;
        this.type = pType;
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
