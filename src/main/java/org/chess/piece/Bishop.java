package org.chess.piece;

import org.chess.board.Alliance;
import org.chess.board.BoardModel;
import org.chess.board.Location;
import org.chess.game.Direction;

import java.util.Collection;
import java.util.HashSet;

public class Bishop extends Piece {
    public Bishop(Alliance alliance) {
        super(alliance);
    }
    
    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        if (Math.abs(end.rank() - start.rank()) != Math.abs(end.file() - start.file())) {
            return false;
        }

        int rankOffset = (start.rank() < end.rank()) ? 1 : -1;
        int fileOffset = (start.file() < end.file()) ? 1 : -1;
        Direction direction = new Direction(rankOffset, fileOffset);

        Location current = start.shift(direction);
        while (current.isWithinBounds() && !current.equals(end)) {
            if (!board.isEmpty(current)) {
                return false;
            }

            current = current.shift(direction);
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
}
