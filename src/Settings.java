import javax.swing.*;
import java.awt.*;
import java.util.Random;

class Settings extends JPanel {
    private JButton wall = new JButton("Generate Your Wall");
    private JButton cball = new JButton("Generate Your Ball");
    private boolean wallc = false;


    Settings(Run run) {
        this.setLayout(null);
        this.setBounds(0, 0, run.getWidth() / 5, run.getHeight());
        this.setBackground(Color.LIGHT_GRAY);
        JButton random = new JButton("Generate Random Ball");
        random.setBounds(10, 10, this.getWidth() - 20, 30);
        random.addActionListener(e -> {
            boolean randball = true;
            Ball ball1 = null;
            while (randball) {
                randball = false;
                Random rand = new Random();
                ball1 = new Ball(rand.nextInt(run.ballPanel.getWidth()), rand.nextInt(run.ballPanel.getHeight())
                        , rand.nextInt(50), rand.nextInt(100),
                        rand.nextInt(150), rand.nextInt(20), run.ballPanel);
                Ball ball;
                double distance;
                for (int i = 0; i < run.ballPanel.getBalls().size(); i++) {
                    ball = run.ballPanel.getBalls().get(i);
                    distance = Math.sqrt(
                            ((ball.getCenterX() - ball1.getCenterX()) *
                                    (ball.getCenterX() - ball1.getCenterX()))
                                    + ((ball.getCenterY() - ball1.getCenterY()) *
                                    (ball.getCenterY() - ball1.getCenterY()))
                    );
                    if (distance - 10 < ball.getRadius() + ball1.getRadius()) {
                        randball = true;
                    }
                }
            }
            run.ballPanel.balls.add(ball1);
        });
        this.add(random);
        JLabel rad = new JLabel("Radius : ");
        rad.setBounds(random.getX(), random.getHeight() + random.getY() + 5, 70, random.getHeight());
        JLabel mass = new JLabel("Mass : ");
        mass.setBounds(random.getX(), rad.getY() + rad.getHeight() + 5, rad.getWidth(), rad.getHeight());
        this.add(rad);
        this.add(mass);
        JTextField radius = new JTextField();
        JTextField masss = new JTextField();
        radius.setBounds(rad.getWidth() + rad.getX(), rad.getY(), this.getWidth() -
                (rad.getX() + rad.getWidth() + 2), rad.getHeight());
        masss.setBounds(mass.getWidth() + mass.getX(), mass.getY(), this.getWidth() -
                (mass.getWidth() + mass.getX() + 2), mass.getHeight());
        this.add(radius);
        this.add(masss);
        cball.setBounds(10, mass.getY() + mass.getHeight() + 10, random.getWidth(), random.getHeight());
        cball.addActionListener(e -> {
            run.ballPanel.settemprad(Integer.parseInt(radius.getText()));
            run.ballPanel.settempmass(Integer.parseInt(masss.getText()));
            cball.setText("Drag your ball");
        });
        this.add(cball);
        wall.setBounds(cball.getX(), cball.getY() + cball.getHeight() + 10, cball.getWidth(), cball.getHeight());
        wall.addActionListener(e -> {
            setWallc(!isWallc());
            if (isWallc()) {
                wall.setText("End Paint Wall");
            } else wall.setText("Generate Your Wall");
        });
        this.add(wall);
    }

    boolean isWallc() {
        return wallc;
    }

    private void setWallc(boolean wallc) {
        this.wallc = wallc;
    }

    void cballname() {
        cball.setText("Generate Your Ball");
    }

}
