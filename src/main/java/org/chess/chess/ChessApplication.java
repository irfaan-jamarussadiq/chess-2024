package org.chess.chess;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.chess.chess.board.Location;
import org.chess.chess.game.GameController;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setCenter(new GameController().getGameView());
        root.setRight(createMoveList());

        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    private ListView<Location> createMoveList() {
        ListView<Location> moveList = new ListView<>();
        ObservableList<Location> moves = FXCollections.observableArrayList (new Location(2, 2), new Location(2, 4));
        moveList.setItems(moves);
        return moveList;
    }

    public static void main(String[] args) {
        launch();
    }
}