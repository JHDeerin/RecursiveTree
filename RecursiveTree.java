/*****************************************************
* RecursiveTree.java
* By: Jake Deerin
*
* Draws a simple fractal tree using a recursive method with a variable rotation
*
*/

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class RecursiveTree extends Application {
    private final double NEW_BRANCH_SCALE = 0.67;
    private final double START_LENGTH = 350.0;
    private final double MIN_LENGTH = 2.0;
    private final double START_ANGLE = Math.toRadians(-90);

    private final double LEAF_RADIUS = 3.0;
    private boolean drawLeaves = false;

    private double rotateAngle = Math.toRadians(22);

    private VBox layout = new VBox();
    private AnchorPane canvas = new AnchorPane();

    public void drawBranch(double startX, double startY, double length, double angle) {
        if (length > MIN_LENGTH) {
            double endX = startX + Math.cos(angle) * length;
            double endY = startY + Math.sin(angle) * length;

            Line newBranch = new Line(startX, startY, endX, endY);
            newBranch.setStroke(Color.WHITE);

            canvas.getChildren().add(newBranch);

            drawBranch(endX, endY, length * NEW_BRANCH_SCALE, angle + rotateAngle);
            drawBranch(endX, endY, length * NEW_BRANCH_SCALE, angle - rotateAngle);
        } else if (drawLeaves) {
            Ellipse newLeaf = new Ellipse(startX, startY, LEAF_RADIUS, LEAF_RADIUS);
            newLeaf.setFill(Color.GREEN);
            newLeaf.setStroke(Color.TRANSPARENT);
            canvas.getChildren().add(newLeaf);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recursive Tree - Jake Deerin");
        layout.setStyle("-fx-background-color: black;");
        primaryStage.setScene(new Scene(layout, 1600, 1100));

        canvas.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
        canvas.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() - 50);
        canvas.setMaxHeight(1050);
        layout.getChildren().add(canvas);

        Slider angleSlider = new Slider();
        angleSlider.setMin(0);
        angleSlider.setMax(Math.PI);
        angleSlider.setValue(rotateAngle);
        //updates tree when slider value is changed
        angleSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
                canvas.getChildren().clear();
                rotateAngle = angleSlider.getValue();
                drawBranch(canvas.getBoundsInParent().getWidth() / 2,
                    canvas.getBoundsInParent().getHeight() - 10, START_LENGTH, START_ANGLE);
            });
        layout.getChildren().add(angleSlider);

        drawBranch(800, 1040, START_LENGTH, START_ANGLE);

        primaryStage.show();
    }
}
