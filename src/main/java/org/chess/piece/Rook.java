package org.chess.piece;

import org.chess.board.Alliance;
import org.chess.board.BoardModel;
import org.chess.board.Location;
import org.chess.game.Direction;

import java.util.Collection;
import java.util.HashSet;

public class Rook extends Piece {
    public Rook(Alliance alliance) {
        super(alliance);
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        int diffRank = Math.abs(end.rank() - start.rank());
        int diffFile = Math.abs(end.file() - start.file());
        if (diffRank != 0 && diffFile != 0) {
            return false;
        }

        Direction direction;
        if (diffRank == 0) {
            int fileOffset = (start.file() < end.file()) ? 1 : -1;
            direction = new Direction(0, fileOffset);
        } else { // diffFile == 0
            int rankOffset = (start.rank() < end.rank()) ? 1 : -1;
            direction = new Direction(rankOffset, 0);
        }

        Location current = start.offset(direction.rankOffset(), direction.fileOffset());
        while (current.isWithinBounds() && !current.equals(end)) {
            if (!board.isEmpty(current)) {
                return false;
            }

            current = current.offset(direction.rankOffset(), direction.fileOffset());
        }

        return current.isWithinBounds() && current.equals(end) && !Piece.areAllies(this, board.pieceAt(current));
    }

    @Override
    public Collection<Location> getPossibleDestinations(Location location) {
        Collection<Location> possibleDestinations = new HashSet<>();
        for (int step = 1; step <= 8; step++) {
            possibleDestinations.add(location.offset(-step, 0));
            possibleDestinations.add(location.offset(step, 0));
            possibleDestinations.add(location.offset(0, -step));
            possibleDestinations.add(location.offset(0, step));
        }

        return possibleDestinations;
    }

    @Override
    public boolean canMoveFrom(Location start, Location end) {
        int diffRank = Math.abs(start.rank() - end.rank());
        int diffFile = Math.abs(start.file() - end.file());
        return !start.equals(end) && ((diffRank != 0 && diffFile == 0) || (diffFile == 0 && diffFile != 0));
    }
    
    @Override
    public char getLetter() {
        return alliance.isWhite() ? 'R' : 'r';
    }
}
