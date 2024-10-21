import java.awt.*;
import javax.swing.ImageIcon;
class Bullets {
    int x, y;
    int speed = 30;
    int width = 30;
    int height = 30;
    Image img;

    Bullets(int x, int y) {
        this.x = x;
        this.y = y;
         img = new ImageIcon("images\\bullets.png").getImage();
    }

    public void move() {
        y -= speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        // g.fillOval(x, y, width, height);
        g.drawImage(img, x, y, width,height,null);
    }
}
