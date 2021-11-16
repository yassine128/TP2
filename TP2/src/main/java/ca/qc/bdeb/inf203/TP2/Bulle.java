package ca.qc.bdeb.inf203.TP2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bulle {
    protected double x,y;
    protected double r;
    protected double vy;
    protected double ay;
    protected Color color;

    public Bulle(double x, double y,double vy, double r){
        this(x,y,r,Color.rgb(0,0,255,0.4),vy,0);
    }

    public Bulle(double x, double y, double r, Color color, double vy, double ay){
        this.x=x;
        this.y=y;
        this.r=r;
        this.color=color;
        this.ay=ay;
        this.vy=vy;
    }
    public void update(double deltaT){

        vy+=ay*deltaT;
        y+=vy*deltaT;
    }

    public void draw(GraphicsContext context, Camera camera){
        context.setFill(color);
        context.fillOval(x,y,r,r);

    }
}