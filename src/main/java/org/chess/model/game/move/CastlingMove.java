package org.chess.model.game.move;

import java.util.HashMap;
import java.util.Map;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.piece.King;
import org.chess.model.piece.Piece;
import org.chess.model.piece.Rook;

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
        if (!(king instanceof King) || king.hasMoved() || board.pieceAt(getEnd()) != null) {
            return false;
        }

        if (getStart().file() != 5 || getStart().rank() != getEnd().rank()) {
            return false;
        }

        if (king.getAlliance().isWhite() && getStart().rank() != 1) {
            return false;
        }

        if (!king.getAlliance().isWhite() && getStart().rank() != 8) {
            return false;
        }

        if (getEnd().file() == 3) {
            Piece rook = board.pieceAt(new Location(getStart().rank(), 1));
            return rook instanceof Rook && !rook.hasMoved()
                    && board.isEmpty(new Location(getStart().rank(), 2))
                    && board.isEmpty(new Location(getStart().rank(), 3))
                    && board.isEmpty(new Location(getStart().rank(), 4));
        } else if (getEnd().file() == 7) {
            Piece rook = board.pieceAt(new Location(getStart().rank(), 8));
            return rook instanceof Rook && !rook.hasMoved()
                    && board.isEmpty(new Location(getStart().rank(), 6))
                    && board.isEmpty(new Location(getStart().rank(), 7));
        }

        return false;
    }

    @Override
    public Map<Location, Location> getLocationMappings(BoardModel board) {
        Map<Location, Location> locationMappings = new HashMap<>(4);

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

        locationMappings.put(getStart(), getEnd());
        locationMappings.put(rookStart, rookEnd);
        return locationMappings;
    }
}
