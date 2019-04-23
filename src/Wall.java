import java.awt.*;

public class Wall extends Polygon {
    public Wall(int[] xpoints, int[] ypoints, int npoints) {
        super(xpoints, ypoints, npoints);
    }


    public Wall(){}
    public int[] getXpoints(){
        return this.xpoints;
    }
    public int[] getYpoints(){
        return this.ypoints;
    }
    public int getNpoints(){
        return this.npoints;
    }
}
