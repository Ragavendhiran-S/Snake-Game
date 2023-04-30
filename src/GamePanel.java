import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.awt.event.*;
public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = SCREEN_HEIGHT*SCREEN_WIDTH/UNIT_SIZE;
    static final int DELAY = 90;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX,appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    Audio audio;
    GamePanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        random = new Random();
        audio = new Audio();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame(audio);
    }
    public void startGame(Audio audio) {
        audio.start();
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(running) {
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }
            g.setColor(Color.blue);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.white);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.cyan);
            g.setFont(new Font("Ink Free", Font.PLAIN,39));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score ->"+applesEaten,(SCREEN_WIDTH-fontMetrics.stringWidth("Score ->"+applesEaten))/2,g.getFont().getSize());
         }
        else {
            gameOver(g);
        }
    }
    public void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }
    public void move() {
        for(int i=bodyParts; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'P':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'C':
                x[0] = x[0] + UNIT_SIZE;
                break;

        }
    }
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions() {
        //check's the head hits the body
        for(int i=bodyParts; i>0 && running; i--) {
            if((x[0]==x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //check's the head touches left
        if(x[0] < 0) {
            running = false;
        }
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        if(y[0] < 0) {
            running = false;
        }
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        //Score printing
        g.setColor(Color.cyan);
        g.setFont(new Font("Ink Free", Font.PLAIN,39));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Total Score = "+applesEaten,(SCREEN_WIDTH-fontMetrics.stringWidth("Total Score "+applesEaten))/2,g.getFont().getSize());
        //Game Over Design
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.PLAIN,75));
        FontMetrics fontMetrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over !!",(SCREEN_WIDTH-fontMetrics1.stringWidth("Game Over !!"))/2,SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void  keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    direction = 'P';
                    break;
                case KeyEvent.VK_C:
                    direction = 'C';
                    break;
            }
        }
    }
}
