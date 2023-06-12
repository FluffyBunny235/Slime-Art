import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings implements ActionListener {
    JFrame frame;
    JPanel panelHolder, dimensions, buttons;
    JLabel width, height, numAgents, speed;
    JTextField widthF, heightF, numAgentsF;//, speedF;
    JButton addSlime, setBack, done;
    JColorChooser jcc;
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
        /*
        speed = new JLabel("Speed");
        dimensions.add(speed);
        speedF = new JTextField();
        speedF.setColumns(2);
        dimensions.add(speedF);

         */
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
        if (e.getActionCommand().equals("Set Background")) {
            Main.background = jcc.getColor();
        }
        if (e.getActionCommand().equals("Done")) {
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
                int radius = (int)(Math.random()*Math.min(Main.WIDTH, Main.HEIGHT)/64 + Math.min(Main.WIDTH, Main.HEIGHT)/4);
                Main.agents[i] = new Agent(Main.WIDTH/2.0+Math.cos(angle)*radius,Main.HEIGHT/2.0+Math.sin(angle)*radius,angle+3.141592,(int)(Math.random()*Main.slime.size()));
            }
            Main.sim=new Simulation();
            for (Agent a : Main.agents) {
                a.move();
            }
            Main.diffuse();
            frame.setVisible(false);
        }
    }
}
