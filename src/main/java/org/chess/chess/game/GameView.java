package org.chess.chess.game;

import javafx.scene.layout.BorderPane;
import org.chess.chess.board.BoardView;
import org.chess.chess.board.Location;

import java.util.List;

public class GameView extends BorderPane {
    private final BoardView boardView;

    public GameView() {
        this.boardView = new BoardView();
        this.setCenter(boardView);
    }

    public void highlightSquares(List<Move> moves) {
        for (Move move : moves) {
            if (move.isWithinBounds()) {
                boardView.highlightSquare(move.end());
            }
        }
    }

    public void resetSquares(List<Move> moves) {
        for (Move move : moves) {
            if (move.isWithinBounds()) {
                boardView.resetSquare(move.end());
            }
        }
    }

    public void move(Location start, Location end) {
        boardView.move(start, end);
    }
}
