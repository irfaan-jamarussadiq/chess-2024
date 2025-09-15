package org.chess.model.piece;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.MoveRecord;

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

    @Override
    public Collection<MoveRecord> getLegalMoves(Location location, BoardModel board) {
        Collection<MoveRecord> legalMoves = new HashSet<>();
        if (board.isEmpty(location)) {
            return legalMoves;
        }

        legalMoves.add(new MoveRecord(location, location.offset(-1, -1)));
        legalMoves.add(new MoveRecord(location, location.offset(-1, 1)));
        legalMoves.add(new MoveRecord(location, location.offset(0, -1)));
        legalMoves.add(new MoveRecord(location, location.offset(0, 1)));
        legalMoves.add(new MoveRecord(location, location.offset(1, -1)));
        legalMoves.add(new MoveRecord(location, location.offset(-1, 0)));
        legalMoves.add(new MoveRecord(location, location.offset(1, 0)));
        legalMoves.add(new MoveRecord(location, location.offset(1, 1)));

        if (location.rank() == alliance.getStartingPieceRank() && location.file() == 5) {
            legalMoves.add(new MoveRecord(location, new Location(alliance.getStartingPieceRank(), 3)));
            legalMoves.add(new MoveRecord(location, new Location(alliance.getStartingPieceRank(), 7)));
        }

        return legalMoves;
    }
}
