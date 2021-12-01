package ca.qc.bdeb.inf203.TP2;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.ListView;
import java.io.*;
import java.util.ArrayList;

public class Scores{

    private ArrayList<String> classement = new ArrayList<>();
    private ArrayList<Integer> classementScore = new ArrayList<>();

    public void writeScore(Camera camera, String nom) throws IOException {
        PrintWriter ecriture = new PrintWriter(new FileOutputStream("src\\main\\resources\\score.txt", true));

        if (!nom.equals(""))
            ecriture.println(nom + " -- " + Math.round(-camera.getY())+"px");
        ecriture.close();
    }

    public ListView readScore() throws IOException {
        BufferedReader lecture = new BufferedReader(new FileReader("src\\main\\resources\\score.txt"));
        String sLigne;
        classement.clear();
        while ((sLigne = lecture.readLine()) != null) {
            classement.add(sLigne);
            classementScore.add(Integer.parseInt((sLigne.split(" -- ")[1]).split("px")[0]));
        }


        classement = triage();

        System.out.println(classement.size());
        ListView listView = new ListView();
        HBox info = new HBox();
        info.setPrefWidth(330);

        for (int i = 0; i < classement.size(); i++){
            String text = "#"+(i+1) + " -- " + classement.get(i);
            Text affiche = new Text(text);
            listView.getItems().add(affiche);
        }
        return listView;
    }

    public ArrayList<String> triage(){

        String temp;
        for (int position = classement.size() - 1; position >= 0; position--) {
            // tab.length - 1 puisqu’on trie à partir du dernier élément
            for (int recherche = 0; recherche <= position - 1; recherche++){
                // Recherche du plus grand élément et déplacement par
                // permutations vers la fin du tableau
                int pos1 = Integer.parseInt((classement.get(recherche).split(" -- ")[1]).split("px")[0]);
                int pos2 = Integer.parseInt((classement.get(recherche + 1).split(" -- ")[1]).split("px")[0]);
                if (pos1 < pos2) {
                    // Échanger les éléments voisins s'ils ne sont pas dans l'ordre naturel
                    temp = classement.get(recherche);
                    classement.set(recherche, classement.get(recherche+1));
                    classement.set(recherche+1, temp);
                }
            }
        }

        return classement;
    }


}
