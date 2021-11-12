package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Partie {

    ArrayList<Plateforme> listePlateforme = new ArrayList<>();
    Meduse meduse;
    boolean debugHold = false;
    boolean oldDebug = false;
    double oldY;


    public Partie(ArrayList<Plateforme> listePlateforme, Meduse meduse) {
        this.listePlateforme = listePlateforme;
        this.meduse = meduse;
    }

    public void draw(double dt, GraphicsContext context){
        meduse.draw(dt, context);
    }


    public void drawDebugInterface(GraphicsContext context){
        context.setFill(Color.PALEVIOLETRED);
        context.fillRect(meduse.x, meduse.y, meduse.w, meduse.h);

        context.setFill(Color.WHITE);
        context.fillText("Position = (" + Math.round(meduse.x) + ", " + Math.round(meduse.y) + ")", 10, 15);
        context.fillText("Vitesse = (" + Math.round(meduse.vx) + ", " + Math.round(meduse.vy) + ")", 10, 30);
        context.fillText("acc = (" + Math.round(meduse.ax)  + ", " + Math.round(meduse.ay) + ")", 10, 45);
        context.fillText("Touche le sol? " + (oldY == meduse.y ? "oui" : "non"), 10, 60);
        oldY = meduse.y;
    }

    public void update(double dt, GraphicsContext context) {
        meduse.update(dt);


        //Démarre le mode debug
        boolean debug = Input.isKeyPressed(KeyCode.T);
        if (debug && !debugHold && !oldDebug)
            debugHold = true;
        else if (!oldDebug && debugHold && debug)
            debugHold = false;
        oldDebug = debug;


        if (debugHold)
            drawDebugInterface(context);


        for (Plateforme p : listePlateforme) {
            if (p.intercept(meduse, p)) {
                if (debugHold) {
                    p.drawDebug(dt, context);
                }
                else{
                    p.draw(dt, context);
                }
                p.collision(meduse, p);
            }
            else{
                p.draw(dt, context);
            }
            p.update(dt);
        }
    }
}
