package ca.qc.bdeb.inf203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.Random;

public class Partie  {
    private double delai=3;
    Meduse meduse;
    Camera camera;
    boolean debugHold = false;
    boolean oldDebug = false;
    double oldY;
    private Bulle[][] bulles;
    public boolean end = false;


    public void creationPlateforme(){
        Random random=new Random();

        for (int i=0;i<4;i++){
            int randomVal= random.nextInt(4);
            switch (randomVal){
                case 0: Main.listePlateforme.add(new Plateforme(i*100));break;
                case 1: Main.listePlateforme.add(new PlateformeEphemere(i*100));break;
                case 2: Main.listePlateforme.add(new PlateformeMouvante(i*100));break;
                case 3: Main.listePlateforme.add(new PlateformeRebond(i*100));break;
            }
        }
    }

    public Partie() {
        Input.touches.clear();
        Plateforme.posY = 0;
        Main.listePlateforme.clear();
        meduse = new Meduse(0, Main.HEIGHT-50);
        camera = new Camera(0,0,-5);
        bulles= new Bulle[3][5];
    }

    public void draw(double dt, GraphicsContext context){
        meduse.draw(dt, context,camera);
    }

    public void drawDebugInterface(GraphicsContext context){
        context.setFill(Color.PALEVIOLETRED);
        context.fillRect(meduse.x, meduse.y-camera.getY(), meduse.w, meduse.h);

        context.setFill(Color.WHITE);
        context.setFont(new Font(12));
        context.fillText("Position = (" + Math.round(meduse.x) + ", " + Math.round(meduse.y) + ")", 60, 15);
        context.fillText("Vitesse = (" + Math.round(meduse.vx) + ", " + Math.round(meduse.vy) + ")", 60, 30);
        context.fillText("acc = (" + Math.round(meduse.ax)  + ", " + Math.round(meduse.ay) + ")", 60, 45);
        context.fillText("Touche le sol? " + (oldY == meduse.y ? "oui" : "non"), 60, 60);
        oldY = meduse.y;
    }

    public void update(double dt, GraphicsContext context) throws InterruptedException {
        delai+=dt;
        meduse.update(dt, camera);
        camera.update(dt);

        //Démarre le mode debug

        boolean debug = Input.isKeyPressed(KeyCode.T);
        if (debug && !debugHold && !oldDebug)
            debugHold = true;
        else if (!oldDebug && debugHold && debug)
            debugHold = false;
        oldDebug = debug;


        if (debugHold)
            drawDebugInterface(context);


        for (Plateforme p : Main.listePlateforme) {
            if (p.intercept(meduse, p)) {
                if (debugHold) {
                    p.drawDebug(dt, context, camera);
                }
                else{
                    p.draw(dt, context,camera);
                }
                p.collision(meduse, p);
            }
            else{
                p.draw(dt, context,camera);
            }
            p.update(dt);
        }


        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(20));
        context.setFill(Color.WHITE);
        context.fillText(-(int)Math.round(camera.getY())+"PX",Main.WIDTH/2,50);
        if (delai>=3 ){

            delai-=3;
            for (int i=0;i<3;i++){
                Random random=new Random();
                double pos= random.nextDouble()*Main.WIDTH;
                for (int j=0;j<5;j++){
                    double pos2= pos+random.nextDouble()*40-20;
                    double vx= -(random.nextDouble()*100+350);
                    double r= random.nextDouble()*30+10;
                    bulles[i][j]=new Bulle(pos2,Main.HEIGHT,vx,r);
                }
            }
        }
        for (int i=0;i< bulles.length;i++){
            for (int j=0;j< bulles[0].length;j++){
                bulles[i][j].update(dt);
                bulles[i][j].draw(context,camera);
            }
        }

        if (camera.y+Main.HEIGHT < meduse.y) {
            context.setFill(Color.RED);
            context.setFont(new Font(40));
            context.fillText("Partie Terminée", Main.WIDTH/2, Main.HEIGHT/2);
            stopPartie();
        }

        //Delete plateformes
        if (Main.listePlateforme.get(0).getY()>camera.getY()+Main.HEIGHT+meduse.h){
            Main.listePlateforme.remove(0);
        }
    }

    public void stopPartie() throws InterruptedException {
        camera.vy = 0;
        end = true;
    }
}
