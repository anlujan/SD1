package com.example.javafxproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.Comparator;

public class Main extends Application {

    static Map<String, Integer> map = new HashMap<>();

    /**
     * Main Method
     * This method uses the for loop to read "theRaven" HTML file from line 84 to 248.
     * All HTML tags are removed.
     * Each line is split into individual words using the spaces as the delimiter.
     * All punctuation is removed.
     * A Map is used to increment the usage of each word.
     * The JavaFX application is launched.
     * @param args file reader to read HTML file, 'theRaven'
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // start reading the file from line number 84.
        for (int lineNumber = 84; lineNumber < 248; lineNumber++) {
            String line = Files.readAllLines(Paths.get("theRaven.html")).get(lineNumber);
            while (line.contains("<") && line.contains(">")) {
                String htmlTag = line.substring(line.indexOf("<"), line.indexOf(">") + 1);
                line = line.replace(htmlTag, "");
            }

            String[] words = line.split(" ");

            for (String word : words) {
                word = word.replace(',', ' ').replace('.', ' ').replace('“', ' ').replace('”', ' ').toLowerCase().trim();
                if (word.length() > 0) {
                    if (map.containsKey(word)) {
                        map.put(word, map.get(word) + 1);
                    } else {
                        map.put(word, (int) 1L);
                    }
                }
            }
        }

        Application.launch(args);
    }

    /**
     * start Method
     * This method gets the key-value pairs from map and stores them in set.
     * Set is then copied to sortedList.
     * sortedList is sorted in descending order.
     * A string called 'wordCounts' is initialized.
     * The first 20 entries of sortedList are found using the for loop.
     * The 20 words are printed to the console.
     * THe JavaFX stage is set up and titled "Count Words in a File".
     * A label (lbl_counts) is made to display 'wordCounts'.
     * A button (btn) is made to provide a closing mechanism for the window.
     * An event handler is made for 'btn' to provide the action of closing the window.
     * The stage is displayed on the screen.
     *
     * @param stage creates stage for JavaFx window
     *
     */
    @Override
    public void start(Stage stage) {
        // TODO Auto-generated method stub
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<Map.Entry<String, Integer>>(set);
        Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> one, Map.Entry<String, Integer> two) {
                return (two.getValue()).compareTo(one.getValue());
            }
        });

        String wordCounts = "";
        for (int i = 0; i < 20 && i < sortedList.size(); i++) {
            System.out.println(sortedList.get(i));
            wordCounts = wordCounts + " \n" + sortedList.get(i).toString();
        }

        stage.setTitle("Count Words in a File");
        stage.setWidth(400);
        stage.setHeight(450);

        Label lbl_counts = new Label(wordCounts);
        lbl_counts.setPadding(new Insets(10, 5, 5, 10));

        Button btn = new Button("Close");

        btn.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
//				Alert a = new Alert(Alert.AlertType.INFORMATION);
//				a.setHeaderText("Thank you");
//				a.show();

                stage.close();

            }
        });

        VBox layout = new VBox(lbl_counts, btn);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();


    }
}