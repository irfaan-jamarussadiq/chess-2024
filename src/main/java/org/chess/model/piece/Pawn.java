package org.chess.model.piece;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.MoveRecord;

public class Pawn extends Piece {

    public Pawn(Alliance alliance) {
        super(alliance);
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        if (!start.isWithinBounds() || !end.isWithinBounds()) {
            return false;
        }

        Piece endPiece = board.pieceAt(end);

        int pawnDir = alliance.getPawnDirection();
        Location leftPawnLocation = new Location(start.rank() + pawnDir, start.file() - 1);
        Location rightPawnLocation = new Location(start.rank() + pawnDir, start.file() + 1);

        if (leftPawnLocation.isWithinBounds() && end.equals(leftPawnLocation)) {
            Location enPassantLocation = new Location(start.rank(), end.file());
            Piece enPassantPawn = new Pawn(alliance == Alliance.WHITE ? Alliance.BLACK : Alliance.WHITE);
            Piece actualEnPassantPiece = board.pieceAt(enPassantLocation);
            return Piece.areEnemies(this, endPiece)
                    || (Piece.areEnemies(this, actualEnPassantPiece) && actualEnPassantPiece.equals(enPassantPawn)
                    && leftPawnLocation.rank() == getAlliance().getEnPassantEndingRank());
        }

        if (rightPawnLocation.isWithinBounds() && end.equals(rightPawnLocation)) {
            Location enemyPawnLocation = new Location(start.rank(), end.file());
            return Piece.areEnemies(this, endPiece)
                    || (Piece.areEnemies(this, board.pieceAt(enemyPawnLocation))
                        && rightPawnLocation.rank() == getAlliance().getEnPassantEndingRank());
        }

        Location oneSquareForward = new Location(start.rank() + pawnDir, start.file());
        if (oneSquareForward.isWithinBounds() && end.equals(oneSquareForward)) {
            return board.isEmpty(oneSquareForward);
        }

        Location twoSquaresForward = new Location(start.rank() + 2 * pawnDir, start.file());
        if (twoSquaresForward.isWithinBounds() && end.equals(twoSquaresForward)) {
            return !hasMoved() && board.isEmpty(oneSquareForward) && board.isEmpty(twoSquaresForward);
        }

        return false;
    }

    @Override
    public Collection<Location> getPossibleDestinations(Location location) {
        return Set.of(
            location.offset(alliance.getPawnDirection(), 0),
            location.offset(2 * alliance.getPawnDirection(), 0),
            location.offset(alliance.getPawnDirection(), -1),
            location.offset(alliance.getPawnDirection(), 1)
        );
    }

    @Override
    public boolean canMoveFrom(Location start, Location end) {
        int diffRank = end.rank() - start.rank();
        int diffFile = end.file() - start.file();
        int pawnDirection = alliance.getPawnDirection();
        return (diffRank == pawnDirection && diffFile == 0)
            || (diffRank == 2 * pawnDirection && diffFile == 0)
            || (diffRank == pawnDirection && diffFile == -1)
            || (diffRank == pawnDirection && diffFile == 1); 
    }

    @Override
    public char getLetter() {
        return alliance.isWhite() ? 'P' : 'p';
    }

    @Override
    public Collection<MoveRecord> getLegalMoves(Location location, BoardModel board) {
        Collection<MoveRecord> legalMoves = new HashSet<>();

        Collection<Location> possibleDestinations = getPossibleDestinations(location);
        for (Location possibleDestination : possibleDestinations) {
            if (!Piece.areAllies(this, board.pieceAt(possibleDestination))) {
                legalMoves.add(new MoveRecord(location, possibleDestination));
            }
        }

        return legalMoves;
    }
}
