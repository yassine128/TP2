package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.temporal.Temporal;
import javafx.scene.input.KeyCode;

public class Meduse extends GameObject{

    private int indexImages;
    private double tempsTotal;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double ax;
    private int ay = 1200;
    private int w = 50;
    private int h = 50;

    private String[] frames = {"meduse1", "meduse1-g", "meduse2", "meduse2-g", "meduse3", "meduse3-g", "meduse4", "meduse4-g", "meduse5", "meduse5-g", "meduse6", "meduse6-g"};


    public Meduse(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.ax = 100;
    }

    //Permet de mettre à jour la position de la méduse.
    public void update(double deltaT){
        boolean left = Input.isKeyPressed(KeyCode.LEFT);
        boolean right = Input.isKeyPressed(KeyCode.RIGHT);
        boolean jump = Input.isKeyPressed(KeyCode.SPACE) || Input.isKeyPressed(KeyCode.UP);
        movement(left, right, jump, deltaT);
    }

    //Déplacer le personnage
    public void movement(boolean left, boolean right, boolean jump, double dt){
        if (right && x+w < Main.WIDTH){
            vx += dt*ax;
            updateX(dt);
        }
        else if (!right && vx > 0 && x+w < Main.WIDTH){
            vx -= dt*ax;
            updateX(dt);
        }
    }

    public void updateX(double dt){
        x += dt*vx;
    }



    //Permet de dessiner la méduse sur l'écran.
    public void draw(double deltaT, GraphicsContext context){
        tempsTotal += deltaT;
        indexImages = (int) Math.floor(tempsTotal * 8) % (frames.length);

        Image personnage = new Image(frames[indexImages]+".png", 50, 50, true, false);
        context.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        context.drawImage(personnage, x, y);
    }

}