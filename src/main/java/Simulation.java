import javax.swing.*;
import java.awt.*;

public class Simulation {
    private final int HEIGHT = Main.HEIGHT;
    private final int WIDTH = Main.WIDTH;
    private final Color background = Main.background;
    JFrame frame;
    Screen s;
    public Simulation() {
        frame = new JFrame("Simulation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        s = new Screen(WIDTH, HEIGHT);
        frame.add(s);
        frame.pack();
        frame.setVisible(true);
    }
    class Screen extends JPanel {
        int WIDTH;
        int HEIGHT;
        public Screen(int w, int h) {
            setBackground(background);
            WIDTH = w;
            HEIGHT = h;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH, HEIGHT);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Loop through the 2D array and draw a 1x1 rectangle
            // on each pixel that is currently painted
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (Main.display[x][y] != null) {
                        g.setColor(Main.display[x][y]);
                        g.drawRect(x, y, 1, 1);
                    }
                }
            }
            for (Agent a : Main.agents) {
                a.move();
            }
            Main.diffuse();
        }
    }
}