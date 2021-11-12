package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.temporal.Temporal;
import javafx.scene.input.KeyCode;

public class Meduse extends GameObject{

    private int indexImages;
    private double tempsTotal;

    private String[] frames = {"meduse1", "meduse1-g", "meduse2", "meduse2-g", "meduse3", "meduse3-g", "meduse4", "meduse4-g", "meduse5", "meduse5-g", "meduse6", "meduse6-g"};


    public Meduse(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.ax = 100;
        this.ay = 1200;
        this.h = 50;
        this.w = 50;
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
        }
        else if (!right && vx > 0 && x+w < Main.WIDTH){
            vx -= dt*ax;
        }
        if (left && x > 0){
            vx += dt*-ax;
        }
        else if (!left && vx < 0 && x > 0){
            vx += dt*ax;
        }

        if (y + h >= Main.HEIGHT) {
            sauter(jump);
        }
        updateX(dt);
        updateY(dt);
        if (y + h < Main.HEIGHT) {
            vy = vy + ay * dt;
        }
        else {
            vy = 0;
        }
    }

    //Fait sauter le personnage
    public void sauter(boolean jump){
        // Sauter avec Espace ou Flèche vers le haut
        if (jump)
            vy = -600;
    }

    //Update la position de Y
    public void updateY(double dt){
        y += dt*vy;

        if (y + h > Main.HEIGHT){
            y = Main.HEIGHT-h;
        }

    }

    //Update la position de X
    public void updateX(double dt){
        x += dt*vx;
        if (x < 0){
            x = 0;
            vx *= -0.9;
        }
        else if (x + w > Main.WIDTH){
            x = Main.WIDTH-w;
            vx *= -0.5;
        }
    }

    //Permet de dessiner la méduse sur l'écran.
    public void draw(double deltaT, GraphicsContext context){
        tempsTotal += deltaT;
        indexImages = (int) Math.floor(tempsTotal * 8) % (frames.length);

        Image personnage = new Image(frames[indexImages]+".png", 50, 50, true, false);
        //context.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        context.drawImage(personnage, x, y);
    }

    public void setVy(double vy){
        this.vy = vy;
    }
    public void setY(double y){
        this.y = y;
    }

}
