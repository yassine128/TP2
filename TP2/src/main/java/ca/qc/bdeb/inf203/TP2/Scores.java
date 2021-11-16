package ca.qc.bdeb.inf203.TP2;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.text.SimpleDateFormat;

public class Scores {


    private static int classement = 0;


    public void writeScore(Camera camera, String nom) throws IOException {
        PrintWriter ecriture = new PrintWriter(new FileOutputStream("src\\main\\resources\\score.txt", true));

        classement++;
        ecriture.println("#"+classement + " -- " + nom + " -- " + Math.round(camera.getY())+"px");
        ecriture.close();
    }

    public ScrollPane readScore() throws IOException {
        BufferedReader lecture = new BufferedReader(new FileReader("src\\main\\resources\\score.txt"));
        String sLigne;
        ScrollPane scroll = new ScrollPane();
        VBox info = new VBox();
        info.setPrefWidth(330);
        while ((sLigne = lecture.readLine()) != null) {
            Text affiche = new Text(sLigne);
            info.getChildren().addAll(affiche);
        }
        scroll.setContent(info);
        return scroll;
    }


}
