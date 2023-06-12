import java.awt.*;
import java.util.ArrayList;

public class Agent {
    double direction; //0->2pi
    double x;
    double y;
    int type; //0->numSpecies-1
    public Agent(double x, double y, double direction, int type) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.type = type;
    }
    public void move() {
        nextAngle();
        x = x+Math.cos(direction)*Main.speed;
        y = y+Math.sin(direction)*Main.speed;
        checkBounds();
        Main.display[(int)x][(int)y] = Main.slime.get(type);
        //Main.scents[(int)x][(int)y][type] +=20;
        Main.scents[(int)x][(int)y][type] = 100;
    }
    private void checkBounds() {
        if (x < 0) {
            x = 0;
            direction = Math.random()*2*3.1415;
        }
        else if (x >= Main.WIDTH) {
            x = Main.WIDTH-1;
            direction = Math.random()*2*3.1415;
        }
        if (y < 0) {
            y = 0;
            direction = Math.random()*2*3.1415;
        }
        else if (y >= Main.HEIGHT) {
            y = Main.HEIGHT-1;
            direction = Math.random()*2*3.1415;
        }
    }

    private void nextAngle(){
        ArrayList<Double> viableAngles = new ArrayList<>(3);
        ArrayList<Integer> sums = new ArrayList<Integer>(3);
        if ((int)(x+Math.cos(direction+3.14/8)*3) >= 0 && (int)(x+Math.cos(direction+3.14/8)*3) < Main.WIDTH
        && (int)(y + Math.sin(direction + 3.14 / 8) * 3) > 0 && (int)(y + Math.sin(direction + 3.14 / 8) * 3) < Main.HEIGHT) {
            int s = sum(Main.scents[(int)(x+Math.cos(direction+3.14/8)*3)][(int)(y + Math.sin(direction + 3.14 / 8) * 3)]);
            sums.add(s);
            viableAngles.add(direction+3.14/8);
        }
        if ((int)(x+Math.cos(direction)*3) >= 0 && (int)(x+Math.cos(direction)*3) < Main.WIDTH
        && (int)(y + Math.sin(direction) * 3) > 0 && (int)(y + Math.sin(direction) * 3) < Main.HEIGHT){
            int s = sum(Main.scents[(int)(x+Math.cos(direction)*3)][(int)(y + Math.sin(direction) * 3)]);
            sums.add(s);
            viableAngles.add(direction);
        }
        if ((int)(x+Math.cos(direction-3.14/8)*3) >= 0 && (int)(x+Math.cos(direction-3.14/8)*3) < Main.WIDTH
        && (int)(y + Math.sin(direction - 3.14 / 8) * 3) > 0 && (int)(y + Math.sin(direction - 3.14 / 8) * 3) < Main.HEIGHT){
            int s = sum(Main.scents[(int)(x+Math.cos(direction-3.14/8)*3)][(int)(y + Math.sin(direction - 3.14 / 8) * 3)]);
            sums.add(s);
            viableAngles.add(direction-3.14/8);
        }
        int bestIndex = indexMaxValue(sums);
        if (bestIndex != -1) {direction = viableAngles.get(bestIndex);}
    }
    private short sum(short[] s) {
        short val = 0;
        for (int i = 0; i < s.length; i++) {
            if (i==type) {val+=s[i];}
            else{val-=s[i];}
        }
        return val;
    }
    private int indexMaxValue(ArrayList<Integer> arr) {
        if (arr.size() == 0) {return -1;}
        int index = 0;
        int value = 0;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) > value) {
                value = arr.get(i);
                index = i;
            }
            if (arr.get(i) == value && Math.random() < 0.3) {
                index = i;
            }
        }
        return index;
    }
}
