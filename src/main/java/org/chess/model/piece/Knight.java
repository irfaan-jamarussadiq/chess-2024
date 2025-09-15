package org.chess.model.piece;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.MoveRecord;

public class Knight extends Piece {
    public Knight(Alliance alliance) {
        super(alliance);
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        int diffRank = Math.abs(end.rank() - start.rank());
        int diffFile = Math.abs(end.file() - start.file());

        if ((diffRank == 2 && diffFile == 1) || (diffRank == 1 && diffFile == 2)) {
            return start.isWithinBounds() && end.isWithinBounds() && !Piece.areAllies(this, board.pieceAt(end));
        }

        return false;
    }

    @Override
    public Collection<Location> getPossibleDestinations(Location location) {
        return Set.of(
            location.offset(-2, -1),
            location.offset(-2, 1),
            location.offset(-1, -2),
            location.offset(-1, 2),
            location.offset(1, -2),
            location.offset(1, 2),
            location.offset(2, -1),
            location.offset(2, 1)
        );
    }

    @Override
    public boolean canMoveFrom(Location start, Location end) {
        int diffRank = Math.abs(start.rank() - end.rank());
        int diffFile = Math.abs(start.file() - end.file());
        return !start.equals(end) && ((diffRank == 2 && diffFile == 1) || (diffRank == 1 && diffFile == 2));
    }
    
    @Override
    public char getLetter() {
        return alliance.isWhite() ? 'N' : 'n';
    }

    @Override
    public Collection<MoveRecord> getLegalMoves(Location location, BoardModel board) {
        Collection<MoveRecord> legalMoves = new HashSet<>();
        if (board.isEmpty(location)) {
            return legalMoves;
        }

        Collection<Location> possibleDestinations = getPossibleDestinations(location);
        for (Location possibleDestination : possibleDestinations) {
            if (!Piece.areAllies(this, board.pieceAt(possibleDestination))) {
                legalMoves.add(new MoveRecord(location, possibleDestination));
            }
        }

        return legalMoves;
    }
}
