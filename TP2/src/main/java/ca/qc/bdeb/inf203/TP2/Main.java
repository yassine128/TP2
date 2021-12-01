package ca.qc.bdeb.inf203.TP2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;

public class Main extends Application {

    public static Stage primaryStage = new Stage();
    public static int WIDTH = 350;
    public static int HEIGHT = 480;
    public Partie partie;
    public static ArrayList<Plateforme> listePlateforme = new ArrayList<>();
    public static int Page;
    private double mort = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage.setScene(sceneAccueil());
        primaryStage.setTitle("Super Méduse Bros");
        primaryStage.getIcons().add(new Image("meduse1.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public Scene scoreScene() throws IOException {
        Scores monScore = new Scores();

        VBox scorePage = new VBox();
        Scene scene = new Scene(scorePage, WIDTH, HEIGHT);

        Text titre = new Text("Meilleurs Scores");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        HBox info = new HBox();
        info.getChildren().add(monScore.readScore());
        info.setAlignment(Pos.CENTER);


        HBox ajoutScore = new HBox();
        Text nom = new Text("Nom:");
        TextField zoneText = new TextField();
        Button save = new Button("Sauvegarder!");
        ajoutScore.getChildren().addAll(nom, zoneText, save);
        ajoutScore.setAlignment(Pos.CENTER);

        Button retour = new Button("Retourner à l'acceuil");

        scorePage.getChildren().addAll(titre, info, ajoutScore, retour);
        scorePage.setAlignment(Pos.TOP_CENTER);
        scorePage.setSpacing(20);
        scorePage.setPadding(new Insets(10, 10, 10, 10));


        //Event
        save.setOnAction(event -> {
            Camera camera = partie.camera;
            try {
                monScore.writeScore(camera, zoneText.getText());
                info.getChildren().clear();
                info.getChildren().add(monScore.readScore());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        retour.setOnAction(event -> {
            try {
                primaryStage.setScene(sceneAccueil());
                Input.touches.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        quitter(scene);
        return scene;
    }
    public Scene scoreAcceuil() throws IOException {
        Scores monScore = new Scores();

        VBox scorePage = new VBox();
        Scene scene = new Scene(scorePage, WIDTH, HEIGHT);

        Text titre = new Text("Meilleurs Scores");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        HBox info = new HBox();
        info.getChildren().add(monScore.readScore());
        info.setAlignment(Pos.CENTER);

        Button retour = new Button("Retourner à l'acceuil");

        scorePage.getChildren().addAll(titre, info, retour);
        scorePage.setAlignment(Pos.TOP_CENTER);
        scorePage.setSpacing(20);
        scorePage.setPadding(new Insets(10, 10, 10, 10));
        retour.setOnAction(event -> {
            try {
                primaryStage.setScene(sceneAccueil());
                Input.touches.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        quitter(scene);
        return scene;
    }
    public Scene sceneAccueil() throws IOException{
        VBox root = new VBox();
        StackPane stackPane= new StackPane();
        Canvas canvas= new Canvas(WIDTH,HEIGHT);
        Scene sceneAccueil = new Scene(stackPane, 350, 480);
        GraphicsContext context=canvas.getGraphicsContext2D();
        context.drawImage(new Image("accueil.png"),0,0);
        context.setFill((Color.DARKBLUE));
        context.fillRect(0,281,WIDTH,HEIGHT);
        Button btnJouer=new Button("Jouer!");
        btnJouer.setPrefSize(100,40);
        Button btnScore= new Button("Meilleurs Scores");
        btnScore.setPrefSize(150,40);
        root.setPadding(new Insets(HEIGHT*1/2,0,0,0));
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(btnJouer,btnScore);
        stackPane.getChildren().addAll(canvas,root);
        quitter(sceneAccueil);

        btnJouer.setOnAction(event -> {
            try {
                primaryStage.setScene(jeux());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnScore.setOnAction(event -> {
            try{
                primaryStage.setScene(scoreAcceuil());
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    return sceneAccueil;
    }

    public void quitter(Scene scene){
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                Platform.exit();
            }
        });
    }

    public Scene jeux() throws IOException {
        Pane  root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();

        partie = new Partie();

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
                partie.creationPlateforme();
                try {
                    partie.update(deltaT, context);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                partie.draw(deltaT, context);
                lastTime = now;


                //change scene
                if (partie.end){
                    mort += deltaT;
                    if (mort >= 3) {
                        try {
                            primaryStage.setScene(scoreScene());
                            this.stop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                    mort = 0;
                if (Input.isKeyPressed(KeyCode.ESCAPE)){
                    try {
                        primaryStage.setScene(sceneAccueil());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Input.touches.clear();
                    this.stop();
                }
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