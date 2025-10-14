package org.chess.view.game;

import javafx.scene.layout.BorderPane;

import org.chess.model.game.GameModel;
import org.chess.view.board.BoardView;

public class GameView extends BorderPane {
    private final GameModel gameModel;

    public GameView(GameModel gameModel, BoardView boardView) {
        this.gameModel = gameModel;
        super.setCenter(boardView);
    }
}
