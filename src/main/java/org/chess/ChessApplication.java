package org.chess;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

import org.chess.controller.GameController;
import org.chess.model.game.GameModel;
import org.chess.model.game.move.Move;
import org.chess.view.game.GameView;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        GameView view = new GameView();
        GameModel model = new GameModel();
        GameController controller = new GameController(model, view);
        root.setCenter(view);
        root.setRight(createMoveList(model));

        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    private ListView<String> createMoveList(GameModel model) {
        List<String> moveHistory = model.getHistory()
                .stream()
                .map(Move::toString)
                .toList();
        ObservableList<String> moves = FXCollections.observableList(moveHistory);
        ListView<String> moveList = new ListView<>();
        moveList.setItems(moves);
        return moveList;
    }

    public static void main(String[] args) {
        launch();
    }
}