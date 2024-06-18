package org.chess.chess.game;

import javafx.scene.layout.BorderPane;
import org.chess.chess.board.BoardView;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Piece;

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
                boardView.highlightSquare(move.getEnd());
            }
        }
    }

    public void resetSquares(List<Move> moves) {
        for (Move move : moves) {
            if (move.isWithinBounds()) {
                boardView.resetSquare(move.getEnd());
            }
        }
    }

    public void move(Location start, Location end) {
        Piece pieceStart = boardView.pieceAt(start);
        Piece pieceEnd = boardView.pieceAt(end);

        if (pieceStart.isFriendOf(pieceEnd)) {
            return;
        }

        boardView.move(start, end);
    }
}
