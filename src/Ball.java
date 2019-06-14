import javafx.scene.shape.Circle;

import java.awt.geom.Point2D;
import java.util.Random;

public class Ball extends Circle {
    float[] speed = new float[2];
    int zavieh;
    int mass;
    BallPanel ballPanel;
    int[] color = new int[3];

    public Ball(double centerX, double centerY, double radius, float speedx, float speedy, int mass, BallPanel ballPanel) {

        super(centerX, centerY, radius);
        this.ballPanel = ballPanel;
        Random rand = new Random();
        color[0] = rand.nextInt(255);
        color[1] = rand.nextInt(255);
        color[2] = rand.nextInt(255);

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadius(radius);
//        this.zavieh = zavieh;
        this.speed[0] = speedx;
        this.speed[1] = speedy * -1;
        this.mass = mass;
    }

    public void checkCollisions() {
        if (this.getCenterX() - this.getRadius() < 0) {
            this.setCenterX(this.getRadius());
            this.setSpeedx(-(getSpeed()[0]));
        } else if (getCenterX() + getRadius() > ballPanel.getWidth()) {
            setCenterX(ballPanel.getWidth() - getRadius());
            this.setSpeedx(-(getSpeed()[0]));
        }

        if (getCenterY() - getRadius() < 0) {
            setCenterY(getRadius());
            setSpeedy(-(getSpeed()[1]));
        } else if (getCenterY() + getRadius() > ballPanel.getHeight()) {
            setCenterY(ballPanel.getHeight() - getRadius());
            setSpeedy(-(getSpeed()[1]));
        }
    }

    public float[] resolveCollision(Ball ball) {
        float newVelX1 = (this.getSpeed()[0] * (this.getMass() - ball.getMass()) +
                (2 * ball.getMass() * ball.getSpeed()[0])) / (this.getMass() + ball.getMass());
        float newVelY1 = (this.getSpeed()[1] * (this.getMass() - ball.getMass()) +
                (2 * ball.getMass() * ball.getSpeed()[1])) / (this.getMass() + ball.getMass());
        float newVelX2 = (ball.getSpeed()[0] * (ball.getMass() - this.getMass()) +
                (2 * this.getMass() * this.getSpeed()[0])) / (this.getMass() + ball.getMass());
        float newVelY2 = (ball.getSpeed()[1] * (ball.getMass() - this.getMass()) +
                (2 * this.getMass() * this.getSpeed()[1])) / (this.getMass() + ball.getMass());
        this.setSpeedx(newVelX1);
        this.setSpeedy(newVelY1);
        float[] ret = new float[2];
        ret[0] = newVelX2;
        ret[1] = newVelY2;
        return ret;
    }

    public void move(float elapsedSeconds) {
        this.setCenterX(getCenterX() + (this.speed[0] * (elapsedSeconds)));
        setCenterY(getCenterY() + (getSpeed()[1] * (elapsedSeconds)));
    }

    public float[] getSpeed() {
        return speed;
    }

    public void setSpeedx(float speed) {
        this.speed[0] = speed;
    }

    public void setSpeedy(float speed) {
        this.speed[1] = speed;
    }

    public int[] getColor() {
        return color;
    }

    public int getZavieh() {
        return zavieh;
    }

    public void setZavieh(int zavieh) {
        this.zavieh = zavieh;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }
}
