import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings implements ActionListener {
    String spawnMode = "Point";
    JFrame frame;
    JPanel panelHolder, dimensions, buttons;
    JLabel width, height, numAgents;
    JTextField widthF, heightF, numAgentsF;//, speedF;
    JButton addSlime, setBack, done;
    JColorChooser jcc;
    JMenu menu;
    JMenuBar bar;
    JMenuItem border, donut, disc, point, full;
    public Settings() {
        frame = new JFrame("Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelHolder = new JPanel();
        panelHolder.setLayout(new BoxLayout(panelHolder, BoxLayout.Y_AXIS));
        frame.add(panelHolder);

        jcc = new JColorChooser();
        panelHolder.add(jcc);

        dimensions = new JPanel();
        width = new JLabel("Width");
        dimensions.add(width);
        widthF = new JTextField();
        widthF.setColumns(4);
        dimensions.add(widthF);
        height = new JLabel("Height");
        dimensions.add(height);
        heightF = new JTextField();
        heightF.setColumns(4);
        dimensions.add(heightF);
        numAgents = new JLabel("Number of Agents");
        dimensions.add(numAgents);
        numAgentsF = new JTextField();
        numAgentsF.setColumns(7);
        dimensions.add(numAgentsF);
        menu = new JMenu("Mode");
        bar = new JMenuBar();
        border = new JMenuItem("Border");
        border.addActionListener(this);
        menu.add(border);
        donut = new JMenuItem("Donut");
        donut.addActionListener(this);
        menu.add(donut);
        disc = new JMenuItem("Disc");
        disc.addActionListener(this);
        menu.add(disc);
        point = new JMenuItem("Point");
        point.addActionListener(this);
        menu.add(point);
        full = new JMenuItem("Full");
        full.addActionListener(this);
        menu.add(full);
        bar.add(menu);
        dimensions.add(bar);
        panelHolder.add(dimensions);

        buttons = new JPanel();
        addSlime = new JButton("Add Slime");
        addSlime.addActionListener(this);
        buttons.add(addSlime);
        setBack = new JButton("Set Background");
        setBack.addActionListener(this);
        buttons.add(setBack);
        done = new JButton("Done");
        done.addActionListener(this);
        buttons.add(done);
        panelHolder.add(buttons);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Slime")) {
            Main.slime.add(jcc.getColor());
        }
        else if (e.getActionCommand().equals("Set Background")) {
            Main.background = jcc.getColor();
        }
        else if (e.getActionCommand().equals("Done")) {
            Main.HEIGHT = Integer.parseInt(heightF.getText());
            Main.WIDTH = Integer.parseInt(widthF.getText());
            Main.display = new Color[Main.WIDTH][Main.HEIGHT];
            Main.scents = new short[Main.WIDTH][Main.HEIGHT][Main.slime.size()];
            Main.numAgents = Integer.parseInt(numAgentsF.getText());
            //Main.speed = Integer.parseInt(speedF.getText());
            Main.agents = new Agent[Main.numAgents];
            if (Main.slime.size() == 0) {
                Main.slime.add(Color.CYAN);
            }
            for (int i = 0; i < Main.numAgents; i++) {
                double angle = Math.random()*2*3.141592;
                if (spawnMode.equals("Point")) {
                    Main.agents[i] = new Agent(Main.WIDTH/2.0,Main.HEIGHT/2.0,angle, (int)(Math.random()*Main.slime.size()));
                }
                else if (spawnMode.equals("Disc")) {
                    int radius = (int)(Math.random()*Math.min(Main.WIDTH, Main.HEIGHT)/4);
                    Main.agents[i] = new Agent(Main.WIDTH/2.0+Math.cos(angle)*radius,Main.HEIGHT/2.0+Math.sin(angle)*radius,angle+3.141592,(int)(Math.random()*Main.slime.size()));
                }
                else if (spawnMode.equals("Full")) {
                    Main.agents[i] = new Agent(Math.random()*Main.WIDTH, Math.random()*Main.HEIGHT, Math.random()*6.283184, (int)(Math.random()*Main.slime.size()));
                }
                else if (spawnMode.equals("Donut")) {
                    int radius = (int)(Math.random()*Math.min(Main.WIDTH, Main.HEIGHT)/64 + Math.min(Main.WIDTH, Main.HEIGHT)/4);
                    Main.agents[i] = new Agent(Main.WIDTH/2.0+Math.cos(angle)*radius,Main.HEIGHT/2.0+Math.sin(angle)*radius,Math.random()*2*3.141592,(int)(Math.random()*Main.slime.size()));
                }
                else {
                    int x = (int)(Math.random()*(Main.WIDTH+Main.HEIGHT)*2);
                    if (x < Main.WIDTH){Main.agents[i] = new Agent(x,0,Math.random()*3.141592+3.141592, (int)(Math.random()*Main.slime.size()));}
                    else if (x<Main.WIDTH+Main.HEIGHT){Main.agents[i] = new Agent(0,x-Main.WIDTH,Math.random()*3.141592+1.5720796, (int)(Math.random()*Main.slime.size()));}
                    else if (x < 2*Main.WIDTH+Main.HEIGHT){Main.agents[i] = new Agent(x-Main.WIDTH-Main.HEIGHT,Main.HEIGHT-1,Math.random()*3.141592, (int)(Math.random()*Main.slime.size()));}
                    else {Main.agents[i] = new Agent(Main.WIDTH-1,x-2*Main.WIDTH-Main.HEIGHT,Math.random()*3.141592-1.5720796, (int)(Math.random()*Main.slime.size()));}
                }
            }
            Main.sim=new Simulation();
            for (Agent a : Main.agents) {
                a.move();
            }
            Main.diffuse();
            frame.setVisible(false);
        }
        else {
            spawnMode = e.getActionCommand();
        }
    }
}
