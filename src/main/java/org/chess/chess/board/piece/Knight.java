package org.chess.chess.board.piece;

import org.chess.chess.game.Direction;
import org.chess.chess.game.Move;
import org.chess.chess.board.Alliance;
import org.chess.chess.board.Location;

import java.util.List;

public class Knight extends Piece {
    public Knight(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Move> getCandidateMoves(Location location) {
        return List.of(
                new Move(location, location.shift(new Direction(-2, -1))),
                new Move(location, location.shift(new Direction(-2, 1))),
                new Move(location, location.shift(new Direction(-1, -2))),
                new Move(location, location.shift(new Direction(-1, 2))),
                new Move(location, location.shift(new Direction(1, -2))),
                new Move(location, location.shift(new Direction(1, 2))),
                new Move(location, location.shift(new Direction(2, -1))),
                new Move(location, location.shift(new Direction(2, 1)))
        );
    }
}
