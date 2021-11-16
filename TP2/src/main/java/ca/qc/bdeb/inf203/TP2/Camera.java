package ca.qc.bdeb.inf203.TP2;

public class Camera {
    private  double y;
    private double vy;
    private double ay;


    public Camera(double y,double vy, double ay) {
        this.y=y;
        this.vy = vy;
        this.ay = ay;
    }
    public void ajouterY(double y){
        this.y+=y;

    }
    public void update(double deltaT){
        vy+=ay*deltaT;
        y+=vy*deltaT;
    }

    public double getY() {
        return y;
    }
}
