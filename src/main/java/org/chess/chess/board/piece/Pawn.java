package org.chess.chess.board.piece;

import org.chess.chess.board.BoardModel;
import org.chess.chess.game.Direction;
import org.chess.chess.game.Move;
import org.chess.chess.board.Alliance;
import org.chess.chess.board.Location;

import java.util.List;

public class Pawn extends Piece {
    public Pawn(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Move> getCandidateMoves(Location location) {
        int pawnRankOffset = (getAlliance() == Alliance.WHITE) ? 1 : -1;
        return List.of(
                new Move(location, location.shift(new Direction(pawnRankOffset, 0))),
                new Move(location, location.shift(new Direction(pawnRankOffset, 0), 2)),
                new Move(location, location.shift(new Direction(pawnRankOffset, -1))),
                new Move(location, location.shift(new Direction(pawnRankOffset, 1)))
        );
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        Piece endPiece = board.pieceAt(end);

        int pawnDir = (getAlliance() == Alliance.WHITE) ? 1 : -1;
        Location leftPawnLocation = new Location(start.rank() + pawnDir, start.file() - 1);
        Location rightPawnLocation = new Location(start.rank() + pawnDir, start.file() + 1);

        if (leftPawnLocation.isWithinBounds() && end.equals(leftPawnLocation)) {
            return !this.isFriend(endPiece);
        }

        if (rightPawnLocation.isWithinBounds() && end.equals(rightPawnLocation)) {
            return !this.isFriend(endPiece);
        }

        Location oneSquareForward = new Location(start.rank() + pawnDir, start.file());
        if (oneSquareForward.isWithinBounds() && end.equals(oneSquareForward)) {
            return board.isEmpty(oneSquareForward);
        }

        Location twoSquaresForward = new Location(start.rank() + 2 * pawnDir, start.file());
        if (twoSquaresForward.isWithinBounds() && end.equals(twoSquaresForward)) {
            return board.isEmpty(twoSquaresForward);
        }

        return false;
    }
}
