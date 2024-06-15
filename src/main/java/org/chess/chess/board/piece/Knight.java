package org.chess.chess.board.piece;

import org.chess.chess.board.BoardModel;
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

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        int diffRank = Math.abs(end.rank() - start.rank());
        int diffFile = Math.abs(end.file() - start.file());

        if ((diffRank == 2 && diffFile == 1) || (diffRank == 1 && diffFile == 2)) {
            return start.isWithinBounds() && end.isWithinBounds() && !this.isFriendOf(board.pieceAt(end));
        }

        return false;
    }
}
