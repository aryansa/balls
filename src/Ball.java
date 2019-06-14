import javafx.scene.shape.Circle;

import java.util.Random;

class Ball extends Circle {
    private float[] speed = new float[2];
    private int mass;
    private BallPanel ballPanel;
    private int[] color = new int[3];

    Ball(double centerX, double centerY, double radius,
         float speedx, float speedy, int mass,
         BallPanel ballPanel) {
        super(centerX, centerY, radius);
        this.ballPanel = ballPanel;
        Random rand = new Random();
        color[0] = rand.nextInt(255);
        color[1] = rand.nextInt(255);
        color[2] = rand.nextInt(255);
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadius(radius);
        this.speed[0] = speedx;
        this.speed[1] = speedy * -1;
        this.mass = mass;
    }

    void checkCollisions() {
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

    float[] resolveCollision(Ball ball) {
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

    void move(float elapsedSeconds) {
        this.setCenterX(getCenterX() + (this.speed[0] * (elapsedSeconds)));
        setCenterY(getCenterY() + (getSpeed()[1] * (elapsedSeconds)));
    }

    float[] getSpeed() {
        return speed;
    }

    void setSpeedx(float speed) {
        this.speed[0] = speed;
    }

    void setSpeedy(float speed) {
        this.speed[1] = speed;
    }

    int[] getColor() {
        return color;
    }

    int getMass() {
        return mass;
    }

}
