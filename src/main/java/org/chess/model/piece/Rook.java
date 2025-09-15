package org.chess.model.piece;

import java.util.Collection;
import java.util.HashSet;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.MoveRecord;

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

        int rankOffset = diffRank == 0 ? Integer.signum(end.rank() - start.rank()) : 0;
        int fileOffset = diffFile == 0 ? Integer.signum(end.file() - start.file()) : 0;
        Location current = start.offset(rankOffset, fileOffset);
        while (current.isWithinBounds() && !current.equals(end)) {
            if (!board.isEmpty(current)) {
                return false;
            }

            current = current.offset(rankOffset, fileOffset);
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

    @Override
    public Collection<MoveRecord> getLegalMoves(Location location, BoardModel board) {
        Collection<MoveRecord> legalMoves = new HashSet<>();
        if (board.isEmpty(location)) {
            return legalMoves;
        }

        legalMoves.addAll(getMovesOnDiagonal(location, board, -1, 0));
        legalMoves.addAll(getMovesOnDiagonal(location, board, 1, 0));
        legalMoves.addAll(getMovesOnDiagonal(location, board, 0, -1));
        legalMoves.addAll(getMovesOnDiagonal(location, board, 0, 1));
        return legalMoves;
    }

    private Collection<MoveRecord> getMovesOnDiagonal(Location location, BoardModel board, int rankOffset, int fileOffset) {
        Collection<MoveRecord> diagonalMoves = new HashSet<>();

        Location currentLocation = location.offset(rankOffset, fileOffset);
        while (currentLocation.isWithinBounds()) {
            if (Piece.areAllies(this, board.pieceAt(currentLocation))) {
                break;
            }

            diagonalMoves.add(new MoveRecord(location, currentLocation));

            if (Piece.areEnemies(this, board.pieceAt(currentLocation))) {
                break;
            }

            currentLocation = currentLocation.offset(rankOffset, fileOffset);
        }

        return diagonalMoves;
    }
}
