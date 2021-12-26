package com.space;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener , KeyListener {
    private BufferedImage background;
    private BufferedImage fire;
    private BufferedImage monster;
    private BufferedImage ufo;


    private Timer timer;
    private Random rand;
    private ArrayList<Target> targets;
    private ArrayList<Bullet> bullets;
    private int shotX,shotX0,shotY,shotY0;
    private boolean gameStart;
    private int ballX,ballY,ballSize;
    private int ballCount;
    private int t;
    private int degress;
    private int delayForTarget;
    private int skor;
    private int skorCount;
    private int gameCount;

    public GamePanel(){
      initUI();
      initValue();
      initTimer();

      gameCount = 3;
      addKeyListener(this);

        try {
            background = ImageIO.read(new FileImageInputStream(new File("src/image/background.jpeg")));
            monster = ImageIO.read(new FileImageInputStream(new File("src/image/monster.png")));
            fire = ImageIO.read(new FileImageInputStream(new File("src/image/fire.png")));
            ufo = ImageIO.read(new FileImageInputStream(new File("src/image/ufo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initUI(){
        targets = new ArrayList<Target>();
        bullets = new ArrayList<Bullet>();
        randomTargets();
        gameStart = false;
    }
    public void initValue(){

        shotX0 = 250;
        shotY0 = 350;
        shotX = 250;
        shotY = 300;

        degress = (int) Math.toDegrees(Math.atan2(shotY-shotY0,shotX-shotX0));
        ballCount = 15;
        delayForTarget = 0;

        skor = 150;
        skorCount = 0;
    }

    public void initTimer(){
        t=0;
        timer = new Timer(200,this);
    }
    public void randomTargets(){
        rand = new Random();

        int randomSpeed = this.rand.nextInt(4)+2;
        int randomY = -this.rand.nextInt(50);
        targets.add(new Target(this.rand.nextInt(450)+10,randomY,20,20,randomSpeed));

    }

    public Timer getTimer(){
        return timer;
    }

    public void drawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0,0,0));
        g2d.drawImage(background,0,0,1000,1000,null);

        g2d.setColor(Color.WHITE);
        g2d.drawString("Kalan Mermi: " + ballCount,375,20);
        g2d.drawString("Skor:" + skor  ,375,40);
        g2d.drawString("Kalan Oyun : " + gameCount,375,60);

        g2d.setColor(Color.CYAN);
        g2d.drawImage(ufo,200,350,100,50,null);


        for(Target target : targets){
            g2d.drawImage(monster,target.getX(),target.getY(),target.getWidth(),target.getHeight(),null);
        }


        g2d.setColor(Color.CYAN);
        g2d.drawLine(shotX0,shotY0,shotX,shotY);
        for(Bullet bullet : bullets){
            g2d.drawImage(fire,bullet.getX(), bullet.getY(), bullet.getSize(),bullet.getSize(),null);
        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawing(g);
    }

    public void controlShotArea() {
        for(Bullet bullet: bullets){
            Area bulletArea = new Area(new Rectangle(bullet.getX(),bullet.getY(),bullet.getSize(),bullet.getSize()));

            for (Target target : targets){
                Area bulletCloneArea = (Area) bulletArea.clone();
                Area targetArea = new Area(new Rectangle(target.getX(),target.getY(),target.getWidth(),target.getHeight()));

                bulletCloneArea.intersect(targetArea);

                if(!bulletCloneArea.isEmpty()){
                    target.setWidth(0);
                    target.setHeight(0);
                    target.setSpeed(0);
                    skorCount++;
                    skor = 150 + skorCount * 10;
                }
            }
        }

    }
    public void actionPerformed(ActionEvent e) {

        if(gameStart){

            if(targets.size()<15 && delayForTarget % 23 == 0){
                randomTargets();
            }

            for(Target target : targets){
                target.setY(target.getY() + target.getSpeed());
                if(target.getY()>370){
                    target.setSpeed(-target.getSpeed()*2);
                    target.way = true;
                }
                if(target.way == true && target.getY() < 0 ){
                    target.setSpeed(-target.getSpeed());
                    target.way = false;
                }
            }

            delayForTarget++;

            for(Bullet bullet : bullets){
                bullet.setX((int) (bullet.getX() + (30* Math.cos(Math.toRadians(degress)))));
                bullet.setY(bullet.getY()-30);
            }

            if(bullets.size()>= 15 && bullets.get(bullets.size()-1).getY()<-20) {
                gameStart = false;
                skor -= (15-skorCount) * 10;
            }
            controlShotArea();
        }else{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            gameCount--;

            initUI();
            initValue();
            initTimer();
            timer.stop();
            gameStart = true;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 37){
            degress -=5;
            shotX = 250;
            shotY = 300;
            shotX += (int) ( 100*Math.cos(Math.toRadians(degress)));
            shotY += (int) ( 10*Math.sin(Math.toRadians(degress)));
        }

        if(e.getKeyCode() == 38){
            if(gameCount>0){
                ballX = shotX -10;
                ballY = shotY-20;
                ballSize = 20;

                if(ballCount > 0){
                    bullets.add(new Bullet(ballX,ballY,ballSize,true));
                    ballCount--;
                }
                gameStart = true;
                timer.start();

            }else{
                JOptionPane jb = new JOptionPane();
                jb.showMessageDialog(null," \n GAME OVER!");
            }
        }

        if(e.getKeyCode() == 39){
            degress +=5;
            shotX = 250;
            shotY = 300;
            shotX += (int) ( 100*Math.cos(Math.toRadians(degress)));
            shotY += (int) ( 10*Math.sin(Math.toRadians(degress)));
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
