import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class BallPanel extends JPanel {
    public ArrayList<Ball> balls = new ArrayList<Ball>();
    boolean temp = false;
    Ball tempball;
    Line2D line;
    boolean hasswall =false;
    int [] speedtemp = new int[2];
    JLabel tempspeed = new JLabel("") ;
    private int currentFrameRate;
    Run run;
    BallPanel panel = this;
    long previousTime = System.currentTimeMillis();
    long currentTime = previousTime;
    long elapsedTime;
    Wall wall = new Wall() ;
int [] balltemp = new int[2];
    public BallPanel(Run run) {
        this.run = run;
        MouseHandler mouseHandler = new MouseHandler();
        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);
        this.setBounds(run.getSettings().getWidth(), 0, run.getWidth() - run.getSettings().getWidth(), run.getHeight());
        this.setBackground(Color.black);
        tempspeed.setVisible(false);

        this.add(tempspeed);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    currentTime = System.currentTimeMillis();
                    elapsedTime = (currentTime - previousTime);

                    handel(elapsedTime / 1000f);
                    panel.repaint();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    previousTime = currentTime;
                }
            }
        }).start();


    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public boolean isHasswall() {
        return hasswall;
    }

    public Wall getWall() {
        return wall;
    }

    public int[] getBalltemp() {
        return balltemp;
    }
 public void settempmass(int mass){
        this.balltemp[0] = mass;
 }
    public void settemprad(int rad) {
        this.balltemp[1] = rad;
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    void handel(float elapsedTime) {
        double  distance;
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            balls.get(i).move(elapsedTime);
            balls.get(i).checkCollisions();
            for (int j = i+1; j < balls.size() ; j++) {
                Ball ball1 = balls.get(j);
         distance  = Math.sqrt(
                        ((ball.getCenterX() - ball1.getCenterX()) * (ball.getCenterX() - ball1.getCenterX()))
                + ((ball.getCenterY() - ball1.getCenterY()) * (ball.getCenterY() - ball1.getCenterY()))
           );
                if (distance-5 < ball.getRadius() + ball1.getRadius())
                {
                  float[] spedd =  ball.resolveCollision(ball1);
                  balls.get(j).setSpeedx(spedd[0]);
                  balls.get(j).setSpeedy(spedd[1]);
                }
            }

        }
 }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.draw(wall);
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            Ellipse2D.Double circle = new Ellipse2D.Double(ball.getCenterX()-ball.getRadius(), ball.getCenterY()-ball.getRadius(), ball.getRadius()*2, ball.getRadius()*2);
            g2.setColor(new Color(ball.getColor()[0], ball.getColor()[1], ball.getColor()[2]));
            g2.fill(circle); }
        if (temp) {
            g2.setColor(Color.red);
            g2.draw(line);
            Ellipse2D.Double circle = new Ellipse2D.Double(tempball.getCenterX()-tempball.getRadius(), tempball.getCenterY()-tempball.getRadius(), tempball.getRadius()*2, tempball.getRadius()*2);
            g2.setColor(new Color(tempball.getColor()[0], tempball.getColor()[1], tempball.getColor()[2]));
            g2.fill(circle);
           tempspeed.setVisible(true);
            }

    }

    private class MouseHandler extends MouseAdapter implements MouseMotionListener {
        public void mousePressed(MouseEvent e) {
if (run.settings.isWallc()){
    hasswall=true;
wall.addPoint(e.getX(),e.getY());
    System.out.println(wall.getNpoints());
}
else {
    tempball = new Ball(e.getX(), e.getY(), getBalltemp()[1], 10, 10, getBalltemp()[0], panel);
    line = new Line2D.Float(e.getX(), e.getY(), e.getX(), e.getY());
}   }

        public void mouseReleased(MouseEvent e) {
            if (run.settings.isWallc()){

            }
            else {
            tempball.setSpeedx((int) (line.getX2() - line.getX1()));
            tempball.setSpeedy((int) (line.getY2() - line.getY1()));
            speedtemp[0] = (int) tempball.getSpeed()[0];
            speedtemp[1] = (int) tempball.getSpeed()[1];

            tempspeed.setVisible(false);
            balls.add(tempball);
            temp = false;

            run.settings.cballname();
        }
        }

        public void mouseDragged(MouseEvent e) {
            if (run.settings.isWallc()) {
            } else {
                temp = true;
                double x1 = line.getX1();
                double y1 = line.getY1();
                double x2 = e.getX();
                double y2 = e.getY();
                double dx = Math.abs(x2 - x1);
                double dy = Math.abs(y2 - y1);
                if ((x2 - x1) < 0) {
                    line.setLine(line.getX1(), line.getY1(), (x1 + dx), line.getY2());
                } else {
                    line.setLine(line.getX1(), line.getY1(), (x1 - dx), line.getY2());
                }

                if ((y2 - y1) < 0) {
                    line.setLine(line.getX1(), line.getY1(), line.getX2(), (y1 + dy));
                } else {
                    line.setLine(line.getX1(), line.getY1(), line.getX2(), (y1 - dy));
                }
                tempspeed.setText("(" + (line.getX2() - line.getX1()) + "," + -(line.getY2() - line.getY1()) + ")");
                tempspeed.setBounds((int) line.getX2(), (int) line.getY2(), 100, 50);
            }
        }
        public void mouseMoved(MouseEvent e) {
            // Nada
        }
    }


}
