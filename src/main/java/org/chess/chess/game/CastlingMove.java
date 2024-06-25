package org.chess.chess.game;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.King;
import org.chess.chess.board.piece.Piece;
import org.chess.chess.board.piece.Rook;

public class CastlingMove extends Move {
    public CastlingMove(Location start, Location end) {
        super(start, end);
    }

    @Override
    public void execute(BoardModel board) {
        board.movePiece(getStart(), getEnd());

        Location rookStart, rookEnd;
        if (getStart().file() > getEnd().file()) {
            // Long castling
            rookStart = new Location(getStart().rank(), 1);
            rookEnd = new Location(getStart().rank(), 4);
        } else {
            // Short castling
            rookStart = new Location(getStart().rank(), 8);
            rookEnd = new Location(getStart().rank(), 6);
        }

        board.movePiece(rookStart, rookEnd);
    }

    @Override
    public boolean isValid(BoardModel board) {
        Piece king = board.pieceAt(getStart());
        if (!(king instanceof King) || board.pieceAt(getEnd()) == null) {
            return false;
        }

        if (getStart().file() != 5 || getStart().rank() != getEnd().rank()) {
            return false;
        }

        if (king.getAlliance() == Alliance.WHITE && getStart().rank() != 1) {
            return false;
        }

        if (king.getAlliance() == Alliance.BLACK && getStart().rank() != 8) {
            return false;
        }

        if (getEnd().file() == 3) {
            Piece rook = board.pieceAt(new Location(getStart().rank(), 1));
            return rook instanceof Rook
                    && board.isEmpty(new Location(getStart().rank(), 2))
                    && board.isEmpty(new Location(getStart().rank(), 3))
                    && board.isEmpty(new Location(getStart().rank(), 4));
        } else if (getEnd().file() == 7) {
            Piece rook = board.pieceAt(new Location(getStart().rank(), 8));
            return rook instanceof Rook
                    && board.isEmpty(new Location(getStart().rank(), 6))
                    && board.isEmpty(new Location(getStart().rank(), 7));
        }

        return false;
    }
}
