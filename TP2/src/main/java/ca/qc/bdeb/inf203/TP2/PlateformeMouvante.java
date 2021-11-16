package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlateformeMouvante extends Plateforme{
    private double nbSecondeTotal;
    private double xBase;

    public PlateformeMouvante() {
        this.xBase = x;
    }

    @Override
    public void draw(double dt, GraphicsContext context, Camera camera) {
        context.setFill(Color.rgb(184, 15, 36));
        context.fillRect(x, y-camera.getY(), w, h);
    }

    public void update(double deltaT){
        nbSecondeTotal += deltaT;
        x = xBase + Math.sin(1 * nbSecondeTotal) * 150;
    }

}
