package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Partie {

    ArrayList<Plateforme> listePlateforme = new ArrayList<>();
    Meduse meduse;

    public Partie(ArrayList<Plateforme> listePlateforme, Meduse meduse) {
        this.listePlateforme = listePlateforme;
        this.meduse = meduse;
    }

    public void draw(double dt, GraphicsContext context){
        meduse.draw(dt, context);

        for (Plateforme p : listePlateforme){
            p.draw(dt, context);
        }
    }

    public void update(double dt){
        meduse.update(dt);

        for (Plateforme p : listePlateforme) {
            if (p.intercept(meduse, p)) {
                p.collision(meduse, p);
            }
            p.update(dt);
        }
    }


}
