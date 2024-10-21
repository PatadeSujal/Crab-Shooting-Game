import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;
class FallingObjects {
    int x;
    int y;
    int height = 70;
    int width = 70;
    Image crab1;
    Image crab2;
    Image crab3;
    Image crab4;
    Image crab5;
    Random random = new Random();
        int randomSize;
    FallingObjects(int x, int y) {
        this.x = x;
        this.y = y;
        randomSize = random.nextInt(25);
        crab1 = new ImageIcon("images\\crab1.png").getImage();
        crab2 = new ImageIcon("images\\crab2.png").getImage();
        crab3 = new ImageIcon("images\\crab3.png").getImage();
        crab4 = new ImageIcon("images\\crab4.png").getImage();
        crab5 = new ImageIcon("images\\crab5.png").getImage();
    }

    public void moveObj(int speed) {
        y += speed;
    }

    public void drawCrab1(Graphics g) {
        g.drawImage(crab1, x, y, (width + randomSize), (height + randomSize), null);
    }   
    public void drawCrab2(Graphics g) {
        g.drawImage(crab2, x, y, (width + randomSize), (height + randomSize), null);
    }
    public void drawCrab3(Graphics g) {
        g.drawImage(crab3, x, y, (width + randomSize), (height + randomSize), null);
    }
    public void drawCrab4(Graphics g) {
        g.drawImage(crab4, x, y, (width + randomSize), (height + randomSize), null);
    }
    public void drawCrab5(Graphics g) {
        g.drawImage(crab5, x, y, (width + randomSize), (height + randomSize), null);
    }
}