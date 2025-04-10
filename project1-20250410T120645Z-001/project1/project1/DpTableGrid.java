package com.example.project1;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
public class DpTableGrid {

    // الطريقة التي تُرجع GridPane بناءً على dpTable و coins
    public GridPane createDpTableGrid(int[][] dpTable, int[] coins) {
        // إنشاء GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // المسافة بين الأعمدة
        gridPane.setVgap(10); // المسافة بين الصفوف

        // إضافة العناوين (الصف الأول: العناوين الرأسية)
        for (int j = 0; j < coins.length; j++) {
            Label headerLabel = new Label(String.valueOf(coins[j]));
            headerLabel.setFont(new Font(16));
            gridPane.add(headerLabel, j + 1, 0); // إضافة في الصف الأول
        }

        // إضافة العناوين (العمود الأول: عناوين الصفوف)
        for (int i = 0; i < coins.length; i++) {
            Label rowLabel = new Label(String.valueOf(coins[i]));
            rowLabel.setFont(new Font(16));
            gridPane.add(rowLabel, 0, i + 1); // إضافة في العمود الأول
        }

        // إضافة القيم من dpTable داخل مربعات
        for (int i = 0; i < dpTable.length; i++) {
            for (int j = 0; j < dpTable[i].length; j++) {
                // إنشاء مربع يحتوي على القيمة
                StackPane cell = createCell(String.valueOf(dpTable[i][j]));
                gridPane.add(cell, j + 1, i + 1); // إضافة الخلية في GridPane
            }
        }

        return gridPane; // إرجاع الشبكة
    }

    // دالة لإنشاء خلية مربعة تحتوي على القيمة
    private StackPane createCell(String value) {
        Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.GREEN);

        Label label = new Label(value);
        label.setFont(new Font(14));

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, label);

        return stackPane;
    }
}
