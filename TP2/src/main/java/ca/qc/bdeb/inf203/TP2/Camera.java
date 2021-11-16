package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;

public class Camera extends GameObject{

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

    @Override
    public void draw(double dt, GraphicsContext context, Camera camera) {

    }
}
