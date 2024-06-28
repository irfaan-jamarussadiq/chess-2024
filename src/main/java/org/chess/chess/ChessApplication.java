package org.chess.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.chess.chess.game.GameController;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        GameController controller = new GameController();
        root.getChildren().add(controller.getGameView());
        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}