package com.example.project1;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class ArrayDisplay {

    private int[] array;
    private boolean playerOneTurn = true;
    private List<Integer> playerOneChoices = new ArrayList<>();
    private List<Integer> playerTwoChoices = new ArrayList<>();
    TextField tfname1;
    TextField tfname2;

    public void displayArrayFromInput(Stage stage) {
        VBox root = new VBox(10);

        Label larray = new Label("Enter array elements separated by spaces:");
        TextField inputField = new TextField();
        inputField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                    String input = event.getCharacter();
                    if (!input.matches("[0-9 ]")) {
                        event.consume();
                    }
                });
        inputField.setPromptText("Enter array elements separated by spaces");
        Button submitButton = new Button("Submit");
        Button fileButton = new Button("Load from File");
        Label first = new Label("First player name:");
         tfname1 = new TextField();

        Label Second = new Label("Second player name:");
         tfname2 = new TextField();


        submitButton.setStyle(" -fx-text-fill: black; -fx-font-size: 12px; -fx-border-color: green; ");
        fileButton.setStyle(" -fx-text-fill: black; -fx-font-size: 12px; -fx-border-color: green;");

        tfname1.setStyle("-fx-background-color: lightyellow; -fx-font-size: 12px; -fx-text-fill: black; -fx-border-color: green; ");
        tfname2.setStyle("-fx-background-color: lightyellow; -fx-font-size: 12px; -fx-text-fill: black; -fx-border-color: green;");
        inputField.setStyle("-fx-background-color: lightyellow; -fx-font-size: 12px; -fx-text-fill: black; -fx-border-color: green; ");
        first.setStyle(
                "-fx-font-size: 14px;" + "-fx-text-fill: black;" + "-fx-background-color: lightyellow;" + "-fx-border-color: green;"
        );
        Second.setStyle(
                "-fx-font-size: 14px;" + "-fx-text-fill: black;" + "-fx-background-color: lightyellow;" + "-fx-border-color: green;"
        );
        larray.setStyle(
                "-fx-font-size: 14px;" + "-fx-text-fill: black;" + "-fx-background-color: lightyellow;" + "-fx-border-color: green;"
        );



        tfname1.setPrefWidth(130);
        tfname1.setMaxWidth(130);
        tfname2.setPrefWidth(130);
        tfname2.setMaxWidth(130);
        inputField.setPrefWidth(200);
        inputField.setMaxWidth(200);

        submitButton.setOnAction(e -> {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                showError("Error", "Input field is empty.");
                return;
            }
            try {
                array = parseArray(input);
                showArrayInGrid(stage);
            } catch (NumberFormatException ex) {
                showError("Error", "Invalid input. Please enter numbers separated by spaces.");
            }
        });

        fileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                try {
                    array = readArrayFromFile(file);
                    showArrayInGrid(stage);
                } catch (IOException | NumberFormatException ex) {
                    showError("Error", "Error reading file: " + ex.getMessage());
                }
            }
        });

        root.getChildren().addAll(larray,inputField,first, tfname1,Second,tfname2,submitButton, fileButton);
        setBackgroundImage(root,"file:/C:/Users/Lenovo/Desktop/algorithm/player.jpg");

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private int[] parseArray(String input) {
        String[] tokens = input.split("\\s+");
        int[] result = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            result[i] = Integer.parseInt(tokens[i]);
        }
        return result;
    }

    private int[] readArrayFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return parseArray(line);
        }
    }

    public void showArrayInGrid(Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-padding: 20; -fx-alignment: center;");

        int[] leftIndex = {0};
        int[] rightIndex = {array.length - 1};

        for (int i = 0; i < array.length; i++) {
            Label label = new Label(String.valueOf(array[i]));
            label.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-alignment: center;");
            label.setPrefWidth(50);
            label.setPrefHeight(30);
            label.setAlignment(javafx.geometry.Pos.CENTER);

            int index = i;
            label.setOnMouseClicked(event -> {
                if (index == leftIndex[0]) { // اختيار من الطرف الأيسر
                    handleChoice(label, array[index]);
                    leftIndex[0]++;
                } else if (index == rightIndex[0]) { // اختيار من الطرف الأيمن
                    handleChoice(label, array[index]);
                    rightIndex[0]--;
                }

                // إذا انتهت اللعبة
                if (leftIndex[0] > rightIndex[0]) {
                    endGame(stage);
                }
            });

            gridPane.add(label, i, 0);
        }

        Scene scene = new Scene(gridPane, 400, 100);
        stage.setTitle("Two Player Game");
        stage.setScene(scene);
        stage.show();
    }

    private void handleChoice(Label label, int value) {
        label.setText("X");
        label.setDisable(true); // منع النقر على العنصر مرة أخرى

        if (playerOneTurn) {
            playerOneChoices.add(value);
        } else {
            playerTwoChoices.add(value);
        }

        playerOneTurn = !playerOneTurn; // تبديل الدور
    }

    private void endGame(Stage stage) {
        VBox resultPane = new VBox(10);
        resultPane.setStyle("-fx-padding: 20; -fx-alignment: center;");

        int playerOneSum = calculateSum(playerOneChoices);
        int playerTwoSum = calculateSum(playerTwoChoices);


        Label playerOneLabel = new Label(tfname1.getText() + " Choices: " + playerOneChoices + " | Sum: " + playerOneSum);
        Label playerTwoLabel = new Label(tfname2.getText() + " Choices: " + playerTwoChoices + " | Sum: " + playerTwoSum);
        playerOneLabel.setStyle("-fx-background-color: lightyellow; -fx-font-size: 14px; -fx-text-fill: black; -fx-border-color: green; ");
        playerTwoLabel.setStyle("-fx-background-color: lightyellow; -fx-font-size: 14px; -fx-text-fill: black; -fx-border-color: green; ");


        String winnerText;
        if (playerOneSum > playerTwoSum) {
            winnerText = "Winner: " + tfname1.getText() + " with a sum of " + playerOneSum;
        } else if (playerTwoSum > playerOneSum) {
            winnerText = "Winner: " + tfname2.getText() + " with a sum of " + playerTwoSum;
        } else {
            winnerText = "It's a tie! Both players have a sum of " + playerOneSum;
        }

        Label winnerLabel = new Label(winnerText);
        winnerLabel.setStyle("-fx-background-color: lightyellow; -fx-font-size: 14px; -fx-text-fill: black; -fx-border-color: green; ");


        resultPane.getChildren().addAll(playerOneLabel, playerTwoLabel, winnerLabel);
        setBackgroundImage(resultPane,"file:/C:/Users/Lenovo/Desktop/algorithm/player.jpg");

        Scene resultScene = new Scene(resultPane, 400, 250);
        stage.setTitle("Game Over");
        stage.setScene(resultScene);
    }


    private int calculateSum(List<Integer> choices) {
        int sum = 0;
        for (int value : choices) {
            sum += value;
        }
        return sum;
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void setBackgroundImage(Pane pane, String imagePath) {

        Image image = new Image(imagePath);


        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                null,  
                null,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, false)
        );

        pane.setBackground(new Background(backgroundImage));
    }

}
