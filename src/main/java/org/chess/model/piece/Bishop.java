package org.chess.model.piece;

import java.util.Collection;
import java.util.HashSet;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;

public class Bishop extends Piece {
    public Bishop(Alliance alliance) {
        super(alliance);
    }
    
    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        if (Math.abs(end.rank() - start.rank()) != Math.abs(end.file() - start.file())) {
            return false;
        }

        int rankOffset = Integer.signum(end.rank() - start.rank());
        int fileOffset = Integer.signum(end.file() - start.file());
        Location current = start.offset(rankOffset, fileOffset);
        while (current.isWithinBounds() && !current.equals(end)) {
            if (!board.isEmpty(current)) {
                return false;
            }

            current = current.offset(rankOffset, fileOffset);
        }

        return current.isWithinBounds() && !Piece.areAllies(this, board.pieceAt(current));
    }

    @Override
    public Collection<Location> getPossibleDestinations(Location location) {
        Collection<Location> possibleDestinations = new HashSet<>();
        for (int step = 1; step <= 8; step++) {
            possibleDestinations.add(location.offset(-step, -step));
            possibleDestinations.add(location.offset(-step, step));
            possibleDestinations.add(location.offset(step, -step));
            possibleDestinations.add(location.offset(step, step));
        }

        return possibleDestinations;
    }

    @Override
    public boolean canMoveFrom(Location start, Location end) {
        int diffRank = Math.abs(start.rank() - end.rank());
        int diffFile = Math.abs(start.file() - end.file());
        return !start.equals(end) && diffRank == diffFile;
    }

    @Override
    public char getLetter() {
        return alliance.isWhite() ? 'B' : 'b';
    }
}
