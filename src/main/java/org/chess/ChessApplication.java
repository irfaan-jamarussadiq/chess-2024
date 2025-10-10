package org.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.chess.controller.GameController;
import org.chess.model.board.BoardModel;
import org.chess.model.game.GameModel;
import org.chess.view.board.BoardViewBuilder;
import org.chess.view.game.GameViewBuilder;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        BoardModel boardModel = new BoardModel();
        GameModel gameModel = new GameModel(boardModel);
        BoardViewBuilder boardViewBuilder = new BoardViewBuilder(boardModel);
        GameViewBuilder view = new GameViewBuilder(gameModel, boardViewBuilder);
        new GameController(gameModel, view);
        root.setCenter(view.build());

        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}