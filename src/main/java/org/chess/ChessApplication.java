package org.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.chess.controller.GameController;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        GameController controller = new GameController();
        root.setCenter(controller.getView());

        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}