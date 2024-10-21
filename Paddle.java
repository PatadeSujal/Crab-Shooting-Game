import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class Paddle extends JPanel implements ActionListener {
    int height = 20;
    int width = 120;
    int y = 400;
    int x = 900 / 2;
    Timer timer;
    boolean firing = false;
    boolean movingRight = false;
    boolean movingLeft = false;
    int objectColdDown = 0;
    int noOfBullets = 100;
    int objMissed = 0;
    int score = 0;
    int speed = 10;
    int countDown = 3;
    Timer countDownTimer;
    boolean gameStarted = false;
    JLabel countDownLable = new JLabel("3");
    ImageIcon expAnim = new ImageIcon("images\\Explosion.gif");
    ImageIcon hsAnimation = new ImageIcon("images\\hsAnimation.gif");
    JLabel gifLabel = new JLabel(expAnim);
    JLabel gifHS = new JLabel(hsAnimation);
    int lifeLines = 3;
    ScoreDB db = new ScoreDB();
    GetHIghScore hs = new GetHIghScore();
    boolean scoreSaved = false;
    Timer startGame;
    int highScore = hs.getHighScore();
    boolean showBanner = false;
    ArrayList<Bullets> bullets = new ArrayList<>();
    ArrayList<FallingObjects> fallingObjs = new ArrayList<>();
    int randomCrab;

    Paddle() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
        setDoubleBuffered(true); 
        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    movingRight = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    movingLeft = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    firing = true;
                }
                repaint();
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    movingRight = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    movingLeft = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    firing = false;
                }
                repaint();
            }
        });
        startCountDown();

    }

    public void startCountDown() {
        countDownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (countDown > 0) {
                    countDown--;
                    repaint();

                } else {
                    ((Timer) e.getSource()).stop();
                    gameStarted = true;
                    timer = new Timer(30, Paddle.this);
                    timer.start();
                }
            }
        });

        countDownTimer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (firing) {
            fireBullets();
            collapse();
        }
        if (movingRight) {
            x += 20;
        }
        if (movingLeft) {
            x -= 20;
        }

        if (objectColdDown == 0) {

            fallingObjects();
            if (score > 20 && score < 30) {
                speed = 15;
                System.out.println(speed);
            }
            if (score > 30 && score < 50) {
                speed = 20;
                System.out.println(speed);
            }
            if (score > 50) {
                speed = 25;
                System.out.println(speed);
            } else {
                objectColdDown = 50;
            }
        } else {
            objectColdDown--;
        }
        updateObjs();
        updateBullets();
        repaint();
    }

    public void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullets bullet = bullets.get(i);
            bullet.move();
            if (bullet.y < 0) {
                bullets.remove(i);
                i--;
            }
        }
    }

    public void updateObjs() {

        for (int i = 0; i < fallingObjs.size(); i++) {

            FallingObjects fos = fallingObjs.get(i);
            fos.moveObj(speed);
            if (fos.y > y) {
                if (fos.y > 500) {
                    System.out.println("Object Missed");
                    fallingObjs.remove(i);
                    lifeLines--;
                    objMissed++;
                    noOfBullets -= 10;
                }
                break;
            }
        }

    }

    public void fallingObjects() {

        Random rand = new Random();
        randomCrab = rand.nextInt(0, 5);
        System.out.println("Random Number is " + randomCrab);
        int objX = rand.nextInt(900 - 70);
        int objY = 0;
        fallingObjs.add(new FallingObjects(objX, objY));

    }

    public void fireBullets() {
        bullets.add(new Bullets(x - width / 2 + 60, y - height));
        noOfBullets--;
    }

    public void collapse() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullets bullet = bullets.get(i);
            for (int j = 0; j < fallingObjs.size(); j++) {
                FallingObjects fo = fallingObjs.get(j);
                if (bullet.x < fo.x + fo.width &&
                        bullet.x + bullet.width > fo.x &&
                        bullet.y < fo.y + fo.height &&
                        bullet.y + bullet.height > fo.y) {
                    add(gifLabel);
                    gifLabel.setBounds(fo.x, fo.y, 50, 50);
                    bullets.remove(i);
                    noOfBullets += 10;
                    score++;
                    if(score == (highScore + 1)){
                        highScoreGif();
                    }
                    fallingObjs.remove(j);
                    i--;

                    break;
                }
            }
        }
    }
    public void highScoreBanner(Graphics g){
        Image highScoreImg = new ImageIcon("images\\HighScoreImg.png").getImage();
        g.drawImage(highScoreImg,40,100,800,250,null);
        System.out.println("Banner Displaying");
    }
    public void highScoreGif() {
        add(gifHS);
        gifHS.setBounds((x / 2), 10, 400, 500);
        showBanner = true;
        
        Timer gifTimer = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(gifHS);
                repaint();
                showBanner = false;
                ((Timer) e.getSource()).stop(); 
            }
        });
        
        gifTimer.setRepeats(false);  // Only run the timer once
        gifTimer.start();
    }

    public boolean gameOver() {
        if (noOfBullets <= 0 || lifeLines == 0) {
            return true;
        }
        return false;
    }

    public void endGame(Graphics g) {

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.RED);
        g.drawString("Game Over", 346, 300);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("NO of Bullets : " + noOfBullets, 20, 30);
        g.drawString("Score : " + score, 20, 60);
        g.drawString("High Score : " + highScore, 20, 100);
        g.setColor(Color.BLUE);
        g.fillRect(x - width / 2, y, width, height);

        Image img = new ImageIcon("images\\lifeline.png").getImage();
        int x = 15;

        for (int i = 0; i < lifeLines; i++) {
            x = x + 15 + 5;
            g.drawImage(img, x, 65, 15, 15, null);
        }

        for (FallingObjects fo : fallingObjs) {
            if (randomCrab == 0) {
                fo.drawCrab1(g);
            } else if (randomCrab == 1) {
                fo.drawCrab2(g);
            } else if (randomCrab == 2) {
                fo.drawCrab3(g);
            } else if (randomCrab == 3) {
                fo.drawCrab4(g);
            } else if (randomCrab == 4) {
                fo.drawCrab5(g);

            }
        }
        for (Bullets bullet : bullets) {
            bullet.draw(g);
        } 
        if (gameStarted == false) {
            String text;
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 150));
            if (countDown > 0) {
                text = " " + Integer.toString(countDown);
            } else {
                text = "GO";
            }
            g.drawString(text, 365, 300);
        }
        if(showBanner){
            highScoreBanner(g);
        }
        if (gameOver() && gameStarted) {
            endGame(g);
            if (!scoreSaved) {
                if (highScore < score) {
                    db.insertScore(score);  // Insert score only once
                    scoreSaved = true;      // Set flag to true to prevent spamming
                }
                timer.stop();
            }
        }
    }
}