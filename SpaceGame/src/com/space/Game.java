package com.space;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends JFrame {
    public Game(){
       initUI();
    }
    public void initUI(){
        GamePanel panel = new GamePanel();
        add(panel);
        addWindowFocusListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                Timer timer = panel.getTimer();
                timer.stop();;
            }
        });
        addKeyListener((KeyListener) panel);
        setTitle("Uzay Oyunu");
        setSize(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();
                game.setVisible(true);
            }
        });
    }
}
