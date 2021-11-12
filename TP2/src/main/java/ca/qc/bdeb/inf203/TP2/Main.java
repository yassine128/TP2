package ca.qc.bdeb.inf203.TP2;


import com.sun.javafx.application.ParametersImpl;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main extends Application {

    private Stage primaryStage = new Stage();
    public static int WIDTH = 350;
    public static int HEIGHT = 480;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene jeux = jeux();

        primaryStage.setScene(jeux);
        primaryStage.setTitle("Super Méduse Bros");
        primaryStage.getIcons().add(new Image("meduse1.png"));
        primaryStage.show();
    }

    public Scene jeux(){
        Pane  root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();

        Meduse meduse = new Meduse(0, HEIGHT-50);


        ArrayList<Plateforme> listePlateforme = new ArrayList<>();

        Plateforme plateforme = new Plateforme();

        listePlateforme.add(plateforme);
        listePlateforme.add(new PlateformeRebond());
        listePlateforme.add(new PlateformeEphemere());
        listePlateforme.add(new PlateformeEphemere());


        Partie partie = new Partie(listePlateforme, meduse);


        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaT = (now - lastTime) * 1e-9;

                context.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
                context.setFill(Color.DARKBLUE);
                context.fillRect(0, 0, WIDTH, HEIGHT);
                partie.update(deltaT, context);
                partie.draw(deltaT, context);


                lastTime = now;
            }
        }; timer.start();

        //Event
        scene.setOnKeyPressed(event -> {
            Input.setKeyPressed(event.getCode(), true);
        });
        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });


        return scene;
    }


}
