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

    public void collision(Meduse meduse, Plateforme plateforme){
        // Vérifie que la méduse est sur une plateforme
        boolean jump = Input.isKeyPressed(KeyCode.SPACE) || Input.isKeyPressed(KeyCode.UP);
        if (meduse.vy > 0 && (meduse.y + meduse.h) >= plateforme.y && (meduse.x + meduse.w) > plateforme.x && (meduse.x) < (plateforme.x + plateforme.w)) {

            // Teleporte la méduse pour qu'elle ne rentre pas dans la plateforme
            if ((meduse.y + meduse.h) > plateforme.y){
                meduse.y = plateforme.y-meduse.h;
            }
            meduse.setVy(0);
            if (jump)
                meduse.sauter(jump);

        }
    }
}
