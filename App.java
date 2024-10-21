import javax.swing.*;

class ShootingGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Shooting Game");
        Paddle paddle = new Paddle();
        frame.add(paddle);
        frame.setSize(900, 600);
        frame.setVisible(true);
    }

}