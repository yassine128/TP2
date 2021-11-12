package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Random;

public class Plateforme extends GameObject{

    protected static double posY = 0;

    public Plateforme() {
        this.h = 10;
        posY += 100;
        this.y = Main.HEIGHT-posY;
        Random rand = new Random();
        this.w = 80 + rand.nextInt(175 - 80);
        this.x = rand.nextInt((Main.WIDTH - w));
    }

    @Override
    public void draw(double dt, GraphicsContext context) {
        context.setFill(Color.rgb(230, 134, 58));
        context.fillRect(x, y, w, h);
    }

    public void update(double deltaT){
        boolean jump = Input.isKeyPressed(KeyCode.SPACE) || Input.isKeyPressed(KeyCode.UP);
    }

    /**
     * Si la méthode intercep retourne true alors nous alors update la position de la méduse pour qu'elle reste sur la plateforme
     * @param meduse
     * @param plateforme
     */
    public void collision(Meduse meduse, Plateforme plateforme){
        boolean jump = Input.isKeyPressed(KeyCode.SPACE) || Input.isKeyPressed(KeyCode.UP);
        fixPosition(meduse, plateforme);
        if (jump)
            meduse.sauter(jump);
    }


    /**
     * Teleporte le personnage pour qu'il ne reste pas coincé dans la plateforme
     * @param meduse
     * @param plateforme
     */
    public void fixPosition(Meduse meduse, Plateforme plateforme){
        // Teleporte la méduse pour qu'elle ne rentre pas dans la plateforme
        if ((meduse.y + meduse.h) > plateforme.y){
            meduse.setY(plateforme.y-meduse.h);
        }

        meduse.setVy(0);
    }


    /**
     * Vérrifie si la méduse est entré en collision avec une plateforme
     * @param meduse
     * @param plateforme
     * @return
     */
    public boolean intercept(Meduse meduse, Plateforme plateforme){
        if (meduse.vy > 0 && (meduse.x + meduse.w) >= plateforme.x && (meduse.x) <= (plateforme.x + plateforme.w)) {
            if ((meduse.y + meduse.h) >= plateforme.y && (meduse.y + meduse.h) <= (plateforme.y+plateforme.h)+10)
                return true;
        }
        return false;
    }

}
