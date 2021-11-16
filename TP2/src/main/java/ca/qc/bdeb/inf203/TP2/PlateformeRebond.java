package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class PlateformeRebond extends Plateforme {

    @Override
    public void collision(Meduse meduse, Plateforme plateforme){
        boolean jump = Input.isKeyPressed(KeyCode.SPACE) || Input.isKeyPressed(KeyCode.UP);

        meduse.vy *= -1.5;
        if (meduse.vy > -100) {
            meduse.vy = -100;
        }

        if (jump) {
            meduse.sauter(jump);
        }
    }


    @Override
    public void draw(double dt, GraphicsContext context,Camera camera) {
        context.setFill(Color.LIGHTGREEN);
        context.fillRect(x, y-camera.getY(), w, h);
    }

}
