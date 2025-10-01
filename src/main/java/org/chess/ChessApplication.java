package org.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.chess.controller.GameController;
import org.chess.model.board.BoardModel;
import org.chess.model.game.GameModel;
import org.chess.view.game.GameView;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        BoardModel boardModel = new BoardModel();
        GameModel model = new GameModel(boardModel);
        GameView view = new GameView(boardModel);
        new GameController(model, view);
        root.setCenter(view);

        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}