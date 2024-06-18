package org.chess.chess.game;

import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Piece;

import java.util.List;

public class Move {
    private final Location start;
    private final Location end;

    public Move(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    public boolean isWithinBounds() {
        return start.isWithinBounds() && end.isWithinBounds();
    }

    public void execute(BoardModel board) {
        board.movePiece(start, end);
    }

    public boolean isValid(BoardModel board) {
        Piece piece = board.pieceAt(getStart());
        return piece.canMoveFrom(start, end, board);
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }
}
