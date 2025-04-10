package com.example.project1;

import com.example.project1.OptimalGameStrategy;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.project1.OptimalGameStrategy.dpTable;

public class HelloApplication extends Application {

     Button btkeyboard;
     Button openButton;
     TextField tf1 = new TextField();
     TextField tf2 = new TextField();
     Button btkey = new Button("Start");
     Button btdptable = new Button("dptable");
     Button btcomputer = new Button("computer");
     Button btplayer = new Button(" Two Player");
     Button details = new Button("Game details");
     Button exit = new Button("Exit");
    int[] ints;
    OptimalGameStrategy strategy;
    VBox vBox3;
    Scene firstScene;
    Stage firstStage;

    public void start(Stage stage) throws IOException {

        exit.setOnAction(event -> {
            firstStage = new Stage();
            firstScene = new Scene(vBox3, 300, 400);
            firstStage.setScene(firstScene);
            firstStage.sizeToScene(); // ضبط حجم النافذة تلقائيًا حسب حجم الـ Scene
            firstStage.setResizable(false); // منع تغيير الحجم
            firstStage.show();
        });


         vBox3 = new VBox(7);

        vBox3.getChildren().addAll(btcomputer,btplayer,details);
        vBox3.setAlignment(Pos.CENTER);
       BorderPane pane = new BorderPane();

     btcomputer.setOnAction(e->{
         Scene scene = new Scene(pane, 300, 400);
         stage.setScene(scene);
         stage.sizeToScene();
         stage.setResizable(false);
         stage.show();
     });
     btplayer.setOnAction(e->{
         ArrayDisplay arrayDisplay = new ArrayDisplay();
         arrayDisplay.displayArrayFromInput(stage);
     });
     details.setOnAction(e->{
         TextArea textArea = new TextArea();
         File file = new File("C:\\Users\\Lenovo\\Desktop\\algorithm\\details.txt");

         if (file != null) {
             try {

                 String content = readFileContent(file);

                 textArea.setText(content);
             } catch (IOException ex) {
                 showError("Error loading file: " ,"" ,ex.getMessage());
             }
         }
         VBox root = new VBox( textArea);
         Scene scene = new Scene(root, 400, 400);

         stage.setTitle("details");
         stage.setScene(scene);
         stage.show();
     });
        // Create a button to open the file chooser
        openButton = new Button("Read from File");
        btkeyboard = new Button(" by Key board");

        // Add an action to the button
        openButton.setOnAction(e ->{
            openFile(stage);
            showResult();
        });
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(openButton, btkeyboard);
        vBox.setAlignment(Pos.CENTER);
        pane.setCenter(vBox);
        setBackgroundImage(pane,"file:/C:/Users/Lenovo/Desktop/algorithm/coins3.png");

        btkeyboard.setOnAction(e -> {
    tf2.addEventFilter(KeyEvent.KEY_TYPED, event -> {
        String input = event.getCharacter();
        if (!input.matches("[0-9 ]")) {
            event.consume();
        }
    });
    btkey.setOnAction(e1 -> {
        // Get the text from the input field
        String text = tf2.getText().trim();
        String Size = tf1.getText().trim();

        // Check if the text field is empty
        if (text.isEmpty()) {
            showError("Error","Input field is empty.","");
            return;
        }

        try {
            // Split the text into an array of words based on spaces
            String[] textArray = text.split(" ");
             ints = new int[Integer.parseInt(Size)];

            // Convert each word to an integer
            for (int i = 0; i < textArray.length; i++) {
                ints[i] = Integer.parseInt(textArray[i].trim());
            }

            // Initialize and use the OptimalGameStrategy with the parsed integers
             strategy = new OptimalGameStrategy(ints);

        } catch (NumberFormatException ex) {
            // Handle invalid number format in the input
            showError("Error","Input contains non-integer values.","");

        } catch (Exception ex) {
            // Handle any unexpected errors
            showError("Error","Unexpected error ",ex.getMessage());
        }
        showResult();
    });
    btdptable.setOnAction(e1->{
        DpTableGrid dpTableGrid = new DpTableGrid();
        GridPane gridPane = dpTableGrid.createDpTableGrid(dpTable, ints);

        Scene scene = new Scene(gridPane, 400, 400);
        stage.setTitle("DP Table Grid");
        stage.setScene(scene);
        stage.sizeToScene(); // ضبط حجم النافذة تلقائيًا حسب حجم الـ Scene
        stage.setResizable(false); // منع تغيير الحجم
        stage.show();
    });

    Text text1=new Text("Enter Number of values");
    Text text2=new Text("Seprate inputs");


    VBox pane1=new VBox();
    pane1.getChildren().addAll(text1,tf1,text2,tf2,btkey);
  Scene scene1 = new Scene(pane1,300,200);
  stage.setScene(scene1);
  stage.setTitle("Coins Game");
  stage.sizeToScene(); // ضبط حجم النافذة تلقائيًا حسب حجم الـ Scen
   stage.setResizable(false); // منع تغيير الحجم
  stage.show();

});
        setBackgroundImage(vBox3,"file:/C:/Users/Lenovo/Desktop/algorithm/coins3.png");

        // Create the scene and set it on the primary stage
        firstStage = new Stage();
         firstScene = new Scene(vBox3, 300, 400);
        firstStage.setScene(firstScene);
        firstStage.sizeToScene(); // ضبط حجم النافذة تلقائيًا حسب حجم الـ Scene
        firstStage.setResizable(false); // منع تغيير الحجم
        firstStage.show();
    }

            // Method to open the file chooser dialog
            private void openFile(Window owner) {
                // Create a FileChooser
                FileChooser fileChooser = new FileChooser();

                // Set the file extension filters
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                        new FileChooser.ExtensionFilter("All Files", "*.*")
                );

                // Show the file chooser and get the selected file
                File selectedFile = fileChooser.showOpenDialog(owner);
                if (selectedFile == null) {
                    System.out.println("No file selected");
                    showError("Error"," ","No file selected");

                    return; // Exit if no file is selected
                }

                // Read the file
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    // First line indicates the size of the coins array
                    int size = Integer.parseInt(reader.readLine().trim());
                    ints = new int[size];

                    int index = 0; // Track index for coins array
                    while ((line = reader.readLine()) != null) {
                        String[] num = line.split(",");
                        for (String n : num) {
                            if (index < size) {
                                ints[index++] = Integer.parseInt(n.trim());
                            } else {
                                throw new IllegalArgumentException("File contains more numbers than expected.");
                            }
                        }
                    }

                    if (index < size) {
                        throw new IllegalArgumentException("File contains fewer numbers than expected.");
                    }

                     strategy = new OptimalGameStrategy(ints);

                } catch (NumberFormatException e) {
                    showError("Error"," File contains invalid number format.",e.getMessage());
                } catch (IOException e) {
                    showError("Error","Error reading the file:",e.getMessage());

                } catch (IllegalArgumentException e) {

                    showError("Error"," ",e.getMessage());
                }
            }

            private void showResult(){
                // Get results
                String maxResult = String.valueOf(strategy.getMax());
                String dpTable = strategy.printDpTable(ints);
                ArrayList<Integer> solution = strategy.getSolution();

                // Create the UI
                VBox root = new VBox(10); // Vertical layout with spacing
                root.setStyle("-fx-padding: 10; -fx-alignment: center;");

                Button btmax = new Button("Expected Result");
                Button btCoins = new Button("Coins that give the result");
                Button btdp = new Button("Dp Table");
                btmax.setOnAction(e->{
                    Label label=new Label("Wiinn==>"+maxResult );
                    label.setStyle("-fx-border-color: black; -fx-padding: 15; -fx-alignment: center; -fx-wrap-text: true;");
                    label.setPrefWidth(Region.USE_COMPUTED_SIZE);  // يتيح الحجم التلقائي للـ Label بناءً على النص
                    label.setPrefHeight(Region.USE_COMPUTED_SIZE);  // نفس الشيء للارتفاع


                    Pane pane = new Pane();

                    setBackgroundImage(pane,"file:/C:/Users/Lenovo/Desktop/algorithm/coins6.jpg");

                    pane.getChildren().addAll(label,exit);
                    Scene scene = new Scene(pane, 200, 200);
                    Stage stage = new Stage();
                    stage.sizeToScene(); // ضبط حجم النافذة تلقائيًا حسب حجم الـ Scene
                    stage.setResizable(false); // منع تغيير الحجم
                    stage.setScene(scene);
                    stage.show();

                });
                btCoins.setOnAction(e->{
                    Label label = new Label(solution.toString());
                    label.setStyle("-fx-border-color: black; -fx-padding: 15; -fx-alignment: center; -fx-wrap-text: true;");
                    label.setPrefWidth(Region.USE_COMPUTED_SIZE);  // يتيح الحجم التلقائي للـ Label بناءً على النص
                    label.setPrefHeight(Region.USE_COMPUTED_SIZE);  // نفس الشيء للارتفاع

                    Pane pane = new Pane();

                    setBackgroundImage(pane,"file:/C:/Users/Lenovo/Desktop/algorithm/coins5.jpg");

                    pane.getChildren().addAll(label);
                    Scene scene = new Scene(pane, 300, 200);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.sizeToScene(); // ضبط حجم النافذة تلقائيًا حسب حجم الـ Scene
                    stage.setResizable(false); // منع تغيير الحجم
                    stage.show();
                });
                btdp.setOnAction(e->{
                    DpTableGrid dpTableGrid = new DpTableGrid();
                    GridPane gridPane = dpTableGrid.createDpTableGrid(strategy.dpTable, ints);

                    setBackgroundImage(gridPane,"file:/C:/Users/Lenovo/Desktop/algorithm/coins4.jpg");

                    Scene scene = new Scene(gridPane, 400, 400);
                    Stage stage = new Stage();
                    stage.setTitle("DP Table Grid");
                    stage.setScene(scene);
                    stage.show();
                });

                root.getChildren().addAll(btmax,btCoins,btdp);

                setBackgroundImage(root,"file:/C:/Users/Lenovo/Desktop/algorithm/coins.jpg");
                Scene scene=new Scene(root,400,400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.sizeToScene(); // ضبط حجم النافذة تلقائيًا حسب حجم الـ Scene
                stage.setResizable(false); // منع تغيير الحجم
                stage.show();

            }
    public static void showError(String title, String header, String content) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void setBackgroundImage(Pane pane, String imagePath) {

        Image image = new Image(imagePath);


        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                null,  // لا حاجة لتكرار الصورة أفقياً أو عمودياً
                null,  // لا حاجة لضبط نقطة الانطلاق
                null,  // لا حاجة لضبط نقطة الانطلاق
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, false)  // تعيين الحجم ليغطي كامل الحاوية
        );

        pane.setBackground(new Background(backgroundImage));
    }
    private String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }





    public static void main(String[] args) {
        launch(args);
    }
}