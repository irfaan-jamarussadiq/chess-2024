package org.chess.chess.board.piece;

import org.chess.chess.Direction;
import org.chess.chess.Move;
import org.chess.chess.board.Alliance;
import org.chess.chess.board.Location;

import java.util.List;

public class King extends Piece {
    public King(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Move> getCandidateMoves(Location location) {
        return List.of(
                new Move(location, location.shift(new Direction(-1, -1))),
                new Move(location, location.shift(new Direction(-1, 0))),
                new Move(location, location.shift(new Direction(-1, 1))),
                new Move(location, location.shift(new Direction(0, -1))),
                new Move(location, location.shift(new Direction(0, 1))),
                new Move(location, location.shift(new Direction(1, -1))),
                new Move(location, location.shift(new Direction(1, 0))),
                new Move(location, location.shift(new Direction(1, 1)))
        );
    }
}
