package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class GameObject {
    protected double x, y;
    protected double vx, vy;
    protected double ax, ay;
    protected int w, h;

    public abstract void draw(double dt, GraphicsContext context);


}
