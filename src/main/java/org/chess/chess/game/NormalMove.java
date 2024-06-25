package org.chess.chess.game;

import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Piece;

public class NormalMove extends Move {
    public NormalMove(Location start, Location end) {
        super(start, end);
    }

    @Override
    public void execute(BoardModel board) {
        board.movePiece(getStart(), getEnd());
    }

    @Override
    public boolean isValid(BoardModel board) {
        Piece piece = board.pieceAt(getStart());
        return piece.canMoveFrom(getStart(), getEnd(), board);
    }
}
