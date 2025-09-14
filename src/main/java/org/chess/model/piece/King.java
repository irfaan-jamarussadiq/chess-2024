package org.chess.model.piece;

import java.util.Collection;
import java.util.Set;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;

public class King extends Piece {
    public King(Alliance alliance) {
        super(alliance);
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        if (!start.isWithinBounds() || !end.isWithinBounds() || start.equals(end)) {
            return false;
        }

        // Check if castling move
        if (start.file() == 5 && (start.rank() == 1 || start.rank() == 8) && start.rank() == end.rank()
                && (end.file() == 7 || end.file() == 3)) {
            return !Piece.areAllies(this, board.pieceAt(end));
        }

        int diffRank = Math.abs(end.rank() - start.rank());
        int diffFile = Math.abs(end.file() - start.file());
        return diffRank <= 1 && diffFile <= 1 && !Piece.areAllies(this, board.pieceAt(end));
    }

    @Override
    public Collection<Location> getPossibleDestinations(Location location) {
        return Set.of(
            location.offset(-1, -1),
            location.offset(0, -1),
            location.offset(1, -1),
            location.offset(-1, 0),
            location.offset(-1, 1),
            location.offset(1, 1)
        );
    }

    @Override
    public boolean canMoveFrom(Location start, Location end) {
        int diffRank = Math.abs(start.rank() - end.rank());
        int diffFile = Math.abs(start.file() - end.file());
        return !start.equals(end) && diffRank <= 1 && diffFile <= 1;
    }

    @Override
    public char getLetter() {
        return alliance.isWhite() ? 'K' : 'k';
    }
}
