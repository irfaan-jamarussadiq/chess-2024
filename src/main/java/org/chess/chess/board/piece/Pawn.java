package org.chess.chess.board.piece;

import org.chess.chess.Direction;
import org.chess.chess.Move;
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
}
