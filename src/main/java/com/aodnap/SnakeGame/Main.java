package com.aodnap.SnakeGame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        Game game = new Game();

        jFrame.setBounds(10, 10, 900, 700);
        jFrame.setBackground(Color.GRAY);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(game);
        jFrame.setTitle("Snake");
    }
}
