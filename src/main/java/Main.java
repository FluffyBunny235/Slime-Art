import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class Main {
    public static int WIDTH;
    public static int HEIGHT;
    public static int numAgents;
    public static Agent[] agents;
    public static int speed = 1;
    public static Color background = Color.BLACK;
    public static ArrayList<Color> slime = new ArrayList<>(0);
    public static Simulation sim;
    public static Color[][] display;
    public static short[][][] scents;
    public static void main(String[] args) {
        new Settings();
    }

    public static void diffuse() {
        Color[][] newDisplay = new Color[display.length][display[0].length];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double pixelsUsed = 1;
                int r = background.getRed();
                int g = background.getGreen();
                int b = background.getBlue();
                for (int i = -1; i <= 1; i++) {
                    for (int k = -1; k <=1; k++) {
                        if (isInBounds(x+i, y+k)) {
                            pixelsUsed+=2;
                            if (display[x+i][y+k] != null) {
                                r+= 2*display[x+i][y+k].getRed();
                                g+= 2*display[x+i][y+k].getGreen();
                                b+= 2*display[x+i][y+k].getBlue();
                            }
                            else {
                                r+= 2*background.getRed();
                                g+= 2*background.getGreen();
                                b+= 2*background.getBlue();
                            }
                        }
                    }
                }
                r = (int)(r/pixelsUsed);
                g = (int)(g/pixelsUsed);
                b = (int)(b/pixelsUsed);
                newDisplay[x][y] = new Color(r,g,b);
                for (int i = 0; i < slime.size(); i++) {
                    scents[x][y][i] -= 1;
                }
            }
        }
        display = newDisplay;
        sim.s.repaint();
    }
    public static boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT;
    }
}