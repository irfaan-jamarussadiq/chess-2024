package org.chess.view.game;

import javafx.scene.layout.BorderPane;

import org.chess.model.board.Location;
import org.chess.model.game.move.Move;
import org.chess.model.piece.Piece;
import org.chess.view.board.BoardView;

import java.util.List;
import java.util.Map;

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

    public void move(Move move, Map<Location, Location> toUpdate) {
        Piece pieceStart = boardView.pieceAt(move.getStart());
        Piece pieceEnd = boardView.pieceAt(move.getEnd());

        if (Piece.areAllies(pieceStart, pieceEnd)) {
            return;
        }

        for (Location location : toUpdate.keySet()) {
            boardView.move(location, toUpdate.get(location));
        }
    }
}
