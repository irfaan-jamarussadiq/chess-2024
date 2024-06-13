package org.chess.chess.game;

import javafx.scene.layout.BorderPane;
import org.chess.chess.board.BoardView;

public class GameView extends BorderPane {
    private final BoardView boardView;

    public GameView() {
        this.boardView = new BoardView();
        this.setCenter(boardView);
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
