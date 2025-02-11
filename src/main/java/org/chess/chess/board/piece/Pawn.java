package org.chess.chess.board.piece;

import org.chess.chess.board.BoardModel;
import org.chess.chess.game.Direction;
import org.chess.chess.board.Alliance;
import org.chess.chess.board.Location;
import org.chess.chess.game.Path;

import java.util.List;

public class Pawn extends Piece {

    public Pawn(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Path> getCandidatePaths(Location location) {
        int pawnRankOffset = (getAlliance() == Alliance.WHITE) ? 1 : -1;
        return List.of(
                new Path(location, new Direction(pawnRankOffset, 0)),
                new Path(location, new Direction(2 * pawnRankOffset, 0)),
                new Path(location, new Direction(pawnRankOffset, -1)),
                new Path(location, new Direction(pawnRankOffset, 1))
        );
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        if (!start.isWithinBounds() || !end.isWithinBounds()) {
            return false;
        }

        Piece endPiece = board.pieceAt(end);

        int pawnDir = (getAlliance() == Alliance.WHITE) ? 1 : -1;
        Location leftPawnLocation = new Location(start.rank() + pawnDir, start.file() - 1);
        Location rightPawnLocation = new Location(start.rank() + pawnDir, start.file() + 1);

        if (leftPawnLocation.isWithinBounds() && end.equals(leftPawnLocation)) {
            Location enPassantLocation = new Location(start.rank(), end.file());
            Piece enPassantPawn = new Pawn(getAlliance() == Alliance.WHITE ? Alliance.BLACK : Alliance.WHITE);
            Piece actualEnPassantPiece = board.pieceAt(enPassantLocation);
            return this.isEnemyOf(endPiece)
                    || (this.isEnemyOf(actualEnPassantPiece) && actualEnPassantPiece.equals(enPassantPawn)
                    && leftPawnLocation.rank() == getAlliance().getEnPassantEndingRank());
        }

        if (rightPawnLocation.isWithinBounds() && end.equals(rightPawnLocation)) {
            Location enemyPawnLocation = new Location(start.rank(), end.file());
            return this.isEnemyOf(endPiece)
                    || (this.isEnemyOf(board.pieceAt(enemyPawnLocation))
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
}
