package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class PlateformeEphemere extends Plateforme{


    private boolean quitterPlateforme = false;


    public void update(double deltaT){
        if (quitterPlateforme){
            y += deltaT*200;
        }
    }


    @Override
    public void collision(Meduse meduse, Plateforme plateforme){
        boolean jump = Input.isKeyPressed(KeyCode.SPACE) || Input.isKeyPressed(KeyCode.UP);
        fixPosition(meduse, plateforme);
        if (jump) {
            quitterPlateforme = true;
            meduse.sauter(jump);
        }
    }



    @Override
    public void draw(double dt, GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRect(x, y, w, h);
    }

}
