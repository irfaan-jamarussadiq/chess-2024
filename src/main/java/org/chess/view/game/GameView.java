package org.chess.view.game;

import javafx.scene.layout.BorderPane;

import org.chess.model.game.Move;
import org.chess.view.board.BoardView;

import java.util.Collection;

public class GameView extends BorderPane {
    private final BoardView boardView;

    public GameView() {
        this.boardView = new BoardView();
        this.setCenter(boardView);
    }

    public void highlightSquares(Collection<Move> movesHighlighted) {
        for (Move move : movesHighlighted) {
            boardView.highlightSquare(move.end());
        }
    }

    public void resetSquares(Collection<Move> movesHighlighted) {
        for (Move move : movesHighlighted) {
            boardView.resetSquare(move.end());
        }
    }

}
